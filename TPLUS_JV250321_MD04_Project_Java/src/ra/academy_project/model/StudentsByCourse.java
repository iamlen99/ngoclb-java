package ra.academy_project.model;

public class StudentsByCourse {
    private String courseName;
    private int countStudent;

    public StudentsByCourse(String courseName, int countStudent) {
        this.courseName = courseName;
        this.countStudent = countStudent;
    }

    public StudentsByCourse() {};

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCountStudent() {
        return countStudent;
    }

    public void setCountStudent(int countStudent) {
        this.countStudent = countStudent;
    }

    @Override
    public String toString() {
        return String.format("| %s | %s |",
                center(this.courseName, 22),
                center(String.valueOf(this.countStudent), 17));
    }

    private String center(String text, int width) {
        if (text.length() >= width) return text;

        int leftPadding = (width - text.length()) / 2;
        int rightPadding = width - text.length() - leftPadding;

        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
    }
}
