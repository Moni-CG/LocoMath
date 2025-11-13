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

    public RondaPrueba(int idRonda, ArrayList<Jugador> listaJugadores, Temporizador temporizador) {
        this.idRonda = idRonda;
        this.listaJugadores = listaJugadores;
        this.temporizador = temporizador;
        listaPreguntas = new ArrayList<>(6);
        jugadoresRespondieron = new boolean[listaJugadores.size()];
    }

    public boolean tiempoAgotado() {
        return temporizador.isTiempoAgotado();
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
    
    public void asignarPuntos(){
        
    }

}
