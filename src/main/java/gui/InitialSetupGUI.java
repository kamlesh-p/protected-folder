package gui;

import static common.Constants.APPLICATION_NAME;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import common.Init;

/**
 * Initial setup GUI screen
 * 
 * @author kamlesh
 *
 */

public class InitialSetupGUI {

	private JFrame		frmProtectFolder;
	private JTextField	txtCprogramFilesprotectfolder;
	private JButton		btnNext;

	/**
	 * Launch the application.
	 */
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InitialSetupGUI window = new InitialSetupGUI();
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
	public InitialSetupGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmProtectFolder = new JFrame();
		frmProtectFolder.setTitle(APPLICATION_NAME);
		frmProtectFolder.setBounds(100, 100, 400, 250);
		frmProtectFolder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmProtectFolder.getContentPane().setLayout(null);
		frmProtectFolder.setResizable(false);
		// Set Icon to frame
		URL iconURL = getClass().getResource("/lock-icon-frm.png");
		ImageIcon img = new ImageIcon(iconURL);
		frmProtectFolder.setIconImage(img.getImage());

		JLabel lblNewLabel = new JLabel("Preparing initial setup");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(40, 23, 170, 23);
		frmProtectFolder.getContentPane().add(lblNewLabel);

		txtCprogramFilesprotectfolder = new JTextField();
		txtCprogramFilesprotectfolder.setBackground(Color.WHITE);
		txtCprogramFilesprotectfolder.setText("%UserProfile%\\ProtectFolder\\bin");
		txtCprogramFilesprotectfolder.setEditable(false);
		txtCprogramFilesprotectfolder.setBounds(40, 64, 311, 20);
		frmProtectFolder.getContentPane().add(txtCprogramFilesprotectfolder);
		txtCprogramFilesprotectfolder.setColumns(10);

		btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Init.setup();
					frmProtectFolder.dispose();
					SetPasswordGUI.main(null);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}

			}
		});
		btnNext.setBounds(262, 165, 89, 23);
		frmProtectFolder.getContentPane().add(btnNext);
		frmProtectFolder.getRootPane().setDefaultButton(btnNext);
	}

}
