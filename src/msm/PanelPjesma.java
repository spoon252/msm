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

import dialogs.pjesmaDialog;
import models.Pjesma;
import tableModels.PjesmaTable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;

import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class PanelPjesma extends JPanel implements ComponentListener {
	private JTable table;

	/**
	 * Create the panel.
	 * 
	 * @throws SQLException
	 */
	public PanelPjesma() throws SQLException {
		PjesmaTable model = new PjesmaTable();
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
		List<Pjesma> pjesme = Pjesma.GetPjesme();
		addToTable(pjesme, model);
		table.setRowSelectionInterval(0, 0);
		JButton btnAddPjesma = new JButton("Nova pjesma");
		btnAddPjesma.setBounds(10, 389, 102, 29);
		btnAddPjesma.setFont(new Font("Tahoma", Font.PLAIN, 10));
		add(btnAddPjesma);
		btnAddPjesma.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					pjesmaDialog dialog = new pjesmaDialog(null);
					dialog.setTitle("Dodaj pjesmu");
					dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					if (dialog.command == "OK") {
						System.out.println(dialog._pjesma.toString());
						Pjesma dodana_pjesma = Pjesma.DodajPjesmu(dialog._pjesma);
						if (dodana_pjesma != null) {
							model.addRow(dodana_pjesma);
						}
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		JButton btnIzbrisiPjesmu = new JButton("Izbrisi pjesmu");
		btnIzbrisiPjesmu.setBounds(311, 389, 102, 29);
		btnIzbrisiPjesmu.setFont(new Font("Tahoma", Font.PLAIN, 10));
		add(btnIzbrisiPjesmu);
		btnIzbrisiPjesmu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int removed = Pjesma.IzbrisiPjesmu(model.getRow(table.getSelectedRow()).getIdPjesma());
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

		JButton btnIzmijeniPjesmu = new JButton("Izmijeni pjesmu");
		btnIzmijeniPjesmu.setBounds(148, 389, 102, 29);
		btnIzmijeniPjesmu.setFont(new Font("Tahoma", Font.PLAIN, 10));
		add(btnIzmijeniPjesmu);
		btnIzmijeniPjesmu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					pjesmaDialog dialog = new pjesmaDialog(model.getRow(table.getSelectedRow()));
					dialog.setTitle("Izmijeni pjesmu");
					dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					if (dialog.command == "OK")
						System.out.println(dialog._pjesma.toString());
					Pjesma izmijenjena_pjesma = Pjesma.DodajPjesmu(dialog._pjesma);
					if (izmijenjena_pjesma != null) {
						model.replaceRow(table.getSelectedRow(), izmijenjena_pjesma);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		JPanel panel_detalji = new JPanel();
		panel_detalji.setBounds(423, 11, 343, 365);
		add(panel_detalji);
		panel_detalji.setLayout(null);

		JLabel lblAraneri = new JLabel("Aranžeri:");
		lblAraneri.setBounds(10, 175, 54, 15);
		panel_detalji.add(lblAraneri);
		lblAraneri.setFont(new Font("Tahoma", Font.BOLD, 12));

		JLabel lblProducent = new JLabel("Producent:");
		lblProducent.setBounds(8, 215, 68, 15);
		panel_detalji.add(lblProducent);
		lblProducent.setFont(new Font("Tahoma", Font.BOLD, 12));

		JLabel lblTekstopisci = new JLabel("Tekstopisci:");
		lblTekstopisci.setBounds(9, 87, 90, 14);
		panel_detalji.add(lblTekstopisci);
		lblTekstopisci.setFont(new Font("Tahoma", Font.BOLD, 12));

		JLabel lblKompozitori = new JLabel("Kompozitori:");
		lblKompozitori.setBounds(10, 136, 90, 14);
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

		JLabel tekstopisci_label = new JLabel("");
		tekstopisci_label.setBounds(83, 87, 250, 29);
		panel_detalji.add(tekstopisci_label);
		tekstopisci_label.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JLabel producent_label = new JLabel("");
		producent_label.setBounds(86, 219, 247, 29);
		panel_detalji.add(producent_label);
		producent_label.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JLabel izvodjaci_label = new JLabel("");
		izvodjaci_label.setBounds(73, 36, 260, 14);
		panel_detalji.add(izvodjaci_label);
		izvodjaci_label.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JLabel aranzeri_label = new JLabel("");
		aranzeri_label.setBounds(74, 175, 260, 29);
		panel_detalji.add(aranzeri_label);
		aranzeri_label.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JLabel kompozitori_label = new JLabel("");
		kompozitori_label.setBounds(93, 136, 229, 29);
		panel_detalji.add(kompozitori_label);
		kompozitori_label.setFont(new Font("Tahoma", Font.PLAIN, 12));

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

	public void addToTable(List<Pjesma> pjesme, PjesmaTable model) {
		model.clearData();
		for (Pjesma pjesma : pjesme) {
			model.addRow(pjesma);
		}
	}
}
