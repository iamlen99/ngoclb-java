package ra.academy_project.presentation;

import ra.academy_project.business.CourseService;
import ra.academy_project.business.StudentService;
import ra.academy_project.business.impl.CourseServiceImpl;
import ra.academy_project.business.impl.StudentServiceImpl;
import ra.academy_project.model.Course;
import ra.academy_project.model.Student;
import ra.academy_project.validation.Validator;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class StudentPresentation {
    public final StudentService studentService;
    public final CourseService courseService;

    public StudentPresentation() {
        studentService = new StudentServiceImpl();
        courseService = new CourseServiceImpl();
    }

    public void login(Scanner scanner) {
        int attempt = 0;
        final int maxAttempts = 3;
        do {
            System.out.println("============================ DANG NHAP HOC VIEN ============================");
            String email = Validator.inputNotEmptyData(scanner, "Email: ");
            String password = Validator.inputNotEmptyData(scanner, "Mat khau: ");
            System.out.println("============================================================================");

            Optional<Student> student = studentService.login(email, password);
            if (student.isPresent()) {
                System.out.println("Dang nhap thanh cong");
                displayStudentMenu(scanner);
                break;
            } else {
                attempt++;
                if (attempt >= maxAttempts) {
                    System.err.println("Ban da nhap sai 3 lan. Thuc hien thoat dang nhap.");
                    break;
                } else {
                    System.err.println("Sai ten dang nhap hoac mat khau!");
                }
            }

        } while (true);
    }

    public void displayStudentMenu(Scanner scanner) {
        boolean isExit = false;
        do {
            System.out.println("============================== MENU HOC VIEN ===============================");
            System.out.println("1. Xem danh sach khoa hoc");
            System.out.println("2. Dang ky khoa hoc");
            System.out.println("3. Xem khoa hoc da dang ky");
            System.out.println("4. Huy dang ky (neu chua bat dau)");
            System.out.println("5. Doi mat khau");
            System.out.println("6. Dang xuat");
            System.out.println("============================================================================");

            int choice = Validator.inputValidInteger(scanner, "Nhap lua chon: ");

            switch (choice) {
                case 1:
                    displayCourseMenu(scanner);
                    break;

                case 2:
                    break;

                case 3:
                    break;

                case 4:
                    break;

                case 5:
                    break;

                case 6:
                    isExit = true;
                    break;

                default:
                    System.out.println("Vui long chon tu 1-6");
            }
        } while (!isExit);
    }

    public void displayCourseMenu(Scanner scanner) {
        boolean isExit = false;
        do {
            System.out.println("1. Xem danh sach khoa hoc dang co");
            System.out.println("2. Tim kiem khoa hoc theo ten");
            System.out.println("3. Quay lai menu hoc vien");

            int choice = Validator.inputValidInteger(scanner, "Nhap lua chon: ");

            switch (choice) {
                case 1:
                    displayAllCourses();
                    break;

                case 2:
                    findCourseByName(scanner);
                    break;

                case 3:
                    isExit = true;
                    break;

                default:
                    System.out.println("Vui long chon tu 1-3");
            }
        } while (!isExit);
    }

    public void displayAllCourses() {
        List<Course> courseList = courseService.findAll();
        if (courseList.isEmpty()) {
            System.out.println("Danh sach trong");
        } else {
            courseList.forEach(System.out::println);
        }
    }

    public void findCourseByName(Scanner scanner) {
        System.out.print("Nhap ten khoa hoc can tim: ");
        String courseName = scanner.nextLine();
        courseService.findCourseByName(courseName);
    }
}
