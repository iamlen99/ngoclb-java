package ra.academy_project.business;

import ra.academy_project.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> findAll();

    void addCourse(Course course);

    Optional<Course> findCourseById(int courseId);

    void updateCourse(Course course);

    void deleteCourse(Course course);

    void findCourseByName(String name);
}
