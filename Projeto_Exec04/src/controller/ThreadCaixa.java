package controller;

import java.util.concurrent.Semaphore;

public class ThreadCaixa extends Thread {
    private int idOperacao;
    private int codConta;
    private int saldoConta;
    private int valorTransacao;
    private Semaphore semaforoSaque;
    private Semaphore semaforoDeposito;

    public ThreadCaixa(int idOperacao, Semaphore semaforoSaque, Semaphore semaforoDeposito) {
        this.idOperacao = idOperacao;
        this.semaforoSaque = semaforoSaque;
        this.semaforoDeposito = semaforoDeposito;
    }

    public void run() {
        boolean op = geraOperacao();
        if (op == true) {
            try {
                semaforoSaque.acquire();
                saque(codConta, saldoConta, valorTransacao);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaforoSaque.release();
            }
        } else if (op == false) {
            try {
                semaforoDeposito.acquire();
                deposito(codConta, saldoConta, valorTransacao);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaforoDeposito.release();
            }
        }
    }

    private boolean geraOperacao() {
        boolean operacao = true;
        valorTransacao = (int) (Math.random() * 2001) - 1000; // Valor aleatorio de -1000 a 1000.
        if (valorTransacao < 0) {
            operacao = true;
        }
        if (valorTransacao >= 0) {
            operacao = false;
        }
        return operacao;
    }

    private void deposito(int codConta2, int saldoConta2, int valorTransacao2) {
        System.out.println("\nOperacao #" + idOperacao + "=> deposito iniciado.");
        codConta2 = (int) (Math.random() * 1001) + 1000; // Valor aleatorio de 1000 a 2000.
        saldoConta2 = (int) (Math.random() * 10001) + 1000; // Valor aleatorio de 1000 a 11000.
        System.out.println("\nOperacao #" + idOperacao + "=> Tipo: Deposito.\nConta: " + codConta2 + "; Saldo: "
                + saldoConta2 + "; Valor da transacao: " + valorTransacao2);
        System.out.println("\nOperacao #" + idOperacao + "=> deposito finalizado.");
    }

    private void saque(int codConta2, int saldoConta2, int valorTransacao2) {
        System.out.println("\nOperacao #" + idOperacao + "=> saque iniciado.");
        codConta2 = (int) (Math.random() * 1001) + 1000; // Valor aleatorio de 1000 a 2000.
        saldoConta2 = (int) (Math.random() * 10001) + 1000; // Valor aleatorio de 1000 a 11000.
        System.out.println("\nOperacao #" + idOperacao + "=> Tipo: Saque.\nConta: " + codConta2 + "; Saldo: "
                + saldoConta2 + "; Valor da transacao: " + valorTransacao2);
        System.out.println("\nOperacao #" + idOperacao + "=> saque finalizado.");
    }
}