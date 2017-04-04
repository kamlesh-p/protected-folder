package common;

import java.util.logging.Logger;

/**
 * Constants: constants used in this application
 * 
 * @author kamlesh
 *
 */
public class Constants {
	private final static Logger	logger						= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	/**
	 * do not change data in this file, this may cause data corruption
	 */
	public static final String	APPLICATION_NAME			= "Folder Lock";
	// common constants
	public static final String	LOCK						= "LOCK";
	public static final String	UNLOCK						= "UNLOCK";
	public static String		LOCK_OR_UNLOCK				= "";
	public static boolean		UNINSTALL					= false;
	// database directory path
	public static final String	DATABASE_PATH				= System.getProperty("user.home") + "/ProtectFolder/bin/";
	// other constants
	public static final String	AES							= "AES";
	public static final String	UTF_8						= "UTF-8";
	// verification directory
	public static final String	APPDATA_DIRECTORY_PATH		= System.getProperty("user.home") + "/AppData/Roaming/protect/";
	public static final String	VERIFICATION_FILENAME		= "protectFolder.txt";
	// table names
	public static final String	PASSWORD_TABLE				= "PASSWORD_TABLE";
	public static final String	LOCKED_DIRECTORY_TABLE		= "LOCKED_DIRECTORY_TABLE";
	public static final String	PASSWORD_HINT_TABLE			= "PASSWORD_HINT_TABLE";
	public static final String	SECURITY_QUESTIONS_TABLE	= "SECURITY_QUESTIONS_TABLE";
	public static final String	PASSWORD_COLUMN				= "PASSWORD";
	public static final String	LOCKED_DIRECTORY_COLUMN		= "LOCKED_DIRECTORY";
	public static final String	IS_UNLOCKED					= "IS_UNLOCKED";
	public static final String	PASSWORD_HINT_COLUMN		= "PASSWORD_HINT";
	public static final String	SECURITY_QUESTION_COLUMN	= "SECURITY_QUESTION";
	public static final String	SECURITY_ANSWER_COLUMN		= "SECURITY_ANSWER";
	// new filename constants
	public static final String	DOT_BRC_OPN					= ".{";
	public static final String	HYPEN						= "-";
	public static final String	BRC_CLS						= "}";
	// selected directory to lock or unlock
	public static String		ABSOLUTE_PATH;
	// incorrect password count
	public static int			INCORRECT_PASSWORD_COUNT	= 0;
	// security question
	public static final String	SECURITY_QUESTION_01		= "Your nick name?";
	public static final String	SECURITY_QUESTION_02		= "Your date of birth?";
	public static final String	SECURITY_QUESTION_03		= "Your favourite dish (food)?";
	public static final String	SECURITY_QUESTION_04		= "Your favourite movie?";
	// forgot password
	public static boolean		FORGOT_PASSWORD				= false;

	private Constants() {
		// Private constructor
		logger.info("initializing constants...");
	}

}
