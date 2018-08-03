package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.CTOandPB;
import CommonFunction.DesignHandler.Payment;
import CommonFunction.DesignHandler.PaymentType;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class NA25328Test extends SuperTestClass {
	String PNLowerThan500 = "60FELAR1US";
	String PNMoreThan500 = "80X600HEUS";
	private String address1 = "27563 Vista Court Dr";
	private String city = "Plano";
	private String addressState = "Texas";
	private String ZIPcODE = "75074";
	private String phoneNUM = "2025550180";
	
	public NA25328Test(String store) {
		this.Store = store;
		this.testName = "NA-25328";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "payment", "p2", "b2c"})
	public void NA25328(ITestContext ctx) {
		try {
			this.prepareTest();
			B2CPage b2cPage = new B2CPage(driver);
			HMCPage hmcPage = new HMCPage(driver);
			/*
			// Step 1 Configure HMC values
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_Nemo.click();
			hmcPage.Home_payment.click();
			Common.javascriptClick(driver, hmcPage.Home_zibbySetting);
			hmcPage.PaymentLeasing_searchbutton.click();
			driver.findElement(By.xpath("//img[contains(@id,'Content/OrganizerListEntry') and @class='icon']")).click();
			hmcPage.Zibby_Thresholds.clear();
			hmcPage.Zibby_Thresholds.sendKeys("500");
			hmcPage.Zibby_PriceLimit.clear();
			hmcPage.Zibby_PriceLimit.sendKeys("300");
			hmcPage.HMC_Save.click();
			Thread.sleep(5000);
			*/
			// Skip step 2-3, assuming configurations are done by default
			
			// Step 6\7\9\10\11 - cart value >= 500, should be able to place order
			driver.get(testData.B2C.getHomePageUrl() + "/cart");
			B2CCommon.handleGateKeeper(b2cPage, testData);
			// Quick order
			B2CCommon.addPartNumberToCart(b2cPage, PNMoreThan500);

			// Amazon has special process
			b2cPage.Cart_CheckoutButton.click();
			Thread.sleep(2000);

			// Click on guest checkout button if exists
			if (Common.checkElementDisplays(driver, b2cPage.Checkout_StartCheckoutButton, 5)) {
				b2cPage.Checkout_StartCheckoutButton.click();
			}

			// Fill default shipping address
			if (Common.checkElementDisplays(driver, b2cPage.Shipping_FirstNameTextBox, 5)) {
				B2CCommon.fillShippingInfo(b2cPage, "asd", "asd", address1, city, addressState, 
						ZIPcODE, phoneNUM, "lixe1@lenovo.com", testData.B2C.getConsumerTaxNumber());

			}



//			
//			27563 Vista Court Dr 
//			City：Plano
//			State:Texas
//			Zip Code：75074
//			Phone: 2025550180
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			B2CCommon.handleAddressVerify(driver, b2cPage);
			
			// Payment
			if (CommonFunction.DesignHandler.Payment.isPaymentMethodExists(b2cPage, PaymentType.Zibby_B2C)) {

				Payment.payAndContinue(b2cPage, PaymentType.Zibby_B2C, testData);

				// Get Order number
				String orderNum = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);

				
				// Step 4 cart price < 500, Zibby should not show
				driver.get(testData.B2C.getHomePageUrl() + "/cart");
				B2CCommon.handleGateKeeper(b2cPage, testData);
				// Quick order
				B2CCommon.addPartNumberToCart(b2cPage, PNLowerThan500);

				// Amazon has special process
				b2cPage.Cart_CheckoutButton.click();
				Thread.sleep(2000);

				// Click on guest checkout button if exists
				if (Common.checkElementExists(driver, b2cPage.Checkout_StartCheckoutButton, 5)) {
					b2cPage.Checkout_StartCheckoutButton.click();
				}

				// Fill default shipping address
				if (Common.checkElementExists(driver, b2cPage.Shipping_FirstNameTextBox, 5)) {
					B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
				}
				Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
				B2CCommon.handleAddressVerify(driver, b2cPage);
				
				if (CommonFunction.DesignHandler.Payment.isPaymentMethodExists(b2cPage, PaymentType.Zibby_B2C))
					Assert.fail("Zibby should not show when cart price < 500!");
				
				// Step 5 For each item, price after discount < 20, make cart value > 500, Zibby should not show
				driver.manage().deleteAllCookies();
				driver.get(testData.B2C.getHomePageUrl() + "/cart");
				B2CCommon.handleGateKeeper(b2cPage, testData);
				// Quick order
				B2CCommon.addPartNumberToCart(b2cPage, PNLowerThan500);
				b2cPage.Cart_quantity.clear();
				b2cPage.Cart_quantity.sendKeys("5");
				b2cPage.cartPage_Quantity_update.click();
				Thread.sleep(5000);

				// Amazon has special process
				b2cPage.Cart_CheckoutButton.click();
				Thread.sleep(2000);

				// Click on guest checkout button if exists
				if (Common.checkElementExists(driver, b2cPage.Checkout_StartCheckoutButton, 5)) {
					b2cPage.Checkout_StartCheckoutButton.click();
				}

				// Fill default shipping address
				if (Common.checkElementExists(driver, b2cPage.Shipping_FirstNameTextBox, 5)) {
					B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
				}
				Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
				B2CCommon.handleAddressVerify(driver, b2cPage);
				
				if (CommonFunction.DesignHandler.Payment.isPaymentMethodExists(b2cPage, PaymentType.Zibby_B2C))
					Assert.fail("Zibby should not show when price after discount < 400, make cart value > 500!");
				
				// Step 8\12 services are non-leasable, customer have to pay in full
				driver.manage().deleteAllCookies();
				driver.get(testData.B2C.getHomePageUrl() + "/p/" + PNMoreThan500);
				B2CCommon.handleGateKeeper(b2cPage, testData);
				B2CCommon.clickAddtocartOrCustomizeOnPDP(driver);
				
				String servicePN = "";
				if (Common.checkElementDisplays(driver, By.xpath(".//*[contains(@class,'stepsItem-wrapper')]"), 10)) {
					Common.checkElementDisplays(driver, b2cPage.CTO_ChangeWarrantyButton, 10);
					Common.javascriptClick(driver, b2cPage.CTO_ChangeWarrantyButton); // New UI Only 
					Thread.sleep(3000);
					servicePN = driver.findElement(By.xpath(".//*[@id='warrServices']//li[contains(@style,'display')]")).getAttribute("Value");
					Common.javascriptClick(driver, driver.findElement(By.xpath(".//*[@id='warrServices']//li[contains(@style,'display')]//div")));
					// New CTO PB
					CTOandPB.addToCartFromCTONew(driver);
				} else {
					// Old CTO PB
					CTOandPB.addToCartFromCTOOld(driver);
				}
				
				b2cPage.Cart_CheckoutButton.click();
				Thread.sleep(2000);

				// Click on guest checkout button if exists
				if (Common.checkElementExists(driver, b2cPage.Checkout_StartCheckoutButton, 5)) {
					b2cPage.Checkout_StartCheckoutButton.click();
				}

				// Fill default shipping address
				if (Common.checkElementExists(driver, b2cPage.Shipping_FirstNameTextBox, 5)) {
					B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
				}
				Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
				B2CCommon.handleAddressVerify(driver, b2cPage);
				
				Thread.sleep(5000);
				Common.javascriptClick(driver, b2cPage.Payment_ZibbyRadioButton);
				Payment.fillOtherPaymentFields(b2cPage, testData);
				Payment.clickPaymentContinueButton(b2cPage);
				
				Thread.sleep(5000);
				driver.switchTo().frame(b2cPage.Zibby_Iframe);
				b2cPage.Zibby_CloseButton.click();
				driver.switchTo().defaultContent();
				
				Payment.clickPaymentContinueButton(b2cPage);
				Thread.sleep(5000);
				driver.switchTo().frame(b2cPage.Zibby_Iframe);
				
				Thread.sleep(3000);
				b2cPage.Zibby_Phone.sendKeys("2");
				Thread.sleep(1000);
				b2cPage.Zibby_Phone.sendKeys("0");
				Thread.sleep(1000);
				b2cPage.Zibby_Phone.sendKeys("2");
				Thread.sleep(1000);
				b2cPage.Zibby_Phone.sendKeys("5");
				Thread.sleep(1000);
				b2cPage.Zibby_Phone.sendKeys("5");
				Thread.sleep(1000);
				b2cPage.Zibby_Phone.sendKeys("5");
				Thread.sleep(1000);
				b2cPage.Zibby_Phone.sendKeys("0");
				Thread.sleep(1000);
				b2cPage.Zibby_Phone.sendKeys("1");
				Thread.sleep(1000);
				b2cPage.Zibby_Phone.sendKeys("8");
				Thread.sleep(1000);
				b2cPage.Zibby_Phone.sendKeys("0");
				b2cPage.Zibby_Terms.click();
				b2cPage.Zibby_Terms2.click();
				b2cPage.Zibby_Continue.click();
				
				Thread.sleep(3000);
				b2cPage.Zibby_DigitalCode.sendKeys("123456");
				b2cPage.Zibby_DigitalCode2.sendKeys("1234");
				b2cPage.Zibby_Continue.click();
				
				Thread.sleep(3000);
				b2cPage.Zibby_Detail.click();
				Thread.sleep(5000);
				List<WebElement> list = driver.findElements(By.xpath("//*[@class='item ng-scope']"));
				for(WebElement item : list)
				{
					if(item.getText().contains(servicePN) && item.getText().contains("Monthly"))
						Assert.fail("Service is leasing!");
				}
				
				// Step 13 verify order status and YB06
				// Verify HMC value
				driver.get(testData.HMC.getHomePageUrl());
//				HMCCommon.Login(hmcPage, testData);
				HMCCommon.HMCOrderCheck(driver, hmcPage, orderNum);
				
				// Validate YB06 in xml
				if(!HMCCommon.GetYB06Value(hmcPage).equals("Z"))
					Assert.fail("YB06 value is wrong");

				Dailylog.logInfoDB(1, "Order Number is: " + orderNum, this.Store, this.testName);
			} else {
				Assert.fail("Zibby is not configured yet!");
			}
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
}
