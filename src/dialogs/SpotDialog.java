package dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import entiteti.Spot;
import modeli.OsobaListRender;
import javax.swing.JTextArea;
import java.awt.Color;

public class SpotDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNaziv;
	public Spot _spot = new Spot();
	private JButton btnPrihvati = new JButton("Prihvati");
	private JButton btnOtkazi = new JButton("Otkaži");
	public String command = "";
	private List<Album> albumi;
	private List<Pjesma> pjesme;
	private DefaultComboBoxModel pjesme_model = new DefaultComboBoxModel();
	private JComboBox comboPjesme;
	private DefaultListModel<Izvodjac> list_selected = new DefaultListModel<Izvodjac>();
	private DefaultListModel<Izvodjac> list_available = new DefaultListModel<Izvodjac>();
	private JList listDostupniIzvodjaci;
	private JList listSelektovaniIzvodjaci;
	public List<Integer> id_izvodjaci;
	private JTextField txtLokacija;
	private JTextField txtGodina;

	/***
	 * 
	 * @param pjesma Input argument
	 * @throws SQLException
	 */
	public SpotDialog(Spot spot, List<Izvodjac> izvodjaci) throws SQLException {
		inicijalizirajListe(izvodjaci);
		albumi = Album.dohvatiAlbume();
		if(albumi.size() == 0) {
			JOptionPane.showMessageDialog(new JFrame(), "Nije moguće dodati novi spot jer nijedan album nije dodan.", "Greška", JOptionPane.ERROR_MESSAGE);
			this.btnPrihvati.setEnabled(false);
		}
		else
			pjesme = Pjesma.DohvatiPjesmePoAlbumu(albumi.get(0).getIdAlbum());		
		pjesme_model.addAll(pjesme);
		setSize(343, 430);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblLokacija = new JLabel("Lokacija");
		lblLokacija.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLokacija.setBounds(10, 78, 46, 14);
		contentPanel.add(lblLokacija);

		JLabel lblLink = new JLabel("Video link");
		lblLink.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLink.setBounds(130, 278, 63, 14);
		contentPanel.add(lblLink);

		JLabel lblAlbum = new JLabel("Album");
		lblAlbum.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAlbum.setBounds(10, 14, 46, 14);
		contentPanel.add(lblAlbum);
		JComboBox comboAlbumi = new JComboBox(new DefaultComboBoxModel(albumi.toArray()));
		comboAlbumi.setBounds(66, 11, 158, 22);
		contentPanel.add(comboAlbumi);
		comboAlbumi.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value instanceof Album) {
					Album album = (Album) value;
					setText(album.getNaziv() + " " + album.getGodina());
				}
				return this;
			}
		});

		comboAlbumi.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					Album album = (Album) event.getItem();
					try {
						selektujAlbum(album.getIdAlbum());

					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
		JTextArea textAreaLink = new JTextArea();
		textAreaLink.setBorder(new LineBorder(new Color(0, 0, 0), 0));
		textAreaLink.setBounds(10, 293, 307, 54);
		contentPanel.add(textAreaLink);
		
		txtGodina = new JTextField();
		txtGodina.setColumns(10);
		txtGodina.setBounds(66, 103, 158, 20);
		contentPanel.add(txtGodina);

		comboPjesme = new JComboBox(pjesme_model);
		comboPjesme.setBounds(66, 44, 158, 22);
		contentPanel.add(comboPjesme);
		comboPjesme.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value instanceof Pjesma) {
					Pjesma pjesma = (Pjesma) value;
					setText(pjesma.getNaziv());
				}
				return this;
			}
		});
		

		txtLokacija = new JTextField();
		txtLokacija.setBounds(66, 76, 158, 20);
		contentPanel.add(txtLokacija);
		txtLokacija.setColumns(10);

		if (spot != null) {
			_spot = spot;
			int albumId = Spot.dohvatiAlbumIdZaSpot(spot.getIdPjesma());
			comboAlbumi.setSelectedItem(Album.filterById(albumId, albumi));
			pjesme = Pjesma.DohvatiPjesmePoAlbumu(albumId);
			pjesme_model.addAll(pjesme);
			int index = pjesme.indexOf(Pjesma.filterById(spot.getIdPjesma(), pjesme));
			comboPjesme.setSelectedIndex(index);
			txtGodina.setText(Integer.toString(spot.getGodina()));
			txtLokacija.setText(spot.getLokacija());
			textAreaLink.setText(spot.getYoutubeLink());			
		} else {
			if(pjesme.size() > 0)
				comboPjesme.setSelectedIndex(0);
		}

		JLabel lblIzvodjac = new JLabel("Dostupni izvođači");
		lblIzvodjac.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIzvodjac.setBounds(10, 145, 102, 14);
		contentPanel.add(lblIzvodjac);

		JLabel lblIzabraniIzvodjaci = new JLabel("Izabrani izvođači");
		lblIzabraniIzvodjaci.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIzabraniIzvodjaci.setBounds(193, 145, 110, 14);
		contentPanel.add(lblIzabraniIzvodjaci);

		JButton selectIzvodjac = new JButton(">>");
		selectIzvodjac.setBounds(144, 148, 36, 22);
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
		unselectIzvodjac.setBounds(144, 192, 36, 22);
		contentPanel.add(unselectIzvodjac);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 163, 127, 107);
		contentPanel.add(scrollPane);
		listDostupniIzvodjaci = new JList(list_available);
		scrollPane.setViewportView(listDostupniIzvodjaci);
		listDostupniIzvodjaci.setBackground(SystemColor.control);
		listDostupniIzvodjaci.setCellRenderer(new OsobaListRender());
		listDostupniIzvodjaci.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() == 2 && list_available.size() > 0) {
					int index = listDostupniIzvodjaci.locationToIndex(me.getPoint());
					selektujIzvodjaca(index);
				}
			}
		});
		JLabel lblPjesma = new JLabel("Pjesma");
		lblPjesma.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPjesma.setBounds(10, 47, 46, 14);
		contentPanel.add(lblPjesma);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(190, 160, 127, 107);
		contentPanel.add(scrollPane_1);
		listSelektovaniIzvodjaci = new JList(list_selected);
		listSelektovaniIzvodjaci.setBackground(SystemColor.control);
		scrollPane_1.setViewportView(listSelektovaniIzvodjaci);
		listSelektovaniIzvodjaci.setCellRenderer(new OsobaListRender());

		JLabel lblGodina = new JLabel("Godina");
		lblGodina.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblGodina.setBounds(10, 105, 46, 14);
		contentPanel.add(lblGodina);

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
				if(pjesme.size() == 0) {
					JOptionPane.showMessageDialog(new JFrame(), "Nije moguće dodati novi spot jer nije izabrana nijedna pjesma.", "Greška", JOptionPane.ERROR_MESSAGE);
					return;
				}
				Pjesma selectedPjesma = (Pjesma) comboPjesme.getSelectedItem();
				_spot.setLokacija(txtLokacija.getText());
				_spot.setNazivPjesme(selectedPjesma.getNaziv());
				_spot.setIdPjesma(selectedPjesma.getIdPjesma());
				_spot.setGodina(Integer.parseInt(txtGodina.getText()));
				_spot.setYoutubeLink(textAreaLink.getText());
				id_izvodjaci = napraviIdNiz();
				String provjera = provjeriInformacije();
				if (provjera == "")
					SpotDialog.this.dispose();
				else
					JOptionPane.showMessageDialog(new JFrame(), provjera, "Greška u unosu", JOptionPane.ERROR_MESSAGE);

			}
		});

		buttonPane.add(btnOtkazi);
		btnOtkazi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				command = "Cancel";
				SpotDialog.this.dispose();
			}
		});
	}

	private void selektujAlbum(int id) throws SQLException {
		pjesme_model.removeAllElements();
		pjesme = Pjesma.DohvatiPjesmePoAlbumu(id);
		pjesme_model.addAll(pjesme);
		if (!pjesme.isEmpty())
			this.comboPjesme.setSelectedItem(pjesme_model.getElementAt(0));
	}

	private void selektujIzvodjaca() {
		Izvodjac selected = (Izvodjac) listDostupniIzvodjaci.getSelectedValue();
		if (selected == null)
			return;
		list_selected.addElement(selected);
		list_available.remove(listDostupniIzvodjaci.getSelectedIndex());
	}
	
	private void selektujIzvodjaca(int index) {
		Izvodjac selected = list_available.elementAt(index);
		if (selected == null)
			return;
		list_selected.addElement(selected);
		list_available.remove(index);
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
//		if (_pjesma.getNaziv().length() < 1 || _pjesma.getTrajanje().length() < 1 || id_izvodjaci.isEmpty())
//			return "Greška u unošenju informacija. Potrebno je unijeti naziv, trajanje i barem jednog izvođača!";
		return "";
	}
}