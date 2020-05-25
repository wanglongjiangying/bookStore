package wanglong.Controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import wanglong.domain.Product;
import wanglong.service.ProductService;

import java.util.List;

@Controller
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/findAll.do")
    @Secured({"ROLE_son","ROLE_ADMIN"})
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue ="1")Integer page,
                                @RequestParam(name="size",required = true,defaultValue = "5")Integer size){

        ModelAndView modelAndView=new ModelAndView();
        List<Product> products = productService.findAll(page,size);

        PageInfo pageInfo=new PageInfo(products);

       modelAndView.setViewName("product-list");

        modelAndView.addObject("pageInfo",pageInfo);

        return modelAndView;
    }

     @RequestMapping("/save.do")
    public String save(Product product){

        productService.save(product);
        return "redirect:findAll.do";
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping("/findById.do")
    @PreAuthorize("hasAnyAuthority('ROLE_son') or authentication.principal.username=='wanglong'")
    public ModelAndView findById(@RequestParam(name="productId",required = true) String id){
        ModelAndView modelAndView=new ModelAndView();
        Product product = productService.findById(id);
            modelAndView.setViewName("product-update");
            modelAndView.addObject("product",product);
            return modelAndView;
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/deleteProducts.do")
    public String deleteMany(String[] ids){
        productService.deleteMany(ids);
        return "redirect:findAll.do";
    }

    /**
     * 更新操作
     * @param product
     * @return
     */
    @RequestMapping("/update.do")
    public String update(Product product){
        productService.update(product);
        return "redirect:findAll.do";
    }

    /**
     * 删除一个
     * @param id
     * @return
     */
    @RequestMapping("/deleteById.do")
    public String deleteById(@RequestParam(name="productId",required = true)String id){

        productService.deleteById(id);
        return "redirect:findAll.do";
    }

    @RequestMapping("/search.do")
    public ModelAndView search(@RequestParam(name="search",required = true)String search,
                               @RequestParam(name = "page",required = true,defaultValue ="1")Integer page,
                               @RequestParam(name="size",required = true,defaultValue = "5")Integer size){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("product-list");
        List<Product> products=productService.search(search,page,size);
        PageInfo pageInfo=new PageInfo(products);
        modelAndView.addObject("pageInfo",pageInfo);
        return modelAndView;
    }


}
