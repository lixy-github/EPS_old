package com.broadcontact.common.tools;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

/**
 * �������Ҫ���ڶ�Ŀ���ļ�������ϢժҪ��ǩ��ȷ�ϣ��Ա�֤�����ڴ���Ĺ����в��ᱻ�۸ģ������ԣ� ���ұ�֤���ᱻ������ð�������ļ���
 * �������̣�����ǩ����Signature�����ڼ���ǩ��ʱ����(MD5withRSA������Ϸ�ʽ�����Կ��Խ���ϢժҪ��
 * ����ǩ��һ����ɡ����ɺ��ǩ���ļ�����file/sign�¡�
 */
public class SignString {

	public byte[] sign(String privateKeyPath, String signString) {
		boolean flag = false;
		RSAG rsaGen = new RSAG();
		// ��ȡ˽Կ����
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
		// ��ȡ��Կ����
		PublicKey publicKey = rsaGen.getPublicKey(publicKeyName);
		Signature signer = null;

		// ʹ�ù�Կ��ʼ��ǩ��
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

		// ��ȡԭʼǩ���ļ�
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
