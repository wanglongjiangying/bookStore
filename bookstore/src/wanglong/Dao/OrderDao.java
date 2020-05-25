package wanglong.Dao;

import wanglong.domain.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    void addOrder(Order order) throws SQLException;

    void addOrderItems(Order order) throws SQLException;

    List<Order> findOrderByUserId(int userId) throws SQLException;

    Order findOrderAndOrderitemsByOrderId(String orderId)throws SQLException;
}
