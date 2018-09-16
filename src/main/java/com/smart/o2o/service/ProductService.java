package com.smart.o2o.service;

import com.smart.o2o.dto.ImageHandler;
import com.smart.o2o.entity.Product;
import com.smart.o2o.enums.ProductEnum;

public interface ProductService {

    ProductEnum addProduct(Product product, ImageHandler imageHandler);
}
