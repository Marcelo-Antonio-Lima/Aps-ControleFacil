-- CreateTable
CREATE TABLE "public"."User" (
    "id" TEXT NOT NULL,
    "email" TEXT NOT NULL,
    "name" TEXT,
    "saldoId" INTEGER NOT NULL,

    CONSTRAINT "User_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "public"."Saldo" (
    "id" SERIAL NOT NULL,
    "valorMensal" DECIMAL(65,30) NOT NULL,
    "valorAtual" DECIMAL(65,30),

    CONSTRAINT "Saldo_pkey" PRIMARY KEY ("id")
);

-- CreateIndex
CREATE UNIQUE INDEX "User_email_key" ON "public"."User"("email");

-- CreateIndex
CREATE UNIQUE INDEX "User_saldoId_key" ON "public"."User"("saldoId");

-- AddForeignKey
ALTER TABLE "public"."User" ADD CONSTRAINT "User_saldoId_fkey" FOREIGN KEY ("saldoId") REFERENCES "public"."Saldo"("id") ON DELETE RESTRICT ON UPDATE CASCADE;
