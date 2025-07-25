package ra.academy_project.business.impl;

import ra.academy_project.business.StudentService;
import ra.academy_project.dao.StudentDAO;
import ra.academy_project.dao.impl.StudentDAOImpl;
import ra.academy_project.model.Student;

import java.util.List;
import java.util.Optional;

public class StudentServiceImpl implements StudentService {
    public final StudentDAO studentDAO;
    public StudentServiceImpl() {
        studentDAO = new StudentDAOImpl();
    }

    @Override
    public Optional<Student> login(String email, String password) {
        return studentDAO.login(email, password);
    }

    @Override
    public List<Student> findAllStudents() {
        return studentDAO.findAll();
    }

    @Override
    public void addStudent(Student student) {
        if (studentDAO.save(student)) {
            System.out.println("Them sinh vien thanh cong");
        } else {
            System.out.println("Co loi trong qua trinh them sinh vien");
        }
    }

    @Override
    public boolean isExistEmail(String email) {
        return studentDAO.isExistEmail(email);
    }

    @Override
    public Optional<Student> findStudentById(int id) {
        return studentDAO.findById(id);
    }

    @Override
    public void updateStudent(Student student) {
        if(studentDAO.update(student)) {
            System.out.println("Cap nhat thanh cong");
        } else {
            System.out.println("Co loi trong qua trinh cap nhat");
        }
    }

    @Override
    public void deleteStudent(Student student) {
        if (studentDAO.delete(student.getId())) {
            System.out.println("Da xoa thanh cong");
        } else {
            System.out.println("Co loi trong qua trinh xoa");
        }
    }

    @Override
    public List<Student> searchStudents(String searchValue) {
        return studentDAO.search(searchValue);
    }

    @Override
    public void changePassword(int studentId, String newPassword) {
        if (studentDAO.changePassword(studentId, newPassword)) {
            System.out.println("Thay doi mat khau thanh cong");
        } else {
            System.out.println("Co loi trong qua trinh thay doi mat khau");
        }
    }
}
