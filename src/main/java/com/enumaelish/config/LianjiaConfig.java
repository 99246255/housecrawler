package com.enumaelish.config;

import com.enumaelish.webmagic.HousePipeline;
import com.enumaelish.webmagic.LianjiaErshoufangHousesProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.monitor.SpiderMonitor;

/**
 * Created by chenyu on 2017/6/6.
 */
@Component
public class LianjiaConfig {

    @Autowired
    HousePipeline housePipeline;

    @Async
    public void init() {
        String chromeDriverPath = LianjiaErshoufangHousesProcessor.class.getClassLoader().getResource("chromedriver.exe").getFile();
        SeleniumDownloader downloader = new SeleniumDownloader(chromeDriverPath);
        downloader.setSleepTime(3000);
        Spider spider = Spider.create(new LianjiaErshoufangHousesProcessor())
                .addPipeline(housePipeline)
                .addUrl("https://hz.lianjia.com/ershoufang")
//                .setDownloader(downloader)// 可用谷歌浏览器，默认Downloader基于HttpClient
                .thread(1);
        // 注册爬虫监控
        try {
            SpiderMonitor.instance().register(spider);
            spider.run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
