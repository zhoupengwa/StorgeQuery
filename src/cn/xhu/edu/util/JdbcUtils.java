package cn.xhu.edu.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtils {
    //获取mysql结点
    private static ComboPooledDataSource dataSource = new ComboPooledDataSource("mysql");
    private static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

    public static Connection getConnection() {
        Connection conn = connectionThreadLocal.get();
        try {
            if (conn == null) {

                return dataSource.getConnection();
            } else {
                return conn;
            }
        } catch (SQLException e) {
            throw new RuntimeException("数据库连接获取异�?");
        }
    }

    public static PreparedStatement getPreparedStatement(Connection conn, String sql) {
        try {
            return conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException("预处理语句获取异�?");
        }
    }

    public static void setParams(PreparedStatement pstm, Object[] params) {
        if (params != null) {
            try {
                for (int i = 1; i <= params.length; i++) {
                    pstm.setObject(i, params[i - 1]);
                }
            } catch (SQLException e) {
                throw new RuntimeException("设置参数异常");
            }
        }
    }

    public static void beginTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.setAutoCommit(false);
                connectionThreadLocal.set(conn);
            } catch (SQLException e) {
                throw new RuntimeException("�?启事物异�?");
            }
        }
    }

    public static void commitTransaction() {
        Connection conn = connectionThreadLocal.get();
        if (conn != null) {
            try {
                conn.commit();
                conn.close();
                connectionThreadLocal.remove();
            } catch (SQLException e) {
                throw new RuntimeException("事物提交异常");
            }
        }
    }

    public static void rollbackTransaction() {
        Connection conn = connectionThreadLocal.get();
        if (conn != null) {
            try {
                conn.rollback();
                conn.close();
                connectionThreadLocal.remove();
            } catch (SQLException e) {
                throw new RuntimeException("事物回滚异常");
            }
        }
    }

    public static void close(Connection conn, PreparedStatement pstm, ResultSet rs) {

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException("结果集关闭失�?");
            }
        }
        if (pstm != null) {
            try {
                pstm.close();
            } catch (SQLException e) {
                throw new RuntimeException("预处理语句关闭失�?");
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException("连接关闭失败");
            }
        }
    }
}
