package com.rabobank.csprocessor.RabobankCustomerStatementProcessor.processor;

import com.rabobank.csprocessor.RabobankCustomerStatementProcessor.constant.AppConstants;
import com.rabobank.csprocessor.RabobankCustomerStatementProcessor.model.RespHandler;
import com.rabobank.csprocessor.RabobankCustomerStatementProcessor.util.Util;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

@Slf4j
@Component
public class FileHandler {
	
	private static final Logger log = LoggerFactory.getLogger(FileHandler.class);

    @Autowired
    private Util util;

    public ArrayList<RespHandler> handleFileProcessing() throws Exception{

        ArrayList<RespHandler> respHandlers = new ArrayList<>();
        File folder = new File("./input");
        File[] listOfFiles = folder.listFiles();

        for(int i=0; i < listOfFiles.length; i++){
            //Segregate processing based on file type
            if (listOfFiles[i].isFile()
                    && util.checkFileSize(listOfFiles[i])
                    &&  (util.isCSV(listOfFiles[i]) || util.isXML(listOfFiles[i]))){
                    if(util.isCSV(listOfFiles[i])){
                        RespHandler respHandler = util.processCSV(listOfFiles[i]);
                        respHandlers.add(respHandler);
                    }else if(util.isXML(listOfFiles[i])){
                        RespHandler respHandler = util.processXML(listOfFiles[i]);
                        respHandlers.add(respHandler);
                    }
            }else {
                if(!util.checkFileSize(listOfFiles[i])){
                    log.error("File Size more than expected");
                }
                if(!(util.isCSV(listOfFiles[i]) || util.isXML(listOfFiles[i]))){
                    log.error(listOfFiles[i].getName() +  ":  File format not supported");
                }
                RespHandler respHandler = new RespHandler();
                respHandler.setFileName(listOfFiles[i].getName());
                respHandler.setRespCode(AppConstants.ERROR);
                respHandler.setResponse(AppConstants.VALIDATION_ERROR);
                respHandlers.add(respHandler);
            }
        }

        return respHandlers;
    }
}
