package msm;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import models.Osoba;
import tableModels.OsobaTable;

public class PanelOsoba extends JPanel implements ComponentListener  {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public PanelOsoba() throws SQLException {
		OsobaTable model = new OsobaTable(); 
		setLayout(null);
		addComponentListener(this);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 380, 356);
		add(scrollPane);
		table = new JTable(model);
		scrollPane.setViewportView(table);
		table.setBounds(10, 0, 672, 413);
		table.getColumnModel().getColumn(2).setMaxWidth(75);
		GetOsobe(model);
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
	
	public void GetOsobe(OsobaTable model) throws SQLException {
		var con = DatabaseConnector.getConnection(); 
			String query = "SELECT id_osoba, ime, prezime, datum_rodjenja from music_studio.osoba";
			PreparedStatement ps=con.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs=ps.executeQuery();
			model.clearData();
			while(rs.next()){
				Osoba osoba = new Osoba();
				osoba.setValue(rs);
				model.addRow(osoba);
			}
			con.close();
		}
}


