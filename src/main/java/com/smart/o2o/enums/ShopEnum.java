package com.smart.o2o.enums;

public enum ShopEnum {

    CHECK(0,"审核中"), OUT_LINE(-1,"店铺下线"), SUCCESS(1,"操作成功"), PASS(2,"过审"), INEER_ERROR(-1000,"操作失败"),
    NULL_SHOPID(-1001,"店铺Id为空"),NULL_SHOP(-1002,"店铺信息为空");

    private int state;

    private String stateInfo;

    ShopEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

//    通过状态码返回相应的enum值
    private static ShopEnum getEnumInfo(int index) {
        for (ShopEnum shopEnum : values()) {
            if (shopEnum.getState() == index)
                return shopEnum;
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
