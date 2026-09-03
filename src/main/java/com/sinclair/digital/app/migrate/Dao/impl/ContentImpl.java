package com.sinclair.digital.app.migrate.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.Dao.ContentDao;
import com.sinclair.digital.app.migrate.model.Content;
import com.sinclair.digital.app.migrate.model.mappers.RowMappers.ContentRowMapper;

public class ContentImpl implements ContentDao {
	private final Logger logger = Logger.getLogger(ContentImpl.class);

	private Connection con = null;

	public ContentImpl(Connection con) {
		this.con = con;
	}

	public List<Content> getAllContentByTypeWithinDateRange(String type, String startDate, String endDate) {
		String sDate = startDate + " 00:00:00";
		String eDate = endDate + " 23:59:59";

		String query = String.format("select * from content " + "where created between '%s' and '%s' and type = '%s'", sDate, eDate, type);

		logger.info("Executing query: " + query);
		List<Content> content = new ArrayList<>();

		ContentRowMapper mapper = new ContentRowMapper();

		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			statement = con.prepareStatement(query);
			rs = statement.executeQuery();

			while (rs.next()) {
				Content c = mapper.mapRow(rs, 0);
				content.add(c);
			}

			rs.close();
			statement.close();
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

		return content;
	}

	public List<Content> getData(String uuidStr) {
		String query = "select * from content inner join associated_content ac on ac.uuid_associated_content = content.uuid where uuid_content in " + uuidStr;
		List<Content> contentList = null;
		ResultSet rs = null;
		PreparedStatement statement = null;
		try {
			contentList = new ArrayList<>();
			statement = con.prepareStatement(query);
			rs = statement.executeQuery();
			ContentRowMapper mapper = new ContentRowMapper();
			while (rs.next()) {
				Content currContent = mapper.mapRow(rs, 0);
				contentList.add(currContent);
			}

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
