package view;

import java.util.concurrent.Semaphore;
import controller.ThreadCarro;

public class MainExec02 {
    public static void main(String[] args) {
        Semaphore semaforo = new Semaphore(1);
        for (int i = 0; i < 4; i++) {
            Thread carro = new ThreadCarro(i, semaforo);
            carro.start();
        }
    }
}