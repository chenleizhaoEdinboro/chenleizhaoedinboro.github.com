package TestScript.B2C;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.AfterTest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA15492Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	private String MTMUrl;
	private String CTOUrl;
	private String testProduct;
	private String priceRlueID = "";
	private String ruleName = "";
	private String allRuleName = "";
	By emptyCart = By.xpath(
			"//*[contains(text(),'Empty cart') or contains(text(),'カートを空にする') or contains(@alt,'カートを空にする') or contains(@alt,'Empty cart')]");
	By confirmEmpty = By.xpath("//input[contains(@onclick, '.submit')]");
	// private String hmcCookieName = "JSESSIONID";

	public NA15492Test(String store, String mtmUrl, String ctoUrl) {
		this.Store = store;
		this.MTMUrl = mtmUrl;
		this.CTOUrl = ctoUrl;
		this.testName = "NA-15492";

	}

	private enum EnumTestData {
		mtmPro, ctoPro, country, unit, errMsg;
	}

	private String getData(String store, EnumTestData dataName) {
		if (store.equals("AU")) {
			switch (dataName) {
			case mtmPro:
				return "ZA0V0015AU";
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
				return "80V1000BUK";
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
				return "20KH002QUS";
			case ctoPro:
				return "20KFCTO1WWENCA0";
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
				return "80Y7006JCF";
			case ctoPro:
				return "80Y7CTO1WWENCA1";
			case country:
				return "Canada";
			case unit:
				return "Affinity_CA";
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
				return "80WK002TJE";
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
				return "ZA0V0002US";
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
				return "ZA0V0091US";
			case ctoPro:
				return "10EUCTO1WWENUS0";
			case country:
				return "United States";
			case unit:
				return "Softmart Commercial Services unit";
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
				return "ZA0V0002US";
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

	@Test(priority = 0, alwaysRun = true, enabled = true,groups={"shopgroup","pricingpromot","p1","b2c"})
	public void NA15492(ITestContext ctx) throws InterruptedException, MalformedURLException {
		this.prepareTest();
		b2cPage = new B2CPage(driver);
		hmcPage = new HMCPage(driver);
		
		driver.get(testData.B2C.getHomePageUrl()+"/p/"+testData.B2C.getDefaultMTMPN());
		if(!Common.checkElementDisplays(driver, By.xpath("//button[@id='addToCartButtonTop' and not(@disabled)]"), 120)){
			testProduct= getData(testData.Store, EnumTestData.mtmPro);
		}else{
			testProduct=testData.B2C.getDefaultMTMPN();
		}
		System.out.println(testProduct);
		// testProduct = testData.B2C.getDefaultMTMPN();
		ruleName = "FP_" + Store + Common.getDateTimeString();
		allRuleName = "FP_" + Store;

		// Open B2C and HMC
		driver.get(testData.B2C.getHomePageUrl());
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("window.open('" + testData.HMC.getHomePageUrl() + "')");

		// MTM Floor Price Rule
		try {
			String[] activeRule;
			// add MTM product into cart
			B2CCommon.handleGateKeeper(b2cPage, testData);
			driver.get(testData.B2C.getHomePageUrl() + "/cart");
			if (!quickOrder(b2cPage, testProduct))
				testProduct = addProductToCart(MTMUrl, "MTM");

			// delete floor price rule if already exists
			switchToWindow(driver, 1);

			activeRule = checkRule(testProduct);
			if (!activeRule[0].equals(""))
				deleteRule(driver, hmcPage, activeRule[0]);
			if (!activeRule[1].equals(""))
				deleteRule(driver, hmcPage, activeRule[1]);

			// create floor price rule
			priceRlueID = createRule(testProduct, ruleName);

			// Approve Floor Price Rule in HMC
			approveRule(priceRlueID);

			// rediscacheclean and price cache clean for AU
			if (Store.equals("AU")) {
				rediscacheclean(driver, hmcPage, testProduct);
				startCronJob(driver, hmcPage, "NemoClusterClearPriceCacheCronJob", 2);
			}

			// Switch back to B2C
			switchToWindow(driver, 0);
			driver.navigate().refresh();
			Thread.sleep(8000);

			Assert.assertEquals(b2cPage.Cart_ErrorMsg.getText(), getData(testData.Store, EnumTestData.errMsg),
					"MTM floor price error msg in cart");

			Assert.assertEquals(b2cPage.Cart_CheckoutDisable.getAttribute("disabled"), "true",
					"MTM checkout button should be disabled");

			// empty cart
			if (Common.isElementExist(driver, emptyCart)) {
				driver.findElement(emptyCart).click();
			}
			Common.sleep(1000);
			if (Common.isElementExist(driver, confirmEmpty)) {
				driver.findElement(confirmEmpty).click();
			}	

			// CTO floor price rule
			System.out.println("CTO Floor price rule...");
			// ruleName = "FP_" + Common.getDateTimeString();
			
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+testData.B2C.getDefaultCTOPN());
			if(!Common.checkElementDisplays(driver, By.xpath("//button[@id='addToCartButtonTop' and not(@disabled)]"), 120)){
				testProduct = getData(testData.Store, EnumTestData.ctoPro);
			}else{
				testProduct=testData.B2C.getDefaultCTOPN();
			}
			System.out.println(testProduct);
			// testProduct = testData.B2C.getDefaultCTOPN();
			// B2C
			switchToWindow(driver, 0);
			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			driver.get(testData.B2C.getHomePageUrl() + "/p/" + testProduct);
			Thread.sleep(1000);
			if (isAlertPresent()) {
				// find product when unexpected alert
				System.out.println(driver.switchTo().alert().getText());
				driver.switchTo().alert().accept();
				testProduct = addProductToCart(CTOUrl, "CTO");
			} else if (Common.isElementExist(driver, By.xpath(
					"//div[@class='alert negative'and contains(text(),'404') or contains(text(),'not exist')]"))) {
				// find product when product not exist
				testProduct = addProductToCart(CTOUrl, "CTO");
			} else {
				WebElement customizeNew = driver.findElement(
						By.xpath("//form[contains(@action,'" + testProduct + "')]//button[@id='addToCartButtonTop']"));
				Common.waitElementClickable(driver, customizeNew, 30);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", customizeNew);
				Thread.sleep(5000);
				Common.waitElementClickable(driver, b2cPage.Product_AddToCartBtn, 30);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_AddToCartBtn);
				Thread.sleep(5000);
				driver.get(testData.B2C.getHomePageUrl() + "/cart");
				Thread.sleep(1000);
				// find product when not added successfully
				List<WebElement> cartItems = driver.findElements(By.xpath("//div[@class='cart-item']"));
				if (cartItems.size() == 0) {
					testProduct = addProductToCart(CTOUrl, "CTO");
				}
			}

			// HMC: delete floor price rule and web price rule if already exists
			switchToWindow(driver, 1);
			activeRule = checkRule(testProduct);
			if (!activeRule[0].equals(""))
				deleteRule(driver, hmcPage, activeRule[0]);
			if (!activeRule[1].equals(""))
				deleteRule(driver, hmcPage, activeRule[1]);

			// create floor price rule
			priceRlueID = createRule(testProduct, ruleName + "1");

			// Approve Floor Price Rule in HMC
			approveRule(priceRlueID);

			// rediscacheclean and price cache clean for AU
			if (Store.equals("AU")) {
				rediscacheclean(driver, hmcPage, testProduct);
				startCronJob(driver, hmcPage, "NemoClusterClearPriceCacheCronJob", 2);
			}

			// Switch back to B2C
			switchToWindow(driver, 0);
			driver.navigate().refresh();
			Thread.sleep(5000);
			Assert.assertEquals(b2cPage.Cart_ErrorMsg.getText(), getData(testData.Store, EnumTestData.errMsg),
					"CTO floor price error msg in cart");
			Assert.assertEquals(b2cPage.Cart_CheckoutDisable.getAttribute("disabled"), "true",
					"CTO checkout button should be disabled");

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	@Test(priority = 1, alwaysRun = true, enabled = true,groups={"shopgroup","pricingpromot","p1","b2c"})
	public void rollBack(ITestContext ctx) throws InterruptedException {
		try {
			allRuleName = "FP_" + Store;
			System.out.println("rollback"); // roll back
			SetupBrowser();
			hmcPage = new HMCPage(driver);
			try {
				deleteRule(driver, hmcPage, allRuleName);
			} catch (WebDriverException e) {
				System.out.println(e.getMessage());
				deleteRule(driver, hmcPage, allRuleName);
			}

			// rediscacheclean and price cache clean for AU
			if (Store.equals("AU")) {
				rediscacheclean(driver, hmcPage, testProduct);
				startCronJob(driver, hmcPage, "NemoClusterClearPriceCacheCronJob", 1);
			}

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public String createRule(String testProduct, String ruleName) throws InterruptedException {
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
//		new WebDriverWait(driver, 10)
//				.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//tr[@class='loader']")));
		Thread.sleep(5000);
		hmcPage.B2CPriceRules_CreateNewGroup.click();
		Thread.sleep(3000);
		hmcPage.B2CPriceRules_SelectGroupType.click();
		Thread.sleep(3000);
		hmcPage.B2CPriceRules_FloorPriceOption.click();
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
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']/../../*[not(text())]";
		fillRuleInfo(driver, hmcPage, "Country", dataInput, hmcPage.B2CPriceRules_CountrySelect, xpath);

		// Catalog
		dataInput = "Nemo Master Multi Country Product Catalog - Online";
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		fillRuleInfo(driver, hmcPage, "Catalog", dataInput, hmcPage.B2CPriceRules_CatalogSelect, xpath);

		// Material
		xpath = "//span[contains(text(),'" + testProduct
				+ "')]/../../div[starts-with(text()[2],']') or not(text()[2])]";
		fillRuleInfo(driver, hmcPage, "Material", testProduct, hmcPage.B2CPriceRules_MaterialSelect, xpath);

		// B2Cunit
		dataInput = getData(testData.Store, EnumTestData.unit);
		if (this.Store.equals("AU"))
			xpath = "(//span[text()='" + dataInput + "']/../../*[not(text())])[last()]";
		else
			xpath = "//span[text()='" + dataInput + "']/../../*[not(text())]";
		Common.waitElementClickable(driver, hmcPage.B2CPriceRules_B2CunitSelect, 30);
		hmcPage.B2CPriceRules_B2CunitSelect.click();
		Common.waitElementVisible(driver, hmcPage.B2CPriceRules_MasterUnit);
		Common.waitElementVisible(driver, hmcPage.B2CPriceRules_UnitSearch);
		hmcPage.B2CPriceRules_UnitSearch.clear();
		hmcPage.B2CPriceRules_UnitSearch.sendKeys(dataInput);
		target = driver.findElement(By.xpath(xpath));
		Common.doubleClick(driver, target);
		System.out.println("B2Cunit: " + dataInput);
		Thread.sleep(5000);

		// Floor price Value
		dataInput = "5000000";
		hmcPage.B2CPriceRules_PriceValue.clear();
		hmcPage.B2CPriceRules_PriceValue.sendKeys(dataInput);
		System.out.println("Floor Price:" + dataInput);
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
				By.xpath(".//*[@data-validation='You need to add at least one Rule to create Group!']"))) {
			hmcPage.B2CPriceRules_AddPriceRuleToGroup.click();
			System.out.println("click Add Price Rule To Group --- second time");
			hmcPage.B2CPriceRules_CreateGroup.click();
			System.out.println("click Create New Group --- second time");
		}
		Thread.sleep(10000);
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

	public void approveRule(String ruleID) throws InterruptedException {
		System.out.println("Approve rules***********************");
		driver.switchTo().defaultContent();
		hmcPage.Home_System.click();
		hmcPage.B2CPriceRules_Types.click();
		hmcPage.Types_Identifier.clear();
		hmcPage.Types_Identifier.sendKeys("PriceB2CRule");
		hmcPage.Types_Search.click();
		Common.doubleClick(driver, hmcPage.Types_ResultPriceB2CRule);
		Common.waitElementVisible(driver, hmcPage.Types_OpenEditorNewWindow);
		hmcPage.Types_OpenEditorNewWindow.click();
		switchToWindow(driver, 2);
		Common.waitElementVisible(driver, hmcPage.Types_OpenOrganizer);
		hmcPage.Types_OpenOrganizer.click();
		switchToWindow(driver, 3);
		hmcPage.Types_SearchValueInput.clear();
		hmcPage.Types_SearchValueInput.sendKeys(ruleID);
		hmcPage.Types_Search.click();
		Thread.sleep(5000);
		WebElement target = driver.findElement(By.xpath("(//div[contains(text(),'" + ruleID + "')])[1]"));
		Common.doubleClick(driver, target);
		hmcPage.Types_PriceB2CRuleStatus.clear();
		hmcPage.Types_PriceB2CRuleStatus.sendKeys("1");
		Thread.sleep(5000);
		hmcPage.Types_SaveBtn.click();
		System.out.println("Floor Price Rule is approved! Rule ID: " + ruleID);
		driver.close();
		Thread.sleep(500);
		switchToWindow(driver, 2);
		driver.close();
		Thread.sleep(500);
		switchToWindow(driver, 1);
		hmcPage.Home_System.click();
	}

	public void switchToWindow(WebDriver driver, int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
			WebDriverWait wait = new WebDriverWait(driver, 20);// seconds
			if (windowNo != 0)
				wait.until(ExpectedConditions.numberOfWindowsToBe(windowNo + 1));
			driver.switchTo().window(windows.get(windowNo));
		} catch (TimeoutException e) {
			System.out.println("Swith to window timeout, window: " + windowNo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void deleteRule(WebDriver driver, HMCPage hmcPage, String ruleGroup) throws InterruptedException {
		String dataInput;
		String xpath;

		System.out.println("Delete rule: " + ruleGroup + "***********************");
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

		// rule type
		Thread.sleep(8000);
		dataInput = "Floor Prices";
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		fillRuleInfo(driver, hmcPage, "Rule Type", dataInput, hmcPage.B2CPriceRules_ruleType, xpath);

		// group
		dataInput = ruleGroup;
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		while (true) {
			Thread.sleep(1000);
			Common.waitElementClickable(driver, hmcPage.B2CPriceRules_group, 30);
			hmcPage.B2CPriceRules_group.click();
			// hmcPage.B2CPriceRules_group.click();
			Common.waitElementVisible(driver, hmcPage.B2CPriceRules_SearchInput);
			hmcPage.B2CPriceRules_SearchInput.clear();
			hmcPage.B2CPriceRules_SearchInput.sendKeys(dataInput);
			int count = driver.findElements(By.xpath(xpath)).size();
			System.out.println("floor price rule count: " + count);
			if (count != 0) {
				List<WebElement> rules = driver.findElements(By.xpath(xpath));
				rules.get(0).click();
				// filter
				hmcPage.B2CPriceRules_FilterBtn.click();
				// delete
				WebElement deleteBtn = driver.findElement(
						By.xpath("//td[contains(text(),'" + ruleGroup + "')]/..//a[contains(text(),'Delete')]"));
				Common.waitElementClickable(driver, deleteBtn, 30);
				deleteBtn.click();
				Common.waitElementVisible(driver, hmcPage.B2CPriceRules_deleteInput);
				hmcPage.B2CPriceRules_deleteInput.clear();
				hmcPage.B2CPriceRules_deleteInput.sendKeys("DELETE");
				
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_deleteConfirm);
				//hmcPage.B2CPriceRules_deleteConfirm.click();
				System.out.println("Rule Deleted!");
				// clear all
				Thread.sleep(5000);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_clearAll);
				Thread.sleep(5000);
			}

			if (count == 1 || count == 0)
				break;

		}
		driver.switchTo().defaultContent();
		hmcPage.Home_PriceSettings.click();

	}

	public void loginPricingCockpit() {
		if (Common.isElementExist(driver, By.xpath("//div[1]/input[@name='j_username']"))) {
			hmcPage.PricingCockpit_username.clear();
			hmcPage.PricingCockpit_username.sendKeys(testData.HMC.getLoginId());
			hmcPage.PricingCockpit_password.clear();
			hmcPage.PricingCockpit_password.sendKeys(testData.HMC.getPassword());
			hmcPage.PricingCockpit_Login.click();
		}

	}

	public String addProductToCart(String testUrl, String productType) throws InterruptedException {
		System.out.println("Find " + productType + "***********************");
		String validProduct = "";
		driver.get(testData.B2C.getHomePageUrl() + testUrl);

		// Portrait Style: view series button
		List<WebElement> series = driver.findElements(By.xpath("//*[@class='brandListings-footer']/a"));
		System.out.println("series: " + series.size());

		// Landscape Style:US_OUTLET,SG
		List<WebElement> facetedResults = driver.findElements(By.xpath("//*[@class='facetedResults-footer']/a"));
		System.out.println("facetedResults: " + facetedResults.size());

		// if portrait style: find product
		if (facetedResults.size() == 0 || series.size() > 0) {
			System.out.println("Portrait_View Series: " + series.size());
			for (int i = -1; i < series.size(); i++) {
				// if view series exists, click 'view series'
				if (series.size() != 0) {
					series = driver.findElements(By.xpath("//*[@class='brandListings-footer']/a"));
					series.get(i + 1).click();
				}

				// click learn more
				List<WebElement> learnmore = driver.findElements(By.xpath("//*[@class='seriesListings-footer']/a"));
				System.out.println("learnmore number: " + learnmore.size());
				String thirdLevel = driver.getCurrentUrl();
				for (int j = 0; j < learnmore.size(); j++) {
					learnmore = driver.findElements(By.xpath("//*[@class='seriesListings-footer']/a"));
					learnmore.get(j).click();

					// click add to cart(MTM) or customize(CTO)
					List<WebElement> acAddToCart = driver.findElements(By.xpath(
							"//*[contains(@class,'productListing')]//form//*[@id='addToCartButtonTop' and not(@disabled)]"));
					System.out.println("active add to cart number: " + acAddToCart.size());
					// fourthLevel -- after clicking learn more
					String fourthLevel = driver.getCurrentUrl();
					for (int x = 0; x < acAddToCart.size(); x++) {
						acAddToCart = driver.findElements(By.xpath(
								"//*[contains(@class,'productListing')]//form//*[@id='addToCartButtonTop' and not(@disabled)]"));
						((JavascriptExecutor) driver).executeScript("arguments[0].click();", acAddToCart.get(x));
						Thread.sleep(1000);

						// CTO: remember product id
						if (productType.toUpperCase().equals("CTO")) {
							if (isAlertPresent()) {
								System.out.println(driver.switchTo().alert().getText());
								driver.switchTo().alert().accept();
							} else {
								String url = driver.getCurrentUrl();
								validProduct = url.substring(url.lastIndexOf("p/") + 2, url.lastIndexOf("p/") + 17);
								System.out.println("CTO: " + validProduct);

								// CTO: try to click 'add to cart'
								if (Common.isElementExist(driver, By.id("CTO_addToCart"))) {
									if (b2cPage.Product_AddToCartBtn.isDisplayed()) {
										((JavascriptExecutor) driver).executeScript("arguments[0].click();",
												b2cPage.Product_AddToCartBtn);
										Thread.sleep(5000);
									}
								}
							}
						}

						// go to cart
						driver.get(testData.B2C.getHomePageUrl() + "/cart");
						List<WebElement> cartItems = driver.findElements(By.xpath("//div[@class='cart-item']"));
						Thread.sleep(1000);

						// if product exists in cart
						if (cartItems.size() >= 1) {
							// MTM: get product id
							if (productType.toUpperCase().equals("MTM")) {
								validProduct = driver
										.findElement(By.xpath("(//*[contains(@class,'cart-item-partNumber')]/span[not(@class)])[last()]"))
										.getText();
								System.out.println("MTM: " + validProduct);
							}

							// break if product type is added correctly
							if (productType.toUpperCase().equals("MTM") && validProduct.indexOf("CTO") < 0) {
								break;
							} else if (productType.toUpperCase().equals("CTO") && validProduct.indexOf("CTO") > 0) {
								break;
							} else {
								// empty cart if wrong product type is added
								if (Common.isElementExist(driver, emptyCart)) {
									driver.findElement(emptyCart).click();
								}
								Common.sleep(1000);
								if (Common.isElementExist(driver, confirmEmpty)) {
									driver.findElement(confirmEmpty).click();
								}	
								// try to add next product in fourthLevel
								driver.get(fourthLevel);
							}
						} else {
							// if item is not added, try to add next product in
							// fourthLevel
							driver.get(fourthLevel);
						}
					}

					if (driver.getCurrentUrl().indexOf("cart") > 0) {
						// break if product type is added correctly
						break;
					} else {
						// back to thirdLevel to check next fourthLevel
						driver.get(thirdLevel);
					}
				}

				if (driver.getCurrentUrl().indexOf("cart") > 0) {
					// break if product type is added correctly
					break;
				} else {
					// back to testUrl to check next thirdLevel
					driver.get(testData.B2C.getHomePageUrl() + testUrl);
				}
			}
		} else if (facetedResults.size() > 0) {
			// if landscape view
			System.out.println("Landscape_facetedResults: " + facetedResults.size());
			Assert.fail("Test product is invalid, please update test product!");
			// for (int i = 0; i < facetedResults.size(); i++) {
			// facetedResults = driver.findElements(By
			// .xpath("//*[@class='facetedResults-footer']/a"));
			// facetedResults.get(i).click();
			// }
		}

		if (validProduct.equals("")) {
			Assert.fail("Test url is invalid, please update test product!");
		}

		return validProduct;
	}

	public boolean quickOrder(B2CPage b2cPage, String partNum) throws InterruptedException {
		b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
		b2cPage.Cart_AddButton.click();
		try {
			Common.waitElementClickable(b2cPage.PageDriver,
					b2cPage.PageDriver.findElement(By.xpath("//div[@data-p-code='" + partNum + "']")), 5);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public String[] checkRule(String product) throws InterruptedException {
		String floorRule = "";
		String webRule = "";
		String dataInput;
		String xpath;

		System.out.println("Check rules***********************");
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
		hmcPage.PricingCockpit_B2CPriceSimulator.click();
		Thread.sleep(5000);

		// Country
		dataInput = "[" + testData.Store.substring(0, 2).toUpperCase() + "] "
				+ getData(testData.Store, EnumTestData.country);
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']/../../*[not(text())]";
		fillRuleInfo(driver, hmcPage, "Country", dataInput, hmcPage.B2CPriceSimulator_country, xpath);

		// B2C store
		dataInput = "[" + testData.B2C.getStore() + "]";
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		fillRuleInfo(driver, hmcPage, "Catalog", dataInput, hmcPage.B2CPriceSimulator_store, xpath);

		// Catalog
		dataInput = "Nemo Master Multi Country Product Catalog - Online";
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		fillRuleInfo(driver, hmcPage, "Catalog", dataInput, hmcPage.B2CPriceSimulator_catalogVersion, xpath);

		// Date
		hmcPage.B2CPriceSimulator_priceDate.sendKeys(Keys.ENTER);

		// Product
		dataInput = testProduct;
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		fillRuleInfo(driver, hmcPage, "Product", dataInput, hmcPage.B2CPriceSimulator_product, xpath);

		// Debug
		hmcPage.B2CPriceSimulator_debug.click();

		Common.waitElementVisible(driver, hmcPage.B2CPriceSimulator_simulatorResults);

		// Get floor priceRule
		try {
			floorRule = hmcPage.B2CPriceSimulator_floorGroup.getText();
			System.out.println(floorRule);
		} catch (NoSuchElementException e) {
			System.out.println("No active floor price rule: " + product);
		}

		// Get web price rule
		try {
			webRule = hmcPage.B2CPriceSimulator_webGroup.getText();
			System.out.println(webRule);
		} catch (NoSuchElementException e) {
			System.out.println("No active web price rule: " + product);
		}

		driver.switchTo().defaultContent();
		hmcPage.Home_PriceSettings.click();

		String[] rules = new String[] { floorRule, webRule };
		return rules;
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

	public void rediscacheclean(WebDriver driver, HMCPage hmcPage, String testProduct) throws InterruptedException {
		System.out.println("rediscacheclean start");
		hmcPage.Home_System.click();
		hmcPage.Home_rediscacheclean.click();
		driver.switchTo().frame(0);
		hmcPage.Rediscacheclean_productCode.sendKeys(testProduct);
		hmcPage.Rediscacheclean_clean.click();
		// System.out.println("Cleaned the product cache.");
		Thread.sleep(10000);
		driver.switchTo().alert().accept();
		driver.switchTo().defaultContent();
		hmcPage.Home_System.click();
		System.out.println("rediscacheclean end");
	}

	public void startCronJob(WebDriver driver, HMCPage hmcPage, String jobName, int windowSize)
			throws InterruptedException {
		System.out.println("CronJob start");
		// naviagting to System > CronJob > singleCouponJob
		hmcPage.Home_System.click();
		hmcPage.Home_cronJob.click();
		if (!Common.isElementExist(driver, By.xpath("//a/span[contains(.,'Search')]"))) {
			driver.findElement(By.xpath("(//*[contains(@id,'[organizersearch][CronJob]')])[1]")).click();
			driver.findElement(By.xpath("(//*[contains(@id,'[organizerlist][CronJob]')])[1]")).click();
		}
		// Jobname
		hmcPage.CronJob_jobName.clear();
		hmcPage.CronJob_jobName.sendKeys(jobName);
		hmcPage.CronJob_searchButton.click();
		// Selecting the Job From Search Results
		Common.waitElementVisible(driver, driver.findElement(By.xpath("//div[text()='" + jobName + "']")));
		Common.doubleClick(driver, driver.findElement(By.xpath("//div[text()='" + jobName + "']")));
		// Start CronJob
		hmcPage.CronJob_startCronJob.click();
		Thread.sleep(5000);
		switchToWindow(driver, windowSize);
		Assert.assertEquals(hmcPage.CronJob_cronJobSuccessMsg.getText(), "CronJob performed.");
		driver.close();
		switchToWindow(driver, windowSize - 1);
		hmcPage.Home_System.click();
		// // Checking Job Status
		// hmcPage.CronJob_taskTab.click();
		// hmcPage.CronJob_reloadButton.click();
		// Thread.sleep(1000);
		// driver.switchTo().alert().accept();
		// Thread.sleep(3000);
		// Assert.assertEquals(hmcPage.CronJob_status, "FINISHED");
		System.out.println("CronJob end");
	}

}
