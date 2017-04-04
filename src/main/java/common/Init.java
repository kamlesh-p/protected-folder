package common;

import static common.Constants.APPDATA_DIRECTORY_PATH;
import static common.Constants.DATABASE_PATH;
import static common.Constants.IS_UNLOCKED;
import static common.Constants.LOCKED_DIRECTORY_COLUMN;
import static common.Constants.LOCKED_DIRECTORY_TABLE;
import static common.Constants.PASSWORD_COLUMN;
import static common.Constants.PASSWORD_HINT_COLUMN;
import static common.Constants.PASSWORD_HINT_TABLE;
import static common.Constants.PASSWORD_TABLE;
import static common.Constants.SECURITY_ANSWER_COLUMN;
import static common.Constants.SECURITY_QUESTIONS_TABLE;
import static common.Constants.SECURITY_QUESTION_COLUMN;
import static common.Constants.VERIFICATION_FILENAME;
import gui.PasswordGUI;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import lock.unlock.LockOrUnlockDirectory;
import database.DirectoryTable;

public class Init {
	private final static Logger	logger		= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	static Connection			connection	= null;

	public static void setup() throws SQLException {
		/**
		 * one time setup, should be in following order
		 * 1. create directory for dataBase - Init.createDatabaseDirectory()
		 * 2. create database
		 * 3. create required tables
		 * 4. close connection
		 * 4. create AppDatadirectory/files - Init.generateAppDataFile()
		 */
		try {
			Init.createDatabaseDirectory();
			Init.createDatabase();
			Init.createTables();
			Init.createAppDataFile();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			Init.closeConnection();
		}
	}

	public static boolean uninstall() throws IOException {
		String[] dirList = DirectoryTable.getDirectoryListForUnlock();
		int confirm = JOptionPane.showConfirmDialog(null, "Do you like to uninstall?", "Confirm", JOptionPane.YES_NO_OPTION);
		/*
		 * confirm:
		 * 0 - yes
		 * 1 - no
		 * 2 - cancel
		 */
		if (confirm == JOptionPane.YES_OPTION) {
			// if no directories are locked, uninstall directly,
			// else ask for password
			if (dirList.length == 0) {
				clearDataFiles();
				clearAppData();
				return true;
			} else {
				PasswordGUI.main(null);
			}
			logger.info("uninstaling application...");
		}
		return false;
	}

	public static boolean clearAppData() {
		try {
			// uninstall
			setAceessRestriction(false);
			deleteDir(new File(APPDATA_DIRECTORY_PATH));
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "OOPS! Something went wrong while uninstalling");
			return false;
		}

	}

	public static boolean clearDataFiles() {
		try {
			deleteDir(new File(DATABASE_PATH));
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "OOPS! Something went wrong while uninstalling");
			return false;
		}

	}

	public static void UnlockDirectories() throws IOException,
			InterruptedException {
		String[] dirList = DirectoryTable.getDirectoryList();
		for (String directoryPath : dirList) {
			LockOrUnlockDirectory.unlockDirectoryPasswordPreVerified(directoryPath);
		}
		clearAppData();
		clearDataFiles();
		logger.info("uninatalld successfully!");
	}

	/**
	 * to create new directory for database
	 */
	private static void createDatabaseDirectory() {
		// create directory
		logger.info("creating initial directory");
		new File(DATABASE_PATH).mkdirs();
	}

	/**
	 * to create new SQLite database file, depends on createDatabaseDirectory()
	 * method
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private static void createDatabase() throws SQLException, IOException,
			InterruptedException {
		try {
			logger.info("creating base for data");
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_PATH + "protectfolder.db");
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			closeConnection();
			deleteDir(new File(DATABASE_PATH));
			System.exit(0);
		}
		System.out.println("Created database successfully");
	}

	/**
	 * to create new database tables, dependent on createDatebase() method
	 */
	private static void createTables() {
		Statement stmt = null;
		try {
			logger.info("creating data storage files");
			stmt = connection.createStatement();
			// create password table
			String sqlpasswordTable = "CREATE TABLE if not exists " + PASSWORD_TABLE
					+ "(" + PASSWORD_COLUMN + " TEXT not null PRIMARY KEY)";
			// create locked Directories table
			String sqlDirectoryTable = "CREATE TABLE if not exists " + LOCKED_DIRECTORY_TABLE
					+ "(" + LOCKED_DIRECTORY_COLUMN + " TEXT not null PRIMARY KEY,"
					+ IS_UNLOCKED + " boolean DEFAULT false)";

			// create table to store password hint
			String sqlPasswordHintTable = "CREATE TABLE if not exists " + PASSWORD_HINT_TABLE
					+ "(" + PASSWORD_HINT_COLUMN + " TEXT not null PRIMARY KEY)";
			// create security qa table
			String sqlSecurityQuestionsTable = "CREATE TABLE if not exists " + SECURITY_QUESTIONS_TABLE
					+ "(" + SECURITY_QUESTION_COLUMN + " TEXT not null,"
					+ SECURITY_ANSWER_COLUMN + " TEXT not null)";
			stmt.executeUpdate(sqlpasswordTable);
			stmt.executeUpdate(sqlDirectoryTable);
			stmt.executeUpdate(sqlPasswordHintTable);
			stmt.executeUpdate(sqlSecurityQuestionsTable);
			stmt.close();
		} catch (Exception e) {

			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Tables created successfully");
	}

	private static void closeConnection() throws SQLException {
		connection.close();
	}

	public static void resetAppDataFile() throws IOException {
		createAppDataFile();
	}

	/**
	 * create verification file in AppData folder
	 * 
	 * @throws IOException
	 */
	private static void createAppDataFile() throws IOException {
		try {
			logger.info("verification...");
			// create directory
			new File(APPDATA_DIRECTORY_PATH).mkdirs();
			// create new file
			File f = new File(APPDATA_DIRECTORY_PATH + VERIFICATION_FILENAME);
			f.createNewFile();
			// write some data into file
			PrintWriter p = new PrintWriter(f);
			p.print("#PLEASE DO NOT MAKE ANY CHANGE HERE, THIS MAY CAUSE DATA CORRUPTION#");
			p.close();
			/**
			 * set access permission
			 */
			setAceessRestriction(true);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			setAceessRestriction(false);
			deleteDir(new File(APPDATA_DIRECTORY_PATH));
			System.exit(0);
		}
	}

	private static void setAceessRestriction(boolean value) throws IOException {
		logger.info("verification...");
		Path path = Paths.get(APPDATA_DIRECTORY_PATH + VERIFICATION_FILENAME);
		AclFileAttributeView aclAttr = Files.getFileAttributeView(path, AclFileAttributeView.class);
		UserPrincipal user = path.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName(System.getProperty("user.name"));
		AclEntry.Builder builder = AclEntry.newBuilder();
		builder.setPermissions(EnumSet.of(AclEntryPermission.APPEND_DATA,
				AclEntryPermission.DELETE,
				AclEntryPermission.DELETE_CHILD,
				AclEntryPermission.EXECUTE,
				AclEntryPermission.READ_ACL,
				AclEntryPermission.READ_ATTRIBUTES,
				AclEntryPermission.READ_DATA,
				AclEntryPermission.READ_NAMED_ATTRS,
				AclEntryPermission.SYNCHRONIZE,
				AclEntryPermission.WRITE_ACL,
				AclEntryPermission.WRITE_ATTRIBUTES,
				AclEntryPermission.WRITE_DATA,
				AclEntryPermission.WRITE_NAMED_ATTRS,
				AclEntryPermission.WRITE_OWNER));
		builder.setPrincipal(user);
		if (value) {
			builder.setType(AclEntryType.DENY);
		} else {
			builder.setType(AclEntryType.ALLOW);
		}
		aclAttr.setAcl(Collections.singletonList(builder.build()));
	}

	/**
	 * delete the created directory if error occurs while creating them.
	 * 
	 * @param dir
	 * @return true if file deleted successfully
	 */
	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		System.out.println("deleting directories...");
		return dir.delete();
	}

}
