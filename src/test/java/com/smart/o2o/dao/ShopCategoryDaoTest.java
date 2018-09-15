package com.smart.o2o.dao;

import com.smart.o2o.BaseTest;
import com.smart.o2o.entity.ShopCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class ShopCategoryDaoTest extends BaseTest {

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void readShopCategoryList() {
        ShopCategory shopCategory1 = new ShopCategory();
        ShopCategory shopCategory2 = new ShopCategory();
        shopCategory1.setShopCategoryId(1L);
        shopCategory2.setParent(shopCategory1);
        List<ShopCategory> list = shopCategoryDao.readShopCategoryList(new ShopCategory());
        assertEquals(2, list.size());
        System.out.println(list);
    }

    @Test
    public void getByName() {
        ShopCategory shopCategory = shopCategoryDao.getByName("咖啡奶茶");
        System.out.println(shopCategory);
        assertEquals(Long.valueOf("2"), shopCategory.getShopCategoryId());
    }
}