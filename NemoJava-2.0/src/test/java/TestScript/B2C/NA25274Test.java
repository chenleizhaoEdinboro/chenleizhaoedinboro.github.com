package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
import junit.framework.Assert;

public class NA25274Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	MailPage mailPage;
	String productNo;
	String email = "testco";
	String emailAddress = "testco@sharklasers.com";
	String totalPrice;
	
	public NA25274Test(String store, String productNo) {
		this.Store = store;
		this.testName = "NA-25274";
		this.productNo = productNo;
	}

	@Test(alwaysRun= true)
	public void NA25274(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			mailPage = new MailPage(driver);
			
			Dailylog.logInfoDB(1,"Login HMC, and 'wire' payment type.", Store,testName);		
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			By enableEmailCart = By
					.xpath("//*[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.enableEmailCartOnWeb]]_spantrue']");

			if (!driver.findElement(enableEmailCart).isSelected()) {
				driver.findElement(enableEmailCart).click();
			}
			
			hmcPage.Common_SaveButton.click();
			Dailylog.logInfoDB(4,"Login and add product", Store,testName);
			driver.manage().deleteAllCookies();
			driver.get(testData.B2C.getHomePageUrl());
			System.out.println(driver.getCurrentUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Common.javascriptClick(driver, driver.findElement(By.xpath(".//*[@id='myAccount']/a[1]")));
			//b2cPage.Navigation_MyAccountIcon.click();
			//driver.findElement(By.xpath("(//a[contains(@href,'my-account') and not(contains(@class,'has-submenu'))])[1]")).click();
			B2CCommon.login(b2cPage, emailAddress, "1q2w3e4r");
			b2cPage.Navigation_CartIcon.click();
			//Common.scrollToElement(driver, b2cPage.Navigation_ViewCartButton);
			//b2cPage.Navigation_ViewCartButton.click();
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			//B2CCommon.addPartNumberToCart(b2cPage,productNo);
			driver.get(testData.B2C.getHomePageUrl() + "/p/" + productNo);
			Common.scrollToElement(driver, driver.findElement(By.xpath("//*[@id='tab-a-nav-currentmodels']")));
			driver.findElement(By.xpath("//*[@id='tab-a-nav-currentmodels']")).click();
			//Common.scrollToElement(driver, driver.findElement(By.xpath("//button[@id='addToCartButtonTop'][1]")));
			//B2CCommon.clickAddtocartOrCustomizeOnPDP(driver);
			Thread.sleep(1000);
			driver.findElement(By.xpath("//button[@id='addToCartButtonTop'][1]")).click();
			Thread.sleep(6000);
			b2cPage.addTocart_configPage.click();
			Dailylog.logInfoDB(6,"Checkout and type shipping information then continue.", Store,testName);
			driver.findElement(By.xpath("//img[@alt='Email cart']")).click();
			Common.switchToWindow(driver, 1);
			b2cPage.EmailCart_SenderName.sendKeys("TestCO");
			b2cPage.EmailCart_CheckBox.click();
			b2cPage.EmailCart_SendButton.click();
			
			
			Dailylog.logInfoDB(9,"Check the T&C and place quote, show Thank you page.", Store,testName);
			// Send Email	
			driver.findElement(By.xpath("//h1[contains(.,'Cart details')]"));
			Thread.sleep(10000);
			
			Dailylog.logInfoDB(10,"Go to email address and check the email.", Store,testName);
			driver.manage().deleteAllCookies();
			EMailCommon.createEmail(driver,mailPage,email);
			//Boolean flag = checkEmail(driver, mailPage,orderNum, email+"@sharklasers.com" , shippingInfo, paymentInfo);
			Boolean flag = EMailCommon.checkIfEmailReceived(driver, mailPage, "Cart");
			if(flag==false){
				setManualValidateLog("Need Manual Validate in email "+ email+"@sharklasers.com" +", and check email: "+ "Cart from lenovo.com");		
			}
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
}
	


