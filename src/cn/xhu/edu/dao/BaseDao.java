package cn.xhu.edu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import cn.xhu.edu.util.JdbcUtils;

class BaseDao {

	protected <T> List<T> query(String sql, Object[] params, Class<T> clazz) throws Exception {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<T> objectList = new ArrayList<>();
		try {
			conn = JdbcUtils.getConnection();
			pstm = JdbcUtils.getPreparedStatement(conn, sql);
			JdbcUtils.setParams(pstm, params);
			rs = pstm.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				T object = clazz.newInstance();
				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					Object columnValue = rs.getObject(i);
					BeanUtils.setProperty(object, columnName, columnValue);
				}
				objectList.add(object);
			}
		} finally {
			JdbcUtils.close(conn, pstm, rs);
		}
		return objectList;
	}


	public int update(String sql, Object[] params) throws SQLException {
		Connection conn = null;
		PreparedStatement pstm = null;
		int count;
		try {
			conn = JdbcUtils.getConnection();
			pstm = JdbcUtils.getPreparedStatement(conn, sql);
			JdbcUtils.setParams(pstm, params);
			count = pstm.executeUpdate();
		} finally {
			JdbcUtils.close(conn, pstm, null);
		}
		return count;
	}


	protected int count(String sql, Object[] params) throws SQLException {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int count;
		try {
			conn = JdbcUtils.getConnection();
			pstm = JdbcUtils.getPreparedStatement(conn, sql);
			JdbcUtils.setParams(pstm, params);
			rs = pstm.executeQuery();
			rs.next();
			count = rs.getInt("count");
		} finally {
			JdbcUtils.close(conn, pstm, rs);
		}
		return count;
	}
	/*
	 * class MyDateConvert implements Converter {
	 * 
	 * @Override public <T> T convert(Class<T> aClass, Object o) { if (o ==
	 * null) { return null; } if ("".equals("" + o)) { return null; } if ((o
	 * instanceof String)) { DateFormat df = CommUtils.getDateFormat(); try {
	 * return (T) df.parse(o.toString()); } catch (ParseException e) { throw new
	 * RuntimeException(e); } } return (T) o; } }
	 */
}
