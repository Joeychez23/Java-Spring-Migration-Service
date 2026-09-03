package com.sinclair.digital.app.migrate.uploaders;

import java.util.List;
import com.sinclair.digital.app.mysql.MySqlImport;
import com.sinclair.digital.app.mysql.MySqlExport;
import com.sinclair.digital.app.utils.UuidQueryBuilder;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.model.AssociatedContent;
import com.sinclair.digital.app.migrate.model.Content;
import com.sinclair.digital.app.migrate.model.statements.AssociatedContentStatement;
import com.sinclair.digital.app.migrate.model.statements.ContentStatement;
import com.sinclair.digital.app.migrate.Dao.impl.AssociatedContentImpl;
import com.sinclair.digital.app.migrate.Dao.impl.ContentImpl;

public class AssociatedContentUploader {
    private final static Logger logger = Logger.getLogger(AssociatedContentUploader.class);

    private int splitIndex = 500;

    private List<String> contentJoinUuid = new ArrayList<>();

    public List<String> getJoinUuidList() {
        return this.contentJoinUuid;
    }

    public AssociatedContentUploader(JSONArray jsonArray, MySqlExport sqlExport, MySqlImport sqlImport) {
        System.out.print("\n");
        logger.info("Starting \"associated_content\" table upload...");

        List<String> uuidList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject currJson = jsonArray.getJSONObject(i);

            if (currJson.has("uuid")) {
                if (currJson.getString("uuid") != null && !uuidList.contains(currJson.getString("uuid"))) {
                    uuidList.add(currJson.getString("uuid"));
                }

                if (currJson.has("type")) {
                    if (!contentJoinUuid.contains(currJson.getString("uuid")) && currJson.getString("type").equals("MGMDL")) {
                        contentJoinUuid.add(currJson.getString("uuid"));
                    }
                }
            }
        }

        UuidQueryBuilder uuidQueryBuilder = new UuidQueryBuilder();
        List<String> uuidQueryStrArr = uuidQueryBuilder.getUuidQuery(uuidList, splitIndex);

        for (int i = 0; i < uuidQueryStrArr.size(); i++) {
            String uuidQueryStr = uuidQueryStrArr.get(i);
            List<AssociatedContent> tempList = new AssociatedContentImpl(sqlImport.getConnection())
                    .getData(uuidQueryStr);

            try {
                PreparedStatement stmtCheckOff = sqlExport.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS=0");
                stmtCheckOff.execute();
                stmtCheckOff.close();

                for (int j = 0; j < tempList.size(); j++) {
                    AssociatedContent associatedContentObj = tempList.get(j);
                    new AssociatedContentStatement(sqlExport.getConnection())
                            .updateAssociatedContentRow(associatedContentObj);
                }
                PreparedStatement stmtCheckOn = sqlExport.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS=1");
                stmtCheckOn.execute();
                stmtCheckOn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.print("#");
        }
        System.out.print("\n");
        logger.info("\"associated_content\" Table Uploaded");

        System.out.print("\n");

        logger.info("Starting \"acossiated_content -> content\" table upload...");

        for (int i = 0; i < uuidQueryStrArr.size(); i++) {
            String uuidContentStr = uuidQueryStrArr.get(i);
            List<Content> tempList = new ContentImpl(sqlImport.getConnection()).getData(uuidContentStr);

            try {
                PreparedStatement stmtCheckOff = sqlExport.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS=0");
                stmtCheckOff.execute();
                stmtCheckOff.close();

                for (int j = 0; j < tempList.size(); j++) {
                    Content contentObj = tempList.get(j);
                    new ContentStatement(sqlExport.getConnection()).updateContentRow(contentObj);

                    if (!contentJoinUuid.contains(contentObj.getUuid()) && contentObj.getType().equals("MGMDL")) {
                        contentJoinUuid.add(contentObj.getUuid());
                    }
                    if (j % splitIndex == 0 && j != 0) {
                        System.out.print("#");
                    }
                }
                PreparedStatement stmtCheckOn = sqlExport.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS=1");
                stmtCheckOn.execute();
                stmtCheckOn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        System.out.print("\n");
        // logger.info("\"acossiated_content -> content\" Table Uploaded");

    }
}