package TestScript.B2C;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class NA28439Test extends SuperTestClass {

	B2CPage b2cPage = null;
	private String testProduct;
	private String loginID;
	private String pwd;
	private HMCPage hmcPage;
	private boolean isCreate = false;
	private String illegalName = "1234567";
	private String legalName = "12345678";
	private String ruleName;
	private String country;

	public NA28439Test(String store, String country, String ruleName) {
		this.Store = store;
		this.testName = "NA-28439";
		this.country = country;
		this.ruleName = ruleName;
	}

	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");

		if (Common.isElementExist(driver, Promotion)) {

			Actions actions = new Actions(driver);

			actions.moveToElement(page.PromotionBanner).click().perform();

		}
	}

	public void HandleJSpring(WebDriver driver, B2CPage b2cPage, String loginID, String pwd) {

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

	private void validateRule(String minValue, String maxValue) {

		hmcPage.addressValidatorRule_firstnameMaxLength.clear();
		hmcPage.addressValidatorRule_firstnameMaxLength.sendKeys(maxValue);
		hmcPage.addressValidatorRule_firstnameMinLength.clear();
		hmcPage.addressValidatorRule_firstnameMinLength.sendKeys(minValue);

		hmcPage.addressValidatorRule_lastnameMaxLength.clear();
		hmcPage.addressValidatorRule_lastnameMaxLength.sendKeys(maxValue);
		hmcPage.addressValidatorRule_lastnameMinLength.clear();
		hmcPage.addressValidatorRule_lastnameMinLength.sendKeys(minValue);

		if (isCreate) {

			hmcPage.paymentProfile_paymentCreate.click();
		} else {
			hmcPage.HMC_Save.click();
		}

		Common.sleep(5000);

	}

	private void inputShippingName(String firstName, String lastName) {
		b2cPage.Shipping_FirstNameTextBox.clear();
		b2cPage.Shipping_FirstNameTextBox.sendKeys(firstName);
		b2cPage.Shipping_LastNameTextBox.clear();
		b2cPage.Shipping_LastNameTextBox.sendKeys(lastName);
		
		Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
		Common.sleep(2000);
	}

	private void inputBillingName(String firstName, String lastName) {
		if (b2cPage.Payment_FirstNameTextBox.isEnabled()) {
			b2cPage.Payment_FirstNameTextBox.clear();
			b2cPage.Payment_FirstNameTextBox.sendKeys(firstName);
		}
		if (b2cPage.Payment_LastNameTextBox.isEnabled()) {
			b2cPage.Payment_LastNameTextBox.clear();
			b2cPage.Payment_LastNameTextBox.sendKeys(lastName);
		}
		Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
	}
	
	private void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(windows.get(windowNo));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void searchRule(boolean isRollBack) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);

		hmcPage.hmc_internationalization.click();
		Common.waitElementClickable(driver, hmcPage.hmc_addressValidatorRule, 3);
		hmcPage.hmc_addressValidatorRule.click();
		Common.sleep(2000);
		Select countrySel = new Select(
				driver.findElement(By.xpath("//select[contains(@id,'AllInstancesSelectEditor')]")));
		countrySel.selectByVisibleText(country);
		if (isRollBack) {
			Select audience = new Select(driver.findElement(By.xpath("//select[contains(@id,'zAudience]]')]")));
			audience.selectByVisibleText("B2C");
		}
		Common.sleep(2000);
		hmcPage.AddCountry_Search.click();

	}

	private void addRuleToUnit() {
		Dailylog.logInfoDB(4, "add [auB2CName] to this unit and save", Store, testName);
		HMCCommon.searchB2CUnit(hmcPage, testData);
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		hmcPage.B2CUnit_SiteAttributeTab.click();
		Common.sleep(5000);

		hmcPage.AddressValidatorRule.click();
		switchToWindow(1);
		hmcPage.management_id.sendKeys(ruleName);
		hmcPage.B2BUnit_SearchButton.click();
		hmcPage.B2BUnit_searchedPayment.click();
		hmcPage.B2BUnit_use.click();
		switchToWindow(0);
		hmcPage.B2BUnit_Save.click();
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA28439(ITestContext ctx) {
		try {
			this.prepareTest();

			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			// Step 1 : HMC->Internationalization --> address validation rule
			searchRule(false);
			Common.sleep(5000);
			if (Common.isElementExist(driver, By.xpath("//div[contains(@id,'B2C')]"))) {
				// update
				Dailylog.logInfoDB(2, "have a B2C rule,UPDATE", Store, testName);
				Common.doubleClick(driver, hmcPage.serach_result_B2C);
			} else {

				// Step 2 :create an address validation rule for JP,audience:
				// B2B;
				isCreate = true;
				Dailylog.logInfoDB(2, "create an address validation rule", Store, testName);
				Common.rightClick(driver, hmcPage.serach_result);
				hmcPage.result_clone.click();
				Select audience = new Select(driver.findElement(By.xpath("//select[contains(@id,'zAudience')]")));
				audience.selectByVisibleText("B2C");
				hmcPage.management_id.clear();
				hmcPage.management_id.sendKeys(ruleName);
			}
			// Step 3rule: first、last name length update ：8-12
			// default length:0-30
			Dailylog.logInfoDB(3, "update rule", Store, testName);
			validateRule("8", "12");

			Dailylog.logInfoDB(4, "add rule to this unit and save", Store, testName);
			addRuleToUnit();
			hmcPage.hmcHome_hmcSignOut.click();

			driver.get(testData.B2C.getloginPageUrl());
			testProduct = testData.B2C.getDefaultMTMPN();
			Common.sleep(2500);

			B2CCommon.handleGateKeeper(b2cPage, testData);
			loginID = testData.B2C.getLoginID();
			pwd = testData.B2C.getLoginPassword();
			B2CCommon.login(b2cPage, loginID, pwd);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			HandleJSpring(driver, b2cPage, loginID, pwd);

			Common.sleep(2000);
			b2cPage.Navigation_CartIcon.click();
			if (Common.checkElementExists(driver, b2cPage.Navigation_ViewCartButton, 5))
				b2cPage.Navigation_ViewCartButton.click();
			Thread.sleep(2000);
			if (!Common.isElementExist(b2cPage.PageDriver, By.xpath("//*[contains(@id,'quantity')]"), 5)) {
				b2cPage.Cart_QuickOrderTextBox.sendKeys(testProduct);
				b2cPage.Cart_AddButton.click();
				if (!Common.isElementExist(b2cPage.PageDriver, By.xpath("//*[contains(@id,'quantity')]"), 5)) {
					NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Accessories);
					Thread.sleep(5000);

					if (Common.isElementExist(driver, By.xpath("//div[@class='accessoriesCategories']//span"))) {

						b2cPage.B2C_Accessory_BrowseAllCategory.click();
						if (Common.isElementExist(driver, By.xpath("//div[@id='ChargersBatteries']//h3/a"))) {
							driver.findElement(By.xpath("//div[@id='ChargersBatteries']//h3/a")).click();

						} else {
							b2cPage.B2C_Accessory_Charger.click();
						}

					}

					if (Common.isElementExist(driver,
							By.xpath("(.//*[@id='productGrid-target']//div[@class='thumb']//img)"))) {
						b2cPage.B2C_Accessory_Audio.click();
					}
					b2cPage.B2C_Accessory_SubAccessory.click();

					Thread.sleep(2000);

					b2cPage.Add2Cart.click();

					if (Common.isElementExist(driver, By.xpath(".//*[contains(@class,'addToCartPopupButton')]"))) {
						b2cPage.B2C_Accessory_SubAccessory_AddToCartPopup.click();
						// Dailylog.logInfoDB(7, "Clicked on Add to cart
						// popup.", Store, testName);
					}

					if (Common.isElementExist(driver, By.xpath(".//a[contains(@class,'addedToCart')]"))) {
						Thread.sleep(5000);
						Common.javascriptClick(driver,
								driver.findElement(By.xpath(".//a[contains(@class,'addedToCart')]")));

					}
				}
			}

			Thread.sleep(2000);
			Dailylog.logInfoDB(2, "Clicked checkout.", Store, testName);
			b2cPage.Cart_CheckoutButton.click();

			// Fill shipping info
			Actions actions = new Actions(driver);

			if (isElementExsit(driver,
					By.xpath("//fieldset/legend/a[contains(@class,'checkout-shippingAddress-editLink')]"))) {
				actions.moveToElement(b2cPage.EditAddress).click().perform();
			}
			// if (isElementExsit(driver, CopyAddress)) {
			// actions.moveToElement(b2cPage.CopyAddress).click().perform();
			// }
			Dailylog.logInfoDB(6, "check first name on shiiping page", Store, testName);
			B2CCommon.fillShippingInfo(b2cPage, illegalName, legalName, testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), loginID);
			Dailylog.logInfoDB(2, "Clicked Shipping_ContinueButton.", Store, testName);
			Thread.sleep(5000);
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Assert.assertTrue(Common.checkElementDisplays(driver, b2cPage.Shipping_ContinueButton, 5));
			
			Dailylog.logInfoDB(6, "check last name on shipping page", Store, testName);
			inputShippingName(legalName, illegalName);
			Assert.assertTrue(Common.checkElementDisplays(driver, b2cPage.Shipping_ContinueButton, 5));
			
			Dailylog.logInfoDB(6, "go to payment page", Store, testName);
			inputShippingName(legalName,legalName);
			
			if (Common.isElementExist(driver, By.id("address_sel"))) {
				// US
				Select dropdown = new Select(driver.findElement(By.id("address_sel")));
				dropdown.selectByIndex(1);
				b2cPage.Shipping_AddressMatchOKButton.click();
			} else if (Common.isElementExist(driver, By.xpath("//li[@class='list-group-item']/input[@type='radio']"))) {
				if (b2cPage.Shipping_validateAddressItem.isDisplayed()) {
					// USEPP
					b2cPage.Shipping_validateAddressItem.click();
				}
				// USBPCTO
				b2cPage.Shipping_AddressMatchOKButton.click();
			}
			
			Assert.assertFalse(Common.checkElementDisplays(driver, b2cPage.Shipping_ContinueButton, 5));

			if (Common.checkElementExists(driver, b2cPage.Payment_bankDepositLabel, 5)) {
				Common.javascriptClick(driver,
						driver.findElement(By.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']")));

			} else if (Common.isElementExist(driver, By.xpath("//*[@id='PaymentTypeSelection_CARD']"))) {
				// Fill payment info
				Common.javascriptClick(driver, driver.findElement(By.xpath("//*[@id='PaymentTypeSelection_CARD']")));
				B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			}
			
			if (Common.checkElementDisplays(driver, b2cPage.Payment_FirstNameTextBox, 5)) {
				Dailylog.logInfoDB(6, "check first name on payment page", Store, testName);
				inputBillingName(illegalName,legalName);
				Assert.assertFalse(driver.getTitle().contains("Checkout"));

				
				Dailylog.logInfoDB(6, "check last name on payment page", Store, testName);		
				inputBillingName(legalName,illegalName);
				Assert.assertFalse(driver.getTitle().contains("Checkout"));
				
				inputBillingName(legalName,legalName);
				
				Assert.assertTrue(driver.getTitle().contains("Checkout"));
			}else{
				Dailylog.logInfoDB(6, " first name cannot edit on payment page", Store, testName);
			}
			
			
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
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

	public static String getStringDateShort(int gap) {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, gap);
		date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String dateString = formatter.format(date);
		return dateString;
	}

	@Test(alwaysRun = true, priority = 1, enabled = true)
	public void rollback(ITestContext ctx) throws InterruptedException, MalformedURLException {
		try {
			Dailylog.logInfoDB(8, "rollback", Store, testName); // roll back
			SetupBrowser();
			hmcPage = new HMCPage(driver);
			searchRule(true);
			if (Common.isElementExist(driver, By.xpath("//div[contains(@id,'B2C')]"))) {

				Dailylog.logInfoDB(8, "rollback run to default length", Store, testName);
				Common.doubleClick(driver, hmcPage.serach_result_B2C);
				// default length:0-30
				Dailylog.logInfoDB(8, "update rule", Store, testName);
				isCreate=false;
				validateRule("0", "30");

			}

			hmcPage.hmcHome_hmcSignOut.click();

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}
}
