package com.bp.sys.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.bp.sys.po.SysSafetyPrediction;
import com.bp.sys.mapper.SysSafetyPredictionMapper;
import com.bp.common.base.BaseMapperUUID;
import com.bp.common.base.BaseServiceImplUUID;

/**
 * @author yanjisheng
 * @version 1.0
 * @Description: sys_safety_prediction服务层
 * @date 2019年10月25日
 */
@Service
public class SysSafetyPredictionService extends BaseServiceImplUUID<SysSafetyPredictionMapper, SysSafetyPrediction> {

    @Resource
    private SysSafetyPredictionMapper sysSafetyPredictionMapper;

    @Override
    public BaseMapperUUID<SysSafetyPrediction> getMapper() {
        return sysSafetyPredictionMapper;
    }
}
