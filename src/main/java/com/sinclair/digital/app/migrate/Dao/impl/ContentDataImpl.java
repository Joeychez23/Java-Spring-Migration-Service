package com.sinclair.digital.app.migrate.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.model.ContentData;
import com.sinclair.digital.app.migrate.model.mappers.RowMappers.ContentDataRowMap;


public class ContentDataImpl {
	private final static Logger logger = Logger.getLogger(ContentDataImpl.class);

	private Connection connection;

	public ContentDataImpl(Connection connection) {
		this.connection = connection;
	}

	public List<ContentData> getData(String uuidQueryStr) {;

		String query = String.format("select * from content_data where uuid_content in %s", uuidQueryStr);
		List<ContentData> contentList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement =  connection.prepareStatement(query);
			rs = statement.executeQuery();
			ContentDataRowMap mapper = new ContentDataRowMap();
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