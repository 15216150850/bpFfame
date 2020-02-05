package com.bp.sys.service;


import com.bp.common.base.BaseMapper;
import com.bp.common.base.BaseServiceImpl;
import com.bp.common.base.BpBaseMapper;
import com.bp.common.utils.UserUtils;
import com.bp.sys.mapper.TempResMapper;
import com.bp.sys.po.TempRes;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @version 1.0
 * @Description: 临时授权服务层
 * @date 2018年10月30日
 */
@Service
public class TempResService extends BaseServiceImpl<TempResMapper, TempRes> {

    @Resource
    private TempResMapper tempResMapper;

    @Override
    public BpBaseMapper<TempRes> getMapper() {
        return tempResMapper;
    }

    /**
     * 新增用户临时权限
     *
     * @param tempRes
     * @param resId
     * @throws Exception
     */
    public void insertTempRes(TempRes tempRes, String resId) {

        Integer pid = super.insert(tempRes);
        // 先删除用户临时授权权限
        tempResMapper.deleteTempResInfo(pid);
        if (resId != null && !"".equals(resId)) {
            List list = new ArrayList();
            String[] ids = resId.split(",");
            for (String id : ids) {
                Map pmap = new HashMap(2);
                pmap.put("pid", pid);
                pmap.put("resId", Integer.valueOf(id));
                pmap.put("inserter", UserUtils.getCurrentUser().getId());
                list.add(pmap);
            }
            tempResMapper.insertTempResInfo(list);
        }
    }

    /**
     * 修改用户临时权限
     *
     * @param tempRes
     * @param resId
     * @throws Exception
     */
    public void updateTempRes(TempRes tempRes, String resId) {
        super.update(tempRes);
        // 先删除用户临时授权权限
        tempResMapper.deleteTempResInfo(tempRes.getId());
        if (resId != null && !"".equals(resId)) {
            List list = new ArrayList();
            String[] ids = resId.split(",");
            for (String id : ids) {
                Map pmap = new HashMap(2);
                pmap.put("pid", tempRes.getId());
                pmap.put("resId", Integer.valueOf(id));
                pmap.put("inserter", UserUtils.getCurrentUser().getId());
                list.add(pmap);
            }
            tempResMapper.insertTempResInfo(list);
        }
    }

}
