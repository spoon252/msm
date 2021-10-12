package dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import dodaci.DateLabelFormatter;
import entiteti.Osoba;

public class OsobaDialog extends JDialog {
	private final JPanel contentPanel = new JPanel();
	private JTextField txtIme;
	private JTextField txtPrezime;
	public Osoba _osoba = new Osoba();
	private JButton btnPrihvati = new JButton("Prihvati");
	private JButton btnOtkazi = new JButton("Otkaži");
	public String command = "";
	ButtonGroup G = new ButtonGroup();

	public OsobaDialog(Osoba osoba) throws SQLException {	
		if(osoba != null)
			this._osoba = osoba;
		setSize(333, 232);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		
		UtilDateModel model = new UtilDateModel();
		model.setDate(1990, 8, 24);
		model.setSelected(true);
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		
		JLabel lblIme = new JLabel();
		lblIme.setText("Ime");
		lblIme.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIme.setBounds(35, 24, 46, 14);
		contentPanel.add(lblIme);
		JLabel lblPrezime = new JLabel("Prezime");
		lblPrezime.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPrezime.setBounds(35, 59, 63, 14);
		contentPanel.add(lblPrezime);

		txtIme = new JTextField();
		txtIme.setBounds(108, 22, 127, 20);
		contentPanel.add(txtIme);
		txtIme.setColumns(10);
		txtIme.setText(_osoba.getIme());

		txtPrezime = new JTextField();
		txtPrezime.setColumns(10);
		txtPrezime.setBounds(108, 57, 127, 20);
		contentPanel.add(txtPrezime);
		txtPrezime.setText(_osoba.getPrezime());
		
		JLabel lblRodjendan = new JLabel("Datum rodjenja");
		lblRodjendan.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblRodjendan.setBounds(10, 98, 99, 14);
		contentPanel.add(lblRodjendan);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(108, 96, 151, 33);
		contentPanel.add(datePicker);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		buttonPane.add(btnPrihvati);
		getRootPane().setDefaultButton(btnPrihvati);
		btnPrihvati.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				command = "OK";
				_osoba.setIme(txtIme.getText());
				_osoba.setPrezime(txtPrezime.getText());
				_osoba.setDatumRodjenja((Date) datePicker.getModel().getValue());
				OsobaDialog.this.dispose();
			}			
		});

		buttonPane.add(btnOtkazi);
		btnOtkazi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				command = "Cancel";
				OsobaDialog.this.dispose();
			}					
		});		
}
}


