package healthcare.management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Xia
 */
public class HealthcareManagement {

    static Connection con;
    static Statement st;
    static ResultSet rs;
    static PreparedStatement pst;
    
    private static final String DbName = "credentials";
    private static final String DbDriver = "com.mysql.cj.jdbc.Driver";
    private static final String DbUrl = "jdbc:mysql://localhost:3306/" + DbName;
    private static final String DbUsername = "root";
    private static final String DbPass = "";
    public static String Logged = "";
    
    // Making the Connection method static so it can be called from main
    public static void Connection() throws SQLException {
        try {
            Class.forName(DbDriver);
            con = DriverManager.getConnection(DbUrl, DbUsername, DbPass);
            st = con.createStatement();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HealthcareManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Fetch the logged status from the database
    public static void getLogged() {
        String queryLog = "SELECT Logged FROM AlreadyLogged;";
        try {
            pst = con.prepareStatement(queryLog); // Prepare the SQL query
            rs = pst.executeQuery(); // Execute the query and get the result set
        
            if (rs.next()) {
                Logged = rs.getString(1); // Fetch the value of 'Logged'
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(HealthcareManagement.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Ensure that resources are always closed
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(HealthcareManagement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Main method to control the flow
    public static void main(String[] args) {
        try {
            Connection(); // Call the static Connection method
            getLogged(); // Call the static getLogged method
            
            // Check if the user is logged in
            if ("True".equals(Logged)) { // Use equals() for string comparison
                new SignUp().setVisible(true); // Show the SignUp window
            } else {
                new GetStarted().setVisible(true); // Show the GetStarted window
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(HealthcareManagement.class.getName()).log(Level.SEVERE, "Database connection failed", ex);
        }
    }
}
