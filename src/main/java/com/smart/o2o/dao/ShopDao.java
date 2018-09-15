package com.smart.o2o.dao;

import com.smart.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {

    /**
     * 新增店铺
     * @param shop
     * @return
     */
    int createShop(Shop shop);

    /**
     * 更新店铺
     * @param shop
     * @return
     */
    int updateShop(Shop shop);

    /**
     * 通过shopId得到shop对象
     * @param shopId
     * @return
     */
    Shop readByShopId(Long shopId);

    /**
     * 分页查找店铺列表方法
     * @param shopCondition
     * @param start 开始的行数
     * @param size 需要查询多少行
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
                             @Param("start") int start,
                             @Param("size") int size);

    /**
     * 得到店铺列表总数
     * @param shopCondition
     * @return
     */
    int getCount(@Param("shopCondition") Shop shopCondition);
}
