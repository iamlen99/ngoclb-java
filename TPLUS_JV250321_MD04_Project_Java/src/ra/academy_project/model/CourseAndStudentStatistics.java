package ra.academy_project.model;

public class CourseAndStudentStatistics {
    private int countCourse;
    private int countStudent;

    public CourseAndStudentStatistics(int countCourse, int countStudent) {
        this.countCourse = countCourse;
        this.countStudent = countStudent;
    }
    public CourseAndStudentStatistics() {};

    public void setCountCourse(int countCourse) {
        this.countCourse = countCourse;
    }

    public int getCountStudent() {
        return countStudent;
    }

    public void setCountStudent(int countStudent) {
        this.countStudent = countStudent;
    }

    @Override
    public String toString() {
        return String.format("| So luong khoa hoc | %-3d |\n" +
                "| So luong hoc vien | %-3d |", countCourse, countStudent);
    }
}
