package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Singleton
public class DbConnection {
    private static DbConnection dbConnection;//rule 1
    private Connection connection;

    private DbConnection() throws ClassNotFoundException, SQLException {//rule 2
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/lms", "root", "1234");
    }

    public static DbConnection getInstance() throws SQLException, ClassNotFoundException {//rule 3
        if (dbConnection == null) {
            dbConnection = new DbConnection();
        }
        return dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }
}
