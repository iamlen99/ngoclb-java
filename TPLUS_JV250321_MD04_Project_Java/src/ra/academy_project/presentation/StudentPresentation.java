package ra.academy_project.presentation;

import ra.academy_project.business.CourseService;
import ra.academy_project.business.EnrollmentService;
import ra.academy_project.business.StudentService;
import ra.academy_project.business.impl.CourseServiceImpl;
import ra.academy_project.business.impl.EnrollmentServiceImpl;
import ra.academy_project.business.impl.StudentServiceImpl;
import ra.academy_project.model.*;
import ra.academy_project.pagination.Pagination;
import ra.academy_project.validation.Validator;

import java.util.*;

public class StudentPresentation {
    private static final int pageSize = 5;
    public final StudentService studentService;
    public final CourseService courseService;
    public final EnrollmentService enrollmentService;
    public static Student currentStudent;

    public StudentPresentation() {
        studentService = new StudentServiceImpl();
        courseService = new CourseServiceImpl();
        enrollmentService = new EnrollmentServiceImpl();
    }

    public void loginAsStudent(Scanner scanner) {
        int attempt = 0;
        final int maxAttempts = 3;
        do {
            System.out.println("============================ DANG NHAP HOC VIEN ============================");
            String email = Validator.inputNotEmptyData(scanner, "Nhap email: ");
            String password = Validator.inputNotEmptyData(scanner, "Nhap mat khau: ");
            System.out.println("============================================================================");

            Optional<Student> student = studentService.getStudentAccount(email, password);
            if (student.isPresent()) {
                System.out.println("Dang nhap thanh cong");
                currentStudent = student.get();
                displayStudentMenu(scanner);
                break;
            }

            attempt++;
            if (attempt >= maxAttempts) {
                System.err.println("Ban da nhap sai 3 lan. Thuc hien thoat dang nhap.");
                break;
            }
            System.err.println("Sai ten dang nhap hoac mat khau!");
        } while (true);
    }

    public void displayStudentMenu(Scanner scanner) {
        boolean isExit = false;
        do {
            System.out.println("+============================= MENU HOC VIEN ==============================+");
            System.out.println("|    1. Xem danh sach khoa hoc                                             |");
            System.out.println("|    2. Dang ky khoa hoc                                                   |");
            System.out.println("|    3. Xem khoa hoc da dang ky                                            |");
            System.out.println("|    4. Huy dang ky (neu chua bat dau)                                     |");
            System.out.println("|    5. Doi mat khau                                                       |");
            System.out.println("|    6. Dang xuat                                                          |");
            System.out.println("+==========================================================================+");

            int choice = Validator.inputValidInteger(scanner, "Nhap lua chon: ");

            switch (choice) {
                case 1:
                    displayCourseMenu(scanner);
                    break;

                case 2:
                    addEnrollment(scanner);
                    break;

                case 3:
                    displayEnrollmentMenu(scanner);
                    break;

                case 4:
                    cancelEnrollmentByCourseId(scanner);
                    break;

                case 5:
                    changePassword(scanner);
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
                    displayAllCourses(scanner, "noSort");
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

    public void displayAllCourses(Scanner scanner, String sortOrder) {
        int currentPage = 1;
        int totalPages = courseService.getTotalPages(pageSize);

        do {
            List<Course> courses = courseService.findAll(currentPage, pageSize, sortOrder);
            courseService.displayCourses(courses);
            int nextPage = Pagination.handlePagination(scanner, currentPage, totalPages);
            if (nextPage == -1) break;
            currentPage = nextPage;
        } while (true);
    }

    public void findCourseByName(Scanner scanner) {
        String courseName = Validator.inputNotEmptyData(scanner, "Nhap ten khoa hoc can tim: ");
        int currentPage = 1;
        int totalPages = courseService.getTotalPagesByFoundName(courseName, pageSize);
        if (totalPages == 0) {
            System.out.println("Khong tim thay khoa hoc nao voi tu khoa ban vua nhap");
            return;
        }
        do {
            List<Course> courses = courseService.findCourseByName(courseName, currentPage, pageSize);
            courseService.displayCourses(courses);
            int nextPage = Pagination.handlePagination(scanner, currentPage, totalPages);
            if (nextPage == -1) break;
            currentPage = nextPage;
        } while (true);
    }

    public void addEnrollment(Scanner scanner) {
        int courseId = Validator.inputValidInteger(scanner, "Nhap ma khoa hoc can dang ky: ");
        Optional<Course> course = courseService.findCourseById(courseId);
        if (course.isEmpty()) {
            System.out.println("Khong tim thay khoa hoc co id ban vua nhap");
            return;
        }

        if (enrollmentService.isAlreadyEnrolled(currentStudent.getId(), courseId)) {
            System.out.println("Ban da dang ky khoa hoc nay roi!");
            return;
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(currentStudent.getId());
        enrollment.setCourseId(courseId);
        enrollmentService.addEnrollment(enrollment);
    }

    public void displayEnrollmentMenu(Scanner scanner) {
        boolean isExit = false;
        do {
            System.out.println("1. Xem khoa hoc da dang ky");
            System.out.println("2. Sap xep khoa hoc");
            System.out.println("3. Quay lai menu hoc vien");

            int choice = Validator.inputValidInteger(scanner, "Nhap lua chon: ");

            switch (choice) {
                case 1:
                    displayEnrollments(scanner, "noSorting");
                    break;

                case 2:
                    displaySortEnrollmentMenu(scanner);
                    break;

                case 3:
                    isExit = true;
                    break;

                default:
                    System.out.println("Vui long chon tu 1-3");
            }
        } while (!isExit);
    }

    public void displayEnrollments(Scanner scanner, String sortOrder) {
        int currentPage = 1;
        int totalPages = enrollmentService.getTotalPagesByEnrolledStudentId(currentStudent.getId(), pageSize);

        do {
            List<Enrollment> enrollmentList = enrollmentService.getEnrollmentByStudentId(currentStudent.getId(), currentPage, pageSize, sortOrder);
            enrollmentService.displayEnrollmentByStudentId(enrollmentList);

            int nextPage = Pagination.handlePagination(scanner, currentPage, totalPages);
            if (nextPage == -1) {
                break;
            }
            currentPage = nextPage;
        } while (true);
    }

    public void displaySortEnrollmentMenu(Scanner scanner) {
        boolean isExit = false;
        do {
            System.out.println("1. Sap xep khoa hoc theo ten khoa hoc tang dan");
            System.out.println("2. Sap xep khoa hoc theo ten khoa hoc giam dan");
            System.out.println("3. Sap xep khoa hoc theo ngay dang ky tang dan");
            System.out.println("4. Sap xep khoa hoc theo ngay dang ky giam dan");
            System.out.println("5. Quay lai");

            int choice = Validator.inputValidInteger(scanner, "Nhap lua chon: ");
            switch (choice) {
                case 1:
                    displayEnrollments(scanner, "nameASC");
                    break;

                case 2:
                    displayEnrollments(scanner, "nameDESC");
                    break;

                case 3:
                    displayEnrollments(scanner, "createDateASC");
                    break;

                case 4:
                    displayEnrollments(scanner, "createDateDESC");
                    break;

                case 5:
                    isExit = true;
                    break;

                default:
                    System.out.println("Vui long chon tu 1-5");
            }
        } while (!isExit);
    }

    public void cancelEnrollmentByCourseId(Scanner scanner) {
        int cancelledId = Validator.inputValidInteger(scanner, "Nhap ma dang ky cua khoa hoc can huy: ");
        enrollmentService.getEnrollmentById(cancelledId).ifPresentOrElse(enrollmentService::cancelEnrollment,
                () -> {
                    System.out.println("Khong tim thay ma dang ky cua khoa hoc can huy");
                });
    }

    public void changePassword(Scanner scanner) {
        System.out.print("Xac thuc lai email cua ban: ");
        String inputtedEmail = scanner.nextLine();
        if (!inputtedEmail.equals(currentStudent.getEmail())) {
            System.out.println("Email khong dung, thuc hien quay lai menu hoc vien!");
            return;
        }

        System.out.print("Nhap mat khau cu: ");
        String inputtedPassword = scanner.nextLine();
        if (!inputtedPassword.equals(currentStudent.getPassword())) {
            System.out.println("Mat khau khong dung, thuc hien quay lai menu hoc vien!");
            return;
        }

        String newPassword = Validator.inputPassword(scanner, "Nhap mat khau moi: ");
        if (newPassword.equals(currentStudent.getPassword())) {
            System.out.println("Mat khau moi khong duoc trung mat khau cu");
            return;
        }

        studentService.changePassword(currentStudent.getId(), newPassword);
        currentStudent.setPassword(newPassword);
    }
}
