package gui;

import static common.Constants.APPLICATION_NAME;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 * Instructions GUI 
 * 
 * @author kamlesh
 *
 */
public class InstructionGUI {

	private JFrame	frmProtectFolder;

	/**
	 * Launch the application.
	 */
	public static void main(String agrs[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InstructionGUI window = new InstructionGUI();
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
	public InstructionGUI() {
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

		JLabel lblInitialSetup = new JLabel("Instructions");
		lblInitialSetup.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblInitialSetup.setBounds(20, 11, 190, 22);
		frmProtectFolder.getContentPane().add(lblInitialSetup);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 44, 355, 108);
		frmProtectFolder.getContentPane().add(scrollPane);

		JTextArea txtrThisIsAn = new JTextArea();
		txtrThisIsAn.setLineWrap(true);
		txtrThisIsAn.setWrapStyleWord(true);
		txtrThisIsAn.setEditable(false);
		txtrThisIsAn.setBackground(Color.WHITE);
		txtrThisIsAn
				.append("This application provides the feature to protect your files in your system "
						+ "by using a password protection to folder/directory."
						+ "\n\nFeatures:\n"
						+ "1. Password protected folder.\n"
						+ "2. Hide the folder, will be visible only when unlocked.\n\n"
						+ "This software is provided 'as-is', without any "
						+ "express or implied warrenty.\n"
						+ "In no event will the authors be held liable "
						+ "for any damages arising from the use of this software");
		txtrThisIsAn.setCaretPosition(0);;
		scrollPane.setViewportView(txtrThisIsAn);

		final JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmProtectFolder.dispose();
				InitialSetupGUI.main(null);
			}
		});
		btnNext.setEnabled(false);
		btnNext.setBounds(286, 165, 89, 23);
		frmProtectFolder.getContentPane().add(btnNext);

		final JRadioButton rdbtnIAgree = new JRadioButton("I agree");
		rdbtnIAgree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnIAgree.isSelected()) {
					btnNext.setEnabled(true);
				} else {
					btnNext.setEnabled(false);
				}
			}
		});
		rdbtnIAgree.setBounds(19, 163, 121, 23);
		frmProtectFolder.getContentPane().add(rdbtnIAgree);

		frmProtectFolder.getRootPane().setDefaultButton(btnNext);
	}
}
