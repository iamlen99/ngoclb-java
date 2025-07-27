package ra.academy_project.presentation;

import ra.academy_project.business.EnrollmentService;
import ra.academy_project.business.impl.EnrollmentServiceImpl;
import ra.academy_project.model.CourseEnrolledStudent;
import ra.academy_project.model.Enrollment;
import ra.academy_project.model.EnrollmentStatus;
import ra.academy_project.pagination.Pagination;
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
                    displayCourseEnrolledStudents(scanner);
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

    public void displayCourseEnrolledStudents(Scanner scanner) {
        int currentPage = 1;
        final int pageSize = 5;
        int totalPages = enrollmentService.getEnrolledStudentsTotalPages(pageSize);

        do{
            List<CourseEnrolledStudent> enrolledStudentList = enrollmentService.getCourseEnrolledStudents(currentPage, pageSize);
            enrollmentService.displayEnrolledStudents(enrolledStudentList);

            int nextPage = Pagination.handlePagination(scanner, currentPage, totalPages);
            if (nextPage == -1) {
                break;
            }
            currentPage = nextPage;
        } while (true);
    }

    public void approveEnrollment(Scanner scanner) {
        if (!enrollmentService.isExistWaitingStatus()) {
            System.out.println("Toan bo danh sach da duoc duyet");
            return;
        }

        System.out.print("Ban co chac chan muon duyet hay khong hay chi xem thoi?, neu co nhap 'yes', neu khong bam phim bat ky de thoat: ");
        if (!scanner.nextLine().equalsIgnoreCase("yes")) {
            return;
        }

        int enrollmentId = Validator.inputValidInteger(scanner, "Nhap ma dang ky cua sinh vien can duyet dang ky khoa hoc: ");
        Optional<Enrollment> approveEnrollment = enrollmentService.getEnrollmentById(enrollmentId);
        if (approveEnrollment.isEmpty()) {
            System.out.println("Id ban nhap khong ton tai");
            return;
        }

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
            int enrollmentId = Validator.inputValidInteger(scanner, message);
            Optional<Enrollment> enrollment = enrollmentService.getEnrollmentById(enrollmentId);
            if (enrollment.isPresent()) {
                return enrollmentId;
            }
            System.out.println("Id ban nhap khong ton tai");
        } while (true);
    }

    public void deleteEnrollment(Scanner scanner) {
        System.out.print("Ban da co ma dang ky de xoa chua?, neu co nhap 'yes', neu khong bam phim bat ky de thoat: ");
        if (!scanner.nextLine().equalsIgnoreCase("yes")) {
            return;
        }

        int enrollmentId = inputEnrollmentId(scanner, "Nhap ma dang ky cua sinh vien can xoa khoi khoa hoc: ");
        System.out.print("Ban co chac chan xoa sinh vien nay khong (neu co nhap 'y'): ");
        String message = scanner.nextLine();
        if (message.equalsIgnoreCase("y")) {
            enrollmentService.deleteEnrollment(enrollmentId);
        }
    }


}
