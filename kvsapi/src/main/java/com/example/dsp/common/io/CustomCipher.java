package com.example.dsp.common.io;

import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.buf.HexUtils;

public class CustomCipher {
	private final String algorithm;
	private final String key;

	public CustomCipher(String key, String algorithm) {
		this.key = key;
		this.algorithm = algorithm;
	}

	public String encrypt(String text) throws Exception {
		SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes(), algorithm);
		javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(algorithm);
		cipher.init(1, sksSpec);
		byte[] encrypted = cipher.doFinal(text.getBytes());
		return HexUtils.toHexString(encrypted);
	}

	public String decrypt(String encryptedSrc) throws Exception {
		byte[] encrypted = HexUtils.fromHexString(encryptedSrc);
		SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes(), algorithm);
		javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(algorithm);
		cipher.init(2, sksSpec);
		byte[] decrypted = cipher.doFinal(encrypted);
		return new String(decrypted);
	}
}
