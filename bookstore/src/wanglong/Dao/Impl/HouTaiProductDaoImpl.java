package wanglong.Dao.Impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import wanglong.Dao.HouTaiProductDao;
import wanglong.Utils.C3p0Utils;
import wanglong.domain.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HouTaiProductDaoImpl implements HouTaiProductDao {
    /**
     * 查询所有商品
     * @param product
     * @param minprice
     * @param maxprice
     * @return
     * @throws SQLException
     */
    @Override
    public List<Product> htFindProduct(Product product, String minprice, String maxprice) throws SQLException {

        StringBuilder stringBuilder=new StringBuilder("select * from products where 1=1 ");
        List<Object> list=new ArrayList<>();

        if(product.getId()!=null){
            stringBuilder.append("and id = ? ");
            list.add(product.getId());
        }
        if(product.getName()!=null){
            stringBuilder.append("and name like ? ");
            list.add("%"+product.getName().trim()+"%");
        }
        if(product.getCategory()!=null&& product.getCategory().length()>0){
            stringBuilder.append(" and category = ? ");
            list.add(product.getCategory().trim());
        }

        if(minprice!=null&&minprice.length()>0&&maxprice!=null&& maxprice.length()>0){
            stringBuilder.append(" and price between ? and ? ");

            list.add(minprice);

            list.add(maxprice);
        }
        QueryRunner qr=new QueryRunner(C3p0Utils.getDataSource());

        List<Product> query = qr.query(stringBuilder.toString(), new BeanListHandler<Product>(Product.class), list.toArray());

        return query;
    }

    /**
     * 添加商品
     * @param product
     */
    @Override
    public void addProduct(Product product) throws SQLException {

        String sql="insert into products values(?,?,?,?,?,?,?)";

        Object[] strs={product.getId(),product.getName(),product.getPrice(),product.getCategory(),product.getPnum(),product.getImgurl(),product.getDescription()};

        QueryRunner qr=new QueryRunner(C3p0Utils.getDataSource());

        qr.update(sql,strs);
    }

    /**
     * 根据id查早商品
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public Product findProductById(String id) throws SQLException {

        String sql="select * from products where id=? ";


        QueryRunner qr=new QueryRunner(C3p0Utils.getDataSource());

        Product query = qr.query(sql, new BeanHandler<Product>(Product.class), id);

        return query;

    }

    @Override
    public void updateProduct(Product product) throws SQLException {

        String sql="update products set name=?,price=?,category=?,pnum=?,imgurl=?,description=? where id =? ";

        Object[] strs={product.getName(),product.getPrice(),product.getCategory(),product.getPnum(),product.getImgurl(),product.getDescription(),product.getId()};

        QueryRunner qr=new QueryRunner(C3p0Utils.getDataSource());

        qr.update(sql,strs);
    }

    @Override
    public void deleteProductById(String id) throws SQLException {

        String sql="delete from products where id =? ";


        QueryRunner qr=new QueryRunner(C3p0Utils.getDataSource());

        qr.update(sql,id);
    }

    @Override
    public List<Object[]> downLoad(String year, String month) throws SQLException {
        String sql="SELECT products.name,SUM(orderitem.buynum) AS total FROM products,orders,orderitem WHERE 1=1 AND orders.`id`=orderitem.`order_id` AND orderitem.`product_id`=products.`id` AND orders.`paystate`=1 AND YEAR(ordertime)=? AND MONTH(ordertime)=? GROUP BY  products.`name` ORDER BY total DESC;";


        QueryRunner qr=new QueryRunner(C3p0Utils.getDataSource());

        List<Object[]> query = qr.query(sql, new ArrayListHandler(), year, month);
        return query;
    }

    @Override
    public List<Product> htFindProductsByPage(Product product, String minprice, String maxprice, int current,int pageSize) throws SQLException {


        StringBuilder stringBuilder=new StringBuilder("select * from products where 1=1 ");
        List<Object> list=new ArrayList<>();

        if(product.getId()!=null){
            stringBuilder.append("and id = ? ");
            list.add(product.getId());
        }
        if(product.getName()!=null){
            stringBuilder.append("and name like ? ");
            list.add("%"+product.getName().trim()+"%");
        }
        if(product.getCategory()!=null&& product.getCategory().length()>0){
            stringBuilder.append(" and category = ? ");
            list.add(product.getCategory().trim());
        }

        if(minprice!=null&&minprice.length()>0&&maxprice!=null&& maxprice.length()>0){
            stringBuilder.append(" and price between ? and ? ");
            list.add(minprice);
            list.add(maxprice);
        }

        //添加分页条件

        stringBuilder.append(" limit ?, ?");
        int start=(current-1)*pageSize;

        list.add(start);
        list.add(pageSize);

        QueryRunner queryRunner=new QueryRunner(C3p0Utils.getDataSource());

        List<Product> query = queryRunner.query(stringBuilder.toString(), new BeanListHandler<Product>(Product.class), list.toArray());


        return query;
    }

    @Override
    public long getCount(Product product, String minprice, String maxprice) throws SQLException {
        StringBuilder stringBuilder=new StringBuilder("select count(0) from products where 1=1 ");
        List<Object> list=new ArrayList<>();

        if(product.getId()!=null){
            stringBuilder.append("and id = ? ");
            list.add(product.getId());
        }
        if(product.getName()!=null){
            stringBuilder.append("and name like ? ");
            list.add("%"+product.getName().trim()+"%");
        }
        if(product.getCategory()!=null&& product.getCategory().length()>0){
            stringBuilder.append(" and category = ? ");
            list.add(product.getCategory().trim());
        }

        if(minprice!=null&&minprice.length()>0&&maxprice!=null&& maxprice.length()>0){
            stringBuilder.append(" and price between ? and ? ");

            list.add(minprice);

            list.add(maxprice);
        }


        QueryRunner qr=new QueryRunner(C3p0Utils.getDataSource());
        long count =(long) qr.query(stringBuilder.toString(), new ScalarHandler(), list.toArray());
        return count;
    }


}
