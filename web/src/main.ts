import { enableProdMode, importProvidersFrom } from '@angular/core';
import { bootstrapApplication } from '@angular/platform-browser';
import {
  provideHttpClient,
  withInterceptorsFromDi,
} from '@angular/common/http';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideRouter } from '@angular/router';

import { AppComponent } from './app/app.component';
import { routes } from './app/app.routes'; // o './app.routes' según donde lo pongas

import { MessageService, ConfirmationService } from 'primeng/api';
import { DialogService } from 'primeng/dynamicdialog';
import { providePrimeNG } from 'primeng/config';
import Aura from '@primeuix/themes/aura';

import { environment } from './environments/environment';

if (environment.production) {
  enableProdMode();
}

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),
    provideAnimations(),
    provideHttpClient(withInterceptorsFromDi()),
    importProvidersFrom(),
    MessageService,
    ConfirmationService,
    DialogService,
    providePrimeNG({
      theme: {
        preset: Aura,
      },
    }),
  ],
}).catch((err) => console.error(err));
