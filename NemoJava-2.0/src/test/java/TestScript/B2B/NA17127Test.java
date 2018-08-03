package TestScript.B2B;



import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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

public class NA17127Test extends SuperTestClass {
	String today=Common.getDateTimeString();
	String builder;
	String approvera;
	String approverb;
	String builderEmail;
	String approveraEmail;
	String approverbEmail;
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
	
	
	
	public NA17127Test(String store) {
		this.Store = store;
		this.testName = "NA-17127";
		
		builder="builder" + Store + "17127" + today;
		approvera="approvera" + Store + "17127" + today;
		approverb="approverb" + Store + "17127" + today;	
		builderEmail=builder + "@sharklasers.com";
		approveraEmail=approvera + "@sharklasers.com";
		approverbEmail=approverb + "@sharklasers.com";
		newApprovera = "Email_Approvera_update" + Store + "17127";
		newApproverb = "Email_Approverb_update" + Store + "17127";
		newBuilder = "Email_Builder_update" + Store + "17127";
		newApproveraEmail = newApprovera + "@sharklasers.com";
		newApproverbEmail = newApproverb + "@sharklasers.com";
		newBuilderEmail = newBuilder + "@sharklasers.com";
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "email", "p1", "b2b"})
	public void NA17127(ITestContext ctx){
		try{
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			mailPage = new MailPage(driver);
			
			createAccountAndCheckEmail(builder, B2BRole.Builder);
			createAccountAndCheckEmail(approvera, B2BRole.Approver);
			createAccountAndCheckEmail(approverb, B2BRole.Approver);
			
			updateEmail(builderEmail, newBuilderEmail);
			updateEmail(approveraEmail, newApproveraEmail);
			updateEmail(approverbEmail, newApproverbEmail);
			
			for(int i=0; i<2; i++){
				if(i==0){
					//Login with builder mail id to purchase product and send for approval to Approver-A
					purchaseProductAndSendApproval(newApprovera,"", "approver" );
				} else {
					//Login with builder mail id to purchase product and send for approval to Approver-B
					purchaseProductAndSendApproval(newApprovera,newApproverb, "reject" );
				}
			}					
		} catch (Throwable e) {
				handleThrowable(e, ctx);
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
			} else if(Common.checkElementDisplays(driver, mailPage.Inbox_EmailSubject,5)){
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
					driver.manage().deleteAllCookies();
					element.click();
					System.out.println(linkText + " link is clicked.");
					Common.sleep(3000);
					Common.switchToWindow(driver,1);
					Common.sleep(1000);
					if(linkText.contains("Store") && login){
						B2BCommon.Login(b2bPage, loginAccount, "1q2w3e4r");
						Common.sleep(2000);
					}
					String linkedPageUrl=driver.getCurrentUrl();
					System.out.println("The url is: "+linkedPageUrl);		
					if(!linkedPageUrl.contains(urlTitle))
						Assert.fail("Link doesn't redirect to " + urlTitle);
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
	
	private void purchaseProductAndSendApproval(String approverAAddress, String approverBAddress, String process) throws InterruptedException {
		String subject="";
		if(process=="approver"){
			subject="Your Lenovo order was approved!";
		}else if(process=="reject"){
			subject="Your Lenovo order was not approved!";
		}
		//builder login and and a product
		loginAndCheckOut(builderEmail);
		//Send for approve
		String orderNum=sendForApproval(driver,Store,b2bPage,testData,approvera);
		//builder checking approver email
		Common.sleep(10000);
		 
        checkInbox("pending order approval", mailPage.Inbox_BackToStoreLink, approverAAddress, approveraEmail, "Pending order approver, and the orderNum is: "+orderNum, "/my-account");
      //approver login
        login(approveraEmail );
        if(process =="reject"){ 
        	//need get data
            b2bPage.homepage_MyAccount.click();
    		b2bPage.myAccountPage_viewOrderRequireApproval.click();
    		b2bPage.QuotePage_orderSelectChkbox.click();
    		System.out.println("Choose the request order!");
    		//re-assige
    		Common.scrollToElement(driver, b2bPage.OrderApproverPage_approverDropDown);
    		Select select = new Select(b2bPage.OrderApproverPage_approverDropDown);
    		select.selectByValue(approverb.toUpperCase()+"@SHARKLASERS.COM");
//			driver.findElement(By.xpath("//select/option[contains(.,'"+approverb.toUpperCase()+"@')]")).click();
    		b2bPage.QuotePage_clickAssignButton.click();
			System.out.println("Product is re-assigned to Approver-B!!");
			checkInbox("pending order approval", mailPage.Inbox_BackToStoreLink, approverBAddress, approverbEmail, " Order"+ orderNum + "has been reassigned", "/my-account");
    		login(approverbEmail);
        }
        //approver or reject the request
        B2BCommon.processOrder(driver,b2bPage, mailPage, process);
        //builder check the process result email
        checkInbox(subject, mailPage.Inbox_BackToStoreLink, newBuilder, builderEmail, "Product is " +process, "/my-account");		
	}
	
	public static String sendForApproval(WebDriver driver, String country,B2BPage b2bPage,TestData testData, String email) throws InterruptedException{
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
		Thread.sleep(5000);
		if(Common.checkElementDisplays(driver, b2bPage.placeOrderPage_ResellerID, 10)){
			b2bPage.placeOrderPage_ResellerID.sendKeys("reseller@yopmail.com");
		}	
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.PAGE_UP).perform();
		b2bPage.placeOrderPage_Terms.click();
		if(Common.checkElementDisplays(driver, b2bPage.placeOrderPage_selectApprover, 5)){
			actions.sendKeys(Keys.PAGE_UP).perform();
			Select select = new Select(b2bPage.placeOrderPage_selectApprover);
			select.selectByValue(email.toUpperCase()+"@SHARKLASERS.COM");
//			driver.findElement(By.xpath("//select/option[contains(.,'"+email.toUpperCase()+"@')]")).click();
			System.out.println("Choose "+email+"@SHARKLASERS.COM to approver!");
		}
		b2bPage.placeOrderPage_sendForApproval.click();
		String orderNum=b2bPage.placeOrderPage_OrderNumber.getText();
        System.out.println("Order Number is: " + orderNum);
        return orderNum;
	}

	public void createAccountAndCheckEmail(String email,B2BRole role){
		String account=email+"@SHARKLASERS.COM";
//		EMailCommon.createEmail(driver,mailPage,email);
		B2BCommon.createAccount(driver ,testData.B2B.getHomePageUrl() ,testData.B2B.getB2BUnit() ,b2bPage, role, account, Browser);
		HMCCommon.activeAccount(driver,testData, account);
		checkInbox("Customer Registration", mailPage.Inbox_BackToLoginLink, email, email, "Customer Registration email !", "/login");
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
	
	public void checkInbox(String emailSubject, WebElement element, String checkEmail, String loginAccount, String emailTitle, String urlTitle){
		driver.manage().deleteAllCookies();
		String checkEmailAddress=checkEmail+"@sharklasers.com";
		EMailCommon.createEmail(driver,mailPage,checkEmail);
		flag=checkEmail(driver, mailPage, b2bPage, emailSubject, element, checkEmailAddress,loginAccount, emailTitle, urlTitle,true );
		if(flag==false){
			result=false;
			setManualValidateLog("Need Manual Validate in email "+checkEmail+", and check email: "+emailTitle );		
		}
		
	}
	
	public void loginAndCheckOut(String loginAccount) throws InterruptedException {
		addLapTopsToCart();
		b2bPage.cartPage_LenovoCheckout.click();	
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
				Common.javascriptClick(driver, b2bPage.ProductPage_AlertGoToCart);
				Thread.sleep(10000);
				driver.navigate().refresh();
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

