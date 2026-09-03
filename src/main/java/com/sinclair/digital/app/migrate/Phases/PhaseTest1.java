package com.sinclair.digital.app.migrate.Phases;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinclair.digital.app.migrate.Dao.impl.ContentImpl;
import com.sinclair.digital.app.migrate.model.Content;
import com.sinclair.digital.app.migrate.model.mappers.ContentToMap;
import com.sinclair.digital.app.mysql.MySqlImport;

public class PhaseTest1 {
private final static Logger logger = Logger.getLogger(PhaseTest1.class);
	
	private Connection con = null;
	private MySqlImport mysql = null;
	
	public PhaseTest1 () throws IOException {
		mysql = new MySqlImport();
		mysql.connect();
		con = mysql.getConnection();
	}
	
	public void executeToFile(String startDate, String endDate, String fileName) throws IOException {
		long startTimeFull = System.currentTimeMillis();

		con = mysql.getConnection();
	
		ContentImpl contentImpl = new ContentImpl(con);
		ContentToMap contentToMap = new ContentToMap();
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter out = null;
		
		try {
			fw = new FileWriter(fileName, true);
			bw = new BufferedWriter(fw);
			out = new PrintWriter(bw);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// The following call executes the query that return an Array of Content rows
		List<Content> contentList = contentImpl.getAllContentByTypeWithinDateRange("CNTNTPCKGMDL", startDate, endDate);
		
		int size = contentList.size();
		logger.info("Number of Rows returned: " + size);

		long endTimeFull = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTimeFull - startTimeFull) + "ms");
		
		Iterator<Content> iter = (Iterator<Content>) contentList.iterator();
		int rowNum = 0;
		
		while (iter.hasNext()) {
			if(rowNum >= size) {
				break;
			}
			
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			
			Content c = contentList.get(rowNum);

			contentToMap.getMapfromContent(map, c);
			
			ObjectMapper mapper = new ObjectMapper();
			
			String str = null;
			try {
				str = mapper.writeValueAsString(map);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}					
			out.print(str +","+ "\r\n");
			
			rowNum++;
			
		}
		
		bw.flush();
		bw.close();
	}

	
}
