package com.enumaelish.service;


import com.enumaelish.dto.HZSecondhandHouseDTO;
import com.enumaelish.entity.GPInfo;
import com.enumaelish.entity.HZSecondhandHouse;
import com.enumaelish.repository.GpInfoRepository;
import com.enumaelish.repository.HZSecondhandHouseRepository;
import com.github.wenhao.jpa.Specifications;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class HZSecondhandHouseService {

    @Autowired
    HZSecondhandHouseRepository hzSecondhandHouseRepository;

    @Autowired
    GpInfoRepository gpInfoRepository;

    /**
     * 所有杭州的二手房信息
     */
    private static final Set<String> sets = ConcurrentHashMap.<String> newKeySet();

    @PostConstruct
    public void init() {
        List<String> list = findAllIds();
        for (String string : list){
            sets.add(string);
        }
    }

    /**
     * 获取记录总数
     * @return
     */
    public int getCount(){
        return sets.size();
    }

    /**
     * 保存房源信息
     * @param house
     * @return
     */
    public HZSecondhandHouse save(HZSecondhandHouse house){
        return hzSecondhandHouseRepository.save(house);
    }

    /**
     * 根据房源核验编码查找房源记录
     * @param name
     * @return
     */
    public HZSecondhandHouse findById(String name){
        HZSecondhandHouse house = hzSecondhandHouseRepository.findOne(name);
        if(house != null){
            house.setGpInfos(gpInfoRepository.findByFwtybh(name));
        }
        return house;
    }

    /**
     * 保存挂牌信息
     * @param gpInfo
     * @return
     */
    public GPInfo save(GPInfo gpInfo){
        return gpInfoRepository.save(gpInfo);
    }

    /**
     * 查找所有房源记录
     * @return
     */
    public List<HZSecondhandHouse> findAll(){
        return hzSecondhandHouseRepository.findAll();
    }

    /**
     * 查找所有房源核验编码
     * @return
     */
    public List<String> findAllIds(){
        return hzSecondhandHouseRepository.findAllIds();
    }

    /**
     * 页面的多个查询条件
     * @param cqmc
     * @param xqmc
     * @param min
     * @param max
     * @param pageable
     * @return
     */
    public List<HZSecondhandHouse> query(String cqmc, String xqmc, double min, double max, PageRequest pageable){
        cqmc = getLikeQuery(cqmc);
        xqmc = getLikeQuery(xqmc);
        return hzSecondhandHouseRepository.findByxqmc(cqmc, xqmc, min, max, pageable);
    }
    public Page<HZSecondhandHouse> queryPage(String cqmc, String xqmc, double min, double max, PageRequest pageable){
        Specification<HZSecondhandHouse> specification = Specifications.<HZSecondhandHouse>and()
                .predicate( ((root, query, cb) -> {
                    Join conditionList = root.join("gpInfos", JoinType.LEFT);
                    return cb.between(conditionList.get("price"), min, max);
                }))
                .like(StringUtils.isNotBlank(cqmc), "cqmc", getLikeQuery(cqmc))
                .like(StringUtils.isNotBlank(xqmc), "xqmc", getLikeQuery(xqmc))
                .build();
        return hzSecondhandHouseRepository.findAll(specification, pageable);
    }

    /**
     * 模糊匹配
     * @param s
     * @return
     */
    public String getLikeQuery(String s){
        if(StringUtils.isEmpty(s) || StringUtils.isEmpty(s.trim())){
            s = "%";
        }else{
            s = "%" + s.trim() + "%";
        }
        return s;
    }

    /**
     * 记录总数查询
     * @param cqmc
     * @param xqmc
     * @param min
     * @param max
     * @return
     */
    public long count(String cqmc, String xqmc, double min, double max){
        cqmc = getLikeQuery(cqmc);
        xqmc = getLikeQuery(xqmc);
        return hzSecondhandHouseRepository.countByxqmc(cqmc, xqmc, min, max);
    }


    /**
     * 保存抓取的信息
     * @param houses
     */
    public void saveHouses(List<HZSecondhandHouseDTO> houses){
        if(CollectionUtils.isEmpty(houses)){
            return;
        }
        HZSecondhandHouse hzSecondhandHouse = null;
        Date date = new Date();
        for (HZSecondhandHouseDTO hzSecondhandHouseDTO : houses) {
            try {
                // 更新信息，每天爬取几次，如果长时间没有更新，可能表示已售
                if (sets.contains(hzSecondhandHouseDTO.getFwtybh())) {
                    hzSecondhandHouse = findById(hzSecondhandHouseDTO.getFwtybh());
                    if (hzSecondhandHouseDTO.getGisx() != hzSecondhandHouse.getGisx()) {
                        hzSecondhandHouse.setGisx(hzSecondhandHouseDTO.getGisx());
                    }
                    if (hzSecondhandHouseDTO.getGisy() != hzSecondhandHouse.getGisy()) {
                        hzSecondhandHouse.setGisy(hzSecondhandHouseDTO.getGisy());
                    }
                    hzSecondhandHouse.setGpfyid(hzSecondhandHouseDTO.getGpfyid());// 修改历史数据，之后可去掉
                } else {
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
                hzSecondhandHouse = save(hzSecondhandHouse);
                // 更新挂牌信息
                getGpInfos(hzSecondhandHouseDTO, hzSecondhandHouse.getGpInfos());
                sets.add(hzSecondhandHouseDTO.getFwtybh());
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            save(gpInfo);
        }
    }
}
