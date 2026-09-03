package com.sinclair.digital.app.migrate.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.model.Syndication;
import com.sinclair.digital.app.migrate.model.mappers.RowMappers.SyndicationRowMap;


public class SyndicationImpl {
	private final static Logger logger = Logger.getLogger(SyndicationImpl.class);

	private Connection connection;

	public SyndicationImpl(Connection connection) {
		this.connection = connection;
	}

	public List<Syndication> getData(String queryStr) {
        String query = String.format("select * from syndication where uuid_property in %s", queryStr);

		List<Syndication> propertyUuidList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement =  connection.prepareStatement(query);
			rs = statement.executeQuery();
            SyndicationRowMap mapper = new SyndicationRowMap();
			propertyUuidList = mapper.mapData(rs);

			rs.close();
			statement.close();
			return propertyUuidList;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			try {
				rs.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return propertyUuidList;
	}



	public List<String> getPropertyUuid() {

        String query = String.format("select uuid from property");

		List<String> propertyUuidList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement =  connection.prepareStatement(query);
			rs = statement.executeQuery();
			
			while(rs.next()) {
				propertyUuidList.add(rs.getString("uuid"));
			}
			
			rs.close();
			statement.close();
			return propertyUuidList;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			try {
				rs.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return propertyUuidList;
	}
}