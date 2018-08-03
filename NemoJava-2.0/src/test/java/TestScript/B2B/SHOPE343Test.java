package TestScript.B2B;

import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2BPunchoutPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;



public class SHOPE343Test extends SuperTestClass {
	public String HMCURL;
	public HMCPage hmcPage ;
	public B2BPage b2bPage;
	public String url="/le/1213286247/au/en/1213286247/";
	public String partNo="10FGS00F0L";
	public String login="zhaoss5@lenovo.com";
	public String pwd="1q2w3e4r";

	public SHOPE343Test(String Store){
		this.Store = Store;
		this.testName = "SHOPE-343";
	}
	
	private double String2Num(String valueString) {
		String price = valueString.replace("$", "").replace("￥", "").replace(",", "");
		return Double.parseDouble(price);
	}
	
	public void checkCVPrice(HMCPage hmcPage,String C1,String C2,String V1,String V2,String gap,String partNo) {
		System.out.println("check CV list price");
		double price1;
		double price2;
		String currency="";
		String material=partNo.substring(0, 9);
		
		if(Store=="AU") {
			currency="Australian Dollar";
		}else if(Store=="US") {
			currency="US Dollar";
		}else if(Store=="JP") {
			currency="Japanese Yen";
		}
		
		//HMC->Price setting->price feed
		try {
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.loginPricingCockpit(driver, hmcPage, testData);
			hmcPage.PriceSetting_PriceFeed.click();
			Common.sleep(3000);		
			hmcPage.PrieFeed_CVListPrice.click();
			
			if(V1.contains("NONE")) {
				price1=0;
			}else {				
				//Fiter CV
				price1=filterListPrice(hmcPage,currency,C1,V1);
			}
			if(V2.contains("NONE")) {
				price2=0;
			}else {				
				//Fiter CV
				price2=filterListPrice(hmcPage,currency,C2,V2);
			}
			if(gap.contains("add")) {
				Assert.assertEquals(price2-price1,String2Num(gap.replace("add", "").replace("[", "").replace("]", "")) , 0.1);
			}			
		}catch(Exception e) {
			e.printStackTrace();
		}
				
		
	}
	public double filterListPrice(HMCPage hmcPage,String currency,String C,String V) {
		double price;
		try {
			//Fiter CV
			String xpath = "//span[text()='" + currency + "' and @class='select2-match']/../../*[not(text())]";
			HMCCommon.fillRuleInfo(driver, hmcPage, currency, hmcPage.B2BpriceSimulate_CurrencyButton, xpath);
			hmcPage.CVListPrice_Material.clear();
			hmcPage.CVListPrice_Material.sendKeys(partNo);
			hmcPage.CVListPrice_CInput.clear();
			hmcPage.CVListPrice_CInput.sendKeys(C);
			hmcPage.CVListPrice_VInput.clear();
			hmcPage.CVListPrice_VInput.sendKeys(V);
			price= String2Num(hmcPage.CVListPrice_WebPrice.get(0).getText());
			return price;
			
		}catch(Exception e) {
			e.printStackTrace();
			return 0;		

		}
		
	}
	
	@Test(alwaysRun = true, groups = {"shopgroup","pricingpromot","p2","b2b"})
	public void NA9729(ITestContext ctx){
		try{
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2bPage = new B2BPage(driver);
			
			//Step 1: HMC setting
			Dailylog.logInfoDB(1,"Login HMC and Setting on B2B Unit.", Store,testName);
			driver.get(testData.HMC.getHomePageUrl());
			Common.sleep(5000);
			HMCCommon.Login(hmcPage, testData);
			
			HMCCommon.searchB2BUnit(hmcPage, testData);
			Common.sleep(5000);
			hmcPage.B2BUnit_siteAttribute.click();
			Common.sleep(50000);
			System.out.println("Navigate to B2B Commerce ->B2B UNIT->Site Attribute");
			
			System.out.println("Setting toggle");
			hmcPage.AccessoryAddToggle.click();
			hmcPage.SiteAttribute_CustomiseBuyToggle.click();
			hmcPage.SiteAttribute_CustomiseBuyForCMToggle.click();
			hmcPage.SiteAttribute_enableProductBuilder.click();
			hmcPage.SaveButton.click();
			System.out.println("Saving button click");
			
			//Step 2:Login with front B2B web site
			Dailylog.logInfoDB(2,"Login with front B2B web site", Store,testName);
			String b2burl=testData.B2B.getHomePageUrl().substring(0,testData.B2B.getHomePageUrl().indexOf("com")+3)+url;
			driver.get(b2burl+"login");
			B2BCommon.Login(b2bPage, login,pwd);
			
			//clear cart
			Dailylog.logInfoDB(3,"Go to cart and clear cart", Store,testName);
			driver.get(b2burl+"cart");
			if(Common.checkElementDisplays(driver, b2bPage.CartPage_EmptyCartButton, 10)) {
				b2bPage.CartPage_EmptyCartButton.click();
			}
					
			
			//check PLP page
//			driver.get(b2burl);
//			b2bPage.HomePage_productsLink.click();
//			driver.findElement(By.xpath("(//a[@class='products_submenu'])[1]")).click();
//			driver.findElement(By.xpath("//h3[contains(text(),'Contract')]")).click();
//			Assert.assertTrue(Common.checkElementDisplays(driver, b2bPage.PDPPage_AddAccessories, 10));		
			
			//Step 4:Open GA CTO product PDP page
			Dailylog.logInfoDB(4,"Open GA CTO product PDP page", Store,testName);
			driver.get(b2burl+"/p/"+partNo);
			Common.sleep(5000);
			String B2BpriceText = b2bPage.PDPPage_ProductPrice.getText().toString();
			Dailylog.logInfoDB(5,"MTM PDP price:"+ B2BpriceText, Store, testName);
			
			String pdpURL = driver.getCurrentUrl();
			// get product number
			String partNumberMTM = pdpURL.substring(pdpURL.lastIndexOf('/') + 1, pdpURL.length());
			Dailylog.logInfoDB(6,"MTM part Number: "+ partNumberMTM, Store, testName);
			
			//Step 5:Click "Customize And Buy" Button
			b2bPage.laptops_ctoCustomizeAndBuyButton.click();
			Common.sleep(50000);
			String ctoURL = driver.getCurrentUrl();
			// get product number
			String partNumberCTO = ctoURL.substring(ctoURL.lastIndexOf('/') + 1, ctoURL.length());
			Dailylog.logInfoDB(7,"CTO Product Number: "+ partNumberCTO, Store, testName);
			Assert.assertTrue(partNumberCTO.contains("CTO"));
			
			//check CV price 
			String groupid="";
			List<WebElement> elems=driver.findElements(By.xpath("//div[@id='ctoConfigurator']/div/div[@class='selection-result  true']"));
			for(int i=0;i<elems.size();i++) {
				String str=elems.get(i).getAttribute("id");
				groupid=str.substring(str.lastIndexOf('_')+1, str.length());
				System.out.println("Group ID:"+groupid);
				String xpath="//tbody[@id='"+groupid+"']/tr[contains(@class,'comp-item') and contains(@class,'visible')]";
				List<WebElement> eles=driver.findElements(By.xpath(xpath));
				if(eles.size()>1) {
					break;
				}
				
			}
			
			String xpath1="//tbody[@id='"+groupid+"']/tr[contains(@class,'comp-item') and contains(@class,'visible')]/td/input[@checked='checked']/../a";
			String xpath2="//tbody[@id='"+groupid+"']/tr[contains(@class,'comp-item') and contains(@class,'visible')]/td/input[@checked='checked']/../../following-sibling::tr/td[@class='price']";
			String xpath3="//tbody[@id='"+groupid+"']/tr[contains(@class,'comp-item') and contains(@class,'visible')]/td/input[@checked='checked']/../../following-sibling::tr/td/a";
			
			String C1= driver.findElement(By.xpath(xpath1)).getAttribute("val").split(":")[0];
			String V1= driver.findElement(By.xpath(xpath1)).getAttribute("val").split(":")[1];
			String C2= driver.findElement(By.xpath(xpath3)).getAttribute("val").split(":")[0];
			String V2= driver.findElement(By.xpath(xpath3)).getAttribute("val").split(":")[1];
			String gapPrice=driver.findElement(By.xpath(xpath2)).getText();
			checkCVPrice(hmcPage,C1,C2,V1,V2,gapPrice,partNumberCTO);
			
			//click add to cart
			b2bPage.PDP_addToCart.click();
			if(Common.checkElementDisplays(driver, b2bPage.PDPPage_WebPrice, 15)) {
				String PBpriceText2 = b2bPage.PDPPage_WebPrice.getText().toString();
				Dailylog.logInfoDB(8,"PB price:"+ PBpriceText2, Store, testName);
				Assert.assertEquals(String2Num(B2BpriceText), String2Num(PBpriceText2));
				
				while(Common.checkElementDisplays(driver, By.xpath("//button[@class='pricingSummary-button button-called-out button-full ']"), 15)){
					driver.findElement(By.xpath("//button[@class='pricingSummary-button button-called-out button-full ']")).click();
				}

			}
					
			//check the cart price and part number
			Dailylog.logInfoDB(9,"Cart partNumberMTM ："+ b2bPage.CartPage_OnlyPartnum.getText(), Store, testName);
			Dailylog.logInfoDB(10,"Cart Price:"+ b2bPage.cartPage_FirstItermPrice.getText(), Store, testName);
			Assert.assertEquals(b2bPage.CartPage_OnlyPartnum.getText(), partNumberMTM);
			Assert.assertEquals(String2Num(b2bPage.cartPage_FirstItermPrice.getText()), String2Num(B2BpriceText));
			
			//click Edit button
			b2bPage.cartPage_EditButton.get(0).click();
			Common.sleep(50000);
			pdpURL = driver.getCurrentUrl();
			// get product number
			partNumberCTO = pdpURL.substring(pdpURL.lastIndexOf('/') + 1, pdpURL.length());
			Dailylog.logInfoDB(11,"Product Number after edit "+ partNumberCTO, Store, testName);
			Assert.assertTrue(partNumberCTO.contains("CTO"));
			
			//update CV and check the price
			Dailylog.logInfoDB(12,"Update CV and check price", Store, testName);
			
			List<WebElement> es=driver.findElements(By.xpath("//div[@id='ctoConfigurator']/div/div[@class='selection-result  true']"));
			for(int i=es.size()-1;i>=0;i--) {
				String str=es.get(i).getAttribute("id");
				groupid=str.substring(str.lastIndexOf('_')+1, str.length());
				System.out.println("Group ID:"+groupid);
				String xpath="//tbody[@id='"+groupid+"']/tr[contains(@class,'comp-item') and contains(@class,'visible')]";
				List<WebElement> eles=driver.findElements(By.xpath(xpath));
				if(eles.size()>1) {
					break;
				}
				
			}
			xpath1="//tbody[@id='"+groupid+"']/tr[contains(@class,'comp-item') and contains(@class,'visible')]/td/input[contains(@class,'checked')]/../../following-sibling::tr/td/input";
			xpath2="//tbody[@id='"+groupid+"']/tr[contains(@class,'comp-item') and contains(@class,'visible')]/td/input[contains(@class,'checked')]/../../following-sibling::tr/td[@class='price']";
			double gapprice=GetPriceValue(driver.findElement(By.xpath(xpath2)).getText());
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.xpath(xpath1)));
			Common.sleep(30000);
			double updatedPrice=String2Num(b2bPage.PDPPage_OrderTotalPrice.getText());
			Dailylog.logInfoDB(13,"CV gap price:"+gapprice, Store, testName);
			Dailylog.logInfoDB(14,"Update web Price:"+updatedPrice, Store, testName);
			Assert.assertEquals(updatedPrice, gapPrice+ String2Num(B2BpriceText));
			
			//click add to cart
			b2bPage.PDP_addToCart.click();
			if(Common.checkElementDisplays(driver, b2bPage.PDPPage_WebPrice, 15)) {
				String PBpriceText2 = b2bPage.PDPPage_WebPrice.getText().toString();
				Dailylog.logInfoDB(15,"PB price after updated CV:"+ PBpriceText2, Store, testName);
				Assert.assertEquals(updatedPrice, String2Num(PBpriceText2));
				
				while(Common.checkElementDisplays(driver, By.xpath("//button[@class='pricingSummary-button button-called-out button-full ']"), 15)){
					driver.findElement(By.xpath("//button[@class='pricingSummary-button button-called-out button-full ']")).click();
				}

			}
			
			//check the cart price and part number
			Assert.assertEquals(driver.findElements(By.xpath("//p[@class='cart-item-partNumber']/span")).get(1).getText(), partNumberCTO);
			Assert.assertEquals(String2Num(b2bPage.cartPage_FirstItermPrice.getText()), updatedPrice);					
			
		}catch(Throwable e){
			handleThrowable(e, ctx);			
		}
		
	}
	
	public double GetPriceValue(String Price) {
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
		Price = Price.replaceAll("[", "");
		Price = Price.replaceAll("]", "");
		Price = Price.replaceAll("add", "");
		Price = Price.trim();
		String pattern = "\\d+\\.*\\d*";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(Price);
    	double priceValue;
        if (m.find( )) {
    		priceValue = Float.parseFloat( m.group());
    		return priceValue;
        } else {
        	return 0;
        }
	}
	
	

}

