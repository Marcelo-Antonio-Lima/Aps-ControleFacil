import express, { Request, Response } from 'express';
import cors from 'cors';
import { prisma } from './prisma'; // Importa a instância do Prisma

const app = express();
const PORT = 3000;

app.use(cors()); // Permite requisições do seu Angular (Frontend)
app.use(express.json()); // Habilita o parsing de JSON no corpo da requisição

// Tipo de dados esperado do Frontend
interface CadastroBody {
  id: string; // ID do Google (chave primária)
  email: string;
  name: string;
  wantsBalance: boolean;
  valorMensal?: number;
  valorAtual?: number;
}

// ----------------------------------------------------
// ROTA PRINCIPAL DE CADASTRO/LOGIN
// ----------------------------------------------------

app.post('/api/user/upsert', async (req: Request<{}, {}, CadastroBody>, res: Response) => {
  const { id, email, name, wantsBalance, valorMensal, valorAtual } = req.body;

  try {
    // 1. VERIFICAÇÃO INICIAL: Tenta encontrar o usuário pelo ID do Google
    const existingUser = await prisma.user.findUnique({
      where: { id: id },
      include: { saldo: true }
    });

    if (existingUser) {
      // Se o usuário existe, retorna o usuário (comportamento de LOGIN)
      return res.status(200).json({ // <-- RETURN aqui para sair da função
        message: 'Usuário logado com sucesso.',
        user: existingUser
      });
    }

    // 2. CADASTRO DE NOVO USUÁRIO
    console.log(`Cadastrando novo usuário: ${name}`);

    if (wantsBalance && valorMensal !== undefined && valorMensal !== null) {

      // Cria Saldo e User em duas etapas (mais simples fora de transação complexa)
      const novoSaldo = await prisma.saldo.create({
        data: {
          valorMensal: valorMensal,
          valorAtual: valorAtual || 0,
        }
      });

      const newUserWithSaldo = await prisma.user.create({
        data: {
          id: id,
          email: email,
          name: name,
          saldoId: novoSaldo.id, // Conecta ao ID do Saldo
        },
        include: { saldo: true }
      });

      return res.status(201).json({ // <-- RETURN aqui para sair da função
        message: 'Usuário e Saldo criados com sucesso.',
        user: newUserWithSaldo
      });

    } else {
      // Opção B: Criar apenas o User (sem Saldo)
      const newUser = await prisma.user.create({
        data: {
          id: id,
          email: email,
          name: name,
          // saldoId será nulo
        }
      });
      return res.status(201).json({ // <-- RETURN aqui para sair da função
        message: 'Usuário criado com sucesso (sem saldo inicial).',
        user: newUser
      });
    }

  } catch (error) {
    console.error('Erro no processamento do cadastro/login:', error);

    // Erro de duplicação (se o email já estiver cadastrado)
    if ((error as any).code === 'P2002') {
      return res.status(409).json({ message: 'Erro: Email já cadastrado.' }); // <-- RETURN aqui
    }

    // Erro genérico
    return res.status(500).json({ message: 'Erro interno do servidor.' }); // <-- RETURN aqui
  }

  // NOTA: Se o seu TypeScript ainda reclamar, adicione um 'return'
  // simples aqui, embora o código acima já garanta todos os retornos.
  // return;
  });


app.listen(PORT, () => {
  console.log(`Servidor rodando na porta ${PORT}`);
});
