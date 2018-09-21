package com.smart.o2o.dao;

import com.smart.o2o.entity.Headline;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeadLineDao {

    List<Headline> queryList(@Param("condition") Headline condition);
}
