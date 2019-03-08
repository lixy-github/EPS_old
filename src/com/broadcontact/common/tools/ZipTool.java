package com.broadcontact.common.tools;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipTool {
	static String zipName;

	private String ZIPUSER;

	private String ZIPPASS;

	static String TEST = "asd\n asdasd\na";

	static private String fileName;

	static byte[] buffer = new byte[1024];

	static int bytesRead;

	static String entryName;

	public static void main(String[] args) throws IOException {
		args = new String[3];
		args[0] = "c";
		args[1] = "156213.zip";
		args[2] = "156213.bmp";
		boolean HELP = false;
		if (args.length == 0) {
			help();
			System.exit(0);
		}
		if (args.length > 2 && args[0].equals("c")) {
			HELP = true;
			zipName = args[1];

			try {
				ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(zipName));
				for (int i = 2; i < args.length; i++) {
					fileName = args[i];

					FileInputStream file = new FileInputStream(fileName);
					ZipEntry entry = new ZipEntry(fileName);
					zip.putNextEntry(entry);

					while ((bytesRead = file.read(buffer)) != -1)
						zip.write(buffer, 0, bytesRead);
					/*
					 * buffer=TEST.getBytes(); bytesRead=buffer.length;
					 * zip.write(buffer,0,bytesRead);
					 */
					System.out.println(fileName + " added.");
					file.close();
				}
				zip.close();
				System.out.println(zipName + " created.");
			} catch (IOException ioe) {
				System.out.println(ioe);
			}
		}

		// ½âÑ¹£¬¶ÁÑ¹ËõÎÄ¼þ
		if (args.length == 3 && args[0].equals("x")) {
			HELP = true;
			zipName = args[1];
			entryName = args[2];
			try {
				ZipFile zip = new ZipFile(zipName);
				ZipEntry entry = zip.getEntry(entryName);
				if (entry != null) {
					InputStream entryStream = zip.getInputStream(entry);
					FileOutputStream file = new FileOutputStream(entry.getName());
					while ((bytesRead = entryStream.read(buffer)) != -1)
						// System.out.println(new String(buffer)+" "+bytesRead);
						file.write(buffer, 0, bytesRead);
					System.out.println(entry.getName() + " extracted.");
					file.close();
					entryStream.close();
				} else
					System.out.println(entryName + " not found.");
				zip.close();
			} catch (IOException ioe) {
				System.out.println(ioe);
			}
		}

		if (args.length == 2 && args[0].equals("l")) {
			HELP = true;
			try {
				ZipFile zip = new ZipFile(args[1]);

				for (Enumeration list = zip.entries(); list.hasMoreElements();) {
					ZipEntry entry = (ZipEntry) list.nextElement();
					System.out.println(entry.getName());
				}
				zip.close();
			} catch (IOException ioe) {
				System.out.println(ioe);
			}
		}

		if (!HELP)
			help();
	}

	private static void help() {
		System.out.println("Usage:ZipTool<command><zip_file>[<file>...]");
	}
}