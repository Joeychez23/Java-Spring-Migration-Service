package com.sinclair.digital.app.migrate.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.model.SuggestedTeaserPlacement;
import com.sinclair.digital.app.migrate.model.mappers.RowMappers.SuggestedTeaserPlacementRowMap;


public class SuggestedTeaserPlacementImpl {
	private final static Logger logger = Logger.getLogger(SuggestedTeaserPlacementImpl.class);

	private Connection connection;

	public SuggestedTeaserPlacementImpl(Connection connection) {
		this.connection = connection;
	}

	public List<SuggestedTeaserPlacement> getData(String uuidQueryStr) {

		String query = "select * from suggested_teaser_placement where uuid_related_package in " + uuidQueryStr;

		List<SuggestedTeaserPlacement> contentList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			statement =  connection.prepareStatement(query);
			rs = statement.executeQuery();
			SuggestedTeaserPlacementRowMap mapper = new SuggestedTeaserPlacementRowMap();
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