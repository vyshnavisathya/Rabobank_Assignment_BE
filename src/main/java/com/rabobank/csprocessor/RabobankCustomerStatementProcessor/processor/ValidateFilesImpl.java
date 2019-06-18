package com.rabobank.csprocessor.RabobankCustomerStatementProcessor.processor;

import com.rabobank.csprocessor.RabobankCustomerStatementProcessor.model.Record;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ValidateFilesImpl implements ValidateFiles {

    /**
     * This method checks for the condition startbalance - mutation != endbalance. If condition fails , those records
     * will be returned as a list.
     *
     * @return List<Records>
     */
    public List<Record> getNegativeBalanceRecords(List<Record> records) {
        List<Record> endBalanceErrorRecords = new ArrayList<Record>();

        for (Record record : records) {
            if (Math.round((record.getStartBalance() + record.getMutation()) - Math.round(record.getEndBalance())) != 0) {
                endBalanceErrorRecords.add(record);
            }
        }
        return endBalanceErrorRecords;
    }

    /**
     * This method checks for uniqueReference and if any duplicate is found, then returns list of duplicate records
     *
     * @return List<Records>
     */
    public List<Record> getDuplicateRecords(List<Record> records) {
        Map<Integer, Record> uniqeRecords = new HashMap<Integer, Record>();
        List<Record> duplicateRecords = new ArrayList<Record>();

        for (Record record : records) {
            if (uniqeRecords.containsKey(record.getReference())) {
                duplicateRecords.add(record);
            } else {
                uniqeRecords.put(record.getReference(), record);
            }
        }
        List<Record> finalDuplicateRecords = new ArrayList<Record>();
        finalDuplicateRecords.addAll(duplicateRecords);
        for (Record record : duplicateRecords) {
            if (null != uniqeRecords.get(record.getReference())) {
                finalDuplicateRecords.add(uniqeRecords.get(record.getReference()));
                uniqeRecords.remove(record.getReference());
            }
        }
        return finalDuplicateRecords;
    }


}
