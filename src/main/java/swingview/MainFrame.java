/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingview;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;

import steamservermanager.SteamServerManager;
import steamservermanager.SteamServerManagerBuilder;
import steamservermanager.dtos.DiscordBotDTO;
import steamservermanager.dtos.ServerGameDTO;
import steamservermanager.listeners.SteamServerManagerListener;
import steamservermanager.models.enums.ServerStatus;
import steamservermanager.serverrunner.interfaces.ServerProperties;


public class MainFrame extends javax.swing.JFrame {

    private SteamServerManager steamServerManager;
    private List<ServerGameConsole> serverGameConsoleList = new ArrayList<>();
    private List<ServerGameDTO> serverGameLibrary = new ArrayList<>();
    
    
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
              
                    ServerStatus status = getSelectedServer().getStatus();
                    
                    JMenuItem startItem = null;
                    JMenuItem stopItem = null;
                    JMenuItem consoleItem = null;
                    JMenuItem editItem = null;
                    JMenuItem updateItem = null;
                    JMenuItem removeItem = null;
                    
                    if (status == ServerStatus.RUNNING || status == ServerStatus.STOPPED) {
                        if (status == ServerStatus.RUNNING) {
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
        jPanelSettings = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldDiscordBotPrefix = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldDiscordBotOwnerUserId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButtonSaveDiscordBot = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldStatusBotDiscord = new javax.swing.JTextField();
        jToggleButtonStartDiscordBot = new javax.swing.JToggleButton();
        jPasswordFieldDiscordBotToken = new javax.swing.JPasswordField();
        jPanel5 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Steam Server Manager");
        setMaximumSize(new java.awt.Dimension(1020, 680));
        setMinimumSize(new java.awt.Dimension(1020, 680));
        setUndecorated(true);
        setResizable(false);

        jButtonOpenLibrary.setText("Open");
        jButtonOpenLibrary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpenLibraryActionPerformed(evt);
            }
        });

        jLabelLocalLibrary.setText("No directory selected");

        jTableLibrary.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Local Name", "Server Name", "Game", "AppID", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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
            jTableLibrary.getColumnModel().getColumn(2).setResizable(false);
            jTableLibrary.getColumnModel().getColumn(3).setMinWidth(80);
            jTableLibrary.getColumnModel().getColumn(3).setPreferredWidth(80);
            jTableLibrary.getColumnModel().getColumn(3).setMaxWidth(80);
            jTableLibrary.getColumnModel().getColumn(4).setMinWidth(120);
            jTableLibrary.getColumnModel().getColumn(4).setPreferredWidth(120);
            jTableLibrary.getColumnModel().getColumn(4).setMaxWidth(120);
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
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBarUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        jTabbedPane1.addTab("Server Manager", jPanel1);

        jTextAreaSteamCMD.setEditable(false);
        jTextAreaSteamCMD.setBackground(new java.awt.Color(1, 1, 1));
        jTextAreaSteamCMD.setColumns(20);
        jTextAreaSteamCMD.setFont(new java.awt.Font("Hack", 1, 13)); // NOI18N
        jTextAreaSteamCMD.setForeground(new java.awt.Color(87, 149, 254));
        jTextAreaSteamCMD.setRows(5);
        jScrollPane2.setViewportView(jTextAreaSteamCMD);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("SteamCMD Console", jPanel2);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("DiscordBOT"));

        jLabel1.setText("Prefix");

        jTextFieldDiscordBotPrefix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldDiscordBotPrefixActionPerformed(evt);
            }
        });

        jLabel2.setText("Owner UserID");

        jLabel3.setText("Token");

        jButtonSaveDiscordBot.setText("Save");
        jButtonSaveDiscordBot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveDiscordBotActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Status DiscordBOT"));

        jLabel4.setFont(new java.awt.Font("Noto Sans", 1, 13)); // NOI18N
        jLabel4.setText("Status");

        jTextFieldStatusBotDiscord.setEditable(false);
        jTextFieldStatusBotDiscord.setFont(new java.awt.Font("Droid Sans Mono", 1, 13)); // NOI18N

        jToggleButtonStartDiscordBot.setText("Start");
        jToggleButtonStartDiscordBot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonStartDiscordBotActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextFieldStatusBotDiscord)))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jToggleButtonStartDiscordBot, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(104, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldStatusBotDiscord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButtonStartDiscordBot)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPasswordFieldDiscordBotToken)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jTextFieldDiscordBotPrefix, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                            .addComponent(jLabel3)
                            .addComponent(jButtonSaveDiscordBot, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldDiscordBotOwnerUserId))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldDiscordBotPrefix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldDiscordBotOwnerUserId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordFieldDiscordBotToken, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSaveDiscordBot))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Misc"));

        jCheckBox1.setText("Auto restart server when crash");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jCheckBox2.setText("Auto start DiscordBOT");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox2)
                    .addComponent(jCheckBox1))
                .addContainerGap(164, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox2)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelSettingsLayout = new javax.swing.GroupLayout(jPanelSettings);
        jPanelSettings.setLayout(jPanelSettingsLayout);
        jPanelSettingsLayout.setHorizontalGroup(
            jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSettingsLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelSettingsLayout.setVerticalGroup(
            jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSettingsLayout.createSequentialGroup()
                .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 400, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Settings", jPanelSettings);

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
    	LoadingFrame loadingFrame = new LoadingFrame();
        loadingFrame.setVisible(true);
        
        JFileChooser chooser;
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        chooser.setAcceptAllFileFilterUsed(false);
           
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        	SteamServerManagerBuilder steamServerManagerBuilder = 
        			new SteamServerManagerBuilder().setListener(new SteamServerManagerListenerImpl())
        											.setLocalLibrary(chooser.getSelectedFile().toString());
            
            steamServerManager = steamServerManagerBuilder.build();
            steamServerManager.startManager();

            DiscordBotDTO discordBot = steamServerManager.getDiscordBot();
            
            if (discordBot != null){
                jTextFieldDiscordBotPrefix.setText(discordBot.getPrefix());
                jTextFieldDiscordBotOwnerUserId.setText(discordBot.getOwnerUserId()+"");
                jPasswordFieldDiscordBotToken.setText(discordBot.getToken());
            }

            jLabelLocalLibrary.setText(chooser.getSelectedFile().toString());
            
            jButtonNewServer.setEnabled(true);
            jButtonOpenLibrary.setEnabled(false);
            
            updateJTableLibrary();
            jPanelSettings.setEnabled(true);
            
        }
        
        loadingFrame.setVisible(false);
    }//GEN-LAST:event_jButtonOpenLibraryActionPerformed

    private void jButtonNewServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewServerActionPerformed
        NewServerFrame newServerFrame = new NewServerFrame(steamServerManager);
         
        newServerFrame.setVisible(true);  
    }//GEN-LAST:event_jButtonNewServerActionPerformed

    private void jTextFieldDiscordBotPrefixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDiscordBotPrefixActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldDiscordBotPrefixActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jToggleButtonStartDiscordBotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonStartDiscordBotActionPerformed

        if (jToggleButtonStartDiscordBot.getModel().isSelected()){
        	jToggleButtonStartDiscordBot.setEnabled(false);
        	jToggleButtonStartDiscordBot.setSelected(false);
            steamServerManager.startDiscordBot();
        } else {
        	steamServerManager.stopDiscordBot();
        }
    }//GEN-LAST:event_jToggleButtonStartDiscordBotActionPerformed

    private void jButtonSaveDiscordBotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveDiscordBotActionPerformed
        
        String prefix = jTextFieldDiscordBotPrefix.getText();
        String ownerUserIdString = jTextFieldDiscordBotOwnerUserId.getText();
        String token = new String(jPasswordFieldDiscordBotToken.getPassword());
        
        Long ownerUserId = null;
        
        try{
            ownerUserId = Long.valueOf(ownerUserIdString);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid Owner User ID.", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        DiscordBotDTO discordbotDTO = new DiscordBotDTO();
        discordbotDTO.setPrefix(prefix);
        discordbotDTO.setOwnerUserId(ownerUserId);
        discordbotDTO.setToken(token);
        
        steamServerManager.setDiscordBot(discordbotDTO);
    }//GEN-LAST:event_jButtonSaveDiscordBotActionPerformed

    private void startSelectedServer() {
        int jTableIndexSelected = jTableLibrary.getSelectedRow();
        
        if (jTableIndexSelected >= 0){
        	ServerGameDTO serverGameSelected = getSelectedServer();
            
            ServerGameConsole serverGameConsoleFound = getServerConsole(serverGameSelected);
                
            if (serverGameConsoleFound == null){
                ServerProperties serverProperties = steamServerManager.startServerGame(serverGameSelected);
            
                ServerGameConsole serverGameConsole = new ServerGameConsole(serverProperties);

                serverGameConsoleList.add(serverGameConsole);
                
            } else {
                if(!serverGameConsoleFound.getServerProperties().isRunning()){
                    ServerProperties serverProperties = steamServerManager.startServerGame(serverGameSelected);
                    serverGameConsoleFound.setServerProperties(serverProperties);
                }
            }
        }
    }
    
    private ServerGameConsole getServerConsole(ServerGameDTO selectedServer){
                
        for (ServerGameConsole serverGameConsole : serverGameConsoleList){
            if (serverGameConsole.getServerProperties().getServerGame().getIdServerGame().equals(selectedServer.getIdServerGame())){
                return serverGameConsole;
            }
        }
        
        return null;
    }
    
    private void updateSelectedServer() {
    	ServerGameDTO selectedServer = getSelectedServer();
        
        steamServerManager.startUpdateServerGame(selectedServer);
        
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
        } else {
        	startSelectedServer();
        	stopSelectedServer();
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
        } else {
        	startSelectedServer();
        	openConsoleSelectedServer();
        }
    }
    
    private void editSelectedServer(){
        EditServerGameFrame editServerGameFrame = new EditServerGameFrame(getSelectedServer(), steamServerManager);
        
        editServerGameFrame.setVisible(true);
    }

    private ServerGameDTO getSelectedServer() {
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
        	AcrylLookAndFeel.setTheme("Large-Font");
        	
        	UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");

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
    private javax.swing.JButton jButtonSaveDiscordBot;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelLocalLibrary;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelSettings;
    private javax.swing.JPasswordField jPasswordFieldDiscordBotToken;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JProgressBar jProgressBarUpdate;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableLibrary;
    private javax.swing.JTextArea jTextAreaSteamCMD;
    private javax.swing.JTextField jTextFieldDiscordBotOwnerUserId;
    private javax.swing.JTextField jTextFieldDiscordBotPrefix;
    private javax.swing.JTextField jTextFieldStatusBotDiscord;
    private javax.swing.JToggleButton jToggleButtonStartDiscordBot;
    // End of variables declaration//GEN-END:variables
    
    
    public synchronized void updateJTableLibrary(){
    	SwingUtilities.invokeLater(() -> {
			serverGameLibrary = steamServerManager.getServerList();
	        
	        DefaultTableModel model = (DefaultTableModel) jTableLibrary.getModel();
	        
	        model.setRowCount(0);
	        
	        for (ServerGameDTO s : serverGameLibrary){
	            String[] linha = { s.getLocalName(), s.getServerName(), s.getGameName(), s.getAppID()+ "", s.getStatus().toString() };
	        
	            model.addRow(linha);
	        }
		});       
    }
    
    class SteamServerManagerListenerImpl implements SteamServerManagerListener{

        private CircularFifoQueue<String> mensagemFifo = new CircularFifoQueue<>(500);
        private ServerGameDTO serverGameAtual;
        
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
        public void onServerGameChanged(ServerGameDTO serverGame) {
            updateJTableLibrary();
        }

        @Override
        public void onStatusSteamCMD(String status, double pctUpdate) {
            
            jProgressBarUpdate.setStringPainted(true);
            
            if(serverGameAtual != null){
            	
            	if (serverGameAtual.getServerName() != null && !serverGameAtual.getServerName().isEmpty()) {
            		jProgressBarUpdate.setString(serverGameAtual.getServerName() + " - " + status + ": " + pctUpdate + "%");
            	} else {
            		jProgressBarUpdate.setString(serverGameAtual.getLocalName() + " - " + status + ": " + pctUpdate + "%");
            	}

            } else {
                jProgressBarUpdate.setString("");
            }
             
            jProgressBarUpdate.setMaximum(100);
            jProgressBarUpdate.setValue((int) pctUpdate);
        }

        @Override
        public void onStartUpdateServerGame(ServerGameDTO serverGame) {
            serverGameAtual = serverGame;
        }

        @Override
        public void onCompletedUpdateServerGame(ServerGameDTO serverGame) {
            serverGameAtual = null;
            jProgressBarUpdate.setString("");
            jProgressBarUpdate.setValue(0);
        }

		@Override
		public void onDiscordBotChangedStatus(String status) {
			jTextFieldStatusBotDiscord.setText(status);
		}

		@Override
		public void onDiscordBotStarted() {
			jToggleButtonStartDiscordBot.setSelected(true);
			jToggleButtonStartDiscordBot.setEnabled(true);
			jToggleButtonStartDiscordBot.setText("Stop");
		}

		@Override
		public void onDiscordBotStopped() {
			jToggleButtonStartDiscordBot.setEnabled(true);
			jToggleButtonStartDiscordBot.setSelected(false);
			jToggleButtonStartDiscordBot.setText("Start");
		}
    } 
}
