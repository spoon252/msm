package dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;

import javax.swing.AbstractListModel;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import models.Album;
import models.Izvodjac;
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
import javax.swing.ListCellRenderer;
import javax.swing.ComboBoxModel;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;
public class pjesmaDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNaziv;
	private JTextField txtTrajanje;
	public Pjesma _pjesma = new Pjesma();
	private JButton btnPrihvati = new JButton("Prihvati");
	private JButton btnOtkazi = new JButton("Otkaži");
	public String command = "";
	private List<Album> albumi;
	private DefaultListModel<Izvodjac> list_model = new DefaultListModel<Izvodjac>();

	/***
	 * 
	 * @param pjesma Input argument
	 * @throws SQLException 
	 */
	public pjesmaDialog(Pjesma pjesma) throws SQLException {	
		if(pjesma != null)
			this._pjesma = pjesma;
		albumi = Album.DohvatiAlbume();
		setSize(339, 286);
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
		
		JLabel lblIzvodjac = new JLabel("Izvodjac");
		lblIzvodjac.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIzvodjac.setBounds(66, 116, 46, 14);
		contentPanel.add(lblIzvodjac);
		
		JList listIzvodjaci = new JList(list_model);
		listIzvodjaci.setBorder(new LineBorder(SystemColor.desktop));
		listIzvodjaci.setBackground(SystemColor.control);
		listIzvodjaci.setBounds(139, 116, 127, 87);
		contentPanel.add(listIzvodjaci);	
		List<Izvodjac> izvodjaci = Izvodjac.DohvatiIzvodjace();
		list_model.clear();
		listIzvodjaci.setCellRenderer(new IzvodjacListRender());
		for (Izvodjac izvodjac : izvodjaci) {
			System.out.println(izvodjac.toString());
			list_model.addElement(izvodjac);
		}
		
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

class IzvodjacListRender extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList list, Object value,
           int index, boolean isSelected, boolean cellHasFocus)   {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        Izvodjac item = (Izvodjac) value;
        if(item.getPrezime()!="" && item.getPrezime()!=null)
        	((JLabel) c).setText(item.getIme() + " "+ item.getPrezime());
        else
        	((JLabel) c).setText(item.getIme());   
       return c;
   }
	}
