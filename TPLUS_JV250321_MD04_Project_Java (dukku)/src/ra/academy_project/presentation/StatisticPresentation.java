package ra.academy_project.presentation;

import ra.academy_project.validation.Validator;

import java.util.Scanner;

public class StatisticPresentation {
    public void statisticMenu(Scanner scanner) {
        boolean isExit = false;
        do {
            System.out.println("============================== MENU STATISTIC ==============================");
            System.out.println("1. Thong ke tong so luong khoa hoc va hoc vien");
            System.out.println("2. Thong ke hoc vien theo tung khoa hoc");
            System.out.println("3. Top 5 khoa hoc dong hoc vien nhat");
            System.out.println("4. Liet ke khoa hoc co tren 10 hoc vien");
            System.out.println("5. Quay ve menu chinh");
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
