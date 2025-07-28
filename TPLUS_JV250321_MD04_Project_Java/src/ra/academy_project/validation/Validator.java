package ra.academy_project.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Validator {
    private static final String RESET = "\033[0m";
    private static final String RED = "\033[0;31m";

    public static boolean isEmpty(String data) {
        return data == null || data.trim().isEmpty();
    }

    public static String inputNotEmptyData(Scanner scanner, String message) {
        System.out.print(message);
        do {
            String input = scanner.nextLine();
            if (!isEmpty(input)) {
                return input;
            }
            System.out.print(RED + "Ban chua nhap gi ca, hay " + message.toLowerCase() + RESET);
        } while (true);
    }

    public static boolean isInteger(String data) {
        try {
            Integer.parseInt(data);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int inputValidInteger(Scanner scanner, String message) {
        System.out.print(message);
        do {
            String input = scanner.nextLine();
            if (isInteger(input)) {
                return Integer.parseInt(input);
            }
            System.out.print(RED + "Vui long nhap vao mot so nguyen: " + RESET);
        } while (true);
    }

    public static int inputPositiveInteger(Scanner scanner, String message) {
        do {
            int input = Validator.inputValidInteger(scanner, message);
            if (input > 0) {
                return input;
            }
            System.out.println(RED + "Vui long nhap vao mot so nguyen duong" + RESET);
        } while (true);
    }

    public static LocalDate inputDateOfBirth(Scanner scanner, String message) {
        System.out.print(message);
        do {
            String dateOfBirth = scanner.nextLine();
            if (!dateOfBirth.isEmpty()) {
                try {
                    return LocalDate.parse(dateOfBirth, Validator.formatter);
                } catch (Exception e) {
                    System.out.print(RED + "Dinh dang ngay khong hop le (Dung: dd/MM/yyyy), hay nhap lai: " + RESET);
                }
            } else {
                System.out.print(RED + "Ngay sinh khong duoc de trong, vui long nhap ngay sinh (dd/MM/yyyy): " + RESET);
            }
        } while (true);
    }

    public static boolean inputGender(Scanner scanner, String message) {
        System.out.print(message);
        do {
            String gender = scanner.nextLine();
            if (!Validator.isEmpty(gender)) {
                if (gender.equalsIgnoreCase("nam")) {
                    return true;
                } else if (gender.equalsIgnoreCase("nu")) {
                    return false;
                } else {
                    System.out.print(RED + "Gia tri nhap khong hop le, xin hay nhap lai gioi tinh (nam/nu): " + RESET);
                }
            } else {
                System.out.print(RED + "Gioi tinh khong duoc de trong, xin hay nhap gioi tinh (nam/nu): " + RESET);
            }
        } while (true);
    }

    public static String inputPhone(Scanner scanner, String message) {
        System.out.print(message);
        do {
            String phone = scanner.nextLine();
            if (Validator.isEmpty(phone)) {
                return null;
            }
            if (Validator.isValidPhoneNumber(phone)) {
                return phone;
            }
            System.out.print(RED + "Dinh dang dien thoai khong hop le, xin hay nhap lai phone (hoac co the khong nhap): " + RESET);
        } while (true);
    }

    public static String inputPassword(Scanner scanner, String message) {
        System.out.print(message);
        do {
            String password = scanner.nextLine();
            if (Validator.isEmpty(password)) {
                System.out.print(RED + "Mat khau khong duoc de trong, xin hay nhap mat khau: " + RESET);
            } else {
                if (Validator.isValidPassword(password)) {
                    return password;
                }
                System.out.println(RED + "Mat khau phai bao gom chu hoa, chu thuong, 1 ky tu la so, 1 ky tu dac biet\n"
                        + "toi thieu 8 ki tu va toi da 20 ky tu!" + RESET);
            }
        } while (true);
    }

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        String phoneNumberRegex = "^(0|\\+84)(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-5]|9[0-9])[0-9]{7}$";
        return Pattern.matches(phoneNumberRegex, phoneNumber);
    }

    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
        return Pattern.matches(passwordRegex, password);
    }
}
