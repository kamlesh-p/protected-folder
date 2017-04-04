package gui;

import static common.Constants.FORGOT_PASSWORD;
import static common.Constants.UNINSTALL;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.KeyStroke;

/**
 * Help screen shows option to perform below actions:
 * 1. Reset Password
 * 2. Forgot Password
 * 3. UnInstall
 * 
 * @author kamlesh
 *
 */
public class HelpGUI {

	private JFrame	frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HelpGUI window = new HelpGUI();
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
	public HelpGUI() {
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

		JLabel lblHelp = new JLabel("Help:");
		lblHelp.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblHelp.setBounds(40, 23, 46, 14);
		frame.getContentPane().add(lblHelp);

		AbstractAction action = new AbstractAction() {
			/**
			 * 
			 */
			private static final long	serialVersionUID	= -2859249038075476850L;

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

		JButton btnResetPswd = new JButton("Reset Password");
		btnResetPswd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				ResetPasswordGUI.main(null);
			}
		});
		btnResetPswd.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "DoClick");
		btnResetPswd.getActionMap().put("DoClick", action);
		btnResetPswd.setBounds(40, 50, 295, 23);
		frame.getContentPane().add(btnResetPswd);

		JButton btnForgotPswd = new JButton("Forgot Password");
		btnForgotPswd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// forgot password logic
				FORGOT_PASSWORD = true;
				frame.dispose();
				SecurityQuestionsGUI.main(null);
			}
		});
		btnForgotPswd.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "DoClick");
		btnForgotPswd.getActionMap().put("DoClick", action);
		btnForgotPswd.setBounds(40, 85, 295, 23);
		frame.getContentPane().add(btnForgotPswd);

		JButton btnUnisatall = new JButton("Unisatall");
		btnUnisatall.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "DoClick");
		btnUnisatall.getActionMap().put("DoClick", action);
		btnUnisatall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UNINSTALL = true;
				frame.dispose();
				UninstalledSuccessfully.main(null);
			}
		});
		btnUnisatall.setBounds(40, 120, 295, 23);
		frame.getContentPane().add(btnUnisatall);

		JButton btnBack = new JButton("Back");
		btnBack.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "DoClick");
		btnBack.getActionMap().put("DoClick", action);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				HomeScreenGUI.main(null);
			}
		});
		btnBack.setBounds(246, 165, 89, 23);
		frame.getContentPane().add(btnBack);
	}
}
