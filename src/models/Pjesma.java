package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Pjesma {
	public int idPjesma;
	public String album;
	public String naziv;
	public int trajanje;
	
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
	
	public int getTrajanje() {
		return trajanje;
	}
	
	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}
	
	public void setValue(ResultSet rs) {
		try {
			this.idPjesma = rs.getInt(1);
			this.album = rs.getString(2);
			this.naziv = rs.getString(3);
			this.trajanje = rs.getInt(4);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return this.idPjesma +" "+this.naziv + " " + this.album + " " + this.trajanje;
				
	}
}
