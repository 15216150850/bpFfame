package com.bp.sixsys.client;

import com.bp.common.bean.ReturnBean;
import com.bp.common.dto.MsgDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xcj
 * @date 2019/12/3
 */
@FeignClient(name = "msg")
public interface Msgclient {

    /**
     * 消息通知信息
     *
     * @param msgDto 消息对象
     * @return 返回通知状态
     */
    @PostMapping("/api/msgDetial/sendMsg")
    ReturnBean sendMsg(@RequestBody MsgDto msgDto);

    /**
     * 消息通知信息(json字符串方式)
     * @param msgDtoJsonStr 消息对象json字符串
     * @return 返回通知状态
     */
    @PostMapping("/api/msgDetial/sendMsg/MsgDtoJsonStr")
    ReturnBean sendMsgByJson(@RequestParam("msgDtoJsonStr") String msgDtoJsonStr);

}
