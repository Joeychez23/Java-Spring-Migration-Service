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

import com.sinclair.digital.app.migrate.model.ContentAttributes;
import com.sinclair.digital.app.migrate.model.statements.ContentAttributesStatement;
import com.sinclair.digital.app.migrate.Dao.impl.ContentAttributesImpl;

public class ContentAttributesUploader {
    private final static Logger logger = Logger.getLogger(ContentUploader.class);

    private int splitIndex = 5000;

    public ContentAttributesUploader(JSONArray jsonArray, MySqlExport sqlExport, MySqlImport sqlImport) {
        System.out.print("\n");
        logger.info("Starting \"content_attributes\" table upload...");

        List<String> uuidList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject currJson = jsonArray.getJSONObject(i);

            if (currJson.has("uuid")) {
                if (currJson.getString("uuid") != null && !uuidList.contains(currJson.getString("uuid"))) {
                    uuidList.add(currJson.getString("uuid"));
                }
            }
        }

        UuidQueryBuilder uuidQueryBuilder = new UuidQueryBuilder();
        List<String> uuidQueryStrArr = uuidQueryBuilder.getUuidQuery(uuidList, splitIndex);

        for (int i = 0; i < uuidQueryStrArr.size(); i++) {
            String uuidQueryStr = uuidQueryStrArr.get(i);
            List<ContentAttributes> tempList = new ContentAttributesImpl(sqlImport.getConnection())
                    .getData(uuidQueryStr);
            try {
                PreparedStatement stmtCheckOff = sqlExport.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS=0");
                stmtCheckOff.execute();
                stmtCheckOff.close();

                for (int j = 0; j < tempList.size(); j++) {
                    ContentAttributes contentObj = tempList.get(j);
                    new ContentAttributesStatement(sqlExport.getConnection()).updateContentAttributesRow(contentObj);
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
        logger.info("\"content_attributes\" Table Uploaded");

    }
}