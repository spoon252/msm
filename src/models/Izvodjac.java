package models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
