package ra.academy_project.model;

import ra.academy_project.validation.Validator;

public class CourseEnrolledStudent extends Enrollment{
    private String studentName;

    public CourseEnrolledStudent() {};

    public CourseEnrolledStudent(String studentName) {
        super();
        this.studentName = studentName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return String.format("| %-12s | %-12s | %-22s | %-11s | %-20s | %-20s | %-10s |", super.getEnrollmentId()
                , super.getStudentId() ,this.studentName, super.getCourseId(), super.getCourseName()
                , super.getRegisteredDate().format(Validator.dateTimeFormatter), super.getStatus());
    }
}
