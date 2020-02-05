package com.bp.common.interface_;

import java.net.InetAddress;

/**
 *
 * 开发阶段,作为eurake 回调自己的服务
 * 获取服务器IP
 * @auther: 钟欣凯
 * @date: 2019/6/20 17:39
 */
public class DeloMyIp {
    /**
     * 获取本机IP
     * @author 刘毓瑞
     * @return
     */
    public static String getMyIp() {
        String myIp=null;
        try {
            myIp = InetAddress.getLocalHost().getHostAddress();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            myIp="";
        }
        return myIp;
    }
}
