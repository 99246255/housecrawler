package com.enumaelish.webmagic;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 杭州房信网二手房挂牌信息
 */
public class HZHouseTransactionProcessor implements PageProcessor {

    private static final AtomicInteger pageSize = new AtomicInteger(1);
    private Site site = Site.me()
            .setRetryTimes(5).setTimeOut(5000)
            .setSleepTime(1 * 1000);


    public HZHouseTransactionProcessor() {
        this.pageSize.set(1);
    }

    public static final  String URL = "http://jjhygl.hzfc.gov.cn/webty/WebGpxxMapAction_getGpxxSelectList.jspx";
    @Override
    public void process(Page page) {
        String rawText = page.getRawText();
        Gson gs = new Gson();
        JsonObject jsonData = gs.fromJson(rawText, JsonObject.class);
        page.putField("house", jsonData.get("list").toString());
        if(jsonData.get("pageinfo").toString().contains("下一页")) {
            page.addTargetRequest(getRequest(pageSize.incrementAndGet()));
        }
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
        stringIntegerHashMap.put("xqid", "");
        try {
            request.setRequestBody(HttpRequestBody.form(stringIntegerHashMap, "utf-8"));
            request.setMethod(HttpConstant.Method.POST);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return request;
    }
    public Site getSite() {
        return site;
    }


}
