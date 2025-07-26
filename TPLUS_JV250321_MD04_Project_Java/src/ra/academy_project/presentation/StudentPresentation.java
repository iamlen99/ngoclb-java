package ra.academy_project.presentation;

import ra.academy_project.business.CourseService;
import ra.academy_project.business.EnrollmentService;
import ra.academy_project.business.StudentService;
import ra.academy_project.business.impl.CourseServiceImpl;
import ra.academy_project.business.impl.EnrollmentServiceImpl;
import ra.academy_project.business.impl.StudentServiceImpl;
import ra.academy_project.model.Course;
import ra.academy_project.model.Enrollment;
import ra.academy_project.model.EnrollmentStatus;
import ra.academy_project.model.Student;
import ra.academy_project.validation.Validator;

import java.util.*;

public class StudentPresentation {
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
            System.out.printf("| %-3s | %-20s | %-10s | %-15s | %-10s |\n", "ID", "Ten khoa hoc", "Thoi luong", "Giang vien", "Ngay them");
            courseList.forEach(System.out::println);
        }
    }

    public void findCourseByName(Scanner scanner) {
        System.out.print("Nhap ten khoa hoc can tim: ");
        String courseName = scanner.nextLine();
        courseService.findCourseByName(courseName);
    }

    public Enrollment inputEnrollmentData(Scanner scanner) {
        Integer courseId = inputCourseId(scanner, "Nhap ma khoa hoc can dang ky: ");
        if (courseId == null) {
            return null;
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(currentStudent.getId());
        enrollment.setCourseId(courseId);
        return enrollment;
    }

    public void addEnrollment(Scanner scanner) {
        displayAllCourses();
        Enrollment enrollment = inputEnrollmentData(scanner);
        if (enrollment == null) {
            return;
        }
        enrollmentService.addEnrollment(enrollment);
    }

    public Integer inputCourseId(Scanner scanner, String message) {
        List<Enrollment> enrollmentList = enrollmentService.getEnrollmentByStudentId(currentStudent.getId());

        int courseId = Validator.inputValidInteger(scanner, message);
        Optional<Course> course = courseService.findCourseById(courseId);
        if (course.isEmpty()) {
            System.out.println("Khong tim thay khoa hoc co id ban vua nhap");
            return null;
        }

        boolean alreadyEnrolled = enrollmentList.stream()
                .anyMatch(enrollment -> enrollment.getCourseId() == courseId
                        && (enrollment.getStatus() == EnrollmentStatus.WAITING
                        || enrollment.getStatus() == EnrollmentStatus.CONFIRM));

        if (alreadyEnrolled) {
            System.out.println("Ban da dang ki khoa hoc nay roi");
            return null;
        }
        return courseId;
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
                    displayEnrollment();
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

    public void displayEnrollment() {
        List<Enrollment> enrollmentList = enrollmentService.getEnrollmentByStudentId(currentStudent.getId());
        if (!enrollmentList.isEmpty()) {
            Course.printMenu();
            enrollmentList.forEach(System.out::println);
        } else {
            System.out.println("Ban chua dang ky khoa hoc nao");
        }
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
                    sortCourseByCourseNameASC();
                    break;

                case 2:
                    sortCourseByCourseNameDESC();
                    break;

                case 3:
                    sortCourseByRegisteredDateASC();
                    break;

                case 4:
                    sortCourseByRegisteredDateDESC();
                    break;

                case 5:
                    isExit = true;
                    break;

                default:
                    System.out.println("Vui long chon tu 1-5");
            }
        } while (!isExit);
    }

    public void sortCourseByCourseNameASC() {
        List<Enrollment> enrollmentList = enrollmentService.getEnrollmentByStudentId(currentStudent.getId());
        if (!enrollmentList.isEmpty()) {
            Course.printMenu();
            enrollmentList.stream()
                    .sorted(Comparator.comparing(Enrollment::getCourseName))
                    .forEach(System.out::println);
        } else {
            System.out.println("Ban chua dang ky khoa hoc nao");
        }
    }

    public void sortCourseByCourseNameDESC() {
        List<Enrollment> enrollmentList = enrollmentService.getEnrollmentByStudentId(currentStudent.getId());
        if (!enrollmentList.isEmpty()) {
            Course.printMenu();
            enrollmentList.stream()
                    .sorted(Comparator.comparing(Enrollment::getCourseName).reversed())
                    .forEach(System.out::println);
        } else {
            System.out.println("Ban chua dang ky khoa hoc nao");
        }
    }

    public void sortCourseByRegisteredDateASC() {
        List<Enrollment> enrollmentList = enrollmentService.getEnrollmentByStudentId(currentStudent.getId());
        if (!enrollmentList.isEmpty()) {
            Course.printMenu();
            enrollmentList.stream()
                    .sorted(Comparator.comparing(Enrollment::getRegisteredDate))
                    .forEach(System.out::println);
        } else {
            System.out.println("Ban chua dang ky khoa hoc nao");
        }
    }

    public void sortCourseByRegisteredDateDESC() {
        List<Enrollment> enrollmentList = enrollmentService.getEnrollmentByStudentId(currentStudent.getId());
        if (!enrollmentList.isEmpty()) {
            Course.printMenu();
            enrollmentList.stream()
                    .sorted(Comparator.comparing(Enrollment::getRegisteredDate).reversed())
                    .forEach(System.out::println);
        } else {
            System.out.println("Ban chua dang ky khoa hoc nao");
        }
    }

    public void cancelEnrollmentByCourseId(Scanner scanner) {
        displayEnrollment();
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

        StudentManagementPresentation smp = new StudentManagementPresentation();
        String newPassword = smp.inputPassword(scanner, "Nhap mat khau moi: ");
        if (newPassword.equals(currentStudent.getPassword())) {
            System.out.println("Mat khau moi khong duoc trung mat khau cu");
            return;
        }

        studentService.changePassword(currentStudent.getId(), newPassword);
        currentStudent.setPassword(newPassword);
    }
}
