package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import oracle.jdbc.driver.OracleDriver;

public class ConnectionFactory {
	private static Logger log = Logger.getLogger(ConnectionFactory.class);
	private static ConnectionFactory cf = new ConnectionFactory();
	
	private ConnectionFactory() {
		super();
	}
	
	public static ConnectionFactory getInstance() {
		return cf;
	}
	
	public Connection getConnection() {
		Connection conn = null;
		
		Properties prop = new Properties();
		
		try {
			DriverManager.registerDriver(new OracleDriver());
			
			prop.load(new FileReader("src/main/resources/application.properties"));
			
			conn = DriverManager.getConnection(
					prop.getProperty("url"),
					prop.getProperty("usr"),
					prop.getProperty("pw"));
		} catch (SQLException sqle) {
			log.error(sqle.getMessage());
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			log.error(ioe.getMessage());
		}
		return conn;
	}
}
