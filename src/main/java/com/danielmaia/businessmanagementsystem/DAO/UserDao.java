package com.danielmaia.businessmanagementsystem.DAO;

import com.danielmaia.businessmanagementsystem.Model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
