package com.bp.act.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.*;

/**
 * @author 刘毓瑞
 * @version 1.0
 * @Description: 公用的工具类
 * @date 2016年7月20日
 */
public class Common {

    private static final Logger logger = LoggerFactory.getLogger(Common.class);

    /**
     * null则返回空字符串
     *
     * @param parameter
     * @return
     */
    public static String getObjStr(Object parameter) {
        return parameter == null ? "" : parameter.toString();
    }

//	/**
//	 * 获取对象的toString值
// 	 * @param parameter
//	 * @return
//	 */
//	public static String  getObjStr(Object parameter) {
//		return parameter == null ? "" : parameter.toString();
//	}


    public static Double isDouble(Object parameter) {
        Double val = 0.00;
        if (!"".equals(getObjStr(parameter))) {
            val = Double.valueOf(parameter.toString());
        }
        return val;
    }

    /**
     * 转码
     *
     * @param str
     * @return
     */
    public static String toUTF(String str) {
        if ("".equals(str) || str == null) {
            return "";
        } else {
            try {
                str = new String(str.trim().getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("转码失败", e);
            }
            return str;
        }
    }

    /**
     * 处理layUI时间范围插件传递的数据，以~切割开始与结束时间，Map为引用数据类型无需返回值，直接改变值
     *
     * @param para
     * @param spanKey 时间范围的键
     */
    public static void dateSpanSplit(Map para, String spanKey) {
        String timeSpan = Common.getObjStr(para.get(spanKey));
        if (!"".equals(timeSpan)) {
            String[] timeSpans = timeSpan.split("~");
            para.put(spanKey + "Start", timeSpans[0].trim());
            para.put(spanKey + "End", timeSpans[1].trim());
        }
    }

    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }



    /**
     * 获取编码
     *
     * @param prefix 编码前缀(机构编码)
     * @param maxBm  人员数据库最大编码
     */
    public static String buildBm(String prefix, String yearStr, String maxBm) {

        String bmstr = "";
        if (maxBm.length() > 6) {
            bmstr = String.valueOf(Integer.valueOf(maxBm.substring(maxBm.length() - 6)) + 1);
        } else {
            bmstr = String.valueOf(Integer.valueOf(maxBm) + 1);
        }
        String newBm = "";
        switch (bmstr.length()) {
            case 1:
                newBm = prefix + yearStr + "00000" + bmstr;
                break;
            case 2:
                newBm = prefix + yearStr + "0000" + bmstr;
                break;
            case 3:
                newBm = prefix + yearStr + "000" + bmstr;
                break;
            case 4:
                newBm = prefix + yearStr + "00" + bmstr;
                break;
            case 5:
                newBm = prefix + yearStr + "0" + bmstr;
                break;
            default:
                newBm = prefix + yearStr + bmstr;
                break;
        }
        if (maxBm.length() > 6) {
            //如果最大编码的年份不是今年的年份,则从000001开始生成
            if (!yearStr.equals(maxBm.substring(maxBm.length() - 10, maxBm.length() - 6))) {
                newBm = prefix + yearStr + "000001";
            }
        }
        return newBm;
    }

    /**
     * 根据出生日期与体质测试时间计算年龄
     *
     * @param birthDay 戒毒人员出生日期
     * @param cssj     体质测试时间
     * @return
     * @throws Exception
     */
    public static int getAge(Date birthDay, Date cssj) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(cssj);
        //出生日期晚于当前时间，无法计算
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }
        //当前年份
        int yearNow = cal.get(Calendar.YEAR);
        //当前月份
        int monthNow = cal.get(Calendar.MONTH);
        //当前日期
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        //计算整岁数
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;//当前日期在生日之前，年龄减一
                }
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }

    /**
     * 时间相减得出差几个月，精确到天
     *
     * @param startDate 戒毒人员出生日期
     * @param endDate   体质测试时间
     * @return
     * @throws Exception
     */
    public static int getMonthSpan(Date startDate, Date endDate) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        //出生日期晚于当前时间，无法计算
        if (cal.before(startDate)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }
        //当前年份
        int yearEnd = cal.get(Calendar.YEAR);
        //当前月份
        int monthEnd = cal.get(Calendar.MONTH);
        //当前日期
        int dayOfMonthEnd = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(startDate);
        int yearStart = cal.get(Calendar.YEAR);
        int monthStart = cal.get(Calendar.MONTH);
        int dayOfMonthStart = cal.get(Calendar.DAY_OF_MONTH);
        //计算整岁数
        int month = (yearEnd - yearStart) * 12;
        if (monthEnd <= monthStart) {
            if (monthEnd == monthStart) {
                if (dayOfMonthEnd < dayOfMonthStart) {
                    month--;//当前日期在结束日期之前，月份减一
                }
            } else {
                month -= (monthStart - monthEnd);
            }
        } else {
            month += (monthEnd - monthStart);
        }
        return month;
    }

    /**
     * 自定义配置获取随机数(小数)
     *
     * @param min     最小值
     * @param max     最大值
     * @param lastLen 小数点位数(如果是整数,则传入null)
     */
    public static Double getRandomNum(Integer min, Integer max, Integer lastLen) {
        Random random = new Random();
        Double preDouble = Double.valueOf(String.valueOf((random.nextInt(max - min + 1) + min)));
        Double sufDouble = 0.0;
        Double rd = random.nextDouble();
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(lastLen);
        sufDouble = Double.parseDouble(nf.format(rd));
        return preDouble + sufDouble;
    }

    /**
     * 自定义配置获取随机数(整数)
     *
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public static Integer getRandomNum(Integer min, Integer max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

}
