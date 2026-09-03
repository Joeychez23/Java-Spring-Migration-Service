package com.sinclair.digital.app.migrate.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.model.Tag;
import com.sinclair.digital.app.migrate.model.mappers.RowMappers.TagRowMap;


import com.sinclair.digital.app.utils.UuidQueryBuilder;


public class TagImpl {
	private final static Logger logger = Logger.getLogger(TagImpl.class);

	private Connection connection;

	public TagImpl(Connection connection) {
		this.connection = connection;
	}

	public List<Tag> getData(String uuidQueryStr) {
		//String query = "select * from tag where uuid in " + uuidQueryStr;


		String query = "SELECT uuid_tag FROM sinclair.navigation where uuid_tag NOT IN (SELECT uuid FROM sinclair.tag where uuid IN (SELECT uuid FROM sinclair.navigation where uuid_tag IS NOT NULL) IS NOT NULL )";

		//String query = "select distinct nav.uuid_tag from navigation nav left outer join tag t on t.uuid = nav.uuid_tag where nav.uuid_tag is not null and t.uuid is null;";

		List<Tag> contentList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement =  connection.prepareStatement(query);
			rs = statement.executeQuery();
			//TagRowMap mapper = new TagRowMap();
			//contentList = mapper.mapData(rs);

			logger.info("hello world");

			List<String> uuidList = new ArrayList<>();

			while (rs.next()) {
				uuidList.add(rs.getString("uuid_tag"));

				//logger.info(rs.getString("COUNT(*)"));
				//System.exit(0);
			}


			//logger.info(uuidList.size());

			UuidQueryBuilder uuidQueryBuilder = new UuidQueryBuilder();
			List<String> uuidQueryStrArr = uuidQueryBuilder.getUuidQuery(uuidList, 10000);


			logger.info(uuidQueryStrArr);


			System.exit(0);


			


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