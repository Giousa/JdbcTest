package com.giousa.f;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.giousa.JdbcUtil;


/**
 * ͨ�õ�dao���Լ�д�����е�dao���̳д���;
 * ���ඨ����2��ͨ�õķ�����
 * 	1. ����
 *  2. ��ѯ
 * @author Jie.Yuan
 *
 */
public class BaseDao {
	
	// ��ʼ������
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;

	/**
	 * ���µ�ͨ�÷���
	 * @param sql   ���µ�sql���(update/insert/delete)
	 * @param paramsValue  sql�����ռλ����Ӧ��ֵ(���û��ռλ��������null)
	 */
	public void update(String sql,Object[] paramsValue){
		
		try {
			// ��ȡ����
			con = JdbcUtil.getConnection();
			// ����ִ�������stmt����
			pstmt = con.prepareStatement(sql);
			// ����Ԫ���ݣ� �õ�ռλ�������ĸ���
			int count = pstmt.getParameterMetaData().getParameterCount();
			
			// ����ռλ��������ֵ
			if (paramsValue != null && paramsValue.length > 0) {
				// ѭ����������ֵ
				for(int i=0;i<count;i++) {
					pstmt.setObject(i+1, paramsValue[i]);
				}
			}
			// ִ�и���
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(con, pstmt, null);
		}
	}
	
	/**
	 * ��ѯ��ͨ�÷���
	 * @param sql
	 * @param paramsValue
	 */
	public <T> List<T> query(String sql, Object[] paramsValue,Class<T> clazz){
		
		try {
			// ���صļ���
			List<T> list = new ArrayList<T>();
			// ����
			T t = null;
			
			// 1. ��ȡ����
			con = JdbcUtil.getConnection();
			// 2. ����stmt����
			pstmt = con.prepareStatement(sql);
			// 3. ��ȡռλ�������ĸ����� ������ÿ��������ֵ
			//int count = pstmt.getParameterMetaData().getParameterCount();
			if (paramsValue != null && paramsValue.length > 0) {
				for (int i=0; i<paramsValue.length; i++) {
					pstmt.setObject(i+1, paramsValue[i]);
				}
			}
			// 4. ִ�в�ѯ
			rs = pstmt.executeQuery();
			// 5. ��ȡ�����Ԫ����
			ResultSetMetaData rsmd = rs.getMetaData();
			// ---> ��ȡ�еĸ���
			int columnCount = rsmd.getColumnCount();
			
			// 6. ����rs
			while (rs.next()) {
				// Ҫ��װ�Ķ���
				t = clazz.newInstance();
				
				// 7. ����ÿһ�е�ÿһ��, ��װ����
				for (int i=0; i<columnCount; i++) {
					// ��ȡÿһ�е�������
					String columnName = rsmd.getColumnName(i + 1);
					// ��ȡÿһ�е�������, ��Ӧ��ֵ
					Object value = rs.getObject(columnName);
					// ��װ�� ���õ�t�����������  ��BeanUtils�����
					BeanUtils.copyProperty(t, columnName, value);				
				}
				
				// �ѷ�װ��ϵĶ�����ӵ�list������
				list.add(t);
			}
			
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(con, pstmt, rs);
		}
	}
}









