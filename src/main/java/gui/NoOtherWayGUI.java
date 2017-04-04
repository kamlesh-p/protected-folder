package gui;

import static common.Constants.APPLICATION_NAME;

import java.awt.EventQueue;
import java.awt.Font;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * instructions in case the user is unable to unlock or forgot password and
 * recovery answers
 * 
 * 
 * @author kamlesh
 *
 */
public class NoOtherWayGUI {

	private JFrame	frmProtectFolder;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NoOtherWayGUI window = new NoOtherWayGUI();
					window.frmProtectFolder.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public NoOtherWayGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmProtectFolder = new JFrame();
		frmProtectFolder.setBounds(100, 100, 400, 250);
		frmProtectFolder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmProtectFolder.getContentPane().setLayout(null);
		frmProtectFolder.setResizable(false);
		frmProtectFolder.setTitle(APPLICATION_NAME);
		// Set Icon to frame
		URL iconURL = getClass().getResource("/lock-icon-frm.png");
		ImageIcon img = new ImageIcon(iconURL);
		frmProtectFolder.setIconImage(img.getImage());

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 49, 336, 129);
		frmProtectFolder.getContentPane().add(scrollPane);

		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.append("It looks like you have trouble:\n\n"
				+ "If you have reached this screen by coincidence, you can ignore and close this window.\n\n"
				+ "If you are not the intended user, please close the window. "
				+ "The files you are trying to reach would be important for the user.\n\n"
				+ "If you have trouble unlocking the files, "
				+ "you can follow below instructions to unhide all the directories from command prompt window.\n"
				+ "This will also unhide some system directories, you should hide them again to avoid any problems.\n\n"
				+ "NOTE: \"All your folders will start with same name and will be appended with some additional characters.\"\n\n"
				+ "Here is the procedure to unhide your files:\n"
				+ "1. Open the cmd window in the directory:\n"
				+ "> Go to directory and press \"SHIFT + Right click\"\n"
				+ "> Select option- \"Open command window here\".\n\n"
				+ "2. Type the below command:\n"
				+ "   attrib -s -h -r /s /d\n\n"
				+ "3. click ENTER\n\n"
				+ "Hope it helps.");
		textArea.setCaretPosition(0);
		scrollPane.setViewportView(textArea);

		JLabel lblHelp = new JLabel("Help:");
		lblHelp.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblHelp.setBounds(30, 24, 64, 14);
		frmProtectFolder.getContentPane().add(lblHelp);
	}
}
