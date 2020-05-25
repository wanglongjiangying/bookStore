package wanglong.Dao;

import wanglong.domain.Order;
import wanglong.domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    Long getCount(String category) throws SQLException;

    List<Product> findBookBypage(String category,int currentPage,int pageSize) throws SQLException;

    Product findBookById(int id);

    void updatePnum(Order order) throws SQLException;
}
