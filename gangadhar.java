import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
        String sqlQuery = "SELECT id, username, email FROM users";

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

        System.out.println("Connecting to database...");

        // Try-with-resources block using custom driver registration to guarantee success
        try {
            Connection connection;
            if (mysqlDriver != null) {
                Properties connectionProps = new Properties();
                connectionProps.setProperty("user", USERNAME);
                connectionProps.setProperty("password", PASSWORD);
                connection = mysqlDriver.connect(JDBC_URL, connectionProps);
            } else {
                // Fallback to traditional method if download failed but driver is present locally
                connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            }

            try (
                Connection conn = connection;
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlQuery)
            ) {
                System.out.println("Successfully connected to the database!");
                System.out.println("----------------------------------------");

                // Loop through the ResultSet and print retrieved data
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String email = resultSet.getString("email");

                    System.out.printf("User ID: %d, Username: %s, Email: %s%n", id, username, email);
                }
            }
        } catch (SQLException e) {
            // Handle database credentials issues or missing local DB setups
            System.err.println("\nDatabase query execution failed!");
            System.err.println("Error Message: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("\n💡 Pro-tip: Ensure your local MySQL server is active and 'mydatabase' table exists.");
        }
    }
}
