package database;

import static common.Constants.DATABASE_PATH;
import static common.Constants.IS_UNLOCKED;
import static common.Constants.LOCKED_DIRECTORY_COLUMN;
import static common.Constants.LOCKED_DIRECTORY_TABLE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DirectoryTable {
	private final static Logger	logger			= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	static Set<String>			directoryList	= new HashSet<String>();

	// TODO move this to Query class
	public static String[] getDirectoryList()
	{
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_PATH + "protectfolder.db");
			System.out.println("Opened database successfully");
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT " + LOCKED_DIRECTORY_COLUMN + " FROM "
					+ LOCKED_DIRECTORY_TABLE);
			while (rs.next()) {
				String directory = rs.getString(LOCKED_DIRECTORY_COLUMN);
				directoryList.add(directory);
				System.out.println("Direcoty = " + directory);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			System.exit(0);
		}
		System.out.println("Operation done successfully");
		String[] directoryArray = new String[directoryList.size()];
		return directoryList.toArray(directoryArray);
	}

	public static String[] getDirectoryListForUnlock()
	{
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_PATH + "protectfolder.db");
			System.out.println("Opened database successfully");
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT " + LOCKED_DIRECTORY_COLUMN + " FROM "
					+ LOCKED_DIRECTORY_TABLE + " WHERE " + IS_UNLOCKED + " = 'false'");
			while (rs.next()) {
				String directory = rs.getString(LOCKED_DIRECTORY_COLUMN);
				directoryList.add(directory);
				System.out.println("Direcoty = " + directory);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			System.exit(0);
		}
		System.out.println("Operation done successfully");
		String[] directoryArray = new String[directoryList.size()];
		return directoryList.toArray(directoryArray);
	}

	public static void setAsUnlocked(String directoryPath) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_PATH + "protectfolder.db");
			stmt = c.createStatement();
			stmt.executeUpdate("UPDATE " + LOCKED_DIRECTORY_TABLE + " SET " + IS_UNLOCKED + " = 'true' WHERE "
					+ LOCKED_DIRECTORY_COLUMN + " = '" + directoryPath + "'");
			stmt.close();
			c.close();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			System.exit(0);
		}
		System.out.println("Updated data as unlocked");
	}
	
	public static void setAsLocked(String directoryPath) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_PATH + "protectfolder.db");
			stmt = c.createStatement();
			stmt.executeUpdate("UPDATE " + LOCKED_DIRECTORY_TABLE + " SET " + IS_UNLOCKED + " = 'false' WHERE "
					+ LOCKED_DIRECTORY_COLUMN + " = '" + directoryPath + "'");
			stmt.close();
			c.close();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			System.exit(0);
		}
		System.out.println("Updated data as unlocked");
	}

	public static void insertOrIgnore(String directory) {
		Query.insertOrIgnoreData(directory);
	}

	public static void main(String[] args) {
		System.out.println("SELECT " + LOCKED_DIRECTORY_COLUMN + " FROM "
				+ LOCKED_DIRECTORY_TABLE + " WHERE " + IS_UNLOCKED + " = 'false'");
	}
}
