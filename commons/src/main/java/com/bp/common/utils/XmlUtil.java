package com.bp.common.utils;

import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * 生成xml字符串工具类
 * @author yanjisheng
 *
 */
public class XmlUtil {

	private static XmlMapper mapper = new XmlMapper();
	
	/**
	 * 按照指定的列表名称和对象名称，把list序列化为xml字符串
	 * @param list 需要转化为xml字符串的list对象
	 * @param listName 列表名称，即顶级tag的名称
	 * @param itemName 对象名称，即第二级tag的名称
	 * @return
	 */
	public synchronized static String getXmlString(List<Object> list, String listName, String itemName) {
		try {
			long startTime = System.currentTimeMillis();
			//JsonNode listNode = mapper.valueToTree(list);
			String xmlString = mapper.writeValueAsString(list);
			long endTime = System.currentTimeMillis();
			System.out.println("--------\n--------\n序列化耗时"+(endTime-startTime)+"毫秒");
			StringBuilder sb = new StringBuilder(xmlString);
			int firstLtIndex = sb.indexOf("<");
			int firstGtIndex = sb.indexOf(">");
			sb.replace(firstLtIndex+1, firstGtIndex, listName);
			int lastLtIndex = sb.lastIndexOf("<");
			int lastGtIndex = sb.lastIndexOf(">");
			sb.replace(lastLtIndex+2, lastGtIndex, listName);
			String out = sb.toString().replace("item", itemName);
			return out;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 把list序列化为xml字符串，其中列表名称为类名加s，对象名称为类名（不含包名）
	 * @param <T>
	 * @param list 需要转化为xml字符串的list对象
	 * @param type list中项目的类型
	 * @return
	 */
	public static <T> String getXmlString(List<T> list, Class<T> type) {
		String typeName = type.getName();
		String simpleTypeName = typeName.substring(typeName.lastIndexOf(".") + 1);
		return getXmlString((List<Object>) list, simpleTypeName + "s", simpleTypeName);
	}
	
	/**
	 * 把list序列化为xml字符串，其中tag均为大写
	 * @param <T>
	 * @param list 需要转化为xml字符串的list对象
	 * @param type list中项目的类型
	 * @return
	 */
	public static <T> String getUpperCaseXmlString(List<T> list, Class<T> type) {
		return xmlTagUpperCase(getXmlString(list, type));
	}
	
	/**
	 * 把xml格式字符串的tag全部转化为大写字母
	 * @param xmlString
	 * @return
	 */
	public static String xmlTagUpperCase(String xmlString) {
		StringBuilder sb = new StringBuilder(xmlString);
		int i=0, j=0;
		for(i=0; i<sb.length(); i++) {
			//System.out.println("i="+i);
			if(sb.charAt(i)=='<') {
				for(j=i+1; j<sb.length(); j++) {
					//System.out.println("j="+j);
					if(sb.charAt(j)=='>') {
						sb.replace(i+1, j, sb.substring(i+1, j).toUpperCase(Locale.ENGLISH));
						i=j;
						break;
					}
				}
			}		
		}
		return sb.toString();
	}
}
