package TestScript.B2B;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.B2BCommon.B2BRole;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestData.PropsUtils;
import TestScript.SuperTestClass;

public class NA21226Test extends SuperTestClass {

	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public HSSFWorkbook ExcelWBook;
	public HSSFSheet ExcelWSheet;
	String firstProductNum;
	String productType;
	public String expandLevel = "(.//div[contains(@class,'expandable-hitarea')])[1]";
	Calendar now = Calendar.getInstance();
	String expireMessage = "Your product is not available at this time. Please contact your Lenovo Rep.";
	String endDate;
	int size;
	Boolean runFlag = false;
	String url ;
	String unit = "1214314024";
	String account = "testbuilder@yopmail.com";
	String password = "1q2w3e4r";

	public NA21226Test(String Store) {
		this.Store = Store;
		this.testName = "NA-21226";
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","catalogcontract",  "p2", "b2b"})
	public void NA21226(ITestContext ctx) {

		try {
			this.prepareTest();
			//create account
			// set the download path
						String pathName = "D:\\NA21226\\";
						changeChromeDefDownFolder(pathName);
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			url = testData.B2B.getLoginUrl().replace("/le/1214206755/jp/en/1214210743", "/le/1214314024/pe/en/1214314024");
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Thread.sleep(2000);
			hmcPage.Home_B2BCommerceLink.click();
			hmcPage.Home_B2BUnitLink.click();
			hmcPage.B2BUnit_SearchIDTextBox.clear();
			System.out.println("B2BUNIT IS :" + unit);
			hmcPage.B2BUnit_SearchIDTextBox.sendKeys(unit);
			hmcPage.B2BUnit_SearchButton.click();
			hmcPage.B2BUnit_ResultItem.click();
			Thread.sleep(2000);
			hmcPage.B2CUnit_SiteAttributeTab.click();
			Thread.sleep(2000);
			addBuyerRoles(hmcPage.B2BUnit_SA_SelectBuyerRole, "Buyer account");
			Thread.sleep(2000);
			Dailylog.logInfoDB(2, "Roles are created successfully.", Store, testName);

			int count = driver
					.findElements(By.xpath(
							".//input[contains(@id,'[in Content/Attribute[B2BUnit.needApproveUserRoles]]_false')]"))
					.size();
			Thread.sleep(2000);
			Dailylog.logInfoDB(2, "Roles available : " + count, Store, testName);

			for (int i = 1; i <= count; i++) {
				Thread.sleep(1000);
				driver.findElement(By
						.xpath("(.//input[contains(@id,'[in Content/Attribute[B2BUnit.needApproveUserRoles]]_false')])["
								+ i + "]"))
						.click();
			}
			hmcPage.B2BUnit_siteAttribute_emailDomainValidationToggleNo.click();
			hmcPage.B2BUnit_Save.click();
			Thread.sleep(2000);
			hmcPage.Home_EndSessionLink.click();
			Thread.sleep(2000);

			// Creating B2B Approver1 account
			createAccount(driver, url, unit, b2bPage, B2BRole.Buyer,
					account);		

			// 1, Log on b2b website, and find pricelist download;
			Dailylog.logInfoDB(1, "Log on b2b website, and find pricelist download;", Store, testName);

			makeSureDownloadButtonExists();			
			driver.get(url);
			B2BCommon.Login(b2bPage, account, password);

			b2bPage.HomePage_productsLink.click();
			b2bPage.HomePage_Laptop.click();

			productType = driver.findElement(By.xpath("(//div[@class='agreementContract'])[1]")).getText().trim();
			String firstProductProdNumber = driver
					.findElement(
							By.xpath("(//div[@id='resultList']/div/div[last()]//a[contains(.,'View Details')])[1]"))
					.getAttribute("href");

			String[] strs = firstProductProdNumber.split("/");

			firstProductNum = strs[strs.length - 1];
			System.out.println("firstProductNum is :" + firstProductNum);

			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//input[@id='downloadBtn']"))
					&& b2bPage.PLPPage_DownLoadBtn.isDisplayed());

			Select select = new Select(driver.findElement(By.id("priceListFor")));
			select.selectByValue("all_contract_products");

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

			// 2, Price list download file should be download successfully
			Dailylog.logInfoDB(2, "Price list download file should be download successfully", Store, testName);

			b2bPage.PLPPage_DownLoadBtn.click();

			Thread.sleep(20000);

			String filePath = "D:\\NA21226\\priceList.xls";

			FileInputStream excelFileInPutStream = new FileInputStream(filePath);

			Workbook workbook = WorkbookFactory.create(excelFileInPutStream);
			Sheet sheet = workbook.getSheetAt(0);

			ArrayList<String> al = new ArrayList<String>();
			int rowIndex = 1;

			while (sheet.getRow(rowIndex) != null && readCellValue(sheet.getRow(rowIndex), 1) != "BlankCell") {

				String temp = readCellValue(sheet.getRow(rowIndex), 1);

				al.add(temp);

				rowIndex++;

			}

			excelFileInPutStream.close();

			System.out.println("part number list is :" + al);

			// 3 , Go to HMC -> B2B Commerce -> B2B unit -> Site Attribute -> setting the
			// Expire Message: and Expire Days::
			Dailylog.logInfoDB(3,
					" Go to  HMC -> B2B Commerce -> B2B unit -> Site Attribute -> setting the Expire Message: and Expire Days::",
					Store, testName);

			// b2bPage.homepage_Signout.click();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", b2bPage.homepage_Signout);

			driver.get(testData.HMC.getHomePageUrl());
			setExpireInformation();

			// 4, IF contract product then Login HMC -> NEMO -> CONTRACT ->
			// Search with product ID and Partner ID(B2B UNIT ID) then select the latest one
			// Contract,
			// Open this Contract and change the End Date of this product to today
			Dailylog.logInfoDB(4, "change the End Date of this product to today", Store, testName);
			setEndDate("beforeSet");

			// 6, Go to System —> Facet search -> run Indexer hot-updata
			// then clear the product cache on System —> rediscacheclean
			Dailylog.logInfoDB(6,
					"Go to System —> Facet search -> run Indexer hot-updata then clear the product cache on System —> rediscacheclean",
					Store, testName);
			hotUpdate(firstProductNum, 1);
			rediscacheclean(firstProductNum);

			// 7, Check this product on website
			Dailylog.logInfoDB(7, "Check this product on website", Store, testName);

			hmcPage.Home_EndSessionLink.click();

			driver.get(url);
			B2BCommon.Login(b2bPage, account, password);
			b2bPage.HomePage_productsLink.click();
			b2bPage.HomePage_Laptop.click();

			Common.sleep(6000);
			System.out.println(firstProductNum);
			By by = By.xpath("//div[@id='resultList']/div/div[last()]//button[contains(@onclick,'" + firstProductNum
					+ "')]/../..//div[@class='expireMessage']");
			Boolean expireMessageExist = Common.isElementExist(driver, by);

			Assert.assertTrue(expireMessageExist);

			// 8, Try to download pricelist again
			Dailylog.logInfoDB(8, "Try to download pricelist again", Store, testName);

			b2bPage.HomePage_productsLink.click();
			b2bPage.HomePage_Laptop.click();

			Select select1 = new Select(driver.findElement(By.id("priceListFor")));
			select1.selectByValue("all_contract_products");

			b2bPage.PLPPage_DownLoadBtn.click();

			Thread.sleep(20000);

			String filePath1 = "D:\\NA21226\\priceList (1).xls";

			FileInputStream excelFileInPutStream1 = new FileInputStream(filePath1);

			Workbook workbook1 = WorkbookFactory.create(excelFileInPutStream1);
			Sheet sheet1 = workbook1.getSheetAt(0);

			ArrayList<String> al1 = new ArrayList<String>();
			int rowIndex1 = 1;

			while (sheet1.getRow(rowIndex1) != null && readCellValue(sheet1.getRow(rowIndex1), 1) != "BlankCell") {

				String temp = readCellValue(sheet1.getRow(rowIndex1), 1);

				al1.add(temp);

				rowIndex1++;

			}

			excelFileInPutStream1.close();

			System.out.println("al1 part number list is :" + al1);

			Assert.assertTrue(!al1.contains(firstProductNum));

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	@Test(priority = 1,alwaysRun = true,  enabled = true)
	public void rollBackHMCSet() throws MalformedURLException {
		Dailylog.logInfoDB(9, "Go to roll back.", Store, testName);
		SetupBrowser();
		if (runFlag == true) {
			System.out.println("Roll back hmc set!");
			hmcPage = new HMCPage(driver);
			b2bPage = new B2BPage(driver);

			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			System.out.println("Login in HMC.");
			setEndDate("afterSet");
			Dailylog.logInfoDB(9, "Roll back end date successfully.", Store, testName);
			hotUpdate(firstProductNum, 1);
			rediscacheclean(firstProductNum);
			Dailylog.logInfoDB(9, "Hot update and clear the product cache successfully.", Store, testName);
			// open HMC in a new tab --- second tab
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("window.open('" + url + "')");
			switchToWindow(1);
			B2BCommon.Login(b2bPage, account, password);
			System.out.println("Login in successfully!");
			Common.sleep(2000);
			b2bPage.HomePage_productsLink.click();
			System.out.println("Click products link!");
			b2bPage.HomePage_Laptop.click();
			System.out.println("Click laptop!");
			By by = By.xpath("//div[@id='resultList']/div/div[last()]//button[contains(@onclick,'" + firstProductNum
					+ "')]/../..//div[@class='expireMessage']");
			Boolean expireMessageExistAfterRoll = Common.isElementExist(driver, by);

			Assert.assertFalse(expireMessageExistAfterRoll, "Still display the expired msg!");

		}
		Dailylog.logInfoDB(9, "Roll back successfully.", Store, testName);
	}

	public String readCellValue(Row row, int columnIndex) {
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

	

	public void makeSureDownloadButtonExists() {

		driver.get(testData.HMC.getHomePageUrl());
		Common.sleep(3000);
		HMCCommon.Login(hmcPage, testData);

		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BUnitLink.click();
		hmcPage.B2BUnit_SearchIDTextBox.clear();
		hmcPage.B2BUnit_SearchIDTextBox.sendKeys(unit);

		hmcPage.B2BUnit_SearchButton.click();
		hmcPage.B2BUnit_ResultItem.click();

		hmcPage.B2BUnit_siteAttribute.click();

		if (!driver.findElement(By.xpath("//input[contains(@id,'B2BUnit.showDownload') and contains(@id,'true')]"))
				.isSelected()) {
			driver.findElement(By.xpath("//input[contains(@id,'B2BUnit.showDownload') and contains(@id,'true')]"))
					.click();
			hmcPage.SaveButton.click();
		}

		hmcPage.Home_EndSessionLink.click();
	}

	public void hotUpdate(String testProduct, int windownNo) {
		try {
			System.out.println("hot update start");
			driver.switchTo().defaultContent();
			// HMC -> System -> Facet search -> Indexer hot-update wizard :Solr
			// job
			hmcPage.Home_System.click();
			hmcPage.Home_facetSearch.click();
			hmcPage.Home_indexerHotUpdWiz.click();
			// Set solr facet search configuration
			switchToWindow(windownNo);
			hmcPage.IndexerHotUpdate_solrConfigName.click();
			hmcPage.IndexerHotUpdate_mcnemob2bIndex.click();
			hmcPage.IndexerHotUpdate_nextBtn.click();
			hmcPage.IndexerHotUpdate_indexTyeDD.click();
			hmcPage.IndexerHotUpdate_productIndexType.click();
			hmcPage.IndexerHotUpdate_updateIndexRadioBtn.click();
			hmcPage.IndexerHotUpdate_nextBtn.click();
			Common.rightClick(driver, hmcPage.IndexerHotUpdate_emptyRowToAddProduct);
			hmcPage.IndexerHotUpdate_addProductOption.click();
			switchToWindow(windownNo + 1);
			// product number
			hmcPage.IndexerHotUpdate_articleNumber.sendKeys(testProduct);
			// catalog version
			hmcPage.IndexerHotUpdate_catalogSelect.click();
			hmcPage.IndexerHotUpdate_multiCountryOption.click();
			hmcPage.IndexerHotUpdate_searchBtn.click();
			Common.doubleClick(driver, hmcPage.IndexerHotUpdate_searchResult);
			switchToWindow(windownNo);
			Assert.assertEquals(hmcPage.IndexerHotUpdate_articleNum.getText(), testProduct);
			hmcPage.IndexerHotUpdate_startJobBtn.click();
			// System.out.println("Clicked on the start button to start the index update
			// job!!");
			Thread.sleep(10000);
			// System.out.println("waited 240000");
			new WebDriverWait(driver, 240000)
					.until(ExpectedConditions.visibilityOf(hmcPage.IndexerHotUpdate_indexSuccessMsgBox));
			Assert.assertEquals(hmcPage.IndexerHotUpdate_indexSuccessMsgBox.getText(),
					"Indexing finished successfully.");
			hmcPage.IndexerHotUpdate_doneBtn.click();
			// System.out.println("Solr Job Done!!");
			switchToWindow(windownNo - 1);
			hmcPage.Home_facetSearch.click();
			hmcPage.Home_System.click();
			System.out.println("hot update end");
		} catch (Throwable e) {
		}
	}

	public void startCronJob(String jobName) throws InterruptedException {
		// try {
		System.out.println("CronJob start");
		// naviagting to System > CronJob > singleCouponJob
		hmcPage.Home_System.click();
		hmcPage.Home_cronJob.click();
		if (!Common.isElementExist(driver, By.xpath("//a/span[contains(.,'Search')]"))) {
			driver.findElement(By.xpath("(//*[contains(@id,'[organizersearch][CronJob]')])[1]")).click();
			driver.findElement(By.xpath("(//*[contains(@id,'[organizerlist][CronJob]')])[1]")).click();
		}
		// Jobname
		hmcPage.CronJob_jobName.clear();
		hmcPage.CronJob_jobName.sendKeys(jobName);
		hmcPage.CronJob_searchButton.click();
		// Selecting the Job From Search Results
		Common.waitElementVisible(driver, driver.findElement(By.xpath("//div[text()='" + jobName + "']")));
		Common.doubleClick(driver, driver.findElement(By.xpath("//div[text()='" + jobName + "']")));
		// Start CronJob
		hmcPage.CronJob_startCronJob.click();
		Thread.sleep(5000);
		switchToWindow(2);
		Assert.assertEquals(hmcPage.CronJob_cronJobSuccessMsg.getText(), "CronJob performed.");
		driver.close();
		switchToWindow(1);
		hmcPage.Home_System.click();
		System.out.println("CronJob end");
	}

	public void rediscacheclean(String testProduct) {
		try {
			System.out.println("rediscacheclean start");
			hmcPage.Home_System.click();
			hmcPage.Home_rediscacheclean.click();
			driver.switchTo().frame(0);
			hmcPage.Rediscacheclean_productCode.sendKeys(testProduct);
			hmcPage.Rediscacheclean_clean.click();
			// System.out.println("Cleaned the product cache.");
			Thread.sleep(10000);
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
			hmcPage.Home_System.click();
			System.out.println("rediscacheclean end");
		} catch (Throwable e) {

		}
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

	public void switchToLastWindow() {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
			int size = windows.size();
			driver.switchTo().window(windows.get(size - 1));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void chooseTheLastResult() {
		String resultXpath = "//div[contains(@id,'resultlist_Content/McSearchListConfigurable')]//tr[contains(@id,'Content/OrganizerListEntry')]";
		List<WebElement> resultNum = driver.findElements(By.xpath(resultXpath));
		int month[] = new int[resultNum.size()];
		int day[] = new int[resultNum.size()];
		int year[] = new int[resultNum.size()];
		int lastYear = 0;
		int lastMonth = 0;
		int lastDay = 0;
		for (int i = 0; i < resultNum.size(); i++) {
			String startDate = driver.findElement(By.xpath(
					"(//div[contains(@id,'resultlist_Content/McSearchListConfigurable')]//tr[contains(@id,'Content/OrganizerListEntry')])["
							+ (i + 1) + "]/td[6]"))
					.getAttribute("id");
			System.out.println("The startDate is: " + startDate);
			month[i] = Integer.parseInt(startDate.substring(24, 26));
			// System.out.println("The month is: "+month[i]);
			day[i] = Integer.parseInt(startDate.substring(27, 29));
			// System.out.println("The day is: "+day[i]);
			year[i] = Integer.parseInt(startDate.substring(30, 34));
			// System.out.println("The year is: "+year[i]);
		}
		for (int i = 0; i < resultNum.size(); i++) {
			if (lastYear < year[i]) {
				lastYear = year[i];
				lastMonth = month[i];
				lastDay = day[i];
			} else if (lastYear == year[i]) {
				if (lastMonth < month[i]) {
					lastMonth = month[i];
					lastDay = day[i];
				} else if (lastMonth == month[i]) {
					if (lastDay < day[i]) {
						lastDay = day[i];
					}
				}

			}
		}
		String lastDate = Integer.toString(lastMonth) + "/" + Integer.toString(lastDay) + "/"
				+ Integer.toString(lastYear);
		System.out.println("The last date is: " + lastDate);

		if (lastMonth < 10) {
			lastDate = "0" + Integer.toString(lastMonth) + "/" + Integer.toString(lastDay) + "/"
					+ Integer.toString(lastYear);
		}
		if (lastDay < 10) {
			lastDate = Integer.toString(lastMonth) + "/" + "0" + Integer.toString(lastDay) + "/"
					+ Integer.toString(lastYear);
		}
		if (lastMonth < 10 && lastDay < 10) {
			lastDate = "0" + Integer.toString(lastMonth) + "/" + "0" + Integer.toString(lastDay) + "/"
					+ Integer.toString(lastYear);
		}

		WebElement lastSearchResult = driver.findElement(
				By.xpath("(//tr[contains(@id,'Content/OrganizerListEntry')])/td[6][contains(@id,'" + lastDate + "')]"));
		Common.doubleClick(driver, lastSearchResult);
	}

	public void setEndDate(String flag) {
		System.out.println("Start set the end date!");
		boolean nemoFlag = Common.checkElementExists(driver, hmcPage.Home_Agreement, 10);
		if (nemoFlag == false) {
			hmcPage.Home_Nemo.click();
			System.out.println("Click on Nemo!");
		}
		if (productType.contains("Agreement")) {
			hmcPage.Home_Agreement.click();
			System.out.println("Click on Agreement!");
			boolean searchFlag = Common.checkElementExists(driver, hmcPage.Agreement_productCode, 10);
			if (searchFlag == false) {
				hmcPage.Agreement_searchToggleLabel.click();
			}
			hmcPage.Agreement_productCode.clear();
			hmcPage.Agreement_productCode.sendKeys(firstProductNum);
			hmcPage.Agreement_partnerId.clear();
			hmcPage.Agreement_partnerId.sendKeys(unit);
		} else if (productType.contains("Contract")) {
			hmcPage.Home_Contract.click();
			System.out.println("Click on Contract!");
			boolean searchFlag = Common.checkElementExists(driver, hmcPage.Contract_productCode, 10);
			if (searchFlag == false) {
				hmcPage.Contrac_searchToggleLabel.click();
			}
			hmcPage.Contract_productCode.clear();
			hmcPage.Contract_productCode.sendKeys(firstProductNum);
			hmcPage.Contract_partnerId.clear();
			hmcPage.Contract_partnerId.sendKeys(unit);
		}

		Common.sleep(1000);
		driver.findElement(By.xpath("//td[contains(@id,'ajaxselect_" + unit + "')]")).click();
		hmcPage.Contract_searchbutton.click();
		System.out.println("Click search button!");

		String resultXpath = "//div[contains(@id,'resultlist_Content/McSearchListConfigurable')]//tr[contains(@id,'Content/OrganizerListEntry')]";
		List<WebElement> resultNum = driver.findElements(By.xpath(resultXpath));
		if (resultNum.size() > 1) {
			driver.findElement(By.xpath(".//*[@id='Content/McSearchListConfigurable[Contract]_contractId_sort']"))
					.click();
			chooseTheLastResult();
		} else {
			Common.doubleClick(driver, hmcPage.Contract_searchResult);
		}
		System.out.println("Click on last search result!");
		WebElement element = driver.findElement(By.xpath(
				"//div[contains(@id,'Content/StringDisplay[" + firstProductNum + "]') and contains(@id,'span')]"));
		Common.doubleClick(driver, element);

		if (flag.equals("beforeSet")) {
			switchToWindow(1);
			int day;
			int month;
			// String nowDate = (now.get(Calendar.MONTH) +
			// 1)+"/"+(now.get(Calendar.DAY_OF_MONTH)-1)+"/"+now.get(Calendar.YEAR);
			if (now.get(Calendar.DAY_OF_MONTH) - 1 == 0) {
				day = 29;
				month = now.get(Calendar.MONTH);
			} else {
				day = now.get(Calendar.DAY_OF_MONTH) - 1;
				month = now.get(Calendar.MONTH) + 1;
			}
			String nowDate = month + "/" + day + "/" + now.get(Calendar.YEAR);

			if (productType.contains("Agreement")) {
				endDate = hmcPage.Agreement_endDate.getText();
				System.out.println("The endDate is: " + endDate);
				hmcPage.Agreement_endDate.clear();
				hmcPage.Agreement_endDate.sendKeys(nowDate);
			} else if (productType.contains("Contract")) {
				endDate = hmcPage.Contrac_endDate.getAttribute("value");
				System.out.println("The endDate is: " + endDate);
				hmcPage.Contrac_endDate.clear();
				hmcPage.Contrac_endDate.sendKeys(nowDate);
			}
			runFlag = true;
			hmcPage.Catalog_PMISaveButton.click();
			System.out.println("Click save button!");
			driver.close();
			switchToWindow(0);
		} else {
			switchToWindow(1);
			if (productType.contains("Agreement")) {
				hmcPage.Agreement_endDate.clear();
				if (endDate == null) {
					endDate = "01/01/3000";
				} else if (Integer.parseInt(endDate.substring(6)) < 2018) {
					endDate = "01/01/3000";
				}
				hmcPage.Agreement_endDate.sendKeys(endDate);
				System.out.println("Input the end date!");
			} else if (productType.contains("Contract")) {
				hmcPage.Contrac_endDate.clear();
				if (endDate == null) {
					endDate = "01/01/3000";
				} else if (Integer.parseInt(endDate.substring(6)) < 2018) {
					endDate = "01/01/3000";
				}
				hmcPage.Contrac_endDate.sendKeys(endDate);
				System.out.println("Input the end date!");
			}
			hmcPage.Catalog_PMISaveButton.click();
			System.out.println("Click save button!");
			driver.close();
			switchToWindow(0);
		}

		hmcPage.B2BUnit_Save.click();
		System.out.println("Click save button!");
		System.out.println("End set the end date!");
	}
	
	public void expandAccessLevel(int counter) {
		if (Common.isElementExist(driver, By.xpath(expandLevel)) == true && counter < 4) {
			b2bPage.Register_AccessLevelExpand.click();
			expandAccessLevel(++counter);
		}
	}
	public void createAccount(WebDriver driver, String B2BUrl, String B2BUnit, B2BPage b2bPage, B2BRole role,
			String tempEmailAddress) throws InterruptedException {
		driver.manage().deleteAllCookies();
		driver.get(B2BUrl);
		b2bPage.Login_CreateAnAccountButton.click();
		Common.sleep(2000);
		expandAccessLevel(1);
		Common.sleep(1000);
		B2BCommon.clickCorrectAccessLevel(b2bPage, B2BUnit);
		Common.sleep(1000);
		B2BCommon.clickRoleCheckbox(b2bPage, role);

		b2bPage.Register_EmailTextBox.clear();
		b2bPage.Register_EmailTextBox.sendKeys(tempEmailAddress);
		b2bPage.Register_ConfirmEmailTextBox.clear();
		b2bPage.Register_ConfirmEmailTextBox.sendKeys(tempEmailAddress);
		b2bPage.Register_FirstNameTextBox.clear();
		b2bPage.Register_FirstNameTextBox.sendKeys(testData.B2B.getFirstName());
		b2bPage.Register_LastNameTextBox.clear();
		b2bPage.Register_LastNameTextBox.sendKeys(testData.B2B.getLastName());
		b2bPage.Register_PasswordTextBox.clear();
		b2bPage.Register_PasswordTextBox.sendKeys(testData.B2B.getDefaultPassword());
		b2bPage.Register_ConfirmPasswordTextBox.clear();
		b2bPage.Register_ConfirmPasswordTextBox.sendKeys(testData.B2B.getDefaultPassword());
		if (Common.checkElementDisplays(driver, b2bPage.Register_acceptterms, 10)) {
			b2bPage.Register_acceptterms.click();
		}
		b2bPage.Register_CreateAccountButton.click();
		Dailylog.logInfoDB(4, "Account is created: " + tempEmailAddress, Store, testName);
		Thread.sleep(2000);
	}
	
	private void addRoles(WebElement role, String roleName) throws InterruptedException {
		Common.rightClick(driver, hmcPage.B2BUnit_SA_UserApproval);
		hmcPage.B2BUnit_SA_AddRole.click();
		Thread.sleep(5000);
		switchToWindow(1);
		Common.doubleClick(driver, role);
		Dailylog.logInfoDB(2, roleName + " role is updated successfully!!!", Store, testName);
		switchToWindow(0);
	}
	
	private void addBuyerRoles(WebElement selectBuyerRole, String buyerRoles) throws InterruptedException {
		addRoles(selectBuyerRole, buyerRoles);
	}

	public void setExpireInformation() {

		HMCCommon.Login(hmcPage, testData);
		System.out.println("Start set expire information!");
		driver.navigate().refresh();
		// HMCCommon.searchB2BUnit(hmcPage, testData);
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BUnitLink.click();
		hmcPage.B2BUnit_SearchIDTextBox.clear();
		System.out.println("B2BUNIT IS :" + unit);
		hmcPage.B2BUnit_SearchIDTextBox.sendKeys(unit);
		hmcPage.B2BUnit_SearchButton.click();
		hmcPage.B2BUnit_ResultItem.click();

		hmcPage.B2BUnit_siteAttribute.click();
		hmcPage.B2BUnit_expireMessage.clear();
		hmcPage.B2BUnit_expireMessage.sendKeys(expireMessage);
		hmcPage.B2BUnit_expireDays.clear();
		hmcPage.B2BUnit_expireDays.sendKeys("15");
		hmcPage.B2BUnit_Save.click();
		System.out.println("Click save button!");
		System.out.println("End set expire information!");
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