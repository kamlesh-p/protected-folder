package gui;

import java.awt.EventQueue;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

public class TimerGUI {

	private JFrame	frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final TimerGUI window = new TimerGUI();
					window.frame.setVisible(true);

					new Timer().schedule(new TimerTask() {
						int	second	= 5;

						@SuppressWarnings("deprecation")
						@Override
						public void run() {
							window.frame.setTitle("Wait " + second-- + " seconds.");
							if (second <= 0) {
								window.frame.dispose();
								PasswordGUI.main(null);
								Thread.currentThread().stop();
							}
						}
					}, 0, 1000);

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
	public TimerGUI() throws InterruptedException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws InterruptedException
	 */
	private void initialize() throws InterruptedException {
		frame = new JFrame();
		frame.setBounds(100, 100, 300, 200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		// Set Icon to frame
		URL iconURL = getClass().getResource("/red-icon-lock.png");
		ImageIcon img = new ImageIcon(iconURL);
		frame.setIconImage(img.getImage());

		final JLabel lblPleaseTryAfter = new JLabel("                      Please try again!");
		lblPleaseTryAfter.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblPleaseTryAfter.setBounds(23, 22, 234, 116);
		frame.getContentPane().add(lblPleaseTryAfter);

	}
}
