package com.sinclair.digital.app.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import org.apache.log4j.Logger;

public class TimestampConvert {
    private final static Logger logger = Logger.getLogger(TimestampConvert.class);

    public java.sql.Timestamp stringToTimestamp(String str) {

        DateFormat df2 = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss");
        java.util.Date result2 = null;

        java.sql.Timestamp sqlDate = null;

        if (str != null) {
            try {
                result2 = df2.parse(str);
                sqlDate = new java.sql.Timestamp(result2.getTime());
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return sqlDate;
    }

	
	public String timestampToString(java.sql.Timestamp timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss");
		String text = null;

		if (timestamp != null) {
			text = sdf.format(timestamp);
		}

		return text;
	}

}