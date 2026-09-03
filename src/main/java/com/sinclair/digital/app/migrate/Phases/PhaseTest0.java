package com.sinclair.digital.app.migrate.Phases;

import com.sinclair.digital.app.migrate.uploaders.SyndicationUploader;
import com.sinclair.digital.app.mysql.MySqlImport;
import com.sinclair.digital.app.mysql.MySqlExport;

import org.apache.log4j.Logger;

public class PhaseTest0 {
    private final static Logger logger = Logger.getLogger(PhaseTest0.class);

    private MySqlExport sqlExport = new MySqlExport();

    private MySqlImport sqlImport = new MySqlImport();

    public PhaseTest0() {
        sqlImport.connect();
        sqlExport.connect();
        this.uploadData();
    }

    private void uploadData() {
        long startTimeFull = System.currentTimeMillis();

        new SyndicationUploader(sqlExport, sqlImport);

        long endTimeFull = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTimeFull - startTimeFull) + "ms");

    }
}
