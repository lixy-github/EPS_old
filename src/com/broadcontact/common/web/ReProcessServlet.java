package com.broadcontact.common.web;


import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

public class ReProcessServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ReProcessServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        HttpSession session = request.getSession();
   	    HttpServletRequest _req=(HttpServletRequest)session.getAttribute("_FROM_HREF_URL_");
        if(_req != null) {
        	//session.removeAttribute("_FROM_HREF_URL_");
        	RequestDispatcher dispatcher = request.getRequestDispatcher(_req.getRequestURI());
        	dispatcher.forward(_req, response);
        	response.addHeader("URI",_req.getRequestURI());
        }
        else {
        	response.sendRedirect("/");
        }
	}
}
