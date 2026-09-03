package com.sinclair.digital.app.migrate.uploaders;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.Dao.impl.ComponentPresentationDetailsImpl;
import com.sinclair.digital.app.migrate.model.ComponentPresentationDetails;
import com.sinclair.digital.app.migrate.model.statements.ComponentPresentationDetailsStatement;
import com.sinclair.digital.app.mysql.MySqlImport;
import com.sinclair.digital.app.mysql.MySqlExport;
import com.sinclair.digital.app.utils.UuidQueryBuilder;

public class ComponentPresentationDetailsUploader {
	private final static Logger logger = Logger.getLogger(ComponentPresentationDetailsUploader.class);

	private int splitIndex = 1000;
	
	List<String> uuidCpuDetailsList = new ArrayList<>();
	public List<String> getUuidCpDList() {
		return this.uuidCpuDetailsList;
	}
	public ComponentPresentationDetailsUploader(List<String> uuidCpuDetailsList, MySqlExport sqlExport, MySqlImport sqlImport) {
		System.out.print("\n");
		logger.info("Starting \"component_presentation_details\" table upload...");
		
		UuidQueryBuilder queryBuilder = new UuidQueryBuilder();
		List<String> getCpdList = queryBuilder.getUuidQuery(uuidCpuDetailsList, splitIndex);

		for (int i = 0; i < getCpdList.size(); i++) {
			String uuidQueryStr = getCpdList.get(i);
			List<ComponentPresentationDetails> tempList = new ComponentPresentationDetailsImpl(sqlImport.getConnection()).getData(uuidQueryStr);
			try {
				PreparedStatement stmtCheckOff = sqlExport.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS=0");
				stmtCheckOff.execute();
				stmtCheckOff.close();
				
				for (int j = 0; j < tempList.size(); j++) {
					ComponentPresentationDetails cpdObject = tempList.get(j);
					new ComponentPresentationDetailsStatement(sqlExport.getConnection()).updateContentPresentationDetailsRow(cpdObject);
					if(cpdObject.getUuid() != null) {
						this.uuidCpuDetailsList.add(cpdObject.getUuid());
					}
					
				}
				
				PreparedStatement stmtCheckOn = sqlExport.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS=1");
				stmtCheckOn.execute();
				stmtCheckOn.close();
			} catch (SQLException e) {
				// TODO: handle exception
			}
            System.out.print("#");
        }
        System.out.print("\n");
        logger.info("\"component_presentation_details\" Table Uploaded");
	}

}
