package com.smart.o2o.service.impl;

import com.smart.o2o.dao.ShopCategoryDao;
import com.smart.o2o.entity.ShopCategory;
import com.smart.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCategoryImol implements ShopCategoryService {

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Override
    public List<ShopCategory> readShopCategoryList(ShopCategory shopCategoryCondition) {
        return shopCategoryDao.readShopCategoryList(shopCategoryCondition);
    }

    @Override
    public ShopCategory getByName(String name) {
        return shopCategoryDao.getByName(name);
    }
}
