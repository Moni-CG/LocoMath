package Logica;

import Entidad.*;
import java.util.ArrayList;

/**
 *
 * @author MONIC
 */
public class PruebaLocoMath {

    public static void main(String[] args) {

        // Crear jugadores
        Jugador j1 = new Jugador(1, "Fiorella");
        Jugador j2 = new Jugador(2, "Monica");

        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(j1);
        jugadores.add(j2);

        // Crear temporizador de 5 segundos por pregunta
        Temporizador temporizador = new Temporizador(5);

        // Crear ronda de prueba
        RondaPrueba ronda = new RondaPrueba(1, jugadores, temporizador);
        ronda.generarPreguntas(1);

        // Iniciar la primera pregunta
        System.out.println("Iniciando Ronda 1...");
        temporizador.iniciar();

        // Simular respuestas y tiempo
        try {
            Thread.sleep(2000); // Esperar 2 segundos
            ronda.asignarPuntos(j1); // Fiorella responde primero
            Thread.sleep(4000); // Esperar otros 4 segundos (tiempo se agota)
            ronda.verificarTiempoPregunta(); // Verifica si debe pasar a la siguiente
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Mostrar resultados
        System.out.println("🏁 Puntos finales:");
        System.out.println(j1.getUsuario()+ ": " + j1.getPuntaje());
        System.out.println(j2.getUsuario() + ": " + j2.getPuntaje());
    }

}
