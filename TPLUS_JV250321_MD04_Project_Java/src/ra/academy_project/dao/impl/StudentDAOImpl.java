package ra.academy_project.dao.impl;

import ra.academy_project.dao.StudentDAO;
import ra.academy_project.model.CourseEnrolledStudent;
import ra.academy_project.model.Student;
import ra.academy_project.utils.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public Optional<Student> login(String email, String password) {
        Optional<Student> result = Optional.empty();
        Connection conn = null;
        PreparedStatement preStmt = null;
        String sql = "SELECT * FROM student WHERE email = ? AND password = ?";
        try {
            conn = DBUtil.openConnection();
            preStmt = conn.prepareStatement(sql);
            preStmt.setString(1, email);
            preStmt.setString(2, password);
            ResultSet rs = preStmt.executeQuery();
            if (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setDob(LocalDate.parse(rs.getString("dob")));
                student.setEmail(rs.getString("email"));
                student.setSex(rs.getBoolean("sex"));
                student.setPhone(rs.getString("phone"));
                student.setPassword(rs.getString("password"));
                student.setCreateAt(LocalDate.parse(rs.getString("create_at")));
                result = Optional.of(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closePreparedStatement(preStmt);
            DBUtil.closeConnection(conn);
        }
        return result;
    }

    @Override
    public List<Student> findAll() {
        Connection conn = null;
        CallableStatement callStmt = null;
        List<Student> studentList = null;
        try {
            conn = DBUtil.openConnection();
            callStmt = conn.prepareCall("{call find_all_students()}");
            ResultSet rs = callStmt.executeQuery();
            studentList = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setDob(LocalDate.parse(rs.getString("dob")));
                student.setEmail(rs.getString("email"));
                student.setSex(rs.getBoolean("sex"));
                student.setPhone(rs.getString("phone"));
                student.setPassword(rs.getString("password"));
                student.setCreateAt(LocalDate.parse(rs.getString("create_at")));
                studentList.add(student);
            }
        } catch (Exception e) {
            System.out.println("Co loi khi lay danh sach hoc vien: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtil.closeCallableStatement(callStmt);
            DBUtil.closeConnection(conn);
        }
        return studentList;
    }

    @Override
    public boolean save(Student student) {
        Connection conn = null;
        CallableStatement callStmt = null;
        try {
            conn = DBUtil.openConnection();
            callStmt = conn.prepareCall("{call add_student(?,?,?,?,?,?)}");
            callStmt.setString(1, student.getName());
            callStmt.setDate(2, Date.valueOf(student.getDob()));
            callStmt.setString(3, student.getEmail());
            callStmt.setBoolean(4, student.isSex());
            callStmt.setString(5, student.getPhone());
            callStmt.setString(6, student.getPassword());
            callStmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Co loi khi luu hoc vien: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtil.closeCallableStatement(callStmt);
            DBUtil.closeConnection(conn);
        }
        return false;
    }

    @Override
    public boolean isExistEmail(String email) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        String checkExistEmailSql = "SELECT count(id) FROM student WHERE email = ?";
        try {
            conn = DBUtil.openConnection();
            preStmt = conn.prepareStatement(checkExistEmailSql);
            preStmt.setString(1, email);
           ResultSet rs = preStmt.executeQuery();
           return rs.next() && rs.getInt(1) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closePreparedStatement(preStmt);
            DBUtil.closeConnection(conn);
        }
        return false;
    }

    @Override
    public boolean update(Student student) {
        Connection conn = null;
        CallableStatement callStmt = null;
        try {
            conn = DBUtil.openConnection();
            callStmt = conn.prepareCall("{call update_student(?,?,?,?,?,?,?)}");
            callStmt.setInt(1, student.getId());
            callStmt.setString(2, student.getName());
            callStmt.setDate(3, Date.valueOf(student.getDob()));
            callStmt.setString(4, student.getEmail());
            callStmt.setBoolean(5, student.isSex());
            callStmt.setString(6, student.getPhone());
            callStmt.setString(7, student.getPassword());
            callStmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeCallableStatement(callStmt);
            DBUtil.closeConnection(conn);
        }
        return false;
    }

    @Override
    public Optional<Student> findById(int id) {
        Optional<Student> studentOptional = Optional.empty();
        Connection conn = null;
        PreparedStatement preStmt = null;
        String findByIdSql = "SELECT * FROM student WHERE id = ?";
        try {
            conn = DBUtil.openConnection();
            preStmt = conn.prepareStatement(findByIdSql);
            preStmt.setInt(1, id);
            ResultSet rs = preStmt.executeQuery();
            if (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setDob(LocalDate.parse(rs.getString("dob")));
                student.setEmail(rs.getString("email"));
                student.setSex(rs.getBoolean("sex"));
                student.setPhone(rs.getString("phone"));
                student.setPassword(rs.getString("password"));
                student.setCreateAt(LocalDate.parse(rs.getString("create_at")));
                studentOptional = Optional.of(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closePreparedStatement(preStmt);
            DBUtil.closeConnection(conn);
        }
        return studentOptional;
    }

    @Override
    public boolean delete(int id) {
        Connection conn = null;
        CallableStatement callStmt = null;
        try {
            conn = DBUtil.openConnection();
            callStmt = conn.prepareCall("{call delete_student(?)}");
            callStmt.setInt(1, id);
            callStmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeCallableStatement(callStmt);
            DBUtil.closeConnection(conn);
        }
        return false;
    }

    @Override
    public List<Student> search(String searchValue) {
        Connection conn = null;
        CallableStatement callStmt = null;
        List<Student> studentList = null;
        try {
            conn = DBUtil.openConnection();
            callStmt = conn.prepareCall("{call search_students(?)}");
            callStmt.setString(1, searchValue);
            ResultSet rs = callStmt.executeQuery();
            studentList = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setDob(LocalDate.parse(rs.getString("dob")));
                student.setEmail(rs.getString("email"));
                student.setSex(rs.getBoolean("sex"));
                student.setPhone(rs.getString("phone"));
                student.setPassword(rs.getString("password"));
                student.setCreateAt(LocalDate.parse(rs.getString("create_at")));
                studentList.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeCallableStatement(callStmt);
            DBUtil.closeConnection(conn);
        }
        return studentList;
    }

    @Override
    public boolean changePassword(int studentId, String password) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        String  changePasswordSql = "UPDATE student SET password = ? WHERE id = ?";
        try {
            conn = DBUtil.openConnection();
            preStmt = conn.prepareCall(changePasswordSql);
            preStmt.setString(1, password);
            preStmt.setInt(2, studentId);
            preStmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closePreparedStatement(preStmt);
            DBUtil.closeConnection(conn);
        }
        return false;
    }
}
