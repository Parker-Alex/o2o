package com.smart.o2o.entity;

import java.util.Date;

public class ProductImg {

    private Long imgId;
    private String imgAddr;
    private String imgDesc;
    private Integer priority;
    private Date createTime;
    private Product product;

    public Long getImgId() {
        return imgId;
    }

    public void setImgId(Long imgId) {
        this.imgId = imgId;
    }

    public String getImgAddr() {
        return imgAddr;
    }

    public void setImgAddr(String imgAddr) {
        this.imgAddr = imgAddr;
    }

    public String getImgDesc() {
        return imgDesc;
    }

    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
