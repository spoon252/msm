package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import msm.DatabaseConnector;
import tableModels.PjesmaTable;

public class Pjesma {
	public int idPjesma;
	public int idAlbum;
	public String album;
	public String naziv;
	public String trajanje;
	
	public int getIdPjesma() {
		return idPjesma;
	}
	
	public void setIdPjesma(int idPjesma) {
		this.idPjesma = idPjesma;
	}
	
	public String getAlbum() {
		return album;
	}
	
	public void setAlbum(String Album) {
		this.album = Album;
	}
	
	public String getNaziv() {
		return naziv;
	}
	
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	public String getTrajanje() {
		return trajanje;
	}
	
	public void setTrajanje(String trajanje) {
		this.trajanje = trajanje;
	}
	
	public int getIdAlbum() {
		return idAlbum;
	}

	public void setIdAlbum(int albumId) {
		this.idAlbum = albumId;
	}
	
	public void setValue(ResultSet rs) {
		try {
			this.idPjesma = rs.getInt(1);
			this.idAlbum = rs.getInt(2);
			this.album = rs.getString(3);
			this.naziv = rs.getString(4);
			this.trajanje = rs.getString(5);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return this.idPjesma +" "+this.naziv + " " + this.album + " " + this.trajanje;
				
	}
	
	public static List<Pjesma> GetPjesme() throws SQLException {
		List<Pjesma> pjesme = new ArrayList<Pjesma>();
		var con = DatabaseConnector.getConnection();
		String query = "SELECT PJ.id_pjesma, PJ.id_album" + ",AL.naziv as album" + ",PJ.naziv" + ",PJ.trajanje"
				+ " FROM music_studio.pjesma as PJ" + " JOIN music_studio.album as AL"
				+ " ON PJ.id_album = AL.id_album";
		PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Pjesma pjesma = new Pjesma();
			pjesma.setValue(rs);
			pjesme.add(pjesma);
		}
		con.close();
		return pjesme;
	}
	
	public static Pjesma DodajPjesmu (Pjesma pjesma) throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "INSERT INTO Pjesma(id_album, naziv, trajanje) " + 
				"VALUES (?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ps.setInt(1, pjesma.getIdAlbum());
		ps.setString(2, pjesma.getNaziv());
		ps.setString(3, pjesma.getTrajanje());
		ps.execute();
		con.close();
		return pjesma;
	}
	
	public static Pjesma IzmijeniPjesmu (Pjesma pjesma) throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "UPDATE Pjesma SET id_album = ?, naziv = ?, trajanje = ? WHERE id_pjesma = ?";
		PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ps.setInt(1, pjesma.getIdAlbum());
		ps.setString(2, pjesma.getNaziv());
		ps.setString(3, pjesma.getTrajanje());
		ps.setInt(4, pjesma.getIdPjesma());
		ps.execute();
		con.close();
		return pjesma;
	}
	
	public static List<Pjesma> GetPjesmeForAlbum(int id) throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "SELECT id_pjesma" + ",id_album" + ",naziv" + ",trajanje" + " FROM music_studio.pjesma"
				+ " WHERE id_pjesma = ?";
		PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		List<Pjesma> pjesme = new ArrayList<Pjesma>();
		while (rs.next()) {
			Pjesma pjesma = new Pjesma();
			pjesma.setValue(rs);
			pjesme.add(pjesma);
		}
		con.close();
		return pjesme;
	}
}
