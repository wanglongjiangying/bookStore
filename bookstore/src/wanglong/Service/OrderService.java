package wanglong.Service;

import wanglong.domain.Order;

import java.util.List;

public interface OrderService {
   void saveOrderAndOrderItem(Order order);

    List<Order> findOrderByUserId(int parseInt);

    Order findOrderAndOrderitemByOrderId(String orderId);
}
