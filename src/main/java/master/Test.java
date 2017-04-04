package master;

import static common.Constants.DATABASE_PATH;

import java.io.File;

public class Test {
	public static void main(String[] args) {
		System.out.println(new File(DATABASE_PATH).exists());
	}
}
