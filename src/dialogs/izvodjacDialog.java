package dialogs;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import entiteti.Izvodjac;

import javax.swing.JRadioButton;

public class izvodjacDialog extends JDialog {
	private final JPanel contentPanel = new JPanel();
	private JTextField txtIme;
	private JTextField txtPrezime;
	public Izvodjac _izvodjac = new Izvodjac();
	private JButton btnPrihvati = new JButton("Prihvati");
	private JButton btnOtkazi = new JButton("Otkaži");
	public String command = "";
	ButtonGroup G = new ButtonGroup();

	public izvodjacDialog(Izvodjac izvodjac) throws SQLException {	
		if(izvodjac != null)
			this._izvodjac = izvodjac;
		setSize(333, 232);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblIme = new JLabel();
		lblIme.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIme.setBounds(66, 57, 46, 14);
		contentPanel.add(lblIme);

		JLabel lblPrezime = new JLabel("Prezime");
		lblPrezime.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPrezime.setBounds(66, 82, 63, 14);
		contentPanel.add(lblPrezime);

		txtIme = new JTextField();
		txtIme.setBounds(139, 55, 127, 20);
		contentPanel.add(txtIme);
		txtIme.setColumns(10);
		txtIme.setText(_izvodjac.getIme());

		txtPrezime = new JTextField();
		txtPrezime.setColumns(10);
		txtPrezime.setBounds(139, 80, 127, 20);
		contentPanel.add(txtPrezime);
		txtPrezime.setText(_izvodjac.getPrezime());
		
		JRadioButton rbtnSolo = new JRadioButton("Solo");
		rbtnSolo.setBounds(88, 19, 63, 23);
		contentPanel.add(rbtnSolo);
		rbtnSolo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblIme.setText("Ime");
				lblPrezime.setVisible(true);
				txtPrezime.setVisible(true);
				_izvodjac.setTip("Solo");
			}			
		});
		
		JRadioButton rbtnBend = new JRadioButton("Bend");
		rbtnBend.setBounds(160, 19, 63, 23);
		contentPanel.add(rbtnBend);
		
		rbtnBend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblIme.setText("Naziv");
				lblPrezime.setVisible(false);
				txtPrezime.setVisible(false);
				_izvodjac.setTip("Bend");
			}			
		});
		
		G.add(rbtnBend);
		G.add(rbtnSolo);
		if(izvodjac != null) {
			if(izvodjac.getTip() == "Solo")
				rbtnSolo.doClick();
			else
				rbtnBend.doClick();
			rbtnSolo.setEnabled(false);
			rbtnBend.setEnabled(false);
		}		
		else
			rbtnSolo.doClick(); 
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		buttonPane.add(btnPrihvati);
		getRootPane().setDefaultButton(btnPrihvati);
		btnPrihvati.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				command = "OK";
				_izvodjac.setIme(txtIme.getText());
				_izvodjac.setPrezime(txtPrezime.getText());
				izvodjacDialog.this.dispose();
			}			
		});

		buttonPane.add(btnOtkazi);
		btnOtkazi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				command = "Cancel";
				izvodjacDialog.this.dispose();
			}					
		});		
}
}


