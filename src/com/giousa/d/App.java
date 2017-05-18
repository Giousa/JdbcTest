package com.giousa.d;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.junit.Test;

import com.giousa.JdbcUtil;


public class App {

	@Test
	public void testDB() throws Exception {
		Connection conn = JdbcUtil.getConnection();
		DatabaseMetaData metaData = conn.getMetaData();
		
		System.out.println(metaData.getUserName());
		System.out.println(metaData.getURL());
		System.out.println(metaData.getDatabaseProductName());
	}
	
	@Test
	public void testParams() throws Exception {
		Connection conn = JdbcUtil.getConnection();
		// SQL
		String sql = "select * from teacher where id=? and name=?";
		// Object[] values = {"tom","888"};
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ParameterMetaData p_metaDate = pstmt.getParameterMetaData();
		int count = p_metaDate.getParameterCount();
		
		System.out.println(count);
	}
	
	@Test
	public void testRs() throws Exception {
		String sql = "select * from teacher ";
		
		Connection conn = JdbcUtil.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		ResultSetMetaData rs_metaData = rs.getMetaData();
		
		while (rs.next()) {
			int count = rs_metaData.getColumnCount();
			for (int i=0; i<count; i++) {
				String columnName = rs_metaData.getColumnName(i + 1);
				Object columnValue = rs.getObject(columnName);
				System.out.print(columnName + "=" + columnValue + ",");
			}
			System.out.println();
		}
		
	}
	
	
	
}








