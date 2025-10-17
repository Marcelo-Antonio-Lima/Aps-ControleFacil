import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink, ActivatedRoute } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  private readonly fb = inject(FormBuilder);
  private readonly auth = inject(AuthService);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);

  message: string | null = null;
  loading = false;

  form = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]]
  });

  private isSafeInternalPath(path: string): boolean {
    if (!path.startsWith('/')) return false;
    if (path.startsWith('//')) return false;
    if (/^https?:/i.test(path)) return false;
    if (path === '/login' || path === '/cadastro') return false;
    return true;
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    const { email, password } = this.form.getRawValue();
    this.loading = true;
    this.auth.login(email, password).subscribe({
      next: (resp) => {
        this.auth.setSessionFromLogin(resp);
        this.message = 'Login realizado com sucesso.';
        const requested = this.route.snapshot.queryParamMap.get('returnUrl') || '/dashboard';
        const target = this.isSafeInternalPath(requested) ? requested : '/dashboard';
        this.router.navigateByUrl(target);
      },
      error: (err) => {
        this.message = typeof err?.error === 'string' ? err.error : 'Falha no login.';
      }
    }).add(() => { this.loading = false; });
  }
}
