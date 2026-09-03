package com.sinclair.digital.app.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class UuidQueryBuilder {

    private final static Logger logger = Logger.getLogger(UuidQueryBuilder.class);

    private int currIndex = 0;

    public List<String> getUuidQuery(List<String> uuidList, int splitIndex) {
        List<String> retArr = new ArrayList<>();

        while(this.currIndex < uuidList.size()) {
            String queryStr = this.getSubQuery(uuidList, splitIndex);
            retArr.add(queryStr);
        }
        this.currIndex = 0;

        return retArr;
    }


    private String getSubQuery(List<String> uuidList, int splitIndex) {
        String uuidStrStart = "(";
        String uuidStrVal = "";

        Boolean setBool = uuidList.size() - currIndex < splitIndex;

        if(setBool) {
            for(int i = this.currIndex; i < uuidList.size(); i++) {
                uuidStrVal += String.format("'%s'", uuidList.get(i));
                if(i + 1 != uuidList.size()) {
                    uuidStrVal += ",";
                }
                this.currIndex += 1;
            }
        }

        if(!setBool) {
            int endIndex;
            if(this.currIndex == 0) {
                endIndex = (this.currIndex + splitIndex) + 1;
            } else {
                endIndex = (this.currIndex + splitIndex);
            }

            for(int i = this.currIndex; i < endIndex; i++) {
                uuidStrVal += String.format("'%s'", uuidList.get(i));
                if(i != endIndex - 1) {
                    uuidStrVal += ",";
                }

                if(i == endIndex - 1) {
                    this.currIndex += 1;
                    break;
                }

                this.currIndex += 1;
            }
        }

        String uuidStrEnd = ")";
        String uuidQueryStr = uuidStrStart + uuidStrVal + uuidStrEnd;

        return uuidQueryStr;
    }
}


