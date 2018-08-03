package TestScript.B2B;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class CONTENT409Test extends SuperTestClass {
	private B2BPage b2bPage;
	private HMCPage hmcPage;

	public CONTENT409Test(String store) {
		this.Store = store;
		this.testName = "CONTENT-409";
	}

	@Test(alwaysRun = true, groups = { "contentgroup", "storemgmt", "p2", "b2b" })
	public void CONTENT409(ITestContext ctx) {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);

			// 1-2.Go to hmc->B2B commerce->B2Bunit,right click,create B2B unit
			createB2BUnit();
			Dailylog.logInfoDB(1, "2Go to hmc->B2B commerce->B2Bunit,right click,create B2B unit", Store, testName);

			// 3-4.Go to hmc->B2B commerce->B2Bunit,enter B2B unit,click search button
			inputB2BUnit("1213348423", hmcPage.zB2BShowSubscriptionTrue, 3);
			Dailylog.logInfoDB(3, "4Go to hmc->B2B commerce->B2Bunit,right click,create B2B unit", Store, testName);

			// 5-6.Go to hmc->B2B commerce->B2Bunit,enter B2B unit,click search button
			inputB2BUnit("1213577815", hmcPage.zB2BShowSubscriptionNull, 3);
			Dailylog.logInfoDB(5, "6Go to hmc->B2B commerce->B2Bunit,right click,create B2B unit", Store, testName);

			// 7.Go to step 6,modify the value of Show Subscription and uncheck Blank-out
			// Inheritance,then click save button
			inputB2BUnit("1213577815", hmcPage.zB2BShowSubscriptionNull, 7);
			Dailylog.logInfoDB(7,
					"6Go to step 6,modify the value of   Show Subscription and uncheck Blank-out Inheritance,then click save button",
					Store, testName);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private void inputB2BUnit(String unit, WebElement zB2BShowSubscriptionTrue, int number) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BUnitLink.click();
		hmcPage.B2BUnit_SearchIDTextBox.sendKeys(unit);
		hmcPage.B2BUnit_SearchButton.click();
		Common.doubleClick(driver, hmcPage.B2BUnit_ResultItem);
		hmcPage.B2CUnit_SiteAttributeTab.click();
		Common.scrollToElement(driver, hmcPage.showSubscription);
		zB2BShowSubscriptionTrue.click();
		if(number==3) {
			if (!hmcPage.zB2BShowSubscriptionType.isSelected()) {
				hmcPage.zB2BShowSubscriptionCheckbox.click();
			}
			hmcPage.PaymentLeasing_saveAndCreate.click();
			Common.scrollToElement(driver, hmcPage.showSubscription);
			Assert.assertEquals(hmcPage.hidden.getAttribute("value"), "true");
			hmcPage.HMC_Logout.click();
		}
		if(number==7) {
			if (hmcPage.zB2BShowSubscriptionType.isSelected()) {
				hmcPage.zB2BShowSubscriptionCheckbox.click();
			}
			hmcPage.PaymentLeasing_saveAndCreate.click();
			Common.scrollToElement(driver, hmcPage.showSubscription);
			Assert.assertTrue(Common.checkElementExists(driver, hmcPage.zB2BShowSubscriptionText, 15));
		}

		
	}

	private void createB2BUnit() throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BUnitLink.click();
		Common.rightClick(driver, hmcPage.Home_B2BUnitLink);
		Common.mouseHover(driver,  hmcPage.create);
		hmcPage.create_B2BUnit.click();
		hmcPage.B2CUnit_SiteAttributeTab.click();
		Common.scrollToElement(driver, hmcPage.showSubscription);
		Assert.assertEquals(hmcPage.zB2BShowSubscription.getAttribute("value"), "false");
		hmcPage.HMC_Logout.click();
	}

}
