package alarmpanel;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class AlarmPanel {
    static Connection con;
    static Statement st;
    static ResultSet rs;
    static PreparedStatement pst;

    SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
    private static final String DbName = "credentials";
    private static final String DbDriver = "com.mysql.cj.jdbc.Driver";
    private static final String DbUrl = "jdbc:mysql://localhost:3306/" + DbName;
    private static final String DbUsername = "root";
    private static final String DbPass = "";
    public static String Name;

    static Date currentDateObj;
    static String currentDate;
    static DateTimeFormatter timeFormatter;
    static LocalTime currentTime;
    static String ActualTime;
    static String Switch;
    List<String[]> rows = new ArrayList<>();
    
    public static void Connection() throws SQLException {
        try {
            Class.forName(DbDriver);
            con = DriverManager.getConnection(DbUrl, DbUsername, DbPass);
            st = con.createStatement();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AlarmPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void GetName() {
        String queryLog = "SELECT HomeName FROM currentuser;";
        try {
            Connection();
            pst = con.prepareStatement(queryLog);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Name = rs.getString(1);
            }
            pst.close();
            rs.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(AlarmPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setCurrentDate() {
        try {
            currentDateObj = new Date(); // Get the current system date
            currentDate = sdf.format(currentDateObj);
            System.out.println("Current date is: " + currentDate);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error setting the current date.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void getMedsTime() {
    String queryTime = "SELECT * FROM medicineDetails WHERE Name = ? AND Duration = ?;";
    try {
        Connection();
        PreparedStatement pst = con.prepareStatement(queryTime);
        pst.setString(1, Name); 
        pst.setString(2, currentDate); 

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            String medicineName = rs.getString("MedicineName");
            String Time1 = rs.getString("Time1");
            String Time2 = rs.getString("Time2");
            String Time3 = rs.getString("Time3");
            String Instruction = rs.getString("Instruction");

            // Add times only if they are not null
            if (Time1 != null && !Time1.equals("null")) {
                rows.add(new String[]{medicineName, Time1, Instruction});
            }
            if (Time2 != null && !Time2.equals("null")) {
                rows.add(new String[]{medicineName, Time2, Instruction});
            }
            if (Time3 != null && !Time3.equals("null")) {
                rows.add(new String[]{medicineName, Time3, Instruction});
            }
        }

        pst.close();
        rs.close();
        con.close();
    } catch (SQLException ex) {
        Logger.getLogger(AlarmPanel.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    
    public void getAlarmStatus() {
        String queryAlarm = "SELECT * FROM alarmsettings;";
        try {
            Connection();
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
            pst.close();
            rs.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(AlarmPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    
    
    public void getCurrentTime() {
        // Get the current time
        currentTime = LocalTime.now();

        // Format the time in HH:mm:ss format
        timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        ActualTime = currentTime.format(timeFormatter);
    }

    public static void main(String[] args) {
    AlarmPanel alarmPanel = new AlarmPanel(); // Create an instance of AlarmPanel
    alarmPanel.setCurrentDate();
    alarmPanel.GetName();
    alarmPanel.getMedsTime();

    String lastPrintedTime = ""; // To keep track of the last printed time
    while (true) {
        alarmPanel.getAlarmStatus(); // Fetch the latest alarm status in real-time

        if ("On".equals(Switch)) {
            alarmPanel.getCurrentTime(); // Update the current time
            if (!alarmPanel.ActualTime.equals(lastPrintedTime)) { // Check if time has changed
                lastPrintedTime = alarmPanel.ActualTime;
                for (String[] row : alarmPanel.rows) {
                    String time = row[1];  // Assuming time is at index 1
                    String medicineName = row[0];  // Assuming medicine name is at index 0
                    String instruction = row[2];  // Assuming instruction is at index 2

                    if (time.equals(alarmPanel.ActualTime)) {
                        new SetAlarm(Name, medicineName, instruction).setVisible(true);
                    }
                }
            }
        }

        try {
            Thread.sleep(1000); // Wait for 1 second before checking again
        } catch (InterruptedException e) {
            Logger.getLogger(AlarmPanel.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}

}
