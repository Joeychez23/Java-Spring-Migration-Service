package com.sinclair.digital.app.migrate.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.model.AssociatedContent;
import com.sinclair.digital.app.migrate.model.mappers.RowMappers.AssociatedContentRowMap;

public class AssociatedContentImpl {
	private final static Logger logger = Logger.getLogger(AssociatedContentImpl.class);

	private Connection connection;


	public AssociatedContentImpl(Connection connection) {
		this.connection = connection;
	}

	public List<AssociatedContent> getData(String uuidQueryStr) {
		String query = "select * from associated_content where uuid_content in " + uuidQueryStr;

		AssociatedContentRowMap mapper = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<AssociatedContent> contentList = new ArrayList<>();
		try {
			statement = connection.prepareStatement(query);
			rs = statement.executeQuery();
			mapper = new AssociatedContentRowMap();
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