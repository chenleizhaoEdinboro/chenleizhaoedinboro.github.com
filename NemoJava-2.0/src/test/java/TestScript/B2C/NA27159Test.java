package TestScript.B2C;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestData.TestData;
import TestScript.SuperTestClass;
import junit.framework.Assert;
public class NA27159Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public String b2cProductUrl;
	public String ProductID;
	private String country = "Canada";
	private String emailPattern = "^[A-Za-z0-9._%+-]+@lenovo.com$";
	private String emailDefaultPattern = "\\" + "b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}" + "\\" + "b";
	private String invalidMail = "testau@sharklasers.com";
	private String validMail = "zhengpx1@lenovo.com";
	private String savedEmailPattern;
	private String savedEmailDefaultPattern;
	private String testProduct;
	private String loginID;
	private String pwd;
	
	public NA27159Test(String Store) {
		this.Store = Store;
		this.testName = "NA-27159";
	}
	
	private String getErrMsgPerStore(String store, EnumErrMsg msgName) {
			switch (msgName) {
			case emailErr:
				return "Please enter a valid Email";
			case fNameErr:
				return "Please enter a first name";
			case lNameErr:
				return "Please enter a last name";
			case passwordErr:
				return "Please enter a strong password. (Passwords must contain 8-20 characters and include two of the following character types: letters, numbers, or symbols.)";
			case confirmPassErr:
				return "Please confirm your password";
			case accecptErr:
				return "You must accept Terms of Use and Privacy Policy before creating an account.";
			case emailNotMatchErr:
				return "Re-entered email does not match email";
			case passwordNotMatchErr:
				return "Password and password confirmation do not match";
			case emailAlreadyExistsErr:
				return "Oops! An account with this email address already exists. Please login with this account or use a different email address to create a new account. If you need help, please call the phone number at the top of the page.";
			case accountCreatedThankYouMsg:
				return "Thank You For Creating An Account";
			case accountNotVerifiedErr:
				return "Oops! Your account hasn't been verified yet. When you signed up, we sent you an email to verify your account. It looks like you missed this step but it's easy to fix. Please check for an email from Lenovo ID to activate your account or use the Forgot Your Password link and we'll send a new email.";
			case incorrectPassErr:
				return "Incorrect password, please try again. If you forgot your password, please use the link below to reset your password.";
			case accountLockedMsg:
				return "For your security, we've temporarily locked your account as several login attempts failed. Please wait 15 minutes before you try again. If you're still having problems please call the phone number at the top of the page.";
			default:
				return null;
		}
	}
	private enum EnumErrMsg {
		emailErr, fNameErr, lNameErr, passwordErr, confirmPassErr, accecptErr, emailNotMatchErr, passwordNotMatchErr, emailAlreadyExistsErr, accountCreatedThankYouMsg, accountNotVerifiedErr, incorrectPassErr, accountLockedMsg;
	}
	
	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA27159(ITestContext ctx) {
		try {
			this.prepareTest();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			b2cPage = new B2CPage(driver);
			
			// 2, search the address validation rule for CA.
			loginHMC();
			// 3, logon HMC, create an address validation rule for US
			savedEmailPattern =  createAddressEmailRule(emailPattern);
			Dailylog.logInfoDB(3, "Saved email validation rule for US" + savedEmailPattern	, Store, testName);
			// 4, go to  Public site, add product into cart and checkout.
			b2cPage = new B2CPage(driver);
			loginB2CPage();
			//Add one product
			testProduct = testData.B2C.getDefaultMTMPN();
			addPartNumberToCart(b2cPage, testProduct);
			By noStockMess = By.xpath("//*[contains(text(),'No Stock for the Product') or contains(text(),'The product code entered is invalid') "
					+ "or contains(text(),'No Stock for the Product')]");
			if(Common.checkElementDisplays(driver, noStockMess, 5)){
				testProduct = "80Y70063US";
				addPartNumberToCart(b2cPage, testProduct);
			}
			Thread.sleep(3000);
			Assert.assertTrue(driver.getCurrentUrl().toString()
					.contains("cart"));
			Assert.assertTrue(Common.isElementExist(driver,
					By.xpath("//*[@id='mainContent']//div[@class='cart-item']")));
			// click Lenovo checkout on shopping cart page
			driver.findElement(By.xpath("//*[@id='lenovo-checkout-sold-out']"))
					.click();
			Assert.assertTrue(driver.getCurrentUrl().toString()
					.contains("address"));
			Dailylog.logInfoDB(4, "Go to public site ,add product into cart and chenkout.", Store, testName);
			// 5, shipment page, input address and input a gmail personal email address. click on Continue button.
			Common.sleep(3000);
			String currentURL = driver.getCurrentUrl();
			Boolean isPaymentNewUI = currentURL.contains("add-delivery-address");
			if(!isPaymentNewUI){
				if (Common
						.isElementExist(
								driver,
								By.cssSelector(".textLink.checkout-shippingAddress-editLink"))) {
					b2cPage.Shipping_editAddress.click();
				}
			}
			
			//input invalid email
			checkInvalidEmailRule(invalidMail);
			Dailylog.logInfoDB(5, "Input invalid email, click continue button on shipping page.", Store, testName);
			//input valid mail
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Common.sleep(2000);
			B2CCommon.handleAddressVerify(driver, b2cPage);
			Common.sleep(2000);
			if(isPaymentNewUI){
				Assert.assertTrue(driver.getCurrentUrl().contains("add-delivery-address"));
			}else{
				Assert.assertTrue(driver.getCurrentUrl().contains("payment"));
			}
			Dailylog.logInfoDB(6, "Input valid email, click continue button on shipping page.", Store, testName);
			
			// 7, create an address validation rule for CA, audience: default
			loginHMC();
			savedEmailDefaultPattern = createAddressEmailRule(emailDefaultPattern);
			System.out.println(emailDefaultPattern);
			System.out.println(savedEmailDefaultPattern);
			Assert.assertTrue(savedEmailDefaultPattern.equals(emailDefaultPattern));
			Dailylog.logInfoDB(3, "Saved email validation rule for US" + savedEmailDefaultPattern, Store, testName);
			// 8, go to  Public site, add product into cart and checkout.
			b2cPage = new B2CPage(driver);
			loginB2CPage();
			//Add one product
			addPartNumberToCart(b2cPage, testProduct);
			Thread.sleep(3000);
			Assert.assertTrue(driver.getCurrentUrl().toString()
					.contains("cart"));
			Assert.assertTrue(Common.isElementExist(driver,
					By.xpath("//*[@id='mainContent']//div[@class='cart-item']")));
			// click Lenovo checkout on shopping cart page
			driver.findElement(By.xpath("//*[@id='lenovo-checkout-sold-out']"))
					.click();
			Assert.assertTrue(driver.getCurrentUrl().toString()
					.contains("address"));
			Dailylog.logInfoDB(8, "Go to public site ,add product into cart and chenkout.", Store, testName);
			// 9, shipment page, input address and input a gmail personal email address. click on Continue button.
			try {
				b2cPage.Shipping_editAddress.click();
			} catch (Exception e) {
				Dailylog.logInfo("Edit is not present");
			}
			Common.sleep(3000);
			if(!isPaymentNewUI){
				if (Common
						.isElementExist(
								driver,
								By.cssSelector(".textLink.checkout-shippingAddress-editLink"))) {
					b2cPage.Shipping_editAddress.click();
				}
			}
			
//			fillDefaultShippingInfo(b2cPage, testData);
			
			//input invalid email
			checkInvalidEmailRule(invalidMail.split("@")[1]);
			Dailylog.logInfoDB(9, "Input invalid email, click continue button on shipping page.", Store, testName);
			//input valid mail
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Common.sleep(2000);
			B2CCommon.handleAddressVerify(driver, b2cPage);
			Common.sleep(2000);
			if(isPaymentNewUI){
				Assert.assertTrue(driver.getCurrentUrl().contains("add-delivery-address"));
			}else{
				Assert.assertTrue(driver.getCurrentUrl().contains("payment"));
			}
			Dailylog.logInfoDB(9, "Input valid email, click continue button on shipping page.", Store, testName);
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	@AfterTest(alwaysRun = true)
	public void rollback(ITestContext ctx){
		try{
			Dailylog.logInfo("rollback!!!");
			SetupBrowser();
			hmcPage = new HMCPage(driver);
			driver.get(testData.HMC.getHomePageUrl());
			if (Common.checkElementExists(driver, hmcPage.Home_EndSessionLink, 5)) {
				hmcPage.Home_EndSessionLink.click();
			}
			HMCCommon.Login(hmcPage, testData);
			Common.sleep(2000);
			hmcPage.hmc_internationalization.click();
			hmcPage.hmc_addressValidatorRule.click();
			Common.sleep(2000);
			Select countrySel = new Select(
					driver.findElement(By.xpath("//select[contains(@id,'AllInstancesSelectEditor')]")));
			countrySel.selectByVisibleText(country);
			hmcPage.AddCountry_Search.click();
			Common.sleep(2000);
			Common.rightClick(driver, driver.findElement(By.xpath("//img[contains(@id,'OrganizerListEntry[CA-B2C]_img')]")));
			Common.sleep(3000);
			WebElement ele_remove = driver.findElement(By.xpath("//td[contains(@id,'McSearchListConfigurable[AddressValidatorRule]_!remove_true_icon_td')]"));
			ele_remove.click();
			Common.sleep(2000);
			driver.switchTo().alert().accept();
			
		}catch (Throwable e){
			handleThrowable(e,ctx);
		}
	}
	
	public static void addPartNumberToCart(B2CPage b2cPage, String partNum) {
		b2cPage.Cart_QuickOrderTextBox.clear();
		b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
		Common.sleep(1000);
		b2cPage.Cart_AddButton.click();
	}
	
	public void loginB2CPage(){
		driver.get(testData.B2C.getloginPageUrl());
		Common.sleep(2500);
		B2CCommon.handleGateKeeper(b2cPage, testData);
		loginID = testData.B2C.getLoginID();
		pwd = testData.B2C.getLoginPassword();
		B2CCommon.login(b2cPage, loginID, pwd);
		B2CCommon.handleGateKeeper(b2cPage, testData);
		HandleJSpring(driver,b2cPage, loginID, pwd);
		Common.sleep(2000);
		b2cPage.Navigation_CartIcon.click();
		if (Common.checkElementExists(driver, b2cPage.Navigation_ViewCartButton, 5))
			b2cPage.Navigation_ViewCartButton.click();
		Common.sleep(2000);
	}
	
	public void checkInvalidEmailRule(String Mail){
		b2cPage.Shipping_EmailTextBox.clear();
		b2cPage.Shipping_EmailTextBox.sendKeys(Mail);
		Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
		Common.sleep(2000);
		Assert.assertEquals(b2cPage.Shipping_EmailTextBox.getAttribute("data-invalid"), 
				getErrMsgPerStore(testData.Store, EnumErrMsg.emailErr));
		Common.sleep(2000);
		b2cPage.Shipping_EmailTextBox.clear();
		b2cPage.Shipping_EmailTextBox.sendKeys(validMail);
	}
	
	public void loginHMC(){
		hmcPage = new HMCPage(driver);
		driver.get(testData.HMC.getHomePageUrl());
		if (Common.checkElementExists(driver, hmcPage.Home_EndSessionLink, 5)) {
			hmcPage.Home_EndSessionLink.click();
		}
		HMCCommon.Login(hmcPage, testData);
	}
	
	public String createAddressEmailRule(String emailPattern){
		Common.sleep(2000);
		hmcPage.hmc_internationalization.click();
		hmcPage.hmc_addressValidatorRule.click();
		Common.sleep(2000);
		Select countrySel = new Select(
				driver.findElement(By.xpath("//select[contains(@id,'AllInstancesSelectEditor')]")));
		countrySel.selectByVisibleText(country);
		hmcPage.AddCountry_Search.click();
		Common.sleep(5000);
		By by_B2C = By.id("Content/ItemDisplay[B2C]_div2"); 
		if(Common.checkElementDisplays(driver, by_B2C, 3)){
			Common.doubleClick(driver, driver.findElement(by_B2C));
			Common.sleep(3000);
			hmcPage.addressValidatorRule_EmailPattern.clear();
			hmcPage.addressValidatorRule_EmailPattern.sendKeys(emailPattern);
			hmcPage.HMC_Save.click();
		}else{
			Common.rightClick(driver, driver.findElement(By.xpath("//img[contains(@id,'OrganizerListEntry[CA-Default]_img')]")));
			Common.sleep(2000);
			driver.findElement(By.xpath("//td[contains(@id,'McSearchListConfigurable[AddressValidatorRule]_!copytocreator_true_label')]")).click();
			By ruleID = By.id("Content/StringEditor[in Content/Attribute[.zId]]_input");
			WebElement ele_aud = driver.findElement(By.xpath("//*[contains(@id,'EnumerationValueSelectEditor[in Content/Attribute[.zAudience]]')]"));
			Select sel = new Select(ele_aud);
			sel.selectByVisibleText("B2C");
			driver.findElement(ruleID).clear();
			driver.findElement(ruleID).sendKeys("CA-B2C");
			hmcPage.addressValidatorRule_EmailPattern.clear();
			hmcPage.addressValidatorRule_EmailPattern.sendKeys(emailPattern);
			hmcPage.HMC_CreateAndSave.click();
		}
		Common.sleep(3000);
		savedEmailPattern = hmcPage.addressValidatorRule_EmailPattern.getAttribute("value");
		hmcPage.HMC_Logout.click();
		return savedEmailPattern;
	}
	
	public static void fillDefaultShippingInfo(B2CPage b2cPage, TestData testData) {
		B2CCommon.fillShippingInfo(b2cPage, "AutoFirstName", "AutoLastName", testData.B2C.getDefaultAddressLine1(),
				testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
				testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(),
				testData.B2C.getLoginID(), testData.B2C.getConsumerTaxNumber());
	}
	
	public static void fillShippingInfo(B2CPage b2cPage, String firstName, String lastName, String addressline1,
			String city, String state, String postCode, String phone, String... consumerTaxNumber) {
		if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.Shipping_FirstNameTextBox, 3)) {
			b2cPage.Shipping_FirstNameTextBox.clear();
			b2cPage.Shipping_FirstNameTextBox.sendKeys(firstName);
			b2cPage.Shipping_LastNameTextBox.clear();
			b2cPage.Shipping_LastNameTextBox.sendKeys(lastName);
			b2cPage.Shipping_AddressLine1TextBox.clear();
			b2cPage.Shipping_AddressLine1TextBox.sendKeys(addressline1);
			if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.Shipping_CityTextBox, 1)) {
				b2cPage.Shipping_CityTextBox.clear();
				b2cPage.Shipping_CityTextBox.sendKeys(city);
			}
			if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.Shipping_AddressLine3TextBox, 1)) {
				b2cPage.Shipping_AddressLine3TextBox.clear();
				b2cPage.Shipping_AddressLine3TextBox.sendKeys(city);
			}
			Select stateDropdown = new Select(b2cPage.Shipping_StateDropdown);
			stateDropdown.selectByVisibleText(state);
			if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.Shipping_PostCodeTextBox, 1)) {
				b2cPage.Shipping_PostCodeTextBox.clear();
				b2cPage.Shipping_PostCodeTextBox.sendKeys(postCode);
			}
			if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.Shipping_consumerTaxNumber, 1)) {
				b2cPage.Shipping_consumerTaxNumber.clear();
				b2cPage.Shipping_consumerTaxNumber.sendKeys(consumerTaxNumber);
			}
			b2cPage.Shipping_ContactNumTextBox.clear();
			b2cPage.Shipping_ContactNumTextBox.sendKeys(phone);
			// BR
			if (Common.isElementExist(b2cPage.PageDriver, By.id("building"))) {
				b2cPage.PageDriver.findElement(By.id("building")).clear();
				b2cPage.PageDriver.findElement(By.id("building")).sendKeys(city);
			}
			if (Common.isElementExist(b2cPage.PageDriver, By.id("line2"))) {
				b2cPage.PageDriver.findElement(By.id("line2")).clear();
				b2cPage.PageDriver.findElement(By.id("line2")).sendKeys(addressline1);
			}
			if (Common.isElementExist(b2cPage.PageDriver, By.id("district"))) {
				b2cPage.PageDriver.findElement(By.id("district")).clear();
				b2cPage.PageDriver.findElement(By.id("district")).sendKeys("São João Batista");
			}
		}
	}
	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");
		if (Common.isElementExist(driver, Promotion)) {
			Actions actions = new Actions(driver);
			actions.moveToElement(page.PromotionBanner).click().perform();
		}
	}
	public void HandleJSpring(WebDriver driver, B2CPage b2cPage,String loginID, String pwd) {
		if (!driver.getCurrentUrl().contains("account")) {
			driver.get(testData.B2C.getloginPageUrl());
			closePromotion(driver, b2cPage);
			if (Common.isElementExist(driver, By.xpath(".//*[@id='smb-login-button']"))) {
				b2cPage.SMB_LoginButton.click();
			}
			B2CCommon.login(b2cPage, loginID, pwd);
			B2CCommon.handleGateKeeper(b2cPage, testData);
		}
	}
	
	public String findCTOProduct() throws InterruptedException {
		String b2cProductUrl = driver.getCurrentUrl();
		Dailylog.logInfo("b2cProductUrl is :" + b2cProductUrl);
		ProductID = b2cProductUrl.split("/p/")[1].split("#")[0]
				.substring(0, 15);
		Dailylog.logInfo(ProductID);
		return ProductID;
	}
}