/******************************************************************************
 * $Module: EncFormRequestWrapper.java $
 * $Revision: 1.1 $
 * $Author: wusy $
 * $Date: 2018/01/05 06:35:15 $
 *
 ******************************************************************************
 * Copyright (C) 2006, HangZhou BroadContact Biz. Software Co. Ltd.
 * All rights reserved.
 *****************************************************************************/
package com.broadcontact.common.web;

import java.io.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class EncFormRequestWrapper extends HttpServletRequestWrapper implements
		HttpServletRequest {

	private File fdFile;
	private byte[] bkContent;
	private ServletInputStream sis;
	private HttpSession session;
	private String uri; 

	public String getRequestURI() {
		return uri;
	}

	/**
	 * @param req
	 */
	public EncFormRequestWrapper(HttpServletRequest req) {
		super(req);
		uri = req.getRequestURI();
		if ((req.getHeader("Content-Type")!=null)&&(req.getHeader("Content-Type").startsWith("multipart/form-data"))) {
			FileOutputStream out=null;
			try {
				fdFile = File.createTempFile("webgov",".tmp");
				out = new FileOutputStream(fdFile);
			    int m_totalBytes = req.getContentLength();
			    int totalRead = 0;
			    ServletInputStream in = req.getInputStream();
			    while (totalRead < m_totalBytes) {
			    	int b = in.read();
			    	if (b == -1) break;
			    	out.write(b);
			    	totalRead++;
			    }
			    out.flush();
			    out.close();
			}
			catch (IOException ioe) {
				try {
					if (out!=null) out.close();
					if (fdFile!=null) {
						fdFile.delete();
						fdFile = null;
					}
				} catch (IOException e) {
				}
			}
		}
		else {
		    int m_totalBytes = req.getContentLength();
		    if (m_totalBytes<0) m_totalBytes=0;
		    bkContent = new byte[m_totalBytes];
		    int totalRead = 0;
		    int readBytes = 0;
		    for (; totalRead < m_totalBytes; totalRead += readBytes)
		      try {
		        readBytes = req.getInputStream().read(bkContent, totalRead, m_totalBytes - totalRead);
		        if (readBytes == -1) break;
		      } catch (Exception e) {
		      }
		}
	}

	public ServletInputStream getInputStream() throws IOException {
		if (sis == null) {
			if ((fdFile!=null)&&(fdFile.exists())) {
				sis = new EncFormInputStream(new FileInputStream(fdFile));
			}
			else
				sis = new EncFormInputStream(new ByteArrayInputStream(bkContent));
		}
		return sis;
	}
	
	public void setSession(HttpSession session) {
		this.session = session;
	}

	public HttpSession getSession() {
		if (this.session != null)
			return this.session;
		else
			return super.getSession();
	}

	public HttpSession getSession(boolean create) {
		if (this.session != null)
			return this.session;
		else
			return super.getSession(create);
	}
	
	protected void finalize() throws Throwable {
		super.finalize();
		if (sis != null) sis.close();
		if ((fdFile!=null)&&(fdFile.exists())) {
			if (!fdFile.delete()) {
				fdFile.deleteOnExit();
			}
		}
	}
	
	private class EncFormInputStream extends ServletInputStream {
		
		private InputStream is;
		
		public EncFormInputStream(InputStream in) {
			this.is = in;
		}
		/* (non-Javadoc)
		 * @see java.io.InputStream#read()
		 */
		public int read() throws IOException {
			return is.read();
		}
		
		public int available() throws IOException {
			return is.available();
		}

		public void close() throws IOException {
			is.close();
		}
		
		public synchronized void mark(int readlimit) {
			is.mark(readlimit);
		}

		public boolean markSupported() {
			return is.markSupported();
		}

		public int read(byte[] b, int off, int len) throws IOException {
			return is.read(b, off, len);
		}

		public int read(byte[] b) throws IOException {
			return is.read(b);
		}

		public synchronized void reset() throws IOException {
			is.reset();
		}

		public long skip(long n) throws IOException {
			return is.skip(n);
		}
	}
}
