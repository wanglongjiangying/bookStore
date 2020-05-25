package wanglong.Dao;

import wanglong.domain.User;

import java.sql.SQLException;


public interface UserDao {

    void saveUser(User user) throws SQLException;

    User findUserByActiveCode(String activeCode) throws SQLException;

    void activeUser(String activeCode) throws SQLException;

    User findUserByUsernameAndPassword(String username, String password) throws SQLException;

    User findUserById(int userId) throws SQLException;

    void updateUser(User user) throws SQLException;
}
