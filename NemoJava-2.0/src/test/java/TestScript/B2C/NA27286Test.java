package TestScript.B2C;


import org.openqa.selenium.By;
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

public class NA27286Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	MailPage mailPage;
	String productNo;
	String totalPrice;
	String defaultProductNo;
	
	public NA27286Test(String store,String defaultProductNo) {
		this.Store = store;
		this.testName = "NA-27286";
		this.defaultProductNo = defaultProductNo;
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "email", "p1", "b2c"})
	public void NA27286(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			mailPage = new MailPage(driver);
			
			Dailylog.logInfoDB(4,"Login website public, and add product", Store,testName);
			driver.manage().deleteAllCookies();
			driver.get(testData.B2C.getHomePageUrl());
			System.out.println(driver.getCurrentUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			driver.get(testData.B2C.getloginPageUrl());
			//b2cPage.Navigation_MyAccountIcon.click();
			//driver.findElement(By.xpath("(//a[contains(@href,'my-account') and not(contains(@class,'has-submenu'))])[1]")).click();
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
			int i=0;
			while((driver.getCurrentUrl().contains("j_spring_security"))&&(i++<3)) {
				driver.get(testData.B2C.getloginPageUrl());
				B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
			}
			Common.sleep(3000);
			driver.get(testData.B2C.getHomePageUrl()+"/cart");
			//b2cPage.Navigation_CartIcon.click();
			//Common.scrollToElement(driver, b2cPage.Navigation_ViewCartButton);
			//b2cPage.Navigation_ViewCartButton.click();
			B2CCommon.emptyCart(driver, b2cPage);
			//B2CCommon.addPartNumberToCart(b2cPage,productNo);
			//B2CCommon.addPartNumberToCart(b2cPage,"4X40E77324");
			try {
				addPartNumberToCart(b2cPage,testData.B2C.getDefaultMTMPN());
			}catch(Exception e) {
				B2CCommon.addPartNumberToCart(b2cPage,defaultProductNo);
			}
			B2CCommon.clickRequestQuote(b2cPage);
			Common.sleep(3000);
			try {
				b2cPage.Quote_RepIDTextBox.clear();
				b2cPage.Quote_RepIDTextBox.sendKeys(testData.B2C.getRepID());
			} catch (Exception e3) {
				Dailylog.logInfo("Dont sendkeys requestQuote");
			}
			Common.waitElementClickable(driver, b2cPage.Quote_submitQuoteBtn, 5);
			Common.scrollToElement(driver, b2cPage.Quote_submitQuoteBtn);
			b2cPage.Quote_submitQuoteBtn.click();
			String quoteNum = b2cPage.QuoteConfirmPage_QuoteNo.getText();
			Dailylog.logInfoDB(9, "Quote number is: " + quoteNum,Store,testName);
			Dailylog.logInfoDB(10,"Go to email address and check the email.", Store,testName);
			EMailCommon.createEmail(driver,mailPage,testData.B2C.getLoginID());
			//Boolean flag = checkEmail(driver, mailPage,orderNum, email+"@sharklasers.com" , shippingInfo, paymentInfo);
			Boolean flag = false;
			if(Store.contains("JP")){
				flag = EMailCommon.checkEmailContent(driver, mailPage, "お見積","//*[contains(text(),'"+quoteNum+"')]");
			}else{
				flag = EMailCommon.checkEmailContent(driver, mailPage, "uote","//*[contains(text(),'"+quoteNum+"')]");
			}
			if(flag==false){
				setManualValidateLog("Need Manual Validate in email "+testData.B2C.getLoginID() +", and check email: "+ "Gracias por tu orden "+quoteNum);		
			}
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public static void addPartNumberToCart(B2CPage b2cPage, String partNum) {
		b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
		b2cPage.Cart_AddButton.click();
		Common.waitElementClickable(b2cPage.PageDriver,
				b2cPage.PageDriver.findElement(By.xpath("//*[text()='" + partNum + "']")), 5);
	}
	
}
	


