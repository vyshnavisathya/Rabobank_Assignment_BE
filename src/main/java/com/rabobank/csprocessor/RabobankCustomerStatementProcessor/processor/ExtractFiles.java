package com.rabobank.csprocessor.RabobankCustomerStatementProcessor.processor;

import com.rabobank.csprocessor.RabobankCustomerStatementProcessor.model.Record;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@Component
public interface ExtractFiles {

	public List<Record> getDataFromCSV(File file) throws FileNotFoundException;
	
	public List<Record> getDataFromXML(File file) throws JAXBException;
	
}
