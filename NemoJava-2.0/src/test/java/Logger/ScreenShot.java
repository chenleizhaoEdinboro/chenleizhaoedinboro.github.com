package Logger;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import Dao.impl.AutoReportImpl;


/*
 * xiezx 20170303 add Get ScreenShot
 */

public class ScreenShot {
    private static Dao.inter.AutoReportDao testCaseService;
    
	public static String storeImage(WebDriver driver, String pic_id, String url, String startTime,String DB) {
		boolean flag = false;
		System.out.println("pic_id:"+pic_id);;
		
		if(testCaseService==null){
			testCaseService=AutoReportImpl.creatInstance(DB);
		}
		
		try {
			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileInputStream fin = new FileInputStream(srcFile);
			ByteBuffer nbf = ByteBuffer.allocate((int) srcFile.length());
			byte[] array = new byte[1024];
			// int offset = 0;
			int length = 0;
			while ((length = fin.read(array)) > 0) {
				if (length != 1024)
					nbf.put(array, 0, length);
				else
					nbf.put(array);
			}
			fin.close();
			byte[] content = nbf.array();
			flag = testCaseService.insertImage(pic_id, content, url, startTime);
			if (flag == true) {
				return pic_id;
			} else {
				return "";
			}
		} catch (Exception e) {
			Dailylog.logError(e.getMessage(), e);
		}
		if (flag == true) {
			return pic_id;
		} else {
			return "";
		}
	}

	public static synchronized long next() {
		Date date = new Date();
		StringBuilder buf = new StringBuilder();
		int seq = 0;
		final int ROTATION = 9;
		if (seq > ROTATION)
			seq = 0;
		buf.delete(0, buf.length());
		date.setTime(System.currentTimeMillis());//
		String str = String.format("%1$tY%1$tm%1$td%1$tk%1$tM%1$tS%2$01d", date, seq++);
		return Long.parseLong(str)+1;//thread safety so add one

	}

}