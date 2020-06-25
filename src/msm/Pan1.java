package msm;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Pan1 extends JPanel {
	private JTextField textField;
	String varijablaString = "sadadadsad";

	/**
	 * Create the panel.
	 */
	public Pan1() {
		setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(175, 111, 89, 23);
		add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(154, 44, 130, 40);
		add(textField);
		textField.setColumns(10);		
	}

}
