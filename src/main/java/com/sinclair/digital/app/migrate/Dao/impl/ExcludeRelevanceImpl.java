package com.sinclair.digital.app.migrate.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.model.ExcludeRelevance;
import com.sinclair.digital.app.migrate.model.mappers.RowMappers.ExcludeRelevanceRowMap;


public class ExcludeRelevanceImpl {
	private final static Logger logger = Logger.getLogger(ExcludeRelevanceImpl.class);

	private Connection connection;

	public ExcludeRelevanceImpl(Connection connection) {
		this.connection = connection;
	}

	public List<ExcludeRelevance> getData(String uuidQueryStr) {

		String query = "select * from exclude_relevance where uuid_content in " + uuidQueryStr;

		List<ExcludeRelevance> contentList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement =  connection.prepareStatement(query);
			rs = statement.executeQuery();
			ExcludeRelevanceRowMap mapper = new ExcludeRelevanceRowMap();
			contentList = mapper.mapData(rs);
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