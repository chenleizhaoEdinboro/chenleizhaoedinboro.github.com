package TestScript.BIZ;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import CommonFunction.Common;

import com.mysql.jdbc.PreparedStatement;

public class ExportMetaData_B2C_Mysql {
	public static Connection conn = null;
	public static Statement stmt = null;

	@Test
	@Parameters({ "ExcelSummary", "TableName","ReportFolder" })
	public void exportDataReport(String ExcelSummary, String TableName,String ReportFolder) {
		
		try {

			int row = 0;
			int rowSecondSheet = 0;
			int column = 0;
			//Clean up historical files
			String[] Path=Common.GetAllFilePath(ReportFolder);
			 for(int i=0;i<Path.length;i++){
		            if(Path[i].contains("xls")||Path[i].contains("zip")){Common.deleteFile(Path[i]);}
					
		         	}
			
			
			
			String currentDate = getStringDateShort(0);
			String yesterday = getStringDateShort(-1);
			String dayBedoreYesterday = getStringDateShort(-7);
			String filename = ExcelSummary.replace(".xls","_"+currentDate+".xls" );
			String zipname=filename.replace(".xls", ".zip");
		
			WritableWorkbook book = Workbook.createWorkbook(new File(
					filename));

			WritableSheet sheet = book.createSheet("First page", 0);

			Label label = new Label(0, 0, "Url");
			sheet.addCell(label);
			label = new Label(1, 0, "Page");
			sheet.addCell(label);
			label = new Label(2, 0, "MetaTag");
			sheet.addCell(label);
			label = new Label(3, 0, "ActualResult");
			sheet.addCell(label);
			label = new Label(4, 0, "ExpectedResult");
			sheet.addCell(label);
			label = new Label(5, 0, "Status");
			sheet.addCell(label);
			// create second sheet if exceeds limit
			WritableSheet sheet2 = book.createSheet("Second page", 1);
			Label label2 = new Label(0, 0, "Url");
			sheet2.addCell(label2);
			label2 = new Label(1, 0, "Page");
			sheet2.addCell(label2);
			label2 = new Label(2, 0, "MetaTag");
			sheet2.addCell(label2);
			label2 = new Label(3, 0, "ActualResult");
			sheet2.addCell(label2);
			label2 = new Label(4, 0, "ExpectedResult");
			sheet2.addCell(label2);
			label2 = new Label(5, 0, "Status");
			sheet2.addCell(label2);
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://100.67.28.132:3306/bizreport", "root",
					"admin");
		

//			String query = "SELECT * FROM " + TableName + " WHERE TimeStamp>'"
//					+ yesterday + " 00:00:00' AND TimeStamp<'" + currentDate
//					+ " 00:00:00' " +"AND MachineInfo<>'Finish' "+ "ORDER BY Account, SoldTo ASC";
			
			String query = "SELECT * FROM " + TableName;
			
			
			WritableCellFormat format_pass = new WritableCellFormat();
			format_pass.setBackground(Colour.GREEN);	
			
			WritableCellFormat format_fail = new WritableCellFormat();
			format_fail.setBackground(Colour.RED);	
			
			WritableCellFormat format_other = new WritableCellFormat();
			format_other.setBackground(Colour.YELLOW);	
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				row++;
				if (row <= 60000) {

					label = new Label(0, row, rs.getString(1));
					sheet.addCell(label);
					label = new Label(1, row, rs.getString(2));
					sheet.addCell(label);
					label = new Label(2, row, rs.getString(3));
					sheet.addCell(label);
					label = new Label(3, row, rs.getString(4));
					sheet.addCell(label);
					label = new Label(4, row, rs.getString(5));
					sheet.addCell(label);
					
					if(rs.getString(6).equals("Pass")){
						label = new Label(7, row, rs.getString(6),format_pass);
						sheet.addCell(label);
					}else if(rs.getString(6).equals("Fail")){
						label = new Label(7, row, rs.getString(6),format_fail);
						sheet.addCell(label);
					}else{
						label = new Label(7, row, rs.getString(6),format_other);
						sheet.addCell(label);
					}
					
					
					
				} else {

					rowSecondSheet++;

					label2 = new Label(0, rowSecondSheet, rs.getString(1));

					sheet2.addCell(label2);
					label2 = new Label(1, rowSecondSheet, rs.getString(2));
					sheet2.addCell(label2);
					label2 = new Label(2, rowSecondSheet, rs.getString(3));
					sheet2.addCell(label2);
					label2 = new Label(3, rowSecondSheet, rs.getString(4));
					sheet2.addCell(label2);
					label2 = new Label(4, rowSecondSheet, rs.getString(5));
					sheet2.addCell(label2);
					
					if(rs.getString(6).equals("Pass")){
						label = new Label(7, row, rs.getString(6),format_pass);
						sheet.addCell(label);
					}else if(rs.getString(6).equals("Fail")){
						label = new Label(7, row, rs.getString(6),format_fail);
						sheet.addCell(label);
					}else{
						label = new Label(7, row, rs.getString(6),format_other);
						sheet.addCell(label);
					}
					
					
				}
			}
			book.write();

			book.close();

			/*
			 * fw.flush(); fw.close();
			 */
//			String queryCleanup = "DELETE FROM " + TableName
//					+ " WHERE TimeStamp<'" + dayBedoreYesterday + " 00:00:00'";
			
			
			String queryCleanup = "DELETE FROM " + TableName;
			
			
			PreparedStatement ps = (PreparedStatement) conn
					.prepareStatement(queryCleanup);
			final int result = ps.executeUpdate();
			conn.close();
		//	ZipFile(filename,zipname);
			System.out.println("Report File is created successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String toDate(String source) {
		if (source == null) {
			source = "  ";
		}
		return source;

	}

	public static String getStringDateShort(int gap) {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);;
		calendar.add(calendar.DATE, gap);
		date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		return dateString;
	}
	
	 public static void ZipFile(String filepath ,String zippath) {
	    	try {
	            File file = new File(filepath);
	            File zipFile = new File(zippath);
	            InputStream input = new FileInputStream(file);
	            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
	            zipOut.putNextEntry(new ZipEntry(file.getName()));
	            int temp = 0;
	            while((temp = input.read()) != -1){
	                zipOut.write(temp);
	            }
	            input.close();
	            zipOut.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
