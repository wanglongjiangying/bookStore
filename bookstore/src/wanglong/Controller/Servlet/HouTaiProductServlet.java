package wanglong.Controller.Servlet;

import com.mysql.cj.util.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import wanglong.Service.HouTaiProductService;
import wanglong.Service.Impl.HouTaiProductServiceImpl;
import wanglong.Utils.downLoadUtils;
import wanglong.domain.PageResult;
import wanglong.domain.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@WebServlet(urlPatterns = {"/findProductByManyCondition","/htAddProduct","/admin/editProduct","/admin/editProduct1","/admin/deleteProduct","/admin/findProductByPage"})
public class HouTaiProductServlet extends BaseServlet {

    private HouTaiProductService houTaiProductService=new HouTaiProductServiceImpl();

    public void findProductByManyCondition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product=new Product();
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String id = request.getParameter("id");
        String minprice = request.getParameter("minprice");
        String maxprice = request.getParameter("maxprice");

        if(!StringUtils.isNullOrEmpty(name)){
            product.setName(name);
        }
        if(!StringUtils.isNullOrEmpty(category)){
            product.setCategory(category);
        }
        if(!StringUtils.isNullOrEmpty(id)){
            product.setId(Integer.parseInt(id));
        }

       List<Product> products= houTaiProductService.htFindProduct(product,minprice,maxprice);
        request.setAttribute("productList",products);
        request.getRequestDispatcher("/admin/products/list.jsp").forward(request,response);

    }

    /**
     * 分页查询商品
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    public void findProductByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product=new Product();
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String id = request.getParameter("id");
        String minprice = request.getParameter("minprice");
        String maxprice = request.getParameter("maxprice");

        String currentPage = request.getParameter("currentPage");

        if(!StringUtils.isNullOrEmpty(name)){
            product.setName(name);
        }
        if(!StringUtils.isNullOrEmpty(category)){
            product.setCategory(category);
        }
        if(!StringUtils.isNullOrEmpty(id)){
            product.setId(Integer.parseInt(id));
        }

        PageResult<Product> pageResult= houTaiProductService.htFindProductsByPage(product,minprice,maxprice,currentPage);


       request.setAttribute("pageResult",pageResult);
        request.getRequestDispatcher("/admin/products/pageList.jsp").forward(request,response);

    }









    /**
     * 文件上传
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void htAddProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                           try {
                        Product product = new Product();
                        Map<String,String> map=new HashMap<>();

                        Long l = System.currentTimeMillis();
                        String s = l.toString();
                        String s1=s.substring(7,s.length());
                        //
                        map.put("id", s1);
                        //创建磁盘文件项工厂
                        DiskFileItemFactory dfif=new DiskFileItemFactory();

                        String temp = this.getServletContext().getRealPath("/temp");
                        dfif.setRepository(new File(temp));
                        dfif.setSizeThreshold(1024*1024*10);
                        //
                        ServletFileUpload upload=new ServletFileUpload(dfif);
                        upload.setHeaderEncoding("utf-8");
                        //
                        List<FileItem> items = upload.parseRequest(request);
                        for(FileItem fileItem:items){
                            boolean flag = fileItem.isFormField();
                            if(flag){//普通数据
                                String fieldName = fileItem.getFieldName();
                                String value = fileItem.getString("utf-8");
                                map.put(fieldName,value);
                            }else{//上传文件

                                String name = fileItem.getName();

                                String fileName= name.substring(name.lastIndexOf("\\")+1,name.length());
                                //
                                String uuidFileName=UUID.randomUUID()+"_"+fileName;
                                //
                                String imgurl_parent="/productImg";
                                String imgPath=this.getServletContext().getRealPath(imgurl_parent);
                                File parentDir=new File(imgPath);
                                if(!parentDir.exists()){
                                    parentDir.mkdirs();
                                }
                                String imgurl=imgurl_parent+"/"+uuidFileName;
                                map.put("imgurl",imgurl);

                   // System.out.println(uuidFileName);

                    InputStream inputStream=fileItem.getInputStream();
                    FileOutputStream outputStream=new FileOutputStream(new File(parentDir,uuidFileName));
                    IOUtils.copy(inputStream,outputStream);
                    fileItem.delete();
                }
            }



            BeanUtils.populate(product,map);
         houTaiProductService.addProduct(product);

         request.getRequestDispatcher("/findProductByManyCondition").forward(request,response);


        }catch(Exception e) {
            e.printStackTrace();

            response.getWriter().write("添加商品失败");
        }
    }

    public void editProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String id = request.getParameter("id");

            Product product=houTaiProductService.htFindProductById(id);

            request.setAttribute("product",product);

            request.getRequestDispatcher("/admin/products/edit.jsp").forward(request,response);

        }catch(Exception e){
            e.printStackTrace();

            response.getWriter().write("编辑商品失败");
        }

    }

    public void editProduct1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Product product = new Product();
            Map<String,String> map=new HashMap<>();
            //
            DiskFileItemFactory dfif=new DiskFileItemFactory();
            String temp = this.getServletContext().getRealPath("/temp");
            dfif.setRepository(new File(temp));
            dfif.setSizeThreshold(1024*1024*10);
            //
            ServletFileUpload upload=new ServletFileUpload(dfif);
            upload.setHeaderEncoding("utf-8");
            //
            List<FileItem> items = upload.parseRequest(request);
            for(FileItem fileItem:items){
                boolean flag = fileItem.isFormField();
                if(flag){//普通数据
                    String fieldName = fileItem.getFieldName();
                    String value = fileItem.getString("utf-8");
                    map.put(fieldName,value);
                }else{//上传文件

                    String name = fileItem.getName();

                    String fileName= name.substring(name.lastIndexOf("\\")+1,name.length());
                    //
                    String uuidFileName=UUID.randomUUID()+"_"+fileName;
                    //
                    String imgurl_parent="/productImg";
                    String imgPath=this.getServletContext().getRealPath(imgurl_parent);
                    File parentDir=new File(imgPath);
                    if(!parentDir.exists()){
                        parentDir.mkdirs();
                    }
                    String imgurl=imgurl_parent+"/"+uuidFileName;
                    map.put("imgurl",imgurl);

                    // System.out.println(uuidFileName);

                    InputStream inputStream=fileItem.getInputStream();
                    FileOutputStream outputStream=new FileOutputStream(new File(parentDir,uuidFileName));
                    IOUtils.copy(inputStream,outputStream);
                    fileItem.delete();
                }
            }



            BeanUtils.populate(product,map);
            houTaiProductService.updateProduct(product);

            request.getRequestDispatcher("/findProductByManyCondition").forward(request,response);


        }catch(Exception e) {
            e.printStackTrace();

            response.getWriter().write("编辑商品失败");
        }
    }

    public void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = request.getParameter("id");

            houTaiProductService.deleteProductById(id);


         response.sendRedirect(request.getContextPath()+"/findProductByManyCondition");

        }catch(Exception e){
            e.printStackTrace();

            response.getWriter().write("删除商品失败");
        }
    }

    /*
    public void download(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String year = request.getParameter("year");
        String month = request.getParameter("month");

        //查询下载的数据
        List<Object[]> list=houTaiProductService.downLoad(year,month);


        String fileName=year+"年"+month+"月销售榜单。csv";
        String mimeType = this.getServletContext().getMimeType(fileName);
        response.setContentType(mimeType);

        response.setHeader("Content-Disposition","attachment;filename"+ downLoadUtils.getFileName(request.getHeader("User-Agent"),fileName));

        PrintWriter writer = response.getWriter();
        writer.print("商品名称，商品数量");
        for(int i=0;i<list.size();i++){
            Object[] objects = list.get(i);
            writer.write(objects[0]+"  "+objects[1]);

        }

        writer.flush();
        writer.close();


    }*/


    }
