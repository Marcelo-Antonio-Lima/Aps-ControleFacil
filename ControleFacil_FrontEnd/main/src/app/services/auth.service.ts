import { Injectable, computed, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environment';

export interface LoginResponse {
  token: string;
  tokenType: string;
  expiresIn: number;
  userId: number;
  username: string;
  email: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly sessionKey = 'cf_jwt';
  private currentToken = signal<string | null>(this.getStoredToken());
  readonly isAuthenticated = computed(() => !!this.currentToken());

  constructor(private http: HttpClient) {}

  private getStoredToken(): string | null {
    try {
      return localStorage.getItem(this.sessionKey);
    } catch {
      return null;
    }
  }

  private saveToken(token: string | null): void {
    if (token) localStorage.setItem(this.sessionKey, token);
    else localStorage.removeItem(this.sessionKey);
    this.currentToken.set(token);
  }

  register(name: string, email: string, password: string) {
    const body = { username: name, email, senha: password };
    return this.http.post(`${environment.apiBase}/usuarios`, body);
  }

  login(usernameOrEmail: string, password: string) {
    const body = { usernameOrEmail, senha: password };
    return this.http.post<LoginResponse>(`${environment.apiBase}/auth/login`, body);
  }

  setSessionFromLogin(resp: LoginResponse) {
    this.saveToken(resp.token);
  }

  logout(): void {
    this.saveToken(null);
  }

  getToken(): string | null {
    return this.currentToken();
  }
}
