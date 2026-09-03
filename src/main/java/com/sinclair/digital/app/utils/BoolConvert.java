package com.sinclair.digital.app.utils;

import org.apache.log4j.Logger;


public class BoolConvert {
    private final static Logger logger = Logger.getLogger(BoolConvert.class);

    public Boolean IntToBool(int num)  {
        if(num == 0) {
            return false;
        } else if (num == 1) {
            return true;
        } else {
            return null;
        }
    }
}


