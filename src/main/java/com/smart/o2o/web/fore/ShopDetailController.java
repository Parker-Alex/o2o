package com.smart.o2o.web.fore;

import com.smart.o2o.entity.Product;
import com.smart.o2o.service.ProductCategoryService;
import com.smart.o2o.service.ProductService;
import com.smart.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/fore")
public class ShopDetailController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping("/productlist")
    @ResponseBody
    public Map<String, Object> productList(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");

        if (pageIndex > -1 && pageSize > -1) {
//            productService.productListByCondition();
        }
        return map;
    }
}
