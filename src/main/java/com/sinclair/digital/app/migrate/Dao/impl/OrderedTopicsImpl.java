package com.sinclair.digital.app.migrate.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.model.OrderedTopics;
import com.sinclair.digital.app.migrate.model.mappers.RowMappers.OrderedTopicsRowMap;


public class OrderedTopicsImpl {
	private final static Logger logger = Logger.getLogger(OrderedTopicsImpl.class);

	private Connection connection;

	public OrderedTopicsImpl(Connection connection) {
		this.connection = connection;
	}

	public List<OrderedTopics> getData(String uuidQueryStr) {

		String query = "select * from ordered_topics where uuid_content in " + uuidQueryStr;

		List<OrderedTopics> contentList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement =  connection.prepareStatement(query);
			rs = statement.executeQuery();
			OrderedTopicsRowMap mapper = new OrderedTopicsRowMap();
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