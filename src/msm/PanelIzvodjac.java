package msm;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import dialogs.izvodjacDialog;
import dialogs.PjesmaDialog;
import entiteti.Izvodjac;
import entiteti.Pjesma;
import modeli.IzvodjacTable;
import modeli.PjesmaTable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;

import javax.swing.JScrollPane;
import java.awt.Font;

public class PanelIzvodjac extends JPanel implements ComponentListener {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public PanelIzvodjac() throws SQLException {
		IzvodjacTable model = new IzvodjacTable(); 
		setLayout(null);
		addComponentListener(this);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(82, 11, 488, 325);
		add(scrollPane);
		table = new JTable(model);
		scrollPane.setViewportView(table);
		table.setBounds(10, 0, 672, 413);
		
		JButton btnIzmijeni = new JButton("Izmijeni");
		btnIzmijeni.setBounds(286, 347, 89, 23);
		add(btnIzmijeni);
		btnIzmijeni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					izvodjacDialog dialog = new izvodjacDialog(model.getRow(table.getSelectedRow()));
					dialog.setTitle("Izmijeni izvođača");
					dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					if (dialog.command == "OK") {
						Izvodjac rezultat = Izvodjac.IzmijeniIzvodjaca(dialog._izvodjac);
					if (rezultat != null) {
						model.replaceRow(table.getSelectedRow(), rezultat);
					}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		JButton btnIzbrisi = new JButton("Izbriši");
		btnIzbrisi.setBounds(385, 347, 89, 23);
		add(btnIzbrisi);
		btnIzbrisi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int removed = Izvodjac.IzbrisiIzvodjaca(model.getRow(table.getSelectedRow()).getIdIzvodjac());
					if (removed > 0) {
						model.removeRows(table.getSelectedRow());
						if (model.getRowCount() > 0)
							table.setRowSelectionInterval(0, 0);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		table.getColumnModel().getColumn(2).setMaxWidth(95);
		GetIzvodjaci(model);		
		
		JButton btnDodaj = new JButton("Dodaj");
		btnDodaj.setBounds(187, 347, 89, 23);
		add(btnDodaj);
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					izvodjacDialog dialog = new izvodjacDialog(null);
					dialog.setTitle("Dodaj izvođača");
					dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					if (dialog.command == "OK") {
						Izvodjac dodan_izvodjac = Izvodjac.DodajIzvodjaca(dialog._izvodjac);
						if (dodan_izvodjac != null) {
							model.addRow(dodan_izvodjac);
						}
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
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
	}
	
	public void GetIzvodjaci(IzvodjacTable model) throws SQLException {
		var con = DatabaseConnector.getConnection(); 
			String query = "SELECT id_izvodjac, ime, prezime, tip from music_studio.izvodjac";
			PreparedStatement ps=con.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs=ps.executeQuery();
			model.clearData();
			while(rs.next()){
				Izvodjac izvodjac = new Izvodjac();
				izvodjac.setValue(rs);
				model.addRow(izvodjac);
			}
			con.close();
		}
}
