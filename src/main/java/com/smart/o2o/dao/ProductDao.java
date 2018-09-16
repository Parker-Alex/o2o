package com.smart.o2o.dao;

import com.smart.o2o.entity.Product;

public interface ProductDao {

    int addProduct(Product product);

    int updateProduct(Product product);
}
