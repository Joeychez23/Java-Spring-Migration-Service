package com.sinclair.digital.app.migrate.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.model.ReferenceObject;
import com.sinclair.digital.app.migrate.model.mappers.RowMappers.ReferenceObjectRowMap;

public class ReferenceObjectImpl {
	private final static Logger logger = Logger.getLogger(ReferenceObjectImpl.class);

	private Connection connection;

	public ReferenceObjectImpl(Connection connection) {
		this.connection = connection;
	}
	

	public List<ReferenceObject> getAllByVersionSet(String uuidQueryStr) {
		String query = String.format("select * from reference_object where package_version_set_uuid in %s", uuidQueryStr);
		List<ReferenceObject> contentList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(query);
			rs = statement.executeQuery();
			ReferenceObjectRowMap mapper = new ReferenceObjectRowMap();
			contentList = mapper.mapData(rs);
			rs.close();
			statement.close();
			return contentList;
		} catch (

		SQLException e) {
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