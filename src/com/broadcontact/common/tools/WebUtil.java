package com.broadcontact.common.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

public class WebUtil {

	public WebUtil() {
	}

	/**
	 * 设置页面不可Cache
	 * 
	 * @param response
	 */
	public static void setNoCache(HttpServletResponse response) {
		if (response == null)
			return;
		// 防止被proxy server cache
		response.setHeader("Cache-Control", "no-store"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0);
	}

	/**
	 * 保存远程文件到本地服务器
	 * 
	 * @param localFileName
	 *          本地文件名
	 * @param remoteFileUrl
	 *          远程文件URL
	 * @return 返回文件的Mime类型
	 */
	public static String saveRemoteFile(String localFileName, String remoteFileUrl)
			throws MalformedURLException, FileNotFoundException, IOException {

		URL url = null; // url
		FileOutputStream fos = null; // OutputStream
		String mime = "";
		try {
			url = new URL(remoteFileUrl);
		} catch (MalformedURLException e) {
			throw e;
		}
		try {
			fos = new FileOutputStream(new File(localFileName));
		} catch (FileNotFoundException e1) {
			if (fos != null)
				fos.close();
			throw e1;
		}
		try {
			URLConnection urlConn = url.openConnection();
			mime = urlConn.getContentType();
			InputStream is = urlConn.getInputStream();
			StreamUtil.copyStream(is, fos, true);
		} catch (IOException e2) {
			throw e2;
		} finally {
			if (fos != null)
				fos.close();
		}
		return mime;
	}

	/**
	 * 对URL进行编码. 主要是为了兼容不同的版本,统一控制,所以才定义了该方法 该方法暂时调用URLEncoder.encode()
	 * 
	 * @param url
	 * @return
	 */
	public static String URLEncode(String url) {
		try {
			return URLEncoder.encode(url, null);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	/**
	 * 对URL进行解码 主要是为了兼容不同的版本,统一控制,所以才定义了该方法 该方法暂时调用URLDecoder.decode()
	 * 
	 * @param url
	 * @return
	 */
	public static String URLDecode(String url) {
		try {
			return URLDecoder.decode(url, null);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	/**
	 * 从html文本中提取不包含html标记的文本内容
	 * 
	 * @param html
	 * @return String
	 */
	public static String htmlToText(String html) {
		StringBuffer sb = new StringBuffer();
		boolean mark = false;
		int start = 0;
		int pos = 0;
		for (; pos < html.length(); pos++) {
			switch (html.charAt(pos)) {
			case '<':
				if (!mark) {
					sb.append(html.substring(start, pos));
					start = pos;
					mark = true;
					continue;
				}
			case '>':
				if (mark) {
					start = pos + 1;
					mark = false;
					continue;
				}
			}
		}
		if (start < pos - 1)
			sb.append(html.substring(start, pos));
		return sb.toString().trim();
	}

	public static String escapceWebSpace(String text) {
		return TextUtil.replace(text, "&nbsp;", "");
	}

	public static void main(String[] args) {
		String html = "<TABLE border=0 cellPadding=0 cellSpacing=0 width=613>"
				+ "<TBODY><TR><TD><IMG height=43 src=\"/cases/img/case3_sxyg.jpg\" width=613>"
				+ "</TD></TR><TR><TD align=middle vAlign=center><P>&nbsp;</P><TABLE align=center"
				+ " border=0 cellPadding=0 cellSpacing=0 width=\"80%\"><TBODY><TR><TD>　"
				+ "　杭州广通商用软件有限公司为绍兴县运管所构筑一个多功能、大容量、安全高效的电子政"
				+ "务平台，建立一套完整的业务、行政管理信息的采集、处理、交换和发布机制，使工作的效率"
				+ "和质量得以提高，同时形成上联省交通厅、市交通局、下联县、区及相关单位，横联各部门的电"
				+ "子公文流转体系，建立面向未来、面向世界、面向现代化的网上审批运作新体系，以适应国民"
				+ "经济、办公条件和社会环境不断发展变化的需要，为社会各界提供更加方便、快捷、公平、公"
				+ "正的服务。真正做到办公现代化、信息资源化、传输网络化和决策科学化。<P>　"
				+ "　本系统包含了绍兴县运管的政府门户、业务系统、在线业务查询/受理、办公自动"
				+ "化等多个子系统，把内网、外网、专网和办公自动化软件等应用软件有机集成，为各"
				+ "处室办的工作人员方便自如地开展各项工作提供一个集成的信息系统支撑平台。</P>"
				+ "</TD></TR></TBODY></TABLE><P>&nbsp;</P><P>&nbsp;"
				+ "</P></TD></TR></TBODY></TABLE>";
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			// System.out.println();
			escapceWebSpace(htmlToText(html));
		}

		System.out.println((System.currentTimeMillis() - start) / 1000.0);
		// try {
		// long s = System.currentTimeMillis();
		// saveRemoteFile(
		// "D:/testurl.gif",
		// "http://www.google.com/intl/zh-CN_ALL/images/logo.gif");
		// System.out.println(System.currentTimeMillis() - s);
		// } catch (MalformedURLException e) {
		// e.printStackTrace();
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

	}
}