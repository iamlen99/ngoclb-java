package ra.academy_project.presentation;

import ra.academy_project.business.StatisticsService;
import ra.academy_project.business.impl.StatisticsServiceImpl;
import ra.academy_project.model.CourseAndStudentStatistics;
import ra.academy_project.model.StudentsByCourse;
import ra.academy_project.validation.Validator;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class StatisticPresentation {
    public final StatisticsService statisticsService;

    public StatisticPresentation() {
        statisticsService = new StatisticsServiceImpl();
    }

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
                    displayCountCourseAndStudent();
                    break;

                case 2:
                    displayStudentsByCourse();
                    break;

                case 3:
                    displayTop5CourseByStudentCount();
                    break;

                case 4:
                    displayCourseMoreThan10Students();
                    break;

                case 5:
                    isExit = true;
                    break;

                default:
                    System.out.println("Vui long chon tu 1-5");
            }
        } while (!isExit);
    }

    public void displayCountCourseAndStudent() {
        Optional<CourseAndStudentStatistics> countOptional = statisticsService.statisticsCount();
        countOptional.ifPresentOrElse(System.out::println,
                () -> {
                    System.out.println("Danh sach trong");
                });
    }

    public void displayStudentsByCourse() {
        List<StudentsByCourse> listStudent = statisticsService.findAllStudentsByCourse();
        statisticsService.displayStatistics(listStudent);
    }

    public void displayTop5CourseByStudentCount() {
        List<StudentsByCourse> listStudent = statisticsService.getTop5CourseByStudentCount();
        statisticsService.displayStatistics(listStudent);
    }

    public void displayCourseMoreThan10Students() {
        List<StudentsByCourse> listStudent = statisticsService.getCourseMoreThan10Students();
        statisticsService.displayStatistics(listStudent);
    }
}
