package com.smart.o2o.dao;

import com.smart.o2o.entity.Product;

import java.util.List;

public interface ProductDao {

    int addProduct(Product product);

    int updateProduct(Product product);

    Product getProductById(Long pid);

    int deleteProductById(Long pid);
}
