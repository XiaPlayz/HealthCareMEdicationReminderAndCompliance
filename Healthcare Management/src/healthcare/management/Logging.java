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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Xia
 */
public class Logging extends javax.swing.JFrame {

    public Logging(String getName) {
        this.Name = getName;
        this.setTitle("Logs");
        initComponents();
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Images/NotifLogo.png")).getImage());
        getCurrentTime();
        setCurrentDate();
        getMedicine();
        getLogs();
        this.setLocationRelativeTo(null);
    }

    
    private static final String DbName = "credentials";
    private static final String DbDriver = "com.mysql.cj.jdbc.Driver";
    private static final String DbUrl = "jdbc:mysql://localhost:3306/"+DbName;
    private static final String DbUsername = "root";
    private static final String DbPass = "";
    public static String Name;
    
    
    public static Date currentDateObj;
    public static String currentDate;
    public static DateTimeFormatter timeFormatter;
    public static LocalTime currentTime;
    public static String ActualTime;
    
    Connection con;
    Statement st;
    ResultSet rs;
    PreparedStatement pst;
    SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
    
    public void Connection() throws SQLException {
    try {
        Class.forName(DbDriver);
        con = DriverManager.getConnection(DbUrl, DbUsername, DbPass);
        st = con.createStatement();
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    
    
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
    
    public void getMedicine() {
    String queryMed = "SELECT MedicineName, Time1, Time2, Time3, Dosage, Strength, Duration, Instruction FROM medicineDetails WHERE Name = ?;";
    try {
        // Ensure connection is established
        Connection();

        // Prepare the statement
        PreparedStatement pst = con.prepareStatement(queryMed);
        pst.setString(1, Name); // Ensure 'Name' is properly initialized

        ResultSet rs = pst.executeQuery();

        if (!rs.isBeforeFirst()) {

            return;
        }


        // Get the table model and set its structure
        DefaultTableModel table = (DefaultTableModel) MedsTable.getModel();
        table.setColumnIdentifiers(new Object[]{"Medicine Name", "Time", "Dosage", "Strength", "Duration", "Notes"});
        table.setRowCount(0);

        // Debug: Print number of rows before adding data

        // Initialize rows list if necessary
        List<Object[]> rows = new ArrayList<>();

        // Loop through the result set
        while (rs.next()) {
            // Fetch the data from ResultSet
            String medicineName = rs.getString("MedicineName");
            String time1 = rs.getString("Time1");
            String time2 = rs.getString("Time2");
            String time3 = rs.getString("Time3");
            String dosage = rs.getString("Dosage");
            String strength = rs.getString("Strength");
            String duration = rs.getString("Duration");
            String instruction = rs.getString("Instruction");


            // Assuming currentDate is in "MMMM dd, yyyy" format, as set by your setCurrentDate() method
            LocalDate currentDateLocal = LocalDate.parse(currentDate, DateTimeFormatter.ofPattern("MMMM dd, yyyy"));

            // In the loop, compare durationDate with currentDateLocal
            if (((time1 != null && !time1.equalsIgnoreCase("null") && duration != null && !duration.equalsIgnoreCase("null"))
                && LocalTime.parse(time1, timeFormatter).isAfter(LocalTime.now()))
                && currentDateLocal.isEqual(LocalDate.parse(duration, DateTimeFormatter.ofPattern("MMMM dd, yyyy")))
                || currentDateLocal.isBefore(LocalDate.parse(duration, DateTimeFormatter.ofPattern("MMMM dd, yyyy")))){
                rows.add(new Object[]{medicineName, time1, dosage, strength, duration, instruction != null ? instruction : "No notes"});
            }



            // Check for time2
            if (((time2 != null && !time2.equalsIgnoreCase("null") && duration != null && !duration.equalsIgnoreCase("null")) 
                && LocalTime.parse(time2, timeFormatter).isAfter(LocalTime.now())) 
                &&  (currentDateLocal.isEqual(LocalDate.parse(duration, DateTimeFormatter.ofPattern("MMMM dd, yyyy")))
                ||  currentDateLocal.isBefore(LocalDate.parse(duration, DateTimeFormatter.ofPattern("MMMM dd, yyyy"))))) {
                    rows.add(new Object[]{medicineName, time2, dosage, strength, duration, instruction != null ? instruction : "No notes"});
            }

            if (((time3 != null && !time3.equalsIgnoreCase("null") && duration != null && !duration.equalsIgnoreCase("null")) 
                && LocalTime.parse(time3, timeFormatter).isAfter(LocalTime.now())) 
                &&  (currentDateLocal.isEqual(LocalDate.parse(duration, DateTimeFormatter.ofPattern("MMMM dd, yyyy"))) 
                ||  currentDateLocal.isBefore(LocalDate.parse(duration, DateTimeFormatter.ofPattern("MMMM dd, yyyy"))))) {
                    rows.add(new Object[]{medicineName, time3, dosage, strength, duration, instruction != null ? instruction : "No notes"});
            }

        }

        // Add the valid rows to the table


        // Debug: Print number of rows after adding data


        // Close resources
        

        // Ensure table updates on the EDT thread
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
        MedsTable.setDefaultEditor(Object.class, null);
        
        for (int i = 0; i < MedsTable.getColumnModel().getColumnCount(); i++) {
            MedsTable.getColumnModel().getColumn(i).setResizable(false);
        }
        // Disable column reordering (make columns non-draggable)
        MedsTable.getTableHeader().setReorderingAllowed(false);
        
        
        rs.close();
        pst.close();

    } catch (SQLException ex) {
        Logger.getLogger(Logging.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "Error retrieving medicine details. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (DateTimeParseException e) {
        // Handle invalid date format exception
        System.out.println("Error parsing date: " + e.getMessage());
        JOptionPane.showMessageDialog(null, "Error parsing the date. Please ensure the date format is correct.", "Date Parsing Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        // Catch any unexpected exceptions and print a message
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Unexpected error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    

    public void getLogs() {
    String queryLogs = "SELECT MedicineName, Time1, Time2, Time3, Take1, Take2, Take3, Duration FROM medicineDetails WHERE Name = ?;";
        try {
            Connection();
            PreparedStatement pst = con.prepareStatement(queryLogs);
            pst.setString(1, Name); // Ensure 'Name' is properly initialized

        ResultSet rs = pst.executeQuery();

        if (!rs.isBeforeFirst()) {
            return;
        }


        // Get the table model and set its structure
        DefaultTableModel table = (DefaultTableModel) LogsTable.getModel();
        table.setColumnIdentifiers(new Object[]{"Medicine Name", "Time", "Consumed"});
        table.setRowCount(0);
        
        
        List<Object[]> rows = new ArrayList<>();
        
        
        while (rs.next()){
            String medicineName = rs.getString("MedicineName");
            String time1 = rs.getString("Time1");
            String time2 = rs.getString("Time2");
            String time3 = rs.getString("Time3");
            String take1 = rs.getString("Take1");
            String take2 = rs.getString("Take2");
            String take3 = rs.getString("Take3");
            String duration = rs.getString("Duration");
        
            LocalDate currentDateLocal = LocalDate.parse(currentDate, DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
            
            if (currentDateLocal.isBefore(LocalDate.parse(duration, DateTimeFormatter.ofPattern("MMMM dd, yyyy")))){
                if (!"null".equals(take1) && !"null".equals(time1)){
                    rows.add(new Object[]{medicineName, time1, take1});
                } if (!"null".equals(take2) && !"null".equals(time2)){
                    rows.add(new Object[]{medicineName, time2, take2});
                } if (!"null".equals(take3) && !"null".equals(time3)){
                    rows.add(new Object[]{medicineName, time2, take2});
                }
            
            }
            if (currentDateLocal.isEqual(LocalDate.parse(duration, DateTimeFormatter.ofPattern("MMMM dd, yyyy")))){
                if (!"null".equals(take1) && !"null".equals(time1)){
                    rows.add(new Object[]{medicineName, time1, take1});
                } if (!"null".equals(take2) && !"null".equals(time2)){
                    rows.add(new Object[]{medicineName, time2, take2});
                } if (!"null".equals(take3) && !"null".equals(time3)){
                    rows.add(new Object[]{medicineName, time2, take2});
                }
            }
        
        
        
        }
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
            LogsTable.revalidate();
            LogsTable.repaint();
        });
        LogsTable.setDefaultEditor(Object.class, null);
        
        for (int i = 0; i < LogsTable.getColumnModel().getColumnCount(); i++) {
            LogsTable.getColumnModel().getColumn(i).setResizable(false);
        }
        // Disable column reordering (make columns non-draggable)
        LogsTable.getTableHeader().setReorderingAllowed(false);
        
        
        
        
        
        
        
        
        
        
        
        
        } catch (SQLException ex) {
            Logger.getLogger(Logging.class.getName()).log(Level.SEVERE, null, ex);
        }
    
}






















        public void getCurrentTime() {
        // Get the current time
        currentTime = LocalTime.now();

        // Format the time in HH:mm:ss format
        timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        ActualTime = currentTime.format(timeFormatter);


    }

    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        MedsTable = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        LogsTable = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTable2);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jTable3);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/HomeLogo.png"))); // NOI18N
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

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Active Logs.png"))); // NOI18N
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

        jTextField2.setBackground(new java.awt.Color(255, 255, 255));
        jTextField2.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(34, 59, 83));
        jTextField2.setText("Currently taking");
        jTextField2.setBorder(null);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        jLabel1.setFont(new java.awt.Font("Montserrat", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(34, 59, 83));
        jLabel1.setText("My Meds");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 170, 40));

        jButton6.setBackground(new java.awt.Color(204, 204, 204));
        jButton6.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(34, 59, 83));
        jButton6.setText("Edit Notes");
        jButton6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 150, 30));

        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(34, 59, 83));
        jTextField1.setText("History");
        jTextField1.setBorder(null);
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, -1, -1));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane8.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane8.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane8.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jScrollPane8MouseWheelMoved(evt);
            }
        });

        MedsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(MedsTable);

        jPanel2.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 890));

        jScrollPane3.setViewportView(jPanel2);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 150, 330, 190));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane6.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane6.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        LogsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        LogsTable.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                LogsTableMouseWheelMoved(evt);
            }
        });
        jScrollPane6.setViewportView(LogsTable);

        jPanel3.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 810));

        jScrollPane1.setViewportView(jPanel3);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 330, 200));

        jButton8.setBackground(new java.awt.Color(204, 204, 204));
        jButton8.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(34, 59, 83));
        jButton8.setText("Add Medication");
        jButton8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 150, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 380, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        new EditNotes(Name).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       new AccountPage(Name).setVisible(true);
       this.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jScrollPane8MouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jScrollPane8MouseWheelMoved
    if (evt.getWheelRotation() < 0) {
        // Mouse wheel moved up (scroll up)
        System.out.println("Scrolling up");
    } else {
        // Mouse wheel moved down (scroll down)
        System.out.println("Scrolling down");
    }

    // Get the JScrollPane parent of MedsTable
    JScrollPane scrollPane = (JScrollPane) MedsTable.getParent().getParent();

    if (scrollPane != null) {
        // Manually adjust the scroll position
        int newValue = scrollPane.getVerticalScrollBar().getValue() + evt.getWheelRotation() * 10; // Adjust scroll speed here
        scrollPane.getVerticalScrollBar().setValue(newValue);
    }
    }//GEN-LAST:event_jScrollPane8MouseWheelMoved

    private void LogsTableMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_LogsTableMouseWheelMoved

    }//GEN-LAST:event_LogsTableMouseWheelMoved

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        new Reminder(Name,null,null,null).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        new Logging(Name).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(Logging.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Logging.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Logging.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Logging.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Logging(Name).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable LogsTable;
    private javax.swing.JTable MedsTable;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
