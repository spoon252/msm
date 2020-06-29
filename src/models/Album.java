package models;

import java.sql.ResultSet;
import java.sql.SQLException;

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

}
