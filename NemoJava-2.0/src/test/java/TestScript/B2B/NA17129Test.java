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
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.B2BCommon.B2BRole;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Pages.B2BPage;
import Pages.MailPage;
import TestData.TestData;
import TestScript.SuperTestClass;

public class NA17129Test extends SuperTestClass {
	String today=Common.getDateTimeString();
	String builder;
	String approvera;
	String approverb;
	String builderEmail;
	String approveraEmail;
	String approverbEmail;
	String quoteStatus;
	String quoteNum;
	String orderNum;
	boolean flag=true;
	boolean result=true;
	B2BPage b2bPage;
	MailPage mailPage;
	String newApprovera;
	String newApproverb;
	String newBuilder;
	String newApproveraEmail;
	String newApproverbEmail;
	String newBuilderEmail;

	public NA17129Test(String store) {
		this.Store = store;
		this.testName = "NA-17129";
		builder="builder"+ Store + "17129" + today;
		approvera="approvera" + Store + "17129" + today;
		approverb="approverb"+ Store + "17129" + today;
		builderEmail=builder + "@sharklasers.com";
		approveraEmail=approvera + "@sharklasers.com";
		approverbEmail=approverb + "@sharklasers.com";
		newApprovera = "Email_Approvera_update"+ Store + "17129";
		newApproverb = "Email_Approverb_update"+ Store + "17129";
		newBuilder = "Email_Builder_update"+ Store + "17129";
		newApproveraEmail = newApprovera + "@sharklasers.com";
		newApproverbEmail = newApproverb + "@sharklasers.com";
		newBuilderEmail = newBuilder + "@sharklasers.com";
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "email", "p1", "b2b"})
	public void NA17129(ITestContext ctx){
		try{
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			mailPage = new MailPage(driver);
			
			//---- Creating  Accounts ----//
			createAccountAndCheckEmail(builder, B2BRole.Builder);
			createAccountAndCheckEmail(approvera, B2BRole.Approver);
			createAccountAndCheckEmail(approverb, B2BRole.Approver);
			updateEmail(approveraEmail, newApproveraEmail);
			updateEmail(approverbEmail, newApproverbEmail);
			updateEmail(builderEmail, newBuilderEmail);
			//Login with builder mail id to request quote to Approver-A
			requestQuote();							
			//Login with approver-A account to approver the quote
			login(approveraEmail );
	        B2BCommon.processQuote(driver,b2bPage, mailPage, "approver","");		
			checkInbox("Your Lenovo quote was approved!", mailPage.Inbox_BackToStoreLink, newBuilder, builderEmail,"Quote is approved!","/my-account",true );
			//Login with builder mail id to place the order
			login(builderEmail);
			convertOrder();	
			String ifNeedApprover = "false";
			String [] orderInfo = sendForApproval(driver,Store,b2bPage,testData, approverb, ifNeedApprover);
			orderNum = orderInfo[0];
			ifNeedApprover = orderInfo[1];
			if(ifNeedApprover=="true") {
				//approver login
		        login(approverbEmail );
		        //approver or reject the request
		        B2BCommon.processOrder(driver,b2bPage, mailPage, "approver");
			}		
			checkInbox("Your order was approved!", mailPage.Inbox_BackToStoreLink, newBuilder, builderEmail, "Order an place email, and order num  is: "+orderNum,"/login",false );			
			
			//Login with builder mail id to request quote to Approver-A
			requestQuote();
			//Login with approver-A account to reassign the quote
			login(approveraEmail );
	        B2BCommon.processQuote(driver,b2bPage, mailPage, "assign",approverb);
	        checkInbox("Quote Pending Approval", mailPage.Inbox_BackToStoreLink, newApproverb, approverbEmail, "Quote pending approval email, , and quoteID is:"+quoteNum,"/login" ,false  );
	        
			//Login with approver-B account to reject the quote
			login(approverbEmail );
	        B2BCommon.processQuote(driver,b2bPage, mailPage, "reject","");
			checkInbox("Your Lenovo quote was not approved", mailPage.Inbox_BackToStoreLink, newBuilder, builderEmail, "Quote is rejected!","/my-account",true );			
			
			//Assert.assertEquals(result, true);		
		} catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}	
	}	
	
	public void updateEmail(String email, String newEmail){
		driver.manage().deleteAllCookies();
		login(email);
		b2bPage.homepage_MyAccount.click();
		b2bPage.MyAccountPage_updateDetails.click();
		b2bPage.MyAccountPage_updateEmail.click();
		b2bPage.MyAccountPage_emailAddress.clear();
		b2bPage.MyAccountPage_emailAddress.sendKeys(newEmail);
		b2bPage.MyAccountPage_reEnterEmail.sendKeys(newEmail);
		b2bPage.MyAccountPage_saveButton.click();
		b2bPage.homepage_Signout.click();
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
			actions.sendKeys(Keys.PAGE_UP).perform();
			ifNeedApprover = "true";
			b2bPage.placeOrderPage_selectApprover.click();
			driver.findElement(By.xpath("//select/option[contains(.,'"+email.toUpperCase()+"@')]")).click();
			System.out.println("Choose "+email+"@SHARKLASERS.COM to approver!");
		} else {
			ifNeedApprover = "false";
		}
		b2bPage.placeOrderPage_sendForApproval.click();
		String orderNum=b2bPage.placeOrderPage_OrderNumber.getText();
        System.out.println("Order Number is: " + orderNum);
        String orderInfo[] = {orderNum, ifNeedApprover};
        return orderInfo;
	}
	
	public void requestQuote() throws InterruptedException{
		addLapTopsToCart();	
		b2bPage.cartPage_RequestQuoteBtn.click();
		System.out.println("Request Quote button is clicked.");
		Common.sleep(5000);
		b2bPage.cartPage_RepIDBox.sendKeys(testData.B2B.getRepID());
		b2bPage.cartPage_SubmitQuote.click();
		quoteNum=b2bPage.CartPage_getQuoteNumber.getText();
		System.out.println("Quote of "+ approvera + "@SHARKLASERS.COM no is: " + quoteNum);
		checkInbox("Lenovo Quote ID: "+quoteNum, mailPage.Inbox_BackToStoreLink, newBuilder, builderEmail, "Request quote email, and quoteID is:"+quoteNum,"/my-account",true );
		login(builderEmail );
		b2bPage.homepage_MyAccount.click();
		b2bPage.myAccountPage_ViewQuotehistory.click();
		System.out.println("View Quotes link is clicked.");
		b2bPage.QuotePage_ViewQuoteFirst.click();
		driver.findElement(By.xpath("//select/option[contains(.,'"+approvera.toUpperCase()+"@')]")).click();
		System.out.println("Choose "+ approvera + "@SHARKLASERS.COM to approver!");
		b2bPage.placeOrderPage_sendApproval.click();
		System.out.println("Send for approver! ");
		String quoteStatus=b2bPage.QuotePage_FirstQuoteStatus.getText();
		System.out.println("The quote status is: "+quoteStatus);
		Assert.assertEquals("INTERNALPENDING",quoteStatus);		
		checkInbox("Quote Pending Approval", mailPage.Inbox_BackToStoreLink, newApprovera, approveraEmail, "Quote pending approval email, and quoteID is:"+ quoteNum,"/my-account",true );		
	}
    
	public void createAccountAndCheckEmail(String email,B2BRole role){		
		String account=email+"@SHARKLASERS.COM";
		EMailCommon.createEmail(driver,mailPage,email);
		B2BCommon.createAccount(driver ,testData.B2B.getHomePageUrl() ,testData.B2B.getB2BUnit() ,b2bPage, role, account, Browser);
		HMCCommon.activeAccount(driver,testData, account);
		checkInbox("Customer Registration", mailPage.Inbox_BackToLoginLink, email, email, "Customer Registration email !", "/login",false );
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
		b2bPage.homepage_MyAccount.click();
		b2bPage.myAccountPage_ViewQuotehistory.click();
		b2bPage.QuotePage_ViewQuoteFirst.click();
		//b2bPage.QuotePage_ViewQuoteSecond.click();
		System.out.println("Choose the approved products to place an order!");
		b2bPage.cartPage_ConvertToOrderBtn.click();
		System.out.println("Convert to Order is clicked.");	
	}
	/*
	public void checkInbox(String emailSubject, WebElement elementXpath, String checkEmail, String emailTitle){
		EMailCommon.createEmail(driver,mailPage,checkEmail);
		Common.sleep(1000);
		flag=EMailCommon.checkEmail(driver,mailPage, flag, emailSubject, checkEmail, emailTitle);
		if(flag==false){
			result=flag;
			setManualValidateLog("Need Manual Validate in email "+checkEmail+", and check email: "+emailTitle);
		}		
	}
	*/
	public void checkInbox(String emailSubject, WebElement element, String checkEmail, String loginAccount, String emailTitle, String urlTitle ,Boolean whetherLogin){
		String checkEmailAddress =checkEmail+"@sharklasers.com";
		EMailCommon.createEmail(driver,mailPage,checkEmail);
		flag=checkEmail(driver, mailPage, b2bPage, emailSubject, element, checkEmailAddress,loginAccount, emailTitle, urlTitle,whetherLogin );
		if(flag==false){
			result=false;
			setManualValidateLog("Need Manual Validate in email "+checkEmail+", and check email: "+emailTitle );		
		}	
	}
	
	public static Boolean checkEmail(WebDriver driver,MailPage mailPage,B2BPage b2bPage, String emailSubject, WebElement element, String checkEmail, String loginAccount, String emailTilte, String urlTitle, Boolean login){
		String subject = null;
		Boolean reciverEmail=null;
		for(int i=1;i<=5;i++){
			if(i==5){
				reciverEmail=false;
				//System.out.println("ERROR !!!! Email is not received.....!");
				System.out.println("Need Manual Validate in email "+checkEmail+", and check email: "+emailTilte );
			} else if(Common.checkElementExists(driver, mailPage.Inbox_EmailSubject,5)==true){
				subject=mailPage.Inbox_EmailSubject.getText();
				System.out.println("The subject is: "+subject);
				if(subject.contains(emailSubject)){	
					reciverEmail=true;
					i=6;
					Actions actions = new Actions(driver);
					actions.sendKeys(Keys.PAGE_UP).perform();
					mailPage.Inbox_EmailSubject.click();
					System.out.println("Clicked on email containing :" + emailSubject);	
					//mailPage.Inbox_BackToLoginLink.click();
					Common.sleep(10000);
					String linkText=element.getText();
					//String parentWindowId = driver.getWindowHandle();
					element.click();
					System.out.println(linkText + " link is clicked.");
					Common.switchToWindow(driver,1);
				
					Common.sleep(1000);
					if(linkText.contains("Store")==true && login==true){
						B2BCommon.Login(b2bPage, loginAccount, "1q2w3e4r");
						Common.sleep(2000);
					}
					String linkedPageUrl=driver.getCurrentUrl();
					System.out.println("The url is: "+linkedPageUrl);		
					Assert.assertTrue(linkedPageUrl.contains(urlTitle),"not contains urltitle"+checkEmail);
					driver.close();
					Common.switchToWindow(driver,0);

				} else {
					System.out.println("email with this subject is not yet received...!!! Refreshing the inbox after 10 seconds......");
					Common.sleep(10000);
				}
			} 
		}
		return reciverEmail;		
	}

	public void addLapTopsToCart() throws InterruptedException {
		login(builderEmail);
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
				Thread.sleep(10000);
				driver.navigate().refresh();
				if (Common.isElementExist(driver, By.xpath("//div[@class='cart-item']"))) {
					isProductAdded = true;
					System.out.println("Contract product added successfully.");
					break;
				} else {
					driver.get(plpURL);
					System.out.println("Find product again!");
				}
			}
		}
		// contract product add failure, add agreement product
		if (!isProductAdded) {
			int viewDetailsNo = b2bPage.PLPPage_viewDetails.size();
			for (int i = 0; i < viewDetailsNo; i++) {
			//for (int i = viewDetailsNo-2; i >= 0; i--) {
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
							Thread.sleep(10000);
							if (Common.isElementExist(driver, By.xpath("//div[@class='cart-item']"))) {
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
						if (Common.isElementExist(driver, By.xpath("//div[@class='cart-item']"))) {
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


