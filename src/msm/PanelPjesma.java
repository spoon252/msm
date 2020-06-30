package msm;

import javax.swing.JPanel;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import models.Pjesma;
import tableModels.PjesmaTable;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JLabel;

public class PanelPjesma extends JPanel implements ComponentListener {
	private JTable table;

	/**
	 * Create the panel.
	 * 
	 * @throws SQLException
	 */
	public PanelPjesma() throws SQLException {
		PjesmaTable model = new PjesmaTable();
		setLayout(null);
		addComponentListener(this);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 403, 444);
		add(scrollPane);
		table = new JTable(model);
		scrollPane.setViewportView(table);
		table.setBounds(10, 0, 672, 413);
		table.getColumnModel().getColumn(2).setMaxWidth(75);
		GetPjesme(model);
		table.setRowSelectionInterval(0, 0);

		JLabel lblNewLabel = new JLabel("Izvođači: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(423, 45, 67, 14);
		add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Detalji");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(585, 11, 46, 14);
		add(lblNewLabel_1);

		JLabel izvodjaci_label = new JLabel("");
		izvodjaci_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		izvodjaci_label.setBounds(490, 45, 270, 14);
		add(izvodjaci_label);

		JLabel lblKompozitori = new JLabel("Kompozitori:");
		lblKompozitori.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblKompozitori.setBounds(423, 76, 90, 14);
		add(lblKompozitori);

		JLabel kompozitori_label = new JLabel("");
		kompozitori_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		kompozitori_label.setBounds(506, 82, 260, 29);
		add(kompozitori_label);

		JLabel lblTekstopisci = new JLabel("Tekstopisci:");
		lblTekstopisci.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTekstopisci.setBounds(423, 122, 90, 14);
		add(lblTekstopisci);

		JLabel tekstopisci_label = new JLabel("");
		tekstopisci_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tekstopisci_label.setBounds(500, 122, 260, 29);
		add(tekstopisci_label);

		JLabel lblAraneri = new JLabel("Aranžeri:");
		lblAraneri.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAraneri.setBounds(423, 159, 67, 14);
		add(lblAraneri);

		JLabel aranzeri_label = new JLabel("");
		aranzeri_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		aranzeri_label.setBounds(490, 162, 260, 29);
		add(aranzeri_label);

		JLabel lblProducent = new JLabel("Producent:");
		lblProducent.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblProducent.setBounds(423, 199, 90, 14);
		add(lblProducent);

		JLabel producent_label = new JLabel("");
		producent_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		producent_label.setBounds(500, 200, 260, 29);
		add(producent_label);
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

	public void GetPjesme(PjesmaTable model) throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "SELECT PJ.id_pjesma" + ",AL.naziv as album" + ",PJ.naziv" + ",PJ.trajanje"
				+ " FROM music_studio.pjesma as PJ" + " JOIN music_studio.album as AL"
				+ " ON PJ.id_album = AL.id_album";
		PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = ps.executeQuery();
		model.clearData();
		while (rs.next()) {
			Pjesma pjesma = new Pjesma();
			pjesma.setValue(rs);
			model.addRow(pjesma);
		}
		con.close();
	}

	public void addToTable(List<Pjesma> pjesme, PjesmaTable model) {
		for (Pjesma pjesma : pjesme) {
			model.addRow(pjesma);
		}
	}
}
