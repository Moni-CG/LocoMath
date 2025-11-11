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
    private Ronda rondaActual;
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
        int duracionSegundos = 10; // tiempo por pregunta
        jugadores = new Jugador[]{new Jugador(1, "Fio")};
        rondaActual = new Ronda(contadorRondas, jugadores, duracionSegundos);
        rondaActual.iniciarRonda();

        preguntaActual = rondaActual.getPreguntaActual();

        gui.setLblRonda("Ronda: " + contadorRondas);
        gui.setLblPregunta(preguntaActual.toString());
        gui.setTxtResultadoUsuario("");

        iniciarTimerVisual();
    }

    // === SOLO ACTUALIZA LA GUI CON EL TIEMPO RESTANTE DE LA RONDA ===
    private void iniciarTimerVisual() {
        if (timerVisual != null) {
            timerVisual.cancel();
        }

        timerVisual = new Timer();
        timerVisual.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    if (rondaActual != null && rondaActual.getPreguntaActual() != null) {
                        gui.setLblTiempo("Tiempo: " + rondaActual.getTemporizador().getTiempoRestante() + "s");
                    }
                });

                // Si el tiempo se acabó, el Temporizador ya pasa a la siguiente pregunta
                if (!rondaActual.getTemporizador().isEnCurso()) {
                    if (rondaActual.isFinalizada()) {
                        timerVisual.cancel();
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(gui, "🏁 Ronda finalizada");
                            contadorRondas++;
                            nuevaRonda();
                        });
                    }
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
                JOptionPane.showMessageDialog(gui, "✅ Ronda finalizada. ¡Nueva ronda!");
                contadorRondas++;
                nuevaRonda();
            } else {
                // Mostrar siguiente pregunta
                Pregunta p = rondaActual.getPreguntaActual();
                gui.setLblPregunta(p.toString());
                gui.setTxtResultadoUsuario("");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(gui, "Por favor ingresa un número válido.");
        }
    }
}
