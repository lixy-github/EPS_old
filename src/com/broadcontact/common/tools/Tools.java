package com.broadcontact.common.tools;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.math.RandomUtils;

public class Tools {

	public static String getSu(){
		return "yoda";
	}
	
	public static String processStatus(Long value) {
		if (value == null)
			return "δ֪";
		else if (value.longValue() == new Long(0).longValue())
			return "ע��";
		else if (value.longValue() == new Long(1).longValue())
			return "����";
		else if (value.longValue() == new Long(2).longValue())
			return "����";
		else if (value.longValue() == new Long(3).longValue())
			return "�ݻ�";
		else
			return "δ֪";
	}

	/**
	 * 
	 */
	public Tools() {
	}

	public static boolean parseBoolean(int value) {
		if (value == 1)
			return true;
		else
			return false;
	}

	public static int parseInt(boolean value) {
		if (value == true)
			return 1;
		else
			return 0;
	}

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description: �����ַ���ʽ��ת��
	 * </p>
	 * <p>
	 * Copyright: Copyright (c) 2003
	 * </p>
	 * 
	 * @version 1.0
	 */

	public static String getNowDate() {
		return (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());

	}

	/**
	 * ��ʽ������
	 * 
	 * @Param strDate
	 *          ����ʽ���ַ���(yyyyMMddHHmmss) (MM��ʾ�·�)
	 * @Param strFormat
	 *          ��ʽ (���ø�ʽΪyyyy yy mm dd hh HH mm ss) return value: if strDate is
	 *          null, return "", ���޷���ʽ����return strDate
	 */
	public static String formatDate(String strDate, String strFormat) {
		int index = 0;
		String s = "";

		if (strDate == null)
			return "";

		// �ж�strDate�Ƿ�����������ַ������� return strDate
		for (int i = 0; i < strDate.length(); i++) {
			if (!Character.isDigit(strDate.charAt(i)))
				return strDate;
		}

		index = strFormat.indexOf("yyyy");
		if (index >= 0) {
			s = strDate.substring(0, 4);
			strFormat = replace(strFormat, "yyyy", s);
		}

		index = strFormat.indexOf("yy");
		if (index >= 0) {
			s = strDate.substring(2, 4);
			strFormat = replace(strFormat, "yy", s);
		}

		index = strFormat.indexOf("MM");
		if (index >= 0) {
			s = strDate.substring(4, 6);
			strFormat = replace(strFormat, "MM", s);
		}

		index = strFormat.indexOf("dd");
		if (index >= 0) {
			s = strDate.substring(6, 8);
			strFormat = replace(strFormat, "dd", s);
		}

		// 24Сʱ��
		index = strFormat.indexOf("HH");
		if (index >= 0) {
			s = strDate.substring(8, 10);
			strFormat = replace(strFormat, "HH", s);
		}

		// 12Сʱ��
		index = strFormat.indexOf("hh");
		if (index >= 0) {
			s = strDate.substring(8, 10);
			if (Integer.parseInt(s) > 12) {
				s = Integer.toString(Integer.parseInt(s) - 12);
			}
			strFormat = replace(strFormat, "hh", s);
		}

		index = strFormat.indexOf("mm");
		if (index >= 0) {
			s = strDate.substring(10, 12);
			strFormat = replace(strFormat, "mm", s);
		}

		index = strFormat.indexOf("ss");
		if (index >= 0) {
			s = strDate.substring(12);
			strFormat = replace(strFormat, "ss", s);
		}

		return strFormat;
	}

	// ��str�е�����oldStr�滻ΪnewStr
	public static String replace(String str, String oldStr, String newStr) {
		if (str != null) {
			int index = 0;
			int oldLen = oldStr.length(); // oldStr�ַ�������
			if (oldLen <= 0)
				return str;
			int newLen = newStr.length(); // newStr�ַ�������

			while (true) {
				index = str.indexOf(oldStr, index);

				if (index == -1) {
					return str;
				} else {
					str = str.substring(0, index) + newStr
							+ str.substring(index + oldLen);
					index += newLen;
				}
			}
		} else {
			return "";
		}
	}

	// ��һ���ַ���ת��Ϊhtml��ʽ
	// �滻blank spaceΪ&nbsp; ����Ϊ<br>
	public static String strToHtml(String str, boolean supportHtml) {
		if (str == null) {
			return "";
		}

		str = replace(str, " ", "&nbsp;");
		str = replace(str, "\n", "<br>");

		if (supportHtml == false) {
			str = replace(str, "&", "&amp;");
			str = replace(str, "<", "&lt;");
			str = replace(str, ">", "&gt;");
		}
		return str;
	}

	public static String strToShow(String str, String showstr) {
		if (str == null) {
			return "";
		}
		// showstr="���󷴺���";
		str = replace(str, " ", "&nbsp;");
		str = replace(str, "\n", "<br>");

		if (showstr.equals("")) {
		} else {
			String repstr = "<font color=green><b>" + showstr + "</b></font>";
			str = replace(str, showstr, repstr);
		}

		return str;
	}

	// Ĭ��֧��html�﷨
	public static String strToHtml(String str) {
		return strToHtml(str, true);
	}

	// ȡweb ��·��
	public static String getWebDir(javax.servlet.ServletContext application) {
		String strWebDir = application.getRealPath("/");
		return strWebDir;
	}

	// ȡ·���ָ���"/" or "\"
	public static String getPathSplit(javax.servlet.ServletContext application) {
		String strWebDir = application.getRealPath("/");
		if (strWebDir.charAt(0) == '/')
			return "/";
		else
			return "\\";
	}

	// ȡ·���ָ���"/" or "\"
	public static String getSeparator() {
		Properties prop = new Properties(System.getProperties());
		return prop.getProperty("file.separator");
	}

	// if str==null return ""
	public static String processNull(String str) {
		return (str == null) ? "" : str;
	}

	public static String processNull(Date date) {
		return (date == null) ? "" : date.toString();
	}

	public static String processNull(Long value) {
		return (value == null) ? "" : value.toString();
	}

	public static String processNull(Object value) {
		return (value == null) ? "" : value.toString();
	}

	public static String processNull(float value){
		return (value == 0.0) ? "" : value+"";
	}
	
	public static String processNull(Float value){
		return (value == null) ? "" : String.valueOf(value.floatValue());
	}

	// if str==null or str=="" then return "&nbsp;"
	public static String processSpace(String str) {
		return (str == null) ? "&nbsp;" : str;
	}

	// �ַ���ת����������Ҫ������ַ����쳣ת��-1�Ĵ���
	public static int processInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return -1;
		}
	}

	// �ַ���ת����������Ҫ������ַ����쳣ת��-1�Ĵ���
	public static String processDate(Date date) {
		if (date == null)
			return "";
		try {
			SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return myDateFormat.format(date);
		} catch (Exception e) {
			return date.toString();
		}
	}

	// �ַ���ת����������Ҫ������ַ����쳣ת��-1�Ĵ���
	public static String processTime(Date date) {
		if (date == null)
			return "";
		try {
			SimpleDateFormat myDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			return myDateFormat.format(date);
		} catch (Exception e) {
			return date.toString();
		}
	}

	// �ַ���ת����������Ҫ������ַ����쳣ת��-1�Ĵ���
	public static Date formatDate(String date) {
		if (date == null)
			return null;
		try {
			SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return myDateFormat.parse(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static int getMinutes(Date date) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			return cal.get(Calendar.MINUTE) + cal.get(Calendar.HOUR) * 60
					+ cal.get(Calendar.DATE) * 1440;
		} catch (Exception e) {
			return 0;
		}
	}

	// �ַ���ת����������Ҫ������ַ����쳣ת��-1�Ĵ���
	public static Date formatDateTime(String date) {
		if (date == null)
			return null;
		try {
			SimpleDateFormat myDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			return myDateFormat.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// �ַ���ת����������Ҫ������ַ����쳣ת��-1�Ĵ���
	public static String processLong(Long date) {
		if (date == null)
			return "";
		try {
			return date + "";
		} catch (Exception e) {
			return date.toString();
		}
	}

	// �ַ���ת����������Ҫ������ַ����쳣ת��-1�Ĵ���
	public static Long processLong(String str) {
		try {
			return new Long(str);
		} catch (Exception e) {
			return new Long(-1);
		}
	}

	public static Float processFloat(String str) {
		try {
			return Float.valueOf(str);
		} catch (Exception e) {
			return null;
		}
	}

	// ��ʽ��Ǯ��
	// @Param money is need format decimal
	// the format is ###,###.00
	public static String getMoney(double money) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("###,##0.00");
		return df.format(money);
	}

	// �������뺯��
	public static double round(double from, int num) {
		if (num < 1)
			return from;
		BigDecimal b = new BigDecimal(from);
		double to = b.setScale(num, BigDecimal.ROUND_HALF_UP).doubleValue();
		return to;
	}

	// ȡ���ַ�������ʵ���ȣ�����2�ַ���Ӣ��1�ַ�
	public static int getStrLen(String instr) {
		if (instr == null)
			return 0;
		int Num = 0, i = 0;
		char chr = ' ';
		for (i = 0; i < instr.length(); i++) {
			chr = instr.charAt(i);
			if ((int) chr <= (int) '~') // �ж��Ƿ�˫�ֽ�
				Num += 1;
			else
				Num += 2;
		}
		return Num;
	}

	// isoת��ΪGbk
	public static String isoToGBK(String src) {
		String sDst = "";
		try {
			sDst = new String(src.getBytes("ISO8859_1"), "GBK");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return sDst;
	}

	// gbkת��Ϊiso
	public static String gbkToISO(String src) {
		String sDst = "";
		try {
			sDst = new String(src.getBytes("GBK"), "ISO8859_1");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return sDst;
	}

  public static String getRandom(int size){
    String s="";
    String[] str={"1","2","3","4","5","6","7","8","9","q","w","e","r","t","y","u","p","a","s","d","f","g","h","j","k","z","x","c","v","b","n","m"} ;
    for(int i=0;i<size;i++){
      int a=RandomUtils.nextInt()%32;
      s+=str[a];
    }
    return s;
  }

public static String encode4URL(String s) {
	  if (s == null || s.trim().length() == 0) {
          return "";
      }
      if (s.indexOf("=") < 0) {
          return s;
      }

      StringBuffer url = new StringBuffer();

      try {
          String[] temS = s.split("&");
          for (int i = 0; i < temS.length; i++) {
              String tem = temS[i].toString();
              if (tem == null || tem.trim().length() == 0) {
                  continue;
              }
              String[] ss = tem.split("=");
              if (ss.length == 2)
                  url.append(ss[0].toString()).append("=").append(
                          URLEncoder.encode(ss[1].toString(), "utf-8"))
                          .append("&");
              else
                  url.append(ss[0].toString()).append("=").append("&");
          }
      } catch (UnsupportedEncodingException e) {
          return s;
      }
      return url.toString();
}
}