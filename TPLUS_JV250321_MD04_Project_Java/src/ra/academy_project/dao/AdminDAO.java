package ra.academy_project.dao;

import ra.academy_project.model.Admin;

import java.util.Optional;

public interface AdminDAO {
    Optional<Admin> login(String username, String password);

//    Optional<Admin> getByUsername(String username);
//
//    Optional<Admin> create();

}
