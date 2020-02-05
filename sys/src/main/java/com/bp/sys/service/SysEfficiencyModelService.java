package com.bp.sys.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.bp.sys.po.SysEfficiencyModel;
import com.bp.sys.mapper.SysEfficiencyModelMapper;
import com.bp.common.base.BaseMapperUUID;
import com.bp.common.base.BaseServiceImplUUID;

/**
 * @author yanjisheng
 * @version 1.0
 * @Description: 戒治效能分析评估系统评估模型服务层
 * @date 2019年10月23日
 */
@Service
public class SysEfficiencyModelService extends BaseServiceImplUUID<SysEfficiencyModelMapper, SysEfficiencyModel> {

    @Resource
    private SysEfficiencyModelMapper sysEfficiencyModelMapper;

    @Override
    public BaseMapperUUID<SysEfficiencyModel> getMapper() {
        return sysEfficiencyModelMapper;
    }
}
