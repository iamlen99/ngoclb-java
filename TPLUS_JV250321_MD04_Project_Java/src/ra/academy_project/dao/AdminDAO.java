package ra.academy_project.dao;

import ra.academy_project.model.Admin;

import java.util.Optional;

public interface AdminDAO {
    Optional<Admin> getAccount(String username, String password);
}
