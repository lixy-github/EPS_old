package com.broadcontact.common.tools;

/**
 * 基于口令的方式？生成公有/私有密钥对，并保存到磁盘。
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

	// 声明产生的密钥保存路径
	private String publicKeyPath;

	private String privateKeyPath;

	public void init(String vpublicKeyPath, String vprivateKeyPath) {
		publicKeyPath = vpublicKeyPath;
		privateKeyPath = vprivateKeyPath;
	}

	public boolean GenerateRSAKeys(String uName) throws java.io.IOException {
		KeyPairGenerator keyGen = null;
		try {
			// 初始化
			keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(1024);
		} catch (NoSuchAlgorithmException noAlgorithm) {
			System.out.println("没有找到RSA算法");
			return false;
		}
		// 生成密钥对
		try {
			KeyPair keyPair = keyGen.generateKeyPair();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

			// 写入私钥文件
			ObjectOutputStream privateKeyFile = new ObjectOutputStream(
					new FileOutputStream(privateKeyPath + uName + ".privateKey"));
			privateKeyFile.writeObject(privateKey);
			privateKeyFile.close();

			// 写入公钥文件
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
	 * 获得私有密钥
	 */
	public RSAPrivateKey getPrivateKey(String privateKeyPath) {
		RSAPrivateKey privateKey = null;
		try {
			ObjectInputStream privateKeyFile = new ObjectInputStream(
					new FileInputStream(privateKeyPath));
			privateKey = (RSAPrivateKey) privateKeyFile.readObject();
			privateKeyFile.close();
		} catch (IOException e) {
			System.out.println("没有找到私有密钥文件");
		} catch (ClassNotFoundException e) {
			System.out.println("错误的私有密钥文件");
		}
		return privateKey;
	}

	/**
	 * 获得公有密钥
	 */
	public RSAPublicKey getPublicKey(String publicKeyPath) {
		RSAPublicKey publicKey = null;
		try {
			ObjectInputStream publicKeyFile = new ObjectInputStream(
					new FileInputStream(publicKeyPath));
			publicKey = (RSAPublicKey) publicKeyFile.readObject();
			publicKeyFile.close();
		} catch (IOException e) {
			System.out.println("没有找到公有密钥文件");
		} catch (ClassNotFoundException e) {
			System.out.println("错误的公有密钥文件");
		}
		return publicKey;
	}
}
