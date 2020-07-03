package msm;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnector {
	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/music_studio?serverTimezone=UTC", "root", "root");
		} catch (Exception e) {
			System.out.println(e);
		}
		return con;
	}
}
