package com.smart.o2o.service;

import com.smart.o2o.dto.ShopExecution;
import com.smart.o2o.entity.Shop;
import com.smart.o2o.exceptions.ShopException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.InputStream;

public interface ShopService {
    /**
     * 添加店铺方法
     * @param shop
     * @param shopImgInputStream
     * @param fileName InputStream中无法获取文件名，所以还需传入文件名
     * @return
     */
    ShopExecution createShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopException;

    /**
     * 修改店铺方法
     * @param shop
     * @param shopImgInputStream
     * @param fileName
     * @return
     * @throws ShopException
     */
    ShopExecution updateShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopException;

    /**
     * 通过店铺id查找店铺方法
     * @param shopId
     * @return
     */
    Shop readByShopId(long shopId);

    ShopExecution queryShopList(Shop shopCondition, int start, int size);
}
