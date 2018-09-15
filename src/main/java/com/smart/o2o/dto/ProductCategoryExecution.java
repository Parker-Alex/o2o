package com.smart.o2o.dto;

import com.smart.o2o.entity.ProductCategory;
import com.smart.o2o.enums.ProductCategoryEnum;

import java.util.List;

public class ProductCategoryExecution {

    private int state;

    private String stateInfo;

    private List<ProductCategory> list;

    private ProductCategory productCategory;

    public ProductCategoryExecution() {
    }

    public ProductCategoryExecution(int state, ProductCategoryEnum pce) {
        this.state = pce.getCode();
        this.stateInfo = pce.getMsg();
        this.productCategory = productCategory;
    }

    public ProductCategoryExecution(List<ProductCategory> list, ProductCategoryEnum pce) {
        this.list = list;
        this.state = pce.getCode();
        this.stateInfo = pce.getMsg();
    }

    public ProductCategoryExecution(ProductCategoryEnum pce) {
        this.state = pce.getCode();
        this.stateInfo = pce.getMsg();
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

    public List<ProductCategory> getList() {
        return list;
    }

    public void setList(List<ProductCategory> list) {
        this.list = list;
    }
}
