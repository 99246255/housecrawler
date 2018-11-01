/**
 * Project Name:chenxun-solr
 * File Name:ProductRepository.java
 * Package Name:com.chenxun.solr.repository
 * Date:2016年8月20日下午4:54:24
 * Copyright (c) 2016, www midaigroup com Technology Co., Ltd. All Rights Reserved.
 *
*/

package com.enumaelish.repository;

import com.enumaelish.entity.HZSecondhandHouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HZSecondhandHouseRepository extends PagingAndSortingRepository<HZSecondhandHouse,String> {

    @Query("select h from HZSecondhandHouse h, GPInfo gpinfo where h.fwtybh = gpinfo.fwtybh and h.cqmc like ?1 and h.xqmc like ?2 and gpinfo.price > ?3 and gpinfo.price < ?4 group by h.fwtybh order by h.updateTime desc")
    List<HZSecondhandHouse> findByxqmc(String cqmc, String xqmc, double min, double max, Pageable pageable);

    @Query("select count(distinct h.fwtybh) from HZSecondhandHouse h, GPInfo gpinfo where h.fwtybh = gpinfo.fwtybh and h.cqmc like ?1 and h.xqmc like ?2 and gpinfo.price > ?3 and gpinfo.price < ?4 ")
    Long countByxqmc(String cqmc, String xqmc, double min, double max);

    List<HZSecondhandHouse> findAll();

    Page<HZSecondhandHouse> findAll(Specification<HZSecondhandHouse> var1, Pageable pageable);

    @Query("select h.fwtybh from HZSecondhandHouse h ")
    List<String> findAllIds();
}

