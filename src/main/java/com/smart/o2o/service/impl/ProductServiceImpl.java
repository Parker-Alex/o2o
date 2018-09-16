package com.smart.o2o.service.impl;

import com.smart.o2o.dao.ProductDao;
import com.smart.o2o.dto.ImageHandler;
import com.smart.o2o.entity.Product;
import com.smart.o2o.enums.ProductEnum;
import com.smart.o2o.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public ProductEnum addProduct(Product product, ImageHandler imageHandler) {
        return null;
    }
}
