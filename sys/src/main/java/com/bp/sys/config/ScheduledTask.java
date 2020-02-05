package com.bp.sys.config;

import com.bp.common.utils.DateUtil;
import com.bp.sys.client.HrClient;
import com.bp.sys.service.BaseTransferAreaRecordService;
import com.bp.sys.service.DiagBehaveDailyGradeService;
import com.bp.sys.service.DiagBehaveExtraGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时器任务执行
 * @author 刘毓瑞
 *
 */
@Component
public class ScheduledTask {
    @Autowired
    private DiagBehaveDailyGradeService diagBehaveDailyGradeService;
    @Autowired
    private DiagBehaveExtraGradeService diagBehaveExtraGradeService;
    @Autowired
    private BaseTransferAreaRecordService baseTransferAreaRecordService;

    @Autowired
    private HrClient hrClient;
    /**
     * 定时器调用每天零点零分自动生成日常计分统计数据
     */
    @Scheduled(cron= "0 0 0 1/1 * ?")
    public void addDiagBehaveDailyGrade(){
        diagBehaveDailyGradeService.addDiagBehaveDailyGrade(null,new Date());
        //根据劳动合同截止日期，修改劳动合同状态
       hrClient.updateLdhtState();
    }

    /**
     * 定时器调用每天凌晨一点定时补充满基础分到月统计表
     */
    @Scheduled(cron= "0 0 1 1/1 * ?")
    public void paddingBaseGrade(){
        diagBehaveExtraGradeService.paddingBaseGrade(null,new Date());
    }

    /**
     * 定时器调用每月一号零点零分自动生成月行为表现累计奖罚(F)
     */
    @Scheduled(cron= "0 0 0 1 * ?")
    public void addDiagBehaveExtraGrade(){
        diagBehaveExtraGradeService.addDiagBehaveExtraGrade(null,new Date());
    }

    /**
     * 定时器调用每天凌晨修改到期戒毒人员的分期情况
     */
    @Scheduled(cron= "0 0 0 1/1 * ?")
    public void transferAreaTime(){
        baseTransferAreaRecordService.transferAreaByTime(DateUtil.stringToDate("yyyy-MM-dd",DateUtil.getDate()));
    }

}
