package com.sinclair.digital.app.migrate.model.mappers.RowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import com.sinclair.digital.app.migrate.model.AssociatedContent;

public class AssociatedContentRowMap {
    private final Logger logger = Logger.getLogger(AssociatedContentRowMap.class);

    public List<AssociatedContent> mapData(ResultSet rs) throws SQLException {
        List<AssociatedContent> contentList = new ArrayList<>();
        try {
            while (rs.next()) {
                AssociatedContent currContent = this.mapRow(rs);
                contentList.add(currContent);
            }
            rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return contentList;
    }

    public AssociatedContent mapRow(ResultSet rs) throws SQLException {
        AssociatedContent currContent = new AssociatedContent();
        currContent.setUuidContent(rs.getString("uuid_content"));
        currContent.setUuidAssociatedContent(rs.getString("uuid_associated_content"));
        return currContent;
    }
}