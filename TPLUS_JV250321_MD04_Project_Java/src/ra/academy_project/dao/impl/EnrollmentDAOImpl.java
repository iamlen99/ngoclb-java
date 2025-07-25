package ra.academy_project.dao.impl;

import ra.academy_project.dao.EnrollmentDAO;
import ra.academy_project.model.CourseEnrolledStudent;
import ra.academy_project.model.Enrollment;
import ra.academy_project.model.EnrollmentStatus;
import ra.academy_project.utils.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnrollmentDAOImpl implements EnrollmentDAO {

    @Override
    public boolean save(Enrollment enrollment) {
        Connection conn = null;
        CallableStatement callStmt = null;
        try {
            conn = DBUtil.openConnection();
            callStmt = conn.prepareCall("{call add_enrollment(?, ?)}");
            callStmt.setInt(1, enrollment.getStudentId());
            callStmt.setInt(2, enrollment.getCourseId());
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
    public List<Enrollment> findByStudentId(int studentId) {
        List<Enrollment> enrollmentList = null;
        Connection conn = null;
        CallableStatement callStmt = null;
        try {
            conn = DBUtil.openConnection();
            callStmt = conn.prepareCall("CALL find_all_enrollments_by_student_id(?)");
            callStmt.setInt(1, studentId);
            ResultSet rs = callStmt.executeQuery();
            enrollmentList = new ArrayList<>();
            while (rs.next()) {
                Enrollment enrollment = new Enrollment();
                enrollment.setEnrollmentId(rs.getInt("id"));
                enrollment.setStudentId(rs.getInt("student_id"));
                enrollment.setCourseId(rs.getInt("course_id"));
                enrollment.setCourseName(rs.getString("course_name"));
                enrollment.setRegisteredDate(LocalDateTime.parse(rs.getString("registered_at")
                        , DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                enrollment.setStatus(EnrollmentStatus.valueOf(rs.getString("status")));
                enrollmentList.add(enrollment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeCallableStatement(callStmt);
            DBUtil.closeConnection(conn);
        }
        return enrollmentList;
    }

    @Override
    public boolean cancel(int enrollmentId) {
        Connection conn = null;
        CallableStatement callStmt = null;
        try {
            conn = DBUtil.openConnection();
            callStmt = conn.prepareCall("{call cancel_enrollment(?)}");
            callStmt.setInt(1, enrollmentId);
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
    public Optional<Enrollment> findById(int enrollmentId) {
        Optional<Enrollment> enrollmentOptional = Optional.empty();
        Connection conn = null;
        CallableStatement callStmt = null;
        try {
            conn = DBUtil.openConnection();
            callStmt = conn.prepareCall("CALL find_enrollment_by_id(?)");
            callStmt.setInt(1, enrollmentId);
            ResultSet rs = callStmt.executeQuery();

            while (rs.next()) {
                Enrollment enrollment = new Enrollment();
                enrollment.setEnrollmentId(rs.getInt("id"));
                enrollment.setStudentId(rs.getInt("student_id"));
                enrollment.setCourseId(rs.getInt("course_id"));
                enrollment.setCourseName(rs.getString("course_name"));
                enrollment.setRegisteredDate(LocalDateTime.parse(rs.getString("registered_at")
                        , DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                enrollment.setStatus(EnrollmentStatus.valueOf(rs.getString("status")));
                enrollmentOptional =  Optional.of(enrollment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeCallableStatement(callStmt);
            DBUtil.closeConnection(conn);
        }
        return enrollmentOptional;
    }

    @Override
    public List<CourseEnrolledStudent> findAll() {
        List<CourseEnrolledStudent> listEnrolledStudent = null;
        Connection conn = null;
        CallableStatement callStmt = null;
        try {
            conn = DBUtil.openConnection();
            callStmt = conn.prepareCall("CALL find_students_by_course()");
            ResultSet rs = callStmt.executeQuery();
            listEnrolledStudent = new ArrayList<>();
            while (rs.next()) {
                CourseEnrolledStudent enrolledStudent = new CourseEnrolledStudent();
                enrolledStudent.setEnrollmentId(rs.getInt("id"));
                enrolledStudent.setStudentId(rs.getInt("student_id"));
                enrolledStudent.setStudentName(rs.getString("student_name"));
                enrolledStudent.setCourseId(rs.getInt("course_id"));
                enrolledStudent.setCourseName(rs.getString("course_name"));
                enrolledStudent.setRegisteredDate(LocalDateTime.parse(rs.getString("registered_at")
                        , DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                enrolledStudent.setStatus(EnrollmentStatus.valueOf(rs.getString("status")));
                listEnrolledStudent.add(enrolledStudent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeCallableStatement(callStmt);
            DBUtil.closeConnection(conn);
        }
        return listEnrolledStudent;
    }

    @Override
    public boolean approve(int enrollmentId, EnrollmentStatus status) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        String approveSql = "update enrollment set status = ? where id=?";
        try {
            conn = DBUtil.openConnection();
            preStmt = conn.prepareStatement(approveSql);
            preStmt.setString(1, status.name());
            preStmt.setInt(2, enrollmentId);
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

    @Override
    public boolean delete(int enrollmentId) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        String approveSql = "delete from enrollment where id=?";
        try {
            conn = DBUtil.openConnection();
            preStmt = conn.prepareStatement(approveSql);
            preStmt.setInt(1, enrollmentId);
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
