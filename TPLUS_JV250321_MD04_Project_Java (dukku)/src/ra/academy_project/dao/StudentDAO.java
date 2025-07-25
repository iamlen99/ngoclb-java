package ra.academy_project.dao;

import ra.academy_project.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDAO {
    Optional<Student> login(String email, String password);

    List<Student> findAll();

    boolean save(Student student);

    boolean isExistEmail(String email);

//    int count(String email);
//    int count(String email, String status);
//    int count(String name);
//
//    List<Student> list(String name);
//    List<Student> list(String name, List<String> sortKeys, List<String> sortDirs);
//    List<Student> list(String name, List<String> sortKeys, List<String> sortDirs, int offset, int limit);

    boolean update (Student student);

    Optional<Student> findById(int id);

    boolean delete(int id);

    List<Student> search(String searchValue);
}
