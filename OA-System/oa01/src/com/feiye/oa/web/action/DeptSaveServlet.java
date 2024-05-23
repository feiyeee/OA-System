package com.feiye.oa.web.action;

import com.feiye.oa.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeptSaveServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取部门信息
        request.setCharacterEncoding("UTF-8");
        String deptno = request.getParameter("deptno");
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");

        // 连接数据库执行insert语句
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into dept(deptno, dname, loc) values(?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, deptno);
            ps.setString(2, dname);
            ps.setString(3, loc);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, null);
        }

        // 保存成功跳转到列表页面
        // 保存失败跳转到错误页面
        if (count == 1) {
            // 转发是一次请求
            // request.getRequestDispatcher("/dept/list").forward(request, response);

            // 使用重定向
            // 浏览器在地址栏上发送请求，这个请求是get请求
            response.sendRedirect(request.getContextPath() + "/dept/list");
        } else {
            // request.getRequestDispatcher("/error.html").forward(request, response);

            response.sendRedirect(request.getContextPath() + "/error.html");
        }
    }
}
