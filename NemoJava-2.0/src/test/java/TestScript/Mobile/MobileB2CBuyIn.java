package TestScript.Mobile;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.MobileSetup;
import TestScript.SuperTestClass;

public class MobileB2CBuyIn extends SuperTestClass {
	String ProductID = null;
	B2CPage b2cPage = null;
	HMCPage hmcPage = null;
	Actions actions = null;
	String orderNum = null;

	public MobileB2CBuyIn(String store) {
		this.Store = store;
		this.testName = "MobileB2CBuyIn";
	}

	@Test(alwaysRun= true)
	public void NA16452(ITestContext ctx) throws InterruptedException, MalformedURLException {
		By shippingContinue = By
				.xpath(".//*[@id='checkoutForm-shippingContinueButton']");
		By popupOK = By.xpath("//input[@value='ok']");
		By copyAddressButton = By.xpath("//input[@id='copyAddressToBilling']");
		By externalPW = By.xpath("//input[@name='external.field.password']");
		this.prepareTest();
		b2cPage = new B2CPage(driver);
		actions = new Actions(driver);
		driver.get(testData.B2C.getloginPageUrl().replace("/login", ""));
		/*if (!driver.getCurrentUrl().endsWith("RegistrationGatekeeper"))
			B2CCommon.handleGateKeeper(b2cPage, testData);
		B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
		if (!driver.getCurrentUrl().endsWith("RegistrationGatekeeper"))
			B2CCommon.handleGateKeeper(b2cPage, testData);*/
		b2cPage.mainMenu.click();
		b2cPage.Navigation_ProductsLink.click();
		b2cPage.Navigation_Accessory.click();
		Common.waitElementClickable(driver, b2cPage.firstAccessoryType, 5);
		b2cPage.firstAccessoryType.click();
		b2cPage.firstAccessoryProduct.click();
		b2cPage.accessoryAddtoCart.click();
		Common.waitElementClickable(driver, b2cPage.Cart_CheckoutButton, 5);
		b2cPage.Cart_CheckoutButton.click();
		Common.waitElementClickable(driver, b2cPage.GuestCheckout, 5);
		b2cPage.GuestCheckout.click();
		Common.waitElementClickable(driver, b2cPage.EditAddress, 5);
		
        if (Common.isElementExist(driver, copyAddressButton)) {
			actions.moveToElement(b2cPage.CopyAddress).click().perform();
		}

		b2cPage.EditAddress.click();

		Thread.sleep(6000);

		b2cPage.Shipping_FirstNameTextBox.clear();
		b2cPage.Shipping_FirstNameTextBox.sendKeys("Shane");
		b2cPage.Shipping_LastNameTextBox.clear();
		b2cPage.Shipping_LastNameTextBox.sendKeys("Li");
		b2cPage.Shipping_AddressLine1TextBox.clear();
		b2cPage.Shipping_AddressLine1TextBox.sendKeys(testData.B2C.getDefaultAddressLine1());
		if (Store.equals("HK")) {
			b2cPage.Shipping_AddressLine3TextBox.clear();
			b2cPage.Shipping_AddressLine3TextBox.sendKeys("");
		} else {
			b2cPage.Shipping_CityTextBox.clear();
			b2cPage.Shipping_CityTextBox.sendKeys(testData.B2C.getDefaultAddressCity());
			b2cPage.Shipping_PostCodeTextBox.clear();
			b2cPage.Shipping_PostCodeTextBox.sendKeys(testData.B2C.getDefaultAddressPostCode());
		}
		WebElement state = driver.findElement(By.xpath(".//*[@id='state']/option[contains(text(),'New South Wales')]"));
		state.click();
		b2cPage.Mobile.clear();
		b2cPage.Mobile.sendKeys("2022022020");
		b2cPage.Shipping_EmailTextBox.clear();
		b2cPage.Shipping_EmailTextBox.sendKeys("lixe1@lenovo.com");

		Thread.sleep(5000);

		actions.moveToElement(b2cPage.standardShipping).click().perform();

		Thread.sleep(5000);

		actions.moveToElement(b2cPage.Shipping_ContinueButton).click().perform();
		Thread.sleep(12000);
		if (Common.isElementExist(driver, popupOK)) {
			
			b2cPage.addressValidation.click();
			Thread.sleep(8000);

		}
		while (Common.isElementExist(driver, shippingContinue)) {
			actions.moveToElement(b2cPage.Shipping_ContinueButton).click()
					.perform();

			Thread.sleep(6000);
		}
		Thread.sleep(6000);
		new WebDriverWait(driver, 500).until(ExpectedConditions
				.elementToBeClickable(b2cPage.Visa));
		// if(!b2cPage.CreditCard.isSelected()){b2cPage.CreditCard.click();}
		b2cPage.Visa.click();
		b2cPage.CardNumber.sendKeys("4111111111111111");
		b2cPage.ExpiryMonth.sendKeys("06");
		b2cPage.ExpiryYear.sendKeys("20");
		b2cPage.SecurityCode.sendKeys("132");
		b2cPage.NameonCard.sendKeys("LIXE");
		b2cPage.ContinueforPayment.click();
		Thread.sleep(5000);
		if (Common.isElementExist(driver, externalPW)) {
			b2cPage.VisaPassword.sendKeys("1234");
			b2cPage.VisaSubmit.click();
		}
		Thread.sleep(6000);
		// Review and purchase
		// b2cPage.Terms.click();
		b2cPage.OrderSummary_AcceptTermsCheckBox.click();
		B2CCommon.clickPlaceOrder(b2cPage);
		Thread.sleep(8000);
		
	}
	

}