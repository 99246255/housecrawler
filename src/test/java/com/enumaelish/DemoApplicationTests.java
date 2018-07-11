package com.enumaelish;

import com.enumaelish.entity.GPInfo;
import com.enumaelish.entity.HZSecondhandHouse;
import com.enumaelish.service.HZSecondhandHouseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	HZSecondhandHouseService hzSecondhandHouseService;
	@Test
	public void contextLoads() {
		HZSecondhandHouse hzSecondhandHouse = new HZSecondhandHouse();
		hzSecondhandHouse.setCreateTime(new Date());
		hzSecondhandHouse.setUpdateTime(new Date());
		hzSecondhandHouse.setCqmc("cqmc");
		hzSecondhandHouse.setFczsh("fczsh");
		hzSecondhandHouse.setFwtybh("fwtybh");
//		hzSecondhandHouse.setGisx(9.44);
//		hzSecondhandHouse.setGisy(32.3);
		hzSecondhandHouse.setJzmj(223.22);
		hzSecondhandHouse.setXqmc("xqmc");
		hzSecondhandHouse.setXqid(323);
		GPInfo gpInfo = new GPInfo();
		gpInfo.setCjsj(new Date());
		gpInfo.setCreateTime(new Date());
		gpInfo.setFwtybh("fwtybh");
		gpInfo.setGplxrxm("chen");
		gpInfo.setMdmc("mdmc");
		gpInfo.setPrice(3223);
		gpInfo.setScgpshsj(new Date());
		ArrayList<GPInfo> list = new ArrayList<GPInfo>();
		list.add(gpInfo);
		hzSecondhandHouse.setGpInfos(list);
		hzSecondhandHouseService.save(hzSecondhandHouse);
	}

}
