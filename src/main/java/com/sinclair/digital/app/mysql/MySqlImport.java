package com.sinclair.digital.app.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class MySqlImport {
	private final static Logger logger = Logger.getLogger(MySqlImport.class);

	private Connection con = null;

	public void connect() {
		SecretData secretData = new SecretData();
		String importJDBC = secretData.getImportString();

		try {
			String mySqlUrl = importJDBC;

			con = DriverManager.getConnection(mySqlUrl);

			if (con == null) {
				logger.fatal("Can't access database using: " + mySqlUrl);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return con;
	}

	public void closeConnection() {
		this.closeConnection();
	}
}
