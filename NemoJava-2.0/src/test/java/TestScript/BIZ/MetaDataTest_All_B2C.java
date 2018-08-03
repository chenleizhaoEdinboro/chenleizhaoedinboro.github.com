package TestScript.BIZ;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Pages.B2CPage;
import TestData.PropsUtils;
import TestData.TestData;
import TestScript.BIZ.PAGECHECK.CommonFunctions;


public class MetaDataTest_All_B2C {

	private WebDriver driver;
	private String Url;
	private String gaming_Url;
	private String SMB_Url;
	private String outlet_Url;
	private String AddressLine1;
	private String City;
	private String state;
	private String zipCode;
	private String phoneNumber;
	
	
	
	
	private String expectedValue;
	private String RowIndex;
	private static ArrayList<String[]> outputData = new ArrayList<String[]>();
	private String result;
	private TestData testData;
	private String Environment;
	
	private B2CPage b2cPage;
	
	String startCloumn = "2";
	
	private String ignore = "ignore";
	
	
	
	//---------------page----------------
	String currencycode = "";
	String language = "";
	String country = "";
	String storeID = "";
	String BPID = "";
	String TaxononmyType = "";
	String salesType = "";
	String pageTitle = "";
	String pageName = "";
	String pageBreadcrumb = "";
	
	
	
	//---------------ExpectedResults--------------------
	
	String currencycode_Expec = "";
	String language_Expec = "";
	String country_Expec = "";
	String storeID_Expec = "";
	String BPID_Expec = "";
	//String TaxononmyType_Expec = "";
	String salesType_Expec = "";
	String pageTitle_Expec = "";
	String pageName_Expec = "";
	String pageBreadcrumb_Expec = "";
	
	
	

	public MetaDataTest_All_B2C(String rowIndex, String homePage_url,String gaming_Url,String SMB_Url, String outlet_Url,String AddressLine1,String City,String state,String zipCode,String phoneNumber) {
		this.Url = homePage_url;
		this.RowIndex = rowIndex;
		this.gaming_Url = gaming_Url;
		this.SMB_Url = SMB_Url;
		this.outlet_Url = outlet_Url;
		
		this.AddressLine1 = AddressLine1;
		this.City = City;
		this.state = state;
		this.zipCode = zipCode;
		this.phoneNumber = phoneNumber;
	}
	
	CommonFunctions Common = new CommonFunctions();
	
	
	
	@BeforeClass
	private void setupDriver() {
		
		Common.getConnection();
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		
		driver.manage().timeouts().pageLoadTimeout(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@Test
	public void MetaData_Test() {
		
		// b2c metadata testing 
		b2cPage = new B2CPage(driver);
		
		
		/*
		 * Homepage
		 * */
		//1.	Go to www3.lenovo.com/us/en/
		try{
			
			try{
				redirectedToUrl(driver,this.Url);
				
				allPage_test("Homepage","Home Page");

			}catch(Exception e){
				e.printStackTrace();
				System.out.println("url is :" + Url + "#####" + "Homepage check Exception");
				result = "Blocking";
				String page = "Home Page";
				String TaxononmyType_Expec = "Homepage";
				String nameValue = "taxonomytype";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				saveResult(Url,page,nameValue,target,TaxononmyType_Expec,result);

			}
			
			/*
			 * Splitter
			 * */
			
			//TaxononmyType
			try{
				redirectedToUrl(driver,this.Url);
				
				NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Laptops);
				
//				clickElement(driver,b2cPage.Products_Link);
//				clickElement(driver,b2cPage.laptops_subProduct);
				
				allPage_test("splitterpage","Splitter page");
				
				//ProductType
				try{
					String productType_Expec = "PMI";
					String page = "Splitter page";
					String nameValue = "producttype";
					String pageContent = driver.getPageSource().toString();
					String target = getContent(pageContent,nameValue);
					if(target.equals(productType_Expec)){
						result = "Pass";
					}else{
						result = "Fail";
					}
					
					saveResult(Url,page,nameValue,target,productType_Expec,result);

				}catch(Exception e){
					e.printStackTrace();
					String productType_Expec = "PMI";
					String page = "Splitter page";
					String nameValue = "producttype";
					String pageContent = driver.getPageSource().toString();
					String target = getContent(pageContent,nameValue);
					result = "Block";
					saveResult(Url,page,nameValue,target,productType_Expec,result);
				}
				
			}catch(Exception e){
				e.printStackTrace();
				String pageContent = driver.getPageSource().toString();
				String nameValue = "taxonomytype";
				result = "Blocking";
				String target = getContent(pageContent,nameValue);
				String TaxononmyType_Expec = "splitterpage";
				String page = "Splitter page";
				saveResult(Url,page,nameValue,target,TaxononmyType_Expec,result);
			}
			
		
			
			
			/*
			 * Brand
			 * 
			 * */
			
			//taxonomytype
			try{
				redirectedToUrl(driver,this.Url);
				NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Laptops);
				
//				clickElement(driver,b2cPage.Products_Link);
//				clickElement(driver,b2cPage.laptops_subProduct);
				clickElement(driver,b2cPage.thinkpad_brand);
				
				allPage_test("brandpage","Brand Page");
				
				//ProductType
				try{
					String productType_Expec = "PMI";
					String page = "Brand Page";
					String nameValue = "producttype";
					String pageContent = driver.getPageSource().toString();
					String target = getContent(pageContent,nameValue);
					if(target.equals(productType_Expec)){
						result = "Pass";
					}else{
						result = "Fail";
					}
					
					saveResult(Url,page,nameValue,target,productType_Expec,result);

				}catch(Exception e){
					e.printStackTrace();
					String productType_Expec = "PMI";
					String page = "Brand Page";
					String nameValue = "producttype";
					String pageContent = driver.getPageSource().toString();
					String target = getContent(pageContent,nameValue);
					result = "Block";
					saveResult(Url,page,nameValue,target,productType_Expec,result);
				}
				
				//Brand
				try{
					String Brand_Expec = "ThinkPad";
					String page = "Brand Page";
					String nameValue = "brand";
					String pageContent = driver.getPageSource().toString();
					String target = getContent(pageContent,nameValue);
					
					if(target.equals(Brand_Expec)){
						result = "Pass";
					}else{
						result = "Fail";
					}
					saveResult(Url,page,nameValue,target,Brand_Expec,result);
					
				}catch(Exception e){
					e.printStackTrace();
					String Brand_Expec = "ThinkPad";
					String page = "Brand Page";
					String nameValue = "brand";
					String pageContent = driver.getPageSource().toString();
					String target = getContent(pageContent,nameValue);
					result = "Block";
					saveResult(Url,page,nameValue,target,Brand_Expec,result);
				}
		
			}catch(Exception e){
				e.printStackTrace();
				String pageContent = driver.getPageSource().toString();
				String nameValue = "taxonomytype";
				result = "Blocking";
				String target = getContent(pageContent,nameValue);
				String page = "Brand Page";
				String TaxononmyType_Expec = "brandpage";
				saveResult(Url,page,nameValue,target,TaxononmyType_Expec,result);
			}	

	
			
			/*
			 * Series
			 * 
			 */
			//taxonomytype
			try{
				redirectedToUrl(driver,this.Url);
				
				NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Laptops);
//				clickElement(driver,b2cPage.Products_Link);
//				clickElement(driver,b2cPage.laptops_subProduct);
				clickElement(driver,b2cPage.thinkpad_brand);
				clickElement(driver,b2cPage.thinkpadX_series);
				
				allPage_test("seriespage","Series Page");
				
				//ProductType
				try{
					String productType_Expec = "PMI";
					String page = "Series Page";
					String nameValue = "producttype";
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					if(target.equals(productType_Expec)){
						result = "Pass";
					}else{
						result = "Fail";
					}
					
					saveResult(Url,page,nameValue,target,productType_Expec,result);

				}catch(Exception e){
					e.printStackTrace();
					String productType_Expec = "PMI";
					String page = "Series Page";
					String nameValue = "producttype";
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					result = "Block";
					saveResult(Url,page,nameValue,target,productType_Expec,result);
				}
				
				//Brand
				try{
					String Brand_Expec = "ThinkPad";
					String page = "Series Page";
					String nameValue = "brand";
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					
					if(target.equals(Brand_Expec)){
						result = "Pass";
					}else{
						result = "Fail";
					}
					saveResult(Url,page,nameValue,target,Brand_Expec,result);
					
				}catch(Exception e){
					e.printStackTrace();
					String Brand_Expec = "ThinkPad";
					String page = "Series Page";
					String nameValue = "brand";
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					result = "Block";
					saveResult(Url,page,nameValue,target,Brand_Expec,result);
				}
				
				// Series
				
				try{
					String Series_Expec = "X Series";
					String page = "Series Page";
					String nameValue = "series";
					String pageContent = getPageSourceContent(driver);
					
					String target = getContent(pageContent,nameValue);
					
					if(target.equals(Series_Expec)){
						result = "Pass";
					}else{
						result = "Fail";
					}
					saveResult(Url,page,nameValue,target,Series_Expec,result);

				}catch(Exception e){
					e.printStackTrace();
					String Series_Expec = "ThinkPad";
					String page = "Series Page";
					String nameValue = "series";
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					result = "Block";
					saveResult(Url,page,nameValue,target,Series_Expec,result);
					
				}

				//productImpressions
				
				try{
					String productImpressions_Expec = "";
					String page = "Series Page";
					String nameValue = "productImpressions";
					
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					
					if(target.equals(productImpressions_Expec)){
						result = "Pass";
					}else{
						result = "Fail";
					}
					saveResult(Url,page,nameValue,target,productImpressions_Expec,result);
		
				}catch(Exception e){
					e.printStackTrace();
					String productImpressions_Expec = "";
					String page = "Series Page";
					String nameValue = "productImpressions";
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					result = "Block";
					saveResult(Url,page,nameValue,target,productImpressions_Expec,result);
				}
				
				//subseriesPHimpressions
				ArrayList<String> al = new ArrayList<String>();
				try{
					String currentUrl = getCurrentUrl(driver);
					List<WebElement> lists = getElementList("//div[contains(@class,'seriesListings-item')]/div[@class='seriesListings-footer']/a");
					
					for(int x = 1; x<=lists.size(); x++){
						clickElement(driver,driver.findElement(By.xpath("(//div[contains(@class,'seriesListings-item')]/div[@class='seriesListings-footer']/a)["+x+"]")));
						String detailUrl = getCurrentUrl(driver);
						String[] strs = detailUrl.split("/");
						String part = strs[strs.length-1];
						al.add(part);
						redirectedToUrl(driver,currentUrl);
					}
					
					String subseriesPHimpressions_Expec = al.toString().replace("[", "").replace("]", "");
					subseriesPHimpressions_Expec = subseriesPHimpressions_Expec.replace(" ", "");
					String page = "Series Page";
					String nameValue = "subseriesPHimpressions";
					
					
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					
					System.out.println("target is :" + target);
					System.out.println("subseriesPHimpressions_Expec is :" + subseriesPHimpressions_Expec);
					
					
					if(target.equals(subseriesPHimpressions_Expec)){
						result = "Pass";
					}else{
						result = "Fail";
					}
					
					saveResult(Url,page,nameValue,target,subseriesPHimpressions_Expec,result);
		
				}catch(Exception e){
					e.printStackTrace();
					String subseriesPHimpressions_Expec = al.toString().replace("[", "").replace("]", "");
					subseriesPHimpressions_Expec = subseriesPHimpressions_Expec.replace(" ", "");
					String page = "Series Page";
					String nameValue = "subseriesPHimpressions";
					
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					
					result = "Block";
					saveResult(Url,page,nameValue,target,subseriesPHimpressions_Expec,result);
				}
				
				//seriesPHcode
				try{
					String currentUrl = getCurrentUrl(driver);
					
					String[] strs = currentUrl.split("/");
					String str = strs[strs.length-1];
					
					String seriesPHcode_Expec = str;
					String page = "Series Page";
					String nameValue = "seriesPHcode";
					
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					
					if(target.equals(seriesPHcode_Expec)){
						result = "Pass";
					}else{
						result = "Fail";
					}
					
					saveResult(Url,page,nameValue,target,seriesPHcode_Expec,result);
					
					
				}catch(Exception e){
					e.printStackTrace();
					String currentUrl = getCurrentUrl(driver);
					
					String[] strs = currentUrl.split("/");
					String str = strs[strs.length-1];
					
					String seriesPHcode_Expec = str;
					String page = "Series Page";
					String nameValue = "seriesPHcode";
					
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					result = "Block";
					
					saveResult(Url,page,nameValue,target,seriesPHcode_Expec,result);
				}
		
			}catch(Exception e){
				e.printStackTrace();
				String pageContent = getPageSourceContent(driver);
				String nameValue = "taxonomytype";
				result = "Blocking";
				String target = getContent(pageContent,nameValue);
				String page = "Series Page";
				String TaxononmyType_Expec = "seriespage";
				saveResult(Url,page,nameValue,target,TaxononmyType_Expec,result);
			}	
			

			
			
			
			/*
			 * 
			 * Subseries
			 * 
			 * */
			
			//taxonomytype
			try{
				redirectedToUrl(driver,this.Url);
				NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Laptops);
				
//				clickElement(driver,b2cPage.Products_Link);
//				clickElement(driver,b2cPage.laptops_subProduct);
				clickElement(driver,b2cPage.thinkpad_brand);
				clickElement(driver,b2cPage.thinkpadX_series);
			
				clickElement(driver,b2cPage.thinkpad_subSeries);
				
				allPage_test("subseriespage","Sub Series page");
				
				//ProductType
				try{
					String productType_Expec = "PMI";
					String page = "Sub Series page";
					String nameValue = "producttype";
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					if(target.equals(productType_Expec)){
						result = "Pass";
					}else{
						result = "Fail";
					}
					
					saveResult(Url,page,nameValue,target,productType_Expec,result);

				}catch(Exception e){
					e.printStackTrace();
					String productType_Expec = "PMI";
					String page = "Sub Series page";
					String nameValue = "producttype";
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					result = "Block";
					saveResult(Url,page,nameValue,target,productType_Expec,result);
				}
				
				//Brand
				try{
					String Brand_Expec = "ThinkPad";
					String page = "Sub Series page";
					String nameValue = "brand";
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					
					if(target.equals(Brand_Expec)){
						result = "Pass";
					}else{
						result = "Fail";
					}
					saveResult(Url,page,nameValue,target,Brand_Expec,result);
					
				}catch(Exception e){
					e.printStackTrace();
					String Brand_Expec = "ThinkPad";
					String page = "Sub Series page";
					String nameValue = "brand";
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					result = "Block";
					saveResult(Url,page,nameValue,target,Brand_Expec,result);
				}
				
				// Series
				
			try{
				String Series_Expec = "X Series";
				String page = "Sub Series page";
				String nameValue = "series";
				String pageContent = getPageSourceContent(driver);
				
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(Series_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,Series_Expec,result);

			}catch(Exception e){
				e.printStackTrace();
				String Series_Expec = "ThinkPad";
				String page = "Sub Series page";
				String nameValue = "series";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				result = "Block";
				saveResult(Url,page,nameValue,target,Series_Expec,result);
				
			}
			
			//Item Type
			
			try{
				String ItemType_Expec = "CTO";
				String page = "Sub Series page";
				String nameValue = "itemtype";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
			
				if(target.equals(ItemType_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,ItemType_Expec,result);	
				
			}catch(Exception e){
				
				e.printStackTrace();
				String ItemType_Expec = "CTO";
				String page = "Sub Series page";
				String nameValue = "itemtype";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,ItemType_Expec,result);	
			}

			//	Friendly Name
			try{
				String FriendlyName = getElementText(driver,driver.findElement(By.xpath("//*[@id='longscroll-subseries']/div/h1[@itemprop]")));
				String FriendlyName_Expec = FriendlyName.replace("ThinkPad", "").trim();

				String page = "Sub Series page";
				String nameValue = "friendlyname";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(FriendlyName_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				
				saveResult(Url,page,nameValue,target,FriendlyName_Expec,result);

			}catch(Exception e){
				e.printStackTrace();
				String FriendlyName = getElementText(driver,driver.findElement(By.xpath("//*[@id='longscroll-subseries']/div/h1[@itemprop]")));
				String FriendlyName_Expec = FriendlyName.replace("ThinkPad", "").trim();
				String page = "Sub Series page";
				String nameValue = "friendlyname";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				result = "Block";
				
				saveResult(Url,page,nameValue,target,FriendlyName_Expec,result);

			}
				
			
			//Product Description
			try{
				String Product_Description_Expec = "";
				String page = "Sub Series page";
				String nameValue = "productdescription";
				String pageContent = getPageSourceContent(driver);
				
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(Product_Description_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,Product_Description_Expec,result);
		
			}catch(Exception e){
				
				String Product_Description_Expec = "";
				String page = "Sub Series page";
				String nameValue = "productdescription";
				String pageContent = getPageSourceContent(driver);
				
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				
				saveResult(Url,page,nameValue,target,Product_Description_Expec,result);
			}
			
			// Product Status
			
			try{
				
				String ProductStatus_Expec = "";
				
				String page = "Sub Series page";
				String nameValue = "productstatus";
				String pageContent = getPageSourceContent(driver);
				
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(ProductStatus_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,ProductStatus_Expec,result);

			}catch(Exception e){
				e.printStackTrace();
				
				String ProductStatus_Expec = "";
				
				String page = "Sub Series page";
				String nameValue = "productstatus";
				String pageContent = getPageSourceContent(driver);
				
				String target = getContent(pageContent,nameValue);
				result = "Block";
				
				saveResult(Url,page,nameValue,target,ProductStatus_Expec,result);
				
			}
			//Inventory
			
			try{
				String Inventory_Expec = "0";
				String page = "Sub Series page";
				String nameValue = "inventory";
				String pageContent = getPageSourceContent(driver);
				
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(Inventory_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,Inventory_Expec,result);
		
			}catch(Exception e){
				e.printStackTrace();
				String Inventory_Expec = "";
				String page = "Sub Series page";
				String nameValue = "inventory";
				String pageContent = getPageSourceContent(driver);
				
				String target = getContent(pageContent,nameValue);
				result = "Block";
				saveResult(Url,page,nameValue,target,Inventory_Expec,result);
			}
			
			//productid
			try{
				String currentUrl = getCurrentUrl(driver);
				String[] strs = currentUrl.split("/");
				String productValue = strs[strs.length-1];
						
				String productid_Expec = productValue;
				String page = "Sub Series page";
				String nameValue = "productid";
				
				String pageContent = getPageSourceContent(driver);
				
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(productid_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,productid_Expec,result);
		
			}catch(Exception e){
				String currentUrl = getCurrentUrl(driver);
				String[] strs = currentUrl.split("/");
				String productValue = strs[strs.length-1];
						
				String productid_Expec = productValue;
				String page = "Sub Series page";
				String nameValue = "productid";
				
				String pageContent = getPageSourceContent(driver);
				
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,productid_Expec,result);
			}
			
			//bundleid
			try{
				String currentUrl = getCurrentUrl(driver);
				String[] strs = currentUrl.split("/");
				String productValue = strs[strs.length-1];
				String bundleid_Expec = productValue;
				String page = "Sub Series page";
				String nameValue = "bundleid";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(bundleid_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,bundleid_Expec,result);
		
			}catch(Exception e){
				
				e.printStackTrace();
				String currentUrl = getCurrentUrl(driver);
				String[] strs = currentUrl.split("/");
				String productValue = strs[strs.length-1];
				String bundleid_Expec = productValue;
				String page = "Sub Series page";
				String nameValue = "bundleid";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				
				saveResult(Url,page,nameValue,target,bundleid_Expec,result);

			}
			
			
			//productprice
			try{
				String productPrice_Expec = "";
				String page = "Sub Series page";
				String nameValue = "productprice";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(productPrice_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,productPrice_Expec,result);
				
			}catch(Exception e){
				e.printStackTrace();
				String productPrice_Expec = "";
				String page = "Sub Series page";
				String nameValue = "productprice";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,productPrice_Expec,result);
			}
			
			//priceusd
			
			try{
				String priceusd_Expec = "";
				
				String page = "Sub Series page";
				String nameValue = "priceusd";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(priceusd_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,priceusd_Expec,result);
				
			}catch(Exception e){
				e.printStackTrace();
				String priceusd_Expec = "";
				
				String page = "Sub Series page";
				String nameValue = "priceusd";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				saveResult(Url,page,nameValue,target,priceusd_Expec,result);
				
			}
			
			//productcodeimpressions
			ArrayList<String> productCodeList =  new ArrayList<String>();
			
			try{
				List<WebElement> list = getElementList("//form/input[@name='productCodePost']");
			
				for(int x = 1; x<=list.size(); x++){
					String text = driver.findElement(By.xpath("(//form/input[@name='productCodePost'])["+x+"]")).getAttribute("value").toString();
					
					productCodeList.add(text);
				}
				
				String productcodeimpressions_Expec = productCodeList.toString();
				
				String page = "Sub Series page";
				String pageContent = getPageSourceContent(driver);
				String nameValue = "productcodeimpressions";
				
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(productcodeimpressions_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				
				saveResult(Url,page,nameValue,target,productcodeimpressions_Expec,result);
				
			}catch(Exception e){
				e.printStackTrace();
				String productcodeimpressions_Expec = productCodeList.toString();
				
				String page = "Sub Series page";
				String pageContent = getPageSourceContent(driver);
				String nameValue = "productcodeimpressions";
				
				String target = getContent(pageContent,nameValue);
				result = "Block";
				saveResult(Url,page,nameValue,target,productcodeimpressions_Expec,result);
			}
			
			
			//productcode
			try{
				String productcode_Expec = "";
				String page = "Sub Series page";
				String pageContent = getPageSourceContent(driver);
				String nameValue = "productcode";
				
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(productcode_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				
				saveResult(Url,page,nameValue,target,productcode_Expec,result);

			}catch(Exception e){
				e.printStackTrace();
				
				String productcode_Expec = "";
				String page = "Sub Series page";
				String pageContent = getPageSourceContent(driver);
				String nameValue = "productcode";
				
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,productcode_Expec,result);
			}
			
			//productname
			
			try{
				String productname = getElementText(driver,driver.findElement(By.xpath("//*[@id='longscroll-subseries']/div/h1[@itemprop]")));
				String productname_Expec = productname.replace("ThinkPad", "").trim();
				
				String page = "Sub Series page";
				String pageContent = getPageSourceContent(driver);
				String nameValue = "productname";
				
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(productname_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,productname_Expec,result);

			}catch(Exception e){
				e.printStackTrace();
				String productname = getElementText(driver,driver.findElement(By.xpath("//*[@id='longscroll-subseries']/div/h1[@itemprop]")));
				String productname_Expec = productname.replace("ThinkPad", "").trim();
				
				String page = "Sub Series page";
				String pageContent = getPageSourceContent(driver);
				String nameValue = "productname";
				
				String target = getContent(pageContent,nameValue);
				result = "Block";
				saveResult(Url,page,nameValue,target,productname_Expec,result);

			}
			
			//subseriesPHcode
			try{
				String currentUrl = getCurrentUrl(driver);
				String[] strs = currentUrl.split("/");
				String productValue = strs[strs.length-1];
				String subseriesPHcode_Expec = productValue;
				String page = "Sub Series page";
				String nameValue = "bundleid";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(subseriesPHcode_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,subseriesPHcode_Expec,result);
		
			}catch(Exception e){
				
				e.printStackTrace();
				String currentUrl = getCurrentUrl(driver);
				String[] strs = currentUrl.split("/");
				String productValue = strs[strs.length-1];
				String subseriesPHcode_Expec = productValue;
				String page = "Sub Series page";
				String nameValue = "bundleid";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				
				saveResult(Url,page,nameValue,target,subseriesPHcode_Expec,result);

			}
			
			//productInfo.configType
			
			try{
				String productInfoconfigType_Expec = "BaseProduct";
				String page = "Sub Series page";
				String pageContent = getPageSourceContent(driver);
				String nameValue = "productInfo.configType";
				
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(productInfoconfigType_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,productInfoconfigType_Expec,result);
				
			}catch(Exception e){
				e.printStackTrace();
				String productInfoconfigType_Expec = "BaseProduct";
				String page = "Sub Series page";
				String pageContent = getPageSourceContent(driver);
				String nameValue = "productInfo.configType";
				
				String target = getContent(pageContent,nameValue);
				result = "Block";
				saveResult(Url,page,nameValue,target,productInfoconfigType_Expec,result);
			}
			
			//productInfo.name
			
			try{
				String productname = getElementText(driver,driver.findElement(By.xpath("//*[@id='longscroll-subseries']/div/h1[@itemprop]")));
			
				String productInfoname_Expec = productname.replace("ThinkPad", "").trim();
				
				String page = "Sub Series page";
				String pageContent = getPageSourceContent(driver);
				String nameValue = "productname";
				
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(productInfoname_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,productInfoname_Expec,result);

			}catch(Exception e){
				 e.printStackTrace();
				 String productname = getElementText(driver,driver.findElement(By.xpath("//*[@id='longscroll-subseries']/div/h1[@itemprop]")));
				String productInfoname_Expec = productname.replace("ThinkPad", "").trim();
				
				String page = "Sub Series page";
				String pageContent = getPageSourceContent(driver);
				String nameValue = "productname";
				
				String target = getContent(pageContent,nameValue);
				result = "Block";
				
				saveResult(Url,page,nameValue,target,productInfoname_Expec,result); 
			}
			
			//productInfo.pageurl
			
			try{
				String productInfopageurl_Expec = getCurrentUrl(driver);
				String page = "Sub Series page";
				String pageContent = getPageSourceContent(driver);
				
				String nameValue = "productInfo.pageurl";
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(productInfopageurl_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,productInfopageurl_Expec,result); 
			}catch(Exception e){
				e.printStackTrace();
				String productInfopageurl_Expec = getCurrentUrl(driver);
				String page = "Sub Series page";
				String pageContent = getPageSourceContent(driver);
				
				String nameValue = "productInfo.pageurl";
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,productInfopageurl_Expec,result);
			}
			
			//productInfo.priceusd
			try{
				String productInfopriceusd_Expec = "";
				String page = "Sub Series page";
				String pageContent = getPageSourceContent(driver);
				String nameValue = "productInfo.priceusd";
				
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(productInfopriceusd_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,productInfopriceusd_Expec,result);
				
			}catch(Exception e){
				e.printStackTrace();
				String productInfopriceusd_Expec = "";
				String page = "Sub Series page";
				String pageContent = getPageSourceContent(driver);
				String nameValue = "productInfo.priceusd";
				
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,productInfopriceusd_Expec,result);
			}
			
			//productInfo.recid
			try{
				String productInforecid_Expec = "";
				String page = "Sub Series page";
				String pageContent = getPageSourceContent(driver);
				String nameValue = "productInfo.recid";
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(productInforecid_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				
				saveResult(Url,page,nameValue,target,productInforecid_Expec,result);

			}catch(Exception e){
				e.printStackTrace();
				String productInforecid_Expec = "";
				String page = "Sub Series page";
				String pageContent = getPageSourceContent(driver);
				String nameValue = "productInfo.recid";
				String target = getContent(pageContent,nameValue);
				result = "Block";
				
				saveResult(Url,page,nameValue,target,productInforecid_Expec,result);

			}
		
			}catch(Exception e){
				e.printStackTrace();
				String pageContent = getPageSourceContent(driver);
				String nameValue = "taxonomytype";
				result = "Blocking";
				String target = getContent(pageContent,nameValue);
				String page = "Sub Series page";
				String TaxononmyType_Expec = "subseriespage";
				saveResult(Url,page,nameValue,target,TaxononmyType_Expec,result);
			}	
		
			
		
		/*
		 * Configurator
		 * 
		 * */
		
		//taxonomytype
		try{
			redirectedToUrl(driver,this.Url);
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Laptops);
			
//			clickElement(driver,b2cPage.Products_Link);
//			clickElement(driver,b2cPage.laptops_subProduct);
			clickElement(driver,b2cPage.thinkpad_brand);
			clickElement(driver,b2cPage.thinkpadX_series);
		
			clickElement(driver,b2cPage.thinkpad_subSeries);
			clickElement(driver,b2cPage.viewOrCustomize);
			clickElement(driver,b2cPage.customizeButton);
			
			allPage_test("Configurator","Configurator");
			
			//ProductType
			try{
				String ProductType_Expec = "Laptops";
				String page = "Configurator";
				String nameValue = "producttype";
				String pageContent = getPageSourceContent(driver);
				
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(ProductType_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,ProductType_Expec,result);
		
			}catch(Exception e){
				e.printStackTrace();
				String ProductType_Expec = "Laptops";
				String page = "Configurator";
				String nameValue = "producttype";
				String pageContent = getPageSourceContent(driver);
				
				String target = getContent(pageContent,nameValue); 
				
				result = "Block";
				saveResult(Url,page,nameValue,target,ProductType_Expec,result);
				
			}
				
			//	Brand 
			try{
				String Brand_Expec = "ThinkPad";
				String page = "Configurator";
				String pageContent = getPageSourceContent(driver);
				String nameValue = "brand";
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(Brand_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,Brand_Expec,result);
		
			}catch(Exception e){
				e.printStackTrace();
				String Brand_Expec = "ThinkPad";
				String page = "Configurator";
				String pageContent = getPageSourceContent(driver);
				String nameValue = "brand";
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,Brand_Expec,result);
			}
				
			//	Series
			
			try{
				String Series_Expec = "X Series";
				String page = "Configurator";
				String nameValue = "series";
				String pageContent = getPageSourceContent(driver);
				
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(Series_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,Series_Expec,result);

			}catch(Exception e){
				e.printStackTrace();
				String Series_Expec = "ThinkPad";
				String page = "Configurator";
				String nameValue = "series";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				result = "Block";
				saveResult(Url,page,nameValue,target,Series_Expec,result);
				
			}
			
			//Item Type
			
			try{
				String ItemType_Expec = "CTO";
				String page = "Configurator";
				String nameValue = "itemtype";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
			
				if(target.equals(ItemType_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,ItemType_Expec,result);	
				
			}catch(Exception e){
				
				e.printStackTrace();
				String ItemType_Expec = "CTO";
				String page = "Configurator";
				String nameValue = "itemtype";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,ItemType_Expec,result);	
			}
		
			//Subseries ID
			try{
				String SubseriesID_Expec = "";
				String page = "Configurator";
				String nameValue = "subseriesid";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(SubseriesID_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,SubseriesID_Expec,result);
		
				
			}catch(Exception e){
				e.printStackTrace();
				
				String SubseriesID_Expec = "";
				String page = "Configurator";
				String nameValue = "subseriesid";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,SubseriesID_Expec,result);
			}
			
			//Variant ID
			try{
				String VariantID_Expec = "";
				String page = "Configurator";
				String nameValue = "variantid";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(VariantID_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,VariantID_Expec,result);
		
			}catch(Exception e){
				e.printStackTrace();
				
				String VariantID_Expec = "";
				String page = "Configurator";
				String nameValue = "variantid";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,VariantID_Expec,result);

			}
			
			//productcode
			try{
				String productcode_Expec = "";
				String page = "Configurator";
				String pageContent = getPageSourceContent(driver);
				String nameValue = "productcode";
				
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(productcode_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				
				saveResult(Url,page,nameValue,target,productcode_Expec,result);

			}catch(Exception e){
				e.printStackTrace();
				
				String productcode_Expec = "";
				String page = "Configurator";
				String pageContent = getPageSourceContent(driver);
				String nameValue = "productcode";
				
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,productcode_Expec,result);
			}

			//productid
			try{
				String currentUrl = getCurrentUrl(driver);
				
				
				String productValue = currentUrl.substring(currentUrl.indexOf("/p/")+3, currentUrl.length()).split("/")[0].substring(0, currentUrl.substring(currentUrl.indexOf("/p/")+3, currentUrl.length()).split("/")[0].indexOf("WW")+2);

				String productid_Expec = productValue;
				String page = "Configurator";
				String nameValue = "productid";
				
				String pageContent = getPageSourceContent(driver);
				
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(productid_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,productid_Expec,result);
		
			}catch(Exception e){
				String currentUrl = getCurrentUrl(driver);
				
				
				String productValue = currentUrl.substring(currentUrl.indexOf("/p/")+3, currentUrl.length()).split("/")[0].substring(0, currentUrl.substring(currentUrl.indexOf("/p/")+3, currentUrl.length()).split("/")[0].indexOf("WW")+2);

				String productid_Expec = productValue;
				String page = "Configurator";
				String nameValue = "productid";
				
				String pageContent = getPageSourceContent(driver);
				
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,productid_Expec,result);
			}
			
			//productname
			
			try{
				String productname = getElementText(driver,driver.findElement(By.xpath("//h2[contains(@class,'qa_productName')]")));
				String productname_Expec = productname.replace("ThinkPad", "").trim();
				String page = "Configurator";
				String pageContent = getPageSourceContent(driver);
				String nameValue = "productname";
				
				String target = getContent(pageContent,nameValue);
				
				if(productname_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,productname_Expec,result);
		
			}catch(Exception e){
				e.printStackTrace();
				
				String productname = getElementText(driver,driver.findElement(By.xpath("//h2[contains(@class,'qa_productName')]")));
				String productname_Expec = productname.replace("ThinkPad", "").trim();
				String page = "Configurator";
				String pageContent = getPageSourceContent(driver);
				String nameValue = "productname";
				
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				
				saveResult(Url,page,nameValue,target,productname_Expec,result);
		
			}
			
			//productprice
			try{
				String price = getElementText(driver,driver.findElement(By.xpath("//*[@id='w-price']")));
				
				price = price.replace("$", "");
				String productprice_Expec = price.trim();
				String page = "Configurator";
				String pageContent = getPageSourceContent(driver);
				String nameValue = "productprice";
				String target = getContent(pageContent,nameValue).trim();
				
				if(productprice_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,productprice_Expec,result);

			}catch(Exception e){
				e.printStackTrace();
				
				String price = getElementText(driver,driver.findElement(By.xpath("//*[@id='w-price']")));
				
				price = price.replace("$", "");
				String productprice_Expec = price;
				String page = "Configurator";
				String pageContent = getPageSourceContent(driver);
				String nameValue = "productprice";
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,productprice_Expec,result);

			}

		}catch(Exception e){
			e.printStackTrace();
			String pageContent = getPageSourceContent(driver);
			String nameValue = "taxonomytype";
			result = "Blocking";
			String target = getContent(pageContent,nameValue);
			String page = "Configurator";
			String TaxononmyType_Expec = "Configurator";
			saveResult(Url,page,nameValue,target,TaxononmyType_Expec,result);
		}	

		
		/*
		 * Builder
		 * 
		 * */
		//TaxononmyType
		try{
			redirectedToUrl(driver,Url);
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Laptops);
			
//			clickElement(driver,b2cPage.Products_Link);
//			clickElement(driver,b2cPage.laptops_subProduct);
			clickElement(driver,b2cPage.thinkpad_brand);
			clickElement(driver,b2cPage.thinkpadX_series);
			
			clickElement(driver,b2cPage.thinkpad_subSeries);
			clickElement(driver,b2cPage.viewOrCustomize);
			clickElement(driver,b2cPage.customizeButton);
			
			goToBuilderPage();			
			
			allPage_test("Interstitial","Product Builder");
			
			//ProductType
			try{
				String ProductType_Expec = "Laptops";
				String page = "Product Builder";
				String nameValue = "producttype";
				String pageContent =getPageSourceContent(driver);
				
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(ProductType_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,ProductType_Expec,result);
		
			}catch(Exception e){
				e.printStackTrace();
				String ProductType_Expec = "Laptops";
				String page = "Product Builder";
				String nameValue = "producttype";
				String pageContent =getPageSourceContent(driver);
				
				String target = getContent(pageContent,nameValue); 
				
				result = "Block";
				
				saveResult(Url,page,nameValue,target,ProductType_Expec,result);
				
			}
				
			//	Brand 
			try{
				String Brand_Expec = "ThinkPad";
				String page = "Product Builder";
				String pageContent =getPageSourceContent(driver);
				String nameValue = "brand";
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(Brand_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,Brand_Expec,result);
		
			}catch(Exception e){
				e.printStackTrace();
				String Brand_Expec = "ThinkPad";
				String page = "Product Builder";
				String pageContent =getPageSourceContent(driver);
				String nameValue = "brand";
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,Brand_Expec,result);
			}
				
			//	Series
			
			try{
				String Series_Expec = "X Series";
				String page = "Product Builder";
				String nameValue = "series";
				String pageContent =getPageSourceContent(driver);
				
				String target = getContent(pageContent,nameValue);
				
				if(target.equals(Series_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,Series_Expec,result);

			}catch(Exception e){
				e.printStackTrace();
				String Series_Expec = "ThinkPad";
				String page = "Product Builder";
				String nameValue = "series";
				String pageContent =getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				result = "Block";
				saveResult(Url,page,nameValue,target,Series_Expec,result);
				
			}
			
			//Item Type
			
			try{
				String ItemType_Expec = "CTO";
				String page = "Product Builder";
				String nameValue = "itemtype";
				String pageContent =getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
			
				if(target.equals(ItemType_Expec)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,ItemType_Expec,result);	
				
			}catch(Exception e){
				
				e.printStackTrace();
				String ItemType_Expec = "CTO";
				String page = "Product Builder";
				String nameValue = "itemtype";
				String pageContent =getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,ItemType_Expec,result);	
			}
		
			//Subseries ID
			try{
				String SubseriesID_Expec = "";
				String page = "Product Builder";
				String nameValue = "subseriesid";
				String pageContent =getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(SubseriesID_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,SubseriesID_Expec,result);
		
				
			}catch(Exception e){
				e.printStackTrace();
				
				String SubseriesID_Expec = "";
				String page = "Product Builder";
				String nameValue = "subseriesid";
				String pageContent =getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,SubseriesID_Expec,result);
			}
			
			//Variant ID
			try{
				String VariantID_Expec = "";
				String page = "Product Builder";
				String nameValue = "variantid";
				String pageContent =getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(VariantID_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,VariantID_Expec,result);
		
			}catch(Exception e){
				e.printStackTrace();
				
				String VariantID_Expec = "";
				String page = "Product Builder";
				String nameValue = "variantid";
				String pageContent =getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,VariantID_Expec,result);

			}	
	
		}catch(Exception e){
			e.printStackTrace();
			String pageContent =getPageSourceContent(driver);
			String nameValue = "taxonomytype";
			result = "Blocking";
			String target = getContent(pageContent,nameValue);
			String page = "Product Builder";
			String TaxononmyType_Expec = "Interstitial";
			saveResult(Url,page,nameValue,target,TaxononmyType_Expec,result);
		}
		

		
		/*
		 * 
		 * search
		 * 
		 * */
		
		try{
			redirectedToUrl(driver,this.Url);
//			clickElement(driver,b2cPage.HomePage_SearchIcon);
			clickElement(driver,driver.findElement(By.xpath("(//form[contains(@class,'searchInput')]/input[@type='search'])[1]")));
			sendKeysIntoElement(driver,driver.findElement(By.xpath("(//form[contains(@class,'searchInput')]/input[@type='search'])[1]")),"x carbon");
			
			Thread.sleep(5000);
			Actions builder = new Actions(driver);
			builder.sendKeys(
					driver.findElement(By.xpath("(//form[contains(@class,'searchInput')]/input[@type='search'])[1]")),
					Keys.ENTER).perform();
			
			allPage_test("Search","Search");

			
		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "Search";
			String page = "Search";
			String nameValue = "taxonomytype";
			String pageContent = getPageSourceContent(driver);
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);	
		}
		
		/*
		 * Cart
		 * 
		 * */
			
		try{
			redirectedToUrl(driver,Url);
			emptyCart();
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Laptops);
			
//			clickElement(driver,b2cPage.Products_Link);
//			clickElement(driver,b2cPage.laptops_subProduct);
			clickElement(driver,b2cPage.thinkpad_brand);
			clickElement(driver,b2cPage.thinkpadX_series);
			
			clickElement(driver,b2cPage.thinkpad_subSeries);
			
			String currentUrl = driver.getCurrentUrl().toString();
			String[] strs = currentUrl.split("/");
			String productValue = strs[strs.length-1];
			String bundleid_Expec = productValue;
			
			clickElement(driver,b2cPage.viewOrCustomize);
			clickElement(driver,b2cPage.customizeButton);
			
			goToBuilderPage();
			goToCartPage();
			
			allPage_test("Cart","Cart");
			
			String page = "Cart";
			//cart.cartID
			try{
				String cart_cartID_Expec = driver.findElement(By.xpath(".//*[@id='confignumber']/div/strong[2]")).getText().trim();
				
				String nameValue = "cart.cartID";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(cart_cartID_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,cart_cartID_Expec,result);

			}catch(Exception e){
				e.printStackTrace();
				String cart_cartID_Expec = driver.findElement(By.xpath(".//*[@id='confignumber']/div/strong[2]")).getText().trim();

				String nameValue = "cart.cartID";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,cart_cartID_Expec,result);
			}
			
			//cart.basePrice
			String cart_basePrice_Expec = "";
			try{
				String cart_basePrice_Expec_Str = getElementText(driver,driver.findElement(By.xpath("//div[@class='cart-summary']/dl/dd[contains(@class,'summary')]/span")));
				//String cart_basePrice_Expec_Str = driver.findElement(By.xpath("//div[@class='cart-summary']/dl/dd[contains(@class,'summary')]/span")).getText().toString();
				
				float cart_basePrice_Expec_Float = getFloatPrice(cart_basePrice_Expec_Str);
				cart_basePrice_Expec = cart_basePrice_Expec_Float + "";
				
				String nameValue = "cart.basePrice";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(cart_basePrice_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,cart_basePrice_Expec,result);
	
			}catch(Exception e){
				e.printStackTrace();
				String nameValue = "cart.basePrice";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,cart_basePrice_Expec,result);
				
			}
			
			//cart.basePriceUSD
			
			
			String cart_basePriceUSD_Expec = "";
			try{
				String cart_basePriceUSD_Expec_Str = getElementText(driver,driver.findElement(By.xpath("//div[@class='cart-summary']/dl/dd[contains(@class,'summary')]/span")));
				//String cart_basePrice_Expec_Str = driver.findElement(By.xpath("//div[@class='cart-summary']/dl/dd[contains(@class,'summary')]/span")).getText().toString();
				
				float cart_basePriceUSD_Expec_Float = getFloatPrice(cart_basePriceUSD_Expec_Str);
				cart_basePriceUSD_Expec = cart_basePriceUSD_Expec_Float + "";
			
				

				String nameValue = "cart.basePriceUSD";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(cart_basePriceUSD_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";	
				}
				saveResult(Url,page,nameValue,target,cart_basePriceUSD_Expec,result);
				
			}catch(Exception e){
				e.printStackTrace();
				

				String nameValue = "cart.basePriceUSD";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,cart_basePriceUSD_Expec,result);
			}
			
			//cart.totalPrice
			String cart_totalPrice_Expec = "";
			try{
				String cart_totalPrice_Expec_Str = getElementText(driver,driver.findElement(By.xpath("//div[@class='cart-summary']/dl/dd[contains(@class,'summary')]/span")));
				
				
				float cart_totalPrice_Expec_Float = getFloatPrice(cart_totalPrice_Expec_Str);
				cart_totalPrice_Expec = cart_totalPrice_Expec_Float + "";
				
				

				String nameValue = "cart.totalPrice";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(cart_totalPrice_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,cart_totalPrice_Expec,result);
	
			}catch(Exception e){
				e.printStackTrace();
				String nameValue = "cart.totalPrice";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,cart_totalPrice_Expec,result);
			}
			
			//cart.totalPriceUSD
			String cart_totalPriceUSD_Expec = "";
			try{
				String cart_totalPriceUSD_Expec_Str = getElementText(driver,driver.findElement(By.xpath("//div[@class='cart-summary']/dl/dd[contains(@class,'summary')]/span")));
				
				
				float cart_totalPriceUSD_Expec_Float = getFloatPrice(cart_totalPriceUSD_Expec_Str);
				cart_totalPriceUSD_Expec = cart_totalPriceUSD_Expec_Float + "";
				
				
				String nameValue = "cart.totalPriceUSD";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(cart_totalPriceUSD_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,cart_totalPriceUSD_Expec,result);

			}catch(Exception e){
				e.printStackTrace();
				
				String nameValue = "cart.totalPriceUSD";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,cart_totalPriceUSD_Expec,result);
	
			}
			
			//cart.allProducts[n].quantity
			try{
				String car_quantity_Expec = "1";
				
				String nameValue = "cart.allProducts[0].quantity";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);

				if(car_quantity_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,car_quantity_Expec,result);
					
			}catch(Exception e){
				e.printStackTrace();
				String car_quantity_Expec = "1";
				
				String nameValue = "cart.allProducts[0].quantity";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,car_quantity_Expec,result);
			}
			
			//cart.allProducts[n].productcode
			String cart_productcode_Expec = "";
			try{
				cart_productcode_Expec = getElementText(driver,driver.findElement(By.xpath("//*[@id='editConfig']/p/span")));
				
				
				
				String nameValue = "cart.allProducts[0].productcode";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(cart_productcode_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,cart_productcode_Expec,result);
	
			}catch(Exception e){
				e.printStackTrace();
				
				
				String nameValue = "cart.allProducts[0].productcode";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
			
				result = "Block";
			
				saveResult(Url,page,nameValue,target,cart_productcode_Expec,result);
			}
			
			//cart.allProducts[n].bundleid
			
			try{
				String cart_bundleid_Expec = bundleid_Expec;
				
				String nameValue = "cart.allProducts[0].bundleid";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(cart_bundleid_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,cart_bundleid_Expec,result);

			}catch(Exception e){
				e.printStackTrace();
				String cart_bundleid_Expec = bundleid_Expec;
				
				String nameValue = "cart.allProducts[0].bundleid";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,cart_bundleid_Expec,result);
				
			}
			
			//cart.allProducts[n].price
			String cart_price_Expec = "";
			try{
				String cart_price_Expec_Str = getElementText(driver,driver.findElement(By.xpath("//div[@class='cart-summary']/dl/dd[contains(@class,'summary')]/span")));
				
				
				cart_price_Expec = getPrice(cart_price_Expec_Str);
				
				String nameValue = "cart.allProducts[0].price";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(cart_price_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,cart_price_Expec,result);
	
			}catch(Exception e){
				e.printStackTrace();
				
				String nameValue = "cart.allProducts[0].price";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,cart_price_Expec,result);
			}
			
			//cart.allProducts[n].priceUSD
			String cart_priceUSD_Expec = "";
			try{
				
				String cart_priceUSD_Expec_Str = getElementText(driver,driver.findElement(By.xpath("//div[@class='cart-summary']/dl/dd[contains(@class,'summary')]/span")));
				
				
				float cart_priceUSD_Expec_Float = getFloatPrice(cart_priceUSD_Expec_Str);
				cart_priceUSD_Expec = cart_priceUSD_Expec_Float + "";
				
				
				String nameValue = "cart.allProducts[0].priceUSD";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(cart_priceUSD_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,cart_priceUSD_Expec,result);
				
				
				
			}catch(Exception e){
				
				String nameValue = "cart.allProducts[0].priceUSD";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				result = "Block";
				saveResult(Url,page,nameValue,target,cart_priceUSD_Expec,result);
			}
			
			//cart.allProduct[n].type
			try{
				String cart_type_Expec = "Commercial Notebook";
				
				String nameValue = "cart.allProducts[0].type";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(cart_type_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,cart_type_Expec,result);
				
				
			}catch(Exception e){
				e.printStackTrace();
				String cart_type_Expec = "Commercial Notebook";
				
				String nameValue = "cart.allProducts[0].type";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				result = "Block";
			
				saveResult(Url,page,nameValue,target,cart_type_Expec,result);
			
			}
			
			//cart.event.action
			try{
				String cart_action_Expec = "";
				
				
				String nameValue = "cart.event.action";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(cart_action_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,cart_action_Expec,result);
	
			}catch(Exception e){
				e.printStackTrace();
			
				String cart_action_Expec = "";
				
				
				String nameValue = "cart.event.action";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
			
				result = "Block";
				
				saveResult(Url,page,nameValue,target,cart_action_Expec,result);
			}
			
			//cart.event.name
			try{
				String cart_name_Expec = "";
				
				String nameValue = "cart.event.name";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(cart_name_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,cart_name_Expec,result);

			}catch(Exception e){
				e.printStackTrace();
				String cart_name_Expec = "";
				
				String nameValue = "cart.event.name";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				
				saveResult(Url,page,nameValue,target,cart_name_Expec,result);
			}
			
			//cart.event.productcode
			try{
				
				String cart_event_productcode_Expec = "";
				
				String nameValue = "cart.event.productcode";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(cart_productcode_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,cart_event_productcode_Expec,result);
	
			}catch(Exception e){
				e.printStackTrace();
				String cart_event_productcode_Expec = "";
				
				String nameValue = "cart.event.productcode";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				
				saveResult(Url,page,nameValue,target,cart_event_productcode_Expec,result);
				
			}
	
		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "Cart";
			String page = "Cart";
			String nameValue = "taxonomytype";
			String pageContent = getPageSourceContent(driver);
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);	

		}
		
		/*
		 * Checkout--Shipping page
		 * 
		 * */
		try{
			redirectedToUrl(driver,Url);
			emptyCart();
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Laptops);
			
//			clickElement(driver,b2cPage.Products_Link);
//			clickElement(driver,b2cPage.laptops_subProduct);
			clickElement(driver,b2cPage.thinkpad_brand);
			clickElement(driver,b2cPage.thinkpadX_series);
			
			clickElement(driver,b2cPage.thinkpad_subSeries);
			clickElement(driver,b2cPage.viewOrCustomize);
			clickElement(driver,b2cPage.customizeButton);
			
			goToBuilderPage();
			goToCartPage();
			
			
			
			clickElement(driver,b2cPage.lenovo_checkout);
			
			// login and go to the shipping page
			String emailID = "lisong2@lenovo.com";
			String password = "1q2w3e4r";
			login_SignIn(b2cPage,emailID,password);
			clickElement(driver,b2cPage.shippingEdit);
			
			allPage_test("Checkout","Checkout--Shopping page");
			String page = "Checkout--Shopping page";
			//checkout.allProducts[n]
			try{
				String checkout_allProducts_Expec = "";
				
				String nameValue = "checkout.allProducts[0]";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(checkout_allProducts_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,checkout_allProducts_Expec,result);
		
			}catch(Exception e){
				e.printStackTrace();
				String checkout_allProducts_Expec = "";
				
				String nameValue = "checkout.allProducts[0]";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				
				saveResult(Url,page,nameValue,target,checkout_allProducts_Expec,result);
			}
			
			//checkout.allProducts[n].configType
			try{
				String checkout_allProduct_configType_Expec = "CTO";
				
				String nameValue = "checkout.allProducts[0].configType";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(checkout_allProduct_configType_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,checkout_allProduct_configType_Expec,result);				
	
			}catch(Exception e){
				e.printStackTrace();
				
				String checkout_allProduct_configType_Expec = "CTO";
				
				String nameValue = "checkout.allProducts[0].configType";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				
				saveResult(Url,page,nameValue,target,checkout_allProduct_configType_Expec,result);		
			}
			
			//checkout.allProducts[n].price
			try{
				String checkout_allProducts_price_str = getElementText(driver,driver.findElement(By.xpath("//dl[@class='checkout-orderSummary-pricing']/dd[contains(@class,'subtotal')]/span")));

				
				float checkout_allProducts_price_float = getFloatPrice(checkout_allProducts_price_str);
				
				String checkout_allProducts_price_Expec = checkout_allProducts_price_float + "";
				
				String nameValue = "checkout.allProducts[0].price";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(checkout_allProducts_price_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,checkout_allProducts_price_Expec,result);				
	
			}catch(Exception e){
				e.printStackTrace();
			
				String checkout_allProducts_price_str = getElementText(driver,driver.findElement(By.xpath("//dl[@class='checkout-orderSummary-pricing']/dd[contains(@class,'subtotal')]/span")));

				
				float checkout_allProducts_price_float = getFloatPrice(checkout_allProducts_price_str);
				
				String checkout_allProducts_price_Expec = checkout_allProducts_price_float + "";
				
				String nameValue = "checkout.allProducts[0].price";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
			
				saveResult(Url,page,nameValue,target,checkout_allProducts_price_Expec,result);	
			}
			
			
			//checkout.allProducts[n].productcode
			try{
				String checkout_allProducts_productcode_Expec = getElementText(driver,driver.findElement(By.xpath("//p[@class='checkout-shoppingCart-previewSubtitle']"))).split(":")[1].trim();
				
				
				
				String nameValue = "checkout.allProducts[0].productcode";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(checkout_allProducts_productcode_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				
				saveResult(Url,page,nameValue,target,checkout_allProducts_productcode_Expec,result);
	
			}catch(Exception e){
				e.printStackTrace();
				String checkout_allProducts_productcode_Expec = getElementText(driver,driver.findElement(By.xpath("//p[@class='checkout-shoppingCart-previewSubtitle']"))).split(":")[1].trim();
				
				
				String nameValue = "checkout.allProducts[0].productcode";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				
				saveResult(Url,page,nameValue,target,checkout_allProducts_productcode_Expec,result);
			}
			
			//checkout.allProducts[n].type
			try{
				String checkout_allProducts_type_Expec = "Commerce Notebook";
				
				String nameValue = "checkout.allProducts[0].type";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(checkout_allProducts_type_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				
				saveResult(Url,page,nameValue,target,checkout_allProducts_type_Expec,result);
	
			}catch(Exception e){
				e.printStackTrace();
				
				String checkout_allProducts_type_Expec = "Commerce Notebook";
				
				String nameValue = "checkout.allProducts[0].type";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				
				saveResult(Url,page,nameValue,target,checkout_allProducts_type_Expec,result);
				
				
			}
			
			//checkout.allProducts[n].quantity
			try{
				String checkout_allProducts_quantity_Expec = "1";
				
				
				String nameValue = "checkout.allProducts[0].quantity";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(checkout_allProducts_quantity_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,checkout_allProducts_quantity_Expec,result);
				
				
				
			}catch(Exception e){
				e.printStackTrace();
				String checkout_allProducts_quantity_Expec = "1";
				
				
				String nameValue = "checkout.allProducts[0].quantity";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				result = "Block";
				
				saveResult(Url,page,nameValue,target,checkout_allProducts_quantity_Expec,result);
				
			}
			
			//checkout.event.action
			try{
				String checkout_event_action_Expec = "page load";
				
				String nameValue = "checkout.event.action";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(checkout_event_action_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,checkout_event_action_Expec,result);
	
			}catch(Exception e){
				e.printStackTrace();
				String checkout_event_action_Expec = "page load";
				
				String nameValue = "checkout.event.action";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				result = "Block";
				
				saveResult(Url,page,nameValue,target,checkout_event_action_Expec,result);	
			}
			
			//checkout.event.name
			try{
				String checkout_event_name_Expec = "checkout";
				
				String nameValue = "checkout.event.name";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(checkout_event_name_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,checkout_event_name_Expec,result); 
	
			}catch(Exception e){
				e.printStackTrace();
				String checkout_event_name_Expec = "checkout";
				
				String nameValue = "checkout.event.name";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				result = "Block";
				
				saveResult(Url,page,nameValue,target,checkout_event_name_Expec,result); 
			}
			
			//cart.event.step
			try{
				String cart_event_step_Expec = "Shipping";
				
				String nameValue = "cart.event.step";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(cart_event_step_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,cart_event_step_Expec,result); 
	
			}catch(Exception e){
				e.printStackTrace();
				String cart_event_step_Expec = "Shipping";
				
				String nameValue = "cart.event.step";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				result = "Block";
				saveResult(Url,page,nameValue,target,cart_event_step_Expec,result); 
			}
			
			//cardID
			try{
				String cartID_Expec = "";
				
				String nameValue = "cardID";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(cartID_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,cartID_Expec,result);  

			}catch(Exception e){
				e.printStackTrace();
				
				String cartID_Expec = "";
				
				String nameValue = "cardID";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				result = "Block";
				
				saveResult(Url,page,nameValue,target,cartID_Expec,result); 
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			String taxonomytype_Expec = "Cart";
			String page = "Checkout";
			String nameValue = "taxonomytype";
			String pageContent = getPageSourceContent(driver);
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);	
		}
		
		
		
		//Checkout--Payment page
		try{
			redirectedToUrl(driver,Url);
			emptyCart();
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Laptops);
			
//			clickElement(driver,b2cPage.Products_Link);
//			clickElement(driver,b2cPage.laptops_subProduct);
			clickElement(driver,b2cPage.thinkpad_brand);
			clickElement(driver,b2cPage.thinkpadX_series);
			
			clickElement(driver,b2cPage.thinkpad_subSeries);
			clickElement(driver,b2cPage.viewOrCustomize);
			clickElement(driver,b2cPage.customizeButton);
			
			goToBuilderPage();
			goToCartPage();
			
			
			clickElement(driver,b2cPage.lenovo_checkout);
			
			Thread.sleep(30000);
			
			// login and go to the shipping page
			
			if(isElementExist(driver,By.xpath("//*[@id='j_username']"))){
				String emailID = "lisong2@lenovo.com";
				String password = "1q2w3e4r";
				login_SignIn(b2cPage,emailID,password);
			}
			
			clickElement(driver,b2cPage.shippingEdit);
			
			String firstName = "test";
			String lastName = "test";
			String EmailID = "lisong2@lenovo.com";
			fillShippingInfo(b2cPage,firstName,lastName,AddressLine1,City,state,zipCode,phoneNumber,EmailID);
			clickElement(driver,b2cPage.Shipping_ContinueButton);
			
			if(isElementExist(driver, By.xpath("//*[@id='useDeliveryAddress']")) && !driver.findElement(By.xpath("//*[@id='useDeliveryAddress']")).isSelected()){
				clickElement(driver,driver.findElement(By.xpath("//*[@id='useDeliveryAddress']")));
			}
			
			allPage_test("Checkout","Checkout--Payment page");
			
			String page = "Checkout--Payment page";
			
			//checkout.allProducts[n]
			try{
				String checkout_allProducts_Expec = "";
				String nameValue = "checkout.allProducts[0]";
				String pageContent = getPageSourceContent(driver);
				
				String target = getContent(pageContent,nameValue);
				
				if(checkout_allProducts_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,checkout_allProducts_Expec,result);  
		
			}catch(Exception e){
				String checkout_allProducts_Expec = "";
				String nameValue = "checkout.allProducts[0]";
				String pageContent = getPageSourceContent(driver);
				
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,checkout_allProducts_Expec,result); 
			}
			
			//checkout.allProducts[n].configType
			try{
				String checkout_allProduct_configType_Expec = "CTO";
				
				String nameValue = "checkout.allProducts[0].configType";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(checkout_allProduct_configType_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,checkout_allProduct_configType_Expec,result);				
	
			}catch(Exception e){
				e.printStackTrace();
				
				String checkout_allProduct_configType_Expec = "CTO";
				
				String nameValue = "checkout.allProducts[0].configType";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				
				saveResult(Url,page,nameValue,target,checkout_allProduct_configType_Expec,result);		
			}
			
			//checkout.allProducts[n].price
			String checkout_allProducts_price_Expec = "";
			try{
				String checkout_allProducts_price_str = getElementText(driver,driver.findElement(By.xpath("//dd[contains(@class,'checkout_subtotal')]/span")));

				
//				float checkout_allProducts_price_float = getFloatPrice(checkout_allProducts_price_str);
				
				checkout_allProducts_price_Expec = getPrice(checkout_allProducts_price_str);
				
				String nameValue = "checkout.allProducts[0].price";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(checkout_allProducts_price_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,checkout_allProducts_price_Expec,result);				
	
			}catch(Exception e){
				e.printStackTrace();
				
				String nameValue = "checkout.allProducts[0].price";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
			
				saveResult(Url,page,nameValue,target,checkout_allProducts_price_Expec,result);	
			}
			
			//checkout.allProducts[n].productcode
			String checkout_allProducts_productcode_Expec = "";
			try{
			    checkout_allProducts_productcode_Expec = getElementText(driver,driver.findElement(By.xpath("//p[@class='checkout-shoppingCart-previewSubtitle']"))).split(":")[1].trim();
				
				
				
				String nameValue = "checkout.allProducts[0].productcode";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(checkout_allProducts_productcode_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				
				saveResult(Url,page,nameValue,target,checkout_allProducts_productcode_Expec,result);
	
			}catch(Exception e){
				e.printStackTrace();
				
				String nameValue = "checkout.allProducts[0].productcode";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				
				saveResult(Url,page,nameValue,target,checkout_allProducts_productcode_Expec,result);
			}
			
			//checkout.allProducts[n].type
			try{
				String checkout_allProducts_type_Expec = "Commerce Notebook";
				
				String nameValue = "checkout.allProducts[0].type";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(checkout_allProducts_type_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				
				saveResult(Url,page,nameValue,target,checkout_allProducts_type_Expec,result);
	
			}catch(Exception e){
				e.printStackTrace();
				
				String checkout_allProducts_type_Expec = "Commerce Notebook";
				
				String nameValue = "checkout.allProducts[0].type";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				
				saveResult(Url,page,nameValue,target,checkout_allProducts_type_Expec,result);
				
				
			}
			
			//checkout.allProducts[n].quantity
			try{
				String checkout_allProducts_quantity_Expec = "1";
				
				
				String nameValue = "checkout.allProducts[0].quantity";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(checkout_allProducts_quantity_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,checkout_allProducts_quantity_Expec,result);
				
				
				
			}catch(Exception e){
				e.printStackTrace();
				String checkout_allProducts_quantity_Expec = "1";
				
				
				String nameValue = "checkout.allProducts[0].quantity";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				result = "Block";
				
				saveResult(Url,page,nameValue,target,checkout_allProducts_quantity_Expec,result);
				
			}
			
			//checkout.event.action
			try{
				String checkout_event_action_Expec = "page load";
				
				String nameValue = "checkout.event.action";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(checkout_event_action_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,checkout_event_action_Expec,result);
	
			}catch(Exception e){
				e.printStackTrace();
				String checkout_event_action_Expec = "page load";
				
				String nameValue = "checkout.event.action";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				result = "Block";
				
				saveResult(Url,page,nameValue,target,checkout_event_action_Expec,result);	
			}
			
			//checkout.event.name
			try{
				String checkout_event_name_Expec = "checkout";
				
				String nameValue = "checkout.event.name";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(checkout_event_name_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,checkout_event_name_Expec,result); 
	
			}catch(Exception e){
				e.printStackTrace();
				String checkout_event_name_Expec = "checkout";
				
				String nameValue = "checkout.event.name";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				result = "Block";
				
				saveResult(Url,page,nameValue,target,checkout_event_name_Expec,result); 
			}
			
			//cart.event.step
			try{
				String cart_event_step_Expec = "Shipping";
				
				String nameValue = "cart.event.step";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(cart_event_step_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,cart_event_step_Expec,result); 
	
			}catch(Exception e){
				e.printStackTrace();
				String cart_event_step_Expec = "Shipping";
				
				String nameValue = "cart.event.step";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				result = "Block";
				saveResult(Url,page,nameValue,target,cart_event_step_Expec,result); 
			}
			
			//cardID
			
			try{
				String cartID_Expec = "";
				
				String nameValue = "cardID";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(cartID_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,cartID_Expec,result);  

			}catch(Exception e){
				e.printStackTrace();
				
				String cartID_Expec = "";
				
				String nameValue = "cardID";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				result = "Block";
				
				saveResult(Url,page,nameValue,target,cartID_Expec,result); 
			}
			
		}catch(Exception e){
			e.printStackTrace();
			String taxonomytype_Expec = "Checkout";
			String page = "Checkout--Payment page";
			String nameValue = "taxonomytype";
			String pageContent = getPageSourceContent(driver);
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
		}
		
		//Checkout--Summary page
		try{
			redirectedToUrl(driver,Url);
			emptyCart();
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Laptops);
			
//			clickElement(driver,b2cPage.Products_Link);
//			clickElement(driver,b2cPage.laptops_subProduct);
			clickElement(driver,b2cPage.thinkpad_brand);
			clickElement(driver,b2cPage.thinkpadX_series);
			
			clickElement(driver,b2cPage.thinkpad_subSeries);
			clickElement(driver,b2cPage.viewOrCustomize);
			clickElement(driver,b2cPage.customizeButton);
	
			goToBuilderPage();
			goToCartPage();
			
			
			clickElement(driver,b2cPage.lenovo_checkout);
			
			// login and go to the shipping page
			if(isElementExist(driver,By.xpath("//*[@id='j_username']"))){
				String emailID = "lisong2@lenovo.com";
				String password = "1q2w3e4r";
				login_SignIn(b2cPage,emailID,password);
			}

			clickElement(driver,b2cPage.shippingEdit);
			
			String firstName = "test";
			String lastName = "test";
			String EmailID = "lisong2@lenovo.com";
			fillShippingInfo(b2cPage,firstName,lastName,AddressLine1,City,state,zipCode,phoneNumber,EmailID);
			clickElement(driver,b2cPage.Shipping_ContinueButton);
			
			if(isElementExist(driver, By.xpath("//*[@id='useDeliveryAddress']")) && !driver.findElement(By.xpath("//*[@id='useDeliveryAddress']")).isSelected()){
				clickElement(driver,driver.findElement(By.xpath("//*[@id='useDeliveryAddress']")));
			}
			
			fillDefaultPaymentInfo(b2cPage);
			clickElement(driver,b2cPage.Payment_ContinueButton);
			
			allPage_test("Checkout","Checkout--Summary page");
			
			String page = "Checkout--Summary page";
			
			//checkout.allProducts[n]
			try{
				String checkout_allProducts_Expec = "";
				String nameValue = "checkout.allProducts[0]";
				String pageContent = getPageSourceContent(driver);
				
				String target = getContent(pageContent,nameValue);
				
				if(checkout_allProducts_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,checkout_allProducts_Expec,result);  
		
			}catch(Exception e){
				String checkout_allProducts_Expec = "";
				String nameValue = "checkout.allProducts[0]";
				String pageContent = getPageSourceContent(driver);
				
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,checkout_allProducts_Expec,result); 
			}
			
			//checkout.allProducts[n].configType
			try{
				String checkout_allProduct_configType_Expec = "CTO";
				
				String nameValue = "checkout.allProducts[0].configType";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(checkout_allProduct_configType_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,checkout_allProduct_configType_Expec,result);				
	
			}catch(Exception e){
				e.printStackTrace();
				
				String checkout_allProduct_configType_Expec = "CTO";
				
				String nameValue = "checkout.allProducts[0].configType";
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				
				saveResult(Url,page,nameValue,target,checkout_allProduct_configType_Expec,result);		
			}
			
			//checkout.allProducts[n].price
			String checkout_allProducts_price_Expec = "";
			try{
				String checkout_allProducts_price_str = getElementText(driver,driver.findElement(By.xpath("//dd[@class='checkout-review-item-pricing-value']")));

//				float checkout_allProducts_price_float = getFloatPrice(checkout_allProducts_price_str);
				
				checkout_allProducts_price_Expec = getPrice(checkout_allProducts_price_str);
				
				String nameValue = "checkout.allProducts[0].price";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(checkout_allProducts_price_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,checkout_allProducts_price_Expec,result);				
	
			}catch(Exception e){
				e.printStackTrace();
				
				String nameValue = "checkout.allProducts[0].price";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
			
				saveResult(Url,page,nameValue,target,checkout_allProducts_price_Expec,result);	
			}
			
			//checkout.allProducts[n].productcode
			String checkout_allProducts_productcode_Expec = "";
			try{
			    checkout_allProducts_productcode_Expec = getElementText(driver,driver.findElement(By.xpath("//p[@class='checkout-review-item-partNumber']/span"))).trim();

				String nameValue = "checkout.allProducts[0].productcode";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(checkout_allProducts_productcode_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				
				saveResult(Url,page,nameValue,target,checkout_allProducts_productcode_Expec,result);
	
			}catch(Exception e){
				e.printStackTrace();
				
				String nameValue = "checkout.allProducts[0].productcode";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				
				saveResult(Url,page,nameValue,target,checkout_allProducts_productcode_Expec,result);
			}
			
			//checkout.allProducts[n].type
			try{
				String checkout_allProducts_type_Expec = "Commerce Notebook";
				
				String nameValue = "checkout.allProducts[0].type";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(checkout_allProducts_type_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				
				saveResult(Url,page,nameValue,target,checkout_allProducts_type_Expec,result);
	
			}catch(Exception e){
				e.printStackTrace();
				
				String checkout_allProducts_type_Expec = "Commerce Notebook";
				
				String nameValue = "checkout.allProducts[0].type";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				
				saveResult(Url,page,nameValue,target,checkout_allProducts_type_Expec,result);
				
				
			}
			
			//checkout.allProducts[n].quantity
			try{
				String checkout_allProducts_quantity_Expec = "1";
				
				
				String nameValue = "checkout.allProducts[0].quantity";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(checkout_allProducts_quantity_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,checkout_allProducts_quantity_Expec,result);
				
				
				
			}catch(Exception e){
				e.printStackTrace();
				String checkout_allProducts_quantity_Expec = "1";
				
				
				String nameValue = "checkout.allProducts[0].quantity";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				result = "Block";
				
				saveResult(Url,page,nameValue,target,checkout_allProducts_quantity_Expec,result);
				
			}
			
			//checkout.event.action
			try{
				String checkout_event_action_Expec = "page load";
				
				String nameValue = "checkout.event.action";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(checkout_event_action_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,checkout_event_action_Expec,result);
	
			}catch(Exception e){
				e.printStackTrace();
				String checkout_event_action_Expec = "page load";
				
				String nameValue = "checkout.event.action";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				result = "Block";
				
				saveResult(Url,page,nameValue,target,checkout_event_action_Expec,result);	
			}
			
			//checkout.event.name
			try{
				String checkout_event_name_Expec = "checkout";
				
				String nameValue = "checkout.event.name";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(checkout_event_name_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,checkout_event_name_Expec,result); 
	
			}catch(Exception e){
				e.printStackTrace();
				String checkout_event_name_Expec = "checkout";
				
				String nameValue = "checkout.event.name";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				result = "Block";
				
				saveResult(Url,page,nameValue,target,checkout_event_name_Expec,result); 
			}
			
			//cart.event.step
			try{
				String cart_event_step_Expec = "Shipping";
				
				String nameValue = "cart.event.step";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(cart_event_step_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,cart_event_step_Expec,result); 
	
			}catch(Exception e){
				e.printStackTrace();
				String cart_event_step_Expec = "Shipping";
				
				String nameValue = "cart.event.step";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				result = "Block";
				saveResult(Url,page,nameValue,target,cart_event_step_Expec,result); 
			}
			
			//cardID
			
			try{
				String cartID_Expec = "";
				
				String nameValue = "cardID";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				
				if(cartID_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,cartID_Expec,result);  

			}catch(Exception e){
				e.printStackTrace();
				
				String cartID_Expec = "";
				
				String nameValue = "cardID";
				
				String pageContent = getPageSourceContent(driver);
				String target = getContent(pageContent,nameValue);
				result = "Block";
				
				saveResult(Url,page,nameValue,target,cartID_Expec,result); 
			}	
			
		}catch(Exception e){
			e.printStackTrace();
			String taxonomytype_Expec = "Checkout";
			String page = "Checkout--Summary page";
			String nameValue = "taxonomytype";
			String pageContent = getPageSourceContent(driver);
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
		}
		
		
		
		/*
		 * Thank you page 
		 * 
		 * because on the prod environment ,we can not place an order , so this page will be ignored 
		 * 
		 * */
		
		try{
			String currentUrl = getCurrentUrl(driver);
			
			if(!currentUrl.contains("www3.lenovo.com")){
				
				redirectedToUrl(driver,Url);
				emptyCart();
				clickElement(driver,b2cPage.Products_Link);
				clickElement(driver,b2cPage.laptops_subProduct);
				clickElement(driver,b2cPage.thinkpad_brand);
				clickElement(driver,b2cPage.thinkpadX_series);
				
				clickElement(driver,b2cPage.thinkpad_subSeries);
				clickElement(driver,b2cPage.viewOrCustomize);
				clickElement(driver,b2cPage.customizeButton);
				
				goToBuilderPage();
				goToCartPage();
				
				
				
				clickElement(driver,b2cPage.lenovo_checkout);
				
				if(isElementExist(driver,By.xpath("//*[@id='j_username']"))){
					String emailID = "lisong2@lenovo.com";
					String password = "1q2w3e4r";
					login_SignIn(b2cPage,emailID,password);
				}
				
				
				clickElement(driver,b2cPage.shippingEdit);
				String firstName = "test";
				String lastName = "test";
				String EmailID = "lisong2@lenovo.com";
				fillShippingInfo(b2cPage,firstName,lastName,AddressLine1,City,state,zipCode,phoneNumber,EmailID);
				clickElement(driver,b2cPage.Shipping_ContinueButton);
				if(isElementExist(driver, By.xpath("//*[@id='useDeliveryAddress']")) && !driver.findElement(By.xpath("//*[@id='useDeliveryAddress']")).isSelected()){
					clickElement(driver,driver.findElement(By.xpath("//*[@id='useDeliveryAddress']")));
				}
				
				fillDefaultPaymentInfo(b2cPage);
				clickElement(driver,b2cPage.Payment_ContinueButton);
				clickElement(driver,b2cPage.OrderSummary_AcceptTermsCheckBox);
				clickPlaceOrder(b2cPage);
				
				allPage_test("Checkout","Checkout--Summary page");
				
				String page = "Thank You";
				
				//purchase.allProducts[n]
				try{
					String purchase_allProducts_Expec = "";
					String nameValue = "purchase.allProducts[0]";
					String pageContent = getPageSourceContent(driver);
					
					String target = getContent(pageContent,nameValue);
					
					if(purchase_allProducts_Expec.equals(target)){
						result = "Pass";
					}else{
						result = "Fail";
					}
					saveResult(Url,page,nameValue,target,purchase_allProducts_Expec,result);  
			
				}catch(Exception e){
					String purchase_allProducts_Expec = "";
					String nameValue = "purchase.allProducts[0]";
					String pageContent = getPageSourceContent(driver);
					
					String target = getContent(pageContent,nameValue);
					
					result = "Block";
					saveResult(Url,page,nameValue,target,purchase_allProducts_Expec,result); 
				}
				
				//purchase.allProducts[n].configType
				try{
					String purchase_allProduct_configType_Expec = "CTO";
					
					String nameValue = "purchase.allProducts[0].configType";
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					
					if(purchase_allProduct_configType_Expec.equals(target)){
						result = "Pass";
					}else{
						result = "Fail";
					}
					saveResult(Url,page,nameValue,target,purchase_allProduct_configType_Expec,result);				
		
				}catch(Exception e){
					e.printStackTrace();
					
					String purchase_allProduct_configType_Expec = "CTO";
					
					String nameValue = "purchase.allProducts[0].configType";
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					
					result = "Block";
					
					saveResult(Url,page,nameValue,target,purchase_allProduct_configType_Expec,result);		
				}
				
				//purchase.allProducts[n].price
				String purchase_allProducts_price_Expec = "";
				try{
					String purchase_allProducts_price_str = getElementText(driver,driver.findElement(By.xpath("//dd[@class='checkout-review-item-pricing-value']")));

//					float checkout_allProducts_price_float = getFloatPrice(checkout_allProducts_price_str);
					
					purchase_allProducts_price_Expec = getPrice(purchase_allProducts_price_str);
					
					String nameValue = "purchase.allProducts[0].price";
					
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					
					if(purchase_allProducts_price_Expec.equals(target)){
						result = "Pass";
					}else{
						result = "Fail";
					}
					saveResult(Url,page,nameValue,target,purchase_allProducts_price_Expec,result);				
		
				}catch(Exception e){
					e.printStackTrace();
					
					String nameValue = "purchase.allProducts[0].price";
					
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					
					result = "Block";
				
					saveResult(Url,page,nameValue,target,purchase_allProducts_price_Expec,result);	
				}
				
				//checkout.allProducts[n].productcode
				String purchase_allProducts_productcode_Expec = "";
				try{
					purchase_allProducts_productcode_Expec = getElementText(driver,driver.findElement(By.xpath("//p[@class='checkout-review-item-partNumber']/span"))).trim();

					String nameValue = "purchase.allProducts[0].productcode";
					
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					
					if(purchase_allProducts_productcode_Expec.equals(target)){
						result = "Pass";
					}else{
						result = "Fail";
					}
					
					saveResult(Url,page,nameValue,target,purchase_allProducts_productcode_Expec,result);
		
				}catch(Exception e){
					e.printStackTrace();
					
					String nameValue = "purchase.allProducts[0].productcode";
					
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					
					result = "Block";
					
					saveResult(Url,page,nameValue,target,purchase_allProducts_productcode_Expec,result);
				}
				
				//purchase.allProducts[n].type
				try{
					String purchase_allProducts_type_Expec = "Commerce Notebook";
					
					String nameValue = "purchase.allProducts[0].type";
					
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					
					if(purchase_allProducts_type_Expec.equals(target)){
						result = "Pass";
					}else{
						result = "Fail";
					}
					
					saveResult(Url,page,nameValue,target,purchase_allProducts_type_Expec,result);
		
				}catch(Exception e){
					e.printStackTrace();
					
					String purchase_allProducts_type_Expec = "Commerce Notebook";
					
					String nameValue = "purchase.allProducts[0].type";
					
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					
					result = "Block";
					
					saveResult(Url,page,nameValue,target,purchase_allProducts_type_Expec,result);
					
					
				}
				
				//purchase.allProducts[n].quantity
				try{
					String purchase_allProducts_quantity_Expec = "1";
					
					
					String nameValue = "purchase.allProducts[0].quantity";
					
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					
					if(purchase_allProducts_quantity_Expec.equals(target)){
						result = "Pass";
					}else{
						result = "Fail";
					}
					saveResult(Url,page,nameValue,target,purchase_allProducts_quantity_Expec,result);
					
					
					
				}catch(Exception e){
					e.printStackTrace();
					String purchase_allProducts_quantity_Expec = "1";
					
					
					String nameValue = "checkout.allProducts[0].quantity";
					
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					result = "Block";
					
					saveResult(Url,page,nameValue,target,purchase_allProducts_quantity_Expec,result);
					
				}
				//purchase.allProducts[n].USDPrice
				try{
					String purchse_allProducts_USDPrice = "";
					String nameValue = "purchase.allProducts[0].USDPrice";
					
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					if(purchse_allProducts_USDPrice.equals(target)){
						result = "Pass";
					}else{
						result = "Fail";
					}
					
					saveResult(Url,page,nameValue,target,purchse_allProducts_USDPrice,result);
					
					
				}catch(Exception e){
					e.printStackTrace();
					String purchse_allProducts_USDPrice = "";
					String nameValue = "purchase.allProducts[0].USDPrice";
					
					String pageContent = getPageSourceContent(driver);
					String target = getContent(pageContent,nameValue);
					
					result = "Block";
					
					saveResult(Url,page,nameValue,target,purchse_allProducts_USDPrice,result);
	
				}
				
				//purchase.event.name
				
				
				
				
				
				
				
				
				
				
				
				
			}
	
		}catch(Exception e){
			e.printStackTrace();
			String taxonomytype_Expec = "Thank You";
			String page = "Thank You";
			String nameValue = "taxonomytype";
			String pageContent = getPageSourceContent(driver);
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
		}
		
		
		
		
		
		
		
		
		
		
		
		/*
		 *Accessories Home Page
		 * 
		 * 
		 */
		try{
			redirectedToUrl(driver,Url);
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Accessories);
			
//			clickElement(driver,b2cPage.Products_Link);
//			clickElement(driver,b2cPage.accessories_Link);
			
			allPage_test("Accessory Homepage","Accessories Home Page");
		
		}catch(Exception e){
			e.printStackTrace();
			String taxonomytype_Expec = "Accessory Homepage";
			String page = "Accessories Home Page";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);	
		}
		
		/*
		 * 
		 * Accessories Category
		 * */
		try{
			redirectedToUrl(driver,Url);
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Accessories);
			
//			clickElement(driver,b2cPage.Products_Link);
//			clickElement(driver,b2cPage.accessories_Link);
			//driver.findElement(By.xpath("//li/a[@class='products_submenu' and contains(@href,'ACCESSORY')]")).click();
			
			if(isElementExist(driver,By.xpath("//*[@id='extraOptions-chooseCategroy']/../div/button"))){
				
				clickElement(driver,b2cPage.selectACategory);
				
				Random random = new Random();
				int ran = random.nextInt(2) + 2;
				System.out.println("ran is :" + ran);
				String testingAccessCategory = getElementText(driver,driver.findElement(By.xpath("(//*[@id='extraOptions-chooseCategroy']/../div/div/ul/li)["+ran+"]/a/span")));
				System.out.println("testingAccessCategory is :" + testingAccessCategory);
				clickElement(driver,driver.findElement(By.xpath("(//*[@id='extraOptions-chooseCategroy']/../div/div/ul/li)["+ran+"]")));
				
				allPage_test("Category","Accessories Category");
		
			}else{
				clickElement(driver, driver.findElement(By.xpath(".//*[@id='mainContent']//div[@class='accessoriesCategories']/div[1]")));
				
				String testingAccessCategory = getElementText(driver,driver.findElement(By.xpath("(//ul[@class='SearchTabData']/li/a)[1]")));
				System.out.println("testingAccessCategory is :" + testingAccessCategory);
				clickElement(driver, driver.findElement(By.xpath("(//ul[@class='SearchTabData']/li/a)[1]")));
				
				allPage_test("Category","Accessories Category");
				
			}
			
			
			
			//producttype
			try{
				String producttype_Expec = "Accessories";
				String page = "Accessories Category";
				String nameValue = "producttype";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				
				if(producttype_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,producttype_Expec,result);
				
			}catch(Exception e){
				e.printStackTrace();
				String producttype_Expec = "Accessories";
				String page = "Accessories Category";
				String nameValue = "producttype";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				result = "Block";
				saveResult(Url,page,nameValue,target,producttype_Expec,result);
			}

		}catch(Exception e){
			e.printStackTrace();
			String taxonomytype_Expec = "Category";
			String page = "Accessories Category";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);	
		}
		
		
		/*
		 * Accessories Product
		 * */
		try{
			redirectedToUrl(driver,Url);
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Accessories);
			
//			clickElement(driver,b2cPage.Products_Link);
//			clickElement(driver,b2cPage.accessories_Link);
			
			if(isElementExist(driver,By.xpath("//*[@id='extraOptions-chooseCategroy']/../div/button"))){
			
				List<WebElement> category_list = getElementList("//div[@class='prod_cat']//h2/a");
				
				String currentUrl = getCurrentUrl(driver);
				
				for(int x = 1; x <= category_list.size(); x++){
					
					clickElement(driver,driver.findElement(By.xpath("(//div[@class='prod_cat']//h2/a)["+x+"]")));
					
					List<WebElement> productList = getElementList("//div[@class='accessoriesListing-footer']/a");
					
					if(productList.size() != 0){
						
						for(int y = 1; y<= productList.size();y++){
							boolean b = isEnabled(driver,driver.findElement(By.xpath("(//div[@class='accessoriesListing-footer']/a)["+y+"]")));
							if(b){
								clickElement(driver,driver.findElement(By.xpath("(//div[@class='accessoriesListing-footer']/a)["+y+"]")));

								break;
							}
						}
						redirectedToUrl(driver,currentUrl);
						
					}else{
						redirectedToUrl(driver,currentUrl);
					}	
				}
				
				
				allPage_test("Product Page","Accessories Product");
			}else{
				clickElement(driver, driver.findElement(By.xpath(".//*[@id='mainContent']//div[@class='accessoriesCategories']/div[1]")));
				
				String testingAccessCategory = getElementText(driver,driver.findElement(By.xpath("(//ul[@class='SearchTabData']/li/a)[1]")));
				System.out.println("testingAccessCategory is :" + testingAccessCategory);
				clickElement(driver, driver.findElement(By.xpath("(//ul[@class='SearchTabData']/li/a)[1]")));
				clickElement(driver,driver.findElement(By.xpath("(//div[@class='accessoriesListing-footer']/a)[1]")));
				allPage_test("Product Page","Accessories Product");
			}
			
			//productcode
			
			try{
				
				String text = driver.findElement(By.xpath("//p[@class='accessoriesDetail-addtlInfo']/span")).getText().toString();
				
				text = text.split(":")[1].trim();
				
				String productcode_Expec = text;
				String page = "Accessories Product";
				String nameValue = "productcode";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				
				if(productcode_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				
				saveResult(Url,page,nameValue,target,productcode_Expec,result);	
	
			}catch(Exception e){
				e.printStackTrace();
				String text = driver.findElement(By.xpath("//p[@class='accessoriesDetail-addtlInfo']/span")).getText().toString();
				text = text.split(":")[1].trim();
				
				String productcode_Expec = text;
				String page = "Accessories Product";
				String nameValue = "productcode";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				result = "Block";
				
				saveResult(Url,page,nameValue,target,productcode_Expec,result);	
			}
			
			//productid
			try{
				String text = driver.findElement(By.xpath("//p[@class='accessoriesDetail-addtlInfo']/span")).getText().toString();
			
				text = text.split(":")[1].trim();
				
				String productid_Expec = text;
				String page = "Accessories Product";
				String nameValue = "productid";
				String pageContent = driver.getPageSource().toString();
				
				String target = getContent(pageContent,nameValue);
				
				if(productid_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,productid_Expec,result);	

			}catch(Exception e){
				e.printStackTrace();
				String text = driver.findElement(By.xpath("//p[@class='accessoriesDetail-addtlInfo']/span")).getText().toString();
				
				text = text.split(":")[1].trim();
				
				String productid_Expec = text;
				String page = "Accessories Product";
				String nameValue = "productid";
				String pageContent = driver.getPageSource().toString();
				
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,productid_Expec,result);		
			}
			
			//productprice
			try{
				String price = driver.findElement(By.xpath("//p[@class='accessoriesDetail-priceBlock-price']")).getText().toString();
				float price1 = getFloatPrice(price);
				String productprice_Expec = price1 + "";
				String page = "Accessories Product";
				String nameValue = "productprice";
				String pageContent = driver.getPageSource().toString();
				
				String target = getContent(pageContent,nameValue);
				
				if(productprice_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,productprice_Expec,result);

			}catch(Exception e){
				e.printStackTrace();
				String price = driver.findElement(By.xpath("//p[@class='accessoriesDetail-priceBlock-price']")).getText().toString();
				float price1 = getFloatPrice(price);
				String productprice_Expec = price1 + "";
				String page = "Accessories Product";
				String nameValue = "productprice";
				String pageContent = driver.getPageSource().toString();
				
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,productprice_Expec,result);
			}
			
			//type
			try{
				String type_Expec = "";
				String page = "Accessories Product";
				String nameValue = "type";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				
				if(type_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,type_Expec,result);

			}catch(Exception e){
				e.printStackTrace();
				String type_Expec = "";
				String page = "Accessories Product";
				String nameValue = "type";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				result = "Block";
				saveResult(Url,page,nameValue,target,type_Expec,result);
			}
			
			//productInfo.configType
			try{
				String productInfo_configType_Expec = "Accessory";
				String page = "Accessories Product";
				String nameValue = "productInfo.configType";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				
				if(productInfo_configType_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				saveResult(Url,page,nameValue,target,productInfo_configType_Expec,result);
				
			}catch(Exception e){
				e.printStackTrace();
				String productInfo_configType_Expec = "Accessory";
				String page = "Accessories Product";
				String nameValue = "productInfo.configType";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				result = "Block";
				
				saveResult(Url,page,nameValue,target,productInfo_configType_Expec,result);	
			}
			
			
			//productInfo.priceusd
			try{
				String productInfo_priceusd_Expec = "";
				String page = "Accessories Product";
				String nameValue = "productInfo.priceusd";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				
				if(productInfo_priceusd_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				
				saveResult(Url,page,nameValue,target,productInfo_priceusd_Expec,result);

			}catch(Exception e){
				e.printStackTrace();
				String productInfo_priceusd_Expec = "";
				String page = "Accessories Product";
				String nameValue = "productInfo.priceusd";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				
				saveResult(Url,page,nameValue,target,productInfo_priceusd_Expec,result);
	
			}
			
			// productInfo.productcode
			
			try{
				String text = driver.findElement(By.xpath("//p[@class='accessoriesDetail-addtlInfo']/span")).getText().toString();
			
				text = text.split(":")[1].trim();
				String productInfo_productcode_Expec = text;
				String page = "Accessories Product";
				String nameValue = "productInfo.productcode";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				
				if(productInfo_productcode_Expec.equals(target)){
					result = "Pass";
				}else{
					result = "Fail";
				}
				
				saveResult(Url,page,nameValue,target,productInfo_productcode_Expec,result);

			}catch(Exception e){
				e.printStackTrace();
				String text = driver.findElement(By.xpath("//p[@class='accessoriesDetail-addtlInfo']/span")).getText().toString();
				
				text = text.split(":")[1].trim();
				String productInfo_productcode_Expec = text;
				String page = "Accessories Product";
				String nameValue = "productInfo.productcode";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				
				result = "Block";
				saveResult(Url,page,nameValue,target,productInfo_productcode_Expec,result);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			String taxonomytype_Expec = "Product Page";
			String page = "Accessories Product";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);	

		}
		
		
		/*
		 * segment part : Outlet
		 *  
		 * */
		
		try{
			redirectedToUrl(driver,outlet_Url);
			
			allPage_test("Outlet","Outlet");

		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "Outlet";
			String page = "Outlet";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);	
		}
		
		/*
		 * Landing Page
		 * */
		
		try{
			String loginUrl = Url + "/login";
			
			redirectedToUrl(driver,loginUrl);
			
			allPage_test("Landing Page","Landing Page");

		}catch(Exception e){
			e.printStackTrace();
			String taxonomytype_Expec = "Landing Page";
			String page = "Landing Page";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);	
			
		}
		
		/*
		 * 
		 * SMB
		 * */
		
		try{
			redirectedToUrl(driver,SMB_Url);
			
			allPage_test("SMB","SMB");

		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "SMB";
			String page = "SMB";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);	
			
			
		}
		
		
		
		/*
		 * Gaming
		 * 
		 * */
		
		try{
			redirectedToUrl(driver,gaming_Url);
			
			allPage_test("Gaming","Gaming");
			
		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "Gaming";
			String page = "Gaming";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);	
			
		}
		
		
		/*
		 * Data Centers
		 * */
		
		
		//Data Center Homepage
		try{
			redirectedToUrl(driver,this.Url);
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Servers);
			
			allPage_test("Data Center Homepage","Data Center Homepage");
			
			
		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "Data Center Homepage";
			String page = "Data Center Homepage";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);	
			
		}
		
		//Data Center Splitter
		try{
			redirectedToUrl(driver,this.Url);
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Servers);
			clickPlusIcon();
			
			clickElement(driver,driver.findElement(By.xpath("(//div[@class='dcg-home-expander']//a[contains(@href,'servers')]/../../div[contains(@class,'expander-block')]/p/a[contains(@href,'servers')])[1]")));
			
			clickElement(driver,driver.findElement(By.xpath("//a[@class='breadcrumb-item' and contains(@href,'servers')]")));
			
			
			allPage_test("Data Center Splitter","Data Center Splitter");

		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "Data Center Splitter";
			String page = "Data Center Splitter";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);	

		}
		
		//Data Center Subsplitter
		
		try{
			redirectedToUrl(driver,this.Url);
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Servers);
			clickPlusIcon();
			
			clickElement(driver,driver.findElement(By.xpath("(//a[contains(@href,'blades-flex-servers')]/../p/a)[1]")));
			
			allPage_test("Data Center Subsplitter","Data Center Subsplitter");
			
		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "Data Center Subsplitter";
			String page = "Data Center Subsplitter";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);	

		}
		
		//Data Center Series
		
		try{
			
			redirectedToUrl(driver,this.Url);
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Servers);
			clickPlusIcon();
			clickElement(driver,driver.findElement(By.xpath("(//a[contains(@href,'blades-flex-servers')]/../p/a)[1]")));
			
			clickElement(driver,driver.findElement(By.xpath("//a[contains(@href,'compute-nodes') and contains(@class,'footer')]")));
			
			allPage_test("Data Center Series","Data Center Series");
			
		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "Data Center Series";
			String page = "Data Center Series";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);	
			
		}
		
		
		//Data Center Subseries
		try{
			redirectedToUrl(driver,this.Url);
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Servers);
			clickPlusIcon();
			clickElement(driver,driver.findElement(By.xpath("(//a[contains(@href,'blades-flex-servers')]/../p/a)[1]")));
			
			clickElement(driver,driver.findElement(By.xpath("//a[contains(@href,'compute-nodes') and contains(@class,'footer')]")));
			
			clickElement(driver,driver.findElement(By.xpath("(//div[@class='seriesListings-footer']/a)[1]")));
			
			allPage_test("Data Center Subseries","Data Center Subseries");

		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "Data Center Subseries";
			String page = "Data Center Subseries";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
		}
		
		
		//Services -Warranty
		try{
			redirectedToUrl(driver,this.Url);
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Warranty);
			
			allPage_test("Services-Warranty","Services-Warranty");

		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "Services -Warranty";
			String page = "Services -Warranty";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
	
		}
		
		/*
		 * 
		 * Page Bottom
		 * 
		 * 
		 * 
		 * About
		 * */
		
		
		try{
			redirectedToUrl(driver,Url);
			
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	
			clickElement(driver,driver.findElement(By.xpath("//div[@class= 'footer-bottomBar-nav']/a[contains(@href,'privacy')]")));

			allPage_test("Privacy","Privacy");

			
		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "Privacy";
			String page = "Privacy";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);	
		}
		
		//Company
		try{
			redirectedToUrl(driver,Url);
			
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(3000);
			clickElement(driver,driver.findElement(By.xpath("//li[contains(@class,'footer-navigation-links-list')]/h3")));
			Thread.sleep(3000);
			
			if(isElementExist(driver,By.xpath("(//div[@class='footer-navigation-links']//h3/../ul/li)[1]"))){
				clickElement(driver,driver.findElement(By.xpath("(//div[@class='footer-navigation-links']//h3/../ul/li)[1]")));
				allPage_test("Company","Company");
			}else{

				String taxonomytype_Expec = "Company";
				String page = "Company";
				String nameValue = "taxonomytype";
				String target = "";
				result = ignore;
				saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "Company";
			String page = "Company";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
			
		}
		
		
		//Legal
		try{
			redirectedToUrl(driver,Url);
			
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			clickElement(driver,driver.findElement(By.xpath("//li[contains(@class,'footer-navigation-links-list')]/h3")));
			
			if(isElementExist(driver,By.xpath("(//a[contains(@href,'legal')])[1]"))){
				clickElement(driver,driver.findElement(By.xpath("(//a[contains(@href,'legal')])[1]")));
				
				allPage_test("Legal","Legal");
			}else{
				String taxonomytype_Expec = "Legal";
				String page = "Legal";
				String nameValue = "taxonomytype";
				String target = "";
				result = ignore;
				saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
			}
			

		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "Legal";
			String page = "Legal";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
		}
		
		//History
		try{
			redirectedToUrl(driver,Url);
			
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			clickElement(driver,driver.findElement(By.xpath("//li[contains(@class,'footer-navigation-links-list')]/h3")));
			
			if(isElementExist(driver,By.xpath("//a[contains(@href,'history')]"))){
				clickElement(driver,driver.findElement(By.xpath("//a[contains(@href,'history')]")));
				
				allPage_test("History","History");
		
			}else{
				String taxonomytype_Expec = "History";
				String page = "History";
				String nameValue = "taxonomytype";
				
				String target = "";
				result = ignore;
				
				saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
			}
	
		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "History";
			String page = "History";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);

		}
		
		//Location
		try{
			redirectedToUrl(driver,Url);
			
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			clickElement(driver,driver.findElement(By.xpath("//li[contains(@class,'footer-navigation-links-list')]/h3")));
			
			if(isElementExist(driver,By.xpath("//a[contains(@href,'location')]"))){
				clickElement(driver,driver.findElement(By.xpath("//a[contains(@href,'location')]")));
				
				allPage_test("Location","Location");
			}else{
				String taxonomytype_Expec = "Location";
				String page = "Location";
				String nameValue = "taxonomytype";
				String target = "";
				result = ignore;
				saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			String taxonomytype_Expec = "Location";
			String page = "Location";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);

		}
		
		//Management
		
		try{
			redirectedToUrl(driver,Url);
			
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			clickElement(driver,driver.findElement(By.xpath("//li[contains(@class,'footer-navigation-links-list')]/h3")));
			
			if(isElementExist(driver,By.xpath("//a[contains(@href,'management')]"))){
				clickElement(driver,driver.findElement(By.xpath("//a[contains(@href,'management')]")));
				
				allPage_test("Management","Management");
			}else{
				String taxonomytype_Expec = "Management";
				String page = "Management";
				String nameValue = "taxonomytype";
				String target = "";
				result= ignore;
				saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "Management";
			String page = "Management";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
		}
		
		
		//Security
		
		try{
			
			redirectedToUrl(driver,Url);
			
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			clickElement(driver,driver.findElement(By.xpath("//li[contains(@class,'footer-navigation-links-list')]/h3")));
			
			if(isElementExist(driver,By.xpath("//a[contains(@href,'security')]"))){
				clickElement(driver,driver.findElement(By.xpath("//a[contains(@href,'security')]")));
				
				allPage_test("Security","Security");
			}else{
				String taxonomytype_Expec = "Security";
				String page = "Security";
				String nameValue = "taxonomytype";
				String target = "";
				
				result = ignore;
				saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
			}

		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "Security";
			String page = "Security";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
	
		}
		
		//Responsibility
		 	try{
				redirectedToUrl(driver,Url);
				
			
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
				clickElement(driver,driver.findElement(By.xpath("//li[contains(@class,'footer-navigation-links-list')]/h3")));
				if(Common.isElementExist(driver, By.xpath("//a[contains(@href,'social_responsibility')]"))){

					clickElement(driver,driver.findElement(By.xpath("//a[contains(@href,'social_responsibility')]")));
			
					allPage_test("Responsibility","Responsibility");
				}else{
					String taxonomytype_Expec = "Responsibility";
					String page = "Responsibility";
					String nameValue = "taxonomytype";
					String target = "";
					
					result = ignore;
					
					saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
				}
		 	}catch(Exception e){
				e.printStackTrace();
				
				String taxonomytype_Expec = "Responsibility";
				String page = "Responsibility";
				String nameValue = "taxonomytype";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				
				result = "Blocking";
				saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);

			}
		 	
		 	
		 	//Accessibility
		 	
		 	try{
		 		redirectedToUrl(driver,Url);
				
				
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
				clickElement(driver,driver.findElement(By.xpath("//li[contains(@class,'footer-navigation-links-list')]/h3")));
				if(Common.isElementExist(driver, By.xpath("//a[contains(@href,'accessibility')]"))){

					clickElement(driver,driver.findElement(By.xpath("//a[contains(@href,'accessibility')]")));
			
					allPage_test("Accessibility","Accessibility");
				}else{
					String taxonomytype_Expec = "Accessibility";
					String page = "Accessibility";
					String nameValue = "taxonomytype";
					String target = "";
					result = ignore;
					saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
				}
	
		 		
		 	}catch(Exception e){
		 		e.printStackTrace();
		 		
		 		String taxonomytype_Expec = "Accessibility";
				String page = "Accessibility";
				String nameValue = "taxonomytype";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				
				result = "Blocking";
				saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);

		 	}
		 			
		/*
		 * Industry
		 * 
		 * 
		 * 
		 */
		//Students
		try{
			redirectedToUrl(driver,Url);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			clickElement(driver,driver.findElement(By.xpath("//li[contains(@class,'footer-navigation-links-list')]/h3")));
			if(Common.isElementExist(driver, By.xpath("//ul[@style = 'display: block;']//a[contains(@href,'students')]"))){
				
				clickElement(driver,driver.findElement(By.xpath("//ul[@style = 'display: block;']//a[contains(@href,'students')]")));
				
				allPage_test("Students","Students");

			}else{
				String taxonomytype_Expec = "Students";
				String page = "Students";
				String nameValue = "taxonomytype";
				String target = "";
				
				result= ignore;
				saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
			}
		}catch(Exception e){
				e.printStackTrace();
				String taxonomytype_Expec = "Students";
				String page = "Students";
				String nameValue = "taxonomytype";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				
				result = "Blocking";
				saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);

			}
		
		//Enterprise
		try{
		
			redirectedToUrl(driver,Url);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			clickElement(driver,driver.findElement(By.xpath("//li[contains(@class,'footer-navigation-links-list')]/h3")));
			
			if(Common.isElementExist(driver, By.xpath("//ul[@style='display: block;']//a[contains(@href,'enterprise')]"))){
				clickElement(driver,driver.findElement(By.xpath("//ul[@style='display: block;']//a[contains(@href,'enterprise')]")));
				
				allPage_test("Enterprise","Enterprise");
			}else{
				String taxonomytype_Expec = "Enterprise";
				String page = "Enterprise";
				String nameValue = "taxonomytype";
				String target = "";
				result = ignore;
				
				saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
			}
		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "Enterprise";
			String page = "Enterprise";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
		}
		
		//Government
		try{
		
			redirectedToUrl(driver,Url);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			clickElement(driver,driver.findElement(By.xpath("//li[contains(@class,'footer-navigation-links-list')]/h3")));
			
			if(Common.isElementExist(driver, By.xpath("//ul[@style='display: block;']//a[contains(@href,'government')]"))){
				clickElement(driver,driver.findElement(By.xpath("//ul[@style='display: block;']//a[contains(@href,'government')]")));
				
				allPage_test("Government","Government");
			}else{
				String taxonomytype_Expec = "Government";
				String page = "Government";
				String nameValue = "taxonomytype";
				String target = "";
				result = ignore;
				
				saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
				
			}
		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "Government";
			String page = "Government";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
		}
		
		//Health
		try{
		
			redirectedToUrl(driver,Url);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			clickElement(driver,driver.findElement(By.xpath("//li[contains(@class,'footer-navigation-links-list')]/h3")));
			
			if(Common.isElementExist(driver, By.xpath("//ul[@style='display: block;']//a[contains(@href,'health')]"))){
				clickElement(driver,driver.findElement(By.xpath("//ul[@style='display: block;']//a[contains(@href,'health')]")));
				
				allPage_test("Health","Health");
			}else{
				String taxonomytype_Expec = "Health";
				String page = "Health";
				String nameValue = "taxonomytype";
				String target = "";
				result = ignore;
				saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
			}
		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "Health";
			String page = "Health";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
		}
		
		//K-12
		try{
		
			redirectedToUrl(driver,Url);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			clickElement(driver,driver.findElement(By.xpath("//li[contains(@class,'footer-navigation-links-list')]/h3")));
			
			if(Common.isElementExist(driver, By.xpath("//ul[@style='display: block;']//a[contains(@href,'k-12')]"))){
				clickElement(driver,driver.findElement(By.xpath("//ul[@style='display: block;']//a[contains(@href,'k-12')]")));
				
				allPage_test("K-12","K-12");
			}else{
				String taxonomytype_Expec = "K-12";
				String page = "K-12";
				String nameValue = "taxonomytype";
				String target = "";
				result = "Blocking";
				
				saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
			}
		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "K-12";
			String page = "K-12";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
		}
		
		//Higher Education
		try{
			redirectedToUrl(driver,Url);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			clickElement(driver,driver.findElement(By.xpath("//li[contains(@class,'footer-navigation-links-list')]/h3")));
			
			if(Common.isElementExist(driver, By.xpath("//ul[@style='display: block;']//a[contains(@href,'higher-education')]"))){
				clickElement(driver,driver.findElement(By.xpath("//ul[@style='display: block;']//a[contains(@href,'higher-education')]")));
				
				allPage_test("Higher Education","Higher Education");
			}else{
				String taxonomytype_Expec = "Higher Education";
				String page = "Higher Education";
				String nameValue = "taxonomytype";
				String target = "";
				result = ignore;
				
				saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
			}
		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "Higher Education";
			String page = "Higher Education";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
		}
		
		// Defense
		
		try{
			
			redirectedToUrl(driver,Url);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			clickElement(driver,driver.findElement(By.xpath("//li[contains(@class,'footer-navigation-links-list')]/h3")));
			
			if(Common.isElementExist(driver, By.xpath("//ul[@style='display: block;']//a[contains(@href,'defense')]"))){
				clickElement(driver,driver.findElement(By.xpath("//ul[@style='display: block;']//a[contains(@href,'defense')]")));
				
				allPage_test("Defense","Defense");
			}else{
				String taxonomytype_Expec = "Defense";
				String page = "Defense";
				String nameValue = "taxonomytype";
				String target = "";
				
				result = ignore;
				
				saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
			}
	
		}catch(Exception e){
			e.printStackTrace();

			String taxonomytype_Expec = "Defense";
			String page = "Defense";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);

		}
		
		
		
		/*
		 * eComm - Resources
		 * 
		 * 
		 * */
		
		//Account
		try{
			redirectedToUrl(driver,Url);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			clickElement(driver,driver.findElement(By.xpath("//li[contains(@class,'footer-navigation-links-list')]/h3")));
			if(Common.isElementExist(driver, By.xpath("//ul[@style='display: block;']//a[contains(@href,'account')]"))){
	
				clickElement(driver,driver.findElement(By.xpath("//ul[@style='display: block;']//a[contains(@href,'account')]")));
				
				allPage_test("Account","Account");
			}else{
				String taxonomytype_Expec = "Account";
				String page = "Account";
				String nameValue = "taxonomytype";
				String target = "";
				
				result = ignore;
				saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
			}

		}catch(Exception e){
			e.printStackTrace();
			String taxonomytype_Expec = "Account";
			String page = "Account";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);

		}
		//Affinity-Homepage
		
		try{
			redirectedToUrl(driver,Url);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			clickElement(driver,driver.findElement(By.xpath("//li[contains(@class,'footer-navigation-links-list')]/h3")));
			if(Common.isElementExist(driver, By.xpath("//ul[@style='display: block;']//a[contains(@href,'affinity')]"))){
	
				clickElement(driver,driver.findElement(By.xpath("//ul[@style='display: block;']//a[contains(@href,'affinity')]")));
				
				allPage_test("Affinity- Homepage","Affinity-Homepage");
			}else{
				String taxonomytype_Expec = "Affinity- Homepage";
				String page = "Affinity- Homepage";
				String nameValue = "taxonomytype";
				
				String target = "";
				result = ignore;
				
				saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
			}

		}catch(Exception e){
			e.printStackTrace();
			String taxonomytype_Expec = "Affinity- Homepage";
			String page = "Affinity- Homepage";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);

		}

		//FAQ
			
		try{
			redirectedToUrl(driver,Url);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			clickElement(driver,driver.findElement(By.xpath("//li[contains(@class,'footer-navigation-links-list')]/h3")));
			if(Common.isElementExist(driver, By.xpath("//ul[@style='display: block;']//a[contains(@href,'faqs')]"))){
	
				clickElement(driver,driver.findElement(By.xpath("//ul[@style='display: block;']//a[contains(@href,'faqs')]")));
				
				allPage_test("FAQ","FAQ");
			}else{
				String taxonomytype_Expec = "FAQ";
				String page = "FAQ";
				String nameValue = "taxonomytype";
				String target = "";
				result = ignore;
				
				saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
			}

		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "FAQ";
			String page = "FAQ";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
		}			
		
		//Affiliate
		try{
			
			redirectedToUrl(driver,Url);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			clickElement(driver,driver.findElement(By.xpath("//li[contains(@class,'footer-navigation-links-list')]/h3")));
			
		if(Common.isElementExist(driver, By.xpath("//ul[@style='display: block;']//a[contains(@href,'affiliate')]"))){

				clickElement(driver,driver.findElement(By.xpath("//ul[@style='display: block;']//a[contains(@href,'affiliate')]")));
				
				allPage_test("Affiliate","Affiliate");	
		}else{
			
			String taxonomytype_Expec = "Affiliate";
			String page = "Affiliate";
			String nameValue = "taxonomytype";
			
			String target = "";
			result = ignore;
			
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
			
			
		}
		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "Affiliate";
			String page = "Affiliate";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
		}		
		
		//Registration
		try{
			
			redirectedToUrl(driver,Url);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			clickElement(driver,driver.findElement(By.xpath("//li[contains(@class,'footer-navigation-links-list')]/h3")));
		
			if(Common.isElementExist(driver, By.xpath("//ul[@style='display: block;']//a[contains(@href,'registration')]"))){

				clickElement(driver,driver.findElement(By.xpath("//ul[@style='display: block;']//a[contains(@href,'registration')]")));
				
				allPage_test("Registration","Registration");
		}else{
			String taxonomytype_Expec = "Registration";
			String page = "Registration";
			String nameValue = "taxonomytype";
			
			String target = "";
			result = ignore;
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
		}
		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "Registration";
			String page = "Registration";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
		}			
		//Where-to-buy
		try{
			
			redirectedToUrl(driver,Url);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			clickElement(driver,driver.findElement(By.xpath("//li[contains(@class,'footer-navigation-links-list')]/h3")));
			
		if(Common.isElementExist(driver, By.xpath("//ul[@style='display: block;']//a[contains(@href,'reseller')]"))){
			
			
				clickElement(driver,driver.findElement(By.xpath("//ul[@style='display: block;']//a[contains(@href,'reseller')]")));
				
				allPage_test("Where-to-buy","Where-to-buy");

				
		}else{
			String taxonomytype_Expec = "Where-to-buy";
			String page = "Where-to-buy";
			String nameValue = "taxonomytype";
			
			String target = "";
			result = "ignore";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
		}
		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "Where-to-buy";
			String page = "Where-to-buy";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
		}		
		
		//Contact Us
		try{
			
			redirectedToUrl(driver,Url);
			
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			clickElement(driver,driver.findElement(By.xpath("//li[contains(@class,'footer-navigation-links-list')]/h3")));
			
			if(Common.isElementExist(driver, By.xpath("//ul[@style='display: block;']//a[contains(@href,'ordersupport')]"))){
				clickElement(driver,driver.findElement(By.xpath("//ul[@style='display: block;']//a[contains(@href,'ordersupport')]")));

				allPage_test("Contact Us","Contact Us");
			}else{
				String taxonomytype_Expec = "Contact Us";
				String page = "Contact Us";
				String nameValue = "taxonomytype";
				String target = "";
				result = ignore;
				saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
				
			}
		}catch(Exception e){
			e.printStackTrace();
			
			String taxonomytype_Expec = "Contact Us";
			String page = "Contact Us";
			String nameValue = "taxonomytype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			result = "Blocking";
			saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
		}		
		
		
		//Order Status
		try{
			
			redirectedToUrl(driver,Url);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			clickElement(driver,driver.findElement(By.xpath("//li[contains(@class,'footer-navigation-links-list')]/h3")));
			
			if(Common.isElementExist(driver, By.xpath("//ul[@style='display: block;']//a[contains(@href,'lenovo-ovp')]"))){
				
					clickElement(driver,driver.findElement(By.xpath("//ul[@style='display: block;']//a[contains(@href,'lenovo-ovp')]")));
			
					allPage_test("Order Status","Order Status");
				
			}else{
				String taxonomytype_Expec = "Order Status";
				String page = "Order Status";
				String nameValue = "taxonomytype";
				String target = "";
				
				result = ignore;
				saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
				
			}
			}catch(Exception e){
				e.printStackTrace();
				
				String taxonomytype_Expec = "Order Status";
				String page = "Order Status";
				String nameValue = "taxonomytype";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				
				result = "Blocking";
				saveResult(Url,page,nameValue,target,taxonomytype_Expec,result);
			}			


		}catch(Throwable e){
			e.printStackTrace();
			
			
		}
	
	}
	
	@AfterClass
	private void close() {
		driver.close();
		
	}
	
	@AfterSuite
	private void doSuite(){
		markTestOver();
	}
	
	public void emptyCart(){
		String cartUrl = "";
		if(Url.endsWith("/")){
			cartUrl = Url + "cart";
		}else{
			cartUrl = Url + "/cart";
		}
		
		redirectedToUrl(driver,cartUrl);
		if(isElementExist(driver,By.xpath("//*[@id='emptyCartItemsForm']/a"))){
			clickElement(driver,driver.findElement(By.xpath("//*[@id='emptyCartItemsForm']/a")));
		}
	}
	
	
	
		
	
	private void clickPlusIcon(){
		clickElement(driver,driver.findElement(By.xpath("//div[contains(@class,'trigger-link-two')]/a[3]")));
	}
	
	
	
	
	
	
	private void saveResult(String Url,String page,String metaTag,String actualResult,String expectedResult,String status){
		String sql_lan  = "insert into " + "metadatareport_b2c" + "(Url,Page,MetaTag,ActualResult,ExpectedResult,Status) values("+"'"+Url+"'"+","+"'"+page+"'"+","+"'"+metaTag+"'"+","+"'"+actualResult+"'"+","+"'"+expectedResult+"'"+","+"'"+status+"'"+");";
		
		try{
			Common.stmt.executeUpdate(sql_lan);
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
	
	
	
	
	private void login_SignIn(B2CPage b2cPage,String EmailID,String password){
		clearElement(driver,driver.findElement(By.xpath(".//*[@id='j_username']")));
		sendKeysIntoElement(driver,driver.findElement(By.xpath(".//*[@id='j_username']")),EmailID);
		clearElement(driver,driver.findElement(By.xpath("//*[@id='j_password']")));
		sendKeysIntoElement(driver,driver.findElement(By.xpath("//*[@id='j_password']")),password);
		clickElement(driver,b2cPage.Login_LogInButton);
	}
	
	
	
	private void login(B2CPage b2cPage, String emailId, String password) {
		clearElement(driver,b2cPage.Login_LenovoIDTextBox);
		sendKeysIntoElement(driver,b2cPage.Login_LenovoIDTextBox,emailId);
		clearElement(driver,b2cPage.Login_LenovoPasswordTextBox);
		sendKeysIntoElement(driver,b2cPage.Login_LenovoPasswordTextBox,password);
		clickElement(driver,b2cPage.Login_LogInButton);
	}
	
	private boolean isElementExist(WebDriver driver, By by) {
		boolean flag = false;
		driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
		try {
			WebElement element = driver.findElement(by);
			flag = element != null;
		} catch (Exception e) {
		}
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		return flag;
	}
	
	
	private String getPageSourceContent(WebDriver driver){
		String str = "";
		
		if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
			driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
		}
		if(isElementExist(driver, By.xpath("//*[@id='nm_wrapper']"))){
			driver.findElement(By.xpath("//*[@id='nm_closebtn']")).click();
		}
		try{
			str = driver.getPageSource().toString();
		}catch(Exception e){
			if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
				driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
			}
			if(isElementExist(driver, By.xpath("//*[@id='nm_wrapper']"))){
				driver.findElement(By.xpath("//*[@id='nm_closebtn']")).click();
			}
			str = driver.getPageSource().toString();
		}
		return str;	
	}
	
	private String getCurrentUrl(WebDriver driver){
		String str = "";
		
		if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
			driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
		}
		if(isElementExist(driver, By.xpath("//*[@id='nm_wrapper']"))){
			driver.findElement(By.xpath("//*[@id='nm_closebtn']")).click();
		}
		try{
			str = driver.getCurrentUrl().toString();
		}catch(Exception e){
			if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
				driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
			}
			if(isElementExist(driver, By.xpath("//*[@id='nm_wrapper']"))){
				driver.findElement(By.xpath("//*[@id='nm_closebtn']")).click();
			}
			str = driver.getCurrentUrl().toString();
		}
		return str;
	}
	
	private void redirectedToUrl(WebDriver driver,String url){
		driver.get(url);
		
			if(isElementExist(driver, By.xpath(".//*[@title='Close (Esc)']"))){
				driver.findElement(By.xpath(".//*[@title='Close (Esc)']")).click();
			}
			if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
				driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
			}
			if(isElementExist(driver, By.xpath("//*[@id='nm_wrapper']"))){
				driver.findElement(By.xpath("//*[@id='nm_closebtn']")).click();
			}
	}
	
	private String getElementText(WebDriver driver,WebElement element){
		String str = "";
		
		if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
			driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
		}
		if(isElementExist(driver, By.xpath("//*[@id='nm_wrapper']"))){
			driver.findElement(By.xpath("//*[@id='nm_closebtn']")).click();
		}
		try{
			str = element.getText().toString();
		}catch(Exception e){
			if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
				driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
			}
			if(isElementExist(driver, By.xpath("//*[@id='nm_wrapper']"))){
				driver.findElement(By.xpath("//*[@id='nm_closebtn']")).click();
			}
			str = element.getText().toString();
		}
		
		return str;
	}
	
	private void clearElement(WebDriver driver,WebElement element){
		
		if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
			driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
		}
		if(isElementExist(driver, By.xpath("//*[@id='nm_wrapper']"))){
			driver.findElement(By.xpath("//*[@id='nm_closebtn']")).click();
		}
		
		try{
			element.clear();
		}catch(Exception e){
			if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
				driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
			}
			if(isElementExist(driver, By.xpath("//*[@id='nm_wrapper']"))){
				driver.findElement(By.xpath("//*[@id='nm_closebtn']")).click();
			}
			element.clear();
		}
		
		
	}

	private void clickElement(WebDriver driver,WebElement element){
		
		if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
			driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
		}
		if(isElementExist(driver, By.xpath("//*[@id='nm_wrapper']"))){
			driver.findElement(By.xpath("//*[@id='nm_closebtn']")).click();
		}
		try{
			element.click();
		}catch(Exception e){
			System.out.println("--------------------------------------");
			
			e.printStackTrace();
			
			System.out.println("---------------------------------------");
			if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
				driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
			}
			if(isElementExist(driver, By.xpath("//*[@id='nm_wrapper']"))){
				driver.findElement(By.xpath("//*[@id='nm_closebtn']")).click();
			}
			element.click();
		}
		
	}
	private void sendKeysIntoElement(WebDriver driver,WebElement element,String str){
		if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
			driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
		}
		if(isElementExist(driver, By.xpath("//*[@id='nm_wrapper']"))){
			driver.findElement(By.xpath("//*[@id='nm_closebtn']")).click();
		}
		try{
			element.sendKeys(str);
		}catch(Exception e){
			if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
				driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
			}
			if(isElementExist(driver, By.xpath("//*[@id='nm_wrapper']"))){
				driver.findElement(By.xpath("//*[@id='nm_closebtn']")).click();
			}
			element.sendKeys(str);
		}
		
	}
	
	private boolean isEnabled(WebDriver driver, WebElement element){
		if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
			driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
		}
		if(isElementExist(driver, By.xpath("//*[@id='nm_wrapper']"))){
			driver.findElement(By.xpath("//*[@id='nm_closebtn']")).click();
		}
		boolean b = false;
		
		try{
			b = element.isEnabled();
		}catch(Exception e){
				e.printStackTrace();
				if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
					driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
				}
				if(isElementExist(driver, By.xpath("//*[@id='nm_wrapper']"))){
					driver.findElement(By.xpath("//*[@id='nm_closebtn']")).click();
				}
				b = element.isEnabled();
			}
			return b;
		}
		
	
	
	
	
	private List<WebElement> getElementList(String xpath_str){
		List<WebElement> list = null;
		if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
			driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
		}
		if(isElementExist(driver, By.xpath("//*[@id='nm_wrapper']"))){
			driver.findElement(By.xpath("//*[@id='nm_closebtn']")).click();
		}
		try{
			list = driver.findElements(By.xpath(xpath_str));
		}catch(Exception e){
			if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
				driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
			}
			if(isElementExist(driver, By.xpath("//*[@id='nm_wrapper']"))){
				driver.findElement(By.xpath("//*[@id='nm_closebtn']")).click();
			}
			list = driver.findElements(By.xpath(xpath_str));
		}
		
		
		return list;
	}
	
	public void fillShippingInfo(B2CPage b2cPage, String firstName,
			String lastName, String addressline1, String city, String state,
			String postCode, String phone, String mail) {
		if(isElementExist(driver,By.id("firstName"))){
			clearElement(driver,b2cPage.Shipping_FirstNameTextBox);
			sendKeysIntoElement(driver,b2cPage.Shipping_FirstNameTextBox,firstName);
			clearElement(driver,b2cPage.Shipping_LastNameTextBox);
			sendKeysIntoElement(driver,b2cPage.Shipping_LastNameTextBox,lastName);
			clearElement(driver,b2cPage.Shipping_AddressLine1TextBox);
			sendKeysIntoElement(driver,b2cPage.Shipping_AddressLine1TextBox,addressline1);
			if(isElementExist(driver,By.id("townCity"))){
				clearElement(driver,b2cPage.Shipping_CityTextBox);
				sendKeysIntoElement(driver,b2cPage.Shipping_CityTextBox,city);
			}
			if(isElementExist(driver,By.id("line3"))){
				clearElement(driver,b2cPage.Shipping_AddressLine3TextBox);
				sendKeysIntoElement(driver,b2cPage.Shipping_AddressLine3TextBox,city);
			}
			Select stateDropdown = new Select(b2cPage.Shipping_StateDropdown);
			stateDropdown.selectByVisibleText(state);
			
			if(isElementExist(driver,By.id("postcode"))){
				clearElement(driver,b2cPage.Shipping_PostCodeTextBox);
				sendKeysIntoElement(driver,b2cPage.Shipping_PostCodeTextBox,postCode);
			}
			clearElement(driver,b2cPage.Shipping_ContactNumTextBox);
			sendKeysIntoElement(driver,b2cPage.Shipping_ContactNumTextBox,phone);
			clearElement(driver,b2cPage.Shipping_EmailTextBox);
			sendKeysIntoElement(driver,b2cPage.Shipping_EmailTextBox,mail);

		}

	}
	
	
	public void fillDefaultPaymentInfo(B2CPage b2cPage)
			throws InterruptedException {
		clickElement(driver,b2cPage.Payment_CreditCardRadioButton);
		driver.switchTo().frame(
				b2cPage.PageDriver.findElement(By.id("creditcardiframe0")));
		Select dropdown = new Select(b2cPage.Payment_CardTypeDropdown);
		dropdown.selectByVisibleText("Visa");
		clearElement(driver,b2cPage.Payment_CardNumberTextBox);
		sendKeysIntoElement(driver,b2cPage.Payment_CardNumberTextBox,"4111111111111111");
		clearElement(driver,b2cPage.Payment_CardMonthTextBox);
		sendKeysIntoElement(driver,b2cPage.Payment_CardMonthTextBox,"12");
		clearElement(driver,b2cPage.Payment_CardYearTextBox);
		sendKeysIntoElement(driver,b2cPage.Payment_CardYearTextBox,"20");
		clearElement(driver,b2cPage.Payment_SecurityCodeTextBox);
		sendKeysIntoElement(driver,b2cPage.Payment_SecurityCodeTextBox,"123");
		driver.switchTo().defaultContent();
		Thread.sleep(2000);
		clearElement(driver,b2cPage.Payment_CardHolderNameTextBox);
		sendKeysIntoElement(driver,b2cPage.Payment_CardHolderNameTextBox,"Auto");
	}
	
	private void clickContinue(){
		while(Common.isElementExist(driver, By.xpath("//*[@id='product-builder-form']/descendant::div/button"))){
			//driver.findElement(By.xpath("//*[@id='product-builder-form']/descendant::div/button")).click();
			clickElement(driver,driver.findElement(By.xpath("//*[@id='product-builder-form']/descendant::div/button")));
		}
	}
	
	
	public void clickPlaceOrder(B2CPage b2cPage) {
		if (!b2cPage.PageDriver.getCurrentUrl().contains("www3.lenovo.com"))
			clickElement(driver,b2cPage.OrderSummary_PlaceOrderButton);
	}
	
	
	
	public void markTestOver(){
		try {
			String fileName = "\\\\100.67.28.133\\MetaDataTest\\B2C_All_AutoTestInProgress.txt";
			File file = new File(fileName);
			if (file.exists()) {
				file.delete();
			}
			fileName = "\\\\100.67.28.133\\MetaDataTest\\B2C_All_AutoTestDone.txt";
			file = new File(fileName);

			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public String getContent(String pageContent,String nameValue){
		
		String str1 = "<meta name="+"\""+nameValue+"\"";
		String targets = "";
		
		if(pageContent.contains(str1)){
			int index = pageContent.indexOf(str1);
			
			String target = pageContent.substring(index+str1.length()+1, pageContent.length());
			
			String strr = "/>";
			int index_1 = target.indexOf(strr);
			int index_2 = target.indexOf("content=\"");
			String target_1 = target.substring(index_2, index_1);			
			
			targets = target_1.replace("content=\"", "").replace("\"", "");
		}else{
			targets = "NOTEXIST";
		}
		
		
		return targets.trim();
	}
	
	
	
	public void allPage_test(String taxononmyType_Expec,String pageName){
		
		//TaxononmyType
		try{
						
			String TaxononmyType_Expec = taxononmyType_Expec;
			
			String pageContent = driver.getPageSource().toString();
			String nameValue = "taxonomytype";
			
			String target = getContent(pageContent,nameValue);
			
			if(target.equals(TaxononmyType_Expec)){
				result = "Pass";
			}else{
				result = "Fail";
			}
			String page = pageName;
			//private void saveResult(String page,String metaTag,String actualResult,String expectedResult,String status)
			saveResult(Url,page,nameValue,target,TaxononmyType_Expec,result);
	
		}catch(Exception e){
			e.printStackTrace();
			String TaxononmyType_Expec = taxononmyType_Expec;
			String pageContent = driver.getPageSource().toString();
			String nameValue = "taxonomytype";
			result = "Block";
			String target = getContent(pageContent,nameValue);
			String page = pageName;
			saveResult(Url,page,nameValue,target,TaxononmyType_Expec,result);
		}
		//currencycode
		try{
			currencycode_Expec = "USD";
			String nameValue = "currencycode";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			if(target.equals(currencycode_Expec)){
				result = "Pass";
			}else{
				result = "Fail";
			}
			String page = pageName;
			saveResult(Url,page,nameValue,target,currencycode_Expec,result);
		}catch(Exception e){
			 e.printStackTrace();
			 result = "Block";
			 String nameValue = "currencycode";
			 String pageContent = driver.getPageSource().toString();
			 String target = getContent(pageContent,nameValue);
			 String page = pageName;
			 saveResult(Url,page,nameValue,target,currencycode_Expec,result);
		}
		//language
		try{
			language_Expec = Url.split("/")[4];
			String nameValue = "language";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			if(target.equals(language_Expec)){
				result = "Pass";
			}else{
				result = "Fail";
			}
			 String page = pageName;
			saveResult(Url,page,nameValue,target,language_Expec,result);
		}catch(Exception e){
			result = "Block";
			String page = pageName;
			String nameValue = "language";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			saveResult(Url,page,nameValue,target,language_Expec,result);
		}
		
		
		//country
		try{
			country_Expec = Url.split("/")[3].toUpperCase();
			String nameValue = "country";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			if(target.equals(country_Expec)){
				result = "Pass";
			}else{
				result = "Fail";
			}
			String page = pageName;
			saveResult(Url,page,nameValue,target,country_Expec,result);
		}catch(Exception e){
			String page = pageName;
			String nameValue = "country";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			result = "Block";
			saveResult(Url,page,nameValue,target,country_Expec,result);
		}
		
		
		//storeID
		try{
			storeID_Expec = Url.split("/")[5];
			String nameValue = "storeID";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			if(target.equals(storeID_Expec)){
				result = "Pass";
			}else{
				result = "Fail";
			}
			String page = pageName;
			saveResult(Url,page,nameValue,target,storeID_Expec,result);
		}catch(Exception e){
			String page = pageName;
			String nameValue = "storeID";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			result = "Block";
			saveResult(Url,page,nameValue,target,storeID_Expec,result);
		}
		
		
		//BPID
		try{
			BPID_Expec = Url.split("/")[3].toUpperCase() + "00000001";
			String nameValue = "BPID";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			if(target.equals(BPID_Expec)){
				result = "Pass";
			}else{
				result = "Fail";
			}
			String page = pageName;
			saveResult(Url,page,nameValue,target,BPID_Expec,result);
		}catch(Exception e){
			e.printStackTrace();
			String page = "Splitter page";
			String nameValue = "BPID";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			result = "Block";
			saveResult(Url,page,nameValue,target,BPID_Expec,result);
		}
		
		
		
		//salestype
		try{
			salesType_Expec = "Direct";
			String nameValue = "salestype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			if(target.equals(salesType_Expec)){
				result = "Pass";
			}else{
				result = "Fail";
			}
			String page = pageName;
			saveResult(Url,page,nameValue,target,salesType_Expec,result);
		}catch(Exception e){
			e.printStackTrace();
			String page = pageName;
			String nameValue = "salestype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			result = "Block";
			saveResult(Url,page,nameValue,target,salesType_Expec,result);
		}

		//pageTitle
		try{
			pageTitle_Expec = driver.getTitle().toString();
			String nameValue = "pageTitle";
			String page = pageName;
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			if(target.equals(pageTitle_Expec)){
				result = "Pass";
			}else{
				result = "Fail";
			}
			saveResult(Url,page,nameValue,target,pageTitle_Expec,result);
			
		}catch(Exception e){
			e.printStackTrace();
			String nameValue = "pageTitle";
			String page = pageName;
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			result = "Block";
			saveResult(Url,page,nameValue,target,pageTitle_Expec,result);
		}
		
		//pageName
		try{
			String page = pageName;
			pageName_Expec = driver.getTitle().toString();
			String nameValue = "pageName";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			if(target.equals(pageTitle_Expec)){
				result = "Pass";
			}else{
				result = "Fail";
			}
			
			saveResult(Url,page,nameValue,target,pageTitle_Expec,result);
			
		}catch(Exception e){
			e.printStackTrace();
			String page = pageName;
			String nameValue = "pageName";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			result = "Block";
			saveResult(Url,page,nameValue,target,pageTitle_Expec,result);

		}
		
		//pageBreadcrumb
		try{
			pageBreadcrumb_Expec = "ThinkPad Edge:Professional:Laptops";
			String page = pageName;
			String nameValue = "pageBreadcrumb";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			if(target.equals(pageBreadcrumb_Expec)){
				result = "Pass";
			}else{
				result = "Fail";
			}
			
			saveResult(Url,page,nameValue,target,pageBreadcrumb_Expec,result);
	
		}catch(Exception e){
			e.printStackTrace();
			String page = pageName;
			String nameValue = "pageBreadcrumb";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			result = " Block";
			saveResult(Url,page,nameValue,target,pageBreadcrumb_Expec,result);	
		}
		
		
		
		
		
		
		
	}
	
	public String getStringPrice(String price){
		String tempPrice = price;
		String lastCharacter = tempPrice.substring((tempPrice.length()-1), tempPrice.length());
		while(!"1234567890".contains(lastCharacter)){
			tempPrice = tempPrice.substring(0, (tempPrice.length()-1));
			lastCharacter = tempPrice.substring((tempPrice.length()-1), tempPrice.length());
		}
		return tempPrice;
	}
	
	public String getPrice(String str){
		String price = str.replace("$", "").replace("€", "").replace("￥", "").replace("￡", "");
		
		return price.trim();
		
	}

	public float getFloatPrice(String price) {
		getStringPrice(price);
		if (price.contains("$")) {
			if (price.contains(",")) {
				price = price.substring(price.indexOf("$"), price.length());
				price = price.replace("$", "").replace(",", "").replace(" ", "").trim();
				System.out.println("acceessPrice is :" + price);
			} else {
				price = price.substring(price.indexOf("$"), price.length());
				price = price.replace("$", "").replace(" ", "").trim();
				System.out.println("acceessPrice is :" + price);
			}
		} else if (price.contains("€")) {
			if (price.contains(",")) {
				price = price.substring(price.indexOf("€"), price.length());
				price = price.replace("€", "").replace(",", "").replace(" ", "").trim();
				System.out.println("acceessPrice is :" + price);
			} else {
				price = price.substring(price.indexOf("€"), price.length());
				price = price.replace("€", "").replace(" ", "").trim();
				System.out.println("acceessPrice is :" + price);
			}
		}else if(price.contains("￥")){
			if (price.contains(",")) {
				price = price.substring(price.indexOf("￥"), price.length());
				price = price.replace("￥", "").replace(",", "").replace(" ", "").trim();
				System.out.println("acceessPrice is :" + price);
			} else {
				price = price.substring(price.indexOf("￥"), price.length());
				price = price.replace("￥", "").replace(" ", "").trim();
				System.out.println("acceessPrice is :" + price);
			}
		}else if(price.contains("￡")){
			if (price.contains(",")) {
				price = price.substring(price.indexOf("￡"), price.length());
				price = price.replace("￡", "").replace(",", "").replace(" ", "").trim();
				System.out.println("acceessPrice is :" + price);
			} else {
				price = price.substring(price.indexOf("￡"), price.length());
				price = price.replace("￡", "").replace(" ", "").trim();
				System.out.println("acceessPrice is :" + price);
			}
		}else{
			int len = price.length();
			int start = 0;
			for(int x = 0 ; x < len; x++){
				char ch = price.charAt(x);
				if(Character.isDigit(ch)){
					start = x ;
					break;
				}
			}
			price = price.substring(start, len).trim();
			
			char ch = price.charAt(price.length()-3);
			
			if(ch == ','){
				String price_1 = price.substring(0, price.length()-3);
				String price_2 = price.substring(price.length()-3, price.length());
				
				price_1 = price_1.replace(" ", "").replace(",", "").replace(".", "");
				
				price = price_1 + price_2;
				
				price = price.replace(",", ".");
			}else{
				String price_1 = price.substring(0, price.length()-3);
				String price_2 = price.substring(price.length()-3, price.length());
				price_1 = price_1.replace(" ", "").replace(",", "").replace(".", "");
				price = price_1 + price_2;
			}
			
			price = price.replace(",", "").replace(" ", "");
			System.out.println("accessprice is :" + price);
			
		}

		float itemPrice = Float.parseFloat(price);
		return itemPrice;
	}
	
	public void goToBuilderPage(){
		if(isElementExist(driver,By.xpath("//div[contains(@class,'section-header-button')]/button[contains(@class,'qa-configurator-sectionExpandButton')]"))){
			clickElement(driver,driver.findElement(By.xpath("//*[@id='cta-builder-continue']/button")));
		}else{
			clickElement(driver,b2cPage.addTocart_configPage);
		}
	}
	
	
	public void clickContinue_newUI(){
		while(isElementExist(driver,By.xpath(".//*[@id='cta-builder-continue']/button"))){
			clickElement(driver,driver.findElement(By.xpath(".//*[@id='cta-builder-continue']/button")));
		}
	}
	
	
	public void goToCartPage(){
		if(isElementExist(driver,By.xpath("//div[contains(@class,'section-header-button')]/button[contains(@class,'qa-configurator-sectionExpandButton')]"))){
			clickContinue_newUI();
		}else{
			clickContinue();
		}
	}
	
	
	
	
	
	
	
}
