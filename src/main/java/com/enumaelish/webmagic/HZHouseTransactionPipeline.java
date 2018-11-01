package com.enumaelish.webmagic;

import com.enumaelish.dto.HZSecondhandHouseDTO;
import com.enumaelish.service.HZSecondhandHouseService;
import com.enumaelish.service.ScheduledService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.Map;

/**
 * 杭州房信网二手房挂牌信息
 */
@Component
public class HZHouseTransactionPipeline implements Pipeline {

    protected static final Logger logger = LoggerFactory.getLogger(ScheduledService.class);
    /**
     * 所有杭州的二手房信息
     */

    @Autowired
    HZSecondhandHouseService hzSecondhandHouseService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        Map<String, Object> items = resultItems.getAll();
        if (resultItems != null && resultItems.getAll().size() > 0) {
            List<HZSecondhandHouseDTO> houses = new Gson().fromJson((String)items.get("house"), new TypeToken<List<HZSecondhandHouseDTO>>() {}.getType());
            hzSecondhandHouseService.saveHouses(houses);
            logger.info("房源总数" + hzSecondhandHouseService.getCount());
        }
    }

}
