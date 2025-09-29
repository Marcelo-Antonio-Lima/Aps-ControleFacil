/// <reference types="gapi" />
/// <reference types="gapi.auth2" />
/// <reference types="gapi.client" />
import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';

declare const google: any;
declare const gapi: any;

@Injectable({ providedIn: 'root' })
export class GoogleAuthService {
  private accessToken: string | null = null;
  private tokenClient: any;
  // Promise que resolve quando gapi.client estiver pronto
  private gapiClientPromise!: Promise<void>;

  private CLIENT_ID = environment.CLIENT_ID;
  private API_KEY = environment.API_KEY;

  constructor() {
    if (!this.CLIENT_ID || !this.API_KEY) {
      console.error('CLIENT_ID ou API_KEY não definidos no ambiente do Angular.');
      return;
    }

    this.gapiClientPromise = new Promise((resolve) => {

      // Espera pelo evento 'gapi:loaded' disparado pelo index.html
      window.addEventListener('gapi:loaded', () => {
        this.initializeGapiClient(resolve);
      });
    this.initializeTokenClient();
  })};

  private initializeGapiClient(resolve: () => void): void {
      // gapi.load é seguro para ser chamado, mas o client.init deve estar dentro
      gapi.load('client', async () => {
        await gapi.client.init({
          apiKey: this.API_KEY,
          clientId: this.CLIENT_ID, // Use clientId, não clientID
        });
        console.log('gapi.client inicializado.');
        resolve(); // Resolve a promise APÓS A INICIALIZAÇÃO
      });
    }

  private initializeTokenClient(): void {
    if (typeof google === 'undefined') {
      console.error('Google Identity Services não carregou ainda.');
      return;
    }
    this.tokenClient = google.accounts.oauth2.initTokenClient({
      client_id: this.CLIENT_ID,
      scope: 'https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email',
      callback: (tokenResponse: any) => {
        this.accessToken = tokenResponse.access_token;
        gapi.client.setToken({ access_token: this.accessToken });
      },
    });
  }
  async signIn(): Promise<void> {
    if (!this.tokenClient) {
      console.error('Token client not initialized.');
      return;
    }

    return new Promise((resolve) => {
      this.tokenClient.callback = (tokenResponse: any) => {
        if (tokenResponse && tokenResponse.access_token) {
          this.accessToken = tokenResponse.access_token;
          gapi.client.setToken({ access_token: this.accessToken });
          resolve();
        } else {
          console.error('Failed to obtain access token.');
        }
      };

      this.tokenClient.requestAccessToken();
    });
  }

  signOut(): void {
    this.accessToken = null;
    gapi.client.setToken(null);
    console.log('User signed out.');
  }

  isSignedIn(): boolean {
    return this.accessToken !== null;
  }

}
