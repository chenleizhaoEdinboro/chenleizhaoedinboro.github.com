package TestScript.B2B;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA21299Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	private String profileName;

	public NA21299Test(String store) {
		this.Store = store;
		this.testName = "NA-21299";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "payment", "p2", "b2b"})
	public void NA21299(ITestContext ctx) {

		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			profileName = "PURCHASE ORDER AutoB2B" + Store;

			// HMC->Nemo->Payment->Payment Type Customize->Payment Profile
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_Nemo.click();
			hmcPage.Home_payment.click();
			hmcPage.Home_paymentCustomize.click();
			// right click create new payment profile
			Common.rightClick(driver, hmcPage.Home_paymentProfile);
			Common.waitElementClickable(driver, hmcPage.Home_paymentProfileCreate, 8000);
			hmcPage.Home_paymentProfileCreate.click();
			// Name：PURCHASE ORDER Auto
			hmcPage.paymentProfile_name.sendKeys(profileName);
			driver.findElement(By.xpath("//input[contains(@id,'code')]")).sendKeys(profileName);

			// Payment Type: Purchase Order
			hmcPage.paymentProfile_PaymentType.click();
			hmcPage.paymentProfile_PaymentTypePo.click();
			// Configuration Level ：Specific
			hmcPage.paymentProfile_configLevel.click();
			hmcPage.paymentProfile_configLevelS.click();
			// Chanel：ALL
			hmcPage.paymentProfile_channel.click();
			hmcPage.paymentProfile_channelALL.click();
			// Active
			hmcPage.paymentProfile_activeTrue.click();

			// // Help url
			// hmcPage.paymentProfile_disableHelpLinkNo.click();
			// hmcPage.paymentProfile_helpLinkLabel.clear();
			// hmcPage.paymentProfile_helpLinkLabel.sendKeys(linkLabel);
			// hmcPage.paymentProfile_helpLinkType.click();
			// hmcPage.paymentProfile_helpLinkTypeNew.click();
			// hmcPage.paymentProfile_pageLink.clear();
			// hmcPage.paymentProfile_pageLink.sendKeys(pageLink);
			// // Image
			// hmcPage.paymentProfile_disablePaymentImageNo.click();
			// hmcPage.paymentProfile_paymentImgage.click();
			// Thread.sleep(5000);
			// switchToWindow(1);
			// hmcPage.paymentProfile_paymentImgageMedia.clear();
			// hmcPage.paymentProfile_paymentImgageMedia.sendKeys("image");
			// hmcPage.paymentProfile_paymentImgageSearch.click();
			// Common.checkElementExists(driver,
			// hmcPage.paymentProfile_paymentImgageResult, 120);
			// System.out.println("image result is display");
			// hmcPage.paymentProfile_paymentImgageResultFirst.click();
			// hmcPage.B2BUnit_DeliveryMode_SettingUse.click();
			// switchToWindow(0);

			// B2C&B2B Properties tab, add display label: PURCHASE ORDER Auto
			hmcPage.paymentProfile_paymentB2BB2C.click();
			hmcPage.paymentProfile_paymentB2BB2CDisplayName.clear();
			hmcPage.paymentProfile_paymentB2BB2CDisplayName.sendKeys(profileName);
			// B2B unit tab, add B2B unit :
			hmcPage.paymentTypeProfile_b2bunits.click();
			Common.rightClick(driver, hmcPage.paymentTypeProfile_unitsTable);
			hmcPage.paymentTypeProfile_add.click();
			switchToWindow(1);
			hmcPage.paymentTypeProfile_unitId.sendKeys(testData.B2B.getB2BUnit());
			hmcPage.paymentTypeProfile_search.click();
			Common.checkElementExists(driver, hmcPage.paymentTypeProfile_firstSearchResult, 120);
			hmcPage.paymentTypeProfile_firstSearchResult.click();
			hmcPage.paymentTypeProfile_use.click();
			switchToWindow(0);
			// Global tab,Disable Mixed Order:Yes
			hmcPage.paymentProfile_global.click();
			hmcPage.paymentProfile_globalMixedOrderYes.click();
			// create
			hmcPage.paymentProfile_paymentCreate.click();
			System.out.println("paymentProfile created");
			Thread.sleep(5000);
			// B2B UNIT->Site Attributes->Payment Type->Add PURCHASE ORDER
			addPayment("PURCHASEORDER");
			System.out.println("PURCHASEORDER payment added");
			// "Disable Payment Profile Fallback" :Yes
			hmcPage.B2BUnit_disablePaymentProfileFallbackYes.click();
			hmcPage.B2BUnit_Save.click();
			Thread.sleep(2000);
			hmcPage.Home_B2BCommerceLink.click();
			System.out.println("disablePaymentProfileFallback: Yes");
			// Go to B2B website, add item to cart
			addLapTopsToCart();
			// checkout and proceed to payment page
			b2bPage.cartPage_LenovoCheckout.click();
			B2BCommon.fillB2BShippingInfo(driver, b2bPage, "au", "FirstNameJohn", "LastNameSnow",
					testData.B2B.getCompany(), testData.B2B.getAddressLine1(), testData.B2B.getAddressCity(),
					testData.B2B.getAddressState(), testData.B2B.getPostCode(), testData.B2B.getPhoneNum());

			b2bPage.shippingPage_ContinueToPayment.click();
			if(Common.isElementExist(driver, By.id("checkout_validateFrom_ok"))){
				driver.findElement(By.id("checkout_validateFrom_ok")).click();
			}
			// purchase order payment changed to PURCHASE ORDER Auto
			Assert.assertTrue(Common.isElementExist(driver, By.id("PaymentTypeSelection_PURCHASEORDER")),
					"isElementExist PURCHASEORDER when B2BUnit_disablePaymentProfileFallbackYes");
			Assert.assertEquals(b2bPage.Payment_purchaseOrderLabel.getText(), profileName);
			// "Disable Payment Profile Fallback" :NO
			driver.get(testData.HMC.getHomePageUrl());
			driver.manage().deleteAllCookies();
			driver.navigate().refresh();
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2BUnit(hmcPage, testData);
			hmcPage.B2BUnit_siteAttribute.click();
			hmcPage.B2BUnit_disablePaymentProfileFallbackNo.click();
			hmcPage.B2BUnit_Save.click();
			hmcPage.Home_B2BCommerceLink.click();
			System.out.println("disablePaymentProfileFallback: No");
			// Go to B2B website, add item to cart,
			addLapTopsToCart();
			// checkout and proceed to payment page
			b2bPage.cartPage_LenovoCheckout.click();
			if (Common.checkElementExists(driver, b2bPage.shippingPage_EditCart, 5))
				b2bPage.shippingPage_EditCart.click();
			B2BCommon.fillB2BShippingInfo(driver, b2bPage, "au", "FirstNameJohn", "LastNameSnow",
					testData.B2B.getCompany(), testData.B2B.getAddressLine1(), testData.B2B.getAddressCity(),
					testData.B2B.getAddressState(), testData.B2B.getPostCode(), testData.B2B.getPhoneNum());
			b2bPage.shippingPage_ContinueToPayment.click();
			if(Common.isElementExist(driver, By.id("checkout_validateFrom_ok"))){
				driver.findElement(By.id("checkout_validateFrom_ok")).click();
			}
			
			// purchase order payment label changed to PURCHASE ORDER Auto
			Assert.assertTrue(Common.isElementExist(driver, By.id("PaymentTypeSelection_PURCHASEORDER")),
					"isElementExist PURCHASEORDER when B2BUnit_disablePaymentProfileFallbackNo");
			Assert.assertEquals(b2bPage.Payment_purchaseOrderLabel.getText(), profileName);
			// no other image or help link after purchase order(NA-21257)
			Assert.assertFalse(
					Common.isElementExist(driver, By.xpath("//*[@id='PaymentTypeSelection_PURCHASEORDER']/..//a")),
					"isElementExist PURCHASEORDER link");
			Assert.assertFalse(
					Common.isElementExist(driver, By.xpath("//*[@id='PaymentTypeSelection_PURCHASEORDER']/..//img")),
					"isElementExist PURCHASEORDER img");
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	// Roll back by deleting step1 payment profile
	@AfterTest(alwaysRun = true)
	public void rollback() throws InterruptedException, MalformedURLException {

		System.out.println("rollback"); // roll back
		SetupBrowser();
		driver.get(testData.HMC.getHomePageUrl());
		hmcPage = new HMCPage(driver);
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Nemo.click();
		hmcPage.Home_payment.click();
		hmcPage.Home_paymentCustomize.click();
		hmcPage.Home_paymentProfile.click();
		hmcPage.paymentProfile_attributeselect.click();
		hmcPage.paymentProfile_attributeselectName.click();

		Common.checkElementExists(driver, hmcPage.paymentProfile_nameInput, 120);
		hmcPage.paymentProfile_nameInput.clear();
		hmcPage.paymentProfile_nameInput.sendKeys(profileName);
		hmcPage.paymentProfile_paymentType.click();
		hmcPage.paymentProfile_paymentTypePo.click();
		hmcPage.paymentProfile_paymentSearch.click();

		Thread.sleep(5000);
		for (int i = 0; i < 5; i++) {
			if (Common.isElementExist(driver, By.xpath("//tr[contains(@id,'Purchase Order')]"))) {
				Common.rightClick(driver, hmcPage.paymentProfile_poResult);
				hmcPage.paymentProfile_remove.click();
				driver.switchTo().alert().accept();
				Thread.sleep(2000);
			} else
				break;
		}
		Assert.assertFalse(Common.isElementExist(driver, By.xpath("//tr[contains(@id,'Purchase Order')]")),
				"isElementExist hmcPage.paymentProfile_poResult");

	}

	public void addLapTopsToCart() throws InterruptedException {
		driver.manage().deleteAllCookies();
		driver.get(testData.B2B.getLoginUrl());
		B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
		b2bPage.HomePage_productsLink.click();
		// b2bPage.HomePage_Laptop.click();
		driver.findElement(By.xpath("(//a[@class='products_submenu'])[1]")).click();

		String plpURL = driver.getCurrentUrl();
		boolean isProductAdded = false;
		// add contract product to cart
		if (Common.isElementExist(driver, By.xpath("//*[contains(@title,'Add to Cart')]"))) {
			for (int i = 0; i < b2bPage.PLPPage_addToCart.size(); i++) {
				b2bPage.PLPPage_addToCart.get(i).click();
				Common.waitElementClickable(driver, b2bPage.Product_contractAddToCartOnPopup, 60);
				b2bPage.Product_contractAddToCartOnPopup.click();
				Thread.sleep(8000);
				Common.waitElementClickable(driver, b2bPage.ProductPage_AlertGoToCart, 60);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2bPage.ProductPage_AlertGoToCart);
				Thread.sleep(5000);
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
				b2bPage.PLPPage_viewDetails.get(i).click();
				if (isAlertPresent()) {
					System.out.println(driver.switchTo().alert().getText() + " Try next product!");
					driver.switchTo().alert().accept();
					driver.get(plpURL);
				} else {
					String pdpURL = driver.getCurrentUrl();
					String partNum = pdpURL.substring(pdpURL.lastIndexOf('/') + 1, pdpURL.length());
					System.out.println("Product Number: " + partNum);
					Thread.sleep(1000);
					if (Common.isElementExist(driver,
							By.xpath("// *[contains(@id,'Alert')]//*[contains(@id,'_add_to_cart')]"))) {
						Thread.sleep(500);
						if (b2bPage.PDPPage_agreementAddToCartOnPopup.isDisplayed())
							((JavascriptExecutor) driver).executeScript("arguments[0].click();",
									b2bPage.PDPPage_agreementAddToCartOnPopup);
						else {
							try {
								b2bPage.productPage_AddToCart.click();
							} catch (NoSuchElementException e) {
								b2bPage.Agreement_agreementsAddToCart.click();
							}
							b2bPage.HomePage_CartIcon.click();
						}

					} else {
						try {
							b2bPage.productPage_AddToCart.click();
						} catch (NoSuchElementException e) {
							b2bPage.Agreement_agreementsAddToCart.click();
						}
						b2bPage.HomePage_CartIcon.click();
					}
					Thread.sleep(1000);
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

	public void addPayment(String identifier) throws InterruptedException {
		HMCCommon.searchB2BUnit(hmcPage, testData);
		hmcPage.B2BUnit_siteAttribute.click();
		if (!Common.isElementExist(driver,
				By.xpath(".//*[contains(@id,'paymentTypeAndPayerId')]/td[contains(text(),'" + identifier + "')]"))) {
			Common.rightClick(driver, hmcPage.B2BUnit_paymentTypeTable);
			hmcPage.B2BUnit_add.click();
			switchToWindow(1);
			hmcPage.B2BUnit_identifier.sendKeys(identifier);
			hmcPage.B2BUnit_SearchButton.click();
			hmcPage.B2BUnit_searchedPayment.click();
			hmcPage.B2BUnit_use.click();
			switchToWindow(0);
			hmcPage.B2BUnit_Save.click();
		}
	}

	public void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(windows.get(windowNo));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
