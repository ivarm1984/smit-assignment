package ee.ivar.smit.proovitoo.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile("dev-keycloak")
@Configuration
@Log4j2
public class KeycloakConfig {

    @PostConstruct
    public void createClient() {
        log.info("Setting up keycloak");
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl("http://localhost:7080")
                .realm("master")
                .clientId("admin-cli")
                .username("admin")
                .password("admin")
                .build();

        if (keycloak.realms().findAll().stream().anyMatch(realm -> realm.getDisplayName().equals("books"))) {
            return;
        }

        RealmRepresentation realm = new RealmRepresentation();
        realm.setId("books");
        realm.setRealm("books");
        realm.setEnabled(true);

        keycloak.realms().create(realm);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue("1234");

        UserRepresentation user = new UserRepresentation();
        user.setUsername("demo1");
        user.setFirstName("demo1");
        user.setLastName("demo1");
        user.setEmail("demo1@gmail.com");
        user.setCredentials(List.of(credential));
        user.setEnabled(true);
        user.setRealmRoles(List.of("user"));

        keycloak.realm("books").users().create(user);

        UserRepresentation user2 = new UserRepresentation();
        user2.setUsername("demo2");
        user2.setFirstName("demo2");
        user2.setLastName("demo2");
        user2.setEmail("demo2@gmail.com");
        user2.setCredentials(List.of(credential));
        user2.setEnabled(true);
        user2.setRealmRoles(List.of("user"));

        keycloak.realm("books").users().create(user2);

        ClientRepresentation client = new ClientRepresentation();
        client.setClientId("books-app");
        client.setEnabled(true);
        client.setDirectAccessGrantsEnabled(true);
        client.setPublicClient(true);
        client.setRedirectUris(List.of("http://localhost:8080/*"));
        client.setWebOrigins(List.of("*"));
        client.setProtocol("openid-connect");
        keycloak.realm("books").clients().create(client);
    }
}
