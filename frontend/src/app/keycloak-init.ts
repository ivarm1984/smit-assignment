import Keycloak from 'keycloak-js';
import { KeycloakService } from 'keycloak-angular';

export function initializeKeycloak(keycloak: KeycloakService): () => Promise<boolean> {
  return (): Promise<boolean> =>
    keycloak.init({
      config: {
        url: 'http://localhost:7080',
        realm: 'books',
        clientId: 'books-app',
      },
      initOptions: {
        onLoad: 'login-required',
      },
    });
}
