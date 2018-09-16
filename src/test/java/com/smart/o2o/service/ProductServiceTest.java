package com.smart.o2o.service;

import com.smart.o2o.BaseTest;
import com.smart.o2o.dto.ImageHandler;
import com.smart.o2o.dto.ProductExecution;
import com.smart.o2o.entity.Product;
import com.smart.o2o.entity.ProductCategory;
import com.smart.o2o.entity.Shop;
import com.smart.o2o.enums.ProductEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ProductServiceTest extends BaseTest {

    @Autowired
    private ProductService productService;

    @Test
    public void addProduct() throws FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(9L);
        Product product = new Product();
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("service测试");
        product.setPriority(10);
        product.setNormalPrice("998");
        product.setPromotionPrice("666");

        File file = new File("E:/File/o2o/1.jpg");
        InputStream is = new FileInputStream(file);
        ImageHandler imageHandler = new ImageHandler(file.getName(), is);

        File img1 = new File("E:/File/o2o/2.jpg");
        InputStream is1 = new FileInputStream(img1);
        ImageHandler imageHandler1 = new ImageHandler(img1.getName(), is1);
        File img2 = new File("E:/File/o2o/3.jpg");
        InputStream is2 = new FileInputStream(img2);
        ImageHandler imageHandler2 = new ImageHandler(img2.getName(), is2);
        List<ImageHandler> imageHandlers = new ArrayList<>();
        imageHandlers.add(imageHandler1);
        imageHandlers.add(imageHandler2);

        ProductExecution pe = productService.addProduct(product, imageHandler, imageHandlers);
        assertEquals(ProductEnum.SUCCESS.getCode(), pe.getCode());
    }
}