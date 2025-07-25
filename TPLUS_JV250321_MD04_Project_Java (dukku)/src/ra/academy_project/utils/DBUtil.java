package ra.academy_project.utils;

import java.sql.*;

public class DBUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/academy_db";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "quxTNL43@";

    public static Connection openConnection() {
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("Loi khi mo ket noi den database: " + e.getMessage());
        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException("Loi khi dong ket noi: " + e.getMessage());
            }
        }
    }

    public static void closeCallableStatement(CallableStatement callSt) {
        if (callSt != null) {
            try {
                callSt.close();
            } catch (SQLException e) {
                throw new RuntimeException("Loi khi dong CallableStatement: " + e.getMessage());
            }
        }
    }

    public static void closePreparedStatement(PreparedStatement preSt) {
        if (preSt != null) {
            try {
                preSt.close();
            } catch (SQLException e) {
                throw new RuntimeException("Loi khi dong PreparedStatement: " + e.getMessage());
            }
        }
    }
}
