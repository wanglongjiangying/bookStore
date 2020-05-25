package wanglong.Dao.Impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import wanglong.Dao.OrderDao;
import wanglong.Utils.C3p0Utils;
import wanglong.Utils.ManagerThreadLocal;
import wanglong.domain.Order;
import wanglong.domain.OrderItem;
import wanglong.domain.Product;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    /**
     * 添加订单数据
     * @param order
     */
    @Override
    public void addOrder(Order order) throws SQLException {



            String sql = "insert into orders values(?,?,?,?,?,?,?,?)";

            Object[] params = {order.getId(), order.getMoney(), order.getReceiverAddress(), order.getReceiverName(),
                    order.getReceiverPhone(), order.getPaystate(), order.getOrdertime(), order.getUser().getId()};
        /*QueryRunner qr = new QueryRunner(C3p0Utils.getDataSource());
            qr.update(sql, params);
         */


        QueryRunner qr=new QueryRunner();
        qr.update(ManagerThreadLocal.getConnection(),sql,params);

    }

    /**
     * 添加订单详情数据
     * @param order
    @Override
    public void addOrderItems(Order order) {
        try {
            QueryRunner qr = new QueryRunner(C3p0Utils.getDataSource());

            String sql = "insert into orderitem values(?,?,?)";
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                Object[] params = {orderItem.getOrder().getId(), orderItem.getProduct().getId(), orderItem.getBuynum()};
                qr.update(sql, params);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }*/

    /**
     * 用批处理来优化，减少对数据库的操作
     * @param order
     */
    @Override
    public void addOrderItems(Order order) throws SQLException {


            String sql = "insert into orderitem values(?,?,?)";
         List<OrderItem> orderItems=order.getOrderItems();

            //定义二维数组时，第一个数组的长度必须赋值，
            Object[][] params=new Object[orderItems.size()][];
            for(int i=0;i<orderItems.size();i++){
                OrderItem orderItem = orderItems.get(i);
                params[i]=new Object[]{orderItem.getOrder().getId(),orderItem.getProduct().getId(),orderItem.getBuynum()};
            }
            /*
             QueryRunner qr = new QueryRunner(C3p0Utils.getDataSource());
             qr.batch(sql,params);
             */

        QueryRunner qr=new QueryRunner();
        qr.batch(ManagerThreadLocal.getConnection(),sql,params);



    }

    @Override
    public List<Order> findOrderByUserId(int userId) throws SQLException {

        QueryRunner qr=new QueryRunner(C3p0Utils.getDataSource());
        String sql="select * from orders where user_id= ? ";
        List<Order> orders = qr.query(sql, new BeanListHandler<Order>(Order.class), userId);

        return orders;
    }

    /**
     * 通过订单详情查看订单详情
     * 订单详情需要（订单id,收件人，收货地址，联系方式）order表
     *  （商品名称，价格）product表
     *  （数量）在中间表orderItem
     *
     * @param orderId
     * @return
     * @throws SQLException
     */
    @Override
    public Order findOrderAndOrderitemsByOrderId(String orderId) throws SQLException {
        //想查询Order信息，用于显示订单编号
        QueryRunner qr=new QueryRunner(C3p0Utils.getDataSource());

        //（订单id,收件人，收货地址，联系方式）order表
        String sql="select * from orders where id= ? ";
        Order order = qr.query(sql, new BeanHandler<Order>(Order.class), orderId);
        //（商品名称，价格）product表
        //数量）在中间表orderItem
        String sql1="select o.*,p.name,p.price from orderitem o,products p where p.id=o.product_id and order_id = ?";

        List<OrderItem> orderItems = qr.query(sql1, new ResultSetHandler<List<OrderItem>>() {

            @Override
            public List<OrderItem> handle(ResultSet resultSet) throws SQLException {
                //创建集合对象
                List<OrderItem> items = new ArrayList<>();
                while (resultSet.next()) {
                    OrderItem orderItem = new OrderItem();

                    orderItem.setBuynum(resultSet.getInt("buynum"));
                    Product product = new Product();
                    //
                    product.setName(resultSet.getString("name"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setId(resultSet.getInt("product_id"));
                    orderItem.setProduct(product);

                    items.add(orderItem);
                }
                return items;
            }
        }, orderId);

        order.setOrderItems(orderItems);
        return order;
    }


}
