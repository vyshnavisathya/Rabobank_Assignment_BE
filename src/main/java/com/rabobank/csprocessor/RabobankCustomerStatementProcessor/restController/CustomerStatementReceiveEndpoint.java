package com.rabobank.csprocessor.RabobankCustomerStatementProcessor.restController;


import com.rabobank.csprocessor.RabobankCustomerStatementProcessor.model.RespHandler;
import com.rabobank.csprocessor.RabobankCustomerStatementProcessor.processor.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class CustomerStatementReceiveEndpoint {

    @Autowired
    private FileHandler fileHandler;

    @GetMapping(value = "/processCustomerStatement")
    public ArrayList<RespHandler> processCustomerStatement() throws Exception{
        return fileHandler.handleFileProcessing();
    }

}
