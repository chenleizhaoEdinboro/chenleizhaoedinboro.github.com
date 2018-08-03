package TestScript.BIZ;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
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

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Pages.B2CPage;
import TestData.PropsUtils;
import TestData.TestData;

public class MetaDataTest_B2C {
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

	public MetaDataTest_B2C(String rowIndex, String homePage_url,String gaming_Url,String SMB_Url, String outlet_Url,String AddressLine1,String City,String state,String zipCode,String phoneNumber) {
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

	@BeforeClass
	private void setupDriver() {
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
		//•	Homepage
		//1.	Go to www3.lenovo.com/us/en/

		try{
			redirectedToUrl(driver,this.Url);
			//name="taxonomytype" content="splitterpage"
			String expectedStr = getExpectedResult(0).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			saveResult("9",result);
			System.out.println("outputData is :" + outputData);
		}catch(Throwable e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "Homepage check Exception");
			result = "Block";
			saveResult("9",result);
			System.out.println("outputData is :" + outputData);
		}
		
		// •	Search
		//		1.	Go to Lenovo.com
		//		2.	Click on the search bar
		//		3.	Search for x carbon
		//		4.	Click enter
		
		try{
			redirectedToUrl(driver,this.Url);
			clickElement(driver,b2cPage.HomePage_SearchIcon);
			clickElement(driver,b2cPage.HomePage_SearchTextArea);
			sendKeysIntoElement(driver,b2cPage.HomePage_SearchTextArea,"x carbon");
			Actions builder = new Actions(driver);
			builder.sendKeys(
					b2cPage.HomePage_SearchTextArea,
					Keys.ENTER).perform();
			String expectedStr = getExpectedResult(1).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			
			saveResult("10",result);
			System.out.println("outputData is :" + outputData);
			
		}catch(Throwable e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "Search check Exception");
			result = "Block";
			saveResult("10",result);
			System.out.println("outputData is :" + outputData);
		}
		
		//•	Splitterpage
		//	1.	Go to Lenovo.com
		//	2.	Select products: Laptops and Ultrabooks
		
		try{
			redirectedToUrl(driver,this.Url);
			clickElement(driver,b2cPage.Products_Link);
			clickElement(driver,b2cPage.laptops_subProduct);
			//driver.findElement(By.xpath("(//li/a[@class='products_submenu'])[1]")).click();
			String expectedStr = getExpectedResult(2).toLowerCase();
			
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			saveResult("11",result);
			System.out.println("outputData is :" + outputData);
			
		}catch(Throwable e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "Splitterpage check Exception");
			result = "Block";
			saveResult("11",result);
			System.out.println("outputData is :" + outputData);
		}
		
		
		// •	Brandpage
		//		1.	Go to lenovo.com
		//		2.	Select a product: Laptops and Ultrabooks
		//		3.	Select a brand: Thinkpad 
		
		try{
			redirectedToUrl(driver,this.Url);
			clickElement(driver,b2cPage.Products_Link);
			clickElement(driver,b2cPage.laptops_subProduct);
			clickElement(driver,b2cPage.thinkpad_brand);
			//driver.findElement(By.xpath("//div[@id='categoryWrapper']/div[contains(@id,'tab-content')]/article/div/h1/a[contains(@href,'thinkpad')]")).click();
			String expectedStr = getExpectedResult(3).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			saveResult("12",result);
			
		}catch(Throwable e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "brandpage check Exception");
			result = "Block";
			saveResult("12",result);
		}

		// •	Seriespage
		//		1.	Go to lenovo.com
		//		2.	Select a product: Laptops and Ultrabooks
		//		3.	Select a brand: Thinkpad 
		//		4.	Select a Brand: Thinkpad X
		
		try{
			redirectedToUrl(driver,this.Url);
			clickElement(driver,b2cPage.Products_Link);
			clickElement(driver,b2cPage.laptops_subProduct);
			clickElement(driver,b2cPage.thinkpad_brand);
			clickElement(driver,b2cPage.thinkpadX_series);

			String expectedStr = getExpectedResult(4).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			saveResult("13",result);
		}catch(Throwable e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "Seriespage check Exception");
			result = "Block";
			saveResult("13",result);
		}

		// •	Subseriespage
		//		1.	Go to lenovo.com
		//		2.	Select a product: Laptops and Ultrabooks
		//		3.	Select a brand: Thinkpad 
		//		4.	Select a series: Thinkpad X
		//		5.	Select a subseries: X1 Carbon

		try{
			redirectedToUrl(driver,this.Url);
			clickElement(driver,b2cPage.Products_Link);
			clickElement(driver,b2cPage.laptops_subProduct);
			clickElement(driver,b2cPage.thinkpad_brand);
			clickElement(driver,b2cPage.thinkpadX_series);
		
			clickElement(driver,b2cPage.thinkpad_subSeries);

			String expectedStr = getExpectedResult(5).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			saveResult("14",result);

		}catch(Throwable e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "Subseriespage check Exception");
			result = "Block";
			saveResult("14",result);
		}
		
		// •	Gaming
		//		1.	Go to www3.lenovo.com/us/en/gaming
		try{
			if(gaming_Url.equals("BlankCell")){
				result = "Skip";
			}else{
				redirectedToUrl(driver,gaming_Url);
				
				String expectedStr = getExpectedResult(6).toLowerCase();
				int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
				if(index != -1){
					result = "Pass";
				}else{
					result = "Fail";
				}	
			}
			saveResult("15",result);
	
		}catch(Throwable e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "Gaming check Exception");
			result = "Block";
			saveResult("15",result);
		}
		
		//•	SMB
		//		1.	Go to Lenovo.com/us/en/landingpage/small-business

		try{
			if(SMB_Url.equals("BlankCell")){
				result = "Skip";
			}else{
				redirectedToUrl(driver,SMB_Url);
				
				String expectedStr = getExpectedResult(7).toLowerCase();
				int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
				if(index != -1){
					result = "Pass";
				}else{
					result = "Fail";
				}	
			}
			saveResult("16",result);
			
		}catch(Throwable e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "SMB check Exception");
			result = "Block";
			saveResult("16",result);
		}
		
		//•	Outlet
		//		1.	Go to www3.lenovo.com/us/en/outletus
		try{
			if(outlet_Url.equals("BlankCell")){
				result = "Skip";
			}else{
				redirectedToUrl(driver,outlet_Url);

				String expectedStr = getExpectedResult(8).toLowerCase();
				int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
				if(index != -1){
					result = "Pass";
				}else{
					result = "Fail";
				}	
			}
			saveResult("17",result);
			
		}catch(Throwable e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "Outlet check Exception");
			result = "Block";
			saveResult("17",result);
		}
		
		//•	Accessories homepage
		//		1.	Go to lenovo.com
		//		2.	Select Accessories as products
		try{
			redirectedToUrl(driver,Url);
			clickElement(driver,b2cPage.Products_Link);
			clickElement(driver,b2cPage.accessories_Link);
//			driver.findElement(By.xpath("//li/a[@class='products_submenu' and contains(@href,'ACCESSORY')]")).click();
			
			String expectedStr = getExpectedResult(9).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			saveResult("18",result);
			
			
		}catch(Throwable e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "Accessories homepage check Exception");
			result = "Block";
			saveResult("18",result);
		}
		
		//•	Category
		//		1.	Go to lenovo.com
		//		2.	Select Accessories as products
		//		3.	Select Docking 

		try{
			redirectedToUrl(driver,Url);
			clickElement(driver,b2cPage.Products_Link);
			clickElement(driver,b2cPage.accessories_Link);
			//driver.findElement(By.xpath("//li/a[@class='products_submenu' and contains(@href,'ACCESSORY')]")).click();
			
			clickElement(driver,b2cPage.selectACategory);
			
			Random random = new Random();
			int ran = random.nextInt(2) + 2;
			System.out.println("ran is :" + ran);
			String testingAccessCategory = getElementText(driver,driver.findElement(By.xpath("(//*[@id='extraOptions-chooseCategroy']/../div/div/ul/li)["+ran+"]/a/span")));
			System.out.println("testingAccessCategory is :" + testingAccessCategory);
			clickElement(driver,driver.findElement(By.xpath("(//*[@id='extraOptions-chooseCategroy']/../div/div/ul/li)["+ran+"]")));
			String expectedStr = getExpectedResult(10).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			
			if(result.equals("Pass")){
				saveResult("19",result);
			}else{
				saveResult("19",testingAccessCategory+":"+result);
			}
			
		}catch(Throwable e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "Category check Exception");
			result = "Block";
			saveResult("19",result);
			
		}
		
		//•	Configurator
		//		1.	Go to Lenovo.com
		//		2.	Select a product: Laptops and Ultrabooks
		//		3.	Select a brand: Thinkpad 
		//		4.	Select a series: Thinkpad X
		//		5.	Select a subseries: X1 Carbon
		//		6.	Click on the customize button. The page that’s loaded should contain configurator as taxonomy type

		try{
			redirectedToUrl(driver,Url);
			clickElement(driver,b2cPage.Products_Link);
			clickElement(driver,b2cPage.laptops_subProduct);
			clickElement(driver,b2cPage.thinkpad_brand);
			clickElement(driver,b2cPage.thinkpadX_series);
			
			clickElement(driver,b2cPage.thinkpad_subSeries);
			clickElement(driver,b2cPage.viewOrCustomize);
			clickElement(driver,b2cPage.customizeButton);
			
			String expectedStr = getExpectedResult(11).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			saveResult("20",result);
			
		}catch(Throwable e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "Configurator check Exception");
			result = "Block";
			saveResult("20",result);
		}
		
		//•	Interstitial
		//		1.	Go to Lenovo.com
		//		2.	Select a product: Laptops and Ultrabooks
		//		3.	Select a brand: Thinkpad 
		//		4.	Select a series: Thinkpad X
		//		5.	Select a subseries: X1 Carbon
		//		6.	Select Customize
		//		7.	Click Add to Cart. Warranty and Software tabs should contain Interstitial

		try{
			redirectedToUrl(driver,Url);
			clickElement(driver,b2cPage.Products_Link);
			clickElement(driver,b2cPage.laptops_subProduct);
			clickElement(driver,b2cPage.thinkpad_brand);
			clickElement(driver,b2cPage.thinkpadX_series);
			
			clickElement(driver,b2cPage.thinkpad_subSeries);
			clickElement(driver,b2cPage.viewOrCustomize);
			clickElement(driver,b2cPage.customizeButton);
			clickElement(driver,b2cPage.addTocart_configPage);
			
			String expectedStr = getExpectedResult(12).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			saveResult("21",result);
			
		}catch(Throwable e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "Interstitial check Exception");
			result = "Block";
			saveResult("21",result);
		}
		
		
		
		// •	Cart
		//		1.	Go to Lenovo.com
		//		2.	Select a product: Laptops and Ultrabooks
		//		3.	Select a brand: Thinkpad 
		//		4.	Select a series: Thinkpad X
		//		5.	Select a subseries: X1 Carbon
		//		6.	Click on Add to Cart: The cart page should contain the taxonomy type Cart
		try{
			redirectedToUrl(driver,Url);
			clickElement(driver,b2cPage.Products_Link);
			clickElement(driver,b2cPage.laptops_subProduct);
			clickElement(driver,b2cPage.thinkpad_brand);
			clickElement(driver,b2cPage.thinkpadX_series);
			
			clickElement(driver,b2cPage.thinkpad_subSeries);
			clickElement(driver,b2cPage.viewOrCustomize);
			clickElement(driver,b2cPage.customizeButton);
			clickElement(driver,b2cPage.addTocart_configPage);
			clickContinue();
			
			String expectedStr = getExpectedResult(13).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			saveResult("22",result);
			
		}catch(Throwable e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "cart check Exception");
			result = "Block";
			saveResult("22",result);
		}
		
		//•	Checkout
		//		1.	Go to Lenovo.com
		//		2.	Select a product: Laptops and Ultrabooks
		//		3.	Select a brand: Thinkpad 
		//		4.	Select a series: Thinkpad X
		//		5.	Select a subseries: X1 Carbon
		//		6.	Click on Add to Cart: 
		//		7.	Click on Lenovo Checkout button
		//		8.	Note: Sign in, shipping, and Review pages should have checkout as the taxonomy type

		try{
			int ErrorsCount = 0;
			redirectedToUrl(driver,Url);
			clickElement(driver,b2cPage.Products_Link);
			clickElement(driver,b2cPage.laptops_subProduct);
			clickElement(driver,b2cPage.thinkpad_brand);
			clickElement(driver,b2cPage.thinkpadX_series);
			
			clickElement(driver,b2cPage.thinkpad_subSeries);
			clickElement(driver,b2cPage.viewOrCustomize);
			clickElement(driver,b2cPage.customizeButton);
			clickElement(driver,b2cPage.addTocart_configPage);
			clickContinue();
			clickElement(driver,b2cPage.lenovo_checkout);
			
			String expectedStr = getExpectedResult(14).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index == -1){
				ErrorsCount = ErrorsCount + 1;
			}
			// go to the sign in page 
			int index_1 = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index_1 == -1){
				ErrorsCount = ErrorsCount + 1;
			}
			
			// login and go to the shipping page
			String emailID = "lisong2@lenovo.com";
			String password = "1q2w3e4r";
			login_SignIn(b2cPage,emailID,password);
			clickElement(driver,b2cPage.shippingEdit);
			
			int index_2 = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			
			if(index_2 == -1){
				ErrorsCount = ErrorsCount + 1;
			}
			
			
			String firstName = "test";
			String lastName = "test";
			String EmailID = "lisong2@lenovo.com";
			fillShippingInfo(b2cPage,firstName,lastName,AddressLine1,City,state,zipCode,phoneNumber,EmailID);
			clickElement(driver,b2cPage.Shipping_ContinueButton);
			//  go to the payment page 
			int index_3 = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index_3 == -1){
				ErrorsCount = ErrorsCount + 1;
			}
			
			if(isElementExist(driver, By.xpath("//*[@id='useDeliveryAddress']")) && !driver.findElement(By.xpath("//*[@id='useDeliveryAddress']")).isSelected()){
				clickElement(driver,driver.findElement(By.xpath("//*[@id='useDeliveryAddress']")));
			}
			
			fillDefaultPaymentInfo(b2cPage);
			clickElement(driver,b2cPage.Payment_ContinueButton);
			// go to the summary page (place order page )
			int index_4 = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index_4 == -1){
				ErrorsCount = ErrorsCount + 1;
			}

			if(ErrorsCount == 0){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			saveResult("23",result);
			
	
			
		}catch(Throwable e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "Checkout check Exception");
			result = "Block";
			saveResult("23",result);
		}
		
		
		//•	Thank You
		//		1.	Go to Lenovo.com
		//		2.	Select a product: Laptops and Ultrabooks
		//		3.	Select a brand: Thinkpad 
		//		4.	Select a series: Thinkpad X
		//		5.	Select a subseries: X1 Carbon
		//		6.	Click on Add to Cart: 
		//		7.	Click on Lenovo Checkout button
		//		8.	Note: Sign in, shipping, and Review pages should have checkout as the taxonomy type
		//		9.	Place the order and check that the confirmation page has taxonomy type Thank You
		
		try{
			String summaryUrl = driver.getCurrentUrl().toString();
			
			if(summaryUrl.contains("www3.lenovo.com")){
				result = "Skip";
			}else{
				if(!summaryUrl.endsWith("summary")){
					redirectedToUrl(driver,Url);
					clickElement(driver,b2cPage.Products_Link);
					clickElement(driver,b2cPage.laptops_subProduct);
					clickElement(driver,b2cPage.thinkpad_brand);
					clickElement(driver,b2cPage.thinkpadX_series);
					
					clickElement(driver,b2cPage.thinkpad_subSeries);
					clickElement(driver,b2cPage.viewOrCustomize);
					clickElement(driver,b2cPage.customizeButton);
					clickElement(driver,b2cPage.addTocart_configPage);
					clickContinue();
					clickElement(driver,b2cPage.lenovo_checkout);
					String emailID = "lisong2@lenovo.com";
					String password = "1q2w3e4r";
					login_SignIn(b2cPage,emailID,password);
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
					String expectedStr = getExpectedResult(15).toLowerCase();
					int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
					if(index != -1){
						result = "Pass";
					}else{
						result = "Fail";
					}	
						
				}else{
						clickElement(driver,b2cPage.OrderSummary_AcceptTermsCheckBox);
						clickPlaceOrder(b2cPage);
						String expectedStr = getExpectedResult(15).toLowerCase();
						int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
						if(index != -1){
							result = "Pass";
						}else{
							result = "Fail";
						}
				}
			}
			
			saveResult("24",result);	
			
		}catch(Throwable e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "Checkout check Exception");
			result = "Block";
			saveResult("24",result);
		}
		
		//•	Data Center Splitter
		//		1.	Go to lenovo.com
		//		2.	Select Servers, storage and networking
		//		3.	Select Servers
		
		try{
			redirectedToUrl(driver,Url);
			clickElement(driver,b2cPage.Products_Link);
			clickElement(driver,b2cPage.servers_storage_networking_link);
			
			String expectedStr = getExpectedResult(16).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			saveResult("25",result);
		
		}catch(Throwable e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "Data Center Splitter check Exception");
			result = "Block";
			saveResult("25",result);
		}
		
		// •	Data Center Sub-Splitter
		//		1.	Go to lenovo.com
		//		2.	Select Servers, storage and networking
		//		3.	Select Servers
		//		4.	Select Blades Flex
		
		try{
			redirectedToUrl(driver,Url);
			clickElement(driver,b2cPage.Products_Link);
			clickElement(driver,b2cPage.servers_storage_networking_link);
			
			clickElement(driver,driver.findElement(By.xpath("(//div[contains(@class,'seriesListings-item')]/div/a/span)[1]")));
			
			String expectedStr = getExpectedResult(17).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			saveResult("26",result);

		}catch(Throwable e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "Data Center Splitter check Exception");
			result = "Block";
			saveResult("26",result);
		}
		
		//		•	Data Center Series
		//		1.	Go to lenovo.com
		//		2.	Select Servers, storage and networking
		//		3.	Select Servers
		//		4.	Select Blades Flex
		//		5.	Select Compute Nodes

		try{
			redirectedToUrl(driver,Url);
			clickElement(driver,b2cPage.Products_Link);
			clickElement(driver,b2cPage.servers_storage_networking_link);
			
			clickElement(driver,driver.findElement(By.xpath("(//div[contains(@class,'seriesListings-item')]/div/a/span)[1]")));
			clickElement(driver,driver.findElement(By.xpath("(//div[contains(@class,'seriesListings-item')]/div/a/span)[1]")));
			
			String expectedStr = getExpectedResult(18).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			saveResult("27",result);
			
		}catch(Throwable e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "Data Center Series check Exception");
			result = "Block";
			saveResult("27",result);
		}

		
		//		•	Data Center Subseries
		//		1.	Go to lenovo.com
		//		2.	Select Servers, storage and networking
		//		3.	Select Servers
		//		4.	Select Blades Flex
		//		5.	Select Compute Nodes
		//		6.	Select Flex System Blades x440 Compute Node
		
		try{
			redirectedToUrl(driver,Url);
			clickElement(driver,b2cPage.Products_Link);
			clickElement(driver,b2cPage.servers_storage_networking_link);
			
			clickElement(driver,driver.findElement(By.xpath("(//div[contains(@class,'seriesListings-item')]/div/a/span)[1]")));
			clickElement(driver,driver.findElement(By.xpath("(//div[contains(@class,'seriesListings-item')]/div/a/span)[1]")));
	
			clickElement(driver,driver.findElement(By.xpath("(//div[contains(@class,'seriesListings-item')]/div/a/span)[1]")));

			String expectedStr = getExpectedResult(19).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			saveResult("28",result);

		}catch(Throwable e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "Data Center Subseries check Exception");
			result = "Block";
			saveResult("28",result);
		}
		
		//		•	PLP
		//		1.	Go to lenovo.com/le
		//		2.	Select Products: Laptops
		try{
			redirectedToUrl(driver,Url);
			clickElement(driver,b2cPage.Products_Link);
			clickElement(driver,b2cPage.laptops_subProduct);
			
			String expectedStr = getExpectedResult(20).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			saveResult("29",result);
		}catch(Throwable e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "PLP check Exception");
			result = "Block";
			saveResult("29",result);
		}
		
		
		//		•	PDP
		//		1.	Go to lenovo.com/le
		//		2.	Select Products: Laptops
		//		3.	Click any one of the products listed
		try{
			redirectedToUrl(driver,Url);
			clickElement(driver,b2cPage.Products_Link);
			clickElement(driver,b2cPage.laptops_subProduct);
			
			List<WebElement> list = getElementList("//div[@id='categoryWrapper']/div[contains(@id,'tab-content')]/article/div/h1/a");
			
			int num = list.size();
			Random random = new Random();
			int point = random.nextInt(num) + 1;
			
			clickElement(driver,driver.findElement(By.xpath("(//div[@id='categoryWrapper']/div[contains(@id,'tab-content')]/article/div/h1/a)["+point+"]")));
	
			String expectedStr = getExpectedResult(21).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			saveResult("30",result);

		}catch(Throwable e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "PLP check Exception");
			result = "Block";
			saveResult("30",result);
		}
		
		//•		Account
		//		1.	Go to lenovo.com/le
		//		2.	Go to My Account
		//		3.	Click any link, the taxonomy type value should be Account
		try{
			String loginUrl = Url + "login";
			redirectedToUrl(driver,loginUrl);
			String emailID = "lisong2@lenovo.com";
			String password = "1q2w3e4r";
			login(b2cPage,emailID,password);
			
			clickElement(driver,b2cPage.myAccount_link);
			
			List<WebElement> list = getElementList("//a[contains(@href,'my-account') and @class='has-submenu']/..//li[not(contains(@class,'hidden'))]//div[@class='link_text']");
			int size = list.size();
			
			clickElement(driver,driver.findElement(By.xpath("(//a[contains(@href,'my-account') and @class='has-submenu']/..//li[not(contains(@class,'hidden'))]//div[@class='link_text'])[1]")));
			
			String expectedStr = getExpectedResult(22).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}
			saveResult("31",result);

		}catch(Throwable e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "PLP check Exception");
			result = "Block";
			saveResult("31",result);
		}

		
	}

	@AfterClass
	private void close() {
		driver.close();
		pushResult();
	}
	
	@AfterSuite
	private void doSuite(){
		markTestOver();
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
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);
		try {
			WebElement element = driver.findElement(by);
			flag = element != null;
		} catch (Exception e) {
		}
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		return flag;
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
	
	private String getCloumnNum(String startCloumn){
		int point = Integer.parseInt(startCloumn);
		int cloumn = point + 1;
		String CloumnNum = String.valueOf(cloumn);
		return CloumnNum;
	}

	private void saveResult(String columnIndex,String result) {
		outputData.add(new String[] { RowIndex, columnIndex, result});
	}
	
	private String getExpectedResult(int cloumnNum){
		String value = "";
		try{
			FileInputStream excelFileInPutStream = new FileInputStream(FactoryAndData.BIZ.MetaData_B2C.filePath);
			Workbook workbook = WorkbookFactory.create(excelFileInPutStream);
			Sheet sheet = workbook.getSheetAt(2);
			value = sheet.getRow(1).getCell(cloumnNum).getStringCellValue();
		}catch(Exception e){
			e.printStackTrace();
		}
		return value;
		
	}
	
	private void pushResult() {
		try {
			FileInputStream excelFileInPutStream = new FileInputStream(FactoryAndData.BIZ.MetaData_B2C.filePath);
			Workbook workbook = WorkbookFactory.create(excelFileInPutStream);
			Sheet sheet = workbook.getSheetAt(0);

			int rowIndex;
			int columnIndex;
			String testresult;
			int size = outputData.size();
			CellStyle yellowStyle = workbook.createCellStyle();
			yellowStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			yellowStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			CellStyle blankStyle = workbook.createCellStyle();
			blankStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			blankStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			CellStyle greenStyle = workbook.createCellStyle();
			greenStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			greenStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			for (int i = 0; i < size; i++) {
				rowIndex = Integer.parseInt(outputData.get(i)[0]);
				columnIndex = Integer.parseInt(outputData.get(i)[1]);
				testresult = outputData.get(i)[2];
				

				try {
					// Punchout result
					Row row = sheet.getRow(rowIndex);
					Cell cell= row.createCell(columnIndex);
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cell.setCellValue(testresult);
					if (testresult.equals("Pass"))
						sheet.getRow(rowIndex).getCell(columnIndex).setCellStyle(greenStyle);
					else
						sheet.getRow(rowIndex).getCell(columnIndex).setCellStyle(yellowStyle);
				} catch (Exception e) {
				}
				
			}

			FileOutputStream excelFileOutPutStream = new FileOutputStream(FactoryAndData.BIZ.MetaData_B2C.filePath);
			workbook.write(excelFileOutPutStream);
			excelFileOutPutStream.flush();
			excelFileOutPutStream.close();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
	public void markTestOver(){
		try {
			String fileName = "\\\\10.62.6.95\\MetaDataTest\\B2CAutoTestInProgress.txt";
			File file = new File(fileName);
			if (file.exists()) {
				file.delete();
			}
			fileName = "\\\\10.62.6.95\\MetaDataTest\\B2CAutoTestDone.txt";
			file = new File(fileName);

			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
