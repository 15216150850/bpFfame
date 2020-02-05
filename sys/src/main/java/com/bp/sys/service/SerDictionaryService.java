package com.bp.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bp.common.base.BaseServiceImpl;
import com.bp.common.base.BpBaseMapper;
import com.bp.common.bean.DataTable;
import com.bp.common.constants.SysConstant;
import com.bp.sys.mapper.SerDictionaryMapper;
import com.bp.sys.po.SerDictionary;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @version 1.0
 * @Description: service_dictionary服务层
 * @date 2018年11月06日
 */
@Service
public class SerDictionaryService extends BaseServiceImpl<SerDictionaryMapper, SerDictionary> {

    @Resource
    private SerDictionaryMapper serDictionaryMapper;

    @Override
    public BpBaseMapper<SerDictionary> getMapper() {
        return serDictionaryMapper;
    }

    @CacheEvict(value = "dicCache", allEntries = true)
    @Override
    public Integer insert(SerDictionary entity) {
        return super.insert(entity);
    }

    @CacheEvict(value = "dicCache", allEntries = true)
    @Override
    public void update(SerDictionary entity) {
        super.update(entity);
    }


    /**
     * 新闻查询树状栏目用根据type
     *
     * @param type
     * @return
     */
    public List selectDicByTypeTree(String type) {
        return serDictionaryMapper.selectDicByTypeTree(type);
    }

    /**
     * 检测字典代码是否重复
     *
     * @param type 字典类型
     * @param code 字典代码
     * @param id
     * @return
     */
    public Integer checkCode(String type, String code, Long id) {
        return serDictionaryMapper.checkCode(type, code, id);
    }

    /**
     * 递归删除字典
     *
     * @param id 字典id
     * @throws Exception
     */
    @CacheEvict(value = "dicCache", allEntries = true)
    @Override
    public void delete(Integer id) {
        super.delete(id);
        // 查询子集
        List<Map> dicList = serDictionaryMapper.selectChildList(id);
        for (Map dic : dicList) {
            Integer dicId = Integer.valueOf(dic.get("id") + "");
            delete(dicId);
        }
    }

    /**
     * 批量递归删除字典
     *
     * @param idsStr
     */
    @CacheEvict(value = "dicCache", allEntries = true)
    @Override
    public void deleteByIds(String idsStr) {
        String[] ids = idsStr.split(",");
        for (String id : ids) {
            delete(Integer.valueOf(id));
        }
    }

    /**
     * 根据pid 和 type查询字典
     *
     * @param pid
     * @param type
     * @return
     */
    @Cacheable(value = "dicCache")
    public List selectDicByPidAndType(Integer pid, String type) {
        return serDictionaryMapper.selectDicByPidAndType(pid, type);
    }

    /**
     * type查询字典
     *
     * @param type
     * @return
     */
    @Cacheable(value = "dicCache")
    public List selectDicByType(String type) {
        return serDictionaryMapper.selectDicByPidAndType(0, type);
    }

    public DataTable selectDatableList(Map para) {
        DataTable dataTable = new DataTable();
        dataTable.setLength(Integer.valueOf(para.get(SysConstant.LENGTH).toString()));
        dataTable.setPageNum(Integer.valueOf(para.get(SysConstant.START).toString()));
        PageHelper.startPage(dataTable.getPageNum(), dataTable.getLength());
        List<Map<String, Object>> list = getMapper().selectList(para);
        PageInfo<Map> pageInfo = new PageInfo(list);
        dataTable.setData(list);
        dataTable.setRecordsFiltered(pageInfo.getTotal());
        return dataTable;
    }

    /**
     * 根据类型字符串集合获取字典2.0
     *
     * @param types
     * @return
     */
    public List<SerDictionary> selectDicByTypes(String types) {
        String[] type = types.split(",");
        return serDictionaryMapper.selectDicByTypes(type);
    }

    /**
     * 根据name 和 type 查询code
     *
     * @param param
     * @return
     */
    public String selectCodeByNameAndType(Map param) {
        return serDictionaryMapper.selectCodeByNameAndType(param);
    }

    /**
     * 根据字典类型和字典编码查询
     * @param type
     * @param code
     */
    public SerDictionary selectByCodeAndType(String type, String code) {
        QueryWrapper<SerDictionary> serDictionaryQueryWrapper = new QueryWrapper<>();
        serDictionaryQueryWrapper.eq("type", type);
        serDictionaryQueryWrapper.eq("code", code);
        return getMapper().selectOne(serDictionaryQueryWrapper);
    }
}
