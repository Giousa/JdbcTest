package com.giousa.f;

import java.util.List;

import org.junit.Test;

public class AdminDaoTest {

	@Test
	public void testUpdate() throws Exception {
		AdminDao adminDao = new AdminDao();
		//adminDao.delete(2);
		//adminDao.save(new Admin());
		
		// ���Բ�ѯ
		List<Admin> list = adminDao.getAll();
		System.out.println(list);
	}
}
