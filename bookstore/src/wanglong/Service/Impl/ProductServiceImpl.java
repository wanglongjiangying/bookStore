package wanglong.Service.Impl;

import wanglong.Dao.Impl.ProductDaoImpl;
import wanglong.Dao.ProductDao;
import wanglong.Service.ProductService;
import wanglong.domain.PageResult;
import wanglong.domain.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private ProductDao productDao=new ProductDaoImpl();

    @Override
    public PageResult<Product> findBooksByPage(String category, int currentPage) {
        try {
           //初始化PageResult对象
            PageResult<Product> pageResult=new PageResult<Product>();
           //计算总记录数
            Long count = productDao.getCount(category);
            pageResult.setTotalCount(count);
            if(count>0) {
                int pageSize = pageResult.getPageSize();
                //计算totalPage这个方法是向上取整
                int totalPage = (int) Math.ceil(count * 1.0 / pageSize);
                pageResult.setTotalPage(totalPage);

                if (currentPage < 0) {
                    currentPage = 1;
                }
                if (currentPage > totalPage) {
                    currentPage = totalPage;
                }
                pageResult.setCurrentPage(currentPage);

                List<Product> products = productDao.findBookBypage(category, currentPage, pageSize);
                pageResult.setList(products);
            }

            return pageResult;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Product findBookById(int id) {


        return productDao.findBookById(id);
    }
}
