package msm;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;

public class MusicStudioManagement extends JFrame{

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try { 
					MusicStudioManagement frame = new MusicStudioManagement();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	} 

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public MusicStudioManagement() throws SQLException {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 500);
		setLocationRelativeTo(null);
		contentPane = new JPanel();		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 5, 775, 455);
		contentPane.add(tabbedPane);
		
		PanelPjesma panelPjesma = new PanelPjesma();
		tabbedPane.addTab("Pjesme", null, panelPjesma, null);
		PanelAlbum panelAlbum = new PanelAlbum();
		tabbedPane.addTab("Albumi", null, panelAlbum, null);
		PanelIzvodjac panelIzvodjac = new PanelIzvodjac();
		tabbedPane.addTab("Izvođači", null, panelIzvodjac, null);
		PanelOsoba panelOsoba = new PanelOsoba();
		tabbedPane.addTab("Osobe", null, panelOsoba, null);
		}
}
