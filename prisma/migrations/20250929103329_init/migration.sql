-- DropForeignKey
ALTER TABLE "public"."User" DROP CONSTRAINT "User_saldoId_fkey";

-- AlterTable
ALTER TABLE "public"."User" ALTER COLUMN "saldoId" DROP NOT NULL;

-- AddForeignKey
ALTER TABLE "public"."User" ADD CONSTRAINT "User_saldoId_fkey" FOREIGN KEY ("saldoId") REFERENCES "public"."Saldo"("id") ON DELETE SET NULL ON UPDATE CASCADE;
