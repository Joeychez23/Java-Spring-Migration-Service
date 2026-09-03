package com.sinclair.digital.app.migrate.uploaders;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.sinclair.digital.app.migrate.Dao.impl.ContentBrandsImpl;
import com.sinclair.digital.app.migrate.model.ContentBrands;
import com.sinclair.digital.app.migrate.model.statements.ContentBrandsStatement;
import com.sinclair.digital.app.mysql.MySqlExport;
import com.sinclair.digital.app.mysql.MySqlImport;
import com.sinclair.digital.app.utils.UuidQueryBuilder;

public class ContentBrandsUploader {
	private final static Logger logger = Logger.getLogger(ContentBrandsUploader.class);
	
    public ContentBrandsUploader(JSONArray jsonArray,  MySqlExport sqlExport, MySqlImport sqlImport) {
        System.out.print("\n");
    	logger.info("Starting \"content_brands\" table upload...");
    	
    	int splitIndex = 2000;
    	
    	List<String> uuidList = new ArrayList<>();
    	for(int i = 0; i < jsonArray.length(); i++) {
    		JSONObject currJson = jsonArray.getJSONObject(i);
            
            if(currJson.has("uuid")) {
                if (currJson.getString("uuid") != null && !uuidList.contains(currJson.getString("uuid"))) {
                    uuidList.add(currJson.getString("uuid"));
                }
            }
    	}
    	
    	UuidQueryBuilder queryBuilder = new UuidQueryBuilder();
    	List<String> contentQueryList = queryBuilder.getUuidQuery(uuidList, splitIndex);
    	
        for (int i = 0; i < contentQueryList.size(); i++) {
            String uuidQueryStr = contentQueryList.get(i);
            List<ContentBrands> tempList = new ContentBrandsImpl(sqlImport.getConnection()).getData(uuidQueryStr);
            try {
                PreparedStatement stmtCheckOff = sqlExport.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS=0");
                stmtCheckOff.execute();
                
                for(int j = 0; j < tempList.size(); j++) {
                    ContentBrands cbrandsObject = tempList.get(j);
                    new ContentBrandsStatement(sqlExport.getConnection()).updateContentBrandsRow(cbrandsObject);
                }
                
                PreparedStatement stmtCheckOn = sqlExport.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS=1");
                stmtCheckOn.execute(); 
            } catch (Exception e) {
                // TODO: handle exception
            }
            System.out.print("#");
        }
        System.out.print("\n");
        logger.info("\"content_brands\" Table Uploaded");
    	
    }

}
















