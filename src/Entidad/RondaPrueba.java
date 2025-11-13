/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidad;

import java.util.ArrayList;

/**
 *
 * @author fioli
 */
public class RondaPrueba {

    private int idRonda;
    private ArrayList<Pregunta> listaPreguntas;
    private ArrayList<Jugador> listaJugadores;
    private Temporizador temporizador;
    private String[] operacionesRonda;
    private boolean[] jugadoresRespondieron;
    private int preguntaActual;

    public RondaPrueba(int idRonda, ArrayList<Jugador> listaJugadores, Temporizador temporizador) {
        this.idRonda = idRonda;
        this.listaJugadores = listaJugadores;
        this.temporizador = temporizador;
        listaPreguntas = new ArrayList<>();
        jugadoresRespondieron = new boolean[listaJugadores.size()];
        preguntaActual = 0;
    }

    public Temporizador getTemporizador() {
        return temporizador;
    }

    public boolean tiempoAgotado() {
        return temporizador.isTiempoAgotado();
    }

    public void registrarRespuestaJugador(int idJugador) {
        jugadoresRespondieron[idJugador - 1] = true;
    }

    public void asignarOperacionesPorRonda(int idRonda) {

        switch (idRonda) {
            case 1:
                operacionesRonda = new String[]{"+", "+", "+", "+", "+", "+"};
                break;
            case 2:
                operacionesRonda = new String[]{"+", "+", "-", "+", "+", "-"};
                break;
            case 3:
                operacionesRonda = new String[]{"+", "-", "+", "-", "+", "-"};
                break;
            case 4:
                operacionesRonda = new String[]{"+", "+", "-", "-", "x", "x"};
                break;
            case 5:
                operacionesRonda = new String[]{"+", "+", "-", "-", "x", "x"};
                break;
            case 6:
                operacionesRonda = new String[]{"x", "x", "x", "/", "/", "/"};
                break;
            case 7:
                operacionesRonda = new String[]{"+", "+", "x", "x", "/", "/"};
                break;
            case 8:
                operacionesRonda = new String[]{"-", "-", "x", "x", "/", "/"};
                break;
            case 9:
                operacionesRonda = new String[]{"+", "-", "-", "x", "x", "/"};
                break;
            case 10:
                operacionesRonda = new String[]{"+", "-", "x", "x", "/", "/"};
                break;
        }

    }

    public void generarPreguntas(int idRonda) {
        asignarOperacionesPorRonda(idRonda); //Asigna operaciones de la ronda
        listaPreguntas.clear(); //limpia la lista de preguntas

        for (int i = 0; i < 6; i++) {
            Pregunta pregunta = new Pregunta(i + 1);
            pregunta.generarOperacion(operacionesRonda[i]);
            listaPreguntas.add(pregunta);
        }
    }

    public boolean asignarPuntos(Jugador jugador, int respuesta) {
        if (temporizador.isTiempoAgotado()) {
            return false;
        }

        if (isRespuestaCorrecta(respuesta)) {
            if (jugadorRespondio(jugador.getIdJugador())) {
                String tipoOperacion = operacionesRonda[preguntaActual];
                jugador.agregarPuntos(puntosOperacion(tipoOperacion));
                reiniciarRespuestas();
                return !avanzarPregunta(); // true = ronda terminada
            }
        }
        return false;
    }

    public void reiniciarRespuestas() {
        for (int i = 0; i < jugadoresRespondieron.length; i++) {
            jugadoresRespondieron[i] = false;
        }
    }

    public int puntosOperacion(String tipoOperacion) {
        if (tipoOperacion.equals("+") || tipoOperacion.equals("-")) {
            return 100;
        } else if (tipoOperacion.equals("x")) {
            return 150;
        } else if (tipoOperacion.equals("/")) {
            return 200;
        }
        return 0;
    }

    //verifica si un jugador respondio primero
    public boolean jugadorRespondio(int idJugador) {
        for (boolean respondio : jugadoresRespondieron) {
            if (respondio) {
                return false;
            } //ya un jugador respondio primero
        }
        jugadoresRespondieron[idJugador - 1] = true;
        return true;
    }

    public void reiniciarTiempo() {
        if (temporizador != null) {
            temporizador.detener(); // Detiene el temporizador actual
            temporizador = new Temporizador(temporizador.getDuracion()); // Crea uno nuevo con la misma duración
            temporizador.iniciar(); // Lo vuelve a iniciar
            System.out.println("Temporizador reiniciado a " + temporizador.getDuracion() + " segundos.");
        } else {
            System.out.println("No hay un temporizador asignado para reiniciar.");
        }
    }

    public boolean avanzarPregunta() {
        if (preguntaActual < listaPreguntas.size() - 1) {
            preguntaActual++;
            reiniciarRespuestas();
            reiniciarTiempo();
            return true;
        }

        // No hay más preguntas, marcar fin de ronda
        preguntaActual = listaPreguntas.size();
        return false;
    }

    public boolean isRespuestaCorrecta(int respuesta) {
        return listaPreguntas.get(preguntaActual).getResultado() == respuesta;
    }

    public boolean isRondaFinalizada() {
        return preguntaActual >= listaPreguntas.size(); //verifica si llegamos a la ultima pregunta de la ronda
    }

    public String mostrarPregunta() {
        return listaPreguntas.get(preguntaActual).toString();
    }

    public String mostrarRonda() {
        return "Ronda# " + idRonda;
    }

}
