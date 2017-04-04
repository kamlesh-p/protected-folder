package gui;

import static common.Constants.ABSOLUTE_PATH;
import static common.Constants.APPLICATION_NAME;
import static common.Constants.LOCK;
import static common.Constants.LOCK_OR_UNLOCK;
import static common.Constants.UNLOCK;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import lock.unlock.LockOrUnlockDirectory;

import common.Commons;

import database.DirectoryTable;

/**
 * choose the folder needs to be locked or unlocked
 * 
 * @author kamlesh
 *
 */
public class FolderToBeLockedOrUnlockedGUI {

	private JFrame		frmProtectFolder;
	final JFileChooser	fc				= new JFileChooser();
	static String[]		COMBOBOX_LIST	= null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FolderToBeLockedOrUnlockedGUI window = new FolderToBeLockedOrUnlockedGUI();
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
	public FolderToBeLockedOrUnlockedGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		if (LOCK_OR_UNLOCK.equals(LOCK)) {
			COMBOBOX_LIST = DirectoryTable.getDirectoryList();
		} else {
			COMBOBOX_LIST = DirectoryTable.getDirectoryListForUnlock();
		}

		frmProtectFolder = new JFrame();
		frmProtectFolder.setBounds(100, 100, 400, 250);
		frmProtectFolder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmProtectFolder.getContentPane().setLayout(null);
		frmProtectFolder.setTitle(APPLICATION_NAME);
		frmProtectFolder.setResizable(false);
		// Set Icon to frame
		URL iconURL = getClass().getResource("/lock-icon-frm.png");
		ImageIcon img = new ImageIcon(iconURL);
		frmProtectFolder.setIconImage(img.getImage());

		AbstractAction action = new AbstractAction() {

			/**
			 * 
			 */
			private static final long	serialVersionUID	= -9174931875032035885L;

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

		JLabel lblFolderToLock = new JLabel("Please select the folder path");
		lblFolderToLock.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFolderToLock.setBounds(39, 20, 263, 14);
		frmProtectFolder.getContentPane().add(lblFolderToLock);

		final JComboBox<String> comboBox = new JComboBox<String>(COMBOBOX_LIST);
		// if (LOCK_OR_UNLOCK.equals(LOCK)) {
		comboBox.setEditable(true);
		// }
		comboBox.setBounds(39, 45, 279, 23);
		comboBox.addActionListener(comboBox);
		frmProtectFolder.getContentPane().add(comboBox);

		final JButton browseBtn = new JButton("Browse");
		if (LOCK_OR_UNLOCK.equals(LOCK)) {
			browseBtn.setEnabled(true);;
		} else {
			browseBtn.setEnabled(false);
		}
		browseBtn.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "DoClick");
		browseBtn.getActionMap().put("DoClick", action);
		browseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Handle open button action.
				if (e.getSource() == browseBtn) {
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int returnVal = fc.showOpenDialog(browseBtn);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						comboBox.setSelectedItem(file.getAbsolutePath());
					}
				}
			}
		});
		browseBtn.setBounds(328, 46, 23, 23);
		frmProtectFolder.getContentPane().add(browseBtn);

		JButton btnNext = new JButton("Next");
		btnNext.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "DoClick");
		btnNext.getActionMap().put("DoClick", action);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (LOCK_OR_UNLOCK.equals(LOCK)) {
					System.out.println(comboBox.getSelectedItem());
					// check is directory exists and not base directory
					if (new File(comboBox.getSelectedItem().toString()).exists()
							&& Commons.isNotBaseDirectory(comboBox.getSelectedItem().toString())) {
						DirectoryTable.insertOrIgnore(comboBox.getSelectedItem().toString());
						DirectoryTable.setAsLocked(comboBox.getSelectedItem().toString());
						try {
							try {
								LockOrUnlockDirectory.lock(comboBox.getSelectedItem().toString());
								frmProtectFolder.dispose();
								LockedSuccessfullyGUI.main(null);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Incorrect directory: " + comboBox.getSelectedItem().toString());
					}
				} else if (LOCK_OR_UNLOCK.equals(UNLOCK)) {
					ABSOLUTE_PATH = comboBox.getSelectedItem().toString();
					DirectoryTable.setAsUnlocked(ABSOLUTE_PATH);
					frmProtectFolder.dispose();
					PasswordGUI.main(null);
				} else {
					// TODO :LOCK action
				}
			}
		});
		btnNext.setBounds(262, 176, 89, 23);
		frmProtectFolder.getContentPane().add(btnNext);

		frmProtectFolder.getRootPane().setDefaultButton(btnNext);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmProtectFolder.dispose();
				HomeScreenGUI.main(null);
			}
		});
		btnBack.setBounds(151, 176, 89, 23);
		frmProtectFolder.getContentPane().add(btnBack);

	}
}
