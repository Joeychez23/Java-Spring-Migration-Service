package com.sinclair.digital.app.migrate.uploaders;

import java.util.List;
import com.sinclair.digital.app.mysql.MySqlImport;
import com.sinclair.digital.app.mysql.MySqlExport;
import com.sinclair.digital.app.utils.UuidQueryBuilder;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ArrayList;
import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.model.Syndication;
import com.sinclair.digital.app.migrate.model.statements.SyndicationStatement;
import com.sinclair.digital.app.migrate.Dao.impl.SyndicationImpl;

public class SyndicationUploader {

    private final static Logger logger = Logger.getLogger(SyndicationUploader.class);

    private int splitIndex = 200;

    public SyndicationUploader(MySqlExport sqlExport, MySqlImport sqlImport) {
        System.out.print("\n");
        logger.info("Starting \"syndication\" table upload...");

        List<String> propertyUuidList = new ArrayList<>();
        
        propertyUuidList.addAll(new SyndicationImpl(sqlImport.getConnection()).getPropertyUuid());

        UuidQueryBuilder uuidQueryBuilder = new UuidQueryBuilder();
        List<String> propertyUuidQueryList = uuidQueryBuilder.getUuidQuery(propertyUuidList, splitIndex);


        for (int i = 0; i < propertyUuidQueryList.size(); i++) {
            String uuidQueryStr = propertyUuidQueryList.get(i);
            List<Syndication> tempList = new SyndicationImpl(sqlImport.getConnection()).getData(uuidQueryStr);
            try {
                PreparedStatement stmtCheckOff = sqlExport.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS=0");
                stmtCheckOff.execute();
                stmtCheckOff.close();

                try {
                    PreparedStatement uniqueCheckOff = sqlExport.getConnection().prepareStatement("ALTER TABLE syndication DROP KEY uniquie_key ");
                    uniqueCheckOff.execute();
                    uniqueCheckOff.close();
                } catch (SQLException e) {
                    //e.printStackTrace();
                }

                for (int j = 0; j < tempList.size(); j++) {
                    Syndication syndicationObj = tempList.get(j);
                    new SyndicationStatement(sqlExport.getConnection()).updateSyndicationRow(syndicationObj);
                }
                
                try {
                    PreparedStatement uniqueCheckOn = sqlExport.getConnection().prepareStatement("ALTER TABLE syndication ADD CONSTRAINT uniquie_key UNIQUE (`uuid_package`, `uuid_property`)");
                    uniqueCheckOn.execute();
                    uniqueCheckOn.close();
                } catch (SQLException e) {
                    //e.printStackTrace();
                }
                
                PreparedStatement stmtCheckOn = sqlExport.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS=1");
                stmtCheckOn.execute();
                stmtCheckOn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.print("#");
            tempList.clear();
        }
        System.out.print("\n");
        logger.info("\"syndication\" Table Uploaded");

    }
}