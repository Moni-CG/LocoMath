package Logica;

import Entidad.*;
import java.util.ArrayList;

/**
 *
 * @author MONIC
 */
public class PruebaLocoMath {

    public static void main(String[] args) {

        // Crear lista de jugadores
        ArrayList<Jugador> jugadores = new ArrayList<>();

        // Agregar jugadores a la lista
        jugadores.add(new Jugador(1, "Fiorella"));
        jugadores.add(new Jugador(2, "Monica"));

        // Crear una ronda de 10 segundos
        RondaPrueba ronda = new RondaPrueba(1, jugadores, new Temporizador(20));
        ronda.generarPreguntas(6);

    }

}
