/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingview;

import javax.swing.JOptionPane;

import steamservermanager.SteamServerManager;
import steamservermanager.exceptions.ServerLocalNameDuplicatedException;
import steamservermanager.exceptions.ServerLocalNameIsEmptyException;
import steamservermanager.exceptions.SteamIDNotFoundException;
import steamservermanager.utils.SteamAPIUtils;

/**
 *
 * @author asu
 */
public class NewServerFrame extends javax.swing.JFrame {

    /**
     * Creates new form NewServerFrame
     */
    private SteamServerManager manager;
    
    public NewServerFrame(SteamServerManager manager) {
        initComponents();
        setLocationRelativeTo(null);
        this.manager = manager;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextFieldAppId = new javax.swing.JTextField();
        jTextFieldLocalName = new javax.swing.JTextField();
        jTextFieldStartScript = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButtonSave = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jTextFieldGameName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldServerName = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New Server");
        setResizable(false);

        jTextFieldAppId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldAppIdFocusLost(evt);
            }
        });
        jTextFieldAppId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAppIdActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        jLabel1.setText("AppID");

        jLabel2.setText("Local Name");

        jLabel3.setText("Start Script");

        jButtonSave.setText("Save");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jTextFieldGameName.setEnabled(false);

        jLabel4.setText("Game");

        jLabel5.setText("Server Name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldStartScript)
                    .addComponent(jTextFieldLocalName, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFieldGameName)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldAppId, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5))
                        .addGap(0, 390, Short.MAX_VALUE))
                    .addComponent(jTextFieldServerName))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldAppId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldGameName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldLocalName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldServerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldStartScript, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSave)
                    .addComponent(jButtonCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldAppIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAppIdActionPerformed
        
        
    }//GEN-LAST:event_jTextFieldAppIdActionPerformed

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        
        Integer gameId;
        
        try{
            gameId = Integer.parseInt(jTextFieldAppId.getText());

        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Invalid AppID.", "ERROR!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String localName = jTextFieldLocalName.getText(); 
        String serverName = jTextFieldServerName.getText();
        String startScript = jTextFieldStartScript.getText();
        
        try {
            manager.create(gameId, localName, serverName, startScript);
            
        } catch (ServerLocalNameDuplicatedException ex) {
            JOptionPane.showMessageDialog(null, "Invalid Server Name id! \n Esse nome já está sendo utilizado", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (SteamIDNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "The Steam ID provided could not be found.", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (ServerLocalNameIsEmptyException ex) {
            JOptionPane.showMessageDialog(null, "Local name cannot be empty.", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        this.dispose();
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jTextFieldAppIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldAppIdFocusLost
        try{
            Integer gameId = Integer.parseInt(jTextFieldAppId.getText());
        
            String gameName = SteamAPIUtils.getGameNameBySteamId(gameId);

            jTextFieldGameName.setText(gameName);

        } catch(Exception ex){
            jTextFieldGameName.setText("");
            JOptionPane.showMessageDialog(null, "Invalid AppID.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jTextFieldAppIdFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextFieldAppId;
    private javax.swing.JTextField jTextFieldGameName;
    private javax.swing.JTextField jTextFieldLocalName;
    private javax.swing.JTextField jTextFieldServerName;
    private javax.swing.JTextField jTextFieldStartScript;
    // End of variables declaration//GEN-END:variables
}
