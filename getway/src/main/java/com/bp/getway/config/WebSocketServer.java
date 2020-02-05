package com.bp.getway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 钟欣凯
 * webSocket服务器配置
 */
@Component
@ServerEndpoint("/websocket/{key}")
public class WebSocketServer {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static List<WebSocketServer> webSocketSet = new ArrayList<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收sid
     */
    private String key = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("key") String key) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        logger.debug("有新窗口开始监听:" + key);
        this.key = key;
        try {
            sendMessage("连接成功");
        } catch (IOException e) {

            logger.error("io发生异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        logger.debug("移除回话");
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        //当前session对象发送
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 发生错误时回调的方法
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("webSocket发生错误");
        error.printStackTrace();
    }

    /**
     * 群发自定义消息
     * */
    public static void sendInfo(@PathParam("key") String key,String message) {
        for (WebSocketServer item : webSocketSet) {
            try {
//                这里可以设定只推送给这个key的，为null则全部推送
                if(key==null) {
                    item.sendMessage(message);
                }else if(item.key.equals(key)){
                    item.sendMessage(message);
                }
//                item.onClose();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}


