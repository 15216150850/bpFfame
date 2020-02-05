package com.bp.oauth.mapper;

import com.bp.common.entity.LoginUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @auther: 钟欣凯
 * @date: 2019/5/13 10:55
 */
@Mapper
public interface UserInfoMapper {

    Map<String,Object> findUserByUsername(String username);

    List<Map> findPermissionByUsername(Map para);
}
