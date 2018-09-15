package com.smart.o2o.dto;

import com.smart.o2o.entity.Shop;
import com.smart.o2o.enums.ShopEnum;

import java.util.List;

public class ShopExecution {

//    状态码
    private int state;

//    状态信息
    private String stateInfo;

    private Shop shop;

    private List<Shop> shopList;

//    店铺数量
    private int count;

    public ShopExecution() {
    }

//    操作失败时的构造方法
    public ShopExecution(ShopEnum shopEnum) {
        this.state = shopEnum.getState();
        this.stateInfo = shopEnum.getStateInfo();
    }

//    操作成功时的构造方法
    public ShopExecution(ShopEnum shopEnum, Shop shop) {
        this.state = shopEnum.getState();
        this.stateInfo = shopEnum.getStateInfo();
        this.shop = shop;
    }

    public ShopExecution(ShopEnum shopEnum, List<Shop> shopList) {
        this.state = shopEnum.getState();
        this.stateInfo = shopEnum.getStateInfo();
        this.shopList = shopList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
