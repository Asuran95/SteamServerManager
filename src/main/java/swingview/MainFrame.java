/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamservermanager.view;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import steamservermanager.Status;
import steamservermanager.SteamServerManager;
import steamservermanager.exceptions.StartServerException;
import steamservermanager.interfaces.SteamServerManagerListener;
import steamservermanager.interfaces.serverrunner.ServerProperties;
import steamservermanager.models.ServerGame;


public class MainFrame extends javax.swing.JFrame {

    private SteamServerManager steamServerManager;
    private List<ServerGameConsole> serverGameConsoleList = new ArrayList<>();
    private List<ServerGame> serverGameLibrary = new ArrayList<>();
    private ServerGame serverGameAtual;
    
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        setupTableRightClick();
        setLocationRelativeTo(null);
        
        DefaultCaret caret = (DefaultCaret) jTextAreaSteamCMD.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);  
        setLocationRelativeTo(null);
    }
    
    private void setupTableRightClick() {
        jTableLibrary.addMouseListener( new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                openPopupMenu(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                openPopupMenu(e);
            }
            
            private void openPopupMenu(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    JTable source = (JTable) e.getSource();
                    int row = source.rowAtPoint(e.getPoint());
                    int column = source.columnAtPoint(e.getPoint());

                    if (!source.isRowSelected(row))
                        source.changeSelection(row, column, false, false);

                    JPopupMenu popup = new JPopupMenu("Actions");
              
                    Status status = getSelectedServer().getStatus();
                    
                    JMenuItem startItem = null;
                    JMenuItem stopItem = null;
                    JMenuItem consoleItem = null;
                    JMenuItem editItem = null;
                    JMenuItem updateItem = null;
                    JMenuItem removeItem = null;
                    
                    if (status == Status.RUNNING || status == Status.STOPPED) {
                        if (status == Status.RUNNING) {
                            consoleItem = new JMenuItem("Open Console");
                            
                            consoleItem.setFont(consoleItem.getFont().deriveFont(Font.BOLD));
                            
                            consoleItem.addActionListener((evt) -> {
                                openConsoleSelectedServer();
                            });
                            
                            popup.add(consoleItem);
                            
                            stopItem = new JMenuItem("Stop");
                            
                            stopItem.addActionListener((evt) -> {
                                stopSelectedServer();
                            });
                            
                            popup.add(stopItem);
                            
                        } else {
                            startItem = new JMenuItem("Start");
                            
                            startItem.addActionListener((evt) -> {
                                startSelectedServer();
                            });
                            
                            popup.add(startItem);
                            
                            editItem = new JMenuItem("Edit");
                            
                            editItem.addActionListener((evt) -> {
                                editSelectedServer();
                            });
                            
                            popup.add(editItem);
                            
                            removeItem = new JMenuItem("Remove");
                            
                            removeItem.addActionListener((evt) -> {
                                removeSelectedServer();
                            });

                            popup.add(removeItem);
                        }

                        updateItem = new JMenuItem("Update");
                        
                        updateItem.addActionListener((evt) -> {
                            updateSelectedServer();
                        });
                        
                        popup.add(updateItem);
                    }
                    
                    if (startItem != null || updateItem != null || removeItem != null) {
                        popup.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jButtonOpenLibrary = new javax.swing.JButton();
        jLabelLocalLibrary = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableLibrary = new javax.swing.JTable();
        jButtonNewServer = new javax.swing.JButton();
        jProgressBarUpdate = new javax.swing.JProgressBar();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaSteamCMD = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButtonOpenLibrary.setText("Open");
        jButtonOpenLibrary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpenLibraryActionPerformed(evt);
            }
        });

        jLabelLocalLibrary.setText("No directory selected!");

        jTableLibrary.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Server Name", "Game", "ID", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableLibrary.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTableLibrary);
        if (jTableLibrary.getColumnModel().getColumnCount() > 0) {
            jTableLibrary.getColumnModel().getColumn(0).setResizable(false);
            jTableLibrary.getColumnModel().getColumn(1).setResizable(false);
            jTableLibrary.getColumnModel().getColumn(2).setMinWidth(60);
            jTableLibrary.getColumnModel().getColumn(2).setPreferredWidth(60);
            jTableLibrary.getColumnModel().getColumn(2).setMaxWidth(60);
            jTableLibrary.getColumnModel().getColumn(3).setMinWidth(120);
            jTableLibrary.getColumnModel().getColumn(3).setPreferredWidth(120);
            jTableLibrary.getColumnModel().getColumn(3).setMaxWidth(120);
        }

        jButtonNewServer.setText("New");
        jButtonNewServer.setEnabled(false);
        jButtonNewServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewServerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 901, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButtonOpenLibrary)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelLocalLibrary)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jProgressBarUpdate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonNewServer, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addComponent(jSeparator1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOpenLibrary)
                    .addComponent(jLabelLocalLibrary))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonNewServer, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBarUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        jTabbedPane1.addTab("Server Manager", jPanel1);

        jTextAreaSteamCMD.setEditable(false);
        jTextAreaSteamCMD.setBackground(new java.awt.Color(80, 80, 80));
        jTextAreaSteamCMD.setColumns(20);
        jTextAreaSteamCMD.setForeground(new java.awt.Color(102, 204, 0));
        jTextAreaSteamCMD.setRows(5);
        jScrollPane2.setViewportView(jTextAreaSteamCMD);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 901, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("SteamCMD Console", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonOpenLibraryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpenLibraryActionPerformed
        JFileChooser chooser;
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        chooser.setAcceptAllFileFilterUsed(false);
           
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            
            steamServerManager = new SteamServerManager(chooser.getSelectedFile().toString());
            
            steamServerManager.setListener(new SteamServerManagerListenerImpl());
            
            jLabelLocalLibrary.setText(chooser.getSelectedFile().toString());
            
            jButtonNewServer.setEnabled(true);
        }
    }//GEN-LAST:event_jButtonOpenLibraryActionPerformed

    private void jButtonNewServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewServerActionPerformed
        NewServerFrame newServerFrame = new NewServerFrame(steamServerManager);
         
        newServerFrame.setVisible(true);  
    }//GEN-LAST:event_jButtonNewServerActionPerformed

    private void startSelectedServer() {
        int jTableIndexSelected = jTableLibrary.getSelectedRow();
        
        if (jTableIndexSelected >= 0){
            try {
                ServerGame serverGameSelected = getSelectedServer();
                
                ServerGameConsole serverGameConsoleFound = getServerConsole(serverGameSelected);
                    
                if (serverGameConsoleFound == null){
                    ServerProperties serverProperties = steamServerManager.startServer(serverGameSelected);
                
                    ServerGameConsole serverGameConsole = new ServerGameConsole(serverProperties);

                    serverGameConsoleList.add(serverGameConsole);
                    
                } else {
                    if(!serverGameConsoleFound.getServerProperties().isRunning()){
                        ServerProperties serverProperties = steamServerManager.startServer(serverGameSelected);
                        serverGameConsoleFound.setServerProperties(serverProperties);
                    }
                }
                
            } catch (StartServerException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private ServerGameConsole getServerConsole(ServerGame selectedServer){
                
        for (ServerGameConsole serverGameConsole : serverGameConsoleList){
            if (serverGameConsole.getServerProperties().getServerGame().equals(selectedServer)){
                return serverGameConsole;
            }
        }
        
        return null;
    }
    
    private void updateSelectedServer() {
        ServerGame selectedServer = getSelectedServer();
        
        steamServerManager.updateServerGame(selectedServer);
        
        ServerGameConsole serverConsole = getServerConsole(selectedServer);
        
        if (serverConsole != null){
            serverConsole.setVisible(false);
        }
    }
    
    private void stopSelectedServer(){
        
        ServerGameConsole serverConsole = getServerConsole(getSelectedServer());
        
        if (serverConsole != null){
            serverConsole.forceStop();
            serverConsole.setVisible(false);
        }
    }
    
    private void removeSelectedServer() {
        
        int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this server?","Warning", JOptionPane.YES_NO_OPTION);
        
        if (dialogResult == JOptionPane.YES_OPTION){
            
        }  
    }
    
    private void openConsoleSelectedServer() {
        
        ServerGameConsole serverConsole = getServerConsole(getSelectedServer());
        
        if(serverConsole != null){
            serverConsole.setVisible(true);
        }
    }
    
    private void editSelectedServer(){
        
        EditServerGameFrame editServerGameFrame = new EditServerGameFrame(getSelectedServer());
        
        editServerGameFrame.setVisible(true);
    }

    private ServerGame getSelectedServer() {
        int jTableIndexSelected = jTableLibrary.getSelectedRow();
        
        if (jTableIndexSelected != -1) {
            return serverGameLibrary.get(jTableIndexSelected);
        }
        
        return null;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonNewServer;
    private javax.swing.JButton jButtonOpenLibrary;
    private javax.swing.JLabel jLabelLocalLibrary;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JProgressBar jProgressBarUpdate;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableLibrary;
    private javax.swing.JTextArea jTextAreaSteamCMD;
    // End of variables declaration//GEN-END:variables
    
    
    public synchronized void updateJTableLibrary(){
        serverGameLibrary = steamServerManager.getLibrary();
        
        DefaultTableModel model = (DefaultTableModel) jTableLibrary.getModel();
        
        model.setRowCount(0);
        
        for(ServerGame s : serverGameLibrary){
            System.out.println(s.getServerName());
            
            String[] linha = { s.getServerName(), "", s.getAppID()+"", s.getStatus().toString() };
        
            model.addRow(linha);
        }      
    }
    
    class SteamServerManagerListenerImpl implements SteamServerManagerListener{

        private CircularFifoQueue<String> mensagemFifo = new CircularFifoQueue<>(500);
        
        @Override
        public void onSteamCMDStdOut(String out) {
            mensagemFifo.add(out + "\n");
            
            StringBuilder stringBuilder = new StringBuilder();
            
            for(String msgFifo : mensagemFifo){
                stringBuilder.append(msgFifo);
            }

            jTextAreaSteamCMD.setText(stringBuilder.toString());  
        }

        @Override
        public void onUpdateServerStatus() {
            updateJTableLibrary();
        }

        @Override
        public void onReady() {
            updateJTableLibrary();
        }

        @Override
        public void onStatusSteamCMD(String status, double pctUpdate) {
            
            jProgressBarUpdate.setStringPainted(true);
            
            if(serverGameAtual != null){
                jProgressBarUpdate.setString(serverGameAtual.getServerName() + " - " + status + ": " + pctUpdate + "%");
            } else {
                jProgressBarUpdate.setString("");
            }
             
            jProgressBarUpdate.setMaximum(100);
            jProgressBarUpdate.setValue((int) pctUpdate);
        }

        @Override
        public void onUpdateServer(ServerGame serverGame) {
            serverGameAtual = serverGame;
        }

        @Override
        public void onCompleteUpdateServer() {
            serverGameAtual = null;
            jProgressBarUpdate.setString("");
            jProgressBarUpdate.setValue(0);
        }

    } 
}
