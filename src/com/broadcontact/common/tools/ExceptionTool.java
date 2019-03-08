package com.broadcontact.common.tools;

public class ExceptionTool {

	public ExceptionTool() {
	}

	public static void show(Exception e) {
		System.out.println("----" + DateUtil.getNowTime()
				+ "--------------------------------------------------");
		e.printStackTrace(System.out);
	}

	public static void show(String msg, Exception e) {
		System.out.println("----" + DateUtil.getNowTime()
				+ "--------------------------------------------------");
		System.out.println("----" + msg);
		e.printStackTrace(System.out);
	}
}