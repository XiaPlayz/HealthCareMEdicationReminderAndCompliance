/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package alarmpanel;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Xia
 */
public class SetAlarm extends javax.swing.JFrame {

    /**
     * Creates new form SetAlarm
     */
    public SetAlarm(String getName, String getMedicine, String getInstruction) {
        this.Name = getName;
        this.MedicineName = getMedicine;
        this.Instruction = getInstruction;
        initComponents();
        this.setTitle("Notification");
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Images/NotifLogo.png")).getImage());
        try {
            Connection();
        } catch (SQLException ex) {
            Logger.getLogger(SetAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        setCurrentDate();
        getCurrentTime();
        getMedicine();
        checkDB();
        System.out.println(MedicineName+Instruction);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
    
    
    Connection con;
    Statement st;
    ResultSet rs;
    PreparedStatement pst;
    
    
    
    
    
    
    public static String Name;
    public static String MedicineName;
    public static String Instruction;
    public static int MaxValue;
    SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
    private static final String DbName = "credentials";
    private static final String DbDriver = "com.mysql.cj.jdbc.Driver";
    private static final String DbUrl = "jdbc:mysql://localhost:3306/" + DbName;
    private static final String DbUsername = "root";
    private static final String DbPass = "";
    public static DateTimeFormatter timeFormatter;
    public static LocalTime currentTime;
    public static String ActualTime;
    public static Date currentDateObj;
    public static String currentDate;
    
    public void setCurrentDate() {
        try {
            currentDateObj = new Date(); // Get the current system date
            currentDate = sdf.format(currentDateObj);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error setting the current date.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    
    public void getMedicine() {
    String queryMed = "SELECT Time1, Time2, Time3 FROM medicineDetails WHERE Name = ? AND Duration = ?;";
    try {
        Connection();
        
        PreparedStatement pst = con.prepareStatement(queryMed);
        pst.setString(1, Name); // Ensure 'Name' is properly initialized
        pst.setString(2, currentDate); // Ensure 'currentDate' is properly initialized

        ResultSet rs = pst.executeQuery();
        MaxValue = 0;
        while (rs.next()) {
            String Time1 = rs.getString("Time1");
            String Time2 = rs.getString("Time2");
            String Time3 = rs.getString("Time3");
            if (Time1 != null && !Time1.equalsIgnoreCase("null")){
                MaxValue++;
            }

            if (Time2 != null && !Time2.equalsIgnoreCase("null")) {
                MaxValue++;
            }

            if (Time3 != null && !Time3.equalsIgnoreCase("null")) {
                MaxValue++;
            }
        }       
        

        // Close resources
        rs.close();
        pst.close();
        con.close();

    } catch (SQLException ex) {
        Logger.getLogger(SetAlarm.class.getName()).log(Level.SEVERE, null, ex);
        ex.printStackTrace();
    }
}
    
   
    public void Connection() throws SQLException {
    try {
        Class.forName(DbDriver);
        con = DriverManager.getConnection(DbUrl, DbUsername, DbPass);
        st = con.createStatement();
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(SetAlarm.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    
    public void checkDB(){
       String queryCheckDate = "SELECT DateCheck from Adherence";
        try {
            Connection();
            pst = con.prepareStatement(queryCheckDate);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String CheckDate = rs.getString("DateCheck");
                if (!currentDate.equals(CheckDate)) {  // Use .equals() to compare string values
                    String newDate = "UPDATE Adherence set DateCheck = ? , MinVal =?, MaxVal =? Where DateCheck =?;";
                    pst = con.prepareStatement(newDate);  // Ensure you're using PreparedStatement instead of Statement
                    pst.setString(1, currentDate); // Set the currentDate value
                    pst.setInt(2, 0); // Assuming 0 is the MinVal value
                    pst.setInt(3, MaxValue); // Assuming MaxValue is already defined
                    pst.setString(4, CheckDate); // Set the previous DateCheck value
                    pst.executeUpdate();  // Execute the update query
                }
            }
        rs.close();
        pst.close();
        con.close();

        } catch (SQLException ex) {
            Logger.getLogger(SetAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    
    
    }
    
    
    public void updateAdherence() {
    String queryMedTime = "SELECT * FROM medicineDetails WHERE Name = ? AND Duration = ?;";
    try {
        Connection();
        pst = con.prepareStatement(queryMedTime);
        pst.setString(1, Name);
        pst.setString(2, currentDate);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            String Time1 = rs.getString("Time1");
            String Time2 = rs.getString("Time2");
            String Time3 = rs.getString("Time3");
            
            if (Time1.equals(ActualTime)) {  // Correct string comparison
                String updateTake1 = "UPDATE medicinedetails SET Take1 = 'Yes' WHERE Name = ? AND MedicineName = ?";
                pst = con.prepareStatement(updateTake1);
                pst.setString(1, Name);
                pst.setString(2, MedicineName);
                pst.executeUpdate();
                
                // Get the MinVal from Adherence
                String getMinVal = "SELECT MinVal FROM Adherence";
                pst = con.prepareStatement(getMinVal);
                ResultSet min = pst.executeQuery();
                if (min.next()) {
                    String updateMin = "UPDATE Adherence SET MinVal = MinVal + 1";  // Query to update MinVal
                    pst = con.prepareStatement(updateMin);
                    pst.executeUpdate();  // Execute the update
                }
                min.close();
            }
            if (Time2.equals(ActualTime)) {  // Correct string comparison
                String updateTake2 = "UPDATE medicinedetails SET Take2 = 'Yes' WHERE Name = ? AND MedicineName = ?";
                pst = con.prepareStatement(updateTake2);
                pst.setString(1, Name);
                pst.setString(2, MedicineName);
                pst.executeUpdate();
                
                // Get the MinVal from Adherence
                String getMinVal = "SELECT MinVal FROM Adherence";
                pst = con.prepareStatement(getMinVal);
                ResultSet min = pst.executeQuery();
                if (min.next()) {
                    String updateMin = "UPDATE Adherence SET MinVal = MinVal + 1";  // Query to update MinVal
                    pst = con.prepareStatement(updateMin);
                    pst.executeUpdate();  // Execute the update
                }
                min.close();
            }
            if (Time3.equals(ActualTime)) {  // Correct string comparison
                String updateTake3 = "UPDATE medicinedetails SET Take3 = 'Yes' WHERE Name = ? AND MedicineName = ?";
                pst = con.prepareStatement(updateTake3);
                pst.setString(1, Name);
                pst.setString(2, MedicineName);
                pst.executeUpdate();
                
                // Get the MinVal from Adherence
                String getMinVal = "SELECT MinVal FROM Adherence";
                pst = con.prepareStatement(getMinVal);
                ResultSet min = pst.executeQuery();
                if (min.next()) {
                    String updateMin = "UPDATE Adherence SET MinVal = MinVal + 1";  // Query to update MinVal
                    pst = con.prepareStatement(updateMin);
                    pst.executeUpdate();  // Execute the update
                }
                min.close();
            }
            
        }
        rs.close();  // Close ResultSet
    } catch (SQLException ex) {
        Logger.getLogger(SetAlarm.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    public void noButton() {
    String queryMedTime = "SELECT * FROM medicineDetails WHERE Name = ? AND Duration = ?;";
    try {
        Connection();
        pst = con.prepareStatement(queryMedTime);
        pst.setString(1, Name);
        pst.setString(2, currentDate);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            String Time1 = rs.getString("Time1");
            String Time2 = rs.getString("Time2");
            String Time3 = rs.getString("Time3");
            
            if (Time1.equals(ActualTime)) {  // Correct string comparison
                String updateTake1 = "UPDATE medicinedetails SET Take1 = 'No' WHERE Name = ? AND MedicineName = ?";
                pst = con.prepareStatement(updateTake1);
                pst.setString(1, Name);
                pst.setString(2, MedicineName);
                pst.executeUpdate();
            }
            if (Time2.equals(ActualTime)) {  // Correct string comparison
                String updateTake2 = "UPDATE medicinedetails SET Take2 = 'No' WHERE Name = ? AND MedicineName = ?";
                pst = con.prepareStatement(updateTake2);
                pst.setString(1, Name);
                pst.setString(2, MedicineName);
                pst.executeUpdate();
                            }
            if (Time3.equals(ActualTime)) {  // Correct string comparison
                String updateTake3 = "UPDATE medicinedetails SET Take3 = 'No' WHERE Name = ? AND MedicineName = ?";
                pst = con.prepareStatement(updateTake3);
                pst.setString(1, Name);
                pst.setString(2, MedicineName);
                pst.executeUpdate();
            }
            
        }
        rs.close();  // Close ResultSet
    } catch (SQLException ex) {
        Logger.getLogger(SetAlarm.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        MName = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        MNameLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setText("Take your medicine");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 200, 40));

        MName.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        MName.setForeground(new java.awt.Color(0, 51, 153));
        MName.setText(MedicineName);
        jPanel1.add(MName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 180, 30));

        jLabel3.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 153));
        jLabel3.setText("Instruction:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        jLabel4.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText(Instruction);
        jLabel4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 290, 50));

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 210, -1, -1));

        jButton2.setText("Take");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 210, -1, -1));

        MNameLabel.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        MNameLabel.setForeground(new java.awt.Color(0, 51, 153));
        MNameLabel.setText("Medicine Name:");
        jPanel1.add(MNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 180, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        updateAdherence();
        this.setVisible(false);
        new AlarmPanel();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        noButton();
        this.setVisible(false);
        new AlarmPanel();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(SetAlarm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SetAlarm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SetAlarm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SetAlarm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SetAlarm(Name,MedicineName,Instruction).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel MName;
    private javax.swing.JLabel MNameLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
