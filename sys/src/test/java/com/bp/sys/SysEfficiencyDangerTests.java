package com.bp.sys;

import com.bp.sys.po.SysDangerModel;
import com.bp.sys.po.SysEfficiencyModel;
import com.bp.sys.service.SysDangerModelService;
import com.bp.sys.service.SysEfficiencyModelService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysEfficiencyDangerTests {

    @Autowired
    private SysEfficiencyModelService sysEfficiencyModelService;
    
    @Autowired
    private SysDangerModelService sysDangerModelService;

    @Test
    public void contextLoads() {
    	String[] efficiencyTypes = {"生理脱毒效能","生理脱毒效能","生理脱毒效能","心理康复效能评估","心理康复效能评估","心理康复效能评估","心理康复效能评估","认知能力效果评估","认知能力效果评估","认知能力效果评估","认知能力效果评估","认知能力效果评估","社会适应效能评估","社会适应效能评估","社会适应效能评估","社会适应效能评估","社会适应效能评估","行为方式效能评估","行为方式效能评估","行为方式效能评估","行为方式效能评估","行为方式效能评估"};
        String[] efficiencyTargets = {"A1","A2","A3","心理训练指标K","心理矫治指标V","康复训练指标H1","康复训练指标V","法律常识","思想道德","戒毒常识","文化素质教育","戒毒康复教育","生活来源及居所情况","职业能力情况","社会支持情况","接受社会监督和援助意愿情况","家庭功能恢复情况","累计净得分F","累计净得分F","累计净得分F","累计净得分F","累计净得分F"};
        String[] efficiencyStandards = {"得分","得分","得分","得分","得分","得分","得分","考试得分","考试得分","考试得分","考试得分","考试得分","评估结果","评估结果","评估结果","评估结果","评估结果","300分＞F≥0分","600分＞F≥300分","800分＞F≥600分","900分＞F≥800分","F≥900分"};
        String[] efficiencyScores = {"A1","A2","(A3*10)/7","K/2","V*5","H1","V*5","考试得分*20%","考试得分*20%","考试得分*20%","考试得分*20%","考试得分*20%","(评估得分/总分）*4","(评估得分/总分）*4","(评估得分/总分）*4","(评估得分/总分）*4","(评估得分/总分）*4","12","14","16","18","20"};
        String[] efficiencyRemarks = {"","","","6","4","6","4","4","4","4","4","4","4","4","4","4","4","","","","",""};
        String[] dangerTypes = {"戒毒人员档案信息","戒毒人员档案信息","戒毒人员档案信息","戒毒人员档案信息","戒毒人员档案信息","戒毒人员档案信息","戒毒人员档案信息","戒毒人员档案信息","戒毒人员档案信息","戒毒人员档案信息","戒毒人员档案信息","戒毒人员档案信息","行为表现","行为表现","行为表现","行为表现","行为表现","康复训练","康复训练","康复训练","生命体征","生命体征","心理情绪","心理情绪","心理情绪","心理情绪","心理情绪","心理情绪","心理情绪","心理情绪","心理情绪","心理情绪","心理情绪","心理情绪","心理情绪","心理情绪","心理情绪","心理情绪","心理情绪","心理情绪","突发危疾重症","突发危疾重症","突发危疾重症"};
        String[] dangerTargets = {"年龄","年龄","年龄","健康状况","健康状况","健康状况","健康状况","健康状况","籍贯","违法犯罪记录","练武经验","从军从警经验","上月行为表现奖罚分","上月行为表现奖罚分","上月行为表现奖罚分","上月行为表现奖罚分","上月行为表现奖罚分","体质测试","体质测试","改善率","血压","心率","躯体化因子分","强迫症状因子分","抑郁因子分","人际关系敏感因子分","焦虑因子分","敌对因子分","偏执因子分","精神病性因子分","恐怖因子分","躯体化因子分","强迫症状因子分","抑郁因子分","人际关系敏感因子分","焦虑因子分","敌对因子分","偏执因子分","精神病性因子分","恐怖因子分","疾病种类","疾病种类","疾病种类"};
        String[] dangerStandards = {"<18",">=46 <=55",">55","较差","一般","有严重疾病","慢性病","身体伤残","外省籍","是","是","是","上月行为表现奖罚分低于上月平均值50%","上月行为表现奖罚分低于上月平均值50%-100%","上月行为表现奖罚分低于上月平均值100%以上","-100<上月行为表现奖罚分<0","上月行为表现奖罚分<-100","M1<2","M2<2","V0≤0","收缩压≥140毫米汞柱，舒张压≥90毫米汞柱","心率>100","3-3.8为中度","3-3.8为中度","3-3.8为中度","3-3.8为中度","3-3.8为中度","3-3.8为中度","3-3.8为中度","3-3.8为中度","3-3.8为中度","3.9及以上为重度","3.9及以上为重度","3.9及以上为重度","3.9及以上为重度","3.9及以上为重度","3.9及以上为重度","3.9及以上为重度","3.9及以上为重度","3.9及以上为重度","1种","2种","3种及以上"};
        String[] sickness = {"","0.2","0.3","0.2","0.1","0.3","0.2","0.3","","","","","","","","","","","","","0.3","0.2","0.1","","","","","","","","","","","","","","","","","","0.3","0.6","1"};
        String[] suicide = {"","","","0.2","","0.3","0.2","0.3","","","","","0.1","0.2","0.3","0.4","0.6","0.1","0.2","0.2","0.1","0.2","0.1","","0.1","0.1","0.1","","0.2","0.2","0.1","0.2","","0.2","0.2","0.2","","0.3","0.3","0.2","","",""};
        String[] hurt = {"","","","","","0.3","","0.3","","","","","0.1","0.2","0.3","0.4","0.6","0.1","0.2","0.2","","0.2","0.1","0.1","0.1","0.1","0.1","","0.1","0.1","0.1","0.2","0.2","0.2","0.2","0.2","","0.2","0.2","0.2","","",""};
        String[] disabled = {"","","","","","0.3","","0.3","","","","","0.1","0.2","0.3","0.4","0.6","0.1","0.2","0.2","","0.2","0.1","0.1","0.1","0.1","0.1","","0.1","0.1","0.1","0.2","0.2","0.2","0.2","0.2","","0.2","0.2","0.2","","",""};
        String[] escape = {"0.3","0.1","","","","","","","0.1","0.3","0.3","0.3","","","0.1","0.2","0.3","","","","","","","","","","","0.2","","","","","","","","","0.3","","","","","",""};
    
        int i = 0;
        while(i < 22) {
        	SysEfficiencyModel sysEfficiencyModel = new SysEfficiencyModel();
        	sysEfficiencyModel.setSortNo(i+1);
        	sysEfficiencyModel.setType(efficiencyTypes[i]);
        	sysEfficiencyModel.setTarget(efficiencyTargets[i]);
        	sysEfficiencyModel.setStandard(efficiencyStandards[i]);
        	sysEfficiencyModel.setScore(efficiencyScores[i]);
        	sysEfficiencyModel.setRemark(efficiencyRemarks[i]);
        	sysEfficiencyModelService.insert(sysEfficiencyModel);
        	i++;
        }
        i = 0;
        while(i < 43) {
        	SysDangerModel sysDangerModel = new SysDangerModel();
        	sysDangerModel.setSortNo(i+1);
        	sysDangerModel.setType(dangerTypes[i]);
        	sysDangerModel.setTarget(dangerTargets[i]);
        	sysDangerModel.setStandard(dangerStandards[i]);
        	sysDangerModel.setSickness(sickness[i]);
        	sysDangerModel.setSuicide(suicide[i]);
        	sysDangerModel.setHurt(hurt[i]);
        	sysDangerModel.setDisabled(disabled[i]);
        	sysDangerModel.setEscape(escape[i]);
        	sysDangerModelService.insert(sysDangerModel);
        	i++;
        }
    }

}
