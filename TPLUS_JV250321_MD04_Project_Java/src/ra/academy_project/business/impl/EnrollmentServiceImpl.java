package ra.academy_project.business.impl;

import ra.academy_project.business.EnrollmentService;
import ra.academy_project.dao.EnrollmentDAO;
import ra.academy_project.dao.impl.EnrollmentDAOImpl;
import ra.academy_project.model.CourseEnrolledStudent;
import ra.academy_project.model.Enrollment;
import ra.academy_project.model.EnrollmentStatus;

import java.util.List;
import java.util.Optional;

public class EnrollmentServiceImpl implements EnrollmentService {
    public final EnrollmentDAO enrollmentDAO;

    public EnrollmentServiceImpl() {
        enrollmentDAO = new EnrollmentDAOImpl();
    }

    @Override
    public void addEnrollment(Enrollment enrollment) {
        if (enrollmentDAO.save(enrollment)) {
            System.out.println("Dang ky khoa hoc thanh cong");
        } else {
            System.out.println("Co loi trong qua trinh dang ky khoa hoc");
        }
    }

    @Override
    public List<Enrollment> getEnrollmentByStudentId(int studentId, int currentPage, int pageSize, String sortOrder) {
        return enrollmentDAO.findByStudentId(studentId, currentPage, pageSize, sortOrder);
    }

    @Override
    public void displayEnrollmentByStudentId(List<Enrollment> enrollments) {
        if (enrollments.isEmpty()) {
            System.out.println("Ban chua dang ky khoa hoc nao");
        } else {
            System.out.printf("%104s\n", "+------------------------------------------------------------------------------------------------------+");
            System.out.printf("| %-12s | %-12s | %-11s | %-20s | %-20s | %-10s |\n", "Ma dang ky", "Ma sinh vien"
                    , "Ma khoa hoc", "Ten khoa hoc", "Ngay dang ky", "Trang thai");
            System.out.printf("%104s\n", "+------------------------------------------------------------------------------------------------------+");
            enrollments.forEach(System.out::println);
            System.out.printf("%104s\n", "+------------------------------------------------------------------------------------------------------+");
        }
    }

    @Override
    public int getTotalPagesByEnrolledStudentId(int studentId, int pageSize) {
        return enrollmentDAO.getTotalPages(studentId, pageSize);
    }

    @Override
    public void cancelEnrollment(Enrollment enrollment) {
        if (enrollment.getStatus() == EnrollmentStatus.CANCEL) {
            System.out.println("Khoa hoc nay da duoc huy roi");
            return;
        }

        if (enrollment.getStatus() == EnrollmentStatus.CONFIRM) {
            System.out.println("Khong the huy dang ky khoa hoc vi khoa hoc nay da duoc dang ky thanh cong");
            return;
        }

        if (enrollmentDAO.cancel(enrollment.getEnrollmentId())) {
            System.out.println("Huy dang ky khoa hoc thanh cong");
        } else {
            System.out.println("Co loi trong qua trinh huy dang ky khoa hoc");
        }
    }

    @Override
    public Optional<Enrollment> getEnrollmentById(int enrollmentId) {
        return enrollmentDAO.findById(enrollmentId);
    }

    @Override
    public List<CourseEnrolledStudent> getCourseEnrolledStudents(int currentPage, int pageSize) {
        return enrollmentDAO.findAll(currentPage, pageSize);
    }

    @Override
    public void displayEnrolledStudents(List<CourseEnrolledStudent> students) {
        if (students.isEmpty()) {
            System.out.println("Danh sach trong");
        } else {
            System.out.printf("%129s\n", "+-------------------------------------------------------------------------------------------------------------------------------+");
            System.out.printf("| %-12s | %-12s | %-22s | %-11s | %-20s | %-20s | %-10s |\n", "Ma dang ky", "Ma sinh vien", "Ten sinh vien"
                    , "Ma khoa hoc", "Ten khoa hoc", "Ngay dang ky", "Trang thai");
            System.out.printf("%129s\n", "+-------------------------------------------------------------------------------------------------------------------------------+");
            students.forEach(System.out::println);
            System.out.printf("%129s\n", "+-------------------------------------------------------------------------------------------------------------------------------+");
        }
    }

    @Override
    public int getEnrolledStudentsTotalPages(int pageSize) {
        return enrollmentDAO.getTotalPages(pageSize);
    }

    @Override
    public void approveEnrollment(int enrollmentId, EnrollmentStatus status) {
        if (enrollmentDAO.approve(enrollmentId, status)) {
            System.out.println("Duyet dang ky thanh cong");
        } else {
            System.out.println("Co loi trong qua trinh duyet");
        }
    }

    @Override
    public void deleteEnrollment(int enrollmentId) {
        if (enrollmentDAO.delete(enrollmentId)) {
            System.out.println("Xoa hoc vien khoi khoa hoc thanh cong");
        } else {
            System.out.println("Co loi trong qua trinh xoa hoc vien khoi khoa hoc");
        }
    }

    @Override
    public boolean isExistWaitingStatus() {
        return enrollmentDAO.countWaitingStatus() != 0;
    }

    @Override
    public boolean isAlreadyEnrolled(int studentId, int courseId) {
        return enrollmentDAO.countAlreadyEnrollment(studentId, courseId) != 0;
    }
}
