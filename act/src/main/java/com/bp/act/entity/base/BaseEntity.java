package com.bp.act.entity.base;


import java.io.Serializable;
import java.util.Date;

/**
 * @author
 * @version 1.0
 * @Description: 实体基类
 * @date 2019年3月15日 09:53:06
 */

public class BaseEntity implements Serializable {

    /**
     * 使用IdWorker 生成ID
     */

    private Integer id;
    /**
     * 录入人
     */
    private Integer inserter;
    /**
     * 录入时间
     */

    private Date insertTime;
    /**
     * 更新人
     */
    private Integer updater;
    /**
     * 更新时间
     */

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }


    public Integer getInserter() {
        return inserter;
    }

    public void setInserter(Integer inserter) {
        this.inserter = inserter;
    }

    public Integer getUpdater() {
        return updater;
    }

    public void setUpdater(Integer updater) {
        this.updater = updater;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
