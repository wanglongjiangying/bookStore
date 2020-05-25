package wanglong.service;

import wanglong.domain.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAll(Integer page,Integer size);

    Order findByid(String id);

    void deleteMany(String[] ids) throws Exception;

    void deleteById(String id) throws Exception;
}

