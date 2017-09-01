package com.enumaelish.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 代理ip获取
 */
public class ProxyGetProcessor implements PageProcessor {

    private static ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
    static {
        for (int i = 2; i < 400; i++) {
            queue.add("http://www.xicidaili.com/nn/"+ i);
        }
    }

    private Site site = Site.me()
            .setRetryTimes(1)
            .setSleepTime(1 * 1000)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    @Override
    public void process(Page page) {
        List<String> all = page.getHtml().xpath("//table[@id='ip_list']/tbody/tr/td/text()").all();
        StringBuffer sb=new StringBuffer();
        for (int i = 0; i< all.size()/10 ;i ++){
            sb.append(all.get(i * 10 +1) + ":" + all.get(i*10+2) + "\r\n");
        }
        try {
            String path="d://4.txt";
            File file=new File(path);
            if(!file.exists())
                file.createNewFile();
            FileOutputStream out=new FileOutputStream(file,true); //如果追加方式用true
            out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
            out.close();
        }
        catch(IOException ex) {
            System.out.println(ex.getStackTrace());
        }
        String poll = queue.poll();
        page.addTargetRequest(poll);

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new ProxyGetProcessor())
                .addUrl("http://www.xicidaili.com/nn/")//                .setDownloader(downloader)// 可用谷歌浏览器，默认Downloader基于HttpClient
                .thread(1).run();
    }
}
