package ra.academy_project.business;

import ra.academy_project.model.CourseAndStudentStatistics;
import ra.academy_project.model.StudentsByCourse;

import java.util.List;
import java.util.Optional;

public interface StatisticsService {
    Optional<CourseAndStudentStatistics> statisticsCount ();

    List<StudentsByCourse> findAllStudentsByCourse();
}
