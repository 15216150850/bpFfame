package com.bp.sixsys.po.hr;
import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bp.common.base.BaseEntityUUID;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


/**
 * @author 钟欣凯
 * @version 1.0
 * @Description: 警察基本信息表实体类
 * @date 2019年07月12日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "警察基本信息表")
@Builder
@TableName(value = "base_police")

public class BasePolice extends BaseEntityUUID {

    /**
     * 警察编码
     */
    @Excel(name = "警号")
    @ApiModelProperty(value = "警察编码")
    private String jcbm;

    /**
     * 戒毒机构编码
     */
    @ApiModelProperty(value = "戒毒机构编码")
    @Excel(name = "单位")
    private String jdjgbm;

    /**
     * 部门编码
     */
    @Excel(name = "部门")
    @ApiModelProperty(value = "部门编码")
    private String bmbm;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String xm;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    @Excel(name = "性别")
    private String xb;

    /**
     * 出生日期
     */
    @ApiModelProperty(value = "出生日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期")
    private Date csrq;


    @TableField(exist = false)
    private String csrqStr;

    /**
     * 籍贯
     */
    @ApiModelProperty(value = "籍贯")
    @Excel(name = "籍贯")
    private String jg;

    /**
     * 出生地
     */
    @ApiModelProperty(value = "出生地")
    @Excel(name = "出生地")
    private String csd;

    /**
     * 民族
     */
    @ApiModelProperty(value = "民族")
    @Excel(name = "民族")
    private String mz;

    /**
     * 健康状况
     */
    @ApiModelProperty(value = "健康状况")
    @Excel(name = "健康状况")
    private String jkzk;

    /**
     * 婚姻状况
     */
    @ApiModelProperty(value = "婚姻状况")
    @Excel(name = "婚姻状况")
    private String hyzk;

    /**
     * 参加工作时间
     */
    @ApiModelProperty(value = "参加工作时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "参加工作日期")
    private Date cjgzsj;

    @TableField(exist = false)
    private String cjgzsjStr;
    /**
     * 工龄计算校正值
     */
    @ApiModelProperty(value = "工龄计算校正值")
    private Float gljsjzz;

    /**
     * 政治面貌
     */
    @ApiModelProperty(value = "政治面貌")
    @Excel(name = "政治面貌")
    private String zzmm;

    /**
     * 进入本单位日期
     */
    @ApiModelProperty(value = "进入本单位日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "录警日期")
    private Date jrbdwrq;

    @TableField(exist = false)
    private String jrbdwrqStr;
    /**
     * 人事关系所在单位名称
     */
    @ApiModelProperty(value = "人事关系所在单位名称")
    private String rsgxszdwmc;

    /**
     * 户籍所在地
     */
    @ApiModelProperty(value = "户籍所在地")
    private String hjszd;

    /**
     * 居民身份证号码
     */
    @ApiModelProperty(value = "居民身份证号码")
    @Excel(name = "身份证号")
    private String jmsfzhm;

    /**
     * 人员照片
     */
    @ApiModelProperty(value = "人员照片")
    private String ryzp;

    /**
     * 人员状态
     */
    @ApiModelProperty(value = "人员状态")
    @Excel(name = "状态")
    private String ryzt;

    /**
     * 职务类别
     */
    @ApiModelProperty(value = "职务类别")
    private String zwlb;

    /**
     * 行政职务
     */
    @ApiModelProperty(value = "行政职务")
    @Excel(name = "现任职务")
    private String xzzw;

    /**
     * 党内职务
     */
    @ApiModelProperty(value = "党内职务")
    private String dnzw;

    /**
     * 职级
     */
    @ApiModelProperty(value = "职级")
    @Excel(name = "现任职级")
    private String zj;

    /**
     * 警衔
     */
    @ApiModelProperty(value = "警衔")
    @Excel(name = "现任警衔")
    private String jx;

    /**
     * 警衔变动类型
     */
    @ApiModelProperty(value = "警衔变动类型")
    @Excel(name = "警衔变动类别")
    private String jxbglx;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    @Excel(name = "移动电话")
    private String dh;

    /**
     * 文化程度
     */
    @ApiModelProperty(value = "文化程度")
//    @Excel(name = "最高学历")
    private String whcd;

    /**
     * 最高学位
     */
    @ApiModelProperty(value = "最高学位")
    @Excel(name = "学位")
    private String zgxw;

    /**
     * 专业
     */
    @ApiModelProperty(value = "专业")
    @Excel(name = "全日制所学专业")
    private String zy;

    /**
     * 警力分布情况
     */
    @ApiModelProperty(value = "警力分布情况")
    private String jlfbqk;

    /**
     * 一线警察分布情况
     */
    @ApiModelProperty(value = "一线警察分布情况")
    private String yxjcfbqk;

    /**
     * 人员来源
     */
    @ApiModelProperty(value = "人员来源")
    private String ryly;

    /**
     * 调任类型
     */
    @ApiModelProperty(value = "调任类型")
    private String drlx;

    /**
     * 状态变更日期
     */
    @ApiModelProperty(value = "状态变更日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date ztbgrq;

    /**
     * 兼职情况
     */
    @ApiModelProperty(value = "兼职情况")
    private String jzqk;

    /**
     * 考生类型
     */
    @ApiModelProperty(value = "考生类型")
    private String kslx;

    /**
     * 年龄(新增)
     */
    @ApiModelProperty(value = "年龄(新增)")
    private Integer nl;

    /**
     * 参加组织日期
     */
    @ApiModelProperty(value = "参加组织日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入党日期")
    private Date cjzzrq;

    /**
     * 户口性质
     */
    @ApiModelProperty(value = "户口性质")
    private String hkxz;

    /**
     * 联系方式
     */
    @ApiModelProperty(value = "联系方式")
    @Excel(name = "移动电话")
    private String lxfs;

    /**
     * 入学日期
     */
    @ApiModelProperty(value = "入学日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date rxrq;

    /**
     * 毕业日期
     */
    @ApiModelProperty(value = "毕业日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date byrq;

    /**
     * 学校名称
     */
    @ApiModelProperty(value = "学校名称")
    @Excel(name = "全日制毕业学校")
    private String xxmc;

    /**
     * 学校(单位)所在政区
     */
    @ApiModelProperty(value = "学校(单位)所在政区")
    private String xxszzq;

    /**
     * 学制
     */
    @ApiModelProperty(value = "学制")
    private String xz;

    /**
     * 所学专业类别
     */
    @ApiModelProperty(value = "所学专业类别")
    private String sxzylb;

    /**
     * 学位授予日期
     */
    @ApiModelProperty(value = "学位授予日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date xwsyrq;

    /**
     * 学位授予单位
     */
    @ApiModelProperty(value = "学位授予单位")
    private String xwsydw;

    /**
     * 学位授予单位所在政区
     */
    @ApiModelProperty(value = "学位授予单位所在政区")
    private String xwsydwszzq;

    /**
     * 学习经历
     */
    @ApiModelProperty(value = "学习经历")
    private String xxjl;

    /**
     * 岗位名称
     */
    @ApiModelProperty(value = "岗位名称")
    @Excel(name = "岗位名称")
    private String gwmc;

    /**
     * 警察类型
     */
    @ApiModelProperty(value = "警察类型")
    @Excel(name = "人员类型")
    private String jclx;

    @ApiModelProperty(value = "头像")
    private String tx;

    @ApiModelProperty(value = "学历")
    @Excel(name = "最高学历")
    private String xl;

    @ApiModelProperty(value = "人员编制类型")
    private String rybzlx;
    /**
     * excel所需字段
     */


    @TableField(exist = false)
    private String rtzjrqStr;

    @ApiModelProperty(value = "任同职级日期")
    @Excel(name = "任同职级日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date rtzjrq;

    @TableField(exist = false)
    private String rxzrqStr;

    @ApiModelProperty(value = "任现职日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任现职日期")
    private Date rxzrq;

    @TableField(exist = false)
    private String rdrqStr;

    @ApiModelProperty(value = "入党日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入党日期")
    private Date rdrq;


    @Excel(name = "全日制学历")
    private String qrzxl;


    @Excel(name = "全日制学位")
    private String qrzxw;

    @Excel(name = "在职学历")
    private String zzxl;

    @Excel(name = "在职学位")
    private String zzxw;

    @Excel(name = "在职毕业学校")
    private String zzbyxx;

    @Excel(name = "在职所学专业")
    private String zzsxzy;

    @Excel(name = "专业技术职称")
    private String zyjszc;

    @Excel(name = "现衔起算日期")
    @TableField(exist = false)
    private String xxqsrqStr;

    @Excel(name = "是否列入警务通讯录")
    @ApiModelProperty(value = "是否列入警务通讯录")
    private String sfjrjwtxl;

    @Excel(name = "血型")
    @ApiModelProperty(value = "血型")
    private String xx;


    /**
     * 页面所需字段
     */
    @ApiModelProperty(value = "警衔名称")
    @TableField(exist = false)
    private String jxName;


    @ApiModelProperty(value = "性别名称")
    @TableField(exist = false)
    private String xbName;

    @ApiModelProperty(value = "部门名称")
    @TableField(exist = false)
    private String bmmc;

    @ApiModelProperty(value = "政治面貌名称")
    @TableField(exist = false)
    private String zzmmName;

    @ApiModelProperty(value = "职级名称")
    @TableField(exist = false)
    private String zjName;

    @ApiModelProperty(value = "人员状态名称")
    @TableField(exist = false)
    private String ryztName;

    @ApiModelProperty(value = "变更前职级名称")
    @TableField(exist = false)
    private String bgqzjName;

    @ApiModelProperty(value = "戒毒机构名称")
    @TableField(exist = false)
    private String jdjgmc;


    @ApiModelProperty(value = "警察绑定复选框勾选")
    private String layChecked;


    @ApiModelProperty(value = "排序")
    private Integer sortNo;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "第二学历")
    private String xl2;

    @ApiModelProperty(value = "是否为常用联系人")
    @TableField(exist = false)
    private String sfwcylxr;
}