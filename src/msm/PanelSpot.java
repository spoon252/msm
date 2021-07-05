package msm;

import javax.swing.JPanel;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

import dialogs.OsobaFunkcijaDialog;
import dialogs.PjesmaDialog;
import dialogs.SpotDialog;
import dodaci.PomocneFunkcije;
import entiteti.Funkcija;
import entiteti.Izvodjac;
import entiteti.Osoba;
import entiteti.Pjesma;
import entiteti.Spot;
import modeli.PjesmaTable;
import modeli.SpotTable;

import java.awt.Dialog;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;

public class PanelSpot extends JPanel implements ComponentListener {
	private JTable table;
	private SpotTable model = new SpotTable();
	private List<Izvodjac> list_izvodjaci;
	private List<Osoba> list_osobe;
	private List<Funkcija> funkcije;
	private JLabel izvodjaci_label;
	private JLabel reziseri_label;
	private JLabel producenti_label;
	private JLabel lokacija_label;
	private JLabel glumci_label;

	public PanelSpot() throws SQLException {
		System.out.print("csasd spot");

		setBorder(null);
		model.setModelEditable(false);
		addComponentListener(this);
		setLayout(null);
		JScrollPane tableScrollPane = new JScrollPane();
		tableScrollPane.setBounds(10, 11, 403, 365);
		add(tableScrollPane);
		table = new JTable(model);
		tableScrollPane.setViewportView(table);
		table.setBounds(10, 0, 672, 413);
		table.getColumnModel().getColumn(2).setMaxWidth(75);
		// dohvati sve spotove i popuni tabelu
		List<Spot> spotovi = Spot.dohvatiSpotove();
		dodajUTabelu(spotovi, model);
		this.funkcije = Funkcija.dohvatiFunkcije();
		List<Osoba> sve_osobe = Osoba.dohvatiSve();
		JButton btnAdd = new JButton("Dodaj");
		btnAdd.setBounds(70, 387, 67, 29);
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 10));
		add(btnAdd);
		//DODAJ, OBRISI I IZMIJENI SPOT - DIALOG
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					SpotDialog dialog = new SpotDialog(null, null);
					dialog.setTitle("Dodaj spot");
					dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					if (dialog.command == "OK") {
						Spot dodano = Spot.dodajSpot(dialog._spot);
						if (dodano != null) {
							model.addRow(dodano);
							spotovi.add(dodano);
							Spot.dodajIzvodjaceZaSpot(dodano.getIdSpot(), dialog.id_izvodjaci);
							table.setRowSelectionInterval(spotovi.size() - 1, spotovi.size() - 1);
						}
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		JButton btnIzbrisi = new JButton("Izbriši");
		btnIzbrisi.setBounds(251, 387, 79, 29);
		btnIzbrisi.setFont(new Font("Tahoma", Font.PLAIN, 10));
		add(btnIzbrisi);
		btnIzbrisi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() == -1 && spotovi.size() > 0)
						table.setRowSelectionInterval(0, 0);
					else if (table.getSelectedRow() == -1 || spotovi.size() < 1)
						return;
					int selectedRow = table.getSelectedRow();
					int removed = Spot.izbrisiSpot(model.getRow(selectedRow).getIdSpot());
					if (removed > 0) {
						model.removeRows(selectedRow);
						spotovi.remove(selectedRow);
						if (model.getRowCount() > 0)
							table.setRowSelectionInterval(0, 0);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton btnIzmijeni = new JButton("Izmijeni");
		btnIzmijeni.setBounds(157, 387, 77, 29);
		btnIzmijeni.setFont(new Font("Tahoma", Font.PLAIN, 10));
		add(btnIzmijeni);
		btnIzmijeni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() == -1 && spotovi.size() > 0)
						table.setRowSelectionInterval(0, 0);
					else if (table.getSelectedRow() == -1 || spotovi.size() < 1)
						return;
					SpotDialog dialog = new SpotDialog(model.getRow(table.getSelectedRow()), list_izvodjaci);
					dialog.setTitle("Izmijeni pjesmu");
					dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					if (dialog.command == "OK") {
						Spot izmijenjeno = Spot.izmijeniSpot(dialog._spot);
						if (izmijenjeno != null) {
							model.replaceRow(table.getSelectedRow(), izmijenjeno);
							Spot.izbrisiIzvodjaceZaSpot(izmijenjeno.getIdSpot());
							Spot.dodajIzvodjaceZaSpot(izmijenjeno.getIdSpot(), dialog.id_izvodjaci);
							dohvatiDodatneInformacije();
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		JButton btnPromijeniDetalje = new JButton("Uredi detalje");
		btnPromijeniDetalje.setBounds(543, 387, 108, 29);
		add(btnPromijeniDetalje);
		btnPromijeniDetalje.setFont(new Font("Tahoma", Font.PLAIN, 10));

		// DETALJI PANEL

		JPanel panel_detalji = new JPanel();
		panel_detalji.setBounds(423, 11, 343, 365);
		add(panel_detalji);
		panel_detalji.setLayout(null);

		JLabel lblReziseri = new JLabel("Režiseri");
		lblReziseri.setBounds(10, 149, 54, 15);
		panel_detalji.add(lblReziseri);
		lblReziseri.setFont(new Font("Tahoma", Font.BOLD, 12));

		JLabel lblProducent = new JLabel("Producenti");
		lblProducent.setBounds(10, 94, 74, 15);
		panel_detalji.add(lblProducent);
		lblProducent.setFont(new Font("Tahoma", Font.BOLD, 12));

		JLabel lblLokacija = new JLabel("Lokacija");
		lblLokacija.setBounds(10, 275, 90, 14);
		panel_detalji.add(lblLokacija);
		lblLokacija.setFont(new Font("Tahoma", Font.BOLD, 12));

		JLabel lblGlumci = new JLabel("Glumci");
		lblGlumci.setBounds(10, 212, 90, 14);
		panel_detalji.add(lblGlumci);
		lblGlumci.setFont(new Font("Tahoma", Font.BOLD, 12));

		JLabel lblNewLabel = new JLabel("Izvođači");
		lblNewLabel.setBounds(10, 36, 67, 14);
		panel_detalji.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));

		JLabel lblNewLabel_1 = new JLabel("Detalji");
		lblNewLabel_1.setBounds(148, 0, 46, 14);
		panel_detalji.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));

		lokacija_label = new JLabel("");
		lokacija_label.setBackground(Color.WHITE);
		lokacija_label.setVerticalAlignment(SwingConstants.TOP);
		lokacija_label.setBounds(10, 300, 311, 34);
		panel_detalji.add(lokacija_label);
		lokacija_label.setFont(new Font("Tahoma", Font.PLAIN, 12));

		producenti_label = new JLabel("");
		producenti_label.setBackground(Color.WHITE);
		producenti_label.setVerticalAlignment(SwingConstants.TOP);
		producenti_label.setBounds(10, 114, 311, 34);
		panel_detalji.add(producenti_label);
		producenti_label.setFont(new Font("Tahoma", Font.PLAIN, 12));

		izvodjaci_label = new JLabel("");
		izvodjaci_label.setBounds(10, 49, 311, 41);
		panel_detalji.add(izvodjaci_label);
		izvodjaci_label.setFont(new Font("Tahoma", Font.PLAIN, 12));

		reziseri_label = new JLabel("");
		reziseri_label.setBackground(Color.WHITE);
		reziseri_label.setVerticalAlignment(SwingConstants.TOP);
		reziseri_label.setBounds(10, 163, 311, 38);
		panel_detalji.add(reziseri_label);
		reziseri_label.setFont(new Font("Tahoma", Font.PLAIN, 12));

		glumci_label = new JLabel("");
		glumci_label.setBackground(Color.WHITE);
		glumci_label.setVerticalAlignment(SwingConstants.TOP);
		glumci_label.setBounds(10, 237, 311, 28);
		panel_detalji.add(glumci_label);
		glumci_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblLink = new JLabel("Otvori video u pretraživaču");
		lblLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblLink.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if(row<0)
					return;
				String lok = model.getRow(row).getYoutubeLink();
				if(lok == "" || lok == null)
					JOptionPane.showMessageDialog(new JFrame(), "Link je prazan!", "Greška", JOptionPane.ERROR_MESSAGE);
				else
					PomocneFunkcije.openLinkInBrowser(lok);
			}
		});
		lblLink.setForeground(Color.BLUE);
		lblLink.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLink.setBounds(79, 345, 194, 14);
		panel_detalji.add(lblLink);
		btnPromijeniDetalje.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() == -1 && spotovi.size() > 0)
						table.setRowSelectionInterval(0, 0);
					else if (table.getSelectedRow() == -1 || spotovi.size() < 1)
						return;
					OsobaFunkcijaDialog dialog = new OsobaFunkcijaDialog(model.getRow(table.getSelectedRow()).getIdSpot(), funkcije,
							sve_osobe);
					dialog.setTitle("Izmijeni detalje");
					dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					if (dialog.command == "OK") {
						Osoba.azurirajOsobeZaSpot(dialog.selektovane_osobe,
								model.getRow(table.getSelectedRow()).getIdPjesma());
						dohvatiDodatneInformacije();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				try {
					if (!e.getValueIsAdjusting())
						dohvatiDodatneInformacije();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		if (spotovi.size() > 0)
			table.setRowSelectionInterval(0, 0);
	}

	public void dohvatiDodatneInformacije() throws SQLException {
		var row = table.getSelectedRow();
		if (row < 0)
			return;
		int selected = model.getRow(row).getIdSpot();
		lokacija_label.setText(model.getRow(row).getLokacija());
		list_izvodjaci = Izvodjac.dohvatiIzvodjacePoSpotu(selected);
		list_osobe = Osoba.dohvatiSveZaSpot(selected);
		popuniDodatneInformacije();
		String labeltext = "";
		for (Izvodjac izvodjac : list_izvodjaci) {
			if (izvodjac.getPrezime() != null)
				labeltext += (izvodjac.getIme() + " " + izvodjac.getPrezime()) + "; ";
			else
				labeltext += izvodjac.getIme() + "; ";
		}
		izvodjaci_label.setText(labeltext);
	}

	public void popuniDodatneInformacije() {
		String reziseri = "";
		String glumci = "";
		String producenti = "";
		reziseri_label.setText("");
		glumci_label.setText("");
		producenti_label.setText("");
		for (Osoba osoba : list_osobe)
			switch (osoba.getFunkcija()) {
			case "Reziser":
				reziseri += (osoba.getIme() + " " + osoba.getPrezime()) + "; ";
				break;
			case "Glumac":
				glumci += (osoba.getIme() + " " + osoba.getPrezime()) + "; ";
				break;
			case "Producent":
				producenti += (osoba.getIme() + " " + osoba.getPrezime()) + "; ";
				break;
			default:
				break;
			}
		reziseri_label.setText(reziseri);
		glumci_label.setText(glumci);
		producenti_label.setText(producenti);
	}

	public void dodajUTabelu(List<Spot> spotovi, SpotTable model) {
		model.clearData();
		for (Spot spot : spotovi) {
			model.addRow(spot);
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

}
