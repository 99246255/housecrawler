package com.enumaelish.utils;

import java.util.List;

/**
 * 从js 中获取
 */
public class GetCityUtil {

    public static  String getCityName(List<String> list){
        String city = null;
        if(list == null){
            return city;
        }
        for (String string : list){
            if(string.contains("cityName")){// 上海是这个
                String cityName = string.substring(string.indexOf("cityName"));
                String[] split = cityName.split("'");
                if(split.length >1){
                    city = split[1];
                }
            }
            if(string.contains("city_name")){// 杭州，北京
                String cityName = string.substring(string.indexOf("city_name"));
                String[] split = cityName.split("'");
                if(split.length >1){
                    city = split[1];
                }
            }
        }
        return city;
    }
}
