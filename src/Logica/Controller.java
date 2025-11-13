package Logica;

import Presentacion.*;
import Entidad.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.util.*;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * Controlador principal del juego. Maneja la lógica del flujo de rondas,
 * preguntas, temporizador y respuestas del usuario.
 *
 * @author fioli
 */
public class Controller implements ActionListener {

    private GuiJuego gui;
    private RondaPrueba rondaActual;
    private Temporizador temporizador;
    private Jugador jugador; // Asumiendo un solo jugador para simplicidad
    private int contadorRondas = 1; // Inicia en ronda 1
    private Timer timerActualizacion; // Timer de Swing para actualizar la GUI

    public Controller() {
        gui = new GuiJuego();
        initEvents();
        inicializarJuego();
        gui.setVisible(true);
    }

    /**
     * Inicializa los eventos de la GUI.
     */
    private void initEvents() {
        gui.getBtnEnviar().addActionListener(this);
    }

    /**
     * Inicializa el juego: crea el jugador, la primera ronda, genera preguntas,
     * inicia el temporizador y muestra la ronda y pregunta inicial.
     */
    private void inicializarJuego() {
        // Crear un jugador (puedes expandir para múltiples si es necesario)
        this.jugador = new Jugador(1, "Jugador1"); // Asume que Jugador tiene constructor (int id, String nombre)

        // Crear lista de jugadores (solo uno por ahora)
        ArrayList<Jugador> listaJugadores = new ArrayList<>();
        listaJugadores.add(jugador);

        // Crear temporizador (asume duración en segundos, ej. 30 segundos por pregunta)
        this.temporizador = new Temporizador(30);

        // Crear primera ronda
        this.rondaActual = new RondaPrueba(contadorRondas, listaJugadores, temporizador);
        rondaActual.generarPreguntas(contadorRondas);

        // Mostrar ronda y primera pregunta
        mostrarRondaYPregunta();

        // Iniciar temporizador y timer de actualización de GUI
        temporizador.iniciar();
        iniciarTimerActualizacion();
    }

    /**
     * Muestra la ronda actual y la pregunta actual en la GUI.
     */
    private void mostrarRondaYPregunta() {
        gui.setLblRonda(rondaActual.mostrarRonda());
        gui.setLblPregunta(rondaActual.mostrarPregunta());
        gui.setTxtResultadoUsuario(""); // Limpiar campo de respuesta
    }

    /**
     * Inicia un Timer de Swing para actualizar el tiempo en la GUI cada segundo
     * y verificar si el tiempo se agotó.
     */
    private void iniciarTimerActualizacion() {
        timerActualizacion = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.setLblTiempo("Tiempo: " + temporizador.getTiempoRestante() + "s");
                rondaActual.verificarTiempoPregunta();

                if (rondaActual.tiempoAgotado()) {
                    JOptionPane.showMessageDialog(gui, "⏰ ¡Se acabó el tiempo!");
                    pasarASiguientePreguntaORonda();
                } else if (rondaActual.isRondaFinalizada()) {
                    pasarASiguientePreguntaORonda();
                }
            }
        });
        timerActualizacion.start();
    }

    /**
     * Maneja el evento del botón "Enviar": verifica la respuesta del usuario.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.getBtnEnviar()) {
            verificarRespuesta();
        }
    }

    /**
     * Verifica la respuesta del usuario. Si es correcta, asigna puntos y pasa a
     * la siguiente. Si no, no hace nada (puedes agregar lógica para intentos si
     * es necesario).
     */
    private void verificarRespuesta() {
        try {
            int respuestaUsuario = Integer.parseInt(gui.getTxtResultadoUsuario().trim());
            if (rondaActual.isRespuestaCorrecta(respuestaUsuario)) {
                // Registrar respuesta y asignar puntos
                rondaActual.registrarRespuestaJugador(jugador.getIdJugador());
                rondaActual.asignarPuntos(jugador);
                JOptionPane.showMessageDialog(gui, "¡Respuesta correcta! Puntos asignados.");
                pasarASiguientePreguntaORonda();
            } else {
                JOptionPane.showMessageDialog(gui, "Respuesta incorrecta. Intenta de nuevo.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(gui, "Por favor, ingresa un número válido.");
        }
    }

    /**
     * Pasa a la siguiente pregunta o ronda según corresponda.
     */
    private void pasarASiguientePreguntaORonda() {
        temporizador.detener();

        if (rondaActual.avanzarPregunta()) {
            // Hay más preguntas
            mostrarRondaYPregunta();
            reiniciarTemporizador(30);
        } else if (++contadorRondas <= 10) {
            // Nueva ronda
            ArrayList<Jugador> listaJugadores = new ArrayList<>();
            listaJugadores.add(jugador);

            rondaActual = new RondaPrueba(contadorRondas, listaJugadores, new Temporizador(30));
            rondaActual.generarPreguntas(contadorRondas);
            mostrarRondaYPregunta();
            reiniciarTemporizador(30);
        } else {
            // Fin del juego
            timerActualizacion.stop();
            JOptionPane.showMessageDialog(gui,
                    "🎉 ¡Juego terminado! Puntaje final: " + jugador.getPuntaje());
        }
    }

    private void reiniciarTemporizador(int segundos) {
        temporizador = new Temporizador(segundos);
        temporizador.iniciar();
    }

}
