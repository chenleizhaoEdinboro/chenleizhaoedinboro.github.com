package TestScript.B2B;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import CommonFunction.B2BCommon.B2BRole;
import Pages.B2BPage;
import Pages.MailPage;
import TestData.TestData;
import TestScript.SuperTestClass;

public class NA17176Test extends SuperTestClass {
	
	String today=Common.getDateTimeString();
	String buyer;
	String approvera;
	String approverb;
	String buyerEmail;
	String approveraEmail;
	String approverbEmail;
	String quoteStatus;
	String quoteNum;
	String orderNum;
	boolean flag=true;
	boolean result=true;
	B2BPage b2bPage;
	MailPage mailPage;
	
	public NA17176Test(String store) {
		this.Store = store;
		this.testName = "NA-17176";
		buyer="buyer"+ Store + "17176" + today;
		//buyer="usbuyer";
		approvera="approvera" + Store + "17176" + today;
		approverb="approverb" + Store + "17176" + today;
		buyerEmail=buyer + "@sharklasers.com";
		//buyerEmail=buyer+"@yopmail.com";
		approveraEmail=approvera + "@sharklasers.com";
		approverbEmail=approverb + "@sharklasers.com";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "email", "p1", "b2b"})
	public void NA17176(ITestContext ctx) {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			mailPage = new MailPage(driver);
			
			//---- Creating  Accounts ----//
			createAccountAndCheckEmail(buyer, B2BRole.Buyer);
			createAccountAndCheckEmail(approvera, B2BRole.Approver);
			createAccountAndCheckEmail(approverb, B2BRole.Approver);
			
			//Login with buyer mail id and an order
			addLapTopsToCart();						
			b2bPage.cartPage_LenovoCheckout.click();
			
			orderNum=B2BCommon.placeAnOrder(driver, Store, b2bPage, testData);
	        //checkInbox("Order Confirmation", mailPage.Inbox_BackToStoreLink, buyer, "Order Confirmation "+orderNum, "/login" ,false);
			EMailCommon.createEmail(driver,mailPage,buyer);
			EMailCommon.checkEmailContentinAllEmail(driver, mailPage, "Order Confirmation", "//*[contains(text(),'"+ orderNum +"')]");
	        
	        /*----Request quote and then place the order----*/
	        //Login with buyer mail id to request quote to Approver-A, and approver the request
	        requestAndProcessQuote(approvera ,"approver" ,"");
	        checkInbox("Your Lenovo quote was approved!", mailPage.Inbox_BackToStoreLink, buyer, "Quote is approved!","/login" ,false );
	        //buyer converting the quote to order and placing order
			convertOrder();	
			String ifNeedApprover = "false";
			String [] orderInfo = sendForApproval(driver,Store,b2bPage,testData,approvera,ifNeedApprover);
			orderNum = orderInfo[0];
			ifNeedApprover = orderInfo[1];
			if(ifNeedApprover=="true") {
				//approver login
		        login(approveraEmail );
		        //approver or reject the request
		        B2BCommon.processOrder(driver,b2bPage, mailPage, "approver");
			}
			//buyer checking order email
			EMailCommon.createEmail(driver,mailPage,buyer);
			EMailCommon.checkEmailContentinAllEmail(driver, mailPage, "Order Confirmation", "//*[contains(text(),'"+ orderNum +"')]");			
			
			/*----Request quote and then reject the quote----*/
			//Login with builder mail id to request quote to Approver-A, and assign to Approver-B the request
			requestAndProcessQuote(approvera ,"assign" ,approverb);
			checkInbox("Quote Pending Approval", mailPage.Inbox_BackToStoreLink, approverb, "Quote pending approval email, , and quoteID is:"+quoteNum,"/login" ,false  );
			
			login(approverbEmail );
	        B2BCommon.processQuote(driver,b2bPage, mailPage, "reject","");
	        //buyer checking the quote rejected email
	        checkInbox("Your Lenovo quote was not approved", mailPage.Inbox_BackToStoreLink, buyer, "Quote is rejected!" ,"/login" ,false);			
	            
	        //Assert.assertEquals(true, result);
		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}
	
	public static String[] sendForApproval(WebDriver driver, String country,B2BPage b2bPage,TestData testData, String email, String ifNeedApprover){
		B2BCommon.fillB2BShippingInfo(driver, b2bPage,country, "FirstNameJohn", "LastNameSnow", 
				testData.B2B.getCompany(), testData.B2B.getAddressLine1(), testData.B2B.getAddressCity(), 
				testData.B2B.getAddressState(), testData.B2B.getPostCode(),testData.B2B.getPhoneNum());	
		b2bPage.shippingPage_ContinueToPayment.click();
		System.out.println("Go to Payment page!");
		if(Common.checkElementExists(driver, b2bPage.shippingPage_validateFromOk, 5)){
			b2bPage.shippingPage_validateFromOk.click();
		}
		b2bPage.paymentPage_PurchaseOrder.click();
		Common.sleep(2000);
		b2bPage.paymentPage_purchaseNum.sendKeys("1234567890");
		b2bPage.paymentPage_purchaseDate.sendKeys(Keys.ENTER);
		
		B2BCommon.fillDefaultB2bBillingAddress(driver, b2bPage, testData);
		
		System.out.println("Go to Order page!");
		if(Common.checkElementExists(driver, b2bPage.placeOrderPage_ResellerID, 60)){
			b2bPage.placeOrderPage_ResellerID.sendKeys("reseller@yopmail.com");
		}	
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.PAGE_UP).perform();
		b2bPage.placeOrderPage_Terms.click();
		if(Common.checkElementExists(driver, b2bPage.placeOrderPage_selectApprover, 20)){
			ifNeedApprover = "true";
			b2bPage.placeOrderPage_selectApprover.click();
			driver.findElement(By.xpath("//select/option[contains(.,'"+email.toUpperCase()+"@')]")).click();
			System.out.println("Choose "+email+"@SHARKLASERS.COM to approver!");
		}else {
			ifNeedApprover = "false";
		}
		b2bPage.placeOrderPage_sendForApproval.click();
		String orderNum=b2bPage.placeOrderPage_OrderNumber.getText();
        System.out.println("Order Number is: " + orderNum);
        String orderInfo[] = {orderNum, ifNeedApprover};
        return orderInfo;
	}
	
	public void requestAndProcessQuote(String approverAccount, String process, String anotherAprApproverount) throws InterruptedException{
		String approverEmail=approverAccount+"@sharklasers.com";
		//Login with buyer mail id, and requesting quote
		addLapTopsToCart();
		quoteNum=B2BCommon.requestQuote(driver,b2bPage, approverAccount);
		//buyer checking quote email-don't neet to check it
		//checkInbox("Lenovo Quote ID", mailPage.Inbox_BackToStoreLink, buyer, "Request quote email, and quoteID is:"+quoteNum,"/login",false );
		//approver checking quote email
		checkInbox("Quote Pending Approval", mailPage.Inbox_BackToStoreLink, approverAccount, "Quote pending approval email, , and quoteID is:"+quoteNum,"/my-account",true );	
		//approver account process quote
		login(approverEmail );
        B2BCommon.processQuote(driver,b2bPage, mailPage, process,anotherAprApproverount);		    
	}
	
	public void createAccountAndCheckEmail(String email,B2BRole role){
		String account=email+"@SHARKLASERS.COM";
		EMailCommon.createEmail(driver,mailPage,email);		
		B2BCommon.createAccount(driver ,testData.B2B.getHomePageUrl() ,testData.B2B.getB2BUnit() ,b2bPage, role, account, Browser);
		HMCCommon.activeAccount(driver,testData, account);
		checkInbox("Customer Registration", mailPage.Inbox_BackToLoginLink, email, "Customer Registration email !","/login",false );
	}     
	
	public void login(String loginAccount){
		driver.manage().deleteAllCookies();
		driver.get(testData.B2B.getHomePageUrl());
		if(Common.checkElementExists(driver, b2bPage.homepage_Signout, 5)){
			b2bPage.homepage_Signout.click();
		}
		B2BCommon.Login(b2bPage, loginAccount, "1q2w3e4r");
		if(Common.checkElementExists(driver, b2bPage.Login_EmailTextBox, 5)){
			B2BCommon.Login(b2bPage, loginAccount, "1q2w3e4r");
		}
		Common.sleep(2000);
	}
	
	public void convertOrder(){
		driver.manage().deleteAllCookies();
		driver.get(testData.B2B.getHomePageUrl());
		if(Common.checkElementExists(driver, b2bPage.homepage_Signout, 5)){
			b2bPage.homepage_Signout.click();
		}
		if(Common.checkElementExists(driver, b2bPage.Login_EmailTextBox, 2)){
			B2BCommon.Login(b2bPage,buyerEmail, "1q2w3e4r");
		}		
		//sleep(1000);
		b2bPage.homepage_MyAccount.click();
		b2bPage.myAccountPage_ViewQuotehistory.click();
		b2bPage.QuotePage_ViewQuoteFirst.click();
		System.out.println("Choose the approved products to place an order!");
		b2bPage.cartPage_ConvertToOrderBtn.click();
		System.out.println("Convert to Order is clicked.");	
	}
	public void checkInbox(String emailSubject, WebElement element, String account, String emailTitle, String urlTitle,Boolean whetherLogin){
		String checkEmail=account+"@sharklasers.com";
		EMailCommon.createEmail(driver,mailPage,account);
		flag=EMailCommon.checkEmail(driver, mailPage, b2bPage, emailSubject, element, checkEmail, emailTitle, urlTitle,whetherLogin );
		/*
		if(flag==true || flag==false){
			result=flag;
			} else {
			setManualValidateLog("Need Manual Validate in email "+checkEmail+", and check email: "+emailTitle );		
			}
		*/
		if(flag==false){
			result=false;
			setManualValidateLog("Need Manual Validate in email "+checkEmail+", and check email: "+emailTitle );		
		}
	}
	
	public void addLapTopsToCart() throws InterruptedException {
		login(buyerEmail);
		b2bPage.HomePage_productsLink.click();
		b2bPage.HomePage_Laptop.click();
		String plpURL = driver.getCurrentUrl();
		boolean isProductAdded = false;
		// add contract product to cart
		if (Common.isElementExist(driver, By.xpath("//*[contains(@title,'Add to Cart')]"))) {
			for (int i = 0; i < b2bPage.PLPPage_addToCart.size(); i++) {
			  //for (int i = b2bPage.PLPPage_addToCart.size()-1; i >= 0; i--) {
				b2bPage.PLPPage_addToCart.get(i).click();
				Common.waitElementClickable(driver, b2bPage.Product_contractAddToCartOnPopup, 60);
				b2bPage.Product_contractAddToCartOnPopup.click();
				Common.waitElementClickable(driver, b2bPage.ProductPage_AlertGoToCart, 60);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2bPage.ProductPage_AlertGoToCart);
				Thread.sleep(6000);
				driver.navigate().refresh();
//				if(Common.checkElementExists(driver, driver.findElement(By.xpath("//div[@class='cart-item']")), 60)){
				if (Common.isElementExist(driver, By.xpath("//div[@class='cart-item']"))) {
					isProductAdded = true;
					System.out.println("Contract product added successfully.");
					break;
				} else {
					driver.get(plpURL);
				}
			}
		}
		// contract product add failure, add agreement product
		if (!isProductAdded) {
			int viewDetailsNo = b2bPage.PLPPage_viewDetails.size();
			for (int i = 0; i < viewDetailsNo; i++) {
			//for (int i = viewDetailsNo-1; i>= 0; i--) {
				b2bPage.PLPPage_viewDetails.get(i).click();
				if (isAlertPresent()) {
					System.out.println(driver.switchTo().alert().getText() + " Try next product!");
					driver.switchTo().alert().accept();
					driver.get(plpURL);
				} else if (driver.getTitle().contains("Not Found")) {
					System.out.println("Product Not Found, Try next product!");
					driver.get(plpURL);
				}else {
					String pdpURL = driver.getCurrentUrl();
					String partNum = pdpURL.substring(pdpURL.lastIndexOf('/') + 1, pdpURL.length());
					System.out.println("Product Number: " + partNum);
					Thread.sleep(10000);
					if (Common.isElementExist(driver,
							By.xpath("//*[contains(@id,'Alert')]//*[contains(@id,'_add_to_cart')]"))) {
						Thread.sleep(10000);
						if (b2bPage.PDPPage_agreementAddToCartOnPopup.isDisplayed()){
							((JavascriptExecutor) driver).executeScript("arguments[0].click();",
									b2bPage.PDPPage_agreementAddToCartOnPopup);
							if(Common.checkElementExists(driver, b2bPage.HomePage_CartIcon, 60)){
								b2bPage.HomePage_CartIcon.click();
							}
							break;													
						}						
						 else if(Common.isElementExist(driver, By.xpath("//div/button[contains(@class,'add-to-cart')][contains(@disabled,'disabled')]"))){
							 driver.get(plpURL);
						} else {
							b2bPage.Agreement_agreementsAddToCart.click();
							b2bPage.HomePage_CartIcon.click();
							Thread.sleep(6000);
							if(Common.isElementExist(driver, By.xpath("//div[@class='cart-item']"))){
								System.out.println("Agreement product added successfully.");
								break;
							} else {
								driver.get(plpURL);
							}
						}
					} else if(Common.isElementExist(driver, By.xpath("//div/button[contains(@class,'add-to-cart')][contains(@disabled,'disabled')]"))){
						 driver.get(plpURL);
					} else {
						b2bPage.Agreement_agreementsAddToCart.click();
						b2bPage.HomePage_CartIcon.click();
						Thread.sleep(10000);
						if(Common.checkElementExists(driver, driver.findElement(By.xpath("//div[@class='cart-item']")), 60)){
							System.out.println("Agreement product added successfully.");
							break;
						} else {
							driver.get(plpURL);
						}
					}	
					
				}
			}
		}
	}
	
	public boolean isAlertPresent() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 15);// seconds
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}
		
}
