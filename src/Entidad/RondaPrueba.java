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
   ArrayList<Pregunta> listaPreguntas;
   ArrayList<Jugador> listaJugadores;
   private Temporizador temporizador;

    public RondaPrueba(int idRonda, ArrayList<Jugador> listaJugadores, Temporizador temporizador) {
        this.idRonda = idRonda;
        this.listaJugadores = listaJugadores;
        this.temporizador = temporizador;
        listaPreguntas = new ArrayList<>();
    }
    
    
   
}
