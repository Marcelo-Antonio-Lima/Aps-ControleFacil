// src/main.ts

import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
import { AppComponent } from './app/app.component';

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(), // <-- Adicione o provedor aqui
    // ... outros provedores, como o de roteamento, se houver
  ]
}).catch(err => console.error(err));

