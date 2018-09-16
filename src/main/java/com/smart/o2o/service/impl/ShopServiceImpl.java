package com.smart.o2o.service.impl;

import com.smart.o2o.dao.ShopDao;
import com.smart.o2o.dto.ImageHandler;
import com.smart.o2o.dto.ShopExecution;
import com.smart.o2o.entity.Shop;
import com.smart.o2o.enums.ShopEnum;
import com.smart.o2o.exceptions.ShopException;
import com.smart.o2o.service.ShopService;
import com.smart.o2o.util.ImageUtil;
import com.smart.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    @Transactional
    public ShopExecution createShop(Shop shop, ImageHandler imageHandler) {
        //判断shop是否为空
        if (shop == null) {
            return new ShopExecution(ShopEnum.NULL_SHOP);
        }else {
            try {
                //初始化shop属性
                shop.setStatus(0);
                shop.setCreateTime(new Date());
                shop.setLastEditTime(new Date());
                //添加店铺
                int effectedNum = shopDao.createShop(shop);
                //如果添加店铺失败，抛出异常
                if (effectedNum <= 0) {
                    throw new ShopException("添加店铺失败!");
                }else {
                    if (imageHandler.getImage() != null) {
                        try {
                            //将图片地址添加到shop中
                            addShopImg(shop, imageHandler);
                        } catch (Exception e) {
                            throw new ShopException("addShopImg error" + e.getMessage());
                        }
                        //更新shop
                        effectedNum= shopDao.updateShop(shop);
                        //如果更新失败，抛出异常
                        if (effectedNum <= 0) {
                            throw new ShopException("添加图片地址失败!");
                        }
                    }
                }
            }catch (Exception e) {
                throw new ShopException("createShop error:" + e.getMessage());
            }
        }
        return new ShopExecution(ShopEnum.CHECK, shop);
    }

    @Override
    public ShopExecution updateShop(Shop shop, ImageHandler imageHandler) throws ShopException {
        //判断店铺是否存在
        if (shop == null || shop.getShopId() == null) {
            return new ShopExecution(ShopEnum.NULL_SHOP);
        }else {
            //如果上传图片不为空
            try {
                if (imageHandler.getImage() != null && imageHandler.getImageName() != null && !"".equals(imageHandler.getImageName())) {
                    Shop exShop = shopDao.readByShopId(shop.getShopId());
                    //如果改店铺原来有图片
                    if (exShop.getShopImg() != null) {
                        //将原来的图片删除
                        ImageUtil.deleteFileOrPath(exShop.getShopImg());
                    }
                    //添加新的图片
                    addShopImg(shop, imageHandler);
                }
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopEnum.INEER_ERROR);
                }else {
                    shop = shopDao.readByShopId(shop.getShopId());
                    return new ShopExecution(ShopEnum.SUCCESS, shop);
                }
            }catch (Exception e) {
                throw new ShopException("update shop error: " + e.getMessage());
            }
        }
    }

    /**
     * 通过店铺id获得店铺信息方法
     * @param shopId
     * @return
     */
    @Override
    public Shop readByShopId(long shopId) {
        return shopDao.readByShopId(shopId);
    }

    /**
     * 分页查询店铺列表
     * @param shopCondition
     * @param start 开始的页数
     * @param size 每页的大小
     * @return
     */
    @Override
    public ShopExecution queryShopList(Shop shopCondition, int start, int size) {
        //表示从数据列表的的第几条开始查询；当开始页数start为1时，startPage为0，第一页的内容从列表中第0条开始查询
//        当开始页数start为2时，startPage为5，第2页的内容从列表中第5条开始查询
        int startPage = start > 0 ? (start - 1) * size : 0;
        ShopExecution shopExecution = new ShopExecution();
        List<Shop> shopList = shopDao.queryShopList(shopCondition, startPage, size);
        if (shopList != null) {
            shopExecution.setShopList(shopList);
            shopExecution.setCount(shopDao.getCount(shopCondition));
        }else{
            shopExecution.setState(ShopEnum.INEER_ERROR.getState());
        }
        return shopExecution;
    }

    private void addShopImg(Shop shop, ImageHandler imageHandler) {
        //获取图片的相对路径
        String dest = PathUtil.getShopImgPath(shop.getShopId());
        //获取处理后图片的地址
        String shopImgAddr = ImageUtil.generateThumbnail(dest, imageHandler);
        //将图片地址添加到shop中
        shop.setShopImg(shopImgAddr);
    }
}
