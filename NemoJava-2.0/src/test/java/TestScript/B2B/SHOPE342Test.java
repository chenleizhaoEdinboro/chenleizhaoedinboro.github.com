package TestScript.B2B;

import static org.testng.Assert.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import CommonFunction.B2BCommon;
import CommonFunction.B2CCommon;
import CommonFunction.HMCCommon;

public class SHOPE342Test extends SuperTestClass {
	//public B2CPage b2cPage;
	public HMCPage hmcPage;
	public B2BPage b2bPage;
	String partNumber1;
	String ID1;
	public float WebPrice;
	public float CtoPrice;
	public float localPrice;
	
	public SHOPE342Test(String store,String country,String unit) {
		this.Store = store;
		this.testName = "SHOPE-342";
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup", "p2", "b2b" })
	public void SHOP342 (ITestContext ctx) throws InterruptedException {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2bPage = new B2BPage(driver);
			partNumber1 = "10FGS00F0L";
			ID1="1213286247";
			localPrice=0;
			//System.out.println(testData.B2B.getHomePageUrl());
			//driver.get(testData.B2B.getHomePageUrl());
			enableToggle(driver,hmcPage,ID1);
			String contractID= getContractNo(driver);
			//String contractID="5304617725";
			localPrice = getContractPrice(contractID);
			//localPrice = (float) 854.56805;
			System.out.println(localPrice);
			gotoCMCTOB2Bwebsite(driver,b2bPage);
			
			
		}catch (Throwable e) {
			handleThrowable(e, ctx);
			
		}
	//Roll back
		enableToggle(driver,hmcPage,ID1);
	}
	
	public void enableToggle(WebDriver driver,HMCPage hmcPage,String ID) throws InterruptedException {
		
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BUnitLink.click();
		
		hmcPage.B2BUnit_SearchIDTextBox.sendKeys(ID);
		hmcPage.B2BUnit_SearchButton.click();
		hmcPage.B2BUnit_ResultItem.click();
		hmcPage.B2BUnit_siteAttribute.click();

		System.out.println("Accessory Add,Customise Buy  and Customise Buy For CM All Enale");
		//Enable Accessory Add Toggle
		//driver.findElement(By.xpath("//*[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.accesoryAddToggle][1]]_spantrue']")).click();
		hmcPage.AccessoryAddToggle.click();
		//Enable Customise Buy Toggle
		driver.findElement(By.xpath("//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.customiseBuyToggle]]_spantrue']")).click();
			
		//Enable Customise Buy For CM Toggle
		driver.findElement(By.xpath( "//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.customiseBuyForCMToggle]]_spantrue']")).click();
		//click save button
		//driver.findElement(By.xpath("//div[contains(@id,'organizer.editor.save.title')]")).click();
		hmcPage.SaveButton.click();
		Common.sleep(2000);
		cleanRadis(driver,hmcPage, partNumber1);
		Common.sleep(2000);
		hmcPage.hmcHome_hmcSignOut.click();
	}
	
	public static void cleanRadis(WebDriver driver,HMCPage hmcPage, String partNm)
			throws InterruptedException {
		hmcPage.Home_System.click();
		//hmcPage.Home_RadisCacheCleanLink.click();
		driver.findElement(By.xpath(".//a[@id='Tree/StaticContentLeaf[2]_label']")).click();
		Thread.sleep(5000);
		hmcPage.PageDriver
				.switchTo()
				.frame(hmcPage.PageDriver.findElement(By
						.xpath(".//iframe[contains(@src,'nemoClearCachePage')]")));
		hmcPage.Radis_CleanProductTextBox.clear();
		hmcPage.Radis_CleanProductTextBox.sendKeys(partNm);
		hmcPage.Radis_CleanButton.click();
		Common.waitAlertPresent(hmcPage.PageDriver, 60);
		hmcPage.PageDriver.switchTo().alert().accept();
		hmcPage.PageDriver.switchTo().defaultContent();
		
	}
	
	public void gotoCMCTOB2Bwebsite(WebDriver driver,B2BPage b2bPage) {
		String url = "https://pre-c-hybris.lenovo.com/le/1213286247/au/en/1213286247/p/10FGS00F0L";
		//driver.get(testData.B2B.getHomePageUrl());
		driver.get(url);
		driver.manage().deleteAllCookies();
		driver.get(url);
		//System.out.println(testData.B2B.getBuyerId());
		//System.out.println(testData.B2B.getDefaultPassword());
		B2BCommon.Login(b2bPage, "zhaoss5@lenovo.com", "1q2w3e4r");
		Dailylog.logInfoDB(0,"Login website successfully" , Store, testName);
		driver.get(url);
		//b2cPage.Navigation_ProductsLink.click();
		//====================================================================================================
		//direct add to cart and then edit to change cv and check part No. on cart page
		driver.findElement(By.xpath("//li[contains(@class,'product')]/a/span")).click();
		Common.sleep(2000);
		driver.findElement(By.xpath("//*[@id='Desktops & All-In-Ones']")).click();
		Dailylog.logInfoDB(1,"Go to successfully" , Store, testName);
		Common.sleep(2000);	
		String addtoCartxpath="//*[@id='resultList']//dd[contains(text(),'"+partNumber1+"')]/../../../../../../div[@class='facetedResults-footer']//img[@title='Add to Cart']";
		String Accessoriesxpath = "//*[@id='resultList']//dd[contains(text(),'"+partNumber1+"')]/../../../../../../div[@class='facetedResults-footer']//button[contains(text(),'Accessorize & Buy')]";
		String customizeAndBuy = "//*[@id='resultList']//dd[contains(text(),'"+partNumber1+"')]/../../../../../../div[@class='facetedResults-footer']//a[contains(text(),'Customize and buy')]";
		String plpWebPrice = "//*[@id='resultList']//dd[contains(text(),'"+partNumber1+"')]/../../../../../..//p";
		//check contract price same as PLP page web price
		float plpWebprice=B2CCommon.GetPriceValue(driver.findElement(By.xpath(plpWebPrice)).getText().toString());
		//Assert.assertTrue(plpWebprice==localPrice,"PLP page web price is incorrect");
		Assert.assertEquals(plpWebprice, localPrice, 0.5);
		Dailylog.logInfoDB(2,"PLP page web price correct" , Store, testName);
		
		//direct click AddToCart button
		driver.findElement(By.xpath(addtoCartxpath)).click();
		Dailylog.logInfoDB(2,"AddToCart button click" , Store, testName);
		Common.sleep(1000);
		driver.findElement(By.xpath("//div[@class='addtoCartCTA']/button")).click();
		Common.sleep(10000);
		driver.findElement(By.xpath("//div[@class='addtoCartCTA']/a[@class='goToCartCTA']")).click();
		String cartMTMpartNumber = driver
				.findElement(By.xpath("//p[@class='cart-item-partNumber']/span")).getText().toString();
		Assert.assertEquals(cartMTMpartNumber, partNumber1);
		String ItempriceXpath="//div[@class='cart-item-pricing-and-quantity-itemPrice-amount']";
		String lineTotalXpath="//div[@class='cart-item-pricing-and-quantity-finalPrice-amount']";
		String Itemprice = driver
				.findElement(By.xpath(ItempriceXpath)).getText().toString().trim();
		String lineTotal = driver
				.findElement(By.xpath(lineTotalXpath)).getText().toString().trim();
		//check direct add to cart web price correct
		//item price
		Assert.assertTrue(B2CCommon.GetPriceValue(Itemprice)==WebPrice,"after edit Cart page web price is incorrect");
		Dailylog.logInfoDB(2,"Edit Cart price correct" , Store, testName);
		//line price
		Assert.assertTrue(B2CCommon.GetPriceValue(lineTotal)==CtoPrice,"after edit Cart page web price is incorrect");
		Dailylog.logInfoDB(2,"Edit Cart price correct" , Store, testName);
		//edit click 
		
		driver.findElement(By.xpath("//a[contains(text(),'Edit')]")).click();
		
		String ctoURL = driver.getCurrentUrl();
		
		Assert.assertTrue(ctoURL.contains("CTO"));
		
		Dailylog.logInfoDB(3,"Direct to CTO page" , Store, testName);
		String xpath1 = "//span[contains(text(),'i5-6600')and contains(text(),'3.90 GHz')]/../../..//td[@class='price']";
		String xpath= "//span[contains(text(),'i5-6600')and contains(text(),'3.90 GHz')]";
		
		//Check price on CTO page
		chkOnCtoPage(xpath1,xpath);
		
		String CTOpartNumber = driver.findElement(By.xpath("//p[@class='cart-item-partNumber']/span")).getText().toString();
		
		Assert.assertTrue(CTOpartNumber.contains("CTO"));
		
		//check price after change cv
		//item price
		Itemprice = driver
				.findElement(By.xpath(ItempriceXpath)).getText().toString().trim();
		lineTotal = driver
				.findElement(By.xpath(lineTotalXpath)).getText().toString().trim();
		Assert.assertTrue(B2CCommon.GetPriceValue(Itemprice)==WebPrice,"after edit Cart page web price is incorrect");
		Dailylog.logInfoDB(2,"Edit Cart price correct" , Store, testName);
		//line price
		Assert.assertTrue(B2CCommon.GetPriceValue(lineTotal)==CtoPrice,"after edit Cart page web price is incorrect");
		Dailylog.logInfoDB(2,"Edit Cart price correct" , Store, testName);
		
		//remove product from cart 
		driver.findElement(By.xpath("//span[contains(text(),'Remove')]")).click();
		
		//click product -> Laptops Link back to PLP page
		
		//====================================================================================================
		
		//click Customize And Buy Button and change some cv to check the product No. on cart page
		
		driver.findElement(By.xpath("//li[contains(@class,'product')]/a/span")).click();
		Common.sleep(2000);
		driver.findElement(By.xpath("//*[@id='Desktops & All-In-Ones']")).click();
		Dailylog.logInfoDB(1,"Go to PLP page successfully" , Store, testName);
		Common.sleep(2000);	
		driver.findElement(By.xpath("//*[@id='resultList']//dd[contains(text(),'"+partNumber1+"')]/../../../../../../div[@class='facetedResults-footer']//a[contains(text(),'Customize and buy')]")).click();
		//get the url contains cto 
		ctoURL = driver.getCurrentUrl();
		Assert.assertTrue(ctoURL.contains("CTO"));
		Dailylog.logInfoDB(3,"Direct to CTO page" , Store, testName);	
		
		chkOnCtoPage(xpath1,xpath);
		CTOpartNumber = driver.findElement(By.xpath("//p[@class='cart-item-partNumber']/span")).getText().toString();
		
		Assert.assertTrue(CTOpartNumber.contains("CTO"));
		//check price after change cv
		Itemprice = driver
				.findElement(By.xpath(ItempriceXpath)).getText().toString().trim();
		lineTotal = driver
				.findElement(By.xpath(lineTotalXpath)).getText().toString().trim();
		//item price
		Assert.assertTrue(B2CCommon.GetPriceValue(Itemprice)==WebPrice,"after edit Cart page web price is incorrect");
		Dailylog.logInfoDB(2,"Edit Cart price correct" , Store, testName);
		//line price
		Assert.assertTrue(B2CCommon.GetPriceValue(lineTotal)==CtoPrice,"after edit Cart page web price is incorrect");
		Dailylog.logInfoDB(2,"Edit Cart price correct" , Store, testName);
		//remove product from cart 
		driver.findElement(By.xpath("//span[contains(text(),'Remove')]")).click();
		
		//=======================================================================================		
		
		
		//click Customize And Buy and change some cv and then edit to default cv
		
				driver.findElement(By.xpath("//li[contains(@class,'product')]/a/span")).click();
				Common.sleep(2000);
				driver.findElement(By.xpath("//*[@id='Desktops & All-In-Ones']")).click();
				Dailylog.logInfoDB(1,"Go to PLP page successfully" , Store, testName);
				Common.sleep(2000);	
				
				//click Customize And Buy Button
				driver.findElement(By.xpath("//*[@id='resultList']//dd[contains(text(),'"+partNumber1+"')]/../../../../../../div[@class='facetedResults-footer']//a[contains(text(),'Customize and buy')]")).click();
				//get the url contains cto 
				ctoURL = driver.getCurrentUrl();
				Assert.assertTrue(ctoURL.contains("CTO"));
				Dailylog.logInfoDB(3,"Direct to CTO page" , Store, testName);	
				
				chkOnCtoPage(xpath1,xpath);
				
				CTOpartNumber = driver.findElement(By.xpath("//p[@class='cart-item-partNumber']/span")).getText().toString();
				
				Assert.assertTrue(CTOpartNumber.contains("CTO"));
				
				driver.findElement(By.xpath("//a[contains(text(),'Edit')]")).click();
				
				ctoURL = driver.getCurrentUrl();
				
				Assert.assertTrue(ctoURL.contains("CTO"));
				//check price after change cv
				//item price
				Assert.assertTrue(B2CCommon.GetPriceValue(Itemprice)==WebPrice,"after edit Cart page web price is incorrect");
				Dailylog.logInfoDB(2,"Edit Cart price correct" , Store, testName);
				//line price
				Assert.assertTrue(B2CCommon.GetPriceValue(lineTotal)==CtoPrice,"after edit Cart page web price is incorrect");
				Dailylog.logInfoDB(2,"Edit Cart price correct" , Store, testName);
				Dailylog.logInfoDB(3,"Direct to CTO page" , Store, testName);	
				xpath1 = "//span[contains(text(),'i5-6500')and contains(text(),'3.60 GHz')]/../../..//td[@class='price']";
				xpath= "//span[contains(text(),'i5-6500')and contains(text(),'3.60 GHz')]";
				chkOnCtoPage(xpath1,xpath);
				cartMTMpartNumber = driver
						.findElement(By.xpath("//p[@class='cart-item-partNumber']/span")).getText().toString();
				Assert.assertEquals(cartMTMpartNumber, partNumber1);
				Itemprice = driver
						.findElement(By.xpath("//div[@class='cart-item-pricing-and-quantity-itemPrice-amount']")).getText().toString().trim();
				lineTotal = driver
						.findElement(By.xpath("//div[@class='cart-item-pricing-and-quantity-finalPrice-amount']")).getText().toString().trim();
				//check price after change cv
				//item price
				Assert.assertTrue(B2CCommon.GetPriceValue(Itemprice)==WebPrice,"after edit Cart page web price is incorrect");
				Dailylog.logInfoDB(2,"Edit Cart price correct" , Store, testName);
				//line price
				Assert.assertTrue(B2CCommon.GetPriceValue(lineTotal)==CtoPrice,"after edit Cart page web price is incorrect");
				Dailylog.logInfoDB(2,"Edit Cart price correct" , Store, testName);
		}
	
	public void chkOnCtoPage(String xpath1,String xpath) {
		//check CTO page price
		String webPrice = driver.findElement(By.xpath("//div[@id='w-price']")).getText().toString();
		String ctoPrice = driver
				.findElement(By.xpath("//*[@id='ctoYourPrice']")).getText().toString();
		
		//get C&V price 
		String cvPrice = driver
				.findElement(By.xpath(xpath1))
				.getText().toString();
		System.out.println("========================");
		System.out.println("CV price String is" + cvPrice);
		//change C&V on CTO page 
		driver.findElement(By.xpath(xpath)).click();
		if(cvPrice.contains("add")) {
			cvPrice = cvPrice.replaceAll("[^0-9\\.]", "");
			WebPrice = B2CCommon.GetPriceValue(cvPrice) + B2CCommon.GetPriceValue(webPrice);
			CtoPrice = B2CCommon.GetPriceValue(cvPrice) + B2CCommon.GetPriceValue(ctoPrice);
			webPrice = driver.findElement(By.xpath("//div[@id='w-price']")).getText().toString();
			ctoPrice = driver
					.findElement(By.xpath("//*[@id='ctoYourPrice']")).getText().toString();
			Assert.assertTrue(WebPrice==B2CCommon.GetPriceValue(webPrice));
			Assert.assertTrue(CtoPrice==B2CCommon.GetPriceValue(ctoPrice));
			
		}else if (cvPrice.contains("subtract")) {
			cvPrice = cvPrice.replaceAll("[^0-9\\.]", "");
			WebPrice = B2CCommon.GetPriceValue(webPrice)-B2CCommon.GetPriceValue(cvPrice);
			System.out.println("web price is " + WebPrice);
			CtoPrice = B2CCommon.GetPriceValue(ctoPrice) - B2CCommon.GetPriceValue(cvPrice);
			System.out.println("Cto price is " + CtoPrice);
			webPrice = driver.findElement(By.xpath("//div[@id='w-price']")).getText().toString();
			ctoPrice = driver
					.findElement(By.xpath("//*[@id='ctoYourPrice']")).getText().toString();
			System.out.println(B2CCommon.GetPriceValue(webPrice));
			System.out.println("web price is " + B2CCommon.GetPriceValue(webPrice));
			System.out.println("CTO price is " + B2CCommon.GetPriceValue(ctoPrice));
			Assert.assertTrue(WebPrice==B2CCommon.GetPriceValue(webPrice));
			Assert.assertTrue(CtoPrice==B2CCommon.GetPriceValue(ctoPrice));
		}
		//click add to cart on cto page
		driver.findElement(By.xpath("//span[@id='CTO_addToCart']")).click();
		
		
	}
	
	
	public float getContractPrice(String contractID) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Nemo.click();
		hmcPage.Home_Contract.click();
		driver.findElement(By.xpath("//input[contains(@id,'Contract.contractId')]")).sendKeys(contractID);
		hmcPage.Contract_searchbutton.click();
		String contractType = driver.findElement(By.xpath("(//tbody/tr[contains(@id,'Content/OrganizerListEntry')])[last()]/td[5]/div")).getAttribute("id");
		System.out.println(contractType);
		Common.doubleClick(driver, hmcPage.Contract_searchResult);
		float localPrice=0;;
		if (contractType.contains("ZCOQ")||contractType.contains("ZCNQ")) {
			String contractPrice = driver.findElement(By.xpath("//div[text()='"+partNumber1+"']/../../following-sibling::td[1]/div/div")).getText().toString();
			System.out.println(contractPrice);
			localPrice= B2CCommon.GetPriceValue(contractPrice);
			System.out.println(localPrice);
		}else if (contractType.contains("ZCLP")) {
			String contractPrice = driver.findElement(By.xpath("//div[text()='"+partNumber1+"']/../../following-sibling::td[1]/div/div")).getText().toString();
			System.out.println(contractPrice);
			if (Store.equals("AU")) {
				hmcPage.Home_PriceSettings.click();
				hmcPage.Home_ExchangeRate.click();
				Common.sleep(2000);
				// From Currency: AUD(B2B page price) To Currency: USD
				hmcPage.ExchangeRate_fromCurrency.click();
				driver.findElement(By.xpath("//td/select[contains(@id,'ExchangeRate.fromCurrency')]/option[contains(text(),'AUD')]")).click();
				//hmcPage.ExchangeRate_fromCurrency.sendKeys("AUD");
				Common.sleep(3000);
				hmcPage.ExchangeRate_toCurrency.click();
				Common.sleep(1000);
				//driver.findElement(By.xpath("//td/select[contains(@id,'ExchangeRate.fromCurrency')]/option[contains(text(),'USD')]")).click();
				hmcPage.ExchangeRate_toCurrency.sendKeys("USD");
				driver.findElement(By.xpath("//td/select[contains(@id,'ExchangeRate.toCurrency')]/option[contains(text(),'USD')]")).click();
			}
			if (Store.equals("JP")) {
				hmcPage.Home_ExchangeRate.click();
				// From Currency: JPY(B2B page price) To Currency: USD
				hmcPage.ExchangeRate_fromCurrency.click();
				driver.findElement(By.xpath("//td/select[contains(@id,'ExchangeRate.fromCurrency')]/option[contains(text(),'JPY')]")).click();
				//hmcPage.ExchangeRate_fromCurrency.sendKeys("JPY");
				Common.sleep(3000);
				hmcPage.ExchangeRate_toCurrency.click();
				Common.sleep(1000);
				//driver.findElement(By.xpath("//td/select[contains(@id,'ExchangeRate.fromCurrency')]/option[contains(text(),'USD')]")).click();
				hmcPage.ExchangeRate_toCurrency.sendKeys("USD");
				driver.findElement(By.xpath("//td/select[contains(@id,'ExchangeRate.toCurrency')]/option[contains(text(),'USD')]")).click();
			
			}
			Common.sleep(2000);
			
			hmcPage.Contract_searchbutton.click();
			Common.sleep(2000);
			if (Common.isElementExist(driver,
					By.xpath("//td[contains(text(),'The search was finished. No results were found')]"))) {
				Assert.fail("ExchangeRate_rate no result!!");
			}
			// order by valid from (descend)
			hmcPage.ExchangeRate_validFrom.click();
			Common.sleep(3000);
			Common.doubleClick(driver, hmcPage.ExchangeRate_rateRow);
			Common.waitElementVisible(driver, hmcPage.ExchangeRate_rate);
			String rateText = hmcPage.ExchangeRate_rate.getAttribute("value").toString();
			float changeRate=B2CCommon.GetPriceValue(rateText);
			System.out.println("Exchange Rate : " + rateText);
			localPrice= B2CCommon.GetPriceValue(contractPrice);
			localPrice =  localPrice*changeRate;
			System.out.println("=============================");
			System.out.println(localPrice);
			
		}
		return localPrice;
			
	}
	
	public String getContractNo(WebDriver driver) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_System.click();
		hmcPage.B2CPriceRules_Types.click();
		hmcPage.typeCode.sendKeys("PcaSnapshot");
		hmcPage.B2BUnit_SearchButton.click();
		WebElement PcaSnapshot = driver.findElement(By.xpath("//*[@id='Content/StringDisplay[PcaSnapshot]_span']"));
		Common.doubleClick(driver,PcaSnapshot);
		
		//hmcPage.open_newwindow.click();
		driver.findElement(By.xpath("//*[@id='Content/ImageToolbarAction[open_editor_newwindow]_img']")).click();
		Common.switchToWindow(driver, 1);
		driver.findElement(By.xpath("//*[@id='GenericItemChip$6[openorganizer]_img']")).click();
		Common.switchToWindow(driver, 2);
		driver.findElement(By.xpath("//option[@value='soldToUid']")).click();
		Common.sleep(2000);
		driver.findElement(By.xpath("//option[@value='productCode']")).click();
		
		driver.findElement(By.xpath("//input[contains(@id,'PcaSnapshot.soldToUid')]")).sendKeys(ID1);
		driver.findElement(By.xpath("//input[contains(@id,'PcaSnapshot.productCode')]")).sendKeys(partNumber1);
		Common.sleep(1000);
		driver.findElement(By.xpath("//span[contains(@id,'_searchbutton')]")).click();
		WebElement searchResult = driver.findElement(By.xpath("//div[@class='olcResultList']/table/tbody/tr[3]/td[3]"));
		Common.doubleClick(driver,searchResult);
		String contractID=driver.findElement(By.xpath("//input[contains(@id,'caContractAgreementId')]")).getAttribute("value");
		System.out.println("contractID is"+contractID);
		driver.close();
		Common.switchToWindow(driver, 1);
		driver.close();
		Common.switchToWindow(driver, 0);
		return contractID;
			
	}
	
}

	
