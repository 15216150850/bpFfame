package com.bp.common.base;


import com.bp.common.utils.ValidatorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 
 * @version 1.0
 * @Description: 控制器基类
 * @date 2016年7月16日
 */
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 重定向
     */
    protected static String REDIRECT = "redirect:";

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;
    /**
     * 验证实体
     *
     * @param object
     */
    protected void validateEntity(Object object) {
        ValidatorUtils.validateEntity(object);
    }

}