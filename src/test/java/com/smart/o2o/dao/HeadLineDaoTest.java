package com.smart.o2o.dao;

import com.smart.o2o.BaseTest;
import com.smart.o2o.entity.Headline;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class HeadLineDaoTest extends BaseTest {

    @Autowired
    private HeadLineDao headLineDao;

    @Test
    public void queryList() {
        Headline headline = new Headline();
        headline.setStatus(1);
        List<Headline> list = headLineDao.queryList(headline);
        System.out.println(list.size());
    }
}