package dbproject.dbconnection;

// Acknowledgments: This example is a modification of code provided 
// by Dimitri Rakitine.
// Usage from command line on key.csc.ncsu.edu: 
// see instructions in FAQ
// Website for Oracle setup at NCSU : http://www.csc.ncsu.edu/techsupport/technotes/oracle.php
//Note: If you run the program more than once, it will not be able to create the COFFEES table anew after the first run; 
//	you can remove the COFFEES tables between the runs by typing "drop table COFFEES;" in SQL*Plus.
import java.sql.*;

public class dbconnection_dbObject {

    static final String jdbcURL
            = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";

    private static dbconnection_dbObject instance = null;
    
    
    public dbconnection_dbObject() {
    }

    public static Statement getDBConnection() {
        
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
        if (instance == null) {
            instance = new dbconnection_dbObject();
            try {

            // Load the driver. This creates an instance of the driver
                // and calls the registerDriver method to make Oracle Thin
                // driver available to clients.
                Class.forName("oracle.jdbc.driver.OracleDriver");

                String user = "wli29";	// For example, "jsmith"    
                String passwd = "200063423";	// Your 9 digit student ID number
                try {

		// Get a connection from the first driver in the
                    // DriverManager list that recognizes the URL jdbcURL
                    conn = DriverManager.getConnection(jdbcURL, user, passwd);

		// Create a statement object that will be sending your
                    // SQL statements to the DBMS
                    stmt = conn.createStatement();

                } finally {
                   
                }
            } catch (ClassNotFoundException | SQLException oops) {
            }
        } else {
            
        }
            return stmt;
    }

    static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Throwable whatever) {
            }
        }
    }

    static void close(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (Throwable whatever) {
            }
        }
    }

    static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Throwable whatever) {
            }
        }
    }
}
