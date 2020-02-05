package com.bp.common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.LongLongSeqHelper;

import javax.swing.text.html.ListView;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 类型转换工具类
 * @author 钟欣凯
 */
public class TypeConversionUtils<T> {
    /**
     *  将对象转化为Map
     * @param o 对象
     * @return 转化的Map
     */
    public static Map objToMap (Object o){

        return JSON.parseObject(JSON.toJSONString(o), Map.class);
    }



}
