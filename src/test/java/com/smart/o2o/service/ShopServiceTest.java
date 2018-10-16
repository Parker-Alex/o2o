package com.smart.o2o.service;

import com.smart.o2o.BaseTest;
import com.smart.o2o.dto.ImageHandler;
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
        shop.setShopName("重构后的测试");
        shop.setPriority(2);
        shop.setShopImg("重构后的测试");
        shop.setShopDesc("重构后的测试");
        shop.setShopTel("重构后的测试");
        shop.setShopAdvice(ShopEnum.CHECK.getStateInfo());
        shop.setCreateTime(new Date());
        shop.setStatus(ShopEnum.CHECK.getState());
        File file = new File("E:/File/o2o/m1.jpg");
        InputStream is = new FileInputStream(file);
        ImageHandler imageHandler = new ImageHandler(file.getName(), is);
        ShopExecution shopExecution = shopService.createShop(shop, imageHandler);
        assertEquals(ShopEnum.CHECK.getState(), shopExecution.getState());
    }

    @Test
    public void updateShop() throws FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(11L);
        shop.setShopName("测试后的名字");
        File file = new File("E:/File/img/m1.jpg");
        InputStream inputStream = new FileInputStream(file);
        ImageHandler imageHandler = new ImageHandler(file.getName(), inputStream);
        ShopExecution shopExecution = shopService.updateShop(shop, imageHandler);
        assertEquals(ShopEnum.SUCCESS.getState(), shopExecution.getState());
        System.out.println("图片地址: " + shopExecution.getShop().getShopImg());
    }

    @Test
    public void queryShopList() {
        Shop shop = new Shop();
        shop.setShopName("测试");
        shop.setShopCategory(null);
        shop.setStatus(1);
        ShopExecution shopExecution = shopService.queryShopList(shop, 2, 5);
        System.out.println(shopExecution.getCount());
        System.out.println(shopExecution.getShopList().size());
    }
}