package ra.academy_project.business.impl;

import ra.academy_project.business.StatisticsService;
import ra.academy_project.dao.StatisticsDAO;
import ra.academy_project.dao.impl.StatisticsDAOImpl;
import ra.academy_project.model.CourseAndStudentStatistics;
import ra.academy_project.model.StudentsByCourse;

import java.util.List;
import java.util.Optional;

public class StatisticsServiceImpl implements StatisticsService {
    public final StatisticsDAO  statisticsDAO;
    public StatisticsServiceImpl() {
        statisticsDAO = new StatisticsDAOImpl();
    }

    @Override
    public Optional<CourseAndStudentStatistics> statisticsCount() {
        return statisticsDAO.statisticsCount();
    }

    @Override
    public List<StudentsByCourse> findAllStudentsByCourse() {
        return statisticsDAO.findAll();
    }
}
