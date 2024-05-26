package com.feiye.oa.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LoginCheckFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        /**
         * 用户访问 index.jsp 时不能拦截
         * 用户已经登录了，需要放行，不能拦截
         * 用户要去登录，不能拦截
         * WelcomeServlet不能拦截
         */

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        // 获取请求路径
        String servletPath = request.getServletPath();

        HttpSession session = request.getSession(false);
        if ("/index.jsp".equals(servletPath) || "/welcome".equals(servletPath) ||
                "/user/login".equals(servletPath) || "/user/exit".equals(servletPath) ||
                session != null && session.getAttribute("user") != null) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
