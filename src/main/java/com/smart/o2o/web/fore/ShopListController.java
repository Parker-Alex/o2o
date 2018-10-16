package com.smart.o2o.web.fore;

import com.smart.o2o.dto.ShopExecution;
import com.smart.o2o.entity.Area;
import com.smart.o2o.entity.Shop;
import com.smart.o2o.entity.ShopCategory;
import com.smart.o2o.service.AreaService;
import com.smart.o2o.service.ShopCategoryService;
import com.smart.o2o.service.ShopService;
import com.smart.o2o.util.HttpServletRequestUtil;
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
public class ShopListController {

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private ShopService shopService;

    /**
     * 得到商店列表和区域列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/shopListpageinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> shopListPageInfo(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        long parentId = HttpServletRequestUtil.getLong(request, "parentId");
        List<ShopCategory> shopCategoryList = null;
        //如果店铺类别有parentId，查找该parentId下的所有店铺类别
        if (parentId != -1) {
            try {
                ShopCategory scCondition = new ShopCategory();
                ShopCategory parent = new ShopCategory();
                parent.setShopCategoryId(parentId);
                scCondition.setParent(parent);
                shopCategoryList = shopCategoryService.readShopCategoryList(scCondition);
            }catch (Exception e) {
                map.put("success", false);
                map.put("msg", e.toString());
            }
        //如果店铺类别没有的parentId，那么是一级类别，然后查找所有一级店铺类别
        }else {
            try {
                shopCategoryList = shopCategoryService.readShopCategoryList(null);
            }catch (Exception e) {
                map.put("success", false);
                map.put("msg", e.toString());
            }
        }
        map.put("sclist", shopCategoryList);
        List<Area> areaList = null;
        try{
            areaList = areaService.readAreaList();
            map.put("arealist", areaList);
            map.put("success", true);
            return map;
        }catch (Exception e) {
            map.put("success", false);
            map.put("msg", e.toString());
        }
        return map;
    }

    /**
     * 条件查询店铺列表方法
     * @param request
     * @return
     */
    @RequestMapping(value = "listshop", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listShop(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if (pageIndex > -1 && pageSize > -1) {
            String shopName = HttpServletRequestUtil.getString(request, "shopName");
            Long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            Long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
            Integer areaId = HttpServletRequestUtil.getInt(request, "areaId");
            Shop shopCondition = compactShopCondition4Search(shopName, parentId, shopCategoryId, areaId);
            ShopExecution se = shopService.queryShopList(shopCondition, pageIndex, pageSize);
            map.put("shoplist", se.getShopList());
            map.put("count", se.getCount());
            map.put("success", true);
            return map;
        }else {
            map.put("success", false);
            map.put("msg", "请输入页码或每页大小");
        }
        return map;
    }

    /**
     * 将店铺相关信息组合进店铺对象
     * @param shopName
     * @param parentId
     * @param shopCategoryId
     * @param areaId
     * @return
     */
    private Shop compactShopCondition4Search(String shopName, Long parentId, Long shopCategoryId, Integer areaId) {
        Shop shopCondition = new Shop();
        if (parentId != -1L) {
            ShopCategory childSC = new ShopCategory();
            ShopCategory parentSC = new ShopCategory();
            parentSC.setShopCategoryId(parentId);
            childSC.setParent(parentSC);
            shopCondition.setShopCategory(childSC);
        }
        if (shopCategoryId != -1L) {
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }
        if (areaId != -1) {
            Area area = new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }
        if (shopName != null) {
            shopCondition.setShopName(shopName);
        }
        shopCondition.setStatus(1);
        return shopCondition;
    }
}
