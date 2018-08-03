package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.DesignHandler.Payment;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class NA22214Test extends SuperTestClass {
	public B2CPage b2cPage;
	public MailPage mailPage;
	String mixSubseriesNum;
	String ctoSubseriesNum;
	String mtmSubseriesNum;
	String dealsUrl;

	public NA22214Test(String store, String mixSubseriesNum, String ctoSubseriesNum, String mtmSubseriesNum,
			String dealsUrl) {
		this.Store = store;
		this.testName = "NA-22214";
		this.mixSubseriesNum = mixSubseriesNum;
		this.ctoSubseriesNum = ctoSubseriesNum;
		this.mtmSubseriesNum = mtmSubseriesNum;
		this.dealsUrl = dealsUrl;
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA22214(ITestContext ctx) {

		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			mailPage = new MailPage(driver);
			// Step 1 enter B2C and go to subseries page
			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Common.sleep(3000);
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+mixSubseriesNum);
			Common.sleep(3000);
				if(Common.checkElementExists(driver, b2cPage.Product_custiome, 5)) {
					List<WebElement> findElements = driver.findElements(By.xpath("//div[@class='tabbedBrowse-productListing-footer']"));
					for (int j = 0; j < findElements.size(); j++) {
						if(findElements.get(j).getText().toLowerCase().contains("add to cart")) {
							Assert.assertTrue(Common.checkElementExists(driver, b2cPage.PLP_PartNumber, 5));
						}
						
					}
					Dailylog.logInfoDB(1, "all has PN", Store, testName);
					
				}

			// Step 2 Only CTO in subseries
			Common.sleep(3000);
			driver.get(testData.B2C.getHomePageUrl() + "/p/" + ctoSubseriesNum);
			Common.sleep(2000);
			if(Common.checkElementExists(driver, b2cPage.Product_custiome, 5)) {
				if(b2cPage.Product_custiome.getText().toLowerCase().contains("customize")) {
					Assert.assertTrue(!Common.checkElementExists(driver, b2cPage.PLP_PartNumber, 5));
					Dailylog.logInfoDB(2, "CTO has no PN", Store, testName);
				}
			}
			
			// Step 3 Only MTM in subseries
			Common.sleep(3000);
			driver.get(testData.B2C.getHomePageUrl() + "/p/" + mtmSubseriesNum);
			Common.sleep(2000);
			if(Common.checkElementExists(driver, b2cPage.Product_custiome, 5)) {
				if(b2cPage.Product_custiome.getText().toLowerCase().contains("add to cart")) {
					Assert.assertTrue(Common.checkElementExists(driver, b2cPage.PLP_PartNumber, 5));
					Dailylog.logInfoDB(3, "MTM has  PN", Store, testName);
				}
			}
			
			// Step 4 Add one MTM into cart
			Common.sleep(2000);
			List<WebElement> findElements = driver.findElements(By.xpath("//button[@id='addToCartButtonTop']"));
			Common.javascriptClick(driver, findElements.get(findElements.size()-1));
			Common.sleep(2000);
			b2cPage.CartPage_icon.click();
			Common.sleep(3000);
			/*if(Common.checkElementExists(driver, b2cPage.Cart_PartNumber, 5)) {
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Cart_PartNumber, 5));
			}else if(Common.checkElementExists(driver, b2cPage.Cart_number, 5)){
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Cart_number, 5));
			}else {
				Assert.fail("cart has no partnumber");
			}*/
			Dailylog.logInfoDB(4, "Add one MTM into cart", Store, testName);
			
			// Step 5 Check deals page
			Common.sleep(2000);
			driver.get(testData.B2C.getHomePageUrl() + dealsUrl);
			if(Common.checkElementExists(driver, b2cPage.PLP_PartNumber, 5)) {
				Dailylog.logInfoDB(5, "Check deals page", Store, testName);
			}else {
				driver.get(testData.B2C.getHomePageUrl() + "/landingpage/promotions/lenovo-clearance-sale/");
				Dailylog.logInfoDB(5, "Check deals page", Store, testName);
			}
			
			// Step 6 Click Shop Now Button for one MTM then Add it to Cart
			List<WebElement> shownow = driver.findElements(By.xpath("//a[contains(.,'Shop Now')]"));
			if(shownow.size()>2){
				Common.javascriptClick(driver, shownow.get(shownow.size()-2));
			}else{
				Common.javascriptClick(driver, shownow.get(shownow.size()-1));
			}
			
			Common.sleep(2000);
			if(Common.checkElementDisplays(driver, By.xpath("//button[@id='addToCartButtonTop']"), 5)) {
				List<WebElement> add = driver.findElements(By.xpath("//button[@id='addToCartButtonTop']"));
				if(add.size()>1) {
					Common.javascriptClick(driver, add.get(add.size()-1));	
				}
			}
			if(Common.checkElementDisplays(driver, By.xpath("//button[@id='addToCart']"), 5)) {
				Common.javascriptClick(driver, driver.findElement(By.xpath("//button[@id='addToCart']")));
			}
			Common.sleep(3000);
			Common.scrollToElement(driver, b2cPage.CartPage_icon);
			Common.javascriptClick(driver, b2cPage.CartPage_icon);
//			b2cPage.CartPage_icon.click();
			Common.sleep(3000);
			Assert.assertTrue(Common.checkElementExists(driver, b2cPage.CartPage_PartNumberOfAddedProduct, 5)||
					Common.checkElementExists(driver, b2cPage.CartPage_PartNumberOfAddedProductOldUI, 5));
			String partNumber ;
			if(Common.checkElementExists(driver, b2cPage.CartPage_PartNumberOfAddedProduct, 3)){
				partNumber = b2cPage.CartPage_PartNumberOfAddedProduct.getText();
			}else{
				partNumber = b2cPage.CartPage_PartNumberOfAddedProductOldUI.getText();
			}
			Dailylog.logInfoDB(6, "PN displayed on cart page", Store, testName);

			// Step 7 Click"Checkout"Button then place order
			b2cPage.Cart_CheckoutButton.click();
			Common.sleep(5000);
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
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//*[contains(.,'"+partNumber+"')]"), 2), "check part num on shipping");
			
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			// b2cPage.Shipping_ContinueButton.click();
			B2CCommon.handleAddressVerify(driver, b2cPage);
			// Fill payment info
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			B2CCommon.fillPaymentAddressInfo(b2cPage, firstName, lastName, testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
//			Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//*[contains(.,'"+partNumber+"')]"), 2), "check part num on payment");
			Payment.clickPaymentContinueButton(b2cPage);

			B2CCommon.handleVisaVerify(b2cPage);
			// Place Order
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//*[contains(.,'"+partNumber+"')]"), 2), "check part num on review page");
					
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			// Get Order number
			String orderNum= B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			EMailCommon.createEmail(driver, mailPage, tempEmail);
			EMailCommon.checkEmailContent(driver, mailPage,  orderNum,  "//*[contains(text(),'"+orderNum+"')]");
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}
