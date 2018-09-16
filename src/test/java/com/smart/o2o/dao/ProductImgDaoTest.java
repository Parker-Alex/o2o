package com.smart.o2o.dao;

import com.smart.o2o.BaseTest;
import com.smart.o2o.entity.ProductImg;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testAbatchlist() {
        List<ProductImg> list = new ArrayList<>();
        ProductImg pi1 = new ProductImg();
        pi1.setCreateTime(new Date());
        pi1.setPid(1L);
        pi1.setImgDesc("测试1");
        pi1.setPriority(1);

        ProductImg pi2 = new ProductImg();
        pi2.setCreateTime(new Date());
        pi2.setPid(1L);
        pi2.setImgDesc("测试2");
        pi2.setPriority(2);
        list.add(pi1);
        list.add(pi2);
        int num = productImgDao.batchlist(list);
        assertEquals(2,num);
    }

    @Test
    public void testCdeletePI() {
        int num = productImgDao.deletePI(1L);
        assertEquals(2, num);
    }

    @Test
    public void testBlistByPid() {
        List<ProductImg> list = productImgDao.listByPid(1L);
        for (ProductImg pi : list) {
            System.out.println(pi);
        }
        assertEquals(2, list.size());
    }
}