package com.sinclair.digital.app.migrate.Phases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;

import com.sinclair.digital.app.migrate.uploaders.ContentAttributesUploader;
import com.sinclair.digital.app.migrate.uploaders.ContentBrandsUploader;
import com.sinclair.digital.app.migrate.uploaders.SuggestedTeaserPlacementUploader;
import com.sinclair.digital.app.mysql.MySqlImport;
import com.sinclair.digital.app.mysql.MySqlExport;

import org.apache.log4j.Logger;

public class PhaseTest3 {
    private final static Logger logger = Logger.getLogger(PhaseTest3.class);

    private MySqlExport sqlExport = new MySqlExport();
    private MySqlImport sqlImport = new MySqlImport();

    public PhaseTest3(String reqExt) {
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

        if(reqExt.equals("home/cloud-user/storage/" + jsonFile)) {
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
        long startTime = System.currentTimeMillis();

        new ContentAttributesUploader(jsonArray, sqlExport, sqlImport);

        new SuggestedTeaserPlacementUploader(jsonArray, sqlExport, sqlImport);
        
        new ContentBrandsUploader(jsonArray, sqlExport, sqlImport);

        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime-startTime) + "ms");
        
    }
}