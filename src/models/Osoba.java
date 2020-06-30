package models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Osoba {
	public int idOsoba;
	public String ime;
	public String prezime;
	public Date datumRodjenja;
	
	public int getIdosoba() {
		return idOsoba;
	}
	public void setIdOsoba(int idOsoba) {
		this.idOsoba = idOsoba;
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
	public Date getDatumRodjenja() {
		return datumRodjenja;
	}
	public void setDatumRodjenja(Date datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}
	
	public void setValue(ResultSet rs) {
		try {
			this.idOsoba = rs.getInt(1);
			this.ime = rs.getString(2);
			this.prezime = rs.getString(3);
			this.datumRodjenja = rs.getDate(4);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return this.idOsoba +" "+this.ime + " " + this.prezime + " " + this.datumRodjenja.toString();
				
	}

}
