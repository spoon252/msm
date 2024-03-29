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
import entiteti.Funkcija;
import entiteti.Izvodjac;
import entiteti.Osoba;
import entiteti.Pjesma;
import modeli.PjesmaTable;

import java.awt.Dialog;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.SwingConstants;

public class PanelPjesma extends JPanel implements ComponentListener {
	private JTable table;
	private PjesmaTable model = new PjesmaTable();
	private List<Izvodjac> list_izvodjaci;
	private List<Funkcija> funkcije;
	private List<Osoba> list_osobe;
	private JLabel izvodjaci_label;
	private JLabel aranzeri_label;
	private JLabel producenti_label;
	private JLabel tekstopisci_label;
	private JLabel kompozitori_label;

	public PanelPjesma() throws SQLException {
		System.out.print("csasd");
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
		// dohvati sve pjesme i popuni tabelu
		List<Pjesma> pjesme = Pjesma.getPjesme();
		dodajUTabelu(pjesme, model);
		this.funkcije = Funkcija.dohvatiFunkcije();		
		JButton btnAddPjesma = new JButton("Dodaj");
		btnAddPjesma.setBounds(70, 387, 67, 29);
		btnAddPjesma.setFont(new Font("Tahoma", Font.PLAIN, 10));
		add(btnAddPjesma);
		// DODAJ, OBRISI I IZMIJENI PJESMU - DIALOG
		btnAddPjesma.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					PjesmaDialog dialog = new PjesmaDialog(null, null);
					dialog.setTitle("Dodaj pjesmu");
					dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					if (dialog.command == "OK") {
						Pjesma dodana_pjesma = Pjesma.dodajPjesmu(dialog._pjesma);
						if (dodana_pjesma != null) {
							model.addRow(dodana_pjesma);
							pjesme.add(dodana_pjesma);
							Pjesma.dodajIzvodjaceZaPjesmu(dodana_pjesma.getIdPjesma(), dialog.id_izvodjaci);
							table.setRowSelectionInterval(pjesme.size() - 1, pjesme.size() - 1);
						}
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		JButton btnIzbrisiPjesmu = new JButton("Izbriši");
		btnIzbrisiPjesmu.setBounds(251, 387, 79, 29);
		btnIzbrisiPjesmu.setFont(new Font("Tahoma", Font.PLAIN, 10));
		add(btnIzbrisiPjesmu);
		btnIzbrisiPjesmu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() == -1 && pjesme.size() > 0)
						table.setRowSelectionInterval(0, 0);
					else if (table.getSelectedRow() == -1 || pjesme.size() < 1)
						return;
					int selectedRow = table.getSelectedRow();
					int removed = Pjesma.IzbrisiPjesmu(model.getRow(selectedRow).getIdPjesma());
					if (removed > 0) {
						model.removeRows(selectedRow);
						pjesme.remove(selectedRow);
						if (model.getRowCount() > 0)
							table.setRowSelectionInterval(0, 0);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton btnIzmijeniPjesmu = new JButton("Izmijeni");
		btnIzmijeniPjesmu.setBounds(157, 387, 77, 29);
		btnIzmijeniPjesmu.setFont(new Font("Tahoma", Font.PLAIN, 10));
		add(btnIzmijeniPjesmu);
		btnIzmijeniPjesmu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() == -1 && pjesme.size() > 0)
						table.setRowSelectionInterval(0, 0);
					else if (table.getSelectedRow() == -1 || pjesme.size() < 1)
						return;
					PjesmaDialog dialog = new PjesmaDialog(model.getRow(table.getSelectedRow()), list_izvodjaci);
					dialog.setTitle("Izmijeni pjesmu");
					dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					if (dialog.command == "OK") {
						Pjesma izmijenjena_pjesma = Pjesma.izmijeniPjesmu(dialog._pjesma);
						if (izmijenjena_pjesma != null) {
							model.replaceRow(table.getSelectedRow(), izmijenjena_pjesma);
							Pjesma.izbrisiIzvodjaceZaPjesmu(izmijenjena_pjesma.getIdPjesma());
							Pjesma.dodajIzvodjaceZaPjesmu(izmijenjena_pjesma.getIdPjesma(), dialog.id_izvodjaci);
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

		JLabel lblAraneri = new JLabel("Aranžeri:");
		lblAraneri.setBounds(9, 201, 54, 15);
		panel_detalji.add(lblAraneri);
		lblAraneri.setFont(new Font("Tahoma", Font.BOLD, 12));

		JLabel lblProducent = new JLabel("Producenti:");
		lblProducent.setBounds(9, 149, 74, 15);
		panel_detalji.add(lblProducent);
		lblProducent.setFont(new Font("Tahoma", Font.BOLD, 12));

		JLabel lblTekstopisci = new JLabel("Tekstopisci:");
		lblTekstopisci.setBounds(10, 306, 90, 14);
		panel_detalji.add(lblTekstopisci);
		lblTekstopisci.setFont(new Font("Tahoma", Font.BOLD, 12));

		JLabel lblKompozitori = new JLabel("Kompozitori:");
		lblKompozitori.setBounds(10, 251, 90, 14);
		panel_detalji.add(lblKompozitori);
		lblKompozitori.setFont(new Font("Tahoma", Font.BOLD, 12));

		JLabel lblNewLabel = new JLabel("Izvođači: ");
		lblNewLabel.setBounds(10, 36, 67, 14);
		panel_detalji.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));

		JLabel lblNewLabel_1 = new JLabel("Detalji");
		lblNewLabel_1.setBounds(148, 0, 46, 14);
		panel_detalji.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));

		tekstopisci_label = new JLabel("");
		tekstopisci_label.setVerticalAlignment(SwingConstants.TOP);
		tekstopisci_label.setBounds(86, 305, 250, 34);
		panel_detalji.add(tekstopisci_label);
		tekstopisci_label.setFont(new Font("Tahoma", Font.PLAIN, 12));

		producenti_label = new JLabel("");
		producenti_label.setVerticalAlignment(SwingConstants.TOP);
		producenti_label.setBounds(93, 149, 247, 34);
		panel_detalji.add(producenti_label);
		producenti_label.setFont(new Font("Tahoma", Font.PLAIN, 12));

		izvodjaci_label = new JLabel("");
		izvodjaci_label.setBounds(73, 36, 260, 14);
		panel_detalji.add(izvodjaci_label);
		izvodjaci_label.setFont(new Font("Tahoma", Font.PLAIN, 12));

		aranzeri_label = new JLabel("");
		aranzeri_label.setVerticalAlignment(SwingConstants.TOP);
		aranzeri_label.setBounds(76, 201, 260, 25);
		panel_detalji.add(aranzeri_label);
		aranzeri_label.setFont(new Font("Tahoma", Font.PLAIN, 12));

		kompozitori_label = new JLabel("");
		kompozitori_label.setVerticalAlignment(SwingConstants.TOP);
		kompozitori_label.setBounds(98, 251, 238, 28);
		panel_detalji.add(kompozitori_label);
		kompozitori_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnPromijeniDetalje.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() == -1 && pjesme.size() > 0)
						table.setRowSelectionInterval(0, 0);
					else if (table.getSelectedRow() == -1 || pjesme.size() < 1)
						return;					
					OsobaFunkcijaDialog dialog = new OsobaFunkcijaDialog(model.getRow(table.getSelectedRow()).getIdPjesma(), funkcije);
					dialog.setTitle("Izmijeni detalje");
					dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					if (dialog.command == "OK") {
						Osoba.azurirajOsobeZaPjesmu(dialog.selektovane_osobe,
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
		if (pjesme.size() > 0)
			table.setRowSelectionInterval(0, 0);
	}

	public void dohvatiDodatneInformacije() throws SQLException {
		var row = table.getSelectedRow();
		if (row < 0)
			return;
		int selected = model.getRow(row).getIdPjesma();
		list_izvodjaci = Izvodjac.dohvatiIzvodjacePoPjesmi(selected);
		list_osobe = Osoba.dohvatiSveZaPjesmu(selected);
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
		String aranzeri = "";
		String kompozitori = "";
		String producenti = "";
		String tekstopisci = "";
		aranzeri_label.setText("");
		kompozitori_label.setText("");
		tekstopisci_label.setText("");
		producenti_label.setText("");
		for (Osoba osoba : list_osobe)
			switch (osoba.getFunkcija()) {
			case "Aranzer":
				aranzeri += (osoba.getIme() + " " + osoba.getPrezime()) + "; ";
				break;
			case "Kompozitor":
				kompozitori += (osoba.getIme() + " " + osoba.getPrezime()) + "; ";
				break;
			case "Producent":
				producenti += (osoba.getIme() + " " + osoba.getPrezime()) + "; ";
				break;
			case "Tekstopisac":
				tekstopisci += (osoba.getIme() + " " + osoba.getPrezime()) + "; ";
				break;
			default:
				break;
			}
		aranzeri_label.setText(aranzeri);
		kompozitori_label.setText(kompozitori);
		tekstopisci_label.setText(tekstopisci);
		producenti_label.setText(producenti);
	}

	public void dodajUTabelu(List<Pjesma> pjesme, PjesmaTable model) {
		model.clearData();
		for (Pjesma pjesma : pjesme) {
			model.addRow(pjesma);
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
