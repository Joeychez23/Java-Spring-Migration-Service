package com.sinclair.digital.app.migrate.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.model.ReferenceObjectTag;
import com.sinclair.digital.app.migrate.model.mappers.RowMappers.ReferenceObjectTagRowMap;

public class ReferenceObjectTagImpl {
	private final static Logger logger = Logger.getLogger(ReferenceObjectTagImpl.class);

	private Connection connection;

	public ReferenceObjectTagImpl(Connection connection) {
		this.connection = connection;
	}
	

	public List<ReferenceObjectTag> getAllByUuid(String uuidQueryStr) {
        String query = String.format("select * from reference_object_tags where uuid_reference_object in %s", uuidQueryStr);
		List<ReferenceObjectTag> contentList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(query);
			rs = statement.executeQuery();
			ReferenceObjectTagRowMap mapper = new ReferenceObjectTagRowMap();
			contentList = mapper.mapData(rs);
			rs.close();
			statement.close();
			return contentList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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