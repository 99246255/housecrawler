package com.enumaelish.service;

import com.enumaelish.dto.HZSecondhandHouseDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * @Date: 2018/10/17 16:01
 * @Description:
 */
@Service
public class ScheduledService {

    private static AtomicInteger pageSize = new AtomicInteger(0);

    @Autowired
    RestTemplate restTemplate;

    protected static final Logger logger = LoggerFactory.getLogger(ScheduledService.class);

    @Value("${threadsize}")
    int threadSize;
    @Autowired
    HZSecondhandHouseService hzSecondhandHouseService;

    public void init() {
        // 初始化定时任务线程池
        ExecutorService scheduledExecutorService = Executors.newFixedThreadPool(threadSize);
        for (int i = 0; i< threadSize;i++) {
            scheduledExecutorService.submit(() -> getHouses());
        }
    }
    public static final  String URL = "http://jjhygl.hzfc.gov.cn/webty/WebFyAction_getGpxxSelectList.jspx";
    /**
     * 爬取房产数据
     */
    public void getHouses(){
        try {
            int i = pageSize.incrementAndGet();
            String s = restTemplate.postForObject(URL, getRequest(i), String.class);
            Gson gs = new Gson();
            JsonObject jsonData = gs.fromJson(s, JsonObject.class);
            List<HZSecondhandHouseDTO> houses = gs.fromJson(jsonData.get("list").toString(), new TypeToken<List<HZSecondhandHouseDTO>>() {
            }.getType());
            hzSecondhandHouseService.saveHouses(houses);
            if (houses.size() < 10) {
                pageSize = new AtomicInteger(0);
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            logger.info(pageSize.get() + "   " + hzSecondhandHouseService.getCount());
            getHouses();
        }
    }



    private static final String body = "gply=&wtcsjg=&jzmj=&ordertype=&fwyt=&hxs=&havepic=&xzqh=&secondxzqh=&starttime=&endtime=&keywords=&xqid=0&signid=ff80808166484c980166486b4e0b0023&threshold=ff80808166484c980166486b4e0b0021&salt=ff80808166484c980166486b4e0b0022&nonce=0&hash=0448c9b2298cc81d7e0b7a2ab77fcd9261f956537b0939664985b08a1bc4ce20&page=";
    public String getBody(int i){
        return body+i;
    }

    public static MultiValueMap<String, String> getRequest(int i){
        MultiValueMap<String, String> stringIntegerHashMap = new LinkedMultiValueMap<String, String>();
        stringIntegerHashMap.add("page",String.valueOf(i));
        stringIntegerHashMap.add("starttime", "");
        stringIntegerHashMap.add("endtime", "");
        stringIntegerHashMap.add("keywords", "");
        stringIntegerHashMap.add("ordertype", "");
        stringIntegerHashMap.add("wtcsjg", "");
        stringIntegerHashMap.add("fwyt", "");
        stringIntegerHashMap.add("xqid", "0");
        stringIntegerHashMap.add("signid", "ff80808166484c980166486b4e0b0023");
        stringIntegerHashMap.add("threshold", "ff80808166484c980166486b4e0b0021");
        stringIntegerHashMap.add("salt", "ff80808166484c980166486b4e0b0022");
        stringIntegerHashMap.add("nonce", "0");
        stringIntegerHashMap.add("hash", "0448c9b2298cc81d7e0b7a2ab77fcd9261f956537b0939664985b08a1bc4ce20");
        return stringIntegerHashMap;
    }
}
