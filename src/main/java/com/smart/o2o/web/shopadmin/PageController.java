package com.smart.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/shop")
public class PageController {

    @RequestMapping(value = "/opshop")
    public String opShop() {
        return "shop/shop1";
    }

    @RequestMapping(value = "/shoplist")
    public String shopList() {
        return "shop/shoplist";
    }


    @RequestMapping("/shopmanagement")
    public String practiceShop() {
        return "shop/shopmanagement";
    }

    @RequestMapping("/productcategorylist")
    public String productcategorylist() {
        return "shop/productCategoryList";
    }
}
