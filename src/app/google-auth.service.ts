/// <reference types="gapi" />
/// <reference types="gapi.auth2" />
/// <reference types="gapi.client" />
import { Injectable } from '@angular/core';
import * as dotenv from 'dotenv';
dotenv.config();

declare const google: any;
declare const gapi: any;

@Injectable({ providedIn: 'root' })
export class GoogleAuthService {
  private accessToken: string | null = null;
  private tokenClient: any;

  private CLIENT_ID = process.env["CLIENTID "];
  private API_KEY = process.env["APIKEY "];

  constructor() {
    this.initializeGapiClient();
    this.initializeTokenClient();
  }

  private initializeGapiClient(): void {
    gapi.load('client', async () => {
      await gapi.client.init({
        apiKey: this.API_KEY,
        clientID: this.CLIENT_ID,
      });
    });
  }

  private initializeTokenClient(): void {
    if (typeof google === 'undefined') {
      console.error('Google Identity Services não carregou ainda.');
      return;
    }
    this.tokenClient = google.accounts.oauth2.initTokenClient({
      client_id: this.CLIENT_ID,
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
