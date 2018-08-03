package TestScript.B2C;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class NA18050Test extends SuperTestClass {
	private B2CPage b2cPage;
	private String anotherStore;
	private String HomePage_ViewCartLink = "//div/a[contains(@id,'rollovercartViewCart')]";
	private String CartPage_EmptyCartMsg = ".//*[@id='mainContent']/div[2]/h3";
	private String partNum;
	private String accessoryNo;
	private String CartPage_EmpCart = "//a[contains(@href,'emptyCart')]";
	private String CartPage_EmpCart_NewUI = "//form[contains(@action,'emptyCart')]/a";
	private boolean EmpCartIsNewUI;
	private boolean CartPage_PartNumberOfAddedProductNewUI;
	private String partNumOfProductInCart;

	public NA18050Test(String store, String anotherStore, String accessory) {
		this.Store = store;
		this.anotherStore = anotherStore;
		this.accessoryNo = accessory;
		this.testName = "NA-18050";
	}

	
	
	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA18050(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			String b2cLoginURL = testData.B2C.getloginPageUrl();
			partNum = testData.B2C.getDefaultMTMPN();
			String selectAnotherCountry = ".//*[@id='countrySelector']/option[@cc='"
					+ anotherStore + "']";
			String selectBaseCountry = ".//*[@id='countrySelector']/option[@cc='"
					+ Store + "']";

			// =========== Step:1 Accessing B2C URL ===========//
			driver.get(b2cLoginURL);
			Common.sleep(1000);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(),
					testData.B2C.getLoginPassword());
			HandleJSpring(driver);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Dailylog.logInfoDB(1, "Successfully Logged into B2C website.",
					Store, testName);
			B2CCommon.closeHomePagePopUP(driver);

			// =========== Step:2 Checking the cart status ===========//
			Common.scrollToElement(driver,
					b2cPage.CartPage_CountrySelectorDropdown);
			b2cPage.CartPage_CountrySelectorDropdown.click();
			driver.findElement(By.xpath(selectAnotherCountry)).click();
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			Common.scrollToElement(driver,
					b2cPage.CartPage_CountrySelectorDropdown);
			b2cPage.CartPage_CountrySelectorDropdown.click();
			driver.findElement(By.xpath(selectBaseCountry)).click();
			Common.sleep(2000);
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			Assert.assertFalse(Common.isElementExist(driver, By.xpath("(.//*[@id='editConfig']/h4/div)")));
			Dailylog.logInfoDB(2, "Cart is cleared.", Store, testName);
			Common.sleep(2000);

			// =========== Step:3 Add a product to cart ===========//
			addPartNumberToCart(b2cPage, partNum);
			Common.sleep(3000);
			By noStockMess =  By.xpath("//*[contains(text(),'is invalid') or"
					+ " contains(text(),'No Stock for the Product') or contains(text(),'Unfortunately') ]");
			if(Common.checkElementDisplays(driver, noStockMess, 5)){
				partNum = productBackNum(this.Store);
				addPartNumberToCart(b2cPage, partNum);
			}
			Dailylog.logInfoDB(3, "Product has been added to cart", Store,
					testName);
			Assert.assertEquals(
					1,
					driver.findElements(
							By.xpath("(.//*[@id='editConfig']/h4/div)")).size());

			// =========== Step:4 Switch the Country from Country Selector
			// Dropdown ===========//
			Common.scrollToElement(driver,
					b2cPage.CartPage_CountrySelectorDropdown);
			b2cPage.CartPage_CountrySelectorDropdown.click();
			driver.findElement(By.xpath(selectAnotherCountry)).click();
			Dailylog.logInfoDB(4, "Switched to different store: "
					+ anotherStore, Store, testName);
			Common.sleep(2000);
			String homePageURL_newCountry = driver.getCurrentUrl();
			Assert.assertTrue(homePageURL_newCountry.contains(anotherStore
					.toLowerCase()));

			// =========== Step:5 Check the Cart Status ===========//
			checkCartStatus("empty");
			Dailylog.logInfoDB(5, "Checking cart status..It should be empty.",
					Store, testName);

			// =========== Step:6 Clear the cart ===========//
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			Common.sleep(2000);
			Assert.assertFalse(Common.isElementExist(driver, By.xpath("(.//*[@id='editConfig']/h4/div)")));

			Dailylog.logInfoDB(6, "Cart is cleared.", Store, testName);

			// =========== Step:7 Add a product to cart ===========//
			// used to add cto,now changing to mtm
			B2CCommon.addPartNumberToCart(b2cPage, accessoryNo);
			Common.sleep(1000);
			Assert.assertEquals(
					1,
					driver.findElements(
							By.xpath("(.//*[@id='editConfig']/h4/div)")).size());
			String partNumOfProductInCart_DiffStore = getProductNum();
			Dailylog.logInfoDB(7,
					"Product has been added to cart..For Different Store. Prod number is: "
							+ partNumOfProductInCart_DiffStore, Store, testName);

			// =========== Step:8 Switch back to Base Store ===========//
			Common.scrollToElement(driver,
					b2cPage.CartPage_CountrySelectorDropdown);
			b2cPage.CartPage_CountrySelectorDropdown.click();
			driver.findElement(By.xpath(selectBaseCountry)).click();
			Dailylog.logInfoDB(8, "Switched back to Base Store.", Store,
					testName);
			Common.sleep(2000);
			B2CCommon.closeHomePagePopUP(driver);
			String homePageURL_BaseCountry = driver.getCurrentUrl();
			Assert.assertTrue(homePageURL_BaseCountry.contains(Store
					.toLowerCase()));

			// =========== Step:9 Check Cart Status ===========//
			checkCartStatus("NotEmpty");
			partNumOfProductInCart = getProductNum();
			Dailylog.logInfoDB(9, "Checking Cart Status.Product in Cart: "
					+ partNumOfProductInCart, Store, testName);
			Assert.assertTrue(partNumOfProductInCart.contains(partNum));
			Common.sleep(1000);

			// =========== Step:10 Again Switch back to new Store ===========//
			Common.scrollToElement(driver,
					b2cPage.CartPage_CountrySelectorDropdown);
			b2cPage.CartPage_CountrySelectorDropdown.click();
			driver.findElement(By.xpath(selectAnotherCountry)).click();
			Dailylog.logInfoDB(10, "Switched back to different store", Store,
					testName);
			Common.sleep(2000);
			B2CCommon.closeHomePagePopUP(driver);
			String homePageURL_newCountry_again = driver.getCurrentUrl();
			Assert.assertTrue(homePageURL_newCountry_again
					.contains(anotherStore.toLowerCase()));
			checkCartStatus("NotEmpty");
			partNumOfProductInCart = getProductNum();
			Assert.assertTrue(partNumOfProductInCart.contains(partNumOfProductInCart_DiffStore));

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public String productBackNum(String store){
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
		case "HK":
			return "80Y7000NHH";
		default:
			return null;
		
		}	
			
	}
	
	public static void addPartNumberToCart(B2CPage b2cPage, String partNum) {
		b2cPage.Cart_QuickOrderTextBox.clear();
		b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
		Common.sleep(1000);
		b2cPage.Cart_AddButton.click();
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
	
	
	private String getProductNum(){
		CartPage_PartNumberOfAddedProductNewUI = Common.checkElementExists(driver, b2cPage.CartPage_PartNumberOfAddedProduct, 3);
		if(CartPage_PartNumberOfAddedProductNewUI){
			partNumOfProductInCart = b2cPage.CartPage_PartNumberOfAddedProduct.getText();
		}else if(Common.checkElementExists(driver, b2cPage.CartPage_PartNumberOfAddedProductOldUI, 3)){
			partNumOfProductInCart = b2cPage.CartPage_PartNumberOfAddedProductOldUI.getText();
		}
		return partNumOfProductInCart;
	}
	
	private void checkCartStatus(String cartStatus) {
		b2cPage.HomePage_CartIcon.click();
		EmpCartIsNewUI = Common.isElementExist(driver, By.xpath(CartPage_EmpCart_NewUI));
		if (Common.isElementExist(driver, By.xpath(HomePage_ViewCartLink))) {
			driver.findElement(By.xpath(HomePage_ViewCartLink)).click();
		}
		if (cartStatus.equalsIgnoreCase("empty")) {
			if (Common.isElementExist(driver, By.xpath(CartPage_EmptyCartMsg))) {
				System.out.println("Cart is empty..!!");
			} else if(EmpCartIsNewUI){
				String partNumOfProductInCart = b2cPage.CartPage_PartNumberOfAddedProduct
						.getText();
				Assert.assertFalse(partNumOfProductInCart.contains(partNum));
			}else if(Common.isElementExist(driver, By.xpath(CartPage_EmpCart))){
				String partNumOfProductInCart = b2cPage.CartPage_PartNumberOfAddedProductOldUI
						.getText();
				Assert.assertFalse(partNumOfProductInCart.contains(partNum));
			}
		} else if (cartStatus.equalsIgnoreCase("NotEmpty")) {
			Assert.assertFalse(Common.isElementExist(driver,
					By.xpath(CartPage_EmptyCartMsg)));

		}

	}
}
