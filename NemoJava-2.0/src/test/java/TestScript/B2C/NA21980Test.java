package TestScript.B2C;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;
public class NA21980Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public MailPage mailPage;
	private String quoteNo;	
	
	String cartUrl;
	String UserName;
	String UserEmailHeader;
	String ProductNo;
	public NA21980Test(String store) {
		this.Store = store;
		this.testName = "NA-21980";
	}	

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "email", "p2", "b2c"})
	public void NA21980(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			mailPage = new MailPage(driver);

			UserName = testData.B2C.getLoginID();			
			if(Store.equals("US_BPCTO")){
				UserName = "testus@sharklasers.com";
			}
			
			ProductNo = testData.B2C.getDefaultMTMPN();
			
			UserEmailHeader = UserName.split("@")[0];

			driver.manage().deleteAllCookies();
			driver.get(testData.B2C.getTeleSalesUrl() + "/login");
			Common.sleep(2500);
			
			B2CCommon.closeHomePagePopUP(driver);
			
			B2CCommon.handleGateKeeper(b2cPage, testData);
			// ASM Login
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(),
					testData.B2C.getTelesalesPassword());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Dailylog.logInfoDB(3,"Logged in into B2C as Telesales User",Store,testName);
			// Start Session
			StartSession();
			Common.sleep(2000);
			// Adding a product to cart
			
			cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			
			driver.get(cartUrl);
			
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			Common.sleep(3000);
			// step~4 : Add a product			
			Dailylog.logInfoDB(4,"Added 1 product using Quick order",Store,testName);
			
			b2cPage.Cart_QuickOrderTextBox.clear();
			b2cPage.Cart_QuickOrderTextBox.sendKeys(ProductNo);
			Common.sleep(1000);
			b2cPage.Cart_AddButton.click();

			float Price = GetPriceValue(b2cPage.cartInfo_subTotalAftAnnProd.getText().toString());
			String newPrice = String.valueOf(Price - 1);
			b2cPage.OverrideValue.sendKeys(newPrice);
			b2cPage.OverrideDropdown.click();
			b2cPage.OverrideCheckbox.sendKeys("xxxxx");
			b2cPage.OverrideUpdate.click();
			
			// step~5 : Request quote
			Dailylog.logInfoDB(5, " Request quote", Store, testName);
			
			Common.scrollToElement(driver, b2cPage.RequestQuoteBtn);
			RequestQuote(true);
			Common.sleep(10000);
			
			driver.findElement(By.xpath("//span[contains(@class,'ASM_end_session')]")).click();
			driver.switchTo().alert().accept();
			Common.sleep(6000);
			b2cPage.Tele_TransactionSearch.click();
			b2cPage.Tele_TransactionSearch_TransactionId.sendKeys(quoteNo);
			b2cPage.Tele_TransactionSearch_Search.click();
			Common.sleep(3000);
			Common.javascriptClick(driver, driver.findElement(By.xpath("//tbody[@id='advTransactionSearchTable']/tr[1]"))); 
			Common.sleep(5000);
			
			b2cPage.StartSessionButton.click();
			Common.scrollToElement(driver, b2cPage.ASM_ApproveButton);
			b2cPage.ASM_ApproveButton.click();
			b2cPage.ASM_ApproveComment.sendKeys("Approved by ..");
			b2cPage.ASM_PopupApprove.click();
			
			// step~6 : Check quote confirmation email
			
			Dailylog.logInfoDB(6,"Check quote confirmation email",Store,testName);
			
			driver.manage().deleteAllCookies();
			EMailCommon.createEmail(driver,mailPage,UserEmailHeader);
			String emailTitle ="Quote Confirmation";
			switch(Store){
			case "JP":
				emailTitle = "見積";
				break;
			case "AU":
				emailTitle = "Quote Confirmation";
				break;
			case "USEPP":
			case "US_BPCTO":
			case "US_OUTLET":
			case "US":
			case "CA_AFFINITY":
				emailTitle = "Lenovo quote";
				break;
			}
			boolean flag = EMailCommon.checkIfEmailReceived(driver, mailPage, emailTitle);
			if(flag==false){
				setManualValidateLog("Need Manual Validate in email "+ UserName +", and check email: "+ quoteNo);		
			}
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public void StartSession(){
		
		Dailylog.logInfoDB(3,"clicked on Assisted service station",Store,testName);
		
		Common.javascriptClick(driver, b2cPage.MyAccount_myAccount);
		
		b2cPage.myAccountPage_startAssistedServiceSession.click();
		Common.sleep(2000);
		
		new WebDriverWait(driver, 500).until(ExpectedConditions
				.presenceOfElementLocated(By
						.xpath(".//*[@id='customerFilter']")));
		b2cPage.ASM_SearchCustomer.sendKeys(testData.B2C.getLoginID());
		new WebDriverWait(driver, 500).until(ExpectedConditions
				.presenceOfElementLocated(By
						.cssSelector("[id^='ui-id-']>a")));
		b2cPage.ASM_CustomerResult.click();
		
		
		Common.sleep(5000);

		b2cPage.ASM_StartSession.click();
	}
	

	private void RequestQuote(boolean status){
		b2cPage.RequestQuoteBtn.click();
		Dailylog.logInfoDB(5,"Request Quote is clicked",Store,testName);
		Common.sleep(4000);
		if(Common.checkElementDisplays(driver, b2cPage.Quote_createOneTimeQuote, 10)){
			b2cPage.Quote_createOneTimeQuote.click();
			Common.sleep(3000);
			b2cPage.Quote_contactEmail.sendKeys(UserName);
		}
		b2cPage.Quote_RepID.clear();
		b2cPage.Quote_RepID.sendKeys(testData.B2C.getRepID());
		Common.sleep(2000);
		b2cPage.Quote_SubmitQuoteBtn.click();
		Common.sleep(3500);
		String url = driver.getCurrentUrl();
		Assert.assertTrue(url.contains("submitquote"));
		Common.sleep(2000);
		quoteNo = b2cPage.QuoteConfirmPage_QuoteNo.getText();
		Dailylog.logInfoDB(5,"Quote number = " + quoteNo,Store,testName);
		Common.sleep(2000);
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
