package com.bp.sys.service;

import javax.annotation.Resource;

import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.SysConstant;
import com.bp.common.utils.IdUtils;
import com.bp.common.utils.UserUtils;
import com.bp.sys.client.GovClient;
import com.bp.sys.client.MedicalClient;
import com.bp.sys.client.PsyClient;
import com.bp.sys.mapper.WxxsjfxMapper;
import com.bp.sys.po.BaseStepFourth;
import com.bp.sys.po.Wxxsjfx;
import com.bp.sys.po.Wxxsjfx;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bp.common.base.BaseMapperUUID;
import com.bp.common.base.BaseServiceImplUUID;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author zhangyu
 * @version 1.0
 * @Description: 危险性数据分析服务层
 * @date 2019年10月25日
 */
@Service
public class WxxsjfxService extends BaseServiceImplUUID<WxxsjfxMapper, Wxxsjfx> {

    @Resource
    private WxxsjfxMapper wxxsjfxMapper;

    @Override
    public BaseMapperUUID<Wxxsjfx> getMapper() {
        return wxxsjfxMapper;
    }

    @Autowired
    private BaseAddictsService baseAddictsService;

    @Resource
    private GovClient govClient;

    @Resource
    private PsyClient psyClient;

    @Resource
    private MedicalClient medicalClient;

    @Autowired
    private BigDataService bigDataService;

    @Autowired
    private BaseStepFourthService baseStepFourthService;


    /**
     * 同步数据
     */
    public void syncData() {
        logger.info("================================================ 开始同步危险性分析数据 start ================================================");
        List<Map<String, Object>> dataList = wxxsjfxMapper.selectList(new HashMap());
        if (dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                Map param = dataList.get(i);
                String jdrybm = param.get("jdrybm").toString();
                String handle = "update";
                Wxxsjfx wxxsjfx = wxxsjfxMapper.selectByJdrybm(jdrybm);
                if (null == wxxsjfx) {
                    wxxsjfx = new Wxxsjfx();
                    wxxsjfx.setUuid(IdUtils.getUuid());
                    wxxsjfx.setJdrybm(jdrybm);
                    wxxsjfx.setInserter(UserUtils.getCurrentUser().getId());
                    wxxsjfx.setInsertTime(new Date());
                    handle = "insert";
                } else {
                    wxxsjfx.setUpdater(UserUtils.getCurrentUser().getId());
                    wxxsjfx.setUpdateTime(new Date());
                }

                Double dadf = 0.00, daTfwjzzdf = 0.00, daZsdf = 0.00,
                        daZsgdf = 0.00, daZcdf = 0.00, daTtdf = 0.00,
                        zdbhdf = 0.00, zdbhTfwjzzdf = 0.00, zdbhZsdf = 0.00,
                        zdbhZsgdf = 0.00, zdbhZcdf = 0.00, zdbhTtdf = 0.00,
                        xlqxdf = 0.00, xlqxTfwjzzdf = 0.00, xlqxZsdf = 0.00, xlqxZsgdf = 0.00, xlqxZcdf = 0.00,
                        xlqxTtdf = 0.00, smtzdf = 0.00, smtzTfwjzzdf = 0.00, smtzZsdf = 0.00,
                        smtzZsgdf = 0.00, smtzZcdf = 0.00, smtzTtdf = 0.00, kfxldf = 0.00,
                        kfxlTfwjzzdf = 0.00, kfxlZsdf = 0.00, kfxlZsgdf = 0.00, kfxlZcdf = 0.00,
                        kfxlTtdf = 0.00, xwbxdf = 0.00, xwbxTfwjzzdf = 0.00,
                        xwbxZsdf = 0.00, xwbxZsgdf = 0.00, xwbxZcdf = 0.00, xwbxTtdf = 0.00, zf = 0.00;

                /********************** 存储档案分信息 start **********************/
                ReturnBean daDataBean = baseAddictsService.selectJdryList(jdrybm);
                if (daDataBean.code == 0 && null != daDataBean.data) {
                    List<Map> personData = (List<Map>) daDataBean.data;
                    Map dataMap = personData.get(0);
                    if (null != dataMap.get("age")) {
                        // 根据年龄计算
                        int age = Integer.parseInt(dataMap.get("age").toString());
                        if (age < 18) {
                            daTtdf += 0.3;
                        } else if (age >= 46 && age <= 55) {
                            daTfwjzzdf += 0.2;
                            daTtdf += 0.1;
                        } else if (age > 55) {
                            daTfwjzzdf += 0.3;
                        }
                    }
                    // 根据健康状况计算
                    if (null != dataMap.get("jkzk")) {
                        String jkzk = dataMap.get("jkzk").toString();
                        switch (jkzk) {
                            case "较差":
                                daTfwjzzdf += 0.2;
                                daZsdf += 0.2;
                                break;
                            case "一般":
                                daTfwjzzdf += 0.1;
                                break;
                            case "身体伤残":
                            case "有严重疾病":
                                daTfwjzzdf += 0.3;
                                daZsdf += 0.3;
                                daZsgdf += 0.3;
                                daZcdf += 0.3;
                                break;
                            case "慢性病":
                                daTfwjzzdf += 0.3;
                                daZsdf += 0.3;
                                break;
                        }
                    }

                    // 根据籍贯计算
                    if (null != dataMap.get("jglx")) {
                        String jglx = dataMap.get("jglx").toString();
                        if ("外省籍".equals(jglx)) {
                            daTtdf += 0.1;
                        }
                        // 根据 违法犯罪记录、练武经验、从军从警经验 计算
                        String wffzjl = dataMap.get("wffzjl").toString();
                        String lwjy = dataMap.get("lwjy").toString();
                        String cjcjjl = dataMap.get("cjcjjl").toString();
                        if ("是".equals(wffzjl) || "是".equals(lwjy) || "是".equals(cjcjjl)) {
                            daTtdf += 0.3;
                        }
                    }
                }
                /********************** 存储档案分信息 end **********************/


                /********************** 存储重点病号得分信息 start **********************/
                ReturnBean zdryDataBean = govClient.selectZdbhlxByJdrybm(jdrybm);
                if (zdryDataBean.code == 0 && null != zdryDataBean.data) {
                    Map data = (Map) zdryDataBean.data;
                    String zdbhlx = data.get("zdbhlx").toString();
                    if (StringUtils.isNotBlank(zdbhlx)) {
                        String[] zdbhlxArray = zdbhlx.split(",");
                        if (zdbhlxArray.length == 1) {
                            zdbhTfwjzzdf += 0.3;
                        } else if (zdbhlxArray.length == 2) {
                            zdbhTfwjzzdf += 0.6;
                        } else if (zdbhlxArray.length >= 3) {
                            zdbhTfwjzzdf += 1;
                        }
                    }
                }
                /********************** 存储重点病号得分信息 end **********************/


                /********************** 存储心理情绪得分信息 start **********************/
                ReturnBean xlqxDataBean = psyClient.selectYzfList(jdrybm);
                if (xlqxDataBean.code == 0 && null != xlqxDataBean.data) {
                    List<Map> list = (List<Map>) xlqxDataBean.data;
                    for (int j = 0; j < list.size(); j++) {
                        if (null != list.get(j)) {
                            String name = list.get(j).get("yzmcZh").toString();
                            Double score = Double.parseDouble(list.get(j).get("yzdf").toString());
                            switch (name) {
                                case "躯体化":
                                    if (score.compareTo(3.00) >= 0 && score.compareTo(3.8) <= 0) {
                                        xlqxTfwjzzdf += 0.1;
                                        xlqxZsdf += 0.1;
                                        xlqxZsgdf += 0.1;
                                        xlqxZcdf += 0.1;
                                    } else if (score.compareTo(3.90) >= 0) {
                                        xlqxZsdf += 0.2;
                                        xlqxZsgdf += 0.2;
                                        xlqxZcdf += 0.2;
                                    }
                                    break;
                                case "强迫症状":
                                    if (score.compareTo(3.00) >= 0 && score.compareTo(3.8) <= 0) {
                                        xlqxZsgdf += 0.1;
                                        xlqxZcdf += 0.1;
                                    } else if (score.compareTo(3.90) >= 0) {
                                        xlqxZsgdf += 0.2;
                                        xlqxZcdf += 0.2;
                                    }
                                    break;
                                case "抑郁":
                                    if (score.compareTo(3.00) >= 0 && score.compareTo(3.8) <= 0) {
                                        xlqxZsdf += 0.1;
                                        xlqxZsgdf += 0.1;
                                        xlqxZcdf += 0.1;
                                    } else if (score.compareTo(3.90) >= 0) {
                                        xlqxZsdf += 0.2;
                                        xlqxZsgdf += 0.2;
                                        xlqxZcdf += 0.2;
                                    }
                                    break;
                                case "人际关系敏感":
                                    if (score.compareTo(3.00) >= 0 && score.compareTo(3.8) <= 0) {
                                        xlqxZsdf += 0.1;
                                        xlqxZsgdf += 0.1;
                                        xlqxZcdf += 0.1;
                                    } else if (score.compareTo(3.90) >= 0) {
                                        xlqxZsdf += 0.2;
                                        xlqxZsgdf += 0.2;
                                        xlqxZcdf += 0.2;
                                    }
                                    break;
                                case "焦虑":
                                    if (score.compareTo(3.00) >= 0 && score.compareTo(3.8) <= 0) {
                                        xlqxZsdf += 0.1;
                                        xlqxZsgdf += 0.1;
                                        xlqxZcdf += 0.1;
                                    } else if (score.compareTo(3.90) >= 0) {
                                        xlqxZsdf += 0.2;
                                        xlqxZsgdf += 0.2;
                                        xlqxZcdf += 0.2;
                                    }
                                    break;
                                case "敌对":
                                    if (score.compareTo(3.00) >= 0 && score.compareTo(3.8) <= 0) {
                                        xlqxTtdf += 0.2;
                                    } else if (score.compareTo(3.90) >= 0) {
                                        xlqxTtdf += 0.3;
                                    }
                                    break;
                                case "偏执":
                                    if (score.compareTo(3.00) >= 0 && score.compareTo(3.8) <= 0) {
                                        xlqxZsdf += 0.2;
                                        xlqxZsgdf += 0.1;
                                        xlqxZcdf += 0.1;
                                    } else if (score.compareTo(3.90) >= 0) {
                                        xlqxZsdf += 0.3;
                                        xlqxZsgdf += 0.2;
                                        xlqxZcdf += 0.2;
                                    }
                                    break;
                                case "精神病性":
                                    if (score.compareTo(3.00) >= 0 && score.compareTo(3.8) <= 0) {
                                        xlqxZsdf += 0.2;
                                        xlqxZsgdf += 0.1;
                                        xlqxZcdf += 0.1;
                                    } else if (score.compareTo(3.90) >= 0) {
                                        xlqxZsdf += 0.3;
                                        xlqxZsgdf += 0.2;
                                        xlqxZcdf += 0.2;
                                    }
                                    break;
                                case "恐怖":
                                    if (score.compareTo(3.00) >= 0 && score.compareTo(3.8) <= 0) {
                                        xlqxZsdf += 0.1;
                                        xlqxZsgdf += 0.1;
                                        xlqxZcdf += 0.1;
                                    } else if (score.compareTo(3.90) >= 0) {
                                        xlqxZsdf += 0.2;
                                        xlqxZsgdf += 0.2;
                                        xlqxZcdf += 0.2;
                                    }
                                    break;
                            }
                        }
                    }
                }
                /********************** 存储心理情绪得分信息 end **********************/


                /********************** 存储生命体征得分信息 start **********************/
                ReturnBean smtzDataBean = medicalClient.selectTjjlByJdrybm(jdrybm);
                if (smtzDataBean.code == 0 && null != smtzDataBean.data) {
                    Map data = (Map) smtzDataBean.data;
                    String xy = data.get("xy").toString();
                    // 血压
                    if (xy.indexOf("/") > -1) {
                        // 收缩压
                        Double ssy = StringUtils.isBlank(xy.split("/")[0]) ? 0.00 : Double.parseDouble(xy.split("/")[0]);
                        // 舒张压
                        Double szy = StringUtils.isBlank(xy.split("/")[1]) ? 0.00 : Double.parseDouble(xy.split("/")[1]);
                        if (ssy >= 140 && szy >= 90) {
                            smtzTfwjzzdf += 0.3;
                            smtzZsdf += 0.1;
                        }
                    }
                    // 心率
                    String xl = data.get("xl").toString();
                    if (StringUtils.isNotBlank(xl)) {
                        if (Double.parseDouble(xl) > 100) {
                            smtzTfwjzzdf += 0.2;
                            smtzZsdf += 0.2;
                            smtzZsgdf += 0.2;
                            smtzZcdf += 0.2;
                        }

                    }
                }
                /********************** 存储生命体征得分信息 end **********************/


                /********************** 存储康复训练得分信息 start **********************/
                ReturnBean rtData = baseStepFourthService.selectByJdrybm(jdrybm);
                if (rtData.code == 0 && null != rtData.data) {
                    BaseStepFourth baseStepFourth = (BaseStepFourth) rtData.data;
                    Double M1 = null == baseStepFourth.getSltdqjdxzdpgjg() ? 0.00 : baseStepFourth.getSltdqjdxzdpgjg();
                    Double M2 = null == baseStepFourth.getJysyqjdxzdpgjg() ? 0.00 : baseStepFourth.getJysyqjdxzdpgjg();
                    if (M1.compareTo(2.00) < 0) {
                        kfxlZsdf += 0.1;
                        kfxlZsgdf += 0.1;
                        kfxlZcdf += 0.1;
                    }
                    if (M2.compareTo(2.00) < 0) {
                        kfxlZsdf += 0.2;
                        kfxlZsgdf += 0.2;
                        kfxlZcdf += 0.2;
                    }
                }
                /********************** 存储康复训练得分信息 end **********************/


                /********************** 存储行为表现得分信息 start **********************/
                List<Map<String, Double>> xwList = bigDataService.evaluationDyxwbxf(jdrybm);
                if (xwList.size() > 0) {
                    Map data = xwList.get(0);
                    Double score = Double.parseDouble(data.get("jdryAvgScore").toString());
                    Double avgScore = Double.parseDouble(data.get("totalAvgScore").toString());
                    if (score < avgScore) {
                        // 百分比
                        Double percent = Math.floor((avgScore - score) / avgScore);
                        BigDecimal bd = new BigDecimal(percent);
                        percent = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        if (percent <= 0.5) {
                            xwbxZsdf += 0.1;
                            xwbxZsgdf += 0.1;
                            xwbxZcdf += 0.1;
                        } else if (percent > 0.5 && percent <= 1) {
                            xwbxZsdf += 0.2;
                            xwbxZsgdf += 0.2;
                            xwbxZcdf += 0.2;
                        } else if (percent > 1) {
                            xwbxZsdf += 0.3;
                            xwbxZsgdf += 0.3;
                            xwbxZcdf += 0.3;
                            xwbxTtdf += 0.1;
                        }
                        // 分数
                        if (score.compareTo(Double.parseDouble("-100")) > 0 && score.compareTo(0.00) < 0) {
                            xwbxZsdf += 0.4;
                            xwbxZsgdf += 0.4;
                            xwbxZcdf += 0.4;
                            xwbxTtdf += 0.2;
                        } else if (score.compareTo(Double.parseDouble("-100")) < 0) {
                            xwbxZsdf += 0.6;
                            xwbxZsgdf += 0.6;
                            xwbxZcdf += 0.6;
                            xwbxTtdf += 0.3;
                        }
                    }
                }
                /********************** 存储行为表现得分信息 end **********************/

                // 存储总分
                dadf = daTfwjzzdf + daZsdf + daZsgdf + daZcdf + daTtdf;
                zdbhdf = zdbhTfwjzzdf + zdbhZsdf + zdbhZsgdf + zdbhZcdf + zdbhTtdf;
                xlqxdf = xlqxTfwjzzdf + xlqxZsdf + xlqxZsgdf + xlqxZcdf + xlqxTtdf;
                smtzdf = smtzTfwjzzdf + smtzZsdf + smtzZsgdf + smtzZcdf + smtzTtdf;
                kfxldf = kfxlTfwjzzdf + kfxlZsdf + kfxlZsgdf + kfxlZcdf + kfxlTtdf;
                xwbxdf = xwbxTfwjzzdf + xwbxZsdf + xwbxZsgdf + xwbxZcdf + xwbxTtdf;

                zf = dadf + zdbhdf + xlqxdf + smtzdf + kfxldf + xwbxdf;

                wxxsjfx.setDadf(dadf).setDaTfwjzzdf(daTfwjzzdf).setDaZsdf(daZsdf)
                        .setDaZsgdf(daZsgdf).setDaZcdf(daZcdf).setDaTtdf(daTtdf)
                        .setZdbhdf(zdbhdf).setZdbhTfwjzzdf(zdbhTfwjzzdf).setZdbhZsdf(zdbhZsdf)
                        .setZdbhZsgdf(zdbhZsgdf).setZdbhZcdf(zdbhZcdf).setZdbhTtdf(zdbhTtdf)
                        .setXlqxdf(xlqxdf).setXlqxTfwjzzdf(xlqxTfwjzzdf).setXlqxZsdf(xlqxZsdf)
                        .setXlqxZsgdf(xlqxZsgdf).setXlqxZcdf(xlqxZcdf).setXlqxTtdf(xlqxTtdf)
                        .setSmtzdf(smtzdf).setSmtzTfwjzzdf(smtzTfwjzzdf).setSmtzZsdf(smtzZsdf)
                        .setSmtzZsgdf(smtzZsgdf).setSmtzZcdf(smtzZcdf).setSmtzTtdf(smtzTtdf)
                        .setKfxldf(kfxldf).setKfxlTfwjzzdf(kfxlTfwjzzdf).setKfxlZsdf(kfxlZsdf)
                        .setKfxlZsgdf(kfxlZsgdf).setKfxlZcdf(kfxlZcdf).setKfxlTtdf(kfxlTtdf)
                        .setXwbxdf(xwbxdf).setXwbxTfwjzzdf(xwbxTfwjzzdf).setXwbxZsdf(xwbxZsdf)
                        .setXwbxZsgdf(xwbxZsgdf).setXwbxZcdf(xwbxZcdf).setXwbxTtdf(xwbxTtdf)
                        .setZf(zf);

                if ("insert".equals(handle)) {
                    wxxsjfxMapper.insert(wxxsjfx);
                } else {
                    super.update(wxxsjfx);
                }
            }
        }
        logger.info("================================================ 结束同步戒毒人员危险性数据 end ================================================");
    }

    /**
     * 根据戒毒人员编码查询各项得分
     *
     * @param jdrybm
     * @return
     */
    public ReturnBean selectScoreByJdrybm(String jdrybm) {
        Wxxsjfx wxxsjfx = wxxsjfxMapper.selectByJdrybm(jdrybm);
        return ReturnBean.ok(wxxsjfx);
    }

    /**
     * 大数据-查询人员列表
     *
     * @param para
     * @return
     */
    public ReturnBean selectWxxsjList(Map para) {
        if (para.get("page") != null) {
            para.put("page", (Integer.valueOf(para.get(SysConstant.PAGE).toString()) - 1) * Integer.valueOf(para.get(SysConstant.LIMIT).toString()));
        }
        Long count = wxxsjfxMapper.selectPersonListCount(para);
        List<Map<String, Object>> list = wxxsjfxMapper.selectPersonList(para);
        return ReturnBean.list(list, count);
    }

    /**
     * 查询本月全所平均分
     *
     * @return
     */
    public Float selectAvg() {
        return wxxsjfxMapper.selectAvg();
    }


    public List<Map<String, Object>> getDetailData(String jdrybm) {
        Wxxsjfx wxxsjfx = wxxsjfxMapper.selectByJdrybm(jdrybm);
        String typeArray[] = {"da", "zdbh", "xlqx", "smtz", "kfxl", "xwbx"};
        String typeNameArray[] = {"戒毒人员档案信息", "重点病号", "心理情绪", "生命体征", "康复训练", "行为表现"};
        List<Map<String, Object>> listData = new ArrayList<>();
        if (null != wxxsjfx) {
            for (int i = 0; i < typeArray.length; i++) {
                Map data = new HashMap();
                String type = typeArray[i];
                data.put("type", type);
                data.put("typeName", typeNameArray[i]);
                Double tfwjzz = 0.00, zs = 0.00, zsg = 0.00, zc = 0.00, tt = 0.00;
                switch (type) {
                    case "da":
                        tfwjzz = wxxsjfx.getDaTfwjzzdf();
                        zs = wxxsjfx.getDaZsdf();
                        zsg = wxxsjfx.getDaZsgdf();
                        zc = wxxsjfx.getDaZcdf();
                        tt = wxxsjfx.getDaTtdf();
                        break;
                    case "zdbh":
                        tfwjzz = wxxsjfx.getZdbhTfwjzzdf();
                        zs = wxxsjfx.getZdbhZsdf();
                        zsg = wxxsjfx.getZdbhZsgdf();
                        zc = wxxsjfx.getZdbhZcdf();
                        tt = wxxsjfx.getZdbhTtdf();
                        break;
                    case "xlqx":
                        tfwjzz = wxxsjfx.getXlqxTfwjzzdf();
                        zs = wxxsjfx.getXlqxZsdf();
                        zsg = wxxsjfx.getXlqxZsgdf();
                        zc = wxxsjfx.getXlqxZcdf();
                        tt = wxxsjfx.getXlqxTtdf();
                        break;
                    case "smtz":
                        tfwjzz = wxxsjfx.getSmtzTfwjzzdf();
                        zs = wxxsjfx.getSmtzZsdf();
                        zsg = wxxsjfx.getSmtzZsgdf();
                        zc = wxxsjfx.getSmtzZcdf();
                        tt = wxxsjfx.getSmtzTtdf();
                        break;
                    case "kfxl":
                        tfwjzz = wxxsjfx.getKfxlTfwjzzdf();
                        zs = wxxsjfx.getKfxlZsdf();
                        zsg = wxxsjfx.getKfxlZsgdf();
                        zc = wxxsjfx.getKfxlZcdf();
                        tt = wxxsjfx.getKfxlTtdf();
                        break;
                    case "xwbx":
                        tfwjzz = wxxsjfx.getXwbxTfwjzzdf();
                        zs = wxxsjfx.getXwbxZsdf();
                        zsg = wxxsjfx.getXwbxZsgdf();
                        zc = wxxsjfx.getXwbxZcdf();
                        tt = wxxsjfx.getXwbxTtdf();
                        break;
                }
                data.put("tfwjzz", tfwjzz);
                data.put("zs", zs);
                data.put("zsg", zsg);
                data.put("zc", zc);
                data.put("tt", tt);

                listData.add(data);
            }
        }
        return listData;
    }

}
