package app.security.test;

import org.junit.Ignore;
import org.junit.Test;

import database.PasswordHintTable;
import database.PasswordTable;

@Ignore
public class SecurityTest {

	static String	alwaysTrue01	= "abc or 1=1";
	static String	alwaysTrue02	= "\" or\"\"=\"";

	static String	dropTableQueryInjection		= "105; DROP TABLE PASSWORD_HINT_TABLE";

	@Test
	public void sqlInjectionTest01() {
		char[] password = PasswordTable.getPassword(alwaysTrue01.toCharArray());
		assert (password == null);
	}

	@Test
	public void sqlInjectionTest02() {
		char[] password = PasswordTable.getPassword(alwaysTrue02.toCharArray());
		assert (password == null);
	}

	@Test
	public void sqlInjectionTest03() {
		PasswordHintTable.updatePasswordHint(dropTableQueryInjection);
		String passwordHint = PasswordHintTable.getPasswordHint();
		assert (passwordHint != null);
	}

}
