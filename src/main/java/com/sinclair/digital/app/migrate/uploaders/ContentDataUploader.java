package com.sinclair.digital.app.migrate.uploaders;

import java.util.List;
import com.sinclair.digital.app.mysql.MySqlImport;
import com.sinclair.digital.app.mysql.MySqlExport;
import com.sinclair.digital.app.utils.UuidQueryBuilder;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.model.ContentData;
import com.sinclair.digital.app.migrate.model.statements.ContentDataStatement;
import com.sinclair.digital.app.migrate.Dao.impl.ContentDataImpl;

public class ContentDataUploader {
    private final static Logger logger = Logger.getLogger(ContentUploader.class);

    private int splitIndex = 2000;

    public ContentDataUploader(List<String> uuidList, MySqlExport sqlExport, MySqlImport sqlImport) {
        System.out.print("\n");
        logger.info("Starting \"content_data\" table upload...");

        UuidQueryBuilder uuidQueryBuilder = new UuidQueryBuilder();
        List<String> uuidQueryStrArr = uuidQueryBuilder.getUuidQuery(uuidList, splitIndex);

        for (int i = 0; i < uuidQueryStrArr.size(); i++) {
            String uuidQueryStr = uuidQueryStrArr.get(i);
            List<ContentData> tempList = new ContentDataImpl(sqlImport.getConnection()).getData(uuidQueryStr);

            try {
                PreparedStatement stmtCheckOff = sqlExport.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS=0");
                stmtCheckOff.execute();
                stmtCheckOff.close();
    
                for (int j = 0; j < tempList.size(); j++) {
                    ContentData contentObj = tempList.get(j);
                    new ContentDataStatement(sqlExport.getConnection()).updateContentDataRow(contentObj);
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
        logger.info("\"content_data\" Table Uploaded");
    }
}