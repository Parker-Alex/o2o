package com.smart.o2o.service;

import com.smart.o2o.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {

    List<ShopCategory> readShopCategoryList(ShopCategory shopCategoryCondition);

    ShopCategory getByName(String name);
}
