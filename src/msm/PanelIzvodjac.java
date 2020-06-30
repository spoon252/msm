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
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import models.Izvodjac;
import models.Pjesma;
import tableModels.IzvodjacTable;
import tableModels.PjesmaTable;

import java.awt.BorderLayout;
import java.awt.Color;
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
		scrollPane.setBounds(0, 0, 683, 395);
		add(scrollPane);
		table = new JTable(model);
		scrollPane.setViewportView(table);
		table.setBounds(10, 0, 672, 413);
		table.getColumnModel().getColumn(2).setMaxWidth(75);
		GetIzvodjaci(model);
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
