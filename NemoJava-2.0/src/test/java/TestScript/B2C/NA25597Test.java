package TestScript.B2C;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;



public class NA25597Test extends SuperTestClass {
	
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public MailPage mailPage;
	
	public String homePageUrl;
	public String loginUrl;
	public String cartUrl;
	
	public String hmcLoginUrl;
	public String hmcHomePageUrl;

	public NA25597Test(String store) {
		this.Store = store;
		this.testName = "NA-25597";
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2c"})
	public void NA25597(ITestContext ctx) {
		try {
			this.prepareTest();
			
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			mailPage = new MailPage(driver);
			
			homePageUrl = testData.B2C.getTeleSalesUrl();
			loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			
			hmcLoginUrl = testData.HMC.getHomePageUrl();
			hmcHomePageUrl = testData.HMC.getHomePageUrl();
			
			//3, Enable shown approver list on quote popups at B2CUNIT level.
			//HMC --> B2C eCommerce --> B2C Unit --> Site Attribute
			
			Dailylog.logInfoDB(3, "Under site Attribute tab , set the show quote approver for tele users : yes", Store, testName);
			
			driver.get(hmcHomePageUrl);
			HMCCommon.Login(hmcPage, testData);
			
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			Common.sleep(100000);
			
			if(!driver.findElement(By.xpath("//input[contains(@id,'B2CUnit.zTeleUserByGeo') and contains(@id,'true')]")).isSelected()){
				driver.findElement(By.xpath("//input[contains(@id,'B2CUnit.zTeleUserByGeo') and contains(@id,'true')]")).click();
			}
			
			driver.findElement(By.xpath("//div[contains(@id,'organizer.editor.save.title')]")).click();
			
			Assert.assertTrue(driver.findElement(By.xpath("//input[contains(@id,'B2CUnit.zTeleUserByGeo') and contains(@id,'true')]")).isSelected());
			
			hmcPage.Home_EndSessionLink.click();
			
			//4, Login web as telesales user.
			Dailylog.logInfoDB(4, "Login web as telesales user.", Store, testName);
			
			driver.get(homePageUrl);
			B2CCommon.handleTeleGateKeeper(b2cPage, testData);
			driver.get(loginUrl);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			
			//5, Clicking Start Assisted Service Session line 
			Dailylog.logInfoDB(5, "Clicking Start Assisted Service Session line ", Store, testName);
			
			b2cPage.myAccountPage_startAssistedServiceSession.click();
			
			//6, Assign customer into ASM, then start session. Add product into cart.
			Dailylog.logInfoDB(6, "Assign customer into ASM, then start session. Add product into cart.", Store, testName);
			
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath(".//*[@id='customerFilter']")));
			b2cPage.ASM_SearchCustomer.sendKeys(testData.B2C.getLoginID());
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.cssSelector("[id^='ui-id-']>a")));
			b2cPage.ASM_CustomerResult.click();
			b2cPage.ASM_StartSession.click();
			
			Thread.sleep(5000);
			driver.get(cartUrl);
			
			B2CCommon.emptyCart(driver, b2cPage);
			
			b2cPage.Cart_QuickOrderTextBox.sendKeys(testData.B2C.getDefaultMTMPN());
			b2cPage.Cart_AddButton.click();
			
			Assert.assertTrue(driver.findElement(By.xpath("//input[@class='cartDetails-overriddenPrice' and @name = 'price']")).isDisplayed());

			//7, Conduct price override.
			Dailylog.logInfoDB(7, "Conduct price override.", Store, testName);
			
			// Override
			Thread.sleep(4000);
			float Price = GetPriceValue(b2cPage.cartInfo_subTotalAftAnnProd.getText().toString());
			Price = Price - 1;
			b2cPage.OverrideValue.sendKeys(Price + "");
			b2cPage.OverrideDropdown.click();
			b2cPage.OverrideCheckbox.sendKeys("xxxxx");
			b2cPage.OverrideUpdate.click();
			System.out.println("Override performs successfully");
			Thread.sleep(5000);
			
			float Price1 = GetPriceValue(b2cPage.cartInfo_subTotalAftAnnProd.getText().toString());
			
			Assert.assertEquals(Price, Price1, 0.001);;
			
			//8,Click request quote btn
			Dailylog.logInfoDB(8, "Click request quote btn", Store, testName);
			
			b2cPage.Override_RequestQuote.click();
			Thread.sleep(6000);
			b2cPage.Override_RepID.clear();
			Thread.sleep(4000);
			b2cPage.Override_RepID.sendKeys(testData.B2C.getRepID());
			Thread.sleep(6000);
			
			//9, Quote creation popup shown with approver list.
			
			Dailylog.logInfoDB(9, "Quote creation popup shown with approver list.", Store, testName);
			
			String approver = Common.javaScriptGetText(driver, driver.findElement(By.xpath("(//select[@id='quoteApprover']/option)[2]")));
			//MarkHadley(mhadley1) 
			int start = approver.indexOf("(") + 1;
			int end = approver.length() - 1;
			String approverName = approver.substring(start, end);
			Dailylog.logInfoDB(9, "the approver Name need in hmc is :" + approverName, Store, testName);
			
			String hmcUrl = testData.HMC.getHomePageUrl();
			
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.open('"+ hmcUrl +"')");
			
			Common.switchToWindow(driver, 1);
			driver.manage().window().maximize();
			
			HMCCommon.Login(hmcPage, testData);
			
			//2, HMC --> User --> Customers --> Administration -->  	
			//Telesales user email:
			//Enter an accessible E-mail address and save
			//Dailylog.logInfoDB(2, "set Telesales user email", Store, testName);
			
		
	
			hmcPage.home_userTab.click();
			hmcPage.userTab_customerTab.click();
			hmcPage.customer_customerIDTextBox.clear();
			hmcPage.customer_customerIDTextBox.sendKeys(approverName);
			
			hmcPage.customer_customerSearchButtonn.click();
			hmcPage.customer_searchedResultImage.click();
			hmcPage.customerSearch_administrationTab.click();
			String setEmail = "gaopan"  + "25597@sharklasers.com";
			if(!driver.findElement(By.xpath("//input[contains(@id,'Customer.zTeleUserEmail')]")).getAttribute("value").equals(setEmail)){
				driver.findElement(By.xpath("//input[contains(@id,'Customer.zTeleUserEmail')]")).clear();
				driver.findElement(By.xpath("//input[contains(@id,'Customer.zTeleUserEmail')]")).sendKeys(setEmail);
				driver.findElement(By.xpath("//div[contains(@id,'organizer.editor.save.title')]")).click();
				Thread.sleep(10000);
				Dailylog.logInfoDB(2, "set Telesales user email", Store, testName);
			}
			driver.close();
			
			Common.switchToWindow(driver, 0);
			
			//10, Clicking YES btn.
			Dailylog.logInfoDB(10, "Clicking YES btn.", Store, testName);
			
			Select select = new Select(driver.findElement(By.xpath("//select[@id='quoteApprover']")));
			select.selectByValue(approverName);
		
			b2cPage.Override_SubmitQuote.click();
			Thread.sleep(6000);
			String QuoteNo = b2cPage.Override_QuoteNumber.getText().toString();
			Dailylog.logInfo(Store + " quote number is " + QuoteNo);

			EMailCommon.goToMailHomepage(driver);
			mailPage.Inbox_EditButton.click();
			Thread.sleep(3000);
			mailPage.Inbox_InputEmail.clear();
			mailPage.Inbox_InputEmail.sendKeys(setEmail.split("@")[0]);
			mailPage.Inbox_SetButton.click();
	
			String mailSubject = QuoteNo;
			
			boolean b = EMailCommon.checkIfEmailReceived(driver, mailPage, mailSubject);
			
			Assert.assertTrue(b);
	
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	
	
	public float GetPriceValue(String Price) {
		Price = Price.replaceAll("\\$", "").replaceAll("HK", "").replaceAll("SG", "").replace("£", "")
				.replace("€", "").replaceAll("￥", "").replaceAll("NT", "")
				.replaceAll("₩", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.trim();
		float priceValue;
		priceValue = Float.parseFloat(Price);
		return priceValue;
	}
	
	
	
}
	
	