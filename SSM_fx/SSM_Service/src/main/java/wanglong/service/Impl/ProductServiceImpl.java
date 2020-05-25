package wanglong.service.Impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanglong.Dao.ProductDao;
import wanglong.domain.Product;
import wanglong.service.ProductService;

import java.util.List;
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> findAll(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        List<Product> products = productDao.findAll();
        return products;
    }

    @Override
    public void save(Product product) {
        productDao.save(product);
    }

    @Override
    public Product findById(String id) {
        return productDao.findById(id);
    }

    @Override
    public void deleteMany(String[] ids) {

        for (String id:ids){
            productDao.deleteById(id);
        }

    }

    @Override
    public void update(Product product) {
        productDao.update(product);
    }

    @Override
    public void deleteById(String id) {
        productDao.deleteById(id);
    }

    @Override
    public List<Product> search(String search,Integer page,Integer size) {

        PageHelper.startPage(page,size);
        search=search.toUpperCase();
        List<Product> products= productDao.search("%"+search+"%");
        return products;
    }
}
