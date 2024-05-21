package com.feiye.oa.utils;

import java.sql.*;
import java.util.ResourceBundle;

/**cc
 * JDBC的工具类
 */
public class DBUtil {

    // 静态变量：在类加载时执行
    // 自上而下的顺序
    // 属性资源文件绑定
    private static ResourceBundle bundle = ResourceBundle.getBundle("resources.jdbc");
    // 根据属性配置文件的key获取value
    private static String driver = bundle.getString("driver");
    private static String url = bundle.getString("url");
    private static String user = bundle.getString("user");
    private static String password = bundle.getString("password");


    static {
        // 注册驱动(只需要注册一次，放在静态代码块中.DBUtil类加载时执行)
        // "com.mysql.jdbc.Driver"是连接数据库的驱动，不能写死，以后可能还会连接Oracle数据库
        // OCP开闭原则：对扩展开放，对修改关闭（在进行功能扩展时，不需要修改Java代码）
        // Class.forName("com.mysql.jdbc.Driver");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**cc
     * 获取数据库连接对象
     * @return conn 连接对象
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        // 获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    /**cc
     * 释放资源
     * @param conn 连接对象
     * @param ps 数据库操作对象
     * @param rs 结果集对象
     */
    public static void close(Connection conn, Statement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
