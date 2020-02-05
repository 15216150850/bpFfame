package com.bp.sys.service;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bp.common.utils.DateUtil;
import org.springframework.stereotype.Service;
import com.bp.sys.po.SysHolidayVacations;
import com.bp.sys.mapper.SysHolidayVacationsMapper;
import com.bp.common.base.BaseMapperUUID;
import com.bp.common.base.BaseServiceImplUUID;

import java.util.Date;

/**
 * @author zhangyu
 * @version 1.0
 * @Description: 节假日设置服务层
 * @date 2020年01月17日
 */
@Service
public class SysHolidayVacationsService extends BaseServiceImplUUID<SysHolidayVacationsMapper,SysHolidayVacations>{

    @Resource
    private SysHolidayVacationsMapper sysHolidayVacationsMapper;

	@Override
	public BaseMapperUUID<SysHolidayVacations> getMapper() {
		return sysHolidayVacationsMapper;
	}

    @Override
    public void update(SysHolidayVacations entity) {
	    String holidayDate = entity.getHolidayDate();
	    String status = entity.getStatus();
        SysHolidayVacations holidayVacations = super.getOne(new QueryWrapper<SysHolidayVacations>().eq("holiday_date", holidayDate));
        if (null == holidayVacations) {
            holidayVacations = new SysHolidayVacations(holidayDate, status);
            super.insert(holidayVacations);
        } else {
            entity.setUuid(holidayVacations.getUuid());
            super.update(entity);
        }
    }
}
