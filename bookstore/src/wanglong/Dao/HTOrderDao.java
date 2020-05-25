package wanglong.Dao;

import wanglong.domain.Order;
import wanglong.domain.OrderItem;

import java.sql.SQLException;
import java.util.List;

public interface HTOrderDao {
    List<Order> findOrders(String id, String receiverName) throws SQLException;

    Order viewOrderItem(String id) throws SQLException;

    void deleteOrderById(String id) throws SQLException;

    void deleteOrderItemById(String id) throws SQLException;
}
