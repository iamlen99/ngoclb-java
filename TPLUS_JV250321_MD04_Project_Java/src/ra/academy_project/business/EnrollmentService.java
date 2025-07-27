package ra.academy_project.business;

import ra.academy_project.model.CourseEnrolledStudent;
import ra.academy_project.model.Enrollment;
import ra.academy_project.model.EnrollmentStatus;

import java.util.List;
import java.util.Optional;

public interface EnrollmentService {
    void addEnrollment(Enrollment enrollment);

    List<Enrollment> getEnrollmentByStudentId(int studentId, int currentPage, int pageSize,  String sortOrder);

    void displayEnrollmentByStudentId(List<Enrollment> enrollments);

    int getTotalPagesByEnrolledStudentId(int studentId, int pageSize);

    void cancelEnrollment(Enrollment enrollment);

    Optional<Enrollment> getEnrollmentById(int enrollmentId);

    List<CourseEnrolledStudent> getCourseEnrolledStudents(int currentPage, int pageSize);

    void displayEnrolledStudents(List<CourseEnrolledStudent> students);

    int getEnrolledStudentsTotalPages(int pageSize);

    void approveEnrollment(int enrollmentId, EnrollmentStatus status);

    void deleteEnrollment(int enrollmentId);

    boolean isExistWaitingStatus();

    boolean isAlreadyEnrolled(int studentId, int courseId);
}
