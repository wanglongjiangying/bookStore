package wanglong.Service;

import wanglong.domain.PageResult;
import wanglong.domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface HouTaiProductService {
    List<Product> htFindProduct(Product product, String minprice, String maxprice);

    void addProduct(Product product);

    Product htFindProductById(String id) throws SQLException;

    void updateProduct(Product product) throws SQLException;

    void deleteProductById(String id) throws SQLException;

    List<Object[]> downLoad(String year, String month);

    PageResult<Product> htFindProductsByPage(Product product, String minprice, String maxprice, String currentPage);
}

