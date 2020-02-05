package com.bp.sys;

//import com.bp.sys.mapper.BaseStepFifthMapper;
//import com.bp.sys.po.BaseStepFifth;
//import com.bp.sys.service.BaseStepFifthService;
import com.bp.sys.mapper.BaseAddictsMapper;
import com.bp.sys.mapper.BaseDepartmentMapper;
import com.bp.sys.mapper.BasePoliceMapper;
import com.bp.sys.po.BasePolice;
import com.bp.sys.po.BaseStepFifth;
import com.bp.sys.po.BaseTransferAreaAdvice;
import com.bp.sys.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysApplicationTests {

    @Autowired
    private BaseStepFourthService baseStepFourthService;

    @Autowired
    private BaseStepThirdService baseStepThirdService;

    @Autowired
    private BaseTransferAreaAdviceService baseTransferAreaAdviceService;

    @Autowired
    private BasePoliceService basePoliceService;

    @Autowired
    private BaseAddictsService baseAddictsService;

    @Resource
    private BaseDepartmentMapper baseDepartmentMapper;
    @Resource
    private BaseAddictsMapper baseAddictsMapper;

    @Test
    public void contextLoads() {
        Map map=baseStepFourthService.getRefDays(255,-102.0,23);
        System.out.println(map);
    }


    /**
     * 测试新增一条转区建议书
     */
    @Test
    public void insertAdvice() {
        BaseTransferAreaAdvice baseTransferAreaAdvice = new BaseTransferAreaAdvice();
        baseTransferAreaAdvice
            .setJdrybms("360101180188,360101180189")
            .setJdryxms("小亮,小强")
            .setNowArea("01");
        baseTransferAreaAdviceService.insert(baseTransferAreaAdvice);
    }

    @Test
    public void insertStepThird(){
        baseStepThirdService.baseStepThirdInsert("360101180188",0.00,null);
    }




    @Test
    public void basePoliceTest(){


      List<BasePolice> basePolices = basePoliceService.selectOrderByXzzw();

        for (int i = 0; i < basePolices.size(); i++) {
            BasePolice basePolice = basePolices.get(i);
            basePolice.setSortNo(i+1);
            //basePoliceMapper.update(basePolice);
        }

    }

    @Test
    public void bigDataStatisticsZdjdry(){
        baseAddictsService.bigDataStatisticsZdjdry(new HashMap());

    }
}
