/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package healthcare.management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class AlarmSettings extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public AlarmSettings() {
        this.setTitle("Alarm Settings");
        try {
            Connection();
        } catch (SQLException ex) {
            Logger.getLogger(AlarmSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Images/NotifLogo.png")).getImage());
        ButtonSetup();
        this.setLocationRelativeTo(null);
        this.setTitle("Alarm Settings");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    
    
    
    
    Connection con;
    Statement st;
    PreparedStatement pst;
    
    
    private static final String DbName = "credentials";
    private static final String DbDriver = "com.mysql.cj.jdbc.Driver";
    private static final String DbUrl = "jdbc:mysql://localhost:3306/"+DbName;
    private static final String DbUsername = "root";
    private static final String DbPass = "";
    public static String Switch;
    
    
    
    
    public void Connection() throws SQLException{
        try {
            Class.forName(DbDriver);
            con = DriverManager.getConnection(DbUrl, DbUsername, DbPass);
            st = con.createStatement();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getAlarmStatus() {
        String queryAlarm = "SELECT * FROM alarmsettings;";
        try {
            pst = con.prepareStatement(queryAlarm);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String AlarmSwitch = rs.getString("Switch");
                // Use .equals() for string comparison
                if ("On".equals(AlarmSwitch)) {
                    Switch = "On";
                } else {
                    Switch = "Off";
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlarmSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ButtonSetup() {
        getAlarmStatus();
    
        if ("On".equals(Switch)) {
            Bon.setSelected(true);
            Boff.setSelected(false);
        } else {
            Boff.setSelected(true);
            Bon.setSelected(false);
        }
    }

    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        Bon = new javax.swing.JRadioButton();
        Boff = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(180, 196, 208));

        Bon.setBackground(new java.awt.Color(180, 196, 208));
        buttonGroup1.add(Bon);
        Bon.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Bon.setText("On");
        Bon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BonActionPerformed(evt);
            }
        });

        Boff.setBackground(new java.awt.Color(180, 196, 208));
        buttonGroup1.add(Boff);
        Boff.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Boff.setText("Off");
        Boff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoffActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(Bon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addComponent(Boff)
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Bon)
                    .addComponent(Boff))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(34, 59, 83));
        jLabel1.setText("Alarm Settings");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BonActionPerformed
        String queryUpdateAlarm = "UPDATE alarmsettings set SWITCH = 'On' WHERE SWITCH = 'Off';";
        if ("Off".equals(Switch)) {
            try {
            st.execute(queryUpdateAlarm);
            // Optionally, update the local Switch variable if needed
            Switch = "On";  // Assuming Switch is a class-level variable
            
            // Optionally show a confirmation message
            JOptionPane.showMessageDialog(null, "Alarm is turned On.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(AlarmSettings.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error updating alarm status.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        }
    }//GEN-LAST:event_BonActionPerformed

    private void BoffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoffActionPerformed
        String queryUpdateAlarm = "UPDATE alarmsettings set SWITCH = 'Off' WHERE SWITCH = 'On';";
        if ("On".equals(Switch)) {
            try {
            st.execute(queryUpdateAlarm);
            // Optionally, update the local Switch variable if needed
            Switch = "Off";  // Assuming Switch is a class-level variable
            
            // Optionally show a confirmation message
            JOptionPane.showMessageDialog(null, "Alarm is turned Off.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(AlarmSettings.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error updating alarm status.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        }
    }//GEN-LAST:event_BoffActionPerformed

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
            java.util.logging.Logger.getLogger(AlarmSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AlarmSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AlarmSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlarmSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AlarmSettings().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Boff;
    private javax.swing.JRadioButton Bon;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
