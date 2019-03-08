package com.broadcontact.common.web;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.*;
import javax.servlet.http.*;

import com.broadcontact.common.tools.Tools;
public class LoginFilter implements Filter{

    protected FilterConfig filterConfig;
    private String loginPage;
    private String sessionAttr;

    public LoginFilter(){
    }

    public void init(FilterConfig arg0)
        throws ServletException{
        filterConfig = arg0;
        loginPage = filterConfig.getInitParameter("loginPage");
        sessionAttr = filterConfig.getInitParameter("sessionAttr");
    }

    public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
        throws IOException, ServletException{
        HttpServletRequest request = (HttpServletRequest)srequest;
        HttpServletResponse response = (HttpServletResponse)sresponse;
        HttpSession session = request.getSession();
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
		try {
			if (Tools.processNull(loginPage).equals("")) {
				if (session.getAttribute(sessionAttr) == null) {
					Principal user = request.getUserPrincipal();
					if (user == null) {
						response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					}
				} else {
					filterChain.doFilter(srequest, sresponse);
				}
			} else {
				String lp = loginPage;
				if (!requestURI.equals(lp)) {
					if (session.getAttribute(sessionAttr) == null) {
						String requestType = request.getHeader("X-Requested-With");
						if(requestType != null && "XMLHttpRequest".equalsIgnoreCase(requestType.trim())) {
		        			 //ajax请求
							response.setHeader("sessionStatus", "timeout");
		        			response.sendError(601, "session timeout");
		        		}else{
		        			response.sendRedirect(lp);
		        		}
						
					} else {
						filterChain.doFilter(srequest, sresponse);
					}
				} else {
					filterChain.doFilter(srequest, sresponse);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void destroy(){
        filterConfig = null;
    }
}