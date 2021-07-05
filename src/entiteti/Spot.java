package entiteti;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import msm.DatabaseConnector;

public class Spot {
	public int idSpot;
	public int idPjesma;
	public int godina;
	public String nazivPjesme;
	public String youtubeLink;
	public String lokacija;
	
	public int getIdPjesma() {
		return idPjesma;
	}

	public void setIdPjesma(int idPjesma) {
		this.idPjesma = idPjesma;
	}

	public String getLokacija() {
		return lokacija;
	}

	public void setLokacija(String lokacija) {
		this.lokacija = lokacija;
	}

	public int getIdSpot() {
		return idSpot;
	}
	
	public void setIdSpot(int id_Spot) {
		this.idSpot = id_Spot;
	}	

	public String getNazivPjesme() {
		return nazivPjesme;
	}

	public void setNazivPjesme(String nazivPjesme) {
		this.nazivPjesme = nazivPjesme;
	}	
	
	public String getYoutubeLink() {
		return youtubeLink;
	}
	
	public void setYoutubeLink(String link) {
		this.youtubeLink = link;
	}
	
	public int getGodina() {
		return godina;
	}
	
	public void setGodina(int godina) {
		this.godina = godina;
	}
	
	public void setValue(ResultSet rs) {
		try {
			this.idSpot = rs.getInt(1);
			this.nazivPjesme = rs.getString(2);
			this.godina = rs.getInt(3);
			this.youtubeLink = rs.getString(4);
			this.lokacija = rs.getString(5);
			this.idPjesma = rs.getInt(6);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Spot> dohvatiSpotove() throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "SELECT s.id_Spot, p.naziv, s.godina, s.youtube_link, s.lokacija, p.id_pjesma "
				+ "from music_studio.Spot as s "
				+ "JOIN pjesma as p "
				+ "ON p.id_pjesma = s.id_pjesma";
		PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = ps.executeQuery();
		List<Spot> spotovi = new ArrayList<Spot>();
		while (rs.next()) {
			Spot spot = new Spot();
			spot.setValue(rs);
			spotovi.add(spot);
		}
		con.close();
		return spotovi;
	}
	
	public static Spot dodajSpot (Spot spot) throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "INSERT INTO Spot(godina, youtube_link, id_pjesma, lokacija) VALUES (?, ?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, spot.getGodina());
		ps.setString(2, spot.getYoutubeLink());
		ps.setInt(3, spot.getIdPjesma());
		ps.setString(4, spot.getLokacija());
		ps.execute();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		int id = rs.getInt(1);
		spot.setIdSpot(id);
		con.close();
		return spot;
	}
	
	public static int izbrisiSpot (int id) throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "DELETE FROM Spot WHERE id_Spot = ?";
		PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ps.setInt(1, id);
		ps.execute();
		con.close();
		return id;
	}
	
	public static void dodajIzvodjaceZaSpot (int id, List<Integer> izvodjaci) throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "INSERT INTO izvodjacSpot(id_izvodjac, id_Spot) VALUES (?, ?);";
		PreparedStatement ps = con.prepareStatement(query);
		for(int id_izvodjac : izvodjaci) {
			ps.setInt(1, id_izvodjac);
			ps.setInt(2, id);
			ps.addBatch();
		}
		ps.executeBatch();
		con.close();
	}
	
	public static int dohvatiAlbumIdZaSpot(int id) throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "SELECT id_album FROM music_studio.pjesma where id_pjesma=?";
		PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		int res = rs.getInt(1);
		con.close();
		return res;
	}
	
	public static void izbrisiIzvodjaceZaSpot (int id) throws SQLException {
		var con = DatabaseConnector.getConnection();
		String query = "DELETE FROM izvodjacSpot WHERE id_Spot = ?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, id);
		ps.execute();
		con.close();
	}
	
	public static Spot izmijeniSpot (Spot Spot) throws SQLException {
		var con = DatabaseConnector.getConnection();		
		String query = "UPDATE Spot SET godina =?, youtube_link=?, lokacija=? WHERE id_Spot = ?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, Spot.getGodina());
		ps.setString(2, Spot.getYoutubeLink());
		ps.setString(3, Spot.getLokacija());
		ps.setInt(4, Spot.getIdSpot());
		ps.execute();
		con.close();
		return Spot;
	}
	
	public static Spot filterById(int id, List<Spot> Spots) {
	    for(Spot Spot : Spots) {
	        if(Spot.getIdSpot() == id) {
	            return Spot;
	        }
	    }
	    return null;
	}
}
