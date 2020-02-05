package com.bp.act.utils;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * 类型转换工具类
 *
 * @author 钟欣凯
 */
public class TypeConversionUtils<T> {
    /**
     * 将对象转化为Map
     *
     * @param o 对象
     * @return 转化的Map
     */
    public static Map objToMap(Object o) {

        return JSON.parseObject(JSON.toJSONString(o), Map.class);
    }


}
