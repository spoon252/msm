package models;

public class Izvodjac {
	public int idIzvodjac;
	public String ime;
	public String prezime;
	public String datumRodjenja;
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
	public String getDatumRodjenja() {
		return datumRodjenja;
	}
	public void setDatumRodjenja(String datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}
}
