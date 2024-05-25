package com.feiye.oa.web.action;

import com.feiye.oa.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet({"/user/login", "/user/exit"})
public class UserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if ("/user/login".equals(servletPath)) {
            doLogin(request, response);
        } else if ("/user/exit".equals(servletPath)) {
            doExit(request, response);
        }
    }

    private void doExit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取session对象，销毁session
        HttpSession session = request.getSession(false);
        if (session!=null) {
            // 手动销毁session
            session.invalidate();
            // 销毁cookie
            Cookie[] cookies = request.getCookies();
            if (cookies!=null) {
                for (Cookie cookie : cookies) {
                    String name = cookie.getName();
                    if ("username".equals(name) || "password".equals(name)) {
                        // 销毁cookie
                        cookie.setMaxAge(0);
                        // 设置cookie的路径
                        cookie.setPath(request.getContextPath());   // 该项目下的所有cookie都删除
                        // 响应cookie给浏览器，浏览器端会将之前的cookie覆盖
                        response.addCookie(cookie);
                    }
                }
            }

            // 另一种方式
            /*Cookie cookie1 = new Cookie("username", "");
            cookie1.setMaxAge(0);
            cookie1.setPath(request.getContextPath());

            Cookie cookie2 = new Cookie("password", "");
            cookie2.setMaxAge(0);
            cookie2.setPath(request.getContextPath());

            response.addCookie(cookie1);
            response.addCookie(cookie2);*/

            // 跳转到登录页面
            response.sendRedirect(request.getContextPath());
        }
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 目的：验证用户名和密码是否正确

        boolean success = false;
        // 获取用户名和密码
        // 前端是这样提交的：username=admin&password=123
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 连接数据库验证用户名和密码
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from t_user where username=? and password=?";
            // 编译SQL
            ps = conn.prepareStatement(sql);
            // 给?传值
            ps.setString(1, username);
            ps.setString(2, password);
            // 执行SQL
            rs = ps.executeQuery();
            // 该结果集中最多只有一条数据
            if (rs.next()) {
                // 登录成功
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }

        // 登陆成功/失败
        if (success) {
            // 获取session对象(必须获取session，没有session也要新建一个)
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            // 登录成功，且选择了“十天内免登录”功能
            String f = request.getParameter("f");
            if ("1".equals(f)) {
                // 创建cookie对象存储用户名
                Cookie cookie1 = new Cookie("username", username);
                // 创建cookie对象存储密码
                Cookie cookie2 = new Cookie("password", password);   // 真实情况下是加密的
                // 设置cookie的有效期为十天
                cookie1.setMaxAge(60*60*24*10);
                cookie2.setMaxAge(60*60*24*10);
                // 设置cookie的path（只要访问这个应用，浏览器就一定要携带这两个cookie）
                cookie1.setPath(request.getContextPath());
                cookie2.setPath(request.getContextPath());
                // 响应cookie给浏览器
                response.addCookie(cookie1);
                response.addCookie(cookie2);
            }

            // 成功，跳转到用户列表页面
            response.sendRedirect(request.getContextPath() + "/dept/list");
        } else {
            // 失败，跳转到失败页面
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }
}

