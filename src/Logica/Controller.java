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
    private int contadorPreguntas = 1;
    private Temporizador temporizador;
    private Timer timerVisual; // actualiza la GUI
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

    //aqui se brindan funcion que se le brindara a los botones al momento de accionar estos
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.getBtnEnviar()) {
            verificarRespuesta();
        }
    }

    // crea nueva ronda con su pregunta y tiempo
    private void nuevaRonda() {
        int duracionSegundos = 20; //duracion para cada pregunta
        jugadores = new Jugador[]{new Jugador(1, "Fio")};
        rondaActual = new Ronda(contadorPreguntas, jugadores, duracionSegundos);
        Operacion operacion = new Operacion();

        rondaActual.iniciarRonda();
        preguntaActual = rondaActual.getPreguntaActual();

        gui.setLblRonda("Ronda: " + contadorPreguntas);
        gui.setLblPregunta(preguntaActual.toString());
        gui.setTxtResultadoUsuario("");
        iniciarTemporizador(duracionSegundos);
    }

    private void iniciarTemporizador(int duracion) {
        if (temporizador != null) {
            temporizador.detener();
        }
        if (timerVisual != null) {
            timerVisual.cancel();
        }

        temporizador = new Temporizador(duracion);
        temporizador.iniciar();

        timerVisual = new Timer();
        timerVisual.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    gui.setLblTiempo("Tiempo: " + temporizador.getTiempoRestante() + "s");
                });

                if (!temporizador.isEnCurso()) {
                    timerVisual.cancel();
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(gui, "¡Tiempo agotado!");
                        contadorPreguntas++;
                        nuevaRonda();
                    });
                }
            }
        }, 0, 1000);
    }

    //se verificara la respuesta que brinda el usuario con la pregunta
    public void verificarRespuesta() {
        try {
            int respuestaUsuario = Integer.parseInt(gui.getTxtResultadoUsuario());
            Resultado resultado = new Resultado(1, respuestaUsuario);
            rondaActual.responder(jugadores[0], resultado);

            if (rondaActual.isFinalizada()) {
                contadorPreguntas++;
                JOptionPane.showMessageDialog(gui, "Ronda finalizada. ¡Nueva ronda!");
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
