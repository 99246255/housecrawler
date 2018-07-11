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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 杭州房信网二手房挂牌信息
 */
@Component
public class HZHouseTransactionPipeline implements Pipeline {

    /**
     * 所有杭州的二手房信息
     */
    private static final Set<String> sets = ConcurrentHashMap.<String> newKeySet();;
    @Autowired
    HZSecondhandHouseService hzSecondhandHouseService;

    @PostConstruct
    public void init() {
        List<HZSecondhandHouse> list = hzSecondhandHouseService.findAll();
        for (HZSecondhandHouse hzSecondhandHouse : list){
            sets.add(hzSecondhandHouse.getFwtybh());
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
                try {
                    // 更新信息，每天爬取几次，如果长时间没有更新，可能表示已售
                    if(sets.contains(hzSecondhandHouseDTO.getFwtybh())){
                        hzSecondhandHouse = hzSecondhandHouseService.findById(hzSecondhandHouseDTO.getFwtybh());
                        if(hzSecondhandHouseDTO.getGisx() != hzSecondhandHouse.getGisx()){
                            hzSecondhandHouse.setGisx(hzSecondhandHouseDTO.getGisx());
                        }
                        if(hzSecondhandHouseDTO.getGisy() != hzSecondhandHouse.getGisy()){
                            hzSecondhandHouse.setGisy(hzSecondhandHouseDTO.getGisy());
                        }
                        hzSecondhandHouse.setGpfyid(hzSecondhandHouseDTO.getGpfyid());// 修改历史数据，之后可去掉
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
                        hzSecondhandHouse.setGpfyid(hzSecondhandHouseDTO.getGpfyid());
                    }
                    hzSecondhandHouse.setUpdateTime(date);
                    hzSecondhandHouse = hzSecondhandHouseService.save(hzSecondhandHouse);
                    // 更新挂牌信息
                    getGpInfos(hzSecondhandHouseDTO, hzSecondhandHouse.getGpInfos());
                    sets.add(hzSecondhandHouseDTO.getFwtybh());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println(sets.size());
        }
    }

    /**
     * 比较历史的挂牌信息，没有相同的新增一个
     * @param hzSecondhandHouseDTO
     * @param gpInfos
     * @return
     */
    private void getGpInfos(HZSecondhandHouseDTO hzSecondhandHouseDTO, List<GPInfo> gpInfos){
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
            hzSecondhandHouseService.save(gpInfo);
        }
    }
}
