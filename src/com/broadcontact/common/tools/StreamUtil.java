package com.broadcontact.common.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtil {

	/**
	 * 构造方法
	 * 
	 */
	public StreamUtil() {
		super();
	}

	/**
	 * 把InputStream的数据拷贝到OutputStream中去
	 * 
	 * @param is
	 *          InputStream
	 * @param os
	 *          OutputStream
	 * @param close
	 *          copy finish is close the OutputStream ?
	 * @throws IOException
	 */
	public static void copyStream(InputStream is, OutputStream os, boolean close)
			throws IOException {
		int b;
		while ((b = is.read()) != -1) {
			os.write(b);
		}
		is.close();
		if (close)
			os.close();
	}

}