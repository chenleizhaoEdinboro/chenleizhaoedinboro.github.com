package FactoryAndData.BIZ;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import TestScript.BIZ.MetaDataTest_All_B2B;

public class MetaData_All_B2B {
	public static String filePath = "\\\\100.67.28.133\\MetaDataTest\\MetaDataTest.xlsx";
	//public static String filePath = "D:\\MetaDataTest\\MetaDataTest.xlsx";
	

	@DataProvider(name = "MetaData")
	public static Object[][] DataMetaData() {
		return readInputData();
	}

	@Factory(dataProvider = "MetaData")
	public Object[] createTest(String homePage_url, String userName, String passWord) {

		Object[] tests = new Object[1];

		tests[0] = new MetaDataTest_All_B2B(homePage_url, userName, passWord);

		return tests;
	}
	


	public static String[][] readInputData() {
		try {
			String fileName = "\\\\100.67.28.133\\MetaDataTest\\B2B_All_AutoTestInProgress.txt";
			//String fileName = "D:\\MetaDataTest\\AutoTestInProgress.txt";
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			fileName = "\\\\100.67.28.133\\MetaDataTest\\B2B_All_AutoTestDone.txt";
			file = new File(fileName);
			if (file.exists()) {
				file.delete();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		try {
			FileInputStream excelFileInPutStream = new FileInputStream(filePath);
		
			Workbook workbook = WorkbookFactory.create(excelFileInPutStream);
			Sheet sheet = workbook.getSheetAt(1);
			int rowIndex = 1;

			ArrayList<String[]> inputData = new ArrayList<String[]>();
			while (sheet.getRow(rowIndex) != null && readCellValue(sheet.getRow(rowIndex), 0) != "BlankCell") {
				String[] tempRow = {readCellValue(sheet.getRow(rowIndex), 0),readCellValue(sheet.getRow(rowIndex),1),readCellValue(sheet.getRow(rowIndex),2)
						 };
				inputData.add(tempRow);
				rowIndex++;
			}
			excelFileInPutStream.close();
			String[][] test = (String[][]) inputData.toArray(new String[0][0]);
			return test;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	private static String readCellValue(Row row, int columnIndex) {
		Cell cell = row.getCell(columnIndex);
		if (cell == null) {
			return "BlankCell";
		}
		DecimalFormat df = new DecimalFormat("#");
		int type = cell.getCellType();
		if (type == Cell.CELL_TYPE_STRING) {
			return cell.getStringCellValue();
		} else if (type == Cell.CELL_TYPE_NUMERIC) {
			return df.format(cell.getNumericCellValue());
		} else if (type == Cell.CELL_TYPE_BLANK) {
			return "BlankCell";
		} else {
			return "Error!";
		}
	}
}
