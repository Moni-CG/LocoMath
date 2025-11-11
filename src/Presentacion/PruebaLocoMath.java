package Presentacion;

import Entidad.*;

/**
 *
 * @author MONIC
 */
public class PruebaLocoMath {

    public static void main(String[] args) {

        // Crear jugadores
        Jugador j1 = new Jugador(1, "Fiorella");
        Jugador j2 = new Jugador(2, "Carlos");

        Jugador[] jugadores = {j1, j2};

        // Crear una ronda de 10 segundos
        Ronda ronda = new Ronda(1, jugadores, 10);

        // Iniciar la ronda
        ronda.iniciarRonda();

        // Simular respuestas
        try {
            Thread.sleep(3000); // Esperar 3 segundos
        } catch (InterruptedException e) {
        }

        ronda.responder(j1, new Resultado(j1.getIdJugador(),4)); // correcta
        ronda.responder(j2, new Resultado(j2.getIdJugador(), 5)); // incorrecta

        // Esperar a que termine el temporizador
        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
        }

        System.out.println("\n--- Resultados finales ---");
        System.out.println(j1.getUsuario() + " → Puntaje: " + j1.getPuntaje());
        System.out.println(j2.getUsuario() + " → Puntaje: " + j2.getPuntaje());
    }

}
