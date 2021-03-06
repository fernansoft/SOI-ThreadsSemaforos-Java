package controller;

import java.util.concurrent.Semaphore;

public class ThreadCarro extends Thread {
    private static int vet14MelhoresVoltas[] = new int[14];
    private static int vet14idCarros[] = new int[14];
    private static int vet7Equipes[] = new int[7];
    private static int ultPosicVetVoltas = 0;
    private static int qtdeCarrosFinalizaram = 0;
    private int IdCarro;
    private Semaphore semaforo;

    public ThreadCarro(int IdCarro, Semaphore semaforo) {
        this.IdCarro = IdCarro;
        this.semaforo = semaforo;
        System.out.println("Carro #" + IdCarro + " Equipe #" + (int) IdCarro / 2 + " criado!");
        // Determinei que a equipe de cada carro sera definida pelo seu id dividido por
        // 2, retornando apenas a parte inteira. Assim tendo 2 equipes para cada carro.
    }

    public void run() {
        try {
            int deuVolta = 0;
            while (deuVolta == 0) {
                // Esse while serve para nao correr o risco de uma thread iniciar e terminar sem
                // que tenha corrido.
                semaforo.acquire();
                if (verificarEquipe() == true) {
                    vet7Equipes[(int) IdCarro / 2] = 1;
                    // Atribuição de 1 na posição da equipe no vetor para bloquear a liberação de
                    // outro carro da mesma equipe.
                    System.out.println("Carro #" + IdCarro + " Equipe #" + (int) IdCarro / 2 + " entrou na pista!");
                    darVoltas();
                    deuVolta = 1;
                } else {
                    semaforo.release();
                    // Caso a equipe nao esteja disponível, o semáforo sera liberado para outra
                    // Thread correr. Assim não cria gargalos de espera.
                }
            }
            if (qtdeCarrosFinalizaram == 14) {
                // Verifica se todos os 14 carros ja correram, para assim poder organizar e
                // mostrar o vetor com as melhores voltas.
                System.out.println("\nGrid de melhores tempos:\n");
                organizarVetorParaCrescente(vet14MelhoresVoltas, vet14idCarros);
                for (int i = 0; i < 14; i++) {
                    System.out.println("Posicao numero " + (i+1) + "=> Carro #" + vet14idCarros[i] + " Menor volta: "
                            + vet14MelhoresVoltas[i] + "seg.");
                }
                System.out.println("\n");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaforo.release();
        }
    }

    private boolean verificarEquipe() {
        // função que verifica se a equipe esta disponível para que um carro possa
        // correr sem que outro da mesma equipe corra junto.
        boolean test = false;
        if (vet7Equipes[(int) IdCarro / 2] == 0) {
            test = true;
        } else if (vet7Equipes[(int) IdCarro / 2] == 1) {
            test = false;
        }
        return test;
    }

    private void darVoltas() {
        // Metodo que faz a operacao da Thread dar as 3 voltas na pista.
        int tempoDaVolta;
        int menorVolta = 2001;
        for (int i = 0; i < 3; i++) {
            tempoDaVolta = (int) (Math.random() * 1001) + 1000;
            // Gera aleatorio de 1000 a 2000.
            try {
                Thread.sleep(tempoDaVolta);
                // Para sumular o tempo da volta (em escala de milisegundos, mas considerei os
                // valores gerados como segundos para fins de demonstração)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (tempoDaVolta < menorVolta) {
                // If para descobrir qual foi a menor volta da Thread
                menorVolta = tempoDaVolta;
            }
            System.out.println("Carro #" + IdCarro + " Equipe #" + (int) IdCarro / 2 + " deu a volta numero " + (i+1)+ " em " + tempoDaVolta + "seg.");
        }
        System.out.println("Carro #" + IdCarro + " Equipe #" + (int) IdCarro / 2 + "=> Volta mais rapida feita em "
                + menorVolta + "seg.");
        vet7Equipes[(int) IdCarro / 2] = 0;// Atribuicao de valor 0 para liberar o outro carro da mesma equipe.
        vet14MelhoresVoltas[ultPosicVetVoltas] = menorVolta; // Vetor recebe a melhor volta da Thread e seu id.
        vet14idCarros[ultPosicVetVoltas] = IdCarro; // Vetor recebe o id da Thread na mesma posicao do vetor volta.
        ultPosicVetVoltas++;
        qtdeCarrosFinalizaram++;
    }

    private void organizarVetorParaCrescente(int[] vetor1, int[] vetor2) {
        // funcao que organiza o vetor do parametro em ordem crescente e retorna ele.
        int varAuxiliarvet;
        for (int i = 0; i < vetor1.length - 1; i++) {
            for (int j = i + 1; j < vetor1.length; j++) {
                if (vetor1[i] > vetor1[j]) {
                    varAuxiliarvet = vetor1[i];
                    vetor1[i] = vetor1[j];
                    vetor1[j] = varAuxiliarvet;

                    varAuxiliarvet = vetor2[i];
                    vetor2[i] = vetor2[j];
                    vetor2[j] = varAuxiliarvet;
                }
            }
        }
    }
}