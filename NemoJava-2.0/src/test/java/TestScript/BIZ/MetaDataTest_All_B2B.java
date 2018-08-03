package TestScript.BIZ;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Pages.B2BPage;
import Pages.B2CPage;
import TestData.PropsUtils;
import TestData.TestData;
import TestScript.BIZ.PAGECHECK.CommonFunctions;


public class MetaDataTest_All_B2B {

	private WebDriver driver;

	private String Url;
	private String userName;
	private String passWord;
	
	private B2BPage b2bPage;
	
	private String result;
	private String currencycode_Expec;
	private String language_Expec;
	private String country_Expec;
	private String storeID_Expec;
	private String BPID_Expec;
	private String salesType_Expec;
	private String pageTitle_Expec;
	private String pageName_Expec;
	private String pageBreadcrumb_Expec;
	
	
	
	private String cartUrl;
	
	private String homePage_Url;
	

	public MetaDataTest_All_B2B(String homePage_url,String userName,String passWord) {
		this.Url = homePage_url;
		this.userName = userName;
		this.passWord = passWord;
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
		
		// b2b metadata testing 
		b2bPage = new B2BPage(driver);
		
		
		
		//1.	https://www3.lenovo.com/le/abbglobal/us/en/
		try{
			
			
			/*
			 * User Registration
			 * */
			
			
			try{
				redirectedToUrl(driver,this.Url);
				clickElement(driver,b2bPage.createAnAccount);
				
				allPage_test("User Registration","User Registration");

			}catch(Exception e){
				e.printStackTrace();
				System.out.println("url is :" + Url + "#####" + "User Registration check Exception");
				result = "Blocking";
				String page = "User Registration";
				String TaxononmyType_Expec = "User Registration";
				String nameValue = "taxonomytype";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				saveResult(Url,userName,passWord,page,nameValue,target,TaxononmyType_Expec,result);

			}
			
			
			//	Login
			
			try{
				redirectedToUrl(driver,this.Url);
				
				allPage_test("Login","Login");

			}catch(Exception e){
				e.printStackTrace();
				
				System.out.println("url is :" + Url + "#####" + "Login check Exception");
				result = "Blocking";
				String page = "Login";
				String TaxononmyType_Expec = "Login";
				String nameValue = "taxonomytype";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				saveResult(Url,userName,passWord,page,nameValue,target,TaxononmyType_Expec,result);

			}
			
			//Homepage
			
			try{
				redirectedToUrl(driver,this.Url);
				login(b2bPage,userName,passWord);
				
				String currentURL = getCurrentUrl(driver);
				homePage_Url = currentURL;
				if(currentURL.endsWith("/")){
					cartUrl = currentURL + "cart";
				}else{
					cartUrl = currentURL + "/cart";
				}
				
				
				allPage_test("Homepage","Homepage");
	
			}catch(Exception e){
				e.printStackTrace();
				
				System.out.println("url is :" + Url + "#####" + "Homepage check Exception");
				result = "Blocking";
				String page = "Homepage";
				String TaxononmyType_Expec = "Homepage";
				String nameValue = "taxonomytype";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				saveResult(Url,userName,passWord,page,nameValue,target,TaxononmyType_Expec,result);
	
			}
			
			//PLP
			try{
				clickElement(driver,b2bPage.HomePage_productsLink);
				clickElement(driver,driver.findElement(By.xpath("(//a[@class='products_submenu'])[1]")));
				
				allPage_test("PLP","PLP");

			}catch(Exception e){
				e.printStackTrace();
				
				System.out.println("url is :" + Url + "#####" + "PLP check Exception");
				result = "Blocking";
				String page = "PLP";
				String TaxononmyType_Expec = "PLP";
				String nameValue = "taxonomytype";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				saveResult(Url,userName,passWord,page,nameValue,target,TaxononmyType_Expec,result);
	
			}
			
			//PDP
			try{
				clickElement(driver,b2bPage.HomePage_productsLink);
				clickElement(driver,driver.findElement(By.xpath("(//a[@class='products_submenu'])[1]")));
				
				clickElement(driver,driver.findElement(By.xpath("(//*[@id='resultList']/div/div/div/a)[1]")));
				
				allPage_test("PDP","PDP");

			}catch(Exception e){
				e.printStackTrace();
				
				System.out.println("url is :" + Url + "#####" + "PDP check Exception");
				result = "Blocking";
				String page = "PDP";
				String TaxononmyType_Expec = "PDP";
				String nameValue = "taxonomytype";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				saveResult(Url,userName,passWord,page,nameValue,target,TaxononmyType_Expec,result);
	
			}
			
			//Configurator
			try{
				clickElement(driver,b2bPage.HomePage_productsLink);
				clickElement(driver,driver.findElement(By.xpath("(//a[@class='products_submenu'])[1]")));
				
				clickElement(driver,b2bPage.productPage_agreementsAndContract);
				
				if(Common.isElementExist(driver,By.xpath("//form/label[contains(.,'Agreement')]"))){
					b2bPage.productPage_radioAgreementButton.click();
				}else{
					b2bPage.productPage_raidoContractButton.click();
				}
				
				if(Common.isElementExist(driver,By.xpath("(//div[@class='agreementContract'])[contains(.,'Agreement')]"))){
					List<WebElement> agreementList = driver.findElements(By.xpath("(//div[@class='agreementContract'])[contains(.,'Agreement')]"));
					if(agreementList.size() >= 1){
						driver.findElement(By.xpath("(//*[@id='resultList']/div/div[4]/a)[1]")).click();
						Thread.sleep(5000);
						
						allPage_test("Configurator","Configurator");
					}	
				}else{
					result = "Skip";
					String page = "Configurator";
					String TaxononmyType_Expec = "Configurator";
					String nameValue = "taxonomytype";
					String pageContent = driver.getPageSource().toString();
					String target = getContent(pageContent,nameValue);
					saveResult(Url,userName,passWord,page,nameValue,target,TaxononmyType_Expec,result);	
				}
				
			}catch(Exception e){
				e.printStackTrace();
				
				System.out.println("url is :" + Url + "#####" + "Configurator check Exception");
				result = "Blocking";
				String page = "Configurator";
				String TaxononmyType_Expec = "Configurator";
				String nameValue = "taxonomytype";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				saveResult(Url,userName,passWord,page,nameValue,target,TaxononmyType_Expec,result);
			}
						
			//Product Builder (Interstitial)
			
			try{
				clickElement(driver,b2bPage.HomePage_productsLink);
				clickElement(driver,driver.findElement(By.xpath("(//a[@class='products_submenu'])[1]")));
				
				clickElement(driver,driver.findElement(By.xpath("//form[contains(@id,'addToAccessorisForm')]/button")));
				
				allPage_test("Interstitial","Product Builder");

			}catch(Exception e){
				e.printStackTrace();
				
				System.out.println("url is :" + Url + "#####" + "Product Builder check Exception");
				result = "Blocking";
				String page = "Product Builder";
				String TaxononmyType_Expec = "Interstitial";
				String nameValue = "taxonomytype";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				saveResult(Url,userName,passWord,page,nameValue,target,TaxononmyType_Expec,result);
				
			}
			
			
			//Cart
			
			try{
				clickElement(driver,b2bPage.HomePage_productsLink);
				clickElement(driver,driver.findElement(By.xpath("(//a[@class='products_submenu'])[1]")));
				
				b2bPage.addToCartBtn.click();
				b2bPage.addtoCartPOP.click();
				(new WebDriverWait(driver, 500)).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h2[contains(text(),'Adding to Cart')]")));
		
				redirectedToUrl(driver,cartUrl);
				
				allPage_test("Cart","Cart");
				
			}catch(Exception e){
				e.printStackTrace();
				
				System.out.println("url is :" + Url + "#####" + "Cart check Exception");
				result = "Blocking";
				String page = "Cart";
				String TaxononmyType_Expec = "Cart";
				String nameValue = "taxonomytype";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				saveResult(Url,userName,passWord,page,nameValue,target,TaxononmyType_Expec,result);
			}
			
			//Checkout
			//shipping 
			try{
				clickElement(driver,b2bPage.cartPage_LenovoCheckout);
				
				allPage_test("Checkout","Checkout-shipping");
				
			}catch(Exception e){
				e.printStackTrace();
				
				System.out.println("url is :" + Url + "#####" + "Checkout shipping  check Exception");
				result = "Blocking";
				String page = "Checkout-shipping";
				String TaxononmyType_Expec = "Checkout";
				String nameValue = "taxonomytype";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				saveResult(Url,userName,passWord,page,nameValue,target,TaxononmyType_Expec,result);
	
			}
			
			//Checkout
			//payment 
			try{
				clickElement(driver,b2bPage.shippingEdit);
				clickElement(driver,b2bPage.shippingCarrotIcon);
				clickElement(driver,b2bPage.shippingSelectAddress);
				Thread.sleep(4000);
				clearTxt(driver,b2bPage.FirstName);
				sendKeysIntoElement(driver,b2bPage.FirstName,"test");
				clearTxt(driver,b2bPage.LastName);
				sendKeysIntoElement(driver,b2bPage.LastName,"test");
				clearTxt(driver,b2bPage.companyInput);
				String Account = Url.split("/")[4];
				sendKeysIntoElement(driver,b2bPage.companyInput,Account);
				clearTxt(driver,b2bPage.Mobile);
				clickElement(driver,b2bPage.shippingPage_ContinueToPayment);
				
				
				allPage_test("Checkout","Checkout-payment");
	
			}catch(Exception e){
				e.printStackTrace();
				
				System.out.println("url is :" + Url + "#####" + "Checkout payment  check Exception");
				result = "Blocking";
				String page = "Checkout-payment";
				String TaxononmyType_Expec = "Checkout";
				String nameValue = "taxonomytype";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				saveResult(Url,userName,passWord,page,nameValue,target,TaxononmyType_Expec,result);
					
			}
			
			//place order page 
			try{
				if(isElementExist(driver, By.xpath("//input[@id='PaymentTypeSelection_PURCHASEORDER']"))){
					
					clickElement(driver,driver.findElement(By.xpath("//input[@id='PaymentTypeSelection_PURCHASEORDER']")));
					clearElement(driver,b2bPage.purchaseNum);
					sendKeysIntoElement(driver,b2bPage.purchaseNum,"1234567890");
					
					SimpleDateFormat dataFormat = new SimpleDateFormat("MM/dd/YYYY");
					sendKeysIntoElement(driver,b2bPage.purchaseDate,dataFormat.format(new Date()).toString());
					
					if(Common.isElementExist(driver, By.xpath("//*[@id='checkoutForm-fieldset-purchaseorder']/fieldset"))){
						if(isElementExist(driver,By.xpath("//div[@id='ui-datepicker-div']//tr[last()]/td/a"))){
				            driver.findElements(By.xpath("//div[@id='ui-datepicker-div']//tr[last()]/td/a")).get(driver.findElements(By.xpath("//div[@id='ui-datepicker-div']//tr[last()]/td/a")).size()-1).click();
				        }	
					}			
				}else if(isElementExist(driver, By.xpath("//input[@id='PaymentTypeSelection_CARD']"))){
					clickElement(driver,driver.findElement(By.xpath("//span[contains(@class,'triangle')]")));
					clickElement(driver,driver.findElement(By.xpath("(//li[contains(@id,'ui-id')])[1]")));
					clearElement(driver,b2bPage.purchaseNum);
					sendKeysIntoElement(driver,b2bPage.purchaseNum,"1234567890");
					clearElement(driver,b2bPage.addressFirstName);
					sendKeysIntoElement(driver,b2bPage.addressFirstName,"test");
					clearElement(driver,b2bPage.addressLastName);
					sendKeysIntoElement(driver,b2bPage.addressLastName,"account");
					clearElement(driver,b2bPage.addressPhone);
					SimpleDateFormat dataFormat = new SimpleDateFormat("MM/dd/YYYY");
					sendKeysIntoElement(driver,b2bPage.purchaseDate,dataFormat.format(new Date()).toString());
					if(isElementExist(driver,By.xpath("//div[@id='ui-datepicker-div']//tr[last()]/td/a"))){
			            driver.findElements(By.xpath("//div[@id='ui-datepicker-div']//tr[last()]/td/a")).get(driver.findElements(By.xpath("//div[@id='ui-datepicker-div']//tr[last()]/td/a")).size()-1).click();
			        }
	
				}
				clickElement(driver,b2bPage.ContinueforPayment);
				
				allPage_test("Checkout","Checkout-palce order");
				
				
			}catch(Exception e){
				e.printStackTrace();
				
				System.out.println("url is :" + Url + "#####" + "Checkout place order  check Exception");
				result = "Blocking";
				String page = "Checkout-palce order";
				String TaxononmyType_Expec = "Checkout";
				String nameValue = "taxonomytype";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				saveResult(Url,userName,passWord,page,nameValue,target,TaxononmyType_Expec,result);
			}
			
			//Thank you
			try{
				String summaryUrl = getCurrentUrl_prod();
				if(summaryUrl.contains("www3.lenovo.com")){
					result = "Skip";
					String page = "Thank You";
					String TaxononmyType_Expec = "Thank you";
					String nameValue = "taxonomytype";
					String pageContent = driver.getPageSource().toString();
					String target = getContent(pageContent,nameValue);
					saveResult(Url,userName,passWord,page,nameValue,target,TaxononmyType_Expec,result);	
				}else{
					
					if(isElementExist(driver, By.xpath("//*[@id='ccEmailAddress']"))){
						sendKeysIntoElement(driver,b2bPage.ccEmailAddress,"testlenovo@lenovo.com");
					}
					if(isElementExist(driver, By.xpath("//*[@id='commentArea']"))){
						sendKeysIntoElement(driver,b2bPage.commentArea,"good");
					}
					if(isElementExist(driver, By.xpath("//*[@id='shippingLabel']"))){
						sendKeysIntoElement(driver,b2bPage.shippingLabel,"good");
					}
					if(isElementExist(driver, By.xpath("//*[@id='invoiceInstruction']"))){
						sendKeysIntoElement(driver,b2bPage.invoiceInstruction,"good");
					}
					clickElement(driver,driver.findElement(By.xpath("//input[@id='Terms1']")));
					
					clickPlaceOrder(b2bPage);
					
					allPage_test("Thank you","Thank You");
		
				}
				
			}catch(Exception e){
				e.printStackTrace();
				
				System.out.println("url is :" + Url + "#####" + "Thank You  check Exception");
				result = "Blocking";
				String page = "Thank You";
				String TaxononmyType_Expec = "Thank you";
				String nameValue = "taxonomytype";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				saveResult(Url,userName,passWord,page,nameValue,target,TaxononmyType_Expec,result);

			}
			
			
			//Accessories Homepage
			try{
				redirectedToUrl(driver,homePage_Url);
				
				clickElement(driver,b2bPage.MenuAccessory);
				
				allPage_test("Accessory Homepage","Accessory Homepage");
	
			}catch(Exception e){
				e.printStackTrace();
				
				System.out.println("url is :" + Url + "#####" + "Thank You  check Exception");
				result = "Blocking";
				String page = "Accessory Homepage";
				String TaxononmyType_Expec = "Accessory Homepage";
				String nameValue = "taxonomytype";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				saveResult(Url,userName,passWord,page,nameValue,target,TaxononmyType_Expec,result);
			}
			
			//Accessories Category
			try{
				
				clickElement(driver,driver.findElement(By.xpath("(//div[@class='yCmsComponent']/div/div/h2/a)[1]")));
				allPage_test("Category","Accessories Category");

			}catch(Exception e){
				e.printStackTrace();
				
				System.out.println("url is :" + Url + "#####" + "Category  check Exception");
				result = "Blocking";
				String page = "Accessories Category";
				String TaxononmyType_Expec = "Category";
				String nameValue = "taxonomytype";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				saveResult(Url,userName,passWord,page,nameValue,target,TaxononmyType_Expec,result);
				
			}
			
			//Product
			try{
				clickElement(driver,driver.findElement(By.xpath("//*[@id='mainContent']//a[@class='accessoriesListing-footer-button']/span")));
				
				allPage_test("Product Page","Accessories Product Page");
					
			}catch(Exception e){
				e.printStackTrace();
				
				System.out.println("url is :" + Url + "#####" + "Product  check Exception");
				result = "Blocking";
				String page = "Accessories Product Page";
				String TaxononmyType_Expec = "Product Page";
				String nameValue = "taxonomytype";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				saveResult(Url,userName,passWord,page,nameValue,target,TaxononmyType_Expec,result);

			}
			
			/*
			 * Account
			 * 
			 * */			
		
			//Order Status
			try{
				redirectedToUrl(driver,Url);
				clickElement(driver,b2bPage.myAccount_link);
				
				String currentwinHandle = driver.getWindowHandle().toString();
				
				clickElement(driver,driver.findElement(By.xpath("//ul/li/a[contains(@href,'Status')]")));
				
				Set<String> set = driver.getWindowHandles();
				
				for(String str : set){
					if(str.equals(currentwinHandle)){
						continue;
					}
					driver.switchTo().window(str);
				}

				allPage_test("Order Status","Order Status");
				
				driver.close();
				
				driver.switchTo().window(currentwinHandle);
				
				//driver.switchTo().defaultContent();
	
			}catch(Exception e){
				e.printStackTrace();
				
				System.out.println("url is :" + Url + "#####" + "Order Status check Exception");
				result = "Blocking";
				String page = "Order Status";
				String TaxononmyType_Expec = "Order Status";
				String nameValue = "taxonomytype";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				saveResult(Url,userName,passWord,page,nameValue,target,TaxononmyType_Expec,result);
	
			}

			//User Profile
			
			try{
				redirectedToUrl(driver,Url);
				clickElement(driver,b2bPage.myAccount_link);
				clickElement(driver,driver.findElement(By.xpath("//ul/li/a[contains(@href,'profile')]")));
				
				allPage_test("User Profile","User Profile");
				
				
			}catch(Exception e){
				e.printStackTrace();
				
				System.out.println("url is :" + Url + "#####" + "User Profile  check Exception");
				result = "Blocking";
				String page = "User Profile";
				String TaxononmyType_Expec = "User Profile";
				String nameValue = "taxonomytype";
				String pageContent = driver.getPageSource().toString();
				String target = getContent(pageContent,nameValue);
				saveResult(Url,userName,passWord,page,nameValue,target,TaxononmyType_Expec,result);
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
		
	private void clickPlaceOrder(B2BPage b2bPage) {
		if (!driver.getCurrentUrl().contains("www3.lenovo.com"))
			clickElement(driver,b2bPage.OrderSummary_PlaceOrderButton);
	}
	
	
	
	
	private void saveResult(String Url,String userName,String passWord,String page,String metaTag,String actualResult,String expectedResult,String status){
		String sql_lan  = "insert into " + "metadatareport_b2b" + "(Url,userName,passWord,Page,MetaTag,ActualResult,ExpectedResult,Status) values("+"'"+Url+"'"+","+"'"+userName+"'"+","+"'"+passWord+"'"+","+"'"+page+"'"+","+"'"+metaTag+"'"+","+"'"+actualResult+"'"+","+"'"+expectedResult+"'"+","+"'"+status+"'"+");";
		
		try{
			Common.stmt.executeUpdate(sql_lan);
		}catch(Exception e){
			e.printStackTrace();
		}
	
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
	
	public void login(B2BPage page, String username, String password) {
		sendKeysIntoElement(driver,page.IDcheckbox,username);
		sendKeysIntoElement(driver,page.PWcheckbox,password);
		clickElement(driver,page.signin);
		
	}
	
	
	public String getCurrentUrl(WebDriver driver){
		String str = "";
		if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
			driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
		}
		try{
			str = driver.getCurrentUrl().toString();
		}catch(Exception e){
			e.printStackTrace();
			if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
				driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
			}
			str = driver.getCurrentUrl().toString();
		}
		
		return str;
		
	}

	public void clickElement(WebDriver driver,WebElement element){
		
		if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
			driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
		}
		try{
			element.click();
		}catch(Exception e){
			if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
				driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
			}
			element.click();
		}

	}
	
	public void clearElement(WebDriver driver,WebElement element){
		if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
			driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
		}
		try{
			element.clear();
		}catch(Exception e){
			if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
				driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
			}
			element.clear();
		}
	}
	
	
	public void clearTxt(WebDriver driver,WebElement element){
		if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
			driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
		}
		try{
			element.clear();
		}catch(Exception e){
			if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
				driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
			}
			element.clear();
		}

	}	
	
	
	
	public void sendKeysIntoElement(WebDriver driver,WebElement element,String str){
		if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
			driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
		}
		try{
			element.sendKeys(str);
		}catch(Exception e){
			if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
				driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
			}
			element.sendKeys(str);
		}

	}
	
	public void redirectedToUrl(WebDriver driver,String url){
		driver.get(url);
		if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
			driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
		}
	}
	
	public String getCurrentUrl_prod(){
		if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
			driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
		}
		String str = driver.getCurrentUrl().toString();
		return str;
	}
	
	
	
	public void clickPlaceOrder(B2CPage b2cPage) {
		if (!b2cPage.PageDriver.getCurrentUrl().contains("www3.lenovo.com"))
			clickElement(driver,b2cPage.OrderSummary_PlaceOrderButton);
	}
	
	
	
	public void markTestOver(){
		try {
			String fileName = "\\\\100.67.28.133\\MetaDataTest\\B2B_All_AutoTestInProgress.txt";
			File file = new File(fileName);
			if (file.exists()) {
				file.delete();
			}
			fileName = "\\\\100.67.28.133\\MetaDataTest\\B2B_All_AutoTestDone.txt";
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
			saveResult(Url,userName,passWord,page,nameValue,target,TaxononmyType_Expec,result);
	
		}catch(Exception e){
			e.printStackTrace();
			String TaxononmyType_Expec = taxononmyType_Expec;
			String pageContent = driver.getPageSource().toString();
			String nameValue = "taxonomytype";
			result = "Block";
			String target = getContent(pageContent,nameValue);
			String page = pageName;
			saveResult(Url,userName,passWord,page,nameValue,target,TaxononmyType_Expec,result);
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
			saveResult(Url,userName,passWord,page,nameValue,target,currencycode_Expec,result);
		}catch(Exception e){
			 e.printStackTrace();
			 result = "Block";
			 String nameValue = "currencycode";
			 String pageContent = driver.getPageSource().toString();
			 String target = getContent(pageContent,nameValue);
			 String page = pageName;
			 saveResult(Url,userName,passWord,page,nameValue,target,currencycode_Expec,result);
		}
		//language
		try{
			language_Expec = Url.split("/")[6];
			String nameValue = "language";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			if(target.equals(language_Expec)){
				result = "Pass";
			}else{
				result = "Fail";
			}
			 String page = pageName;
			saveResult(Url,userName,passWord,page,nameValue,target,language_Expec,result);
		}catch(Exception e){
			result = "Block";
			String page = pageName;
			String nameValue = "language";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			saveResult(Url,userName,passWord,page,nameValue,target,language_Expec,result);
		}
		
		
		//country
		try{
			country_Expec = Url.split("/")[5].toUpperCase();
			String nameValue = "country";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			if(target.equals(country_Expec)){
				result = "Pass";
			}else{
				result = "Fail";
			}
			String page = pageName;
			saveResult(Url,userName,passWord,page,nameValue,target,country_Expec,result);
		}catch(Exception e){
			String page = pageName;
			String nameValue = "country";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			result = "Block";
			saveResult(Url,userName,passWord,page,nameValue,target,country_Expec,result);
		}
		
		
		//storeID
		try{
			storeID_Expec = homePage_Url.split("/")[7];
			String nameValue = "storeID";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			if(target.equals(storeID_Expec)){
				result = "Pass";
			}else{
				result = "Fail";
			}
			String page = pageName;
			saveResult(Url,userName,passWord,page,nameValue,target,storeID_Expec,result);
		}catch(Exception e){
			String page = pageName;
			String nameValue = "storeID";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			result = "Block";
			saveResult(Url,userName,passWord,page,nameValue,target,storeID_Expec,result);
		}
		
		
		//BPID
		try{
			BPID_Expec = homePage_Url.split("/")[7];
			String nameValue = "BPID";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			
			if(target.equals(BPID_Expec)){
				result = "Pass";
			}else{
				result = "Fail";
			}
			String page = pageName;
			saveResult(Url,userName,passWord,page,nameValue,target,BPID_Expec,result);
		}catch(Exception e){
			e.printStackTrace();
			String page = "Splitter page";
			String nameValue = "BPID";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			result = "Block";
			saveResult(Url,userName,passWord,page,nameValue,target,BPID_Expec,result);
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
			saveResult(Url,userName,passWord,page,nameValue,target,salesType_Expec,result);
		}catch(Exception e){
			e.printStackTrace();
			String page = pageName;
			String nameValue = "salestype";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			result = "Block";
			saveResult(Url,userName,passWord,page,nameValue,target,salesType_Expec,result);
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
			saveResult(Url,userName,passWord,page,nameValue,target,pageTitle_Expec,result);
			
		}catch(Exception e){
			e.printStackTrace();
			String nameValue = "pageTitle";
			String page = pageName;
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			result = "Block";
			saveResult(Url,userName,passWord,page,nameValue,target,pageTitle_Expec,result);
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
			
			saveResult(Url,userName,passWord,page,nameValue,target,pageTitle_Expec,result);
			
		}catch(Exception e){
			e.printStackTrace();
			String page = pageName;
			String nameValue = "pageName";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			result = "Block";
			saveResult(Url,userName,passWord,page,nameValue,target,pageTitle_Expec,result);

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
			
			saveResult(Url,userName,passWord,page,nameValue,target,pageBreadcrumb_Expec,result);
	
		}catch(Exception e){
			e.printStackTrace();
			String page = pageName;
			String nameValue = "pageBreadcrumb";
			String pageContent = driver.getPageSource().toString();
			String target = getContent(pageContent,nameValue);
			result = " Block";
			saveResult(Url,userName,passWord,page,nameValue,target,pageBreadcrumb_Expec,result);	
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

}
