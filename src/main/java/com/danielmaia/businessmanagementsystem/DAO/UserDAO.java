package com.danielmaia.businessmanagementsystem.DAO;

import com.danielmaia.businessmanagementsystem.Model.User;
import java.util.List;

public interface UserDAO {

    List<User> findAll();
    void insertUser(User user);
    void updateUser(User user);
    void executeUpdateUser(User user);
    public void deleteUser(User user);

}
