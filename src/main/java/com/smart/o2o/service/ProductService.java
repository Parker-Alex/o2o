package com.smart.o2o.service;

import com.smart.o2o.dto.ImageHandler;
import com.smart.o2o.dto.ProductExecution;
import com.smart.o2o.entity.Product;
import com.smart.o2o.exceptions.ProductException;

import java.util.List;

public interface ProductService {

    ProductExecution addProduct(Product product, ImageHandler imageHandler,
                                List<ImageHandler> imageHandlers) throws ProductException;

    Product getProductById(Long pid);

    ProductExecution updateProduct(Product product, ImageHandler imageHandler,
                                   List<ImageHandler> imageHandlers) throws ProductException;

    ProductExecution productListByCondition(Product product, int start, int size);

    int changeStatus(Product product);
}
