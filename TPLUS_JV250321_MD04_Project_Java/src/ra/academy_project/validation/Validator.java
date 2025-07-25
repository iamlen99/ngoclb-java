package ra.academy_project.validation;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Validator {
    public static boolean isEmpty (String data) {
        return data == null || data.trim().isEmpty();
    }

    public static String inputNotEmptyData (Scanner scanner, String message) {
        System.out.print(message);
        do {
            String input = scanner.nextLine();
            if (!isEmpty(input)) {
                return input;
            }
            System.err.print("Ban chua nhap gi ca, hay nhap " + message.toLowerCase());
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
            System.err.print("Vui long nhap vao mot so nguyen: ");
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
