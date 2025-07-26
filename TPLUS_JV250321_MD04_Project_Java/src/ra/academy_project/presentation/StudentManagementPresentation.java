package ra.academy_project.presentation;

import ra.academy_project.business.StudentService;
import ra.academy_project.business.impl.StudentServiceImpl;
import ra.academy_project.model.Student;
import ra.academy_project.validation.Validator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class StudentManagementPresentation {
    public final StudentService studentService;

    public StudentManagementPresentation() {
        studentService = new StudentServiceImpl();
    }

    public void studentManagementMenu(Scanner scanner) {
        boolean isExit = false;
        do {
            System.out.println("+======================== MENU STUDENT MANAGEMENT =========================+");
            System.out.println("| 1. Hien thi danh sach hoc vien                                           |");
            System.out.println("| 2. Them moi hoc vien                                                     |");
            System.out.println("| 3. Chinh sua thong tin hoc vien                                          |");
            System.out.println("| 4. Xoa hoc vien                                                          |");
            System.out.println("| 5. Tim kiem theo ten, email hoac id                                      |");
            System.out.println("| 6. Sap xep theo ten hoac id                                              |");
            System.out.println("| 7. Quay ve menu chinh                                                    |");
            System.out.println("+==========================================================================+");

            int choice = Validator.inputValidInteger(scanner, "Nhap lua chon: ");

            switch (choice) {
                case 1:
                    displayAllStudents();
                    break;

                case 2:
                    addStudent(scanner);
                    break;

                case 3:
                    updateStudent(scanner);
                    break;

                case 4:
                    deleteStudent(scanner);
                    break;

                case 5:
                    searchStudents(scanner);
                    break;

                case 6:
                    displaySortMenu(scanner);
                    break;

                case 7:
                    isExit = true;
                    break;

                default:
                    System.out.println("Vui long chon tu 1-7");
            }
        } while (!isExit);
    }

    public Student inputData(Scanner scanner) {
        Student student = new Student();
        student.setName(inputStudentName(scanner, "Nhap ten hoc vien: "));
        student.setDob(inputDateOfBirth(scanner, "Nhap ngay sinh hoc vien: "));
        student.setEmail(inputEmail(scanner, "Nhap email hoc vien: "));
        student.setSex(inputGender(scanner, "Nhap gioi tinh (nam/nu): "));
        student.setPhone(inputPhone(scanner, "Nhap so dien thoai hoc vien: "));
        student.setPassword(inputPassword(scanner, "Nhap mat khau: "));
        return student;
    }

    public String inputStudentName(Scanner scanner, String message) {
        System.out.print(message);
        do {
            String studentName = scanner.nextLine();
            if (!studentName.isEmpty()) {
                return studentName;
            }
            System.out.print("Ten hoc vien khong duoc de trong, vui long nhap ten hoc vien: ");
        } while (true);
    }

    public LocalDate inputDateOfBirth(Scanner scanner, String message) {
        System.out.print(message);
        do {
            String dateOfBirth = scanner.nextLine();
            if (!dateOfBirth.isEmpty()) {
                try {
                    return LocalDate.parse(dateOfBirth, Validator.formatter);
                } catch (Exception e) {
                    System.out.print("Dinh dang ngay khong hop le (Dung: dd/MM/yyyy), hay nhap lai: ");
                }
            } else {
                System.out.print("Ngay sinh khong duoc de trong, vui long nhap ngay sinh: ");
            }
        } while (true);
    }

    public String inputEmail(Scanner scanner, String message) {
        System.out.print(message);
        do {
            String email = scanner.nextLine();
            if (!email.isEmpty()) {
                if (Validator.isValidEmail(email)) {
                    if (!studentService.isExistEmail(email)) {
                        return email;
                    }
                    System.out.print("Email da ton tai, vui long nhap lai: ");
                } else {
                    System.out.print("Dinh dang email khong hop le, xin hay nhap lai: ");
                }
            } else {
                System.out.print("Email khong duoc de trong, hay nhap email: ");
            }
        } while (true);
    }

    public boolean inputGender(Scanner scanner, String message) {
        System.out.print(message);
        do {
            String gender = scanner.nextLine();
            if (!gender.isEmpty()) {
                if (gender.equalsIgnoreCase("nam")) {
                    return true;
                } else if (gender.equalsIgnoreCase("nu")) {
                    return false;
                } else {
                    System.out.print("Gia tri nhap khong hop le, xin hay nhap lai gioi tinh (nam/nu): ");
                }
            } else {
                System.out.print("Gioi tinh khong duoc de trong, xin hay nhap gioi tinh (nam/nu): ");
            }
        } while (true);
    }

    public String inputPhone(Scanner scanner, String message) {
        System.out.print(message);
        do {
            String phone = scanner.nextLine();
            if (Validator.isEmpty(phone)) {
                return null;
            }
            if (Validator.isValidPhoneNumber(phone)) {
                return phone;
            }
            System.out.println("Dinh dang dien thoai khong hop le, xin hay nhap lai phone (hoac co the khong nhap): ");
        } while (true);
    }

    public String inputPassword(Scanner scanner, String message) {
        System.out.print(message);
        do {
            String password = scanner.nextLine();
            if (Validator.isEmpty(password)) {
                System.out.print("Mat khau khong duoc de trong, xin hay nhap mat khau: ");
            } else {
                if (Validator.isValidPassword(password)) {
                    return password;
                }
                System.out.println("Mat khau phai bao gom chu hoa, chu thuong, 1 ky tu la so, 1 ky tu dac biet\n"
                        + "toi thieu 8 ki tu va toi da 20 ky tu");
            }
        } while (true);
    }

    public void displayAllStudents() {
        List<Student> studentList = studentService.findAllStudents();
        if (studentList.isEmpty()) {
            System.out.println("Danh sach trong");
        } else {
            System.out.printf("| %-3s | %-20s | %-10s | %-25s | %-9s | %-13s | %-12s | %-10s |\n", "ID", "Ho ten", "Ngay sinh"
                    , "Email", "Gioi tinh", "So dien thoai", "Mat khau", "Ngay them");
            studentList.forEach(System.out::println);
        }
    }

    public void addStudent(Scanner scanner) {
        Student student = inputData(scanner);
        studentService.addStudent(student);
    }

    public void updateStudent(Scanner scanner) {
        int idToUpdate = Validator.inputValidInteger(scanner, "Nhap id cua hoc vien can cap nhat thong tin: ");
        studentService.findStudentById(idToUpdate).ifPresentOrElse(student -> {
                    boolean isExit = false;
                    do {
                        System.out.println("1. Cap nhat ten hoc vien");
                        System.out.println("2. Cap nhat ngay sinh");
                        System.out.println("3. Cap nhat email");
                        System.out.println("4. Cap nhat gioi tinh");
                        System.out.println("5. Cap nhat so dien thoai");
                        System.out.println("6. Cap nhat mat khau");
                        System.out.println("7. Thoat");

                        int choice = Validator.inputValidInteger(scanner, "Nhap lua chon: ");

                        switch (choice) {
                            case 1:
                                String newName = inputStudentName(scanner, "Nhap ten hoc vien moi: ");
                                student.setName(newName);
                                break;

                            case 2:
                                LocalDate newDob = inputDateOfBirth(scanner, "Nhap ngay sinh moi: ");
                                student.setDob(newDob);
                                break;

                            case 3:
                                String newEmail = inputEmail(scanner, "Nhap email moi: ");
                                student.setEmail(newEmail);
                                break;

                            case 4:
                                student.setSex(!student.isSex());
                                break;

                            case 5:
                                String newPhone = inputPhone(scanner, "Nhap so dien thoai moi: ");
                                student.setPhone(newPhone);
                                break;

                            case 6:
                                String newPassword = inputPassword(scanner, "Nhap mat khau moi: ");
                                student.setPassword(newPassword);
                                break;

                            case 7:
                                isExit = true;
                                break;

                            default:
                                System.out.println("Vui long chon tu 1-7");
                        }

                        studentService.updateStudent(student);
                    } while (!isExit);
                },
                () -> {
                    System.out.println("Id ban vua nhap khong ton tai!");
                });
    }

    public void deleteStudent(Scanner scanner) {
        int idToDelete = Validator.inputValidInteger(scanner, "Nhap id cua hoc vien ban muon xoa: ");
        studentService.findStudentById(idToDelete).ifPresentOrElse(student -> {
                    System.out.print("Ban co chac chan muon xoa hoc vien nay khong? (neu co nhap 'y'): ");
                    String confirm = scanner.nextLine();
                    if (!confirm.equalsIgnoreCase("y")) {
                        return;
                    }
                    studentService.deleteStudent(student);
                },
                () -> {
                    System.out.println("Khong tim thay id ban vua nhap");
                });
    }

    public void searchStudents(Scanner scanner) {
        System.out.print("Nhap ten, email hoac id cua hoc vien ma ban can tim: ");
        String searchValue = scanner.nextLine();
        List<Student> students = studentService.searchStudents(searchValue);
        if (students.isEmpty()) {
            System.out.println("Khong tim thay hoc vien voi tu khoa ban vua nhap");
        } else {
            students.forEach(System.out::println);
        }
    }

    public void displaySortMenu(Scanner scanner) {
        boolean isExit = false;
        do {
            System.out.println("1. Sap xep hoc vien theo ten tang dan");
            System.out.println("2. Sap xep hoc vien theo ten giam dan");
            System.out.println("3. Sap xep hoc vien theo id tang dan");
            System.out.println("4. Sap xep hoc vien theo id giam dan");
            System.out.println("5. Thoat");

            int choice = Validator.inputValidInteger(scanner, "Lua chon cua ban: ");

            switch (choice) {
                case 1:
                    sortStudentsByNameASC();
                    break;

                case 2:
                    sortStudentsByNameDESC();
                    break;

                case 3:
                    sortStudentsByIdASC();
                    break;

                case 4:
                    sortStudentsByIdDESC();
                    break;

                case 5:
                    isExit = true;
                    break;

                default:
                    System.out.println("Vui long chon tu 1-5");
            }
        } while (!isExit);
    }

    public void sortStudentsByNameASC() {
        List<Student> listStudent = studentService.findAllStudents();
        if (listStudent.isEmpty()) {
            System.out.println("Danh sach trong");
        } else {
            List<Student> listStudentSort = new ArrayList<>(listStudent);
            for (Student student : listStudentSort) {
                String[] studentName = student.getName().split(" ");
                for (int i = 0; i < studentName.length / 2; i++) {
                    String temp = studentName[i];
                    studentName[i] = studentName[studentName.length - i - 1];
                    studentName[studentName.length - i - 1] = temp;
                }
                student.setName(String.join(" ", studentName));
            }

            listStudentSort = listStudentSort.stream()
                    .sorted(Comparator.comparing(Student::getName
                    )).toList();

            for (Student student : listStudentSort) {
                String[] studentName = student.getName().split(" ");
                for (int i = 0; i < studentName.length / 2; i++) {
                    String temp = studentName[i];
                    studentName[i] = studentName[studentName.length - i - 1];
                    studentName[studentName.length - i - 1] = temp;
                }
                student.setName(String.join(" ", studentName));
            }

            listStudentSort.forEach(System.out::println);
        }
    }

    public void sortStudentsByNameDESC() {
        List<Student> listStudent = studentService.findAllStudents();
        if (listStudent.isEmpty()) {
            System.out.println("Danh sach trong");
        } else {
            listStudent.stream()
                    .sorted(Comparator.comparing(Student::getName).reversed())
                    .forEach(System.out::println);
        }
    }

    public void sortStudentsByIdASC() {
        List<Student> listStudent = studentService.findAllStudents();
        if (listStudent.isEmpty()) {
            System.out.println("Danh sach trong");
        } else {
            listStudent.stream()
                    .sorted(Comparator.comparing(Student::getId))
                    .forEach(System.out::println);
        }
    }

    public void sortStudentsByIdDESC() {
        List<Student> listStudent = studentService.findAllStudents();
        if (listStudent.isEmpty()) {
            System.out.println("Danh sach trong");
        } else {
            listStudent.stream()
                    .sorted(Comparator.comparing(Student::getId).reversed())
                    .forEach(System.out::println);
        }
    }
}
