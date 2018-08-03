package TestScript.B2C;

import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class NA19762Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	//账号在每次dbcopy之后都需要重新创建,根据wlhkunde,wlhadmin
	public String customerName = "wlhbuilder@sharklasers.com";
	public String customerPassword = "1q2w3e4r";
	public String adminName = "wlhapprover@sharklasers.com";
	public String adminPassword = "1q2w3e4r";
	private String order;
	public MailPage mailPage;
	String UserEmailHeader;
//	private String defaultMTMPN;
	String partnumber = "81CT0023GE";
	String url = "https://pre-c-hybris.lenovo.com/de/de/wlh/login";
	
	public NA19762Test(String store) {
		this.Store = store;
		this.testName = "NA-19762";
	}

	@Test(alwaysRun = true,groups= {"contentgroup","storemgmt","p2","b2c"})
	public void NA19762(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			mailPage = new MailPage(driver);
//			defaultMTMPN = testData.B2C.getDefaultMTMPN();
			//3.choose product and drop order 
			loadCustomer();
			Common.sleep(5000);
			testEmail(order);
			Dailylog.logInfoDB(1, "2,3choose product and drop orde", Store, testName);
			//4-5 WLH admin log website
			loadAdmin();
			Dailylog.logInfoDB(4, "5,WLH admin log website", Store, testName);
			//6.choose order and reject order
			testOrder();
			testEmail(order);
			Dailylog.logInfoDB(6, "choose order and reject order", Store, testName);
			//7.continue to step 2,3 
			loadCustomer();
			testEmail(order);
			Dailylog.logInfoDB(7, "continue to step 2,3 ", Store, testName);
			//8.continue to step 4,5
			loadAdmin();
			Dailylog.logInfoDB(8, "continue to step 4,5", Store, testName);
			//9.wlh admin  enter po number and po date then appove order
			testOrder();
			b2cPage.approvalPoDate.clear();
			b2cPage.approvalPoDate.sendKeys("");
			b2cPage.poNumber.clear();
			b2cPage.poNumber.sendKeys(order);
			b2cPage.orderReject.click();
			testEmail(order);
			Dailylog.logInfoDB(9, "wlh admin  enter po number and po date then appove order", Store, testName);
			
			//10.No create account button
			driver.get(url);
			Assert.assertTrue(!driver.findElement(By.xpath("//a[@class='button-called-out signInModule-createAccount-button']")).isDisplayed());
			Dailylog.logInfoDB(10, "No create account button", Store, testName);
			
			//11.no saved cart no viewed my address book no view my order history
			B2CCommon.login(b2cPage, customerName, customerPassword);
			Common.sleep(2000);
			Assert.assertTrue(!driver.findElement(By.xpath("//div[@id='accountNav']/ul/li[2]/a")).isDisplayed());
			Assert.assertTrue(!driver.findElement(By.xpath("//div[@id='accountNav']/ul/li[4]/a")).isDisplayed());
			Assert.assertTrue(!driver.findElement(By.xpath("//div[@id='accountNav']/ul/li[6]/a")).isDisplayed());
			
			((JavascriptExecutor)driver).executeScript("(window.open(''))");
			Common.switchToWindow(driver, 1);
			driver.get(url);
			B2CCommon.login(b2cPage, customerName, customerPassword);
			Common.sleep(2000);
			Assert.assertTrue(!driver.findElement(By.xpath("//div[@id='accountNav']/ul/li[2]/a")).isDisplayed());
			Assert.assertTrue(!driver.findElement(By.xpath("//div[@id='accountNav']/ul/li[4]/a")).isDisplayed());
			Assert.assertTrue(!driver.findElement(By.xpath("//div[@id='accountNav']/ul/li[6]/a")).isDisplayed());
			Dailylog.logInfoDB(11, "no saved cart no viewed my address book no view my order history", Store, testName);
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void testOrder() {
		List<WebElement> findElements = driver.findElements(By.xpath("//*[@id=\"order_history\"]/tbody/tr/td[2]"));
		for (int i = 0; i < findElements.size(); i++) {
			if(findElements.get(i).getText().equals(order)) {
				findElements.get(i).click();
				Common.sleep(3000);
				b2cPage.orderReject.click();
			}
		}
	}

	public void testEmail(String order) {
		driver.manage().deleteAllCookies();
		EMailCommon.goToMailHomepage(driver);
		Common.sleep(3000);
		driver.findElement(By.xpath("//span[@class='strong']/span[@id='inbox-id']")).click();
		driver.findElement(By.xpath("//span[@id='inbox-id']/input")).clear();
		driver.findElement(By.xpath("//span[@id='inbox-id']/input")).sendKeys(adminName);
		driver.findElement(By.xpath("//*[@id=\"gm-host-select\"]/option[@value='sharklasers.com']")).click();
		driver.findElement(By.xpath("//button[@class='save button small']")).click();
		Common.sleep(5000);
		driver.findElement(By.xpath(".//tbody[@id='email_list']/tr[2]")).click();
		Common.sleep(3000);
		String confirmation = driver.findElement(By.xpath("//li[@class='attach-file']/a")).getText();
		System.out.println("order"+order+"======confirmation"+confirmation);
		Assert.assertTrue(confirmation.contains(order));
//		EMailCommon.checkConfirmationEmail(driver, adminName, order);
	}

	public void loadCustomer() throws Exception {
		driver.get(url);
		B2CCommon.login(b2cPage, customerName, customerPassword);
		Common.sleep(2000);
		b2cPage.CartPage_icon.click();
		Common.sleep(2000);
		B2CCommon.addPartNumberToCart(b2cPage, partnumber);
		Common.sleep(5000);
		b2cPage.Cart_CheckoutButton.click();
		B2CCommon.fillShippingInfo(b2cPage, "AutoFirstName", "AutoLastName", testData.B2C.getDefaultAddressLine1(),
				testData.B2C.getDefaultAddressCity(), "Bayern",
				testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(),
				testData.B2C.getLoginID(), testData.B2C.getConsumerTaxNumber(),adminName);
		driver.findElement(By.xpath("//input[@id='personalNumber']")).clear();
		driver.findElement(By.xpath("//input[@id='personalNumber']")).sendKeys("123456");
		Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
//		b2cPage.Shipping_ContinueButton.click();
		Common.scrollToElement(driver, driver.findElement(By.xpath("//input[@id='purchase_bicCode']")));
		driver.findElement(By.xpath("//input[@id='purchase_bicCode']")).clear();
		driver.findElement(By.xpath("//input[@id='purchase_bicCode']")).sendKeys("123456789");
		driver.findElement(By.xpath("//input[@id='purchase_IBAN']")).clear();
		driver.findElement(By.xpath("//input[@id='purchase_IBAN']")).sendKeys("123456789123456789123");
		Common.javascriptClick(driver, driver.findElement(By.xpath("//input[@id='useDeliveryAddress']")));
//		driver.findElement(By.xpath("//input[@id='useDeliveryAddress']")).click();
		b2cPage.ContinueforPayment.click();
		Common.sleep(2000);
		b2cPage.OrderSummary_AcceptTermsCheckBox.click();
		b2cPage.OrderSummary_PlaceOrderButton.click();
		order = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
	}

	public void loadAdmin() throws MalformedURLException {
//		SetupBrowser();
//		driver.manage().deleteAllCookies();
		driver.get(url);
		B2CCommon.login(b2cPage, adminName, adminPassword);
		Common.sleep(2000);
		driver.get(testData.B2C.getHomePageUrl()+"/my-account");
		Common.sleep(2000);
		b2cPage.adminOrderHistory.click();
		Assert.assertTrue(b2cPage.orderNumbers.getText().contains(order));
	}

}
