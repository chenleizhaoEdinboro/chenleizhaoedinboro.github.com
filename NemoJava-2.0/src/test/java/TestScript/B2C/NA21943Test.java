package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA21943Test extends SuperTestClass{
	String b2cAccount;
	String productNo;
	String productNum = "3";
	String beforDisPrice;
	String totalPrice;
	String cartID;
	String partNO;
	String subTotalAftAndProd;
	String orderNO;
	HMCPage hmcPage;
	B2CPage b2cPage;
	float expectPriceAftDiscount;
	
	
	public String tele_homePageUrl;
	public String tele_loginUrl;
	public String tele_cartUrl;
	
	public String ordinary_homePageUrl;
	public String ordinary_loginUrl;
	public String ordinary_cartUrl;
	
	public String hmcLoginUrl;
	public String hmcHomePageUrl;
	
	
	
	
	public NA21943Test(String store){
		this.Store = store;
		this.testName = "NA-21943";
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "telesales", "p1", "b2c" ,"compatibility"})
	public void NA21943(ITestContext ctx){
		try{
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			
			tele_homePageUrl = testData.B2C.getTeleSalesUrl();
			tele_loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			tele_cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			
			ordinary_homePageUrl = testData.B2C.getHomePageUrl();
			ordinary_loginUrl = testData.B2C.getloginPageUrl();
			ordinary_cartUrl = ordinary_homePageUrl + "/cart";
			
			hmcLoginUrl = testData.HMC.getHomePageUrl();
			hmcHomePageUrl = testData.HMC.getHomePageUrl();
			
			this.productNo = testData.B2C.getDefaultMTMPN();
			this.partNO = testData.B2C.getDefaultMTMPN();
			this.b2cAccount = testData.B2C.getLoginID();
			
			
			//1 , login B2C with B2C account 
			Dailylog.logInfoDB(1, "login B2C with B2C account ", Store, testName);
			Common.NavigateToUrl(driver, Browser, ordinary_loginUrl);
			Thread.sleep(10000);
			
			B2CCommon.DoubleLogin(driver, testData, b2cPage, ordinary_loginUrl, testData.B2C.getLoginID(), testData.B2C.getLoginPassword(),Browser);
			Thread.sleep(10000);
			
			boolean b = Common.isElementExist(driver, By.xpath("//ul[contains(@class,'general_Menu')]//a[contains(@href,'logout')]"));
			
			Assert.assertTrue(b,"login failed!!!!");
			//2,Add product to cart, change the product quantity is 3,click update, copy the cart id
			Dailylog.logInfoDB(2, "Add product to cart, change the product quantity is 3,click update, copy the cart id ", Store, testName);
			B2CCommon.closeHomePagePopUP(driver);
			
			Common.NavigateToUrl(driver, Browser, ordinary_cartUrl);
			
			//part number is : 81A5003AAU
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			Thread.sleep(10000);
			B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());
			
			b2cPage.Cart_quantity.clear();
			b2cPage.Cart_quantity.sendKeys("3");
			
			b2cPage.cartPage_Quantity_update.click();
			
			Thread.sleep(10000);
			
			Assert.assertTrue(b2cPage.Cart_quantity.getAttribute("value").equals("3"),"step2, the product quantity change failed , After change the quantity , the number is :" + b2cPage.Cart_quantity.getAttribute("value"));
			
			String totalprice_cartPage = driver.findElement(By.xpath("//dl[@class='cart-summary-pricingTotal']/dd/span")).getText();
			float totalPrice_cartPage = B2CCommon.GetPriceValue(totalprice_cartPage);
			Dailylog.logInfoDB(2, "the total price on the cart page is :" + totalPrice_cartPage, Store, testName);
			
			Thread.sleep(10000);
			b2cPage.Cart_saveCart.click();
			Thread.sleep(8000);
			b2cPage.Cart_nameTextBox.clear();
			
			String timeStamp = Common.getDateTimeString();
			b2cPage.Cart_nameTextBox.sendKeys(timeStamp);
			
			b2cPage.Cart_saveCartBtn.click();
			
			Thread.sleep(5000);

			// cart id is : 0044036954
			String cartID = driver.findElement(By.xpath("(//td[contains(.,'"+timeStamp+"')]/../td/a)[1]")).getText().toString();
		
			
			//3, Login B2C with telsales account
			Dailylog.logInfoDB(3, "Login B2C with telsales account", Store, testName);
			
			B2CCommon.javascript_logout(driver);
			Thread.sleep(3000);
			
			Common.NavigateToUrl(driver, Browser, tele_loginUrl);
			B2CCommon.DoubleLogin(driver, testData, b2cPage, tele_loginUrl, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword(),Browser);
			//4,Click 'Start Assisted Service Session' in 'My Account',
			//	input B2C account (users in the same country) under the 'customer ID' label,
			//	click 'Start session',
			//	click 'Copy Tracsaction',
			//	choose the 'Transaction Type' and 'Transaction  ID',
			//	click 'Copy it',
			
			Dailylog.logInfoDB(4, "Click 'Start Assisted Service Session' in 'My Account',"
					+ "input B2C account (users in the same country) under the 'customer ID' label,"
					+ "click 'Start session',"
					+ "click 'Copy Tracsaction',"
					+ "click 'Copy Tracsaction',"
					+ "choose the 'Transaction Type' and 'Transaction  ID',"
					+ "click 'Copy it',", Store, testName);
		
			b2cPage.myAccountPage_startAssistedServiceSession.click();
			
			Thread.sleep(5000);
			B2CCommon.closeHomePagePopUP(driver);
			
			
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath(".//*[@id='customerFilter']")));
			b2cPage.ASM_SearchCustomer.sendKeys(testData.B2C.getLoginID());
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.cssSelector("[id^='ui-id-']>a")));
			b2cPage.ASM_CustomerResult.click();
			b2cPage.ASM_StartSession.click();
			Thread.sleep(6000);
			
			b2cPage.assistedServiceMode_copyTransaction.click();
			Select  sel =new Select(b2cPage.assistedServiceMode_transactionType);
			sel.selectByValue("CART");
			Thread.sleep(4000);
			b2cPage.assistedServiceMode_transactionID.sendKeys(cartID);
			Common.sleep(3000);
			b2cPage.assistedServiceMode_copyIt.click();
			
			String cartQuantity = b2cPage.cartInfo_cartQuantity.getAttribute("value");
			System.out.println("The quantity in cart page is: "+cartQuantity);
			Assert.assertEquals(cartQuantity, productNum, "The actual cart quantity is not equals with expect!");
			
			String price_total_editCartPage = "";
			
			if(!Store.equals("JP")){
				price_total_editCartPage = driver.findElement(By.xpath("//div[@class='savedcarttotal']/p")).getText().trim().split(":")[1].trim().toString();
			}else{
				price_total_editCartPage = driver.findElement(By.xpath("//div[@class='savedcarttotal']/p")).getText().trim().split("￥")[1].trim().toString();
			}
			float price_Total_editCartPage = B2CCommon.GetPriceValue(price_total_editCartPage);
			Dailylog.logInfoDB(4, "the price on the edit cart page is :" + price_Total_editCartPage, Store, testName);
			
			Assert.assertTrue(totalPrice_cartPage == price_Total_editCartPage, "the total price on the cart page is not equal with the total price on the edit cart page");
			
			//5, click 'Edit Cart', 
			Dailylog.logInfoDB(5, "click edit cart ", Store, testName);
			
			b2cPage.cartInfo_editCart.click();
//			String quantityEditAttribute = b2cPage.cartInfo_cartQuantityAfterEdit.getAttribute("disabled");
			
			boolean bb = false;
			
			if(Common.isElementExist(driver, By.xpath("//input[@name='initialQuantity']"))){
				bb = driver.findElement(By.xpath("//input[@name='initialQuantity']")).isEnabled();
			}else{
				bb = driver.findElement(By.xpath("//input[contains(@class,'QuantityUpdate')]")).isEnabled();
			}			
			
			System.out.println("bb is: "+bb);
			Assert.assertTrue(!bb, "The cart quantity is not gray and can change!");
			
			//6, add another product into cart 
			Dailylog.logInfoDB(6, "add another product into the cart", Store, testName);			

//			B2CCommon.addPartNumberToCartTele(b2cPage, testData.B2C.getDefaultMTMPN());
			b2cPage.Cart_QuickOrderTextBox.sendKeys(testData.B2C.getDefaultMTMPN());
			b2cPage.Cart_AddButton.click();
			
			Thread.sleep(8000);
			
			String price_addedNew = "";
			
			if(Common.isElementExist(driver, By.xpath("(//dt[@class='cartDetails-tsPrice'])[last()]"))){
				price_addedNew = driver.findElement(By.xpath("(//dt[@class='cartDetails-tsPrice'])[last()]")).getText();
			}else{
				price_addedNew = driver.findElement(By.xpath("(//dd[@class='cartDetails-tsPrice'])[last()]")).getText();
			}
			
			float price_adddedNew_f = B2CCommon.GetPriceValue(price_addedNew);
			Dailylog.logInfoDB(6, "the  price on the new added product is :" + price_adddedNew_f, Store, testName);
			
			
			String totalprice_cartPageWithNewAdded = driver.findElement(By.xpath("//dl[@class='cart-summary-pricingTotal']/dd/span")).getText();
			float totalPrice_cartPageWithNewAdded = B2CCommon.GetPriceValue(totalprice_cartPageWithNewAdded);
			Dailylog.logInfoDB(6, "the total price on the cart page is :" + totalPrice_cartPageWithNewAdded, Store, testName);
			
			Assert.assertTrue((price_adddedNew_f + totalPrice_cartPage) == totalPrice_cartPageWithNewAdded,"on the step 6, the price is not correct");
			
			//7, finish the checkout process 
			Dailylog.logInfoDB(7, "finish the checkout process ", Store, testName);
			
			b2cPage.Cart_CheckoutButton.click();
			Thread.sleep(3000);
			B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			
//			String totalPrice_shippingPage = driver.findElement(By.xpath(".//*[@id='summaryTotalPriceDiv']/div/span[@id='withTaxTotal']")).getText().toString();
			String totalPrice_shippingPage = "";
			
			if(Common.isElementExist(driver, By.xpath("//dd[contains(@class,'checkout_subtotal')]"))){
				totalPrice_shippingPage = driver.findElement(By.xpath("//dd[contains(@class,'checkout_subtotal')]")).getText().toString();
			}else{
				totalPrice_shippingPage = driver.findElement(By.xpath("//span[contains(@class,'subTotalWithoutCoupon')]")).getText().toString();
			}
			System.out.println("totalPrice_shippingPage is :" + totalPrice_shippingPage);
			float totalPrice_shippingPage_f = B2CCommon.GetPriceValue(totalPrice_shippingPage);
			System.out.println("totalPrice_shippingPage_f is :" + totalPrice_shippingPage_f);
			
			Assert.assertTrue(totalPrice_shippingPage_f == totalPrice_cartPageWithNewAdded, "the price on shipping page is :" + totalPrice_shippingPage_f + ", but the price on the cart page is :" + totalPrice_cartPageWithNewAdded);
			
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			
			if (Common.checkElementExists(driver,
					b2cPage.Shipping_AddressMatchOKButton, 10))
				b2cPage.Shipping_AddressMatchOKButton.click();
			
			
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			
			String totalPrice_paymentPage = "";
			
			if(Common.isElementExist(driver, By.xpath("//dd[contains(@class,'checkout_subtotal')]"))){
				totalPrice_paymentPage = driver.findElement(By.xpath("//dd[contains(@class,'checkout_subtotal')]")).getText().toString();
			}else{
				totalPrice_paymentPage = driver.findElement(By.xpath("//span[contains(@class,'subTotalWithoutCoupon')]")).getText().toString();
			}
			
			float totalPrice_paymentPage_f = B2CCommon.GetPriceValue(totalPrice_paymentPage);
			
			Assert.assertTrue(totalPrice_paymentPage_f == totalPrice_cartPageWithNewAdded, "the price on payment page is :" + totalPrice_paymentPage_f + ", but the price on the cart page is :" + totalPrice_cartPageWithNewAdded);
			
			
			Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
			
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			
			Thread.sleep(5000);
			
			String orderNO = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			Dailylog.logInfoDB(7, "the order number is :" + orderNO, Store, testName);
			
			
			//8, login HMC and check the price
			driver.manage().deleteAllCookies();
			Common.NavigateToUrl(driver, Browser, hmcHomePageUrl);
			
			HMCCommon.Login(hmcPage, testData);
			
			hmcPage.Home_Order.click();
			hmcPage.Home_Order_Orders.click();
			hmcPage.Home_Order_OrderID.sendKeys(orderNO);
			hmcPage.Home_Order_OrderSearch.click();
			hmcPage.Home_Order_OrderDetail.click();
			By byLocators01 = By.xpath("//div[contains(text(),'Total Price:')]/../../td[5]//input");
			String totalPrice_HMC = driver.findElement(byLocators01).getAttribute("value").toString().replace(",", "");
	        float ftotalPrice_HMC= Float.parseFloat(totalPrice_HMC);
	        
	        Assert.assertTrue(ftotalPrice_HMC == totalPrice_cartPageWithNewAdded,"the price in hmc is :" + ftotalPrice_HMC + ""
	        		+ ", but the price on the cart page is :" + totalPrice_cartPageWithNewAdded);
	
			
		} catch(Throwable e){
			handleThrowable(e, ctx);
		}
	}
		
	
	

	
	
}