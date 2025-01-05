package ee.ivar.smit.proovitoo.config;

import ee.ivar.smit.proovitoo.book.BookEntity;
import ee.ivar.smit.proovitoo.book.BookRepository;
import ee.ivar.smit.proovitoo.user.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("dev-data")
@Component
@Log4j2
@RequiredArgsConstructor
public class DevData {

    private final BookRepository bookRepository;
    private final UserService userService;

    @PostConstruct
    public void postConstruct() {
        log.info("Setting up keycloak");
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl("http://localhost:7080")
                .realm("master")
                .clientId("admin-cli")
                .username("admin")
                .password("admin")
                .build();

        if (booksRealmExists(keycloak)) {
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

        Response response = keycloak.realm("books").users().create(user);
        String user1Sub = String.valueOf(response.getMetadata().get("Location")).split("users/")[1].split("]")[0];

        UserRepresentation user2 = new UserRepresentation();
        user2.setUsername("demo2");
        user2.setFirstName("demo2");
        user2.setLastName("demo2");
        user2.setEmail("demo2@gmail.com");
        user2.setCredentials(List.of(credential));
        user2.setEnabled(true);
        user2.setRealmRoles(List.of("user"));

        Response response2 = keycloak.realm("books").users().create(user2);
        String user2Sub = String.valueOf(response2.getMetadata().get("Location")).split("users/")[1].split("]")[0];

        ClientRepresentation client = new ClientRepresentation();
        client.setClientId("books-app");
        client.setEnabled(true);
        client.setDirectAccessGrantsEnabled(true);
        client.setPublicClient(true);
        client.setRedirectUris(List.of("http://localhost:8080/*", "http://localhost:4200/*"));
        client.setWebOrigins(List.of("*"));
        client.setProtocol("openid-connect");
        keycloak.realm("books").clients().create(client);

        if (bookRepository.findAll().isEmpty()) {
            BookEntity book1 = new BookEntity();
            book1.setAuthor("author1");
            book1.setTitle("title");
            book1.setUser(userService.getUserBySub(user1Sub));
            bookRepository.save(book1);

            BookEntity book2 = new BookEntity();
            book2.setAuthor("author2");
            book2.setTitle("title asdfadsf");
            book2.setUser(userService.getUserBySub(user2Sub));
            bookRepository.save(book2);
        }
    }

    private static boolean booksRealmExists(Keycloak keycloak) {
        if (keycloak.realms().findAll() == null) {
            return false;
        }
        return keycloak.realms().findAll().stream().anyMatch(realm -> "books".equals(realm.getRealm()));
    }
}
