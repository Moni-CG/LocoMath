package Logica;

import Presentacion.*;
import Entidad.*;
import Servidor.Cliente;
import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author fioli
 */
public class Controller implements ActionListener {

    private GuiJuego gui;
    private Login login;
    private Jugador jugador;
    private Cliente cliente;

    public Controller() {
        login = new Login();
        gui = new GuiJuego();
        initEvents();
        login.setVisible(true);
    }

    /**
     * Asocia los listeners a la interfaz
     */
    private void initEvents() {
        gui.getBtnEnviar().addActionListener(this);
        login.getBtnIngresar().addActionListener(this);
    }

    /**
     * Manejo de eventos
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == gui.getBtnEnviar()) {
            procesarRespuesta();
        }

        if (e.getSource() == login.getBtnIngresar()) {
            try {
                iniciarSesion();
            } catch (Exception ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Inicia sesión del jugador y establece conexión con el servidor.
     */
    public void iniciarSesion() throws Exception {
        String nombre = login.getJtxNombre().getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(login,
                    "Ingrese un nombre para continuar.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        jugador = new Jugador(0, nombre); // el ID real lo asigna el servidor
        cliente = new Cliente("127.0.0.1", 5000, this);

        cliente.enviarNombre(nombre);

        login.dispose();
        gui.setVisible(true);

        // Mostramos nombre en GUI desde el inicio
        gui.setLblNombre(nombre);
    }

    /**
     * Envía la respuesta del usuario al servidor (solo eso).
     */
    private void procesarRespuesta() {
        try {
            int respuesta = Integer.parseInt(gui.getTxtResultadoUsuario().trim());
            cliente.enviarRespuesta(respuesta);
            gui.setTxtResultadoUsuario("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(gui,
                    "Por favor ingrese un número válido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // ----------------------------
    // MÉTODOS QUE LLAMA EL CLIENTE
    // ----------------------------

    /** Actualiza la pregunta en pantalla */
    public void actualizarPregunta(String texto) {
        gui.setLblPregunta(texto);
    }

    /** Actualiza la ronda */
    public void actualizarRonda(int r) {
        gui.setLblRonda("Ronda: " + r);
    }

    /** Actualiza el puntaje */
    public void actualizarPuntaje(String usuario, int puntaje) {

        if (usuario.equals(jugador.getUsuario())) {
            jugador.setPuntaje(puntaje);
        }

        gui.setLblPuntaje(String.valueOf(puntaje));
    }

    /** Recibe mensaje del servidor cuando termina el juego */
    public void mostrarFin() {
        JOptionPane.showMessageDialog(gui,
                "El juego ha terminado.\nPuntaje final: " + jugador.getPuntaje(),
                "Fin del Juego",
                JOptionPane.INFORMATION_MESSAGE);

        System.exit(0);
    }
}
