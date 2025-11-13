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

    public void asignarPuntos(Jugador jugador) {
        if (temporizador.isTiempoAgotado()) {
            return;
        } //si se agoto tiempo no se asigna puntos

        if (jugadorRespondio(jugador.getIdJugador())) {
            String tipoOperacion = operacionesRonda[preguntaActual];
            jugador.agregarPuntos(puntosOperacion(tipoOperacion));
            reiniciarRespuestas();
        }
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

    public boolean jugadorRespondio(int idJugador) {
        for (boolean respondio : jugadoresRespondieron) {
            if (respondio) {
                return false;
            } //ya un jugador respondio primero
        }
        jugadoresRespondieron[idJugador - 1] = true;
        return true;
    }

    public void verificarTiempoPregunta() {
        if (temporizador.isTiempoAgotado()) {
            System.out.println("Se acabó el tiempo." + (preguntaActual + 1));
            preguntaActual++;
            if (preguntaActual < listaPreguntas.size()) {
                temporizador.detener();
                temporizador = new Temporizador(temporizador.getDuracion()); // reinicia el tiempo
                temporizador.iniciar();
                reiniciarRespuestas();
                System.out.println("Nueva pregunta: " + mostrarPregunta());
            } else {
                System.out.println("Ronda terminada.");
            }
        }
    }

    public boolean avanzarPregunta() {
        if (preguntaActual < listaPreguntas.size() - 1) {
            preguntaActual++;
            reiniciarRespuestas();
            return true;
        }
        return false; // Si no hay más preguntas, la ronda termina
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
