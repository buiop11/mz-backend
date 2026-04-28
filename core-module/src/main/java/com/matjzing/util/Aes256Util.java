package com.matjzing.util;

import java.util.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;

@Component
public class Aes256Util {

	// 암호화 iv값 변경
	private String iv = "fZXyx9QkSLXdXJhlB";
	private final Key keySpec;

	// 슬래시(/) replace char 변경
	private static final String REPLACEMENT_SLASH_CHAR = "Irf1Ut";
	private static final String REPLACEMENT_PLUS_CHAR = "7QLF3t";

	public Aes256Util() {
		this.iv = iv.substring(0, 16);
		byte[] keyBytes = new byte[16];
		byte[] b = iv.getBytes(StandardCharsets.UTF_8);
		int len = b.length;
		if (len > keyBytes.length) {
			len = keyBytes.length;
		}
		System.arraycopy(b, 0, keyBytes, 0, len);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
		this.keySpec = keySpec;
	}

	public String encrypt(String str) {
		Cipher c = null;
		String enStr= null;
		try {
			c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
			byte[] encrypted = c.doFinal(str.getBytes(StandardCharsets.UTF_8));
			enStr = Base64.getEncoder().encodeToString(encrypted);
			return enStr;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (NoSuchPaddingException e) {
			throw new RuntimeException(e);
		} catch (InvalidAlgorithmParameterException e) {
			throw new RuntimeException(e);
		} catch (IllegalBlockSizeException e) {
			throw new RuntimeException(e);
		} catch (BadPaddingException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		}
	}

	public String encryptReplaceSlash(String str) {
		return encrypt(str).replaceAll("/", REPLACEMENT_SLASH_CHAR).replaceAll("\\+", REPLACEMENT_PLUS_CHAR);
	}

	public String decrypt(String str)  {
		Cipher c = null;
		try {
			c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
			byte[] byteStr = Base64.getDecoder().decode(str.getBytes());
			return new String(c.doFinal(byteStr), StandardCharsets.UTF_8);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (NoSuchPaddingException e) {
			throw new RuntimeException(e);
		} catch (InvalidAlgorithmParameterException e) {
			throw new RuntimeException(e);
		} catch (BadPaddingException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		} catch (IllegalBlockSizeException e) {
			throw new RuntimeException(e);
		}
	}

	public String decryptReplaceSlash(String str)  {
		return decrypt(str.replaceAll(REPLACEMENT_SLASH_CHAR, "/").replaceAll(REPLACEMENT_PLUS_CHAR,"\\+"));
	}

}