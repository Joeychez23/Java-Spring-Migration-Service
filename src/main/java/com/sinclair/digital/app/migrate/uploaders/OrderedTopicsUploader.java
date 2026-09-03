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

import com.sinclair.digital.app.migrate.model.OrderedTopics;
import com.sinclair.digital.app.migrate.model.statements.OrderedTopicsStatement;
import com.sinclair.digital.app.migrate.Dao.impl.OrderedTopicsImpl;

public class OrderedTopicsUploader {
    private final static Logger logger = Logger.getLogger(OrderedTopicsUploader.class);

    private int splitIndex = 2000;

    private List<String> tagList = new ArrayList<>();

    public OrderedTopicsUploader(JSONArray jsonArray, MySqlExport sqlExport, MySqlImport sqlImport) {
        System.out.print("\n");
        logger.info("Starting \"ordered_topics\" table upload...");

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
            List<OrderedTopics> tempList = new OrderedTopicsImpl(sqlImport.getConnection()).getData(uuidQueryStr);
            try {
                PreparedStatement stmtCheckOff = sqlExport.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS=0");
                stmtCheckOff.execute();
                stmtCheckOff.close();
    
                for (int j = 0; j < tempList.size(); j++) {
                    OrderedTopics orderedTopicsObj = tempList.get(j);
                    new OrderedTopicsStatement(sqlExport.getConnection()).updateOrderedTopicsRow(orderedTopicsObj);
    
                    if(orderedTopicsObj.getUuidTag() != null && !this.tagList.contains(orderedTopicsObj.getUuidTag())) {
                        this.tagList.add(orderedTopicsObj.getUuidTag());
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
        logger.info("\"ordered_topics\" Table Uploaded");


    }

    public List<String> getTagList() {
        return this.tagList;
    }
}