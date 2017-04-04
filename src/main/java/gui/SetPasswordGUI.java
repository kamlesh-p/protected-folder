package gui;

import static common.Constants.APPLICATION_NAME;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import common.Constants;

import database.PasswordHintTable;
import database.PasswordTable;

/**
 * set password for the first time.
 * 
 * @author kamlesh
 *
 */
public class SetPasswordGUI {

	private JFrame			frmProtectFolder;
	private JPasswordField	newPasswordField;
	private JPasswordField	confirmPasswordField;
	private JTextField		passwordHintTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SetPasswordGUI window = new SetPasswordGUI();
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
	public SetPasswordGUI() {
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

		JLabel lblEnterNewPassword = new JLabel("Enter new password (minium 6 characters)");
		lblEnterNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEnterNewPassword.setBounds(30, 11, 318, 14);
		frmProtectFolder.getContentPane().add(lblEnterNewPassword);

		JLabel lblNewPassword = new JLabel("New Password*:");
		lblNewPassword.setBounds(30, 53, 102, 14);
		frmProtectFolder.getContentPane().add(lblNewPassword);

		JLabel lblConfirmPassword = new JLabel("Confirm Password*:");
		lblConfirmPassword.setBounds(30, 93, 118, 14);
		frmProtectFolder.getContentPane().add(lblConfirmPassword);

		newPasswordField = new JPasswordField();
		newPasswordField.setBounds(158, 50, 194, 20);
		frmProtectFolder.getContentPane().add(newPasswordField);

		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(158, 90, 194, 20);
		frmProtectFolder.getContentPane().add(confirmPasswordField);

		JButton btnDone = new JButton("Done");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char[] newPassword = newPasswordField.getPassword();
				char[] confirmPassword = confirmPasswordField.getPassword();
				String passwordHint = passwordHintTextField.getText();
				System.out.println(newPassword.length == 0);
				// all fields are mandatory
				if (passwordHint.equals("") || newPassword.length == 0 || confirmPassword.length == 0) {
					JOptionPane.showMessageDialog(null, "Please enter value in all fields");
				} else {
					// password should be minimum 6 digits
					if (newPassword.length < 6 || confirmPassword.length < 6) {
						JOptionPane.showMessageDialog(null, "Password should be of minimum 6 characters.");
					} else {
						if (Arrays.equals(newPassword, confirmPassword)) {
							if (Arrays.equals(newPassword, passwordHint.toCharArray())) {
								JOptionPane.showMessageDialog(null, "Password hint should not be same as password");
							} else if (Constants.FORGOT_PASSWORD == true) {
								PasswordTable.insert(newPassword);
								PasswordHintTable.insertPasswordHint(passwordHint);
								frmProtectFolder.dispose();
								HomeScreenGUI.main(null);
							} else {
								PasswordTable.insert(newPassword);
								PasswordHintTable.insertPasswordHint(passwordHint);
								frmProtectFolder.dispose();
								SecurityQuestionsGUI.main(null);
							}
						} else {
							JOptionPane.showMessageDialog(null, "Both the password does not match\nPlease re-enter");
						}
					}
				}
			}
		});
		btnDone.setBounds(263, 165, 89, 23);
		frmProtectFolder.getContentPane().add(btnDone);

		JLabel lblPasswordHint = new JLabel("Password Hint*:");
		lblPasswordHint.setBounds(30, 133, 102, 14);
		frmProtectFolder.getContentPane().add(lblPasswordHint);

		passwordHintTextField = new JTextField();
		passwordHintTextField.setBounds(158, 130, 194, 20);
		frmProtectFolder.getContentPane().add(passwordHintTextField);
		passwordHintTextField.setColumns(10);

		frmProtectFolder.getRootPane().setDefaultButton(btnDone);

		final JToggleButton toggleButtonPassword = new JToggleButton("...");
		toggleButtonPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (toggleButtonPassword.isSelected()) {
					newPasswordField.setEchoChar((char) 0);
				} else {
					newPasswordField.setEchoChar('*');
				}
			}
		});
		toggleButtonPassword.setBounds(365, 50, 20, 20);
		frmProtectFolder.getContentPane().add(toggleButtonPassword);

		final JToggleButton toggleButtonConfirmPassword = new JToggleButton("...");
		toggleButtonConfirmPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (toggleButtonConfirmPassword.isSelected()) {
					confirmPasswordField.setEchoChar((char) 0);
				} else {
					confirmPasswordField.setEchoChar('*');
				}
			}
		});
		toggleButtonConfirmPassword.setBounds(365, 95, 20, 20);
		frmProtectFolder.getContentPane().add(toggleButtonConfirmPassword);

	}
}
