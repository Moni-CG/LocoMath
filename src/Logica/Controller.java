/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import Presentacion.*;
import Entidad.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

/**
 *
 * @author fioli
 */
public class Controller implements ActionListener {

    private GuiJuego gui;
    private Pregunta preguntaActual;
    private int contadorPreguntas = 1;

    public Controller() {
        gui = new GuiJuego();
        initEvents();
        nuevaPregunta();
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

    //generar nueva pregunta para los jugadores
    private void nuevaPregunta() {
        Operacion operacion = new Operacion();
        preguntaActual = new Pregunta(contadorPreguntas, operacion);
        gui.setLblIdPregunta(preguntaActual.toStringIdPregunta());
        gui.setLblPregunta(preguntaActual.toString());
        gui.setTxtResultadoUsuario("");
    }

    //se verificara la respuesta que brinda el usuario con la pregunta
    public void verificarRespuesta() {
        try {
            int respuestaUsuario = Integer.parseInt(gui.getTxtResultadoUsuario());
            
            //como prueba agregamos por ahora id de jugador 1
            Resultado resultado = new Resultado(1, respuestaUsuario);

            boolean correcta = preguntaActual.verificarRespuesta(resultado);

            if (correcta) {
                JOptionPane.showMessageDialog(gui, "¡Correcto!");
            } else {
                JOptionPane.showMessageDialog(gui, "Incorrecto. La respuesta era: "
                        + preguntaActual.getOperacion().getResultado());
            }

            contadorPreguntas++;
            nuevaPregunta(); // genera una nueva pregunta
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(gui, "Por favor ingresa un número válido.");
        }
    }
}
