package com.sinclair.digital.app.migrate.uploaders;



import java.util.List;
import com.sinclair.digital.app.mysql.MySqlImport;
import com.sinclair.digital.app.mysql.MySqlExport;
import com.sinclair.digital.app.utils.UuidQueryBuilder;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.model.PropertyContentUnion;
import com.sinclair.digital.app.migrate.model.statements.PropertyContentUnionStatement;
import com.sinclair.digital.app.migrate.Dao.impl.PropertyContentUnionImpl;

public class PropertyContentUnionUploader {

    private final static Logger logger = Logger.getLogger(PropertyContentUnionUploader.class);

    private int splitIndex = 100000;

    public PropertyContentUnionUploader(List<String> versionSetUuidList , MySqlExport sqlExport, MySqlImport sqlImport) {     
        System.out.print("\n");   
        logger.info("Starting \"property_content_union\" table upload...");

        UuidQueryBuilder uuidQueryBuilder = new UuidQueryBuilder();
        List<String> versionSetUuid = uuidQueryBuilder.getUuidQuery(versionSetUuidList, splitIndex);

        for(int i = 0; i < versionSetUuid.size(); i++) {
        	String uuidQueryStr = versionSetUuid.get(i);
    		List<PropertyContentUnion> tempList = new PropertyContentUnionImpl(sqlImport.getConnection()).getData(uuidQueryStr);
            try {
                PreparedStatement stmtCheckOff = sqlExport.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS=0");
                stmtCheckOff.execute();
                stmtCheckOff.close();
    
                for (int j = 0; j < tempList.size(); j++) {
                    PropertyContentUnion propertyContentUnionObj = tempList.get(j);
                    new PropertyContentUnionStatement(sqlExport.getConnection()).updatePropertyContentUnionRow(propertyContentUnionObj);
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
        logger.info("\"property_content_union\" Table Uploaded");
    }
    
}