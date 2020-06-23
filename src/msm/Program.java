package msm;

import java.awt.EventQueue;
import java.sql.Connection;

public class Program {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Connection con=DatabaseConnector.getConnection();
	}
}
