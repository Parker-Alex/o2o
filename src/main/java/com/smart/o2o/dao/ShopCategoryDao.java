package com.smart.o2o.dao;

import com.smart.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCategoryDao {

    /**
     * 条件查询店铺类别
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> readShopCategoryList(@Param("shopCategoryCondition")ShopCategory shopCategoryCondition);

    ShopCategory getByName(String name);
}
