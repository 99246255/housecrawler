package com.enumaelish.service;


import com.enumaelish.entity.GPInfo;
import com.enumaelish.entity.HZSecondhandHouse;
import com.enumaelish.repository.GpInfoRepository;
import com.enumaelish.repository.HZSecondhandHouseRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HZSecondhandHouseService {

    @Autowired
    HZSecondhandHouseRepository hzSecondhandHouseRepository;

    @Autowired
    GpInfoRepository gpInfoRepository;

    public void save(List<HZSecondhandHouse> houses){
        hzSecondhandHouseRepository.save(houses);
    }

    public HZSecondhandHouse save(HZSecondhandHouse house){
        return hzSecondhandHouseRepository.save(house);
    }

    public GPInfo save(GPInfo gpInfo){
        return gpInfoRepository.save(gpInfo);
    }

    public List<HZSecondhandHouse> findAll(){
        return hzSecondhandHouseRepository.findAll();
    }

    public List<HZSecondhandHouse> query(String xqmc, double min, double max, PageRequest pageable){
        if(StringUtils.isEmpty(xqmc) || StringUtils.isEmpty(xqmc.trim())){
            xqmc = "%";
        }else{
            xqmc = "%" + xqmc.trim() + "%";
        }
        return hzSecondhandHouseRepository.findByxqmc(xqmc, min, max, pageable);
    }

    public long count(String xqmc, double min, double max){
        if(StringUtils.isEmpty(xqmc) || StringUtils.isEmpty(xqmc.trim())){
            xqmc = "%";
        }else{
            xqmc = "%" + xqmc.trim() + "%";
        }
        return hzSecondhandHouseRepository.countByxqmc(xqmc, min, max);
    }
}
