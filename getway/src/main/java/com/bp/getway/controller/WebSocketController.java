package com.bp.getway.controller;

import com.bp.getway.config.WebSocketServer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 钟欣凯
 * webSocket接口,供其他服务调用,由于zuul只支持转发http请求,
 * 目前的解决方法是将webSocket架设在网关中,日后有更好的办法再解决
 */
@RestController
public class WebSocketController {

    /**
     * 发送消息的接口调用
     * @param key
     * @param data
     */
    @PostMapping("/sendMsg")
    public void sendMsg(@RequestParam(name = "key",required = false) String key,
                        @RequestParam(name = "data",required = false) String data){
        WebSocketServer.sendInfo(key,data);
    }
}
