package com.smart.o2o.dao;

import com.smart.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryDao {

    List<ProductCategory> listBySid(Long sid);

    int batchlist(List<ProductCategory> list);

    int deletePC(@Param("id") Long id, @Param("sid") Long sid);
}
