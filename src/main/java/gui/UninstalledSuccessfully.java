package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import common.Init;

public class UninstalledSuccessfully {

	private JFrame		frame;
	private String[]	args;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UninstalledSuccessfully window = new UninstalledSuccessfully(args);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 */
	public UninstalledSuccessfully(String[] args) throws IOException {
		this.args = args;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		// Set Icon to frame
		URL iconURL_Frm = getClass().getResource("/lock-icon-frm.png");
		ImageIcon img = new ImageIcon(iconURL_Frm);
		frame.setIconImage(img.getImage());

		URL iconURL1 = getClass().getResource("/uninstall.png");
		ImageIcon icon = new ImageIcon(iconURL1);
		JLabel lblNewLabel = new JLabel(icon);
		lblNewLabel.setBounds(132, 48, 108, 114);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblUninstalling = new JLabel("Uninstalling...");
		lblUninstalling.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUninstalling.setBounds(27, 27, 135, 14);
		frame.getContentPane().add(lblUninstalling);

		JLabel lblCompleted = new JLabel("Completed!");
		lblCompleted.setVisible(true);
		if (null != args || Init.uninstall()) {
			lblCompleted.setVisible(true);
		}
		lblCompleted.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCompleted.setBounds(250, 28, 99, 14);
		frame.getContentPane().add(lblCompleted);

	}
}
