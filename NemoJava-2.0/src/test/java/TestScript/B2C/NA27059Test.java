package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA27059Test extends SuperTestClass {
	public HMCPage hmcPage;
	public B2CPage b2cPage;
	public String partNumberBack="20HDCTO1WWENUS0";
	public int i=0;
	public String[] priceLabels;
	public String[] priceItem;
	String PNWithoutDiscount;
	String PNWithDiscount;
	float PriceWithoutDiscount=0;
	float OriginPriceWithDiscount=0;
	float PriceWithDiscount=0;
	boolean newPB=true;
	
	public NA27059Test(String store) {
		this.Store = store;
		this.testName = "NA-27059";

	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"shopgroup", "pricingpromot", "p1", "b2c"})
	public void NA27059(ITestContext ctx) throws Exception {
		try {
				this.prepareTest();
				hmcPage = new HMCPage(driver);
				b2cPage = new B2CPage(driver);

				checkPLPPrice(partNumberBack);
	            ((JavascriptExecutor) driver).executeScript("arguments[0].click();",b2cPage.PDP_AddToCartOrCustomize);
	            Common.sleep(3000);
	            
	            //get the price on CTO page
	            Map<String, Float> map=checkPrice(true);
            
	            //add options in HMC
	            addGroupItem(PNWithoutDiscount,PNWithDiscount,partNumberBack,Store,"B2C");

	            Dailylog.logInfoDB(1,"Add preselect option in HMC successfully" ,Store, testName);	
	            
	            checkPLPPrice(partNumberBack);
	            ((JavascriptExecutor) driver).executeScript("arguments[0].click();",b2cPage.PDP_AddToCartOrCustomize);
	            Common.sleep(3000);
	            
	            //Check price
	            Map<String, Float> map1=checkPrice(false);
	            
	            
	            if(newPB){
		            if(map.containsKey("PB_basePrice")){
			            Assert.assertEquals(map.get("PB_basePrice"), map1.get("PB_basePrice"), 0.1);
		            }
		            
		            if(map.containsKey("PB_yourSavePrice")){
			            Assert.assertEquals(map.get("PB_yourSavePrice")+OriginPriceWithDiscount-PriceWithDiscount, map1.get("PB_yourSavePrice"), 0.1);

		            }else{
		            	Assert.assertEquals(OriginPriceWithDiscount-PriceWithDiscount, map1.get("PB_yourSavePrice"), 0.1);
		            }
		
		            if(map.containsKey("PB_yourPrice")){
		            	 Assert.assertEquals(map.get("PB_yourPrice")+PriceWithoutDiscount+PriceWithDiscount, map1.get("PB_yourPrice"), 0.01);
		            }
	            }else{
	            	
	            	Assert.assertEquals(map.get("CTO_WebPrice")+OriginPriceWithDiscount+PriceWithoutDiscount, map1.get("CTO_WebPrice"), 0.1);
	            	Assert.assertEquals(map.get("CTO_SavePrice")+OriginPriceWithDiscount-PriceWithDiscount, map1.get("CTO_SavePrice"), 0.1);
	            	Assert.assertEquals(map.get("CTO_OrderPrice")+PriceWithDiscount+PriceWithoutDiscount, map1.get("CTO_OrderPrice"), 0.1);
	            	
	            }	                      	          								

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	@Test
	public void rollBack(){
		System.out.println("---------RollBack----------");
		try{
			SetupBrowser();
    		hmcPage = new HMCPage(driver);
			//driver.get(testData.HMC.getHomePageUrl());

    		Common.NavigateToUrl(driver, Browser,testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			searchProduct("online",partNumberBack);
			hmcPage.Catalog_multiCountry.click();
			
			String xpath = "//div[contains(@id,'ItemDisplay')][contains(.,'B2C')]/../../td[contains(.,'"+this.Store+"')]";
			if(Common.checkElementDisplays(driver, By.xpath(xpath), 10)){
				Common.rightClick(driver, driver.findElement(By.xpath(xpath)));
				hmcPage.HMC_RemoveResult.click();
				
				if(Common.isAlertPresent(driver)){
					driver.switchTo().alert().accept();
				}
				Dailylog.logInfoDB(1,"Pre-selected option Deleted " ,Store, testName);

				Common.sleep(3000);
				hmcPage.products_PB_saveBtn.click();
	
			}
			hmcPage.hmcHome_hmcSignOut.click();

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void checkPLPPrice(String partNumber){
		
		//driver.get(testData.B2C.getHomePageUrl()+"/p/"+partNumber);
		Common.NavigateToUrl(driver, Browser,testData.B2C.getHomePageUrl()+"/p/"+partNumber);
		//get the price on PLP page
		List<WebElement> priceLabelsItems = driver.findElements(By.xpath("//div[@id='singlesku-configure-summary']//div/dl[@class='pricingSummary-details']/dt[contains(text(),':')]"));
		List<WebElement> priceItems = driver.findElements(By.xpath("//div[@id='singlesku-configure-summary']//div/dl[@class='pricingSummary-details']/dt[@class='webprice pricingSummary-priceList-label']/following-sibling::dd[contains(text(),'$')]"));
		priceLabels= new String[priceItems.size()];
		priceItem= new String[priceItems.size()];
		i=0;
		for(WebElement e:priceLabelsItems){
			if(e.getText()!=" "){
				priceLabels[i++]=e.getText();
			}
		}
		i=0;
		for(WebElement e1:priceItems){
			if(e1.getText()!=" "){
				priceItem[i++]=e1.getText();
			}
		}
		
		for(i=0;i<priceItem.length;i++){
			Dailylog.logInfoDB(i,"PLP Page-"+priceLabels[i]+":"+priceItem[i] ,Store, testName);
		}
	}
	
	private Map<String,Float> checkPrice(boolean getOption) {
		
		  Map<String, Float> map = new HashMap<String, Float>();

		  if(!Common.checkElementDisplays(driver,b2cPage.ctoPrice, 120)){  
	            if(Common.checkElementDisplays(driver,b2cPage.newCTO_BasePrice, 10)){
	            	map.put("CTO_basePrice",GetPriceValue( b2cPage.newCTO_BasePrice.getText()));
	            	Dailylog.logInfoDB(1,"CTO_basePrice"+b2cPage.newCTO_BasePrice.getText(),Store, testName);
				}
				if(Common.checkElementDisplays(driver,b2cPage.cto_YouSavePrice, 10)){
					map.put("CTO_yourSavePrice",GetPriceValue( b2cPage.cto_YouSavePrice.getText()));
					Dailylog.logInfoDB(1,"CTO_yourSavePrice"+b2cPage.cto_YouSavePrice.getText(),Store, testName);
				}
				map.put("CTO_yourPrice", GetPriceValue(b2cPage.newCTO_PBYourPrice.getText()));
				Dailylog.logInfoDB(1,"CTO_yourPrice"+b2cPage.newCTO_PBYourPrice.getText(),Store, testName);
	            
				Common.sleep(3000);
	            //b2cPage.Product_AddToCartBtn.click();
				((JavascriptExecutor) driver).executeScript("arguments[0].click();",b2cPage.Product_AddToCartBtn);
	            Common.sleep(3000);
	            b2cPage.Product_AddToCartBtn.click();
//				((JavascriptExecutor) driver).executeScript("arguments[0].click();",b2cPage.Product_AddToCartBtn);
	            //Get the Price on PB page
	            Common.sleep(5000);
	            
	            if(Common.checkElementDisplays(driver,b2cPage.newCTO_BasePrice, 120)){
	            	map.put("PB_basePrice", GetPriceValue(b2cPage.newCTO_BasePrice.getText()));
	            	Dailylog.logInfoDB(1,"PB_basePrice"+b2cPage.newCTO_BasePrice.getText(),Store, testName);
				}
				if(Common.checkElementDisplays(driver,b2cPage.cto_YouSavePrice, 10)){
					map.put("PB_yourSavePrice", GetPriceValue(b2cPage.cto_YouSavePrice.getText()));
					Dailylog.logInfoDB(1,"PB_yourSavePrice"+b2cPage.cto_YouSavePrice.getText(),Store, testName);
				}
				if(Common.checkElementDisplays(driver,b2cPage.NewPB_AsConfiguredPrice, 10)){
					map.put("PB_AsConfiguredPrice", GetPriceValue(b2cPage.NewPB_AsConfiguredPrice.getText()));
					Dailylog.logInfoDB(1,"PB_AsConfiguredPrice"+b2cPage.NewPB_AsConfiguredPrice.getText(),Store, testName);
				}
					            
	            map.put("PB_yourPrice", GetPriceValue(b2cPage.newCTO_PBYourPrice.getText()));
	            Dailylog.logInfoDB(1,"PB_yourPrice"+b2cPage.newCTO_PBYourPrice.getText(),Store, testName);
	    
				
				if(getOption){
		            //get the option
		            //expand all the option
		            List<WebElement> optionHeaders = driver.findElements(By.xpath("//div[@class='configuratorItem-accordion-content software' or @class='configuratorItem-accordion-content accessories']/div[@class='expandableHeading section-header']"));
					for(WebElement e2:optionHeaders){						
						e2.click();
					}
					//get the option and price				
					PNWithoutDiscount=b2cPage.NewPB_OptionCheckboxWithoutDiscount.get(0).getAttribute("value");
					PNWithDiscount=b2cPage.NewPB_OptionCheckboxWithDiscount.get(0).getAttribute("value");
					
					String xpath="//input[@value='"+PNWithDiscount+"']/../div/p[@class='del-price']/del";
					OriginPriceWithDiscount=GetPriceValue(driver.findElement(By.xpath(xpath)).getText());
					xpath="//input[@value='"+PNWithDiscount+"']/../div/p/span[@class='option-price option-price-opt']";
					PriceWithDiscount=GetPriceValue(driver.findElement(By.xpath(xpath)).getText());
					
					xpath="//input[@value='"+PNWithoutDiscount+"']/../div/p/span[@class='option-price option-price-opt']";
					PriceWithoutDiscount=GetPriceValue(driver.findElement(By.xpath(xpath)).getText());
					
					Dailylog.logInfoDB(1,"OriginPriceWithDiscount"+OriginPriceWithDiscount,Store, testName);
					Dailylog.logInfoDB(1,"PriceWithDiscount"+PriceWithDiscount,Store, testName);
					Dailylog.logInfoDB(1,"PriceWithoutDiscount"+PriceWithoutDiscount,Store, testName);
				}											
	            
			}else{
				//CTO page
				newPB=false;
				System.out.println(newPB);
				map.put("CTO_WebPrice",GetPriceValue( b2cPage.cto_WebPrice.getText()));
				map.put("CTO_SavePrice",GetPriceValue( b2cPage.cto_YouSavePrice.getText()));
				map.put("CTO_OrderPrice",GetPriceValue( b2cPage.ctoPrice.getText()));
				
				Dailylog.logInfoDB(1,"CTO_WebPrice"+b2cPage.cto_WebPrice.getText(),Store, testName);
				Dailylog.logInfoDB(1,"CTO_SavePrice"+b2cPage.cto_YouSavePrice.getText(),Store, testName);
				Dailylog.logInfoDB(1,"CTO_OrderPrice"+b2cPage.ctoPrice.getText(),Store, testName);
				
				b2cPage.cto_AddToCartButton.click();
				//old pb page
	            map.put("PB_webPrice",GetPriceValue( b2cPage.pb_WebPrice.getText()));
	            Dailylog.logInfoDB(1,"PB_webPrice"+b2cPage.pb_WebPrice.getText(),Store, testName);

	            if(Common.checkElementDisplays(driver,b2cPage.InstanSavingPrice, 10)){
	            	map.put("PB_InstanSavingPrice",GetPriceValue( b2cPage.InstanSavingPrice.getText()));
	            	Dailylog.logInfoDB(1,"PB_InstanSavingPrice"+b2cPage.InstanSavingPrice.getText(),Store, testName);
				}
	            if(Common.checkElementDisplays(driver,b2cPage.PB_SavedPrice, 10)){
	            	map.put("PB_SavingPrice",GetPriceValue( b2cPage.PB_SavedPrice.getText()));
	            	Dailylog.logInfoDB(1,"PB_SavingPrice"+b2cPage.PB_SavedPrice.getText(),Store, testName);
				}
	           

				if(getOption){
					//get the option and price				
					PNWithoutDiscount=b2cPage.PB_OptionCheckboxWithoutDiscount.get(0).getAttribute("value");
					PNWithDiscount=b2cPage.PB_OptionCheckboxWithDiscount.get(0).getAttribute("value");
					Dailylog.logInfoDB(1,"PNWithoutDiscount"+PNWithoutDiscount,Store, testName);
					Dailylog.logInfoDB(1,"PNWithDiscount"+PNWithDiscount,Store, testName);
					
					while(OriginPriceWithDiscount==0){						
						String xpath="//input[@value='"+PNWithDiscount+"']/../div/del[@class='option-origin-price']";
						OriginPriceWithDiscount=GetPriceValue(driver.findElement(By.xpath(xpath)).getText());
						
						
						xpath="//input[@value='"+PNWithDiscount+"']/../div/span[@class='option-price']";
						PriceWithDiscount=GetPriceValue(driver.findElement(By.xpath(xpath)).getText());
												
						xpath="//input[@value='"+PNWithoutDiscount+"']/../div/span[@class='option-price']";
						PriceWithoutDiscount=GetPriceValue(driver.findElement(By.xpath(xpath)).getText());					
						
						b2cPage.Product_Continue.click();
					}
					Dailylog.logInfoDB(1,"OriginPriceWithDiscount"+OriginPriceWithDiscount,Store, testName);
					Dailylog.logInfoDB(1,"PriceWithDiscount"+PriceWithDiscount,Store, testName);
					Dailylog.logInfoDB(1,"PriceWithoutDiscount"+PriceWithoutDiscount,Store, testName);
					
				}
				
	            
			}
		  return map;
		
		
		
		
	}
	
	private void switchToWindow(int windowNo) {
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
	
	 
	private void searchProduct(String category, String productID) {
		Dailylog.logInfoDB(1,"Search Product",Store, testName);
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
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",hmcPage.Catalog_SearchButton);
		Common.sleep(3000);
		hmcPage.products_resultItem.click();
//		((JavascriptExecutor) driver).executeScript("arguments[0].click();",hmcPage.products_resultItem);
		Common.waitElementClickable(driver, hmcPage.Catalog_SearchButton, 5);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",hmcPage.Catalog_SearchButton);
		Common.sleep(3000);
		Common.waitElementClickable(driver, hmcPage.products_resultItem, 30);
		hmcPage.products_resultItem.click();
		Common.sleep(3000);
	}
	
	
	private void addOption(HMCPage hmcPage,String Option) throws InterruptedException{
		Dailylog.logInfoDB(1,"Add Options",Store, testName);
		Common.rightClick(driver, hmcPage.product_preselectedOptionsTable);
		Common.sleep(3000);
		//hmcPage.product_CreateProductOptions.click();
		JavascriptExecutor js= (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", hmcPage.product_CreateProductOptions);
		Dailylog.logInfoDB(1,"Create Product Options",Store, testName);
		Common.waitElementVisible(driver, hmcPage.product_preselecteOptionsSearch);
		hmcPage.product_preselecteOptionsSearch.click();
		switchToWindow(2);
//		Common.waitElementVisible(driver, hmcPage.products_PB_code);
		Common.sleep(3000);
		hmcPage.products_PB_code.clear();
		hmcPage.products_PB_code.sendKeys(Option);
		Select catalog = new Select(hmcPage.products_PB_catalogVersionSel);
		catalog.selectByVisibleText("masterMultiCountryProductCatalog - Online");
//		hmcPage.products_PB_searchBtn.click();
		Common.javascriptClick(driver, hmcPage.products_PB_searchBtn);
		By targetAcc = By.xpath("//div[contains(@id,'" + Option + "')]");
		Common.waitElementClickable(driver, driver.findElement(targetAcc), 5);
		driver.findElement(targetAcc).click();
		hmcPage.products_PB_useBtn.click();
		switchToWindow(1);
		//hmcPage.products_PB_saveBtn.click();
		Common.javascriptClick(driver, hmcPage.products_PB_saveBtn);
	}
	
	private void addGroupItem(String Option1,String Option2,String productID,String country, String channel)
			throws InterruptedException {
		//driver.get(testData.HMC.getHomePageUrl());
		Common.NavigateToUrl(driver, "IE",testData.HMC.getHomePageUrl());
		Common.NavigateToUrl(driver, Browser,testData.HMC.getHomePageUrl());
		Common.sleep(3000);
		HMCCommon.Login(hmcPage, testData);
		searchProduct("online",productID);
		Common.sleep(3000);
		Common.waitElementClickable(driver, hmcPage.Catalog_multiCountry, 150);
		hmcPage.Catalog_multiCountry.click();
		String xpath = "//div[contains(@id,'ItemDisplay')][contains(.,'" + channel
						+ "')]/../../td[contains(.,'" + Store + "')]";
		if(Common.checkElementDisplays(driver, By.xpath(xpath), 5)){
			Common.doubleClick(driver, driver.findElement(By.xpath(xpath)));
			switchToWindow(1);			
		}else{
			Common.rightClick(driver, hmcPage.product_productBuilderTable);
			hmcPage.product_CreateProductBuilder.click();
			switchToWindow(1);
			// select country
			hmcPage.preselectedOptions_Country.click();
			By countryX = By.xpath("//select/option[contains(text(),'" + country + "')]");
			driver.findElement(countryX).click();
			Dailylog.logInfoDB(1,"Selected Country: " + country,Store, testName);
			// select channel
			hmcPage.preselectedOptions_Chanel.click();
			hmcPage.preselectedOptions_channelB2C.click();
			Dailylog.logInfoDB(1,"selected channel B2C",Store, testName);		
		}
		String xpath1 = "//input[contains(@value,'"+Option1+"')][contains(@value,'masterMultiCountryProductCatalog - Online')]";
		if (!Common.checkElementDisplays(driver, By.xpath(xpath1), 10)) {
			addOption(hmcPage,Option1);
		}		
		String xpath2 = "//input[contains(@value,'"+Option2+"')][contains(@value,'masterMultiCountryProductCatalog - Online')]";
		if (!Common.checkElementDisplays(driver, By.xpath(xpath1), 10)) {
			addOption(hmcPage,Option1);
		}
		if (!Common.checkElementDisplays(driver, By.xpath(xpath2), 10)) {
			addOption(hmcPage,Option2);
		}
		driver.close();
		switchToWindow(0);
		hmcPage.hmcHome_hmcSignOut.click();
		
	}
	
	public float GetPriceValue(String Price) {
		if (Price.contains("FREE")||Price.contains("INCLUDED")){
			return 0;
		}
		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("HK", "");
		Price = Price.replaceAll("SG", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.trim();
		String pattern = "\\d+\\.*\\d*";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(Price);
    	float priceValue;
        if (m.find( )) {
    		priceValue = Float.parseFloat( m.group());
    		return priceValue;
        } else {
        	return 0;
        }
	}

}
