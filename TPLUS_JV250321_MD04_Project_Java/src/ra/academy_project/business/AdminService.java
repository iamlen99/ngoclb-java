package ra.academy_project.business;

import ra.academy_project.model.Admin;

import java.util.Optional;

public interface AdminService {
     Optional<Admin> login(String username, String password);
}
