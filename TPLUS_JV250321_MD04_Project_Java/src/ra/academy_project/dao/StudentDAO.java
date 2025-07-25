package ra.academy_project.dao;

import ra.academy_project.model.CourseEnrolledStudent;
import ra.academy_project.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDAO {
    Optional<Student> login(String email, String password);

    List<Student> findAll();

    boolean save(Student student);

    boolean isExistEmail(String email);


    boolean update (Student student);

    Optional<Student> findById(int id);

    boolean delete(int id);

    List<Student> search(String searchValue);

    boolean changePassword(int studentId, String password);
}
