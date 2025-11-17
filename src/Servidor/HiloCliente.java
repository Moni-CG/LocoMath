/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

import Entidad.*;
import java.io.*;
import java.net.*;

/**
 *
 * @author fioli
 */
public class HiloCliente extends Thread {

    private Socket socket;
    private Servidor servidor;
    private BufferedReader entrada;
    private PrintWriter salida;
    private Jugador jugador;

    public HiloCliente(Socket socket, Servidor servidor) throws IOException {
        this.socket = socket;
        this.servidor = servidor;

        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        salida = new PrintWriter(socket.getOutputStream(), true);
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void enviar(String msg) {
        salida.println(msg);
    }

    @Override
    public void run() {
        try {
            salida.println("NOMBRE?");
            String nombre = entrada.readLine();
            jugador = new Jugador(servidor.generarIdJugador(), nombre);

            salida.println("BIENVENIDO|" + nombre);

            String linea;
            while ((linea = entrada.readLine()) != null) {

                if (linea.startsWith("RESPUESTA|")) {
                    int respuesta = Integer.parseInt(linea.split("\\|")[1]);
                    servidor.recibirRespuesta(jugador, respuesta);
                }

            }

        } catch (Exception e) {
            System.out.println("Jugador desconectado: " + (jugador != null ? jugador.getUsuario() : "desconocido"));
        } finally {
            servidor.removerCliente(this);
            try {
                socket.close();
            } catch (IOException ex) {
            }
        }
    }
}
