package com.smart.o2o.service.impl;

import com.smart.o2o.dao.HeadLineDao;
import com.smart.o2o.entity.Headline;
import com.smart.o2o.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeadLineServiceImpl implements HeadLineService {

    @Autowired
    private HeadLineDao headLineDao;


    @Override
    public List<Headline> getList(Headline headLine) {
        return headLineDao.queryList(headLine);
    }
}
