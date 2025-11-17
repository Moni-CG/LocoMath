/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import Presentacion.GuiPersonalizar;
import Servidor.Servidor;
import java.awt.event.*;

/**
 *
 * @author fioli
 */
public class ControllerPersonalizar implements ActionListener {

    private GuiPersonalizar gui;
    private Servidor servidor;

    public ControllerPersonalizar(Servidor servidor) {
        this.servidor = servidor;
        gui = new GuiPersonalizar();
        initEvents();
        gui.setVisible(true);
    }

    private void initEvents() {
        gui.getBtnJugar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.getBtnJugar()) {
            int jugadores = gui.getSpinCantidadJugadores();
            int rondas = gui.getSpinCantidadRondas();
            int tiempo = gui.getSpinCantidadTiempo();

            servidor.configurarJuegoInicial(jugadores, rondas, tiempo);

            gui.dispose(); // cerramos la pantalla de personalización
        }
    }
}
