/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import Servidor.Servidor;

/**
 *
 * @author fioli
 */
public class MainServidor {

    public static void main(String[] args) {
        try {
            Servidor servidor = new Servidor(5000); // inicia servidor
            servidor.iniciar(); // hilo que acepta conexiones

            // Abrimos la GUI de personalización
            new ControllerPersonalizar(servidor);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
