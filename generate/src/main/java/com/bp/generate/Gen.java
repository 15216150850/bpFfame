package com.bp.generate;


import com.krt.generator.*;

/**
 * @Description: 偷懒代码生成
 * @author
 * @date 2016年7月26日
 * @version 1.
 */
public class Gen {

	public static void main(String[] args) {
		Gen gen = new Gen();
		gen.generator("edu", "base_addicts", "BaseAddicts");

	}

	public void generator(String packageName, String tableName, String entityName){
//		new GenControllerUtil(packageName, tableName, entityName);
//		new GenServiceUtil(packageName, tableName, entityName);
		new GenMapperUtil(packageName, tableName, entityName);
		new GenEntityUtil(packageName, tableName, entityName);
		new GenMapperXmlUtil(packageName, tableName, entityName);
//		new GenJspUtil(packageName, tableName, entityName);
//		new GenPermissionUtil(packageName, tableName, entityName);
	}
}
