package Logica;

import Entidad.*;
import java.util.ArrayList;

/**
 *
 * @author MONIC
 */
public class PruebaLocoMath {

    public static void main(String[] args) {
       Temporizador temporizador = new Temporizador(5);
        temporizador.iniciar();

        // Simula que otra clase consulta el tiempo cada segundo
        while (temporizador.isEnCurso()) {
            System.out.println(temporizador.toString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nReiniciando temporizador...\n");
        temporizador.iniciar();

        while (temporizador.isEnCurso()) {
            System.out.println(temporizador.toString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Temporizador finalizado completamente.");
    }

}
