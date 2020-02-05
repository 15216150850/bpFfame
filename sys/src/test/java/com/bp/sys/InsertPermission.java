package com.bp.sys;

import com.bp.sys.po.Res;
import com.bp.sys.service.ResService;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InsertPermission {

    @Autowired
    private ResService resService;

    /**
     * 给指定的菜单项添加新增、修改和删除按钮的权限
     */
    @Test
    public void contextLoads() {
        int[] resIds = {2329, 2330};//需要添加权限按钮项的id
        for(int resId : resIds) {
        	Res resList = resService.selectEntityById(resId);
        	String t = resList.getUrl();
        	String permissionBase = t.substring(0, t.lastIndexOf('/')+1).replace('/', ':');
        	if(StringUtils.isEmpty(resList.getPermission())) {
        		resList.setPermission(permissionBase + "list");
        		resService.update(resList);
        	}
        	Res resInsert = new Res();
        	resInsert.setPid(resId);
        	resInsert.setName("新增");
        	resInsert.setType("button");
        	resInsert.setPermission(permissionBase + "insert"); 
        	resService.insert(resInsert);
        	Res resUpdate = new Res();
        	resUpdate.setPid(resId);
        	resUpdate.setName("修改");
        	resUpdate.setType("button");
        	resUpdate.setPermission(permissionBase + "update"); 
        	resService.insert(resUpdate);
        	Res resDelete = new Res();
        	resDelete.setPid(resId);
        	resDelete.setName("删除");
        	resDelete.setType("button");
        	resDelete.setPermission(permissionBase + "delete"); 
        	resService.insert(resDelete);
        	Res resSubmit = new Res();
        	resSubmit.setPid(resId);
        	resSubmit.setName("提报");
        	resSubmit.setType("button");
        	resSubmit.setPermission(permissionBase + "submit"); 
        	resService.insert(resSubmit);
        }
    }

}
