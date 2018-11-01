package com.enumaelish.webmagic;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 杭州房信网二手房挂牌信息
 */
public class HZHouseTransactionProcessor implements PageProcessor {

    private static AtomicInteger pageSize = new AtomicInteger(0);
    private Site site = Site.me()
            .setRetryTimes(Integer.MAX_VALUE).setTimeOut(10000)
            .setSleepTime(100);


    public HZHouseTransactionProcessor() {
        this.pageSize.set(1);
    }

    public static final  String URL = "http://jjhygl.hzfc.gov.cn/webty/WebFyAction_getGpxxSelectList.jspx";
    @Override
    public void process(Page page) {
        String rawText = page.getRawText();
        Gson gs = new Gson();
        JsonObject jsonData = gs.fromJson(rawText, JsonObject.class);
        page.putField("house", jsonData.get("list").toString());
        page.addTargetRequest(getRequest(pageSize.incrementAndGet()));
    }

    public static Request getRequest(int i){
        Request request = new Request(URL);
        HashMap<String, Object> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put("page", i);
        stringIntegerHashMap.put("starttime", "");
        stringIntegerHashMap.put("endtime", "");
        stringIntegerHashMap.put("keywords", "");
        stringIntegerHashMap.put("ordertype", "");
        stringIntegerHashMap.put("wtcsjg", "");
        stringIntegerHashMap.put("fwyt", "");
        stringIntegerHashMap.put("xqid", "0");
        stringIntegerHashMap.put("signid", "ff80808166484c980166486b4e0b0023");
        stringIntegerHashMap.put("threshold", "ff80808166484c980166486b4e0b0021");
        stringIntegerHashMap.put("salt", "ff80808166484c980166486b4e0b0022");
        stringIntegerHashMap.put("nonce", "0");
        stringIntegerHashMap.put("hash", "0448c9b2298cc81d7e0b7a2ab77fcd9261f956537b0939664985b08a1bc4ce20");
        request.setRequestBody(HttpRequestBody.form(stringIntegerHashMap, "utf-8"));
        request.setMethod(HttpConstant.Method.POST);
        return request;
    }
    public Site getSite() {
        return site;
    }


}
