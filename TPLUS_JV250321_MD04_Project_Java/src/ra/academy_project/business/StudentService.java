package ra.academy_project.business;
import ra.academy_project.model.Course;
import ra.academy_project.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Optional<Student> getStudentAccount(String email, String password);

    List<Student> findAllStudents(int currentPage, int pageSize, String sortOrder);

    int getTotalPages(int pageSize);

    void displayStudents(List<Student> students);

    void addStudent(Student student);

    boolean isExistEmail(String email);

    Optional<Student> findStudentById(int id);

    void updateStudent(Student student);

    void deleteStudent(Student student);

    List<Student> searchStudents(String searchValue,int currentPage, int pageSize);

    int getSearchedTotalPages(String searchValue, int pageSize);

    void changePassword(int studentId, String newPassword);
}
