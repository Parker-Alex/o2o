package com.smart.o2o.dao;

import com.smart.o2o.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {

    int batchlist(List<ProductImg> list);

    int deletePI(Long pid);

    List<ProductImg> listByPid(Long pid);
}
