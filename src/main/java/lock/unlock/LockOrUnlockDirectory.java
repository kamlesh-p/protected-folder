package lock.unlock;

import static common.Constants.ABSOLUTE_PATH;
import static common.Constants.BRC_CLS;
import static common.Constants.DOT_BRC_OPN;
import static common.Constants.HYPEN;

import java.io.IOException;

import common.Commons;

import database.PasswordTable;
import encrypt.decrypt.Encryptor;

public class LockOrUnlockDirectory {
	/**
	 * lock the directory.
	 * 
	 * @param absoluteDirectory
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void lock(String absoluteDirectory) throws IOException,
			InterruptedException {
		// TODO: change absolute path
		String finalChildDirectory = getFinnelChildDirectoryName(absoluteDirectory);
		String finalChildDirectoryPath = getFinnelChildDirectoryPath(absoluteDirectory);
		Encryptor encryptor = new Encryptor(Commons.getSecretKey(finalChildDirectory));
		// encode with word characters and then take word character from result
		String logicalName = getLogicalName(finalChildDirectory, encryptor.encrypt(absoluteDirectory.replaceAll("[^\\w]", "")).toString());
		// lock
		Process p1 = Runtime.getRuntime().exec("cmd /c ren \"" + absoluteDirectory + "\" \"" + logicalName + "\"");
		p1.waitFor();
		p1.destroy();
		// set attributes
		Process p2 = Runtime.getRuntime().exec("cmd /c attrib +h +s \"" + finalChildDirectoryPath + logicalName + "\"");
		p2.waitFor();
		p2.destroy();
	}

	/**
	 * unlock directory with password verification.
	 * 
	 * @param password
	 * @return boolean
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static boolean verifyPaawordAndUnlock(char[] password)
			throws IOException, InterruptedException {
		// verify password
		if (PasswordTable.validatePassword(password)) {
			String lastChildDirectory = getFinnelChildDirectoryName(ABSOLUTE_PATH);
			String lastChildDirectoryPath = getFinnelChildDirectoryPath(ABSOLUTE_PATH);
			Encryptor encryptor = new Encryptor(Commons.getSecretKey(lastChildDirectory));
			// encode with word characters and then take word character from
			// result
			String logicalName = getLogicalName(lastChildDirectory, encryptor.encrypt(ABSOLUTE_PATH.replaceAll("[^\\w]", "")).toString());
			// set attributes
			Process p1 = Runtime.getRuntime().exec("cmd /c attrib -h -s \"" + lastChildDirectoryPath + logicalName + "\"");
			p1.waitFor();
			p1.destroy();
			// lock
			Process p2 = Runtime.getRuntime().exec("cmd /c ren \"" + lastChildDirectoryPath + logicalName + "\" \"" + lastChildDirectory + "\"");
			p2.waitFor();
			p2.destroy();
			// SET UNLOCK AS TRUE

			return true;
		} else {
			return false;
		}
	}

	/**
	 * unlock directory
	 * 
	 * @param directoryPath
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void unlockDirectoryPasswordPreVerified(String directoryPath)
			throws IOException, InterruptedException {
		String lastChildDirectory = getFinnelChildDirectoryName(directoryPath);
		String lastChildDirectoryPath = getFinnelChildDirectoryPath(directoryPath);
		Encryptor encryptor = new Encryptor(Commons.getSecretKey(lastChildDirectory));
		// encode with word characters and then take word character from
		// result
		String logicalName = getLogicalName(lastChildDirectory, encryptor.encrypt(directoryPath.replaceAll("[^\\w]", "")).toString());
		// set attributes
		Process p1 = Runtime.getRuntime().exec("cmd /c attrib -h -s \"" + lastChildDirectoryPath + logicalName + "\"");
		p1.waitFor();
		p1.destroy();
		// lock
		Process p2 = Runtime.getRuntime().exec("cmd /c ren \"" + lastChildDirectoryPath + logicalName + "\" \"" + lastChildDirectory + "\"");
		p2.waitFor();
		p2.destroy();
	}

	/**
	 * get the last directory name in the given path
	 * 
	 * @param absoluteDirectory
	 * @return last directory name
	 */
	private static String getFinnelChildDirectoryName(String absoluteDirectory) {
		String[] directoryArray;
		String finalChildDirectory = null;
		if (absoluteDirectory.contains("\\")) {
			directoryArray = absoluteDirectory.split("\\\\");
			finalChildDirectory = directoryArray[directoryArray.length - 1];
		}
		if (absoluteDirectory.contains("/")) {
			directoryArray = absoluteDirectory.split("/");
			finalChildDirectory = directoryArray[directoryArray.length - 1];
		}
		if (absoluteDirectory.contains("\\") && absoluteDirectory.contains("/")) {
			directoryArray = absoluteDirectory.replaceAll("\\\\", "/").split("/");
			finalChildDirectory = directoryArray[directoryArray.length - 1];
		}
		return finalChildDirectory;
	}

	/**
	 * get the path up-to final directory.
	 * 
	 * @param absoluteDirectory
	 * @return path
	 */
	private static String getFinnelChildDirectoryPath(String absoluteDirectory) {
		String[] directoryArray;
		String finalChildDirectoryPath = null;
		if (absoluteDirectory.contains("\\")) {
			directoryArray = absoluteDirectory.split("\\\\");
			StringBuffer path = new StringBuffer();
			for (int i = 0; i < directoryArray.length - 1; i++) {
				path = path.append(directoryArray[i]);
			}
			finalChildDirectoryPath = path.toString();
		}
		if (absoluteDirectory.contains("/")) {
			directoryArray = absoluteDirectory.split("/");
			StringBuffer path = new StringBuffer();
			for (int i = 0; i < directoryArray.length - 1; i++) {
				path = path.append(directoryArray[i]);
			}
			finalChildDirectoryPath = path.toString();
		}
		if (absoluteDirectory.contains("\\") && absoluteDirectory.contains("/")) {
			directoryArray = absoluteDirectory.replaceAll("\\\\", "/").split("/");
			StringBuffer path = new StringBuffer();
			for (int i = 0; i < directoryArray.length - 1; i++) {
				path = path.append(directoryArray[i]);
			}
			finalChildDirectoryPath = path.toString();
		}
		return finalChildDirectoryPath + "/";
	}

	/**
	 * convert the folder name to a logical name.
	 * 
	 * @param finalChildDirectory
	 * @param encodedName
	 * @return logicalName
	 */
	private static String getLogicalName(String finalChildDirectory, String encodedName) {
		encodedName = encodedName.replaceAll("[^\\w]", "");
		if (encodedName.length() < 16) {
			StringBuffer newName = new StringBuffer(encodedName);
			for (int i = 0; i < 16 - encodedName.length(); i++) {
				newName = newName.append(i);
			}
			encodedName = newName.toString();
		} else {
			encodedName = encodedName.substring(0, 16);
		}
		return finalChildDirectory + DOT_BRC_OPN
				+ encodedName.substring(0, 4) + HYPEN
				+ encodedName.substring(4, 8) + HYPEN
				+ encodedName.substring(8, 12) + HYPEN
				+ encodedName.substring(12, 16) + BRC_CLS;
	}

}
