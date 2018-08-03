package TestScript.B2B;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestData.PropsUtils;
import TestScript.SuperTestClass;

public class NA16675Test extends SuperTestClass {

	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public String homepageUrl;
	public String cartUrl;
	public String pathName;

	public NA16675Test(String Store) {
		this.Store = Store;
		this.testName = "NA-16675";
	}

	@Test(alwaysRun = true, groups = {"browsegroup","catalogcontract",  "p1", "b2b"})
	public void NA16675(ITestContext ctx) {
		try {
			this.prepareTest();
			
			setDefaultChromeDownlaodPath();
			
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);

			// 1, Go to HMC-B2B Commerce-b2b Unit-site attribute-
			// Show Download->yes
			// click save button

			Dailylog.logInfoDB(1, "Go to HMC-B2B Commerce-b2b Unit-site attribute-Show Download->yes click save button",
					Store, testName);

			makeSureDownloadButtonExists();

			// 2, Log on B2B store with right user name and password
			Dailylog.logInfoDB(2, "Log on B2B store with right user name and password", Store, testName);

			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());

			// 3, Click Laptops in navigation bar，for example, also work for other
			// categroies
			Dailylog.logInfoDB(3, "Click Laptops in navigation bar，for example, also work for other categroies", Store,
					testName);

			createFolderAndClearFiles();

			Common.javascriptClick(driver, b2bPage.HomePage_productsLink);
			b2bPage.HomePage_Laptop.click();

			// 4, Scroll down to the bottom of the PLP page,check download formate “excel”
			// and click on "download"
			Dailylog.logInfoDB(4,
					"Scroll down to the bottom of the PLP page,check download formate excel and click on download",
					Store, testName);
			Common.scrollToElement(driver, b2bPage.downloadExcelFormat);
			b2bPage.downloadExcelFormat.click();
			b2bPage.PLPPage_DownLoadBtn.click();
			Thread.sleep(20000);

			String filePath = "D:\\NA16675\\priceList.xls";

			FileInputStream excelFileInPutStream = new FileInputStream(filePath);
			Workbook workbook = WorkbookFactory.create(excelFileInPutStream);
			Sheet sheet = workbook.getSheetAt(0);

			ArrayList<String> al = new ArrayList<String>();
			int columnIndex = 0;

			while (sheet.getRow(0) != null && readCellValue(sheet.getRow(0), columnIndex) != "BlankCell") {
				String temp = readCellValue(sheet.getRow(0), columnIndex).trim();
				al.add(temp);
				columnIndex++;
			}
			excelFileInPutStream.close();

			System.out.println("part number list is :" + al);
			Assert.assertTrue(al.size() == 6 && al.get(0).equals("Category Name") && al.get(1).equals("Product Number")
					&& al.get(2).equals("Web Price") && al.get(3).equals("Contract Number/Agreement")
					&& al.get(4).equals("Currency") && al.get(5).equals("Soldto Number"));

			// 5, check download format “CSV” and click on "download"
			Dailylog.logInfoDB(5, "check download formate CSV and click on download", Store, testName);

			b2bPage.downloadCSVFormat.click();
			b2bPage.PLPPage_DownLoadBtn.click();
			Thread.sleep(20000);
			String csvFilePath = "D:\\NA16675\\priceList.csv";

			List<String> csvData = getCSVFileData(csvFilePath);
			String[] csvStrs = csvData.get(0).split(",");

			Assert.assertTrue(
					csvStrs.length == 6 && csvStrs[0].equals("Category Name") && csvStrs[1].equals("Product Number")
							&& csvStrs[2].equals("Web Price") && csvStrs[3].equals("Contract Number/Agreement")
							&& csvStrs[4].equals("Currency") && csvStrs[5].equals("Soldto Number"));

			// 6, check download formate “CIF” and click on "download"
			Dailylog.logInfoDB(6, "check download formate CIF and click on download", Store, testName);
			b2bPage.downloadCIFFormat.click();
			b2bPage.PLPPage_DownLoadBtn.click();

			Thread.sleep(20000);
			String cifFilePath = "D:\\NA16675\\priceList.cif";
			File file = new File(cifFilePath);
			Assert.assertTrue(file.exists());

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public List<String> getCSVFileData(String filePath) {

		File csv = new File(filePath);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(csv));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line = "";
		String everyLine = "";
		List<String> allString = new ArrayList<>();
		try {
			while ((line = br.readLine()) != null) {
				everyLine = line;
				System.out.println(everyLine);
				allString.add(everyLine);
			}
			System.out.println("Number of lines in csv file :" + allString.size());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return allString;
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
		HMCCommon.Login(hmcPage, testData);

		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BUnitLink.click();
		hmcPage.B2BUnit_SearchIDTextBox.clear();
		hmcPage.B2BUnit_SearchIDTextBox.sendKeys(testData.B2B.getB2BUnit());

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

	public void setDefaultChromeDownlaodPath() throws Exception {

		String homeUrl = driver.getCurrentUrl().toString();
		pathName = "D:\\NA16675\\";
		changeChromeDefDownFolder(pathName);
		driver.get(homeUrl);
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

	

}
