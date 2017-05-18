package com.giousa.b;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.giousa.JdbcUtil;

public class AdminDao {
	
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public void save(List<Admin> list) {
		
		String sql = "INSERT INTO teacher(name,pwd) values(?,?)";
		
		try {
			con = JdbcUtil.getConnection();
			pstmt = con.prepareStatement(sql); 
			
			for (int i=0; i<list.size(); i++) {
				Admin admin = list.get(i);
				pstmt.setString(1, admin.getUserName());
				pstmt.setString(2, admin.getPwd());
				
				pstmt.addBatch();
				
				if (i % 5 == 0) {
					pstmt.executeBatch();
					pstmt.clearBatch();
				}
				
			}
			
			pstmt.executeBatch();
			pstmt.clearBatch();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(con, pstmt, rs);
		}
	}
}

















