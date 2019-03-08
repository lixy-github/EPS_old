package com.broadcontact.common.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	/**
	 * Encodes a string
	 * 
	 * @param str
	 *          String to encode
	 * @return Encoded String
	 * @throws NoSuchAlgorithmException
	 */
	public static String crypt(String str) throws NoSuchAlgorithmException {
		if (str == null || str.length() == 0) {
			throw new IllegalArgumentException(
					"String to encript cannot be null or zero length");
		}

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(str.getBytes());
		byte[] hash = md.digest();
		StringBuffer hexString = new StringBuffer();

		for (int i = 0; i < hash.length; i++) {
			if ((0xff & hash[i]) < 0x10) {
				hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
			} else {
				hexString.append(Integer.toHexString(0xFF & hash[i]));
			}
		}

		return hexString.toString();
	}

	public static void main(String[] args) {
		try {
			String a = "地区.地区1.dfd.s";
			String aa = a.replaceAll(".地区[0-9]+", ".地区22");
			System.out.println(aa);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
