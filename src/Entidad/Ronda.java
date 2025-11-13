package Entidad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Moni
 */
public class Ronda {

    private int idRonda;
    private Pregunta[] preguntas; // Array de 6 preguntas
    private int preguntaActual; // Índice de la pregunta actual (0 a 5)
    private Jugador[] jugadores;
    private boolean finalizada;
    private Temporizador temporizador;
    private boolean[] jugadoresRespondieron; // Se reinicia por cada pregunta

    public Ronda(int idRonda, Jugador[] jugadores, int duracionSegundos) {
        this.idRonda = idRonda;
        this.jugadores = jugadores;
        this.preguntas = new Pregunta[6]; // Crear 6 preguntas
        //for (int i = 0; i < 6; i++) {
          //  this.preguntas[i] = new Pregunta(idRonda * 10 + i, new Operacion()); // Asumiendo un ID único para cada pregunta
        //}
        this.preguntaActual = 0;
        this.temporizador = new Temporizador(duracionSegundos);
        this.finalizada = false;
        this.jugadoresRespondieron = new boolean[jugadores.length];
    }

    public Pregunta getPreguntaActual() { // Devuelve la pregunta actual
        return preguntas[preguntaActual];
    }

    public Temporizador getTemporizador() {
        return temporizador;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void iniciarRonda() {
        System.out.println("===Ronda " + idRonda + " ===");
        temporizador.iniciar(); // Iniciar temporizador para la primera pregunta
    }


    public void responder(Jugador jugador, Resultado respuesta) {
        if (finalizada) {
            System.out.println("Ronda finalizada. No se puede responder.");
            return;
        }

        // Chequear si el tiempo expiró para la pregunta actual
        if (!temporizador.isEnCurso()) {
            if (!todosRespondieron()) {
                pasarASiguientePregunta(); // Pasar automáticamente si el tiempo expiró y no todos respondieron
            }
            System.out.println("Tiempo terminado para esta pregunta. No se puede responder.");
            return;
        }

        int index = obtenerIndiceJugador(jugador);
        if (index == -1) {
            System.out.println("Jugador no pertenece a esta ronda.");
            return;
        }

        if (jugadoresRespondieron[index]) {
            System.out.println(jugador.getUsuario() + " ya respondió esta pregunta.");
            return;
        }

        boolean correcto = preguntas[preguntaActual].verificarRespuesta(respuesta);

        if (correcto) {
            jugador.setAciertos(jugador.getAciertos() + 1);
            jugador.setPuntaje(jugador.getPuntaje() + 10);
            System.out.println(jugador.getUsuario() + " respondió correctamente!");
        } else {
            jugador.setErrores(jugador.getErrores() + 1);
            jugador.setPuntaje(Math.max(0, jugador.getPuntaje() - 1)); // -5 por error
            System.out.println(jugador.getUsuario() + " falló.");
        }

        jugadoresRespondieron[index] = true;

        if (todosRespondieron()) {
            pasarASiguientePregunta();
        }
    }

    private void pasarASiguientePregunta() {
        temporizador.detener(); // Detener el temporizador actual

        if (preguntaActual < 5) {
            preguntaActual++;

            // Reiniciar respuestas
            Arrays.fill(jugadoresRespondieron, false);

            // Crear un nuevo temporizador para la siguiente pregunta
            temporizador = new Temporizador(20); // duración fija o variable
            temporizador.iniciar();
        } else {
            finalizarRonda();
        }
    }

    private int obtenerIndiceJugador(Jugador jugador) {
        for (int i = 0; i < jugadores.length; i++) {
            if (jugadores[i].getIdJugador() == jugador.getIdJugador()) {
                return i;
            }
        }
        return -1;
    }

    private boolean todosRespondieron() {
        for (boolean responded : jugadoresRespondieron) {
            if (!responded) {
                return false;
            }
        }
        return true;
    }

    public void finalizarRonda() {
        finalizada = true;
        temporizador.detener();
        System.out.println("Ronda " + idRonda + " finalizada.");
    }

    public String mostrarIdRonda() {
        return "Ronda #" + idRonda;
    }   

    }

