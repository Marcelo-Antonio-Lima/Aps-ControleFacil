import { Component, OnInit, NgZone  } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { GoogleAuthService } from './google-auth.service';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

interface UserData {
  name: string;
  wantsBalance: boolean;
  valorMensal: number | null;
  valorAtual: number | null;
}

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [FormsModule, CommonModule], // Adicionar CommonModule para usar *ngIf
  providers: [GoogleAuthService],
  templateUrl: './app.component.html', // Usaremos um arquivo de template
})
export class AppComponent implements OnInit {
  title = 'Controle Fácil';

  // Estado de autenticação
  isAuthenticated: boolean = false;

  // Dados do formulário de cadastro/saldo
  userData: UserData = {
    name: '',
    wantsBalance: false,
    valorMensal: null,
    valorAtual: null,
  };

  // 1. Inicializa o estado de autenticação
  constructor(private googleAuth: GoogleAuthService, private http: HttpClient, private zone: NgZone) {}

  ngOnInit(): void {
    // Apenas verifica se há um token, o estado completo é definido após o login
    this.isAuthenticated = this.googleAuth.isSignedIn();
  }

  // 2. Método de Login com Google
  async signIn() {
    try {
      await this.googleAuth.signIn();


      this.isAuthenticated = true;

      // Aqui, você deve obter as informações do usuário logado (nome, email, id) do Google.
      // A biblioteca gapi armazena estas informações após o signIn.
      const googleUser = gapi.auth2.getAuthInstance().currentUser.get();
      const profile = googleUser.getBasicProfile();

      this.userData.name = profile.getName() || ''; // Preenche o nome padrão

      console.log('Usuário autenticado. Exibindo tela de cadastro.');

      // *** Nível de integração com o Backend (Prisma) ***
      // Após o login, você deve fazer uma requisição ao seu Backend
      // para verificar se o ID do Google (profile.getId()) já existe no seu banco de dados.
      // Se existir: redirecionar para a tela principal (Dashboard)
      // Se NÃO existir: exibir o formulário de Cadastro/Complemento de Dados.

    } catch (error) {
      console.error('Erro durante o login do Google:', error);
      this.isAuthenticated = false;
    }
  }

  // 3. Método de Submissão do Formulário
  async registerUser() {
    if (!this.isAuthenticated) {
      alert('Você precisa estar logado com o Google para se cadastrar.');
      return;
    }

    if (!this.userData.name) {
      alert('O nome é obrigatório.');
      return;
    }

    // ----------------------------------------------------
    // Lógica para enviar dados ao seu Backend (Node/Prisma)
    // ----------------------------------------------------

    const googleUser = gapi.auth2.getAuthInstance().currentUser.get();
    const profile = googleUser.getBasicProfile();

    const userId = profile.getId(); // Chave primária (ID do Google)
    const email = profile.getEmail();

    const userPayload = {
      id: profile.getId(),
      email: profile.getEmail(),
      name: this.userData.name,
      wantsBalance: this.userData.wantsBalance,
      valorMensal: this.userData.wantsBalance ? this.userData.valorMensal : undefined,
      valorAtual: this.userData.wantsBalance ? this.userData.valorAtual : undefined,
    };

    try {
      const response = await this.http.post('http://localhost:3000/api/user/upsert', userPayload).toPromise();

      console.log('Resposta do Backend:', response);
      alert('Operação realizada com sucesso!');
      // Aqui você navegaria para o dashboard após o sucesso:
      // this.router.navigate(['/dashboard']);

    } catch (error) {
      console.error('Erro ao salvar no backend:', error);
      alert('Falha no cadastro/login. Verifique o console do backend.');
    }
  }

  // 4. Logout
  signOut() {
    this.googleAuth.signOut();
    this.isAuthenticated = false;
    // Resetar dados do formulário
    this.userData = { name: '', wantsBalance: false, valorMensal: null, valorAtual: null };
  }
}
