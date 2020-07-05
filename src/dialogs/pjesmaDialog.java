package dialogs;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import models.Album;
import models.Pjesma;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;

public class pjesmaDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNaziv;
	private JTextField txtTrajanje;
	public Pjesma _pjesma = new Pjesma();
	private JButton btnPrihvati = new JButton("Prihvati");
	private JButton btnOtkazi = new JButton("Otkaži");
	public String command = "";
	private List<Album> albumi;

	/***
	 * 
	 * @param pjesma Input argument
	 */
	public pjesmaDialog(Pjesma pjesma) {	
		if(pjesma != null)
			this._pjesma = pjesma;
		setBounds(100, 100, 333, 232);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Naziv");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(66, 23, 46, 14);
		contentPanel.add(lblNewLabel);

		JLabel lblTrajanjes = new JLabel("Trajanje (s)");
		lblTrajanjes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTrajanjes.setBounds(66, 54, 63, 14);
		contentPanel.add(lblTrajanjes);

		JLabel lblAlbum = new JLabel("Album");
		lblAlbum.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAlbum.setBounds(66, 86, 63, 14);
		contentPanel.add(lblAlbum);

		JLabel lblSpot = new JLabel("Spot");
		lblSpot.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSpot.setBounds(66, 118, 63, 14);
		contentPanel.add(lblSpot);

		JComboBox comboAlbumi = new JComboBox();
		comboAlbumi.setBounds(139, 83, 127, 22);
		contentPanel.add(comboAlbumi);

		JComboBox comboSpot = new JComboBox();
		comboSpot.setBounds(139, 115, 127, 22);
		contentPanel.add(comboSpot);

		txtNaziv = new JTextField();
		txtNaziv.setBounds(139, 21, 127, 20);
		contentPanel.add(txtNaziv);
		txtNaziv.setColumns(10);
		txtNaziv.setText(_pjesma.getNaziv());

		txtTrajanje = new JTextField();
		txtTrajanje.setColumns(10);
		txtTrajanje.setBounds(139, 52, 127, 20);
		contentPanel.add(txtTrajanje);
		txtTrajanje.setText(_pjesma.getTrajanje());
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		buttonPane.add(btnPrihvati);
		getRootPane().setDefaultButton(btnPrihvati);
		btnPrihvati.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				command = "OK";
				_pjesma.setTrajanje(txtTrajanje.getText());
				_pjesma.setNaziv(txtNaziv.getText());				
				pjesmaDialog.this.dispose();
			}			
		});

		buttonPane.add(btnOtkazi);
		btnOtkazi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				command = "Cancel";
				pjesmaDialog.this.dispose();
			}					
		});		
	}
}
