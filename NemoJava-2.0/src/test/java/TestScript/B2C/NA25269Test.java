package TestScript.B2C;

import java.io.File;
import java.io.FileNotFoundException;
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
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.HMCPage;

import TestScript.SuperTestClass;

public class NA25269Test extends SuperTestClass {
	public HMCPage hmcPage;
	private String filePath = "D:/SMB.xls";

	public NA25269Test(String store) {
		this.Store = store;
		this.testName = "NA-25269";
	}

	@Test(alwaysRun = true,groups= {"contentgroup","storemgmt","p2","b2c"})
	public void NA25269(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);

			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("uid[unique=true]", "TestSMB25269");
			map.put("Groups(uid)", "ussmb_unit");
			map.put("locName[lang=en]", "TestSMB25269");
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
			map.put("companyName", "TestSMB_25269");
			map.put("companyAddress1", "Test Address1 cc");
			map.put("companyAddress2", "Test Address2 cc");
			map.put("companyCity", "sss");
			map.put("zipCode", "12357");
			map.put("companyState(Code)", "US-NJ");
			map.put("Industry", "Education");
			map.put("companySize", "100-499 employees");
			map.put("companyYear", "2007");
			map.put("phoneticCompanyName", "Test Phonetic name01");
			map.put("repID", "1234567890");
			map.put("DMU", "1111111111");

			String groupName = smbCheck(map, "group");
			
			// upload if group not exists
			if (groupName.equals("")) {
				createAndUpload(map);
				groupName = smbCheck(map, "group");

			}
			Dailylog.logInfoDB(0, "groupName_1: " + groupName, Store, testName);

			if (groupName.equals(map.get("locName[lang=en]")))
				map.put("locName[lang=en]", groupName + "1");

			String companyName = smbCheck(map, "company");
			Dailylog.logInfoDB(0, "companyName_1: " + companyName, Store, testName);
			if (companyName.equals(map.get("companyName")))
				map.put("companyName", companyName + "1");

			createAndUpload(map);

			groupName = smbCheck(map, "group");
			Dailylog.logInfoDB(0, "groupName_2: " + groupName, Store, testName);
			Assert.assertEquals(groupName, map.get("locName[lang=en]"));

			companyName = smbCheck(map, "company");
			Dailylog.logInfoDB(0, "companyName_2: " + companyName, Store, testName);
			Assert.assertEquals(companyName, map.get("companyName"));

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	private void writeExcel(FileOutputStream os, String excelExtName, String sheetName,
			LinkedHashMap<String, String> data) throws IOException {
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

	private String smbCheck(LinkedHashMap<String, String> map, String itemToCheck) {
		String itemName;
		String value = "";
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_smb.click();
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
			hmcPage.hmcHome_hmcSignOut.click();
			return value;
		} catch (NoSuchElementException e) {
			hmcPage.hmcHome_hmcSignOut.click();
			return value;
		}
		if (itemToCheck.toLowerCase().contains("group")) {
			driver.findElement(searchedItemX).click();
			value = hmcPage.SMB_locname.getAttribute("value");
		} else {
			Common.doubleClick(driver, driver.findElement(searchedItemX));
			value = hmcPage.SMB_zCompanyName.getAttribute("value");
		}		
		hmcPage.hmcHome_hmcSignOut.click();
		return value;
	}

	private void createAndUpload(LinkedHashMap<String, String> map) throws IOException, InterruptedException {
		File file = new File(filePath);
		FileOutputStream excelFileInputStream = new FileOutputStream(file);
		writeExcel(excelFileInputStream, "xls", "NemoSMBCustomerGroup", map);
		excelFileInputStream.close();

		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_smb.click();
		Common.waitElementClickable(driver, hmcPage.SMB_smbGroupUpload, 3);
		hmcPage.SMB_smbGroupUpload.click();
		Common.waitElementClickable(driver, hmcPage.SMBGroupUpload_upload, 3);
		hmcPage.SMBGroupUpload_upload.click();

		if (file.exists()) {
			Common.switchToWindow(driver, 1);
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
			hmcPage.hmcHome_hmcSignOut.click();
			file.delete();
		} else {
			hmcPage.hmcHome_hmcSignOut.click();
			file.delete();
			Assert.fail("upload file not eixsts!");
		}

	}
	


}