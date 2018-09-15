package com.smart.o2o.service;

import com.smart.o2o.BaseTest;
import com.smart.o2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class AreaServiceTest extends BaseTest {

    @Autowired
    private AreaService areaService;

    @Test
    public void readAreaList() {
        List<Area> areaList = areaService.readAreaList();
        assertEquals("广西", areaList.get(0).getAreaName());
    }
}