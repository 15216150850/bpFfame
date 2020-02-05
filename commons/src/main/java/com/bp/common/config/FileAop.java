package com.bp.common.config;

import com.bp.common.anno.FileAnnotation;
import com.bp.common.bean.ReturnBean;
import com.bp.common.utils.Common;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

/**
 * Created by IntelliJ IDEA
 *
 * @author xcj
 * @date 2018/9/12
 */
@Aspect
@Component
public class FileAop {
    private static final Logger logger = LoggerFactory.getLogger(FileAop.class);


    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 此处的切点是注解的方式，也可以用包名的方式达到相同的效果
     * '@Pointcut("execution(* com.wwj.springboot.service.impl.*.*(..))")'
     */
    @Pointcut("@annotation(com.bp.common.anno.FileAnnotation)")
    public void operationLog(){}


    /**
     * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
     */
    @After("@annotation(com.bp.common.anno.FileAnnotation)")
    public void after(JoinPoint joinPoint){
        System.out.println("方法最后执行.....");
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        FileAnnotation fileAnnotation = methodSignature.getMethod().getDeclaredAnnotation(FileAnnotation.class);
        System.out.println(fileAnnotation.paramName());
        String[] paramNames = methodSignature.getParameterNames();// 参数名
        if (paramNames != null && paramNames.length > 0) {
            Object[] args = joinPoint.getArgs();// 参数值
            for (int i = 0; i < paramNames.length; i++) {
                Object object = args[i];
                //获取uuid的数组
                String[] uuidArr = getFieldValueByName(fileAnnotation.paramName(),object);
                if(uuidArr!=null) {
                    //批量修改
                    for (String uuid: uuidArr){
                        // 异步将Log对象发送到队列
//                    amqpTemplate.convertAndSend(FileQueue.FILE_QUEUE, uuid);
                        CompletableFuture.runAsync(() -> {
                            try {
//                            amqpTemplate.convertAndSend(FileQueue.FILE_QUEUE, uuid);
                                restTemplate.getForObject("http://file/api/fileStore/updateFileStatus/"+uuid, ReturnBean.class);
                                logger.info("异步调用修改文件状态：" + uuid, uuid);
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        });
                    }
                }
            }
        }
    }

    /**
     * 根据属性名获取属性值
     */
    private String[] getFieldValueByName(String fieldNameStr, Object obj) {
        StringBuilder uuidStr = new StringBuilder();
        String[] fieldNameArr = fieldNameStr.split(",");
        if(fieldNameArr.length==0){
            return null;
        }
        //根据逗号隔开的字段名,获取到多个uuid的值
        for (String fieldName:fieldNameArr) {
            try {
                String firstLetter = fieldName.substring(0, 1).toUpperCase();
                String getter = "get" + firstLetter + fieldName.substring(1);
                Method method = obj.getClass().getMethod(getter, new Class[] {});
                Object value = method.invoke(obj, new Object[] {});
                uuidStr.append(","+Common.getObjStr(value));
            } catch (Exception e) {
                return null;
            }
        }
        return uuidStr.toString().substring(1).split(",");
    }

}
