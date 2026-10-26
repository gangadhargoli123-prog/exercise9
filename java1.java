import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Main {

    // Define constants for JDBC URL, username, and password
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String USERNAME = "root"; // Replace with your MySQL username
    private static final String PASSWORD = "password"; // Replace with your MySQL password

    // Central Maven repository URL for the MySQL Connector/J driver
    private static final String DRIVER_URL = "https://maven.org";
    private static final String DRIVER_FILE_NAME = "mysql-connector-j-8.3.0.jar";

    public static void main(String[] args) {
        // Setup the driver file destination
        File driverFile = new File(DRIVER_FILE_NAME);

        // Auto-download the driver if it's missing from the current folder
        if (!driverFile.exists()) {
            System.out.println("Downloading MySQL JDBC Driver automatically... Please wait.");
            try (BufferedInputStream in = new BufferedInputStream(new URL(DRIVER_URL).openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream(driverFile)) {
                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
                System.out.println("Driver downloaded successfully!");
            } catch (Exception e) {
                System.err.println("Failed to auto-download driver. Please connect to the internet.");
            }
        }

        // Dynamically load the driver into memory
        Driver mysqlDriver = null;
        try {
            URLClassLoader classLoader = new URLClassLoader(new URL[]{driverFile.toURI().toURL()});
            Class<?> driverClass = Class.forName("com.mysql.cj.jdbc.Driver", true, classLoader);
            mysqlDriver = (Driver) driverClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.err.println("Error initializing the downloaded driver: " + e.getMessage());
        }

        // SQL Queries
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(100) NOT NULL, "
                + "email VARCHAR(100) NOT NULL"
                + ")";
        
        String insertSQL = "INSERT INTO users (name, email) VALUES (?, ?)";

        // Establish connection and execute queries
        try {
            Connection connection;
            if (mysqlDriver != null) {
                Properties connectionProps = new Properties();
                connectionProps.setProperty("user", USERNAME);
                connectionProps.setProperty("password", PASSWORD);
                connection = mysqlDriver.connect(JDBC_URL, connectionProps);
            } else {
                connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            }

            // Using try-with-resources for Statement and PreparedStatement management
            try (
                Connection conn = connection;
                Statement statement = conn.createStatement();
                PreparedStatement pstmt = conn.prepareStatement(insertSQL)
            ) {
                // Step 4: Create table if it doesn't exist
                statement.execute(createTableSQL);

                // Step 5 & 6: Insert first record
                pstmt.setString(1, "John Doe");
                pstmt.setString(2, "johndoe@example.com");
                pstmt.executeUpdate();

                // Step 7: Insert second record
                pstmt.setString(1, "Jane Doe");
                pstmt.setString(2, "janedoe@example.com");
                pstmt.executeUpdate();

                // Step 8: Print success confirmation
                System.out.println("Data inserted successfully.");

            }
        } catch (SQLException e) {
            // Step 9: Handle SQLException
            System.err.println("\nDatabase connection or insertion failed!");
            System.err.println("Error Message: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
        }
    }
}
