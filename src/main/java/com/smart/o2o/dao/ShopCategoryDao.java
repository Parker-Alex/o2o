package com.smart.o2o.dao;

import com.smart.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCategoryDao {

    /**
     * 条件查询店铺类别
     * @param condition
     * @return
     */
    List<ShopCategory> readShopCategoryList(@Param("condition")ShopCategory condition);

    ShopCategory getByName(String name);
}
