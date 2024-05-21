package com.feiye.oa.web.action;

import com.feiye.oa.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeptListServlet extends HttpServlet {
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doGet(request, response);
//    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 获取应用的根路径
        String contextPath = request.getContextPath();

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.print("<!DOCTYPE html>");
        out.print("    <html>");
        out.print("    	<head>");
        out.print("    		<meta charset='utf-8'>");
        out.print("    		<title>部门列表页面</title>");

        out.print("<script type='text/javascript'>");
        out.print("        function del(deptno) {");
        out.print("            var ok = window.confirm('是否确认删除？');");
        out.print("            if(ok) {");
        out.print("                document.location.href = '/oa/dept/delete?deptno=' + deptno;");
        out.print("            }");
        out.print("        }");
        out.print("</script>");

        out.print("    	</head>");
        out.print("    	<body>");
        out.print("    		<h1 align='center'>部门列表</h1>");
        out.print("    		<hr />");
        out.print("    		<table border='1px' align='center' width='50%'>");
        out.print("    			<tr>");
        out.print("    				<th>序号</th>");
        out.print("    				<th>部门编号</th>");
        out.print("    				<th>部门名称</th>");
        out.print("    				<th>操作</th>");
        out.print("    			</tr>");

        // 连接数据库，查询所有部门
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        System.out.println("try-catch语句之前");
        try {
            System.out.println("连接之前");
            // 获取连接
            conn = DBUtil.getConnection();
            System.out.println("连接之后");
            if (conn == null) {
                out.print("    			<tr><td colspan='4'>数据库连接失败！</td></tr>");
                out.print("    		</table>");
                out.print("    		<hr />");
                out.print("    		<a href='/oa/add.html'>新增部门</a>");
                out.print("    	</body>");
                out.print("</html>");
                return;
            }
            // 获取预编译的数据库操作对象
            String sql = "select deptno as a, dname, loc from dept";
            ps = conn.prepareStatement(sql);
            // 执行SQL语句
            rs = ps.executeQuery();
            // 处理结果集
            int i = 0;
            while (rs.next()) {
                String deptno = rs.getString("a");
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");

                // ↓ 动态
                out.print("    			<tr>");
                out.print("    				<td>" +(++i)+ "</td>");
                out.print("    				<td>" +deptno+ "</td>");
                out.print("    				<td>" +dname+ "</td>");
                out.print("    				<td>");
                out.print("    					<a href='javascript:void(0)' onclick='del(" +deptno+ ")'>删除</a>");
                out.print("    					<a href='/oa/dept/edit?deptno=" +deptno+ "'>修改</a>");
                out.print("    					<a href='" +contextPath+ "/dept/detail?deptno=" +deptno+ "'>详情</a>");
                out.print("    				</td>");
                out.print("    			</tr>");
            }
            if (i == 0) {
                out.print("    			<tr><td colspan='4'>没有数据</td></tr>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }

        out.print("    		</table>");
        out.print("    		<hr />");
        out.print("    		<a href='/oa/add.html'>新增部门</a>");
        out.print("    	</body>");
        out.print("</html>");
    }


}
