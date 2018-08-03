package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.Payment;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class ACCT112Test extends SuperTestClass {
	
	private B2CPage b2cPage;
	private HMCPage hmcPage;
	public String customer;
	public String password_cus;
	
	public int index = 0;
	
	public ACCT112Test(String store , String customer,String password_cus) {
		this.Store = store;
		this.customer = customer;
		this.password_cus = password_cus;
		this.testName = "ACCT-112";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "account", "p2", "b2c"})
	public void ACCT112(ITestContext ctx) throws Exception {
		try{
			this.prepareTest();
			
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			
			
			//1, Login SMB website with telesales account and start ASM in my-account page.
			Dailylog.logInfoDB(1, "Login SMB website with telesales account and start ASM in my-account page.", Store, testName);
			
			driver.get(testData.B2C.getloginPageUrl());
			
			smbLogin(testData.B2C.getTelesalesAccount(),testData.B2C.getTelesalesPassword());
			
			driver.get(driver.getCurrentUrl() + "my-account");
			
			b2cPage.myAccountPage_startAssistedServiceSession.click();
			
			//2, Click Store Type select SMB and input ID in Store ID chose one store go to SMB store
			Dailylog.logInfoDB(2, "Click Store Type select SMB and input ID in Store ID chose one store go to SMB store", Store, testName);
			
			Select storeType = new Select(driver.findElement(By.xpath("//select[@name='storeType']")));
			
			storeType.selectByValue("SMB");
			
			Assert.assertTrue(driver.findElement(By.xpath("//input[@name='storeId']")).getAttribute("value").equals("smbpro"), "store id does not equal with smbpro");
			
			//3, Check the ASM panel layout
			Dailylog.logInfoDB(3, "Check the ASM panel layout", Store, testName);
			
			//4, Check the dropdown boxes in ASM model
			Dailylog.logInfoDB(3, "Check the dropdown boxes in ASM model", Store, testName);
			
				
			//5, Input SMB customer in customer search box
			Dailylog.logInfoDB(4, "Input SMB customer in customer search box",Store, testName);
			
			b2cPage.ASM_SearchCustomer.sendKeys(customer);
			new WebDriverWait(driver, 500)
					.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[id^='ui-id-']>a")));
			
			Assert.assertTrue(!driver.findElement(By.xpath("(//div[@id='asmAutoCompleteCustomer']//span[@class='name'])[last()]")).getText().equals(""), "Company nanme is not existing after input customer name");
			
			//6, Go to the advanced customer search page
			Dailylog.logInfoDB(6, "Go to the advanced customer search page", Store, testName);
			
			driver.findElement(By.xpath("//*[@id='showCustomerSearch']/span")).click();
			
			driver.findElement(By.xpath("//*[@id='id']")).clear();
			driver.findElement(By.xpath("//*[@id='id']")).sendKeys(customer);
			
			driver.findElement(By.xpath("//*[@id='advancedCustomerSearch']")).click();
			
			Thread.sleep(20000);
			
			List<WebElement> list_title = driver.findElements(By.xpath(".//*[@id='customerPopupContent']/div/table/thead/tr/th"));
			
			for(int x = 1 ; x<=list_title.size(); x++){
			
				String temp = driver.findElement(By.xpath("(//*[@id='customerPopupContent']/div/table/thead/tr/th)["+x+"]")).getText().trim();
				
				if(temp.equals("Company")){
					index = x;
					break;
				}
				
			}
			
			Dailylog.logInfoDB(6, "index is :" + index, Store, testName);
			
			Assert.assertTrue(!driver.findElement(By.xpath("(//tr[@class='b2cCustomer']/td)["+index+"]")).getText().equals(""), "company name is empty");
			
			
			//7, Select a SMB customer and start seession
			Dailylog.logInfoDB(7, "Select a SMB customer and start seession", Store, testName);
			
			driver.findElement(By.xpath("(//tr[@class='b2cCustomer']/td)[1]")).click();
			
			b2cPage.Tele_StartSession.click();
			
			//8,After emulating the customer,go to home page, check user group display on home page
			Dailylog.logInfoDB(8, "After emulating the customer,go to home page, check user group display on home page", Store, testName);
			
			driver.get(testData.B2C.getHomePageUrl());
			
			Assert.assertTrue(!driver.findElement(By.xpath("//div[@class='price_tier_title']/img")).getAttribute("src").equals(""), "user group does not diplay on the homepage");
			
			//9, In ASM mode check the product  display 
			Dailylog.logInfoDB(9, "In ASM mode check the product  display ", Store, testName);
			
			
			//10.Add one product to cart and place order,remember order number.
			Dailylog.logInfoDB(10, "Add one product to cart and place order,remember order number.", Store, testName);
			
			gotoSummaryPage();
			// Place Order
			Common.sendFieldValue(driver.findElement(By.xpath(".//*[@id='repId']")), testData.B2C.getRepID());
			
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			
			String orderNumberString  = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			
			Dailylog.logInfoDB(10, "order number is :" + orderNumberString, Store, testName);
			
			//11, Check the rep id and in HMC,Go HMC> oder>orders,input the order number generated in step 10 and search,double click the result ï¼Œgo to *Administration* Tag and check the rep id
			Dailylog.logInfoDB(11, "Check the rep id and in HMC,Go HMC> oder>orders,input the order number generated in step 10 and search,double click the result ï¼Œgo to *Administration* Tag and check the rep id", Store, testName);
			
			b2cPage.ASM_signout.click();
			
			hmc_bpID(orderNumberString);
			
			String RepID = driver.findElement(By.xpath("//input[contains(@id,'Order.repId')]")).getAttribute("value").trim();
			
			String bpID = driver.findElement(By.xpath("//input[contains(@id,'rder.bpId')]")).getAttribute("value").trim();
			
			Dailylog.logInfoDB(11, "rep id in hmc is :" + RepID, Store, testName);
			Dailylog.logInfoDB(11, "bp id in hmc is :" + bpID, Store, testName);
			
			Assert.assertTrue(RepID.equals(testData.B2C.getRepID()), "rep id does not euqual with " + testData.B2C.getRepID());
			
			//12, Sign out the ASM and sign with SMB user
			Dailylog.logInfoDB(12, "Sign out the ASM and sign with SMB user", Store, testName);
			
			driver.get(testData.B2C.getloginPageUrl());
			
			smbLogin(customer,password_cus);
			
			//13, Place order through direct web and remember the order number.
			Dailylog.logInfoDB(13, "Place order through direct web and remember the order number.", Store, testName);		
			
			gotoSummaryPage();
			
			// Place Order
			Common.sendFieldValue(driver.findElement(By.xpath(".//*[@id='repId']")), testData.B2C.getRepID());
			
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			
			String orderNumberString_1  = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			
			//14, Check the  BP_id in HMC,Go HMC> oder>orders,input the order number which generated in step 14 and search,double click the 
			Dailylog.logInfoDB(14, "Check the  BP_id in HMC,Go HMC> oder>orders,input the order number which generated in step 14 and search,double click the ", Store, testName);
			
			hmc_bpID(orderNumberString_1);
			
			String bpID_cus = driver.findElement(By.xpath("//input[contains(@id,'rder.bpId')]")).getAttribute("value").trim();
			
			Dailylog.logInfoDB(11, "bp id for customer in hmc is :" + bpID_cus, Store, testName);
			
			Assert.assertTrue(bpID_cus.equals(bpID), "bp id of telesales does not equal with bpid of customer");
	
		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}
	
	public void gotoSummaryPage() throws Exception{
		driver.get(testData.B2C.getHomePageUrl() + "/cart");
		
		
		B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());
		
		b2cPage.Cart_CheckoutButton.click();
		Thread.sleep(3000);
		
		String tempEmail = EMailCommon.getRandomEmailAddress();
		String firstName = Common.getDateTimeString();
		String lastName = Common.getDateTimeString();
		if (Common.checkElementExists(driver, b2cPage.Checkout_StartCheckoutButton, 5)) {
			b2cPage.Checkout_StartCheckoutButton.click();
		}
		// Fill shipping info
		if (Common.checkElementExists(driver, b2cPage.Shipping_FirstNameTextBox, 5)) {
			B2CCommon.fillShippingInfo(b2cPage, firstName, lastName, testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), tempEmail,
					testData.B2C.getConsumerTaxNumber());
		}
		Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
		// b2cPage.Shipping_ContinueButton.click();
		B2CCommon.handleAddressVerify(driver, b2cPage);
		// Fill payment info
		B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
		B2CCommon.fillPaymentAddressInfo(b2cPage, firstName, lastName, testData.B2C.getDefaultAddressLine1(),
				testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
				testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
//		Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
		Payment.clickPaymentContinueButton(b2cPage);

		B2CCommon.handleVisaVerify(b2cPage);
	}

	public void hmc_bpID(String orderNumber) throws Exception{
		driver.get(testData.HMC.getHomePageUrl());
		
		HMCCommon.Login(hmcPage, testData);
		
		hmcPage.Home_Order.click();
		hmcPage.Home_Order_Orders.click();
		hmcPage.Home_Order_OrderID.clear();
		hmcPage.Home_Order_OrderID.sendKeys(orderNumber);
		hmcPage.Home_Order_OrderSearch.click();
		Thread.sleep(5000);
		
		hmcPage.Home_Order_OrderDetail.click();
		// page1.OrderReload.click();
		Thread.sleep(5000);
		hmcPage.Home_Order_OrderAdmin.click();
		Thread.sleep(5000);
	}
	
	public void smbLogin(String user, String password){
		
		driver.findElement(By.xpath("//*[@id='smb-login-button']")).click();
		Common.sleep(3000);
		
		Common.sendFieldValue(driver.findElement(By.xpath("//*[@id='login.email.id']")), user);
		Common.sendFieldValue(driver.findElement(By.xpath("//*[@id='login.password']")), password);

		driver.findElement(By.xpath("//*[@id='nemoLoginForm']/div/button[@type='submit']")).click();
		
		Common.sleep(6000);
	}

}
