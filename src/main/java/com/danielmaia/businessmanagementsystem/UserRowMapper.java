package com.danielmaia.businessmanagementsystem;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.danielmaia.businessmanagementsystem.Model.User;
import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int arg1) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("userId"));
        user.setFirstName(rs.getString("userFirstName"));
        user.setLastName(rs.getString("userLastName"));
        user.setEmail(rs.getString("userEmail"));
        return user;
    }
}