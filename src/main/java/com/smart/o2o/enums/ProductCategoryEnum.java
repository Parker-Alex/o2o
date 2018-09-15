package com.smart.o2o.enums;

public enum ProductCategoryEnum {
    INNER_ERROR(-1, "获取商品属性失败"),
    SUCCESS(0, "成功"),
    NULL(-2, "添加的商品为空");

    private int code;

    private String msg;

    ProductCategoryEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private static ProductCategoryEnum getInstance(int index) {
        for (ProductCategoryEnum productCategoryEnum : values()) {
            if (productCategoryEnum.getCode() == index) {
                return productCategoryEnum;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
