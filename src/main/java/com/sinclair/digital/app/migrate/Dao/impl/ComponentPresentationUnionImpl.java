package com.sinclair.digital.app.migrate.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.model.ComponentPresentationUnion;
import com.sinclair.digital.app.migrate.model.mappers.RowMappers.ComponentPresentationUnionRowMap;

public class ComponentPresentationUnionImpl {
    private final static Logger logger = Logger.getLogger(ComponentPresentationUnionImpl.class);

    private Connection connection;

    public ComponentPresentationUnionImpl(Connection connection) {
        this.connection = connection;
    }

    public List<ComponentPresentationUnion> getData(String uuidQueryStr) {
        String query = String.format("select * from component_presentation_union where uuid in %s", uuidQueryStr);
        List<ComponentPresentationUnion> contentList = new ArrayList<>();
        PreparedStatement statement = null;
		ResultSet rs = null;
        try {
            statement = connection.prepareStatement(query);
            rs = statement.executeQuery();
            ComponentPresentationUnionRowMap mapper = new ComponentPresentationUnionRowMap();
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