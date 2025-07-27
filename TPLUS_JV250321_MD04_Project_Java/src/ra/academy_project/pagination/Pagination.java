package ra.academy_project.pagination;

import ra.academy_project.validation.Validator;

import java.util.Scanner;

public class Pagination {
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";

    public static int handlePagination(Scanner scanner, int currentPage, int totalPages) {
        String pre = currentPage > 1 ? "Previous " : "";
        String next = currentPage < totalPages ? " Next" : "";
        StringBuilder pageNumberDisplay = new StringBuilder();

        for (int i = 1; i <= totalPages; i++) {
            if (i == currentPage) {
                pageNumberDisplay.append(RED).append(i).append(RESET);
            }
            else {
                pageNumberDisplay.append(i);
            }

            if (i < totalPages) {
                pageNumberDisplay.append(" ");
            }
        }

        System.out.println(pre + pageNumberDisplay + next);

        String choice = Validator.inputNotEmptyData(scanner, "Nhap so trang (hoac prev/next) de chuyen trang, nhap exit de tro ve menu: ")
                .trim().toLowerCase();

        switch (choice) {
            case "prev":
                if (currentPage > 1) return currentPage - 1;
                System.out.println("Dang o trang dau tien");
                break;

            case "next":
                if (currentPage < totalPages) return currentPage + 1;
                System.out.println("Dang o trang cuoi cung");
                break;

            case "exit":
                return -1;

            default:
                try {
                    int selectedPage = Integer.parseInt(choice);
                    if (selectedPage >= 1 && selectedPage <= totalPages) {
                        return selectedPage;
                    }
                    System.out.println(RED + "Lua chon khong hop le!" + RESET);
                } catch (NumberFormatException e) {
                    System.out.println(RED + "Lua chon khong hop le!" + RESET);
                }
        }
        return currentPage;
    }
}
