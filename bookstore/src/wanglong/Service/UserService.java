package wanglong.Service;

import wanglong.Exception.UserException;
import wanglong.domain.User;

import java.sql.SQLException;

public interface UserService {

    void saveUser(User user) throws UserException;

    void activeUser(String activeCode) throws  UserException;


    User loginUser(String username, String password) throws UserException;

    User findUserById(int parseInt) throws UserException;

    void updateUser(User user) throws UserException;
}
