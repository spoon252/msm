package entiteti;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import msm.DatabaseConnector;

public class Funkcija {
	public String naziv_funkcije;
	public String komentar;

	public String getNazivFunkcije() {
		return naziv_funkcije;
	}

	public void setNazivFunkcije(String naziv_funkcije) {
		this.naziv_funkcije = naziv_funkcije;
	}

	public String getKomentar() {
		return komentar;
	}

	public void setKomentar(String komentar) {
		this.komentar = komentar;
	}

	public String toString() {
		if (this.komentar != "")
			return this.naziv_funkcije + " " + this.komentar;
		else
			return this.naziv_funkcije;
	}

	public static List<Funkcija> DohvatiFunkcije() throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "SELECT naziv_funkcije, komentar FROM music_studio.funkcija";
		PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = ps.executeQuery();
		List<Funkcija> funkcije = new ArrayList<Funkcija>();
		while (rs.next()) {
			Funkcija funkcija = new Funkcija();
			funkcija.setNazivFunkcije(rs.getString(1));
			funkcija.setKomentar(rs.getString(2));
			funkcije.add(funkcija);
		}
		con.close();
		return funkcije;
	}
}
