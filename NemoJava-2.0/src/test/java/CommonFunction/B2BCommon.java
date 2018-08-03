package CommonFunction;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2BPunchoutPage;
import Pages.MailPage;
import TestData.TestData;

public class B2BCommon {

	/**
	 * @Owner Pan
	 * @Parameter:
	 * @Usage:
	 */
	public static void Login(B2BPage b2bPage, String emailId, String password) {
		b2bPage.Login_EmailTextBox.clear();
		b2bPage.Login_EmailTextBox.sendKeys(emailId);
		b2bPage.Login_PasswordTextBox.clear();
		b2bPage.Login_PasswordTextBox.sendKeys(password);
		b2bPage.Login_SignInButton.click();
	}
	
	/**
	 * @Owner Pan
	 * @Parameter:
	 * @Usage:
	 */
	
	public static void fillB2BShippingInfo(WebDriver driver,B2BPage b2bPage,TestData testData){
		

		//b2bPage.shippingPage_EditCart.click();
		if (Common.checkElementDisplays(driver, By.xpath("//a[contains(text(),'Edit')]"), 3)) {
			b2bPage.shippingPage_EditCart.click();
		}		

		b2bPage.shippingPage_ShipFName.clear();
		b2bPage.shippingPage_ShipFName.sendKeys(testData.B2B.getFirstName());
		b2bPage.shippingPage_ShipLName.clear();
		b2bPage.shippingPage_ShipLName.sendKeys(testData.B2B.getLastName());
		b2bPage.shippingPage_CompanyName.clear();
		b2bPage.shippingPage_CompanyName.sendKeys(testData.B2B.getCompany());
		b2bPage.shippingPage_AddressLine1.clear();
		b2bPage.shippingPage_AddressLine1.sendKeys(testData.B2B.getAddressLine1());
		b2bPage.shippingPage_CityOrSuburb.clear();
		b2bPage.shippingPage_CityOrSuburb.sendKeys(testData.B2B.getAddressCity());
		Select stateDropdown = new Select(b2bPage.shippingPage_State);
		stateDropdown.selectByVisibleText(testData.B2B.getAddressState());
		/*b2bPage.shippingPage_State.click();
		driver.findElement(By.xpath("//select[@id='state']/option[text()='" + State + "']")).click();*/
		b2bPage.shippingPage_PostCode.clear();
		b2bPage.shippingPage_PostCode.sendKeys(testData.B2B.getPostCode());
		b2bPage.shippingPage_ShipContactNumber.clear();
		b2bPage.shippingPage_ShipContactNumber.sendKeys(testData.B2B.getPhoneNum());
		driver.findElement(By.xpath(".//*[@id='email']")).clear();
		driver.findElement(By.xpath(".//*[@id='email']")).sendKeys(testData.B2B.getBuyerId());
		
	}
	
	/**
	 * @Owner Pan
	 * @Parameter:
	 * @Usage:
	 */
	public static void fillB2BShippingInfo(WebDriver driver,B2BPage b2bPage,String Store,String FirstName,String LastName,String Company,String Address,String City,String State,String PostCode,String PhoneNum){

		//b2bPage.shippingPage_EditCart.click();
		if (Common.checkElementDisplays(driver, By.xpath("//a[contains(text(),'Edit')]"), 3)) {
			b2bPage.shippingPage_EditCart.click();
		}		

		b2bPage.shippingPage_ShipFName.clear();
		b2bPage.shippingPage_ShipFName.sendKeys(FirstName);
		b2bPage.shippingPage_ShipLName.clear();
		b2bPage.shippingPage_ShipLName.sendKeys(LastName);
		b2bPage.shippingPage_CompanyName.clear();
		b2bPage.shippingPage_CompanyName.sendKeys(Company);
		b2bPage.shippingPage_AddressLine1.clear();
		b2bPage.shippingPage_AddressLine1.sendKeys(Address);
		b2bPage.shippingPage_CityOrSuburb.clear();
		b2bPage.shippingPage_CityOrSuburb.sendKeys(City);
		Select stateDropdown = new Select(b2bPage.shippingPage_State);
		stateDropdown.selectByVisibleText(State);
		/*b2bPage.shippingPage_State.click();
		driver.findElement(By.xpath("//select[@id='state']/option[text()='" + State + "']")).click();*/
		b2bPage.shippingPage_PostCode.clear();
		b2bPage.shippingPage_PostCode.sendKeys(PostCode);
		b2bPage.shippingPage_ShipContactNumber.clear();
		b2bPage.shippingPage_ShipContactNumber.sendKeys(PhoneNum);
		driver.findElement(By.xpath(".//*[@id='email']")).clear();
		if(Store.toLowerCase().equals("us")){
			driver.findElement(By.xpath(".//*[@id='email']")).sendKeys("testus@sharklasers.com");
		}else if(Store.toLowerCase().equals("au")){
			driver.findElement(By.xpath(".//*[@id='email']")).sendKeys("testau@sharklasers.com");
		}else if(Store.toLowerCase().equals("jp")){
			driver.findElement(By.xpath(".//*[@id='email']")).sendKeys("testjp@sharklasers.com");
		}		
	}
	
	/**
	 * @Owner Pan
	 * @Parameter:
	 * @Usage:
	 */
	public static void creditCardPayment(WebDriver driver,B2BPage b2bPage){
		
		Actions actions = new Actions(driver);
		By Card = By.xpath("//input[@id='PaymentTypeSelection_CARD']");

		if (Common.isElementExist(driver, Card)) {
		actions.moveToElement(b2bPage.paymentPage_creditCardRadio).click().perform();
        
		driver.switchTo().frame(b2bPage.creditCardFrame);
		b2bPage.paymentPage_Visa.click();
		b2bPage.paymentPage_CardNumber.sendKeys("4111111111111111");
		b2bPage.paymentPage_ExpiryMonth.sendKeys("06");
		b2bPage.paymentPage_ExpiryYear.sendKeys("20");
		b2bPage.paymentPage_SecurityCode.sendKeys("132");
		driver.switchTo().defaultContent();
		b2bPage.paymentPage_NameonCard.sendKeys("LIXE");
		
		}
	}

	/**
	 * @Owner Pan
	 * @Parameter:
	 * @Usage:
	 */
	public static void fillB2bBillingAddress(WebDriver driver,B2BPage b2bPage,String FirstName,String LastName,String companyName,String Address,String City,String State,String PostCode,String PhoneNum){
		b2bPage.paymentPage_FirstName.clear();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		b2bPage.paymentPage_FirstName.sendKeys(FirstName);
		b2bPage.paymentPage_LastName.clear();
		b2bPage.paymentPage_LastName.sendKeys(LastName);
		Common.sleep(2000);
		if (b2bPage.paymentPage_companyName.getAttribute("editable").contains("true")&&! Common.isAttributePresent(b2bPage.paymentPage_companyName,"disabled")) {
			b2bPage.paymentPage_companyName.clear();
			b2bPage.paymentPage_companyName.sendKeys(companyName);
			System.out.println("Input Company name!");
		}
		
		if (b2bPage.paymentPage_addressLine1.getAttribute("editable").contains("true")&& ! Common.isAttributePresent(b2bPage.paymentPage_addressLine1,"disabled")){
				//!(b2bPage.paymentPage_addressLine1.getAttribute("disabled").isEmpty())) {
			b2bPage.paymentPage_addressLine1.clear();
			b2bPage.paymentPage_addressLine1.sendKeys(Address);
			System.out.println("Input address!");
		}
		
		if (b2bPage.paymentPage_cityOrSuburb.getAttribute("editable").contains("true")&& ! Common.isAttributePresent(b2bPage.paymentPage_cityOrSuburb,"disabled")) {
			b2bPage.paymentPage_cityOrSuburb.clear();
			b2bPage.paymentPage_cityOrSuburb.sendKeys(City);
		}
		if (b2bPage.paymentPage_addressState.getAttribute("editable").contains("true")&&! Common.isAttributePresent(b2bPage.paymentPage_addressState,"disabled")) {
			Select stateDropdown = new Select(b2bPage.paymentPage_addressState);
			stateDropdown.selectByVisibleText(State);	
		}
				
		if (b2bPage.paymentPage_addressPostcode.getAttribute("editable").contains("true")&&! Common.isAttributePresent(b2bPage.paymentPage_addressPostcode,"disabled")) {
			b2bPage.paymentPage_addressPostcode.clear();
			b2bPage.paymentPage_addressPostcode.sendKeys(PostCode);
		}
		
		if (b2bPage.paymentPage_addressPostcode.getAttribute("editable").contains("true")&&! Common.isAttributePresent(b2bPage.paymentPage_addressPostcode,"disabled")) {
			b2bPage.paymentPage_addressPostcode.clear();
			b2bPage.paymentPage_addressPostcode.sendKeys(PostCode);
		}
		
		if (b2bPage.paymentPage_Phone.getAttribute("editable").contains("true")&&! Common.isAttributePresent(b2bPage.paymentPage_Phone,"disabled")) {
			b2bPage.paymentPage_Phone.clear();
			b2bPage.paymentPage_Phone.sendKeys(PhoneNum);
		}	
		Common.javascriptClick(driver, b2bPage.paymentPage_ContinueToPlaceOrder);
		//b2bPage.paymentPage_ContinueToPlaceOrder.click();
	}
	
	
	/**
	 * @Owner Pan
	 * @Parameter:
	 * @Usage:
	 */
	public static void fillDefaultB2bBillingAddress(WebDriver driver,B2BPage b2bPage,TestData testData){
		fillB2bBillingAddress(driver, b2bPage, "FirstNameJohn", "LastNameSnow", testData.B2B.getCompany(), testData.B2B.getAddressLine1(), testData.B2B.getAddressCity(), testData.B2B.getAddressState(), testData.B2B.getPostCode(), testData.B2B.getPhoneNum());
	}
	
	
	/**
	 * @Owner Pan
	 * @Parameter:
	 * @Usage:
	 */
	public static String requestQuote(WebDriver driver,B2BPage b2bPage, String email ){
		b2bPage.cartPage_RequestQuoteBtn.click();
		System.out.println("Request Quote button is clicked.");
		Common.sleep(5000);
		b2bPage.cartPage_SubmitQuote.click();
		String quoteNum=b2bPage.CartPage_getQuoteNumber.getText();
		System.out.println("Quote of "+ email + "@SHARKLASERS.COM no is: " + quoteNum);
		b2bPage.homepage_MyAccount.click();
		b2bPage.myAccountPage_ViewQuotehistory.click();
		System.out.println("View Quotes link is clicked.");
		b2bPage.QuotePage_ViewQuoteFirst.click();
		driver.findElement(By.xpath("//select/option[contains(.,'"+email.toUpperCase()+"@')]")).click();
		System.out.println("Choose "+ email + "@SHARKLASERS.COM to approver!");
		b2bPage.placeOrderPage_sendApproval.click();
		System.out.println("Send for approver! ");
		String quoteStatus=b2bPage.QuotePage_FirstQuoteStatus.getText();
		System.out.println("The quote status is: "+quoteStatus);
		Assert.assertEquals("INTERNALPENDING",quoteStatus);
		return quoteNum;
	}
	
	/**
	 * @Owner Pan
	 * @Parameter:
	 * @Usage:
	 */
	public static void processQuote(WebDriver driver, B2BPage b2bPage, MailPage mailPage, String flag,String email ){				
		b2bPage.homepage_MyAccount.click();
		b2bPage.myAccountPage_viewQuoteRequireApproval.click();
		b2bPage.QuotePage_orderSelectChkbox.click();
		System.out.println("Choose the request quote!");
		if(flag=="approver"){
			b2bPage.QuotePage_clickApproveButton.click();
			System.out.println("Product is approved by Approver-A!!");
		}else if(flag=="reject") {
			b2bPage.QuotePage_clickRejectButton.click();
			System.out.println("Product is rejected by Approver-B!!");
		}else{
			b2bPage.QuotePage_approverDropDown.click();
			driver.findElement(By.xpath("//select/option[contains(.,'"+email.toUpperCase()+"@')]")).click();
			b2bPage.QuotePage_clickAssignButton.click();			
			System.out.println("Product is assigned to Approver-B!!");
		}			
	}
	
	/**
	 * @Owner Pan
	 * @Parameter:
	 * @Usage:
	 */
	public static void processOrder(WebDriver driver, B2BPage b2bPage, MailPage mailPage, String flag){
		b2bPage.homepage_MyAccount.click();
		b2bPage.myAccountPage_viewOrderRequireApproval.click();
		b2bPage.QuotePage_orderSelectChkbox.click();
		System.out.println("Choose the request quote!");
		if(flag=="approver"){
			b2bPage.QuotePage_clickApproveButton.click();
			System.out.println("Product is approved by Approver-A!!");
		}else {
			b2bPage.QuotePage_clickRejectButton.click();
			System.out.println("Product is rejected by Approver-B!!");
		}
	}
	
	/**
	 * @throws InterruptedException 
	 * @Owner Pan
	 * @Parameter:
	 * @Usage:
	 */
	public static String placeAnOrder(WebDriver driver, String country,B2BPage b2bPage,TestData testData) throws InterruptedException{
		fillB2BShippingInfo(driver, b2bPage,country, "FirstNameJohn", "LastNameSnow", 
				testData.B2B.getCompany(), testData.B2B.getAddressLine1(), testData.B2B.getAddressCity(), 
				testData.B2B.getAddressState(), testData.B2B.getPostCode(),testData.B2B.getPhoneNum());			
		Common.javascriptClick(driver, b2bPage.shippingPage_ContinueToPayment);
		//b2bPage.shippingPage_ContinueToPayment.click();
		System.out.println("Go to Payment page!");
		if(Common.checkElementExists(driver, b2bPage.shippingPage_validateFromOk, 5)){
			if (Common.checkElementExists(driver, b2bPage.shipping_validateSelect, 10)) {
				b2bPage.shipping_validateSelect.click();
				b2bPage.shipping_validateSelectOne.click();
				
			}
			b2bPage.shippingPage_validateFromOk.click();
		}
		if(Common.isElementExist(driver, By.xpath("//*[@id='PaymentTypeSelection_CARD']"))){
			creditCardPayment(driver, b2bPage);
		}else{
			b2bPage.paymentPage_PurchaseOrder.click();
			Common.sleep(2000);
			b2bPage.paymentPage_purchaseNum.sendKeys("1234567890");
			b2bPage.paymentPage_purchaseDate.sendKeys(Keys.ENTER);
		}
		
		
		fillDefaultB2bBillingAddress(driver, b2bPage, testData);
		System.out.println("Go to Order page!");
		Common.sleep(2000);
		if(Common.checkElementDisplays(driver, b2bPage.placeOrderPage_ResellerID, 5)){
			Common.scrollToElement(driver, b2bPage.placeOrderPage_ResellerID);
			b2bPage.placeOrderPage_ResellerID.sendKeys("reseller@yopmail.com");
		}
		
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.PAGE_UP).perform();
		if(Common.checkElementExists(driver, b2bPage.placeOrderPage_Terms, 20)){
			actions.sendKeys(Keys.PAGE_UP).perform();
			Common.scrollToElement(driver, b2bPage.placeOrderPage_Terms);
			b2bPage.placeOrderPage_Terms.click();		
		}		
		b2bPage.placeOrderPage_sendForApproval.click();
//		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		String orderNum=b2bPage.placeOrderPage_OrderNumber.getText();
        System.out.println("Order Number is: " + orderNum);
        return orderNum;
	}
	
	/**
	 * @Owner Pan
	 * @Parameter:
	 * @Usage:
	 */
	public static String sendForApproval(WebDriver driver, String country,B2BPage b2bPage,TestData testData, String email){
		fillB2BShippingInfo(driver, b2bPage,country, "FirstNameJohn", "LastNameSnow", 
				testData.B2B.getCompany(), testData.B2B.getAddressLine1(), testData.B2B.getAddressCity(), 
				testData.B2B.getAddressState(), testData.B2B.getPostCode(),testData.B2B.getPhoneNum());	
		Common.javascriptClick(driver, b2bPage.shippingPage_ContinueToPayment);
		//b2bPage.shippingPage_ContinueToPayment.click();
		System.out.println("Go to Payment page!");
		if(Common.checkElementExists(driver, b2bPage.shippingPage_validateFromOk, 5)){
			if (Common.checkElementExists(driver, b2bPage.shipping_validateSelect, 10)) {
				b2bPage.shipping_validateSelect.click();
				b2bPage.shipping_validateSelectOne.click();
				
			}
			b2bPage.shippingPage_validateFromOk.click();
		}
		b2bPage.paymentPage_PurchaseOrder.click();
		Common.sleep(2000);
		b2bPage.paymentPage_purchaseNum.sendKeys("1234567890");
		b2bPage.paymentPage_purchaseDate.sendKeys(Keys.ENTER);
		
		fillDefaultB2bBillingAddress(driver, b2bPage, testData);
		System.out.println("Go to Order page!");
		if(Common.checkElementExists(driver, b2bPage.placeOrderPage_ResellerID, 60)){
			b2bPage.placeOrderPage_ResellerID.sendKeys("reseller@yopmail.com");
		}		
		Common.javascriptClick(driver, b2bPage.placeOrderPage_Terms);
		Common.scrollAndClick(driver, b2bPage.placeOrderPage_selectApprover);
//		b2bPage.placeOrderPage_selectApprover.click();
		Common.sleep(1000);
		//constantly not able to find email
		//driver.findElement(By.xpath("//select/option[contains(.,'"+"APPROVERAJP"+"')]")).click();
		driver.findElement(By.xpath("//select/option[contains(.,'"+email.toUpperCase()+"')]")).click();
		
		String approverID = driver.findElement(By.xpath("//select/option[contains(.,'"+email.toUpperCase()+"')]")).getText();
		System.out.println("Choosed "+approverID+" to approver!");
		Common.javascriptClick(driver, b2bPage.placeOrderPage_sendForApproval);
		String orderNum=b2bPage.placeOrderPage_OrderNumber.getText();
        System.out.println("Order Number is: " + orderNum);
        return orderNum;
	}
	
	/**
	 * @Owner Pan
	 * @Parameter:
	 * @Usage:
	 */
	public static void addProduct(WebDriver driver, B2BPage b2bPage, String product){
		b2bPage.HomePage_CartIcon.click();
		b2bPage.cartPage_quickOrder.clear();
		b2bPage.cartPage_quickOrder.sendKeys(product);
		b2bPage.cartPage_addButton.click();
		boolean flag=Common.isElementExist(driver, By.xpath("//span[contains(text(),'" + product + "')]"));
		if(flag==true){
			System.out.println("Added product into cart, product: " + product);
		} else {
			System.out.println("The product code: " + product + " is invalid, pls update test data!");
		}
	}

	/**
	 * @Owner Haiyang
	 * @Parameter:
	 * @Usage:
	 */
    public static void createAccount(WebDriver driver, String B2BUrl,String B2BUnit,B2BPage b2bPage,B2BRole role,String tempEmailAddress,String browser ){
		Common.NavigateToUrl(driver, browser, B2BUrl);
		b2bPage.Login_CreateAnAccountButton.click();
		expandAccessLevel(b2bPage);
		clickCorrectAccessLevel(b2bPage, B2BUnit);
		clickRoleCheckbox(b2bPage, role);
		
		b2bPage.Register_EmailTextBox.clear();
		b2bPage.Register_EmailTextBox.sendKeys(tempEmailAddress);
		b2bPage.Register_ConfirmEmailTextBox.clear();
		b2bPage.Register_ConfirmEmailTextBox.sendKeys(tempEmailAddress);
		b2bPage.Register_FirstNameTextBox.clear();
		b2bPage.Register_FirstNameTextBox.sendKeys("AutoFirstName");
		b2bPage.Register_LastNameTextBox.clear();
		b2bPage.Register_LastNameTextBox.sendKeys("AutoLastName");
		b2bPage.Register_PasswordTextBox.clear();
		b2bPage.Register_PasswordTextBox.sendKeys("1q2w3e4r");
		b2bPage.Register_ConfirmPasswordTextBox.clear();
		b2bPage.Register_ConfirmPasswordTextBox.sendKeys("1q2w3e4r");
		b2bPage.Register_AgreeOptinCheckBox.click();
		if(Common.checkElementDisplays(driver, b2bPage.Register_acceptterms, 3)){
			b2bPage.Register_acceptterms.click();
		}
		b2bPage.Register_CreateAccountButton.click();
		System.out.println("Create account: "+tempEmailAddress);
    }
    
    /**
	 * @Owner Haiyang
	 * @Parameter:
	 * @Usage:
	 */
	public static void expandAccessLevel(B2BPage b2bPage) {
		int count = 0;
		while (Common.checkElementExists(b2bPage.PageDriver, b2bPage.Register_AccessLevelExpand,5) && count < 5) {
			b2bPage.Register_AccessLevelExpand.click();
			count++;
		}
	}

	/**
	 * @Owner Haiyang
	 * @Parameter:
	 * @Usage:
	 */
	public static void clickCorrectAccessLevel(B2BPage b2bPage, String b2bUnit) {
		b2bPage.PageDriver.findElement(By.xpath(".//*[@id='accessLevel']//input[@value='" + b2bUnit + "']")).click();
	}

	/**
	 * @Owner Haiyang
	 * @Parameter:
	 * @Usage:
	 */
	public static void clickRoleCheckbox(B2BPage b2bPage, B2BRole role) {
		String xPath = "";
		switch (role) {
		case Buyer:
			xPath = ".//input[@value='buyerDefault']";
			break;
		case Admin:
			xPath = ".//input[@value='customAdminControlled']";
			break;
		case Builder:
			xPath = ".//input[@value='builderDefault']";
			break;
		case Approver:
			xPath = ".//input[@value='approverDefault']";
			break;
		case Browser:
			xPath = ".//input[@value='browserDefault']";
			break;
		default:
			break;
		}
		Common.waitElementClickable(b2bPage.PageDriver, b2bPage.PageDriver.findElement(By.xpath(xPath)), 60);
		b2bPage.PageDriver.findElement(By.xpath(xPath)).click();
	}
		
	public enum B2BRole {
		Buyer, Builder, Approver, Admin, Browser;
	}
	
	/**
	 * @Owner Qianqian
	 * @Parameter:
	 * @Usage:
	 */
	public static boolean verifyContractLabel(WebElement element) {
		boolean flag = false;
		if (element.isDisplayed()) {
			if (element.getText().trim().equals("Contract")
					|| element.getText().trim().equals("Agreement")) {
						flag = true;
			}
		}
		return flag;
	}
			
	/**
	 * @throws InterruptedException 
	 * @Owner Haiyang
	 * @Parameter:
	 * @Usage:
	 */
	public static void approveBuyerAccount(WebDriver driver, TestData testData, B2BPage b2bPage, String email) throws InterruptedException
	{
		b2bPage.MyCompany.click();
		b2bPage.MyCompany_EditOrDisableUser.click();
		b2bPage.MyCompany_Email.clear();
		b2bPage.MyCompany_Email.sendKeys(email);
		b2bPage.MyCompany_SearchButton.click();		
		driver.findElement(
				By.xpath(".//*[@id='manage_user']//a[contains(.,'" + email.toUpperCase() + "')]"))
				.click();
		b2bPage.MyCompany_EditAccount.click();
		b2bPage.MyCompany_ApprovalStatus.click();
		b2bPage.MyCompany_ApprovalStatusActive.click();
		b2bPage.MyCompany_BuyerDefault.click();
		b2bPage.MyCompany_AdobeSystemPtyLtd.click();
		Thread.sleep(10000);
		b2bPage.MyCompany_DefaultStore.click();
		b2bPage.MyCompany_SaveUpdate.click();
	}
	
	/**
	 * @Owner Pan
	 * @Parameter:
	 * @Usage:
	 */
	public static void clearTheCart(WebDriver driver, B2BPage b2bPage) {
		Dailylog.logInfo("Clearing the cart");
		int productCount = driver
				.findElements(
						By.xpath("//a[contains(text(),'Empty cart')]")).size();
		System.out.println("Count is : " + productCount);
		if ( productCount > 0) {
			b2bPage.cartPage_emptyCartButton.click();
		} else {
			Dailylog.logInfo("Cart is already empty");
		}
		Common.sleep(2000);
	}
	
	
	/**
	 * @Owner XIE
	 * @Parameter:
	 * @Usage:
	 */
	
	public static void punchoutLogin(WebDriver driver, B2BPunchoutPage b2bPunchoutPage){
		if(Common.checkElementExists(driver, b2bPunchoutPage.Ariba_Username, 3)){
			b2bPunchoutPage.Ariba_Username.clear();
			b2bPunchoutPage.Ariba_Username.sendKeys("lenovopunchouttester");
			if(Common.checkElementExists(driver, b2bPunchoutPage.Ariba_Password, 3)){
				b2bPunchoutPage.Ariba_Password.clear();
				b2bPunchoutPage.Ariba_Password.sendKeys("lenovopunchouttester");
			}
			b2bPunchoutPage.Ariba_Login.click();
		}

	}
	
	/**
	 * @Owner Pan
	 * @Parameter:
	 * @Usage:
	 */
	
	public static void loginASM(WebDriver driver,B2BPage b2bPage,TestData testData){
		if(Common.isElementExist(driver, By.xpath("//*[@id='asmLoginForm']/fieldset/div[2]/input"))){
			b2bPage.MyAccountPage_ASMPassword.clear();
			b2bPage.MyAccountPage_ASMPassword.sendKeys(testData.B2B
					.getDefaultPassword());
			b2bPage.MyAccountPage_ASMLogIn.click();
		}
	}
	
	public static String placeOrderFromCartCheckout(WebDriver driver, B2BPage b2bPage, TestData testData) throws InterruptedException
	{
		Common.javascriptClick(driver, b2bPage.cartPage_LenovoCheckout);
		B2BCommon.fillB2BShippingInfo(driver, b2bPage, testData.Store, "FirstNameJohn", "LastNameSnow",
				testData.B2B.getCompany(), testData.B2B.getAddressLine1(), testData.B2B.getAddressCity(),
				testData.B2B.getAddressState(), testData.B2B.getPostCode(), testData.B2B.getPhoneNum());
		Common.javascriptClick(driver, b2bPage.shippingPage_ContinueToPayment);

		Common.sleep(5000);
		if (Common.checkElementExists(driver, b2bPage.shippingPage_validateFromOk, 10)) {
			if (Common.checkElementExists(driver, b2bPage.shipping_validateSelect, 10)) {
				b2bPage.shipping_validateSelect.click();
				b2bPage.shipping_validateSelectOne.click();
				
			}
			b2bPage.shippingPage_validateFromOk.click();
		}
		
		
		b2bPage.paymentPage_PurchaseOrder.click();
		Common.sleep(2000);
		b2bPage.paymentPage_purchaseNum.sendKeys("1234567890");
		b2bPage.paymentPage_purchaseDate.sendKeys(Keys.ENTER);

		fillDefaultB2bBillingAddress(driver, b2bPage, testData);
		System.out.println("Go to Order page!");

		Thread.sleep(5000);
		if (Common.checkElementDisplays(driver, b2bPage.placeOrderPage_ResellerID, 5)) {
			Common.scrollToElement(driver, b2bPage.placeOrderPage_ResellerID);
			b2bPage.placeOrderPage_ResellerID.sendKeys("reseller@yopmail.com");
		}
		Common.javascriptClick(driver, b2bPage.placeOrderPage_Terms);
		Common.javascriptClick(driver, b2bPage.placeOrderPage_PlaceOrder);
		return b2bPage.placeOrderPage_OrderNumber.getText();
	}
}
