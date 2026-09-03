package com.sinclair.digital.app.migrate.uploaders;

import java.util.List;
import com.sinclair.digital.app.migrate.model.Content;
import com.sinclair.digital.app.migrate.model.mappers.jsonContentMapper;
import com.sinclair.digital.app.mysql.MySqlExport;

import org.json.JSONArray;

import com.sinclair.digital.app.migrate.model.statements.ContentStatement;

import java.util.ArrayList;
import org.apache.log4j.Logger;


public class ContentUploader {
    private final static Logger logger = Logger.getLogger(ContentUploader.class);

    public ContentUploader(JSONArray jsonArray, MySqlExport sqlExport) {
        System.out.print("\n");
        logger.info("Starting \"content\" table upload...");

        List<Content> contentList = new ArrayList<>();

        jsonContentMapper mapper = new jsonContentMapper();
        contentList = mapper.jsonMapper(jsonArray);

        for (int i = 0; i < contentList.size(); i++) {
            Content contentObj = contentList.get(i);
            new ContentStatement(sqlExport.getConnection()).updateContentRow(contentObj);
        }
        System.out.print("\n");
        logger.info("\"content\" Table Uploaded");
    }
}