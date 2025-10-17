import { Injectable, computed, signal } from '@angular/core';

export interface UserAccount {
  id: string;
  name: string;
  email: string;
  passwordHash: string;
  createdAt: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly usersKey = 'cf_users';
  private readonly sessionKey = 'cf_session';

  private currentEmail = signal<string | null>(this.getSessionEmail());
  readonly isAuthenticated = computed(() => !!this.currentEmail());

  private getSessionEmail(): string | null {
    try {
      const raw = localStorage.getItem(this.sessionKey);
      return raw ? JSON.parse(raw)?.email ?? null : null;
    } catch {
      return null;
    }
  }

  private saveSession(email: string | null): void {
    if (email) localStorage.setItem(this.sessionKey, JSON.stringify({ email }));
    else localStorage.removeItem(this.sessionKey);
    this.currentEmail.set(email);
  }

  private loadUsers(): UserAccount[] {
    try {
      const raw = localStorage.getItem(this.usersKey);
      return raw ? (JSON.parse(raw) as UserAccount[]) : [];
    } catch {
      return [];
    }
  }

  private saveUsers(users: UserAccount[]): void {
    localStorage.setItem(this.usersKey, JSON.stringify(users));
  }

  private hash(input: string): string {
    let h = 0;
    for (let i = 0; i < input.length; i++) h = (h << 5) - h + input.charCodeAt(i);
    return Math.abs(h).toString(16);
  }

  register(name: string, email: string, password: string): { ok: true } | { ok: false; message: string } {
    const users = this.loadUsers();
    if (users.some(u => u.email.toLowerCase() === email.toLowerCase())) {
      return { ok: false, message: 'E-mail já cadastrado.' };
    }
    const user: UserAccount = {
      id: crypto.randomUUID(),
      name,
      email,
      passwordHash: this.hash(password),
      createdAt: new Date().toISOString()
    };
    users.push(user);
    this.saveUsers(users);
    this.saveSession(email);
    return { ok: true };
  }

  login(email: string, password: string): { ok: true } | { ok: false; message: string } {
    const users = this.loadUsers();
    const user = users.find(u => u.email.toLowerCase() === email.toLowerCase());
    if (!user) return { ok: false, message: 'Usuário não encontrado.' };
    if (user.passwordHash !== this.hash(password)) return { ok: false, message: 'Senha inválida.' };
    this.saveSession(email);
    return { ok: true };
  }

  logout(): void {
    this.saveSession(null);
  }

  getCurrentEmail(): string | null {
    return this.currentEmail();
  }
}
