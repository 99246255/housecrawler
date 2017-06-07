package com.enumaelish.controller;

import com.enumaelish.entity.House;
import com.enumaelish.service.HouseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HouseController {

    @Autowired
    HouseService houseService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "housesearch";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> search(HttpServletRequest request) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        String city = request.getParameter("city");
        double min = 0;
        double max = Double.MAX_VALUE;
        try{
            min = Double.valueOf(request.getParameter("min"));
            max = Double.valueOf(request.getParameter("max"));
        }catch (Exception e){
        }
        // 若什么都不输入，则表示搜索全部商品
        try {
            PageRequest pageable = getPageRequest(request);
            Page<House> query = houseService.query(city, min, max, pageable);
            List list = query.getContent();
            stringObjectHashMap.put("results", list);
            stringObjectHashMap.put("totalElements", query.getTotalElements());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringObjectHashMap;
    }
    /**
     * 获取分页请求
     */
    protected PageRequest getPageRequest(HttpServletRequest request){
        int page = 1;
        int size = 10;
        Sort sort = null;
        try {
            String sortName = request.getParameter("sortName");
            String sortOrder = request.getParameter("sortOrder");
            if(StringUtils.isNotBlank(sortName) && StringUtils.isNotBlank(sortOrder)){
                if(sortOrder.equalsIgnoreCase("desc")){
                    sort = new Sort(Sort.Direction.DESC, sortName);
                }else{
                    sort = new Sort(Sort.Direction.ASC, sortName);
                }
            }
            page = Integer.parseInt(request.getParameter("pageNumber")) - 1;
            size = Integer.parseInt(request.getParameter("pageSize"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(sort == null){
            sort = new Sort(Sort.Direction.ASC, "id");
        }
        PageRequest pageRequest = new PageRequest(page, size, sort);
        return pageRequest;
    }

    /**
     * 获取分页请求
     * @param sort 排序条件
     * @return
     */
    protected PageRequest getPageRequest(HttpServletRequest request,Sort sort){
        int page = 0;
        int size = 10;
        try {
            String sortName = request.getParameter("sortName");
            String sortOrder = request.getParameter("sortOrder");
            if(StringUtils.isNotBlank(sortName) && StringUtils.isNotBlank(sortOrder)){
                if(sortOrder.equalsIgnoreCase("desc")){
                    sort.and(new Sort(Sort.Direction.DESC, sortName));
                }else{
                    sort.and(new Sort(Sort.Direction.ASC, sortName));
                }
            }
            page = Integer.parseInt(request.getParameter("pageNumber")) - 1;
            size = Integer.parseInt(request.getParameter("pageSize"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        PageRequest pageRequest = new PageRequest(page, size, sort);
        return pageRequest;
    }
}
