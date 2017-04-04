package database;

import static common.Constants.SECURITY_ANSWER_COLUMN;
import static common.Constants.SECURITY_QUESTIONS_TABLE;
import static common.Constants.SECURITY_QUESTION_COLUMN;

import common.Commons;
import common.Constants;

import encrypt.decrypt.Encryptor;

public class SecurityQuestionsTable {

	public static void insert(String question, String Answer) {
		Encryptor encryptor = new Encryptor(Commons.getSecretKey(String.valueOf(Answer)));
		String encryptedAnswer = encryptor.encrypt(String.valueOf(Answer));
		Query.insertData(SECURITY_QUESTIONS_TABLE, SECURITY_QUESTION_COLUMN + "," + SECURITY_ANSWER_COLUMN,
				question + "', '" + encryptedAnswer);
	}

	public static boolean readAndVerifySecurityAnswer(String question, String answer) {
		Encryptor encryptor = new Encryptor(Commons.getSecretKey(String.valueOf(answer)));
		String encryptedAnswer = Query.readSecurityAnswer(question);
		String decryptedAnswer = encryptor.decrypt(encryptedAnswer);
		return (answer.equals(decryptedAnswer));
	}

	public static void main(String[] args) {
		readAndVerifySecurityAnswer(Constants.SECURITY_QUESTION_01, "q");
	}
}
