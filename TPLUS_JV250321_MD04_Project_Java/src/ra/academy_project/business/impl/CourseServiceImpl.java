package ra.academy_project.business.impl;

import ra.academy_project.business.CourseService;
import ra.academy_project.dao.CourseDAO;
import ra.academy_project.dao.impl.CourseDAOImpl;
import ra.academy_project.model.Course;

import java.util.List;
import java.util.Optional;

public class CourseServiceImpl implements CourseService {
    public final CourseDAO courseDAO;

    public CourseServiceImpl() {
        courseDAO = new CourseDAOImpl();
    }

    @Override
    public List<Course> findAll(int currentPage, int pageSize, String sortOrder) {
        return courseDAO.findAll(currentPage, pageSize,  sortOrder);
    }

    @Override
    public int getTotalPages(int pageSize) {
        return courseDAO.getTotalPages(pageSize);
    }

    @Override
    public void displayCourses(List<Course> courses) {
        if (courses.isEmpty()) {
            System.out.println("Danh sach trong.");
        } else {
            System.out.printf("%76s\n", "+--------------------------------------------------------------------------+");
            System.out.printf("| %-3s | %-22s | %-10s | %-15s | %-10s |\n"
                    , "ID", "Ten khoa hoc", "Thoi luong", "Giang vien", "Ngay them");
            System.out.printf("%76s\n", "+--------------------------------------------------------------------------+");
            courses.forEach(System.out::println);
            System.out.printf("%76s\n", "+--------------------------------------------------------------------------+");
        }
    }

    @Override
    public void addCourse(Course course) {
        if (courseDAO.save(course)) {
            System.out.println("Them khoa hoc thanh cong");
        } else {
            System.out.println("Co loi trong qua trinh them khoa hoc");
        }
    }

    @Override
    public Optional<Course> findCourseById(int courseId) {
        return courseDAO.findById(courseId);
    }

    @Override
    public void updateCourse(Course course) {
        if (courseDAO.update(course)) {
            System.out.println("Cap nhat khoa hoc thanh cong");
        } else {
            System.out.println("Co loi trong qua trinh cap nhat khoa hoc");
        }
    }

    @Override
    public void deleteCourse(Course course) {
        if (courseDAO.delete(course.getId())) {
            System.out.println("Xoa khoa hoc thanh cong");
        } else {
            System.out.println("Co loi trong qua trinh xoa khoa hoc");
        }
    }

    @Override
    public List<Course> findCourseByName(String name, int currentPage, int pageSize) {
        return courseDAO.findByName(name, currentPage, pageSize);
    }

    @Override
    public int getTotalPagesByFoundName(String name, int pageSize) {
        return courseDAO.getTotalPagesByFoundName(name, pageSize);
    }
}
