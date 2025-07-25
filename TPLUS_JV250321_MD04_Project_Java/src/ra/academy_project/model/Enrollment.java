package ra.academy_project.model;

import ra.academy_project.validation.Validator;

import java.time.LocalDateTime;

public class Enrollment {
    private int enrollmentId;
    private int studentId;
    private int courseId;
    private String courseName;
    private LocalDateTime registeredDate;
    private EnrollmentStatus status;

    public Enrollment(int enrollmentId, int studentId, int courseId, String courseName, LocalDateTime registeredDate, EnrollmentStatus status) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.courseName = courseName;
        this.registeredDate = registeredDate;
        this.status = status;
    }

    public Enrollment(){};

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public LocalDateTime getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(LocalDateTime registeredDate) {
        this.registeredDate = registeredDate;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("| %-12s | %-12s | %-11s | %-20s | %-20s | %-10s |", this.enrollmentId, this.studentId, this.courseId
                , this.courseName, this.registeredDate.format(Validator.dateTimeFormatter), this.status);
    }
}
