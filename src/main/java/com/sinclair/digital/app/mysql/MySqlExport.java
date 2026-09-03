package com.sinclair.digital.app.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class MySqlExport {

        private final static Logger logger = Logger.getLogger(MySqlExport.class);
        private Connection con = null;

        public void connect() {
                SecretData secretData = new SecretData();
                String exportJDBC = secretData.getExportString();

                try {
                       String mySqlUrl = exportJDBC;

                        this.con = DriverManager.getConnection(mySqlUrl);

                        if (this.con == null) {
                                logger.fatal("Can't access database using: " + mySqlUrl);
                        } else {
                                logger.info("Connected");
                        }
                } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }

        public Connection getConnection() {
                return this.con;
        }

        public void closeConnection() {
                this.closeConnection();
        }

}