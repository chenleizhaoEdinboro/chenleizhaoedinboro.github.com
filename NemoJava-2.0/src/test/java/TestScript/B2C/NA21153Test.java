package TestScript.B2C;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class NA21153Test extends SuperTestClass {

	B2CPage b2cPage = null;
	private String testProduct;
	private String loginID;
	private String pwd;

	public NA21153Test(String store) {
		this.Store = store;
		this.testName = "NA-21153";
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

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA21153(ITestContext ctx) {
		try {
			this.prepareTest();

			int previousRecordsNumber = 0;
			int afterRecordsNumber = 0;
			By CopyAddress = By.xpath("//input[@id='copyAddressToBilling']");

			// Update HMC configurations

			b2cPage = new B2CPage(driver);
			driver.get(testData.B2C.getloginPageUrl());
			testProduct = testData.B2C.getDefaultMTMPN();
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

			String tempEmail = EMailCommon.getRandomEmailAddress();
			String firstName = Common.getDateTimeString();
			String lastName = Common.getDateTimeString();

			// Fill shipping info
			Actions actions = new Actions(driver);

			if (isElementExsit(driver,
					By.xpath("//fieldset/legend/a[contains(@class,'checkout-shippingAddress-editLink')]"))) {
				actions.moveToElement(b2cPage.EditAddress).click().perform();
			}
			// if (isElementExsit(driver, CopyAddress)) {
			// actions.moveToElement(b2cPage.CopyAddress).click().perform();
			// }

			B2CCommon.fillShippingInfo(b2cPage, firstName, lastName, testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), tempEmail);
			Dailylog.logInfoDB(2, "Clicked Shipping_ContinueButton.", Store, testName);
			Thread.sleep(5000);
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
//			if (Common.checkElementExists(driver, b2cPage.Shipping_AddressMatchOKButton, 10)) {
//				b2cPage.Shipping_AddressMatchOKButton.click();
//				
//			}
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
			if (Common.checkElementExists(driver, b2cPage.Payment_bankDepositLabel,5)) {
				Common.javascriptClick(driver, driver.findElement(By
						.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']")));
				
			}else if(Common.isElementExist(driver, By
					.xpath("//*[@id='PaymentTypeSelection_CARD']"))){
				// Fill payment info
				Common.javascriptClick(driver, driver.findElement(By
						.xpath("//*[@id='PaymentTypeSelection_CARD']")));
				B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			}
			
			B2CCommon.fillPaymentAddressInfo(b2cPage, firstName, lastName, testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
			
			Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
			Dailylog.logInfoDB(2, "Clicked Payment_ContinueButton.", Store, testName);

			// Place Order
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.open('about:blank','_blank');", "");
			Set<String> winHandels = driver.getWindowHandles();
			List<String> it = new ArrayList<String>(winHandels);
			driver.switchTo().window(it.get(1));
			driver.get(testData.HMC.getHomePageUrl());

			HMCPage hmcPage = new HMCPage(driver);
			// driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			previousRecordsNumber = HMCCommon.getOrderRecordsNumber(driver, hmcPage, getStringDateShort(0),
					testData.B2C.getLoginID());
			driver.switchTo().window(it.get(0));
			B2CCommon.clickPlaceOrder(b2cPage);
			driver.switchTo().window(it.get(1));
			HMCCommon.isOrderGenerated(driver, hmcPage, previousRecordsNumber);

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
}
