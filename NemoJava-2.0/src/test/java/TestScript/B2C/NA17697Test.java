package TestScript.B2C;

import java.net.MalformedURLException;

import junit.framework.Assert;

//import org.apache.commons.io.filefilter.TrueFileFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA17697Test extends SuperTestClass {
	private HMCPage hmcPage;
	private static String couponName = "CouponDiscount" +Common.getDateTimeString();
	private String Country;
	private String partNum;
	public NA17697Test(String store, String country) {
		this.Store = store;
		this.testName = "NA-17697";
		this.Country = country;
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"shopgroup", "email", "p2", "b2c"})
	public void NA17697(ITestContext ctx) throws Exception {
		try{
			partNum = testData.B2C.getDefaultMTMPN();
			String useLimit = "//input[@placeholder='Add limit...']";
			String disableStartCronJobCheckBoxVerification = "//table[contains(@title,'active - Scheduled task')]//input[@value='false']";
			String generateURL = "urlCouponId";
			String catalogVersion = "Nemo Master Multi Country Product Catalog - Online";
			String couponValue = "15";
			String jobName = "nemo" + Store + "-singleCouponJob";
			

			this.prepareTest();
			hmcPage = new HMCPage(driver);
			String hmcURL = testData.HMC.getHomePageUrl();
			//================= Step:1 Login To HMC =================//
			driver.get(hmcURL);
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(1, "Successfully Logged into HMC", Store, testName);
			//================= Step:2 Navigate to B2C Price Rule =================//
			hmcPage.Home_PriceSettings.click();
			hmcPage.Home_PricingCockpit.click();
			driver.switchTo().frame(hmcPage.PricingCockpit_iframe);
			hmcPage.PricingCockpit_B2CPriceRules.click();
			Dailylog.logInfoDB(2, "B2C Price Rules is clicked.", Store, testName);
			Common.sleep(4000);

			//================= Step:3 Create a New Price Rule "ECoupon Discount" =================//
			hmcPage.B2CPriceRules_CreateNewGroup.click();
			Dailylog.logInfoDB(3, "Create New Group is clicked.", Store, testName);
			hmcPage.B2CPriceRules_SelectGroupType.click();
			hmcPage.B2CPriceRules_eCouponDiscountOption.click();
			hmcPage.B2CPriceRules_Continue.click();
			Dailylog.logInfoDB(3, "Continue is clicked after selecting eCoupon discount as coupon type.", Store, testName);
			hmcPage.B2CPriceRules_couponTitle.sendKeys(couponName);
			hmcPage.B2CPriceRules_description.sendKeys(couponName);
			Common.sleep(1500);
			hmcPage.B2CPriceRules_TypeSingleCoupon.click();
			Common.sleep(500);
			hmcPage.B2CPriceRules_count.sendKeys("1");
			if(Common.isElementExist(driver,By.xpath(useLimit))){
				driver.findElement(By.xpath(useLimit)).sendKeys("5");
			}
			if(Common.isElementExist(driver,By.xpath(generateURL))){
				driver.findElement(By.xpath(generateURL)).click();
			}
			Common.sleep(2000);
			//Function to handle country
			Common.scrollToElement(driver, hmcPage.B2CpriceSimulate_CountryButton);
			Common.sleep(1500);
			String xpathCountry = "//span[text()='" + Country + "' and @class='select2-match']/../../*[not(text())]";
			fillRuleInfo(driver, hmcPage, Country, hmcPage.B2CPriceSimulator_country, xpathCountry);
			//HMCCommon.fillB2CPriceRuleDetails(driver, hmcPage.B2CpriceSimulate_CountryButton, hmcPage.B2BPriceRules_SearchInput, Country, 2000);
			Common.sleep(1500);
			//Function to handle catalog version
			HMCCommon.fillB2CPriceRuleDetails(driver, hmcPage.B2CPriceRules_catalogVersionDD, hmcPage.B2BPriceRules_SearchInput,catalogVersion, 2000);
			Common.sleep(1500);
			//Function to handle material
			Common.scrollToElement(driver, hmcPage.B2CPriceRules_MaterialSelect);

			HMCCommon.fillB2CPriceRuleDetails(driver, hmcPage.B2CPriceRules_MaterialSelect, hmcPage.B2BPriceRules_SearchInput,partNum, 2000);
			Common.sleep(3000);
			//----- Selecting Dynamic Rate ------//
			Common.scrollToElement(driver, hmcPage.B2CPriceRules_dynamicRate);
			hmcPage.B2CPriceRules_dynamicRate.click();
			Common.sleep(1000);
			Dailylog.logInfoDB(3, "Dynamic rate is clicked.", Store, testName);
			hmcPage.B2CPriceRules_dynamicValue.sendKeys(couponValue);
			//----- Add to Group button is clicked ----//
			hmcPage.B2CPriceRules_addToGroupButton.click();
			Common.sleep(1000);

			//======================= Step:4 Saving the New Price Rule ==============================//
			Common.scrollToElement(driver, hmcPage.B2CPriceRules_createNewGroupButton);
			hmcPage.B2CPriceRules_createNewGroupButton.click();			
			Dailylog.logInfoDB(4, "Create New Coupon button is clicked.", Store, testName);
			Common.sleep(5000);
			String couponName = hmcPage.B2CPriceRules_couponName.getText();
			//---- Validating coupon Name after successful creation ----//
			Assert.assertTrue(couponName.contains("CouponDiscount"));
			Dailylog.logInfoDB(4, "Coupon Name is: " + couponName, Store, testName);
			driver.switchTo().defaultContent();

			//================= Step:5 Navigating to System > CronJob > singleCouponJob =============//
			hmcPage.Home_System.click();
			hmcPage.Home_cronJob.click();
			Dailylog.logInfoDB(5, "Cronjob is clicked.", Store, testName);
			hmcPage.CronJob_selectCronJob.click();
			hmcPage.selectCronJob_SingleCoupon.click();
			Common.sendFieldValue(hmcPage.CronJob_jobName, jobName);
			hmcPage.CronJob_searchButton.click();

			//==================== Step:6 Selecting the Job From Search Results =====================//
			WebElement crobJob = driver.findElement(By.xpath("//div/div[contains(.,'" + jobName + "')]"));
			Common.doubleClick(driver, crobJob);

			if(Common.isElementExist(driver, By.xpath(disableStartCronJobCheckBoxVerification))){
				hmcPage.CronJob_enableStartCronJobCheckBox.click();
				Dailylog.logInfoDB(6, "Enabled cronjob", Store, testName);
			}else{
				Dailylog.logInfoDB(6, "Start Cron Job is already Enabled.", Store, testName);
			}
			Thread.sleep(4000);
			//---- Selecting Run As Tab -----//
			hmcPage.SingleCouponCronJob_RunAsTab.click();
			//---- Entering values in Process Server Node Field ----//
			hmcPage.SingleCouponCronJob_ServerNode.clear();
			
			hmcPage.SingleCouponCronJob_ServerNode.sendKeys("2");
			hmcPage.SaveButton.click();
			//hmcPage.HMC_Save.click();
			Common.sleep(3000);
			//---- Start CronJob ----//
			hmcPage.CronJobs_StartConronJobs.click();
			Common.sleep(3000);
			Dailylog.logInfoDB(6, "Start Cron Job is clicked", Store, testName);

			String  Currenthandle= driver.getWindowHandle();
			for (String handle : driver.getWindowHandles()) {
				driver.switchTo().window(handle);
			}
			String cronJobSuccessMsg = hmcPage.CronJob_cronJobSuccessMsg.getText();
			Assert.assertTrue(cronJobSuccessMsg.equalsIgnoreCase("CronJob performed."));
			driver.close();
			driver.switchTo().window(Currenthandle);
			//---- Checking Job Status ----//
			hmcPage.CronJob_taskTab.click();
			Common.sleep(3000);
			hmcPage.CronJob_reloadButton.click();

			driver.switchTo().alert().accept();
			String cronJobStatus = hmcPage.CronJob_status.getText();
			Dailylog.logInfoDB(6, "CronJob status is: " + cronJobStatus , Store, testName);
			Assert.assertTrue(cronJobStatus.contains("FINISHED"));
			Common.sleep(3000);

			//=========== Step:7 Naviagting to Multimedia > Media > Folder ("singlecoupons") ========//
			hmcPage.Multimedia_menu.click();
			hmcPage.Multimedia_media.click();
			Dailylog.logInfoDB(7, "Multimedia > Media is clicked" , Store, testName);
			Common.sleep(1500);
			hmcPage.Multimedia_clickSelectFolder.click();
			Common.sleep(500);
			hmcPage.Multimedia_selectFolder.click();
			Dailylog.logInfoDB(7, "SingleCoupon folder is selected" , Store, testName);
			Common.sleep(2000);
			Common.sendFieldValue(hmcPage.Multimedia_Media_Identifier, couponName);
			hmcPage.CronJob_searchButton.click();
			Common.sleep(2000);
			hmcPage.Multimedia_SortIdentifier.click();
			String Multimedia_singleCouponName = "//div[contains(@id,'Content/StringDisplay[20')]/div[contains(@id,'"+couponName+"')]";
			System.out.println();
			if(!Common.isElementExist(driver, By.xpath(Multimedia_singleCouponName), 10)){
				Assert.fail("Cannot find Ecoupon On medias");
			}
			//String name_SingleCoupon = driver.findElement(By.xpath(Multimedia_singleCouponName + "[1]")).getText();
			//Assert.assertTrue(name_SingleCoupon.contains(couponName));
			Common.sleep(3000);
			hmcPage.Home_EndSessionLink.click();
			Dailylog.logInfoDB(7, "Logged out from HMC", Store, testName);
			Common.sleep(3000);
		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}
	@Test(alwaysRun = true, priority = 1, groups = { "shopgroup", "pricingpromot", "p2", "b2c" })
	public void RollBack() throws MalformedURLException, InterruptedException{
		System.out.println("Roll Back Code");
		SetupBrowser();
		hmcPage = new HMCPage(driver);
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_PriceSettings.click();
		hmcPage.Home_PricingCockpit.click();
		driver.switchTo().frame(hmcPage.PricingCockpit_iframe);
		hmcPage.PricingCockpit_B2CPriceRules.click();
		Common.sleep(3000);
		WebElement deleteSelectedCoupon = driver.findElement(By.xpath("//td[contains(.,'" + couponName + "')]/..//a[contains(@class,'deleteGroup')]"));
		//Common.scrollToElement(driver, deleteSelectedCoupon);
		Common.javascriptClick(driver, deleteSelectedCoupon);
		//deleteSelectedCoupon.click();
		Common.sleep(1000);
		hmcPage.B2CPriceRules_deleteConfirmationTxtBox.sendKeys("DELETE");
		Common.sleep(500);
		hmcPage.B2CPriceRules_confirmButtonOnConfPopUp.click();
		Dailylog.logInfoDB(8, "Coupon is deleted", Store, testName);
		driver.switchTo().defaultContent();
		Common.sleep(2000);
		hmcPage.Home_EndSessionLink.click();
		Common.sleep(3000);
	}

	public void fillRuleInfo(WebDriver driver, HMCPage hmcPage,  String dataInput, WebElement ele1,
			String xpath) throws InterruptedException {
		WebElement target;
		Common.waitElementClickable(driver, ele1, 30);
		Thread.sleep(1000);
		ele1.click();
		Common.waitElementVisible(driver, hmcPage.B2CPriceRules_SearchInput);
		hmcPage.B2CPriceRules_SearchInput.clear();
		hmcPage.B2CPriceRules_SearchInput.sendKeys(dataInput);
		target = driver.findElement(By.xpath(xpath));
		Common.waitElementVisible(driver, target);
		target.click();
		Thread.sleep(5000);
	}

}
