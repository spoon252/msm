package dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
import modeli.OsobaListRender;

public class AlbumDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNaziv;
	private JTextField txtGodina;
	public Album _album = new Album();
	private JButton btnPrihvati = new JButton("Prihvati");
	private JButton btnOtkazi = new JButton("Otkaži");
	public String command = "";
	private DefaultListModel<Izvodjac> list_selected = new DefaultListModel<Izvodjac>();
	private DefaultListModel<Izvodjac> list_available = new DefaultListModel<Izvodjac>();
	private JList listDostupniIzvodjaci;
	private JList listSelektovaniIzvodjaci;
	public List<Integer> id_izvodjaci;
	private JTextField textField;

	/***
	 * 
	 * @param pjesma Input argument
	 * @throws SQLException
	 */
	public AlbumDialog(Album album, List<Izvodjac> izvodjaci) throws SQLException {
		inicijalizirajListe(izvodjaci);
		if (album != null) {
			_album = album;
		}
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

		JLabel lblTrajanjes = new JLabel("Godina");
		lblTrajanjes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTrajanjes.setBounds(66, 54, 63, 14);
		contentPanel.add(lblTrajanjes);
		
		JLabel label = new JLabel("");
		label.setForeground(Color.RED);
		label.setBounds(139, 79, 132, 14);
		contentPanel.add(label);
		
		txtNaziv = new JTextField();
		txtNaziv.setBounds(139, 21, 127, 20);
		contentPanel.add(txtNaziv);
		txtNaziv.setColumns(10);
		txtNaziv.setText(_album.getNaziv());

		txtGodina = new JTextField();
		txtGodina.setColumns(10);
		txtGodina.setBounds(139, 52, 127, 20);
		contentPanel.add(txtGodina);
		txtGodina.setText(Integer.toString(_album.getGodina()));

		txtGodina.addKeyListener(new KeyAdapter() {
	          public void keyPressed(KeyEvent ke) {
	             String value = txtGodina.getText();
	             int l = value.length();
	             if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' && l<4) || ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
	            	 txtGodina.setEditable(true);
	                label.setText("");
	             } else {
	            	 txtGodina.setEditable(false);
	                label.setText("Pogrešan unos!");
	             }
	          }
	       });
		

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
		
		textField = new JTextField();
		textField.setText((String) null);
		textField.setColumns(10);
		textField.setBounds(139, 52, 127, 20);
		contentPanel.add(textField);
		
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
				_album.setNaziv(txtNaziv.getText());
				_album.setGodina(Integer.parseInt(txtGodina.getText()));
				id_izvodjaci = napraviIdNiz();
				String provjera = provjeriInformacije();
				if (provjera == "")
					AlbumDialog.this.dispose();
				else
					JOptionPane.showMessageDialog(new JFrame(), provjera, "Greška u unosu", JOptionPane.ERROR_MESSAGE);

			}
		});

		buttonPane.add(btnOtkazi);
		btnOtkazi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				command = "Cancel";
				AlbumDialog.this.dispose();
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
		if (_album.getNaziv().length() < 1 || 
				id_izvodjaci.isEmpty())
			return "Greška u unošenju informacija. Potrebno je unijeti naziv, godinu i barem jednog izvođača!";
		if(_album.getGodina()<1945 || _album.getGodina()>2025)
			return "Greška u unosu godine!";
		return "";
	}
}

