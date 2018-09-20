package com.smart.o2o.enums;

public enum ProductEnum {
    NULL(-1, "商品为空"),
    OUT(0, "商品下架"),
    SUCCESS(1, "商品上架"),
    FAIL(-2, "操作失败"),
    SUCCESS_UPDATE(3, "更新成功");

    private int code;

    private String msg;

    ProductEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private static ProductEnum getInstance(int index) {
        for (ProductEnum pe : values()) {
            if (index == pe.getCode()) {
                return pe;
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
