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

public class PanelPjesma extends JPanel implements ComponentListener {
	private JTable table;

	/**
	 * Create the panel.
	 * @throws SQLException 
	 */
	public PanelPjesma() throws SQLException {
		PjesmaTable model = new PjesmaTable(); 
		setLayout(null);
		addComponentListener(this);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 683, 395);
		add(scrollPane);
		table = new JTable(model);
		scrollPane.setViewportView(table);
		table.setBounds(10, 0, 672, 413);
		table.getColumnModel().getColumn(2).setMaxWidth(75);
		JButton btnNewButton = new JButton("Osvježi listu");
		GetPjesme(model);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					GetPjesme(model);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});btnNewButton.setFont(new Font("Tahoma",Font.PLAIN,11));btnNewButton.setBounds(10,403,119,23);

	add(btnNewButton);
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
			String query = "SELECT PJ.id_pjesma"
					+",AL.naziv as album"
					+",PJ.naziv"
					+",PJ.trajanje" 
					+" FROM music_studio.pjesma as PJ" 
					+" JOIN music_studio.album as AL" 
					+" ON PJ.id_album = AL.id_album";
			PreparedStatement ps=con.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs=ps.executeQuery();
			model.clearData();
			while(rs.next()){
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
