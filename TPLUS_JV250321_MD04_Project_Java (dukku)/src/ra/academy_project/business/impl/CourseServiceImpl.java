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
    public List<Course> findAll() {
        return  courseDAO.findAll();
    }

    @Override
    public void addCourse(Course course) {
        if(courseDAO.save(course)) {
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
    public void findCourseByName(String name) {
        List<Course> courses = courseDAO.findByName(name);
        if (courses.isEmpty()) {
            System.out.println("Khong tim duoc khoa hoc nao voi tu khoa ban vua nhap.");
        } else {
            courses.forEach(System.out::println);
        }
    }
}
