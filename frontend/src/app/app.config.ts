import { ApplicationConfig, provideZoneChangeDetection, APP_INITIALIZER } from '@angular/core';
import { provideRouter } from '@angular/router';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { initializeKeycloak } from './keycloak-init';
import { routes } from './app.routes';
import { provideHttpClient, withInterceptorsFromDi, HTTP_INTERCEPTORS } from '@angular/common/http'
import { AuthInterceptor } from './auth.interceptor';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideToastr } from 'ngx-toastr';

export const appConfig: ApplicationConfig = {
  providers: [provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
        {
          provide: APP_INITIALIZER,
          useFactory: initializeKeycloak,
          multi: true,
          deps: [KeycloakService]
        }, KeycloakService, provideHttpClient(withInterceptorsFromDi()),
          {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true,
          }, provideAnimations(),
                provideToastr(),]
};
