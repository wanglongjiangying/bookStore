package wanglong.Controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import wanglong.domain.Order;
import wanglong.service.OrderService;

import java.util.List;

@Controller
@RequestMapping("orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue ="1")Integer page,
                                @RequestParam(name="size",required = true,defaultValue = "5")Integer size){
       ModelAndView modelAndView=new ModelAndView();
      List<Order> orders=orderService.findAll(page,size);
        PageInfo pageInfo=new PageInfo(orders);
       modelAndView.setViewName("orders-list");
       modelAndView.addObject("pageInfo",pageInfo);
       return modelAndView;
    }
    @RequestMapping("/deleteMany.do")
    public String deleteMany(String[] ids){

        try {
            for(String id:ids){
                System.out.println(id);
            }
           // orderService.deleteMany(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:findAll.do";
    }

    @RequestMapping("/deleteById.do")
    public String deleteById(@RequestParam(name="id")String id){

        try {
            orderService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:findAll.do";
    }

    @RequestMapping("/findById.do")
    public String findById(@RequestParam(name = "id",required=true)String ordersId, Model model)  {
        try {


            Order order = orderService.findByid(ordersId);

            System.out.println(order);

            model.addAttribute("orders", order);


            return "order-show";
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }



}
