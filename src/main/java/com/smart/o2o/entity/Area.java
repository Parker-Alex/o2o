package com.smart.o2o.entity;

import java.util.Date;

public class Area {

    private Integer areaId;
    private String areaName;
    private Integer areaPriority;
    private Date creatTime;
    private Date lastEditTime;

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getAreaPriority() {
        return areaPriority;
    }

    public void setAreaPriority(Integer areaPriority) {
        this.areaPriority = areaPriority;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    @Override
    public String toString() {
        return "Area{" +
                "areaId=" + areaId +
                ", areaNmae='" + areaName + '\'' +
                ", areaPriority=" + areaPriority +
                ", creatTime=" + creatTime +
                ", lastEditTime=" + lastEditTime +
                '}';
    }
}
