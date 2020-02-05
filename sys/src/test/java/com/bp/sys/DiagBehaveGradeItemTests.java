package com.bp.sys;


import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.bp.sys.po.BaseAddicts;
import com.bp.sys.po.DiagBehaveGradeItem;
import com.bp.sys.service.BaseAddictsService;
import com.bp.sys.service.DiagBehaveGradeItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DiagBehaveGradeItemTests {

//    @Autowired
//    private DiagBehaveGradeItemService diagBehaveGradeItemService;
//    @Autowired
//    private BaseAddictsService baseAddictsService;
//    public static String fileName="条款导入";
//    /**
//     * 新增条款测试
//     */
//    @Test
//    public void insert() {
////        String parentId = "";
////        String type = "";
////        String code = "";
////        String content = "";
//        ImportParams params = new ImportParams();
//        params.setHeadRows(1);
//        params.setNeedSave(true);
//        File file=new File("E:"+File.separator+fileName+".xlsx");
//        List<Map> list= ExcelImportUtil.importExcel(file, Map.class, params);
//        for (int i=0;i<list.size();i++){
//            Map map=list.get(i);
//            DiagBehaveGradeItem diagBehaveGradeItem = new DiagBehaveGradeItem();
////            diagBehaveGradeItem.setParentId(parentId);
//            diagBehaveGradeItem.setCode(String.valueOf(map.get("code")));
//            diagBehaveGradeItem.setContent(String.valueOf(map.get("content")));
//            diagBehaveGradeItem.setType(String.valueOf(map.get("type")));
//            diagBehaveGradeItem.setLowerGrade(map.get("lower_grade")==null?null:Integer.valueOf(String.valueOf(map.get("lower_grade"))));
//            diagBehaveGradeItem.setUpperGrade(map.get("upper_grade")==null?null:Integer.valueOf(String.valueOf(map.get("upper_grade"))));
//            diagBehaveGradeItemService.insert(diagBehaveGradeItem);
//        }
//    }
//
//    /**
//     * 根据uuid查询子节点测试
//     */
//    @Test
//    public void selectChildList() {
//        List<DiagBehaveGradeItem> list=diagBehaveGradeItemService.selectChildList("f58486fa930b42adabb5fe45e5b79997");
//        System.out.println(list);
//    }
//
//    /**
//     * 查询条款树测试
//     */
//    @Test
//    public void selectAllTree(){
//        List<DiagBehaveGradeItem> list = diagBehaveGradeItemService.selectAll();
//        System.out.println(list);
//    }
//
//    /**
//     * 条款编码校验测试
//     */
//    @Test
//    public void checkCode() {
//        String uuid = "";
//        String code = "0101010";
//        Integer b = diagBehaveGradeItemService.checkCode(uuid,code);
//        System.out.println(b);
//    }
//
//    /**
//     * 自动生成条款编码测试
//     */
//    @Test
//    public void autoChildrenInfo(){
//        String parentCode = "010101";
//        String uuid = "f58486fa930b42adabb5fe45e5b79997";
//        Map map = diagBehaveGradeItemService.autoGetChildInfo(parentCode,uuid);
//        System.out.println(map);
//    }
//
//
//
//    @Test
//    public void autoDiagBehave(){
//        List baseAddictsList = baseAddictsService.selectAll();
//        for (int i=0;i<baseAddictsList.size();i++){
//            Map map=(Map)baseAddictsList.get(i);
//            Date rsrq=null;
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            try {
//                rsrq=sdf.parse(map.get("rsrq").toString());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            if(rsrq!=null){
//                baseAddictsService.autoDiagBehave(map.get("jdrybm").toString(),rsrq,null);
//            }
//
//        }
//
//    }
//



}
