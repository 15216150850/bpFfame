package com.bp.common.config;

import com.bp.common.anno.CodeName;
import com.bp.common.anno.Sertionary;
import com.bp.common.base.BaseEntity;
import com.bp.common.bean.ReturnBean;
import com.bp.common.utils.Common;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 字典aop，用于字典字段赋值
 */

@Aspect
public class SertionaryAop {

    @Autowired
    private RestTemplate restTemplate;

    @AfterReturning(value = "@annotation(com.bp.common.anno.Sertionary)", returning = "rvt")
    public void setSertionaryName(JoinPoint joinPoint, Object rvt) {
        if (rvt instanceof ReturnBean) {
            ReturnBean bean = (ReturnBean) rvt;
            Object data = bean.data;
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Sertionary logAnnotation = methodSignature.getMethod().getDeclaredAnnotation(Sertionary.class);
            Class aClass = logAnnotation.value();
                if (data instanceof List) {
                    List list = (List) data;
                    Field[] declaredFields = aClass.getDeclaredFields();
                    for (Field field :
                            declaredFields) {
                        String name = field.getName();
                        System.out.println(name);
                        CodeName annotation = field.getAnnotation(CodeName.class);
                        if (annotation!=null){
                            String setMethodName = "set" + name.substring(0,1).toUpperCase()+name.substring(1);
                            String getMethodName = "get" + name.substring(0,1).toUpperCase()+name.substring(1);
                            List<String> paraList = new ArrayList<>();
                            for (Object o :
                                    list) {
                                Class<?> aClass1 = o.getClass();
                                Method mh = ReflectionUtils.findMethod(aClass, getMethodName,
                                        String.class);
                                Object code    = ReflectionUtils.invokeMethod(mh, o);
                                String type = annotation.type();
                                paraList.add(Common.getObjStr(code)+":"+type);
//                                ReturnBean forObject = restTemplate.getForObject("http://sys/api/serDictionary/getNameByCodeAndType/"
//                                        + code + "/" + type, ReturnBean.class);
//                                String codeName = Common.getObjStr(forObject.msg);
//                                 mh = ReflectionUtils.findMethod(aClass, setMethodName,
//                                        String.class);
//                                ReflectionUtils.invokeMethod(mh, o, codeName);
//                                System.out.println(o);
//                                System.out.println(o);
                            }

                            for (Object o :
                                    list) {
                                
                            }
   

                        }

                    }
                } else if (data instanceof BaseEntity) {

                }


        }
    }

}
