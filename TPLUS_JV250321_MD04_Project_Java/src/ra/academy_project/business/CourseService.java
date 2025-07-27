package ra.academy_project.business;

import ra.academy_project.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> findAll(int currentPage, int pageSize,  String sortOrder);

    int getTotalPages(int pageSize);

    void displayCourses(List<Course> courses);

    void addCourse(Course course);

    Optional<Course> findCourseById(int courseId);

    void updateCourse(Course course);

    void deleteCourse(Course course);

    List<Course> findCourseByName(String name, int currentPage, int pageSize);

    int getTotalPagesByFoundName(String name, int pageSize);

}
