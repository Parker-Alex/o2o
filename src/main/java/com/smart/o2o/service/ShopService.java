package com.smart.o2o.service;

import com.smart.o2o.dto.ImageHandler;
import com.smart.o2o.dto.ShopExecution;
import com.smart.o2o.entity.Shop;
import com.smart.o2o.exceptions.ShopException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.InputStream;

public interface ShopService {

    ShopExecution createShop(Shop shop, ImageHandler imageHandler) throws ShopException;

    ShopExecution updateShop(Shop shop, ImageHandler imageHandler) throws ShopException;

    /**
     * 通过店铺id查找店铺方法
     * @param shopId
     * @return
     */
    Shop readByShopId(long shopId);

    ShopExecution queryShopList(Shop shopCondition, int start, int size);
}
