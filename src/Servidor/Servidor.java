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
    private int jugadoresEsperados = 2;
    private int rondasTotales = 10;
    private int tiempoPorPregunta = 20;
    private boolean configurado = false;
    private int siguienteId = 1;
    private Thread hiloTemporizador;

    public Servidor(int puerto) throws IOException {
        serverSocket = new ServerSocket(puerto);
        clientesConectados = new ArrayList<>();
        System.out.println("Servidor iniciado en el puerto " + puerto);
    }

    public synchronized void configurarJuegoInicial(int jugadores, int rondas, int tiempo) {
        this.jugadoresEsperados = jugadores;
        this.rondasTotales = rondas;
        this.tiempoPorPregunta = tiempo;
        this.configurado = true;

        System.out.println("[CONFIG] Jugadores: " + jugadores
                + ", Rondas: " + rondas
                + ", Tiempo por pregunta: " + tiempo + "s");
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

                    if (configurado && clientesConectados.size() == jugadoresEsperados) {
                        iniciarRonda();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public synchronized int generarIdJugador() {
        return siguienteId++;
    }

    /**
     * Inicia una nueva ronda o pregunta
     */
    public synchronized void iniciarRonda() {
        if (numRonda > rondasTotales) {
            Jugador ganador = obtenerGanador();

            String mensajeGanador = "GANADOR|"
                    + ganador.getUsuario()
                    + "|" + ganador.getPuntaje();
            broadcast(mensajeGanador);
            broadcast("FINJUEGO|¡Juego terminado!");
            return;
        }

        // Crear ronda si es nueva
        if (rondaActual == null || rondaActual.isRondaFinalizada()) {
            ArrayList<Jugador> jugadores = new ArrayList<>();
            for (HiloCliente c : clientesConectados) {
                jugadores.add(c.getJugador());
            }

            rondaActual = new Ronda(numRonda, jugadores, new Temporizador(tiempoPorPregunta));
            rondaActual.generarPreguntas(numRonda);
            System.out.println("Inicia Ronda " + numRonda);
        }

        enviarPreguntaActual();
    }

    /**
     * Envía la pregunta actual y comienza temporizador
     */
    private void enviarPreguntaActual() {
        //MATAR EL HILO ANTERIOR SI AÚN SIGUE VIVO
        if (hiloTemporizador != null && hiloTemporizador.isAlive()) {
            hiloTemporizador.interrupt();
        }

        Temporizador temp = rondaActual.getTemporizador();
        temp.iniciar();

        broadcast("RONDA|" + numRonda);
        broadcast("PREGUNTA|" + rondaActual.mostrarPregunta());

        hiloTemporizador = new Thread(() -> {
            try {
                while (temp.isEnCurso() && !Thread.currentThread().isInterrupted()) {
                    broadcast("TIEMPO|" + temp.getTiempoRestante());
                    Thread.sleep(1000);
                }

                if (Thread.currentThread().isInterrupted()) {
                    return;
                }

                if (temp.isTiempoAgotado()) {
                    boolean avanzo = rondaActual.avanzarPregunta();
                    if (avanzo) {
                        enviarPreguntaActual();
                    } else {
                        numRonda++;
                        rondaActual = null;
                        iniciarRonda();
                    }
                }
            } catch (InterruptedException e) {
                // hilo detenido → OK
            }
        });

        hiloTemporizador.start();
    }

    /**
     * Recibe la respuesta de un jugador
     */
    public synchronized void recibirRespuesta(Jugador jugador, int respuesta) {
        boolean finaliza = rondaActual.asignarPuntos(jugador, respuesta);

        // Actualiza puntaje para todos los clientes
        broadcast("PUNTAJE|" + jugador.getUsuario() + "|" + jugador.getPuntaje());

        if (finaliza) {
            // Pregunta finalizada → avanzar a siguiente pregunta
            boolean avanzo = rondaActual.avanzarPregunta();
            if (avanzo) {
                enviarPreguntaActual();
            } else {
                numRonda++;
                rondaActual = null; // nueva ronda
                iniciarRonda();
            }
        } else {
            // Pregunta continúa, enviamos la misma pregunta actual
            broadcast("PREGUNTA|" + rondaActual.mostrarPregunta());
        }
    }

    public void broadcast(String mensaje) {
        for (HiloCliente c : clientesConectados) {
            c.enviar(mensaje);
        }
    }

    private Jugador obtenerGanador() {
        Jugador ganador = null;
        for (HiloCliente c : clientesConectados) {
            Jugador j = c.getJugador();
            if (ganador == null || j.getPuntaje() > ganador.getPuntaje()) {
                ganador = j;
            }
        }
        return ganador;
    }

    public synchronized void removerCliente(HiloCliente cliente) {
        clientesConectados.remove(cliente);
        System.out.println("Cliente removido. Quedan: " + clientesConectados.size());

        if (clientesConectados.isEmpty()) {
            System.out.println("No hay clientes. Cerrando servidor...");
            cerrarServidor();
        }
    }

    public void cerrarServidor() {
        try {
            if (hiloTemporizador != null && hiloTemporizador.isAlive()) {
                hiloTemporizador.interrupt();
            }

            for (HiloCliente c : clientesConectados) {
                try {
                    c.interrupt();
                } catch (Exception ex) {
                }
                try {
                    c.enviar("SERVIDOR_CERRADO");
                } catch (Exception ex) {
                }
            }

            clientesConectados.clear();

            serverSocket.close(); // ← IMPORTANTE

            System.out.println("Servidor apagado correctamente.");
        } catch (IOException ex) {
            System.out.println("Error al cerrar servidor: " + ex.getMessage());
        }
    }

}
