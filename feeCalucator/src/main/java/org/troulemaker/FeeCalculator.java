package org.troulemaker;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeSet;

import org.troulemaker.TransactionData;

public class FeeCalculator {
	
	/**
	 *  Method prepares the data structure containing all the transaction with there processing fee 
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public static TreeSet getTranscationType() {

		TreeSet<TransactionData> transactionData = new TreeSet<TransactionData>();
		try {

			FileInputStream excelFile = new FileInputStream(new File(TransactionConstant.SOURCE_DATA));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			TransactionData rowEntry = null;

			while (iterator.hasNext()) {

				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
				if (currentRow.getRowNum() > 0) {
					rowEntry = new TransactionData();
					while (cellIterator.hasNext()) {

						Cell currentCell = cellIterator.next();
						switch (currentCell.getColumnIndex()) {
						case 0:
							rowEntry.setExternalTransactionId(currentCell.getStringCellValue());
							break;
						case 1:
							rowEntry.setClientId(currentCell.getStringCellValue());
							break;
						case 2:
							rowEntry.setSecurityId(currentCell.getStringCellValue());
							break;
						case 3:
							rowEntry.setTransactionType(currentCell.getStringCellValue());
							break;
						case 4:
							rowEntry.setTransactionDate(currentCell.getDateCellValue());
							break;
						case 5:
							rowEntry.setMarketValue(currentCell.getNumericCellValue());
							break;
						case 6:
							rowEntry.setPriorityFlag(currentCell.getStringCellValue());

						}

					}
					if (rowEntry.getPriorityFlag().equals("Y")) {

						rowEntry.setProcessingFee(TransactionConstant.TRANSACTION_HIGH_PRIORITY_PROCESSING_FEE);
					} else if (rowEntry.getPriorityFlag().equals("N") && (rowEntry.getTransactionType()
							.equals(TransactionConstant.TRANSACTION_TYPE_BUY)
							|| rowEntry.getTransactionType().equals(TransactionConstant.TRANSACTION_TYPE_DEPOSIT))) {

						rowEntry.setProcessingFee(
								TransactionConstant.TRANSACTION_NORMAL_PRIORITY_BUY_DEPOSIT_PROCESSING_FEE);

					} else if (rowEntry.getPriorityFlag().equals("N") && (rowEntry.getTransactionType()
							.equals(TransactionConstant.TRANSACTION_TYPE_SELL)
							|| rowEntry.getTransactionType().equals(TransactionConstant.TRANSACTION_TYPE_WITHDRAW))) {
						rowEntry.setProcessingFee(
								TransactionConstant.TRANSACTION_NORMAL_PRIORITY_SELL_WITHDRAW_PROCESSING_FEE);

					} else {
						Iterator<TransactionData> transactionDataIterator = transactionData.iterator();

						while (transactionDataIterator.hasNext()) {
							TransactionData singleTransactionData = transactionDataIterator.next();
							if (singleTransactionData.getTransactionDate()
									.compareTo(rowEntry.getTransactionDate()) == 0) {
								if (singleTransactionData.getClientId().equals(rowEntry.getClientId())
										&& singleTransactionData.getSecurityId().equals(rowEntry.getSecurityId())) {
									if (((singleTransactionData.getTransactionType()
											.equals(TransactionConstant.TRANSACTION_TYPE_SELL)
											&& singleTransactionData.getTransactionType()
													.equals(TransactionConstant.TRANSACTION_TYPE_BUY)))
											|| ((singleTransactionData.getTransactionType()
													.equals(TransactionConstant.TRANSACTION_TYPE_BUY)
													&& singleTransactionData.getTransactionType()
															.equals(TransactionConstant.TRANSACTION_TYPE_SELL)))) {
										rowEntry.setProcessingFee(
												TransactionConstant.TRANSACTION_INTRADAY_PROCESSING_FEE);
									}

								}

							}

						}

					}

					transactionData.add(rowEntry);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return transactionData;
	}

	/**
	 * This method generates the summary report for all the transactions 
	 * 
	 * @param transactionData
	 */
	public static void generateReport(TreeSet<TransactionData> transactionData) {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Report");

		int rowNum = 1;
		Row headerRow = sheet.createRow(0);

		// Creating cells
		for (int i = 0; i < TransactionConstant.REPORT_COLUMNS.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(TransactionConstant.REPORT_COLUMNS[i]);
		}

		Iterator<TransactionData> transactionDataIterator = transactionData.iterator();
		int size = transactionData.size();
		while (transactionDataIterator.hasNext()) {
			Row row = sheet.createRow(rowNum++);
			TransactionData singRow = transactionDataIterator.next();
			row.createCell(0).setCellValue(singRow.getClientId());
			row.createCell(1).setCellValue(singRow.getTransactionType());
			row.createCell(2).setCellValue(singRow.getTransactionDate().toString());
			row.createCell(3).setCellValue(singRow.getPriorityFlag());
			row.createCell(4).setCellValue(singRow.getProcessingFee());

		}
		try {
			FileOutputStream fileOut = new FileOutputStream("reportSummary.xlsx");
			workbook.write(fileOut);
			fileOut.close();

			// Closing the workbook
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		TreeSet<TransactionData> transactionData = getTranscationType();
		generateReport(transactionData);
	}
}
