package TestScript.B2C;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
public class CONTENT402Test extends SuperTestClass {
	public HMCPage hmcPage;
	private B2CPage b2cPage;
	private String filePath = "D:/SMB402.xls";
	public CONTENT402Test(String store) {
		this.Store = store;
		this.testName = "CONTENT-402";
	}

	@Test(alwaysRun = true, groups = {"contentgroup", "storemgmt", "p2", "b2c"})
	public void CONTENT402(ITestContext ctx) {
		try {
			this.prepareTest();
			
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			
			//1-4 upload smb file
			setData("1234567890","right");
			
			//5.check rep id
			smbCheckRepid("TestSMB239431","1234567890");
			Dailylog.logInfoDB(5, " rep id exist", Store, testName);
			
			//6.7,8open smb website
			openSmbWebsite("TestSMB_239431","1234567890");
			Dailylog.logInfoDB(6, "7,8validate rep id", Store, testName);
			
			//9.upload smb file
			driver.manage().deleteAllCookies();
			setData("2900723398","right");
			Dailylog.logInfoDB(9, "upload smb file", Store, testName);
			
			//10.check rep id
			smbCheckRepid("TestSMB239431","2900723398");
			Dailylog.logInfoDB(10, " rep id exist", Store, testName);
			
			//10.open smb website
			openSmbWebsite("TestSMB_239431","2900723398");
			Dailylog.logInfoDB(10, "validate rep id", Store, testName);
			
			//11.12,13change rep id 
			driver.manage().deleteAllCookies();
			setData("290000002","wrong");
			Dailylog.logInfoDB(10, "11.12,13 change rep id ", Store, testName);
			
			 
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	@Test(alwaysRun = true, groups = {"contentgroup", "storemgmt", "p2", "b2c"})
	public void test() {
		//14.15 change rep id
		try {
			setData("29000000003","wrong");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Dailylog.logInfoDB(14, "15 change rep id ", Store, testName);
	}

	private void setData(String repId, String right) throws IOException, InterruptedException {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("uid[unique=true]", "TestSMB239431");
		map.put("Groups(uid)", "us_smb_unit");
		map.put("locName[lang=en]", "TestSMB239431");
		map.put("siteLogo(code)", "");
		map.put("contactPersonID", "AU0000001");
		map.put("paymentAuthSettingList", "CARD:PRE_AUTH_1");
		map.put("displayedCreditCards", "amex;jcb;maestro");
		map.put("paymentType", "CARD;PAYPAL;IGF");
		map.put("deliveryModes", "standard-net;standard-gross");
		map.put("flatRateShippingEnabled", "true");
		map.put("PaymentType and mail to", "IGF:ewr@email.com");
		map.put("PaymentType and mail cc", "IGF:ewr111@email.com");
		map.put("PaymentType and mail bcc", "IGF:ewr@email.com");
		map.put("bpId", "vvvvvbbbb");
		map.put("enableKlarnaPayment", "true");
		map.put("defaultTaxExemptionShipto", "aabb123");
		map.put("defaultTaxExemptionSoldto", "ccdd123");
		map.put("locName[lang=en_US]", "23452dd");
		map.put("companyName", "TestSMB_239431");
		map.put("companyAddress1", "Test Address1 cc");
		map.put("companyAddress2", "Test Address2 cc");
		map.put("companyCity", "sss");
		map.put("zipCode", "12357");
		map.put("companyState(Code)", "US-NJ");
		map.put("Industry", "Education");
		map.put("companySize", "100-499 employees");
		map.put("companyYear", "2007");
		map.put("phoneticCompanyName", "Test Phonetic name01");
		map.put("repID", repId);
		map.put("DMU", "1111111111");
		
		File file = new File(filePath);
		FileOutputStream excelFileInputStream = new FileOutputStream(file);
		writeExcel(excelFileInputStream, "xls", "NemoSMBCustomerGroup", map);
		excelFileInputStream.close();

		deleteExistGroup(map, "group");
		deleteExistGroup(map, "company");
		
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_smb.click();
		if(right.equals("right")) {
			Common.waitElementClickable(driver, hmcPage.SMB_smbGroupUpload, 3);
			hmcPage.SMB_smbGroupUpload.click();
			Common.waitElementClickable(driver, hmcPage.SMBGroupUpload_upload, 3);
			hmcPage.SMBGroupUpload_upload.click();
			Common.switchToWindow(driver, 1);
			if (file.exists()) {
				hmcPage.SMBGroupUpload_selectFile.sendKeys(filePath);
				hmcPage.SMBGroupUpload_uploadBtn.click();
				Common.switchToWindow(driver, 0);
				hmcPage.SMBGroupUpload_update.click();
				Common.switchToWindow(driver, 1);
				hmcPage.SMBGroupUpload_confirm.click();
				try {
					Common.waitElementVisible(driver, hmcPage.SMBGroupUpload_successResult);
				} catch (TimeoutException e) {
					Assert.fail("upload success message is not displayed");
				}
				hmcPage.SMBGroupUpload_close.click();
				Common.switchToWindow(driver, 0);

			} else {
				Assert.fail("upload file not eixsts!");
			}

			smbCheck(map, "group");
			Assert.assertEquals(hmcPage.SMB_locname.getAttribute("value"), map.get("locName[lang=en]"));

			smbCheck(map, "company");
			Assert.assertEquals(hmcPage.SMB_zCompanyName.getAttribute("value"), map.get("companyName"));
			hmcPage.hmcHome_hmcSignOut.click();
			Dailylog.logInfoDB(1, "1-4 upload smb file", Store, testName);
			
			file.delete();
		}
		
		if(right.equals("wrong")) {
			Common.waitElementClickable(driver, hmcPage.SMB_smbGroupUpload, 3);
			hmcPage.SMB_smbGroupUpload.click();
			Common.waitElementClickable(driver, hmcPage.SMBGroupUpload_upload, 3);
			hmcPage.SMBGroupUpload_upload.click();
			Common.sleep(3000);
			Common.switchToWindow(driver, 1);
			Common.sleep(3000);
			hmcPage.SMBGroupUpload_selectFile.sendKeys(filePath);
			hmcPage.SMBGroupUpload_uploadBtn.click();
			Common.switchToWindow(driver, 0);
			hmcPage.SMBGroupUpload_update.click();
			Common.switchToWindow(driver, 1);
			Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath("//pre[contains(.,'rep id must be 10 digit')]")), 15));
			hmcPage.SMBGroupUpload_close.click();
//			hmcPage.hmcHome_hmcSignOut.click();
		}
	}

	private void openSmbWebsite(String company, String repId) {
		driver.get(testData.B2C.getHomePageUrl());
		Common.sleep(3000);
		b2cPage.createAccountSmb.click();
		b2cPage.companyName.clear();
		b2cPage.companyName.sendKeys(company);
		Common.waitElementClickable(driver, driver.findElement(By.xpath("//body[@class=' language-en oo_bar']/ul[contains(.,'"+company+"')]")), 15);
		driver.findElement(By.xpath("//body[@class=' language-en oo_bar']/ul[contains(.,'"+company+"')]")).click();
		Common.sleep(3000);
		Assert.assertEquals(driver.findElement(By.xpath("//input[@id='repId']")).getAttribute("value"), repId);
	}

	private void smbCheckRepid(String company, String repId) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_smb.click();
		hmcPage.SMB_SMBCustomerGroup.click();
		hmcPage.SMBCustomerGroup_uid.sendKeys(company);
		hmcPage.SMB_searchbutton.click();
		Common.doubleClick(driver, hmcPage.B2BUnit_ResultItem);
		Common.scrollToElement(driver, hmcPage.repIdSMB);
		Assert.assertEquals(hmcPage.repIdSMBCustomerGroup.getAttribute("value"), repId);
		hmcPage.hmcHome_hmcSignOut.click();
	}

	private void writeExcel(FileOutputStream os, String excelExtName, String sheetName, LinkedHashMap<String, String> data) throws IOException {
		Workbook wb = null;
		try {
			if ("xls".equals(excelExtName)) {
				wb = new HSSFWorkbook();
			} else if ("xlsx".equals(excelExtName)) {
				wb = new XSSFWorkbook();
			} else {
				throw new Exception("file extention not correct");
			}

			Sheet sheet = wb.createSheet(sheetName);
			Row row1 = sheet.createRow(0);
			Row row2 = sheet.createRow(1);

			int i = 0;
			for (Entry<String, String> colValue : data.entrySet()) {
				Cell colCell = row1.createCell(i);
				colCell.setCellValue(colValue.getKey());
				Cell valueCell = row2.createCell(i);
				valueCell.setCellValue(colValue.getValue());
				++i;
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

	private void smbCheck(LinkedHashMap<String, String> map, String itemToCheck) {
		String itemName;
		if (itemToCheck.toLowerCase().contains("group")) {
			itemName = map.get("uid[unique=true]");
			hmcPage.SMB_SMBCustomerGroup.click();
			hmcPage.SMBCustomerGroup_uid.sendKeys(itemName);
		} else {
			itemName = map.get("companyName");
			hmcPage.SMB_SMBCompany.click();
			Common.sendFieldValue(hmcPage.SMB_SMBCompany_CompanyName, itemName);
		}

		hmcPage.SMB_searchbutton.click();
		By searchedItemX = By.xpath("(//td[contains(.,'" + itemName + "')])[last()]");
		try {
			Common.waitElementClickable(driver, driver.findElement(searchedItemX), 3);
		} catch (TimeoutException e) {
			Assert.fail("no search result of : " + itemName);
		} catch (NoSuchElementException e) {
			Assert.fail("no search result of : " + itemName);
		}
		if (itemToCheck.toLowerCase().contains("group"))
			driver.findElement(searchedItemX).click();
		else
			Common.doubleClick(driver, driver.findElement(searchedItemX));
	}

	private void deleteExistGroup(LinkedHashMap<String, String> map, String itemToCheck) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_smb.click();
		String itemName;
		if (itemToCheck.toLowerCase().contains("group")) {
			itemName = map.get("uid[unique=true]");
			hmcPage.SMB_SMBCustomerGroup.click();
			hmcPage.SMBCustomerGroup_uid.sendKeys(itemName);
		} else {
			itemName = map.get("companyName");
			hmcPage.SMB_SMBCompany.click();
			Common.sendFieldValue(hmcPage.SMB_SMBCompany_CompanyName, itemName);
		}
		hmcPage.SMB_searchbutton.click();
		By searchedItemX = By.xpath("(//td[contains(.,'" + itemName + "')])[last()]");
		if (Common.checkElementDisplays(driver, searchedItemX, 5)) {
			if (itemToCheck.toLowerCase().contains("group"))
				driver.findElement(searchedItemX).click();
			else
				Common.doubleClick(driver, driver.findElement(searchedItemX));
			hmcPage.SMB_SMBCompanyAndCustomer_Delete.click();
			Common.sleep(2000);
			Alert alert = driver.switchTo().alert();
			alert.accept();		
			// hmcPage.SMB_CustomerGroup_SearchToggle.click();
			// Common.sendFieldValue(hmcPage.SMB_CustomerGroup_IdentifyCode, itemName);
			// Common.sleep(2000);
			// hmcPage.SMB_CustomerGroup_Srarch.click();
			// Assert.assertEquals(false, Common.isElementExist(driver, searchedItemX, 5));
		}
		hmcPage.hmcHome_hmcSignOut.click();
	}

}
