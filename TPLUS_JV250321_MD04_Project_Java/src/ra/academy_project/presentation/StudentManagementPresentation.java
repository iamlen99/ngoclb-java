package ra.academy_project.presentation;

import ra.academy_project.business.StudentService;
import ra.academy_project.business.impl.StudentServiceImpl;
import ra.academy_project.model.Student;
import ra.academy_project.pagination.Pagination;
import ra.academy_project.validation.Validator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class StudentManagementPresentation {
    public static final int pageSize = 5;
    public final StudentService studentService;

    public StudentManagementPresentation() {
        studentService = new StudentServiceImpl();
    }

    public void studentManagementMenu(Scanner scanner) {
        boolean isExit = false;
        do {
            System.out.println("+======================== MENU QUAN LY HOC VIEN =========================+");
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
                    displayAllStudents(scanner, "noSort");
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
        student.setName(Validator.inputNotEmptyData(scanner, "Nhap ten hoc vien: "));
        student.setDob(Validator.inputDateOfBirth(scanner, "Nhap ngay sinh hoc vien: "));
        student.setEmail(inputEmail(scanner, "Nhap email hoc vien: "));
        student.setSex(Validator.inputGender(scanner, "Nhap gioi tinh (nam/nu): "));
        student.setPhone(Validator.inputPhone(scanner, "Nhap so dien thoai hoc vien: "));
        student.setPassword(Validator.inputPassword(scanner, "Nhap mat khau: "));
        return student;
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

    public void displayAllStudents(Scanner scanner, String sortOrder) {
        int currentPage = 1;
        int totalPages = studentService.getTotalPages(pageSize);
        do {
            List<Student> studentList = studentService.findAllStudents(currentPage, pageSize, sortOrder);
            studentService.displayStudents(studentList);
            int nextPage = Pagination.handlePagination(scanner, currentPage, totalPages);
            if (nextPage == -1) {
                break;
            }
            currentPage = nextPage;
        } while (true);
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
                                String newName = Validator.inputNotEmptyData(scanner, "Nhap ten hoc vien moi: ");
                                student.setName(newName);
                                break;

                            case 2:
                                LocalDate newDob = Validator.inputDateOfBirth(scanner, "Nhap ngay sinh moi: ");
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
                                String newPhone = Validator.inputPhone(scanner, "Nhap so dien thoai moi: ");
                                student.setPhone(newPhone);
                                break;

                            case 6:
                                String newPassword = Validator.inputPassword(scanner, "Nhap mat khau moi: ");
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
        int currentPage = 1;
        System.out.print("Nhap ten, email hoac id cua hoc vien ma ban can tim: ");
        String searchValue = scanner.nextLine();
        int totalPages = studentService.getSearchedTotalPages(searchValue, pageSize);
        if (totalPages == 0) {
            System.out.println("Khong tim thay hoc vien nao co id ban vua nhap!");
            return;
        }

        do {
            List<Student> students = studentService.searchStudents(searchValue, currentPage, pageSize);
            studentService.displayStudents(students);
            int nextPage = Pagination.handlePagination(scanner, currentPage, totalPages);
            if (nextPage == -1) {
                break;
            }
            currentPage = nextPage;
        } while (true);
    }

    public void displaySortMenu(Scanner scanner) {
        boolean isExit = false;
        do {
            System.out.println("1. Sap xep hoc vien theo ten tang dan");
            System.out.println("2. Sap xep hoc vien theo ten giam dan");
            System.out.println("3. Sap xep hoc vien theo id tang dan");
            System.out.println("4. Sap xep hoc vien theo id giam dan");
            System.out.println("5. Quay lai menu quan ly hoc vien");

            int choice = Validator.inputValidInteger(scanner, "Lua chon cua ban: ");

            switch (choice) {
                case 1:
                    displayAllStudents(scanner, "nameASC");
                    break;

                case 2:
                    displayAllStudents(scanner, "nameDESC");
                    break;

                case 3:
                    displayAllStudents(scanner, "idASC");
                    break;

                case 4:
                    displayAllStudents(scanner, "idDESC");
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
