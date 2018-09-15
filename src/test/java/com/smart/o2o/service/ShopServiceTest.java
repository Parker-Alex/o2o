package com.smart.o2o.service;

import com.smart.o2o.BaseTest;
import com.smart.o2o.dto.ShopExecution;
import com.smart.o2o.entity.Area;
import com.smart.o2o.entity.Shop;
import com.smart.o2o.entity.ShopCategory;
import com.smart.o2o.entity.User;
import com.smart.o2o.enums.ShopEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.*;

public class ShopServiceTest extends BaseTest {

    @Autowired
    private ShopService shopService;

    @Test
    public void createShop() throws FileNotFoundException {
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
        shop.setShopName("Service测试5");
        shop.setPriority(2);
        shop.setShopImg("s-test");
        shop.setShopDesc("s-test");
        shop.setShopTel("s-test");
        shop.setShopAdvice(ShopEnum.CHECK.getStateInfo());
        shop.setCreateTime(new Date());
        shop.setStatus(ShopEnum.CHECK.getState());
        File file = new File("E:/File/o2o/1.jpg");
        InputStream is = new FileInputStream(file);
        ShopExecution shopExecution = shopService.createShop(shop, is, file.getName());
        assertEquals(ShopEnum.CHECK.getState(), shopExecution.getState());
    }

    @Test
    public void updateShop() throws FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(11L);
        shop.setShopName("测试后的名字");
        File file = new File("E:/File/img/1.jpg");
        InputStream inputStream = new FileInputStream(file);
        ShopExecution shopExecution = shopService.updateShop(shop, inputStream, file.getName());
        assertEquals(ShopEnum.SUCCESS.getState(), shopExecution.getState());
        System.out.println("图片地址: " + shopExecution.getShop().getShopImg());
    }

    @Test
    public void queryShopList() {
        Shop shop = new Shop();
        User user = new User();
        user.setUserId(1L);
        shop.setUser(user);
        ShopExecution shopExecution = shopService.queryShopList(shop, 2, 5);
        System.out.println(shopExecution.getCount());
        System.out.println(shopExecution.getShopList().size());
    }
}