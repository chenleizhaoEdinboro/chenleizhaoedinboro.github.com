package TestScript.B2B;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
public class NA15503Test extends SuperTestClass{
	
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public B2BCommon b2bCommon;
	public HMCCommon hmcCommon;
	public String homePageUrl;
	ArrayList<String> al;
	public String orderNumber;
	public String HMCTotalPrice_1;
	
	
	String date = Common.getDateTimeString();
	
	public NA15503Test(String Store){
		this.Store = Store;
		this.testName = "NA-15503";
	}
	
	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p1", "b2b", "compatibility"})
	public void NA15503(ITestContext ctx){
		try{
			this.prepareTest();
			 b2bPage = new B2BPage(driver);
			 hmcPage = new HMCPage(driver);
			
			//1,  Login to HMC, go to HMC->B2B Commerce->B2B unit->type B2B Unit ID->search B2B unit
			Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			driver.navigate().refresh();
			HMCSearchB2BUnit();
			
			// set the Expedited shipping enabled 
			By rateShipping_Enabled = By.xpath("//input[contains(@id,'Content/Attribute[B2BUnit.flatRateShippingEnabled]]_true')]");
			if(!driver.findElement(rateShipping_Enabled).isSelected()){
				driver.findElement(rateShipping_Enabled).click();
			}
			Thread.sleep(10000);
			hmcPage.B2BUnit_Save.click();
			Thread.sleep(5000);
			//2 ,Click B2B unit and go to Site Attribute tab, 
			//find “Delivery Mode and Flat Rate”.
			//right click form to Add Delivery model, search value of “expedite-shipping-gross” and click to use-> adjust Rate value->save
			By locator_gross = By.xpath(".//*[@id='Content/NemoB2BDeliveryModeFlatRate[in Content/Attribute[B2BUnit.deliveryModeAndFlatRate]]_expedite-shipping-gross_tr']/td[contains(.,'expedite-shipping-gross')]");
			if(!isElementExsit(driver,locator_gross)){
				Common.rightClick(driver, hmcPage.B2BUnit_siteAttribute_DeliveryModeIdentifiers);
				hmcPage.B2BUnit_siteAttribute_AddDeliveryModeAndFlatRate.click();
				
				String handle = driver.getWindowHandle().toString();
				System.out.println("handle is :" + handle);
				for(String winHandle : driver.getWindowHandles()){
					if(winHandle.equals(handle)){
						continue;
					}else{
						driver.switchTo().window(winHandle);
					}
				}
				
				hmcPage.B2BUnit_DeliveryMode_TextBox.sendKeys("expedite-shipping-gross");
				Thread.sleep(3000);
				hmcPage.B2BUnit_DeliveryMode_SearchButton.click();
				hmcPage.B2BUnit_DeliveryMode_SettingValue.click();
				hmcPage.B2BUnit_DeliveryMode_SettingUse.click();
				
				driver.switchTo().window(handle);
				hmcPage.B2BUnit_SiteAttribute_expediteRate.clear();
				hmcPage.B2BUnit_SiteAttribute_expediteRate.click();
				hmcPage.B2BUnit_SiteAttribute_expediteRate.sendKeys("20.0");
			}else{
				String rateValue = driver.findElement(By.xpath(".//*[@id='Content/NemoB2BDeliveryModeFlatRate[in Content/Attribute[B2BUnit.deliveryModeAndFlatRate]]_expedite-shipping-gross']")).getAttribute("value");
				System.out.println("rateValue is :" + rateValue);
				if(!rateValue.equals("20.0")){
					hmcPage.B2BUnit_SiteAttribute_expediteRate.clear();
					hmcPage.B2BUnit_SiteAttribute_expediteRate.click();
					hmcPage.B2BUnit_SiteAttribute_expediteRate.sendKeys("20.0");
				}
			}
			
			Thread.sleep(10000);
			hmcPage.B2BUnit_Save.click();
			Thread.sleep(5000);
			driver.navigate().refresh();
			Thread.sleep(5000);
			String rateValue = driver.findElement(By.xpath(".//*[@id='Content/NemoB2BDeliveryModeFlatRate[in Content/Attribute[B2BUnit.deliveryModeAndFlatRate]]_expedite-shipping-gross']")).getAttribute("value");
			System.out.println("after change , the rateValue is :" + rateValue);		
			Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='Content/NemoB2BDeliveryModeFlatRate[in Content/Attribute[B2BUnit.deliveryModeAndFlatRate]]_expedite-shipping-gross']")).getAttribute("value").equals("20.0"));
			
			Thread.sleep(4000);
			hmcPage.Home_closeSession.click();
			
			
			// 3, login B2B store, add product to cart, save a company cart（include at last 2 kinds of product）.
			
			Common.NavigateToUrl(driver, Browser, testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuilderId(), testData.B2B.getDefaultPassword());
			homePageUrl = driver.getCurrentUrl();
			
			b2bPage.HomePage_CartIcon.click();
//			if(Common.checkElementExists(driver, b2bPage.cartPage_emptyCartButton,3)){
//				b2bPage.cartPage_emptyCartButton.click();
//			}
			if(Common.isElementExist(driver, By.xpath("//a[contains(text(),'Empty cart')]"))){
				b2bPage.cartPage_emptyCartButton.click();
			}
			b2bPage.HomePage_productsLink.click();
			List<WebElement> subList = driver.findElements(By.xpath("//a[@class='products_submenu']"));
			for(int b = 1; b <= subList.size();b++){
				driver.findElement(By.xpath("(//a[@class='products_submenu'])["+b+"]")).click();
//				List<WebElement> resultProd = driver.findElements(By.xpath(".//*[@id='resultList']/div"));
				
				List<WebElement> partNumber = driver.findElements(By.xpath("(//*[@id='resultList']/div/div/div[2]/div[1]/b/dl/dd[1])"));
				al = new ArrayList<String>();
				for(int x = 1; x <= partNumber.size();x++){
					String str = driver.findElement(By.xpath("(//*[@id='resultList']/div/div/div[2]/div[1]/b/dl/dd[1])["+x+"]")).getText().toString();
					al.add(str);
				}
				if(al.size() < 2){
					b2bPage.HomePage_productsLink.click();
				}else{
					break;
				}
						
				
//				b2bPage.productPage_agreementsAndContract.click();
//				if(Common.isElementExist(driver, By.xpath("//form/label[contains(.,'Contract')]"))){
//					b2bPage.productPage_raidoContractButton.click();
//					Thread.sleep(3000);
//					List<WebElement> resultProd = driver.findElements(By.xpath(".//*[@id='resultList']/div"));
//					if(resultProd.size() <2){
//						b2bPage.HomePage_productsLink.click();
//					}else{
//						break;
//					}
//				}else{
//					b2bPage.HomePage_productsLink.click();
//					System.out.println("under this category, the contact product is not existing!!!");
//				}
				
			}
//			
//			driver.findElement(By.xpath("(//a[@class='products_submenu'])[1]")).click();
			
//			if(common.checkElementExists(driver,b2bPage.cartPage_emptyCartButton)){
//				List<WebElement> subList = driver.findElements(By.xpath("//a[@class='products_submenu']"));
//				subList.get(0).click();
//			}else{
//				System.out.println(Environment+"/"+"B2B"+"/"+Store+",  on homepage , under products , the subList is not existing");
//			}
			
			List<WebElement> partNumber = driver.findElements(By.xpath("(//*[@id='resultList']/div/div/div[2]/div[1]/b/dl/dd[1])"));
			al = new ArrayList<String>();
			for(int x = 1; x <= partNumber.size();x++){
				String str = driver.findElement(By.xpath("(//*[@id='resultList']/div/div/div[2]/div[1]/b/dl/dd[1])["+x+"]")).getText().toString();
				al.add(str);
			}
			b2bPage.HomePage_CartIcon.click();
			
			b2bPage.cartPage_quickOrder.clear();
			b2bPage.cartPage_quickOrder.sendKeys(al.get(0));
			b2bPage.cartPage_addButton.click();
			
			b2bPage.cartPage_quickOrder.clear();
			b2bPage.cartPage_quickOrder.sendKeys(al.get(0));
			b2bPage.cartPage_addButton.click();
			Common.javascriptClick(driver, b2bPage.cartPage_saveCartButton);
			//b2bPage.cartPage_saveCartButton.click();
			b2bPage.cartPage_companySaveCartButton.click();
			b2bPage.cartPage_SaveCart_cartNameField.clear();
			b2bPage.cartPage_SaveCart_cartNameField.sendKeys(date);
			b2bPage.cartPage_SaveCart_save.click();
			
			Assert.assertTrue(driver.getCurrentUrl().toString().endsWith("save-carts")); 
			By cartItem = By.xpath("//h3[text()='"+date+"']");
			Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(cartItem),3)); 
			
			Thread.sleep(5000);
			((JavascriptExecutor) driver).executeScript("scroll(0,-600)");
			b2bPage.homepage_Signout.click();
			Thread.sleep(3000);
						
			// step 4 , logon the B2B store with another account(should under same dmu with above account), 
			//go to my saved cart history, to check the account can see the shared cart.
			Common.NavigateToUrl(driver, Browser, testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
			b2bPage.homepage_MyAccount.click();
			b2bPage.myAccountPage_viewCartHistory.click();
			
			Assert.assertTrue(driver.getCurrentUrl().toString().endsWith("save-carts")); 
			Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(cartItem),3));
			
			// step 5, click 'view' link on the cart
			By cartIDXpath = By.xpath("//h3[text()='" + date + "']/../../td[@data-title='Cart ID']//a");
			String cartID = driver.findElement(cartIDXpath).getText().toString();
			System.out.println("cartID is :"+ cartID);
			//driver.findElement(cartIDXpath).click();
			
			By viewLink = By.xpath("//h3[text()='" + date + "']/../../td[@data-title='viewCart']/a");
			Common.javascriptClick(driver, driver.findElement(viewLink));
			Thread.sleep(2000);
			
			//cart Detail is displayed
			Assert.assertTrue(driver.getCurrentUrl().toString().contains(cartID));
			// there is an open cart link 
			List<WebElement> list = driver.findElements(By.xpath("//*[@id='openCartButton']"));
			Assert.assertTrue(b2bPage.cartDetailsPage_openCart.isDisplayed() && list.size() == 1);
			
			// step 6, click on the 'open cart' button
			Common.javascriptClick(driver, b2bPage.cartDetailsPage_openCart);
			
			Thread.sleep(10000);
			// verify that cart is displayed in shopping cart
			Assert.assertTrue(driver.getCurrentUrl().toString().endsWith("cart"));
			verifyProduct();
			// step 7 , update quantity to 3 and update. click on Lenovo Checkout button.Check the price of “ORDER SUMMARY”
			b2bPage.cartPage_Quantity.clear();
			b2bPage.cartPage_Quantity.sendKeys("3");
			b2bPage.cartPage_QuantityUpdate.click();
			String cartTotalPrice = b2bPage.cartPage_TotalPrice.getText().toString();
			System.out.println("cartTotalPrice is :" + cartTotalPrice);
			b2bPage.cartPage_LenovoCheckout.click();
			Thread.sleep(3000);
			Assert.assertTrue(driver.getCurrentUrl().toString().endsWith("add-delivery-address"));
			
			String shippingSubTotal = b2bPage.shippingPage_subtotalPrice.getText().toString();
			float shippingSubTotal_float = string2Num(shippingSubTotal);
			Assert.assertTrue(cartTotalPrice.equals(shippingSubTotal));
			
			// step 8 , set the shipping address; Select Expedited Shipping if possible；
			if(Store.toLowerCase().equals("us")){
				B2BCommon.fillB2BShippingInfo(driver,b2bPage,"us","testFname","testLname","Georgia","1535 Broadway","New York","New York","10036-4077","2129450100");
			}else if(Store.toLowerCase().equals("au")){
				B2BCommon.fillB2BShippingInfo(driver,b2bPage,"au","testFname","testLname","adobe_global","62 Streeton Dr","RIVETT","Australian Capital Territory","2611","2123981900");
			}
			Common.javascriptClick(driver, 	b2bPage.shippingPage_expeditedShipping);
			
			Thread.sleep(10000);
			String deliveryPrice = b2bPage.shippingPage_deliveryPrice.getText().toString();
			float deliveryPrice_float = string2Num(deliveryPrice);
			System.out.println("deliveryPrice is :" + deliveryPrice);
			Assert.assertTrue(deliveryPrice.contains("$20"));
			
			String totalPrice = b2bPage.shippingPage_totalPrice.getText().toString();
			float totalPrice_float = string2Num(totalPrice);
			
			float sumTotal = shippingSubTotal_float + deliveryPrice_float;
			System.out.println("sumTotal is :" + sumTotal);
			System.out.println("totalPrice_float is :" + totalPrice_float);
			Assert.assertTrue(totalPrice_float == sumTotal);
			
			//b2bPage.shippingPage_ContinueToPayment.click();
			WebElement continue_ShippingPage = driver.findElement(By.xpath(".//*[@id='checkoutForm-shippingContinueButton']"));
			JavascriptExecutor js = (JavascriptExecutor)driver;
			//js.executeScript("args[0].click();", driver.findElement(By.xpath(".//*[@id='checkoutForm-shippingContinueButton']")));
			js.executeScript("arguments[0].click()", continue_ShippingPage);
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.elementToBeClickable(b2bPage.paymentPage_ContinueToPlaceOrder));
			Thread.sleep(2000);
//			if(Common.checkElementExists(driver, b2bPage.shippingPage_validateFromOk,3)){
//				b2bPage.shippingPage_validateFromOk.click();
//			}
			if(Common.isElementExist(driver, By.id("checkout_validateFrom_ok"))){
				b2bPage.shippingPage_validateFromOk.click();
			}
				
			// step 9, use credit card and select Card type with visa, input card num, data and security code
			Payment(driver,b2bPage,"Visa");
			Assert.assertTrue(driver.getCurrentUrl().endsWith("summary"));
			// step 10,check the checkbox of condition&terms and click 'place order'
			if(Common.isElementExist(driver, By.xpath("//*[@id='resellerID']"))){
				b2bPage.placeOrderPage_ResellerID.clear();
				b2bPage.placeOrderPage_ResellerID.sendKeys("2900718028");
	
			}
			
			Common.javascriptClick(driver, b2bPage.placeOrderPage_Terms);
			b2bPage.placeOrderPage_PlaceOrder.click();
		
			Thread.sleep(10000);
			Assert.assertTrue(driver.getCurrentUrl().toString().contains("Confirmation"));
			
			List<WebElement> list_verify = driver.findElements(By.xpath("//tbody[@class='checkout-confirm-orderSummary-table-content']/tr"));
	
			Assert.assertTrue(list_verify.size() == 2);

			// step 11 , Check the Order Status and Price in HMC 
			
			orderNumber = driver.findElement(By.xpath("//td[text()='Order number:']/../td[2]")).getText().toString().trim();
			System.out.println("orderNumber IS :" + orderNumber);
//			float taxes = (float) 0.00;
//			List<WebElement> TaxList = driver.findElements(By.xpath("//td[text()='Order number:']/../td[2]"));
//			if(TaxList.size() == 0){
//				System.out.println("NO taxes");
//				taxes = (float) 0.00;
//			}else{
//				String Taxes = driver.findElement(By.xpath("//td[contains(.,'Taxes')]/../td[2]")).getText().toString().trim();
//				taxes = string2Num(Taxes);
//				System.out.println("taxes is :" + taxes);
//			}
			
			String HMCTotalPrice = driver.findElement(By.xpath("//td[contains(.,'Subtotal')]/../td[2]")).getText().toString().trim();
			
			float hmctotalPrice = string2Num(HMCTotalPrice);
			hmctotalPrice = hmctotalPrice + Float.parseFloat("20");
			DecimalFormat decimalFormat=new DecimalFormat(".00");
			
			HMCTotalPrice_1 = decimalFormat.format(hmctotalPrice);
			System.out.println("on order page , HMCTotalPrice_1 is  :" + HMCTotalPrice_1);
			b2bPage.homepage_Signout.click();
			Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			driver.navigate().refresh();
			
			HMCOrderCheck();
			hmcPage.Home_closeSession.click();
			
			// step 12,go to My account->My saved cart history
			Common.NavigateToUrl(driver, Browser, testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
			
			b2bPage.homepage_MyAccount.click();
			b2bPage.myAccountPage_viewCartHistory.click();
			Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(cartItem),3)); 
			// step 13, click on view link on the cart, go to cart detail page and click on open cart button
			Common.javascriptClick(driver, driver.findElement(viewLink));
			Thread.sleep(5000);
			Common.javascriptClick(driver, b2bPage.cartDetailsPage_openCart);
			Thread.sleep(10000);
			Assert.assertTrue(driver.getCurrentUrl().toString().endsWith("cart"));
			
			// step 14, Save Cart Again 
			Common.javascriptClick(driver, b2bPage.cartPage_saveCartButton);
		
			b2bPage.cartPage_companySaveCartButton.click();
			b2bPage.cartPage_SaveCart_cartNameField.clear();
			b2bPage.cartPage_SaveCart_cartNameField.sendKeys(date + "today");
			b2bPage.cartPage_SaveCart_save.click();
			
			// step 18 , navigate to myAccount-->my saved cart history
			Assert.assertTrue(driver.getCurrentUrl().toString().endsWith("save-carts")); 
			Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(cartItem),3)); 
			
		}catch(Throwable e){
			handleThrowable(e, ctx);
		}
	}
	
	public void HMCOrderCheck() throws Exception{
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        By byLocators01 = By.xpath("//div[contains(text(),'Total Price:')]/../../td[5]//input");
        Thread.sleep(5000);
        hmcPage.Home_Order.click();
        hmcPage.Home_Order_Orders.click();
        hmcPage.Home_Order_OrderID.clear();
        hmcPage.Home_Order_OrderID.sendKeys(orderNumber);
        hmcPage.Home_Order_OrderSearch.click();
        Thread.sleep(5000);
        Assert.assertTrue(hmcPage.Home_Order_OrderStatus.getText().contains("Completed"));
        hmcPage.Home_Order_OrderDetail.click();
        //hmcPage.OrderReload.click();
        Thread.sleep(5000);
        System.out.println("Order Status is Completed");
        String totalPrice_HMC = driver.findElement(byLocators01).getAttribute("value").toString();
        totalPrice_HMC = totalPrice_HMC.replace(",", "");
        System.out.println("totalPrice_HMC is :" + totalPrice_HMC);
        Assert.assertTrue(HMCTotalPrice_1.equals(totalPrice_HMC));
               
    }
	
	public void HMCSearchB2BUnit(){
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BUnitLink.click();
		hmcPage.B2BUnit_SearchIDTextBox.clear();
		System.out.println("B2BUNIT IS :" + testData.B2B.getB2BUnit());
		hmcPage.B2BUnit_SearchIDTextBox.sendKeys(testData.B2B.getB2BUnit());
		hmcPage.B2BUnit_SearchButton.click();
		hmcPage.B2BUnit_ResultItem.click();
		hmcPage.B2BUnit_siteAttribute.click();
	}
	
	public boolean isElementExsit(WebDriver driver, By locator) {
		boolean flag = false;
		try {
			WebElement element = driver.findElement(locator);
			flag = null != element;
		} catch (NoSuchElementException e) {
			// System.out.println( "this category has only one page");
		}
		return flag;
	}
	
	public void verifyProduct(){
		try{
			Thread.sleep(5000);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		List<WebElement> list = driver.findElements(By.xpath("//*[@id='mainContent']/div/div/div[@class='cart-item']"));
		
		Assert.assertTrue(list.size() == 2);
		
		
		System.out.println("verify products");
	}
	
	
	
	public float string2Num(String str){
		str = str.replace("$", "").replace(",", "");
		float str_1 = Float.parseFloat(str);
		return str_1;
	}
	
	public void Payment(WebDriver driver,B2BPage b2bPage,String paymentMethod){
		if (paymentMethod.equals("Visa")) {
			try{
				Thread.sleep(3000);
			}catch(Exception e){
				e.printStackTrace();
			}
			if(Common.isElementExist(driver, By.xpath("//*[@id='PaymentTypeSelection_CARD']"))){
				Actions actions = new Actions(driver);
				actions.moveToElement(b2bPage.paymentPage_creditCardRadio).click().perform();
				try{
					Thread.sleep(3000);
				}catch(Exception e){
					e.printStackTrace();
				}
				B2BCommon.creditCardPayment(driver, b2bPage);

				try{
					Thread.sleep(6000);
				}catch(Exception e){
					e.printStackTrace();
				}
				if (b2bPage.paymentPage_purchaseNum.isDisplayed() && b2bPage.paymentPage_purchaseNum.isEnabled()) {
					b2bPage.paymentPage_purchaseNum.sendKeys("123456");
					Common.sleep(2000);
					
					b2bPage.paymentPage_purchaseDate.click();
					
					Calendar CD = Calendar.getInstance();
					String DD = CD.get(Calendar.DATE) + "";;
					
					driver.findElement(By.xpath("//*[@id='ui-datepicker-div']//a[text()='"+DD+"']")).click();
				}
			}else{
				b2bPage.paymentPage_PurchaseOrder.click();
				Common.sleep(2000);
				b2bPage.paymentPage_purchaseNum.sendKeys("1234567890");
				b2bPage.paymentPage_purchaseDate.sendKeys(Keys.ENTER);
			}
			
			
			System.out.println("payment paied");
			
			b2bPage.paymentPage_FirstName.clear();
			b2bPage.paymentPage_FirstName.sendKeys("testFname");
			
			b2bPage.paymentPage_LastName.clear();
			b2bPage.paymentPage_LastName.sendKeys("testLname");
			
			/*
			 * if(Store.toLowerCase().equals("us")){
				fillB2BShippingInfo("test","test","Georgia","1535 Broadway","New York","New York","10036-4077","2129450100");
			}else if(Store.toLowerCase().equals("au")){
				fillB2BShippingInfo("test","test","adobe_global","62 Streeton Dr","RIVETT","Australian Capital Territory","2611","2123981900");
			}
			 * 
			 * */
			if(b2bPage.paymentPage_addressLine1.isEnabled()){
				b2bPage.paymentPage_addressLine1.clear();
				if(Store.toLowerCase().equals("us")){
					b2bPage.paymentPage_addressLine1.sendKeys("1535 Broadway");
				}else if(Store.toLowerCase().equals("au")){
					b2bPage.paymentPage_addressLine1.sendKeys("1535 Broadway");
				}
				
			}
			if(b2bPage.paymentPage_cityOrSuburb.isEnabled()){
				b2bPage.paymentPage_cityOrSuburb.clear();
				if(Store.toLowerCase().equals("us")){
					b2bPage.paymentPage_cityOrSuburb.sendKeys("New York");
				}else if(Store.toLowerCase().equals("au")){
					b2bPage.paymentPage_cityOrSuburb.sendKeys("RIVETT");
				}
			}
			if(b2bPage.paymentPage_addressState.isEnabled()){
				b2bPage.paymentPage_addressState.click();
				if(Store.toLowerCase().equals("us")){
					driver.findElement(By.xpath(".//*[@id='address.region']/option[contains(.,'New York')]")).click();
				}else if(Store.toLowerCase().equals("au")){
					driver.findElement(By.xpath(".//*[@id='address.region']/option[contains(.,'Australian Capital Territory')]")).click();
				}
			}
			if(b2bPage.paymentPage_addressPostcode.isEnabled()){
				b2bPage.paymentPage_addressPostcode.clear();
				if(Store.toLowerCase().equals("us")){
					b2bPage.paymentPage_addressPostcode.sendKeys("10036-4077");
				}else if(Store.toLowerCase().equals("au")){
					b2bPage.paymentPage_addressPostcode.sendKeys("2611");
				}
			}
			b2bPage.paymentPage_Phone.clear();
			b2bPage.paymentPage_Phone.sendKeys("1234567890");
			try{
				Thread.sleep(6000);
			}catch(Exception e){
				e.printStackTrace();
			}
			b2bPage.paymentPage_ContinueToPlaceOrder.click();
			(new WebDriverWait(driver, 500)).until(ExpectedConditions.visibilityOf(b2bPage.placeOrderPage_PlaceOrder));
		}
	}
	
}