package com.cccuu.project.utils;

import org.apache.log4j.Logger;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 用户登录后台过滤器
 *
 * @author zhaixiaoliang
 * @author 2017年3月23日上午11:28:05
 * @Description
 */
public class SysLoginUserFilter implements Filter {
    Logger log = Logger.getLogger(SysLoginUserFilter.class);

    public void init(FilterConfig fConfig) throws ServletException {
    	log.info("--------LoginFilter init-----------");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        System.out.println("访问路径:" + req.getRequestURI());
        String urlStr = req.getRequestURI();
        if (urlStr.indexOf("toLogin") != -1 || urlStr.indexOf("doLogin") != -1 )
        {//直接放行的页面
            chain.doFilter(req, resp);
        } else {
            //登陆过滤
            HttpSession session = req.getSession();
            Object userObject = session.getAttribute(Constants.SYS_SESSION_USER);
            //用户没有登录
            if (userObject == null) {
                resp.sendRedirect(req.getContextPath() + "/toLogin.htm");
            } else {//已经登录
                chain.doFilter(req, resp);
            }
        }
    }

    /**
     * 通过HttpServletRequest返回IP地址
     *
     * @param request HttpServletRequest
     * @return ip String
     * @throws Exception
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        log.info("-------LoginFilter destroy----------");
    }

}
