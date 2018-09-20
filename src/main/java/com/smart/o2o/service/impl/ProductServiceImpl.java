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
    public Product getProductById(Long pid) {
        return productDao.getProductById(pid);
    }

    @Override
    @Transactional
    public ProductExecution updateProduct(Product product, ImageHandler imageHandler,
                                          List<ImageHandler> imageHandlers) throws ProductException {
        if (product != null) {
            product.setLastEditTime(new Date());
            if (imageHandler != null) {
                //将原来的缩略图删除
                ImageUtil.deleteFileOrPath(product.getImgAddr());
                //再添加新的缩略图
                addImage(product, imageHandler);
            }else {
                throw new ProductException("商品缩略图为空");
            }
            if (imageHandlers != null && imageHandlers.size() > 0) {
                List<ProductImg> productImgs = productImgDao.listByPid(product.getProductId());
                for (ProductImg pi : productImgs) {
                    ImageUtil.deleteFileOrPath(pi.getImgAddr());
                }
                int delnum = productImgDao.deletePI(product.getProductId());
                if (delnum < 0) {
                    throw new ProductException("删除详情图失败");
                }
                addImageList(product, imageHandlers);
            }else {
                throw new ProductException("商品详情图为空");
            }
            int num = productDao.updateProduct(product);
            if (num > 0) {
                return new ProductExecution(product, ProductEnum.SUCCESS_UPDATE);
            }else {
                return new ProductExecution(ProductEnum.FAIL);
            }
        }else {
            return new ProductExecution(ProductEnum.NULL);
        }
    }

    @Override
    public ProductExecution productListByCondition(Product product, int start, int size) {
        int startIndex = start > 0 ? (start - 1)*size : 0;
        List<Product> productList = productDao.productList(product, startIndex, size);
        int count = productDao.count(product);
        ProductExecution pe = new ProductExecution();
        pe.setNum(count);
        pe.setProducts(productList);
        return pe;
    }

    @Override
    @Transactional
    public int changeStatus(Product product) {
        return productDao.updateProduct(product);
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
