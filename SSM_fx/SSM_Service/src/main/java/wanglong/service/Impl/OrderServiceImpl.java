package wanglong.service.Impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanglong.Dao.OrderDao;
import wanglong.domain.Order;
import wanglong.service.OrderService;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    /**
     * 分页查询所有订单
     * @param page
     * @param size
     * @return
     */
    @Override
    public List<Order> findAll(Integer page,Integer size) {
        PageHelper.startPage(page,size);
        return orderDao.findAll();
    }

    @Override
    public Order findByid(String id) {
        return orderDao.findById(id);
    }

    @Override
    public void deleteMany(String[] ids) throws Exception {
        for(String id:ids){
            orderDao.deleteOrder_traveller(id);
            orderDao.deleteById(id);
        }

    }

    @Override
    public void deleteById(String id) throws Exception {
        orderDao.deleteOrder_traveller(id);
        orderDao.deleteById(id);
    }


}
