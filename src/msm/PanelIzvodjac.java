package msm;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.Font;

public class PanelIzvodjac extends JPanel implements ComponentListener {

	/**
	 * Create the panel.
	 */
	public PanelIzvodjac() {
		System.out.println("constructor izvodjac");
		addComponentListener(this);
		DefaultTableModel model = new DefaultTableModel(); 
		//JTableHeader header = table.getTableHeader();
		//add(header, BorderLayout.NORTH);
		AddTableHeaders(model);
		setLayout(null);
	}
	
	private void AddTableHeaders(DefaultTableModel model) {
		model.addColumn("ID"); 
		model.addColumn("Album"); 
		model.addColumn("Naziv"); 
		model.addColumn("Trajanje"); 
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
		System.out.println("izvodjac show");		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}
}
