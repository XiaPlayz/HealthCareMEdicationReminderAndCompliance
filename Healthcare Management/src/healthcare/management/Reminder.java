/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package healthcare.management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public final class Reminder extends javax.swing.JFrame {

    public Reminder(String getName, String getTime1,String getTime2,String getTime3) {
        this.Name = getName;
        this.Time1 = getTime1;
        this.Time2 = getTime2;
        this.Time3 = getTime3;
        initComponents();
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Images/NotifLogo.png")).getImage());
        Cbox();
        getCurrentTime();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("Set Reminder");
        date();
        try {
            Connection();
        } catch (SQLException ex) {
            Logger.getLogger(Reminder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    Connection con;
    Statement st;
    PreparedStatement pst;
    
    private static final String DbName = "credentials";
    private static final String DbDriver = "com.mysql.cj.jdbc.Driver";
    private static final String DbUrl = "jdbc:mysql://localhost:3306/"+DbName;
    private static final String DbUsername = "root";
    private static final String DbPass = "";
    
    
    public static DateTimeFormatter timeFormatter;
    public static LocalTime currentTime;
    public static String ActualTime;
    
    
    
    public void Connection() throws SQLException{
        try {
            Class.forName(DbDriver);
            con = DriverManager.getConnection(DbUrl, DbUsername, DbPass);
            st = con.createStatement();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Reminder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void getCurrentTime() {
        // Get the current time
        currentTime = LocalTime.now();

        // Format the time in HH:mm:ss format
        timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        ActualTime = currentTime.format(timeFormatter);


    }
    
    SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
    public static String SelectedDate;
    public static String Name;
    public static String Time1;
    public static String Time2;
    public static String Time3;
    public static String currentDate;
    public static String CboxselectedOption;
    Date DateChooserDate;
    public static Date selectedDateObj;
    public static Date currentDateObj;
    
public void getSelectedDate() {
    try {
        DateChooserDate = DateChooser.getDate(); // Retrieve the selected date from DateChooser
        if (DateChooserDate != null) {
            SelectedDate = sdf.format(DateChooserDate);
            selectedDateObj = sdf.parse(SelectedDate); // Parse to Date object
        } else {
            SelectedDate = null;
            selectedDateObj = null;
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error retrieving the date.", "Error", JOptionPane.ERROR_MESSAGE);
    }

}


public void emptyAll() {
    MedicineName.setText(null); // Clears the text field
    DosageBox.setText(null);    // Clears the text field
    StrengthBox.setText(null);  // Clears the text field
    Time1 = null;               // Resets variables
    Time2 = null;
    Time3 = null;

    if (DateChooser != null) {  // Check if DateChooser is not null before clearing
        DateChooser.setDate(null); // Clears the date selection for a date picker
    }
    
    InstructionBox.setText(null); // Clears the text field
    DateChooserDate = null;       // Resets variables
    SelectedDate = null;
    selectedDateObj = null;
}


    
public void date() {
    try {
        currentDateObj = new Date(); // Get the current system date
        currentDate = sdf.format(currentDateObj); // Format as string
        reminderDate.setText(currentDate); // Update UI with current date
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error setting the current date.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

public void Cbox() {
    CboxselectedOption = (String) jComboBox2.getSelectedItem();

    if ("1 time daily".equals(CboxselectedOption)) {
        Time1Button.setEnabled(true);
        Time2Button.setEnabled(false);
        Time3Button.setEnabled(false);
    } else if ("2 times daily".equals(CboxselectedOption)) {
        Time1Button.setEnabled(true);
        Time2Button.setEnabled(true);
        Time3Button.setEnabled(false);
    } else if ("3 times daily".equals(CboxselectedOption)) {
        Time1Button.setEnabled(true);
        Time2Button.setEnabled(true);
        Time3Button.setEnabled(true);
    } else if ("As needed".equals(CboxselectedOption)){
        Time1Button.setEnabled(true);
        Time2Button.setEnabled(false);
        Time3Button.setEnabled(false);
    } else if ("Select Frequency".equals(CboxselectedOption)){
        Time1Button.setEnabled(false);
        Time2Button.setEnabled(false);
        Time3Button.setEnabled(false);
    } else {
        Time1Button.setEnabled(false);
        Time2Button.setEnabled(false);
        Time3Button.setEnabled(false);
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

        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        MedicineName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        DosageComboBox = new javax.swing.JComboBox<>();
        DosageBox = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        StrengthComboBox = new javax.swing.JComboBox<>();
        StrengthBox = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        DateChooser = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        Time1Button = new javax.swing.JButton();
        Time3Button = new javax.swing.JButton();
        Time2Button = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        InstructionBox = new javax.swing.JTextArea();
        jButton11 = new javax.swing.JButton();
        reminderDate = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setBackground(new java.awt.Color(0, 51, 102));
        jLabel10.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 102));
        jLabel10.setText("Today");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/HomeLogo.png"))); // NOI18N
        jButton7.setBorder(null);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 590, -1, -1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Active Reminder.png"))); // NOI18N
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

        jPanel2.setBackground(new java.awt.Color(180, 196, 208));
        jPanel2.setToolTipText("");
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(34, 60, 83));
        jButton1.setForeground(new java.awt.Color(204, 204, 204));
        jButton1.setText("Set Reminder");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 420, 130, 30));

        MedicineName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MedicineNameActionPerformed(evt);
            }
        });
        jPanel2.add(MedicineName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 120, 30));

        jLabel1.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Medicine Name");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        jLabel2.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Dosage");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, -1, -1));

        DosageComboBox.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        DosageComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "tsp", "capsule", "tbsp", "tablet", "drop" }));
        DosageComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DosageComboBoxActionPerformed(evt);
            }
        });
        jPanel2.add(DosageComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 70, 30));

        DosageBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DosageBoxActionPerformed(evt);
            }
        });
        jPanel2.add(DosageBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 130, 30));

        jComboBox2.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Frequency", "1 time daily", "2 times daily", "3 times daily", "As needed" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 120, 30));

        jLabel4.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Frequency");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        StrengthComboBox.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        StrengthComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "mg", "g", "mL", "ounce" }));
        StrengthComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StrengthComboBoxActionPerformed(evt);
            }
        });
        jPanel2.add(StrengthComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 120, 70, 30));
        jPanel2.add(StrengthBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, 130, 30));

        jLabel5.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 102));
        jLabel5.setText("Strength");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, -1, 20));

        jLabel7.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jLabel7.setText("Set Date");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 205, -1, 20));
        jPanel2.add(DateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, 140, 30));

        jLabel6.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel6.setText("Select Duration");
        jPanel4.add(jLabel6);

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 300, 90));

        Time1Button.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        Time1Button.setText("SET TIME");
        Time1Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Time1ButtonActionPerformed(evt);
            }
        });
        jPanel2.add(Time1Button, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, -1));

        Time3Button.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        Time3Button.setText("SET TIME");
        Time3Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Time3ButtonActionPerformed(evt);
            }
        });
        jPanel2.add(Time3Button, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 260, -1, -1));

        Time2Button.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        Time2Button.setText("SET TIME");
        Time2Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Time2ButtonActionPerformed(evt);
            }
        });
        jPanel2.add(Time2Button, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 260, -1, -1));

        jLabel8.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 102));
        jLabel8.setText("Write Instruction");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 290, -1, 30));

        InstructionBox.setColumns(5);
        InstructionBox.setLineWrap(true);
        InstructionBox.setRows(5);
        InstructionBox.setWrapStyleWord(true);
        jPanel2.add(InstructionBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, 240, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 320, 470));

        jButton11.setBackground(new java.awt.Color(51, 102, 255));
        jButton11.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("SET TIME");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 490, -1, 30));

        reminderDate.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        reminderDate.setForeground(new java.awt.Color(0, 51, 102));
        reminderDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        reminderDate.setText(currentDate);
        jPanel1.add(reminderDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, 170, 20));

        jLabel9.setBackground(new java.awt.Color(0, 51, 102));
        jLabel9.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 102));
        jLabel9.setText("Input Your Medicine");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 380, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    getSelectedDate();
    Cbox();

    // Validate input fields
    if (MedicineName.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter the medicine name.", "No Medicine Name", JOptionPane.ERROR_MESSAGE);
        return;
    }
    if (DosageBox.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter the dosage.", "No Dosage", JOptionPane.ERROR_MESSAGE);
        return;
    }
    try {
   
        int intdosage = Integer.parseInt(DosageBox.getText().trim());
    } catch (NumberFormatException intEx) {
        try {
       
            float floatdosage = Float.parseFloat(DosageBox.getText().trim());

        } catch (NumberFormatException floatEx) {
            JOptionPane.showMessageDialog(
            this,
            "Please enter a valid number for dosage.",
            "Invalid Input",
            JOptionPane.ERROR_MESSAGE
            );
            return;
        }
    }
    if ("Select Frequency".equals(CboxselectedOption)) {
        JOptionPane.showMessageDialog(this, "Please set a frequency.", "Frequency Required", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    if (StrengthBox.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a Strength.", "No Strength", JOptionPane.ERROR_MESSAGE);
        return;
    }
    try {
        int intStrength = Integer.parseInt(StrengthBox.getText().trim());
        System.out.println("Strength entered as integer: " + intStrength);
    } catch (NumberFormatException intEx) {
        try {
            float floatStrength = Float.parseFloat(StrengthBox.getText().trim());
            System.out.println("Strength entered as float: " + floatStrength);
        } catch (NumberFormatException floatEx) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for strength.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }   
    }
    
    if (Time1 == null && "1 time daily".equals(CboxselectedOption)) {
        JOptionPane.showMessageDialog(this, "Please set a time for the daily reminder.", "Time Required", JOptionPane.ERROR_MESSAGE);
        return;
    }
    if (Time1 == null && "As needed".equals(CboxselectedOption)) {
        JOptionPane.showMessageDialog(this, "Please set a time for the daily reminder.", "Time Required", JOptionPane.ERROR_MESSAGE);
        return;
    }
    if (((Time1 == null || Time2 == null) )&& "2 times daily".equals(CboxselectedOption)) {
        JOptionPane.showMessageDialog(this, "Please set a time for the daily reminder.", "Time Required", JOptionPane.ERROR_MESSAGE);
        return;
    }
    if (Time1 != null && Time2 != null){
        if ((LocalTime.parse(Time1, timeFormatter).isAfter(LocalTime.parse(Time2, timeFormatter)) || 
            LocalTime.parse(Time1, timeFormatter).equals(LocalTime.parse(Time2, timeFormatter))) && "2 times daily".equals(CboxselectedOption)) {
            JOptionPane.showMessageDialog(this, "Your 2nd Selected Time must be Later than " + Time1, "Invalid Time", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }    
    if ((Time1 == null || Time2 == null || Time3 == null ) && "3 times daily".equals(CboxselectedOption)) {
        JOptionPane.showMessageDialog(this, "Please set a time for the daily reminder.", "Time Required", JOptionPane.ERROR_MESSAGE);
        return;
    }

            
    if (Time2 != null && Time3 != null){
        if ((LocalTime.parse(Time2, timeFormatter).isAfter(LocalTime.parse(Time3, timeFormatter)) || 
        LocalTime.parse(Time2, timeFormatter).equals(LocalTime.parse(Time3, timeFormatter))) && 
        "3 times daily".equals(CboxselectedOption)) {
    
        JOptionPane.showMessageDialog(this, "Your 3rd Selected Time must be Later than " + Time3, "Invalid Time", JOptionPane.ERROR_MESSAGE);
        return;
    }
    }
    
            
    if (SelectedDate == null || selectedDateObj == null) {
        JOptionPane.showMessageDialog(this, "Please select a valid date.", "Invalid Date", JOptionPane.ERROR_MESSAGE);
        return;
    }



    if (InstructionBox.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter an instruction.", "Instruction Required", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Ensure the selected date is after the current date
    if (!selectedDateObj.after(currentDateObj)) {
        JOptionPane.showMessageDialog(this, "Please select a date after " + currentDate + ".", "Invalid Date", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Combine dosage and strength
    String Dosage = DosageBox.getText() + " " + DosageComboBox.getSelectedItem();
    String Strength = StrengthBox.getText() + " " + StrengthComboBox.getSelectedItem();
    String Instruction = InstructionBox.getText();

    // Create the SQL query
    String queryReg = "INSERT INTO medicineDetails (Name, MedicineName, Dosage, Frequency, Strength, Time1, Time2, Time3, Duration, Instruction, Take1,Take2,Take3) \n" +
        "VALUES ('" + Name + "', '" + MedicineName.getText() + "', '" + Dosage + "', '" + CboxselectedOption + "', '" + Strength + "', '" + Time1 + "', '" + Time2 + "', '" + 
        Time3 + "', '" + SelectedDate + "', '" + Instruction + "', 'null','null','null');";

    String queryVerify = "SELECT * FROM medicineDetails;";

    String queryCheck = "SELECT * FROM medicineDetails WHERE Name = '" + Name + "' AND MedicineName = '" + MedicineName.getText() + "' AND Dosage = '" + Dosage + 
        "' AND Frequency = '" + CboxselectedOption + "' AND Strength = '" + Strength + "' AND Time1 = '" + Time1 + "' AND Time2 = '" + Time2 + 
        "' AND Time3 = '" + Time3 + "' AND Duration = '" + SelectedDate + "' AND Instruction = '" + Instruction + "';";

    
    
    
    
    
try {
    // Use the same variable for the PreparedStatement
    pst = con.prepareStatement(queryCheck); // Use pst instead of st
    ResultSet Verify = pst.executeQuery();
    
    pst = con.prepareStatement(queryVerify); // Continue using pst
    ResultSet Check = pst.executeQuery();

    if (Verify.next() && Check.next()) {
        JOptionPane.showMessageDialog(null, "Duplicate Entry", "Duplicate Error", JOptionPane.ERROR_MESSAGE);
    } else {
        // Assuming you are using queryReg to insert data
        pst = con.prepareStatement(queryReg);
        pst.executeUpdate();
        System.out.println(queryReg);
        emptyAll();
        JOptionPane.showMessageDialog(null, "Record Inserted Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        this.setVisible(false);
        new Home().setVisible(true);
    }
    } catch (SQLException e) {
    
        JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    
    
    
    
    
    
    
    
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

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        new Logging(Name).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void MedicineNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MedicineNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MedicineNameActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11ActionPerformed

    private void Time1ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Time1ButtonActionPerformed
       new Time1(Name).setVisible(true);   
    }//GEN-LAST:event_Time1ButtonActionPerformed

    private void Time3ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Time3ButtonActionPerformed
        new Time3(Name,Time1,Time2).setVisible(true);
    }//GEN-LAST:event_Time3ButtonActionPerformed

    private void Time2ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Time2ButtonActionPerformed
        new Time2(Name,Time1).setVisible(true);
    }//GEN-LAST:event_Time2ButtonActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        Cbox();
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        new AccountPage(Name).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void StrengthComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StrengthComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_StrengthComboBoxActionPerformed

    private void DosageBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DosageBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DosageBoxActionPerformed

    private void DosageComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DosageComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DosageComboBoxActionPerformed

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
            java.util.logging.Logger.getLogger(Reminder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reminder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reminder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reminder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reminder(Name,Time1,Time2,Time3).setVisible(true);

                }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DateChooser;
    private javax.swing.JTextField DosageBox;
    private javax.swing.JComboBox<String> DosageComboBox;
    private javax.swing.JTextArea InstructionBox;
    private javax.swing.JTextField MedicineName;
    private javax.swing.JTextField StrengthBox;
    private javax.swing.JComboBox<String> StrengthComboBox;
    private javax.swing.JButton Time1Button;
    private javax.swing.JButton Time2Button;
    private javax.swing.JButton Time3Button;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel reminderDate;
    // End of variables declaration//GEN-END:variables
}
