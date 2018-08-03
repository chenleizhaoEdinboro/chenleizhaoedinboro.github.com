package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA23818Test extends SuperTestClass {
	public HMCPage hmcPage;

	public NA23818Test(String store) {
		this.Store = store;
		this.testName = "NA-23818";
	}

	@Test(alwaysRun = true,groups= {"contentgroup","storemgmt","p2","b2c"})
	public void NA23818(ITestContext ctx) {
		try {
			this.prepareTest();

			hmcPage = new HMCPage(driver);

			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();

			String selectedStoreType = changeStoreType("SMB", false);
			Assert.assertTrue(selectedStoreType.contains("SMB"), "selectedStoreType");

			hmcPage.B2CUnit_sMBGatekeeperToggleTrue.click();
			hmcPage.HMC_Save.click();
			Common.sleep(5000);
			Common.waitElementClickable(driver, hmcPage.Template_reloadBtn, 3);
			hmcPage.Template_reloadBtn.click();
			if (Common.isAlertPresent(driver))
				driver.switchTo().alert().accept();
			String isStoreTypeTrueSelected = hmcPage.B2CUnit_sMBGatekeeperToggleTrue.getAttribute("value");
			Assert.assertEquals(isStoreTypeTrueSelected, "true", "isStoreTypeTrueSelected");

			selectedStoreType = changeStoreType("PUBLIC_CONSUMER", true);
			Assert.assertTrue(selectedStoreType.contains("SMB"), "selectedStoreType");
			
			driver.get(testData.B2C.getHomePageUrl());
			String url = driver.getCurrentUrl();
			Assert.assertTrue(url.contains("toggle=GatekeeperSMB"), "smb url");

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	private String changeStoreType(String storeType, boolean isPopUP) {
		Select storeTypeSel = new Select(hmcPage.B2CUnit_storeType);
		storeTypeSel.selectByVisibleText(storeType);
		hmcPage.HMC_Save.click();
		Common.sleep(5000);
		if (isPopUP) {
			boolean isPopUPActual = Common.checkElementDisplays(driver, By.id("y_popupmessage_ok_label"), 10);
			Assert.assertEquals(isPopUPActual, isPopUP, "isPopUPActual");
			String errorMsg = hmcPage.B2CUnit_popupMessageText.getText();
			Dailylog.logInfoDB(0, errorMsg, Store, testName);
			Assert.assertTrue(errorMsg.toLowerCase().contains("smb"), "errorMsg");
			hmcPage.B2CUnit_popupMessageOK.click();
		}
		Common.waitElementClickable(driver, hmcPage.Template_reloadBtn, 3);
		hmcPage.Template_reloadBtn.click();
		if (Common.isAlertPresent(driver))
			driver.switchTo().alert().accept();
		String selectedStoreType = hmcPage.B2CUnit_selectedStoreType.getText();
		return selectedStoreType;
	}

}
