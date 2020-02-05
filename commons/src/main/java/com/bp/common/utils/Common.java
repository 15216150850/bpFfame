package com.bp.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bp.common.exception.BpException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.*;

/**
 * 
 * @Description: 公用的工具类
 * @author 刘毓瑞
 * @date 2016年7月20日
 * @version 1.0
 */
public class Common {

	private static final Logger logger = LoggerFactory.getLogger(Common.class);

	/**
	 * null则返回空字符串
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
		if(!"".equals(getObjStr(parameter))){
			val =  Double.valueOf(parameter.toString());
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
	 * @param para
	 * @param spanKey 时间范围的键
	 */
	public static void dateSpanSplit(Map para,String spanKey){
		String timeSpan=Common.getObjStr(para.get(spanKey));
		if(!"".equals(timeSpan)){
			String[] timeSpans=timeSpan.split("~");
			para.put(spanKey+"Start",timeSpans[0].trim());
			para.put(spanKey+"End",timeSpans[1].trim());
		}
	}
	public static String getUuid(){
		return UUID.randomUUID().toString().replace("-","");
	}

	/**
	 * 检测文件是否是excel
	 *
	 * @param file
	 */
	public static void isExcel(MultipartFile file) {
		if (file == null || file.isEmpty()) {
			throw new BpException("上传excel不能为空");
		}
		String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
		if (!"xls".equalsIgnoreCase(fileExt) && !"xlsx".equalsIgnoreCase(fileExt)) {
			throw new BpException("文件类型不合法");
		}
	}

	/**
	 * 获取编码
	 * @param prefix 编码前缀(机构编码)
	 * @param maxBm 人员数据库最大编码
	 */
	public static String buildBm(String prefix,String yearStr,String maxBm){

		String bmstr = "";
		if(maxBm.length()>6){
			bmstr = String.valueOf(Integer.valueOf(maxBm.substring(maxBm.length()-6))+1);
		}else{
			bmstr = String.valueOf(Integer.valueOf(maxBm)+1);
		}
		String newBm = "";
		switch (bmstr.length()){
			case 1:  newBm=prefix+yearStr+"00000"+bmstr;
					break;
			case 2:  newBm=prefix+yearStr+"0000"+bmstr;
				break;
			case 3:  newBm=prefix+yearStr+"000"+bmstr;
				break;
			case 4:  newBm=prefix+yearStr+"00"+bmstr;
				break;
			case 5:  newBm=prefix+yearStr+"0"+bmstr;
				break;
			default: newBm=prefix+yearStr+bmstr;
				break;
		}
		if(maxBm.length()>6){
			//如果最大编码的年份不是今年的年份,则从000001开始生成
			if(!yearStr.equals(maxBm.substring(maxBm.length()-10,maxBm.length()-6))){
				newBm = prefix+yearStr+"000001";
			}
		}
		return newBm;
	}

	/**
	 * 根据出生日期与体质测试时间计算年龄
	 * @param birthDay 戒毒人员出生日期
	 * @param cssj 体质测试时间
	 * @return
	 * @throws Exception
	 */
	public static int getAge(Date birthDay,Date cssj) throws Exception {
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
				if(dayOfMonthNow < dayOfMonthBirth){
					 age--;//当前日期在生日之前，年龄减一
				}
			}else{
				age--;//当前月份在生日之前，年龄减一
			}
		}
		return age;
	}

	/**
	 * 时间相减得出差几个月，精确到天
	 * @param startDate 戒毒人员出生日期
	 * @param endDate 体质测试时间
	 * @return
	 * @throws Exception
	 */
	public static int getMonthSpan(Date startDate,Date endDate) throws Exception {
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
		int month = (yearEnd - yearStart)*12;
		if (monthEnd <= monthStart) {
			if (monthEnd == monthStart) {
				if(dayOfMonthEnd < dayOfMonthStart){
					month--;//当前日期在结束日期之前，月份减一
				}
			}else{
				month-=(monthStart-monthEnd);
			}
		}else{
			month+=(monthEnd-monthStart);
		}
		return month;
	}

	/**
	 * 自定义配置获取随机数(小数)
	 * @param min 最小值
	 * @param max 最大值
	 * @param lastLen 小数点位数
	 */
	public static Double getRandomNum(Integer min, Integer max, Integer lastLen){
		Random random = new Random();
		Double preDouble = Double.valueOf(String.valueOf((random.nextInt(max-min+1) + min)));
		Double sufDouble = 0.0;
		Double rd=random.nextDouble();
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(lastLen);
		sufDouble = Double.parseDouble(nf.format(rd));
		return preDouble + sufDouble;
	}

	/**
	 * 自定义配置获取随机数(整数)
	 * @param min 最小值
	 * @param max 最大值
	 * @return
	 */
	public static Integer getRandomNum(Integer min, Integer max){
		Random random = new Random();
		return random.nextInt(max) % (max - min + 1) + min;
		/*random.nextInt(max-min+1) + min;*/
	}

	/**
	 * @author 肖陈军
	 * 复杂查询时处理页面返回来的复杂查询条件
	 * @param para
	 * @return 返回处理好的条件list集合
	 */
	public static List<Map> dealCondition(Map para){
		//判断页面是否用了复杂查询,没有则直接返回空
		String newJson = StringEscapeUtils.unescapeHtml4(Common.getObjStr(para.get("jsonStr")));
		if(!"".equals(newJson)) {
			//将复杂查询条件转成json数组
			JSONArray conditionArray = JSON.parseArray(newJson);
			List<Map> reList = new ArrayList<>();
			//解析每组条件,取出我们所需要的字段名,判断条件,判断值,范围值等,每组封成map,存入list中,返回
			for (int i = 0; i < conditionArray.size(); i++) {
				Map valMap = new HashMap(5);
				Map condition = (Map) conditionArray.get(i);
				String conditionFieldVal = condition.get("conditionFieldVal").toString();
				String conditionOptionVal = condition.get("conditionOptionVal").toString();
				String logicalOperator = condition.get("logicalOperator").toString();
				Map conditionValueVal = (Map) condition.get("conditionValueVal");
				Map conditionValueLeftVal = (Map) condition.get("conditionValueLeftVal");
				Map conditionValueRightVal = (Map) condition.get("conditionValueRightVal");
				//字段名变成下划线格式
				valMap.put("conditionFieldVal", humpToUnderline(conditionFieldVal));
				valMap.put("conditionOptionVal", conditionOptionVal);
				valMap.put("conditionValueVal", conditionValueVal.get("value").toString());
				valMap.put("conditionValueLeftVal", conditionValueLeftVal.get("value"));
				valMap.put("conditionValueRightVal", conditionValueRightVal.get("value"));
				valMap.put("logicalOperator",logicalOperator);
				reList.add(valMap);
			}
			return reList;
		}else {
			return null;
		}
	}

	/**
	 * 将驼峰式变成下划线
	 * @param para
	 * @return
	 */
	public static String humpToUnderline(String para){
		StringBuilder sb=new StringBuilder(para);
		int temp=0;//定位
		for(int i=0;i<para.length();i++){
			if(Character.isUpperCase(para.charAt(i))){
				sb.insert(i+temp, "_");
				temp+=1;
			}
		}
		return sb.toString().toLowerCase();
	}

	/**
	 * 数字转成大写
	 * @param num
	 * @return
	 */
	public static String intToChinese(Integer num) {
		if(num==null){
			num = 0;
		}
		String[] units = {"", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿"};
		char[] nums = {'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};
		StringBuilder sb = new StringBuilder();
		char[] val = String.valueOf(num).toCharArray();
		int len = val.length;
		for (int i = 0; i < len; i++) {
			String m = String.valueOf(val[i]);
			int n = Integer.valueOf(m);
			if (n == 0) {
				//如果当前位置的前一个位置是0 忽略
				if (len > 1 && '0' == val[i - 1]) {
					continue;
				} else {
					sb.append(nums[n]);
				}
			} else {
				String numStr = String.valueOf(nums[n]);
				String unitStr = units[(len - 1) - i];
				//特殊处理 一十 --> 十
				if("一".equals(numStr) && "十".equals(unitStr)) {
					sb.append(unitStr);
				} else {
					sb.append(numStr).append(unitStr);
				}

			}
		}
		//去除尾部多余的零
		String s = sb.toString();
		if(s.length() > 1 && "零".equals(String.valueOf(s.charAt(s.length()-1)))) {
			s = s.substring(0, s.length() - 1);
		}
		return s;
	}

}
