package dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import entiteti.Album;
import entiteti.Izvodjac;
import entiteti.Pjesma;
import modeli.OsobaListRender;

public class PjesmaDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNaziv;
	private JTextField txtTrajanje;
	public Pjesma _pjesma = new Pjesma();
	private JButton btnPrihvati = new JButton("Prihvati");
	private JButton btnOtkazi = new JButton("Otkaži");
	public String command = "";
	private List<Album> albumi;
	private DefaultListModel<Izvodjac> list_selected = new DefaultListModel<Izvodjac>();
	private DefaultListModel<Izvodjac> list_available = new DefaultListModel<Izvodjac>();
	private JList listDostupniIzvodjaci;
	private JList listSelektovaniIzvodjaci;
	public List<Integer> id_izvodjaci;

	/***
	 * 
	 * @param pjesma Input argument
	 * @throws SQLException
	 */
	public PjesmaDialog(Pjesma pjesma, List<Izvodjac> izvodjaci) throws SQLException {
		inicijalizirajListe(izvodjaci);
		albumi = Album.dohvatiAlbume();

		setSize(343, 380);
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
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value instanceof Album) {
					Album album = (Album) value;
					setText(album.getNaziv());
				}
				return this;
			}
		});
		
		if (pjesma != null) {
			_pjesma = pjesma;
			comboAlbumi.setSelectedItem(Album.filterById(_pjesma.getIdAlbum(), albumi));
		}

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

		JLabel lblIzvodjac = new JLabel("Dostupni izvođači");
		lblIzvodjac.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIzvodjac.setBounds(10, 134, 102, 14);
		contentPanel.add(lblIzvodjac);

		listSelektovaniIzvodjaci = new JList(list_selected);
		listSelektovaniIzvodjaci.setBorder(new LineBorder(SystemColor.desktop));
		listSelektovaniIzvodjaci.setBackground(SystemColor.menu);
		listSelektovaniIzvodjaci.setBounds(190, 149, 127, 148);
		contentPanel.add(listSelektovaniIzvodjaci);
		listSelektovaniIzvodjaci.setCellRenderer(new OsobaListRender());

		JLabel lblIzabraniIzvodjaci = new JLabel("Izabrani izvođači");
		lblIzabraniIzvodjaci.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIzabraniIzvodjaci.setBounds(190, 135, 110, 14);
		contentPanel.add(lblIzabraniIzvodjaci);

		JButton selectIzvodjac = new JButton(">>");
		selectIzvodjac.setBounds(144, 175, 36, 22);
		contentPanel.add(selectIzvodjac);
		selectIzvodjac.setMargin(new Insets(1, 1, 1, 1));
		selectIzvodjac.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selektujIzvodjaca();
			}
		});

		JButton unselectIzvodjac = new JButton("<<");
		unselectIzvodjac.setMargin(new Insets(1, 1, 1, 1));
		unselectIzvodjac.setBounds(144, 219, 36, 22);
		contentPanel.add(unselectIzvodjac);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 149, 127, 148);
		contentPanel.add(scrollPane);

		listDostupniIzvodjaci = new JList(list_available);
		scrollPane.setViewportView(listDostupniIzvodjaci);
		listDostupniIzvodjaci.setBorder(null);
		listDostupniIzvodjaci.setBackground(SystemColor.control);
		listDostupniIzvodjaci.setCellRenderer(new OsobaListRender());
		unselectIzvodjac.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deselektujIzvodjaca();
			}
		});

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
				id_izvodjaci = napraviIdNiz();
				String provjera = provjeriInformacije();
				if (provjera == "")
					PjesmaDialog.this.dispose();
				else
					JOptionPane.showMessageDialog(new JFrame(), provjera, "Greška u unosu", JOptionPane.ERROR_MESSAGE);

			}
		});

		buttonPane.add(btnOtkazi);
		btnOtkazi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				command = "Cancel";
				PjesmaDialog.this.dispose();
			}
		});
	}

	private void selektujIzvodjaca() {
		Izvodjac selected = (Izvodjac) listDostupniIzvodjaci.getSelectedValue();
		if (selected == null)
			return;
		list_selected.addElement(selected);
		list_available.remove(listDostupniIzvodjaci.getSelectedIndex());
	}

	private void deselektujIzvodjaca() {
		Izvodjac selected = (Izvodjac) listSelektovaniIzvodjaci.getSelectedValue();
		if (selected == null)
			return;
		list_available.addElement(selected);
		list_selected.remove(listSelektovaniIzvodjaci.getSelectedIndex());
	}

	private void inicijalizirajListe(List<Izvodjac> izvodjaci) throws SQLException {
		List<Izvodjac> svi_izvodjaci = Izvodjac.dohvatiIzvodjace();
		if (izvodjaci == null) {
			list_available.addAll(svi_izvodjaci);
		} else {
			list_selected.addAll(izvodjaci);
			for (Izvodjac izvodjac : svi_izvodjaci)
				if (!izvodjaci.contains(izvodjac))
					list_available.addElement(izvodjac);
		}
	}

	private List<Integer> napraviIdNiz() {
		List<Integer> result = new ArrayList<Integer>();
		var arr = list_selected.toArray();
		for (int i = 0; i < arr.length; i++)
			result.add(((Izvodjac) arr[i]).getIdIzvodjac());
		return result;
	}

	private String provjeriInformacije() {
		if (_pjesma.getNaziv().length() < 1 || _pjesma.getTrajanje().length() < 1 || id_izvodjaci.isEmpty())
			return "Greška u unošenju informacija. Potrebno je unijeti naziv, trajanje i barem jednog izvođača!";
		return "";
	}
}