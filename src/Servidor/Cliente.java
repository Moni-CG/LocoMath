/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

import Entidad.*;
import Logica.*;
import java.io.*;
import java.net.*;

/**
 *
 * @author fioli
 */
public class Cliente {

    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;
    private Controller controller; // se comunica con tu GUI

    public Cliente(String host, int puerto, Controller controller) throws Exception {
        this.controller = controller;

        socket = new Socket(host, puerto);
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        salida = new PrintWriter(socket.getOutputStream(), true);

        escucharServidor();
    }

    private void escucharServidor() {
        new Thread(() -> {
            try {
                String linea;
                while ((linea = entrada.readLine()) != null) {

                    if (linea.startsWith("PREGUNTA|")) {
                        controller.actualizarPregunta(linea.substring(9));
                    }

                    if (linea.startsWith("PUNTAJE|")) {
                        String[] p = linea.split("\\|");
                        controller.actualizarPuntaje(p[1], Integer.parseInt(p[2]));
                    }

                    if (linea.startsWith("RONDA|")) {
                        controller.actualizarRonda(Integer.parseInt(linea.split("\\|")[1]));
                    }

                    if (linea.startsWith("FINJUEGO|")) {
                        controller.mostrarFin();
                    }
                }

            } catch (Exception e) {
                System.out.println("Conexión perdida con el servidor.");
            }
        }).start();
    }

    public void enviarRespuesta(int r) {
        salida.println("RESPUESTA|" + r);
    }

    public void enviarNombre(String nombre) {
        salida.println(nombre);
    }
}
