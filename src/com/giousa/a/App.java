package com.giousa.a;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

import org.junit.Test;

import com.giousa.JdbcUtil;


public class App {

		private Connection con;
		private Statement stmt;
		private PreparedStatement pstmt;
		private ResultSet rs;
		

		// 1. 没有使用防止sql注入的案例  结果：执行成功
		@Test
		public void testLogin() {
			
			String userName = "giousa";
			String pwd = " ' or 1=1 -- ";
			
			String sql = "select * from teacher where name='"+userName+"'  and pwd='"+pwd+"' ";
			System.out.println(sql);
			try {
				con = JdbcUtil.getConnection();
				stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					System.out.println("登陆成功，编号：" + rs.getInt("id"));
				}else {
					System.out.println("用户不存在");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					rs.close();
					stmt.close();
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
		// 2. 使用PreparedStatement, 防止sql注入  结果：执行失败
		@Test
		public void testLogin2() {
			
			String userName = "tom";
			String pwd = " ' or 1=1 -- ";
			
			String sql = "select * from teacher where name=?  and pwd=? ";
			try {
			
				con = JdbcUtil.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, userName);
				pstmt.setString(2, pwd);

				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					System.out.println("登陆成功，编号：" + rs.getInt("id"));
				}else {
					System.out.println("用户不存在");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					rs.close();
					pstmt.close();
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
}
