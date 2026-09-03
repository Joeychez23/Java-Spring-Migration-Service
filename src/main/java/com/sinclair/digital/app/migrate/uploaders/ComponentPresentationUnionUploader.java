package com.sinclair.digital.app.migrate.uploaders;

import java.util.List;
import com.sinclair.digital.app.mysql.MySqlImport;
import com.sinclair.digital.app.mysql.MySqlExport;
import com.sinclair.digital.app.utils.UuidQueryBuilder;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ArrayList;
import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.model.ComponentPresentationUnion;
import com.sinclair.digital.app.migrate.model.statements.ComponentPresentationUnionStatement;
import com.sinclair.digital.app.migrate.Dao.impl.ComponentPresentationUnionImpl;

public class ComponentPresentationUnionUploader {
    private final static Logger logger = Logger.getLogger(ComponentPresentationUnionUploader.class);

    private List<String> uuidFilterList = new ArrayList<>();
    public List<String> getUuidFilterList() {
        return this.uuidFilterList;
    }

    private List<String> uuidCpuDetailsList = new ArrayList<>();
    public List<String> getUuidCpuDetailsList() {
        return this.uuidCpuDetailsList;
    }

    public ComponentPresentationUnionUploader(List<String> cpuUuidList, MySqlExport sqlExport, MySqlImport sqlImport) {
        System.out.print("\n");
        logger.info("Starting \"component_presentation_union\" table upload...");

        int splitIndex = 1000;

        UuidQueryBuilder queryBuilder = new UuidQueryBuilder();
        List<String> uuidCpuQueryList = queryBuilder.getUuidQuery(cpuUuidList, splitIndex);
        
        for (int i = 0; i < uuidCpuQueryList.size(); i++) {
            String uuidQueryStr = uuidCpuQueryList.get(i);
            List<ComponentPresentationUnion> tempList = new ComponentPresentationUnionImpl(sqlImport.getConnection()).getData(uuidQueryStr);
            try {
                PreparedStatement stmtCheckOff = sqlExport.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS=0");
                stmtCheckOff.execute();
                stmtCheckOff.close();
    
                for (int j = 0; j < tempList.size(); j++) {
                    ComponentPresentationUnion cpuObj = tempList.get(j);
                    new ComponentPresentationUnionStatement(sqlExport.getConnection()).updateComponentPresentationUnionRow(cpuObj);

                    if(cpuObj.getUuidFilter() != null && !this.uuidFilterList.contains(cpuObj.getUuidFilter())) {
                        this.uuidFilterList.add(cpuObj.getUuidFilter());
                    }
                    if(cpuObj.getUuidComponentPresentationDetails() != null && !this.uuidFilterList.contains(cpuObj.getUuidComponentPresentationDetails())) {
                        this.uuidCpuDetailsList.add(cpuObj.getUuidComponentPresentationDetails());
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
        logger.info("\"component_presentation_union\" Table Uploaded");
        
    }
}