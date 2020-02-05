package com.bp.sys.controller;

import com.bp.common.bean.ReturnBean;
import com.bp.common.utils.DateUtil;
import com.bp.sys.po.User;
import com.bp.sys.service.BaseTransferAreaRecordService;
import com.bp.sys.service.DiagBehaveDailyGradeService;
import com.bp.sys.service.DiagBehaveExtraGradeService;
import com.bp.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther: 钟欣凯
 * @date: 2019/5/14 09:48
 */
@RestController
public class TestContrlller {

    @Autowired
    private UserService userService;

    @Autowired
    private BaseTransferAreaRecordService baseTransferAreaRecordService;

    @Autowired
    private DiagBehaveDailyGradeService diagBehaveDailyGradeService;

    @Autowired
    private DiagBehaveExtraGradeService diagBehaveExtraGradeService;

    @GetMapping("testData")
    public ReturnBean testData() {

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("username", "admin");
        map.put("password", "123456");
        list.add(map);
        return ReturnBean.list(list, (long) list.size());
    }


    @GetMapping("ingnore/findAllUser")
    public ReturnBean<List<User>> findAllUser(@RequestParam Map para) {


        return userService.selectList(para);

    }


    /**
     * 修改时间线
     *
     * @param date
     * @return
     */
    @GetMapping("updateTime/{date}")
    public ReturnBean updateTime(@PathVariable("date") String date) {
        //根据时间线的修改,将到期戒毒人员转区
        baseTransferAreaRecordService.transferAreaByTime(DateUtil.stringToDate("yyyy-MM-dd", date));
        //根据时间生成到指定日期的每日行为统计结果
        diagBehaveDailyGradeService.addDiagBehaveDailyGradeByDate(DateUtil.stringToDate("yyyy-MM-dd", date));
        //根据时间生成到指定日期的每月行为考核结果
        diagBehaveExtraGradeService.addDiagBehaveExtraGradeByDate(DateUtil.stringToDate("yyyy-MM-dd", date));
        return ReturnBean.ok();
    }


}
