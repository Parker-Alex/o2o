package com.smart.o2o.web.fore;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class ForePageController {

    @RequestMapping("/homepage")
    public String homePage() {
        return "fore/home";
    }

    @RequestMapping("/shoplist")
    public String shopList() {
        return "fore/shoplist";
    }

    @RequestMapping("/ttt")
    public String test() {
        return "tttt";
    }
}
