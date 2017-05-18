package com.giousa.a;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

import org.junit.Test;

import com.giousa.JdbcUtil;

public class App_call {

	private Connection con;
	private ResultSet rs;
	

	@Test
	public void testCall() throws Exception {
		
		try {
			con = JdbcUtil.getConnection();
			CallableStatement cstmt = con.prepareCall("CALL proc_login");
			rs = cstmt.executeQuery();
			
			if (rs.next()) {
				String name = rs.getString("name");
				String pwd = rs.getString("pwd");
				System.out.println(name + pwd);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
