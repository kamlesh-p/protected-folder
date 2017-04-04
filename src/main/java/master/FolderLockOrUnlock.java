package master;

import static common.Constants.APPDATA_DIRECTORY_PATH;
import static common.Constants.DATABASE_PATH;
import gui.AutoResetGUI;
import gui.HomeScreenGUI;
import gui.InstructionGUI;

import java.io.File;

/**
 * Initial class to run this application.
 * 
 * @author kamlesh
 *
 */
public class FolderLockOrUnlock {
	/**
	 * if all the setup files exists, open home screen
	 * if only verification file exists or only database file exists, open auto-recovery screen
	 * else open the setup screen
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		if (new File(DATABASE_PATH).exists() && new File(APPDATA_DIRECTORY_PATH).exists()) {
			HomeScreenGUI.main(null);
		} else if (!new File(DATABASE_PATH).exists() && new File(APPDATA_DIRECTORY_PATH).exists()) {
			AutoResetGUI.main(new String[] { APPDATA_DIRECTORY_PATH });
		} else if (new File(DATABASE_PATH).exists() && !new File(APPDATA_DIRECTORY_PATH).exists()) {
			AutoResetGUI.main(new String[] { DATABASE_PATH });
		} else {
			InstructionGUI.main(null);
		}
	}
}
