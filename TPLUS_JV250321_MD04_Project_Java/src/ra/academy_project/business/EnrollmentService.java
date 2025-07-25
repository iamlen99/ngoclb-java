package ra.academy_project.business;

import ra.academy_project.model.CourseEnrolledStudent;
import ra.academy_project.model.Enrollment;
import ra.academy_project.model.EnrollmentStatus;

import java.util.List;
import java.util.Optional;

public interface EnrollmentService {
    void addEnrollment(Enrollment enrollment);

    List<Enrollment> getEnrollmentByStudentId(int studentId);

    void cancelEnrollment(Enrollment enrollment);

    Optional<Enrollment> getEnrollmentById(int enrollmentId);

    List<CourseEnrolledStudent> getCourseEnrolledStudents();

    void approveEnrollment(int enrollmentId, EnrollmentStatus status);

    void deleteEnrollment(int enrollmentId);
}
