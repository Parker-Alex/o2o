package com.smart.o2o.service.impl;

import com.smart.o2o.dao.ProductDao;
import com.smart.o2o.dao.ProductImgDao;
import com.smart.o2o.dto.ImageHandler;
import com.smart.o2o.dto.ProductExecution;
import com.smart.o2o.entity.Product;
import com.smart.o2o.entity.ProductImg;
import com.smart.o2o.enums.ProductEnum;
import com.smart.o2o.exceptions.ProductException;
import com.smart.o2o.service.ProductService;
import com.smart.o2o.util.ImageUtil;
import com.smart.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;

    @Override
    @Transactional
    public ProductExecution addProduct(Product product, ImageHandler imageHandler,
                                  List<ImageHandler> imageHandlers) throws ProductException {
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            product.setCreateTime(new Date());
            product.setStatus(1);
            if (imageHandler != null) {
                addImage(product, imageHandler);
            }
            try {
                int num = productDao.addProduct(product);
                if (num <= 0) {
                    throw new ProductException("添加商品失败");
                }
            }catch (Exception e) {
                throw new ProductException("添加商品错误: " + e.toString());
            }
            if (imageHandlers != null && imageHandlers.size() > 0) {
                addImageList(product, imageHandlers);
            }
            return new ProductExecution(product, ProductEnum.SUCCESS);
        }else {
            return new ProductExecution(ProductEnum.NULL);
        }
    }

    @Override
    public ProductExecution getProductById(Long pid) {
        try {
            Product product = productDao.getProductById(pid);
            if (product == null) {
                return new ProductExecution(ProductEnum.NULL);
            }else {
                return new ProductExecution(product, ProductEnum.SUCCESS);
            }
        }catch (Exception e) {
            throw new ProductException("获取商品失败: " + e.toString());
        }
    }

    /**
     * 添加商品缩略图方法
     * @param product
     * @param imageHandler
     */
    private void addImage(Product product, ImageHandler imageHandler) {
        String dest = PathUtil.getShopImgPath(product.getShop().getShopId());
        String imgAddr = ImageUtil.generateThumbnail(dest, imageHandler);
        product.setImgAddr(imgAddr);
    }

    /**
     * 添加商品详情图方法
     * @param product
     * @param imageHandlers
     */
    private void addImageList(Product product, List<ImageHandler> imageHandlers) {
        String dest = PathUtil.getShopImgPath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();
        for (ImageHandler imageHandler : imageHandlers) {
            String imgAddr = ImageUtil.generateNormal(dest, imageHandler);
            ProductImg productImg = new ProductImg();
            productImg.setPid(product.getProductId());
            productImg.setImgAddr(imgAddr);
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }
        if (productImgList.size() > 0) {
            try {
                int num = productImgDao.batchlist(productImgList);
                if (num <= 0) {
                    throw new ProductException("创建商品详情图失败");
                }
            }catch (Exception e) {
                throw new ProductException("操作失败: " + e.toString());
            }
        }
    }


}
