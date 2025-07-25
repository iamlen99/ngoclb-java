package ra.academy_project.utils.statements;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class StudentStatement {
    private Connection conn;

    public StudentStatement(Connection conn) {
        this.conn = conn;
    }

    public int update(int id, String name, LocalDate date) throws Exception {
        int updatedRow = 0;
        try {
            String sql = "UPDATE student SET name = ?, date=? WHERE id = ?";
            PreparedStatement stmt = this.conn.prepareStatement(sql, new String[]{"id"});
            stmt.setString(1, name);
            stmt.setInt(2, id);
            stmt.setDate(3, Date.valueOf(date));
            updatedRow = stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {

        }
        return updatedRow;
    }

    public int update(int id, LocalDate date) throws Exception {
        int updatedRow = 0;
        try {
            String sql = "UPDATE student SET date=? WHERE id = ?";
            PreparedStatement stmt = this.conn.prepareStatement(sql, new String[]{"id"});
            stmt.setInt(2, id);
            stmt.setDate(1, Date.valueOf(date));
            updatedRow = stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {

        }
        return updatedRow;
    }

}
