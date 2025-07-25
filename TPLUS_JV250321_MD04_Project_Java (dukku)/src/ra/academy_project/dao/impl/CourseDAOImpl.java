package ra.academy_project.dao.impl;

import ra.academy_project.dao.CourseDAO;
import ra.academy_project.model.Course;
import ra.academy_project.utils.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseDAOImpl implements CourseDAO {

    @Override
    public List<Course> findAll() {
        Connection conn = null;
        CallableStatement callStmt = null;
        List<Course> courseList = null;
        try {
            conn = DBUtil.openConnection();
            callStmt = conn.prepareCall("{call find_all_courses()}");
            ResultSet rs = callStmt.executeQuery();
            courseList = new ArrayList<>();
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDuration(rs.getInt("duration"));
                course.setInstructor(rs.getString("instructor"));
                course.setCreateAt(LocalDate.parse(rs.getString("create_at")));
                courseList.add(course);
            }
        } catch (Exception e) {
            System.out.println("Co loi khi lay danh sach khoa hoc: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtil.closeCallableStatement(callStmt);
            DBUtil.closeConnection(conn);
        }
        return courseList;
    }

    @Override
    public boolean save(Course  course) {
        Connection conn = null;
        CallableStatement callStmt = null;
        try {
            conn = DBUtil.openConnection();
            callStmt = conn.prepareCall("{call add_course(?,?,?)}");
            callStmt.setString(1, course.getName());
            callStmt.setInt(2, course.getDuration());
            callStmt.setString(3, course.getInstructor());
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
    public boolean update(Course course) {
        Connection conn = null;
        CallableStatement callStmt = null;
        try {
            conn = DBUtil.openConnection();
            callStmt = conn.prepareCall("{call update_course(?,?,?,?)}");
            callStmt.setInt(1, course.getId());
            callStmt.setString(2, course.getName());
            callStmt.setInt(3, course.getDuration());
            callStmt.setString(4, course.getInstructor());
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
    public Optional<Course> findById(int id) {
        Optional<Course> courses = Optional.empty();
        Connection conn = null;
        CallableStatement callStmt = null;
        try {
            conn = DBUtil.openConnection();
            callStmt = conn.prepareCall("{call find_course_by_id(?)}");
            callStmt.setInt(1, id);
            ResultSet rs = callStmt.executeQuery();
            if (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDuration(rs.getInt("duration"));
                course.setInstructor(rs.getString("instructor"));
                course.setCreateAt(LocalDate.parse(rs.getString("create_at")));
                courses = Optional.of(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeCallableStatement(callStmt);
            DBUtil.closeConnection(conn);
        }
        return courses;
    }

    @Override
    public boolean delete(int id) {
        Connection conn = null;
        CallableStatement callStmt = null;
        try {
            conn = DBUtil.openConnection();
            callStmt = conn.prepareCall("{call delete_course_by_id(?)}");
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
    public List<Course> findByName(String name) {
        Connection conn = null;
        CallableStatement callStmt = null;
        List<Course> courseList = null;
        try {
            conn = DBUtil.openConnection();
            callStmt = conn.prepareCall("{call search_course_by_name(?)}");
            callStmt.setString(1, name);
            ResultSet rs = callStmt.executeQuery();
            courseList = new ArrayList<>();
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDuration(rs.getInt("duration"));
                course.setInstructor(rs.getString("instructor"));
                course.setCreateAt(LocalDate.parse(rs.getString("create_at")));
                courseList.add(course);
            }
        } catch (Exception e) {
            System.out.println("Co loi khi tim kiem thong tin khoa hoc: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtil.closeCallableStatement(callStmt);
            DBUtil.closeConnection(conn);
        }
        return courseList;
    }
}
