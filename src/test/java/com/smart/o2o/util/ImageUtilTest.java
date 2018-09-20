package com.smart.o2o.util;

import com.smart.o2o.BaseTest;
import com.smart.o2o.dao.ProductImgDao;
import com.smart.o2o.entity.ProductImg;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class ImageUtilTest extends BaseTest {

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void deleteFileOrPath() {
        List<ProductImg> imgs = productImgDao.listByPid(5L);
        for(ProductImg pi : imgs) {
            System.out.println(pi.getImgAddr());
            ImageUtil.deleteFileOrPath(pi.getImgAddr());
        }
    }
}