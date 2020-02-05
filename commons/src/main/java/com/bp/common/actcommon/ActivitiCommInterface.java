package com.bp.common.actcommon;


import com.bp.common.exception.BpException;
import com.bp.common.utils.DateUtil;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工作流公用接口
 *
 * @author 钟欣凯
 * @date 2018-10-24 10:42:39
 */
public interface ActivitiCommInterface<T> {
    /**
     * 修改业务数据库的流程状态
     *
     * @param t 实体对象
     */
    void updateWorkFlowStateByPid(T t);

    /**
     * 查询流程历史的相关信息
     *
     * @param pId            流程实例ID
     * @param commentList    流程历史批注
     * @param attachmentList 流程历史附件
     * @return 查询结果
     */
    @SuppressWarnings("unchecked")
    default T findByPId(String pId, List<Map> commentList, List<Map> attachmentList) {
        T t = selectTByPId(pId);
        try {
            Class<T> clazz = (Class<T>) t.getClass();
            if (null != commentList && commentList.size() > 0) {
                for (Map commentMap : commentList
                ) {

                    SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Long aLong = Long.valueOf(commentMap.get("time").toString());
                    String d = format.format(aLong);
                    Date date=format.parse(d);
                    commentMap.put("timeStr", DateUtil.dateToString("yyyy-MM-dd HH:ss:mm", date));
                }
                Method mh = ReflectionUtils.findMethod(clazz, "setCommentList", List.class);
                ReflectionUtils.invokeMethod(mh, t,commentList);
            }
            if (null != attachmentList && attachmentList.size() > 0) {
                Method mh = ReflectionUtils.findMethod(clazz, "setAttachmentList", List.class);
                ReflectionUtils.invokeMethod(mh, t, attachmentList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BpException("反射异常");
        }
        return t;
    }



    /**
     * 流程完成后执行的逻辑(用户自定义)
     *
     * @param pid 流程实例ID
     */
    void doFinishBusiness(String pid,Integer currentUserId);


    /**
     * 修改流程状态
     *
     * @param pId      流程实体ID
     * @param docState 状态
     */
    @SuppressWarnings("unchecked")
    default void updateFlowState(String pId, String docState,Integer flowState) {
        Class<T> clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        try {
            T t = clazz.newInstance();
            Method mh = ReflectionUtils.findMethod(clazz, "setPid",
                    String.class);
            ReflectionUtils.invokeMethod(mh, t, pId);
            mh = ReflectionUtils.findMethod(clazz, "setDocState",
                    String.class);
            ReflectionUtils.invokeMethod(mh, t, docState);
            mh = ReflectionUtils.findMethod(clazz, "setFlowState", Integer.class);
            ReflectionUtils.invokeMethod(mh, t, flowState);
            updateWorkFlowStateByPid(t);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BpException("反射异常");
        }
    }

    /**
     * 根据流程ID查询实体对象
     *
     * @param pid 流程实例ID
     * @return 查询结果
     */
    T selectTByPId(String pid);
}
