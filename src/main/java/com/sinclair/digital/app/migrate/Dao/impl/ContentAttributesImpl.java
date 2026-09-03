package com.sinclair.digital.app.migrate.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.model.ContentAttributes;
import com.sinclair.digital.app.migrate.model.mappers.RowMappers.ContentAttributesRowMap;




public class ContentAttributesImpl {
	private final static Logger logger = Logger.getLogger(ContentAttributesImpl.class);

	private Connection connection;

	public ContentAttributesImpl(Connection connection) {
		this.connection = connection;
	}

	public List<ContentAttributes> getData(String uuidQueryStr) {
		String query = "select * from content_attributes where uuid_parent in " + uuidQueryStr;

		PreparedStatement statement = null;
		ResultSet rs = null;
		List<ContentAttributes> contentList = new ArrayList<>();
		try {
			statement =  connection.prepareStatement(query);
			rs = statement.executeQuery();
			ContentAttributesRowMap mapper = new ContentAttributesRowMap();
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