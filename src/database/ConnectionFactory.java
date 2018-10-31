package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Kendra on 10/23/2018.
 */
public class ConnectionFactory {

    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e) {
// ERROR! Could not load database driver
        }
    }

    public static Connection openConnection() {
        try {
            String dbName = "pipelineInfo.sqlite";
            String connectionURL = "jdbc:sqlite:" + dbName;
            Connection connection = null;
            connection = DriverManager.getConnection(connectionURL);
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection(Connection connection, boolean commit) {
        try {
            if (commit) {
                connection.commit();
            } else {
                connection.rollback();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
