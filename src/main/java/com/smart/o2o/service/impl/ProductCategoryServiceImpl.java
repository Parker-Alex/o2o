package com.smart.o2o.service.impl;

import com.smart.o2o.dao.ProductCategoryDao;
import com.smart.o2o.dto.ProductCategoryExecution;
import com.smart.o2o.entity.ProductCategory;
import com.smart.o2o.enums.ProductCategoryEnum;
import com.smart.o2o.exceptions.ProductCategoryException;
import com.smart.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Override
    public List<ProductCategory> listBySid(Long sid) {
        return productCategoryDao.listBySid(sid);
    }

    @Override
    public ProductCategoryExecution batchlist(List<ProductCategory> list) throws ProductCategoryException{
        if (list != null && list.size() > 0) {
           try {
               int num = productCategoryDao.batchlist(list);
               if (num > 0) {
                   return new ProductCategoryExecution(list, ProductCategoryEnum.SUCCESS);
               }else {
                   throw new ProductCategoryException("创建店铺失败");
               }
           }catch (Exception e) {
               throw new ProductCategoryException("batchlist function error: " + e.getMessage());
           }
        }else {
            return new ProductCategoryExecution(ProductCategoryEnum.NULL);
        }
    }

    @Override
    @Transactional
    public ProductCategoryExecution deletePC(Long id, Long sid) {
        try {
            int num = productCategoryDao.deletePC(id, sid);
            if (num > 0) {
                return new ProductCategoryExecution(ProductCategoryEnum.SUCCESS);
            }else {
                throw new ProductCategoryException("商品类别删除失败");
            }
        }catch (Exception e) {
            throw new ProductCategoryException("delete productcategory error: " + e.getMessage());
        }
    }

}
