package TestScript.B2C;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.CTOandPB;
import Logger.Dailylog;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class SHOP33Test extends SuperTestClass{ 
	public HMCPage hmcPage;
	public String partNumber;
	public String country;
	public String unit;
	public String store;
	public String discountPrice = "100";
	public String productName;
	public String group;
	public float costPrice;
	public float floorprice;
	public float listprice;
	public float webprice;
	public String currency;
	
	public SHOP33Test (String store, String country, String unit, String group) {
		this.Store = store;
		this.country = country;
		this.unit = unit;
		this.group = group;
		this.testName = "SHOPE-221";
		
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup","pricingpromot","p2", "b2c"})
	public void SHOP33 (ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			String[] ruleType1= {"Floor","eCoupon Discounts","Instant Savings","Web","List Price Override"};
			System.out.println(group);
			if (group.equals("B2C")) {
				partNumber = "20HRS14W00";
				//partNumber = testData.B2C.getDefaultCTOPN();
				getDebugPrice(driver,hmcPage,country,Store,group,unit);
				for (int i =0;i< ruleType1.length;i++) {
					createRule(ruleType1[i],discountPrice,unit,group);
					checkResult(ruleType1[i]);
					Dailylog.logInfoDB(i, group+":"+ ruleType1[i] + " TEST IS PASSED", Store, testName);
					}
			}else if (group.equals("B2B")) {
				partNumber= "20FCS05500";
				//partNumber = testData.B2B.getDefaultMTMPN1();
				getDebugPrice(driver,hmcPage,country,Store,group,unit);
				createRule(ruleType1[0],discountPrice,unit,group);
				checkResult(ruleType1[0]);
				Dailylog.logInfoDB(0, group +":"+ ruleType1[0] + " TEST IS PASSED", Store, testName);
			}
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}
		
	}
	
	public void getDebugPrice(WebDriver driver,HMCPage hmcPage,String country,String store,String group,String unit) {
		String costprice;
		String floorPrice;
		String listPrice;
		String webPrice;
		String currency1;
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_PriceSettings.click();
		HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
		Common.sleep(2000);
		if (group.equals("B2C")) {
			HMCCommon.B2CPriceSimulateDebug(driver,hmcPage,"["+this.Store+ "] " +country,this.Store+ " "+"Web Store",
					"Nemo Master Multi Country Product Catalog - Online",partNumber);
			Common.sleep(2000);
		}else if (group.equals("B2B")) {
			B2BPriceSimulateDebug(driver,hmcPage,country,Store,
					"Nemo Master Multi Country Product Catalog - Online",partNumber,unit);
			Common.sleep(2000);
			
		}
		currency1 = driver.findElement(By.xpath("//span[@id='ps_total_currency']")).getText();
		currency = "Currency: "+ currency1;
		System.out.println("Debug currency is:" + currency1);
		productName = driver.findElement(By.xpath("//h2[@id='ps_result_product']")).getText();
		System.out.println("Debug product Nmae is:" + productName);
		costprice= driver.findElement(By.xpath("//td[@class='cost']/samp[@id='value']")).getText();
		listPrice= driver.findElement(By.xpath("//td[@class='list']/samp[@id='value']")).getText();
		floorPrice= driver.findElement(By.xpath("//td[@class='floor']/samp[@id='value']")).getText();
		webPrice= hmcPage.B2BpriceSimulate_webPrice.getText();
		Common.sleep(2000);
		costPrice = B2CCommon.GetPriceValue(costprice);
		System.out.println("Debug Cost Price is:" + costPrice);
		listprice =B2CCommon.GetPriceValue(listPrice);
		System.out.println("Debug List Price is:" + listPrice);
		webprice = B2CCommon.GetPriceValue(webPrice);
		System.out.println("Debug Web Price is:" + webPrice);
		floorprice = B2CCommon.GetPriceValue(floorPrice);
		System.out.println("Debug Floor Price is:" + floorPrice);
		driver.switchTo().defaultContent();
		hmcPage.hmcHome_hmcSignOut.click();

	}
	
	public void createRule(String ruleType, String discountPrice, String unit,String group) throws InterruptedException {
		WebElement target;
		String dataInput;
		String xpath;
		//String priceRlueID = null;
		System.out.println("Create rules***********************"+ ruleType);
		String ruleName = this.Store + ruleType.replace(" ", "");
		driver.get(testData.HMC.getHomePageUrl());

		if (Common.isElementExist(driver, By.id("Main_user"))) {
			HMCCommon.Login(hmcPage, testData);
			driver.navigate().refresh();
			
		}
		driver.navigate().refresh();
		Common.sleep(1000);
		hmcPage.Home_PriceSettings.click();
		Common.sleep(1000);
		hmcPage.Home_PricingCockpit.click();
		driver.switchTo().frame(0);

		// loginPricingCockpit();
		if (group.equals("B2C")) {
		hmcPage.PricingCockpit_B2CPriceRules.click();
		Thread.sleep(5000);
		Common.javascriptClick(driver, hmcPage.B2CPriceRules_CreateNewGroup);
		// hmcPage.B2CPriceRules_CreateNewGroup.click();
		Thread.sleep(3000);
		hmcPage.B2CPriceRules_SelectGroupType.click();
		Thread.sleep(3000);
		}else if (group.equals("B2B")) {
			// loginPricingCockpit();
			hmcPage.PricingCockpit_b2bPriceRules.click();
			Thread.sleep(5000);
			Common.javascriptClick(driver, hmcPage.B2CPriceRules_CreateNewGroup);
			// hmcPage.B2CPriceRules_CreateNewGroup.click();
			Thread.sleep(3000);
			hmcPage.B2CPriceRules_SelectGroupType.click();
			Thread.sleep(3000);
		}

		if (ruleType.equals("Instant Savings")) {
			hmcPage.B2CPriceRules_InstantSavingOption.click();
		} else if (ruleType.equals("Floor")) {
			hmcPage.B2CPriceRules_FloorPriceOption.click();
		} else if (ruleType.equals("Web")) {
			hmcPage.B2CPriceRules_WebPriceOption.click();
		} else if (ruleType.equals("eCoupon Discounts")) {
			hmcPage.B2CPriceRules_eCouponDiscountOption.click();
		}else if (ruleType.equals("List Price Override")) {
			driver.findElement(By.xpath("//ul/li/div[contains(.,'List Price Override')]")).click();
		}

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_Continue);

		// Floor price name
		hmcPage.B2CPriceRules_PriceRuleName.clear();
		hmcPage.B2CPriceRules_PriceRuleName.sendKeys(ruleName);

		// Validate from date
		hmcPage.B2CPriceRules_ValidFrom.click();
		Thread.sleep(1000);
		int count = driver.findElements(By.xpath("//td[contains(@class,'today')]/preceding-sibling::*")).size();
		WebElement yesterday;
		if (count > 0) {
			yesterday = driver.findElements(By.xpath("//td[contains(@class,'today')]/preceding-sibling::*")).get(count-1);
			System.out.println("Valid From: " + yesterday.getText());
			yesterday.click();
			hmcPage.B2CPriceRules_ValidFrom.sendKeys(Keys.ENTER);
		} else {
			yesterday = driver.findElements(By.xpath("//td[contains(@class,'today')]/../preceding-sibling::tr/td")).get(count-1);
			System.out.println("Valid From: " + yesterday.getText());
			yesterday.click();
			hmcPage.B2CPriceRules_ValidFrom.sendKeys(Keys.ENTER);
		}

		// Country
		dataInput = country;
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']/../../*[not(text())]";
		fillRuleInfo(driver, hmcPage, "Country", dataInput, hmcPage.B2CPriceRules_CountrySelect, xpath);

		// Catalog
		dataInput = "Nemo Master Multi Country Product Catalog - Online";
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		fillRuleInfo(driver, hmcPage, "Catalog", dataInput, hmcPage.B2CPriceRules_CatalogSelect, xpath);

		// B2Cunit
		if (group.equals("B2C")) {
		dataInput = unit;
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
		}else if (group.equals("B2B")) {
			// B2B unit
			dataInput = unit;
			Common.scrollToElement(driver,hmcPage.B2BPriceRules_B2BunitSelect);
			hmcPage.B2BPriceRules_B2BunitSelect.click();
			hmcPage.B2CPriceRules_SearchInput.clear();
			hmcPage.B2CPriceRules_SearchInput.sendKeys(dataInput);
			Common.sleep(2000);
			hmcPage.B2CPriceRules_SearchInput.sendKeys(Keys.ENTER);
			Common.sleep(2000);
			System.out.println("B2Bunit: " + dataInput);
			Thread.sleep(3000);
		}
		if (ruleType.equals("List Price Override")) {
			hmcPage.B2CPriceRules_PriceValue.clear();
			hmcPage.B2CPriceRules_PriceValue.sendKeys(discountPrice);
			Thread.sleep(2000);
		}else if (ruleType.equals("Instant Savings")) {
			hmcPage.B2CPriceRules_dynamicRate.click();
			hmcPage.B2CPriceRules_DynamicMinusButton.click();
			hmcPage.B2CPriceRules_ecouponDollorButton.click();
			hmcPage.B2CPriceRules_dynamicValue.clear();
			hmcPage.B2CPriceRules_dynamicValue.sendKeys(discountPrice);

		} else if (ruleType.equals("Floor")) {

			hmcPage.B2CPriceRules_PriceValue.clear();
			hmcPage.B2CPriceRules_PriceValue.sendKeys(discountPrice);
			Thread.sleep(2000);

		} else if (ruleType.equals("eCoupon Discounts")) {
			// display ecopon message
			//hmcPage.B2CPriceRules_ecouponMessageDisplay.click();
			driver.findElement(By.xpath("//div[@class='checkbox']/label[contains(text(),'Display CouponMessage')]")).click();
			hmcPage.B2CPriceRules_ecouponMessageEdit.click();
			// language
			String language;
			if ((this.Store).contains("_")) {
				language = "en_" + this.Store.split("_")[0];
			} else if (this.Store == "JP") {
				language = "ja_JP";
			} else if (this.Store == "AU") {
				language = "en";
			} else if (this.Store == "BR") {
				language = "pt";
			} else {
				language = "en_" + this.Store;
			}
			System.out.println(language);

			xpath = "//span[text()='" + language + "' and @class='select2-match']";
			fillRuleInfo(driver, hmcPage, "Language", language, hmcPage.B2CPriceRules_ChooseCountry, xpath);
			hmcPage.B2CPriceRules_ecouponMessageInput.clear();
			hmcPage.B2CPriceRules_ecouponMessageInput.sendKeys("<span style=\"color: #fdda97\">Valid through 10/31/2017</span>");
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_ecouponMessageAdd);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_ecouponMessageSave);
			// Priority
			hmcPage.B2CPriceRules_ecouponPriority.clear();
			hmcPage.B2CPriceRules_ecouponPriority.sendKeys("500");
			hmcPage.B2CPriceRules_dynamicRate.click();
			hmcPage.B2CPriceRules_DynamicMinusButton.click();
			hmcPage.B2CPriceRules_ecouponDollorButton.click();
			hmcPage.B2CPriceRules_dynamicValue.clear();
			hmcPage.B2CPriceRules_dynamicValue.sendKeys(discountPrice);
			Thread.sleep(2000);

		} else if (ruleType.equals("Web")) {
			hmcPage.B2CPriceRules_dynamicRate.click();
			hmcPage.B2CPriceRules_DynamicMinusButton.click();
			hmcPage.B2CPriceRules_ecouponDollorButton.click();
			hmcPage.B2CPriceRules_dynamicValue.clear();
			hmcPage.B2CPriceRules_dynamicValue.sendKeys(discountPrice);
			Thread.sleep(2000);

		}

		// Material
		xpath = "//span[contains(text(),'" + partNumber + "')]/../../div[starts-with(text()[2],']') or not(text()[2])]";
		fillRuleInfo(driver, hmcPage, "Material", partNumber, hmcPage.B2CPriceRules_MaterialSelect, xpath);
		
	} 
	
	public void fillRuleInfo(WebDriver driver, HMCPage hmcPage, String name, String dataInput, WebElement ele1, String xpath) throws InterruptedException {
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
	
	public void checkResult(String ruleType) {
		WebElement parentEle;
		String productNamexpath,currencyxpath,listpricexpath,webpricexpath,
		floorbeforexpath,floorafterxpaht,floorxpath,ecouponpricexpath;
		//check element xpath
		ecouponpricexpath = "//div[@class='price-simulate-content']/p[contains(text(),'coupon Price ')]/span";
		productNamexpath = "//div[@class='price-simulate-content']/p[1]/b";
		currencyxpath = "//div[@class='price-simulate-content']/p[2]";
		listpricexpath="//div[@class='price-simulate-content']/p[contains(text(),'list Price ')]/span";
		webpricexpath= "//div[@class='price-simulate-content']/p[contains(text(),'web Price ')]/span";
		floorbeforexpath = "//div[@class='price-simulate-content']/p[contains(text(),'floor Price ')]/span";
		floorafterxpaht = "//div[@class='price-simulate-content']/p[6]/span";
		floorxpath = "//div[@class='price-simulate-content']/p[contains(text(),'floor Price ')]/span";
		parentEle = driver.findElement(By.xpath("//div[@class='price-simulate-content']"));
		String salespricexpath = "//div[@class='price-simulate-content']/p[contains(text(),'sales Price ')]/span";
		String isfloorprice,islistprice,issalesprice,isproductname,iscurrency;
		float isfloorPrice,islistPrice,issalesPrice;
		//mouse move to price table
		Common.mouseHover(driver, parentEle);
		parentEle.click();
		Common.sleep(2000);
		
		if (ruleType.equals("Instant Savings")) {//check Instant Savings rule
			isfloorprice = driver.findElement(By.xpath(floorxpath)).getText().replaceAll("\\s*", "");
			isfloorPrice= B2CCommon.GetPriceValue(isfloorprice);
			System.out.println(ruleType + " Floor Price is:" + isfloorPrice);
			Assert.assertEquals(isfloorPrice, floorprice);
			islistprice = driver.findElement(By.xpath(listpricexpath)).getText().replaceAll("\\s*", "");
			islistPrice = B2CCommon.GetPriceValue(islistprice);
			System.out.println(ruleType + " List Price is:" + islistPrice);
			Assert.assertEquals(islistPrice, listprice);
			issalesprice = driver.findElement(By.xpath(salespricexpath)).getText().replaceAll("\\s*", "");
			issalesPrice = B2CCommon.GetPriceValue(issalesprice);
			System.out.println(ruleType + " Sales Price is:" + issalesPrice);
			Assert.assertEquals(issalesPrice,webprice - B2CCommon.GetPriceValue(discountPrice));
		
		}else if (ruleType.equals("Web")) {//check Web rule
			String iswebprice;
			float iswebPrice;
			isfloorprice = driver.findElement(By.xpath(floorxpath)).getText().replaceAll("\\s*", "");
			isfloorPrice= B2CCommon.GetPriceValue(isfloorprice);
			System.out.println(ruleType + " Floor Price is:" + isfloorPrice);
			Assert.assertEquals(isfloorPrice, floorprice);
			islistprice = driver.findElement(By.xpath(listpricexpath)).getText().replaceAll("\\s*", "");
			islistPrice = B2CCommon.GetPriceValue(islistprice);
			System.out.println(ruleType + " List Price is:" + islistPrice);
			Assert.assertEquals(islistPrice, listprice);
			iswebprice = driver.findElement(By.xpath(webpricexpath)).getText().replaceAll("\\s*", "");
			iswebPrice = B2CCommon.GetPriceValue(iswebprice);
			System.out.println(ruleType + " Web Price is:" + iswebPrice);
			Assert.assertEquals(iswebPrice,webprice - B2CCommon.GetPriceValue(discountPrice));
		
		}else if (ruleType.equals("Floor")) {//check Floor rule
			String iswebprice,isfloorafter;
			float iswebPrice, isfloorAfter;
			isfloorprice = driver.findElement(By.xpath(floorbeforexpath)).getText().replaceAll("\\s*", "").substring(7);
			isfloorPrice= B2CCommon.GetPriceValue(isfloorprice);
			System.out.println(ruleType + " Before Floor Price is:" + isfloorPrice);
			Assert.assertEquals(isfloorPrice, floorprice);
			isfloorafter = driver.findElement(By.xpath(floorafterxpaht)).getText().replaceAll("\\s*", "").substring(6);
			isfloorAfter= B2CCommon.GetPriceValue(isfloorafter);
			System.out.println(ruleType + " After Floor Price is:" + isfloorAfter);
			Assert.assertEquals(isfloorAfter, B2CCommon.GetPriceValue(discountPrice));
			islistprice = driver.findElement(By.xpath(listpricexpath)).getText().replaceAll("\\s*", "");
			islistPrice = B2CCommon.GetPriceValue(islistprice);
			System.out.println(ruleType + " List Price is:" + islistPrice);
			Assert.assertEquals(islistPrice, listprice);
			iswebprice = driver.findElement(By.xpath(webpricexpath)).getText().replaceAll("\\s*", "");
			iswebPrice = B2CCommon.GetPriceValue(iswebprice);
			System.out.println(ruleType + " Web Price is:" + iswebPrice);
			Assert.assertEquals(iswebPrice, webprice);
		
		}else if(ruleType.equals("List Price Override")) {//check List Price rule
			isfloorprice = driver.findElement(By.xpath(floorxpath)).getText().replaceAll("\\s*", "");
			isfloorPrice= B2CCommon.GetPriceValue(isfloorprice);
			System.out.println(ruleType + " Floor Price is:" + isfloorPrice);
			Assert.assertEquals(isfloorPrice, floorprice);
			islistprice = driver.findElement(By.xpath(listpricexpath)).getText().replaceAll("\\s*", "");
			islistPrice = B2CCommon.GetPriceValue(islistprice);
			System.out.println(ruleType + " List Price is:" + islistPrice);
			Assert.assertEquals(islistPrice, B2CCommon.GetPriceValue(discountPrice));
			
		}else if (ruleType.equals("eCoupon Discounts")) {//check eCoupon rule
			String isecouponprice;
			float isecouponPrice;
			isecouponprice = driver.findElement(By.xpath(ecouponpricexpath)).getText().replaceAll("\\s*", "");
			isecouponPrice = B2CCommon.GetPriceValue(isecouponprice);
			System.out.println(ruleType + " eCoupon Price is:" + isecouponPrice);
			Assert.assertEquals(isecouponPrice, webprice -B2CCommon.GetPriceValue(discountPrice));
			isfloorprice = driver.findElement(By.xpath(floorxpath)).getText().replaceAll("\\s*", "");
			isfloorPrice= B2CCommon.GetPriceValue(isfloorprice);
			System.out.println(ruleType + " Floor Price is:" + isfloorPrice);
			Assert.assertEquals(isfloorPrice, floorprice);
			islistprice = driver.findElement(By.xpath(listpricexpath)).getText().replaceAll("\\s*", "");
			islistPrice = B2CCommon.GetPriceValue(islistprice);
			System.out.println(ruleType + " List Price is:" + islistPrice);
			Assert.assertEquals(islistPrice, listprice);
			
		}
		//check Product Name and Currency
		isproductname  = driver.findElement(By.xpath(productNamexpath)).getText();
		System.out.println(ruleType + " Product name is:" + isproductname);
		Assert.assertTrue(productName.contains(isproductname), "The product name is incorrect!");
		iscurrency = driver.findElement(By.xpath(currencyxpath)).getText();
		System.out.println(ruleType + " " + iscurrency);
		Assert.assertEquals(iscurrency, currency);
		System.out.println("******************"+ruleType + " Test is PASSED!"+ "*************");
		logOff();
		
	}
	
	public void logOff() {
		/**
		 * Log off HMC
		 */
		driver.findElement(By.xpath("//button[@class='btn btn-default']")).click();
		driver.switchTo().defaultContent();
		hmcPage.hmcHome_hmcSignOut.click();
	}


	public static void fillB2CPriceRuleDetails(WebDriver driver,
			WebElement DDLocator, WebElement txtBoxLocator, String value,
			int waitTime) throws InterruptedException {
		Common.waitElementClickable(driver, DDLocator, 30);
		Thread.sleep(1000);
		DDLocator.click();
		Common.waitElementVisible(driver, txtBoxLocator);
		txtBoxLocator.sendKeys(value);
		Thread.sleep(waitTime);
		txtBoxLocator.sendKeys(Keys.ENTER);
		Common.sleep(1500);

}

	
	public static void B2BPriceSimulateDebug(WebDriver driver,HMCPage hmcPage,String country,
			String store,String catalog,String partNumber,String unit,String... smbPriceTier){	
		driver.findElement(By.xpath("//div[@class='links b2b-links']/a[contains(@href,'priceSimulate')]")).click();
		//hmcPage.PricingCockpit_B2CPriceSimulator.click();
		Common.sleep(3000);
		try{
			fillB2CPriceRuleDetails(driver,hmcPage.B2BpriceSimulate_CountryButton,hmcPage.B2CPriceRules_SearchInput,country,10);
			Common.sleep(5000);
			fillB2CPriceRuleDetails(driver,hmcPage.B2BpriceSimulate_StoreButton,hmcPage.B2CPriceRules_SearchInput,country + " LE Store",10);
			Common.sleep(1000);
			fillB2CPriceRuleDetails(driver,hmcPage.B2BpriceSimulate_B2BUnitButton,hmcPage.B2CPriceRules_SearchInput,unit,10);
			Common.sleep(1000);
			if(Common.checkElementDisplays(driver, hmcPage.B2BpriceSimulate_SMBPriceTierButton, 10)){
				Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_SMBPriceTierButton, 30);
				Thread.sleep(1000);
				hmcPage.B2BpriceSimulate_SMBPriceTierButton.click();
				Common.waitElementVisible(driver, hmcPage.B2CPriceRules_SearchInput);
				hmcPage.B2CPriceRules_SearchInput.sendKeys(smbPriceTier);
				hmcPage.B2CPriceRules_SearchInput.sendKeys(Keys.ENTER);
			}
			fillB2CPriceRuleDetails(driver,hmcPage.B2CPriceSimulator_catalogVersion,hmcPage.B2CPriceRules_SearchInput,catalog,10);
			Common.waitElementClickable(driver, hmcPage.B2CPriceSimulator_priceDate, 12);
			hmcPage.B2CPriceSimulator_priceDate.click();
			hmcPage.B2CPriceSimulator_priceDate.sendKeys(Keys.ENTER);
			Common.sleep(3000);
			CommonFunction.Common.scrollToElement(driver, hmcPage.B2CPriceSimulator_product);
			Common.waitElementClickable(driver, hmcPage.B2CPriceSimulator_product, 120);
			hmcPage.B2CPriceSimulator_product.click();
			Common.waitElementClickable(driver, hmcPage.B2CPriceRules_SearchInput, 3);
			hmcPage.B2CPriceRules_SearchInput.sendKeys(partNumber); 
			WebElement productResult = driver.findElement(By.xpath("//span[text()='" + partNumber + "']"));
			Common.waitElementVisible(driver, productResult);
			productResult.click();
			Common.sleep(1500);
			hmcPage.B2CPriceSimulator_debug.click();
			
		}catch(Exception e){
			e.printStackTrace();
		}	
		Common.sleep(5000); 		
	}

}