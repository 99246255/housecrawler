package com.enumaelish.service;

import com.enumaelish.entity.House;
import com.enumaelish.repository.HouseRepository;
import com.enumaelish.specification.SimpleSpecificationBuilder;
import com.enumaelish.specification.SpecificationOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseService {

    @Autowired
    HouseRepository houseRepository;

    public void save(List<House> houses){
        houseRepository.save(houses);
    }

    public Page<House> query(String city, double min, double max,PageRequest pageable){
        SimpleSpecificationBuilder<House> builder = new SimpleSpecificationBuilder<House>();
        if(StringUtils.isNotBlank(city)){
            builder.add("city", SpecificationOperator.Operator.eq.name(), city);
        }
        builder.add("totalPrice", SpecificationOperator.Operator.ge.name(), min);
        builder.add("totalPrice", SpecificationOperator.Operator.le.name(), max);
        return houseRepository.findAll(builder.generateSpecification(),pageable);
    }
}
