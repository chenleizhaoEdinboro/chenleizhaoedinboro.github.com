package TestScript.B2C;

import org.testng.annotations.Test;
import org.testng.ITestContext;
import org.openqa.selenium.By;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import junit.framework.Assert;
import CommonFunction.Common;
import CommonFunction.CTOCommon;
import CommonFunction.B2CCommon;
import CommonFunction.DesignHandler.*;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.CTOPage;
import TestData.TestData;
import TestScript.SuperTestClass;


public class SHOPE206Test extends SuperTestClass{
	
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public CTOPage ctoPage;
	public int count=0;
	public String tierName1="AutoTest1";
	public String tierName2="AutoTest2";	
	private String filePath = "D:\\SHOPE206Add.xls";
	private String filePathDel = "D:\\SHOPE206Del.xls";	
	public String unit="Small Business Portal Japan";
	public String groupName="ヤトロ電子株式会社";
	public String[] groupid1= {"jp00001","jp00002"};
	public String[] groupid2= {"jp00001","jp00002","78419390","jp00003","jp00004"};
	public String[] groupid3= {"jp00001","jp00003","78419390","1234567890","jp00005","jp00006","jp00007","jp00008","jp00009","jp00010","jp00011","jp00012","jp00013"};
	public String[] groupid4= {"jp00001","jp00002","jp00003","78419390","1234567890","jp00005","jp00006","jp00007","jp00008","jp00009","jp00010","jp00011","jp00012","jp00013"};

	public SHOPE206Test(String store){
		this.Store= store;	
		this.testName= "SHOPE-206";
	}
	
	@Test(priority = 0, alwaysRun = true, enabled = true,groups={"shopgroup","pricingpromot","p2","b2c"})
	public void SHOPE206(ITestContext ctx){
		try{			
			this.prepareTest();
			hmcPage= new HMCPage(driver);
			
			
			//Step 1: Go to hmc - Pricing Setting -Pricing Cockpit - Pricing Tier --Create new tier
			driver.get(testData.HMC.getHomePageUrl());
			if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)){
				HMCCommon.Login(hmcPage, testData);
			}
			Thread.sleep(3000);
			if(!Common.checkElementDisplays(driver, hmcPage.Home_PricingCockpit, 5)){
				hmcPage.Home_PriceSettings.click();
			}			
			HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			Thread.sleep(5000);
			hmcPage.PricingCockpit_PriceTierRules.click();
			Thread.sleep(5000);
			
			//delete exist Japan tiers
			String xpath = "//td[contains(text(),'"+unit+"')]/following-sibling::td/a[contains(.,'Delete')]";
			while(driver.findElements(By.xpath(xpath)).size()>0&&count<5) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElements(By.xpath(xpath)).get(0));
				Common.waitElementVisible(driver, hmcPage.B2CPriceRules_deleteInput);
				hmcPage.B2CPriceRules_deleteInput.clear();
				hmcPage.B2CPriceRules_deleteInput.sendKeys("DELETE");
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_deleteConfirm);
				Common.sleep(10000);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_FilterBtn);
				Common.sleep(3000);
				count++;
			}
			
			Dailylog.logInfoDB(1, "Deleted all exsite japan rules",Store, testName);
			
			

			//Step 2-3-4: [Manually]Navigate to the  Customer Groups --Add New Customer Group - Manually -Input group ID
			createPriceTier(driver,hmcPage,tierName1,1);
			Dailylog.logInfoDB(2,"Tier with all valiad group Manually created", Store,testName);
			createPriceTier(driver,hmcPage,tierName2,2);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_FilterBtn);
			editPriceTier(driver,hmcPage,tierName2,2);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_FilterBtn);
			Dailylog.logInfoDB(3,"Tier contains invalid group Manually created", Store,testName);
			
			//Step 5:Upload ] Navigate to the  Customer Groups --Add New Customer Group - Upload -Choose file --confirm
			File file = new File(filePath);
			FileOutputStream excelFileInputStream = new FileOutputStream(file);
			writeExcelAdd(excelFileInputStream, "xls",groupid3);
			excelFileInputStream.close();			
			uploadFileAdd(file,tierName1);
			file.delete();
			Dailylog.logInfoDB(8,"Checked upload add customer group function", Store,testName);
			
			//Step 6:Delete rules
			File file1 = new File(filePathDel);
			FileOutputStream excelFileInputStream1 = new FileOutputStream(file1);
			writeExcelAdd(excelFileInputStream1, "xls",groupid4);
			excelFileInputStream1.close();			
			uploadFileDel(file1,tierName1);
			file1.delete();
			Dailylog.logInfoDB(9,"Checked upload delete customer group function", Store,testName);
					
			//Roll back:deleted all Rule
			while(driver.findElements(By.xpath(xpath)).size()>0&&count<5) {
				
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElements(By.xpath(xpath)).get(0));
				Common.waitElementVisible(driver, hmcPage.B2CPriceRules_deleteInput);
				hmcPage.B2CPriceRules_deleteInput.clear();
				hmcPage.B2CPriceRules_deleteInput.sendKeys("DELETE");
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_deleteConfirm);
				Common.sleep(10000);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_FilterBtn);
				Common.sleep(3000);
				count++;
			}

			Dailylog.logInfoDB(10, "Deleted all exsite japan rules",Store, testName);
						
		}catch(Throwable e){
			handleThrowable(e,ctx);
		}
	}

	public void addGroup1(WebDriver driver,HMCPage hmcPage){
		for(int k=0;k<groupid1.length;k++) {
			String xpath = "//span[text()='" +groupid1[k]+ "']";
			for(int i=0;i<10;i++) {
				Common.javascriptClick(driver, hmcPage.B2BPriceRules_PriceTierGroup);
				if(Common.checkElementDisplays(driver, By.xpath("//div/ul[@class='select2-results' and not(@id)]/li[1]"), 10)) {
					break;
				}

			}

			Common.sleep(5000);
			hmcPage.B2BPriceRules_PriceTierInput.clear();
			hmcPage.B2BPriceRules_PriceTierInput.sendKeys(groupid1[k]);
			Common.waitElementClickable(driver, driver.findElement(By.xpath(xpath)), 20);
			driver.findElement(By.xpath(xpath)).click();		
			System.out.println("Input group:"+groupid1[k]);	
		}
		Common.javascriptClick(driver, hmcPage.PriceTier_SaveAddCustomerGroup);		
		
	}
	public void addGroup2(WebDriver driver,HMCPage hmcPage){
		//add group
		for(int k=0;k<groupid2.length;k++) {
			String xpath = "//span[text()='" +groupid2[k]+ "']";
			for(int i=0;i<10;i++) {
				Common.javascriptClick(driver, hmcPage.B2BPriceRules_PriceTierGroup);
				if(Common.checkElementDisplays(driver, By.xpath("//div/ul[@class='select2-results' and not(@id)]/li[1]"), 10)) {
					break;
				}
			}

			Common.sleep(5000);
			hmcPage.B2BPriceRules_PriceTierInput.clear();
			hmcPage.B2BPriceRules_PriceTierInput.sendKeys(groupid2[k]);
			if(k>2) {
				
				Common.waitElementClickable(driver, driver.findElement(By.xpath(xpath)), 20);
				driver.findElement(By.xpath(xpath)).click();		
				System.out.println("Input group:"+groupid2[k]);	
				
			}else if(k==1||k==0) {
				
				xpath="//span[text()='" +groupid2[k]+ "']/../..";
				Assert.assertTrue(driver.findElement(By.xpath(xpath)).getAttribute("class").contains("disabled"));
				
			}else{
				xpath="//ul/li[text()='No matches found']";
				Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath(xpath), 10));
			}

		}
		Common.javascriptClick(driver, hmcPage.PriceTier_SaveAddCustomerGroup);		
	}
	
	public void editPriceTier(WebDriver driver,HMCPage hmcPage,String name,int j) {
		String xpath="//td[text()='"+name+"']/following-sibling::td/a[contains(.,' Edit')]";
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.xpath(xpath)));	
		Common.sleep(5000);
		
		Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath("//tr[contains(@class,'jsgrid')]/td[contains(text(),'"+groupid2[3]+"')]")), 10));
		Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath("//tr[contains(@class,'jsgrid')]/td[contains(text(),'"+groupid2[4]+"')]")), 10));
		//add group
		hmcPage.PriceTier_AddCustomerGroup.click();
		Common.sleep(5000);
		
		for(int i=0;i<10;i++) {
			Common.javascriptClick(driver, hmcPage.B2BPriceRules_PriceTierGroup);
			if(Common.checkElementDisplays(driver, By.xpath("//div/ul[@class='select2-results' and not(@id)]/li[1]"), 10)) {
				break;
			}
		}

		Common.sleep(5000);
		hmcPage.B2BPriceRules_PriceTierInput.clear();
		hmcPage.B2BPriceRules_PriceTierInput.sendKeys(groupid2[3]);
		Common.sleep(3000);
		xpath="//span[text()='" +groupid2[3]+ "']/../..";
		Assert.assertTrue(driver.findElement(By.xpath(xpath)).getAttribute("class").contains("disabled"));
		
		hmcPage.B2BPriceRules_PriceTierInput.clear();
		Common.sleep(5000);
		Common.javascriptClick(driver, hmcPage.B2BPriceRules_PriceTierGroup);
		Common.sleep(3000);
		Common.javascriptClick(driver, hmcPage.PriceTier_CloseAddCustomerGroup);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2BPriceRules_PriceTierSave);
		System.out.println("Clicked Save Button");
		
		driver.switchTo().defaultContent();
		driver.navigate().refresh();
		HMCCommon.loginPricingCockpit(driver, hmcPage, testData);
		Common.sleep(5000);
		hmcPage.PricingCockpit_PriceTierRules.click();
		Common.sleep(5000);
		
	}	
	public void createPriceTier(WebDriver driver,HMCPage hmcPage,String name,int i) throws InterruptedException{
		
		System.out.println("Create Price Tier rules***********************");
		Thread.sleep(3000);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_CreateNewTier);		
		Thread.sleep(3000);
		//name
		hmcPage.B2BPriceRules_PriceTierName.clear();
		hmcPage.B2BPriceRules_PriceTierName.sendKeys(name);
		System.out.println("Input rulename");
		
		//Description
		hmcPage.B2BPriceRules_PriceTierDescription.clear();
		hmcPage.B2BPriceRules_PriceTierDescription.sendKeys("Test SHOPE-206");
		System.out.println("Input Description");
		
		//Min
		hmcPage.B2BPriceRules_PriceTierMin.clear();
		hmcPage.B2BPriceRules_PriceTierMin.sendKeys("0");
		System.out.println("Input Min");
		
		//Max
		hmcPage.B2BPriceRules_PriceTierMax.clear();
		hmcPage.B2BPriceRules_PriceTierMax.sendKeys("10000");
		System.out.println("Input Max");
		
		//Day
		hmcPage.B2BPriceRules_PriceTierDay.clear();
		hmcPage.B2BPriceRules_PriceTierDay.sendKeys("30");
		System.out.println("Input Day");
		
		//Store Unit
		String xpath = "//span[text()='" +unit+ "' and @class='select2-match']/../../*[not(text())]";
		HMCCommon.fillRuleInfo(driver, hmcPage, unit, hmcPage.B2BPriceRules_PriceTierStoreUnit, xpath);
		System.out.println("Input Store Unit");
		Common.sleep(3000);
		
		Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//tr[@class='jsgrid-nodata-row']/td[text()='Not found']"), 10));
		
		//add group
		hmcPage.PriceTier_AddCustomerGroup.click();
		Common.sleep(5000);
		if(i==1) {
			addGroup1(driver,hmcPage);
		}else if(i==2) {
			addGroup2(driver,hmcPage);
		}
		System.out.println("Added group");
		
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2BPriceRules_PriceTierSave);
		System.out.println("Clicked Save Button");
		
	}

	private void uploadFileDel(File file,String name) throws InterruptedException{
		String xpath="//td[text()='"+name+"']/following-sibling::td/a[contains(.,' Edit')]";
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.xpath(xpath)));	
		Common.sleep(5000);
		
		if(file.exists()) {
			//upload group
			hmcPage.PriceTier_DeleteCustomerGroup.click();
			Common.sleep(5000);
			hmcPage.PriceTier_ChooseFileDele.sendKeys(filePathDel);
			Common.sleep(8000);
			hmcPage.PriceTier_DelValidate.click();
			Common.sleep(5000);
			
			//check error message--------need 
			Assert.assertTrue(Common.checkElementExists(driver,driver.findElement(By.xpath("//div[@id='batchDeleteMessagPanel']/div[text()='10 customer groups will be deleted.']")), 10));
			Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath("//div[@id='batchDeleteMessagPanel']/div[contains(.,'Below row number is not valid:')]")), 10));
			Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath("//div[@id='batchDeleteMessagPanel']/div/a[contains(.,'Not Customer Group: ')]/span[text()='5 ']")), 10));
			Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath("//div[@id='batchDeleteMessagPanel']/div/a[contains(.,'Not Belong To Store Unit: ')]/span[text()='4 ']")), 10));
			Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath("//div[@id='batchDeleteMessagPanel']/div/a[contains(.,'Not Belong To Tier: ')]/span[text()='2 3 ']")), 10));

			
			Common.javascriptClick(driver, hmcPage.PriceTier_DelConfirm);			
			xpath="//tr[@class='jsgrid-nodata-row']/td[contains(text(),'Not found')]";
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath(xpath), 10));
			
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2BPriceRules_PriceTierSave);
			System.out.println("Clicked Save Button");
			
			Common.sleep(3000);
		}else {
			Assert.fail("upload file not eixsts!");
		}
		
	}
	private void uploadFileAdd(File file,String name) throws InterruptedException{
		
		String xpath="//td[text()='"+name+"']/following-sibling::td/a[contains(.,' Edit')]";
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.xpath(xpath)));	
		Common.sleep(5000);
	
		Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath("//tr[contains(@class,'jsgrid')]/td[contains(text(),'"+groupid1[0]+"')]")), 10));
		Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath("//tr[contains(@class,'jsgrid')]/td[contains(text(),'"+groupid1[1]+"')]")), 10));
		//add group
		
		if(file.exists()) {
			//upload group
			hmcPage.PriceTier_AddCustomerGroup.click();
			Common.sleep(5000);
			hmcPage.PriceTier_UploadCustomerGroup.click();
			hmcPage.PriceTier_ChooseFile.sendKeys(filePath);
			Common.sleep(8000);
			//check error message
			Assert.assertTrue(Common.checkElementExists(driver,  driver.findElement(By.xpath("//p[@id='uploadError' and contains(.,'Below Customer Groups already exist in tiers:jp00003,jp00001,78419390;')]")), 10));
			Assert.assertTrue(Common.checkElementExists(driver,  driver.findElement(By.xpath("//p[@id='uploadError' and contains(.,'Below Customer Groups do not exist:1234567890;')]")), 10));
			Assert.assertTrue(Common.checkElementExists(driver,  driver.findElement(By.xpath("//p[@id='uploadResult' and text()='9 rows is successfully processed!']")), 10));
			Dailylog.logInfoDB(4,"Checked error message upload add", Store,testName);
			
			hmcPage.PriceTier_SaveAddCustomerGroup.click();
			Common.sleep(3000);
		}else {
			Assert.fail("upload file not eixsts!");
		}
		
		//check filter,search,sort
		Select entrys = new Select(hmcPage.PriceTier_ShowEntrys);
		entrys.selectByVisibleText("25");
		Assert.assertEquals(11, driver.findElements(By.xpath("//tr[contains(@class,'jsgrid') and contains(@class,'row')]/td[contains(.,'jp000')]")).size());
		entrys.selectByVisibleText("10");
		Assert.assertEquals(10, driver.findElements(By.xpath("//tr[contains(@class,'jsgrid') and contains(@class,'row')]/td[contains(.,'jp000')]")).size());
		Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//div[contains(.,'Pages:') and @class='jsgrid-pager']"), 10));
		Assert.assertEquals("1", driver.findElement(By.xpath("//div[contains(.,'Pages:') and @class='jsgrid-pager']/span[contains(@class,'current-page')]")).getText());
		Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//div[contains(.,'Pages:') and @class='jsgrid-pager']/span/a[text()='2']"), 10));
		Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//div[contains(.,'Pages:') and @class='jsgrid-pager']/span/a[text()='Next']"), 10));
		Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//div[contains(.,'Pages:') and @class='jsgrid-pager']/span/a[text()='Last']"), 10));	
		Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//div[contains(.,'Pages:') and @class='jsgrid-pager'and contains(.,'1 of 2')]"), 10));
		Dailylog.logInfoDB(5,"Checked show entrys and pages display", Store,testName);
		
		
		//sort group
		hmcPage.PriceTier_CompanyGroup.click();
		Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath("//tr[@class='jsgrid-header-row']/th[contains(@class,'jsgrid-header-sort-asc') and contains(.,'CUSTOMER GROUP')]")), 10));
		List<WebElement> groups=driver.findElements(By.xpath("//tr[contains(@class,'jsgrid') and contains(@class,'row')]/td[contains(.,'jp000')]"));
		Assert.assertEquals("jp00001",groups.get(0).getText());
		Assert.assertEquals("jp00002",groups.get(1).getText());
		
		hmcPage.PriceTier_CompanyGroup.click();
		Assert.assertTrue(Common.checkElementExists(driver,  driver.findElement(By.xpath("//tr[@class='jsgrid-header-row']/th[contains(@class,'jsgrid-header-sort-desc') and contains(.,'CUSTOMER GROUP')]")), 10));
		List<WebElement> grous=driver.findElements(By.xpath("//tr[contains(@class,'jsgrid') and contains(@class,'row')]/td[contains(.,'jp000')]"));
		
		Assert.assertEquals(groupid3[groupid3.length-1],grous.get(0).getText());
		Assert.assertEquals(groupid3[groupid3.length-2],grous.get(1).getText());
		
		//sort group name
		hmcPage.PriceTier_CompanyName.click();
		Assert.assertTrue(Common.checkElementExists(driver,driver.findElement(By.xpath("//tr[@class='jsgrid-header-row']/th[contains(@class,'jsgrid-header-sort-asc') and contains(.,'COMPANY NAME')]")), 10));
		
		List<WebElement> names=driver.findElements(By.xpath("//div[@class='jsgrid-grid-body']/table[@class='jsgrid-table']//tr[contains(@class,'jsgrid') and contains(@class,'row')]/td[2]"));
		Assert.assertEquals("Richard Bliah Associates",names.get(0).getText());
		Assert.assertEquals("RPAテクノロジーズ株式会社",names.get(1).getText());
		
		hmcPage.PriceTier_CompanyName.click();
		Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath("//tr[@class='jsgrid-header-row']/th[contains(@class,'jsgrid-header-sort-desc') and contains(.,'COMPANY NAME')]")), 10));
		List<WebElement> nams=driver.findElements(By.xpath("//div[@class='jsgrid-grid-body']/table[@class='jsgrid-table']//tr[contains(@class,'jsgrid') and contains(@class,'row')]/td[2]"));
		Assert.assertEquals("株式会社ユーザベース",nams.get(0).getText());
		Assert.assertEquals("株式会社マネーフォワード",nams.get(1).getText());
		
		Dailylog.logInfoDB(6,"Checked sort of group and name", Store,testName);

		
		//search
		hmcPage.PriceTier_CompanyClearFiter.click();
		hmcPage.PriceTier_GroupSearchBox.clear();
		hmcPage.PriceTier_GroupSearchBox.sendKeys("jp00001");
		hmcPage.PriceTier_CompanySearch.click();
		
		//by group id
		List<WebElement> lists=driver.findElements(By.xpath("//div[@class='jsgrid-grid-body']/table[@class='jsgrid-table']//tr[contains(@class,'jsgrid') and contains(@class,'row')]/td"));
		Assert.assertEquals(3,lists.size());
		Assert.assertEquals("jp00001",lists.get(0).getText());
		hmcPage.PriceTier_CompanyClearFiter.click();
		
		//by group name
		hmcPage.PriceTier_NameSearchBox.clear();
		hmcPage.PriceTier_NameSearchBox.sendKeys(groupName);
		hmcPage.PriceTier_CompanySearch.click();
		
		List<WebElement> lis=driver.findElements(By.xpath("//div[@class='jsgrid-grid-body']/table[@class='jsgrid-table']//tr[contains(@class,'jsgrid') and contains(@class,'row')]/td"));
		Assert.assertEquals(3,lis.size());
		Assert.assertEquals(groupName,lis.get(1).getText());
		Dailylog.logInfoDB(7,"Checked search function", Store,testName);
		
		//delete 
		String groupid=lis.get(0).getText();
		lis.get(2).click();
		Assert.assertTrue(driver.switchTo().alert().getText().contains(groupid));
		driver.switchTo().alert().accept();
		hmcPage.PriceTier_CompanyClearFiter.click();
		
		//by group name
		hmcPage.PriceTier_NameSearchBox.clear();
		hmcPage.PriceTier_NameSearchBox.sendKeys(groupName);
		hmcPage.PriceTier_CompanySearch.click();
		xpath="//tr[@class='jsgrid-nodata-row']/td[contains(text(),'Not found')]";
		Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath(xpath)), 10));
		hmcPage.PriceTier_CompanyClearFiter.click();
		Dailylog.logInfoDB(8,"Checked delete function", Store,testName);
		
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2BPriceRules_PriceTierSave);
		System.out.println("Clicked Save Button");
		
		driver.switchTo().defaultContent();
		driver.navigate().refresh();
		HMCCommon.loginPricingCockpit(driver, hmcPage, testData);
		Common.sleep(5000);
		hmcPage.PricingCockpit_PriceTierRules.click();
		Common.sleep(5000);
		
	}

	private void writeExcelAdd(FileOutputStream os, String excelExtName,String groupid[]) throws IOException {
		Workbook wb = null;
		try {
			if ("xls".equals(excelExtName)) {
				wb = new HSSFWorkbook();
			} else if ("xlsx".equals(excelExtName)) {
				wb = new XSSFWorkbook();
			} else {
				throw new Exception("file extention not correct");
			}

			Sheet sheet = wb.createSheet();
			
			for(int i=0;i<groupid.length;i++) {
				Row row = sheet.createRow(i);
				Cell colCell = row.createCell(0);
				colCell.setCellValue(groupid[i]);
			}
		
			wb.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (wb != null) {
				wb.close();
			}
		}
	}
	
    
}