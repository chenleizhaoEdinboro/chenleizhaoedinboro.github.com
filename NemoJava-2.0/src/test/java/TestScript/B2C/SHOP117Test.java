package TestScript.B2C;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class SHOP117Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;

	public SHOP117Test(String store) {
		this.Store = store;
		this.testName = "SHOPE-117";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup", "productbuilder", "p2", "b2c" })
	public void SHOP117(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);

			String filePath = "D:\\SHOP117\\";

			deleteFile(filePath);

			String subScription = "RR00000001";

			String mt = "20L5";

			String contents = "$productCatalog=masterMultiCountryProductCatalog\r\n" + "$catalogVersion=catalogversion(catalog(id[default=$productCatalog]),version[default='Online'])[unique=true,default=$productCatalog:Online]\r\n" + "$OSType=OS Independent\r\n" + " \r\n" + "INSERT_UPDATE ProductReference;source(code,$catalogVersion)[unique=true];target(code,$catalogVersion)[unique=true];referenceType(code);regionCountry;active;preselected;OSType[default=$OSType]\r\n" + ";" + mt + ";" + subScription + ";SUBSCRIPTION;:;true;false;;\r\n" + "";

			createFile(filePath, contents);

			// checkProductReferrence(mt, subScription, true);

			importImpex(filePath);

			boolean isReferExist = checkProductReferrence(mt, subScription, true);

			deleteFile(filePath);

			Assert.assertTrue(isReferExist, "isReferExist");

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private void deleteFile(String filePath) {
		File file = new File(filePath);
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

	private void createFile(String filePath, String contents) throws UnsupportedEncodingException, IOException {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(filePath + "shop117.impex"));
			out.write(contents.getBytes("utf-8"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}

	}

	private boolean checkProductReferrence(String mt, String subScription, boolean isDelete) throws InterruptedException {
		boolean flag = false;
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.marketing.click();
		hmcPage.Marketing_productReference.click();
		hmcPage.ProductReference_source.click();
		Common.switchToWindow(driver, 1);
		hmcPage.ProductReference_articleNumber.sendKeys(mt);
		Select catalogSel = new Select(hmcPage.ProductReference_catalogVersion);
		catalogSel.selectByVisibleText("masterMultiCountryProductCatalog - Online");
		hmcPage.ProductReference_productSearch.click();
		driver.findElement(By.xpath("//div[text()='" + mt + "']")).click();
		hmcPage.ProductReference_productUse.click();
		Common.switchToWindow(driver, 0);
		hmcPage.ProductReference_target.click();
		Common.switchToWindow(driver, 1);
		hmcPage.ProductReference_articleNumber.sendKeys(subScription);
		catalogSel = new Select(hmcPage.ProductReference_catalogVersion);
		catalogSel.selectByVisibleText("masterMultiCountryProductCatalog - Online");
		hmcPage.ProductReference_productSearch.click();
		driver.findElement(By.xpath("//div[text()='" + subScription + "']")).click();
		hmcPage.ProductReference_productUse.click();
		Common.switchToWindow(driver, 0);
		hmcPage.ProductReference_search.click();
		Common.waitElementVisible(driver, driver.findElement(By.xpath("//div[@class='olcEntry']")));
		if (!Common.checkElementDisplays(driver, By.xpath("//td[@class='olcEmpty']"), 5))
			flag = true;
		if (flag && isDelete) {
			hmcPage.ProductReference_openEditor.click();
			hmcPage.ProductReference_editorDelete.click();
			driver.switchTo().alert().accept();
			// flag = false;
		}
		hmcPage.hmcHome_hmcSignOut.click();
		return flag;
	}

	private void importImpex(String filePath) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.system.click();
		hmcPage.System_tools.click();
		hmcPage.Tools_import.click();
		Common.sleep(5000);
		Common.switchToWindow(driver, 1);
		Common.waitElementClickable(driver, hmcPage.Import_importImpexIcon, 10);
		hmcPage.Import_importImpexIcon.click();
		Common.sleep(5000);
		Common.switchToWindow(driver, 2);
		Common.waitElementClickable(driver, hmcPage.ImportImpex_selectFile, 10);
		hmcPage.ImportImpex_selectFile.sendKeys(filePath + "shop117.impex");
		hmcPage.ImportImpex_uploadBtn.click();
		Common.switchToWindow(driver, 1);
		hmcPage.ImportImpex_startBtn.click();
		Common.sleep(5000);
		new WebDriverWait(driver, 3000000).until(ExpectedConditions.visibilityOf(hmcPage.ImportImpex_finishMsg));
		Common.sleep(5000);
		Dailylog.logInfoDB(0, "ImportImpex_status: " + hmcPage.ImportImpex_status.getText(), Store, testName);
		Dailylog.logInfoDB(0, "ImportImpex_result : " + hmcPage.ImportImpex_result.getText(), Store, testName);
		Assert.assertTrue(hmcPage.ImportImpex_status.getText().contains("FINISHED"), "ImportImpex_status should be FINISHED");
		Assert.assertTrue(hmcPage.ImportImpex_result.getText().contains("SUCCESS"), "ImportImpex_result should be SUCCESS");
		driver.close();
		Common.switchToWindow(driver, 0);
		hmcPage.HMC_Logout.click();
	}
}
