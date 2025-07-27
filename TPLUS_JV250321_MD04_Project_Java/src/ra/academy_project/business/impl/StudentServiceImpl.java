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
    public Optional<Student> getStudentAccount(String email, String password) {
        return studentDAO.getAccount(email, password);
    }

    @Override
    public List<Student> findAllStudents(int currentPage, int pageSize, String sortOrder) {
        return studentDAO.findAll(currentPage, pageSize, sortOrder);
    }

    @Override
    public int getTotalPages(int pageSize) {
        return studentDAO.getTotalPages(pageSize);
    }

    @Override
    public void displayStudents(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println("Danh sach trong");
        } else {
            System.out.printf("%127s\n", "+-----------------------------------------------------------------------------------------------------------------------------+");
            System.out.printf("| %-3s | %-20s | %-10s | %-25s | %-9s | %-13s | %-12s | %-10s |\n", "ID", "Ho ten", "Ngay sinh"
                    , "Email", "Gioi tinh", "So dien thoai", "Mat khau", "Ngay them");
            System.out.printf("%127s\n", "+-----------------------------------------------------------------------------------------------------------------------------+");
            students.forEach(System.out::println);
            System.out.printf("%127s\n", "+-----------------------------------------------------------------------------------------------------------------------------+");
        }
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
        if (studentDAO.update(student)) {
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
    public List<Student> searchStudents(String searchValue, int currentPage, int pageSize) {
        return studentDAO.search(searchValue, currentPage, pageSize);
    }

    @Override
    public int getSearchedTotalPages(String searchValue, int pageSize) {
        return studentDAO.getSearchedTotalPages(searchValue, pageSize);
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
