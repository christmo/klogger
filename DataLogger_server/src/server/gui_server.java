package server;

import Utilities.UtilidadesApp;
import java.awt.Dialog.ModalityType;
import java.net.MalformedURLException;
import java.util.GregorianCalendar;
import javax.swing.JDialog;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager;
import server.gui.pnEnviarComando;

/**
 * @author qmarqeva
 */
public class gui_server extends javax.swing.JFrame {

    public static int aux3;
    public static boolean listo = true;
    public static boolean STOP_SERVERS;
    private JDialog envCmd;

    public gui_server() {
        STOP_SERVERS = false;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtPuertoAtlantis = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnStop = new javax.swing.JButton();
        btnIniciar = new javax.swing.JButton();
        btnEnviarCmd = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DataLogger");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Puertos"));

        txtPuertoAtlantis.setText("777");
        txtPuertoAtlantis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPuertoAtlantisActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Equipos :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addComponent(txtPuertoAtlantis, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtPuertoAtlantis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("SERVER"));

        btnStop.setFont(new java.awt.Font("Tahoma", 1, 11));
        btnStop.setForeground(new java.awt.Color(255, 0, 0));
        btnStop.setText("Stop");
        btnStop.setEnabled(false);
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });

        btnIniciar.setFont(new java.awt.Font("Tahoma", 1, 11));
        btnIniciar.setForeground(new java.awt.Color(0, 0, 255));
        btnIniciar.setText("Iniciar");
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnStop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnIniciar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStop, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnEnviarCmd.setText("ENVIAR CMD");
        btnEnviarCmd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarCmdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEnviarCmd)
                        .addGap(47, 47, 47)))
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEnviarCmd, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        if (runServer()) {
            btnIniciar.setEnabled(false);
            btnStop.setEnabled(true);
            txtPuertoAtlantis.setEnabled(false);
            STOP_SERVERS = false;

            System.out.println("Puerto [" + txtPuertoAtlantis.getText() + "]");
        }
    }//GEN-LAST:event_btnIniciarActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed

        STOP_SERVERS = true;

        server.detenerServidores();
        btnIniciar.setEnabled(true);
        btnStop.setEnabled(false);
        txtPuertoAtlantis.setEnabled(true);

    }//GEN-LAST:event_btnStopActionPerformed

    private void txtPuertoAtlantisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPuertoAtlantisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPuertoAtlantisActionPerformed

    private void btnEnviarCmdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarCmdActionPerformed

        if (envCmd == null) {
            envCmd = new JDialog();
            envCmd.getContentPane().add(new pnEnviarComando(this));
            envCmd.setTitle("Enviar CMD");
            envCmd.setLocationRelativeTo(this);
            envCmd.setModalityType(ModalityType.APPLICATION_MODAL);
            envCmd.pack();
        }
        envCmd.setVisible(true);
    }//GEN-LAST:event_btnEnviarCmdActionPerformed

    public void cerrarDialog(){
        envCmd.setVisible(false);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws MalformedURLException {

        UtilidadesApp.cargaConfiguraciones();

        if (UtilidadesApp.getDebugMode()) {
            System.out.println("Inicio de Aplicación [" + new GregorianCalendar().getTime() + "]");
        } else {
            UtilidadesApp.logInfo.info("Inicio de Aplicación [" + new GregorianCalendar().getTime() + "]");
        }

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            java.awt.EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    new gui_server().setVisible(true);
                }
            });
        } catch (ClassNotFoundException ex) {
            UtilidadesApp.logError.info("Clase no encontrada: Look and Feel" + ex);
        } catch (InstantiationException ex) {
            UtilidadesApp.logError.info("No se pudo inicializar: Look and Feel" + ex);
        } catch (IllegalAccessException ex) {
            UtilidadesApp.logError.info("IllegalAccessException en Look and Feel" + ex);
        } catch (UnsupportedLookAndFeelException ex) {
            UtilidadesApp.logError.info("Look and Feel no soportado" + ex);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviarCmd;
    private javax.swing.JButton btnIniciar;
    private javax.swing.JButton btnStop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField txtPuertoAtlantis;
    // End of variables declaration//GEN-END:variables
    initServer server;

    public boolean runServer() {

        server = new initServer();

        int portAtlantis = Integer.valueOf(txtPuertoAtlantis.getText());
        boolean rest = server.runServers(portAtlantis);
        return rest;
    }
}
