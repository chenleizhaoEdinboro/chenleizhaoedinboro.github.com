package TestScript.B2C;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA16394Test extends SuperTestClass {
	public HMCPage hmcPage;
	public B2CPage b2cPage;
	String childUnit;
	String parentUnit;
	String storeID;

	public NA16394Test(String store, String childUnit, String parentUnit, String storeID) {
		this.Store = store;
		this.testName = "NA-16394";
		this.childUnit = childUnit;
		this.parentUnit = parentUnit;
		this.storeID = storeID;
	}

	@Test(alwaysRun = true,groups= {"contentgroup","storemgmt","p2","b2c"})
	public void NA16394(ITestContext ctx) {
		try {
			this.prepareTest();

			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);

			List<String> passcodes = new ArrayList<String>();

			// sharelenovo, remove all, no
			goTositeAttributes(parentUnit);
			setToggle("no");
			removeAll();
			hmcPage.hmcHome_hmcSignOut.click();
			Dailylog.logInfoDB(0, parentUnit + ", remove all, no", Store, testName);

			// aupremium_unit, remove all, na
			goTositeAttributes(childUnit);
			setToggle("na");
			removeAll();
			hmcPage.hmcHome_hmcSignOut.click();
			Dailylog.logInfoDB(0, childUnit + ", remove all, na", Store, testName);

			// sharelenovo, 4444, 5555, yes
			passcodes.add("4444");
			passcodes.add("5555");
			goTositeAttributes(parentUnit);
			setToggle("yes");
			addPasscode(passcodes);
			hmcPage.hmcHome_hmcSignOut.click();
			Dailylog.logInfoDB(0, parentUnit + ", 4444, 5555, yes", Store, testName);

			// upremium_unit, check Toggle and value
			goTositeAttributes(childUnit);
			String toggleInheritedInfo = hmcPage.PasscodeGatekeeper_toggleInheritedInfo.getText();
			Dailylog.logInfoDB(0, "toggleInheritedInfo: " + toggleInheritedInfo, Store, testName);
			Assert.assertEquals(toggleInheritedInfo, "It is inherited from \"" + parentUnit + "\"", "toggleInheritedInfo");
			String tableInheritedInfo = hmcPage.PasscodeGatekeeper_tableInheritedInfo.getText();
			Dailylog.logInfoDB(0, "tableInheritedInfo: " + tableInheritedInfo, Store, testName);
			Assert.assertEquals(tableInheritedInfo, "It is inherited from \"" + parentUnit + "\"", "tableInheritedInfo");
			boolean isYesSelected = hmcPage.B2CUnit_passcodeGatekeeperYes.isSelected();
			Dailylog.logInfoDB(0, "isYesSelected: " + isYesSelected, Store, testName);
			Assert.assertEquals(isYesSelected, true, "isYesSelected");
			List<String> actualPasscodes = new ArrayList<String>();
			for (int i = 0; i < hmcPage.PasscodeGatekeeper_oldPasscode.size(); i++) {
				String passcode = hmcPage.PasscodeGatekeeper_oldPasscode.get(i).getAttribute("value");
				Dailylog.logInfoDB(0, "passcode: " + passcode, Store, testName);
				actualPasscodes.add(passcode);
			}
			Assert.assertEquals(actualPasscodes.size(), passcodes.size(), "actualPasscodes.size()");
			for (int i = 0; i < hmcPage.PasscodeGatekeeper_oldPasscode.size(); i++)
				Assert.assertEquals(actualPasscodes.get(i), passcodes.get(i), "actualPasscodes");
			hmcPage.hmcHome_hmcSignOut.click();
			Dailylog.logInfoDB(0, childUnit + " check pass", Store, testName);

			// B2C, passcode works
			String testUrl = testData.envData.getHttpsDomain() + "/" + Store.toLowerCase() + "/en/" + storeID + "/";
			driver.get(testUrl);
			String currentUrl = driver.getCurrentUrl();
			Assert.assertTrue(currentUrl.contains("toggle=PasscodeGatekeeper"));
			b2cPage.PasscodeGateKeeper_PasscodeTextBox.clear();
			b2cPage.PasscodeGateKeeper_PasscodeTextBox.sendKeys(passcodes.get(1));
			b2cPage.GateKeeper_LoginButton.click();
			currentUrl = driver.getCurrentUrl();
			Assert.assertEquals(getUrl(currentUrl), getUrl(testUrl));
			Dailylog.logInfoDB(0, "B2C check pass: " + passcodes.get(1), Store, testName);

			// aupremium_unit, 7777, 8888, other
			passcodes.add("7777");
			passcodes.add("8888");
			goTositeAttributes(childUnit);
			addPasscode(passcodes);
			hmcPage.hmcHome_hmcSignOut.click();
			Dailylog.logInfoDB(0, childUnit + ", 7777, 8888, other", Store, testName);

			// B2C, passcode works
			driver.get(testUrl);
			driver.manage().deleteAllCookies();
			driver.get(testUrl);
			currentUrl = driver.getCurrentUrl();
			System.out.println(currentUrl);
			Assert.assertTrue(currentUrl.contains("toggle=PasscodeGatekeeper"));
			b2cPage.PasscodeGateKeeper_PasscodeTextBox.clear();
			b2cPage.PasscodeGateKeeper_PasscodeTextBox.sendKeys(passcodes.get(3));
			b2cPage.GateKeeper_LoginButton.click();
			currentUrl = driver.getCurrentUrl();
			Assert.assertEquals(getUrl(currentUrl), getUrl(testUrl));
			Dailylog.logInfoDB(0, "B2C check pass: " + passcodes.get(3), Store, testName);

			// sharelenovo, no
			goTositeAttributes(parentUnit);
			hmcPage.B2CUnit_passcodeGatekeeperNo.click();
			Common.sleep(8000);
			hmcPage.Common_SaveButton.click();
			hmcPage.hmcHome_hmcSignOut.click();
			Dailylog.logInfoDB(0, parentUnit + "no", Store, testName);

			// upremium_unit check
			goTositeAttributes(childUnit);
			toggleInheritedInfo = hmcPage.PasscodeGatekeeper_toggleInheritedInfo.getText();
			Dailylog.logInfoDB(0, "toggleInheritedInfo: " + toggleInheritedInfo, Store, testName);
			Assert.assertEquals(toggleInheritedInfo, "It is inherited from \"" + parentUnit + "\"", "toggleInheritedInfo");
			boolean isNoSelected = hmcPage.B2CUnit_passcodeGatekeeperNo.isSelected();
			Dailylog.logInfoDB(0, "isNoSelected: " + isNoSelected, Store, testName);
			Assert.assertEquals(isNoSelected, true, "isNoSelected");

			// B2C
			driver.get(testUrl);
			driver.manage().deleteAllCookies();
			driver.get(testUrl);
			currentUrl = driver.getCurrentUrl();
			Assert.assertEquals(getUrl(currentUrl), getUrl(testUrl));
			Dailylog.logInfoDB(0, "B2C check pass: no", Store, testName);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	private String getUrl(String url) {
		return url.replace("https://", "http://").replace("LIeCommerce:M0C0v0n3L!@", "");
	}

	private void removeAll() {
		if (!Common.checkElementDisplays(driver, hmcPage.GateKeeper_listEmptyMsg, 5)) {
			Common.rightClick(driver, hmcPage.B2CUnit_passcodeGatekeeperTable);
			Common.waitElementClickable(driver, hmcPage.GateKeeper_selectAll, 5);
			hmcPage.GateKeeper_selectAll.click();
			// to handle remove failure when page still loading
			Common.sleep(8000);
			Common.rightClick(driver, hmcPage.B2CUnit_passcodeGatekeeperTable);
			Common.waitElementClickable(driver, hmcPage.BaseStore_remove, 5);
			hmcPage.BaseStore_remove.click();
			Common.sleep(8000);
			Assert.assertTrue(Common.checkElementDisplays(driver, hmcPage.GateKeeper_listEmptyMsg, 3), "remove all -- list empty message should display");
			hmcPage.Common_SaveButton.click();
		}
	}

	private void setToggle(String isGateKeeperTurnOn) {
		hmcPage.B2CUnit_serialNumberGatekeeperNo.click();
		if (isGateKeeperTurnOn.toLowerCase().equals("no"))
			hmcPage.B2CUnit_passcodeGatekeeperNo.click();
		else if (isGateKeeperTurnOn.toLowerCase().equals("na"))
			hmcPage.B2CUnit_passcodeGatekeeperNa.click();
		else if (isGateKeeperTurnOn.toLowerCase().equals("yes")) {
			hmcPage.B2CUnit_passcodeGatekeeperYes.click();
		}
		Common.sleep(8000);
		hmcPage.Common_SaveButton.click();

	}

	private void addPasscode(List<String> passcodes) {
		for (int i = 0; i < passcodes.size(); i++) {
			By xpath = By.xpath("//table[@title='dynamicPasscodeGatekeeper']//input[@type='text'and@value='" + passcodes.get(i) + "']");
			if (!Common.checkElementDisplays(driver, xpath, 2)) {
				Common.rightClick(driver, hmcPage.B2CUnit_passcodeGatekeeperTable);
				Common.waitElementClickable(driver, hmcPage.HMC_add, 5);
				hmcPage.HMC_add.click();
				hmcPage.PasscodeGatekeeper_newPasscode.sendKeys(passcodes.get(i));
				Common.sleep(8000);
				hmcPage.Common_SaveButton.click();
			}
		}
	}

	private void goTositeAttributes(String unit) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_B2CCommercelink.click();
		hmcPage.Home_B2CUnitLink.click();
		hmcPage.B2CUnit_IDTextBox.sendKeys(unit);
		hmcPage.B2CUnit_SearchButton.click();
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		hmcPage.B2CUnit_SiteAttributeTab.click();
	}

}
