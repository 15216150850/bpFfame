package com.bp.act.service;

import javax.annotation.Resource;

import com.bp.act.bean.ReturnBean;
import com.bp.act.contants.SysConstant;
import com.bp.act.entity.ActFinishTask;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import com.bp.act.mapper.ActFinishTaskMapper;


import java.util.List;
import java.util.Map;

/**
 * @author 钟欣凯
 * @version 1.0
 * @Description: 个人已办理的任务服务层
 * @date 2019年09月28日
 */
@Service
public class ActFinishTaskService {

    @Resource
    private ActFinishTaskMapper actFinishTaskMapper;

    /**
     * 查询列表
     *
     * @param para
     * @return
     */
    public ReturnBean<List<ActFinishTask>> selectList(Map para) {
        int page = Integer.parseInt(para.get(SysConstant.PAGE).toString());
        int limit = Integer.parseInt(para.get(SysConstant.LIMIT).toString());
        PageHelper.startPage(page, limit);
        List<ActFinishTask> list = actFinishTaskMapper.selectListAll(para);
        PageInfo<ActFinishTask> pageInfo = new PageInfo(list);
        return ReturnBean.list(list, pageInfo.getTotal());
    }

    /**
     * 根据流程实例ID查询
     *
     * @param pid
     * @return
     */
    public List<ActFinishTask> getRecordByPid(String pid) {

        return actFinishTaskMapper.getRecordByPid(pid);
    }

    /**
     * 添加
     *
     * @param actFinishTask
     */
    public void insert(ActFinishTask actFinishTask) {
        actFinishTaskMapper.insert(actFinishTask);
    }

    /**
     * 修改
     *
     * @param actFinishTask
     */
    public void update(ActFinishTask actFinishTask) {
        actFinishTaskMapper.update(actFinishTask);
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    public ActFinishTask selectEntityById(Integer id) {
        return actFinishTaskMapper.selectEntityById(id);
    }

    public void delete(Integer valueOf) {
        actFinishTaskMapper.delete(valueOf);
    }

    public void deleteByIds(String ids) {
        String [] idArr = ids.split(",");
        actFinishTaskMapper.deleteByIds(idArr);
    }

    public void updateCurrentStateByPid(String pId, String name) {
        actFinishTaskMapper.updateCurrentStateByPid(pId,name);
    }

    /**
     * 获取流程的审批节点数据,取得每个节点最新的意见信息:用于回显流程各个部门的审批意见
     *
     * @param pid 流程pid
     * @return 返回该流程所有节点最新的审批意见
     */
    public ReturnBean<List<ActFinishTask>> selectFinallyTaskByPid(String pid){
        List<ActFinishTask> list = actFinishTaskMapper.selectFinallyTaskByPid(pid);
        return ReturnBean.list(list,(long)list.size());
    }
}
