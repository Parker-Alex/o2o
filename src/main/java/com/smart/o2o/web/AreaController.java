package com.smart.o2o.web;

import com.smart.o2o.entity.Area;
import com.smart.o2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/superadmin")
public class AreaController {

    Logger logger = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/arealist", method = RequestMethod.GET)
    @ResponseBody//将返回结构自动转化为json格式
    public Map<String, Object> readAreaList() {
        logger.info("===start===");
        long startTime = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        try {
            List<Area> areaList = areaService.readAreaList();
            map.put("list", areaList);
            map.put("total", areaList.size());
//            map.put("success", true);
        }catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", e.toString());
        }
        logger.error("test error");
        long endTime = System.currentTimeMillis();
        logger.debug("costTime:[{}ms]",endTime - startTime);
        logger.info("===end===");
        return map;
    }
}
