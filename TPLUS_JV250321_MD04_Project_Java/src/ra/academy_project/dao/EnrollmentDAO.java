package ra.academy_project.dao;

import ra.academy_project.model.CourseEnrolledStudent;
import ra.academy_project.model.Enrollment;
import ra.academy_project.model.EnrollmentStatus;

import java.util.List;
import java.util.Optional;

public interface EnrollmentDAO {
    boolean save (Enrollment enrollment);

    List<Enrollment> findByStudentId (int studentId, int currentPage, int pageSize, String sortOrder);

    int getEnrolledTotalPages (int studentId, int pageSize);

    boolean cancel(int enrollmentId);

    Optional<Enrollment> findById(int enrollmentId);

    List<CourseEnrolledStudent> findAll(int currentPage, int pageSize);

    int getTotalPages(int pageSize);

    boolean approve(int enrollmentId, EnrollmentStatus status);

    boolean delete(int enrollmentId);

    int countWaittingStatus();

    int countAlreadyEnrollment(int studentId, int courseId);
}
