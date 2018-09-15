package com.smart.o2o.dao;

import com.smart.o2o.BaseTest;
import com.smart.o2o.entity.Area;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class AreaDaoTest extends BaseTest {

    @Autowired
    private AreaDao areaDao;

    @Test
    public void readAreaList() {
        List<Area> areaList = areaDao.readAreaList();
//        Assert.assertEquals(2,areaList.size());
        Assert.assertEquals("广西", areaList.get(0).getAreaName());
//        System.out.println(areaList.toString());
    }
}