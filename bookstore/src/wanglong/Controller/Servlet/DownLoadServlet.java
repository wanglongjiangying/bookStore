package wanglong.Controller.Servlet;

import wanglong.Service.HouTaiProductService;
import wanglong.Service.Impl.HouTaiProductServiceImpl;
import wanglong.Utils.downLoadUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/admin/download")
public class DownLoadServlet extends HttpServlet {
    private HouTaiProductService houTaiProductService=new HouTaiProductServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String year = request.getParameter("year");
        String month = request.getParameter("month");

        //查询下载的数据
        List<Object[]> list=houTaiProductService.downLoad(year,month);


        String fileName=year+"年"+month+"月销售榜单.xlsx";
        String mimeType = this.getServletContext().getMimeType(fileName);
        response.setContentType(mimeType);

        response.setHeader("Content-Disposition","attachment;filename="+ downLoadUtils.getFileName(request.getHeader("User-Agent"),fileName));

        PrintWriter writer = response.getWriter();
        writer.print("商品名称，商品数量");
        for(int i=0;i<list.size();i++){
            Object[] objects = list.get(i);
            writer.println(objects[0]+","+objects[1]);

            System.out.println(objects[0]);

        }

        writer.flush();
        writer.close();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
doPost(request,response);
    }
}
