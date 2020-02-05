package com.bp.sys;

//import com.bp.sys.mapper.BaseStepFifthMapper;
//import com.bp.sys.po.BaseStepFifth;
//import com.bp.sys.service.BaseStepFifthService;

import com.bp.sys.mapper.BasePoliceMapper;
import com.bp.sys.po.BasePolice;
import com.bp.sys.po.BaseTransferAreaAdvice;
import com.bp.sys.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DiagApplicationTests {

    @Autowired
    private DiagBehaveExtraGradeService diagBehaveExtraGradeService;
    @Autowired
    private DiagBehaveDailyGradeService diagBehaveDailyGradeService;

    @Test
    public void contextLoads1() {
        diagBehaveExtraGradeService.autoExtra(null,null);
    }

    @Test
    public void contextLoads2() {
//        diagBehaveDailyGradeService.selectXz();
    }

}
