/**
 * Project Name:chenxun-solr
 * File Name:ProductRepository.java
 * Package Name:com.chenxun.solr.repository
 * Date:2016年8月20日下午4:54:24
 * Copyright (c) 2016, www midaigroup com Technology Co., Ltd. All Rights Reserved.
 *
*/

package com.enumaelish.repository;

import com.enumaelish.entity.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends CrudRepository<House, String> , JpaSpecificationExecutor<House> {

    Page<House> findAll(Specification<House> var1, Pageable pageable);
}

