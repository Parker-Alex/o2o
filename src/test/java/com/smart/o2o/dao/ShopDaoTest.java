package com.smart.o2o.dao;

import com.smart.o2o.BaseTest;
import com.smart.o2o.entity.Area;
import com.smart.o2o.entity.Shop;
import com.smart.o2o.entity.ShopCategory;
import com.smart.o2o.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ShopDaoTest extends BaseTest {

    @Autowired
    private ShopDao shopDao;

    @Test
    public void createShop() {
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        User user = new User();
        Shop shop = new Shop();
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        user.setUserId(1L);
        shop.setShopCategory(shopCategory);
        shop.setUser(user);
        shop.setArea(area);
        shop.setShopName("测试4");
        shop.setPriority(2);
        shop.setShopImg("test");
        shop.setShopAddr("test");
        shop.setShopDesc("test");
        shop.setShopTel("test");
        shop.setShopAdvice("test");
        shop.setStatus(2);
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
        int effectedNum = shopDao.createShop(shop);
//        System.out.println(area);
        assertEquals(1, effectedNum);
    }

    @Test
    public void updateShop() {
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        User user = new User();
        Shop shop = new Shop();
        shop.setShopId(6L);
        area.setAreaId(1);
        shopCategory.setShopCategoryId(1L);
        shop.setShopCategory(shopCategory);
        shop.setUser(user);
        shop.setArea(area);
        shop.setShopName("测试11");
        shop.setPriority(0);
        shop.setShopImg("测试");
        shop.setShopAddr("测试");
        shop.setShopDesc("测试");
        shop.setShopTel("测试");
        shop.setShopAdvice("测试");
        shop.setStatus(0);
        shop.setLastEditTime(new Date());
        int effectedNum = shopDao.updateShop(shop);
//        System.out.println(area);
        assertEquals(1, effectedNum);
        System.out.println(shop);
    }

    @Test
    public void readByShopId() {
        Shop shop = shopDao.readByShopId(1L);
        System.out.println("areaId: " + shop.getArea().getAreaId());
        System.out.println("areaNmae: " + shop.getArea().getAreaName());
    }

    @Test
    public void queryShopList() {
        Shop shop = new Shop();
//        shop.setShopName("测试");
//        shop.setStatus(0);
//        ShopCategory shopCategory = new ShopCategory();
//        shopCategory.setShopCategoryId(2L);
//        shop.setShopCategory(shopCategory);
//        User user= new User();
//        user.setUserId(1L);
//        shop.setUser(user);
        Area area = new Area();
        area.setAreaId(2);
        shop.setArea(area);
        List<Shop> shopList = shopDao.queryShopList(shop, 0, 7);
        for (int i=0; i<shopList.size(); i++) {
            System.out.println(shopList.get(i));
        }
    }

    @Test
    public void getCount() {
        Shop shop = new Shop();
        Area area = new Area();
        area.setAreaId(2);
        shop.setArea(area);
        System.out.println(shopDao.getCount(shop));
    }
}