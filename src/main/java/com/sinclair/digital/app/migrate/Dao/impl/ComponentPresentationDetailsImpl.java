package com.sinclair.digital.app.migrate.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.model.ComponentPresentationDetails;
import com.sinclair.digital.app.migrate.model.mappers.RowMappers.ComponentPresentationDetailsRowMap;

public class ComponentPresentationDetailsImpl {
	private final static Logger logger = Logger.getLogger(ComponentPresentationDetailsImpl.class);
	
	private Connection connection;
	
	public ComponentPresentationDetailsImpl(Connection connection) {
		this.connection = connection;
	}
	
	public List<ComponentPresentationDetails> getData(String uuidQueryStr) {
		String query = "select * from component_presentation_details where uuid in " + uuidQueryStr;
		
		List<ComponentPresentationDetails> cpdList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try {
			statement = connection.prepareStatement(query);
			rs = statement.executeQuery();
			ComponentPresentationDetailsRowMap mapper = new ComponentPresentationDetailsRowMap();
			cpdList = mapper.mapData(rs);
			
			rs.close();
			statement.close();
			return cpdList;
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
		
		return cpdList;
	}
}
