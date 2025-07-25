package ra.academy_project.presentation;

import ra.academy_project.business.CourseService;
import ra.academy_project.business.impl.CourseServiceImpl;
import ra.academy_project.model.Course;
import ra.academy_project.validation.Validator;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class CoursePresentation {
    public final CourseService courseService;

    public CoursePresentation() {
        courseService = new CourseServiceImpl();
    }

    public void courseManagementMenu(Scanner scanner) {
        boolean isExit = false;
        do {
            System.out.println("========================== MENU COURSE MANAGEMENT ==========================");
            System.out.println("1. Hien thi danh sach khoa hoc");
            System.out.println("2. Them moi khoa hoc");
            System.out.println("3. Chinh sua thong tin khoa hoc");
            System.out.println("4. Xoa khoa hoc");
            System.out.println("5. Tim kiem theo ten");
            System.out.println("6. Sap xep theo ten hoac id");
            System.out.println("7. Quay ve menu chinh");
            System.out.println("============================================================================");

            int choice = Validator.inputValidInteger(scanner, "Nhap lua chon: ");

            switch (choice) {
                case 1:
                    displayAllCourses();
                    break;

                case 2:
                    addCourse(scanner);
                    break;

                case 3:
                    updateCourse(scanner);
                    break;

                case 4:
                    deleteCourse(scanner);
                    break;

                case 5:
                    findCourseByName(scanner);
                    break;

                case 6:
                    sortCoursesMenu(scanner);
                    break;

                case 7:
                    isExit = true;
                    break;

                default:
                    System.out.println("Vui long chon tu 1-7");
            }
        } while (!isExit);
    }

    public Course inputData(Scanner scanner) {
        Course course = new Course();
        course.setName(inputCourseName(scanner, "Nhap ten khoa hoc:"));
        course.setDuration(inputDuration(scanner, "Nhap thoi luong khoa hoc (gio):"));
        course.setInstructor(inputInstructor(scanner, "Nhap ten giang vien phu trach:"));
        return course;
    }

    public String inputCourseName(Scanner scanner, String message) {
        System.out.println(message);
        do {
            String courseName = scanner.nextLine();
            if (!Validator.isEmpty(courseName)) {
                return courseName;
            }
            System.err.println("Ten khoa hoc khong duoc de trong, vui long nhap ten khoa hoc");
        } while (true);
    }

    public int inputDuration(Scanner scanner, String message) {
        System.out.println(message);
        do {
            String input = scanner.nextLine();
            if (!Validator.isEmpty(input)) {
                if (Validator.isInteger(input)) {
                    int duration = Integer.parseInt(input);
                    if (duration > 0) {
                        return duration;
                    }
                    System.err.println("Vui long nhap thoi luong khoa hoc la so nguyen duong!");
                } else {
                    System.err.println("Vui long nhap vao 1 nguyen");
                }
            } else {
                System.err.println("Thoi luong khoa hoc khong duoc de trong, vui long nhap thoi luong khoa hoc");
            }
        } while (true);
    }

    public String inputInstructor(Scanner scanner, String message) {
        System.out.println(message);
        do {
            String instructor = scanner.nextLine();
            if (!Validator.isEmpty(instructor)) {
                return instructor;
            }
            System.err.println("Ten giang vien phu trach khong duoc de trong, vui long nhap ten khoa hoc");
        } while (true);
    }

    public void displayAllCourses() {
        List<Course> courses = courseService.findAll();
        if (courses.isEmpty()) {
            System.out.println("Danh sach trong.");
        } else {
            System.out.printf("| %-3s | %-20s | %-10s | %-15s | %-10s |\n", "ID", "Ten khoa hoc", "Thoi luong", "Giang vien", "Ngay them");
            courses.forEach(System.out::println);
        }
    }

    public void addCourse(Scanner scanner) {
        Course course = inputData(scanner);
        courseService.addCourse(course);
    }

    public void updateCourse(Scanner scanner) {
        int updateId = Validator.inputValidInteger(scanner, "Nhap id cua khoa hoc can cap nhat:");
        courseService.findCourseById(updateId).ifPresentOrElse(course -> {
                    boolean isExit = false;
                    do {
                        System.out.println("1. Cap nhat ten khoa hoc");
                        System.out.println("2. Cap nhat thoi luong khoa hoc");
                        System.out.println("3. Cap nhat ten giang vien phu trach");
                        System.out.println("4. Thoat");

                        int choice = Validator.inputValidInteger(scanner, "Nhap lua chon: ");

                        switch (choice) {
                            case 1:
                                String newCourseName = inputCourseName(scanner, "Nhap ten khoa hoc moi:");
                                course.setName(newCourseName);
                                break;
                            case 2:
                                int newDuration = inputDuration(scanner, "Nhap thoi luong khoa hoc moi: ");
                                course.setDuration(newDuration);
                                break;
                            case 3:
                                String newInstructor = inputInstructor(scanner, "Nhap ten giang vien phu trach moi: ");
                                course.setInstructor(newInstructor);
                                break;
                            case 4:
                                isExit = true;
                                break;
                            default:
                                System.out.println("Vui long chon tu 1-4");
                        }
                        courseService.updateCourse(course);
                    } while (!isExit);
                },
                () -> {
                    System.out.println("Id ban vua nhap khong ton tai!");
                });
    }

    public void deleteCourse(Scanner scanner) {
        int deleteId = Validator.inputValidInteger(scanner, "Nhap id cua khoa hoc can xoa: ");
        courseService.findCourseById(deleteId).ifPresentOrElse(course -> {
                    System.out.println("Ban co chac chan muon xoa khoa hoc nay khong, neu co hay nhap 'y'");
                    String confirm = scanner.nextLine();
                    if (!confirm.equalsIgnoreCase("y")) {
                        return;
                    }
                    courseService.deleteCourse(course);
                },
                () -> {
                    System.out.println("Id ban vua nhap khong ton tai!");
                });
    }

    public void findCourseByName(Scanner scanner) {
        String courseName = inputCourseName(scanner, "Nhap ten khoa hoc can tim:");
        courseService.findCourseByName(courseName);
    }

    public void sortCoursesMenu(Scanner scanner) {
        System.out.println("1. Sap xep theo ten tang dan");
        System.out.println("2. Sap xep theo ten giam dan");
        System.out.println("3. Sap xep theo id tang dan");
        System.out.println("4. Sap xep theo id giam dan");

        int choice = Validator.inputValidInteger(scanner, "Nhap lua chon: ");
        switch (choice) {
            case 1:
                sortCourseByNameASC(scanner);
                break;

            case 2:
                sortCourseByNameDESC(scanner);
                break;

            case 3:
                sortCourseByIdASC(scanner);
                break;

            case 4:
                sortCourseByIdDESC(scanner);
                break;

            default:
                System.out.println("Vui long chon tu 1-4");
        }
    }

    public void sortCourseByNameASC(Scanner scanner) {
        List<Course> courses = courseService.findAll();
        if (courses.isEmpty()) {
            System.out.println("Danh sach trong.");
        } else {
            courses.stream().sorted(Comparator.comparing(Course::getName)).forEach(System.out::println);
        }
    }

    public void sortCourseByNameDESC(Scanner scanner) {
        List<Course> courses = courseService.findAll();
        if (courses.isEmpty()) {
            System.out.println("Danh sach trong.");
        } else {
            courses.stream().sorted(Comparator.comparing(Course::getName).reversed()).forEach(System.out::println);
        }
    }

    public void sortCourseByIdASC(Scanner scanner) {
        List<Course> courses = courseService.findAll();
        if (courses.isEmpty()) {
            System.out.println("Danh sach trong.");
        } else {
            courses.stream().sorted(Comparator.comparing(Course::getId)).forEach(System.out::println);
        }
    }

    public void sortCourseByIdDESC(Scanner scanner) {
        List<Course> courses = courseService.findAll();
        if (courses.isEmpty()) {
            System.out.println("Danh sach trong.");
        } else {
            courses.stream().sorted(Comparator.comparing(Course::getId).reversed()).forEach(System.out::println);
        }
    }
}
