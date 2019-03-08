package com.broadcontact.common.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ibm
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class EncodeFilter implements Filter {
	ServletContext sc;

	FilterConfig fc;

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		EncodeFilterRequestWrapper reqwrap = new EncodeFilterRequestWrapper(
				request, fc.getInitParameter("encode"));

		chain.doFilter(reqwrap, res);
	}

	public void init(FilterConfig filterConfig) {
		this.fc = filterConfig;
		this.sc = filterConfig.getServletContext();
	}

	public void destroy() {
		this.sc = null;
		this.fc = null;
	}

	private class EncodeFilterRequestWrapper extends HttpServletRequestWrapper {

		private String encode = "";

		private String queryStr = null;

		public EncodeFilterRequestWrapper(HttpServletRequest arg0) {
			super(arg0);
			queryStr = arg0.getQueryString();
		}

		public EncodeFilterRequestWrapper(HttpServletRequest arg0, String encode) {
			this(arg0);
			this.encode = encode;
		}

		public String getParameter(String arg0) {
			if (super.getParameter(arg0) == null)
				return null;
			String enc = super.getCharacterEncoding();
			try {
				return new String(super.getParameter(arg0).getBytes("ISO-8859-1"),
						encode);
			} catch (Exception e) {
				return super.getParameter(arg0);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.servlet.ServletRequest#getParameterValues(java.lang.String)
		 */
		public String[] getParameterValues(String arg0) {
			if (super.getParameterValues(arg0) == null)
				return null;
			String[] value = super.getParameterValues(arg0);
			String enc = super.getCharacterEncoding();
			try {
				for (int i = 0; i < value.length; i++)
					value[i] = new String(value[i].getBytes("ISO-8859-1"), encode);
			} catch (Exception e) {
			}
			return value;
		}

		public String getCharacterEncoding() {
			return encode;
		}

	}
}