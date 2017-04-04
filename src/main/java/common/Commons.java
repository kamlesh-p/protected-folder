package common;

import static common.Constants.AES;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * CommonUtils: this class contains commonly used methods to perform different
 * actions in this application.
 * 
 * @author kamlesh
 *
 */
public class Commons {
	/**
	 * 
	 * @param key
	 * @return SevretKey for encryption/decryption
	 */
	public static SecretKey getSecretKey(String key) {
		if (key.length() >= 16) {
			String newkey = key.substring(0, 16);
			return new SecretKeySpec(newkey.getBytes(), AES);
		} else {
			StringBuffer newkey = new StringBuffer(key);
			for (int i = 0; i < 16 - key.length(); i++) {
				newkey = newkey.append("0");
			}
			return new SecretKeySpec(newkey.toString().getBytes(), AES);
		}
	}

	/**
	 * 
	 * @param directory
	 * @return return true if it base directory else false
	 */
	public static boolean isNotBaseDirectory(String directory) {
		int folderLength = 0;
		if (directory.contains("\\")) {
			folderLength = directory.split("\\\\").length;
		} else if (directory.contains("/")) {
			folderLength = directory.split("/").length;
		}
		return (folderLength > 1);
	}

	private Commons() {
		// private constructor (no need to instantiate the class)
	}
}
