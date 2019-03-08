package com.broadcontact.common.tools;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

public class TextUtil {

	public TextUtil() {
	}

	/**
	 * 把一个字符串按给定的长度换成多行字符串(换行符为 <br>)
	 * 
	 * @param text
	 *          要转换的字符串
	 * @param lineLen
	 *          行长度
	 * @param lineTag
	 *          换行标记
	 * @return
	 */
	public static String convLine(String text, int lineLen, String lineTag) {
		String tmpstr = "";
		int count = 0;
		int i = 1;
		int strlen = text.length();
		count = strlen / lineLen; // 得到行数
		try {
			if (count > 0) {
				tmpstr = text.substring(0, lineLen);
			} else {
				tmpstr = text;
			}
			for (i = 1; i < count; i++) {
				tmpstr += lineTag + text.substring(i * lineLen, (i + 1) * lineLen);
			}
			tmpstr += lineTag + text.substring(i * lineLen);
		} catch (Exception e) {
			tmpstr = text;
		}
		return tmpstr;
	}

	/**
	 * 把str中的oldStr换成newStr, modi by liudh to improve performance
	 * 
	 * @param str
	 * @param oldStr
	 * @param newStr
	 * @return
	 */
	public static String replace(String str, String oldStr, String newStr) {
		if (str == null)
			return "";
		if (oldStr == null || oldStr.equals(""))
			return str;
		if (newStr == null)
			return str;
		StringBuffer relstr = new StringBuffer();
		int index = str.indexOf(oldStr);
		if (index == -1)
			return str;
		int oldidx = 0;
		while (index >= 0) {
			relstr.append(str.substring(oldidx, index));
			relstr.append(newStr);
			// str= str.substring(index + oldStr.length());
			oldidx = index + oldStr.length();
			index = str.indexOf(oldStr, index + oldStr.length());
		}
		relstr.append(str.substring(oldidx));
		return relstr.toString();
	}

	/**
	 * 将标准字符串转化为xml的转义字符串 use replace
	 */
	public static String getXmlStr(String standardStr) {
		standardStr = replace(standardStr, "&", "&amp;");
		standardStr = replace(standardStr, "<", "&lt;");
		standardStr = replace(standardStr, ">", "&gt;");
		standardStr = replace(standardStr, "\"", "&quot;");
		standardStr = replace(standardStr, "'", "&apos;");
		return standardStr;
	}

	/**
	 * 将xml的转义字符串转化为标准字符串 use replace
	 */
	public static String getStandardStr(String xmlStr) {
		xmlStr = replace(xmlStr, "&amp;", "&");
		xmlStr = replace(xmlStr, "&lt;", "<");
		xmlStr = replace(xmlStr, "&gt;", ">");
		xmlStr = replace(xmlStr, "&quot;", "\"");
		xmlStr = replace(xmlStr, "&apos;", "'");
		return xmlStr;
	}

	public static String getValidString(String str) {
		if (str == null) {
			str = "";
		}
		return str;
	}

	/**
	 * 字符串分组函数，返回一个字符串按给定字符串分割后返回的数组。 (从别的项目拷贝过来的，算法太差，找个时间重写)
	 * 
	 * @param s
	 * @param s1
	 * @return
	 */
	public static String[] split(String src, String delimit) {
		if (src == null)
			return null;
		if (src.equals(""))
			return new String[0];
		String[] result = null;
		if (delimit == null || delimit.equals("")) {
			result = new String[1];
			result[0] = src;
			return result;
		}
		List rel = new LinkedList();
		int len = delimit.length();
		int i = src.indexOf(delimit);
		String tmp = null;
		while (i >= 0) {
			tmp = src.substring(0, i);
			rel.add(tmp);
			src = src.substring(i + len);
			i = src.indexOf(delimit);
		}
		rel.add(src);
		result = new String[rel.size()];
		rel.toArray(result);
		return result;
	}

	/**
	 * 分割字符串，如果分割出的字符串中含有无效字符(空字符或空格),则不返回
	 * 
	 * @param src
	 * @param delimit
	 * @return
	 */
	public static String[] trimSplit(String src, String delimit) {
		if (src == null)
			return null;
		if (src.equals(""))
			return new String[0];
		String[] result = null;
		if (delimit == null || delimit.equals("")) {
			result = new String[1];
			result[0] = src;
			return result;
		}
		List rel = new LinkedList();
		int len = delimit.length();
		int i = src.indexOf(delimit);
		String tmp = null;
		while (i >= 0) {
			tmp = src.substring(0, i);
			if (!"".equals(tmp.trim()))
				rel.add(tmp);
			src = src.substring(i + len);
			i = src.indexOf(delimit);
		}
		if (!"".equals(src.trim()))
			rel.add(src);
		result = new String[rel.size()];
		rel.toArray(result);
		return result;
	}

	/**
	 * 判断一个字符串是否为空字符
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null)
			return true;
		if (str.trim().equals(""))
			return true;
		return false;
	}

	/**
	 * 判断一个字符串是否不为空字符
	 * 
	 * @param str
	 * @return
	 */
	public static boolean notEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 转换一个可能是null的String,若是null就返回一个""，为了避免出现空指针的异常
	 * 
	 * @param str
	 * @return
	 */
	public static String notNull(String str) {
		return (str == null ? "" : str);
	}

	/**
	 * 把null或者空字符换成Html的空格
	 * 
	 * @param str
	 * @return
	 */
	public static String empty2space(String str) {
		if (isEmpty(str))
			return "&nbsp;";
		return str;
	}

	/**
	 * parse a string to int
	 */
	public static int parseInt(String str) {
		if (str == null || str.length() == 0)
			str = "-1";
		return Integer.parseInt(str);
	}

	/**
	 * Create A String by chr of count
	 * 
	 * @param chr
	 * @param count
	 * @return
	 */
	public static String createChars(String chr, int count) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < count; i++) {
			sb.append(chr);
		}
		return sb.toString();
	}

	/**
	 * 对字符串进行编码
	 * 
	 * @param str
	 * @return
	 */
	public static String encode(String str) {
		// return URLEncoder.encode(str);
		if (str == null)
			return null;
		try {
			return new String(str.getBytes("GB2312"), "ISO8859_1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return str;
		}
	}

	/**
	 * 对字符串进行解码
	 * 
	 * @param str
	 * @return
	 */
	public static String decode(String str) {
		// return URLDecoder.decode(str);
		if (str == null)
			return null;
		try {
			return new String(str.getBytes("ISO8859_1"), "GB2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return str;
		}
	}

	public static void main(String[] args) {
		System.out.println(Arith.round(22.245, 2));
	}
}