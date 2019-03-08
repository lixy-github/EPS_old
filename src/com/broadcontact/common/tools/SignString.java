package com.broadcontact.common.tools;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

/**
 * 这个类主要用于对目标文件进行消息摘要并签名确认，以保证数据在传输的过程中不会被篡改（完整性） 并且保证不会被第三方冒名发送文件。
 * 大致流程：由于签名类Signature可以在计算签名时采用(MD5withRSA）的组合方式，所以可以将消息摘要及
 * 数字签名一并完成。生成后的签名文件存在file/sign下。
 */
public class SignString {

	public byte[] sign(String privateKeyPath, String signString) {
		boolean flag = false;
		RSAG rsaGen = new RSAG();
		// 获取私钥对象
		PrivateKey privateKey = rsaGen.getPrivateKey(privateKeyPath);
		Signature signer = null;
		try {
			signer = Signature.getInstance("SHA1withRSA");
			signer.initSign(privateKey);
		} catch (NoSuchAlgorithmException noAlgorithm) {
			System.out.println(noAlgorithm);
		} catch (InvalidKeyException badKey) {
			System.out.println(badKey);
		}
		byte[] fileData = signString.getBytes();
		for (int i = 0; i < fileData.length; i++) {
			try {
				signer.update(fileData[i]);
			} catch (SignatureException signError) {
				System.out.println(signError);
			}
		}
		byte[] theSignature = null;
		try {
			theSignature = signer.sign();
		} catch (SignatureException signError) {
			System.out.println(signError);
		}
		return theSignature;
	}

	public boolean verifySignature(String publicKeyName, String signString,
			byte[] signCode) {
		RSAG rsaGen = new RSAG();
		// 获取公钥对象
		PublicKey publicKey = rsaGen.getPublicKey(publicKeyName);
		Signature signer = null;

		// 使用公钥出始化签名
		try {
			signer = Signature.getInstance("SHA1withRSA");
			signer.initVerify(publicKey);
		} catch (NoSuchAlgorithmException noAlgorithm) {
			System.out.println(noAlgorithm);
			return false;
		} catch (InvalidKeyException badKey) {
			System.out.println(badKey);
			return false;
		}
		byte[] fileData = signString.getBytes();
		for (int i = 0; i < fileData.length; i++) {
			try {
				signer.update(fileData[i]);
			} catch (SignatureException signError) {
				System.out.println(signError);
			}
		}

		// 读取原始签名文件
		byte[] sigBytes = signCode;
		boolean signatureVerified = false;
		try {
			signatureVerified = signer.verify(sigBytes);
		} catch (SignatureException signError) {
			System.out.println(signError);
			return false;
		}
		return signatureVerified;
	}
}
