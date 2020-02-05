package com.bp.sys.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.bp.sys.po.SysDangerModel;
import com.bp.sys.mapper.SysDangerModelMapper;
import com.bp.common.base.BaseMapperUUID;
import com.bp.common.base.BaseServiceImplUUID;

/**
 * @author yanjisheng
 * @version 1.0
 * @Description: 戒毒人员危险性分析评估模型服务层
 * @date 2019年10月23日
 */
@Service
public class SysDangerModelService extends BaseServiceImplUUID<SysDangerModelMapper, SysDangerModel> {

    @Resource
    private SysDangerModelMapper sysDangerModelMapper;

    @Override
    public BaseMapperUUID<SysDangerModel> getMapper() {
        return sysDangerModelMapper;
    }
}
