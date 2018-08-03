package TestScript.B2B;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.google.common.base.Verify;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2BPage;
import TestScript.SuperTestClass;

public class NA18036Test extends SuperTestClass{
	private static B2BPage b2bPage;
	String createdCartName = "company_" + Common.getDateTimeString();
	public NA18036Test(String store) {
		this.Store = store;
		this.testName = "NA-18036";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2b"})
	public void NA18036(ITestContext ctx) {
		try{
			this.prepareTest();
			String updatedCartQuantity = "4";
			b2bPage = new B2BPage(driver);

			//Step 1:
			Dailylog.logInfoDB(1, "Login B2B website", Store, testName);
			loginB2B(testData.B2B.getBuilderId());
			Thread.sleep(2000);
			clearCart();

			//Step 2:
			Dailylog.logInfoDB(2, "Add one product to cart", Store, testName);
			addProductToCart();
			String productName = b2bPage.b2bCart_itemName.getText();
			String productQuantity = b2bPage.cartPage_Quantity.getAttribute("value");
			String productPrice = b2bPage.cartPage_TotalPrice.getText();

			//Step 3:
			Dailylog.logInfoDB(3, "Click Save cart button, select company cart, input cartName. click save.", Store, testName);
			saveCart(true);
			searchCartByName();
			Thread.sleep(1000);
			Assert.assertEquals(b2bPage.savedCarts_cartName.getText(), createdCartName);
			Assert.assertTrue(b2bPage.savedCarts_viewLink.isDisplayed(), "View link is not displayed.");
			Assert.assertTrue(b2bPage.savedCarts_editLink.isDisplayed(), "Edit link is not displayed.");
			Assert.assertTrue(b2bPage.savedCarts_emailLink.isDisplayed(), "Email link is not displayed.");
			Assert.assertTrue(b2bPage.savedCarts_deleteLink.isDisplayed(), "Delete link is not displayed.");
			Assert.assertTrue(b2bPage.savedCarts_unshareLink.isDisplayed(), "Unshare link is not displayed.");

			//Step 4:
			Dailylog.logInfoDB(4, "logoff and logon with another account. go to my account/my saved cart history.", Store, testName);
			logOutLogIn(testData.B2B.getBuyerId());
			searchCartByName();
			Thread.sleep(1000);
			Assert.assertEquals(b2bPage.savedCarts_cartName.getText(), createdCartName);
			Assert.assertTrue(b2bPage.savedCarts_viewLink.isDisplayed(), "View link is not displayed.");
			Assert.assertTrue(b2bPage.savedCarts_disabledEditLink.isDisplayed(), "Disabled edit link is not displayed.");
			Assert.assertTrue(b2bPage.savedCarts_emailLink.isDisplayed(), "Email link is not displayed.");
			Assert.assertTrue(b2bPage.savedCarts_disabledDeleteLink.isDisplayed(), "Disabled delete link is not displayed.");
			Assert.assertTrue(!(Common.isElementExist(driver, By.xpath(".//a/div[contains(.,'Unshare')]"))), "Unshare link is displayed.");

			//Step 5:
			Dailylog.logInfoDB(5, "click on view and go to the cart detail page", Store, testName);
			b2bPage.savedCarts_viewLink.click();
			Thread.sleep(1000);
			Assert.assertEquals(b2bPage.b2bCart_itemName.getText(), productName);
			Assert.assertEquals(b2bPage.cartHistory_quantity.getAttribute("value"), productQuantity);
			Assert.assertEquals(b2bPage.cartHistory_productPrice.getText(), productPrice);

			//Step 6:
			Dailylog.logInfoDB(6, "logoff and logon with user one created the saved cart. go to my account/ my saved cart history page.", Store, testName);
			logOutLogIn(testData.B2B.getBuilderId());

			//Step 7:
			Dailylog.logInfoDB(7, "click unshare link of step 3 saved cart", Store, testName);
			searchCartByName();
			Thread.sleep(1000);
			b2bPage.savedCarts_unshareLink.click();
			searchCartByName();
			Thread.sleep(1000);
			Assert.assertTrue(b2bPage.savedCarts_shareLink.isDisplayed(), "Share link is not displayed.");
			Assert.assertFalse(Common.isElementExist(driver, By.xpath(".//td[@data-title='Cart Name']/small")), "Cart name is displayed. Cart still exists.");

			//Step 8:
			Dailylog.logInfoDB(8, "logoff and logon user two. go to my account/my saved cart history.", Store, testName);
			logOutLogIn(testData.B2B.getBuyerId());
			Thread.sleep(1000);
			searchCartByName();
			Assert.assertTrue(b2bPage.savedCarts_noCartAvailable.isDisplayed(), "No cart available is not displayed.");
			Assert.assertFalse(Common.isElementExist(driver, By.xpath(".//td[@data-title='Cart Name']/h3")), "Cart name is displayed. Cart still exists.");

			//Step 9:
			Dailylog.logInfoDB(9, "logoff and logon with user one. go to my account/ my saved cart history page.", Store, testName);
			logOutLogIn(testData.B2B.getBuilderId());
			Thread.sleep(1000);
			searchCartByName();
			Assert.assertTrue(b2bPage.savedCarts_shareLink.isDisplayed(), "Share link is not displayed.");

			//Step 10:
			Dailylog.logInfoDB(10, "click on view and click edit cart on cart detail page,", Store, testName);
			b2bPage.savedCarts_viewLink.click();
			Thread.sleep(1000);
			Assert.assertTrue(b2bPage.savedCarts_personalSavedCartLabel.isDisplayed(), "Personal saved cart label is not displayed.");
			b2bPage.savedCarts_editCartButton.click();
			b2bPage.cartPage_Quantity.clear();
			b2bPage.cartPage_Quantity.sendKeys(updatedCartQuantity);
			b2bPage.cartPage_QuantityUpdate.click();
			Thread.sleep(1000);
			saveCart(false);
			Thread.sleep(1000);
			searchCartByName();
			Assert.assertTrue(b2bPage.savedCarts_shareLink.isDisplayed(), "Share link is not displayed.");
			
			//Step 11:
			Dailylog.logInfoDB(11, "click on share link.", Store, testName);
			b2bPage.savedCarts_shareLink.click();
			searchCartByName();
			Thread.sleep(1000);
			Assert.assertEquals(b2bPage.savedCarts_savedCartType.getText(), "[CompanyCart]");
			Assert.assertTrue(b2bPage.savedCarts_unshareLink.isDisplayed(), "Unshare link is not displayed.");
			
			//Step 12:
			Dailylog.logInfoDB(11, "logoff and logon user two. go to my account/my saved cart history.", Store, testName);
			logOutLogIn(testData.B2B.getBuyerId());
			searchCartByName();
			Thread.sleep(1000);
			Assert.assertEquals(b2bPage.savedCarts_cartName.getText(), createdCartName);
			
			//Step 13:
			Dailylog.logInfoDB(11, "click on view", Store, testName);
			b2bPage.savedCarts_viewLink.click();
			Thread.sleep(1000);
			Assert.assertEquals(b2bPage.cartHistory_quantity.getAttribute("value"), updatedCartQuantity);
		}catch (Throwable e){
			handleThrowable(e, ctx);
		}
	}

	public void loginB2B(String emailID) throws InterruptedException{
		driver.get(testData.B2B.getLoginUrl());
		B2BCommon.Login(b2bPage, emailID, testData.B2B.getDefaultPassword());
		Thread.sleep(2000);
	}

	public void logOutLogIn(String emailID) throws InterruptedException{
		b2bPage.homepage_Signout.click();
		loginB2B(emailID);
		b2bPage.homepage_MyAccount.click();
		b2bPage.myAccountPage_viewCartHistory.click();
		Thread.sleep(2000);
	}

	public void clearCart() throws InterruptedException{
		driver.get(testData.B2B.getHomePageUrl() + "/cart");
		Thread.sleep(2000);
		if(Common.isElementExist(driver, By.xpath("//a[contains(text(),'Empty cart')]"))){
			b2bPage.cartPage_emptyCartButton.click();
			Dailylog.logInfoDB(1, "Cleared the cart. Cart is empty now.", Store, testName);
		} else{
			Dailylog.logInfoDB(1, "Cart is already empty. No need to clear cart.", Store, testName);
		}
	}

	public void addProductToCart() throws InterruptedException{
		b2bPage.HomePage_productsLink.click();
		Thread.sleep(1000);
		b2bPage.HomePage_Laptop.click();
		Thread.sleep(1000);
		if(Common.isElementExist(driver, By.xpath("(//*[contains(@action,'restfulAddToCart')]/button)[1]"))){
			//For MTM products
			b2bPage.laptops_addToCartButton.click();
			Thread.sleep(1000);
			//Add to cart button on the overlay
			b2bPage.addtoCartPOP.click();
			Thread.sleep(3000);
			//Go to cart button when the add to cart button has been clicked
			b2bPage.goToCartPop.click();
		}else if (Common.isElementExist(driver, By.xpath("(.//img[@title='Customize and buy'])[1]"))) {
			//For CTO products
			b2bPage.laptops_ctoCustomizeAndBuyButton.click();
			Thread.sleep(5000);
			//Pop up stating warning
			if(Common.checkElementExists(driver, b2bPage.laptops_addToCartOverlayForCTO, 5000)){
				b2bPage.laptops_addToCartOverlayForCTO.click();
				Thread.sleep(3000);
			}else {
				//No pop up
				b2bPage.laptops_addToCartForCTO.click();
				Thread.sleep(3000);
			}
			b2bPage.productBuilder_addToCartButton.click();
		}
		Thread.sleep(5000);
	}

	public void searchCartByName(){
		Select searchCartDropDown = new Select(b2bPage.cartHistory_searchCartByDropDown);
		searchCartDropDown.selectByVisibleText("Cart Name");
		b2bPage.cartHistory_searchCartTextBox.clear();
		b2bPage.cartHistory_searchCartTextBox.sendKeys(createdCartName);
		b2bPage.cartHistory_searchCartButton.click();
	}

	public void saveCart(boolean companyCart) throws InterruptedException{
		b2bPage.cartPage_saveCartButton.click();
		Thread.sleep(1000);
		if(companyCart){
			b2bPage.cartPage_companySaveCartButton.click();
		}
		b2bPage.cartPage_SaveCart_cartNameField.clear();
		b2bPage.cartPage_SaveCart_cartNameField.sendKeys(createdCartName);
		b2bPage.cartPage_SaveCart_save.click();
		Assert.assertTrue(driver.getCurrentUrl().contains("save-carts"), "Save cart page is not displayed.");
	}
}
