/*
 * pnEnviarComando.java
 *
 * Created on 25/01/2012, 11:32:39 AM
 */
package server.gui;

import DataBase.ConexionBase;
import javax.swing.JOptionPane;
import server.gui_server;

/**
 *
 * @author qmarqeva
 */
public class pnEnviarComando extends javax.swing.JPanel {

    gui_server padre;

    /** Creates new form pnEnviarComando */
    public pnEnviarComando(gui_server pdr) {
        padre = pdr;
        initComponents();
        spIdEstacion.setMinimum(0);
        spIdEstacion.setMaximum(999);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtIdEquipo = new javax.swing.JTextField();
        txtComando = new javax.swing.JTextField();
        spIdEstacion = new com.toedter.components.JSpinField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnEnviar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        txtIdEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdEquipoActionPerformed(evt);
            }
        });

        spIdEstacion.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel1.setText("ID Estacion");

        jLabel2.setText("ID Equipo");

        jLabel3.setText("Comando");

        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(spIdEstacion, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addComponent(jLabel3)
                    .addComponent(txtComando, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btnCancelar)
                .addContainerGap(46, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spIdEstacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(8, 8, 8)
                .addComponent(txtComando, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEnviar)
                    .addComponent(btnCancelar))
                .addContainerGap(22, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtIdEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdEquipoActionPerformed
    }//GEN-LAST:event_txtIdEquipoActionPerformed

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed

        if (spIdEstacion.getValue() > 0
                && !txtComando.getText().isEmpty()
                && !txtIdEquipo.getText().isEmpty()) {
            
            ConexionBase cbCmd = new ConexionBase();
            String std =
                    cbCmd.enviarComando(
                    spIdEstacion.getValue(),
                    txtComando.getText(),
                    txtIdEquipo.getText());
            cbCmd.CerrarConexion();

            JOptionPane.showMessageDialog(null, std, "Estado", JOptionPane.INFORMATION_MESSAGE);

            limpiarComp();
            padre.cerrarDialog();

        } else {
            JOptionPane.showMessageDialog(null, "Campos insuficientes", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnEnviarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpiarComp();
        padre.cerrarDialog();
    }//GEN-LAST:event_btnCancelarActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private com.toedter.components.JSpinField spIdEstacion;
    private javax.swing.JTextField txtComando;
    private javax.swing.JTextField txtIdEquipo;
    // End of variables declaration//GEN-END:variables

    public void limpiarComp() {
        spIdEstacion.setValue(0);
        txtComando.setText("");
        txtIdEquipo.setText("");
    }
}
