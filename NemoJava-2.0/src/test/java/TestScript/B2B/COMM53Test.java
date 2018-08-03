package TestScript.B2B;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class COMM53Test extends SuperTestClass {
	private B2BPage b2bPage;
	private HMCPage hmcPage;
	public MailPage mailPage;
	private String CartPage_EmptyCartButton = ".//*[@id='emptyCartItemsForm']/a";
	String ProductNo;
	String Rep_Value = "2900718028";
	String QuoteStatus;
	String QuotePartNumber;
	String QuoteId;
	String QuoteQuantity;
	String QuoteTotalPrice;
	String Unit_One;
	String Unit_Two;
	String streetName;
	String cityName;
	String stateName;
	String postal;
	String region;

	public COMM53Test(String store,String street, String city,String state,String pin,String regionCode) {
		this.Store = store;
		this.streetName=street;
		this.cityName=city;
		this.stateName=state;
		this.postal=pin;
		this.region=regionCode;
		this.testName = "COMM-53";
	}

	public void closePromotion(WebDriver driver, B2BPage page) {
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");

		
		if (Common.isElementExist(driver, Promotion)) {

			Actions actions = new Actions(driver);

			actions.moveToElement(page.PromotionBanner).click().perform();

		}
	}

	public void HandleJSpring(WebDriver driver, B2BPage b2bPage, String loginID, String pwd) {

		if (driver.getCurrentUrl().contains("security")) {

			driver.get(testData.B2B.getLoginUrl());
			closePromotion(driver, b2bPage);
			// if (Common.isElementExist(driver,
			// By.xpath(".//*[@id='smb-login-button']"))) {
			// b2bPage.SMB_LoginButton.click();
			// }
			B2BCommon.Login(b2bPage, loginID, pwd);

		}
	}

	@Test(alwaysRun = true, groups = { "commercegroup", "cartcheckout", "p2", "b2b" })
	public void COMM52(ITestContext ctx) {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			
			// Step~1 : Go to HMC and enable request quote
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(1, "Logged in into HMC", Store, testName);
			Common.sleep(5000);
			createAddress();
			hmcPage.B2BUnit_siteAttribute.click();
		       
			hmcPage.enableB2BQuoteApprovedEmail.click();
			hmcPage.enableB2BEmailQuote.click();
			hmcPage.enableB2BQuoteSendEmail.click();
			hmcPage.enableQuoteApproval.click();
			hmcPage.enableQuoteConvert.click();
			hmcPage.enableB2BQuoteReviewerRemindSendEmail.click();
			hmcPage.enableB2BQuoteReviewerReassignedRemindSendEmail.click();

			hmcPage.B2BUnit_isQuoteAvailable.click();
			hmcPage.addressFromCRM.click();

			hmcPage.B2BUnit_Save.click();
			hmcPage.HMC_Logout.click();
			
			// step~2
			driver.get(testData.B2B.getLoginUrl());

			B2BCommon.Login(b2bPage, testData.B2B.getBuilderId(), testData.B2B.getDefaultPassword());
			HandleJSpring(driver, b2bPage, testData.B2B.getBuilderId(), testData.B2B.getDefaultPassword());
			// step~3
			Common.sleep(3000);
			b2bPage.HomePage_CartIcon.click();
			Common.sleep(1500);
			if (Common.isElementExist(driver, By.xpath(CartPage_EmptyCartButton))) {
				driver.findElement(By.xpath(CartPage_EmptyCartButton)).click();
			}
			B2BCommon.addProduct(driver, b2bPage, testData.B2B.getDefaultMTMPN1());
			Common.sleep(2000);
			b2bPage.cartPage_LenovoCheckout.click();

			B2BCommon.fillB2BShippingInfo(driver, b2bPage, Store, "FirstNameJohn", "LastNameSnow",
					testData.B2B.getCompany(), testData.B2B.getAddressLine1(), testData.B2B.getAddressCity(),
					testData.B2B.getAddressState(), testData.B2B.getPostCode(), testData.B2B.getPhoneNum());

			Common.javascriptClick(driver, b2bPage.shippingPage_ContinueToPayment);
			if (Common.isElementExist(driver, By.id("checkout_validateFrom_ok"))) {
				b2bPage.shippingPage_validateFromOk.click();
			}
			Common.sleep(5000);
			if (Common.isElementExist(driver, By.id("address_sel"))) {
				// US
				Select dropdown = new Select(driver.findElement(By.id("address_sel")));
				dropdown.selectByIndex(1);
				b2bPage.shippingPage_validateFromOk.click();
			}
			
			b2bPage.paymentPage_PurchaseOrder.click();
			Common.sleep(2000);
			b2bPage.paymentPage_purchaseNum.sendKeys("1234567890");
			b2bPage.paymentPage_purchaseDate.sendKeys(Keys.ENTER);
			
            b2bPage.paymentPage_inputAddressFilter.click();
            b2bPage.paymentPage_inputAddressFilter.clear();
            b2bPage.paymentPage_inputAddressFilter.sendKeys(cityName);
            driver.findElement(By.xpath("//li[contains(text(),'"+cityName+"')]")).click();
            Common.sleep(3000);
//            assert b2bPage.paymentPage_companyName.getText().equals("testBilling");
			
			Common.javascriptClick(driver, b2bPage.paymentPage_ContinueToPlaceOrder);
			
			if(Common.isElementExist(driver, By.xpath("//*[@id='resellerID']"))){
				b2bPage.placeOrderPage_ResellerID.clear();
				b2bPage.placeOrderPage_ResellerID.sendKeys("2900718028");
	
			}
			
			b2bPage.placeOrderPage_Terms.click();
			driver.findElement(
					By.xpath("//select[@id='approveId']/option[@value='"
							+ testData.B2B.getApproverId2().toUpperCase() + "']")).click();
			b2bPage.placeOrderPage_sendForApproval.click();
			String OrderId=b2bPage.placeOrderPage_OrderNumber.getText();
		
			Dailylog.logInfoDB(5, "Order Id : " + OrderId, Store, testName);
			
			mailPage = new MailPage(driver);
			EMailCommon.goToMailHomepage(driver);
			EMailCommon.createEmail(driver, mailPage, "auapprover2");
			
			Common.sleep(15000);
			checkEmailContent(driver, mailPage, "pending order approval", "testBilling", testData.B2B.getCompany());
			Dailylog.logInfoDB(10, "verify approver email company name", Store, testName);
			driver.get(testData.B2B.getLoginUrl());

			B2BCommon.Login(b2bPage, testData.B2B.getApproverId2(), testData.B2B.getDefaultPassword());
			HandleJSpring(driver, b2bPage, testData.B2B.getApproverId2(), testData.B2B.getDefaultPassword());
            
			b2bPage.homepage_MyAccount.click();
			b2bPage.myAccountPage_viewOrderRequireApproval.click();
			
			driver.findElement(By.xpath("(.//*[@id='" + OrderId + "'])[1]")).click();
			b2bPage.QuotePage_clickApproveButton.click();
			Dailylog.logInfoDB(10, "Order approved successfully", Store, testName);
			
			EMailCommon.goToMailHomepage(driver);
			EMailCommon.createEmail(driver, mailPage, "aubuilder");
			
			Common.sleep(15000);
			checkEmailContent(driver, mailPage, OrderId, "testBilling", testData.B2B.getCompany());
			Dailylog.logInfoDB(10, "verify builder email company name", Store, testName);
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public void createAddress(){
		HMCCommon.searchB2BUnit(hmcPage, testData);
		Common.sleep(4500);
        hmcPage.B2BCustomer_AddressesTab.click();
        String addressField="Content/StringEditor[in Content/CreateItemListEntry[n/a]]";
        if( !Common.checkElementDisplays(driver, By.xpath("//img[contains(@id,'"+streetName+"')]"), 5)){
        	  Common.rightClick(driver, driver.findElement(By.xpath("//div[contains(text(),'Postal Code')]")));
              hmcPage.Address_CreateAddress.click();
              Common.sleep(3000);
              driver.findElement(By.id(addressField+"_input")).sendKeys(postal);
              driver.findElement(By.id(addressField+"[1]_input")).sendKeys(cityName);
              driver.findElement(By.id(addressField+"[2]_input")).sendKeys(streetName);
              hmcPage.Address_SaveAddress.click();
              Common.sleep(3000);
              driver.findElement(By.xpath("//img[contains(@id,'"+streetName+"')]")).click();
              switchToWindow(1);
              hmcPage.Address_setBillingAddress.click();
              hmcPage.Address_visibleforIndirect.click();
              hmcPage.Address_CompanyName.clear();
              hmcPage.Address_CompanyName.sendKeys("testBilling");
              Select sel = new Select(driver.findElement(By.xpath("//*[contains(@id,'[Address.country]]_select')]")));
              sel.selectByVisibleText("Australia");
              hmcPage.Address_regionSetting.click();
              switchToWindow(2);
              hmcPage.Address_regionISO.sendKeys(region);
              hmcPage.Address_RegionSearch.click();
              Common.doubleClick(driver, hmcPage.Address_FirstRegion);
              switchToWindow(1);
              driver.findElement(By.id("ImageToolbarAction[organizer.editor.save.title]_label")).click();
                    
              switchToWindow(0);
              hmcPage.Address_SaveAddress.click();
              Common.sleep(3000);
        }
	}

	public static Boolean checkEmailContent(WebDriver driver, MailPage mailPage, String title, String billingCompany,
			String shippingCompany) {

		Boolean reciverEmail = false;
		Common.sleep(10000);
		List<WebElement> emailTile = driver.findElements(By.xpath("//td[contains(text(),'" + title + "')]"));
		int totalEmail = emailTile.size();
		if (totalEmail == 0) {
			System.out.println(
					"email with this subject is not yet received...!!! Refreshing the inbox after 10 seconds......");
			Common.sleep(10000);
		} else {
			System.out.println(emailTile.size());
			emailTile.get(0).click();
			Common.sleep(5000);
			WebElement emailShipingCompany = driver
					.findElement(By.xpath("//div[@class='email_body']//td[contains(text()[5],'" + shippingCompany + "')]"));
			Assert.assertTrue(Common.checkElementDisplays(driver, emailShipingCompany, 5),
					"billing company name is not correct");

			WebElement shippingName = driver
					.findElement(By.xpath("//div[@class='email_body']//td[contains(text()[5],'" + billingCompany + "')]"));
			Assert.assertTrue(Common.checkElementDisplays(driver, shippingName, 5),
					"shipping company name is not correct");

		}

		return reciverEmail;
	}

	public void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(
					driver.getWindowHandles());
			driver.switchTo().window(windows.get(windowNo));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
