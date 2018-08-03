package TestScript.B2C;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.WriterExcelUtil;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA28065Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	String partNumber = "20H8X001US";
	String Salesorg = "US10";
	String partNumber1 = "80UD016QUS";
	String partNumber2 = "80Y70063US";
	String today = Common.getDateTimeString();
	String newFilePath = "d:\\na28065.xls";
	String[] newData = {"US","Outlet","20H8X001US","Notebooks","English US [en-us]","deaPad MIIX 700-12ISK","","","384.99","USD","12/24/2017","119.99","USD","12/24/2017","12/24/2018","","","","","","","","","","","","","","","","","","","","","New","OUTLET_US","20H8X001US","499.99","20H8X001US",""};
	String[] table = {"20H8X001US","","99","OUTLET_US","2016/7/11"};
	String[] table1 = {"80UD016QUS","","99","OUTLET_US","2016/7/11"};
	String[] table2 = {"80Y70063US","","99","OUTLET_US","2016/7/11"};
	String[] newData1 = {" ","Outlet","80UD016QUS","Notebooks","English US [en-us]","deaPad MIIX 700-12ISK","","","259.99","USD","","","","","","","","","","","","","","","","","","","","","","","","","","","","80UD016QUS","219.99","80UD016QUS",""};
	String[] newData2 = {" ","Public","80Y70063US","Notebooks","English US [en-us]","deaPad MIIX 700-12ISK","","","749.99","USD","1/12/2018","667.7","USD","1/12/2018","1/12/2019","","","","","","","","","","","","","","","","","","","","","","","80Y70063US","649.99","80Y70063US",""};
	String newFilePath1 = "d:\\na280651.xls";
	String newFilePath2 = "d:\\na280652.xls";
	String typeode = "priceb2c";
	String channel = "Outlet";
	String channelECC = "ECC";
	String channelB2C = "B2C";
	String newdata2costprice = "1006.78";
	String newdata2listprice = "1329.99";
	DecimalFormat df  = new DecimalFormat("######0.00");   
	String actual = df.format(Double.valueOf(newData1[8])*0.75);
	public NA28065Test(String Store) {
		this.Store = Store;
		this.testName = "COMM-339";
	}

	@Test(alwaysRun = true, priority = 0, enabled = true, groups = { "shopgroup", "pricingpromot", "p2", "b2c" })
	public void NA28065(ITestContext ctx) {
		try {

			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			
			// 1.HMC -> Price Settings -> Pricing Cockpit -> B2C Price Simulator
			driver.get(testData.HMC.getHomePageUrl());
			if (Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)) {
				HMCCommon.Login(hmcPage, testData);
			}
			hmcPage.Home_PriceSettings.click();
			HMCCommon.loginPricingCockpit(driver, hmcPage, testData);
			Common.sleep(3000);
			HMCCommon.B2CPriceSimulateDebug(driver, hmcPage, getData(this.Store, EnumTestData.country1),
					getData(this.Store, EnumTestData.store), "Nemo Master Multi country Product Catalog - Online",
					partNumber);
			Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(text(),'"+partNumber+"')]")).isDisplayed());
			Dailylog.logInfoDB(1, "Web Price before rule in HMC:", Store, testName);

			// 2.HMC -> Price Settings -> Pricing Cockpit -> Price Feed
			hmcPage.controller.click();
			Common.scrollToElement(driver, hmcPage.priceFeed);
			hmcPage.priceFeed.click();
			Common.sleep(3000);
			hmcPage.salesOrg.sendKeys(Salesorg);
			hmcPage.material.sendKeys(partNumber);
			hmcPage.filterBtn.click();
			Common.sleep(2000);
			Assert.assertTrue(driver.findElement(By.xpath("//table//tbody/tr[contains(text(),'"+channelECC+"')]")).isDisplayed());
			Dailylog.logInfoDB(2, "HMC -> Price Settings -> Pricing Cockpit -> Price Feed", Store, testName);

			// 3.HMC -> Nemo -> Product&Outlet Upload
			driver.switchTo().defaultContent();
			hmcPage.Home_Nemo.click();
			hmcPage.ProductOutletUpload.click();
			List<Map<String, Object>> data = write(newData);
			List<Map<String, Object>> datatable = write2(table);
			Common.sleep(3000);
			//write data to excel
			WriterExcelUtil.writeExcel(data, newFilePath,datatable);
			// upload file
			uploadFile(newFilePath);
			Dailylog.logInfoDB(3, "HMC -> Nemo -> Product&Outlet Upload", Store, testName);

			// 4.HMC -> Price Settings -> Pricing Cockpit -> Price Feed
			Common.scrollToElement(driver, hmcPage.Home_PriceSettings);
			HMCCommon.loginPricingCockpit(driver, hmcPage, testData);
			Common.scrollToElement(driver, hmcPage.priceFeed);
			hmcPage.priceFeed.click();
			Common.sleep(3000);
			hmcPage.salesOrg.sendKeys(Salesorg);
			hmcPage.material.sendKeys(partNumber);
//			HMCCommon.fillB2CPriceRuleDetails(driver,hmcPage.channelOutlet,hmcPage.channelOutletInput1,channel,10);
			hmcPage.filterBtn.click();
			Common.sleep(5000);
			Assert.assertTrue(driver.findElement(By.xpath("//table//tbody/tr[contains(text(),'"+channel+"')]")).isDisplayed());
			Dailylog.logInfoDB(4, "HMC -> Price Settings -> Pricing Cockpit -> Price Feed", Store, testName);

			// 5.HMC -> Price Settings -> Pricing Cockpit -> B2C Price Simulator
			Common.scrollToElement(driver, hmcPage.controller);
			hmcPage.controller.click();
			HMCCommon.B2CPriceSimulateDebug(driver, hmcPage, getData(this.Store, EnumTestData.country1),
					getData(this.Store, EnumTestData.store), "Nemo Master Multi country1 Product Catalog - Online",
					partNumber);
			Assert.assertTrue(driver.findElement(By.xpath("//table[@class='custom-table']//tr/td[contains(text(),'"+newData[11]+"')]")).isDisplayed());
			Assert.assertTrue(driver.findElement(By.xpath("//table[@class='custom-table']//tr/td[contains(text(),'"+newData[8]+"')]")).isDisplayed());
			Assert.assertTrue(driver.findElement(By.xpath("//table[@class='custom-table']//tr/td[contains(text(),'"+newData[39]+"')]")).isDisplayed());
			Dailylog.logInfoDB(5, "HMC -> Price Settings -> Pricing Cockpit -> B2C Price Simulator", Store, testName);

			// 6.HMC -> Price Settings -> Pricing Cockpit -> B2C Price Simulator
			HMCCommon.B2CPriceSimulateDebug(driver, hmcPage, getData(this.Store, EnumTestData.country1),
					getData(this.Store, EnumTestData.unit), "Nemo Master Multi country1 Product Catalog - Online",
					partNumber);
			Dailylog.logInfoDB(6, "HMC -> Price Settings -> Pricing Cockpit -> B2C Price Simulator", Store, testName);

			// 7.Repeat step 1 to 3 for product 20GF0001US in outlet channel, and now HMC ->
			// Nemo -> Product&Outlet Upload
			hmcPage.Home_PriceSettings.click();
			HMCCommon.loginPricingCockpit(driver, hmcPage, testData);
			hmcPage.PricingCockpit_B2CPriceRules.click();
			Common.sleep(3000);
			HMCCommon.B2CPriceSimulateDebug(driver, hmcPage, getData(this.Store, EnumTestData.country1),
					getData(this.Store, EnumTestData.store), "Nemo Master Multi country1 Product Catalog - Online",
					partNumber);

			Common.scrollToElement(driver, hmcPage.priceFeed);
			hmcPage.priceFeed.click();
			Common.sleep(3000);
			hmcPage.salesOrg.sendKeys(Salesorg);
			hmcPage.material.sendKeys(partNumber);
			HMCCommon.fillB2CPriceRuleDetails(driver,hmcPage.channelOutlet,hmcPage.channelOutletInput1,channel,10);
			hmcPage.filterBtn.click();
			Common.sleep(5000);
			driver.switchTo().defaultContent();

			hmcPage.Home_Nemo.click();
			hmcPage.ProductOutletUpload.click();
			List<Map<String, Object>> data1 = write(newData1);
			List<Map<String, Object>> datatable1 = write2(table);
			Common.sleep(3000);
			//write data to excel
			WriterExcelUtil.writeExcel(data1, newFilePath1,datatable1);
			// upload file
			uploadFile(newFilePath1);
			Dailylog.logInfoDB(7, "HMC -> Nemo -> Product&Outlet Upload", Store, testName);

			// 8.HMC -> Price Settings -> Pricing Cockpit -> Price Feed
			Common.scrollToElement(driver, hmcPage.Home_PriceSettings);
			hmcPage.Home_PriceSettings.click();
			hmcPage.PricingCockpit_B2CPriceRules.click();
			hmcPage.priceFeed.click();
			Common.sleep(3000);
			hmcPage.salesOrg.sendKeys(Salesorg);
			hmcPage.material.sendKeys(partNumber1);
//			HMCCommon.fillB2CPriceRuleDetails(driver,hmcPage.channelOutlet,hmcPage.channelOutletInput1,channel,10);
			hmcPage.filterBtn.click();
			Common.sleep(5000);
			Assert.assertTrue(driver.findElement(By.xpath("//table//tbody/tr[contains(text(),'"+channel+"')]")).isDisplayed());
			String text = driver.findElement(By.xpath("//table[@class='custom-table']//tr/td[contains(text(),'"+newData1[8]+"')]")).getText();
			Assert.assertEquals(text, actual);
			Dailylog.logInfoDB(8, "HMC -> Price Settings -> Pricing Cockpit -> Price Feed", Store, testName);

			// 9.HMC -> Price Settings -> Pricing Cockpit -> B2C Price Simulator
			HMCCommon.B2CPriceSimulateDebug(driver, hmcPage, getData(this.Store, EnumTestData.country1),
					getData(this.Store, EnumTestData.store), "Nemo Master Multi country1 Product Catalog - Online",
					partNumber);
			Assert.assertTrue(driver.findElement(By.xpath("//table[@class='custom-table']//tr/td[contains(text(),'"+actual+"')]")).isDisplayed());
			Assert.assertTrue(driver.findElement(By.xpath("//table[@class='custom-table']//tr/td[contains(text(),'"+newData1[8]+"')]")).isDisplayed());
			Assert.assertTrue(driver.findElement(By.xpath("//table[@class='custom-table']//tr/td[contains(text(),'"+newData1[39]+"')]")).isDisplayed());
			Dailylog.logInfoDB(9, "HMC -> Price Settings -> Pricing Cockpit -> B2C Price Simulator", Store, testName);

			// 10.HMC -> Price Settings -> Pricing Cockpit -> B2C Price Simulator
			HMCCommon.B2CPriceSimulateDebug(driver, hmcPage, getData(this.Store, EnumTestData.country1),
					getData(this.Store, EnumTestData.store), "Nemo Master Multi country1 Product Catalog - Online",
					partNumber);
			Assert.assertTrue(driver.findElement(By.xpath("//table[@class='custom-table']//tr/td[contains(text(),'"+newData1[11]+"')]")).isDisplayed());
			Assert.assertTrue(driver.findElement(By.xpath("//table[@class='custom-table']//tr/td[contains(text(),'"+newData1[8]+"')]")).isDisplayed());
			Assert.assertTrue(driver.findElement(By.xpath("//table[@class='custom-table']//tr/td[contains(text(),'"+newData1[39]+"')]")).isDisplayed());			
			Dailylog.logInfoDB(10, "HMC -> Price Settings -> Pricing Cockpit -> B2C Price Simulator", Store, testName);

			// 11.HMC -> Nemo -> Product&Outlet Upload
			driver.switchTo().defaultContent();
			hmcPage.Home_Nemo.click();
			hmcPage.ProductOutletUpload.click();
			List<Map<String, Object>> data2 = write(newData2);
			List<Map<String, Object>> datatable2 = write2(table);
			Common.sleep(3000);
			//write data to excel
			WriterExcelUtil.writeExcel(data2, newFilePath2,datatable2);
			// upload file
			uploadFile(newFilePath2);
			Dailylog.logInfoDB(11, "HMC -> Nemo -> Product&Outlet Upload", Store, testName);
			
			//12.HMC -> Price Settings -> Pricing Cockpit -> Price Feed
			Common.scrollToElement(driver, hmcPage.Home_PriceSettings);
			hmcPage.Home_PriceSettings.click();
			HMCCommon.loginPricingCockpit(driver, hmcPage, testData);
			hmcPage.priceFeed.click();
			Common.sleep(3000);
			hmcPage.salesOrg.sendKeys(Salesorg);
			hmcPage.material.sendKeys(partNumber2);
//			HMCCommon.fillB2CPriceRuleDetails(driver,hmcPage.channelOutlet,hmcPage.channelOutletInput1,channelB2C,10);
			hmcPage.filterBtn.click();
			Common.sleep(5000);
			Assert.assertTrue(driver.findElement(By.xpath("//table//tbody/tr[contains(text(),'"+channelB2C+"')]")).isDisplayed());
			Assert.assertTrue(driver.findElement(By.xpath("//table[@class='custom-table']//tr/td[contains(text(),'"+newData2[11]+"')]")).isDisplayed());
			Assert.assertTrue(driver.findElement(By.xpath("//table[@class='custom-table']//tr/td[contains(text(),'"+newData2[8]+"')]")).isDisplayed());
			Assert.assertTrue(driver.findElement(By.xpath("//table[@class='custom-table']//tr/td[contains(text(),'"+newData2[39]+"')]")).isDisplayed());
			Dailylog.logInfoDB(12, "HMC -> Price Settings -> Pricing Cockpit -> Price Feed", Store, testName);

			//13.HMC -> Price Settings -> Pricing Cockpit -> B2C Price Simulator
			HMCCommon.B2CPriceSimulateDebug(driver, hmcPage, getData(this.Store, EnumTestData.country1),
					getData(this.Store, EnumTestData.unit), "Nemo Master Multi country1 Product Catalog - Online",
					partNumber);
			Assert.assertTrue(driver.findElement(By.xpath("//table[@class='custom-table']//tr/td[contains(text(),'"+newdata2costprice+"')]")).isDisplayed());
			Assert.assertTrue(driver.findElement(By.xpath("//table[@class='custom-table']//tr/td[contains(text(),'"+newdata2listprice+"')]")).isDisplayed());
			Dailylog.logInfoDB(13, "HMC -> Price Settings -> Pricing Cockpit -> B2C Price Simulator", Store, testName);
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void deletePrice() {
		hmcPage.system.click();
		hmcPage.types.click();
		hmcPage.typeCode.clear();
		hmcPage.typeCode.sendKeys(typeode);
		hmcPage.ProductBuidler_searchBtn.click();
		Common.sleep(2000);
		Common.doubleClick(driver, hmcPage.typeCodeResult);
		hmcPage.open_newwindow.click();
		switchToWindow(1);
		hmcPage.open_openorganizer.click();
		switchToWindow(2);
		hmcPage.material_select.click();
		hmcPage.salesOrg_select.click();
		hmcPage.priceB2Cmaterial.clear();
		hmcPage.priceB2Cmaterial.sendKeys(partNumber);
		hmcPage.priceB2CsalesOrg.clear();
		hmcPage.priceB2CsalesOrg.sendKeys(Salesorg);
		hmcPage.ProductBuidler_searchBtn.click();
		Common.sleep(3000);
		Common.rightClick(driver, hmcPage.resultOne);
		hmcPage.selectAll.click();
		hmcPage.deleteAll.click();
		driver.switchTo().alert().accept();
	}

	public void uploadFile(String filePath) {
		hmcPage.Upload.click();
		switchToWindow(1);

		WebElement uploadFile = hmcPage.UploadPage_ChooseFileButton;
		uploadFile.sendKeys(filePath);
		hmcPage.UploadPage_UploadButton.click();
		// String returnInformation = hmcPage.UploadPage_UploadInformation.getText();
		// Assert.assertTrue(returnInformation.contains("Upload Successfully!"), "Upload
		// fail!");
		System.out.println("Upload successfully!");
		switchToWindow(0);
		hmcPage.update.click();
	}


	public void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(windows.get(windowNo));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	private enum EnumTestData {
		country1, country, unit, store;
	}

	private String getData(String store, EnumTestData dataName) {
		if (store.equals("US_OUTLET")) {
			switch (dataName) {

			case country1:
				return "[US] United States";
			case country:
				return "United States";
			case unit:
				return "usweb";
			case store:
				return "[outletus] US Outlet";
			default:
				return null;
			}
		} else {
			return null;
		}
	}
	
	public List<Map<String, Object>> write(String[] data) {
		//数据准备
		       List<Map<String, Object>> list = new ArrayList<>();
		         for (int i = 0; i < 1; i++) {
		            Map<String, Object> map = new LinkedHashMap<>();
		             map.put("Country", data[0]);
		             map.put("Channel", data[1]);
		             map.put("Product", data[2]);
		             map.put("Type", data[3]);
		             map.put("LanguageID", data[4]);
		             map.put("ShortDescription", data[5]);
		             map.put("ItemEffectiveStartDate", data[6]);
		             map.put("ProductCategory", data[7]);
		             map.put("ListPrice", data[8]);
		             map.put("Currency", data[9]);
		             map.put("PriceEffectiveStartDate", data[10]);
		             map.put("CostPrice", data[11]);
		             map.put("CostPriceCurrency", data[12]);
		             map.put("CostPriceStartDate", data[13]);
		             map.put("CostPriceEndDate", data[14]);
		             map.put("ImageFilename", data[15]);
		             map.put("Warranty", data[16]);
		             map.put("OperatingSystem",data[17]);
		             map.put("PointingDevice", data[18]);
		             map.put("Memory", data[19]);
		             map.put("Graphics",data[20]);
		             map.put("Processor", data[21]);
		             map.put("Battery", data[22]);
		             map.put("Display", data[23]);
		             map.put("HardDrive",data[24]);
		             map.put("FormFactor", data[25]);
		             map.put("OpticalDrive", data[26]);
		             map.put("Wireless", data[27]);
		             map.put("LAN", data[28]);
		             map.put("Bluetooth", data[29]);
		             map.put("FingerPrintReader",data[30]);
		             map.put("TV_Tuner", data[31]);
		             map.put("HDMI", data[32]);
		             map.put("NFC", data[33]);
		             map.put("Outlet", data[34]);
		             map.put("SerialNumber", data[35]);
		             map.put("ProductCondition", data[36]);
		             map.put("StorageLocation", data[37]);
		             map.put("OutletPartnumber", data[38]);
		             map.put("OutletPrice", data[39]);
		             map.put("OriginalPartNumber", data[40]);
		             map.put("isConfigurableOutlet", data[41]);
		             list.add(map);
		         }
				return list;
	}
	
	public List<Map<String, Object>> write2(String[] data) {
		 List<Map<String, Object>> list = new ArrayList<>();
         for (int i = 0; i < 1; i++) {
            Map<String, Object> map = new LinkedHashMap<>();
             map.put("itemName", data[0]);
             map.put("productGroupName", data[1]);
             map.put("inventory", data[2]);
             map.put("inventoryLocation", data[3]);
             map.put("introductionDate", data[4]);
             list.add(map);
         }
		return list;
	}
	

}
