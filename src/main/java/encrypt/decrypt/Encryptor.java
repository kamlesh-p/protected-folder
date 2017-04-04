package encrypt.decrypt;

import static common.Constants.AES;
import static common.Constants.UTF_8;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import common.Commons;

public class Encryptor {

	private Cipher	ecipher;
	private Cipher	dcipher;

	/*
	 * Encryptor secret key should be of 16 character length.
	 */
	public Encryptor(SecretKey key) {
		try {
			ecipher = Cipher.getInstance(AES);
			dcipher = Cipher.getInstance(AES);
			ecipher.init(Cipher.ENCRYPT_MODE, key);
			dcipher.init(Cipher.DECRYPT_MODE, key);
		} catch (Exception e) {
			System.out.println("Failed in encryptor initialization");
		}
	}

	public String encrypt(String str) {
		try {
			byte[] utf8 = str.getBytes(UTF_8);
			byte[] enc = ecipher.doFinal(utf8);
			return new sun.misc.BASE64Encoder().encode(enc);
		} catch (Exception e) {
			System.out.println("Failed in Encryption");
		}
		return null;
	}

	public String decrypt(String str) {
		try {
			byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
			byte[] utf8 = dcipher.doFinal(dec);
			return new String(utf8, UTF_8);
		} catch (Exception e) {
			System.out.println("Failed in Decryption");
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			Encryptor encrypter = new
					Encryptor(Commons.getSecretKey("q"));
			String encrypted = encrypter.encrypt("q");
			System.out.println("Before Decryption   : " + encrypted);
			String decrypted = encrypter.decrypt(encrypted);
			System.out.println("After Decryption   : " + decrypted);
		} catch (Exception e) {
		}

	}
}
