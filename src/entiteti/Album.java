package entiteti;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import msm.DatabaseConnector;

public class Album {
	public int idAlbum;
	public String naziv;
	public int godina;
	
	public int getIdAlbum() {
		return idAlbum;
	}
	
	public void setIdAlbum(int id_album) {
		this.idAlbum = id_album;
	}
	
	public String getNaziv() {
		return naziv;
	}
	
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	public int getGodina() {
		return godina;
	}
	
	public void setGodina(int godina) {
		this.godina = godina;
	}
	
	public void setValue(ResultSet rs) {
		try {
			this.idAlbum = rs.getInt(1);
			this.naziv = rs.getString(2);
			this.godina = rs.getInt(3);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return this.idAlbum +" "+this.naziv + " " + this.godina;				
	}
	
	public static List<Album> dohvatiAlbume() throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "SELECT id_album, naziv, godina from music_studio.album";
		PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = ps.executeQuery();
		List<Album> albumi = new ArrayList<Album>();
		while (rs.next()) {
			Album album = new Album();
			album.setValue(rs);
			albumi.add(album);
		}
		con.close();
		return albumi;
	}
	
	public static Album dodajAlbum (Album album) throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "INSERT INTO Album(naziv, godina) VALUES (?, ?)";
		PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, album.getNaziv());
		ps.setInt(2, album.getGodina());
		ps.execute();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		int id = rs.getInt(1);
		album.setIdAlbum(id);
		con.close();
		return album;
	}
	
	public static int izbrisiAlbum (int id) throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "DELETE FROM Album WHERE id_album = ?";
		PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ps.setInt(1, id);
		ps.execute();
		con.close();
		return id;
	}
	
	public static void dodajIzvodjaceZaAlbum (int id, List<Integer> izvodjaci) throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "INSERT INTO izvodjacalbum(id_izvodjac, id_album) VALUES (?, ?);";
		PreparedStatement ps = con.prepareStatement(query);
		for(int id_izvodjac : izvodjaci) {
			ps.setInt(1, id_izvodjac);
			ps.setInt(2, id);
			ps.addBatch();
		}
		ps.executeBatch();
		con.close();
	}
	
	public static void izbrisiIzvodjaceZaAlbum (int id) throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "DELETE FROM izvodjacalbum WHERE id_album = ?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, id);
		ps.execute();
		con.close();
	}
	
	public static Album izmijeniAlbum (Album album) throws SQLException {
		var con = DatabaseConnector.getConnection();		
		String query = "UPDATE Album SET naziv = ?, godina = ? WHERE id_album = ?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, album.getNaziv());
		ps.setInt(2, album.getGodina());
		ps.setInt(3, album.getIdAlbum());
		ps.execute();
		con.close();
		return album;
	}
	
	public static Album filterById(int id, List<Album> albums) {
	    for(Album album : albums) {
	        if(album.getIdAlbum() == id) {
	            return album;
	        }
	    }
	    return null;
	}	

}
