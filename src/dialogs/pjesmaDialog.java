package dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import models.Album;
import models.Pjesma;

import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
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
	 * @throws SQLException 
	 */
	public pjesmaDialog(Pjesma pjesma) throws SQLException {	
		if(pjesma != null)
			this._pjesma = pjesma;
		albumi = Album.GetAlbumi();
		setSize(333, 232);
		setLocationRelativeTo(null);
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

		JComboBox comboAlbumi = new JComboBox(new DefaultComboBoxModel(albumi.toArray()));
		comboAlbumi.setBounds(139, 83, 127, 22);
		contentPanel.add(comboAlbumi);
		comboAlbumi.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof Album){
                	Album album = (Album) value;
                    setText(album.getNaziv());
                }
                return this;
            }
        } );

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
				Album selected = (Album) comboAlbumi.getSelectedItem();
				_pjesma.setTrajanje(txtTrajanje.getText());
				_pjesma.setIdAlbum(selected.getIdAlbum());
				_pjesma.setAlbum(selected.getNaziv());
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
