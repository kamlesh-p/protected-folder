package gui;

import static common.Constants.SECURITY_QUESTION_01;
import static common.Constants.SECURITY_QUESTION_02;
import static common.Constants.SECURITY_QUESTION_03;
import static common.Constants.SECURITY_QUESTION_04;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import common.Constants;

import database.SecurityQuestionsTable;

/**
 * security questions, to recover password
 * 
 * @author kamlesh
 *
 */
public class SecurityQuestionsGUI {

	private JFrame		frame;
	private JTextField	textField_SQ01_NickName;
	private JTextField	textField_SQ03_FavouriteFood;
	private JTextField	textField_SQ04_FavouriteFilm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SecurityQuestionsGUI window = new SecurityQuestionsGUI();
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
	public SecurityQuestionsGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 370);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		// Set Icon to frame
		URL iconURL_Frm = getClass().getResource("/lock-icon-frm.png");
		ImageIcon img = new ImageIcon(iconURL_Frm);
		frame.setIconImage(img.getImage());

		JLabel lblWhatIsYour = new JLabel(SECURITY_QUESTION_01);
		lblWhatIsYour.setBounds(30, 50, 295, 14);
		frame.getContentPane().add(lblWhatIsYour);

		textField_SQ01_NickName = new JTextField();
		textField_SQ01_NickName.setBounds(30, 75, 318, 20);
		frame.getContentPane().add(textField_SQ01_NickName);
		textField_SQ01_NickName.setColumns(10);

		JLabel lblSecurityQuestionto = new JLabel("Security question (It's needed, if you forget password)");
		lblSecurityQuestionto.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSecurityQuestionto.setBounds(10, 11, 347, 14);
		frame.getContentPane().add(lblSecurityQuestionto);

		JLabel lblYourDateOf = new JLabel(SECURITY_QUESTION_02);
		lblYourDateOf.setBounds(30, 106, 295, 14);
		frame.getContentPane().add(lblYourDateOf);

		UtilDateModel model = new UtilDateModel();
		model.setDate(1990, 00, 01);
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		datePanel.setBounds(30, 143, 295, 20);
		final JDatePickerImpl datePicker_SQ02 = new JDatePickerImpl(datePanel);
		// JFormattedTextField textField = datePicker.getJFormattedTextField();
		// textField.setBounds(30, 143, 295, 20);
		datePicker_SQ02.setBounds(30, 131, 318, 26);
		frame.getContentPane().add(datePicker_SQ02);

		textField_SQ03_FavouriteFood = new JTextField();
		textField_SQ03_FavouriteFood.setBounds(30, 193, 318, 20);
		frame.getContentPane().add(textField_SQ03_FavouriteFood);
		textField_SQ03_FavouriteFood.setColumns(10);

		JLabel lblNewLabel = new JLabel(SECURITY_QUESTION_03);
		lblNewLabel.setBounds(30, 168, 295, 14);
		frame.getContentPane().add(lblNewLabel);

		textField_SQ04_FavouriteFilm = new JTextField();
		textField_SQ04_FavouriteFilm.setBounds(30, 249, 318, 20);
		frame.getContentPane().add(textField_SQ04_FavouriteFilm);
		textField_SQ04_FavouriteFilm.setColumns(10);

		JLabel lblYourFavouriteNumber = new JLabel(SECURITY_QUESTION_04);
		lblYourFavouriteNumber.setBounds(30, 224, 295, 14);
		frame.getContentPane().add(lblYourFavouriteNumber);

		JLabel lblPleaseRemenberThe = new JLabel("Please remember the answers.");
		lblPleaseRemenberThe.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPleaseRemenberThe.setBounds(30, 289, 188, 14);
		frame.getContentPane().add(lblPleaseRemenberThe);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println();

				if (textField_SQ01_NickName.getText().equals("") ||
						textField_SQ03_FavouriteFood.getText().equals("") ||
						textField_SQ04_FavouriteFilm.getText().equals("") ||
						datePicker_SQ02.getJFormattedTextField().getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter value in all fields");
				} else if (Constants.FORGOT_PASSWORD == true) {
					if (SecurityQuestionsTable.readAndVerifySecurityAnswer(SECURITY_QUESTION_01, textField_SQ01_NickName.getText())
							&& SecurityQuestionsTable.readAndVerifySecurityAnswer(SECURITY_QUESTION_02, datePicker_SQ02.getJFormattedTextField().getText())
							&& SecurityQuestionsTable.readAndVerifySecurityAnswer(SECURITY_QUESTION_03, textField_SQ03_FavouriteFood.getText())
							&& SecurityQuestionsTable.readAndVerifySecurityAnswer(SECURITY_QUESTION_04, textField_SQ04_FavouriteFilm.getText())) {
						frame.dispose();
						SetPasswordGUI.main(null);
					} else {
						JOptionPane.showMessageDialog(null, "Answers does not match!");
					}

				}

				else {
					// save security data
					SecurityQuestionsTable.insert(SECURITY_QUESTION_01, textField_SQ01_NickName.getText());
					SecurityQuestionsTable.insert(SECURITY_QUESTION_02, datePicker_SQ02.getJFormattedTextField().getText());
					SecurityQuestionsTable.insert(SECURITY_QUESTION_03, textField_SQ03_FavouriteFood.getText());
					SecurityQuestionsTable.insert(SECURITY_QUESTION_04, textField_SQ04_FavouriteFilm.getText());
					frame.dispose();
					HomeScreenGUI.main(null);
				}

			}
		});
		btnOk.setBounds(259, 286, 89, 23);
		frame.getContentPane().add(btnOk);

	}
}
