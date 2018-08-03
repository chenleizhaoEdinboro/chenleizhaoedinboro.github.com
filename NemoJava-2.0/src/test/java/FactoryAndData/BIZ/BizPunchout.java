package FactoryAndData.BIZ;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import TestScript.BIZ.BizPunchoutTest;

public class BizPunchout {
	public static String filePath = "\\\\10.62.6.95\\B2BPunchout\\PunchoutInput.xlsx";
	public static boolean testCheckout = false;

	@DataProvider(name = "BizPunchout")
	public static Object[][] DataBizPunchout() {
//		 return new Object[][] {
//		 { "999", "https://pre-c-hybris.lenovo.com/nemopunchouttool/ariba",
//		 "NetworkID", "IndianaU-T", "lenovob2b-T",
//		 "", "", "1213465598" }};
//			 { "999", "https://pre-c-hybris.lenovo.com/nemopunchouttool/oci",
//				 "-", "DOLBY-T", "lenovob2b-T",
//				 "view", "rowan", "1213414740" }};
//				 { "999", "https://pre-c-hybris.lenovo.com/nemopunchouttool/oxml",
//					 "-", "PFIZER_RB", "oagb2b",
//					 "", "", "1212457439" }};
		 
		 
		return readInputData();
	}

	@Factory(dataProvider = "BizPunchout")
	public Object[] createTest(String rowIndex, String url, String domain, String id, String password,
			String inboundKey, String inboundValue, String soldtoNum) {

		Object[] tests = new Object[1];

		tests[0] = new BizPunchoutTest(rowIndex, url, domain, id, password, inboundKey, inboundValue, soldtoNum);

		return tests;
	}

	public static String[][] readInputData() {
		try {
			String fileName = "\\\\10.62.6.95\\B2BPunchout\\AutoTestInProgress.txt";
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			fileName = "\\\\10.62.6.95\\B2BPunchout\\AutoTestDone.txt";
			file = new File(fileName);
			if (file.exists()) {
				file.delete();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		File inputFile = new File(filePath);
		if(inputFile.exists())
		{
			testCheckout = true;
		}else
		{
			testCheckout = false;
			filePath = filePath.replace("PunchoutInput","PunchoutInput_nocheckout");
		}
		
		try {
			FileInputStream excelFileInPutStream = new FileInputStream(filePath);
		
			Workbook workbook = WorkbookFactory.create(excelFileInPutStream);
			Sheet sheet = workbook.getSheetAt(0);
			int rowIndex = 2;

			ArrayList<String[]> inputData = new ArrayList<String[]>();
			while (sheet.getRow(rowIndex) != null && readCellValue(sheet.getRow(rowIndex), 1) != "BlankCell") {
				String[] tempRow = { Integer.toString(rowIndex), readCellValue(sheet.getRow(rowIndex), 1),
						readCellValue(sheet.getRow(rowIndex), 2), readCellValue(sheet.getRow(rowIndex), 3),
						readCellValue(sheet.getRow(rowIndex), 4), readCellValue(sheet.getRow(rowIndex), 5),
						readCellValue(sheet.getRow(rowIndex), 6), readCellValue(sheet.getRow(rowIndex), 7) };
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
