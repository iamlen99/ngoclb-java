package ra.academy_project.presentation;

import ra.academy_project.validation.Validator;

import java.util.Scanner;

public class EnrollmentManagementPresentation {
    public void enrollmentManagementMenu(Scanner scanner) {
        boolean isExit = false;
        do {
            System.out.println("======================== MENU ENROLLMENT MANAGEMENT ========================");
            System.out.println("1. Hien thi hoc vien theo tung khoa hoc");
            System.out.println("2. Them hoc vien vao khoa hoc");
            System.out.println("3. Xoa hoc vien khoi khoa hoc");
            System.out.println("4. Quay ve menu chinh");
            System.out.println("============================================================================");

            int choice = Validator.inputValidInteger(scanner, "Nhap lua chon: ");

            switch (choice) {
                case 1:
                    break;

                case 2:
                    break;

                case 3:
                    break;

                case 4:
                    isExit = true;
                    break;

                default:
                    System.out.println("Vui long chon tu 1-4");
            }
        } while (!isExit);
    }
}
