package com.broadcontact.clothing.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PublicUtils {

	/**
	 * 获取Ip地址
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
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

	/**
	 * 检验图片验证码 是否正确
	 * 
	 * @param session
	 * @param imgCodeByUser
	 * @return
	 */
	public static Map<String, Object> checkImgCode(HttpSession session, String imgCodeByUser) {
		Map<String, Object> map = new HashMap<String, Object>();

		String imgCode = (String) session.getAttribute("imgCode");
		String msg = "";
		boolean flag = true;
		if (!imgCodeByUser.equalsIgnoreCase(imgCode)) {
			msg = "图片验证码不正确";
			flag = false;
		}
		map.put("msg", msg);
		map.put("flag", flag);
		return map;
	}

	/**
	 * 
	 * @param session
	 * @param emailCodeByUser
	 *            用户填写的邮箱验证码
	 * @param emailByUser
	 *            用户填写的邮箱
	 * @return
	 */
	public static Map<String, Object> checkEmail(HttpSession session, String emailCodeByUser, String emailByUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		boolean flag = true;
		Long lastTime = (Long) session.getAttribute("sendEmailCodeTime");
		Long nowTime = new Date().getTime();
		if (lastTime == null) {
			msg = "请先获取验证码";
			flag = false;
		} else {
			if ((nowTime - lastTime) >= 10 * 60 * 1000) {
				msg = "10分钟之内未获取验证码,重新获取";
				flag = false;
			} else {
				String emailCode = (String) session.getAttribute("emailCode");
				String email = (String) session.getAttribute("email");
				if (!emailCodeByUser.equals(emailCode)) {
					msg = "验证码错误,请核对";
					flag = false;
				} else {
					if (!emailByUser.equals(email)) {
						msg = "验证码发送邮箱与当前填写邮箱不匹配";
						flag = false;
					}
				}

			}
		}
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 验证验证码是否正确
	 * 
	 * @param session
	 * @param msg
	 * @param telCodeByUser
	 *            用户填写的验证码
	 * @param telByUser
	 *            用户填写的电话
	 * @return
	 */
	public static Map<String, Object> checkTelCode(HttpSession session, String telCodeByUser, String telByUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		boolean flag = true;
		Long lastTime = (Long) session.getAttribute("sendTelCodeTime");// 获取发送验证码的时间
		Long nowTime = new Date().getTime();
		if (lastTime == null) {
			msg = "请先获取验证码";
			flag = false;
		} else {
			if ((nowTime - lastTime) >= 10 * 60 * 1000) {
				msg = "10分钟之内未获取验证码,重新获取";
				flag = false;
			} else {
				String telCode = (String) session.getAttribute("telCode");
				String tel = (String) session.getAttribute("tel");

				if (!telCode.equals(telCodeByUser)) {
					msg = "验证码错误,请核对";
					flag = false;
				} else {
					// 验证码正确后 检验手机是否一致
					if (!tel.equals(telByUser)) {
						msg = "验证码发送手机与当前填写手机不匹配";
						flag = false;
					}
				}
			}
		}
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 读取配置文件
	 * 
	 * @param request
	 * @param msg
	 * @param retVal
	 * @param configName
	 *            要读取的文件名
	 * @return
	 */
	public static Map<String, Object> readConfig(HttpServletRequest request, String configName) {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		int retVal = 0;
		String filename = request.getRealPath("") + File.separator + "WEB-INF" + File.separator + "classes"
				+ File.separator + configName;
		Properties prop = new Properties();
		try {
			File fp = new File(filename);
			if (!fp.exists()) {
				msg = "读取不到配置文件" + filename;
				retVal = -1;
			}
			prop.load(new FileInputStream(fp));
			fp = null;
		} catch (Exception e) {
			msg = "读取文件失败";
			retVal = -1;
		}
		map.put("msg", msg);
		map.put("prop", prop);
		map.put("retVal", retVal);

		return map;
	}

	/**
	 * 读取html
	 * 
	 * @param path
	 *            读取的html模板的路径
	 * @return
	 */
	public static String readHtml(String path) {
		File file = new File(path);

		InputStream input = null;
		try {
			input = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		StringBuffer buffer = new StringBuffer();
		byte[] bytes = new byte[1024];
		try {
			for (int n; (n = input.read(bytes)) != -1;) {
				buffer.append(new String(bytes, 0, n, "UTF-8"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		String html = buffer.toString();
		String start = "<body>";
		String end = "</body>";
		int s = html.indexOf(start) + start.length();
		int e = html.indexOf(end);
		html = html.substring(s, e);
		return html;
	}
}
