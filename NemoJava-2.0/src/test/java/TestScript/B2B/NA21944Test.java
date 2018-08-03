package TestScript.B2B;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA21944Test extends SuperTestClass{
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public String homePageUrl;
	ArrayList<String> list;
	private String partNo;
	private String pdpURL;
	private String cartID;
	private String orderNO;
	
	public String homepageUrl;
	public String cartUrl;
	
	public NA21944Test(String store){
		this.Store= store;
		this.testName="NA-21944";
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2b"})
	public void NA21944(ITestContext ctx){
		try{
			this.prepareTest();
			b2bPage= new B2BPage(driver);
			hmcPage= new HMCPage(driver);
			
			//Login US URL with approver account
			Dailylog.logInfoDB(1,"Login URL with approver account" + Store.toLowerCase(),Store,testName);
			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getApproverId(), testData.B2B.getDefaultPassword());
			Thread.sleep(1000);
			
			homepageUrl = driver.getCurrentUrl().toString();
			cartUrl = homepageUrl + "cart";
		   			
		    //Clear products in cart
		    Dailylog.logInfoDB(2,"Clear Cart" + Store.toLowerCase(),Store,testName);
			b2bPage.HomePage_CartIcon.click();
			if(Common.isElementExist(driver, By.xpath("//a[contains(text(),'Empty cart')]"))){
				b2bPage.cartPage_emptyCartButton.click();
			}
			
			//Select a valid product and add it to cart
			Dailylog.logInfoDB(3,"Add a product to cart" + Store.toLowerCase(),Store,testName);
			b2bPage.HomePage_productsLink.click();
			b2bPage.HomePage_Laptop.click();
			b2bPage.productPage_agreementsAndContract.click();
			Thread.sleep(1000);
			//If have agreement product, Add it to cart
			if(Common.isElementExist(driver, By.xpath("//label[contains(.,'Agreement')]/input"))){
					System.out.println("Have agreement products");
					b2bPage.Laptops_agreementChk.click();
					Thread.sleep(1000);
					
					int viewDetailsNo = b2bPage.PLPPage_viewDetails.size();
					for (int i = 1; i <= viewDetailsNo; i++) {
						driver.findElement(By.xpath("(.//a[contains(.,'View Details')])[" + i + "]")).click();
						if (isAlertPresent()) {
							System.out.println(driver.switchTo().alert().getText() + " Try next product!");
							driver.switchTo().alert().accept();

						} else if (driver.getTitle().contains("Not Found")) {
							System.out.println("Product Not Found, Try next product!");

						} else
							break;
					}
					pdpURL = driver.getCurrentUrl();
					System.out.println("PDP URL: " + pdpURL);
					// get product number
					partNo = pdpURL.substring(pdpURL.lastIndexOf('/') + 1, pdpURL.length());
					System.out.println("Product Number: " + partNo);
					Thread.sleep(1000);
					
					if (Common.isElementExist(driver,
							By.xpath("//*[@id='b_alert_add_to_cart' or @id='b_marning_add_to_cart']"))) {
						Thread.sleep(500);
						if (b2bPage.PDPPage_agreementAddToCartOnPopup.isDisplayed())
							((JavascriptExecutor) driver).executeScript("arguments[0].click();",
									b2bPage.PDPPage_agreementAddToCartOnPopup);
						else {
							b2bPage.Agreement_agreementsAddToCart.click();
							b2bPage.HomePage_CartIcon.click();
						}
					} else {
						// add product into cart
						b2bPage.Agreement_agreementsAddToCart.click();
						b2bPage.HomePage_CartIcon.click();
					}
					
			}else{
				driver.findElement(
						By.xpath("(//*[contains(@id,'addToCartForm')]/button)[1]"))
						.click();
				(new WebDriverWait(driver, 20))
						.until(ExpectedConditions
								.elementToBeClickable(b2bPage.productPage_AlertAddToCart));

				b2bPage.productPage_AlertAddToCart.click();
				(new WebDriverWait(driver, 20))
						.until(ExpectedConditions.invisibilityOfElementLocated(By
								.xpath("//h2[contains(text(),'Adding to Cart')]")));
				driver.get(cartUrl);
			}					
		    // checkout products
			Dailylog.logInfoDB(4,"Click checkout" + Store.toLowerCase(),Store,testName);
			b2bPage.HomePage_CartIcon.click();
			driver.findElement(By.id("validateDateformatForCheckout")).click();
			
			// Fill shipping info
			Dailylog.logInfoDB(5,"Fill shipping info" + Store.toLowerCase(),Store,testName);
			B2BCommon.fillB2BShippingInfo(driver,b2bPage,"us","Alberto","Costantini","Georgia","620 Broadway","Everett","Massachusetts","02149","8572779690");
			Thread.sleep(2000);
			b2bPage.shippingPage_ContinueToPayment.click();
			Thread.sleep(2000);
			if(Common.isElementExist(driver, By.id("checkout_validateFrom_ok"))){
				b2bPage.shippingPage_validateFromOk.click();
			}
						
			//Fill payment info
			Dailylog.logInfoDB(6,"Fill payment info on " + Store.toLowerCase(),Store,testName);
			B2BCommon.creditCardPayment(driver, b2bPage);
			
			B2BCommon.fillDefaultB2bBillingAddress(driver, b2bPage, testData);
			
			//check the checkBox of condition&terms and click 'place order'
			Dailylog.logInfoDB(7,"check the checkBox and place order " + Store.toLowerCase(),Store,testName);
			if(Common.isElementExist(driver, By.xpath("//*[@id='resellerID']"))){
				b2bPage.placeOrderPage_ResellerID.clear();
				b2bPage.placeOrderPage_ResellerID.sendKeys("2900718028");				
				}
						
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", b2bPage.placeOrderPage_Terms);
			b2bPage.placeOrderPage_PlaceOrder.click();
			Assert.assertTrue(driver.getCurrentUrl().toString().contains("ocrSoftHitConfirmation"));			
			cartID = driver.findElement(By.xpath("//td[text()='Cart ID:']/../td[2]")).getText().toString().trim();
			System.out.println("cart ID IS :" + cartID);
			
			//Sign out and login with teleSales account
			Dailylog.logInfoDB(8,"Signout and login with telesale account " + Store.toLowerCase(),Store,testName);
			b2bPage.homepage_Signout.click();
			B2BCommon.Login(b2bPage, testData.B2B.getTelesalesId(), testData.B2B.getDefaultPassword());
			//Go to my account page and click "Start Assisted Service Session" link
			b2bPage.homepage_MyAccount.click();
			b2bPage.MyAccountPage_StartAssSerSession.click();
			//input the teleSales user name and password to login ASM
			B2BCommon.loginASM(driver, b2bPage, testData);
			
			//Click DPL Report 
			Dailylog.logInfoDB(9,"DPL search" + Store.toLowerCase(),Store,testName);
			Thread.sleep(5000);
			driver.findElement(By.xpath("//button[@id='asmDplReportButton']")).click();
			Thread.sleep(20000);
			//Search with CarID and store 
			driver.findElement(By.xpath("//select[@name='orderType']/option[contains(.,'Cart')]")).click();
			driver.findElement(By.xpath("//input[@name='code']")).sendKeys(cartID);
			
			Select select1 = new Select(driver.findElement(By.xpath("(//select[@name='storeType'])[last()]")));
			select1.selectByValue("b2b");
			
			driver.findElement(By.xpath("//button[contains(.,'Search')]")).click();
			Thread.sleep(5000);
			//Reject cart on DPL 
			Dailylog.logInfoDB(10,"Reject then Accept cart" + Store.toLowerCase(),Store,testName);
			List<WebElement> minPrices = driver.findElements(By.xpath("//tbody[@id='dplReportTableBody']/tr[contains(@id,'row')]"));
			Assert.assertEquals(minPrices.size(), 1);
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//td[text()='PENDING']")),
					"Check OCR status should be pending");
			
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//span[contains(@class,'glyphicon-ok')]")),
					"correct icon should be display");
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//span[contains(@class,'glyphicon-remove')]")),
					"x icon should be display");
			driver.findElement(By.xpath("//span[contains(@class,'glyphicon-remove')]")).click();
			
			driver.findElement(By.xpath("//button[contains(.,'Search')]")).click();
			Thread.sleep(5000);
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//td[text()='REJECT']")),
					"Check OCR status should be reject");
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//span[contains(@class,'glyphicon-ok')]")),
					"correct icon should be display");
			Assert.assertFalse(Common.isElementExist(driver, By.xpath("//span[contains(@class,'glyphicon-remove')]")),
					"x icon should not be display");
			System.out.println("Accept");
			//Accept Cart on DPL
			driver.findElement(By.xpath("//span[contains(@class,'glyphicon-ok')]")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//button[contains(.,'Search')]")).click();
			Thread.sleep(5000);
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//td[text()='APPROVE']")),
					"Check OCR status should be approve");
			Assert.assertFalse(Common.isElementExist(driver, By.xpath("//span[contains(@class,'glyphicon-ok')]")),
					"correct icon should be display");
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//span[contains(@class,'glyphicon-remove')]")),
					"x icon should be display");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//button[@id='cboxClose']")).click();
			
			//Copy Cart ID to transaction 
			Dailylog.logInfoDB(11,"Copy Cart ID to transaction ID" + Store.toLowerCase(),Store,testName);
			new WebDriverWait(driver,500).until(ExpectedConditions.presenceOfElementLocated(By.
					xpath("//input[@name='transactionId']")));
			Thread.sleep(5000);
			driver.findElement(By.xpath("//input[@name='transactionId']")).click();
			driver.findElement(By.xpath("//input[@name='transactionId']")).sendKeys(cartID);
			
			new WebDriverWait(driver,500).until(ExpectedConditions.presenceOfElementLocated(By.
					cssSelector("[id^='ui-id-']>li>a")));
			driver.findElement(By.cssSelector("[id^='ui-id-']>li>a")).click();
			Thread.sleep(3000);
			
	        // Start session and open cart then place order
			Dailylog.logInfoDB(12,"Start session and open cart then place order" + Store.toLowerCase(),Store,testName);
			b2bPage.MyAccountPage_StartSessionButton.click();
			Thread.sleep(5000);
			Assert.assertTrue(driver.getCurrentUrl().toString().contains("save-cart"));
			
			b2bPage.cartDetailsPage_openCart.click();
			Thread.sleep(3000);
			driver.findElement(By.id("validateDateformatForCheckout")).click();
			
			// Fill shipping info
			B2BCommon.fillB2BShippingInfo(driver,b2bPage,"us","Alberto","Costantini","Georgia","620 Broadway","Everett","Massachusetts","02149","8572779690");
			Thread.sleep(2000);
			b2bPage.shippingPage_ContinueToPayment.click();
			Thread.sleep(2000);
			if(Common.isElementExist(driver, By.id("checkout_validateFrom_ok"))){
					b2bPage.shippingPage_validateFromOk.click();
				}
									
			//Fill payment info						
			B2BCommon.creditCardPayment(driver, b2bPage);
			
			B2BCommon.fillDefaultB2bBillingAddress(driver, b2bPage, testData);
			//check the checkBox of condition&terms and click 'place order'
			if(Common.isElementExist(driver, By.xpath("//*[@id='resellerID']"))){
				b2bPage.placeOrderPage_ResellerID.clear();
				b2bPage.placeOrderPage_ResellerID.sendKeys("2900718028");				
				}
									
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", b2bPage.placeOrderPage_Terms);
			b2bPage.placeOrderPage_PlaceOrder.click();
			
			Assert.assertTrue(driver.getCurrentUrl().toString().contains("orderConfirmation"));			
			orderNO=b2bPage.placeOrderPage_OrderNumber.getText().toString().trim();
			System.out.println("Order Number IS :" + orderNO);
			
			//Check order status in HMC
			Dailylog.logInfoDB(16, "Check the order statues in HMC",Store,testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.HMCOrderCheck(hmcPage, orderNO);
								
			}catch(Throwable e){
				handleThrowable(e,ctx);
			}
	}
	
	public boolean isAlertPresent(){
		try{
				WebDriverWait wait= new WebDriverWait(driver,15);
				wait.until(ExpectedConditions.alertIsPresent());
				return true;
			
		}catch(TimeoutException e) {
			return false;
		}
	}

}
