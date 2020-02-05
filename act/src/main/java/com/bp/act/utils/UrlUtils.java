package com.bp.act.utils;

/**
 * @author 钟欣凯
 * @date: 2019/2/22 13:49
 */
public class UrlUtils {

    public static String getUrl(String baseUrl) {

        String[] split = baseUrl.split("/");
        return split[0] + "/" + "api/" + split[1];
    }
}
