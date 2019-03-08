package com.broadcontact.common.tools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Win32Utils {
	public static String getEnv(String envName) throws Exception {
		return theOneInstance.doGetEnv(envName);
	}

	private static final String SET_CMD;
	static {
		String osName = System.getProperty("os.name");
		if ((osName.indexOf("NT") != -1) || (osName.indexOf("200") != -1)
				|| (osName.indexOf("XP") != -1))
			SET_CMD = "cmd /c set";
		else
			// Windows
			// 9x/ME
			SET_CMD = "command /c set";
	}

	private static Win32Utils theOneInstance = new Win32Utils();

	private Win32Utils() {
	}

	private String doGetEnv(String envName) throws Exception {
		Process p = Runtime.getRuntime().exec(SET_CMD);
		StreamPumper outPump = new StreamPumper(p.getInputStream());
		StreamPumper errPump = new StreamPumper(p.getErrorStream());
		outPump.start();
		errPump.start();
		p.waitFor();
		outPump.join();
		errPump.join();
		return parseLines(envName, outPump.getLineList());
	}

	private String parseLines(String envName, List lineList) {
		for (Iterator iterator = lineList.iterator(); iterator.hasNext();) {
			String line = (String) iterator.next();
			String[] elmArray = line.split("=");
			if (elmArray[0].equalsIgnoreCase(envName))
				return elmArray[1];
		}
		return null;
	}

	private class StreamPumper extends Thread {
		private InputStream is;

		private List lineList = new ArrayList();

		StreamPumper(InputStream is) {
			this.is = is;
		}

		public void run() {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(is,
						"ISO-8859-1"));
				String line;
				while ((line = br.readLine()) != null)
					lineList.add(line);
			} catch (Exception e) {
			}
		}

		List getLineList() {
			return lineList;
		}
	}
}