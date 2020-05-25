package wanglong.Controller.Servlet;

import org.omg.CORBA.Request;
import wanglong.Service.Impl.ProductServiceImpl;
import wanglong.Service.ProductService;
import wanglong.domain.Product;
import wanglong.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
                                                        //判断是否登录
@WebServlet(urlPatterns = {"/addCart","/changeCardNum","/ifLogin"})
public class CartServlet extends HttpServlet {
    private ProductService productService=new ProductServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String uri=requestURI.substring(requestURI.lastIndexOf("/")+1,requestURI.length());

        //添加购物车
        if(uri.equals("addCart")){
            String id = request.getParameter("id");
            Product product=productService.findBookById(Integer.parseInt(id));
            //Map集合一个放商品一个放数量 这个Map集合放在session中
           Map<Product,Integer> cart=(Map<Product, Integer>) request.getSession().getAttribute("cart");

           //重写equals方法，用id来判断是否是同一个商品，同一个商品只需要在数量上加一
            //不重写equals方法比较的是对象的地址，每一次通过id找到对象是一个新的对象，虽然内容是一样都是，地址是不一样的


            if(cart==null){//如果没有购物车数据，新建一个Map对象
                cart=new HashMap<Product,Integer>();
                cart.put(product,1);
            }else{//如果session中有这个商品，直接数量加一
                if(cart.containsKey(product)){
                      cart.put(product,cart.get(product)+1);
                }else{
                    cart.put(product,1);
                }
            }
            //打印数据
            Set<Product> products = cart.keySet();
            for(Product product1:products){
                System.out.println(product1+":>>>>>>>"+cart.get(product1));
            }
            //设置购物车数据
            request.getSession().setAttribute("cart",cart);
           response.sendRedirect(request.getContextPath() +"/cart.jsp");
        }

        //改变购物车的数量
        if(uri.equals("changeCardNum")){
            String id = request.getParameter("id");
            String count=request.getParameter("count");
            Product product = productService.findBookById(Integer.parseInt(id));
            Map<Product,Integer> cart=(Map<Product,Integer>) request.getSession().getAttribute("cart");

          if(cart.containsKey(product)){
              if("0".equals(count)){//删除商品
                  cart.remove(product);
              }else{//添加或者减去数量
                  cart.put(product,Integer.parseInt(count));
              }

          }
          request.getSession().setAttribute("cart",cart);

          response.sendRedirect(request.getContextPath()+"/cart.jsp");
        }

        if(uri.equals("ifLogin")){
         User user= (User) request.getSession().getAttribute("user_session");

         if(user==null){
             response.sendRedirect(request.getContextPath()+"/login.jsp");
         }else{
             response.sendRedirect(request.getContextPath()+"/order.jsp");
         }

        }


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     doPost(request,response);
    }

    /*public static void main(String[] args) {
        ProductService productService=new ProductServiceImpl();

        productService.findBookById('')

    }*/
}
