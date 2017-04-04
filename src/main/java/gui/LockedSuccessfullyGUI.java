package gui;

import static common.Constants.APPLICATION_NAME;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * GUI shown on successfully locking a folder
 * 
 * @author kamlesh
 *
 */
public class LockedSuccessfullyGUI {

	private JFrame	frmProtectFolder;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LockedSuccessfullyGUI window = new LockedSuccessfullyGUI();
					window.frmProtectFolder.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws InterruptedException
	 */
	public LockedSuccessfullyGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws InterruptedException
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

		JLabel lblFolderLockedSuccessfully = new JLabel("Folder Locked Successfully");
		lblFolderLockedSuccessfully.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFolderLockedSuccessfully.setBounds(25, 27, 200, 25);
		frmProtectFolder.getContentPane().add(lblFolderLockedSuccessfully);

		URL iconURL1 = getClass().getResource("/Lock.png");
		ImageIcon icon = new ImageIcon(iconURL1);
		JLabel label = new JLabel(icon);
		label.setBounds(143, 77, 127, 122);
		frmProtectFolder.getContentPane().add(label);

		URL iconURL2 = getClass().getResource("/home.jpg");
		ImageIcon icon2 = new ImageIcon(iconURL2);
		JLabel lblHomeIcon = new JLabel(icon2);
		lblHomeIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblHomeIcon.setBounds(330, 158, 21, 21);
		lblHomeIcon.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e) {
				frmProtectFolder.dispose();
				HomeScreenGUI.main(null);
			}
		});
		frmProtectFolder.getContentPane().add(lblHomeIcon);
	}
}
