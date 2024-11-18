package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConnection {
	private static Connection connection = null;
	private Properties properties = new Properties();

	public DatabaseConnection() {
		try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
			if (input == null) {
				System.out.println("No se encontró db.properties");
				return;
			}
			// Cargar propiedades desde el archivo
			properties.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public Connection getConnection() {
		String url = properties.getProperty("db.url");
		String username = properties.getProperty("db.username");
		String password = properties.getProperty("db.password");

		try {
			connection = DriverManager.getConnection(url, username, password);
			System.out.println("Conectado a la base de datos.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;
	}
}