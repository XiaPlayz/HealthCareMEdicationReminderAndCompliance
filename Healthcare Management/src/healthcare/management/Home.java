/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package healthcare.management;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;



/**
 *
 * @author Xia
 */
public class Home extends javax.swing.JFrame {

    public Home() {
        initComponents();
        this.setTitle("Home");
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Images/NotifLogo.png")).getImage());
        try {
            Connection();
            GetName();
            setCurrentDate();
            getCurrentTime();
            getMedicine();
        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setLocationRelativeTo(null);
    }
    
    
    Connection con;
    Statement st;
    ResultSet rs;
    PreparedStatement pst;
    SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
    
    private static final String DbName = "credentials";
    private static final String DbDriver = "com.mysql.cj.jdbc.Driver";
    private static final String DbUrl = "jdbc:mysql://localhost:3306/"+DbName;
    private static final String DbUsername = "root";
    private static final String DbPass = "";
    public static String Name = "";
    
    
    public static Date currentDateObj;
    public static String currentDate;
    
    public static DateTimeFormatter timeFormatter;
    public static LocalTime currentTime;
    public static String ActualTime;
    
public void setCurrentDate() {
    try {
        currentDateObj = new Date(); // Get the current system date
        currentDate = sdf.format(currentDateObj);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(
            this, 
            "Error setting the current date.", 
            "Error", 
            JOptionPane.ERROR_MESSAGE
        );
    }
    }
    
public void getCurrentTime() {
        // Get the current time
        currentTime = LocalTime.now();

        // Format the time in HH:mm:ss format
        timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        ActualTime = currentTime.format(timeFormatter);


    }
    

public void getMedicine() {
    String queryMed = "SELECT MedicineName, Time1, Time2, Time3, Instruction FROM medicineDetails WHERE Name = ? AND Duration = ?;";
    try {
        Connection();
        
        PreparedStatement pst = con.prepareStatement(queryMed);
        pst.setString(1, Name); // Ensure 'Name' is properly initialized
        pst.setString(2, currentDate); // Ensure 'currentDate' is properly initialized

        ResultSet rs = pst.executeQuery();

        DefaultTableModel table = (DefaultTableModel) MedsTable.getModel();
        table.setColumnIdentifiers(new Object[]{"Medicine Name", "Time", "Notes"});
        table.setRowCount(0); // Clear existing rows

        List<Object[]> rows = new ArrayList<>();

        while (rs.next()) {
            String medicineName = rs.getString("MedicineName");
            String Time1 = rs.getString("Time1");
            String Time2 = rs.getString("Time2");
            String Time3 = rs.getString("Time3");
            String instruction = rs.getString("Instruction");

            if (Time1 != null && !Time1.equalsIgnoreCase("null") && LocalTime.parse(Time1, timeFormatter).isAfter(LocalTime.now())) {
                rows.add(new Object[]{medicineName, Time1, instruction != null ? instruction : "No notes"});
            }

            if (Time2 != null && !Time2.equalsIgnoreCase("null") && LocalTime.parse(Time2, timeFormatter).isAfter(LocalTime.now())) {
                rows.add(new Object[]{medicineName, Time2, instruction != null ? instruction : "No notes"});
                //int MaxProgress++;
            }

            if (Time3 != null && !Time3.equalsIgnoreCase("null") && LocalTime.parse(Time3, timeFormatter).isAfter(LocalTime.now())) {
                rows.add(new Object[]{medicineName, Time3, instruction != null ? instruction : "No notes"});
            }
        }

        // Sort the rows based on time
        rows.sort((row1, row2) -> {
            String time1Str = (String) row1[1];
            String time2Str = (String) row2[1];
            return time1Str.compareTo(time2Str);
        });

        // Add sorted rows to the table
        SwingUtilities.invokeLater(() -> {
            for (Object[] row : rows) {
                table.addRow(row);
            }
            MedsTable.revalidate();
            MedsTable.repaint();
        });

        // Disable editing for all cells (entire table)
        MedsTable.setDefaultEditor(Object.class, null);
        
        for (int i = 0; i < MedsTable.getColumnModel().getColumnCount(); i++) {
            MedsTable.getColumnModel().getColumn(i).setResizable(false);
        }
        // Disable column reordering (make columns non-draggable)
        MedsTable.getTableHeader().setReorderingAllowed(false);

        // Close resources
        rs.close();
        pst.close();
        con.close();

    } catch (SQLException ex) {
        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        ex.printStackTrace();
    }
}



public void Connection() throws SQLException {
    try {
        Class.forName(DbDriver);
        con = DriverManager.getConnection(DbUrl, DbUsername, DbPass);
        st = con.createStatement();
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    
    
public void GetName(){
    String queryLog = "SELECT HomeName FROM currentuser;";

    try {
        pst = con.prepareStatement(queryLog);
        ResultSet rs = pst.executeQuery();

        // Check if there is a result
        if (rs.next()) {
            // Retrieve HomeUser and store it in the static variable
            Name = rs.getString(1);
        }

        // Update the JLabel with the retrieved name
        HomeName.setText("Hello, " + Name + "!");
    // Repaint JLabel to reflect changes

        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }

    } 
    





    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        HomeName = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        MedsTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        HomeName.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        HomeName.setForeground(new java.awt.Color(0, 0, 0));
        HomeName.setText("Hello, " + Name + "!");
        jPanel1.add(HomeName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 340, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Home.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -1, -1, 290));

        jLabel3.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Today");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, -1));

        jButton1.setBackground(new java.awt.Color(34, 60, 83));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("ADD MED");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 310, 90, 30));

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Active HomeLogo.png"))); // NOI18N
        jButton7.setBorder(null);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 590, -1, -1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Reminder.png"))); // NOI18N
        jButton2.setBorder(null);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 590, -1, -1));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Tracking.png"))); // NOI18N
        jButton3.setBorder(null);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 590, -1, -1));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Logs.png"))); // NOI18N
        jButton4.setBorder(null);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 590, -1, -1));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Account.png"))); // NOI18N
        jButton5.setBorder(null);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 590, -1, -1));

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        MedsTable.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        MedsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        MedsTable.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                MedsTableMouseWheelMoved(evt);
            }
        });
        jScrollPane3.setViewportView(MedsTable);

        jPanel4.add(jScrollPane3);

        jScrollPane1.setViewportView(jPanel4);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 340, 230));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 380, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new Reminder(Name, null,null,null).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        new Home().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new Reminder(Name,null,null,null).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        new Adherence(Name).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        new AccountPage(Name).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        new Logging(Name).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void MedsTableMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_MedsTableMouseWheelMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_MedsTableMouseWheelMoved

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
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel HomeName;
    private javax.swing.JTable MedsTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
