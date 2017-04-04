package gui;

import static common.Constants.APPLICATION_NAME;
import static common.Constants.INCORRECT_PASSWORD_COUNT;
import static common.Constants.UNINSTALL;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import lock.unlock.LockOrUnlockDirectory;

import common.Init;

import database.PasswordHintTable;
import database.PasswordTable;

public class PasswordGUI {

	private JFrame			frmProtectFolder;
	private JPasswordField	passwordField;
	private JButton			btnNext;
	private JButton			btnResetPassword;
	private JLabel			lblPasswordHint;
	private JLabel			lblCapsLockIs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PasswordGUI window = new PasswordGUI();
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
	public PasswordGUI() {
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
			/** generated serial version Id */
			private static final long	serialVersionUID	= 4003046123748550278L;

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

		final JButton btnHelp = new JButton("Help!");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmProtectFolder.dispose();
				NoOtherWayGUI.main(null);
			}
		});
		btnHelp.setVisible(false);
		btnHelp.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "DoClick");
		btnHelp.getActionMap().put("DoClick", action);

		btnHelp.setBounds(258, 84, 89, 23);
		frmProtectFolder.getContentPane().add(btnHelp);

		JLabel lblPleaseEnterPassword = new JLabel("Please enter password");
		lblPleaseEnterPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPleaseEnterPassword.setBounds(43, 26, 203, 14);
		frmProtectFolder.getContentPane().add(lblPleaseEnterPassword);

		lblPasswordHint = new JLabel("");
		lblPasswordHint.setBounds(43, 121, 304, 22);
		lblPasswordHint.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		frmProtectFolder.getContentPane().add(lblPasswordHint);
		lblPasswordHint.setVisible(false);

		passwordField = new JPasswordField();
		passwordField.addKeyListener(new keyListnerClass());
		passwordField.setBounds(43, 51, 304, 22);
		frmProtectFolder.getContentPane().add(passwordField);

		btnNext = new JButton("Next");
		btnNext.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "DoClick");
		btnNext.getActionMap().put("DoClick", action);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char[] password = passwordField.getPassword();

				if (password.length == 0) {
					JOptionPane.showMessageDialog(null, "Please enter the password");
				} else {
					if (password.length < 6) {
						JOptionPane.showMessageDialog(null, "Password should be of minimum 6 characters.");
					} else {
						// if uninstall is true; verify password and
						// unlock all folders and
						// remove all installation files
						try {
							if (UNINSTALL == true && PasswordTable.validatePassword(password)) {
								Init.UnlockDirectories();
								frmProtectFolder.dispose();
								UninstalledSuccessfully.main(new String[] { "uninstalled" });
							}
							// validates and unlock if password matches
							else if (UNINSTALL != true && LockOrUnlockDirectory.verifyPaawordAndUnlock(password)) {
								frmProtectFolder.dispose();
								UnlockedSuccessfullyGUI.main(null);
							} else {
								INCORRECT_PASSWORD_COUNT++;
								JOptionPane.showMessageDialog(null, "Incorrect Password");
								if (INCORRECT_PASSWORD_COUNT >= 2) {
									// give password hint
									lblPasswordHint.setVisible(true);
									lblPasswordHint.setText("Password Hint : " + PasswordHintTable.getPasswordHint());;
								}
								if (INCORRECT_PASSWORD_COUNT % 3 == 0) {
									URL iconURL = getClass().getResource("/red-icon-lock.png");
									ImageIcon img = new ImageIcon(iconURL);
									frmProtectFolder.setIconImage(img.getImage());
									frmProtectFolder.dispose();
									TimerGUI.main(null);
								}
								if (INCORRECT_PASSWORD_COUNT == 10) {
									btnHelp.setVisible(true);
								}
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		btnNext.setBounds(258, 165, 89, 23);
		frmProtectFolder.getContentPane().add(btnNext);

		btnResetPassword = new JButton("Reset Password");
		btnResetPassword.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "DoClick");
		btnResetPassword.getActionMap().put("DoClick", action);
		btnResetPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmProtectFolder.dispose();
				ResetPasswordGUI.main(null);
			}
		});
		btnResetPassword.setBounds(43, 165, 128, 23);
		frmProtectFolder.getContentPane().add(btnResetPassword);

		frmProtectFolder.getRootPane().setDefaultButton(btnNext);

		lblCapsLockIs = new JLabel("CapsLock is on!");
		lblCapsLockIs.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCapsLockIs.setForeground(Color.BLUE);
		lblCapsLockIs.setVisible(false);
		lblCapsLockIs.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblCapsLockIs.setBounds(43, 84, 109, 18);
		frmProtectFolder.getContentPane().add(lblCapsLockIs);

	}

	class keyListnerClass implements KeyListener {

		/** Handle the key typed event from the text field. */
		public void keyTyped(KeyEvent e) {
			// displayInfo(e);
		}

		/** Handle the key pressed event from the text field. */
		public void keyPressed(KeyEvent e) {
			displayInfo(e);
		}

		/** Handle the key released event from the text field. */
		public void keyReleased(KeyEvent e) {
			// displayInfo(e);
		}

		protected void displayInfo(KeyEvent e) {

			int keyCode = e.getKeyCode();
			if (keyCode == 20) {
				if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)) {
					lblCapsLockIs.setVisible(true);
				} else {
					lblCapsLockIs.setVisible(false);
				}
			}
		}
	}
}
