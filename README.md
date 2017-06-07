SpringBoot + webmagic
学习webmagic写的demo
自用的爬虫，下半年打算买房，所以抓点数据看看行情
可选用谷歌浏览器做Downloader。chromedrive是2.25版本的，支持的Chrome版本v53-55，如果版本不一致自行替换chromedrive或修改chrome版本

目前的只有二手房的爬虫，比如上海二手房用的http ，北京杭州https， 二手房页面信息不一样
每个城市的html可能不一样所以可能只写了一部分的二手房Processor，有的城市可能需要额外处理

LianjiaGetHousesProcessor 中的site.setSleepTime(3 * 1000) 尽量长一点，否则会被链家给封IP的，具体设置多少合适我也不清楚，反正已被封过，一个城市最多也就几万条，3秒30条够用

