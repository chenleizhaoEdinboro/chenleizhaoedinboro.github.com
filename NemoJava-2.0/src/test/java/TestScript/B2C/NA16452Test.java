package TestScript.B2C;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA16452Test extends SuperTestClass {
	String ProductID = null;
	B2CPage b2cPage = null;
	HMCPage hmcPage = null;
	Actions actions = null;
	String orderNum = null;
	static String url;

	public NA16452Test(String store) {
		this.Store = store;
		this.testName = "NA-16452";
	}



	@Test(alwaysRun= true)
	public void NA16452(ITestContext ctx) {
		try {

			this.prepareTest();
			b2cPage = new B2CPage(driver);
			actions = new Actions(driver);
			setRequestQuote(Store);
			
			Common.NavigateToUrl(driver,Browser,testData.B2C.getHomePageUrl());
//			driver.manage().deleteAllCookies();
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Thread.sleep(2000);
			Common.NavigateToUrl(driver,Browser,testData.B2C.getHomePageUrl()+"/login");
			if(Common.isElementExist(driver, By.xpath("//button[contains(@class,'mfp-close')]"))){
				driver.findElement(By.xpath("//button[contains(@class,'mfp-close')]")).click();
			}
			if(Store=="US_SMB") {
				driver.findElement(By.xpath(".//*[@id='smb-login-button']")).click();
				if(Common.isElementExist(driver, By.xpath(".//*[@id='login.email.id']"))) {
					driver.findElement(By.xpath(".//*[@id='login.email.id']")).clear();
					driver.findElement(By.xpath(".//*[@id='login.email.id']")).sendKeys(testData.B2C.getLoginID());
					driver.findElement(By.xpath(".//*[@id='login.password']")).sendKeys("1q2w3e4r");
					driver.findElement(By.xpath(".//*[@id='nemoLoginForm']/div[5]/button")).click();
					Common.sleep(3000);
				}
			}else {				
				B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
			}
			
			Thread.sleep(5000);
			Common.NavigateToUrl(driver,Browser,B2CCommon.getCTOProduct(testData.B2C.getHomePageUrl(),Store));
			Thread.sleep(3000);


			Dailylog.logInfo(B2CCommon.getCTOProduct(testData.B2C.getHomePageUrl(),Store));
			Thread.sleep(5000);
			if(Store=="CO" || Store== "BR"){
				ProductID=isStoreCoAddToCart();
			}else{				
				addToCart();
			}
			
			if(Store=="GB" || Store=="IE" || Store=="FR"|| Store=="BR" ||Store=="CO") {//DRpayment
				orderNum =B2CCommon.placeOrderFromClickingStartCheckoutButtonInCart(driver,b2cPage,testData,Store);
				Dailylog.logInfo("orderNum:"+orderNum);
				checkOrderStatus();
			}else {
				requestQuote();				
				//shipping
				if(Common.isElementExist(driver, By.cssSelector(".textLink.checkout-shippingAddress-editLink"))){
						//b2cPage.Shipping_editAddress.click();
					Common.sleep(8000);
					if(Common.isElementExist(driver, By.cssSelector(".textLink.checkout-shippingAddress-editLink"), 3)) {
						try {
							driver.findElement(By.cssSelector(".textLink.checkout-shippingAddress-editLink")).click();
						} catch (Exception e) {
						}						
					}
						B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
						Thread.sleep(3000);
						actions.sendKeys(Keys.PAGE_UP).perform();
						try {
							b2cPage.Shipping_ContinueButton.click();
						} catch (Exception e) {
							if(Common.isElementExist(driver, By.xpath(".//*[@id='mainContent']/div[1]/div/div[1]/div[4]/button"), 3))
								driver.findElement(By.xpath(".//*[@id='mainContent']/div[1]/div/div[1]/div[4]/button")).click();
						}
				}
				if(Common.isElementExist(driver, By.id("checkout_validateFrom_skip"), 3)) {
					driver.findElement(By.id("checkout_validateFrom_skip")).click();
				}
				
				if (Common.checkElementExists(driver, b2cPage.Shipping_validateAddressItem, 3))
					b2cPage.Shipping_validateAddressItem.click();
				if (Common.checkElementExists(driver, b2cPage.Shipping_validateAddress, 3))
					b2cPage.Shipping_validateAddress.click();
				Thread.sleep(3000);
					selectPayment();

					if(Common.isElementExist(driver, By.id("Terms1"), 3))
						try {
							b2cPage.OrderSummary_AcceptTermsCheckBox.click();
						} catch (Exception e) {
							driver.findElement(By.xpath(".//*[@id='placeOrderForm1']/div[1]/label[1]")).click();
						}

											
					B2CCommon.clickPlaceOrder(b2cPage);
					try {
						orderNum = b2cPage.OrderThankyou_OrderNumberLabel.getText();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						orderNum = driver.findElement(By.xpath(".//*[@class='thankYouForYourOrder']/div[1]/div[2]/span[2]")).getText();
					}
					Dailylog.logInfoDB(1, "orderNum:"+orderNum, Store, testName);
					checkOrderStatus();

			}
			//EMailCommon.checkConfirmationEmail(driver, testData.B2C.getLoginID(), orderNum);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}


	private void selectPayment() throws InterruptedException {
		// TODO Auto-generated method stub
		Boolean b;
		b=Common.checkElementExists(driver, b2cPage.Payment_paypalRadioBox, 10);
			
		
		if (Store=="AU" ||Store=="US" ||Store=="NZ" && b) {
			Thread.sleep(3000);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Payment_paypalRadioBox);
			//b2cPage.Payment_paypalRadioBox.click();
			b2cPage.Payment_ContinueButton.click();
			driver.switchTo().frame(driver.findElement(By.name("injectedUl")));
			b2cPage.Payment_paypalLoginID.clear();
			b2cPage.Payment_paypalLoginID.sendKeys("accept@lenovo.com");
			b2cPage.Payment_paypalLoginPwd.clear();
			b2cPage.Payment_paypalLoginPwd.sendKeys("Hybris@sap");
			b2cPage.Payment_paypalSignBtn.click();
			driver.switchTo().defaultContent();
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Common.waitElementClickable(driver, b2cPage.Payment_paypalContinueBtn, 3);
			Thread.sleep(3000);
			b2cPage.Payment_paypalContinueBtn.click();
		} else if (Store=="CO"){
			driver.findElement(By.xpath("//*[@id='PaymentTypeSelection_Wire']")).click();
			driver.findElement(By.id("add-payment-method-continue")).click();
		}
		else if(Store=="BR"){
			//PaymentTypeSelection_PAYU
			driver.findElement(By.id("PaymentTypeSelection_BANKDEPOSIT")).click();
			driver.findElement(By.id("add-payment-method-continue")).click();
		}
		else {
			try {
				B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
				if(Common.isElementExist(driver, By.xpath(".//*[@id='mainContent']/div[1]/div/div[1]/div[4]/button"), 3))
					driver.findElement(By.xpath(".//*[@id='mainContent']/div[1]/div/div[1]/div[4]/button")).click();
				if(Common.isElementExist(driver, By.id("add-payment-method-continue"), 3))
					driver.findElement(By.id("add-payment-method-continue")).click();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		B2CCommon.handleVisaVerify(b2cPage);//handle USEPP
	}

	public void requestQuote() {
		// TODO Auto-generated method stub
		String quoteNum = null;
		B2CCommon.clickRequestQuote(b2cPage);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			b2cPage.Quote_RepIDTextBox.clear();
			b2cPage.Quote_RepIDTextBox.sendKeys(testData.B2C.getRepID());
		} catch (Exception e3) {
			Dailylog.logInfo("Dont sendkeys requestQuote");
		}
		Common.waitElementClickable(driver, b2cPage.Quote_submitQuoteBtn, 5);
		
		b2cPage.Quote_submitQuoteBtn.click();
		quoteNum = b2cPage.Quote_quoteNum.getText();
		

		//my account
//		if(Common.isElementExist(driver, By.xpath("//ul[contains(@class,'menu general')]/li[contains(@class,'myaccount')]/a"))){
//			b2cPage.Navigation_MyAccountIcon.click();
//			b2cPage.Navigation_subMyAccountSpan.click();
//		}else{
//			Common.NavigateToUrl(driver,Browser,testData.B2C.getHomePageUrl()+"/my-account");
//		}
		Common.NavigateToUrl(driver,Browser,testData.B2C.getHomePageUrl()+"/my-account");
		//my account quote
		if(Common.isElementExist(driver,By.linkText("View my saved quote"))){
			driver.findElement(By.linkText("View my saved quote")).click();
		}else{
			Common.NavigateToUrl(driver,Browser,testData.B2C.getHomePageUrl()+"/my-account/quotes");			
		}
		
		
		b2cPage.Quote_searchTextBox.clear();
		b2cPage.Quote_searchTextBox.sendKeys(quoteNum);
		Common.sleep(3000);
		b2cPage.Quote_searchSubmitBtn.click();
		//b2cPage.Quote_viewMoreBtn.click();
		driver.findElement(By.xpath("//*[@id='accountQuote-content']/div[3]/table/tbody/tr[1]/td[9]/p/a")).click();
		b2cPage.Quote_convertOrder.click();
	}

	public String findCTOProduct() throws InterruptedException {
		String b2cProductUrl = driver.getCurrentUrl();
		ProductID = b2cProductUrl.split("/p/")[1].split("#")[0].substring(0, 15);
		System.out.println(ProductID);
		return b2cProductUrl;
	}

	public void addToCart() throws InterruptedException {
		ProductID = findCTOProduct();
		//driver.findElement(By.xpath("(.//*[@id='view-customize'])[1]")).click();
		// to
		// cart
		Thread.sleep(10000);

		
		Common.NavigateToUrl(driver,Browser,driver.getCurrentUrl()+"/customize?");
		Thread.sleep(3000);
		//isAlertPresent();
		Thread.sleep(12000);
		

		int a=0;
		while(!Common.isElementExist(driver, By.xpath(".//*[@id='render-summary']//span[@id='CTO_addToCart']")) 
				&& a<2 ){
			Common.NavigateToUrl(driver,Browser,B2CCommon.getCTOProduct(testData.B2C.getHomePageUrl(),Store));			
			Thread.sleep(8000);
			driver.findElement(By.xpath("(.//*[@id='view-customize'])[1]")).click();
			Thread.sleep(4000);
			Common.NavigateToUrl(driver,Browser,driver.getCurrentUrl()+"/customize?");
			Thread.sleep(8000);
			a++;																							
		}
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", driver.findElement(By.xpath(".//*[@id='render-summary']//span[@id='CTO_addToCart']")));	

		Thread.sleep(5000);
		int b=0;
		while(!Common.isElementExist(driver, By.xpath("//*[@id='product-builder-form']/descendant::div/button")) 
				&& b<2 ){
			driver.navigate().refresh();
			driver.findElement(By.xpath(".//*[@id='render-summary']//span[@id='CTO_addToCart']")).click();
			b++;																							
		}
		
		Thread.sleep(8000);

		int continueBtnNum = driver.findElements(By.xpath("//ol//a//span")).size() - 3;
		for (int i = 0; i < continueBtnNum; i++) {
			if(Common.isElementExist(driver, By.id("CTO_addToCart"))){
				driver.findElement(By.id("CTO_addToCart")).click();
			}else if(Common.isElementExist(driver, By.xpath("//*[@id='product-builder-form']/descendant::div/button"))){
				//b2cPage.Product_Continue.click();
				js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[@id='product-builder-form']/descendant::div/button")));	
			}
		}
		
		Thread.sleep(3000);
		
		if (Common.checkElementExists(driver, b2cPage.Product_Productbuilder_AddToCartBtn, 3))
			//b2cPage.Product_Productbuilder_AddToCartBtn.click();
			js.executeScript("arguments[0].click();", driver.findElement(By.cssSelector(".pricingSummary-button.button-called-out.button-full")));	
	}

	public void saveCart() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String cartName = sdf.format(date);
		b2cPage.Cart_saveCart.click();
		b2cPage.Cart_nameTextBox.clear();
		b2cPage.Cart_nameTextBox.sendKeys(cartName);
		b2cPage.Cart_saveCartBtn.click();
		b2cPage.Cart_searchCartDropDown.sendKeys("Cart Name");
		b2cPage.Cart_searchTextBox.sendKeys(cartName);
		b2cPage.Cart_searchButton.click();
		b2cPage.Cart_viewCartHistory.click();
		b2cPage.Cart_openCart.click();
	}
	public boolean isAlertPresent(){ 
		if(driver.findElement(By.xpath(".//*[@id='bundleAlert']/div/div/div[1]/button")).isDisplayed()){
			driver.findElement(By.xpath(".//*[@id='bundleAlert']/div/div/div[1]/button")).click();
		}
		
        try  
        {  
            Alert alert = driver.switchTo().alert();  
            alert.accept();  
            return true;  
        }     
        catch (NoAlertPresentException Ex)  
        {  
            return false;  
        } 
	}

	private void checkOrderStatus() {
		// TODO Auto-generated method stub
		hmcPage = new HMCPage(driver);
		driver.manage().deleteAllCookies();
		Common.NavigateToUrl(driver,Browser,testData.HMC.getHomePageUrl());
		if(Common.isElementExist(driver, By.id("Main_user"))){		
			HMCCommon.Login(hmcPage, testData);
		}
		try {
			HMCCommon.HMCOrderCheck(hmcPage, orderNum);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setRequestQuote(String store) throws InterruptedException{
		hmcPage = new HMCPage(driver);
		
		Common.NavigateToUrl(driver,Browser,testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		
		//Quote Display
		hmcPage.Home_B2CCommercelink.click();
		hmcPage.Home_B2CUnitLink.click();
		hmcPage.B2CUnit_IDTextBox.sendKeys(testData.B2C.getUnit());	
		hmcPage.B2CUnit_SearchButton.click();
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		hmcPage.B2CUnit_SiteAttributeTab.click();
		driver.findElement(By.xpath(".//input[contains(@id,'/Attribute[B2CUnit.isQuoteDisplay]]_true')]")).click();
		driver.findElement(By.xpath("(.//input[contains(@id,'B2CUnit.enableB2CQuoteConvert')])[1]")).click();
		Dailylog.logInfo("set enable B2CQuote Convert");
		driver.findElement(By.xpath(".//div[contains(@id,'organizer.editor.save.title')]")).click();
		Dailylog.logInfo("set is Quote Display");
		
		//Quote Available
		Thread.sleep(5000);
		driver.findElement(By.xpath("//a[contains(.,'Base Commerce')]")).click();
		driver.findElement(By.xpath(".//*[@id='Tree/GenericLeafNode[BaseStore]_label']")).click();
		driver.findElement(By.xpath(".//input[contains(@id,'BaseStore.uid')]")).sendKeys(testData.B2C.getStore());
		driver.findElement(By.xpath(".//span[contains(@id,'_searchbutton')]")).click();
		//Common.doubleClick(driver, driver.findElement(By.xpath(".//div[contains(@id,'"+testData.B2C.getStore()+"]')]")));
		//driver.findElement(By.xpath(".//*[@id='Content/StringDisplay["+store+"]_span']")).click();
	    Common.doubleClick(driver, driver.findElement(By.xpath(".//*[@id='Content/StringDisplay["+testData.B2C.getStore()+"]_span']")));
		driver.findElement(By.xpath(".//span[contains(@id,'administration')]")).click();
		driver.findElement(By.xpath("(.//input[contains(@id,'BaseStore.isQuoteAvailable')])[1]")).click();
		driver.findElement(By.xpath(".//div[contains(@id,'organizer.editor.save.title')]")).click();
		Dailylog.logInfo("set is Quote Available");
	}
	public String  isStoreCoAddToCart() throws InterruptedException{
		Thread.sleep(8000);
		driver.findElement(By.xpath("(.//*[@id='view-customize'])[1]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("(.//*[@id='addToCartButtonTop'])[1]")).click();
		Thread.sleep(8000);
		int continueBtnNum = driver.findElements(By.xpath("//ol//a//span")).size() - 3;
		for (int i = 0; i < continueBtnNum; i++) {
			try {
				b2cPage.Product_Continue.click();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				driver.findElement(By.id("cta-builder-continue")).click();
			}
		}
		if(Common.isElementExist(driver, By.id("cta-builder-continue"))) {
			
			driver.findElement(By.id("cta-builder-continue")).click();
		}
		Thread.sleep(3000);
		
		if (Common.checkElementExists(driver, b2cPage.Product_Productbuilder_AddToCartBtn, 3))
			b2cPage.Product_Productbuilder_AddToCartBtn.click();
		Thread.sleep(5000);
		return driver.findElement(By.xpath(".//*[@id='editConfig']/p/span")).getText();
		
	}
}
