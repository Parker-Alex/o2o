package com.smart.o2o.dao;

import com.smart.o2o.BaseTest;
import com.smart.o2o.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ProductCategoryDaoTest extends BaseTest {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void listBySid() {
        List<ProductCategory> list = productCategoryDao.listBySid(13L);
        System.out.println(list.size());
    }

    @Test
    public void batchlist() {
        List<ProductCategory> list = new ArrayList<>();
        ProductCategory p1 = new ProductCategory();
        p1.setCreateTime(new Date());
        p1.setName("测试1");
        p1.setPriority(5);
        p1.setSid(13L);
        ProductCategory p2 = new ProductCategory();
        p2.setCreateTime(new Date());
        p2.setName("测试2");
        p2.setPriority(9);
        p2.setSid(13L);
        list.add(p1);
        list.add(p2);
        int num = productCategoryDao.batchlist(list);
        assertEquals(2, num);
    }

    @Test
    public void deletePC() {
        int num = productCategoryDao.deletePC(12L, 13L);
        assertEquals(1, num);
    }
}