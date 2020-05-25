package wanglong.Dao;

import wanglong.domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface HouTaiProductDao {
    List<Product> htFindProduct(Product product, String minprice, String maxprice) throws SQLException;

    void addProduct(Product product) throws SQLException;

    Product findProductById(String id) throws SQLException;

    void updateProduct(Product product) throws SQLException;

    void deleteProductById(String id) throws SQLException;

    List<Object[]> downLoad(String year, String month) throws SQLException;

    List<Product> htFindProductsByPage(Product product, String minprice, String maxprice, int current,int pageSize) throws SQLException;

    long getCount(Product product, String minprice, String maxprice) throws SQLException;
}
