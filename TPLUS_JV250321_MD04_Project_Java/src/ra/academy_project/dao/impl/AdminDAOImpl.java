package ra.academy_project.dao.impl;

import ra.academy_project.dao.AdminDAO;
import ra.academy_project.model.Admin;
import ra.academy_project.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class AdminDAOImpl implements AdminDAO {

    @Override
    public Optional<Admin> getAccount(String username, String password) {
        Optional<Admin> result = Optional.empty();
        Connection conn = null;
        PreparedStatement preStmt = null;
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        try {
            conn = DBUtil.openConnection();
            preStmt = conn.prepareStatement(sql);
            preStmt.setString(1, username);
            preStmt.setString(2, password);
            ResultSet rs = preStmt.executeQuery();
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                result = Optional.of(admin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closePreparedStatement(preStmt);
            DBUtil.closeConnection(conn);
        }
        return result;
    }
}
