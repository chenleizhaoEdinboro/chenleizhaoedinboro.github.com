package TestScript.BIZ.PAGECHECK;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Pages.B2CPage;
import TestData.PropsUtils;

public class CommonFunctions {
	public  Connection conn = null;
	public  Statement stmt = null;
	public Date date1;
	
	public String Tablename = "pagecheck";
	public void getConnection(){
		final String url_sql = "jdbc:mysql://100.67.28.132:3306/bizreport";
		final String uer_sql = "root";
		final String pwd_sql = "admin";
		final String name_sql = "com.mysql.jdbc.Driver";
		
		
		try{
			Class.forName(name_sql);
			System.out.println("load the Driver successfully");
		}catch(Exception e){
			System.out.println("can not find the Driver");
			e.printStackTrace();
		}
		try{
			conn = DriverManager.getConnection(url_sql, uer_sql, pwd_sql);
			stmt = conn.createStatement();
			System.out.println("connect the database successfully");
		}catch(Exception e){
			System.out.println("Fail to connect the database");
			e.printStackTrace();
		}
		
	}
	
	
	
	public int AssertTrue(String testCaseNum,boolean b,String message,String summary,String testPoint,int ErrorsCount){
		int temp = 0;
		try{
			assert b: insertToReport(message,summary,testPoint);
		}catch(Error e){
			temp = 1;
		}
		return temp;
	}
	
	public String insertToReport(String message,String summary,String testPoint){
		
		insertSql(message,summary,testPoint);
		
		return "Error";
	}
	
	public void insertSql(String message, String summary,String testPoint){
		// message  include  testcasenumber , country , page, timestamp 
		String lineMessage = message + "#" + summary + "#" + testPoint;
		String[] strs = lineMessage.split("#");
		
		
		String TestCase_Column = strs[0];
		String Country_Column = strs[1];
		String Page_Column = strs[2];
		String Summary_Column = strs[6];
		String TestPoint_Column = strs[7];
		String Pass_Column = "";
		String Fail_Column = "";
		String Block_Column = "";
		
		if(lineMessage.contains("Due to")){
			Block_Column = strs[5];
		}else if(lineMessage.contains("PASS")){
			Pass_Column = strs[5];
		}else{
			Fail_Column = strs[5];
		}
		
		String timeStamp_Column = strs[3];
		String machineInfo_Column = strs[4];
		
		String sql_lan = "";
		if(!Block_Column.isEmpty()){
			sql_lan = "insert into " + Tablename + "(TestCase,Country,Page,Summary,TestPoint,Pass,Fail,Block,TimeStamp,MachineInfo) values("+"'"+TestCase_Column+"'"+","+"'"+Country_Column+"'"+","+"'"+Page_Column+"'"+","+"'"+Summary_Column+"'"+","+"'"+TestPoint_Column+"'"+","+"'"+""+"'"+","+"'"+""+"'"+","+"'"+Block_Column+"'"+","+"'"+timeStamp_Column+"'"+","+"'"+machineInfo_Column+"'"+");";
		}else if(!Fail_Column.isEmpty()){
			sql_lan = "insert into " + Tablename + "(TestCase,Country,Page,Summary,TestPoint,Pass,Fail,Block,TimeStamp,MachineInfo) values("+"'"+TestCase_Column+"'"+","+"'"+Country_Column+"'"+","+"'"+Page_Column+"'"+","+"'"+Summary_Column+"'"+","+"'"+TestPoint_Column+"'"+","+"'"+""+"'"+","+"'"+Fail_Column+"'"+","+"'"+""+"'"+","+"'"+timeStamp_Column+"'"+","+"'"+machineInfo_Column+"'"+");";
		}else if(!Pass_Column.isEmpty()){
			sql_lan = "insert into " + Tablename + "(TestCase,Country,Page,Summary,TestPoint,Pass,Fail,Block,TimeStamp,MachineInfo) values("+"'"+TestCase_Column+"'"+","+"'"+Country_Column+"'"+","+"'"+Page_Column+"'"+","+"'"+Summary_Column+"'"+","+"'"+TestPoint_Column+"'"+","+"'"+Pass_Column+"'"+","+"'"+""+"'"+","+"'"+""+"'"+","+"'"+timeStamp_Column+"'"+","+"'"+machineInfo_Column+"'"+");";
		}
		
		try{
			stmt.executeUpdate(sql_lan);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void AddTestCasePassContent(String message,String summary,String testPoint){
		insertSql(message,summary,testPoint);
	}
	
	public void detailFailReason(Exception e,String testCaseNum,String message,String summary,String testPoint,int ErrorsCount){
		e.printStackTrace();
		
		if(e.toString().contains("TimeoutException")){
			ErrorsCount++;
			ErrorsCount = ErrorsCount + AssertTrue(testCaseNum,false,message + "#" + "Due to Timeout exeeding time limit of 20s and page is not responding.",summary,testPoint,ErrorsCount);
			
			
		}else if(e.toString().contains("NoSuchElementException")){
			ErrorsCount++;
			ErrorsCount = ErrorsCount + AssertTrue(testCaseNum,false,message + "#" + "Element is not visible",summary,testPoint,ErrorsCount);
			
		}else if(e.toString().contains("ElementNotVisibleException")){
			ErrorsCount++;
			ErrorsCount = ErrorsCount + AssertTrue(testCaseNum,false,message + "#" + "some Element is not visible",summary,testPoint,ErrorsCount);
			
		}else{
			ErrorsCount++;
			ErrorsCount = ErrorsCount + AssertTrue(testCaseNum,false,message + "#" + "other Exceptions",summary,testPoint,ErrorsCount);
		}

	}

	public String getDate(){
		date1=new Date();
		
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String time=format.format(date1);
		
		return time;
	}
	
	public boolean isElementExist(WebDriver driver, By by) {
		boolean flag = false;
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		try {
			WebElement element = driver.findElement(by);
			flag = element != null;
		} catch (Exception e) {
		}
		driver.manage().timeouts().implicitlyWait(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		return flag;
	}
	
	
	public boolean isLogined(WebDriver driver,B2CPage b2cPage,String homePageUrl){
		driver.get(homePageUrl);
		
		b2cPage.myAccount_link.click();
		
		boolean b = isElementExist(driver,By.xpath("(//li[not(contains(@class,'hidden'))]/div/a[contains(@href,'my-account') and contains(@class,'linkLevel_2')])[1]"));

		return b;
	}

	
	public String getDateTimeString() {
		Date datetime = new Date();
		DateFormat milliFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return milliFormat.format(datetime);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
