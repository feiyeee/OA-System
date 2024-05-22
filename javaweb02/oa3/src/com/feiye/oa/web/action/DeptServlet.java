package com.feiye.oa.web.action;

import com.feiye.oa.bean.Dept;
import com.feiye.oa.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet({"/dept/list", "/dept/detail", "/dept/delete", "/dept/save", "/dept/modify"})
public class DeptServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if("/dept/list".equals(servletPath)) {
            doList(request, response);
        } else if ("/dept/detail".equals(servletPath)) {
            doDetail(request, response);
        } else if("/dept/delete".equals(servletPath)) {
            doDel(request, response);
        } else if("/dept/save".equals(servletPath)) {
            doSave(request, response);
        } else if("/dept/modify".equals(servletPath)) {
            doModify(request, response);
        }
    }

    /**mc
     * 修改部门
     * @param request
     * @param response
     * @throws IOException
     */
    private void doModify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 解决请求体的中文乱码问题
        request.setCharacterEncoding("UTF-8");
        // 获取表单中的数据
        String deptno = request.getParameter("deptno");
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");
        // 连接数据库，执行更新语句
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            conn = DBUtil.getConnection();
            String sql = "update dept set dname=?, loc=? where deptno=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dname);
            ps.setString(2, loc);
            ps.setString(3, deptno);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, null);
        }

        if (count == 1) {
            response.sendRedirect(request.getContextPath() + "/dept/list");
        }
    }

    /**mc
     * 保存部门信息
     * @param request
     * @param response
     * @throws IOException
     */
    private void doSave(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

        if (count == 1) {
            response.sendRedirect(request.getContextPath() + "/dept/list");
        }
    }

    /**mc
     * 根据部门编号删除部门
     * @param request
     * @param response
     */
    private void doDel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取部门编号
        String deptno = request.getParameter("deptno");
        // 连接数据库，删除部门
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from dept where deptno=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, deptno);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, null);
        }

        if (count == 1) {
            // 删除成功
            // 重定向到列表页面
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/dept/list");
        }
    }

    /**mc
     * 根据部门编号获取部门详细信息/修改部门信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void doDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取部门编号
        String deptno = request.getParameter("deptno");

        // 创建部门对象
        Dept dept = new Dept();

        // 根据部门编号获取部门信息，将部门信息封装成咖啡豆
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select dname, loc from dept where deptno=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, deptno);
            rs = ps.executeQuery();
            // 该结果集中只有一条数据，不需要while循环
            if (rs.next()) {
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");
                // 封装对象（创建豆子）
                dept.setDeptno(deptno);
                dept.setDname(dname);
                dept.setLoc(loc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }

        // 这个豆子只有一个，所以不用袋子，只需要将这个咖啡豆放到request域中即可
        request.setAttribute("dept", dept);
        // 转发
        // request.getRequestDispatcher("/detail.jsp").forward(request, response);

        /*String f = request.getParameter("f");
        if ("m".equals(f)) {
            // 转发到修改页面
            request.getRequestDispatcher("/edit.jsp").forward(request, response);
        } else if ("d".equals(f)) {
            // 转发到详情页面
            request.getRequestDispatcher("/detail.jsp").forward(request, response);
        }*/

        String forward = "/" + request.getParameter("f") + ".jsp";
        request.getRequestDispatcher(forward).forward(request, response);
    }

    /**mc
     * 连接数据库，查询所有部门的信息，将部门信息收集好，然后跳转到JSP做页面展示
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void doList(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        // 准备一个容器，用来专门存储部门
        List<Dept> depts = new ArrayList<>();

        // 连接数据库，查询所有部门信息
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 获取连接
            conn = DBUtil.getConnection();
            // 执行SQL语句
            String sql = "select deptno, dname, loc from dept";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            // 遍历结果集
            while (rs.next()) {
                // 从结果集中取出
                String deptno = rs.getString("deptno");
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");

                // 将以上的零散的数据封装成Java对象
                Dept dept = new Dept();
                dept.setDeptno(deptno);
                dept.setDname(dname);
                dept.setLoc(loc);

                // 将部门对象放到list集合中
                depts.add(dept);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }

        // 将一个集合放到请求域中
        request.setAttribute("deptList", depts);
        // 转发（不要重定向）
        request.getRequestDispatcher("/list.jsp").forward(request, response);

    }
}
