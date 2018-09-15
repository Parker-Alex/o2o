package com.smart.o2o.service;

import com.smart.o2o.dto.ProductCategoryExecution;
import com.smart.o2o.entity.ProductCategory;
import com.smart.o2o.exceptions.ProductCategoryException;

import java.util.List;

public interface ProductCategoryService {

    List<ProductCategory> listBySid(Long sid);

    ProductCategoryExecution batchlist(List<ProductCategory> list) throws ProductCategoryException;

    ProductCategoryExecution deletePC(Long id, Long sid);

}
