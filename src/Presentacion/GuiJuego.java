package Presentacion;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author fioli
 */
public class GuiJuego extends javax.swing.JFrame {

    public GuiJuego() {
        initComponents();

    }

    public JButton getBtnEnviar() {
        return btnEnviar;
    }

    public void setBtnEnviar(JButton btnEnviar) {
        this.btnEnviar = btnEnviar;
    }

    public String getLblRonda() {
        return lblRonda.getText();
    }

    public void setLblRonda(String lblIdRonda) {
        this.lblRonda.setText(lblIdRonda);
    }

    public String getLblPregunta() {
        return lblPregunta.getText();
    }

    public void setLblPregunta(String lblPregunta) {
        this.lblPregunta.setText(lblPregunta);
    }

    public JPanel getPanelJuego() {
        return panelJuego;
    }

    public void setPanelJuego(JPanel panelJuego) {
        this.panelJuego = panelJuego;
    }

    public String getTxtResultadoUsuario() {
        return txtResultadoUsuario.getText();
    }

    public void setTxtResultadoUsuario(String txtResultadoUsuario) {
        this.txtResultadoUsuario.setText(txtResultadoUsuario);
    }

    public String getLblTiempo() {
        return lblTiempo.getText();
    }

    public void setLblTiempo(String lblTiempo) {
        this.lblTiempo.setText(lblTiempo);
    }
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelJuego = new javax.swing.JPanel();
        lblPregunta = new javax.swing.JLabel();
        txtResultadoUsuario = new javax.swing.JTextField();
        btnEnviar = new javax.swing.JButton();
        panelTiempo = new javax.swing.JPanel();
        lblTiempo = new javax.swing.JLabel();
        panelRonda = new javax.swing.JPanel();
        lblRonda = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblPregunta.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        lblPregunta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        txtResultadoUsuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btnEnviar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnEnviar.setText("Enviar");

        panelTiempo.setBackground(new java.awt.Color(153, 153, 255));

        javax.swing.GroupLayout panelTiempoLayout = new javax.swing.GroupLayout(panelTiempo);
        panelTiempo.setLayout(panelTiempoLayout);
        panelTiempoLayout.setHorizontalGroup(
            panelTiempoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTiempoLayout.createSequentialGroup()
                .addGap(195, 195, 195)
                .addComponent(lblTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(196, Short.MAX_VALUE))
        );
        panelTiempoLayout.setVerticalGroup(
            panelTiempoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTiempoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblTiempo, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );

        panelRonda.setBackground(new java.awt.Color(153, 153, 255));

        lblRonda.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout panelRondaLayout = new javax.swing.GroupLayout(panelRonda);
        panelRonda.setLayout(panelRondaLayout);
        panelRondaLayout.setHorizontalGroup(
            panelRondaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRondaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblRonda, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(226, 226, 226))
        );
        panelRondaLayout.setVerticalGroup(
            panelRondaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRondaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblRonda, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelJuegoLayout = new javax.swing.GroupLayout(panelJuego);
        panelJuego.setLayout(panelJuegoLayout);
        panelJuegoLayout.setHorizontalGroup(
            panelJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTiempo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelRonda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelJuegoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(panelJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelJuegoLayout.createSequentialGroup()
                        .addGroup(panelJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtResultadoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(221, 221, 221))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelJuegoLayout.createSequentialGroup()
                        .addComponent(lblPregunta, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(200, 200, 200))))
        );
        panelJuegoLayout.setVerticalGroup(
            panelJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJuegoLayout.createSequentialGroup()
                .addComponent(panelRonda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(lblPregunta, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(txtResultadoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(panelTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelJuego, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelJuego, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviar;
    private javax.swing.JLabel lblPregunta;
    private javax.swing.JLabel lblRonda;
    private javax.swing.JLabel lblTiempo;
    private javax.swing.JPanel panelJuego;
    private javax.swing.JPanel panelRonda;
    private javax.swing.JPanel panelTiempo;
    private javax.swing.JTextField txtResultadoUsuario;
    // End of variables declaration//GEN-END:variables
}
