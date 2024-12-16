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
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Xia
 */
public class EditNotes extends javax.swing.JFrame {

    /**
     * Creates new form EditNotes
     */
    public EditNotes(String getName) {
        this.Name = getName;
        this.setTitle("Notes");
        try {
            Connection();
        } catch (SQLException ex) {
            Logger.getLogger(EditNotes.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Images/NotifLogo.png")).getImage());
        setCurrentDate();
        getMedicine();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Add a WindowListener to open the Account JFrame when this JFrame is closed
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                new Logging(Name).setVisible(true); // Open the Account JFrame
            }
        });
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
    public static String Name;
    public static String SelectedMeds;
    public static String Notes;
    
    
    
    public static Date currentDateObj;
    public static String currentDate;
    public static DateTimeFormatter timeFormatter;
    public static LocalTime currentTime;
    public static String ActualTime;
    
    
    
    public void Connection() throws SQLException {
        try {
            Class.forName(DbDriver);
            con = DriverManager.getConnection(DbUrl, DbUsername, DbPass);
            st = con.createStatement();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
public void getMedicineName() {
    String queryMeds = "SELECT MedicineName FROM medicinedetails WHERE Name = ?";
    try {
        // Ensure 'con' is a valid database connection
        Connection(); // Initialize the connection
        PreparedStatement pst = con.prepareStatement(queryMeds);
        pst.setString(1, Name); // Pass the parameter 'name' to the query

        ResultSet rs = pst.executeQuery(); // Execute the query and get the result set
        boolean found = false; // Track if a match is found

        while (rs.next()) {
            String dbMeds = rs.getString("MedicineName"); // Get the MedicineName column

            if (getMedsName.getText().equals(dbMeds)) {
                SelectedMeds = dbMeds; // Assign the selected medicine
                found = true;
                break;
            }
        }

        if (!found) {
            // Show the error message if no match was found
            JOptionPane.showMessageDialog(null, "Medicine not found. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Close resources
        rs.close();
        pst.close();
    } catch (SQLException ex) {
        Logger.getLogger(EditNotes.class.getName()).log(Level.SEVERE, null, ex);
    }
}


    public void getCurrentTime() {
        // Get the current time
        currentTime = LocalTime.now();

        // Format the time in HH:mm:ss format
        timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        ActualTime = currentTime.format(timeFormatter);


    }
   

    public void getNotes() {
    String queryNotes = "SELECT Instruction FROM medicinedetails WHERE Name = ? AND MedicineName = ?;";
    try {
        Connection(); // Ensure 'con' is a valid database connection
        PreparedStatement pst = con.prepareStatement(queryNotes);
        pst.setString(1, Name); // Pass the 'Name' variable to the query
        pst.setString(2, SelectedMeds); // Pass the 'SelectedMeds' variable to the query

        ResultSet rs = pst.executeQuery(); // Execute the query

        if (rs.next()) {
            String dbNotes = rs.getString("Instruction");
            Notes = dbNotes;
            NotePanel.setText(Notes); // Set the fetched data to the NotePanel
        }

        // Close resources
        rs.close();
        pst.close();

    } catch (SQLException ex) {
        Logger.getLogger(EditNotes.class.getName()).log(Level.SEVERE, null, ex);
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
    String queryMed = "SELECT MedicineName, Instruction FROM medicineDetails WHERE Name = ?;";
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
        DefaultTableModel table = (DefaultTableModel) MedsList.getModel();
        table.setColumnIdentifiers(new Object[]{"Medicine Name", "Notes"});
        table.setRowCount(0); // Clear previous rows

        // Initialize rows list if necessary
        List<Object[]> rows = new ArrayList<>();

        // Loop through the result set
        while (rs.next()) {
            // Fetch the data from ResultSet
            String medicineName = rs.getString("MedicineName");
            String instruction = rs.getString("Instruction");
            rows.add(new Object[]{medicineName, instruction != null ? instruction : "No notes"});
            
        }

        // Ensure table updates on the EDT thread
        SwingUtilities.invokeLater(() -> {
            for (Object[] row : rows) {
                table.addRow(row);
            }
            MedsList.revalidate();
            MedsList.repaint();
        });

        MedsList.setDefaultEditor(Object.class, null);

        // Disable column reordering and resizing
        for (int i = 0; i < MedsList.getColumnModel().getColumnCount(); i++) {
            MedsList.getColumnModel().getColumn(i).setResizable(false);
        }
        MedsList.getTableHeader().setReorderingAllowed(false);

        // Close resources
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

    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();
        getMedsName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        MedsList = new javax.swing.JTable();
        NotePanel = new javax.swing.JTextArea();

        jFormattedTextField1.setText("jFormattedTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(34, 60, 83));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        getMedsName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getMedsName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getMedsNameActionPerformed(evt);
            }
        });
        jPanel1.add(getMedsName, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 170, 30));

        jLabel1.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Medicine Name");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 170, -1));

        jButton1.setText("Back");
        jButton1.setMaximumSize(new java.awt.Dimension(102, 23));
        jButton1.setMinimumSize(new java.awt.Dimension(102, 23));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, 120, 30));

        jButton2.setText("Get Medicine Notes");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 150, 30));

        jButton3.setText("Update Notes");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, 120, 30));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MedsList.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(MedsList);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 330, 690));

        jScrollPane1.setViewportView(jPanel2);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 330, 170));

        NotePanel.setColumns(20);
        NotePanel.setRows(5);
        jPanel3.add(NotePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 330, 140));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void getMedsNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getMedsNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_getMedsNameActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new Logging(Name).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (Notes == null) {
            JOptionPane.showMessageDialog(null, "Notes is null", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Notes.equals(NotePanel.getText())) {
            JOptionPane.showMessageDialog(null, "The notes are the same", "Notes", JOptionPane.INFORMATION_MESSAGE);
        } else if (NotePanel.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "The note panel is empty", "Notes", JOptionPane.ERROR_MESSAGE);
        }else {
            String queryUpdate = "UPDATE medicinedetails SET Instruction = ? WHERE Name = ? AND MedicineName = ?;";
        try {
            Connection(); // Ensure the connection is established

        // Prepare the statement
        PreparedStatement pst = con.prepareStatement(queryUpdate);
        pst.setString(1, NotePanel.getText()); // Update with the new text
        pst.setString(2, Name); // Pass the 'Name' variable to the query
        pst.setString(3, SelectedMeds); // Pass the 'SelectedMeds' variable

        // Execute the update
        int rowsUpdated = pst.executeUpdate();

        // Show success message
        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(null, "Notes updated successfully", "Notes", JOptionPane.INFORMATION_MESSAGE);;
            new Logging(Name).setVisible(true);
            this.setVisible(false);
            
        } else {
            JOptionPane.showMessageDialog(null, "No records were updated", "Notes", JOptionPane.WARNING_MESSAGE);
        }

        // Close the prepared statement
        pst.close();

    } catch (SQLException ex) {
        Logger.getLogger(EditNotes.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "An error occurred while updating the notes", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        getMedicineName();
        getNotes();
        
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(EditNotes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditNotes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditNotes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditNotes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditNotes(Name).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable MedsList;
    private javax.swing.JTextArea NotePanel;
    private javax.swing.JTextField getMedsName;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
