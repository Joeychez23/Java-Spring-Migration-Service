package com.sinclair.digital.app.migrate.model.statements;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.sinclair.digital.app.migrate.model.AssociatedContent;

import org.apache.log4j.Logger;

public class AssociatedContentStatement {
    private final static Logger logger = Logger.getLogger(AssociatedContentStatement.class);

    private Connection connection = null;

    private String getQueryString() {
        String insertStart = "INSERT INTO associated_content(";
        String param = "uuid_content, uuid_associated_content";
        String insertEnd = ")";
        String valueStart = " VALUES (";
        String value = "?, ?";
        String valueEnd = ")";
        String query = insertStart + param + insertEnd + valueStart + value + valueEnd;

        return query;
    }

    public AssociatedContentStatement(Connection connection) {
        this.connection = connection;
    }

    public void updateAssociatedContentRow(AssociatedContent contentObj) {
        PreparedStatement PreState = null;
        try {
            PreState = this.connection.prepareStatement(this.getQueryString());

            PreState.setString(1, contentObj.getUuidContent());
            PreState.setString(2, contentObj.getUuidAssociatedContent());

            PreState.execute();
            PreState.close();
        } catch (SQLException e) {
            // e.printStackTrace();
            // System.out.print("!");
        } finally {
            try {
                PreState.close();
            } catch (SQLException e) {
                // e.printStackTrace();
            }
        }
    }
}