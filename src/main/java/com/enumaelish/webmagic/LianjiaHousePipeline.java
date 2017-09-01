package com.enumaelish.webmagic;

import com.enumaelish.entity.House;
import com.enumaelish.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class LianjiaHousePipeline implements Pipeline {

    @Autowired
    HouseService houseService;
    public void process(ResultItems resultItems, Task task) {
        Map<String, Object> items = resultItems.getAll();
        if (resultItems != null && resultItems.getAll().size() > 0) {
            List<String> description = (List<String>) items.get("description");
            List<String> houseInfo = (List<String>) items.get("houseInfo");
            List<String> flood = (List<String>) items.get("flood");
            List<String> links = (List<String>) items.get("links");
            List<String> totalPrice = (List<String>) items.get("totalPrice");
            List<String> unitPrice = (List<String>) items.get("unitPrice");
            List<String> pic = (List<String>) items.get("pic");
            List<String> positionInfo = (List<String>) items.get("positionInfo");
            String city = (String) items.get("city");
            List<House> list = new ArrayList<>();
            int lenth = links.size();
            for (int i = 0 ; i < lenth ; i++){
                House house = new House();
                house.setCity(city);
                house.setDescription(description.get(i));
                house.setHouseInfo(houseInfo.get(i));
                house.setFlood(flood.get(i));
                house.setId(links.get(i));
                house.setTotalPrice(Double.valueOf(totalPrice.get(i)));
                house.setUnitPrice(unitPrice.get(i));
                house.setPic(pic.get(i));
                house.setPositionInfo(positionInfo.get(i));
                list.add(house);
            }
            houseService.save(list);
        }
    }

}
