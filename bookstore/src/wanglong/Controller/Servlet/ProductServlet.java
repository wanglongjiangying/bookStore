package wanglong.Controller.Servlet;

import wanglong.Service.Impl.ProductServiceImpl;
import wanglong.Service.ProductService;
import wanglong.domain.PageResult;
import wanglong.domain.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/showProductByPage","/productInfo"})
public class ProductServlet extends HttpServlet {

    private ProductService productService=new ProductServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String uri=requestURI.substring(requestURI.lastIndexOf("/")+1,requestURI.length());
        //分页查询商品
        if(uri.equals("showProductByPage")){
            String category = request.getParameter("category");
            String currentPage=request.getParameter("currentPage");

            int currentPage1;
            if(currentPage!=null&&!"".equals(currentPage)){
                currentPage1=Integer.parseInt(currentPage);
            }else{
                currentPage1=1;
            }
            PageResult<Product> pageResult = productService.findBooksByPage(category,currentPage1);

            request.setAttribute("pageResult",pageResult);
            request.setAttribute("category",category);

           request.getRequestDispatcher("/product_list.jsp").forward(request,response);
        }
        if(uri.equals("productInfo")){
            String id = request.getParameter("id");

           Product product=productService.findBookById(Integer.parseInt(id));


           request.setAttribute("product",product);

           request.getRequestDispatcher("/product_info.jsp").forward(request,response);



        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     doPost(request,response);
    }
}
