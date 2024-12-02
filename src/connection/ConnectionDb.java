package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionDb {

    // 1.create 2.get 3.release(boolean) 4.close

    private String URL = "jdbc:mysql://localhost:3306/";
    private String USER = "sa";
    private String PASSWORD = "1234";
    private List<Connection> connections = new ArrayList<>();
    private int INITIONAL_SIZE = 10;

    public ConnectionDb() throws SQLException {
        for (int i = 0; i < INITIONAL_SIZE; i++) {
            createConnection();
        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public Connection getConnection() throws SQLException {
        try {
            if (connections.isEmpty()) {
                return createConnection();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connections.remove(connections.size() - 1);
    }
    private boolean releaseConnection(Connection connection) {
        connections.add(connection);
        return connections.size() == INITIONAL_SIZE;
    }

}
