package msm;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class MsmFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField textField;
	/**
	 * Create the frame.
	 */
	public MsmFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 477);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
		Pan1 panel = new Pan1();
		tabbedPane.addTab("Pjesme", null, panel, null);
		panel.setLayout(null);
		
		table = new JTable();
		table.setBounds(25, 303, 399, -274);
		panel.add(table);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(67, 84, 30, 22);
		panel.add(comboBox);

		
		textField = new JTextField();
		textField.setBounds(107, 72, 86, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Izvodjaci", null, panel_1, null);
		panel_1.setLayout(null);
	}
}
