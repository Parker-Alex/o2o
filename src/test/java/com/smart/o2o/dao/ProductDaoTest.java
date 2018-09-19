package com.smart.o2o.dao;

import com.smart.o2o.BaseTest;
import com.smart.o2o.entity.Product;
import com.smart.o2o.entity.ProductCategory;
import com.smart.o2o.entity.ProductImg;
import com.smart.o2o.entity.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ProductDaoTest extends BaseTest {

    @Autowired
    private ProductDao productDao;

    @Test
    public void addProduct() {
        Shop shop = new Shop();
        shop.setShopId(13L);
        Product p = new Product();
        p.setProductName("测试1");
        p.setPriority(6);
        p.setProductDesc("测试1");
        p.setNormalPrice("1000");
        p.setPromotionPrice("999");
        p.setCreateTime(new Date());
        p.setStatus(1);
        p.setShop(shop);
        int num = productDao.addProduct(p);
        assertEquals(1, num);
    }

    @Test
    public void updateProduct() {
        Shop shop = new Shop();
        shop.setShopId(13L);
        ProductCategory pc = new ProductCategory();
        pc.setId(18L);
        Product p = new Product();
        p.setProductId(1L);
        p.setProductName("更新测试3");
        p.setPriority(6);
        p.setProductDesc("更新测试3");
        p.setNormalPrice("2000");
        p.setPromotionPrice("1999");
        p.setLastEditTime(new Date());
        p.setStatus(11);
        p.setShop(shop);
        p.setProductCategory(pc);
        int num = productDao.updateProduct(p);
        assertEquals(1, num);
    }

    @Test
    public void getProductById() {
        Product p = productDao.getProductById(5L);
        System.out.println(p);
    }

    @Test
    public void deleteProductById() {
        int num = productDao.deleteProductById(2L);
        assertEquals(1, num);
    }
}