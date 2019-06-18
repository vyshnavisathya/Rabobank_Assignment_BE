package com.rabobank.csprocessor.RabobankCustomerStatementProcessor.util;

import com.rabobank.csprocessor.RabobankCustomerStatementProcessor.constant.AppConstants;
import com.rabobank.csprocessor.RabobankCustomerStatementProcessor.model.Record;
import com.rabobank.csprocessor.RabobankCustomerStatementProcessor.model.RespHandler;
import com.rabobank.csprocessor.RabobankCustomerStatementProcessor.processor.ExtractFiles;
import com.rabobank.csprocessor.RabobankCustomerStatementProcessor.processor.ExtractFilesImpl;
import com.rabobank.csprocessor.RabobankCustomerStatementProcessor.processor.ValidateFiles;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class Util {
	
	private static final Logger log = LoggerFactory.getLogger(Util.class);
	
    @Autowired
    ValidateFiles validateFiles;

    @Autowired
    ExtractFiles extractFiles;

    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    //CSV file header
    private static final String FILE_HEADER = "TransactionReference,Description";

    public RespHandler processCSV(File file) throws Exception{
        List<Record> errorRecords = new ArrayList<Record>();
        RespHandler respHandler =  new RespHandler();
        respHandler.setFileName(file.getName());
        File csvFile = file;
        List<Record> extractedRecords = extractFiles.getDataFromCSV(csvFile);
        errorRecords.addAll(validateFiles.getDuplicateRecords(extractedRecords));
        errorRecords.addAll(validateFiles.getNegativeBalanceRecords(extractedRecords));
        if (!errorRecords.isEmpty()) {
            writeErrorReport(errorRecords, file.getName());
            log.info("Report generated successfully");
            respHandler.setRespCode(AppConstants.SUCCESS);
            respHandler.setResponse(AppConstants.VALIDATION_ERROR);
            respHandler.setRecords(errorRecords);
        } else {
            log.info("No duplicate/invalid records found");
            respHandler.setRespCode(AppConstants.SUCCESS);
            respHandler.setResponse(AppConstants.VALIDATION_SUCCESS);
        }
        return respHandler;
    }

    public RespHandler processXML(File file) throws Exception{
        List<Record> errorRecords = new ArrayList<Record>();
        RespHandler respHandler =  new RespHandler();
        respHandler.setFileName(file.getName());
        ArrayList<RespHandler> respHandlers = new ArrayList<>();
        File xmlFile = file;
        List<Record> extractedRecords = extractFiles.getDataFromXML(xmlFile);
        errorRecords.addAll(validateFiles.getDuplicateRecords(extractedRecords));
        errorRecords.addAll(validateFiles.getNegativeBalanceRecords(extractedRecords));
        if (!errorRecords.isEmpty()) {
            writeErrorReport(errorRecords, file.getName());
            log.info("Report generated successfully");
            respHandler.setRespCode(AppConstants.SUCCESS);
            respHandler.setResponse(AppConstants.VALIDATION_ERROR);
            respHandler.setRecords(errorRecords);
        } else {
            log.info("No duplicate/invalid records found");
            respHandler.setRespCode(AppConstants.SUCCESS);
            respHandler.setResponse(AppConstants.VALIDATION_SUCCESS);
        }
        return respHandler;
    }

    private void writeErrorReport(List<Record> records, String fileName) {

        log.info("Generating a report out of error records.....");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("FailedRecords_" + getFileName(fileName) +".csv",true);

            //Write the CSV file header
            fileWriter.append(FILE_HEADER.toString());

            //Add a new line separator after the header
            fileWriter.append(NEW_LINE_SEPARATOR);

            //Write a new record object list to the CSV file
            for (Record record : records) {
                fileWriter.append(String.valueOf(record.getReference()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(record.getDescription());
                fileWriter.append(NEW_LINE_SEPARATOR);
            }


            log.info("CSV Report of Error records was created successfully !!!");

        } catch (Exception e) {
            log.error("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {

            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                log.error("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }

        }
    }

    private String getFileName(String fileFullName){
        int length = fileFullName.length();
        return fileFullName.substring(0, length-4)
                + "_"
                + fileFullName.substring(length-3, length);
    }

    public boolean checkFileSize(File file){
        return file.length()/1048576 < 2;
    }

    public boolean isCSV(File file){
        return FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("csv");
    }

    public boolean isXML(File file){
        return FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("xml");
    }

}
