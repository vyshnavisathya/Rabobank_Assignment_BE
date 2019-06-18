package com.rabobank.csprocessor.RabobankCustomerStatementProcessor.processor;

import com.rabobank.csprocessor.RabobankCustomerStatementProcessor.model.Record;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ValidateFiles {
	
	public List<Record> getDuplicateRecords(List<Record> records);
	
	public List<Record> getNegativeBalanceRecords(List<Record> records);

}
