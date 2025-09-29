import { Client } from 'pg';
import * as dotenv from 'dotenv';

// Carrega as variáveis de ambiente do seu arquivo .env
dotenv.config();

const DB_NAME = "Controle_Facil_DB";

/**
 * Conecta-se ao servidor PostgreSQL (usando o banco padrão 'postgres')
 * e cria o banco de dados Controle_Facil_DB se ele ainda não existir.
 */
export async function createDatabaseIfNotExist() {
  // 1. Obtém a URL de conexão do .env
  const dbUrl = process.env['DATABASE_URL'];

  if (!dbUrl) {
    throw new Error("DATABASE_URL não está definida no arquivo .env");
  }

  // 2. Cria uma URL de conexão "administrativa"
  // Remove o nome específico do banco de dados (Controle_Facil_DB) da URL
  // e conecta-se ao banco de dados padrão 'postgres'
  // Ex: "postgresql://user:pass@host:port/postgres"
  const adminUrl = dbUrl.substring(0, dbUrl.lastIndexOf('/')) + '/postgres';

  const client = new Client({
    connectionString: adminUrl,
  });

  try {
    await client.connect();

    // Comando SQL para criar o banco de dados.
    // Note o uso da aspas duplas (") para permitir letras maiúsculas/minúsculas no nome.
    // O comando 'CREATE DATABASE' não pode ser executado dentro de uma transação.
    const createDbQuery = `CREATE DATABASE "${DB_NAME}"`;

    console.log(`Tentando criar o banco de dados: ${DB_NAME}`);

    await client.query(createDbQuery);
    console.log(`✅ Banco de dados "${DB_NAME}" criado com sucesso.`);

  } catch (error: any) {
    // O erro mais comum é 'duplicate_database', que significa que o banco já existe.
    if (error.code === '42P04') {
      console.log(`⚠️ Banco de dados "${DB_NAME}" já existe. Continuar.`);
    } else {
      console.error('❌ Erro ao tentar criar o banco de dados:', error.message);
      // É importante re-lançar outros erros para evitar problemas inesperados
      throw error;
    }
  } finally {
    await client.end();
  }
}
