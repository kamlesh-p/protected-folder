package gui;

import static common.Constants.APPLICATION_NAME;
import static common.Constants.LOCK;
import static common.Constants.LOCK_OR_UNLOCK;
import static common.Constants.UNLOCK;

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
 * HomeScreen: GUI of initial screen containing below operations:
 * 1. Lock folder
 * 2. Unlock folder
 * 3. Help
 * 
 * @author kamlesh
 *
 */
public class HomeScreenGUI {

	private JFrame	frmProtectFolder;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeScreenGUI window = new HomeScreenGUI();
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
	public HomeScreenGUI() {
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
			private static final long	serialVersionUID	= -1805296475343176517L;

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

		JButton btnLock = new JButton("LOCK");
		btnLock.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "DoClick");
		btnLock.getActionMap().put("DoClick", action);
		btnLock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LOCK_OR_UNLOCK = LOCK;
				frmProtectFolder.dispose();
				FolderToBeLockedOrUnlockedGUI.main(null);
			}
		});
		btnLock.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLock.setBounds(40, 69, 150, 73);
		frmProtectFolder.getContentPane().add(btnLock);

		JButton btnUnlock = new JButton("UNLOCK");
		btnUnlock.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "DoClick");
		btnUnlock.getActionMap().put("DoClick", action);
		btnUnlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LOCK_OR_UNLOCK = UNLOCK;
				frmProtectFolder.dispose();
				FolderToBeLockedOrUnlockedGUI.main(null);
			}
		});
		btnUnlock.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnUnlock.setBounds(206, 69, 150, 73);
		frmProtectFolder.getContentPane().add(btnUnlock);

		JLabel lblNewLabel = new JLabel("Please select the option to be performed:");
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblNewLabel.setBounds(40, 20, 287, 23);
		frmProtectFolder.getContentPane().add(lblNewLabel);

		JButton btnUninstall = new JButton("Help");
		btnUninstall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmProtectFolder.dispose();
				HelpGUI.main(null);
			}
		});
		btnUninstall.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnUninstall.setBounds(206, 165, 150, 23);
		frmProtectFolder.getContentPane().add(btnUninstall);

		URL iconURL1 = getClass().getResource("/home.jpg");
		ImageIcon icon = new ImageIcon(iconURL1);
		JLabel lblNewLabel_1 = new JLabel(icon);
		lblNewLabel_1.setBounds(335, 20, 21, 21);
		frmProtectFolder.getContentPane().add(lblNewLabel_1);

	}
}
