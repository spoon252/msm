package msm;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import models.Album;
import models.Izvodjac;
import models.Pjesma;
import tableModels.AlbumTable;
import tableModels.IzvodjacTable;
import tableModels.PjesmaTable;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import java.awt.Color;

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
		setLayout(null);
		addComponentListener(this);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(38, 11, 263, 356);
		add(scrollPane);
		table_albumi = new JTable(model);
		scrollPane.setViewportView(table_albumi);
		table_albumi.setBounds(10, 0, 672, 413);
		table_albumi.getColumnModel().getColumn(1).setMaxWidth(50);
		List<Album> albumi = Album.DohvatiAlbume();
		addToTable(albumi, model);
		if (model.getRowCount() > 0) {
			table_albumi.setRowSelectionInterval(0, 0);
			loadAdditionalInfo();
		}
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(485, 209, 268, 158);
		add(scrollPane_1);

		table_pjesme = new JTable(pjesma_model);
		scrollPane_1.setViewportView(table_pjesme);
		table_pjesme.getColumnModel().getColumn(2).setMaxWidth(50);
		table_pjesme.removeColumn(table_pjesme.getColumnModel().getColumn(1));

		JLabel lblNewLabel = new JLabel("Pjesme");
		lblNewLabel.setBounds(485, 189, 46, 14);
		add(lblNewLabel);

		JList<String> list = new JList<String>(list_model);
		list.setForeground(Color.BLACK);
		list.setFont(new Font("Tahoma", Font.PLAIN, 11));
		list.setBorder(new LineBorder(UIManager.getColor("Button.darkShadow")));
		list.setBackground(UIManager.getColor("Button.background"));
		list.setBounds(484, 34, 145, 144);
		add(list);

		JLabel lblNewLabel_1 = new JLabel("Izvođači");
		lblNewLabel_1.setBounds(485, 12, 46, 14);
		add(lblNewLabel_1);

		JButton btnDodaj = new JButton("Dodaj");
		btnDodaj.setBounds(24, 378, 89, 23);
		add(btnDodaj);

		JButton btnIzmijeni = new JButton("Izmijeni");
		btnIzmijeni.setBounds(123, 378, 89, 23);
		add(btnIzmijeni);

		JButton btnIzbrii = new JButton("Izbriši");
		btnIzbrii.setBounds(225, 378, 89, 23);
		add(btnIzbrii);

		// Click listener on Albumi table
		table_albumi.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				try {
					loadAdditionalInfo();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	public void loadAdditionalInfo() throws SQLException {
		int selected = model.getRow(table_albumi.getSelectedRow()).getIdAlbum();
		list_izvodjaci = Izvodjac.DohvatiIzvodjacePoAlbumu(selected);
		List<Pjesma> pjesme = Pjesma.DohvatiPjesmePoAlbumu(selected);
		list_model.clear();
		for (Izvodjac izvodjac : list_izvodjaci) {
			System.out.println(izvodjac.toString());
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
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	public void addToTable(List<Album> albumi, AlbumTable model) {
		model.clearData();
		for (Album album : albumi) {
			model.addRow(album);
		}
	}
}
