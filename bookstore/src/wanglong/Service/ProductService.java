package wanglong.Service;

import wanglong.domain.PageResult;
import wanglong.domain.Product;

public interface ProductService {
    PageResult<Product> findBooksByPage(String category,int currentPage);

    Product findBookById(int parseInt);
}
