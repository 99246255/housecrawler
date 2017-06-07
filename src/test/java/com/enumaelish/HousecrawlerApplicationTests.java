package com.enumaelish;

import com.enumaelish.webmagic.LianjiaErshoufangHousesProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HousecrawlerApplicationTests {


	@Test
	public void testProcessor() throws Exception{
		//chromedriver.exe需与浏览器版本对应
		String chromeDriverPath = LianjiaErshoufangHousesProcessor.class.getClassLoader().getResource("chromedriver.exe").getFile();
		Spider jdSpider = Spider.create(new LianjiaErshoufangHousesProcessor())
				.addUrl("http://hz.lianjia.com/ershoufang")
//				.setDownloader(new SeleniumDownloader(chromeDriverPath))
				.thread(5);
		// 注册爬虫监控
		SpiderMonitor.instance().register(jdSpider);
		jdSpider.run();
	}

}
