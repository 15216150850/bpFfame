package com.bp.sys.xmlcontroller;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bp.common.utils.XmlUtil;
import com.bp.sys.po.User;

@Controller
public class XmlUserController {
	
	@GetMapping("/ingnore/xml")
	@ResponseBody
	public String list() {
		List<User> list = getList();
		long startTime = System.currentTimeMillis();
		String xmlString = XmlUtil.getUpperCaseXmlString(list, User.class);
		long endTime = System.currentTimeMillis();
		System.out.println("--------\n--------\n耗时"+(endTime-startTime)+"毫秒");
		return xmlString;
    }
	
	@GetMapping("/ingnore/lowerCaseXml")
	@ResponseBody
	public String listLowerCase() {
		List<User> list = getList();
		long startTime = System.currentTimeMillis();
		String xmlString = XmlUtil.getXmlString(list, User.class);
		long endTime = System.currentTimeMillis();
		System.out.println("--------\n--------\n耗时"+(endTime-startTime)+"毫秒");
		return xmlString;
	}
	
	private List<User> getList(){
		List<User> list = new LinkedList<>();
		String[] usernames = {"Alice", "Bob", "Cindy", "Diana", "Emily", "Fionce", 
				"George", "Helen", "Ilizabeth", "James", "Ken", "Lucas", "May", 
				"Nancy", "Okay", "Peter", "Queen", "Richard", "Simon", "Tony", 
				"Union", "Victory", "William", "Xray", "Yellow", "Zack"};
		for(int i=1; i<=10000; i++) {
			User user = new User();
			user.setId(i);
			user.setName("测试");
			Random random = new Random();		
			user.setUsername(usernames[random.nextInt(usernames.length)]);
			list.add(user);
		}
		return list;
	}
	
}
