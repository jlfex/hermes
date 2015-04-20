package com.jlfex.hermes.common.utils;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.exception.ServiceException;

/**
 * 加密工具
 */
public abstract class Cryptos {

	/**
	 * 散列字符串
	 * 
	 * @param str
	 * @param hashType
	 * @return
	 */
	public static byte[] hash(String str, HashType hashType) {
		try {
			MessageDigest md = MessageDigest.getInstance(hashType.toString());
			return md.digest(str.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new ServiceException("cannot hash " + str + ".", e);
		}
	}
	
	/**
	 * 十六进制转换
	 * 
	 * @param bytes
	 * @return
	 */
	public static String hex(byte[] bytes) {
		String hex = "0123456789abcdef";
		StringBuffer out = new StringBuffer(bytes.length);
		for (byte b: bytes) {
			out.append(hex.charAt(b >>> 4 & 0x0F));
			out.append(hex.charAt(b & 0x0F));
		}
		return out.toString();
	}
	
	/**
	 * 散列并转换字符串
	 * 
	 * @param str
	 * @param hashType
	 * @return
	 */
	public static String hashAndHex(String str, HashType hashType) {
		return hex(hash(str, hashType));
	}
	
	/**
	 * 加密字符串
	 * 
	 * @param str
	 * @param key
	 * @param encryptType
	 * @return
	 */
	public static String encrypt(String str, String key, EncryptType encryptType) {
		try {
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), encryptType.toString());
			Cipher cipher = Cipher.getInstance(encryptType.toString());
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			return Base64.encodeBase64String(cipher.doFinal(str.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			throw new ServiceException("cannot encrypt " + str + " by " + encryptType.toString() + ".", e);
		} catch (NoSuchPaddingException e) {
			throw new ServiceException("cannot encrypt " + str + " by " + encryptType.toString() + ".", e);
		} catch (InvalidKeyException e) {
			throw new ServiceException("cannot encrypt " + str + " by " + encryptType.toString() + ".", e);
		} catch (IllegalBlockSizeException e) {
			throw new ServiceException("cannot encrypt " + str + " by " + encryptType.toString() + ".", e);
		} catch (BadPaddingException e) {
			throw new ServiceException("cannot encrypt " + str + " by " + encryptType.toString() + ".", e);
		} catch (Exception e) {
			throw new ServiceException("cannot encrypt " + str + " by " + encryptType.toString() + ".", e);
		}
	}
	
	/**
	 * 解密字符串
	 * 
	 * @param str
	 * @param key
	 * @param encryptType
	 * @return
	 */
	public static String decrypt(String str, String key, EncryptType encryptType) {
		try {
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), encryptType.toString());
			Cipher cipher = Cipher.getInstance(encryptType.toString());
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			return new String(cipher.doFinal(Base64.decodeBase64(str)));
		} catch (NoSuchAlgorithmException e) {
			throw new ServiceException("cannot decrypt " + str + " by " + encryptType.toString() + ".", e);
		} catch (NoSuchPaddingException e) {
			throw new ServiceException("cannot decrypt " + str + " by " + encryptType.toString() + ".", e);
		} catch (InvalidKeyException e) {
			throw new ServiceException("cannot decrypt " + str + " by " + encryptType.toString() + ".", e);
		} catch (IllegalBlockSizeException e) {
			throw new ServiceException("cannot decrypt " + str + " by " + encryptType.toString() + ".", e);
		} catch (BadPaddingException e) {
			throw new ServiceException("cannot decrypt " + str + " by " + encryptType.toString() + ".", e);
		} catch (Exception e) {
			throw new ServiceException("cannot decrypt " + str + " by " + encryptType.toString() + ".", e);
		}
	}

	/**
	 * 加密字符串
	 * 
	 * @param str
	 * @param key
	 * @return
	 */
	public static String encryptAES(String str, String key) {
		return encrypt(str, key, EncryptType.AES);
	}
	
	/**
	 * 加密字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String encryptAES(String str) {
		String key = App.config("app.secret");
		if (!Strings.empty(key) && key.length() >= 16) return encryptAES(str, key.substring(0, 16));
		throw new ServiceException("configuration app.secret is invalid.");
	}

	/**
	 * 解密字符串
	 * 
	 * @param str
	 * @param key
	 * @return
	 */
	public static String decryptAES(String str, String key) {
		return decrypt(str, key, EncryptType.AES);
	}
	
	/**
	 * 解密字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String decryptAES(String str) {
		String key = App.config("app.secret");
		if (!Strings.empty(key) && key.length() >= 16) return decryptAES(str, key.substring(0, 16));
		throw new ServiceException("configuration app.secret is invalid.");
	}
	
	/**
	 * 加密字符串
	 * 
	 * @param str
	 * @param key
	 * @return
	 */
	public static String encryptDES(String str, String key) {
		return encrypt(str, key, EncryptType.DES);
	}
	
	/**
	 * 加密字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String encryptDES(String str) {
		String key = App.config("app.secret");
		if (!Strings.empty(key) && key.length() >= 8) return encryptDES(str, key.substring(0, 8));
		throw new ServiceException("configuration app.secret is invalid.");
	}
	
	/**
	 * 解密字符串
	 * 
	 * @param str
	 * @param key
	 * @return
	 */
	public static String decryptDES(String str, String key) {
		return decrypt(str, key, EncryptType.DES);
	}
	
	/**
	 * 解密字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String decryptDES(String str) {
		String key = App.config("app.secret");
		if (!Strings.empty(key) && key.length() >= 8) return decryptDES(str, key.substring(0, 8));
		throw new ServiceException("configuration app.secret is invalid.");
	}
	
	/**
	 * 散列类型
	 * 
	 * @author ultrafrog
	 * @version 1.0, 2013-11-14
	 * @since 1.0
	 */
	public enum HashType {
		
		MD5("MD5"),
		SHA1("SHA-1"),
		SHA256("SHA-256"),
		SHA512("SHA-512");
		
		/** 运算规则 */
		private String algorithm;
		
		/**
		 * 构造函数
		 * 
		 * @param algorithm
		 */
		private HashType(String algorithm) {
			this.algorithm = algorithm;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return algorithm;
		}
	}
	
	/**
	 * 加密类型
	 * 
	 * @author ultrafrog
	 * @version 1.0, 2014-02-19
	 * @since 1.0
	 */
	public enum EncryptType {
		
		AES("AES"),
		DES("DES");
		
		/** 算法 */
		private String algorithm;
		
		/**
		 * 构造函数
		 * 
		 * @param algorithm
		 */
		private EncryptType(String algorithm) {
			this.algorithm = algorithm;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return algorithm;
		}
	}
}
