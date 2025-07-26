package ra.academy_project.dao;

import ra.academy_project.model.CourseAndStudentStatistics;
import ra.academy_project.model.StudentsByCourse;

import java.util.List;
import java.util.Optional;

public interface StatisticsDAO {
    Optional<CourseAndStudentStatistics> statisticsCount ();

    List<StudentsByCourse> findAll();

    List<StudentsByCourse> getTop5Course ();

    List<StudentsByCourse> getCourseMoreThan10Student ();
}
