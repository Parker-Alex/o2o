package com.smart.o2o.web.fore;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class ForePageController {

    //跳转主页面路由
    @RequestMapping("/homepage")
    public String homePage() {
        return "fore/home";
    }
    //跳转店铺列表路由
    @RequestMapping("/shoplist")
    public String shopList() {
        return "fore/shoplist";
    }
    //跳转店铺细节路由
    @RequestMapping("/shopdetail")
    public String test() {
        return "fore/shopdetail";
    }
}
