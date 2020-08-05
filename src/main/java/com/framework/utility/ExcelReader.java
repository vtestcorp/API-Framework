/**
 * 
 */
package com.framework.utility;

import java.io.FileInputStream;
import java.util.LinkedHashMap;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.framework.constants.Constants;

public class ExcelReader {
	/*
	 * Creates a objects for excel reader
	 * 
	 */

	public FileInputStream fis = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;
	LinkedHashMap<String, String> testFlowsMap = new LinkedHashMap<String, String>();
	LinkedHashMap<String, String> testSuiteMap = new LinkedHashMap<String, String>();

	/*
	 * Finds the workbook instance for XLSX file and path.
	 * 
	 */

	public ExcelReader() {
		try {
			fis = new FileInputStream(Constants.EXCEL_PATH);
			// Finds the workbook instance for XLSX file
			workbook = new XSSFWorkbook(fis);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/*
	 * Returns the data from a cell.
	 * 
	 */

	@SuppressWarnings("deprecation")
	public String getCellData(String sheetName, String colName, int rowNum) {
		try {
			// Blank rows return empty values
			if (rowNum <= 0)
				return "";
			// get sheet index.
			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			// If sheet not found return empty values
			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			// get column number
			for (int i = 0; i < row.getLastCellNum(); i++) {
				// System.out.println("------------Test-----------------"+row.getCell(i).getStringCellValue().trim());
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}
			if (col_Num == -1)
				return "";

			// sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(col_Num);
			// System.out.println("++" + cell);
			if (cell == null)
				return "";
			// System.out.println(cell.getCellType());
			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

				return cell.getStringCellValue().trim();
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());

		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in xls";
		}
	}

	/*
	 * Returns the data from a cell.
	 * 
	 */

	@SuppressWarnings("deprecation")
	public String getCellData(String sheetName, int colNum, int rowNum) {
		try {

			int index = workbook.getSheetIndex(sheetName);

			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum);
			if (row == null)
				return "";
			cell = row.getCell(colNum);
			if (cell == null)
				return "";

			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				return cell.getStringCellValue();
			} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				DataFormatter fmt = new DataFormatter();
				// String cellText = String.valueOf(cell.getNumericCellValue());
				String cellText = String.valueOf(fmt.formatCellValue(cell));
				// cell.setCellType(Cell.CELL_TYPE_STRING);
				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				System.out.println(cell.getStringCellValue().trim());
				return cell.getStringCellValue().trim();
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
		}
	}

	/*
	 * Returns last row number.
	 */
	public int getLastRowNum(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);

		if (index == -1)
			return 0;

		Sheet sheet = workbook.getSheetAt(index);

		return sheet.getLastRowNum();
	}

	/*
	 * Returns last column number.
	 */
	public int getLastColNum(String sheetName, int number) {
		int index = workbook.getSheetIndex(sheetName);

		if (index == -1)
			return 0;

		Sheet sheet = workbook.getSheetAt(index);

		return sheet.getRow(number).getLastCellNum();

	}
	
	public int getLastRowNumber(String sheetName){
		int rows = 0;
		while (!getCellData(sheetName, 0, rows).equals("")) {
			rows++;
		}
		return rows;
	}

}