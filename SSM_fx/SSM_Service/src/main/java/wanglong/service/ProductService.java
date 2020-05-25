package wanglong.service;

import wanglong.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll(Integer page,Integer size);

    void save(Product product);

    Product findById(String id);

    /**
     * 批量删除
     * @param ids
     */
    void deleteMany(String[] ids);

    void update(Product product);

    void deleteById(String id);

    List<Product> search(String search,Integer page,Integer size);
}


