import { PrismaClient } from '@prisma/client';

// Cria uma única instância do Prisma Client
const prisma = new PrismaClient();

// Exporta a instância para ser usada em qualquer lugar
export { prisma };
