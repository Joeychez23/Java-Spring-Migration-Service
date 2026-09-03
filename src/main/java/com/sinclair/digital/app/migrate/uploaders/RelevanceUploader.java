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

import com.sinclair.digital.app.migrate.model.Relevance;
import com.sinclair.digital.app.migrate.model.statements.RelevanceStatement;
import com.sinclair.digital.app.migrate.Dao.impl.RelevanceImpl;

public class RelevanceUploader {
    private final static Logger logger = Logger.getLogger(RelevanceUploader.class);

    private int splitIndex = 25;

    private List<String> tagList = new ArrayList<>();

    public RelevanceUploader(JSONArray jsonArray, MySqlExport sqlExport, MySqlImport sqlImport) {
        System.out.print("\n");
        logger.info("Starting \"relevance\" table upload...");

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
            List<Relevance> tempList = new RelevanceImpl(sqlImport.getConnection()).getData(uuidQueryStr);
            try {
                PreparedStatement stmtCheckOff = sqlExport.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS=0");
                stmtCheckOff.execute();
                stmtCheckOff.close();
    
                for (int j = 0; j < tempList.size(); j++) {
                    Relevance relevanceObj = tempList.get(j);
                    new RelevanceStatement(sqlExport.getConnection()).updateRelevanceRow(relevanceObj);
    
                    if(relevanceObj.getUuidTag() != null && !this.tagList.contains(relevanceObj.getUuidTag())) {
                        this.tagList.add(relevanceObj.getUuidTag());
                    }
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
        logger.info("\"relevance\" Table Uploaded");
    }

    public List<String> getTagList() {
        return this.tagList;
    }
}