package com.sinclair.digital.app.migrate.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.model.ContentBrands;
import com.sinclair.digital.app.migrate.model.mappers.RowMappers.ContentBrandsRowMap;

public class ContentBrandsImpl {
	private final static Logger logger = Logger.getLogger(FilterImpl.class);
	
	private Connection connection;
	
	public ContentBrandsImpl(Connection connection) {
		this.connection = connection;
	}
	
	public List<ContentBrands> getData(String uuidQueryStr) {
		
		// WE NEED TO WORK ON THIS
		String query = String.format("select * from content_brands where uuid_content in %s", uuidQueryStr);
		
		List<ContentBrands> contentBrandsList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try {
			statement = connection.prepareStatement(query);
			rs = statement.executeQuery();
			ContentBrandsRowMap mapper = new ContentBrandsRowMap();
			contentBrandsList = mapper.mapData(rs);
			
			rs.close();
			statement.close();
			return contentBrandsList;
			
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
		
		return contentBrandsList;
	}

}
