package wanglong.Service.Impl;

import wanglong.Dao.Impl.UserDaoImpl;
import wanglong.Dao.UserDao;
import wanglong.Exception.UserException;
import wanglong.Service.UserService;
import wanglong.Utils.SendJMail;
import wanglong.domain.User;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public void saveUser(User user) throws UserException {
        try {
            //注册
            userDao.saveUser(user);
            //给用户发送激活邮件active
            String link = "http://localhost:8080/bookstore/active?activeCode=" + user.getActiveCode();
            String href = "<a href=\"" + link + "\">点击此处激活您的书城账户</a>";
            System.out.println("href::::::    " + href);
            SendJMail.sendMail(user.getEmail(), href);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("会员注册失败,会员名已存在");
        }
    }

    @Override
    public void activeUser(String activeCode) throws UserException {
        try {
            User user = userDao.findUserByActiveCode(activeCode);

            if (user == null) {
                throw new UserException("非法激活，用户不存在");
            }
            if (user != null && user.getState() == 1) {
                throw new UserException("用户已激活");
            }
            userDao.activeUser(activeCode);
        } catch (UserException | SQLException e) {
            throw new UserException(e.getMessage());
        }
    }

    @Override
    public User loginUser(String username, String password) throws UserException {

        try {
            User user = userDao.findUserByUsernameAndPassword(username, password);

            if (user == null) {
                throw new UserException("登录失败，用户名或密码错误");
            }
            if (user.getState() == 0) {
                throw new UserException("用户未激活，请先登录邮箱进行激活");
            }

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public User findUserById(int userId) throws UserException {


        try {
            User user = userDao.findUserById(userId);

            if (user == null) {
                throw new UserException("用户还没有登录，请先登录");
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateUser(User user) throws UserException {
        try {
            userDao.updateUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("服务器正忙，请稍后再试");
        }

    }
}
