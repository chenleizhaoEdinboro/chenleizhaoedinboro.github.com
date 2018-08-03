package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class NA17640Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public MailPage mailPage;
	public String productNO;

	String tempEmailName = Common.getDateTimeString();

	public NA17640Test(String store) {
		this.Store = store;
		this.testName = "NA-17640";
	}

	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");

		if (Common.isElementExist(driver, Promotion)) {
			Actions actions = new Actions(driver);
			actions.moveToElement(page.PromotionBanner).click().perform();

		}
	}
	
	public void HandleJSpring(WebDriver driver) {

		if (driver.getCurrentUrl().contains("j_spring_security_check")) {

			driver.get(driver.getCurrentUrl().replace(
					"j_spring_security_check", "login"));
			closePromotion(driver, b2cPage);
			if (Common.isElementExist(driver,
					By.xpath(".//*[@id='smb-login-button']"))) {
				b2cPage.SMB_LoginButton.click();
			}
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(),
					testData.B2C.getLoginPassword());
			B2CCommon.handleGateKeeper(b2cPage, testData);
		}
	}
	
	
	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA17640(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			mailPage = new MailPage(driver);

			// step~1,2,3&4
			// Login into HMC and set Email cart function
			EnableEmailSettingHMC(1, "false", "true");
			Common.sleep(2000);

			// +=+=+=+=+=+Step~5 : login into B2C web site as a
			// buyer=+=+=+=+=+//
			// close login page pop-up
			if (Common.isElementExist(driver, By.xpath(".//*[@id='window-close']/img"))) {
				Dailylog.logInfoDB(5, "pop-up is present", Store, testName);
				Common.sleep(2000);
				driver.findElement(By.xpath(".//*[@id='window-close']/img")).click();
				Dailylog.logInfoDB(5, "Closed Pop-up", Store, testName);
			} else {
				Dailylog.logInfoDB(5, "pop-up is not present", Store, testName);
			}
			B2CLoginProcess(5);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			// Adding a product to cart
			b2cPage.Navigation_CartIcon.click();
			if (Common.checkElementExists(driver, b2cPage.Navigation_ViewCartButton, 5))
				b2cPage.Navigation_ViewCartButton.click();
			if (Common.isElementExist(driver, By.xpath("//a[contains(text(),'Empty cart')]"))) {
				driver.findElement(By.xpath("//a[contains(text(),'Empty cart')]")).click();
			}
			Common.sleep(2000);
			// Add a product

		    B2CCommon.clearTheCart(driver, b2cPage, testData);
			productNO = testData.B2C.getDefaultMTMPN();
			addPartNumberToCart(b2cPage, productNO);
			By noStockMess =  By.xpath("//*[contains(text(),'No Stock for the Product') or contains(text(),'is invalid') "
					+ "or contains(text(),'查無此產品型號') or contains(text(),'ご入力された製品番号は有効')]");
			if(Common.checkElementDisplays(driver, noStockMess, 5)){
				productNO = productNum(this.Store);
				addPartNumberToCart(b2cPage, productNO);
			}
			Dailylog.logInfoDB(5, "Added product no. : " + productNO, Store, testName);
			Common.sleep(2000);
			
			//TODO login
			
			Common.javascriptClick(driver, b2cPage.Cart_saveCart);
			b2cPage.Cart_nameTextBox.sendKeys("" + tempEmailName);
			b2cPage.Cart_saveCartBtn.click();
			Dailylog.logInfoDB(5, "Saved Cart with Name :" + "" + tempEmailName, Store, testName);
			// step~6
			ViewSavedCart(6);
			Assert.assertFalse(Common.checkElementDisplays(driver, By.xpath("//div/a[contains(@href,'EmailCart')]"), 3),
					"Email Cart Link is present.");
			
			// step~7&8
			EnableEmailSettingHMC(7, "true", "false");
			
			// step~9
			// close login page pop-up
			if (Common.isElementExist(driver, By.xpath(".//*[@id='window-close']/img"))) {
				Dailylog.logInfoDB(5, "pop-up is present", Store, testName);
				Common.sleep(2000);
				driver.findElement(By.xpath(".//*[@id='window-close']/img")).click();
				Dailylog.logInfoDB(5, "Closed Pop-up", Store, testName);
			} else {
				Dailylog.logInfoDB(5, "pop-up is not present", Store, testName);
			}
			B2CLoginProcess(9);
//			HandleJSpring(driver);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			
			// step~10
			ViewSavedCart(10);
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//div/a[contains(@href,'EmailCart')]"), 3),
					"Email cart link is not present");
			
			// step~11
			b2cPage.SavedCart_EmailCart.click();
			Common.switchToWindow(driver, 1);
			// ~~~~~~~~~~~~~~~~~~~~Validate redirected
			// Page~~~~~~~~~~~~~~~~~~~~~~~~~~//
			Assert.assertTrue(driver.getCurrentUrl().contains("EmailCartInfo"), "Page has not redirected Properly");
			WebElement SavedCart_disableEmailInputBox1 = driver.findElement(By.id("senderAddress"));
			Assert.assertTrue(SavedCart_disableEmailInputBox1.isDisplayed(), "Email Input box is not disabled.");
			
			// step~12
			Assert.assertTrue(b2cPage.SavedCart_FirstName.getText().isEmpty(), "Name field is not Empty. Step:12(1)");
			Common.sendFieldValue(b2cPage.SavedCart_FirstName, "17640");
			Common.sleep(2000);
			b2cPage.SavedCart_ClearBtn.click();
			Common.sleep(2000);
			Assert.assertTrue(b2cPage.SavedCart_FirstName.getText().isEmpty(), "Name field is not Empty. Step:12(2)");
			
			// step~13
			Common.sendFieldValue(b2cPage.SavedCart_FirstName, "17640");
			Common.sleep(1000);
			b2cPage.SavedCart_inputCheckBox.click();
			Dailylog.logInfoDB(13, "Clicked on check input box of I certify...", Store, testName);
			b2cPage.SavedCart_sendButton.click();
			Common.sleep(5000);
			Assert.assertTrue(b2cPage.SavedCart_EmailSuccessMesg.isDisplayed(),
					"Email success message is not displayed");
			Dailylog.logInfoDB(13, "Before driver closes", Store, testName);
			driver.close();
			Common.switchToWindow(driver, 0);
			
			// step~14
			String[] emailAddress = testData.B2C.getLoginID().split("@");
			if(!(this.Store=="US_BPCTO")){
				CheckInbox(emailAddress[0]);
			}
			
			// step~15&16
			EnableEmailSettingHMC(15, "true", "true");
			Common.sleep(2000);
			
			// step~18
			// close login page pop-up
			if (Common.isElementExist(driver, By.xpath(".//*[@id='window-close']/img"))) {
				Dailylog.logInfoDB(5, "pop-up is present", Store, testName);
				Common.sleep(2000);
				driver.findElement(By.xpath(".//*[@id='window-close']/img")).click();
				Dailylog.logInfoDB(5, "Closed Pop-up", Store, testName);
			} else {
				Dailylog.logInfoDB(5, "pop-up is not present", Store, testName);
			}
			EMailCommon.createEmail(driver, mailPage, tempEmailName);
			B2CLoginProcess(18);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			ViewSavedCart(18);
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//div/a[contains(@href,'EmailCart')]"), 3),
					"Email cart link is not present");
			
			// step~19
			b2cPage.SavedCart_EmailCart.click();
			Common.switchToWindow(driver, 1);
			
			// Step~20
			Assert.assertTrue(b2cPage.SavedCart_FirstName.getText().isEmpty(), "Name field is not Empty. Step:19(1)");
			Common.sendFieldValue(b2cPage.SavedCart_FirstName, "17640");
			b2cPage.SavedCart_ClearBtn.click();
			Assert.assertTrue(b2cPage.SavedCart_FirstName.getText().isEmpty(), "Name field is not Empty. Step:19(2)");
			Assert.assertTrue(b2cPage.SavedCart_EmailInputBox.getText().isEmpty(), "Name field is not Empty. Step:19");
			Common.sendFieldValue(b2cPage.SavedCart_FirstName, "17640");
			String FirstMail = tempEmailName + "@sharklasers.com";
//			String SecondMail = tempEmailName + 1 + "@sharklasers.com";
			b2cPage.EmailCart_SenderEmail.clear();
			Common.sendFieldValue(b2cPage.EmailCart_SenderEmail, FirstMail);
			Common.sleep(2000);
			if(!this.Store.equals("JP")){
				b2cPage.SavedCart_ChkOutThisProd.click();
			}
			b2cPage.SavedCart_inputCheckBox.click();
			b2cPage.SavedCart_sendButton.click();
			Assert.assertTrue(b2cPage.SavedCart_EmailSuccessMesg.isDisplayed(),
					"Email success message is not displayed");
			driver.close();
			Common.switchToWindow(driver, 0);
			
			// step~21 : check both mail
			CheckInbox(tempEmailName);
//			CheckInbox(tempEmailName + 1);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}
	
	public String productNum(String store){
		switch(Store){
		case "GB":
			return "80Y80015UK";
		case "HKZF":
			return "80Y7000NHH";
		case "JP":
			return "80Y7000KJP";
		case "AU":
			return "20LD0003AU";
		case "US":
			return "80Y70063US";
		case "CA_AFFINITY":
			return "80Y70063US";
		case "USEPP":
			return "80Y70063US";
		case "US_BPCTO":
			return "80Y70063US";
		default:
			return null;
		
		}	
			
	}

	// =*=*=*=*=*=*=*=*=*= Changing Email Cart On Web Toggle and Email Cart
	// Input Toggle =*=*=*=*=*=*==*=*=//
	public void EnableEmailSettingHMC(int n, String data1, String data2) {
		driver.manage().deleteAllCookies();
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		Dailylog.logInfoDB(n, "Logged in into HMC", Store, testName);
		hmcPage.Home_B2CCommercelink.click();
		hmcPage.Home_B2CUnitLink.click();
		Common.sendFieldValue(hmcPage.B2CUnit_IDTextBox, testData.B2C.getUnit());
		hmcPage.B2CUnit_SearchButton.click();
		Common.sleep(2000);
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		Dailylog.logInfoDB(n, "Clicked on search result for B2C unit", Store, testName);

		new WebDriverWait(driver, 500)
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(.,'Site Attributes')]")));
		hmcPage.B2CUnit_SiteAttributeTab.click();
		Common.waitElementClickable(driver, driver.findElement(
				By.xpath("//div/input[contains(@id,'enableEmailCartOnWeb')][@value='" + data1 + "']")), 30);
		// Email Cart On Web
		driver.findElement(By.xpath("//div/input[contains(@id,'enableEmailCartOnWeb')][@value='" + data1 + "']"))
				.click();
		Dailylog.logInfoDB(n, "Changed value of Email Cart on web is : " + data1, Store, testName);
		// Email Cart Input
		driver.findElement(By.xpath("//div/input[contains(@id,'enbaleEmailCartInput')][@value='" + data2 + "']"))
				.click();
		Dailylog.logInfoDB(n, "Changed value of Email Cart input is : " + data2, Store, testName);
		hmcPage.Types_SaveBtn.click();
		Common.sleep(2000);
		hmcPage.HMC_Logout.click();
		Common.sleep(2000);
	}

	public void B2CLoginProcess(int n) {
		driver.get(testData.B2C.getloginPageUrl());
//		driver.manage().deleteAllCookies();
		driver.get(testData.B2C.getloginPageUrl());
		
		Common.sleep(5000);

		// close login page pop-up
		if (Common.isElementExist(driver, By.xpath(".//*[@id='window-close']/img"))) {
			Dailylog.logInfoDB(n, "pop-up is present", Store, testName);
			Common.sleep(2000);
			driver.findElement(By.xpath(".//*[@id='window-close']/img")).click();
			Dailylog.logInfoDB(n, "Closed Pop-up", Store, testName);
		} else {
			Dailylog.logInfoDB(n, "pop-up is not present", Store, testName);
		}

		B2CCommon.handleGateKeeper(b2cPage, testData);
		// Buyer Login
		B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
		Dailylog.logInfoDB(n, "Logged in into B2C...", Store, testName);
		HandleJSpring(driver);
		B2CCommon.handleGateKeeper(b2cPage, testData);
		Common.sleep(1000);
	}
	
	public static void addPartNumberToCart(B2CPage b2cPage, String partNum) {
		Common.sleep(3000);
		b2cPage.Cart_QuickOrderTextBox.clear();
		b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
		Common.sleep(1000);
		b2cPage.Cart_AddButton.click();
	}

	public void ViewSavedCart(int n) {
		if (!driver.getCurrentUrl().contains("save-carts")) {
			Common.mouseHover(driver, b2cPage.Navigation_MyAccountIcon);
			Common.sleep(1000);
			Common.javascriptClick(driver, b2cPage.Navigation_MyAccountIcon);
//			Common.javascriptClick(driver, b2cPage.saveCart_MyAccount);

			Common.sleep(2000);
			Assert.assertTrue(driver.getCurrentUrl().contains("pre-c"), "URL has changed...plz check");
			Common.javascriptClick(driver, b2cPage.MyAccount_ViewSavedCartHistory);
		}
		driver.findElement(By.xpath("//td[contains(.,'" + "" + tempEmailName + "')]/../td/a/div")).click();
		Dailylog.logInfoDB(n, "clicked on view link for save cart " + "" + tempEmailName, Store, testName);
		Common.sleep(3500);
	}

	// =*=*=*=*=*=*=*=*=Login into Guerrilla mail=*=*=*=*=*=*=//
	private void LoginGuerrilla(String MailName) {
		EMailCommon.goToMailHomepage(driver);
		Common.sleep(2000);
		mailPage.Inbox_EditButton.click();
		mailPage.Inbox_InputEmail.clear();
		mailPage.Inbox_InputEmail.sendKeys(MailName);
		mailPage.Inbox_SetButton.click();
		Dailylog.logInfoDB(6, "Clicked on Set button", Store, testName);
	}

	// =*=*=*=*=*=*=*Checking Mail content*=*=*=*=*=*=*=*=*=*=*//
	private void Check_Content(int i) {
		Dailylog.logInfoDB(6, "Checking mail content...", Store, testName);
		if (Common.isElementExist(driver, By.xpath("(//td[contains(.,'Cart From')])[1]")) == true) {
			mailPage.SavedCart_EmailSub17640.click();
			Common.sleep(4000);
			// checking Saved Cart
			Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'" + productNO + "')]"))
					.isDisplayed(), "Saved Cart for same Product is not present.");
			mailPage.Mail_backToInbox.click();
		} else if (i < 6) {
			Dailylog.logInfoDB(6, "Mail is not present...check later", Store, testName);
			Check_Content(++i);
		}
	}

	// =*=*=*=*=*=*=*=*=*Checking Mail and verifying Saved carts
	// seal=*=*=*=*=*=*=*=//
	private void CheckInbox(String MailName) {
		Dailylog.logInfoDB(6, "Checking email...", Store, testName);
		LoginGuerrilla(MailName);
		Assert.assertTrue(EMailCommon.checkIfEmailReceived(driver, mailPage, "17640"));
		Common.sleep(4000);
		Check_Content(5);
	}

}