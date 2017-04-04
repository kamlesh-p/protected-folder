package database;

import static common.Constants.PASSWORD_HINT_COLUMN;
import static common.Constants.PASSWORD_HINT_TABLE;

public class PasswordHintTable {
	public static void insertPasswordHint(String passwordHint) {
		Query.insertData(PASSWORD_HINT_TABLE, PASSWORD_HINT_COLUMN, passwordHint);
	}

	public static void updatePasswordHint(String newPasswordHint) {
		Query.updateData(PASSWORD_HINT_TABLE, PASSWORD_HINT_COLUMN, newPasswordHint);
	}

	public static String getPasswordHint() {
		return Query.readPasswordHint();
	}
}
