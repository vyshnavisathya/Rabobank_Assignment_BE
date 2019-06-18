package com.rabobank.csprocessor.RabobankCustomerStatementProcessor.processor;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.rabobank.csprocessor.RabobankCustomerStatementProcessor.model.Record;
import com.rabobank.csprocessor.RabobankCustomerStatementProcessor.model.Records;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExtractFilesImpl implements ExtractFiles {

	/**
	 * @return List<Records>
	 */
	private static final Logger logger = LoggerFactory.getLogger(ExtractFilesImpl.class);
	public List<Record> getDataFromCSV(File file) throws FileNotFoundException {

		logger.info("Mapping csv file into java object");

		HeaderColumnNameTranslateMappingStrategy<Record> beanStrategy = new HeaderColumnNameTranslateMappingStrategy<Record>();
		beanStrategy.setType(Record.class);

		Map<String, String> columnMapping = new HashMap<String, String>();
		columnMapping.put("Reference", "reference");
		columnMapping.put("AccountNumber", "accountNumber");
		columnMapping.put("Description", "description");
		columnMapping.put("Start Balance", "startBalance");
		columnMapping.put("Mutation", "mutation");
		columnMapping.put("End Balance", "endBalance");

		beanStrategy.setColumnMapping(columnMapping);

		CsvToBean<Record> csvToBean = new CsvToBean<Record>();
		CSVReader reader = new CSVReader(new FileReader(file));
		List<Record> records = csvToBean.parse(beanStrategy, reader);
		logger.info("File parsed Successfully");
		return records;
	}

	/**This method processes the incoming XML file and maps it to the corresponding java object
	 *
	 * @return List<Records>
	 */
	public List<Record> getDataFromXML(File file) throws JAXBException {

		logger.info("Mapping xml file into java object");
        JAXBContext jaxbContext = JAXBContext.newInstance(Records.class);
   
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
        Records rootRecord= (Records) jaxbUnmarshaller.unmarshal(file);

        logger.info("File parsed Successfully");
		return rootRecord.getRecord();
	}

}
