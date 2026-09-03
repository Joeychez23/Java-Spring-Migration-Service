package com.sinclair.digital.app.migrate.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.model.Relevance;
import com.sinclair.digital.app.migrate.model.mappers.RowMappers.RelevanceRowMap;


public class RelevanceImpl {
	private final static Logger logger = Logger.getLogger(RelevanceImpl.class);

	private Connection connection;

	public RelevanceImpl(Connection connection) {
		this.connection = connection;
	}

	public List<Relevance> getData(String uuidQueryStr) {
		String query = "select * from relevance where uuid_content in " + uuidQueryStr;

		List<Relevance> contentList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement =  connection.prepareStatement(query);
			rs = statement.executeQuery();
			RelevanceRowMap mapper = new RelevanceRowMap();
			contentList = mapper.mapData(rs);
			rs.close();
			statement.close();
			return contentList;
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

		return contentList;
	}
}