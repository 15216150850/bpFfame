package com.bp.sys.service;


import com.bp.common.base.BaseServiceImpl;
import com.bp.common.base.BpBaseMapper;
import com.bp.common.bean.DataTable;
import com.bp.common.constants.SysConstant;
import com.bp.sys.mapper.SerDictionaryMapper;
import com.bp.sys.mapper.SerDictionaryTypeMapper;
import com.bp.sys.po.SerDictionaryType;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @version 1.0
 * @Description: 业务字典类型服务层
 * @date 2018年11月06日
 */
@Service
public class SerDictionaryTypeService extends BaseServiceImpl<SerDictionaryTypeMapper, SerDictionaryType> {

    @Resource
    private SerDictionaryTypeMapper serDictionaryTypeMapper;

    @Resource
    private SerDictionaryMapper serDictionaryMapper;

    @Override
    public BpBaseMapper<SerDictionaryType> getMapper() {
        return serDictionaryTypeMapper;
    }

    /**
     * 删除字典
     *
     * @param id
     * @param code
     * @throws Exception
     */
    @CacheEvict(value = "dicCache", allEntries = true)
    public void delete(Integer id, String code) {
        super.delete(id);
        serDictionaryMapper.deleteByType(code);
    }

    /**
     * 检测字典编码
     *
     * @param code
     * @param id
     * @return
     */
    public Integer checkCode(String code, Integer id) {
        return serDictionaryTypeMapper.checkCode(code, id);
    }

    /**
     * 批量删除字典
     *
     * @param ids
     * @param codes
     */
    @CacheEvict(value = "dicCache", allEntries = true)
    public void deleteByIdsAndCodes(String ids, String codes) {
        super.deleteByIds(ids);
        serDictionaryMapper.deleteByTypes(codes);
    }

    public DataTable selectDatableList(Map para) {
        DataTable dataTable = new DataTable();
        dataTable.setLength(Integer.valueOf(para.get(SysConstant.LENGTH).toString()));
        dataTable.setPageNum(Integer.valueOf(para.get(SysConstant.START).toString()));
        PageHelper.startPage(Integer.valueOf(para.get(SysConstant.START).toString()) / dataTable.getLength() + 1, Integer.valueOf(para.get(SysConstant.LENGTH).toString()));
        List<Map<String, Object>> list = getMapper().selectList(para);
        PageInfo<Map> pageInfo = new PageInfo(list);
        dataTable.setData(list);
        dataTable.setRecordsFiltered(pageInfo.getTotal());
        return dataTable;
    }

}
