import ra.academy_project.presentation.AdminPresentation;
import ra.academy_project.presentation.StudentPresentation;
import ra.academy_project.validation.Validator;

import java.util.Scanner;

public class Main {
    private static final AdminPresentation adminPresentation = new AdminPresentation();
    private static final StudentPresentation studentPresentation = new StudentPresentation();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("========================= HE THONG QUAN LY DAO TAO =========================");
            System.out.println("1. Dang nhap voi tu cach Quan tri vien");
            System.out.println("2. Dang nhap voi tu cach Hoc vien");
            System.out.println("3. Thoat");
            System.out.println("============================================================================");

            int choice = Validator.inputValidInteger(scanner, "Lua chon cua ban: ");

            switch (choice) {
                case 1:
                    adminPresentation.login(scanner);
                    break;

                case 2:
                    studentPresentation.login(scanner);
                    break;

                case 3:
                    System.exit(0);
                    break;

                default:
                    System.err.println("Vui long chon tu 1-3");
            }
        } while (true);
    }

}
