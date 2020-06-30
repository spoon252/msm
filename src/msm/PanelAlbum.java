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
		GetAlbumi(model);
		table_albumi.setRowSelectionInterval(0, 0);
		loadAdditionalInfo();

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
		list.setBorder(new LineBorder(UIManager.getColor("Button.darkShadow")));
		list.setBackground(UIManager.getColor("Button.background"));
		list.setBounds(484, 34, 145, 144);
		add(list);

		JLabel lblNewLabel_1 = new JLabel("Izvođači");
		lblNewLabel_1.setBounds(485, 12, 46, 14);
		add(lblNewLabel_1);

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
		GetIzvodjaciForAlbum(list_model, selected);
		GetPjesmeForAlbum(pjesma_model, selected);
	}

	public void GetAlbumi(AlbumTable model) throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "SELECT id_album, naziv, godina from music_studio.album";
		PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = ps.executeQuery();
		model.clearData();
		while (rs.next()) {
			Album album = new Album();
			album.setValue(rs);
			model.addRow(album);
		}
		con.close();
	}

	public void GetIzvodjaciForAlbum(DefaultListModel<String> list_model, int id) throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "SELECT IZ.id_izvodjac, IZ.ime, IZ.prezime,IZ.tip "
				+ "FROM music_studio.izvodjac as IZ " 
				+ "WHERE IZ.id_izvodjac IN (SELECT id_izvodjac "
						+ "FROM izvodjacalbum "
						+ "WHERE id_album = ?)";
		PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		list_model.clear();
		while (rs.next()) {
			Izvodjac izvodjac = new Izvodjac();
			izvodjac.setValue(rs);
			if (izvodjac.getPrezime() != null)
				list_model.addElement(izvodjac.getIme() + " " + izvodjac.getPrezime());
			else
				list_model.addElement(izvodjac.getIme());

		}
		con.close();
	}

	public void GetPjesmeForAlbum(PjesmaTable model, int id) throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "SELECT id_pjesma" + ",id_album" + ",naziv" + ",trajanje" + " FROM music_studio.pjesma"
				+ " WHERE id_pjesma = ?";
		PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		model.clearData();
		while (rs.next()) {
			Pjesma pjesma = new Pjesma();
			pjesma.setValue(rs);
			model.addRow(pjesma);
		}
		con.close();
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
}
