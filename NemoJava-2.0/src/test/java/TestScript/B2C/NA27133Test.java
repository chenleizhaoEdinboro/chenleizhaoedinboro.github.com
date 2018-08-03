package TestScript.B2C;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.text.DecimalFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


import org.openqa.selenium.Alert;
import org.openqa.selenium.chrome.ChromeDriver;
import TestData.PropsUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA27133Test extends SuperTestClass{

	public Map<String, String> map1;
	public Map<String, String> map2;
	public Map<String, String> map3;
	public String[][] cv;
	public String pathName;
	
	public NA27133Test(String Store){
		this.Store = Store;
		this.testName = "NA-27133";
	}
	
	@Test(priority = 0, alwaysRun = true, enabled = true,groups={"shopgroup","pricingpromot","p2","b2c"})
	public void NA27133(ITestContext ctx){
		try{
			this.prepareTest();
			
			pathName="D:\\NA27133\\";
			
			createFolderAndClearFiles();
			// set the default download path
			Dailylog.logInfoDB(1, "Setting Chrome download default path",Store, testName);
			changeChromeDefDownFolder(pathName);
			
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			HMCPage hmcPage= new HMCPage(driver);
			
			//--------------Step 1 :Check B2C Price Rule -----------
			driver.get(testData.HMC.getHomePageUrl());		
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_PriceSettings.click();
						
			HMCCommon.loginPricingCockpit(driver, hmcPage, testData);
			hmcPage.PricingCockpit_b2bPriceRules.click();

			map1=checkRules(false);
			driver.switchTo().defaultContent();
			
			HMCCommon.loginPricingCockpit(driver, hmcPage, testData);
			hmcPage.PricingCockpit_B2CPriceRules.click();
			map2=checkRules(false);
			driver.switchTo().defaultContent();
			
			HMCCommon.loginPricingCockpit(driver, hmcPage, testData);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.PricingCockpit_CVRules);
    		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.PricingCockpit_B2CCVRules);
			map3=checkRules(true);
			if(map3.containsKey("groupName")){
				System.out.println(map3.get("groupName"));
	        }
			

			Common.sleep(5000);
			driver.findElement(By.xpath("(//div/a[contains(@class,'exportGroup')])[2]")).click();
			Common.sleep(5000);
			String filePath = "D:\\NA27133\\"+map3.get("groupName")+".xlsx";
			

			FileInputStream excelFileInPutStream = new FileInputStream(filePath);
			Workbook workbook = WorkbookFactory.create(excelFileInPutStream);
			Sheet sheet = workbook.getSheetAt(0);

			ArrayList<String> al = new ArrayList<String>();
			int columnIndex = 0;

			while (sheet.getRow(1) != null && readCellValue(sheet.getRow(0), columnIndex) != "BlankCell") {
				String temp = readCellValue(sheet.getRow(0), columnIndex).trim();
				System.out.println(temp);
				al.add(temp);
				columnIndex++;
			}
			excelFileInPutStream.close();
						
			
		}catch(Throwable e){
			handleThrowable(e, ctx);
		}
	
	}
	
	public String readCellValue(Row row, int columnIndex) {
		Cell cell = row.getCell(columnIndex);
		if (cell == null) {
			return "BlankCell";
		}
		DecimalFormat df = new DecimalFormat("#");
		int type =  cell.getCellType();
		if (type ==  Cell.CELL_TYPE_STRING) {
			return cell.getStringCellValue();
		} else if (type == Cell.CELL_TYPE_NUMERIC) {
			return df.format(cell.getNumericCellValue());
		} else if (type == Cell.CELL_TYPE_BLANK) {
			return "BlankCell";
		} else {
			return "Error!";
		}
	}
	
	public Map<String, String> checkRules(boolean CV) throws InterruptedException {
		
		System.out.println("Check search rules***********************");
		Map<String, String> map = new HashMap<String, String>();
		String groupID;
		String groupType;
		String groupName;
		String groupDes;
		String groupValidFrom1;
		String groupValidFrom2;
		String groupValidTo1;
		String groupValidTo2;
		String groupCreate1;
		String groupCreate2;
		
				
		Thread.sleep(5000);
		List<WebElement> items=driver.findElements(By.xpath("//div/a/span[text()='Show']"));
		if(items.size()<1){
			Dailylog.logInfo("Have no price rules exist");
		}else{
			groupID=driver.findElement(By.xpath("(//tr/td[@class='type no-border'])[1]")).getText();
			groupType=driver.findElement(By.xpath("(//tr/td[@class='type no-border'])[2]")).getText();
			groupName=driver.findElement(By.xpath("(//tr/td[@class='name no-border'])[1]")).getText();
			groupDes=driver.findElement(By.xpath("(//tr/td[@class='description no-border'])[1]")).getText();
			groupValidFrom1=driver.findElement(By.xpath("(//td[@class='time no-border']/p)[1]")).getText();
			groupValidFrom2=driver.findElement(By.xpath("(//td[@class='time no-border']/p)[2]")).getText();
			groupValidTo1=driver.findElement(By.xpath("(//td[@class='time no-border']/p)[3]")).getText();
			groupValidTo2=driver.findElement(By.xpath("(//td[@class='time no-border']/p)[4]")).getText();
			groupCreate1=driver.findElement(By.xpath("(//td[@class='time no-border']/p)[5]")).getText();
			groupCreate2=driver.findElement(By.xpath("(//td[@class='time no-border']/p)[6]")).getText();
			
			map.put("groupID",groupID );
			map.put("groupType",groupType);
			map.put("groupName",groupName);
			map.put("groupDes",groupDes);
			map.put("groupValidFrom1",groupValidFrom1);
			map.put("groupValidFrom2",groupValidFrom2);
			map.put("groupValidTo1",groupValidTo1);
			map.put("groupValidTo2",groupValidTo2);
			map.put("groupCreate1",groupCreate1);
			map.put("groupCreate2",groupCreate2);
			Dailylog.logInfo("groupID:"+groupID);
			Dailylog.logInfo("groupType:"+groupType);
			Dailylog.logInfo("groupName:"+groupName);
			Dailylog.logInfo("groupValidFrom1:"+groupValidFrom1);
			Dailylog.logInfo("groupValidFrom2:"+groupValidFrom2);
			Dailylog.logInfo("groupValidTo1:"+groupValidTo1);
			Dailylog.logInfo("groupValidTo2:"+groupValidTo2);
			Dailylog.logInfo("groupCreate1:"+groupCreate1);
			Dailylog.logInfo("groupCreate2:"+groupCreate2);
			
			
			items.get(0).click();

			getValue("rules",CV,map);
			checkSearchItems(map,"rules");
				
			WebElement elem=driver.findElement(By.xpath("(//li/a[contains(text(),'Restrictions ') and (@role='tab')]/b[@data-autoupdate='restrictionsCount'])[1]"));
			if(elem.getText().contains("0")){
				Dailylog.logInfo("Rule has no restriction");
			}else{
				driver.findElement(By.xpath("(//li/a[contains(text(),'Restrictions ') and (@role='tab')]/b[@data-autoupdate='restrictionsCount'])[1]/..")).click();
				getValue("restrictions",CV,map);
				checkSearchItems(map,"restrictions");
			}
			
		}
		return map;
	
	}
	
	public Map<String, String> getValue(String str,boolean CV,Map<String, String> map){
		
		String xpath="//table[contains(@class,'rulesListings')]/tbody[@class='"+str+"']/tr[1]/td";
		List<WebElement> rule=driver.findElements(By.xpath(xpath));
		map.put(str+"ID", rule.get(0).getText());
		map.put(str+"Status", rule.get(1).getText());
		
		Dailylog.logInfo("ID:"+rule.get(0).getText());
		Dailylog.logInfo("Status:"+rule.get(1).getText());
		
		if(rule.get(2).getText().length()>0){
			map.put(str+"Country", rule.get(2).getText());
			Dailylog.logInfo("Country:"+rule.get(2).getText());
		}
		if(rule.get(3).getText().length()>0){
			map.put(str+"State", rule.get(3).getText());
			Dailylog.logInfo("State:"+rule.get(3).getText());
		}
		if(rule.get(4).getText().length()>0){
			map.put(str+"Category", rule.get(4).getText());
			Dailylog.logInfo("Category:"+rule.get(4).getText());
		}
		if(rule.get(5).getText().length()>0){
			map.put(str+"BaseProduct", rule.get(5).getText());
			Dailylog.logInfo("BaseProduct:"+rule.get(5).getText());
		}
		if(rule.get(6).getText().length()>0){
			map.put(str+"ParentBundle", rule.get(6).getText());
			Dailylog.logInfo("ParentBundle:"+rule.get(6).getText());
		}
		if(rule.get(7).getText().length()>0){
			map.put(str+"Material", rule.get(7).getText());
			Dailylog.logInfo("Material:"+rule.get(7).getText());
		}
		if(rule.get(8).getText().length()>0){
			map.put(str+"Unit", rule.get(8).getText());
			Dailylog.logInfo("Unit:"+rule.get(8).getText());
		}
		if(rule.get(9).getText().length()>0){
			map.put(str+"TIERS", rule.get(9).getText());
			Dailylog.logInfo("TIERS:"+rule.get(9).getText());
		}
		if(CV){
			if(rule.get(10).getText().length()>0){
				map.put(str+"NB", rule.get(10).getText());
				Dailylog.logInfo("NB:"+rule.get(10).getText());
			}
			if(rule.get(11).getText().length()>0){
				map.put(str+"CV", rule.get(11).getText());
				Dailylog.logInfo("CV:"+rule.get(11).getText());
			}
			if(rule.get(12).getText().length()>0){
				map.put(str+"OPERATION", rule.get(12).getText());
				Dailylog.logInfo("OPERATION:"+rule.get(12).getText());
			}
			if(rule.get(13).getText().length()>0){
				map.put(str+"VALUE", rule.get(13).getText());
				Dailylog.logInfo("VALUE:"+rule.get(13).getText());
			}
			
		}else{
			if(rule.get(10).getText().length()>0){
				map.put(str+"OPERATION", rule.get(10).getText());
				Dailylog.logInfo("OPERATION:"+rule.get(10).getText());
			}
			if(rule.get(11).getText().length()>0){
				map.put(str+"VALUE", rule.get(11).getText());
				Dailylog.logInfo("VALUE:"+rule.get(11).getText());
			}
		}
		
		return map;
		
		
		
	}
	public void checkSearchItems(Map<String, String> map,String str){
		boolean flag;
		String str1;
		if(str.contains("rule")){
			flag=false;
			str1="rule";
		}else{
			flag=true;
			str1="restriction";
		}
		checkSearch(flag,"//tr/th[@id='"+str1+"HeaderId']",map.get(str+"ID"),1);
		if(map.containsKey(str+"Country")){
			checkSearch(flag,"//tr/th[@id='"+str1+"HeaderCountry']",map.get(str+"Country"),3);
		}
		Common.sleep(3000);
		if(map.containsKey(str+"Category")){
			checkSearch(flag,"//tr/th[@id='"+str1+"HeaderCategory']",map.get(str+"Category"),5);
		}
		Common.sleep(3000);
		if(map.containsKey(str+"BaseProduct")){
			checkSearch(flag,"//tr/th[@id='"+str1+"HeaderBaseProduct']",map.get(str+"BaseProduct"),6);
		}
		Common.sleep(3000);
		if(map.containsKey(str+"Material")){
			checkSearch(flag,"//tr/th[@id='"+str1+"HeaderMaterial']",map.get(str+"Material"),8);
		}
		Common.sleep(3000);
		if(map.containsKey(str+"Unit")){
			checkSearch(flag,"//tr/th[@id='"+str1+"HeaderUnit']",map.get(str+"Unit"),9);
		}
		
	}
	
	public void checkSearch(boolean res,String xpath,String value,int col){
		driver.findElement(By.xpath(xpath)).click();
		driver.findElement(By.xpath(xpath)).clear();
		driver.findElement(By.xpath(xpath)).sendKeys(value);
		
		String xpath1;
		Common.sleep(5000);
		driver.findElement(By.xpath("(//div/a[contains(@class,'filterGroup')])[1]")).click();
		if(res){
			 xpath1="//tbody[@class='restrictions']/tr/td["+col+"]";
		}else{
			 xpath1="//tbody[@class='rules']/tr/td["+col+"]";
		}
		
		Common.sleep(5000);
		List<WebElement> e=driver.findElements(By.xpath(xpath1));
		for(WebElement e1:e){
			System.out.println(e1.getText());
			Assert.assertEquals(e1.getText(), value);
		}
		
		driver.findElement(By.xpath(xpath)).clear();
		Common.sleep(5000);
		driver.findElement(By.xpath("(//div/a[contains(@class,'filterGroup')])[1]")).click();
		
	}
	public void createFolderAndClearFiles() {
		File file = new File(pathName);

		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}

		if (file.exists()) {
			File[] files = file.listFiles();

			for (File file1 : files) {
				file1.delete();
			}
		}

	}
	
	public void changeChromeDefDownFolder(String downloadFilepath) throws IOException {
		driver.close();
		
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(cap);
		
		driver.manage().timeouts().pageLoadTimeout(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}
	
	
}
