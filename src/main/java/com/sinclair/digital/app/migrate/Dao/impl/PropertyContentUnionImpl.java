package com.sinclair.digital.app.migrate.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.model.PropertyContentUnion;
import com.sinclair.digital.app.migrate.model.mappers.RowMappers.PropertyContentUnionRowMap;


public class PropertyContentUnionImpl {
	private final static Logger logger = Logger.getLogger(PropertyContentUnionImpl.class);

	private Connection connection;

	public PropertyContentUnionImpl(Connection connection) {
		this.connection = connection;
	}
	
	public List<PropertyContentUnion> getData(String uuidQueryStr) {
		String query = "select * from property_content_union where package_version_set_uuid in " + uuidQueryStr;
		
		List<PropertyContentUnion> contentList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement =  connection.prepareStatement(query);
			rs = statement.executeQuery();
            PropertyContentUnionRowMap mapper = new PropertyContentUnionRowMap();
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