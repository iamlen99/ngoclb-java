package ra.academy_project.business;
import ra.academy_project.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Optional<Student> login(String email, String password);

    List<Student> findAllStudents();

    void addStudent(Student student);

    boolean isExistEmail(String email);

    Optional<Student> findStudentById(int id);

    void updateStudent(Student student);

    void deleteStudent(Student student);

    List<Student> searchStudents(String searchValue);

    void changePassword(int studentId, String newPassword);
}
