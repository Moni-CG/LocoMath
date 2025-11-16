package Logica;

import Presentacion.*;
import Entidad.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.util.*;

/**
 * @author fioli
 */
public class Controller implements ActionListener {

    private GuiJuego gui;
    private Login login;
    private Ronda rondaActual;
    private Jugador jugador;
    private Timer timerGUI; // Timer para actualizar el label del tiempo
    private int numRonda = 1; // ronda actual

    public Controller() {
        login = new Login();
        gui = new GuiJuego();
        initEvents();
        login.setVisible(true);
    }

    /**
     * Inicializa los eventos de la interfaz.
     */
    private void initEvents() {
        gui.getBtnEnviar().addActionListener(this);
        login.getBtnIngresar().addActionListener(this);
    }

    /**
     * Maneja el evento de los botones al dar click
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.getBtnEnviar()) {
            procesarRespuesta();
        }
        if (e.getSource() == login.getBtnIngresar()) {
            iniciarSesion();
        }
    }

    public void iniciarSesion() {
        String nombre = login.getJtxNombre().getText().trim();

        if (nombre.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(login,
                    "Ingrese un nombre para continuar.",
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        jugador = new Jugador(1, nombre);
        // Cerrar login y abrir juego
        login.dispose();
        abrirJuego();
    }

    private void abrirJuego() {
        gui.setVisible(true);
        iniciarJuego();
    }

    /**
     * Inicia el juego con la primera ronda y jugador.
     */
    private void iniciarJuego() {
        
        Temporizador temporizador = new Temporizador(20); // duracion para cada 
        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador);

        rondaActual = new Ronda(numRonda, jugadores, temporizador);
        rondaActual.generarPreguntas(numRonda);

        iniciarTemporizadorVisual();
        actualizarVista();
    }

    /**
     * Procesa la respuesta del usuario.
     */
    private void procesarRespuesta() {
        try {
            int respuestaUsuario = Integer.parseInt(gui.getTxtResultadoUsuario().trim());

            boolean asignaPuntos = rondaActual.asignarPuntos(jugador, respuestaUsuario);
            // Aquí revisa si la ronda termino
            if (asignaPuntos) {
                finalizarRonda();
                return;
            }
            actualizarVista();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(gui, "Por favor, ingresa un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Inicia el temporizador visual que actualiza la GUI cada segundo.
     */
    private void iniciarTemporizadorVisual() {
        detenerTemporizadorVisual(); // esto es por si ya hay uno

        timerGUI = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rondaActual.getTemporizador().isTiempoAgotado()) {
                    siguientePregunta();
                } else {
                    gui.setLblTiempo("Tiempo: " + rondaActual.getTemporizador().getTiempoRestante() + "s");
                }
            }
        });

        rondaActual.getTemporizador().iniciar();
        timerGUI.start();
    }

    /**
     * Detiene el temporizador visual de la GUI.
     */
    private void detenerTemporizadorVisual() {
        if (timerGUI != null && timerGUI.isRunning()) {
            timerGUI.stop();
        }
    }

    /**
     * Avanza a la siguiente pregunta o finaliza la ronda si no hay más.
     */
    private void siguientePregunta() {
        boolean hayMasPreguntas = rondaActual.avanzarPregunta();
        if (hayMasPreguntas) {
            iniciarTemporizadorVisual();
            actualizarVista();
        } else {
            finalizarRonda();
        }
    }

    /**
     * Actualiza los labels y limpia campos en la GUI.
     */
    private void actualizarVista() {
        gui.setLblRonda("Ronda: " + numRonda);
        gui.setLblPregunta(rondaActual.mostrarPregunta());
        gui.setTxtResultadoUsuario("");
        gui.setLblTiempo("Tiempo: " + rondaActual.getTemporizador().getTiempoRestante() + "s");
    }

    /**
     * Finaliza la ronda actual, mostrando puntaje y permitiendo continuar o
     * salir.
     */
    private void finalizarRonda() {
        detenerTemporizadorVisual();
        rondaActual.getTemporizador().detener();

        JOptionPane.showMessageDialog(gui,
                "¡Ronda " + numRonda + " finalizada!\nPuntaje total: " + jugador.getPuntaje(),
                "Fin de Ronda", JOptionPane.INFORMATION_MESSAGE);

        int opcion = JOptionPane.showConfirmDialog(gui, "¿Deseas jugar otra ronda?", "Continuar", JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION && numRonda < 10) {
            numRonda++;
            iniciarJuego();
        } else {
            JOptionPane.showMessageDialog(gui, "Gracias por jugar. Puntaje final: " + jugador.getPuntaje());
            System.exit(0);
        }
    }
}
