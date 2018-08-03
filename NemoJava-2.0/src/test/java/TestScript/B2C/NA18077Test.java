package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

public class NA18077Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public String laptopPageURL;
	public String B2C_Country;
	public String dataInput;
	public String xpath;
	public String mtmNum;
	public String b2cunitNo;
	public String SavedCart_ProdPrice1;
	private String product1;
	private String product2_bak =  "06P4069";
	private String priceRlueID = "";
	private String ruleName = "";
	private String newPrice;
	String CartName = Common.getDateTimeString();
	String priceChangeMessage = "the price has changed";
	String product2;
	String WarehouseData;
	
	public NA18077Test(String store, String accessory, String countrySearch, String baseStore) {
		this.Store = store;
		this.product2 = accessory;
		this.B2C_Country = countrySearch;
//		this.b2cunitNo= b2cUnit;
		this.testName = "NA-18077";
	}
	
	private enum EnumTestData {
		mtmPro, ctoPro, country, unit, errMsg;
	}
	
	private String getData(String store, EnumTestData dataName) {
		if (store.equals("AU")) {
			switch (dataName) {
			case mtmPro:
				return "65C5KAC1AU";
			case ctoPro:
				return "10KMCTO1WWENAU5";
			case country:
				return "Australia";
			case unit:
				return "AU Public unit";
			case errMsg:
				return "Oh dear … something’s not right. Please call us to complete your order. We’re really sorry for the inconvenience.";
			default:
				return null;
			}
		} else if (store.equals("GB")) {
			switch (dataName) {
			case mtmPro:
				return "61A9MAT1UK";
			case ctoPro:
				return "20H1CTO1WWENGB1";
			case country:
				return "United Kingdom";
			case unit:
				return "gbweb";
			case errMsg:
				return "Oh dear … something’s not right. Please call us to complete your order. We’re really sorry for the inconvenience.";
			default:
				return null;
			}
		} else if (store.equals("IE")) {
			switch (dataName) {
			case mtmPro:
				return "80SG001AUK";
			case ctoPro:
				return "10FMCTO1WWENIE0";
			case country:
				return "Ireland";
			case unit:
				return "Ireland store";
			case errMsg:
				return "Oh dear … something’s not right. Please call us to complete your order. We’re really sorry for the inconvenience.";
			default:
				return null;
			}
		} else if (store.equals("CA")) {
			switch (dataName) {
			case mtmPro:
				return "80XM00F2CF";
			case ctoPro:
				return "20H1CTO1WWENCA0";
			case country:
				return "Canada";
			case unit:
				return "CA web store unit";
			case errMsg:
				return "Oh dear … something’s not right. Please call us to complete your order. We’re really sorry for the inconvenience.";
			default:
				return null;
			}
		} else if (store.equals("CA_AFFINITY")) {
			switch (dataName) {
			case mtmPro:
				return "60FELAR1US";
			case ctoPro:
				return "10FYCTO1WWENCA0";
			case country:
				return "Canada";
			case unit:
				return "CA oma store unit";
			case errMsg:
				return "Oh dear … something’s not right. Please call us to complete your order. We’re really sorry for the inconvenience.";
			default:
				return null;
			}
		} else if (store.equals("HK")) {
			switch (dataName) {
			case mtmPro:
				return "80U10082HH";
			case ctoPro:
				return "20H1CTO1WWENHK1";
			case country:
				return "Hong Kong S.A.R. of China";
			case unit:
				return "HK public store unit";
			case errMsg:
				return "Oh dear … something’s not right. Please call us to complete your order. We’re really sorry for the inconvenience.";
			default:
				return null;
			}
		} else if (store.equals("HKZF")) {
			switch (dataName) {
			case mtmPro:
				return "80TU0006HH";
			case ctoPro:
				return "20FHCTO1WWENHK0";
			case country:
				return "Hong Kong S.A.R. of China";
			case unit:
				return "HK public store unit";
			case errMsg:
				return "糟糕…發生問題。請撥打電話給我們以完成您的訂單。造成不便，我們深感抱歉。";
			default:
				return null;
			}
		} else if (store.equals("JP")) {
			switch (dataName) {
			case mtmPro:
				return "60E2GAR1JP";
			case ctoPro:
				return "20H5CTO1WWJAJP6";
			case country:
				return "Japan";
			case unit:
				return "JP public store unit";
			case errMsg:
				return "エラーが発生しました。お電話でご連絡ください。注文処理を完了させていただきます。ご不便をおかけしますことを重ねてお詫び申し上げます。";
			default:
				return null;
			}
		} else if (store.equals("NZ")) {
			switch (dataName) {
			case mtmPro:
				return "80U2002GAU";
			case ctoPro:
				return "20HNCTO1WWENNZ3";
			case country:
				return "New Zealand";
			case unit:
				return "NZ Public unit";
			case errMsg:
				return "Oh dear … something’s not right. Please call us to complete your order. We’re really sorry for the inconvenience.";
			default:
				return null;
			}
		} else if (store.equals("SG")) {
			switch (dataName) {
			case mtmPro:
				return "80TU001MSB";
			case ctoPro:
				return "20H1CTO1WWENSG0";
			case country:
				return "Singapore";
			case unit:
				return "Singapore Public Store";
			case errMsg:
				return "Oh dear … something’s not right. Please call us to complete your order. We’re really sorry for the inconvenience.";
			default:
				return null;
			}
		} else if (store.equals("US")) {
			switch (dataName) {
			case mtmPro:
				return "60FELAR1US";
			case ctoPro:
				return "20H1CTO1WWENUS0";
			case country:
				return "United States";
			case unit:
				return "US web store unit";
			case errMsg:
				return "Oh dear … something’s not right. Please call us to complete your order. We’re really sorry for the inconvenience.";
			default:
				return null;
			}
		} else if (store.equals("US_BPCTO")) {
			switch (dataName) {
			case mtmPro:
				return "60FELAR1US";
			case ctoPro:
				return "10EUCTO1WWENUS0";
			case country:
				return "United States";
			case unit:
				return "insight direct usa unit";
			case errMsg:
				return "Oh dear … something’s not right. Please call us to complete your order. We’re really sorry for the inconvenience.";
			default:
				return null;
			}
		} else if (store.equals("US_OUTLET")) {
			switch (dataName) {
			case mtmPro:
				return "30B3X006US";
			case ctoPro:
				return "20EVCTO1WWENUS0";
			case country:
				return "United States";
			case unit:
				return "US Outlet";
			case errMsg:
				return "Oh dear … something’s not right. Please call us to complete your order. We’re really sorry for the inconvenience.";
			default:
				return null;
			}
		} else if (store.equals("USEPP")) {
			switch (dataName) {
			case mtmPro:
				return "60FELAR1US";
			case ctoPro:
				return "20H1CTO1WWENUS0";
			case country:
				return "United States";
			case unit:
				return "US lenovousepp store unit";
			case errMsg:
				return "Oh dear … something’s not right. Please call us to complete your order. We’re really sorry for the inconvenience.";
			default:
				return null;
			}
		} else if (store.equals("KR")) {
			switch (dataName) {
			case mtmPro:
				return "F0CC000GUS";
			case ctoPro:
				return "20H8CTO1WWENUS0";
			case country:
				return "Korea";
			case unit:
				return "Korea Public Store";
			case errMsg:
				return "Oh dear … something’s not right. Please call us to complete your order. We’re really sorry for the inconvenience.";
			default:
				return null;
			}
		} else {
			return null;
		}
	}
	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA18077(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			product1 = testData.B2C.getDefaultMTMPN();
			/*
			// step~1
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Common.sleep(5000);
			hmcPage.Home_baseCommerce.click();
			hmcPage.Home_baseStore.click();
			hmcPage.baseStore_id.clear();
			hmcPage.baseStore_id.sendKeys(testData.B2C.getStore());
			hmcPage.baseStore_search.click();
			hmcPage.BaseStore_SearchResult.click();
			Dailylog.logInfoDB(1, "Clicked on search result", Store, testName);
			// click on Administration :In HMC -> Base store -> adminstration -> 
			//priceChangeMessage: The price has change
			hmcPage.baseStore_administration.click();
			hmcPage.baseStore_priceChangeMessageInput.clear();
			hmcPage.baseStore_priceChangeMessageInput.sendKeys(priceChangeMessage);
			hmcPage.Common_SaveButton.click();
			hmcPage.HMC_Logout.click();
			*/
			// step~2:Go to B2C website and Login	
			loginB2CPage();
			Dailylog.logInfoDB(2, "ClicGo to B2C website and Login", Store, testName);
			
			// step~3 :Add 2 kinds of Products into Cart
			b2cPage.Navigation_CartIcon.click();
			if (Common.checkElementExists(driver, b2cPage.Navigation_ViewCartButton, 5))
				b2cPage.Navigation_ViewCartButton.click();
			Common.sleep(2000);
			B2CCommon.emptyCart(driver, b2cPage);
			Common.sleep(3000);
			// Adding First Product
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			addPartNumberToCart(b2cPage, product1);
			Common.sleep(3000);
			By noStockMess =  By.xpath("//*[contains(text(),'is invalid') or"
					+ " contains(text(),'No Stock for the Product') or contains(text(),'Unfortunately') ]");
			if(Common.checkElementDisplays(driver, noStockMess, 5)){
				product1 = productBackNum(this.Store);
				addPartNumberToCart(b2cPage, product1);
			}
			Dailylog.logInfoDB(3, "Added product1: " + product1, Store, testName);
			// Adding second Product
			Common.sleep(2000);
			addPartNumberToCart(b2cPage, product2);
			Common.sleep(3000);
			if(Common.checkElementDisplays(driver, noStockMess, 5)){
				product2 = product2_bak;
				addPartNumberToCart(b2cPage, product2);
			}
			Dailylog.logInfoDB(3, "Added product2: " + product2, Store, testName);
			// get products details
			String Cart_FirstProdPrice = b2cPage.Cart_itemPriceList.get(0).getText();
			String Cart_FirstProdNo = b2cPage.Cart_itemNumberList.get(0).getText();
			((JavascriptExecutor) driver).executeScript("scroll(0,300)");
			String Cart_SecondProdPrice = b2cPage.Cart_itemPriceList.get(1).getText();
			String Cart_SecondProdNo = b2cPage.Cart_itemNumberList.get(1).getText();
			
			// step~4 :Click “Save cart” button
			Common.javascriptClick(driver, b2cPage.Cart_saveCart);
			Common.sleep(5000);
			boolean isDisplayed = Common.checkElementDisplays(driver, By.id("realsavecartname"), 5);
			Assert.assertTrue(isDisplayed, "Save Cart Text box is not present");
			Dailylog.logInfoDB(4, "Click “Save cart” button", Store, testName);
			
			// step~5:Enter saved cart name: Pricing_Cart Click “OK”
			b2cPage.Cart_nameTextBox.clear();
			b2cPage.Cart_nameTextBox.sendKeys("Save_" + CartName);
			b2cPage.Cart_saveCartBtn.click();
			Dailylog.logInfoDB(5, "Saved Cart button is clicked, Cart name is: " + "Save_" + CartName, Store, testName);
			
			// step~6&7
			ViewSavedCart();
			// verify product
			String SavedCart_ProdNo1 = b2cPage.Cart_itemNumberList.get(0).getText();
			String SavedCart_ProdNo2 = b2cPage.Cart_itemNumberList.get(1).getText();
			SavedCart_ProdPrice1 = b2cPage.Cart_itemPriceList.get(0).getText();
			String SavedCart_ProdPrice2 = b2cPage.Cart_itemPriceList.get(1).getText();
			Assert.assertEquals(Cart_FirstProdNo, SavedCart_ProdNo1);
			Assert.assertEquals(Cart_SecondProdNo, SavedCart_ProdNo2);
			Assert.assertEquals(Cart_FirstProdPrice, SavedCart_ProdPrice1);
			Assert.assertEquals(Cart_SecondProdPrice, SavedCart_ProdPrice2);
			
			//step~8:In the HMC -> Pricing Setting -> Pricing Cockepit -> Login -> Click B2C Price Rule
			//-> Click Create New Group -> Select Webprice then Continue -> Create the Webprice for Prduct One
			
			// create web price rule
			ruleName = "WP_" + Store + Common.getDateTimeString();
			newPrice = Integer.toString((int)(GetPriceValue(SavedCart_ProdPrice1) - 1));
			priceRlueID = createRule(product1, ruleName, newPrice);
			System.out.println(priceRlueID);
			Dailylog.logInfoDB(8, "create the webprice rule", Store, testName);
			
			// step~9:Back to B2B website->Click “my Account” link->Click “my saved carts” link->Click “view” in Pricing_Cart line
			loginB2CPage();
			Common.sleep(2000);
			if(!driver.getCurrentUrl().contains("save-cart")){
				ViewSavedCart();	
			}
			Common.sleep(2000);
			
			//verify:As the price has change, there will display the message: The price has change
			String PriceChangeMesg = b2cPage.savedCart_priceChangeMessage.getText();
			Assert.assertEquals(PriceChangeMesg, priceChangeMessage, "The price has change is display");
			
			//verify:Product one will strike off old price
			String savedCart_oldPrice = b2cPage.Cart_itemPriceList.get(1).getText();
			Assert.assertEquals(savedCart_oldPrice, SavedCart_ProdPrice2);
			
			//verify:show new price
			verifyChangePrice();
			Dailylog.logInfoDB(9, "clicked on view link for save cart " + "Save_" + CartName + ".Verify the price change message", Store, testName);
			
			// Step~10:Click “Open Cart”.Verify the Product one on Cart page will show New Price and Product Two price has no change
			Common.sleep(2000);
			b2cPage.SavedCart_OpenCart.click();
			Common.sleep(2000);
			verifyChangePrice();
			String Cart_SecondProdPrice2 = b2cPage.Cart_itemPriceList.get(1).getText();
			Assert.assertEquals(Cart_SecondProdPrice2, SavedCart_ProdPrice2);
			Dailylog.logInfoDB(10, "Clicked on Open Cart,verify the Product one on Cart page will show New Price and Product Two price has no change ", Store, testName);
			
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
	
	public static void emptyCart(WebDriver driver, B2CPage b2cPage) throws Exception {
		if (Common.isElementExist(driver, By.xpath("//form[@id='emptyCartItemsForm']/a"))) {
			b2cPage.Cart_emptyCart.click();
			Thread.sleep(4000);
			if (Common.isElementExist(driver,
					By.xpath("//div[@class='popup_arrow_box']/div/input[contains(@onclick,'submit')]"))) {
				b2cPage.NewCart_ConfirmDelete.click();
			}
		}else if(Common.isElementExist(driver, By.xpath("//form[@id='emptyCartItemsForm']/a"))){
			b2cPage.Cart_emptyCart.click();
			Thread.sleep(4000);
			if (Common.isElementExist(driver,
					By.xpath("//div[@class='popup_arrow_box']/div/input[contains(@onclick,'submit')]"))) {
				b2cPage.NewCart_ConfirmDelete.click();
			}
		}
	}
	
	public static float GetPriceValue(String Price) {

		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.replaceAll("￥", "");
		Price = Price.replaceAll("£", "");
		Price = Price.trim();
		float priceValue;
		priceValue = Float.parseFloat(Price);
		return priceValue;
	}

	public void verifyChangePrice(){
		String saveCart_newPrice = b2cPage.Cart_itemPriceList.get(0).getText();
		Float final_saveCart_newPrice = GetPriceValue(saveCart_newPrice);
		Float final_SavedCart_ProdPrice1 = GetPriceValue(SavedCart_ProdPrice1);
		System.out.println(final_saveCart_newPrice);
		System.out.println(final_SavedCart_ProdPrice1);
		Assert.assertNotEquals(final_saveCart_newPrice, final_SavedCart_ProdPrice1);
	}
	
	public String createRule(String testProduct, String ruleName, String newPrice) throws InterruptedException {
		WebElement target;
		String dataInput;
		String xpath;
		System.out.println("Create rules***********************");
		driver.get(testData.HMC.getHomePageUrl());
		// driver.manage().deleteCookieNamed(hmcCookieName);
		// Thread.sleep(1000);
		// driver.get(testData.HMC.getHomePageUrl());
		if (Common.isElementExist(driver, By.id("Main_user"))) {
			HMCCommon.Login(hmcPage, testData);
			driver.navigate().refresh();
		}
		hmcPage.Home_PriceSettings.click();
		hmcPage.Home_PricingCockpit.click();
		driver.switchTo().frame(0);
		// loginPricingCockpit();
		hmcPage.PricingCockpit_B2CPriceRules.click();
//		new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//tr[@class='loader']")));
		Thread.sleep(5000);
		hmcPage.B2CPriceRules_CreateNewGroup.click();
		Thread.sleep(3000);
		hmcPage.B2CPriceRules_SelectGroupType.click();
		Thread.sleep(3000);
		hmcPage.B2BPriceRules_WebPrices.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_Continue);
		//hmcPage.B2CPriceRules_Continue.click();
		// Floor price name
		hmcPage.B2CPriceRules_PriceRuleName.clear();
		hmcPage.B2CPriceRules_PriceRuleName.sendKeys(ruleName);
		// Validate from date
		hmcPage.B2CPriceRules_ValidFrom.click();
		Thread.sleep(1000);
		int count = driver.findElements(By.xpath("//td[contains(@class,'today')]/preceding-sibling::*")).size();
		WebElement yesterday;
		if (count > 0) {
			yesterday = driver.findElements(By.xpath("//td[contains(@class,'today')]/preceding-sibling::*"))
					.get(count - 1);
			System.out.println("Valid From: " + yesterday.getText());
			yesterday.click();
			hmcPage.B2CPriceRules_ValidFrom.sendKeys(Keys.ENTER);
		} else {
			yesterday = driver.findElements(By.xpath("//td[contains(@class,'today')]/../preceding-sibling::tr/td"))
					.get(count - 1);
			System.out.println("Valid From: " + yesterday.getText());
			yesterday.click();
			hmcPage.B2CPriceRules_ValidFrom.sendKeys(Keys.ENTER);
		}
		// Country
		dataInput = getData(testData.Store, EnumTestData.country);
//		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']/../..[not(text())]";
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		if(this.Store.equals("US")||this.Store.equals("USEPP")||this.Store.equals("US_BPCTO")){
			Actions action = new Actions(driver);
			Common.waitElementClickable(driver, hmcPage.B2CPriceRules_CountrySelect, 30);
			Thread.sleep(1000);
			hmcPage.B2CPriceRules_CountrySelect.click();
			Common.waitElementVisible(driver, hmcPage.B2CPriceRules_SearchInput);
			hmcPage.B2CPriceRules_SearchInput.clear();
			hmcPage.B2CPriceRules_SearchInput.sendKeys(dataInput);
			Common.sleep(2000);
			action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
		}else{
			fillRuleInfo(driver, hmcPage, "Country", dataInput, hmcPage.B2CPriceRules_CountrySelect, xpath);	
		}	
		// Catalog
		dataInput = "Nemo Master Multi Country Product Catalog - Online";
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		Common.sleep(2000);
		fillRuleInfo(driver, hmcPage, "Catalog", dataInput, hmcPage.B2CPriceRules_CatalogSelect, xpath);
		// Material
		xpath = "//span[contains(text(),'" + testProduct
				+ "')]/../../div[starts-with(text()[2],']') or not(text()[2])]";
		fillRuleInfo(driver, hmcPage, "Material", testProduct, hmcPage.B2CPriceRules_MaterialSelect, xpath);
		// B2Cunit
		dataInput = getData(testData.Store, EnumTestData.unit);
		if (this.Store.equals("AU")){
			xpath = "(//span[text()='" + dataInput + "']/../../*[not(text())])[last()]";
		}else if(this.Store.equals("JP")){
			xpath = "//ul[@class='content was-hidden']/../div/a[2]/span[text()='" + dataInput + "']";
		}else if(this.Store.equals("CA_AFFINITY")||this.Store.equals("US_BPCTO")){
			xpath = "//ul[@class='content was-hidden']/../div/a/span[text()='" + dataInput + "']";
		}
		else{
			xpath = "//span[text()='" + dataInput + "']/../../*[not(text())]";

		}
		Common.waitElementClickable(driver, hmcPage.B2CPriceRules_B2CunitSelect, 30);
		hmcPage.B2CPriceRules_B2CunitSelect.click();
		Common.waitElementVisible(driver, hmcPage.B2CPriceRules_MasterUnit);
		Common.waitElementVisible(driver, hmcPage.B2CPriceRules_UnitSearch);
		hmcPage.B2CPriceRules_UnitSearch.clear();
		hmcPage.B2CPriceRules_UnitSearch.sendKeys(dataInput);
		target = driver.findElement(By.xpath(xpath));
		Common.sleep(2000);
		Common.waitElementVisible(driver, target);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", target);
		System.out.println("B2Cunit: " + dataInput);
		Thread.sleep(2000);
		// Floor price Value
		hmcPage.B2CPriceRules_PriceValue.clear();
		hmcPage.B2CPriceRules_PriceValue.sendKeys(newPrice);
		System.out.println("Web Price:" + newPrice);
		Thread.sleep(2000);
		// Add Price Rule To Group
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_AddPriceRuleToGroup);
		//hmcPage.B2CPriceRules_AddPriceRuleToGroup.click();
		System.out.println("click Add Price Rule To Group --- first time");
		Thread.sleep(2000);
		
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_CreateGroup);
		//hmcPage.B2CPriceRules_CreateGroup.click();
		System.out.println("click Create New Group --- first time");
		Thread.sleep(1000);
		if (Common.isElementExist(driver,
				By.xpath("./[@data-validation='You need to add at least one Rule to create Group!']"))) {
			hmcPage.B2CPriceRules_AddPriceRuleToGroup.click();
			System.out.println("click Add Price Rule To Group --- second time");
			hmcPage.B2CPriceRules_CreateGroup.click();
			System.out.println("click Create New Group --- second time");
		}
		Thread.sleep(2000);
		// Record Price Rule ID
		if (Common.isElementExist(driver, By.xpath("//div[@class='modal-footer']//button[text()='Close']"))) {
			hmcPage.B2CPriceRules_CloseBtn.click();
			System.out.println("Clicked close!");
			Thread.sleep(3000);
		}
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_FilterBtn);
		// hmcPage.B2CPriceRules_FilterBtn.click();
		System.out.println("Clicked filter!");
		Thread.sleep(3000);
		WebElement showRuleID = driver.findElement(By.xpath("//td[text()='" + ruleName + "']/..//span[text()='Show']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", showRuleID);
		String priceRlueID = driver
				.findElement(By.xpath("(//td[text()='" + ruleName + "']/../../../..//tbody/tr/td[1])[2]")).getText();
		System.out.println("Price Rule ID is : " + priceRlueID);
		driver.switchTo().defaultContent();
		hmcPage.Home_PriceSettings.click();
		return priceRlueID;
	}
	
	public void ViewSavedCart() {
		driver.get(testData.B2C.getHomePageUrl()+"/my-account");
		b2cPage.MyAccount_ViewSavedCartHistory.click();
		Common.sleep(2000);	
		//   /[@id='cntTable']/tbody/tr[1]/td[2]
		Assert.assertTrue(driver.findElement(By.xpath("//td[contains(.,'" + "Save_" + CartName + "')]")).isDisplayed(),
				"saved cart is not present");
		//  /[@id='cntTable']/tbody/tr[1]/td[8]/a/div
		Common.sleep(2000);
		WebElement ele = driver.findElement(By.xpath("//td[contains(.,'" + "Save_" + CartName + "')]/../td/a/div"));
		Common.javascriptClick(driver, ele);
//		driver.findElement(By.xpath("//td[contains(.,'" + "Save_" + CartName + "')]/../td/a/div")).click();
		Common.sleep(3500);
	}
	
	public void fillRuleInfo(WebDriver driver, HMCPage hmcPage, String name, String dataInput, WebElement ele1,
			String xpath) throws InterruptedException {
		WebElement target;
		Common.waitElementClickable(driver, ele1, 30);
		Thread.sleep(1000);
		ele1.click();
		Common.waitElementVisible(driver, hmcPage.B2CPriceRules_SearchInput);
		hmcPage.B2CPriceRules_SearchInput.clear();
		hmcPage.B2CPriceRules_SearchInput.sendKeys(dataInput);
		target = driver.findElement(By.xpath(xpath));
		
		Common.waitElementVisible(driver, target);
		target.click();
		System.out.println(name + ": " + dataInput);
		Thread.sleep(5000);
	}
	
	public void loginB2CPage(){
		driver.manage().deleteAllCookies();
		driver.get(testData.B2C.getHomePageUrl());
		B2CCommon.handleGateKeeper(b2cPage, testData);
		driver.get(testData.B2C.getloginPageUrl());
		B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
		Common.sleep(2000);
		B2CCommon.handleGateKeeper(b2cPage, testData);
//		if(!driver.getCurrentUrl().contains("account")) {
//			driver.get(testData.B2C.getloginPageUrl());
//			Common.sleep(2500);
//			B2CCommon.handleGateKeeper(b2cPage, testData);
//			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
//		}
		Common.sleep(2000);
//		Assert.assertTrue(driver.getCurrentUrl().contains("my-account"));
	}
}