package com.enumaelish.config;

import com.enumaelish.webmagic.HousePipeline;
import com.enumaelish.webmagic.LianjiaErshoufangHousesProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用代理爬取
 */
@Component
public class LianjiaConfig {

    @Autowired
    HousePipeline housePipeline;

    @Async
    public void init() {
//        String chromeDriverPath = LianjiaErshoufangHousesProcessor.class.getClassLoader().getResource("chromedriver.exe").getFile();
//        SeleniumDownloader downloader = new SeleniumDownloader(chromeDriverPath);
//        downloader.setSleepTime(3000);
        String filePath = LianjiaConfig.class.getClassLoader().getResource("proxy.txt").getFile();
        List<Proxy> proxyList = new ArrayList<>();
        try {
            String encoding = "UTF8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    try {
                        String[] split = lineTxt.split(":");
                        if(split.length == 2){
                            int port = Integer.valueOf(split[1]);
                            proxyList.add(new Proxy(split[0], port));
                        }
                    }catch (Exception e){
                    }
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(new SimpleProxyProvider(proxyList));
        Spider spider = Spider.create(new LianjiaErshoufangHousesProcessor())
                .addPipeline(housePipeline)
                .addUrl("https://bj.lianjia.com/ershoufang")
                .setDownloader(httpClientDownloader)
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
