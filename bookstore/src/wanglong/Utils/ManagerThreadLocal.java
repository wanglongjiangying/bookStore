package wanglong.Utils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *事务管理
 */
public class ManagerThreadLocal {

    //创建本地线程，
    private static ThreadLocal<Connection> threadLocal=new ThreadLocal<Connection>();

    public static Connection getConnection() {
        try {

            Connection connection = threadLocal.get();
            //如果本地线程中没有连接，先键连接
            if (connection == null) {
                connection = C3p0Utils.getConnection();
                threadLocal.set(connection);
                System.out.println("第一次获取连接");
            } else {
                System.out.println("从threadLocal获取连接");
            }
            return connection;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 开起线程就是把关闭自动提交
     */
    public static void beginTransaction() {
        try {
            getConnection().setAutoCommit(false);
        }catch (Exception e){
                e.printStackTrace();
        }
    }

    /**
     *
     */
    public static void commitTransaction(){

        try {
            getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rollback(){
        try {
            getConnection().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 在关闭连接的时候，把本地线程移除
     */
    public static void closeConnection(){
        try {
            getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            threadLocal.remove();
        }
    }


}
