package ra.academy_project.dao.impl;

import ra.academy_project.dao.StatisticsDAO;
import ra.academy_project.model.CourseAndStudentStatistics;
import ra.academy_project.model.Student;
import ra.academy_project.model.StudentsByCourse;
import ra.academy_project.utils.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StatisticsDAOImpl implements StatisticsDAO {
    @Override
    public Optional<CourseAndStudentStatistics> statisticsCount() {
        Connection conn = null;
        CallableStatement callStmt = null;
        Optional<CourseAndStudentStatistics> listStatistic = Optional.empty();
        try {
            conn = DBUtil.openConnection();
            callStmt = conn.prepareCall("{call statistics_all_courses_and_all_students(?,?)}");
            callStmt.registerOutParameter(1, Types.INTEGER);
            callStmt.registerOutParameter(2, Types.INTEGER);
            callStmt.execute();
            CourseAndStudentStatistics courseAndStudentStatistics = new CourseAndStudentStatistics();
            courseAndStudentStatistics.setCountCourse(callStmt.getInt(1));
            courseAndStudentStatistics.setCountStudent(callStmt.getInt(2));
            listStatistic = Optional.of(courseAndStudentStatistics);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeCallableStatement(callStmt);
            DBUtil.closeConnection(conn);
        }
        return listStatistic;
    }

    @Override
    public List<StudentsByCourse> findAll() {
        Connection conn = null;
        CallableStatement callStmt = null;
        List<StudentsByCourse> listStatistic = null;
        try {
            conn = DBUtil.openConnection();
            callStmt = conn.prepareCall("{call statistics_count_students_by_each_course()}");
            ResultSet rs = callStmt.executeQuery();
            listStatistic = new ArrayList<>();
            while (rs.next()) {
                StudentsByCourse studentsByCourse = new StudentsByCourse();
                studentsByCourse.setCourseName(rs.getString(1));
                studentsByCourse.setCountStudent(rs.getInt(2));
                listStatistic.add(studentsByCourse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeCallableStatement(callStmt);
            DBUtil.closeConnection(conn);
        }
        return listStatistic;
    }
    @Override
    public List<StudentsByCourse> getTop5Course() {
        Connection conn = null;
        CallableStatement callStmt = null;
        List<StudentsByCourse> listStatistic = null;
        try {
            conn = DBUtil.openConnection();
            callStmt = conn.prepareCall("{call get_top_5_course_by_student_count()}");
            ResultSet rs = callStmt.executeQuery();
            listStatistic = new ArrayList<>();
            while (rs.next()) {
                StudentsByCourse studentsByCourse = new StudentsByCourse();
                studentsByCourse.setCourseName(rs.getString(1));
                studentsByCourse.setCountStudent(rs.getInt(2));
                listStatistic.add(studentsByCourse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeCallableStatement(callStmt);
            DBUtil.closeConnection(conn);
        }
        return listStatistic;
    }

    @Override
    public List<StudentsByCourse> getCourseMoreThan10Student() {
        Connection conn = null;
        CallableStatement callStmt = null;
        List<StudentsByCourse> listStatistic = null;
        try {
            conn = DBUtil.openConnection();
            callStmt = conn.prepareCall("{call get_courses_have_more_than_10_student()}");
            ResultSet rs = callStmt.executeQuery();
            listStatistic = new ArrayList<>();
            while (rs.next()) {
                StudentsByCourse studentsByCourse = new StudentsByCourse();
                studentsByCourse.setCourseName(rs.getString(1));
                studentsByCourse.setCountStudent(rs.getInt(2));
                listStatistic.add(studentsByCourse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeCallableStatement(callStmt);
            DBUtil.closeConnection(conn);
        }
        return listStatistic;
    }
}
