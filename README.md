SpringBoot + webmagic
学习webmagic写的demo
自用的爬虫，过段时间打算买房，新房位置和价格比较难中和，所以考虑二手房，先抓点数据看看行情
可选用谷歌浏览器做Downloader。chromedrive是2.25版本的，支持的Chrome版本v53-55，如果版本不一致自行替换chromedrive或修改chrome版本

目前的只有二手房的爬虫，比如上海二手房用的http ，北京杭州https， 二手房页面信息不一样
每个城市的html可能不一样所以可能只写了一部分的二手房Processor，有的城市可能需要额外处理
LianjiaConfig 中修改爬取的初始url,格式https://XX城市.lianjia.com/ershoufang，如https://hz.lianjia.com/ershoufang， http://sh.lianjia.com/ershoufang, https://bj.lianjia.com/ershoufang
LianjiaGetHousesProcessor 中的site.setSleepTime(30 * 1000) 尽量长一点，否则会被链家给封IP的，具体设置多少合适我也不清楚，反正已被封过，一个城市最多也就几万条，设置3秒被封过，被封了换个城市爬取即可

搜索页面，很简陋，条件也只有价格、城市过滤
http://localhost:8111/index

接下来考虑爬取链接历史记录保存(考虑房价多版本记录)，以及爬取二手房成交

建表
CREATE TABLE `house` (
  `id` varchar(255) NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `flood` varchar(255) DEFAULT NULL,
  `house_info` varchar(255) DEFAULT NULL,
  `total_price` double(100,6) DEFAULT NULL,
  `unit_price` varchar(255) DEFAULT NULL,
  `pic` varchar(255) DEFAULT NULL,
  `position_info` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
