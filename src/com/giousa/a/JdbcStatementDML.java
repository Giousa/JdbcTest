package com.giousa.a;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import com.giousa.JdbcUtil;

public class JdbcStatementDML {
	
	private static String url = "jdbc:mysql://localhost:3306/test";
	private static String user = "root";
	private static String password = "123456";

	@Test
	public void createTable(){
		Statement stmt = null;
		Connection conn = null;
		try {
			//1.驱动注册程序
			Class.forName("com.mysql.jdbc.Driver");
			//2.获取连接对象
			conn = DriverManager.getConnection(url, user, password);
			System.out.println(conn+"数据库连接成功！");
			
			//3.创建Statement
			stmt = conn.createStatement();
			
			//4.准备sql
			String sql = "CREATE TABLE teacher(id INT PRIMARY KEY AUTO_INCREMENT,name VARCHAR(20),pwd VARCHAR(20)) DEFAULT CHARSET=utf8";
			
			//5.发送sql语句，执行sql语句,得到返回结果
			int count = stmt.executeUpdate(sql);
			
			//6.输出
			System.out.println("当前数目："+count+"行！");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally {
			//7.关闭连接(顺序:后打开的先关闭)
			if(stmt!=null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
		}
		
	}
	
	@Test
	public void insertDB(){
		Connection conn = null;
		Statement stmt = null;
	
		try {
			//通过工具类获取连接对象
			conn = JdbcUtil.getConnection();
			
			//3.创建Statement对象
			stmt = conn.createStatement();
			
			//4.sql语句
			String sql = "INSERT INTO teacher(name,pwd) VALUES('giousa','12j233')";
			
			//5.执行sql
			int count = stmt.executeUpdate(sql);
			
			System.out.println("添加成功！ count = "+count);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally{
			JdbcUtil.close(conn, stmt);
		}
	}
	
	@Test
	public void updateDB(){
		Connection conn = null;
		Statement stmt = null;
		//模拟用户输入
		String name = "nanoha";
		int id = 3;
		try {
			/*//1.注册驱动
			Class.forName("com.mysql.jdbc.Driver");
			
			//2.获取连接对象
			conn = DriverManager.getConnection(url, user, password);*/
			//通过工具类获取连接对象
			conn = JdbcUtil.getConnection();
			
			//3.创建Statement对象
			stmt = conn.createStatement();
			
			//4.sql语句
			String sql = "UPDATE teacher SET name='"+name+"' WHERE id="+id+"";
			
			System.out.println(sql);
			
			//5.执行sql
			int count = stmt.executeUpdate(sql);
			
			System.out.println("更新成功! count = "+count);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally{
			//关闭资源
			JdbcUtil.close(conn, stmt);
		}
	}

	@Test
	public void testDelete(){
		Connection conn = null;
		Statement stmt = null;
		//模拟用户输入
		int id = 4;
		try {
			/*//1.注册驱动
			Class.forName("com.mysql.jdbc.Driver");
			
			//2.获取连接对象
			conn = DriverManager.getConnection(url, user, password);*/
			//通过工具类获取连接对象
			conn = JdbcUtil.getConnection();
			
			//3.创建Statement对象
			stmt = conn.createStatement();
			
			//4.sql语句
			String sql = "DELETE FROM teacher WHERE id="+id+"";
			
			System.out.println(sql);
			
			//5.执行sql
			int count = stmt.executeUpdate(sql);
			
			System.out.println("删除成功！ count = "+count);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally{
			JdbcUtil.close(conn, stmt);
		}
	}
	
	@Test
	public void queryDB(){
		Connection conn = null;
		Statement stmt = null;
		try{
			//获取连接
			conn = JdbcUtil.getConnection();
			//创建Statement
			stmt = conn.createStatement();
			//准备sql
			String sql = "SELECT * FROM teacher";
			//执行sql
			ResultSet rs = stmt.executeQuery(sql);
			
			//移动光标
			/*boolean flag = rs.next();
			
			flag = rs.next();
			flag = rs.next();
			if(flag){
				//取出列值
				//索引
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String gender = rs.getString(3);
				System.out.println(id+","+name+","+gender);
				
				//列名称
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				System.out.println(id+","+name+","+gender);
			}*/
			
			//遍历结果
			while(rs.next()){
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String pwd = rs.getString("pwd");
				System.out.println(id+","+name+","+pwd);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			JdbcUtil.close(conn, stmt);
		}
	}
}
