import { Component, signal, computed, inject } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { NgIf } from '@angular/common';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, NgIf],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('main');
  private readonly auth = inject(AuthService);
  private readonly router = inject(Router);
  protected readonly loggedIn = computed(() => this.auth.isAuthenticated());

  protected logout(): void {
    this.auth.logout();
    this.router.navigateByUrl('/login');
  }
}
