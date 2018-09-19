package com.smart.o2o.web.shopadmin;

import com.smart.o2o.dto.ProductCategoryExecution;
import com.smart.o2o.dto.Result;
import com.smart.o2o.entity.ProductCategory;
import com.smart.o2o.entity.Shop;
import com.smart.o2o.enums.ProductCategoryEnum;
import com.smart.o2o.exceptions.ProductCategoryException;
import com.smart.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 得到商品类别列表方法
     * @param request
     * @return
     */
    @RequestMapping(value = "/productcategorylist", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<ProductCategory>> productcategorylist(HttpServletRequest request) {
        Shop shop = (Shop) request.getSession().getAttribute("currentShop");
        if (shop != null && shop.getShopId() > 0) {
            List<ProductCategory> list = productCategoryService.listBySid(shop.getShopId());
            return new Result<List<ProductCategory>>(true, list);
        }else {
            ProductCategoryEnum pce = ProductCategoryEnum.INNER_ERROR;
            return new Result<List<ProductCategory>>(false, pce.getMsg(), pce.getCode());
        }
    }

    /**
     * 添加商品类别方法
     * @param list
     * @param request
     * @return
     */
    @RequestMapping(value = "/addproductcategory", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addProductCategory(@RequestBody List<ProductCategory> list,
                                                  HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Shop shop = (Shop) request.getSession().getAttribute("currentShop");
        for (ProductCategory pc : list) {
            pc.setCreateTime(new Date());
            pc.setSid(shop.getShopId());
        }
        if (list.size() > 0) {
            try {
                ProductCategoryExecution pce = productCategoryService.batchlist(list);
                if (pce.getState() == ProductCategoryEnum.SUCCESS.getCode()) {
                    map.put("success", true);
                }else {
                    map.put("success", false);
                    map.put("msg", pce.getStateInfo());
                }
            }catch (ProductCategoryException e) {
                map.put("success", false);
                map.put("msg", e.getMessage()+e.toString());
                return map;
            }
        }else {
            map.put("success", false);
            map.put("msg", "你还没有添加任何一个商品类别");
        }
        return map;
    }

    /**
     * 删除商品类别方法
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/deleteproductcategory", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteProductcategory(Long id, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        if (id != null && id > 0) {
            try {
                Shop shop = (Shop) request.getSession().getAttribute("currentShop");
                ProductCategoryExecution pce= productCategoryService.deletePC(id, shop.getShopId());
                if (pce.getState() == ProductCategoryEnum.SUCCESS.getCode()) {
                    map.put("success", true);
                }else {
                    map.put("success", false);
                    map.put("msg", pce.getStateInfo());
                }
            }catch (ProductCategoryException e) {
                map.put("success", false);
                map.put("msg", e.toString());
                return map;
            }
        }else {
            map.put("success", false);
            map.put("msg", "请选择要删除的对象");
        }
        return map;
    }

//    @RequestMapping(value = "/deleteproductcategory", method = RequestMethod.GET)
//    public String deleteProductcategory(@RequestParam("id") Long id) {
//        int num = productCategoryService.deletePC(id);
//        if (num > 0) {
//            return "redirect:/shopadmin/productcategorylist";
//        }else {
//            throw new ProductCategoryException("删除失败");
//        }
//    }

}
