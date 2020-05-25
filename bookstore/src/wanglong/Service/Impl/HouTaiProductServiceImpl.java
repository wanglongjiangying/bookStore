package wanglong.Service.Impl;

import com.mysql.cj.util.StringUtils;
import wanglong.Dao.HouTaiProductDao;
import wanglong.Dao.Impl.HouTaiProductDaoImpl;
import wanglong.Service.HouTaiProductService;
import wanglong.domain.PageResult;
import wanglong.domain.Product;

import java.sql.SQLException;
import java.util.List;

public class HouTaiProductServiceImpl implements HouTaiProductService {

    private HouTaiProductDao houTaiProductDao=new HouTaiProductDaoImpl();

    @Override
    public List<Product> htFindProduct(Product product, String minprice, String maxprice) {
        try {
            return houTaiProductDao.htFindProduct(product, minprice, maxprice);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void addProduct(Product product) {
        try {
            houTaiProductDao.addProduct(product);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public Product htFindProductById(String id) throws SQLException {

      return  houTaiProductDao.findProductById(id);
    }

    @Override
    public void updateProduct(Product product) throws SQLException {
        houTaiProductDao.updateProduct(product);
    }

    @Override
    public void deleteProductById(String id) throws SQLException {
        houTaiProductDao.deleteProductById(id);
    }

    @Override
    public List<Object[]> downLoad(String year, String month) {
        try {
            return houTaiProductDao.downLoad(year,month);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 分页查询
     * @param product
     * @param minprice
     * @param maxprice
     * @param currentPage
     * @return
     */
    @Override
    public PageResult<Product> htFindProductsByPage(Product product, String minprice, String maxprice, String currentPage) {
        try {

            PageResult<Product> pageResult = new PageResult<>();


            long totalCount = houTaiProductDao.getCount(product, minprice, maxprice);

           pageResult.setTotalCount(totalCount);

            if(totalCount>0) {

                int current;
                if (StringUtils.isNullOrEmpty(currentPage)) {
                    current = 1;
                } else {
                    current = Integer.parseInt(currentPage);
                }

                int totalPage=(int)Math.ceil((totalCount*1.0)/pageResult.getPageSize());
                pageResult.setTotalPage(totalPage);

                pageResult.setCurrentPage(current);
                List<Product> products = houTaiProductDao.htFindProductsByPage(product, minprice, maxprice, current, pageResult.getPageSize());
                pageResult.setList(products);
            }

         return pageResult;
        }catch (Exception e){
            e.printStackTrace();
        }









        return null;
    }
}
