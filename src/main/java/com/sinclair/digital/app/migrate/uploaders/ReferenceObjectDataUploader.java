package com.sinclair.digital.app.migrate.uploaders;

import java.util.List;
import com.sinclair.digital.app.mysql.MySqlImport;
import com.sinclair.digital.app.mysql.MySqlExport;
import com.sinclair.digital.app.utils.UuidQueryBuilder;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.model.ReferenceObjectData;
import com.sinclair.digital.app.migrate.model.statements.ReferenceObjectDataStatement;
import com.sinclair.digital.app.migrate.Dao.impl.ReferenceObjectDataImpl;

public class ReferenceObjectDataUploader {
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

    public ReferenceObjectDataUploader(List<String> uuidRefObjList, MySqlExport sqlExport, MySqlImport sqlImport) {
        System.out.print("\n");
        logger.info("Starting \"reference_object_data\" table upload...");

        UuidQueryBuilder uuidQueryBuilder = new UuidQueryBuilder();
        List<String> uuidReferenceObjectDataList = uuidQueryBuilder.getUuidQuery(uuidRefObjList, splitIndex);

        for (int i = 0; i < uuidReferenceObjectDataList.size(); i++) {
            String uuidQueryStr = uuidReferenceObjectDataList.get(i);
            List<ReferenceObjectData> tempList = new ReferenceObjectDataImpl(sqlImport.getConnection()).getAllByUuid(uuidQueryStr);
            try {
                PreparedStatement stmtCheckOff = sqlExport.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS=0");
                stmtCheckOff.execute();
                stmtCheckOff.close();
    
                for (int j = 0; j < tempList.size(); j++) {
                    ReferenceObjectData ReferenceObjectData = tempList.get(j);
                    new ReferenceObjectDataStatement(sqlExport.getConnection()).updateReferenceObjectDataRow(ReferenceObjectData);
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
        logger.info("\"reference_object_data\" Table Uploaded");
        
    }
}