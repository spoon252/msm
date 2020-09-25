package msm;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import dialogs.osobaDialog;
import entiteti.Osoba;
import modeli.OsobaTable;
import java.awt.Dialog;

import javax.swing.JScrollPane;

public class PanelOsoba extends JPanel implements ComponentListener {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public PanelOsoba() throws SQLException {
		OsobaTable model = new OsobaTable(); 
		setLayout(null);
		addComponentListener(this);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(52, 11, 543, 325);
		add(scrollPane);
		table = new JTable(model);
		scrollPane.setViewportView(table);
		table.setBounds(10, 0, 672, 413);
		table.getColumnModel().getColumn(0).setMaxWidth(125);
		table.getColumnModel().getColumn(1).setMaxWidth(125);
		//table.getColumnModel().getColumn(2).setMaxWidth(125);

		JButton btnIzmijeni = new JButton("Izmijeni");
		btnIzmijeni.setBounds(286, 347, 89, 23);
		add(btnIzmijeni);
		btnIzmijeni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					osobaDialog dialog = new osobaDialog(model.getRow(table.getSelectedRow()));
					dialog.setTitle("Izmijeni osobu");
					dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					if (dialog.command == "OK") {
						Osoba rezultat = Osoba.Izmijeni(dialog._osoba);
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
					int removed = Osoba.Izbrisi(model.getRow(table.getSelectedRow()).getIdosoba());
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
		
		JButton btnDodaj = new JButton("Dodaj");
		btnDodaj.setBounds(187, 347, 89, 23);
		add(btnDodaj);
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					osobaDialog dialog = new osobaDialog(null);
					dialog.setTitle("Dodaj osobu");
					dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					if (dialog.command == "OK") {
						Osoba dodan_izvodjac = Osoba.Dodaj(dialog._osoba);
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
}
