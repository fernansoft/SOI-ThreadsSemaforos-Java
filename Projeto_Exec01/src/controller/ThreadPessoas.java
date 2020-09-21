package controller;

import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;

public class ThreadPessoas extends Thread {
    private int tamanhoCorredor = 200;
    private int id_Pessoa;
    private Semaphore semaforo;

	public ThreadPessoas(int id_Pessoa, Semaphore semaforo) {
        this.id_Pessoa = id_Pessoa;
        this.semaforo = semaforo;
    }

    public void run() {
        corre();
        // ---------inicio seção crítica---------
        try {
            semaforo.acquire();
            cruzaPorta();
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, "Exception: Thread" + id_Pessoa + "parou!\n" + e.getMessage());
        } finally {
            semaforo.release();
        }
        // ---------fim seção crítica---------
    }

    private void corre() {
        int metrosJaCorridos = 0;
        while (metrosJaCorridos < tamanhoCorredor) {
            metrosJaCorridos += (int) ((Math.random() * 3) + 4);
            System.out.println("Pessoa #" + id_Pessoa + " correu " + metrosJaCorridos + "m.");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null,
                        "Exception: Thread" + id_Pessoa + "parou no método 'corre'!\n" + e.getMessage());
            }
        }
        System.out.println("Pessoa #" + id_Pessoa + " chegou no fim do corredor!");
    }

    private void cruzaPorta() {
        int tempo = (int) ((Math.random() * 1001) + 1000);
        System.out.println("Pessoa #" + id_Pessoa + " está cruzando a porta!");
        try {
            sleep(tempo);
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null,
                    "Exception: Thread" + id_Pessoa + "parou no método 'cruzaPorta'!\n" + e.getMessage());
        }
        System.out.println("Pessoa #" + id_Pessoa + " cruzou a porta!");
    }
}