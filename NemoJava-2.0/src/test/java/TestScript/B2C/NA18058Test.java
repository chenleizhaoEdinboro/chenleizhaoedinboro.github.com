package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.Payment;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA18058Test extends SuperTestClass {
	private B2CPage b2cPage;
	private HMCPage hmcPage;
	private String anotherStore;
	private String storeID;
	Actions actions = null;
	String ProductNo;
	String Rep_Value = "1234567890";
	String QuoteStatus;
	String QuotePartNumber;
	String QuoteId;
	String QuoteQuantity;
	String QuoteTotalPrice;
	String Unit_One;
	String Unit_Two;
	private String loginID;
	private String pwd;
	private String FirstCartPartNo;
	private String ThirdCartPartNo;;
	private String backProdcutNo = "4X40E77324";

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

	public NA18058Test(String store, String storeName, String webStoreId) {
		this.Store = store;
		this.anotherStore = storeName;
		this.storeID = webStoreId;
		this.testName = "NA-18058";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA18058(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			actions = new Actions(driver);
			String selectAnotherCountry = ".//*[@id='countrySelector']/option[@cc='" + anotherStore + "']";
			// Step~1 : Go to HMC and enable request quote
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(1, "Logged in into HMC", Store, testName);
			Common.sleep(5000);
			hmcPage.Home_baseCommerce.click();
			hmcPage.Home_baseStore.click();
			hmcPage.baseStore_id.clear();
			hmcPage.baseStore_id.sendKeys(testData.B2C.getStore());
			hmcPage.baseStore_search.click();
			Common.doubleClick(driver, hmcPage.BaseStore_SearchResult);
			Thread.sleep(3000);
			hmcPage.baseStore_administration.click();
			Thread.sleep(5000);
			hmcPage.baseStore_isQuoteAvailable.click();
			hmcPage.HMC_Save.click();
			Thread.sleep(5000);
			hmcPage.HMC_Logout.click();
			// // step~2

			// step~3
			// add product to cart
			driver.get(testData.B2C.getloginPageUrl());
			FirstCartPartNo = testData.B2C.getDefaultMTMPN();
			ThirdCartPartNo = testData.B2C.getDefaultMTMPN();
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
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			Common.sleep(2000);
			b2cPage.Cart_QuickOrderTextBox.clear();
			b2cPage.Cart_QuickOrderTextBox.sendKeys(FirstCartPartNo);
			b2cPage.Cart_AddButton.click();
			if (!Common.isElementExist(b2cPage.PageDriver, By.xpath("//*[contains(@id,'quantity')]"), 5)) {

				b2cPage.Cart_QuickOrderTextBox.clear();
				b2cPage.Cart_QuickOrderTextBox.sendKeys(backProdcutNo);
				b2cPage.Cart_AddButton.click();
				FirstCartPartNo = backProdcutNo;
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

					Thread.sleep(2000);

					if (Common
							.isElementExist(
									driver,
									By.xpath("(//p[contains(@class,'partNumber')]/span)[3]"))) {
						FirstCartPartNo = driver.findElement(By.xpath("(//p[contains(@class,'partNumber')]/span)[3]")).getText();
					}else{
						FirstCartPartNo = driver.findElement(By.xpath("//p[contains(@class,'partNumber')]/span")).getText();
						
					}
				}
			}

			Thread.sleep(2000);
			if (Common
					.isElementExist(
							driver,
							By.xpath("(//p[contains(@class,'partNumber')]/span)[3]"))) {
				FirstCartPartNo = driver.findElement(By.xpath("(//p[contains(@class,'partNumber')]/span)[3]")).getText();
			}else{
				FirstCartPartNo = driver.findElement(By.xpath("//p[contains(@class,'partNumber')]/span")).getText();
				
			}
			
			Dailylog.logInfoDB(3, "part no of first product on cart page : " + FirstCartPartNo, Store, testName);
			// step~4
			// latest progress
			RequestQuote();
			// step~5
			String FirstQuoteStatus = QuoteStatus;
			Dailylog.logInfoDB(5, "First Quote Status : " + FirstQuoteStatus, Store, testName);
			Assert.assertTrue(FirstQuoteStatus.contains("APPROVED"));

			String FirstQuotePartNumber = QuotePartNumber;
			Dailylog.logInfoDB(5, "First Quote PartNumber : " + FirstQuotePartNumber, Store, testName);
			Assert.assertTrue(FirstQuotePartNumber.equals(FirstCartPartNo));

			String FirstQuoteId = QuoteId;
			Dailylog.logInfoDB(5, "First Quote Id : " + FirstQuoteId, Store, testName);

			String FirstQuoteQuantity = QuoteQuantity;
			Dailylog.logInfoDB(5, "First Quote Quantity : " + FirstQuoteQuantity, Store, testName);

			String FirstQuoteTotalPrice = QuoteTotalPrice;
			Dailylog.logInfoDB(5, "First Quote TotalPrice : " + FirstQuoteTotalPrice, Store, testName);
			// step~6
			driver.get(testData.B2C.getHomePageUrl() + "/cart");

			driver.findElement(By.xpath("//*[@id='quickOrderProductId']")).clear();
			driver.findElement(By.xpath("//*[@id='quickOrderProductId']")).sendKeys(FirstCartPartNo);
			if (Common.isElementExist(driver, By.xpath("//*[@id='quickAddInput']/a"))) {
				driver.findElement(By.xpath("//*[@id='quickAddInput']/a")).click();
			} else {
				driver.findElement(By.xpath(".//*[@id='quickAddInput']/button")).click();
			}
			Common.sleep(2000);
			Dailylog.logInfoDB(6, "part no of Second product on cart page : " + FirstCartPartNo, Store, testName);
			// step~7
			b2cPage.Cart_CheckoutButton.click();
			Common.sleep(4000);
//			Assert.assertTrue(driver.getTitle().contains("Shipping"));
			// step~8

			if (Common.isElementExist(driver, By.cssSelector(".textLink.checkout-shippingAddress-editLink"))) {
				// b2cPage.Shipping_editAddress.click();
				Common.sleep(8000);
				if (Common.isElementExist(driver, By.cssSelector(".textLink.checkout-shippingAddress-editLink"), 3)) {
					try {
						driver.findElement(By.cssSelector(".textLink.checkout-shippingAddress-editLink")).click();
					} catch (Exception e) {
					}
				}
				B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
				Thread.sleep(3000);
				actions.sendKeys(Keys.PAGE_UP).perform();
				try {
					b2cPage.Shipping_ContinueButton.click();
				} catch (Exception e) {
					if (Common.isElementExist(driver,
							By.xpath(".//*[@id='mainContent']/div[1]/div/div[1]/div[4]/button"), 3))
						driver.findElement(By.xpath(".//*[@id='mainContent']/div[1]/div/div[1]/div[4]/button")).click();
				}
			}
			if (Common.isElementExist(driver, By.id("checkout_validateFrom_skip"), 5)) {
				driver.findElement(By.id("checkout_validateFrom_skip")).click();
			}

			if (Common.checkElementExists(driver, b2cPage.Shipping_validateAddressItem, 5))
				b2cPage.Shipping_validateAddressItem.click();
			if (Common.checkElementExists(driver, b2cPage.Shipping_validateAddress, 5))
				b2cPage.Shipping_validateAddress.click();
			Thread.sleep(3000);

			// step~9 : select payment method
			Actions actions = new Actions(driver);
			if(Common.isElementExist(driver, By.id("PaymentTypeSelection_PURCHASEORDER"))){
				Common.javascriptClick(b2cPage.PageDriver, b2cPage.payment_PurchaseOrder);
				
			}else if(Common.isElementExist(driver, By.id("PaymentTypeSelection_BANKDEPOSIT"))){
				Common.javascriptClick(b2cPage.PageDriver, b2cPage.Payment_bankDeposit);
			}else {
				B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			}
			
			Payment.fillOtherPaymentFields(b2cPage, testData);

			RequestQuote();
			// storing value from Quote page
			String SecondQuoteStatus = QuoteStatus;
			Dailylog.logInfoDB(9, "Second Quote Status : " + SecondQuoteStatus, Store, testName);
			Assert.assertTrue(SecondQuoteStatus.contains("APPROVED"));

			String SecondQuotePartNumber = QuotePartNumber;
			Dailylog.logInfoDB(9, "Second Quote PartNumber : " + SecondQuotePartNumber, Store, testName);
			Assert.assertTrue(SecondQuotePartNumber.equals(FirstCartPartNo));

			String SecondQuoteId = QuoteId;
			Dailylog.logInfoDB(9, "Second Quote Id : " + SecondQuoteId, Store, testName);

			String SecondQuoteQuantity = QuoteQuantity;
			Dailylog.logInfoDB(9, "Second Quote Quantity : " + SecondQuoteQuantity, Store, testName);

			String SecondQuoteTotalPrice = QuoteTotalPrice;
			Dailylog.logInfoDB(9, "Second Quote TotalPrice : " + SecondQuoteTotalPrice, Store, testName);
			// step~10 :Step:4 Switch the Country from Country Selector
			// Dropdown ===========//
			Common.scrollToElement(driver, b2cPage.CartPage_CountrySelectorDropdown);
			b2cPage.CartPage_CountrySelectorDropdown.click();
			driver.findElement(By.xpath(selectAnotherCountry)).click();
			Dailylog.logInfoDB(10, "Switched to different store: " + anotherStore, Store, testName);
			Common.sleep(5000);
			String homePageURL_newCountry = driver.getCurrentUrl();
			Assert.assertTrue(homePageURL_newCountry.contains(anotherStore.toLowerCase()));

			// step~11
			driver.get(driver.getCurrentUrl() + "/cart");
			Thread.sleep(2000);
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			Common.sleep(2000);
			b2cPage.Cart_QuickOrderTextBox.clear();
			b2cPage.Cart_QuickOrderTextBox.sendKeys(ThirdCartPartNo);
			b2cPage.Cart_AddButton.click();
			if (!Common.isElementExist(b2cPage.PageDriver, By.xpath("//*[contains(@id,'quantity')]"), 5)) {

				b2cPage.Cart_QuickOrderTextBox.clear();
				b2cPage.Cart_QuickOrderTextBox.sendKeys(backProdcutNo);
				b2cPage.Cart_AddButton.click();
				ThirdCartPartNo = backProdcutNo;
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
			
			if (Common
					.isElementExist(
							driver,
							By.xpath("(//p[contains(@class,'partNumber')]/span)[3]"))) {
				ThirdCartPartNo = driver.findElement(By.xpath("(//p[contains(@class,'partNumber')]/span)[3]"))
						.getText();
			}else{
				ThirdCartPartNo = driver.findElement(By.xpath("//p[contains(@class,'partNumber')]/span")).getText();
				
			}
			Dailylog.logInfoDB(12, "part no of Third product on cart page : " + ThirdCartPartNo, Store, testName);
			// step~12
			RequestQuote();
			// step~13
			String ThirdQuoteStatus = QuoteStatus;
			Dailylog.logInfoDB(13, "Third Quote Status : " + ThirdQuoteStatus, Store, testName);
			Assert.assertTrue(ThirdQuoteStatus.contains("APPROVED"));

			String ThirdQuotePartNumber = QuotePartNumber;
			Dailylog.logInfoDB(13, "Third Quote PartNumber : " + ThirdQuotePartNumber, Store, testName);
			Assert.assertTrue(ThirdQuotePartNumber.equals(ThirdCartPartNo));

			String ThirdQuoteId = QuoteId;
			Dailylog.logInfoDB(13, "Third Quote Id : " + ThirdQuoteId, Store, testName);

			String ThirdQuoteQuantity = QuoteQuantity;
			Dailylog.logInfoDB(13, "Third Quote Quantity : " + ThirdQuoteQuantity, Store, testName);

			String ThirdQuoteTotalPrice = QuoteTotalPrice;
			Dailylog.logInfoDB(13, "Third Quote TotalPrice : " + ThirdQuoteTotalPrice, Store, testName);
			// step~14 : Go to my account page and check history
			Common.sleep(4000);
			driver.get(testData.B2C.getHomePageUrl() + "/my-account");

			b2cPage.MyAccount_ViewSavedQuote.click();

			// checking presence of All (3) QuoteID
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//a[contains(.,'" + FirstQuoteId + "')]")));
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//a[contains(.,'" + SecondQuoteId + "')]")));
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//a[contains(.,'" + ThirdQuoteId + "')]")));
			// verifying search criteria
			Dailylog.logInfoDB(14, "Verifying Search Criteria", Store, testName);
			Validate_SearchCriteriaText("QuoteID");
			Validate_SearchCriteriaText("StoreID");

			Validate_SearchCriteriaText("ProductPartNumber");
			Validate_SearchCriteriaText("TotalAmount");

			// verify Column Name
			Dailylog.logInfoDB(14, "Verifying Column Name", Store, testName);
			Validate_ColumnNameText("Quote ID");
			Validate_ColumnNameText("Store");
			Validate_ColumnNameText("Status");
			Validate_ColumnNameText("Part Number");
			Validate_ColumnNameText("Quantity");
			Validate_ColumnNameText("Total Price");
			Validate_ColumnNameText("Date Placed");
			Validate_ColumnNameText("Actions");
			// step~15
			driver.findElement(By.xpath("//a[contains(.,'View')][contains(@href,'" + FirstQuoteId + "')]")).click();
			Dailylog.logInfoDB(15, "Clicked on View Link of : " + FirstQuoteId, Store, testName);
			Common.sleep(2000);
			Assert.assertTrue(driver.getCurrentUrl().contains(FirstQuoteId));
			Assert.assertTrue(driver.getCurrentUrl().contains(this.Store.toLowerCase()));
			// step~16 : click on convert to order
			Common.javascriptClick(driver, b2cPage.Quote_convertOrder);
			// b2cPage.cartPage_ConvertToOrderBtn.click();
			Dailylog.logInfoDB(16, "Clicked On Convert To Order", Store, testName);
			Assert.assertTrue(b2cPage.Shipping_ContinueButton.isDisplayed());
			String PartNumberOnShipping ;
			if(Common.checkElementExists(driver, b2cPage.PartNumberOnPayment, 5)){
				//old UI
				PartNumberOnShipping = b2cPage.PartNumberOnPayment.getText();
				PartNumberOnShipping = PartNumberOnShipping.split(":  ")[1];
			}else{
				//new UI
				PartNumberOnShipping = driver
						.findElement(By.xpath("//div[@class='summary-items']//p")).getText();
				PartNumberOnShipping = PartNumberOnShipping.split(": ")[1].split("\n")[0];
			}
			
			
			Assert.assertEquals(FirstCartPartNo, PartNumberOnShipping);
			// step~17
			driver.get(testData.B2C.getHomePageUrl() + "/my-account");

			b2cPage.MyAccount_ViewSavedQuote.click();

			driver.findElement(By.xpath("//a[contains(.,'View')][contains(@href,'" + SecondQuoteId + "')]")).click();
			Dailylog.logInfoDB(17, "Clicked on View Link of : " + SecondQuoteId, Store, testName);
			Common.sleep(2000);
			Assert.assertTrue(driver.getCurrentUrl().contains(SecondQuoteId));
			// step~18
			Common.javascriptClick(driver, b2cPage.Quote_convertOrder);
			// b2cPage.cartPage_ConvertToOrderBtn.click();
			Common.sleep(4000);
			Dailylog.logInfoDB(18, "Clicked On Convert To Order", Store, testName);
			Common.sleep(4000);

//			Assert.assertTrue(driver.getTitle().contains("Payment Information"));
			Assert.assertTrue(b2cPage.Payment_ContinueButton.isDisplayed());
			Dailylog.logInfoDB(18, "On Payment Information page", Store, testName);
			String PartNumberOnPayment;
			if(Common.checkElementExists(driver, b2cPage.PartNumberOnPayment, 5)){
				//old UI
				PartNumberOnPayment = b2cPage.PartNumberOnPayment.getText();
				PartNumberOnPayment = PartNumberOnPayment.split(":  ")[1];
			}else{
				//new UI
				PartNumberOnPayment = driver
						.findElement(By.xpath("//div[@class='summary-items']//p")).getText();
				PartNumberOnPayment = PartNumberOnPayment.split(": ")[1].split("\n")[0];
			}
			System.out.println("PartNumberOnPayment :" + PartNumberOnPayment);
			System.out.println("SecondCartPartNo :" + FirstCartPartNo);
			Assert.assertEquals(FirstCartPartNo, PartNumberOnPayment);
			String QuantityOnPayment;
			if(Common.checkElementExists(driver,b2cPage.PartNumberOnPayment, 5)){
				//old UI
				 QuantityOnPayment = driver
						.findElement(By.xpath("((//p[@class='checkout-shoppingCart-previewSubtitle'])[1]/../p)[2]"))
						.getText();
				QuantityOnPayment = QuantityOnPayment.split(": ")[1];
			}else{
				//new UI
				QuantityOnPayment = driver
						.findElement(By.xpath("//div[@class='summary-items']//p")).getText();
				QuantityOnPayment = QuantityOnPayment.split(":")[2].split("\n")[0];
			}
			
			System.out.println("QuantityOnPayment :" + QuantityOnPayment);
			System.out.println("SecondQuoteQuantity :" + SecondQuoteQuantity);
			Assert.assertEquals(SecondQuoteQuantity, QuantityOnPayment);
			String PriceOnPayment = "";
			if(Common.checkElementExists(driver,b2cPage.PriceOnPayment, 5)){
				//old UI
				 PriceOnPayment = b2cPage.PriceOnPayment.getText();
			}else{
				//new UI
				PriceOnPayment = driver
						.findElement(By.xpath("//div[@class='base-price']")).getText();
				
			}
			
			PriceOnPayment = PriceOnPayment.replace(" ", "");
			System.out.println("PriceOnPayment :" + PriceOnPayment);
			System.out.println("SecondQuoteTotalPrice :" + SecondQuoteTotalPrice);
			Assert.assertEquals(SecondQuoteTotalPrice, PriceOnPayment);
			// step~19

			driver.get(testData.B2C.getHomePageUrl() + "/my-account");

			b2cPage.MyAccount_ViewSavedQuote.click();
			driver.findElement(By.xpath(".//*[@id='searchCriteria']/option[text()='Quote ID']")).click();
			b2cPage.Quote_searchTextBox.clear();
			b2cPage.Quote_searchTextBox.sendKeys(FirstQuoteId);

			Dailylog.logInfoDB(19, "Clicked On Quote ID in search criteria", Store, testName);
			b2cPage.Quote_searchSubmitBtn.click();

			String recordsFound = driver.findElement(By.xpath("(.//*[@class='totalResults'])[1]")).getText();

			Assert.assertTrue(recordsFound.contains("1 Record(s) found"));

			Assert.assertTrue(
					driver.findElement(By.xpath("//a[contains(.,'View')][contains(@href,'" + FirstQuoteId + "')]"))
							.isDisplayed());
			// step~20

			driver.findElement(By.xpath(".//*[@id='searchCriteria']/option[text()='Store ID']")).click();
			b2cPage.Quote_searchTextBox.clear();
			b2cPage.Quote_searchTextBox.sendKeys(storeID);

			Dailylog.logInfoDB(20, "Clicked On Store ID in search criteria", Store, testName);
			b2cPage.Quote_searchSubmitBtn.click();

			Assert.assertTrue(
					driver.findElement(By.xpath("//a[contains(.,'View')][contains(@href,'" + FirstQuoteId + "')]"))
							.isDisplayed());
			Assert.assertTrue(
					driver.findElement(By.xpath("//a[contains(.,'View')][contains(@href,'" + SecondQuoteId + "')]"))
							.isDisplayed());
	
			Assert.assertFalse(Common.isElementExist(b2cPage.PageDriver,
					By.xpath("//a[contains(.,'View')][contains(@href,'" + ThirdQuoteId + "')]"), 5));

			// step~21
			driver.findElement(By.xpath(".//*[@id='searchCriteria']/option[text()='Part Number']")).click();
			b2cPage.Quote_searchTextBox.clear();
			b2cPage.Quote_searchTextBox.sendKeys(FirstCartPartNo);

			Dailylog.logInfoDB(21, "Clicked On Part Number in search criteria", Store, testName);
			b2cPage.Quote_searchSubmitBtn.click();
			Assert.assertTrue(
					driver.findElement(By.xpath("//a[contains(.,'View')][contains(@href,'" + FirstQuoteId + "')]"))
							.isDisplayed());

			// step~22
			driver.findElement(By.xpath(".//*[@id='searchCriteria']/option[@value='TotalAmount']")).click();
			driver.findElement(By.xpath(".//*[@id='amountOperator']/option[@value='larger']")).click();
			b2cPage.Quote_searchTextBox.clear();
			b2cPage.Quote_searchTextBox.sendKeys("10");
			Dailylog.logInfoDB(22, "Clicked On Part Number in search criteria", Store, testName);
			b2cPage.Quote_searchSubmitBtn.click();
	
			Assert.assertTrue(Common.isElementExist(b2cPage.PageDriver,
					By.xpath("//a[contains(.,'View')][contains(@href,'" + FirstQuoteId + "')]"), 5));
			Assert.assertTrue(Common.isElementExist(b2cPage.PageDriver,
					By.xpath("//a[contains(.,'View')][contains(@href,'" + SecondQuoteId + "')]"), 5));
			
			Assert.assertTrue(Common.isElementExist(b2cPage.PageDriver,
					By.xpath("//a[contains(.,'View')][contains(@href,'" + ThirdQuoteId + "')]"), 5));
			// step~23
			driver.findElement(By.xpath(".//*[@id='searchCriteria']/option[@value='TotalAmount']")).click();
			driver.findElement(By.xpath(".//*[@id='amountOperator']/option[text()='equals']")).click();
			b2cPage.Quote_searchTextBox.clear();
			String EnteredAmnt = SecondQuoteTotalPrice.split("\\$")[1];
			EnteredAmnt = EnteredAmnt.replace(",", "");
			b2cPage.Quote_searchTextBox.sendKeys(EnteredAmnt);
			Dailylog.logInfoDB(23, "Clicked On Part Number in search criteria", Store, testName);
			b2cPage.Quote_searchSubmitBtn.click();

			
			Assert.assertTrue(
					driver.findElement(By.xpath("//a[contains(.,'View')][contains(@href,'" + SecondQuoteId + "')]"))
							.isDisplayed());

			

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	// validate options of search criteria and Column list
	private void Validate_SearchCriteriaText(String text) {
		Assert.assertTrue(
				driver.findElement(By.xpath(".//*[@id='searchCriteria']/option[@value='" + text + "']")).isDisplayed(),
				text + " is not present in search criteria");
	}

	// validate Column Name Text
	private void Validate_ColumnNameText(String text) {
		Assert.assertTrue(driver.findElement(By.xpath("//table[@class='orderListTable']//th[text()='" + text + "']"))
				.isDisplayed(), text + " is not present in search criteria");
	}

	// =*=*=*=*=*=*=*Add A product to cart for B2B*=*=*=*=*=*=*=//

	// +=+=+=+=++=+=+=+=+=+=Request Quote Method+=+=+=+=+=+=+=+=+=+=//
	private void RequestQuote() {
		
		if(b2cPage.RequestQuoteBtn.isDisplayed()){	
			b2cPage.RequestQuoteBtn.click();
		}else{
			Assert.assertTrue(b2cPage.Payment_RequestQuoteBtn.isDisplayed(), "Request Quote button is not present.");
			b2cPage.Payment_RequestQuoteBtn.click();
		}
		
		
		Dailylog.logInfoDB(4, "Clicked on Request Quote button", Store, testName);
		Common.sleep(6000);
		b2cPage.Quote_RepID.clear();
		b2cPage.Quote_RepID.sendKeys("1234567890");
		Common.sleep(5000);
		b2cPage.Quote_SubmitQuoteBtn.click();
		Common.sleep(5000);
		QuoteStatus = b2cPage.quotePage_QuoteStatus.getText();
		QuotePartNumber = b2cPage.quotePage_PartNumber.getText();
		QuoteId = b2cPage.Quote_quoteNum.getText();
		QuoteQuantity = b2cPage.quotePage_Quantity.getText();
		QuoteTotalPrice = b2cPage.quotePage_TotalPrice.getText();
		Dailylog.logInfoDB(4, "Captured information from Quote page", Store, testName);
	}

}
