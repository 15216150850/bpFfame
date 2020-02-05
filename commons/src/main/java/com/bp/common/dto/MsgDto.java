package com.bp.common.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author xcj
 * @version 1.0
 * @Description: 消息封装实体类
 * @date 2019年12月04日
 */
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
public class MsgDto implements Serializable {

    /**
     * 消息标题(必填)
     */
    private String title;

    /**
     * 发送的 人员id/部门编码/角色编码 逗号隔开字符串(必填)
     */
    private String idStr;

    /**
     * 发送的 人员姓名/部门名称/角色名称 逗号隔开字符串(必填)
     */
    private String nameStr;

    /**
     * 发送的对象类型,idStr,nameStr,receiveType三者必须对应上 (1:人员;2:部门;3:角色;4:流程角色;5:所有人)(必填)
     */
    private String receiveType;

    /**
     * 通知内容(必填)
     */
    private String content;

    /**
     * 标识(1:消息通知;2:待办事项;3工作提醒;4通知公告)(默认为消息通知)
     */
    private String sign;

    /**
     * 是否推送app(1:推送;0:不推送)(默认不推送)
     */
    private String pushApp;

    /**
     * 消息链接的urlApp(非必填,代办事项必填)
     */
    private String urlApp;

    /**
     * 消息链接的urlPc(非必填)
     */
    private String urlPc;
}