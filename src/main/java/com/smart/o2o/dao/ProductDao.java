package com.smart.o2o.dao;

import com.smart.o2o.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {

    int addProduct(Product product);

    int updateProduct(Product product);

    Product getProductById(Long pid);

    int deleteProductById(Long pid);

    List<Product> productList(@Param("condition") Product condition, @Param("start") int start,
                       @Param("size") int size);

    int count(@Param("condition") Product condition);

    int productCategoryIdToNull(Long pcid);
}
