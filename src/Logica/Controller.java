/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import Presentacion.*;
import Entidad.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.util.*;
import javax.swing.SwingUtilities;

/**
 *
 * @author fioli
 */
public class Controller implements ActionListener {

    private GuiJuego gui;
    private Pregunta preguntaActual;
    private int contadorRondas = 1;
    private Timer timerVisual; // Solo para actualizar GUI
    private Temporizador temporizador;
    private Ronda rondaActual;
    private int contadorPreguntas = 0;
    private Jugador[] jugadores;

    public Controller() {
        gui = new GuiJuego();
        nuevaRonda();
        initEvents();
        gui.setVisible(true);
    }

    public void initEvents() {
        gui.getBtnEnviar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.getBtnEnviar()) {
            verificarRespuesta();
        }
    }

    // === CREAR NUEVA RONDA ===
    private void nuevaRonda() {
        int duracionSegundos = 20; // tiempo por pregunta
        jugadores = new Jugador[]{new Jugador(1, "Fio")};
        rondaActual = new Ronda(contadorRondas, jugadores, duracionSegundos);
        rondaActual.iniciarRonda();

        preguntaActual = rondaActual.getPreguntaActual();

        gui.setLblRonda("Ronda: " + contadorRondas);
        gui.setLblPregunta(preguntaActual.toString());
        gui.setTxtResultadoUsuario("");

        iniciarTemporizador();
    }

    // === SOLO ACTUALIZA LA GUI CON EL TIEMPO RESTANTE DE LA RONDA ===
    private void iniciarTemporizador() {
        // Cancela cualquier timer visual anterior
        if (timerVisual != null) {
            timerVisual.cancel();
        }

        // Obtener SIEMPRE el temporizador actualizado desde la ronda
        temporizador = rondaActual.getTemporizador();
        temporizador.iniciar();

        timerVisual = new Timer();
        timerVisual.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    gui.setLblTiempo("Tiempo: " + temporizador.getTiempoRestante() + "s");
                });

                // Cuando el tiempo termina
                if (!temporizador.isEnCurso()) {
                    timerVisual.cancel(); // detiene el visual

                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(gui, "Tiempo agotado para esta pregunta.");

                        rondaActual.responder(jugadores[0], new Resultado(1, -999));

                        // IMPORTANTE: obtener el nuevo temporizador si cambió de pregunta
                        if (rondaActual.isFinalizada()) {
                            JOptionPane.showMessageDialog(gui, "Ronda finalizada. ¡Nueva ronda!");
                            contadorRondas++;
                            nuevaRonda();
                        } else {
                            // mostrar siguiente pregunta
                            Pregunta siguiente = rondaActual.getPreguntaActual();
                            gui.setLblPregunta(siguiente.toString());
                            gui.setTxtResultadoUsuario("");

                            // Reiniciar el nuevo temporizador de la ronda
                            iniciarTemporizador();
                        }
                    });
                }
            }
        }, 0, 1000);
    }

    // === VERIFICAR RESPUESTA DEL USUARIO ===
    public void verificarRespuesta() {
        try {
            int respuestaUsuario = Integer.parseInt(gui.getTxtResultadoUsuario());
            Resultado resultado = new Resultado(1, respuestaUsuario);
            rondaActual.responder(jugadores[0], resultado);

            if (rondaActual.isFinalizada()) {
                timerVisual.cancel();
                JOptionPane.showMessageDialog(gui, "Ronda finalizada. ¡Nueva ronda!");
                contadorRondas++;
                nuevaRonda();
            } else {
                Pregunta p = rondaActual.getPreguntaActual();
                gui.setLblPregunta(p.toString());
                gui.setTxtResultadoUsuario("");
                iniciarTemporizador(); // ← agrega esto
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(gui, "Por favor ingresa un número válido.");
        }
    }
}
