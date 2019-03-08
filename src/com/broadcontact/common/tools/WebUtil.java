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
	 * ����ҳ�治��Cache
	 * 
	 * @param response
	 */
	public static void setNoCache(HttpServletResponse response) {
		if (response == null)
			return;
		// ��ֹ��proxy server cache
		response.setHeader("Cache-Control", "no-store"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0);
	}

	/**
	 * ����Զ���ļ������ط�����
	 * 
	 * @param localFileName
	 *          �����ļ���
	 * @param remoteFileUrl
	 *          Զ���ļ�URL
	 * @return �����ļ���Mime����
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
	 * ��URL���б���. ��Ҫ��Ϊ�˼��ݲ�ͬ�İ汾,ͳһ����,���ԲŶ����˸÷��� �÷�����ʱ����URLEncoder.encode()
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
	 * ��URL���н��� ��Ҫ��Ϊ�˼��ݲ�ͬ�İ汾,ͳһ����,���ԲŶ����˸÷��� �÷�����ʱ����URLDecoder.decode()
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
	 * ��html�ı�����ȡ������html��ǵ��ı�����
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
				+ " border=0 cellPadding=0 cellSpacing=0 width=\"80%\"><TBODY><TR><TD>��"
				+ "�����ݹ�ͨ����������޹�˾Ϊ�������˹�������һ���๦�ܡ�����������ȫ��Ч�ĵ�����"
				+ "��ƽ̨������һ��������ҵ������������Ϣ�Ĳɼ������������ͷ������ƣ�ʹ������Ч��"
				+ "������������ߣ�ͬʱ�γ�����ʡ��ͨ�����н�ͨ�֡������ء�������ص�λ�����������ŵĵ�"
				+ "�ӹ�����ת��ϵ����������δ�����������硢�����ִ���������������������ϵ������Ӧ����"
				+ "���á��칫��������ỷ�����Ϸ�չ�仯����Ҫ��Ϊ�������ṩ���ӷ��㡢��ݡ���ƽ����"
				+ "���ķ������������칫�ִ�������Ϣ��Դ�����������绯�;��߿�ѧ����<P>��"
				+ "����ϵͳ�������������˹ܵ������Ż���ҵ��ϵͳ������ҵ���ѯ/�����칫�Զ�"
				+ "���ȶ����ϵͳ����������������ר���Ͱ칫�Զ��������Ӧ������л����ɣ�Ϊ��"
				+ "���Ұ�Ĺ�����Ա��������ؿ�չ������ṩһ�����ɵ���Ϣϵͳ֧��ƽ̨��</P>"
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