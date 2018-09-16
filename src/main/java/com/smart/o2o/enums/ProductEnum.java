package com.smart.o2o.enums;

public enum ProductEnum {
    NULL(0, "商品为空"),
    SUCCESS(1, "操作成功"),
    FAIL(-1, "操作失败");

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
