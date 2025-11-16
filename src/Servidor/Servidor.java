/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

import Entidad.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author fioli
 */
public class Servidor {

     private ServerSocket serverSocket;
    private ArrayList<HiloCliente> clientesConectados;
    private Ronda rondaActual;
    private int numRonda = 1;

    public Servidor(int puerto) throws IOException {
        serverSocket = new ServerSocket(puerto);
        clientesConectados = new ArrayList<>();
        System.out.println("Servidor iniciado en el puerto " + puerto);
    }

    public void iniciar() {
        new Thread(() -> {
            try {
                while (true) {
                    Socket socket = serverSocket.accept();
                    HiloCliente cliente = new HiloCliente(socket, this);
                    clientesConectados.add(cliente);
                    cliente.start();
                    System.out.println("Jugador conectado. Total: " + clientesConectados.size());

                    if (clientesConectados.size() == 5) {
                        iniciarRonda();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public synchronized void iniciarRonda() {
        ArrayList<Jugador> jugadores = new ArrayList<>();

        for (HiloCliente c : clientesConectados) {
            jugadores.add(c.getJugador());
        }

        rondaActual = new Ronda(numRonda, jugadores, new Temporizador(20));
        rondaActual.generarPreguntas(numRonda);

        broadcast("RONDA|" + numRonda);
        broadcast("PREGUNTA|" + rondaActual.mostrarPregunta());
    }

    public synchronized void recibirRespuesta(Jugador jugador, int respuesta) {
        boolean finaliza = rondaActual.asignarPuntos(jugador, respuesta);

        broadcast("PUNTAJE|" + jugador.getUsuario() + "|" + jugador.getPuntaje());

        if (finaliza) {
            numRonda++;
            if (numRonda <= 10) {
                iniciarRonda();
            } else {
                broadcast("FINJUEGO|Gracias por jugar.");
            }
        } else {
            broadcast("PREGUNTA|" + rondaActual.mostrarPregunta());
        }
    }

    public void broadcast(String mensaje) {
        for (HiloCliente c : clientesConectados) {
            c.enviar(mensaje);
        }
    }
}
