package com.bp.model.sys;

import com.bp.common.base.BaseEntity;
import lombok.Data;


/**
 * @author
 * @version 1.0
 * @Description: 临时授权资源权限表子表实体类
 * @date 2018年10月24日
 */
@Data
public class TempResInfo extends BaseEntity {

    /**
     * 父表ID
     */
    private Integer pid;

    /**
     * 资源ID
     */
    private Integer resId;

}