package TestScript.B2B;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA27290Test extends SuperTestClass{
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	double PLPpriceText = 0d;
	double PDPpriceText = 0d;
	double PBpriceText = 0d;
	boolean preSelected=false;
	boolean cto;
	String productNo="";
	String optionNo="";

	
	public NA27290Test(String store){
		this.Store=store;
		this.testName="NA-27290";
		
	}
	private double String2Num(String valueString){
		String price=valueString.replace("$", "").replace("￥", "").replace(",", "").replace("-", "");
		return Double.parseDouble(price);
	}
	@Test(alwaysRun=true,groups={"shopgroup","pricingpromot","p1","b2b","compatibility"})
	public void NA27290(ITestContext ctx){
		try{
			this.prepareTest();
			b2bPage=new B2BPage(driver);
			hmcPage=new HMCPage(driver);
			
			//check the price and get option
			checkPrice(true);
			
			//Config preselected option
			addGroupItem(productNo,optionNo,this.Store);
			
			//debug price
			double productPrice=checkDebugPrice(productNo);
			double optionpriceText=checkDebugPrice(optionNo);
			
			Dailylog.logInfo("Validate the price");
			if(preSelected){				
				Assert.assertEquals(PBpriceText, productPrice+optionpriceText, 0.1);				
				Assert.assertEquals(PBpriceText, PDPpriceText, 0.1);
				Assert.assertEquals(PBpriceText, PLPpriceText, 0.1);
			}else{
				//Check the price after config preselected option				
				double PLPpriceText1=PLPpriceText;
				double PDPpriceText1=PDPpriceText;
				double PBpriceText1=PBpriceText;
				checkPrice(false);
				Assert.assertEquals(PBpriceText, PBpriceText1+optionpriceText, 0.1);				
				Assert.assertEquals(PBpriceText, PDPpriceText, 0.1);
				Assert.assertEquals(PBpriceText, PLPpriceText, 0.1);
//				Assert.assertEquals(PBpriceText1, PDPpriceText1, 0.1);
//				Assert.assertEquals(PBpriceText1, PLPpriceText1, 0.1);
			}
			
		}
		catch(Throwable e){
			handleThrowable(e,ctx);
		}}
	
	public void checkPrice(boolean getOption){
		
		Dailylog.logInfo("Go to B2B site and Login");
		//driver.get(testData.B2B.getLoginUrl());
		Common.NavigateToUrl(driver, Browser,testData.B2B.getLoginUrl());
		B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
		HandleJSpring(driver);
		b2bPage.HomePage_productsLink.click();
//		driver.findElement(By.xpath("(//a[@class='products_submenu'])[1]")).click();
		Common.javascriptClick(driver, driver.findElement(By.xpath("(//a[@class='products_submenu'])[1]")));
		Common.sleep(3000);
		List<WebElement> mtmPrices = driver
				.findElements(By.xpath("//div/a[contains(text(),'View Details')]"));
	
		if(mtmPrices.size()==0){
			Assert.fail("B2B page have no products");
		}else{

			String B2BpriceText1 = b2bPage.ProductPage_PriceOnPLP.getText().toString();
			PLPpriceText = String2Num(B2BpriceText1);
			Dailylog.logInfo("PLPpriceText:"+PLPpriceText);
//			b2bPage.ProductPage_details.click();
			Common.javascriptClick(driver, b2bPage.ProductPage_details);
			Common.sleep(3000);
			productNo=driver.getCurrentUrl().split("/p/")[1];
			System.out.println("productNo:"+productNo);
			
			if(driver.getCurrentUrl().contains("CTO")&&driver.getCurrentUrl().contains("WW")){
				cto=true;
				String orderTotalPrice = b2bPage.PDPPage_OrderTotalPrice.getText().toString();
				PDPpriceText = String2Num(orderTotalPrice);
				Dailylog.logInfo("PDPpriceText:"+PDPpriceText);
				b2bPage.laptops_addToCartForCTO.click();

			}else{
				//MTM product
				cto=false;
				String B2BpriceText2 = b2bPage.PDPPage_ProductPrice.getText().toString();
				PDPpriceText = String2Num(B2BpriceText2);
				Dailylog.logInfo("PDPpriceText"+PDPpriceText);
				Common.sleep(3000);
				//driver.findElement(By.xpath("//button[contains(@class,'button-cart-pdp large button-full')]")).click();
				Common.javascriptClick(driver, driver.findElement(By.xpath("//button[contains(@class,'button-cart-pdp large button-full')]")));
				
			}
			
			String webPrice = b2bPage.PDPPage_WebPrice.getText().toString();
			PBpriceText = String2Num(webPrice);
			Dailylog.logInfo("PBpriceText:"+PBpriceText);
			
			if(getOption){
				if(Common.checkElementDisplays(driver, By.xpath("//input[@checked='checked']/following-sibling::div/div[contains(text(),'Base warranty')]"), 10)){
					List<WebElement> options=driver.findElements(By.xpath("//input[not(@checked) and (@type='radio') and (contains(@id,'option'))]"));
					optionNo=options.get(0).getAttribute("value");
				
				}else if(Common.checkElementDisplays(driver, By.xpath("//input[(@checked='checked') and (@type='radio') and (contains(@id,'option'))]"), 10)){
					optionNo=driver.findElement(By.xpath("//input[(@checked='checked') and (@type='radio') and (contains(@id,'option'))]")).getAttribute("value");						
					preSelected=true;
					Dailylog.logInfo("Pre-selected option exsited");
				}else if(Common.checkElementDisplays(driver, By.xpath("//div[contains(@class,'expandableHeading')]"), 10)){
					List<WebElement> header=driver.findElements(By.xpath("//div[contains(@class,'expandableHeading')]"));
					//expand header
					if(!header.get(0).getAttribute("class").contains("expanded")){
						header.get(0).click();
					}
					String id=header.get(0).getAttribute("id");
					String xpath="//div[@data-source-id='"+id+"']/div/ul/li/input[not(@checked)]";
					optionNo=driver.findElement(By.xpath(xpath)).getAttribute("value");
								
				}else{
					Assert.fail("product havo no option");
				}
				Dailylog.logInfo("optionNo"+optionNo);
				
			}else{
				Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//input[(@checked='checked') and (@value='"+optionNo+"')]"), 10), "option is selected default");
				Dailylog.logInfo("check the option is selected");
			}		
	}
	
}
	
	@AfterTest
	public void rollBack(){
		System.out.println("---------RollBack----------");
		try{
			SetupBrowser();
    		hmcPage = new HMCPage(driver);
			//driver.get(testData.HMC.getHomePageUrl());
    		Common.NavigateToUrl(driver, Browser,testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			searchProduct("online",productNo);
			hmcPage.Catalog_multiCountry.click();
			
			String xpath = "//div[contains(@id,'ItemDisplay')][contains(.,'B2B')]/../../td[contains(.,'"+this.Store+"')]";
			if(Common.checkElementDisplays(driver, By.xpath(xpath), 10)){
				Common.rightClick(driver, driver.findElement(By.xpath(xpath)));
				hmcPage.HMC_RemoveResult.click();
				
				if(Common.isAlertPresent(driver)){
					driver.switchTo().alert().accept();
				}
				Dailylog.logInfo("Pre-selected option Deleted ");
				hmcPage.products_PB_saveBtn.click();
	
			}
			hmcPage.hmcHome_hmcSignOut.click();

		}catch(Exception e){
			e.printStackTrace();
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
	
	private void searchProduct(String category, String productID) {
		hmcPage.Home_CatalogLink.click();
		Common.waitElementClickable(driver, hmcPage.Home_ProductsLink, 5);
		hmcPage.Home_ProductsLink.click();
		hmcPage.Catalog_ArticleNumberTextBox.sendKeys(productID);
		hmcPage.Catalog_CatalogVersion.click();
		if (category.equals("staged")) {
			hmcPage.Catalog_MasterMultiCountryProductCatalogStaged.click();
		} else if (category.equals("online")) {
			hmcPage.Catalog_MasterMultiCountryProductCatalogOnline.click();
		}
		//hmcPage.Catalog_SearchButton.click();
		Common.javascriptClick(driver, hmcPage.Catalog_SearchButton);
		hmcPage.products_resultItem.click();
	}
	
	private void addOption(HMCPage hmcPage,String Option) throws InterruptedException{
		Dailylog.logInfo("Add Options");
		Common.rightClick(driver, hmcPage.product_preselectedOptionsTable);
		Common.sleep(3000);
		//hmcPage.product_CreateProductOptions.click();
		JavascriptExecutor js= (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", hmcPage.product_CreateProductOptions);
		Dailylog.logInfo("Create Product Options");
		Common.waitElementVisible(driver, hmcPage.product_preselecteOptionsSearch);
		hmcPage.product_preselecteOptionsSearch.click();
		switchToWindow(2);
		Common.waitElementVisible(driver, hmcPage.products_PB_code);
		hmcPage.products_PB_code.clear();
		hmcPage.products_PB_code.sendKeys(Option);
		Select catalog = new Select(hmcPage.products_PB_catalogVersionSel);
		catalog.selectByVisibleText("masterMultiCountryProductCatalog - Online");
		//hmcPage.products_PB_searchBtn.click();
		Common.javascriptClick(driver, hmcPage.products_PB_searchBtn);
		By targetAcc = By.xpath("//div[contains(@id,'" + Option + "')]");
		Common.waitElementClickable(driver, driver.findElement(targetAcc), 5);
		driver.findElement(targetAcc).click();
		hmcPage.products_PB_useBtn.click();
		switchToWindow(1);
		//hmcPage.products_PB_saveBtn.click();
		Common.javascriptClick(driver, hmcPage.products_PB_saveBtn);

	}
	
	private void addGroupItem(String productID ,String Option1,String country)
			throws InterruptedException {
		Common.NavigateToUrl(driver, Browser,testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		searchProduct("online",productID);
		hmcPage.Catalog_multiCountry.click();
		String xpath = "//div[contains(@id,'ItemDisplay')][contains(.,'" + "B2B"
						+ "')]/../../td[contains(.,'" + Store + "')]";
		if(Common.checkElementDisplays(driver, By.xpath(xpath), 5)){
			Common.doubleClick(driver, driver.findElement(By.xpath(xpath)));
			switchToWindow(1);			
		}else{
			Common.rightClick(driver, hmcPage.product_productBuilderTable);
			if(cto){
				hmcPage.product_CreateProductBuilder.click();
			}else{
				hmcPage.product_CreateProductBuilderMTM.click();
			}
			
			switchToWindow(1);
			// select country
			//hmcPage.preselectedOptions_Country.click();
			Common.javascriptClick(driver, hmcPage.preselectedOptions_Country);
			By countryX = By.xpath("//select/option[contains(text(),'" + country + "')]");
			//driver.findElement(countryX).click();
			Common.javascriptClick(driver, driver.findElement(countryX));
			Dailylog.logInfo("Selected Country: " + country);
			// select channel
			//hmcPage.preselectedOptions_Chanel.click();
			Common.javascriptClick(driver, hmcPage.preselectedOptions_Chanel);
			Common.sleep(3000);
			hmcPage.preselectedOptions_channelB2B.click();
			Dailylog.logInfo("selected channel B2B");		
		}
		String xpath1 = "//input[contains(@value,'"+Option1+"')][contains(@value,'masterMultiCountryProductCatalog - Online')]";

		if (!Common.checkElementDisplays(driver, By.xpath(xpath1), 10)) {
			addOption(hmcPage,Option1);
		}	
		Common.sleep(3000);
		driver.close();
		switchToWindow(0);
		hmcPage.hmcHome_hmcSignOut.click();
		Dailylog.logInfo("Add option end");
	}
			
	public double checkDebugPrice(String productNo) throws InterruptedException{
			// Go to HMC -> Price Settings -> Pricing Cockpit -> B2B Price
			Dailylog.logInfo("Price simulate---");
			//driver.get(testData.HMC.getHomePageUrl());
			Common.NavigateToUrl(driver, Browser,testData.HMC.getHomePageUrl());
			if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)){
				HMCCommon.Login(hmcPage, testData);
			}
			hmcPage.Home_PriceSettings.click();
			hmcPage.Home_PricingCockpit.click();
			driver.switchTo().frame(hmcPage.PricingCockpit_iframe);
			Thread.sleep(3000);
			if (Common.isElementExist(driver, By.xpath("//div[1]/input[@name='j_username']"))) {
				hmcPage.PricingCockpit_username.sendKeys(testData.HMC.getLoginId());
				hmcPage.PricingCockpit_password.sendKeys(testData.HMC.getPassword());
				hmcPage.PricingCockpit_Login.click();
			}

			Thread.sleep(3000);
			// Simulator

			hmcPage.PricingCockpit_B2BpriceSimulate.click();

			// For example:
			/*
			 * Country -> Australia
			 * 
			 */
			Thread.sleep(5000);

			hmcPage.B2BpriceSimulate_CountryButton.click();
			System.out.println("country click:");
			Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_CountryText, 3);
			if (testData.Store.equals("AU")) {
				hmcPage.B2BpriceSimulate_CountryText.sendKeys("Australia");
			}
			if (testData.Store.equals("US")) {
				hmcPage.B2BpriceSimulate_CountryText.sendKeys("[US]");
			}
			if (testData.Store.equals("JP")) {
				hmcPage.B2BpriceSimulate_CountryText.sendKeys("Japan");
			}
			hmcPage.B2BpriceSimulate_CountryText.sendKeys(Keys.ENTER);
			System.out.println("contry sendkey:");
			Thread.sleep(10000);
			// Store -> aule
			Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_StoreButton, 120);
			hmcPage.B2BpriceSimulate_StoreButton.click();
			System.out.println("store click:");
			Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_CountryText, 120);
			if (testData.Store.equals("AU")) {
				hmcPage.B2BpriceSimulate_CountryText.sendKeys("aule");
			}
			if (testData.Store.equals("US")) {
				hmcPage.B2BpriceSimulate_CountryText.sendKeys("usle");
			}
			if (testData.Store.equals("JP")) {
				hmcPage.B2BpriceSimulate_CountryText.sendKeys("Japan");
			}
			hmcPage.B2BpriceSimulate_CountryText.sendKeys(Keys.ENTER);
			System.out.println("store sendkey:");
			Thread.sleep(3000);
			// Catalog version -> Nemo Master Multi Country Product Catalog -
			// Online
			Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_CatalogButton, 120);
			hmcPage.B2BpriceSimulate_CatalogButton.click();
			System.out.println("catalog click:");
			Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_CountryText, 3);
			hmcPage.B2BpriceSimulate_CountryText.sendKeys("Nemo Master Multi Country Product Catalog - Online");
			hmcPage.B2BpriceSimulate_CountryText.sendKeys(Keys.ENTER);
			System.out.println("catalog sendkey:");
			Thread.sleep(30000);
			// B2B Unit -> 1213654197 1213577815
			Common.checkElementExists(driver, hmcPage.B2BpriceSimulate_B2BUnitDiv, 120);
			System.out.println("B2BUNIT Visible :" + testData.B2B.getB2BUnit());
			hmcPage.B2BpriceSimulate_B2BUnitButton.click();
			System.out.println("B2BUnit click:");
			Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_CountryText, 120);
			hmcPage.B2BpriceSimulate_CountryText.sendKeys(testData.B2B.getB2BUnit());
			hmcPage.B2BpriceSimulate_CountryText.sendKeys(Keys.ENTER);
			System.out.println("B2BUNIT sendkey:");
			Thread.sleep(3000);
			// Price Date -> 2016-04-28
			Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_DateButton, 120);
			hmcPage.B2BpriceSimulate_DateButton.click();
			System.out.println("data click:");
			hmcPage.B2BpriceSimulate_DateButton.sendKeys(Keys.ENTER);
			System.out.println("data sendkey:");
			Thread.sleep(3000);
			// Product -> 20FAS0S010
			Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_ProductButton, 120);
			hmcPage.B2BpriceSimulate_ProductButton.click();
			System.out.println("product click:");
			Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_CountryText, 3);
			hmcPage.B2BpriceSimulate_CountryText.sendKeys(productNo);
			Thread.sleep(5000);
			WebElement productResult = driver.findElement(By.xpath("//div[text()='[']/span[text()='" + productNo + "']"));
			Common.waitElementVisible(driver, productResult);
			productResult.click();
			System.out.println("product sendkey:");
			Thread.sleep(3000);
			//hmcPage.B2BpriceSimulate_DebugButton.click();
			Common.javascriptClick(driver, hmcPage.B2BpriceSimulate_DebugButton);
			Thread.sleep(3000);
			Common.waitElementVisible(driver, hmcPage.B2BpriceSimulate_webPrice);
			String debugPriceText = hmcPage.B2BpriceSimulate_webPrice.getText().toString();
			double debugPrice = String2Num(debugPriceText);	
			Dailylog.logInfo("product debug price : " + debugPrice);
			driver.switchTo().defaultContent();
			hmcPage.hmcHome_hmcSignOut.click();
			return debugPrice;
		}
	
	public void HandleJSpring(WebDriver driver) {

		if (driver.getCurrentUrl().contains("j_spring_security_check")) {

			driver.get(driver.getCurrentUrl().replace(
					"j_spring_security_check", "login"));

			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
		}
	}

		
}


