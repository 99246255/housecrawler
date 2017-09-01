package com.enumaelish.webmagic;

import com.enumaelish.dto.HZSecondhandHouseDTO;
import com.enumaelish.entity.GPInfo;
import com.enumaelish.entity.HZSecondhandHouse;
import com.enumaelish.service.HZSecondhandHouseService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 杭州房信网二手房挂牌信息
 */
@Component
public class HZHouseTransactionPipeline implements Pipeline {

    /**
     * 所有杭州的二手房信息
     */
    private static final ConcurrentHashMap<String, HZSecondhandHouse> map = new ConcurrentHashMap<>();
    @Autowired
    HZSecondhandHouseService hzSecondhandHouseService;
    @PostConstruct
    public void init() {
        List<HZSecondhandHouse> list = hzSecondhandHouseService.findAll();
        for (HZSecondhandHouse hzSecondhandHouse : list){
            map.put(hzSecondhandHouse.getFwtybh(), hzSecondhandHouse);
        }
    }
    @Override
    public void process(ResultItems resultItems, Task task) {
        Map<String, Object> items = resultItems.getAll();
        if (resultItems != null && resultItems.getAll().size() > 0) {
            Date date = new Date();
            List<HZSecondhandHouseDTO> houses = new Gson().fromJson((String)items.get("house"), new TypeToken<List<HZSecondhandHouseDTO>>() {}.getType());
            HZSecondhandHouse hzSecondhandHouse = null;
            for(HZSecondhandHouseDTO hzSecondhandHouseDTO : houses){
                // 更新信息，每天爬取几次，如果长时间没有更新，可能表示已售
                if(map.containsKey(hzSecondhandHouseDTO.getFwtybh())){
                    hzSecondhandHouse = map.get(hzSecondhandHouseDTO.getFwtybh());
                    if(hzSecondhandHouseDTO.getGisx() != hzSecondhandHouse.getGisx()){
                        hzSecondhandHouse.setGisx(hzSecondhandHouseDTO.getGisx());
                    }
                    if(hzSecondhandHouseDTO.getGisy() != hzSecondhandHouse.getGisy()){
                        hzSecondhandHouse.setGisy(hzSecondhandHouseDTO.getGisy());
                    }
                    hzSecondhandHouse.setGpfyid(hzSecondhandHouse.getGpfyid());// 修改历史数据，之后可去掉
                }else{
                    hzSecondhandHouse = new HZSecondhandHouse();
                    hzSecondhandHouse.setGisy(hzSecondhandHouseDTO.getGisy());
                    hzSecondhandHouse.setGisx(hzSecondhandHouseDTO.getGisx());
                    hzSecondhandHouse.setXqid(hzSecondhandHouseDTO.getXqid());
                    hzSecondhandHouse.setXqmc(hzSecondhandHouseDTO.getXqmc());
                    hzSecondhandHouse.setJzmj(hzSecondhandHouseDTO.getJzmj());
                    hzSecondhandHouse.setFwtybh(hzSecondhandHouseDTO.getFwtybh());
                    hzSecondhandHouse.setFczsh(hzSecondhandHouseDTO.getFczsh());
                    hzSecondhandHouse.setCqmc(hzSecondhandHouseDTO.getCqmc());
                    hzSecondhandHouse.setCreateTime(date);
                    hzSecondhandHouse.setGpfyid(hzSecondhandHouse.getGpfyid());
                }
                hzSecondhandHouse.setUpdateTime(date);
                hzSecondhandHouse = hzSecondhandHouseService.save(hzSecondhandHouse);
                List<GPInfo> gpInfos = getGpInfos(hzSecondhandHouseDTO, hzSecondhandHouse.getGpInfos());
                hzSecondhandHouse.setGpInfos(gpInfos);
                map.put(hzSecondhandHouseDTO.getFwtybh(), hzSecondhandHouse);
            }
        }
    }

    /**
     * 比较历史的挂牌信息，没有相同的新增一个
     * @param hzSecondhandHouseDTO
     * @param gpInfos
     * @return
     */
    private List<GPInfo> getGpInfos(HZSecondhandHouseDTO hzSecondhandHouseDTO, List<GPInfo> gpInfos){
        boolean add = true;
        if(gpInfos == null){
            gpInfos = new ArrayList<GPInfo>();
        }else{
            for (GPInfo gpInfo: gpInfos){
                if(gpInfo.getPrice() == hzSecondhandHouseDTO.getWtcsjg() && gpInfo.getGplxrxm().equals(hzSecondhandHouseDTO.getGplxrxm())){
                    add = false;
                    break;
                }
            }
        }
        if(add) {
            GPInfo gpInfo = new GPInfo();
            gpInfo.setScgpshsj(hzSecondhandHouseDTO.getScgpshsj());
            gpInfo.setPrice(hzSecondhandHouseDTO.getWtcsjg());
            gpInfo.setMdmc(hzSecondhandHouseDTO.getMdmc());
            gpInfo.setGplxrxm(hzSecondhandHouseDTO.getGplxrxm());
            gpInfo.setCreateTime(new Date());
            gpInfo.setCjsj(hzSecondhandHouseDTO.getCjsj());
            gpInfo.setFwtybh(hzSecondhandHouseDTO.getFwtybh());
            gpInfo = hzSecondhandHouseService.save(gpInfo);
            gpInfos.add(gpInfo);
        }
        return gpInfos;
    }
}
