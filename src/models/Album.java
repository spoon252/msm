package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import msm.DatabaseConnector;
import tableModels.AlbumTable;

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
	
	public static List<Album> GetAlbumi() throws SQLException {
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
	

}
