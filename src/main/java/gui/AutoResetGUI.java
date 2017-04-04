package gui;

import static common.Constants.APPDATA_DIRECTORY_PATH;
import static common.Constants.DATABASE_PATH;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import common.Init;

/**
 * GUI for auto-reset
 * 
 * @author kamlesh
 *
 */
public class AutoResetGUI {

	private JFrame			frame;
	private static String	resetFile	= null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		resetFile = args[0];
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AutoResetGUI window = new AutoResetGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AutoResetGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		// Set Icon to frame
		URL iconURL_Frm = getClass().getResource("/lock-icon-frm.png");
		ImageIcon img = new ImageIcon(iconURL_Frm);
		frame.setIconImage(img.getImage());

		JLabel lblOopsSomethingWenk = new JLabel("OOPS! something went wrong.");
		lblOopsSomethingWenk.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblOopsSomethingWenk.setBounds(35, 20, 237, 20);
		frame.getContentPane().add(lblOopsSomethingWenk);

		URL iconURL = getClass().getResource("/wheels02.jpg");
		ImageIcon icon = new ImageIcon(iconURL);
		JLabel lblNewLabel = new JLabel(icon);
		lblNewLabel.setBounds(35, 51, 310, 111);
		frame.getContentPane().add(lblNewLabel);

		JButton btnAutoReset = new JButton("Auto Reset");
		btnAutoReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// if only data base files exists
				if (resetFile.equals(DATABASE_PATH)) {
					try {
						Init.resetAppDataFile();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "OOPS! something went wrong, please try again.");
					}
					System.exit(0);
				}
				if (resetFile.equals(APPDATA_DIRECTORY_PATH) && Init.clearAppData()) {
					System.exit(0);
				}
			}
		});

		btnAutoReset.setBounds(246, 173, 99, 23);
		frame.getContentPane().add(btnAutoReset);
		frame.getRootPane().setDefaultButton(btnAutoReset);
	}
}
