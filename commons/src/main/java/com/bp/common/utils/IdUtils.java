package com.bp.common.utils;

import java.util.UUID;

public class IdUtils {

    public static String getUuid(){

        return  UUID.randomUUID().toString().replace("-","");
    }
}
