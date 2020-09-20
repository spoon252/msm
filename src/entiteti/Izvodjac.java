package entiteti;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import msm.DatabaseConnector;

public class Izvodjac {
	public int idIzvodjac;
	public String ime;
	public String prezime;
	public String tip;
	public int getIdIzvodjac() {
		return idIzvodjac;
	}
	public void setIdIzvodjac(int idIzvodjac) {
		this.idIzvodjac = idIzvodjac;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	
	public void setValue(ResultSet rs) {
		try {
			this.idIzvodjac = rs.getInt(1);
			this.ime = rs.getString(2);
			this.prezime = rs.getString(3);
			this.tip = rs.getString(4);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return this.idIzvodjac +" "+this.ime + " " + this.prezime + " " + this.tip;
				
	}
	
	public static List<Izvodjac> DohvatiIzvodjacePoAlbumu(int id) throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "SELECT IZ.id_izvodjac, IZ.ime, IZ.prezime,IZ.tip "
				+ "FROM music_studio.izvodjac as IZ " 
				+ "WHERE IZ.id_izvodjac IN (SELECT id_izvodjac "
						+ "FROM izvodjacalbum "
						+ "WHERE id_album = ?)";
		PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		List<Izvodjac> izvodjaci = new ArrayList<Izvodjac>(); 
		while (rs.next()) {
			Izvodjac izvodjac = new Izvodjac();
			izvodjac.setValue(rs);
			izvodjaci.add(izvodjac);			
		}
		con.close();
		return izvodjaci;
	}
	
	public static List<Izvodjac> DohvatiIzvodjacePoPjesmi(int id) throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "SELECT IZ.id_izvodjac, IZ.ime, IZ.prezime,IZ.tip "
				+ "FROM music_studio.izvodjac as IZ " 
				+ "WHERE IZ.id_izvodjac IN (SELECT id_izvodjac "
						+ "FROM izvodjacpjesma "
						+ "WHERE id_pjesma = ?)";
		PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		List<Izvodjac> izvodjaci = new ArrayList<Izvodjac>(); 
		while (rs.next()) {
			Izvodjac izvodjac = new Izvodjac();
			izvodjac.setValue(rs);
			izvodjaci.add(izvodjac);			
		}
		con.close();
		return izvodjaci;
	}
	
	public static List<Izvodjac> DohvatiIzvodjace() throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "SELECT id_izvodjac, ime, prezime, tip FROM music_studio.izvodjac";
		PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = ps.executeQuery();
		List<Izvodjac> izvodjaci = new ArrayList<Izvodjac>(); 
		while (rs.next()) {
			Izvodjac izvodjac = new Izvodjac();
			izvodjac.setValue(rs);
			izvodjaci.add(izvodjac);			
		}
		con.close();
		return izvodjaci;
	}
	
	public static Izvodjac DodajIzvodjaca (Izvodjac izvodjac) throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "INSERT INTO Izvodjac(ime, prezime, tip) " + 
				"VALUES (?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ps.setString(1, izvodjac.getIme());
		ps.setString(2, izvodjac.getPrezime());
		ps.setString(3, izvodjac.getTip());
		ps.execute();
		con.close();
		return izvodjac;
	}
	
	public static Izvodjac IzmijeniIzvodjaca (Izvodjac izvodjac) throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "UPDATE Izvodjac SET ime = ?, prezime = ?, tip = ? WHERE id_izvodjac = ?";
		PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ps.setString(1, izvodjac.getIme());
		ps.setString(2, izvodjac.getPrezime());
		ps.setString(3, izvodjac.getTip());
		ps.setInt(4, izvodjac.getIdIzvodjac());
		ps.execute();
		con.close();
		return izvodjac;
	}
	
	public static int IzbrisiIzvodjaca (int id) throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "DELETE FROM Izvodjac WHERE id_izvodjac = ?";
		PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ps.setInt(1, id);
		ps.execute();
		con.close();
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
	if (obj == this) {
	    return true;
	}

	if (!(obj instanceof Izvodjac)) {
	    return false;
	}
	Izvodjac other = (Izvodjac) obj;
	return idIzvodjac == other.getIdIzvodjac();
	}
}
