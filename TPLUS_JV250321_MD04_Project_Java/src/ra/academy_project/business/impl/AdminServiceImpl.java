package ra.academy_project.business.impl;

import ra.academy_project.business.AdminService;
import ra.academy_project.dao.AdminDAO;
import ra.academy_project.dao.impl.AdminDAOImpl;
import ra.academy_project.model.Admin;

import java.util.Optional;

public class AdminServiceImpl implements AdminService {
    public final AdminDAO adminDAO;
    public AdminServiceImpl() {
        adminDAO = new AdminDAOImpl();
    }
    public Optional<Admin> getAdminAccount(String username, String password) {
        return adminDAO.getAccount(username, password);
    }
}
