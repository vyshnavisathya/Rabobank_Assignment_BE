package com.rabobank.csprocessor.RabobankCustomerStatementProcessor.model;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This class handles the final response from after the incoming file is processed successfully
 */
@Component
public class RespHandler {
	private String response;
	private int respCode;
	private List<Record> Records;
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getResponse() {
		return response;
	}

	@Override
	public String toString() {
		return "RespHandler{" +
				"response='" + response + '\'' +
				", respCode=" + respCode +
				", Records=" + Records +
				", fileName='" + fileName + '\'' +
				'}';
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public int getRespCode() {
		return respCode;
	}

	public void setRespCode(int respCode) {
		this.respCode = respCode;
	}

	public List<Record> getRecords() {
		return Records;
	}

	public void setRecords(List<Record> records) {
		Records = records;
	}
}
