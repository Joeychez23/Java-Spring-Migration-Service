package com.sinclair.digital.app.migrate.uploaders;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.Dao.impl.FilterImpl;
import com.sinclair.digital.app.migrate.model.Filter;
import com.sinclair.digital.app.migrate.model.statements.FilterStatement;
import com.sinclair.digital.app.mysql.MySqlImport;
import com.sinclair.digital.app.mysql.MySqlExport;
import com.sinclair.digital.app.utils.UuidQueryBuilder;

public class FilterUploader {
    private final static Logger logger = Logger.getLogger(FilterUploader.class);

	private int splitIndex = 1000;
    
    private List<String> uuidFilterList = new ArrayList<>();
    public List<String> getUuidFilterList() {
        return this.uuidFilterList;
    }
    
    public FilterUploader(List<String> uuidFilterList,  MySqlExport sqlExport, MySqlImport sqlImport) {
		System.out.print("\n");
    	logger.info("Starting \"filter\" table upload...");
    	
		UuidQueryBuilder queryBulder = new UuidQueryBuilder();
    	List<String> filterUuidsList = queryBulder.getUuidQuery(uuidFilterList, splitIndex);

    	for (int i = 0; i < filterUuidsList.size(); i++) {
    		String uuidQueryStr = filterUuidsList.get(i);
    		List<Filter> tempList = new FilterImpl(sqlImport.getConnection()).getData(uuidQueryStr);
			try {
				PreparedStatement stmtCheckOff = sqlExport.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS=0");
				stmtCheckOff.execute();
				stmtCheckOff.close();
				
				for (int j = 0; j < tempList.size(); j++) {
					Filter filterObj = tempList.get(j);
					new FilterStatement(sqlExport.getConnection()).updateFilterRow(filterObj);
					if(filterObj.getUuid() != null) {
						this.uuidFilterList.add(filterObj.getUuid());
					}
				}
				
				PreparedStatement stmtCheckOn = sqlExport.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS=1");
				stmtCheckOn.execute();
				stmtCheckOn.close();
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
            System.out.print("#");
        }
        System.out.print("\n");
        logger.info("\"filter\" Table Uploaded");
    }

}
