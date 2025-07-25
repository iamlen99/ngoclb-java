package ra.academy_project.presentation;

import ra.academy_project.business.EnrollmentService;
import ra.academy_project.business.impl.EnrollmentServiceImpl;
import ra.academy_project.model.CourseEnrolledStudent;
import ra.academy_project.model.Enrollment;
import ra.academy_project.model.EnrollmentStatus;
import ra.academy_project.validation.Validator;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class EnrollmentManagementPresentation {
    public final EnrollmentService enrollmentService;

    public EnrollmentManagementPresentation() {
        enrollmentService = new EnrollmentServiceImpl();
    }

    public void enrollmentManagementMenu(Scanner scanner) {
        boolean isExit = false;
        do {
            System.out.println("======================== MENU ENROLLMENT MANAGEMENT ========================");
            System.out.println("1. Hien thi hoc vien dang ky theo tung khoa hoc");
            System.out.println("2. Duyet sinh vien dang ky khoa hoc");
            System.out.println("3. Xoa hoc vien khoi khoa hoc");
            System.out.println("4. Quay ve menu chinh");
            System.out.println("============================================================================");

            int choice = Validator.inputValidInteger(scanner, "Nhap lua chon: ");

            switch (choice) {
                case 1:
                    displayCourseEnrolledStudents();
                    break;

                case 2:
                    approveEnrollment(scanner);
                    break;

                case 3:
                    deleteEnrollment(scanner);
                    break;

                case 4:
                    isExit = true;
                    break;

                default:
                    System.out.println("Vui long chon tu 1-4");
            }
        } while (!isExit);
    }

    public List<CourseEnrolledStudent> displayCourseEnrolledStudents() {
        List<CourseEnrolledStudent> enrolledStudentList = enrollmentService.getCourseEnrolledStudents();
        if (enrolledStudentList.isEmpty()) {
            System.out.println("Danh sach trong");
        } else {
            System.out.printf("| %-12s | %-12s | %-22s | %-11s | %-20s | %-20s | %-10s |\n", "Ma dang ky", "Ma sinh vien", "Ten sinh vien"
                    , "Ma khoa hoc", "Ten khoa hoc", "Ngay dang ky", "Trang thai");
            enrolledStudentList.forEach(System.out::println);
        }
        return enrolledStudentList;
    }

    public void approveEnrollment(Scanner scanner) {
        List<CourseEnrolledStudent> enrolledStudentList = enrollmentService.getCourseEnrolledStudents();
        boolean isHaveWaitingStatus = enrolledStudentList.stream()
                .anyMatch(student -> student.getStatus().equals(EnrollmentStatus.WAITING));
        if (!isHaveWaitingStatus) {
            System.out.println("Toan bo danh sach da duoc duyet");
            return;
        }

        int enrollmentId = inputEnrollmentId(scanner, "Nhap ma dang ky cua sinh vien can duyet dang ky khoa hoc: ");
        Optional<Enrollment> approveEnrollment = enrollmentService.getEnrollmentById(enrollmentId);
        if (!approveEnrollment.get().getStatus().equals(EnrollmentStatus.WAITING)) {
            System.out.println("Khong the duyet dang ky khi trang thai dang ky khac Waiting");
            return;
        }
        EnrollmentStatus enrollmentStatus = inputEnrollmentStatus(scanner, "Nhap trang thai duyet (deny/confirm): ");
        enrollmentService.approveEnrollment(enrollmentId, enrollmentStatus);
    }

    public EnrollmentStatus inputEnrollmentStatus(Scanner scanner, String message) {
        System.out.print(message);
        do {
            String status = scanner.nextLine();
            if (status.equalsIgnoreCase("confirm")) {
                return EnrollmentStatus.CONFIRM;
            }
            if (status.equalsIgnoreCase("deny")) {
                return EnrollmentStatus.DENIED;
            }
            System.out.print("Vui long nhap trang thai la 'deny' hoac 'confirm': ");
        } while (true);
    }

    public int inputEnrollmentId(Scanner scanner, String message) {
        do {
            List<CourseEnrolledStudent> enrolledStudentList = displayCourseEnrolledStudents();
            int enrollmentId = Validator.inputValidInteger(scanner, message);
            boolean isExistEnrollmentId = enrolledStudentList.stream()
                    .anyMatch(enrollment -> enrollmentId == enrollment.getEnrollmentId());
            if (isExistEnrollmentId) {
                return enrollmentId;
            }
            System.out.println("Id ban nhap khong ton tai");
        } while (true);
    }

    public void deleteEnrollment(Scanner scanner) {
        int enrollmentId = inputEnrollmentId(scanner, "Nhap ma dang ky cua sinh vien can xoa khoi khoa hoc: ");
        System.out.print("Ban co chac chan xoa sinh vien nay khong (neu co nhap 'y'): ");
        String message = scanner.nextLine();
        if (message.equalsIgnoreCase("y")) {
            enrollmentService.deleteEnrollment(enrollmentId);
        }
    }


}
