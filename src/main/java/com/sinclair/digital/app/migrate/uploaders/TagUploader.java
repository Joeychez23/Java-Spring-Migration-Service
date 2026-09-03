package com.sinclair.digital.app.migrate.uploaders;

import java.util.List;
import com.sinclair.digital.app.mysql.MySqlImport;
import com.sinclair.digital.app.mysql.MySqlExport;
import com.sinclair.digital.app.utils.UuidQueryBuilder;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.model.Tag;
import com.sinclair.digital.app.migrate.model.statements.TagStatement;
import com.sinclair.digital.app.migrate.Dao.impl.TagImpl;

public class TagUploader {
    private final static Logger logger = Logger.getLogger(TagUploader.class);

    private int splitIndex = 2000;

    public TagUploader(List<String> uuidTagList, MySqlExport sqlExport, MySqlImport sqlImport) {
        System.out.print("\n");
        logger.info("Starting \"tag\" table upload...");

        UuidQueryBuilder uuidQueryBuilder = new UuidQueryBuilder();
        List<String> uuidQueryStrArr = uuidQueryBuilder.getUuidQuery(uuidTagList, splitIndex);


		uuidQueryStrArr.add("123");
        for (int i = 0; i < uuidQueryStrArr.size(); i++) {
            String uuidQueryStr = uuidQueryStrArr.get(i);
            List<Tag> tempList = new TagImpl(sqlImport.getConnection()).getData(uuidQueryStr);
            try {
                PreparedStatement stmtCheckOff = sqlExport.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS=0");
                stmtCheckOff.execute();
                stmtCheckOff.close();
    
                for (int j = 0; j < tempList.size(); j++) {
                    Tag tagObj = tempList.get(j);
                    new TagStatement(sqlExport.getConnection()).updateTagRow(tagObj);
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
        logger.info("\"tag\" Table Uploaded");
        
    }
}