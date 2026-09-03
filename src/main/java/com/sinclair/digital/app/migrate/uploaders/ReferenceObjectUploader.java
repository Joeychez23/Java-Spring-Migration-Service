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

import com.sinclair.digital.app.migrate.model.ReferenceObject;
import com.sinclair.digital.app.migrate.model.statements.ReferenceObjectStatement;
import com.sinclair.digital.app.migrate.Dao.impl.ReferenceObjectImpl;

public class ReferenceObjectUploader {
    private final static Logger logger = Logger.getLogger(ContentUploader.class);

    private int splitIndex = 25;

    private List<String> cpuUuids = new ArrayList<>();

    public List<String> getCpuUuids() {
        return this.cpuUuids;
    }

    private List<String> refObjUuids = new ArrayList<>();

    public List<String> getRefObjUuids() {
        return this.refObjUuids;
    }

    private List<String> versionSetUuids = new ArrayList<>();

    public List<String> getVersionSetUuids() {
        return this.versionSetUuids;
    }

    public ReferenceObjectUploader(JSONArray jsonArray, MySqlExport sqlExport, MySqlImport sqlImport) {
        System.out.print("\n");
        logger.info("Starting \"reference_object\" table upload...");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject currJson = jsonArray.getJSONObject(i);
            
            if (currJson.has("version_set_uuid")) {
                if (currJson.getString("version_set_uuid") != null && !this.versionSetUuids.contains(currJson.getString("version_set_uuid")) && (currJson.get("version_status").equals("PUBLISHED") || currJson.get("version_status").equals("SCHEDULED"))) {
                    this.versionSetUuids.add(currJson.getString("version_set_uuid"));
                }
            }
        }

        UuidQueryBuilder uuidQueryBuilder = new UuidQueryBuilder();
        List<String> uuidReferenceObjectList = uuidQueryBuilder.getUuidQuery(this.versionSetUuids, splitIndex);

        for (int i = 0; i < uuidReferenceObjectList.size(); i++) {
            String uuidQueryStr = uuidReferenceObjectList.get(i);
            List<ReferenceObject> tempList = new ReferenceObjectImpl(sqlImport.getConnection())
                    .getAllByVersionSet(uuidQueryStr);
            try {
                PreparedStatement stmtCheckOff = sqlExport.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS=0");
                stmtCheckOff.execute();
                stmtCheckOff.close();

                for (int j = 0; j < tempList.size(); j++) {
                    ReferenceObject referenceObject = tempList.get(j);

                    if (referenceObject.getUuidComponentPresentationUnion() != null) {
                        cpuUuids.add(referenceObject.getUuidComponentPresentationUnion());
                    }

                    if (referenceObject.getUuid() != null) {
                        refObjUuids.add(referenceObject.getUuid());
                    }

                    new ReferenceObjectStatement(sqlExport.getConnection()).updateReferenceObjectRow(referenceObject);
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
        logger.info("\"reference_object\" Table Uploaded");

    }
}