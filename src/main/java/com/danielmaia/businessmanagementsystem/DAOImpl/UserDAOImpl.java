package com.danielmaia.businessmanagementsystem.DAOImpl;

import com.danielmaia.businessmanagementsystem.DAO.UserDAO;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.UserRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAOImpl implements UserDAO {

    public UserDAOImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    NamedParameterJdbcTemplate template;

    @Override
    public List<User> findAll() {
        return template.query("select * from users", new UserRowMapper());
    }
    @Override
    public void insertUser(User user) {
        final String sql = "insert into users(userId, userFirstName , userLastName,userEmail) values(:userId,:userFirstName,:userLastName,:userEmail)";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("userId", user.getId())
                .addValue("userFirstName", user.getFirstName())
                .addValue("userLastName", user.getLastName())
                .addValue("userEmail", user.getEmail());
        template.update(sql,param, holder);
    }
    @Override
    public void updateUser(User user) {
        final String sql = "update users set userFistName=:userFirstName, userLastName=:userLastName, userEmail=:userEmail where userId=:userId";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("userId", user.getId())
                .addValue("userFirstName", user.getFirstName())
                .addValue("userLastName", user.getLastName())
                .addValue("userEmail", user.getEmail());
        template.update(sql,param, holder);
    }
    @Override
    public void executeUpdateUser(User user) {
        final String sql = "update users set userFistName=:userFirstName, userLastName=:userLastName, userEmail=:userEmail where userId=:userId";
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("userId", user.getId());
        map.put("userFirstName", user.getFirstName());
        map.put("userLastName", user.getLastName());
        map.put("userEmail", user.getEmail());
        template.execute(sql,map,new PreparedStatementCallback<Object>() {
            @Override
            public Object doInPreparedStatement(PreparedStatement ps)
                    throws SQLException, DataAccessException {
                return ps.executeUpdate();
            }
        });
    }
    @Override
    public void deleteUser(User user) {
        final String sql = "delete from users where userId=:userId";
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("userId", user.getId());
        template.execute(sql,map,new PreparedStatementCallback<Object>() {
            @Override
            public Object doInPreparedStatement(PreparedStatement ps)
                    throws SQLException, DataAccessException {
                return ps.executeUpdate();
            }
        });
    }
}
