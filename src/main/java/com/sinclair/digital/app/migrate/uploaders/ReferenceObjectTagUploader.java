package com.sinclair.digital.app.migrate.uploaders;

import java.util.List;
import com.sinclair.digital.app.mysql.MySqlImport;
import com.sinclair.digital.app.mysql.MySqlExport;
import com.sinclair.digital.app.utils.UuidQueryBuilder;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.model.ReferenceObjectTag;
import com.sinclair.digital.app.migrate.model.statements.ReferenceObjectTagStatement;
import com.sinclair.digital.app.migrate.Dao.impl.ReferenceObjectTagImpl;

public class ReferenceObjectTagUploader {
    private final static Logger logger = Logger.getLogger(ContentUploader.class);

    private int splitIndex = 2000;

    private List<String> cpuUuids = new ArrayList<>();
    public List<String> getCpuUuids() {
        return this.cpuUuids;
    }

    private List<String> refObjUuids = new ArrayList<>();
    public List<String> getRefObjUuids() {
        return this.refObjUuids;
    }

    public ReferenceObjectTagUploader(List<String> uuidRefObjList, MySqlExport sqlExport, MySqlImport sqlImport) {
        System.out.print("\n");
        logger.info("Starting \"reference_object_tag\" table upload...");

        UuidQueryBuilder uuidQueryBuilder = new UuidQueryBuilder();
        List<String> uuidReferenceObjectTagList = uuidQueryBuilder.getUuidQuery(uuidRefObjList, splitIndex);

        for (int i = 0; i < uuidReferenceObjectTagList.size(); i++) {
            String uuidQueryStr = uuidReferenceObjectTagList.get(i);
            List<ReferenceObjectTag> tempList = new ReferenceObjectTagImpl(sqlImport.getConnection()).getAllByUuid(uuidQueryStr);
            try {
                PreparedStatement stmtCheckOff = sqlExport.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS=0");
                stmtCheckOff.execute();
                stmtCheckOff.close();
    
                for (int j = 0; j < tempList.size(); j++) {
                    ReferenceObjectTag ReferenceObjectTag = tempList.get(j);
                    new ReferenceObjectTagStatement(sqlExport.getConnection()).updateReferenceObjectTagRow(ReferenceObjectTag);
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
        logger.info("\"reference_object_tag\" Table Uploaded");

    }
}