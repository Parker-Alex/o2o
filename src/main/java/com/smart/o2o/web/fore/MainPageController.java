package com.smart.o2o.web.fore;

import com.smart.o2o.entity.Headline;
import com.smart.o2o.entity.ShopCategory;
import com.smart.o2o.service.HeadLineService;
import com.smart.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/fore")
public class MainPageController {

    @Autowired
    private HeadLineService headLineService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    /**
     * 展示主页信息方法，
     * @param request
     * @return  头条列表，一级商店类别列表
     */
    @RequestMapping(value = "/mainpageinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> mainPageInfo(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            Headline headline = new Headline();
            headline.setStatus(1);
            List<Headline> headlines = headLineService.getList(headline);
            map.put("headlines", headlines);
        }catch (Exception e) {
            map.put("success", false);
            map.put("msg", e.toString());
            return map;
        }
        try {
            List<ShopCategory> shopCategories = shopCategoryService.readShopCategoryList(null);
            map.put("shopCategories", shopCategories);
        }catch (Exception e) {
            map.put("success", false);
            map.put("msg", e.toString());
            return map;
        }
        map.put("success", true);
        return map;
    }


}
