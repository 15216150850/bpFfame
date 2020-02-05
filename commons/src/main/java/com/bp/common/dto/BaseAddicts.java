package com.bp.common.dto;

import com.bp.common.base.BaseEntityUUID;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;


/**
 * @author 刘毓瑞
 * @version 1.0
 * @Description: 戒毒人员表实体类
 * @date 2019年07月02日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "戒毒人员表")
public class BaseAddicts extends BaseEntityUUID {


    /**
     * 人员类型
     */
    @ApiModelProperty(value = "人员类型")
    private String type;

    /**
     * 戒毒人员编码
     */
    @ApiModelProperty(value = "戒毒人员编码")
    private String jdrybm;

    /**
     * 戒毒机构编码
     */
    @ApiModelProperty(value = "戒毒机构编码")
    private String jdjgbm;

    /**
     * 部门编码
     */
    @ApiModelProperty(value = "部门编码")
    private String bmbm;

    /**
     * 照片
     */
    @ApiModelProperty(value = "照片")
    private String zp;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String xm;

    /**
     * 曾用名
     */
    @ApiModelProperty(value = "曾用名")
    private String cym;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String xb;

    /**
     * 出生日期
     */
    @ApiModelProperty(value = "出生日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date csrq;

    /**
     * 民族
     */
    @ApiModelProperty(value = "民族")
    private String mz;

    /**
     * 籍贯类型
     */
    @ApiModelProperty(value = "籍贯类型")
    private String jglx;

    /**
     * 健康状况
     */
    @ApiModelProperty(value = "健康状况")
    private String jkzk;

    /**
     * 血型
     */
    @ApiModelProperty(value = "血型")
    private String xx;

    /**
     * 身高
     */
    @ApiModelProperty(value = "身高")
    private Integer sg;

    /**
     * 证件种类
     */
    @ApiModelProperty(value = "证件种类")
    private String zjzl;

    /**
     * 证件号码
     */
    @ApiModelProperty(value = "证件号码")
    private String zjhm;

    /**
     * 婚姻状况
     */
    @ApiModelProperty(value = "婚姻状况")
    private String hyzk;

    /**
     * 宗教信仰
     */
    @ApiModelProperty(value = "宗教信仰")
    private String zjxy;

    /**
     * 文化程度
     */
    @ApiModelProperty(value = "文化程度")
    private String whcd;

    /**
     * 政治面貌
     */
    @ApiModelProperty(value = "政治面貌")
    private String zzmm;

    /**
     * 户籍所在地
     */
    @ApiModelProperty(value = "户籍所在地")
    private String hjszd;

    /**
     * 现居住地详细地址
     */
    @ApiModelProperty(value = "现居住地详细地址")
    private String xjzdxxdz;

    /**
     * 原单位(学校)地址
     */
    @ApiModelProperty(value = "原单位(学校)地址")
    private String ydwxxdz;

    /**
     * 原单位(学校)联系电话
     */
    @ApiModelProperty(value = "原单位(学校)联系电话")
    private String ydwxxlxdh;

    /**
     * 主要联系人姓名
     */
    @ApiModelProperty(value = "主要联系人姓名")
    private String zylxrxm;

    /**
     * 主要联系人联系电话
     */
    @ApiModelProperty(value = "主要联系人联系电话")
    private String zylxrlxdh;

    /**
     * 主要联系人关系
     */
    @ApiModelProperty(value = "主要联系人关系")
    private String zylxrgx;

    /**
     * 主要联系人联系地址
     */
    @ApiModelProperty(value = "主要联系人联系地址")
    private String zylxrlxdz;

    /**
     * 强制隔离戒毒开始日期
     */
    @ApiModelProperty(value = "强制隔离戒毒开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date qzgljdksrq;

    /**
     * 强制隔离戒毒结束日期
     */
    @ApiModelProperty(value = "强制隔离戒毒结束日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date qzgljdjsrq;

    /**
     * 决定机关名称
     */
    @ApiModelProperty(value = "决定机关名称")
    private String jdjgmc;

    /**
     * 送戒单位名称
     */
    @ApiModelProperty(value = "送戒单位名称")
    private String sjdwmc;

    /**
     * 治安处罚次数
     */
    @ApiModelProperty(value = "治安处罚次数")
    private Integer zacfcs;

    /**
     * 强戒次数
     */
    @ApiModelProperty(value = "强戒次数")
    private Integer qjcs;

    /**
     * 前科次数
     */
    @ApiModelProperty(value = "前科次数")
    private Integer qkcs;

    /**
     * 何处接收
     */
    @ApiModelProperty(value = "何处接收")
    private String hcjs;

    /**
     * 入所日期
     */
    @ApiModelProperty(value = "入所日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date rsrq;

    /**
     * 吸毒史
     */
    @ApiModelProperty(value = "吸毒史")
    private String xds;

    /**
     * 入所前就业情况
     */
    @ApiModelProperty(value = "入所前就业情况")
    private String rsqjyqk;

    /**
     * 滥用毒品种类
     */
    @ApiModelProperty(value = "滥用毒品种类")
    private String lydpzl;

    /**
     * 吸毒方式
     */
    @ApiModelProperty(value = "吸毒方式")
    private String xdfs;

    /**
     * 吸毒年限
     */
    @ApiModelProperty(value = "吸毒年限")
    private String xdnx;

    /**
     * 社保(医保)情况
     */
    @ApiModelProperty(value = "社保(医保)情况")
    private String sbybqk;

    /**
     * 病残情况
     */
    @ApiModelProperty(value = "病残情况")
    private String bcqk;

    /**
     * 是否有刑事犯罪记录
     */
    @ApiModelProperty(value = "是否有刑事犯罪记录")
    private Integer sfyxsfzjl;

    /**
     * 主要违法事实
     */
    @ApiModelProperty(value = "主要违法事实")
    private String zywfss;

    /**
     * 三假人员
     */
    @ApiModelProperty(value = "三假人员")
    private String sjry;

    /**
     * 三无人员
     */
    @ApiModelProperty(value = "三无人员")
    private String swry;

    /**
     * 收治情况
     */
    @ApiModelProperty(value = "收治情况")
    private String szqk;

    /**
     * 戒毒人员状态
     */
    @ApiModelProperty(value = "戒毒人员状态")
    private String jdryzt;

    /**
     * 分期情况
     */
    @ApiModelProperty(value = "分期情况")
    private String fqqk;

    /**
     * 管理等级
     */
    @ApiModelProperty(value = "管理等级")
    private String gldj;

    /**
     * 是否有从军、从警经历
     */
    @ApiModelProperty(value = "是否有从军、从警经历")
    private Integer sfycjcjjl;

    /**
     * 是否有练武经历
     */
    @ApiModelProperty(value = "是否有练武经历")
    private Integer sfylwjl;

    /**
     * 管理方式
     */
    @ApiModelProperty(value = "管理方式")
    private String glfs;

    /**
     * 暂不接收日期
     */
    @ApiModelProperty(value = "暂不接收日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date zbjsrq;

    /**
     * 暂不接收原因
     */
    @ApiModelProperty(value = "暂不接收原因")
    private String zbjsyy;

    /**
     * 不予接收日期
     */
    @ApiModelProperty(value = "不予接收日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date byjsrq;

    /**
     * 不予接收原因
     */
    @ApiModelProperty(value = "不予接收原因")
    private String byjsyy;

    /**
     * 强制戒毒文书号
     */
    @ApiModelProperty(value = "强制戒毒文书号")
    private String qzjdwsh;

    /**
     * 档案号
     */
    @ApiModelProperty(value = "档案号")
    private String dah;

    /**
     * 是否净身入所
     */
    @ApiModelProperty(value = "是否净身入所")
    private String sfjsrs;

    /**
     * 其他疾病
     */
    @ApiModelProperty(value = "其他疾病")
    private String qtjb;

    /**
     * 其他保类
     */
    @ApiModelProperty(value = "其他保类")
    private String qtbl;

    /**
     * 社保（医保）号码
     */
    @ApiModelProperty(value = "社保（医保）号码")
    private String sbybhm;

    /**
     * 单位学校地址
     */
    @ApiModelProperty(value = "单位学校地址")
    private String dwxxdz;

    /**
     * 单位（学校）联系电话
     */
    @ApiModelProperty(value = "单位（学校）联系电话")
    private String dwxxlxdh;

    /**
     * 其他职业
     */
    @ApiModelProperty(value = "其他职业")
    private String qtzy;

    /**
     * 个人经历
     */
    @ApiModelProperty(value = "个人经历")
    private String grjl;

    /**
     * 所在大队
     */
    @ApiModelProperty(value = "所在大队")
    private String szdd;

    /**
     * 戒毒情况：0-初次强制隔离戒毒，1-两次以上强制隔离戒毒
     */
    @ApiModelProperty(value = "戒毒情况：0-初次强制隔离戒毒，1-两次以上强制隔离戒毒")
    private Integer jdqk;

    /**
     * 其他毒品名称
     */
    @ApiModelProperty(value = "其他毒品名称")
    private String qtdpmc;

    /**
     * 强戒决定书
     */
    @ApiModelProperty(value = "强戒决定书")
    private String qjjds;

    /**
     * 诊断评估手册
     */
    @ApiModelProperty(value = "诊断评估手册")
    private String zdpgsc;

    /**
     * 动态管控情况表
     */
    @ApiModelProperty(value = "动态管控情况表")
    private String dtgkqkb;

    /**
     * 吸毒成瘾认定书
     */
    @ApiModelProperty(value = "吸毒成瘾认定书")
    private String xdcyrds;

    /**
     * 健康状况检查表
     */
    @ApiModelProperty(value = "健康状况检查表")
    private String jkzkjcb;

    /**
     * 其他附件
     */
    @ApiModelProperty(value = "其他附件")
    private String qtfj;

    /**
     * 是否已交接个人物品
     */
    @ApiModelProperty(value = "是否已交接个人物品：0-否，1-是")
    private Integer sfyjjgrwp;

    /**
     * 康复级别
     */
    @ApiModelProperty(value = "康复级别")
    private String kfjb;

    /**
     * 是否已强戒
     */
    @ApiModelProperty(value = "是否已强戒：0-否，1-是")
    private Integer sfyqj;

    /**
     * 已强戒天数
     */
    @ApiModelProperty(value = "已强戒天数")
    private Integer yqjts;

    /**
     * 已缩短强戒天数
     */
    @ApiModelProperty(value = "已缩短强戒天数")
    private Integer ysdqjts;

    /**
     * 是否已登记
     */
    @ApiModelProperty(value = "是否已登记")
    private Integer sfydj;

    /**
     * 戒毒人员家庭社会关系
     */
    @ApiModelProperty(value = "戒毒人员家庭社会关系")
    private ArrayList<BaseAddictsRelation> baseAddictsRelations;

    @ApiModelProperty(value = "是否为重点人员")
    private String sfwzdry;

    @ApiModelProperty(value = "列为重点人员原因")
    private String lwzdryyy;

    @ApiModelProperty(value = "重点类型")
    private String zdlx;

    @ApiModelProperty(value = "折算日常行为考核分")
    private Double rcxwkhf;
    /**
     * 体重KG
     */
    @ApiModelProperty(value = "体重")
    private String tz;

    /**
     * 籍贯
     */
    @ApiModelProperty(value = "籍贯")
    private String jg;

    /**
     * 累计合格数
     */
    @ApiModelProperty(value = "累计合格数")
    private Integer ljhgs;

    /**
     * 连续不合格数
     */
    @ApiModelProperty(value = "连续不合格数")
    private Integer lxbhgs;

    /**
     * 主要联系人备注
     */
    @ApiModelProperty(value = "主要联系人备注")
    private String zylxrbz;

    /**
     * 是否计算基础分：0-否，1-是 默认1
     */
    @ApiModelProperty(value = "是否计算基础分：0-否，1-是 默认1")
    private Integer sfjsjcf;
}