package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class NA25599Test extends SuperTestClass {
	public NA25599Test(String store) {
		this.Store = store;
		this.testName = "NA-25599";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "account", "p2", "b2c"})
	public void NA25599(ITestContext ctx) {
		try {
			this.prepareTest();
			driver.get(testData.B2C.getHomePageUrl());

			B2CPage b2cPage = new B2CPage(driver);
			b2cPage.SMB_LoginButton.click();
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());

			String level = "";
			try {
				b2cPage.SMB_CurrentPriceLevelArrow.click();

				level = b2cPage.SMB_CurrentPriceLevel.getText().split("\\:")[1].trim();
			} catch (Exception e) {
				// In case binded company doesn't have level
				level = "Pro";
			}
			System.out.println("Level is: " + level);
			
			HMCPage hmcPage = new HMCPage(driver);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			hmcPage.B2CUnit_SMBToggleYes.click();
			hmcPage.Common_SaveButton.click();
			Thread.sleep(10000);
			
			hmcPage.Home_PriceSettings.click();
			HMCCommon.loginPricingCockpit(driver,hmcPage, testData);
			hmcPage.PricingCockpit_PricingTierTab.click();
			Thread.sleep(5000);

			// Remove binded company from current group
			List<WebElement> rows;
			int currentIndex = 1;
			if (!level.equals("")) {
				rows = driver.findElements(By.className("custom-table"));
				for (int i = 1; i < rows.size(); i++) {
					if (rows.get(i).findElement(By.xpath(".//td[2]")).getText().trim().equals(level)) {
						currentIndex = i;
						Common.javascriptClick(driver, rows.get(i).findElement(By.xpath(".//a[contains(@class,'editTier')]")));

						List<WebElement> companies = driver.findElements(By.xpath("//li[@class='select2-search-choice']"));
						if(companies.size() == 0)
						{
							Common.javascriptClick(driver, hmcPage.PricingCockpit_UpdateTierButton);
							Thread.sleep(3000);
							break;
						}
						for (int j = 0; j < companies.size(); j++) {
							if (companies.get(j).getText().contains("otherSmbCustomerGroup")) {
								Common.javascriptClick(driver, companies.get(j).findElement(By.className("select2-search-choice-close")));
								Common.javascriptClick(driver, hmcPage.PricingCockpit_UpdateTierButton);
								Thread.sleep(3000);
								System.out.println("Removed from origin group!");
								break;
							}
						}

						break;
					}
				}
			}

			rows = driver.findElements(By.className("custom-table"));
			if (currentIndex == rows.size() - 1) {
				Common.javascriptClick(driver, rows.get(rows.size() - 2).findElement(By.xpath(".//a[contains(@class,'editTier')]")));
			} else {
				Common.javascriptClick(driver, rows.get(rows.size() - 1).findElement(By.xpath(".//a[contains(@class,'editTier')]")));
			}

			System.out.println("Now in new group!");
			Thread.sleep(3000);
			driver.findElement(By.xpath("//ul[@class='select2-choices']//input[contains(@class, 'select2-input')]"))
					.sendKeys("otherSmbCustomerGroup" + Keys.TAB);

			driver.findElement(By.xpath("//div[contains(@class, 'modal-dialog')]//input[@name='zName']")).clear();
			String newLevel = Common.getDateTimeString();
			driver.findElement(By.xpath("//div[contains(@class, 'modal-dialog')]//input[@name='zName']"))
					.sendKeys(newLevel);

			driver.findElement(By.xpath("//div[contains(@class, 'modal-dialog')]//input[@name='zDescription']"))
					.clear();
			String newSaving = Common.getDateTimeString();
			driver.findElement(By.xpath("//div[contains(@class, 'modal-dialog')]//input[@name='zDescription']"))
					.sendKeys(newSaving);

			hmcPage.PricingCockpit_UpdateTierButton.click();
			Thread.sleep(5000);
			
			driver.get(testData.B2C.getHomePageUrl());
			b2cPage.SMB_CurrentPriceLevelArrow.click();

			if (!newLevel.equals(b2cPage.SMB_CurrentPriceLevel.getText().split("\\:")[1].trim())
					|| !newSaving.equals(b2cPage.SMB_CurrentPriceSaving.getText()))
				Assert.fail("Updated level and description is not shown on UI");

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
}
