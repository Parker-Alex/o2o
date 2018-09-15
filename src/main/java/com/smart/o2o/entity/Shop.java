package com.smart.o2o.entity;

import java.util.Date;

public class Shop {

    private Long shopId;
    private String shopName;
    private Integer priority;
    private String shopImg;
    private String shopDesc;
    private String shopTel;
    private String shopAddr;
    private Integer status;
    private Date createTime;
    private Date lastEditTime;
    private String shopAdvice;

    private ShopCategory shopCategory;
    private Area area;
    private User user;

    private Long scid;

    public Long getScid() {
        return scid;
    }

    public void setScid(Long scid) {
        this.scid = scid;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
    }

    public String getShopDesc() {
        return shopDesc;
    }

    public void setShopDesc(String shopDesc) {
        this.shopDesc = shopDesc;
    }

    public String getShopTel() {
        return shopTel;
    }

    public void setShopTel(String shopTel) {
        this.shopTel = shopTel;
    }

    public String getShopAddr() {
        return shopAddr;
    }

    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public String getShopAdvice() {
        return shopAdvice;
    }

    public void setShopAdvice(String shopAdvice) {
        this.shopAdvice = shopAdvice;
    }

    public ShopCategory getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(ShopCategory shopCategory) {
        this.shopCategory = shopCategory;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                ", priority=" + priority +
                ", shopImg='" + shopImg + '\'' +
                ", shopDesc='" + shopDesc + '\'' +
                ", shopTel='" + shopTel + '\'' +
                ", shopAddr='" + shopAddr + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                ", shopAdvice='" + shopAdvice + '\'' +
                ", shopCategory=" + shopCategory +
                ", area=" + area +
                ", user=" + user +
                '}';
    }
}
