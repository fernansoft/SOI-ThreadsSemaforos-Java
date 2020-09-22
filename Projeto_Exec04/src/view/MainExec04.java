package view;

import java.util.concurrent.Semaphore;
import controller.ThreadCaixa;

//Programador: Fernando Oliveira da Costa       data: 22/09/2020

/*Objetivo:
Um banco deve controlar Saques e Depósitos.
O sistema pode permitir um Saque e um Depósito Simultâneos, mas nunca 2 Saques ou 2 Depósitos Simultâneos.
Para calcular a transação (Saque ou Depósito), o método deve receber o código da conta, o saldo da conta e o valor a ser transacionado.
Deve-se montar um sistema que deve considerar que 20 transações simultâneas serão enviadas ao sistema (aleatoriamente essas transações podem ser qualquer uma das opções) e tratar todas as transações, de acordo com as regras acima.
*/

public class MainExec04 {
    public static void main(String[] args) {
        Semaphore semaforoSaque = new Semaphore(1);
        Semaphore semaforoDeposito = new Semaphore(1);
        for (int i = 0; i < 20; i++) {
            Thread operacao = new ThreadCaixa(i, semaforoSaque, semaforoDeposito);
            operacao.start();
        }
    }
}