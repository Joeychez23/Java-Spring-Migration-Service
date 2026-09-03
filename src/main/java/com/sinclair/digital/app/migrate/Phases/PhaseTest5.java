package com.sinclair.digital.app.migrate.Phases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;

import com.sinclair.digital.app.migrate.uploaders.ComponentPresentationDetailsUploader;
import com.sinclair.digital.app.migrate.uploaders.ComponentPresentationUnionUploader;
import com.sinclair.digital.app.migrate.uploaders.FilterUploader;
import com.sinclair.digital.app.migrate.uploaders.PropertyContentUnionUploader;
import com.sinclair.digital.app.migrate.uploaders.ReferenceObjectDataUploader;
import com.sinclair.digital.app.migrate.uploaders.ReferenceObjectTagUploader;
import com.sinclair.digital.app.migrate.uploaders.ReferenceObjectUploader;
import com.sinclair.digital.app.mysql.MySqlImport;
import com.sinclair.digital.app.mysql.MySqlExport;

import org.apache.log4j.Logger;

public class PhaseTest5 {
    private final static Logger logger = Logger.getLogger(PhaseTest5.class);

    private MySqlImport sqlImport = new MySqlImport();
    private MySqlExport sqlExport = new MySqlExport();

    public PhaseTest5(String reqExt) {
        sqlImport.connect();
        sqlExport.connect();

        String currDirectory = FileSystems.getDefault().getPath("").toAbsolutePath().toString();

        currDirectory += "/";
        String[] retStrArr = reqExt.split("/");
        String jsonFile = retStrArr[retStrArr.length - 1];
        for (int i = 0; i < retStrArr.length - 1; i++) {
            if (i + 1 != retStrArr.length) {
                currDirectory += retStrArr[i] += "/";
            }
        }

        if (reqExt.equals("home/cloud-user/storage/" + jsonFile)) {
            currDirectory = "/" + "home/cloud-user/storage/";
        }

        logger.info(currDirectory + jsonFile);

        File file = null;
        if (jsonFile != null) {
            file = new File(currDirectory, jsonFile);
        }

        JSONArray jsonArray = null;
        if (file.exists()) {
            try {
                InputStream input = new FileInputStream(file);
                String jsonTxt = IOUtils.toString(input, "UTF-8");
                jsonTxt = "[" + jsonTxt + "]";
                jsonArray = new JSONArray(jsonTxt);
            } catch (IOException e) {
                logger.info(e);
            }
        }

        if (jsonArray.length() > 0 && jsonArray != null) {
            this.uploadData(jsonArray);
        }
        // Error Logging
        if (!file.exists()) {
            logger.info("File Not Found");
        }
        if (jsonArray.length() == 0) {
            logger.info("No Date Found in File: " + jsonFile);
        }

    }

    private void uploadData(JSONArray jsonArray) {
        long startTimeFull = System.currentTimeMillis();

        ReferenceObjectUploader referenceObjectUploader = new ReferenceObjectUploader(jsonArray,  sqlExport, sqlImport);

        
        List<String> versionSetUuidList = referenceObjectUploader.getVersionSetUuids();
        new PropertyContentUnionUploader(versionSetUuidList, sqlExport, sqlImport);
        versionSetUuidList.clear();


        List<String> refObjUuidList = referenceObjectUploader.getRefObjUuids();
        new ReferenceObjectTagUploader(refObjUuidList, sqlExport, sqlImport);
        new ReferenceObjectDataUploader(refObjUuidList, sqlExport, sqlImport);
        refObjUuidList.clear();
        

        List<String> cpuUuidList = referenceObjectUploader.getCpuUuids();
        ComponentPresentationUnionUploader componentPresentationUnionUploader = new ComponentPresentationUnionUploader(cpuUuidList, sqlExport, sqlImport);
        cpuUuidList.clear();
        
        
        List<String> uuidFilterList = componentPresentationUnionUploader.getUuidFilterList();
        new FilterUploader(uuidFilterList, sqlExport, sqlImport);
        uuidFilterList.clear();
        

        List<String> uuidCpuDetailsList = componentPresentationUnionUploader.getUuidCpuDetailsList();
        new ComponentPresentationDetailsUploader(uuidCpuDetailsList, sqlExport, sqlImport);
        uuidCpuDetailsList.clear();
        
        
        long endTimeFull = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTimeFull - startTimeFull) + "ms");

    }
}


