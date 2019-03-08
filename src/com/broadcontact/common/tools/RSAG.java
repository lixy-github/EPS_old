package com.broadcontact.common.tools;

/**
 * ���ڿ���ķ�ʽ�����ɹ���/˽����Կ�ԣ������浽���̡�
 *
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class RSAG {

	// ������������Կ����·��
	private String publicKeyPath;

	private String privateKeyPath;

	public void init(String vpublicKeyPath, String vprivateKeyPath) {
		publicKeyPath = vpublicKeyPath;
		privateKeyPath = vprivateKeyPath;
	}

	public boolean GenerateRSAKeys(String uName) throws java.io.IOException {
		KeyPairGenerator keyGen = null;
		try {
			// ��ʼ��
			keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(1024);
		} catch (NoSuchAlgorithmException noAlgorithm) {
			System.out.println("û���ҵ�RSA�㷨");
			return false;
		}
		// ������Կ��
		try {
			KeyPair keyPair = keyGen.generateKeyPair();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

			// д��˽Կ�ļ�
			ObjectOutputStream privateKeyFile = new ObjectOutputStream(
					new FileOutputStream(privateKeyPath + uName + ".privateKey"));
			privateKeyFile.writeObject(privateKey);
			privateKeyFile.close();

			// д�빫Կ�ļ�
			ObjectOutputStream publicKeyFile = new ObjectOutputStream(
					new FileOutputStream(publicKeyPath + uName + ".publicKey"));
			publicKeyFile.writeObject(publicKey);
			publicKeyFile.close();

			return true;
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}

	/**
	 * ���˽����Կ
	 */
	public RSAPrivateKey getPrivateKey(String privateKeyPath) {
		RSAPrivateKey privateKey = null;
		try {
			ObjectInputStream privateKeyFile = new ObjectInputStream(
					new FileInputStream(privateKeyPath));
			privateKey = (RSAPrivateKey) privateKeyFile.readObject();
			privateKeyFile.close();
		} catch (IOException e) {
			System.out.println("û���ҵ�˽����Կ�ļ�");
		} catch (ClassNotFoundException e) {
			System.out.println("�����˽����Կ�ļ�");
		}
		return privateKey;
	}

	/**
	 * ��ù�����Կ
	 */
	public RSAPublicKey getPublicKey(String publicKeyPath) {
		RSAPublicKey publicKey = null;
		try {
			ObjectInputStream publicKeyFile = new ObjectInputStream(
					new FileInputStream(publicKeyPath));
			publicKey = (RSAPublicKey) publicKeyFile.readObject();
			publicKeyFile.close();
		} catch (IOException e) {
			System.out.println("û���ҵ�������Կ�ļ�");
		} catch (ClassNotFoundException e) {
			System.out.println("����Ĺ�����Կ�ļ�");
		}
		return publicKey;
	}
}
