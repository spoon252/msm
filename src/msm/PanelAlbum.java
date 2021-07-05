package msm;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.UIDefaults;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dialogs.AlbumDialog;
import dialogs.PjesmaDialog;
import entiteti.Album;
import entiteti.Izvodjac;
import entiteti.Pjesma;
import modeli.AlbumTable;
import modeli.IzvodjacTable;
import modeli.PjesmaTable;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelAlbum extends JPanel implements ComponentListener {
	private JTable table_albumi;
	private JTable table_pjesme;
	private List<Izvodjac> list_izvodjaci;
	private DefaultListModel<String> list_model = new DefaultListModel<String>();
	private PjesmaTable pjesma_model = new PjesmaTable();
	private AlbumTable model = new AlbumTable();

	/**
	 * Create the panel.
	 */
	public PanelAlbum() throws SQLException {
		UIManager.put("Label.disabledForeground", Color.BLACK);
		setLayout(null);
		addComponentListener(this);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(38, 11, 305, 356);
		add(scrollPane);
		table_albumi = new JTable(model);
		scrollPane.setViewportView(table_albumi);
		table_albumi.setBounds(10, 0, 672, 413);
		table_albumi.getColumnModel().getColumn(1).setMaxWidth(50);
		List<Album> albumi = Album.dohvatiAlbume();
		addToTable(albumi, model);
		if (model.getRowCount() > 0) {
			table_albumi.setRowSelectionInterval(0, 0);
			loadAdditionalInfo();
		}
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(435, 214, 268, 158);
		add(scrollPane_1);

		table_pjesme = new JTable(pjesma_model);
		table_pjesme.setEnabled(false);
		table_pjesme.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		scrollPane_1.setViewportView(table_pjesme);
		table_pjesme.getColumnModel().getColumn(2).setMaxWidth(50);
		table_pjesme.removeColumn(table_pjesme.getColumnModel().getColumn(1));

		JLabel lblNewLabel = new JLabel("Pjesme");
		lblNewLabel.setBounds(435, 189, 46, 14);
		add(lblNewLabel);

		JList<String> list = new JList<String>(list_model);
		list.setEnabled(false);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		list.setFont(new Font("Tahoma", Font.PLAIN, 11));
		list.setBorder(new LineBorder(UIManager.getColor("Button.darkShadow")));
		list.setBounds(435, 34, 184, 144);
		list.setForeground(Color.BLACK);
		add(list);

		JLabel lblNewLabel_1 = new JLabel("Izvođači");
		lblNewLabel_1.setBounds(435, 12, 46, 14);
		add(lblNewLabel_1);

		JButton btnDodaj = new JButton("Dodaj");
		btnDodaj.setBounds(36, 378, 89, 23);
		add(btnDodaj);
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					AlbumDialog dialog = new AlbumDialog(null, null);
					dialog.setTitle("Dodaj album");
					dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					if (dialog.command == "OK") {
						Album dodan_album = Album.dodajAlbum(dialog._album);
						if (dodan_album != null) {
							model.addRow(dodan_album);
							albumi.add(dodan_album);
							Album.dodajIzvodjaceZaAlbum(dodan_album.getIdAlbum(), dialog.id_izvodjaci);
							table_albumi.setRowSelectionInterval(albumi.size() - 1, albumi.size() - 1);
						}
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		JButton btnIzmijeni = new JButton("Izmijeni");
		btnIzmijeni.setBounds(137, 378, 89, 23);
		add(btnIzmijeni);
		btnIzmijeni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (table_albumi.getSelectedRow() == -1 && albumi.size() > 0)
						table_albumi.setRowSelectionInterval(0, 0);
					else if (table_albumi.getSelectedRow() == -1 || albumi.size() < 1)
						return;
					AlbumDialog dialog = new AlbumDialog(model.getRow(table_albumi.getSelectedRow()), list_izvodjaci);
					dialog.setTitle("Izmijeni album");
					dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					if (dialog.command == "OK") {
						Album izmijenjen_album = Album.izmijeniAlbum(dialog._album);
						if (izmijenjen_album != null) {
							model.replaceRow(table_albumi.getSelectedRow(), izmijenjen_album);
							Album.izbrisiIzvodjaceZaAlbum(izmijenjen_album.getIdAlbum());
							Album.dodajIzvodjaceZaAlbum(izmijenjen_album.getIdAlbum(), dialog.id_izvodjaci);
							loadAdditionalInfo();
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		JButton btnIzbrisi = new JButton("Izbriši");
		btnIzbrisi.setBounds(241, 378, 89, 23);
		add(btnIzbrisi);
		btnIzbrisi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (table_albumi.getSelectedRow() == -1 && albumi.size() > 0)
						table_albumi.setRowSelectionInterval(0, 0);
					else if (table_albumi.getSelectedRow() == -1 || albumi.size() < 1)
						return;
					int selectedRow = table_albumi.getSelectedRow();
					int removed = Album.izbrisiAlbum(model.getRow(selectedRow).getIdAlbum());
					if (removed > 0) {
						model.removeRows(selectedRow);
						albumi.remove(selectedRow);
						if (model.getRowCount() > 0)
							table_albumi.setRowSelectionInterval(0, 0);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// Click listener on Albumi table
		table_albumi.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				try {
					if (!e.getValueIsAdjusting())
						loadAdditionalInfo();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	public void loadAdditionalInfo() throws SQLException {
		int row = table_albumi.getSelectedRow();
		if (row < 0)
			return;
		int selected = model.getRow(row).getIdAlbum();
		list_izvodjaci = Izvodjac.dohvatiIzvodjacePoAlbumu(selected);
		List<Pjesma> pjesme = Pjesma.DohvatiPjesmePoAlbumu(selected);
		list_model.clear();
		for (Izvodjac izvodjac : list_izvodjaci) {
			if (izvodjac.getPrezime() != null)
				list_model.addElement(izvodjac.getIme() + " " + izvodjac.getPrezime());
			else
				list_model.addElement(izvodjac.getIme());
		}
		pjesma_model.clearData();
		for (Pjesma pjesma : pjesme) {
			pjesma_model.addRow(pjesma);
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {

	}

	@Override
	public void componentMoved(ComponentEvent e) {

	}

	@Override
	public void componentShown(ComponentEvent e) {

	}

	@Override
	public void componentHidden(ComponentEvent e) {

	}

	public void addToTable(List<Album> albumi, AlbumTable model) {
		model.clearData();
		for (Album album : albumi) {
			model.addRow(album);
		}
	}
}
