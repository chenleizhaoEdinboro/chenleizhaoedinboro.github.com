package TestScript.B2C;

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class ProductFileProcess extends SuperTestClass {

	private String cronjobXpath;
	private String b2CUnit;
	public HMCPage hmcPage;
	public B2CPage b2cPage;
	private int itemCount = 0;

	// private int result = 0;

	public ProductFileProcess(String store, String cronjob, String unitName) {
		this.Store = store;
		this.testName = "ProductFileProcess";
		this.cronjobXpath = cronjob;
		this.b2CUnit = unitName;
	}

	public int convertTXT(String txtPath, String excelPath) throws IOException {

		int countRow = 0;
		LinkedList<String[]> text_lines = new LinkedList<String[]>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(txtPath));
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				text_lines.add(sCurrentLine.split("\\t"));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String fileName = excelPath;
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("FirstSheet");
		int row_num = 0;
		for (String[] line : text_lines) {
			countRow++;
			Row row = sheet.createRow(row_num++);
			int cell_num = 0;
			for (String value : line) {
				Cell cell = row.createCell(cell_num++);
				cell.setCellValue(value);
			}
		}
		FileOutputStream fileOut = new FileOutputStream(fileName);
		workbook.write(fileOut);
		fileOut.close();
		workbook.close();
		return countRow;
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

	public boolean storeCTO(int rowCount, Connection SQLConn, String country,
			String Environment, String excelPath)
			throws EncryptedDocumentException, InvalidFormatException,
			IOException, InterruptedException, SQLException {
		boolean result = false;
		int rowIndex;
		String buyURL;
		String Temp;
		String insertStmt;
		PreparedStatement ps;
		// int result;
		int NumberofProduct = 0;
		FileInputStream excelFileInPutStream = new FileInputStream(excelPath);
		Workbook workbook;
		workbook = WorkbookFactory.create(excelFileInPutStream);
		Sheet sheet = workbook.getSheetAt(0);

		for (rowIndex = 2; rowIndex < rowCount; rowIndex++) {
			if (readCellValue(sheet.getRow(rowIndex), 5).contains("CTO")
					&& (readCellValue(sheet.getRow(rowIndex), 3).contains(
							"Notebook") || readCellValue(
							sheet.getRow(rowIndex), 3).contains("ThinkPad"))
					&& !readCellValue(sheet.getRow(rowIndex), 3).contains(
							"Accessories")
					&& !readCellValue(sheet.getRow(rowIndex), 5).equals("")) {
				buyURL = readCellValue(sheet.getRow(rowIndex), 6);
				if (this.Environment.equals("QAS")) {

					Temp = buyURL.replace("pre-c-hybris", "qas-hybris");
					buyURL = Temp;

				}
				if (this.Environment.equals("TunC")) {

					Temp = buyURL.replace("pre-c-hybris", "tun-c-hybris");
					buyURL = Temp;

				}
				// if (this.Environment.equals("ConC")) {
				//
				// Temp = buyURL.replace("pre-c-hybris", "con-c-hybris");
				// buyURL = Temp;
				//
				// }
				if (country.equals("GB") || country.equals("IE")
						|| country.equals("FR")) {
					if (verifyProductStatus(buyURL.replace("http://",
							"http://LIeCommerce:M0C0v0n3L!@"), "CTO") == true) {
						// &&
						// verifyProductStatus(buyURL.replace("http://pre-c-hybris.lenovo.com",
						// "http://www3.lenovo.com")) == true) {
						// store and break
						NumberofProduct++;
						insertStmt = "INSERT INTO product_number_b2c (Country,ProductType,ProductName,ProductNO,Env,TimeAdded ) values('"
								+ country
								+ "','"
								+ "CTO"
								+ "','"
								+ readCellValue(sheet.getRow(rowIndex), 4)
								+ "','"
								+ readCellValue(sheet.getRow(rowIndex), 5)
								+ "','"
								+ Environment
								+ "','"
								+ getStringDateShort(0) + "');";
						ps = SQLConn.prepareStatement(insertStmt);
						ps.executeUpdate();
						System.out.println("Insert PN: "
								+ readCellValue(sheet.getRow(rowIndex), 5));
						if (NumberofProduct >= 2) {
							break;
						}
						result = true;
					}
				} else {
					if (verifyProductStatus(buyURL.replace("http://",
							"http://LIeCommerce:M0C0v0n3L!@"), "CTO") == true) {
						// store and break
						NumberofProduct++;
						insertStmt = "INSERT INTO product_number_b2c (Country,ProductType,ProductName,ProductNO,Env,TimeAdded ) values('"
								+ country
								+ "','"
								+ "CTO"
								+ "','"
								+ readCellValue(sheet.getRow(rowIndex), 4)
								+ "','"
								+ readCellValue(sheet.getRow(rowIndex), 5)
								+ "','"
								+ Environment
								+ "','"
								+ getStringDateShort(0) + "');";
						ps = SQLConn.prepareStatement(insertStmt);
						ps.executeUpdate();
						System.out.println("Insert PN: "
								+ readCellValue(sheet.getRow(rowIndex), 5));
						if (NumberofProduct >= 2) {
							break;
						}
						result = true;
					}
				}
			}
		}
		return result;
	}

	public boolean storeMTM(int rowCount, Connection SQLConn, String country,
			String Environment, String excelPath)
			throws EncryptedDocumentException, InvalidFormatException,
			IOException, InterruptedException, SQLException {
		boolean result = false;
		int NumberofProduct = 0;
		int rowIndex;
		String buyURL;
		String Temp;
		String insertStmt;
		PreparedStatement ps;
		// int result;
		FileInputStream excelFileInPutStream = new FileInputStream(excelPath);
		Workbook workbook;
		workbook = WorkbookFactory.create(excelFileInPutStream);
		Sheet sheet = workbook.getSheetAt(0);
		for (rowIndex = 2; rowIndex < rowCount; rowIndex++) {
			if (!readCellValue(sheet.getRow(rowIndex), 5).contains("CTO")
					&& readCellValue(sheet.getRow(rowIndex), 3).contains(
							"Notebook")
					&& !readCellValue(sheet.getRow(rowIndex), 3).contains(
							"Accessories")
					&& !readCellValue(sheet.getRow(rowIndex), 5).equals("")) {
				buyURL = readCellValue(sheet.getRow(rowIndex), 6);
				if (this.Environment.equals("QAS")) {

					Temp = buyURL.replace("pre-c-hybris", "qas-hybris");
					buyURL = Temp;
				}
				if (this.Environment.equals("TunC")) {

					Temp = buyURL.replace("pre-c-hybris", "tun-c-hybris");
					buyURL = Temp;

				}
				// if (this.Environment.equals("ConC")) {
				//
				// Temp = buyURL.replace("pre-c-hybris", "con-c-hybris");
				// buyURL = Temp;
				//
				// }
				if (country.equals("GB") || country.equals("IE")
						|| country.equals("FR")) {
					if (verifyProductStatus(buyURL.replace("http://",
							"http://LIeCommerce:M0C0v0n3L!@"), "MTM") == true) {
						// && verifyProductStatus(buyURL.replace(
						// "http://pre-c-hybris.lenovo.com",
						// "http://www3.lenovo.com")) == true) {

						NumberofProduct++;
						// store and break
						if (updateStockLevel(readCellValue(
								sheet.getRow(rowIndex), 5)) == true) {
							insertStmt = "INSERT INTO product_number_b2c (Country,ProductType,ProductName,ProductNO,Env,TimeAdded,updatedStock ) values('"
									+ country
									+ "','"
									+ "MTM"
									+ "','"
									+ readCellValue(sheet.getRow(rowIndex), 4)
									+ "','"
									+ readCellValue(sheet.getRow(rowIndex), 5)
									+ "','"
									+ Environment
									+ "','"
									+ getStringDateShort(0)
									+ "','"
									+ "Y"
									+ "');";
						} else {

							insertStmt = "INSERT INTO product_number_b2c (Country,ProductType,ProductName,ProductNO,Env,TimeAdded,updatedStock ) values('"
									+ country
									+ "','"
									+ "MTM"
									+ "','"
									+ readCellValue(sheet.getRow(rowIndex), 4)
									+ "','"
									+ readCellValue(sheet.getRow(rowIndex), 5)
									+ "','"
									+ Environment
									+ "','"
									+ getStringDateShort(0)
									+ "','"
									+ "N"
									+ "');";
						}

						ps = SQLConn.prepareStatement(insertStmt);
						ps.executeUpdate();

						System.out.println("Insert PN: "
								+ readCellValue(sheet.getRow(rowIndex), 5));
						if (NumberofProduct >= 2) {
							break;
						}
						result = true;
					}
				} else {
					if (verifyProductStatus(buyURL.replace("http://",
							"http://LIeCommerce:M0C0v0n3L!@"), "MTM") == true) {
						NumberofProduct++;
						// store and break
						if (updateStockLevel(readCellValue(
								sheet.getRow(rowIndex), 5)) == true) {
							insertStmt = "INSERT INTO product_number_b2c (Country,ProductType,ProductName,ProductNO,Env,TimeAdded,updatedStock ) values('"
									+ country
									+ "','"
									+ "MTM"
									+ "','"
									+ readCellValue(sheet.getRow(rowIndex), 4)
									+ "','"
									+ readCellValue(sheet.getRow(rowIndex), 5)
									+ "','"
									+ Environment
									+ "','"
									+ getStringDateShort(0)
									+ "','"
									+ "Y"
									+ "');";
						} else {
							insertStmt = "INSERT INTO product_number_b2c (Country,ProductType,ProductName,ProductNO,Env,TimeAdded,updatedStock ) values('"
									+ country
									+ "','"
									+ "MTM"
									+ "','"
									+ readCellValue(sheet.getRow(rowIndex), 4)
									+ "','"
									+ readCellValue(sheet.getRow(rowIndex), 5)
									+ "','"
									+ Environment
									+ "','"
									+ getStringDateShort(0)
									+ "','"
									+ "N"
									+ "');";
						}

						ps = SQLConn.prepareStatement(insertStmt);
						ps.executeUpdate();

						System.out.println("Insert PN: "
								+ readCellValue(sheet.getRow(rowIndex), 5));
						if (NumberofProduct >= 2) {
							break;
						}
						result = true;
					}
				}

			}
		}

		return result;
	}

	public boolean storeAccessory(int rowCount, Connection SQLConn,
			String country, String Environment, String excelPath,
			B2CPage b2cpage) throws EncryptedDocumentException,
			InvalidFormatException, IOException, InterruptedException,
			SQLException {
		boolean result = false;
		int NumberofProduct = 1;
		int rowIndex;
		String buyURL;
		String Temp;
		String insertStmt;
		PreparedStatement ps;
		// int result;
		FileInputStream excelFileInPutStream = new FileInputStream(excelPath);
		Workbook workbook;
		workbook = WorkbookFactory.create(excelFileInPutStream);
		Sheet sheet = workbook.getSheetAt(0);
		for (rowIndex = 2; rowIndex < rowCount; rowIndex++) {
			if (!readCellValue(sheet.getRow(rowIndex), 5).contains("CTO")
					&& !readCellValue(sheet.getRow(rowIndex), 3).contains(
							"Notebook")
					&& readCellValue(sheet.getRow(rowIndex), 3).contains(
							"Accessories")
					&& !readCellValue(sheet.getRow(rowIndex), 5).equals("")) {
				buyURL = readCellValue(sheet.getRow(rowIndex), 6);
				if (this.Environment.equals("QAS")) {

					Temp = buyURL.replace("pre-c-hybris", "qas-hybris");
					buyURL = Temp;
				}
				if (this.Environment.equals("TunC")) {

					Temp = buyURL.replace("pre-c-hybris", "tun-c-hybris");
					buyURL = Temp;

				}
				// if (this.Environment.equals("ConC")) {
				//
				// Temp = buyURL.replace("pre-c-hybris", "con-c-hybris");
				// buyURL = Temp;
				//
				// }
				if (country.equals("GB") || country.equals("IE")
						|| country.equals("FR")) {
					if (verifyAccessoryStatus(buyURL.replace("http://",
							"http://LIeCommerce:M0C0v0n3L!@"), b2cpage) == true) {
						// && verifyProductStatus(buyURL.replace(
						// "http://pre-c-hybris.lenovo.com",
						// "http://www3.lenovo.com")) == true) {

						NumberofProduct++;
						// store and break
						if (updateStockLevel(readCellValue(
								sheet.getRow(rowIndex), 5)) == true) {
							insertStmt = "INSERT INTO product_number_b2c (Country,ProductType,ProductName,ProductNO,Env,TimeAdded,updatedStock ) values('"
									+ country
									+ "','"
									+ "Accessory"
									+ "','"
									+ readCellValue(sheet.getRow(rowIndex), 4)
									+ "','"
									+ readCellValue(sheet.getRow(rowIndex), 5)
									+ "','"
									+ Environment
									+ "','"
									+ getStringDateShort(0)
									+ "','"
									+ "Y"
									+ "');";
						} else {

							insertStmt = "INSERT INTO product_number_b2c (Country,ProductType,ProductName,ProductNO,Env,TimeAdded,updatedStock ) values('"
									+ country
									+ "','"
									+ "Accessory"
									+ "','"
									+ readCellValue(sheet.getRow(rowIndex), 4)
									+ "','"
									+ readCellValue(sheet.getRow(rowIndex), 5)
									+ "','"
									+ Environment
									+ "','"
									+ getStringDateShort(0)
									+ "','"
									+ "N"
									+ "');";
						}

						ps = SQLConn.prepareStatement(insertStmt);
						ps.executeUpdate();

						System.out.println("Insert PN: "
								+ readCellValue(sheet.getRow(rowIndex), 5)
								+ "1" + result);
						result = true;
						if (NumberofProduct >= 2) {
							break;
						}

					}
				} else {
					if (verifyAccessoryStatus(buyURL.replace("http://",
							"http://LIeCommerce:M0C0v0n3L!@"), b2cpage) == true) {
						NumberofProduct++;
						// store and break
						if (updateStockLevel(readCellValue(
								sheet.getRow(rowIndex), 5)) == true) {
							insertStmt = "INSERT INTO product_number_b2c (Country,ProductType,ProductName,ProductNO,Env,TimeAdded,updatedStock ) values('"
									+ country
									+ "','"
									+ "Accessory"
									+ "','"
									+ readCellValue(sheet.getRow(rowIndex), 4)
									+ "','"
									+ readCellValue(sheet.getRow(rowIndex), 5)
									+ "','"
									+ Environment
									+ "','"
									+ getStringDateShort(0)
									+ "','"
									+ "Y"
									+ "');";
						} else {
							insertStmt = "INSERT INTO product_number_b2c (Country,ProductType,ProductName,ProductNO,Env,TimeAdded,updatedStock ) values('"
									+ country
									+ "','"
									+ "Accessory"
									+ "','"
									+ readCellValue(sheet.getRow(rowIndex), 4)
									+ "','"
									+ readCellValue(sheet.getRow(rowIndex), 5)
									+ "','"
									+ Environment
									+ "','"
									+ getStringDateShort(0)
									+ "','"
									+ "N"
									+ "');";
						}

						ps = SQLConn.prepareStatement(insertStmt);
						ps.executeUpdate();

						System.out.println("Insert PN: "
								+ readCellValue(sheet.getRow(rowIndex), 5));
						result = true;

						if (NumberofProduct >= 2) {
							break;
						}

					}
				}

			}
		}

		return result;
	}

	public void startCronJob(HMCPage hmcPage, String JobXpath, String B2CUnit)
			throws InterruptedException {
		String temp;

		String parentWindowId = driver.getWindowHandle();
		hmcPage.System_Menu.click();
		hmcPage.System_CronJobs.click();
		hmcPage.CronJobs_OptionContainsforJobs.click();
		hmcPage.CronJob_jobName.sendKeys("productgeneration");
		hmcPage.CronJob_searchButton.click();
		if (!Common.isElementExist(driver, By.xpath(JobXpath))) {
			driver.findElement(By.xpath("//img[contains(@id,'next_page')]"))
					.click();
		}
		WebElement JobName = driver.findElement(By.xpath(JobXpath));
		Common.doubleClick(driver, JobName);
		hmcPage.CronJobs_RunAS.click();
		hmcPage.CronJobs_ServerNode.clear();
		hmcPage.CronJobs_ServerNode.sendKeys("7");
		hmcPage.CronJobs_Administration.click();
		hmcPage.CronJobs_IsSendftpfalse.click();
		hmcPage.CronJobs_unituid.clear();
		hmcPage.CronJobs_unituid.sendKeys(B2CUnit);
		if (!hmcPage.enabledCronJob.isSelected()) {
			hmcPage.enabledCronJob.click();
			hmcPage.Common_SaveButton.click();
			Thread.sleep(5000);
		}
		hmcPage.CronJobs_StartConronJobs.click();
		Thread.sleep(5000);
		System.out.println("start cronjob");

		driver.switchTo().window(parentWindowId);
		temp = hmcPage.CronJobs_CurrentStatus.getText().toString();
		System.out.println(temp + temp.contains("RUNNING"));
		while (temp.contains("RUNNING")) {
			Thread.sleep(10000);
			hmcPage.CronJob_reloadButton.click();
			// process alert
			Common.waitAlertPresent(hmcPage.PageDriver, 60);
			hmcPage.PageDriver.switchTo().alert().accept();
			temp = hmcPage.CronJobs_CurrentStatus.getText().toString();
			System.out.println(temp + temp.contains("RUNNING"));
		}
	}

	public void downloadProductFile(HMCPage hmcPage, String JobXpath,
			String B2CUnit, WebDriver drv) throws InterruptedException,
			IOException, AWTException {
		// String temp;
		String fileResult;
		String downloadPath = "C:\\ProductFiles\\";
		// C:\Users\appadm2\Desktop
		int size;
		int PageIndex = 2;
		String elementXPath = "//div[@title='Go to page " + PageIndex + "']";
		By PageNum = By.xpath(elementXPath);
		// By Next =
		// By.xpath(".//*[@id='MC87x447_next_page_img'
		// and@src='images/icons/footer_next.gif'] ");
		// String parentWindowId = drv.getWindowHandle();

		hmcPage.MultiMedia_Menu.click();
		Common.waitElementClickable(drv, hmcPage.MultiMedia_MediaOption, 5);

		hmcPage.MultiMedia_MediaOption.click();

		hmcPage.Media_Folder.click();

		hmcPage.Media_Search.click();

		fileResult = ".//*[@id='resultlist_Content/McSearchListConfigurable[Media]']//tr//div[contains(text(),'"
				+ B2CUnit + "')]/..";
		List<WebElement> filesGenerated = drv
				.findElements(By.xpath(fileResult));
		size = filesGenerated.size();

		while (size == 0 && Common.isElementExist(drv, PageNum)) {

			WebElement ResultPage = drv.findElement(By.xpath(elementXPath));
			ResultPage.click();

			filesGenerated = drv.findElements(By.xpath(fileResult));
			size = filesGenerated.size();
			PageIndex++;
			elementXPath = "//div[@title='Go to page " + PageIndex + "']";
			PageNum = By.xpath(elementXPath);

		}
		fileResult = "(.//*[@id='resultlist_Content/McSearchListConfigurable[Media]']//tr//div[contains(text(),'"
				+ B2CUnit + "')]/..)[" + size + "]";
		WebElement UpdatedFile = drv.findElement(By.xpath(fileResult));

		Common.doubleClick(drv, UpdatedFile);
		hmcPage.Media_Download.click();

		ArrayList<String> tabs = new ArrayList<String>(drv.getWindowHandles());
		// System.out.println(tabs.size());
		drv.switchTo().window(tabs.get(1));
		Thread.sleep(3000);
		String str1 = drv.getPageSource().toString();

		// byte[] by = str1.getBytes();

		String fileName = downloadPath + B2CUnit + ".txt";

		byte[] byyy = str1.getBytes();

		FileOutputStream fos = new FileOutputStream(fileName);

		fos.write(byyy, 0, byyy.length);

		fos.close();

	}

	@Test(alwaysRun = true)
	public void tryReadTXT(ITestContext ctx) throws ClassNotFoundException,
			SQLException, InterruptedException, IOException, AWTException,
			InvalidFormatException {
		boolean isMTMAdded = false;
		boolean isCTOAdded = false;
		boolean isAccessoryAdded = false;
		String today = getStringDateShort(0);
		this.prepareTest();
		String txtLocation = "C:\\ProductFiles\\" + b2CUnit + ".txt";
		String excelLocation = "C:/ProductFiles/" + b2CUnit + ".xls";
		/*
		 * CleanUpTXT("C:\\ProductFiles\\" + b2CUnit + ".txt");
		 * CleanUpTXT("C:\\ProductFiles\\" + b2CUnit + ".xls");
		 */
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://100.67.28.132:3306/necommerce-auto-report",
				"root", "admin");

		try {
			Date now = new Date();
			Date now_10 = new Date(now.getTime() - 1800000); // set to 10
																// minutes
																// ago
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			String nowTime_10 = dateFormat.format(now_10);

			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);

			// driver.get(testData.HMC.getHomePageUrl());
			// HMCCommon.Login(hmcPage, testData);
			// startCronJob(hmcPage, cronjobXpath, b2CUnit);
			// driver.quit();

			// System.setProperty("webdriver.chrome.driver",
			// "src/test/resources/chromedriver.exe");
			// driver = new ChromeDriver();

			// hmcPage = new HMCPage(driver);
			// driver.get(testData.HMC.getHomePageUrl());
			// HMCCommon.Login(hmcPage, testData);
			b2cPage = new B2CPage(driver);

			// downloadProductFile(hmcPage, cronjobXpath, b2CUnit, driver);
			itemCount = convertTXT(txtLocation, excelLocation);
			try {
				isCTOAdded = storeCTO(itemCount, conn, this.Store,
						this.Environment, excelLocation);

				if (isCTOAdded) {
					String DeleteStmt = "DELETE FROM product_number_b2c WHERE Env='"
							+ this.Environment
							+ "' AND TimeAdded<'"
							+ today
							+ " "
							+ nowTime_10
							+ "'"
							+ " AND productType='CTO'"
							+ " AND country='" + this.Store + "'";
					PreparedStatement ps = conn.prepareStatement(DeleteStmt);
					ps.executeUpdate();
				}
			} catch (Exception e) {
				System.out.println("CTO exception" + e.toString());
			}

			driver.quit();

			System.setProperty("webdriver.chrome.driver",
					"src/test/resources/chromedriver.exe");
			driver = new ChromeDriver();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);

			try {
				isMTMAdded = storeMTM(itemCount, conn, this.Store,
						this.Environment, excelLocation);
				if (isMTMAdded) {

					String DeleteStmt = "DELETE FROM product_number_b2c WHERE Env='"
							+ this.Environment
							+ "' AND TimeAdded<'"
							+ today
							+ " "
							+ nowTime_10
							+ "'"
							+ " AND productType='MTM'"
							+ " AND country='" + this.Store + "'";
					PreparedStatement ps = conn.prepareStatement(DeleteStmt);
					ps.executeUpdate();
				}
			} catch (Exception e) {
				System.out.println("MTM exception" + e.toString());
			}
			try {
				isAccessoryAdded = storeAccessory(itemCount, conn, this.Store,
						this.Environment, excelLocation, b2cPage);

				if (isAccessoryAdded) {

					String DeleteStmt = "DELETE FROM product_number_b2c WHERE Env='"
							+ this.Environment
							+ "' AND TimeAdded<'"
							+ today
							+ " "
							+ nowTime_10
							+ "'"
							+ " AND productType='Accessory'"
							+ " AND country='"
							+ this.Store + "'";
					PreparedStatement ps = conn.prepareStatement(DeleteStmt);
					ps.executeUpdate();
				}
			} catch (Exception e) {
				System.out.println("Accessory exception" + e.toString());
			}

			conn.close();
		} catch (EncryptedDocumentException | IOException e) {
			handleThrowable(e, ctx);
		}

	}

	public boolean verifyProductStatus(String url, String productType)
			throws InterruptedException {
		boolean result = true;
		By invalidByConfig = By.xpath("//div[@class='subComingSoonMsg']");
		// By soldOut = By.xpath("//strong[contains(text(),'Sold Out')]");
		// By TempNotAvailable = By
		// .xpath("(//strong[contains(text(),'Temporarily Unvailable')]/..)[1]");
		By OutOfStock = By
				.xpath("//span[contains(text(),'Out of Stock') or contains(text(),'在庫切れ') or contains(text(),'無庫存')]");
		By NoModelAvailable = By
				.xpath(".//*[contains(text(),'THERE ARE NO MODELS AVAILABLE AT THIS TIME') or contains(text(),'is no longer available, may we suggest')]/../../..[@style='display:block;']");
		By NolongerAvailable = By
				.xpath("//h1[contains(text(),'Sorry, this product is no longer available') or contains(text(),'この製品の販売は終了いたしました')]/../../..[@style!='display:none;']");

		// By differentSoldOut =
		// By.xpath("//span[contains(text(),'Sold Out!')]");
		// By TempUnavailable = By
		// .xpath("//strong[contains(text(),'TempUnavailable')]/..");

		By greyButton = By
				.xpath(".//*[@id='addToCartFormTop']/button//span[@aria-hidden='true']/..");
		By greyAddtoCart = By
				.xpath(".//*[@id='addToCartButtonTop' and @disabled='disabled']");
		// By greyAddtoCart = By.xpath(
		// "//button[contains(text(),'Add to cart') and contains(@class,'out-of-stock') or contains(text(),'添加到購物車') or contains(text(),'カートに入れる') or contains(text(),' Agregar al carrito')]");
		By DestNotExist = By.xpath("//div[contains(text(),'does not exist')]");
		By OurApologies = By.xpath(".//*[@class='errorHeading']");
		By NoPassDisplayRule = By
				.xpath(".//*[@class='notPassDisplayRule-redirectMsg-content']");
		By notifyMe = By.xpath("//button[@value='notifyMe']");
		By redStockMsg = By
				.xpath(".//span[@class='stock_message']//font[@color='red']");
		// boolean test = true;
        System.out.println(url);
		driver.get(url);
        driver.manage().window().maximize();
		B2CCommon.handleGateKeeper(b2cPage, testData);
		Thread.sleep(3000);

		/*
		 * System.out.println(Common.isElementExist(driver, soldOut) + " " +
		 * Common.isElementExist(driver, OutOfStock) + " " +
		 * Common.isElementExist(driver, NoModelAvailable) + " " +
		 * Common.isElementExist(driver, NolongerAvailable) + " " +
		 * Common.isElementExist(driver, differentSoldOut) + " " +
		 * Common.isElementExist(driver, TempUnavailable) + " " +
		 * Common.isElementExist(driver, greyAddtoCart));
		 */
		if (this.Store.equals("FR") || this.Store.equals("IE")
				|| this.Store.equals("GB")) {
			if (Common.checkElementDisplays(driver, redStockMsg, 1)
					&& !url.contains("CTO1")) {
				result = false;
			}
		}

		if (driver.getCurrentUrl().contains("500page.html")
				|| driver.getCurrentUrl().contains("notPassDisplayRule")
				|| Common.checkElementDisplays(driver, OurApologies, 1)
				|| Common.checkElementDisplays(driver, DestNotExist, 1)
				// || Common.checkElementDisplays(driver, soldOut, 1)
				// || Common.checkElementDisplays(driver, TempNotAvailable, 1)
				|| Common.checkElementDisplays(driver, OutOfStock, 1)
				|| Common.checkElementDisplays(driver, NoModelAvailable, 1)
				|| Common.checkElementDisplays(driver, NolongerAvailable, 1)
				|| Common.checkElementDisplays(driver, NoPassDisplayRule, 1)
				// || Common.checkElementDisplays(driver, differentSoldOut, 1)
				// || Common.checkElementDisplays(driver, TempUnavailable, 1)
				|| Common.checkElementDisplays(driver, invalidByConfig, 1)
				|| Common.checkElementDisplays(driver, greyAddtoCart, 1)
				|| Common.checkElementDisplays(driver, notifyMe, 1)
				|| Common.checkElementDisplays(driver, greyButton, 1)) {
			result = false;
		} else {
			if (productType.equals("MTM")) {
				try {
					B2CCommon.addProductToCartFromPDPPage(driver);
					assert driver.getCurrentUrl().contains("cart");
					System.out
							.println("product has been added to cart in cart page");
				} catch (Exception e) {
					result = false;
				}
			}
		}

		return result;

	}

	public boolean verifyAccessoryStatus(String url, B2CPage b2cPage)
			throws InterruptedException {
		By invalidByConfig = By.xpath("//div[@class='subComingSoonMsg']");
		// By soldOut = By.xpath("//strong[contains(text(),'Sold Out')]");
		// By TempNotAvailable = By
		// .xpath("(//strong[contains(text(),'Temporarily Unvailable')]/..)[1]");
		By OutOfStock = By
				.xpath("//span[contains(text(),'Out of Stock') or contains(text(),'在庫切れ') or contains(text(),'無庫存')]");
		By NoModelAvailable = By
				.xpath(".//*[contains(text(),'THERE ARE NO MODELS AVAILABLE AT THIS TIME') or contains(text(),'is no longer available, may we suggest')]/../../..[@style='display:block;']");
		By NolongerAvailable = By
				.xpath("//h1[contains(text(),'Sorry, this product is no longer available') or contains(text(),'この製品の販売は終了いたしました')]/../../..[@style!='display:none;']");
		By NoPassDisplayRule = By
				.xpath(".//*[@class='notPassDisplayRule-redirectMsg-content']");
		// By differentSoldOut =
		// By.xpath("//span[contains(text(),'Sold Out!')]");
		// By TempUnavailable = By
		// .xpath("//strong[contains(text(),'TempUnavailable')]/..");

		By greyButton = By
				.xpath(".//*[@id='addToCartFormTop']/button//span[@aria-hidden='true']/..");
		By greyAddtoCart = By
				.xpath(".//*[@id='addToCartButtonTop' and @disabled='disabled']");
		// By greyAddtoCart = By.xpath(
		// "//button[contains(text(),'Add to cart') and contains(@class,'out-of-stock') or contains(text(),'添加到購物車') or contains(text(),'カートに入れる') or contains(text(),' Agregar al carrito')]");
		By DestNotExist = By.xpath("//div[contains(text(),'does not exist')]");
		By OurApologies = By.xpath(".//*[@class='errorHeading']");
		By notifyMe = By.xpath("//button[@value='notifyMe']");
		By redStockMsg = By
				.xpath(".//span[@class='stock_message']//font[@color='red']");
		// boolean test = true;
		boolean ValidateAccessory = false;
		driver.get(url);
		B2CCommon.handleGateKeeper(b2cPage, testData);
		Thread.sleep(3000);

		/*
		 * System.out.println(Common.isElementExist(driver, soldOut) + " " +
		 * Common.isElementExist(driver, OutOfStock) + " " +
		 * Common.isElementExist(driver, NoModelAvailable) + " " +
		 * Common.isElementExist(driver, NolongerAvailable) + " " +
		 * Common.isElementExist(driver, differentSoldOut) + " " +
		 * Common.isElementExist(driver, TempUnavailable) + " " +
		 * Common.isElementExist(driver, greyAddtoCart));
		 */
		if (this.Store.equals("FR") || this.Store.equals("IE")
				|| this.Store.equals("GB")) {
			if (Common.checkElementDisplays(driver, redStockMsg, 1)
					&& !url.contains("CTO1")) {
				ValidateAccessory = false;
			}
		} else if (driver.getCurrentUrl().contains("500page.html")
				|| driver.getCurrentUrl().contains("notPassDisplayRule")
				|| Common.checkElementDisplays(driver, OurApologies, 1)
				|| Common.checkElementDisplays(driver, DestNotExist, 1)
				|| Common.checkElementDisplays(driver, NoPassDisplayRule, 1)
				// || Common.checkElementDisplays(driver, soldOut, 1)
				// || Common.checkElementDisplays(driver, TempNotAvailable, 1)
				|| Common.checkElementDisplays(driver, OutOfStock, 1)
				|| Common.checkElementDisplays(driver, NoModelAvailable, 1)
				|| Common.checkElementDisplays(driver, NolongerAvailable, 1)
				// || Common.checkElementDisplays(driver, differentSoldOut, 1)
				// || Common.checkElementDisplays(driver, TempUnavailable, 1)
				|| Common.checkElementDisplays(driver, invalidByConfig, 1)
				|| Common.checkElementDisplays(driver, greyAddtoCart, 1)
				|| Common.checkElementDisplays(driver, notifyMe, 1)
				|| Common.checkElementDisplays(driver, greyButton, 1)) {
			ValidateAccessory = false;
		} else {
			b2cPage.Add2Cart.click();
			Common.waitElementClickable(
					driver,
					driver.findElement(By
							.xpath("//button[contains(text(),'Add to cart') and contains(@class,'button-called-out')]")),
					20);

			driver.findElement(
					By.xpath("//button[contains(text(),'Add to cart') and contains(@class,'button-called-out')]"))
					.click();
			Thread.sleep(6000);

			if (driver.findElement(By.xpath("//h2[@class='addedToCartMode']"))
					.isDisplayed()) {
				ValidateAccessory = true;
			}
		}

		return ValidateAccessory;

	}

	@SuppressWarnings("static-access")
	public static String getStringDateShort(int gap) {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, gap);
		date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString;
	}

	public void changeChromeDefDownFolder(String folderPath) throws IOException {
		driver.get("chrome://settings/advanced");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String prefId = "download.default_directory";

		File tempDir = new File(folderPath);

		driver.get("chrome://settings-frame");
		driver.findElement(By.xpath(".//*[@id='advanced-settings-expander']"))
				.click();
		// }
		String tmpDirEscapedPath = tempDir.getCanonicalPath().replace("\\",
				"\\\\");
		js.executeScript(String.format(
				"Preferences.setStringPref('%s', '%s', true)", prefId,
				tmpDirEscapedPath));

	}

	public static void CleanUpTXT(String FILE_PATH) throws IOException {

		File f = new File(FILE_PATH);
		if (f.exists()) {
			f.delete();
		}
		f.createNewFile();

	}

	public static void WriteTXT(String s, String FILE_PATH) {
		FileWriter fw = null;
		try {
			File f = new File(FILE_PATH);
			if (!f.exists())
				f.createNewFile();
			fw = new FileWriter(FILE_PATH, true);
			// /t space
			// \r\n enter to another row
			fw.write(s + "\r\n");

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (fw != null)
				try {
					fw.close();
				} catch (IOException e) {
					throw new RuntimeException("Close Failed");
				}
		}
	}

	// public boolean enableQuickOrder(String partNo){}
	public boolean updateStockLevel(String partNo) throws InterruptedException {
		// boolean statusChanged = false;
		try {
			driver.get(testData.HMC.getHomePageUrl());
			if (Common.checkElementDisplays(driver, By.id("Main_user"), 3)) {
				HMCCommon.Login(hmcPage, testData);
				hmcPage.Home_baseCommerce.click();
				hmcPage.BaseCommerce_StockLevel.click();
			} else {
				if (!Common.checkElementDisplays(driver,
						hmcPage.StockLevel_PartNoTextBox, 1))
					driver.findElement(
							By.id("Content/OrganizerComponent[organizersearch][StockLevel]_togglelabel"))
							.click();
			}

			hmcPage.StockLevel_PartNoTextBox.clear();
			hmcPage.StockLevel_PartNoTextBox.sendKeys(partNo);
			hmcPage.stockLevel_search.click();
			Thread.sleep(5000);
			if (!Common.isElementExist(driver,
					By.xpath("//td[contains(text(),'0 - 0')]"))) {
				Common.doubleClick(
						driver,
						driver.findElement(By
								.xpath("(//div[contains(@id,'Content/OrganizerListEntry')])[1]")));
				Thread.sleep(3000);
				hmcPage.stockLevel_availableAmount.clear();
				hmcPage.stockLevel_availableAmount.sendKeys("1000");
				hmcPage.stockLevel_save.click();
				Thread.sleep(3000);
				try {
					hmcPage.Home_category.click();
					hmcPage.Home_ProductsLink.click();
					hmcPage.Catalog_ArticleNumberTextBox.click();
					hmcPage.Catalog_ArticleNumberTextBox.clear();
					hmcPage.Catalog_ArticleNumberTextBox.sendKeys(partNo);
					hmcPage.Catalog_SearchButton.click();
					if (hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult
							.isDisplayed()) {
						Common.doubleClick(
								driver,
								hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);
						Common.waitElementClickable(driver,
								hmcPage.Catalog_Administration, 5);
						hmcPage.Catalog_Administration.click();
						Thread.sleep(2000);
						driver.findElement(
								By.id("Content/BooleanEditor[in Content/Attribute[NemoMTMVariantProduct.enableQuickOrder][1]]_false"))
								.click();
						hmcPage.SaveButton.click();

					}
				} catch (Exception e) {
				}

				return true;
			}
		} catch (Exception e) {
			System.out.println(partNo + " update stock error! " + this.Store);
		}
		System.out.println(partNo + " cannot be updated!");
		return false;
	}

}
