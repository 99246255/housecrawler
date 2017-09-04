package com.enumaelish.controller;


import com.enumaelish.service.HZSecondhandHouseService;
import com.enumaelish.utils.ControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 链家信息抓取查询
 */
@Controller
public class HZSecondhandHouseController {

    @Autowired
    HZSecondhandHouseService hzSecondhandHouseService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String lianjiaindex() {
        return "hzsecondhandhouse";
    }

    @RequestMapping(value = "/secondhandsearch", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> search(HttpServletRequest request) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        String xqmc = request.getParameter("xqmc");
        double min = 0;
        double max = Double.MAX_VALUE;
        try{
            min = Double.valueOf(request.getParameter("min"));
            max = Double.valueOf(request.getParameter("max"));
        }catch (Exception e){
        }
        String cqmc = request.getParameter("cqmc");
        // 若什么都不输入，则表示搜索全部商品
        try {
            PageRequest pageable = ControllerUtil.getPageRequest(request);
            stringObjectHashMap.put("results", hzSecondhandHouseService.query(cqmc, xqmc, min, max, pageable));
            stringObjectHashMap.put("totalElements", hzSecondhandHouseService.count(cqmc, xqmc, min, max));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringObjectHashMap;
    }


}
