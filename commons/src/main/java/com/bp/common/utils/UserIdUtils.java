package com.bp.common.utils;

import com.bp.common.entity.SysUser;

import java.util.ArrayList;
import java.util.List;

public class UserIdUtils {

    /**
     *  将List的用户ID分割成用逗号隔开的字符串，此为工作流专用
     * @param ids
     * @return
     */
    public static String idSplit(List<Integer> ids){
        StringBuilder sb = new StringBuilder();
        for (Integer id:ids
             ) {
            sb.append(id).append(",");
        }
        if (ids.size() > 0){
            return sb.substring(0,sb.length()-1);
        } else {
            return "1";
        }

    }

    public static String idSplitByBmbm(List<SysUser> sysUsers){
           List<Integer> ids = new ArrayList<>();
        for (SysUser sysUser :
                sysUsers) {
            ids.add(sysUser.getId());
        }
        return idSplit(ids);
    }
    public static List<Integer> getUserIdList(List<SysUser> sysUsers){

        List<Integer> integers = new ArrayList<>();
        for (SysUser sysUser:
              sysUsers) {
            integers.add(sysUser.getId());
        }
        return integers;
    }
}
