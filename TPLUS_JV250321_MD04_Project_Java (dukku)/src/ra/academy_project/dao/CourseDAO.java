package ra.academy_project.dao;

import ra.academy_project.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDAO {
    List<Course> findAll();

    boolean save (Course course);

    boolean update (Course course);

    Optional<Course> findById (int id);

    boolean delete (int id);

    List<Course> findByName(String name);
}
