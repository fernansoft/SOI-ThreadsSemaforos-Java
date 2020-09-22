package view;

/*Programador: Fernando Oliveira da Costa       data: 22/09/2020*/

import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;
import controller.ThreadCarro;
/*Objetivo:
Você foi contratado para automatizar um treino de Fórmula 1.
As regras estabelecidas pela direção da provas são simples:
“No máximo 5 carros das 7 escuderias (14 carros no total) presentes podem entrar na pista simultaneamente, mas apenas um carro de cada equipe.
O segundo carro deve ficar à espera, caso um companheiro de equipe já esteja na pista.
Cada piloto deve dar 3 voltas na pista.
O tempo de cada volta deverá ser exibido e a volta mais rápida de cada piloto deve ser armazenada para, ao final, exibir o grid de largada, ordenado do menor tempo para o maior.
*/

public class MainExec03 {
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null,
                "Sei que o tempo do sleep usa milisegundos, mas para fins de demonstracao, vou apresentar o valor de tempo da volta como se fosse em segundos.");
        Semaphore semaforo = new Semaphore(5);
        for (int i = 0; i < 14; i++) {
            //Cria e inicia as 14 Threads para correrem de acordo com a classe TreadCarro no package controller.
            Thread carro = new ThreadCarro(i, semaforo);
            carro.start();
        }
    }
}
