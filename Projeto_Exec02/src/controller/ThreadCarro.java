package controller;

import java.util.concurrent.Semaphore;

/* Objetivo:
    Fazer uma aplicação que gerencie 4 carros em direcoes diferentes passando um cruzamento.
    Para tal, usar uma variável sentido, que será alterado pela Thread que acontrola cada carro com a movimentação do carro.
    Quando a Thread tiver a possibilidade de ser executada, ela deve imprimir em console o sentido que o carro está passando.
    Só pode passar um carro por vez no cruzamento.
*/

public class ThreadCarro extends Thread {
	private static int sentido;
	private int idCarro;
	private Semaphore semaforo;

	public ThreadCarro(int idCarro, Semaphore semaforo) {
		this.idCarro = idCarro;
		this.semaforo = semaforo;
	}

	public void run() {
		System.out.println("Carro #" + idCarro + " chegou no cruzamento!");
		// --------inicio seção crítica--------
		try {
			semaforo.acquire();
			passarCruzamento();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			semaforo.release();
		}
		// --------fim seção crítica--------
	}

	private void passarCruzamento() {
		System.out.println("Carro #"+ idCarro+ " chegou no cruzamento!");
		int tempoPassando = (int) ((Math.random() * 1001) + 1000);
		// estimando que o carro demora de 1 a 2 seg para passar no cruzamento.
		if (sentido == 0) {
			System.out.println("Carro #" + idCarro + " está passando o cruzamento no sentido Oeste para Leste");
			sentido++;
		} else if (sentido == 1) {
			System.out.println("Carro #" + idCarro + " está passando o cruzamento no sentido Norte para Sul");
			sentido++;
		} else if (sentido == 2) {
			System.out.println("Carro #" + idCarro + " está passando o cruzamento no sentido Leste para Oeste");
			sentido++;
		} else if (sentido == 3) {
			System.out.println("Carro #" + idCarro + " está passando o cruzamento no sentido Sul para Norte");
			sentido++;
		}
		try {
			sleep(tempoPassando);
		} catch (Exception e) {
			System.out.println("Exception: Thread parou!\n" + e.getMessage());
		}
		System.out.println("Carro #" + idCarro + " terminou de passar o cruzamento");
	}
}