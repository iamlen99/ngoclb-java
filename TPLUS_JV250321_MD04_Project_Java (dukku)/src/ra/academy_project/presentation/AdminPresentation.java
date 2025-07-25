package ra.academy_project.presentation;

import ra.academy_project.business.AdminService;
import ra.academy_project.business.impl.AdminServiceImpl;
import ra.academy_project.model.Admin;
import ra.academy_project.validation.Validator;

import java.util.Optional;
import java.util.Scanner;

public class AdminPresentation {
    public final AdminService adminService;
    public AdminPresentation() {
        adminService = new AdminServiceImpl();
    }

    public void login (Scanner scanner) {
        int attempt = 0;
        final int maxAttempts = 3;
        do {
            System.out.println("============================ DANG NHAP QUAN TRI ============================");
            String username = Validator.inputNotEmptyData(scanner, "Tai khoan: ");
            String password = Validator.inputNotEmptyData(scanner, "Mat khau: ");
            System.out.println("============================================================================");

            Optional<Admin> admin = adminService.login(username, password);
            if (admin.isPresent()) {
                System.out.println("Dang nhap thanh cong");
                displayAdminMenu(scanner);
                break;
            } else {
                attempt++;
                if(attempt >= maxAttempts) {
                    System.err.printf("Ban da nhap sai %d lan. Thuc hien thoat dang nhap\n",  maxAttempts);
                    break;
                } else {
                    System.err.println("Sai ten dang nhap hoac mat khau!");
                }
            }
        } while (true);
    }

    public void displayAdminMenu (Scanner scanner) {
        boolean isExit = false;
        do {
            System.out.println("================================ MENU ADMIN ================================");
            System.out.println("1. Quan ly khoa hoc");
            System.out.println("2. Quan ly hoc vien");
            System.out.println("3. Quan ly dang ky hoc");
            System.out.println("4. Thong ke hoc vien theo khoa hoc");
            System.out.println("5. Dang xuat");
            System.out.println("============================================================================");

            int choice = Validator.inputValidInteger(scanner, "Nhap lua chon: ");

            switch (choice) {
                case 1:
                    CoursePresentation coursePresentation = new CoursePresentation();
                    coursePresentation.courseManagementMenu(scanner);
                    break;

                case 2:
                    StudentManagementPresentation studentManagementPresentation = new StudentManagementPresentation();
                    studentManagementPresentation.studentManagementMenu(scanner);
                    break;

                case 3:
                    EnrollmentManagementPresentation enrollmentPresentation = new EnrollmentManagementPresentation();
                    enrollmentPresentation.enrollmentManagementMenu(scanner);
                    break;

                case 4:
                    StatisticPresentation statisticPresentation = new StatisticPresentation();
                    statisticPresentation.statisticMenu(scanner);
                    break;

                case 5:
                    isExit = true;
                    break;

                default:
                    System.out.println("Vui long chon tu 1-5");
            }
        } while (!isExit);
    }
}
