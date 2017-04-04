package database;

import static common.Constants.PASSWORD_COLUMN;
import static common.Constants.PASSWORD_TABLE;

import java.util.Arrays;

import common.Commons;

import encrypt.decrypt.Encryptor;

public class PasswordTable {

	public static void insert(char[] newPassword) {
		Encryptor encryptor = new Encryptor(Commons.getSecretKey(String.valueOf(newPassword)));
		String encryptedPassword = encryptor.encrypt(String.valueOf(newPassword));
		Query.insertOrIgnoreData(PASSWORD_TABLE, PASSWORD_COLUMN, encryptedPassword);
	}

	public static char[] getPassword(char[] password) {
		Encryptor encryptor = new Encryptor(Commons.getSecretKey(String.valueOf(password)));
		String encryptedPassword = Query.readPassword();
		String decryptedPassword = encryptor.decrypt(encryptedPassword);
		return (null != decryptedPassword ? decryptedPassword.toCharArray() : null);
	}

	public static boolean validatePassword(char[] password) {
		char[] retrivedPassword = PasswordTable.getPassword(password);
		return Arrays.equals(password, retrivedPassword);
	}

	public static boolean isPasswordExists() {
		String encryptedPassword = Query.readPassword();
		return (!encryptedPassword.equals(""));
	}

	public static void updatePassword(char[] newPassword) {
		Encryptor encryptor = new Encryptor(Commons.getSecretKey(String.valueOf(newPassword)));
		String encryptedPassword = encryptor.encrypt(String.valueOf(newPassword));
		Query.updateData(PASSWORD_TABLE, PASSWORD_COLUMN, encryptedPassword);
	}

	public static void resetPassword(char[] passwordUpdate)
	{
		Encryptor encryptor = new Encryptor(Commons.getSecretKey(String.valueOf(passwordUpdate)));
		String encryptUpdatedPassword = encryptor.encrypt(String.valueOf(passwordUpdate));
		Query.updateData(PASSWORD_TABLE, PASSWORD_COLUMN,
				encryptUpdatedPassword);
	}

	public static void main(String[] args) {
		PasswordTable.updatePassword(new char[] { 1, 2, 3, 4, 5, 6 });
		System.out.println(Arrays.equals(new char[] { 1, 2, 3, 4, 5, 6 }, (getPassword(new char[] { 1, 2, 3, 4, 5, 6 }))));
	}
}