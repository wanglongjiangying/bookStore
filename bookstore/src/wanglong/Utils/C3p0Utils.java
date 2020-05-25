package wanglong.Utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class C3p0Utils {

    //从c3p0中获取连接池
    private static DataSource ds  = new ComboPooledDataSource();

    public static DataSource getDataSource(){
        return ds;
    }
    //从连接池中获取连接
    public static Connection getConnection(){
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException("服务器错误");
        }
    }
    //关闭连接（用连接池基本上不用关闭，不用会自动放到连接池中）
    public static void closeAll(Connection conn, Statement statement, ResultSet resultSet){
        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            resultSet = null;
        }
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            statement = null;
        }
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                conn = null;
            }
        }
    }
}
