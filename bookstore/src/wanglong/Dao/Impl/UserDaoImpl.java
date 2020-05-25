package wanglong.Dao.Impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import wanglong.Dao.UserDao;
import wanglong.Utils.C3p0Utils;
import wanglong.domain.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    /**
     * 用户注册
     * @param user
     */
    @Override
    public void saveUser(User user) throws SQLException {

            //获取dbUtils中的QueryRunner，，通过连接池获取
            QueryRunner qr = new QueryRunner(C3p0Utils.getDataSource());

            String sql = "insert into user(username,password,gender,email,telephone,introduce," +
                    "activeCode,state,role,registtime) values(?,?,?,?,?,?,?,?,?,?)";
            Object[] params = {user.getUsername(), user.getPassword(), user.getGender(), user.getEmail(), user.getTelephone(),
                    user.getIntroduce(), user.getActiveCode(), user.getState(), user.getRole(), user.getRegistTime()};
            qr.update(sql, params);
    }

    @Override
    public User findUserByActiveCode(String activeCode) throws SQLException {
        //获取dbUtils中的QueryRunner，，通过连接池获取
        QueryRunner qr = new QueryRunner(C3p0Utils.getDataSource());

        String sql="select * from user where activeCode= ?";

        User user = qr.query(sql, new BeanHandler<>(User.class), activeCode);
        return user;

    }

    @Override
    public void activeUser(String activeCode) throws SQLException {
//获取dbUtils中的QueryRunner，，通过连接池获取
        QueryRunner qr = new QueryRunner(C3p0Utils.getDataSource());

        String sql="update user set state=1 where activeCode= ?";

         qr.update(sql,activeCode);

    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    @Override
    public User findUserByUsernameAndPassword(String username, String password) throws SQLException{
        //获取dbUtils中的QueryRunner，，通过连接池获取
        QueryRunner qr = new QueryRunner(C3p0Utils.getDataSource());

        String sql="select * from user where username = ? and password = ?";

        User user = qr.query(sql, new BeanHandler<>(User.class),username,password);
        return user;
    }

    @Override
    public User findUserById(int userId) throws SQLException {
        //获取dbUtils中的QueryRunner，，通过连接池获取
        QueryRunner qr = new QueryRunner(C3p0Utils.getDataSource());

        String sql="select * from user where id= ?";

        User user = qr.query(sql, new BeanHandler<>(User.class), userId);
        return user;
    }

    @Override
    public void updateUser(User user) throws SQLException {
        QueryRunner qr = new QueryRunner(C3p0Utils.getDataSource());

        String sql="update user set password=?, gender=?,telephone=? where id= ?";

        qr.update(sql,user.getPassword(),user.getGender(),user.getTelephone(),user.getId());

    }
}
