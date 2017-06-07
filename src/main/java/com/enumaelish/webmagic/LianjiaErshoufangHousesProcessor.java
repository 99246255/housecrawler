package com.enumaelish.webmagic;

import com.enumaelish.utils.GetCityUtl;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 只爬取二手房
 */
public class LianjiaErshoufangHousesProcessor implements PageProcessor {

    public static final String REGEX_ERSHOUFANGSTART = "http[^\\s]*.lianjia.com/ershoufang";
    public static final String REGEX_ERSHOUFANG = "http[^\\s]*.lianjia.com/ershoufang/[a-zA-Z0-9]*/";
    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(3 * 1000)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    private static ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<String, Long>();


    public void process(Page page) {
        try {
            if(!page.getUrl().regex(REGEX_ERSHOUFANGSTART).match()){
                return;
            }
            List<String> allscript = page.getHtml().xpath("//head/script").all();
            String city = GetCityUtl.getCityName(allscript);
            if(StringUtils.isEmpty(city)){
               return;
            }
            List<String> links;
            List<String> description;
            List<String> pic;
            List<String> positionInfo;
            List<String> houseInfo;
            List<String> flood ;
            List<String> totalPrice;
            List<String> unitPrice;
            List<String> all = page.getHtml().xpath("//a").regex(REGEX_ERSHOUFANG).all();
            Long time = System.currentTimeMillis();
            for (String string : all){
                if(map.containsKey(string)){
                    Long aLong = map.get(string);
                    if(time - aLong < 24 * 3600 * 1000L){
                        continue;
                    }
                }
                map.put(string, time);
                page.addTargetRequest(string);
            }
            if(page.getUrl().regex("https://").match()){// 北京杭州等二手房房信息
                links = page.getHtml().xpath("//li[@class='clear']/div[@class='info clear']/div[@class='title']/a/@href").all();
                description = page.getHtml().xpath("//li[@class='clear']/div[@class='info clear']/div[@class='title']/a/text()").all();
                pic = page.getHtml().xpath("//li[@class='clear']/a[@class='img']/img/@data-original").all();
                positionInfo = page.getHtml().xpath("//li[@class='clear']/div[@class='info clear']/div[@class='flood']/div/a/text()").all();
                houseInfo = page.getHtml().xpath("//li[@class='clear']/div[@class='info clear']/div[@class='address']/div/text()").all();
                flood = page.getHtml().xpath("//li[@class='clear']/div[@class='info clear']/div[@class='flood']/div/text()").all();
                totalPrice = page.getHtml().xpath("//li[@class='clear']/div[@class='info clear']/div[@class='priceInfo']/div[@class='totalPrice']/span/text()").all();
                unitPrice = page.getHtml().xpath("//li[@class='clear']/div[@class='info clear']/div[@class='priceInfo']/div[@class='unitPrice']/span/text()").all();
            }else{// 上海等
                links = page.getHtml().xpath("//ul[@class='js_fang_list']/li/div[@class='info']/div[@class='prop-title']/a/@href").all();
                description = page.getHtml().xpath("//ul[@class='js_fang_list']/li/div[@class='info']/div[@class='prop-title']/a/text()").all();
                pic = page.getHtml().xpath("//ul[@class='js_fang_list']/li/a[@class='img']/img/@data-original").all();
                positionInfo = page.getHtml().xpath("//ul[@class='js_fang_list']/li/div[@class='info']/div[@class='info-table']/div/span[@class='info-col row2-text']/a/span/text()").all();
                houseInfo = page.getHtml().xpath("//ul[@class='js_fang_list']/li/div[@class='info']/div[@class='info-table']/div/span[@class='info-col row1-text']/text()").all();
                flood = new ArrayList<String>();
                for(String string : houseInfo){
                    String[] split = string.split("\\|");
                    if(split.length > 2){
                        flood.add(split[2]);
                    }else{
                        flood.add("");
                    }
                }
                totalPrice = page.getHtml().xpath("//ul[@class='js_fang_list']/li/div[@class='info']/div[@class='info-table']/div/div[@class='info-col price-item main']/span[@class='total-price strong-num']/text()").all();
                unitPrice = page.getHtml().xpath("//ul[@class='js_fang_list']/li/div[@class='info']/div[@class='info-table']/div/span[@class='info-col price-item minor']/text()").all();
            }
            page.putField("description", description);
            page.putField("houseInfo", houseInfo);
            page.putField("flood", flood);
            page.putField("links", links);
            page.putField("totalPrice", totalPrice);
            page.putField("unitPrice", unitPrice);
            page.putField("city", city);
            page.putField("pic", pic);
            page.putField("positionInfo", positionInfo);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Site getSite() {
        return site;

    }

    public static void main(String[] args) throws  Exception{
        //chromedriver.exe需与浏览器版本对应
        String chromeDriverPath = LianjiaErshoufangHousesProcessor.class.getClassLoader().getResource("chromedriver.exe").getFile();
        Spider jdSpider = Spider.create(new LianjiaErshoufangHousesProcessor())
                .addUrl("https://hz.lianjia.com/ershoufang")
//                .setDownloader(new SeleniumDownloader(chromeDriverPath))
                .thread(1);
        // 注册爬虫监控
        SpiderMonitor.instance().register(jdSpider);
        jdSpider.run();
    }
}
