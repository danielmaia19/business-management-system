package com.danielmaia.businessmanagementsystem.DAO;

import com.danielmaia.businessmanagementsystem.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}
