package gui;

import static common.Constants.APPLICATION_NAME;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;

import database.PasswordHintTable;
import database.PasswordTable;
/**
 * Reset password
 * 
 * @author kamlesh
 *
 */
public class ResetPasswordGUI {

	private JFrame			frmProtectFolder;
	private JPasswordField	existingPasswordField;
	private JPasswordField	newPasswordField;
	private JPasswordField	confirmPasswordField;
	private JTextField		passwordHintField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ResetPasswordGUI window = new ResetPasswordGUI();
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
	public ResetPasswordGUI() {
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

		AbstractAction action = new AbstractAction() {
			/**
			 * 
			 */
			private static final long	serialVersionUID	= 2850851723750720009L;

			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JButton) {
					JButton button = (JButton) e.getSource();
					button.doClick();
				} else if (e.getSource() instanceof JComponent) {
					JComponent component = (JComponent) e.getSource();
					component.transferFocus();
				}
			}
		};
		JLabel lblResetPassword = new JLabel("Reset Password");
		lblResetPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblResetPassword.setBounds(29, 15, 110, 14);
		frmProtectFolder.getContentPane().add(lblResetPassword);

		JLabel lblExistingPassword = new JLabel("Existing Password*:");
		lblExistingPassword.setBounds(29, 48, 124, 14);
		frmProtectFolder.getContentPane().add(lblExistingPassword);

		JLabel lblNewPassword = new JLabel("New Password*:");
		lblNewPassword.setBounds(29, 83, 124, 14);
		frmProtectFolder.getContentPane().add(lblNewPassword);

		JLabel lblConfirmPassword = new JLabel("Confirm Password*:");
		lblConfirmPassword.setBounds(29, 118, 124, 14);
		frmProtectFolder.getContentPane().add(lblConfirmPassword);

		existingPasswordField = new JPasswordField();
		existingPasswordField.setBounds(163, 45, 190, 20);
		frmProtectFolder.getContentPane().add(existingPasswordField);

		final JToggleButton toggleButtonExistingPassword = new JToggleButton("...");
		toggleButtonExistingPassword.setBounds(363, 45, 20, 20);
		toggleButtonExistingPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (toggleButtonExistingPassword.isSelected()) {
					existingPasswordField.setEchoChar((char) 0);
				} else {
					existingPasswordField.setEchoChar('*');
				}
			}
		});
		frmProtectFolder.getContentPane().add(toggleButtonExistingPassword);

		newPasswordField = new JPasswordField();
		newPasswordField.setBounds(163, 80, 190, 20);
		frmProtectFolder.getContentPane().add(newPasswordField);

		final JToggleButton toggleButtonNewPassword = new JToggleButton("...");
		toggleButtonNewPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (toggleButtonNewPassword.isSelected()) {
					newPasswordField.setEchoChar((char) 0);
				} else {
					newPasswordField.setEchoChar('*');
				}
			}
		});
		toggleButtonNewPassword.setBounds(363, 80, 20, 20);
		frmProtectFolder.getContentPane().add(toggleButtonNewPassword);

		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(163, 115, 190, 20);
		frmProtectFolder.getContentPane().add(confirmPasswordField);

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
		toggleButtonConfirmPassword.setBounds(363, 115, 20, 20);
		frmProtectFolder.getContentPane().add(toggleButtonConfirmPassword);

		JLabel lblPasswordhint = new JLabel("New Password Hint*:");
		lblPasswordhint.setBounds(29, 153, 124, 14);
		frmProtectFolder.getContentPane().add(lblPasswordhint);

		passwordHintField = new JTextField();
		passwordHintField.setBounds(163, 150, 190, 20);
		frmProtectFolder.getContentPane().add(passwordHintField);

		JButton btnDone = new JButton("Done");
		btnDone.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "DoClick");
		btnDone.getActionMap().put("DoClick", action);
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char[] existingPassword = existingPasswordField.getPassword();
				char[] newPassword = newPasswordField.getPassword();
				char[] confirmPassword = confirmPasswordField.getPassword();
				String newPasswordHint = passwordHintField.getText();
				// check all fields has value
				if (newPasswordHint.equals("") || existingPassword.length == 0 || newPassword.length == 0 || confirmPassword.length == 0) {
					JOptionPane.showMessageDialog(null, "Please enter value in all fields.");
				} else if (existingPassword.length < 6 || newPassword.length < 6 || confirmPassword.length < 6) {
					JOptionPane.showMessageDialog(null, "Password should be of minimum 6 characters");
				} else {
					if (PasswordTable.validatePassword(existingPassword)) {
						if (Arrays.equals(newPassword, confirmPassword)) {
							if (Arrays.equals(newPassword, newPasswordHint.toCharArray())) {
								JOptionPane.showMessageDialog(null, "Password hint should not be same as password");
							} else {
								PasswordTable.updatePassword(newPassword);
								PasswordHintTable.updatePasswordHint(newPasswordHint);
								frmProtectFolder.dispose();
								HomeScreenGUI.main(null);
							}
						} else {
							JOptionPane.showMessageDialog(null, "Both the password does not match\nPlease re-enter");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Existing password does not match");
					}
				}

			}
		});
		btnDone.setBounds(262, 181, 89, 23);
		frmProtectFolder.getContentPane().add(btnDone);

		JButton btnBack = new JButton("Back");
		btnBack.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "DoClick");
		btnBack.getActionMap().put("DoClick", action);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmProtectFolder.dispose();
				HelpGUI.main(null);
			}
		});
		btnBack.setBounds(163, 181, 89, 23);
		frmProtectFolder.getContentPane().add(btnBack);

		frmProtectFolder.getRootPane().setDefaultButton(btnDone);

	}
}
