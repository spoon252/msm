package entiteti;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import msm.DatabaseConnector;

public class Osoba {
	public int idOsoba;
	public String ime;
	public String prezime;
	public Date datumRodjenja;
	public String funkcija;
	
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
	public String getFunkcija() {
		return funkcija;
	}
	public void setDatumRodjenja(java.util.Date datumRodjenja) {
		java.sql.Date sqlDate = new java.sql.Date(datumRodjenja.getTime()); 
		this.datumRodjenja = sqlDate;
	}
	
	public void setValue(ResultSet rs) {
		try {
			this.idOsoba = rs.getInt(1);
			this.ime = rs.getString(2);
			this.prezime = rs.getString(3);
			this.datumRodjenja = rs.getDate(4);
			if(rs.getMetaData().getColumnCount() > 4)
				this.funkcija = rs.getString(5);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return this.idOsoba +" "+this.ime + " " + this.prezime + " " + this.datumRodjenja.toString();
				
	}
	
	public static List<Osoba> DohvatiSve() throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "SELECT id_osoba, ime, prezime, datum_rodjenja from music_studio.osoba";
		PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = ps.executeQuery();
		List<Osoba> osobe = new ArrayList<Osoba>();
		while (rs.next()) {
			Osoba osoba = new Osoba();
			osoba.setValue(rs);
			osobe.add(osoba);
		}
		con.close();
		return osobe;
	}
	
	public static List<Osoba> DohvatiSveZaPjesmu(int id) throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "SELECT os.id_osoba, os.ime, os.prezime, os.datum_rodjenja, ofp.naziv_funkcije " + 
				"from music_studio.osoba as os " + 
				"join osobafunkcijapjesma as ofp " + 
				"on ofp.id_pjesma = ?";
		PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		List<Osoba> osobe = new ArrayList<Osoba>();
		while (rs.next()) {
			Osoba osoba = new Osoba();
			osoba.setValue(rs);
			osobe.add(osoba);
		}
		con.close();
		return osobe;
	}
	
	public static Osoba Dodaj (Osoba osoba) throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "INSERT INTO Osoba(ime, prezime, datum_rodjenja) VALUES (?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, osoba.getIme());
		ps.setString(2, osoba.getPrezime());
		ps.setDate(3, osoba.getDatumRodjenja());
		ps.execute();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		int id = rs.getInt(1);
		osoba.setIdOsoba(id);
		con.close();
		return osoba;
	}
	
	public static int Izbrisi (int id) throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "DELETE FROM Osoba WHERE id_osoba = ?";
		PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ps.setInt(1, id);
		ps.execute();
		con.close();
		return id;
	}
	
	public static Osoba Izmijeni (Osoba osoba) throws SQLException {
		var con = DatabaseConnector.getConnection();		
		String query = "UPDATE Osoba SET ime = ?, prezime = ?, datum_rodjenja = ? WHERE id_osoba = ?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, osoba.getIme());
		ps.setString(2, osoba.getPrezime());
		ps.setDate(3, osoba.getDatumRodjenja());
		ps.setInt(4, osoba.getIdosoba());
		ps.execute();
		con.close();
		return osoba;
	}

}
