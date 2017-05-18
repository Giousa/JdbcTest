package com.giousa.c;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.junit.Test;

public class App {

	@Test
	public void test1() throws Exception {
		
		Admin admin = new Admin();
//		admin.setUserName("Jack");
//		admin.setPwd("999");
		
		BeanUtils.copyProperty(admin, "userName", "jack");
		BeanUtils.setProperty(admin, "age", 18);
		
		Admin newAdmin = new Admin();
		BeanUtils.copyProperties(newAdmin, admin);
		
		Admin adminMap = new Admin();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userName", "Jerry");
		map.put("age", 29);

		BeanUtils.populate(adminMap, map);
		
		System.out.println(adminMap.getUserName());
		System.out.println(adminMap.getAge());
	}
	
	
	@Test
	public void test2() throws Exception {
		String name = "jack";
		String age = "20";
		String birth = "1990-12-24";
		
		Admin admin = new Admin();
		
		ConvertUtils.register(new Converter() {
			@Override
			public Object convert(Class type, Object value) {
				
				if (type != Date.class) {
					return null;
				}
				if (value == null || "".equals(value.toString().trim())) {
					return null;
				}
				
				
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					return sdf.parse(value.toString());
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}
		},Date.class);
		
		
		BeanUtils.copyProperty(admin, "userName", name);
		BeanUtils.copyProperty(admin, "age", age);
		BeanUtils.copyProperty(admin, "birth", birth);
		
		System.out.println(admin);
	}
	
	@Test
	public void test3() throws Exception {
		String name = "userName";
		String age = "20";
		String birth = "1990-12-24";
		
		Admin admin = new Admin();
		
		ConvertUtils.register(new DateLocaleConverter(), Date.class);
				
		BeanUtils.copyProperty(admin, "userName", name);
		BeanUtils.copyProperty(admin, "age", age);
		BeanUtils.copyProperty(admin, "birth", birth);
		
		System.out.println(admin);
	}
}







