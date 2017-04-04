package database;

import static common.Constants.DATABASE_PATH;
import static common.Constants.LOCKED_DIRECTORY_COLUMN;
import static common.Constants.LOCKED_DIRECTORY_TABLE;
import static common.Constants.PASSWORD_COLUMN;
import static common.Constants.PASSWORD_HINT_COLUMN;
import static common.Constants.PASSWORD_HINT_TABLE;
import static common.Constants.PASSWORD_TABLE;
import static common.Constants.SECURITY_ANSWER_COLUMN;
import static common.Constants.SECURITY_QUESTIONS_TABLE;
import static common.Constants.SECURITY_QUESTION_COLUMN;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteConfig.SynchronousMode;

public class Query {
	private final static Logger	logger		= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	static Connection			connection	= null;
	static Statement			stmt		= null;
	static PreparedStatement	pstmt		= null;

	public static void insertData(String TableName, String ColumnName, String Value) {
		try {
			createConnection();
			logger.info("inserting data...");
			String sql = "INSERT INTO " + TableName + " (" + ColumnName + ") " + "VALUES ('" + Value + "')";
			pstmt = connection.prepareStatement(sql);
			pstmt.executeUpdate();
			pstmt.close();
			commitAndCloseConnection();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			System.exit(0);
		}
		System.out.println("Records inserted successfully");
	}

	public static void insertOrIgnoreData(String TableName, String ColumnName, String Value) {
		try {
			createConnection();
			logger.info("inserting data...");
			String sql = "INSERT OR IGNORE INTO " + TableName + " (" + ColumnName + ") " + "VALUES ('" + Value + "')";
			pstmt = connection.prepareStatement(sql);
			pstmt.executeUpdate();
			pstmt.close();
			commitAndCloseConnection();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			System.exit(0);
		}
		System.out.println("Records inserted successfully");
	}

	public static void insertOrIgnoreData(String directory) {
		try {
			createConnection();
			System.out.println("Opened database successfully");
			String sql = "INSERT OR IGNORE INTO " + LOCKED_DIRECTORY_TABLE
					+ " (" + LOCKED_DIRECTORY_COLUMN + ") " + "VALUES ('" + directory + "')";
			pstmt = connection.prepareStatement(sql);
			pstmt.executeUpdate();
			pstmt.close();
			commitAndCloseConnection();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			System.exit(0);
		}
		System.out.println("Records inserted/ignored successfully");
	}

	public static String readPassword() {
		String password = null;
		try {
			createConnection();
			System.out.println("Opened database successfully");
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT " + PASSWORD_COLUMN + " FROM " + PASSWORD_TABLE);
			while (rs.next()) {
				password = rs.getString(PASSWORD_COLUMN);
			}
			rs.close();
			stmt.close();
			closeConnection();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			System.exit(0);
		}
		System.out.println("Operation(read) done successfully");
		return password;
	}

	public static String readPasswordHint() {
		String passwordHint = null;
		try {
			createConnection();
			System.out.println("Opened database successfully");
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT " + PASSWORD_HINT_COLUMN + " FROM " + PASSWORD_HINT_TABLE);
			while (rs.next()) {
				passwordHint = rs.getString(PASSWORD_HINT_COLUMN);
			}
			rs.close();
			stmt.close();
			closeConnection();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			System.exit(0);
		}
		System.out.println("Operation(read) done successfully");
		return passwordHint;
	}

	public static String readSecurityAnswer(String question) {
		String securityAnswer = null;
		try {
			createConnection();
			System.out.println("Opened database successfully");
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT " + SECURITY_ANSWER_COLUMN + " FROM " + SECURITY_QUESTIONS_TABLE
					+ " WHERE " + SECURITY_QUESTION_COLUMN + " = '" + question + "'");
			securityAnswer = rs.getString(SECURITY_ANSWER_COLUMN);
			rs.close();
			stmt.close();
			closeConnection();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			System.exit(0);
		}
		System.out.println("Operation(read) done successfully");
		return securityAnswer;
	}

	public static void updateData(String TableName, String ColumnName, String updatedValue) {
		try {
			createConnection();
			System.out.println("Opened database successfully");
			String sql = "UPDATE " + TableName + " set " + ColumnName + " = ('" + updatedValue + "')";
			pstmt = connection.prepareStatement(sql);
			pstmt.executeUpdate();
			pstmt.close();
			commitAndCloseConnection();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			System.exit(0);
		}
		System.out.println("updated data successfully");
	}

	private static void createConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			config.setSynchronous(SynchronousMode.OFF);
			connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_PATH + "protectfolder.db");
			connection.setAutoCommit(false);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			System.exit(0);
		}
	}

	private static void commitAndCloseConnection() {
		try {
			connection.commit();
			connection.close();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			System.exit(0);
		}
	}

	private static void closeConnection() {
		try {
			connection.close();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			System.exit(0);
		}
	}
}
