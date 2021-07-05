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
import java.util.Hashtable;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import entiteti.Funkcija;
import entiteti.Izvodjac;
import entiteti.Osoba;
import entiteti.Pjesma;
import modeli.OsobaListRender;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Color;

public class OsobaFunkcijaDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton btnPrihvati = new JButton("Prihvati");
	private JButton btnOtkazi = new JButton("Otkaži");
	public String command = "";
	private DefaultListModel<Osoba> list_selected = new DefaultListModel<Osoba>();
	private DefaultListModel<Osoba> list_available = new DefaultListModel<Osoba>();
	private JList listDostupneOsobe;
	private JList listSelektovaneOsobe;
	public Hashtable<String, List<Integer>> selektovane_osobe = new Hashtable<String, List<Integer>>();

	public OsobaFunkcijaDialog(int id, List<Funkcija> funkcije, List<Osoba> sve_osobe) throws SQLException {
		inicijalizirajMapu(id, funkcije);
		setSize(343, 380);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		listDostupneOsobe = new JList(list_available);
		listDostupneOsobe.setBounds(10, 78, 124, 219);
		contentPanel.add(listDostupneOsobe);
		listDostupneOsobe.setBorder(new LineBorder(new Color(0, 0, 0)));
		listDostupneOsobe.setBackground(SystemColor.menu);
		listDostupneOsobe.setCellRenderer(new OsobaListRender());

		JLabel lblFunkcija = new JLabel("Funkcija");
		lblFunkcija.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFunkcija.setBounds(56, 14, 63, 14);
		contentPanel.add(lblFunkcija);

		JComboBox comboFunkcije = new JComboBox(new DefaultComboBoxModel(funkcije.toArray()));
		comboFunkcije.setBounds(129, 11, 171, 22);
		contentPanel.add(comboFunkcije);
		comboFunkcije.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value instanceof Funkcija) {
					Funkcija funkcija = (Funkcija) value;
					setText(funkcija.getNazivFunkcije());
				}
				return this;
			}
		});
		comboFunkcije.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				   if(event.getStateChange() == ItemEvent.DESELECTED) 
				   {
					   Funkcija f = (Funkcija) event.getItem();
					   SnimiTrenutnoStanje(f.getNazivFunkcije());
				   }
				if (event.getStateChange() == ItemEvent.SELECTED) {					
					Funkcija f = (Funkcija) event.getItem();
					filtrirajSelektovaneOsobe(f.getNazivFunkcije(), sve_osobe);
				}
			}
		});

		comboFunkcije.setSelectedItem(funkcije.get(0));
		filtrirajSelektovaneOsobe(funkcije.get(0).getNazivFunkcije(), sve_osobe);
		JLabel lblIOsobe = new JLabel("Dostupne osobe");
		lblIOsobe.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIOsobe.setBounds(17, 53, 102, 14);
		contentPanel.add(lblIOsobe);

		listSelektovaneOsobe = new JList(list_selected);
		listSelektovaneOsobe.setBorder(new LineBorder(SystemColor.desktop));
		listSelektovaneOsobe.setBackground(SystemColor.menu);
		listSelektovaneOsobe.setBounds(190, 78, 127, 219);
		contentPanel.add(listSelektovaneOsobe);
		listSelektovaneOsobe.setCellRenderer(new OsobaListRender());

		JLabel lblIzabraneOsobe = new JLabel("Izabrane osobe");
		lblIzabraneOsobe.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIzabraneOsobe.setBounds(190, 53, 110, 14);
		contentPanel.add(lblIzabraneOsobe);

		JButton selectOsoba = new JButton(">>");
		selectOsoba.setBounds(144, 93, 36, 22);
		contentPanel.add(selectOsoba);
		selectOsoba.setMargin(new Insets(1, 1, 1, 1));
		selectOsoba.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selektuj();
			}
		});

		JButton unselectOsoba = new JButton("<<");
		unselectOsoba.setMargin(new Insets(1, 1, 1, 1));
		unselectOsoba.setBounds(144, 126, 36, 22);
		contentPanel.add(unselectOsoba);
		unselectOsoba.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deselektuj();
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
				Funkcija f = (Funkcija) comboFunkcije.getSelectedItem();
				SnimiTrenutnoStanje(f.getNazivFunkcije());
				OsobaFunkcijaDialog.this.dispose();
			}
		});

		buttonPane.add(btnOtkazi);
		btnOtkazi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				command = "Cancel";
				OsobaFunkcijaDialog.this.dispose();
			}
		});
	}

	private void filtrirajSelektovaneOsobe(String funkcija, List<Osoba> osobe) {
		list_available.removeAllElements();
		list_selected.removeAllElements();
		List<Integer> osobe_id = this.selektovane_osobe.get(funkcija);
		list_available.addAll(osobe);
		if (osobe_id == null)
			return;
		if (!osobe_id.isEmpty())
			for (Osoba osoba : osobe)
				if (osobe_id.contains(osoba.getIdosoba())) {					
					list_selected.addElement(osoba);
					list_available.remove(list_available.indexOf(osoba));
				}			
	}
	
	private void SnimiTrenutnoStanje(String funkcija) {		
		if(list_selected.isEmpty())
			this.selektovane_osobe.put(funkcija, new ArrayList());
		else 
			this.selektovane_osobe.put(funkcija, napraviNizSelektovanihId());
		
	}

	private void selektuj() {
		Osoba selected = (Osoba) listDostupneOsobe.getSelectedValue();
		if (selected == null)
			return;
		list_selected.addElement(selected);
		list_available.remove(listDostupneOsobe.getSelectedIndex());
	}

	private void deselektuj() {
		Osoba selected = (Osoba) listSelektovaneOsobe.getSelectedValue();
		if (selected == null)
			return;
		list_available.addElement(selected);
		list_selected.remove(listSelektovaneOsobe.getSelectedIndex());
	}

	private void inicijalizirajMapu(int id, List<Funkcija> funkcije) throws SQLException {
		if (funkcije.isEmpty())
			return;
		for (Funkcija f : funkcije)
			this.selektovane_osobe.put(f.naziv_funkcije, new ArrayList());
		List<Osoba> osobe = Osoba.dohvatiSveZaPjesmu(id);
		for (Osoba osoba : osobe)
			this.dodajUMapu(osoba);
	}

	private void dodajUMapu(Osoba osoba) {
		List<Integer> value = this.selektovane_osobe.get(osoba.getFunkcija());
		value.add(osoba.getIdosoba());
		this.selektovane_osobe.put(osoba.getFunkcija(), value);
	}

	private List<Integer> napraviNizSelektovanihId() {
		List<Integer> result = new ArrayList<Integer>();
		var arr = list_selected.toArray();
		for (int i = 0; i < arr.length; i++)
			result.add(((Osoba) arr[i]).getIdosoba());
		return result;
	}
}