package com.smart.o2o.dto;

import com.smart.o2o.entity.Product;
import com.smart.o2o.enums.ProductEnum;

import java.util.List;

public class ProductExecution {

    private int code;

    private String msg;

    private int num;

    private Product product;

    private List<Product> products;

    public ProductExecution() {
    }

    public ProductExecution(ProductEnum pe) {
        this.code = pe.getCode();
        this.msg = pe.getMsg();
    }

    public ProductExecution(Product product, ProductEnum pe) {
        this.code = pe.getCode();
        this.msg = pe.getMsg();
        this.product = product;
    }

    public ProductExecution(List<Product> products, ProductEnum pe) {
        this.code = pe.getCode();
        this.msg = pe.getMsg();
        this.products = products;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
