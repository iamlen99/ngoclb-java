package ra.academy_project.dao;

import ra.academy_project.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDAO {
    Optional<Student> getAccount(String email, String password);

    List<Student> findAll(int currentPage, int pageSize, String sortOrder);

    int getTotalPages(int pageSize);

    boolean save(Student student);

    boolean isExistEmail(String email);


    boolean update (Student student);

    Optional<Student> findById(int id);

    boolean delete(int id);

    List<Student> search(String searchValue, int currentPage, int pageSize);

    int getTotalPages(String searchValue, int pageSize);

    boolean changePassword(int studentId, String password);
}
