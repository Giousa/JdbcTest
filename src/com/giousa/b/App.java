package com.giousa.b;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class App {

	@Test
	public void testBatch() throws Exception {
		
		List<Admin> list = new ArrayList<Admin>();
		for (int i=1; i<21; i++) {
			Admin admin = new Admin();
			admin.setUserName("Jack" + i);
			admin.setPwd("888" + i);
			list.add(admin);
		}
		
		AdminDao dao = new AdminDao();
		dao.save(list);
	}
}
