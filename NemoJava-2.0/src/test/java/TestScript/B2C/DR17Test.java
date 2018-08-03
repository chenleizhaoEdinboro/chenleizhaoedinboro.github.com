package TestScript.B2C;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class DR17Test extends SuperTestClass {

	public String MTMPartNumber;
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	Actions actions;
	public String Accessories;
	public String MTMPrice;

	public DR17Test(String store, String MTMPartNumber, String Accessories) {
		this.Store = store;
		this.MTMPartNumber = MTMPartNumber;
		this.Accessories = Accessories;
		this.testName = "DR-17";
	}

	@Test(alwaysRun = true, groups = {"contentgroup", "dr", "p1", "b2c", "compatibility"})
	public void DR17(ITestContext ctx) {

		try {
			this.prepareTest();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
//			actions = new Actions(driver);
//			Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
			// Skip HMC configurations since IE and FR are already DR country by default
//			Common.NavigateToUrl(driver, Browser, testData.B2C.getloginPageUrl());
			Common.NavigateToUrl(driver, Browser, testData.B2C.getloginPageUrl());
			if (!driver.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
			if (!driver.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
			// after the login , get the homepage url immediately
			// get the cart url and empty the cart
			String cartUrl = testData.B2C.getHomePageUrl() + "/cart";
			Common.NavigateToUrl(driver, Browser, cartUrl);

			Thread.sleep(5000);
			// might fail because of new UI
			String message = driver.findElement(
					By.xpath("//div[@class='checkoutInfoTxt']")).getText();
			System.out.println("message is :" + message);
			if (Common.isElementExist(driver,
					By.xpath("//a[contains(text(),'Empty cart')]"))) {
				Common.javascriptClick(driver, driver.findElement(By
						.xpath("//a[contains(text(),'Empty cart')]")));
				// driver.findElement(By.xpath("//a[contains(text(),'Empty cart')]")).click();
				}
			if(Store.equals("FR")){
				if (Common.isElementExist(driver,
					By.xpath("//img[@alt='Vider le panier']"))) {
				driver.findElement(By.xpath("//img[@alt='Vider le panier']"))
						.click();
				Thread.sleep(3000);
				driver.findElement(
						By.xpath("//input[@value='Oui, supprimer le panier']")).click();
				}
			}else{
				if (Common.isElementExist(driver,
						By.xpath("//img[@alt='Empty cart']"))) {
					driver.findElement(By.xpath("//img[@alt='Empty cart']"))
							.click();
					Thread.sleep(3000);
					driver.findElement(
							By.xpath("//input[@value='Yes, Delete Cart']")).click();
				}
			}
			String MTMURL = testData.B2C.getHomePageUrl() + "/p/"
					+ MTMPartNumber;
			System.out.println("MTMURL is :" + MTMURL);
			Common.NavigateToUrl(driver, Browser, MTMURL);

			// 5, Buy a MTM
			String changes = B2CCommon.addProductToCartFromPDPPage(driver,false,false,false,false);
			System.out.println(changes);
			MTMPrice = driver
					.findElement(
							By.xpath("//*[@id='mainContent']//span[contains(@class,'cart-summary-subTotal')]"))
					.getText();
			MTMPrice = String2Price(MTMPrice);
			if (Common.isElementExist(driver,
					By.xpath("//dd[@class='cart-item-addedItem-price']"))) {
				String temp = GetPriceValue(MTMPrice)
						- GetPriceValue(driver
								.findElement(
										By.xpath("//dd[@class='cart-item-addedItem-price']"))
								.getText()) + "";
				MTMPrice=temp;
			}
			// 6, Buy an accessories
			driver.findElement(By.xpath("//*[@id='quickOrderProductId']"))
					.clear();
			driver.findElement(By.xpath("//*[@id='quickOrderProductId']"))
					.sendKeys(Accessories);
			if (Common.isElementExist(driver,
					By.xpath("//*[@id='quickAddInput']/a"))) {
				driver.findElement(By.xpath("//*[@id='quickAddInput']/a"))
						.click();
			} else {
				driver.findElement(By.xpath(".//*[@id='quickAddInput']/button"))
						.click();
			}

			// 7 ,check notice message
			// Once you click the checkout button you will be transferred to
			// Digital River. Lenovo's ecommerce partner, who are the reseller
			// and merchant of products featured on this website.
			if (Common
					.isElementExist(
							driver,
							By.xpath("//*[@id='mainContent']/div[@class='checkoutInfoTxt']"))) {

				String message_1 = driver
						.findElement(
								By.xpath("//*[@id='mainContent']/div[@class='checkoutInfoTxt']"))
						.getText();
				String message_verify = getMessage(Store);
				Assert.assertTrue(message_1.contains(message_verify));

				String hyperLink = driver
						.findElement(
								By.xpath("//*[@id='mainContent']/div[@class='checkoutInfoTxt']/a"))
						.getAttribute("href").toString();
				String hyperLink_verify = getHyperLink(Store);
				Assert.assertTrue(hyperLink.contains(hyperLink_verify));
			} else {

				String message_1 = driver.findElement(
						By.xpath("//div[@class='checkoutInfoTxt']")).getText();

				String message_verify = getMessage(Store);

				Assert.assertTrue(message_1.contains(message_verify));

				String hyperLink = driver
						.findElement(
								By.xpath("//div[@class='checkoutInfoTxt']/a"))
						.getAttribute("href").toString();
				String hyperLink_verify = getHyperLink(Store);
				System.out.println(hyperLink + "  " + hyperLink_verify);
				Assert.assertTrue(hyperLink.contains(hyperLink_verify));
			}

			// 8 , checkout
			b2cPage.Cart_CheckoutButton.click();
			Common.checkElementDisplays(driver, b2cPage.DR_emailTxtBox, 50);
			driver.navigate().back();
			Dailylog.logInfoDB(7, "Lenovo Checkout", Store, testName);

			// 9, xml check
			Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
			if (Common.isElementExist(driver, By.id("Main_user"))) {
				HMCCommon.Login(hmcPage, testData);
				driver.navigate().refresh();
			}
			Thread.sleep(2000);
			hmcPage.Home_Nemo.click();
			hmcPage.NEMO_digitalRiver.click();
			hmcPage.NEMO_digitalRiver_tracelog.click();
			hmcPage.NEMO_digitalRiver_tracelogID.sendKeys(testData.B2C
					.getLoginID());

//			Select country = new Select(hmcPage.NEMO_digitalRiver_tracelogCountry);
			if (Store.equals("GB")) {
				Common.javascriptClick(driver, hmcPage.NEMO_digitalRiver_tracelogCountry_GB);
//				country.selectByValue("244");
			} else if (Store.equals("FR")) {
				Common.javascriptClick(driver, hmcPage.NEMO_digitalRiver_tracelogCountry_FR);
//				country.selectByValue("77");
			} else if (Store.equals("IE")) {
				Common.javascriptClick(driver, hmcPage.NEMO_digitalRiver_tracelogCountry_IE);
//				country.selectByValue("108");
			}

			Thread.sleep(5000);
			hmcPage.NEMO_digitalRiver_search.click();
			Thread.sleep(8000);
			hmcPage.NEMO_digitalRiver_creationtime_sort.click();
			Thread.sleep(8000);
			Common.doubleClick(driver,
					hmcPage.NEMO_digitalRiver_search_firstResultItem);

			String cartID = driver
					.findElement(
							By.xpath("//input[contains(@id,'Content/StringEditor[in Content/Attribute[DigitalRiverTraceLog.cartID]')]"))
					.getAttribute("value").toString();
			System.out.println("cartID is :" + cartID);
			String xmlcontent = hmcPage.NEMO_digitalRiver_search_xml.getText();

			Assert.assertTrue(xmlcontent.contains(MTMPartNumber),"xml content doesn't contains " + MTMPartNumber);

			// 10 , back to cart
//			driver.navigate().back();
			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl() + "/cart");
			Assert.assertTrue(driver.getCurrentUrl().contains("cart"));

			// 11 , add one more accessories product
			driver.findElement(By.xpath("//*[@id='quickOrderProductId']"))
					.clear();
			driver.findElement(By.xpath("//*[@id='quickOrderProductId']"))
					.sendKeys(Accessories);

			if (Common.isElementExist(driver,
					By.xpath("//*[@id='quickAddInput']/a"))) {
				driver.findElement(By.xpath("//*[@id='quickAddInput']/a"))
						.click();
			} else {
				driver.findElement(By.xpath(".//*[@id='quickAddInput']/button"))
						.click();
			}

			// 12 , place order
			Thread.sleep(10000);
			b2cPage.Cart_CheckoutButton.click();
//			Common.javascriptClick(driver, b2cPage.Cart_CheckoutButton);
			Common.checkElementDisplays(driver, b2cPage.DR_emailTxtBox, 50);
			b2cPage.DR_emailTxtBox.sendKeys(testData.B2C.getLoginID());
			b2cPage.DR_fNameTxtBox.sendKeys("TESTdr18");
			b2cPage.DR_lNameTxtBox.sendKeys("TESTdr18");
			b2cPage.DR_addLine1TxtBox.sendKeys(testData.B2C
					.getDefaultAddressLine1());
			b2cPage.DR_cityTxtBox
					.sendKeys(testData.B2C.getDefaultAddressCity());
			b2cPage.DR_PostCode.sendKeys("123");

			b2cPage.DR_phoneNumTxtBox.sendKeys(testData.B2C
					.getDefaultAddressPhone());
			((JavascriptExecutor) driver).executeScript("scroll(0,1000)");
			b2cPage.DR_ccChkBox.click();
			b2cPage.DR_ccNumberTxtBox.sendKeys("4111111111111111");
			b2cPage.DR_monthDD.click();
			b2cPage.DR_selectedMonthOption.click();
			b2cPage.DR_yearDD.click();
			b2cPage.DR_selectedYearOption.click();
			b2cPage.DR_securityCodeTxtBox.sendKeys("123");
			b2cPage.DR_continueBtn.click();

			Thread.sleep(1000);

			if (Common.isElementExist(driver,
					By.xpath("//*[@id='vr_skipregistration']"))) {
				driver.findElement(By.xpath("//*[@id='vr_skipregistration']"))
						.click();
			}
			((JavascriptExecutor) driver).executeScript("scroll(0,1000)");
			b2cPage.DR_tncChkBox.click();
			if(!driver.getCurrentUrl().contains("www3.lenovo.com"))
				b2cPage.DR_submitBtn.click();
			else
				setNoDataLog("Currently in Production Url! Skip place order step and futher check!");
			Thread.sleep(1000);

			String orderNum = b2cPage.DR_orderNum.getText();
			System.out.println("orderNum is :" + orderNum);

			Dailylog.logInfoDB(12, "Place order", Store, testName);

			// 13, continue shopping
			String href_homePage = driver
					.findElement(By.xpath("//*[@id='continueShopping']/a"))
					.getAttribute("href").toString();
			System.out.println("href_homePage is :" + href_homePage);
			//need add a method to verify what the store is.
			if(Store.equals("FR")){
				String homepage_url = "http://www.lenovo.com/fr/fr/index.shtml";
				Assert.assertTrue(href_homePage.equals(homepage_url));
				Common.javascriptClick(driver, driver.findElement(By
						.xpath("//*[@id='continueShopping']/a")));
			}
			if(Store.equals("IE")){
				String homepage_url = "http://www.lenovo.com/ie/en/index.shtml";
				Assert.assertTrue(href_homePage.equals(homepage_url));
				Common.javascriptClick(driver, driver.findElement(By
						.xpath("//*[@id='continueShopping']/a")));
			}
			if(Store.equals("GB")){
				String homepage_url = "http://www.lenovo.com/gb/en/index.shtml";
				Assert.assertTrue(href_homePage.equals(homepage_url));
				Common.javascriptClick(driver, driver.findElement(By
						.xpath("//*[@id='continueShopping']/a")));
			}

			// 14 , order confirmation
			Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
			if (Common.isElementExist(driver, By.id("Main_user"))) {
				HMCCommon.Login(hmcPage, testData);
				driver.navigate().refresh();
			}
			hmcPage.marketing.click();
			hmcPage.marketing_orderStatistics.click();
			hmcPage.marketing_orderStatistics_carts.click();
			hmcPage.marketing_os_carts_orderNumTxtBox.sendKeys(cartID);
			hmcPage.marketing_os_carts_search.click();
			Common.doubleClick(driver, driver.findElement(By
					.xpath("//*[text()='" + cartID + "']")));

			Assert.assertTrue(
					driver.findElement(
							By.xpath("//input[contains(@value,'"
									+ MTMPartNumber + "')]")).isDisplayed(),
					"mtm product is not displayed ");

			Assert.assertTrue(
					driver.findElement(
							By.xpath("//input[contains(@value,'" + Accessories
									+ "')]")).isDisplayed(),
					"Accessories is not displayed ");
			
			Thread.sleep(3000);
			hmcPage.marketing_os_carts_administration.click();
			Thread.sleep(3000);
			boolean ischecked = hmcPage.marketing_os_carts_isDigitalRiverCart
					.isSelected();
			if(!ischecked)
				Assert.fail("isDigitalRiverCart == false");

			// 15, Return to cart page and checkout again
			// switchToOtherWindow(winhandow_hmc);
			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl() + "/cart");
			while (!driver.getCurrentUrl().contains("cart")) {
				driver.navigate().back();
			}
			Thread.sleep(3000);
			b2cPage.Cart_CheckoutButton.click();
			Common.checkElementDisplays(driver, By.xpath("//*[@id='dr_shortPage']"), 50);
			String text = driver.findElement(
					By.xpath("//*[@id='dr_shortPage']")).getText();
			String refer_message = "Requisition already submitted and is being processed";
			String refer_message_FR = "Ce chariot a déjà été soumis. Veuillez réessayer.";
			if(!text.contains(refer_message) && !text.contains(refer_message_FR))
				Assert.fail(text + "| doesn't contains |" + refer_message);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	public String String2Price(String valueString) {
		String price = valueString.replace("$", "").replace("-", "")
				.replace("￥", "").replace("HK", "").replace("SG", "")
				.replace("£", "").replace("€", "").trim();//.replaceAll(" ", "");
		
		if (this.Store.equals("FR")) {
			price = price.replace(",", ".").replace(" ", ",");
		}
		return price;
	}

	public void switchToOtherWindow(String windowHandle) {
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			if (!window.equals(windowHandle)) {
				break;
			}
		}
	}

	public String getMessage(String store) {
		String message = "";
		if (store.equals("IE") || store.equals("GB")) {
			message = "Once you click the checkout button you will be transferred";
		} else if (store.equals("FR")) {
			message = "vous serez transféré vers";
		}
		return message;
	}

	public String getHyperLink(String store) {
		String link = "";
		if (store.equals("IE")) {
			link = "Digital+River";
		} else if (store.equals("GB")) {
			link = "Digital+River";
		} else if (store.equals("FR")) {
			link = "Digital+River";
		}
		return link;
	}

	public float GetPriceValue(String Price) {
		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll("€", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.trim();
		float priceValue;
		priceValue = Float.parseFloat(Price);
		return priceValue;
	}

}
