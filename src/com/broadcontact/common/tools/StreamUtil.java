package com.broadcontact.common.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtil {

	/**
	 * ���췽��
	 * 
	 */
	public StreamUtil() {
		super();
	}

	/**
	 * ��InputStream�����ݿ�����OutputStream��ȥ
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