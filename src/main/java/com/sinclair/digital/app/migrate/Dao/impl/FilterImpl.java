package com.sinclair.digital.app.migrate.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.model.Filter;
import com.sinclair.digital.app.migrate.model.mappers.RowMappers.FilterRowMap;

public class FilterImpl {
	private final static Logger logger = Logger.getLogger(FilterImpl.class);
	
	private Connection connection;
	
	public FilterImpl(Connection connection) {
		this.connection = connection;
	}
	
	public List<Filter> getData(String uuidQueryStr) {
		String query = "select * from filter where uuid in " + uuidQueryStr;
		
		List<Filter> filterList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try {
			statement = connection.prepareStatement(query);
			rs = statement.executeQuery();
			FilterRowMap mapper = new FilterRowMap();
			filterList = mapper.mapData(rs);
			
			rs.close();
			statement.close();
			return filterList;
			
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
		
		return filterList;
	}
}
