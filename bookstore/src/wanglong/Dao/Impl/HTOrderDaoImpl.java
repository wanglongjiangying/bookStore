package wanglong.Dao.Impl;

import com.mysql.cj.util.StringUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import wanglong.Dao.HTOrderDao;
import wanglong.Utils.C3p0Utils;
import wanglong.Utils.ManagerThreadLocal;
import wanglong.domain.Order;
import wanglong.domain.OrderItem;
import wanglong.domain.Product;
import wanglong.domain.User;

import javax.xml.bind.PrintConversionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HTOrderDaoImpl implements HTOrderDao {



    @Override
    public List<Order> findOrders(String id, String receiverName) throws SQLException {
        StringBuilder stringBuilder=new StringBuilder("SELECT u.username,o.* FROM orders o,USER u WHERE  o.user_id=u.id ");
        List<Object> list=new ArrayList<>();

        if(!StringUtils.isNullOrEmpty(id)){
            stringBuilder.append("and o.id like ? ");
            list.add("%"+id.trim()+"%");
        }
        if(!StringUtils.isNullOrEmpty(receiverName)){
            stringBuilder.append("and o.receiverName like ? ");
            list.add("%"+receiverName.trim()+"%");
        }

       // List<Order> orderList=new ArrayList<>();
       // Order order=new Order();
        QueryRunner qr=new QueryRunner(C3p0Utils.getDataSource());
        List<Order> orderList = qr.query(stringBuilder.toString(), new ResultSetHandler<List<Order>>() {
            List<Order> orders=new ArrayList<>();
            @Override
            public List<Order> handle(ResultSet resultSet) throws SQLException {
                while(resultSet.next()){
                    Order order=new Order();
                    User user=new User();
                    order.setId(resultSet.getString("id"));
                    order.setReceiverName(resultSet.getString("receiverName"));
                    order.setReceiverPhone(resultSet.getString("receiverPhone"));
                    order.setReceiverAddress(resultSet.getString("receiverAddress"));
                    user.setId(resultSet.getInt("user_id"));
                    user.setUsername(resultSet.getString("username"));
                    order.setUser(user);
                    order.setOrdertime(resultSet.getDate("ordertime"));
                    order.setPaystate(resultSet.getInt("paystate"));
                    order.setMoney(resultSet.getDouble("money"));
                    orders.add(order);
                    System.out.println("::"+order);
                }
                return orders;
            }
        }, list.toArray());
        return orderList;
    }

    @Override
    public Order viewOrderItem(String id) throws SQLException {
           String sql=" SELECT user.`username`, orders.*,products.*, orderitem.* FROM USER,orders ,products,orderitem WHERE user.id=orders.`user_id` AND orders.id=orderitem.`order_id` AND orderitem.`product_id`=products.`id` AND orders.id = ? ";


        QueryRunner qr=new QueryRunner(C3p0Utils.getDataSource());
        Order order1= qr.query(sql, new ResultSetHandler<Order>() {
            @Override
            public Order handle(ResultSet resultSet) throws SQLException {

                Order order=new Order();
                List<OrderItem> orderItems=new ArrayList<>();
                while(resultSet.next()){
                    OrderItem orderItem1=new OrderItem();
                    User user=new User();
                    user.setId(resultSet.getInt("user_id"));
                    user.setUsername(resultSet.getString("username"));
                    order.setId(resultSet.getString("id"));
                    order.setReceiverName(resultSet.getString("receiverName"));
                    order.setReceiverPhone(resultSet.getString("receiverPhone"));
                    order.setReceiverAddress(resultSet.getString("receiverAddress"));
                    order.setUser(user);
                    order.setOrdertime(resultSet.getDate("ordertime"));
                    order.setPaystate(resultSet.getInt("paystate"));
                    order.setMoney(resultSet.getDouble("money"));
                    //封装product数据
                    Product product=new Product();
                    //product.setId(resultSet.getInt("product_id"));
                    String sid = resultSet.getString("product_id");
                    product.setId(Integer.parseInt(sid));
                    product.setCategory(resultSet.getString("category"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setName(resultSet.getString("name"));
                    product.setDescription(resultSet.getString("description"));
                    product.setImgurl(resultSet.getString("imgurl"));

                    orderItem1.setBuynum(resultSet.getInt("buynum"));
                    orderItem1.setProduct(product);

                   orderItems.add(orderItem1);


                }
                order.setOrderItems(orderItems);

                System.out.println(order);

                return order;
            }
        }, id);


        return order1;

    }

    @Override
    public void deleteOrderById(String id) throws SQLException {
        String sql="delete from orders where id=?";
        QueryRunner qr=new QueryRunner();
        qr.update(ManagerThreadLocal.getConnection(),sql,id);
    }

    @Override
    public void deleteOrderItemById(String id) throws SQLException {
        String sql="delete from orderitem where order_id=?";
        QueryRunner qr=new QueryRunner();
        qr.update(ManagerThreadLocal.getConnection(),sql,id);
    }
}
