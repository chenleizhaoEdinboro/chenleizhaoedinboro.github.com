package TestScript.B2C;

import java.net.MalformedURLException;

import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.testng.Assert;

import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.EMailCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class NA17702Test extends SuperTestClass{

	public HMCPage hmcPage;
	public B2CPage b2cPage;
	//private static EMailCommon emailComm;
	public MailPage mailPage;

	public NA17702Test(String store) {
		this.Store = store;
		this.testName = "NA-17702";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"browsegroup", "email", "p2", "b2c"})
	public void NA17702(ITestContext ctx) {
		try{
			String email1 = EMailCommon.getRandomEmailAddress();
			Thread.sleep(1000);
			String email2 = "fanxx3@lenovo.com";
			Thread.sleep(1000);
			String email3 = "luhao1@lenovo.com";
			Thread.sleep(1000);
			Dailylog.logInfoDB(1, "Email 1 is: " + email1 + "\n" + "Email 2 is: " +email2 + "\n" + "Email 3 is: " +email3 , Store, testName);
			this.prepareTest();
			//emailComm = new EMailCommon();
			mailPage = new MailPage(driver);
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);

			

			//Step 1:
			Dailylog.logInfoDB(1, "Login HMC", Store, "NA-17702");
			hmcReachB2CUnit();

			//Step 2:
			Dailylog.logInfoDB(2, "Confirm the data field Email Product Toggle is Yes", Store, testName);
			switchEmailToggle(hmcPage.siteAttribute_emailProdToggleYes, "Yes");

			//Step 3:
			Dailylog.logInfoDB(3, "Open website, enter into a subseries page", Store, testName);
			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Thread.sleep(1000);
			/*b2cPage.Navigation_ProductsLink.click();
			b2cPage.Navigation_Laptop.click();*/
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Laptops);
			Thread.sleep(2000);
			//Click on the 1st Laptop series
			b2cPage.laptops_firstLaptopType.click();
			//Click on the 1st sub series product
			b2cPage.laptopSeries_firstSeriesType.click();

			//Step 4:
			Dailylog.logInfoDB(4, "Click the Email This Product link for any product", Store, testName);
			//Validating if email product link is present
			Assert.assertEquals(b2cPage.subseries_emailProductLink.isDisplayed(), true);
			//Validating if email product link if present for all of the series products
			if(b2cPage.laptopSeries_subseriesCount.size()>0){
				Assert.assertEquals(b2cPage.laptopSeries_subseriesCount.size(), b2cPage.subseries_emailProductLink1.size());
			}else{
				Assert.assertEquals(b2cPage.laptopSeries_subseriesCount1.size(), b2cPage.subseries_emailProductLink1.size());
			}
			
			b2cPage.subseries_emailProductLink.click();
			Common.sleep(5000);
			//Step 5:
			Dailylog.logInfoDB(5, "Input the information, and use comma between email addresses", Store, testName);
			b2cPage.emailProduct_yourFirstNameTxtBox.clear();
			b2cPage.emailProduct_yourFirstNameTxtBox.sendKeys(Store+"emailcartTest");
			b2cPage.emailProduct_sendToTxtBox.clear();
			b2cPage.emailProduct_sendToTxtBox.sendKeys(email1 + ',' + email2 + ',' + email3);
			b2cPage.emailProduct_sendToEmailButton.click();
			Dailylog.logInfoDB(1, "Product sent to the entered email IDs.", Store, testName);
			if(Store.equalsIgnoreCase("JP")){			
				Assert.assertEquals("E メールは次の宛先に正常に送信されました。" + email1 + ',' + email2 + ',' + email3 + ' ', b2cPage.emailProduct_emailSuccessfullySentMsgBar.getText());
			} else {
				Assert.assertEquals("Email successfully sent to " + email1 + ',' + email2 + ',' + email3 + ' ', b2cPage.emailProduct_emailSuccessfullySentMsgBar.getText());
			}

			//Step 6:
			Dailylog.logInfoDB(6, "Check your mail box", Store, testName);
			
			EMailCommon.createEmail(driver, mailPage, email1);
			EMailCommon.checkIfEmailReceived(driver, mailPage, "send you a product");
		}catch (Throwable e){
			handleThrowable(e, ctx);
		}
	}

	private void hmcReachB2CUnit(){
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_B2CCommercelink.click();
		hmcPage.Home_B2CUnitLink.click();
	}

	private void switchEmailToggle(WebElement toggleLocator, String toggleValue){
		hmcPage.paymentProfile_B2CUnitsIsocode.sendKeys(testData.B2C.getUnit());
		hmcPage.B2CUnit_SearchButton.click();
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		hmcPage.B2CUnit_SiteAttributeTab.click();
		toggleLocator.click();
		hmcPage.Types_SaveBtn.click();
		hmcPage.hmcHome_hmcSignOut.click();
	}

	//Roll back: Turn the email toggle off
	@Test(priority = 1)
	public void rollback() throws InterruptedException, MalformedURLException {
		this.SetupBrowser();
		hmcPage = new HMCPage(driver);
		hmcReachB2CUnit();
		//Set the toggle to No
		switchEmailToggle(hmcPage.siteAttribute_emailProdToggleNo, "No");
	}
}
