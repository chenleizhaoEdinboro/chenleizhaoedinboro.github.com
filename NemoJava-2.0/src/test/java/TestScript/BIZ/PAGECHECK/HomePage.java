package TestScript.BIZ.PAGECHECK;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.EMailCommon;
import Pages.B2CPage;
import Pages.MailPage;
import TestData.TestData;

public class HomePage {
	
	public String Country;
	public String Url;
	public String Account;
	public String Password;
	public String Tablename;
	public String machineInfo;
	
	public WebDriver driver1;
	public B2CPage b2cPage;
	
	public int ErrorsCount = 0;
	
	public TestData testData;
	public String Environment;
	public String Store;
	
	
	
	
	public HomePage(String Store,String Country,String Url,String Account,String Password,String machineInfo){
		this.Store = Store;
		this.Country = Country;
		this.Url = Url;
		this.Account = Account;
		this.Password = Password;
		this.machineInfo = machineInfo;
	}
	
	CommonFunctions Common = new CommonFunctions();
	
	@BeforeSuite
	public void prepareTest(){
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	@BeforeClass
	@Parameters("Environment")
	public void setUp(String Environment){
		this.Environment = Environment;
		System.out.print("Environment is :" + Environment);
		Common.getConnection();
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		System.setProperty("java.net.preferIPv4Stack" , "true");
		testData = new TestData(this.Environment,
				"B2C", this.Store);
	}
	
	/*
	 * Home Page check Test
	 * */
	
	
	@Test
	public void Biz_homePage(){

		ChromeOptions options = new ChromeOptions();
		options.addArguments("chrome.switches", "--disable-extensions");
		driver1 = new ChromeDriver(options);
		driver1.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		b2cPage = new B2CPage(driver1);
		String homePageUrl = testData.B2C.getHomePageUrl();
		driver1.get(homePageUrl);
		driver1.manage().window().maximize();
		
		/*
		 * Test masthead functionality
		 * */		
		// Test each tabs appearance
		// -- Each product image and label appear in the Product dropdown
		try{
			List<WebElement> navigationList = driver1.findElements(By.xpath("//ul[@class='menu prd_Menu']/li/a/span"));
			
			int Errors_image_label  = ErrorsCount;
		
			String navigation_name = driver1.findElement(By.xpath("//ul[@class='menu prd_Menu']/li[contains(@class,'product')]/a/span")).getText().toString();
			driver1.findElement(By.xpath("//ul[@class='menu prd_Menu']/li[contains(@class,'product')]/a/span")).click();
			System.out.println("navigation_name is :" + navigation_name);
			List<WebElement> subList = driver1.findElements(By.xpath("//a[@class='products_submenu']"));
			for(int y = 1; y<=subList.size(); y++){
				
				boolean label_displayed = driver1.findElement(By.xpath("(//a[@class='products_submenu'])["+y+"]/div")).isDisplayed();
				System.out.println("label_displayed:"+ label_displayed);
				String sub_category = driver1.findElement(By.xpath("(//a[@class='products_submenu'])["+y+"]/div")).getText().toString();
				boolean picExists = !driver1.findElement(By.xpath("(//a[@class='products_submenu'])["+y+"]")).getAttribute("style").isEmpty();
				System.out.println("picExists:"+ picExists);
				
				System.out.println("judge:"+ (label_displayed && picExists));
				
				String summary = "Test each tabs appearance";
				String testpoint = "Each product image and label appear in the Product dropdown";
				ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23148", label_displayed && picExists, "NA-23148" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + sub_category + " does not appear in the product dropdown", summary, testpoint, ErrorsCount);
		
			}

			if(ErrorsCount == Errors_image_label){
				String summary = "Test each tabs appearance";
				String testpoint = "Each product image and label appear in the Product dropdown";
				Common.AddTestCasePassContent("NA-23148" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}
		
		}catch(Exception e){
			String summary = "Test each tabs appearance";
			String testpoint = "Each product image and label appear in the Product dropdown";
			
			ErrorsCount++;
			Common.detailFailReason(e, "NA-23148", "NA-23148" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo , summary, testpoint, ErrorsCount);
		}
		
		// -- Weekly deal list appear, as well as each promotion image and label in Deals dropdown
		
		try{
			driver1.get(Url);
			b2cPage.Deals_Link.click();
			
			int Errors_Deals = ErrorsCount;
			
			List<WebElement> dealsLink = driver1.findElements(By.xpath("//a[contains(@class,'linkLevel_3')]"));
			for(int x =1; x <= dealsLink.size();x++){
				
				boolean b = driver1.findElement(By.xpath("(//a[contains(@class,'linkLevel_3')])["+x+"]")).isDisplayed();
				String text = driver1.findElement(By.xpath("(//a[contains(@class,'linkLevel_3')]/div)["+x+"]")).getText().toString();
				
				String summary = "Test each tabs appearance";
				String testpoint = "Weekly deal list appear";
				ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23148", b, "NA-23148" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + text + " does not appear in the Deals dropdown", summary, testpoint, ErrorsCount);
				
			}
			
			
			
			if(Common.isElementExist(driver1, By.xpath("//li[contains(@class,'menu_level_2 promo')]"))){
				List<WebElement> list_promotion = driver1.findElements(By.xpath("//li[contains(@class,'menu_level_2 promo')]"));
				for(int x = 1; x <= list_promotion.size(); x++){
					boolean b = driver1.findElement(By.xpath("(//li[contains(@class,'menu_level_2 promo')])["+x+"]")).isDisplayed();
					String summary = "Test each tabs appearance";
					String testpoint = "each promotion image and label in Deals dropdown appears";
					ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23148", b, "NA-23148" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" +"the "+ x +" promotion product does not appear in the Deals dropdown", summary, testpoint, ErrorsCount);
				}
				
			}
			
			if(ErrorsCount == Errors_Deals){
				String summary = "Test each tabs appearance";
				String testpoint = "Weekly deal list appear, as well as each promotion image and label in Deals dropdown";
				Common.AddTestCasePassContent("NA-23148" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			String summary = "Test each tabs appearance";
			String testpoint = "Weekly deal list appear, as well as each promotion image and label in Deals dropdown";
			
			ErrorsCount++;
			Common.detailFailReason(e, "NA-23148", "NA-23148" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo , summary, testpoint, ErrorsCount);
		}
		
		
		// Each support image and label appear in the Support dropdown
		
		try{
			driver1.get(Url);
			b2cPage.Support_Link.click();
			
			List<WebElement> supportSubList = driver1.findElements(By.xpath("//li[contains(@class,'support_menu')]//ul/li"));
			
			List<WebElement> supportList = driver1.findElements(By.xpath("//li[contains(@class,'support_menu')]//div[@class='support-submenu']//span[contains(@class,'support-icon')]"));
			//li[contains(@class,'support_menu')]//div[@class='support-submenu']//span[@class='over-title']
			int support = ErrorsCount;
			
			for(int x = 1; x <= supportSubList.size(); x++){
				String text = driver1.findElement(By.xpath("(//li[contains(@class,'support_menu')]//div[@class='support-submenu']//span[@class='over-title'])["+x+"]")).getText().toString();
				String summary = "Test each tabs appearance";
				String testpoint = "Each support image and label appear in the Support dropdown";
				ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23148",driver1.findElement(By.xpath("(//li[contains(@class,'support_menu')]//div[@class='support-submenu']//span[@class='over-title'])["+x+"]")).isDisplayed() && driver1.findElement(By.xpath("(//li[contains(@class,'support_menu')]//div[@class='support-submenu']//span[contains(@class,'support-icon')])["+x+"]")).isDisplayed() , "NA-23148" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + text + " does not appear in the support dropdown", summary, testpoint, ErrorsCount);
			}
			
			if(support == ErrorsCount){
				String summary = "Test each tabs appearance";
				String testpoint = "Each support image and label appear in the Support dropdown";
				Common.AddTestCasePassContent("NA-23148" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
				
			}
	
		}catch(Exception e){
			e.printStackTrace();
			String summary = "Test each tabs appearance";
			String testpoint = "Each support image and label appear in the Support dropdown";
			
			ErrorsCount++;
			Common.detailFailReason(e, "NA-23148", "NA-23148" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);
		}
		
		//  The solutions side bars appears as well as the banner
		try{
			driver1.get(Url);
			
			String summary = "Test each tabs appearance";
			String testpoint = "The solutions side bars appears as well as the banner";
			
			int solutions = ErrorsCount;
			ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23148", driver1.findElement(By.xpath("//ul[@class='menu general_Menu']/li[contains(@class,'solutions')]/a/span")).isDisplayed(), "NA-23148" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "Solutions does not appear on the homepage", summary, testpoint, ErrorsCount);
			
			if(ErrorsCount == solutions){
				Common.AddTestCasePassContent("NA-23148" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}
	
			
		}catch(Exception e){
			e.printStackTrace();
			String summary = "Test each tabs appearance";
			String testpoint = "The solutions side bars appears as well as the banner";
			Common.detailFailReason(e, "NA-23148", "NA-23148" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);
	
		}
		
		// The masthead dropdowns should appear and disappear when clicked
		//Validate the masthead links navigate properly. 
		try{
			driver1.get(Url);
			List<WebElement> list = driver1.findElements(By.xpath("//ul[@class='menu prd_Menu']/li"));
			int appear_disappear = ErrorsCount;
			
			for(int x =1; x <= list.size(); x++){
				
				String text = driver1.findElement(By.xpath("(//ul[@class='menu prd_Menu']/li/a/span)["+x+"]")).getText().toString();
				driver1.findElement(By.xpath("(//ul[@class='menu prd_Menu']/li/a/span)["+x+"]")).click();
				boolean b1 = driver1.findElement(By.xpath("(//ul[@class='menu prd_Menu']/li/div)["+x+"]")).isDisplayed();
				driver1.findElement(By.xpath("(//ul[@class='menu prd_Menu']/li/a/span)["+x+"]")).click();
				boolean b2 = driver1.findElement(By.xpath("(//ul[@class='menu prd_Menu']/li/div)["+x+"]")).isDisplayed();
				
				String summary = "Test each tabs appearance";
				String testpoint = "The masthead dropdowns should appear and disappear when clicked";
				ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23148", b1 && !b2,  "NA-23148" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + text +" can not appear or disappear when clicked", summary, testpoint, ErrorsCount);

			}
			
			driver1.findElement(By.xpath("//ul[@class='menu general_Menu']/li[contains(@class,'solutions')]/a/span")).click();
			boolean b3 = driver1.findElement(By.xpath("//li[contains(@class,'solutions')]/div")).isDisplayed();
			driver1.findElement(By.xpath("//ul[@class='menu general_Menu']/li[contains(@class,'solutions')]/a/span")).click();
			boolean b4 = driver1.findElement(By.xpath("//li[contains(@class,'solutions')]/div")).isDisplayed();
			
			String summary = "Test each tabs appearance";
			String testpoint = "The solution dropdowns should appear and disappear when clicked";
			ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23148", b3 && !b4, "NA-23148" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "solutions can not appear or disappear when clicked", summary, testpoint, ErrorsCount);
			
			if(appear_disappear == ErrorsCount){
				Common.AddTestCasePassContent("NA-23148" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}

		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			String summary = "Test each tabs appearance";
			String testpoint = "The masthead dropdowns should appear and disappear when clicked";
			Common.detailFailReason(e, "NA-23148", "NA-23148" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);
		}		
		
		/* 
		 * Test the links at the header and footer of page. 
		- Ensure they are displaying and functioning properly. 
		*/
		
		// Test the links at the header
		
		// ensure the chat window pops up
		
		try{
			int popup_window = ErrorsCount;
			
			driver1.get(Url);
			
			
		
			//???????????????????????????????????????
			driver1.findElement(By.xpath("(//ul[contains(@class,'common_Menu')]/li[contains(@class,'phone')]/a)[1]")).click();
			Thread.sleep(6000);
			driver1.findElement(By.xpath(".//*[@id='accept']")).click();

			
			if(ErrorsCount == popup_window){
				String summary = "Test the links at the header";
				String testpoint = "ensure the chat window pops up";
				Common.AddTestCasePassContent("NA-23153" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}
			
				
			
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			String summary = "Test the links at the header";
			String testpoint = "ensure the chat window pops up";
			Common.detailFailReason(e, "NA-23153", "NA-23153" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);

		}
		
		//Ensure the Cart dropdown appears and disappears when clicked, as well as displaying properly
		try{
			
			int cart_appear_disappear = ErrorsCount;
			driver1.findElement(By.xpath(".//*[@id='cartIcon']/a")).click();
			boolean b1 = driver1.findElement(By.xpath("//*[@id='rollovercartViewCart']")).isDisplayed();
			driver1.findElement(By.xpath(".//*[@id='cartIcon']/a")).click();
			boolean b2 = driver1.findElement(By.xpath("//*[@id='rollovercartViewCart']")).isDisplayed();

			String summary = "Test the links at the header";
			String testpoint = "Ensure the Cart dropdown appears and disappears when clicked, as well as displaying properly";
			ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23153", b1 && !b2, "NA-23153" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "cart icon can not appear or disappear when clicked", summary, testpoint, ErrorsCount);

			if(ErrorsCount == cart_appear_disappear){
				Common.AddTestCasePassContent("NA-23153" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			
			String summary = "Test the links at the header";
			String testpoint = "Ensure the Cart dropdown appears and disappears when clicked, as well as displaying properly";
			Common.detailFailReason(e, "NA-23153", "NA-23153" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);
		
		}
		
		// -- Each item in the Cart dropdown should image, name, and quantity
		
		try{
			
			int item_cart = ErrorsCount;
			
			driver1.get(testData.B2C.getHomePageUrl());
			
			if (!driver1.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
			//B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
			if (!driver1.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
	
			String homeUrl = driver1.getCurrentUrl().toString();
			System.out.println("homeUrl is :" + homeUrl);
			String cartUrl = homeUrl+"/cart";
			System.out.println("cartUrl is :" + cartUrl);
			
			driver1.get(cartUrl);
			
			String partNumber = testData.B2C.getDefaultMTMPN();
			System.out.println("partNumber is :" + partNumber);
			
			b2cPage.Cart_QuickOrderTextBox.clear();
			b2cPage.Cart_QuickOrderTextBox.sendKeys(partNumber);
			b2cPage.Cart_AddButton.click();
			
			String summary = "Test the links at the header";
			String testpoint = "Each item in the Cart dropdown should image, name, and quantity";
			
			if(Common.isElementExist(driver1, By.xpath("//div[@class='cart-item']"))){
				driver1.findElement(By.xpath(".//*[@id='cartIcon']/a")).click();
				
				boolean b = !driver1.findElement(By.xpath("//*[@id='rollovercartContentsItems']/li/a/img")).getAttribute("src").isEmpty() && driver1.findElement(By.xpath("//*[@id='rollovercartContentsItems']/li/a/span[@class='rollovercartItemName']")).isDisplayed() && driver1.findElement(By.xpath(".//*[@id='rollovercartContentsItems']/li/a/span[@class='rollovercartItemQty']")).isDisplayed();
				ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23153", b, "NA-23153" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "Each item in the Cart dropdown does not show  image, name, and quantity", summary, testpoint, ErrorsCount);
	
			}else{
				ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23153", false, "NA-23153" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "Each item in the Cart dropdown does not show  image, name, and quantity", summary, testpoint, ErrorsCount);
			}
			
			if(ErrorsCount == item_cart){
				Common.AddTestCasePassContent("NA-23153" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}
	
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			String summary = "Test the links at the header";
			String testpoint = "Each item in the Cart dropdown should image, name, and quantity";
			Common.detailFailReason(e, "NA-23153", "NA-23153" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);

		}
		
		//  - Ensure the My Account dropdown appears, disappears, and displays properly when clicked, as well as each link works
		//    If the user is not signed in, there should be "Sign In", "Orders", and "Product Registration" links
		try{
			int beforeLogin = ErrorsCount;
			driver1.get(testData.B2C.getloginPageUrl());
			if (!driver1.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
			b2cPage.myAccount_link.click();
			
			boolean b = driver1.findElement(By.xpath("(//li[not(contains(@class,'hidden'))]/div/a[contains(@href,'my-account') and contains(@class,'linkLevel_2')])[1]")).isDisplayed() && driver1.findElement(By.xpath("//a[contains(@class,'linkLevel_2')  and contains(@href,'orders')]")).isDisplayed();
			String summary = "Test the links at the header";
			String testpoint = "If the user is not signed in, there should be Sign In, Orders, and Product Registration links";
			
			ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23153", b, "NA-23153" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "Before login in , there are not Sign In, Order ", summary, testpoint, ErrorsCount);
			
			if(beforeLogin == ErrorsCount){
				Common.AddTestCasePassContent("NA-23153" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			String summary = "Test the links at the header";
			String testpoint = "If the user is not signed in, there should be Sign In, Orders, and Product Registration links";
			Common.detailFailReason(e, "NA-23153", "NA-23153" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);
		}
		
		//  If the user is signed in, there should be "My Account", "Orders", "Product Registration", and "Sign Out" links
		try{
			int singedIn = ErrorsCount;
			
			driver1.get(testData.B2C.getloginPageUrl());
			
			if (!driver1.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
			if (!driver1.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
			
			b2cPage.myAccount_link.click();
			
			boolean b = driver1.findElement(By.xpath("(//li[not(contains(@class,'hidden'))]/div/a[contains(@href,'my-account') and contains(@class,'linkLevel_2')])[1]")).isDisplayed() && driver1.findElement(By.xpath("//a[contains(@class,'linkLevel_2')  and contains(@href,'orders')]")).isDisplayed() && driver1.findElement(By.xpath("//a[contains(@class,'linkLevel_2')  and contains(@href,'logout')]")).isDisplayed();
			String summary = "Test the links at the header";
			String testpoint = "If the user is signed in, there should be My Account, Orders, Product Registration, and Sign Out links";
			
			ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23153", b, "NA-23153" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "After login in , there are not My Account, Orders, Sign Out ", summary, testpoint, ErrorsCount);
			
			if(ErrorsCount == singedIn){
				Common.AddTestCasePassContent("NA-23153" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}

			
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			String summary = "Test the links at the header";
			String testpoint = "If the user is signed in, there should be My Account, Orders, Product Registration, and Sign Out links";
			Common.detailFailReason(e, "NA-23153", "NA-23153" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);

		}
		
		//  - Ensure the community link navigates properly
		try{
			int community = ErrorsCount;
			
			driver1.get(Url);
			if(Common.isElementExist(driver1, By.xpath("//li[contains(@class,'community')]/a"))){
				driver1.findElement(By.xpath("//li[contains(@class,'community')]/a")).click();
				
				
				String community_url = driver1.getCurrentUrl().toString();
				
				String summary = "Test the links at the header";
				String testpoint = "Ensure the community link navigates properly";
				
				boolean b = community_url.contains("Community");
				
				ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23153", b, "NA-23153" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "community link can not navigates properly", summary, testpoint, ErrorsCount);
		
			}
			
			String summary = "Test the links at the header";
			String testpoint = "Ensure the community link navigates properly";
			if(community == ErrorsCount){
				Common.AddTestCasePassContent("NA-23153" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}
	
			
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			String summary = "Test the links at the header";
			String testpoint = "Ensure the community link navigates properly";
			
			Common.detailFailReason(e, "NA-23153", "NA-23153" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);
			
			
		}
		
		//Test the links at the footer
		// - Ensure all links in the footer dropdown navigate properly
		try{
			// ???????????????????????????????????
			
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		/*
		 * 
		 * Test search bar functionality and display
		 * 
		 * */
		// Test search bar functionality and display
		// Search suggestions should dropdown as the user types
		
		try{
			int search_suggestion = ErrorsCount;
			
			driver1.get(Url);
			b2cPage.HomePage_SearchIcon.click();
			b2cPage.HomePage_SearchTextArea.click();
			b2cPage.HomePage_SearchTextArea.sendKeys("Thinkpad");
			
			Thread.sleep(10000);
			
			
			String summary = "Test search bar functionality and display";
			String testpoint = "Search suggestions should dropdown as the user types";
			
			boolean b = driver1.findElement(By.xpath("//ul[@id='ui-id-1']/li")).isDisplayed();
			
			ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23174", b, "NA-23174" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "Search suggestions can not show dropdown as the user types", summary, testpoint, ErrorsCount);
			
			if(ErrorsCount == search_suggestion){
				Common.AddTestCasePassContent("NA-23174" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}
	
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			String summary = "Test search bar functionality and display";
			String testpoint = "Search suggestions should dropdown as the user types";
			Common.detailFailReason(e, "NA-23174", "NA-23174" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);
		}
		
		// Each product displayed should have an image, name, and price
		
		try{
			int image_name_price = ErrorsCount;
			
			driver1.get(Url);
			b2cPage.HomePage_SearchIcon.click();
			b2cPage.HomePage_SearchTextArea.click();
			b2cPage.HomePage_SearchTextArea.sendKeys("Thinkpad");
			
			Thread.sleep(10000);
			
			
			String summary = "Test search bar functionality and display";
			String testpoint = "Each product displayed should have an image, name, and price";
			
			List<WebElement> list = driver1.findElements(By.xpath("//div[@class='result-item']"));
			
			for(int x = 1; x<= list.size(); x++){
				
				boolean b_image = driver1.findElement(By.xpath("(//div[@class='result-item']/img)["+x+"]")).isDisplayed();
				boolean b_name = driver1.findElement(By.xpath("(//div[@class='result-item']/div)["+x+"]")).isDisplayed();
				boolean b_price = driver1.findElement(By.xpath("(//div[@class='result-item']/div/span)["+x+"]")).isDisplayed();
				
				boolean b = b_image && b_name && b_price;
				
				ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23174", b, "NA-23174" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "not every product displayed can show image, name, and price", summary, testpoint, ErrorsCount);
				
				
			}
			
			if(image_name_price == ErrorsCount){
				Common.AddTestCasePassContent("NA-23174" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			String summary = "Test search bar functionality and display";
			String testpoint = "Each product displayed should have an image, name, and price";
			ErrorsCount++;
			Common.detailFailReason(e, "NA-23174", "NA-23174" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);

		}
		
		// Ensure each of these dropdown links navigate properly
		try{
			int navigate_Error = ErrorsCount;
			
			driver1.get(Url);
			b2cPage.HomePage_SearchIcon.click();
			b2cPage.HomePage_SearchTextArea.click();
			b2cPage.HomePage_SearchTextArea.sendKeys("Thinkpad");
			
			Thread.sleep(10000);
			List<WebElement> list = driver1.findElements(By.xpath("//div[@class='result-item']"));
			
			for(int x = 1; x<=list.size(); x++){
				String productName = driver1.findElement(By.xpath("(//div[@class='result-item']/div)["+x+"]/strong")).getText().toString();
				System.out.println(x + "-----" + "productName is :" + productName);
				
				driver1.findElement(By.xpath("(//div[@class='result-item'])["+x+"]")).click();
				
				if(Common.isElementExist(driver1, By.xpath("//*[@id='view-customize']"))){
					String text = driver1.findElement(By.xpath("(//*[@id='longscroll-subseries']/div[1]/h1)")).getText().toString();
					System.out.println(x + "-----" + "text is :" + productName);
					boolean b = text.contains(productName);
					
					String summary = "Test search bar functionality and display";
					String testpoint = "Ensure each of these dropdown links navigate properly";
					ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23174", b, "NA-23174" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + productName +  " can not links navigate properly", summary, testpoint, ErrorsCount);
	
				}else{
					String summary = "Test search bar functionality and display";
					String testpoint = "Ensure each of these dropdown links navigate properly";
					ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23174", false, "NA-23174" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + productName +  " can not links navigate properly", summary, testpoint, ErrorsCount);
				}
				
				driver1.get(Url);
				b2cPage.HomePage_SearchIcon.click();
				b2cPage.HomePage_SearchTextArea.click();
				b2cPage.HomePage_SearchTextArea.sendKeys("Thinkpad");
				Thread.sleep(10000);
			}
			
			if(navigate_Error == ErrorsCount){
				String summary = "Test search bar functionality and display";
				String testpoint = "Ensure each of these dropdown links navigate properly";
				Common.AddTestCasePassContent("NA-23174" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}

		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			String summary = "Test search bar functionality and display";
			String testpoint = "Ensure each of these dropdown links navigate properly";
			
			Common.detailFailReason(e, "NA-23174", "NA-23174" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);
			
		}
		
	
		/*
		 * Hero image should display and fucntion properly
		 * 
		 * */
		//- Each hero image should display
		
		try{
			int HeroImage = ErrorsCount;
			driver1.get(Url);
			
			List<WebElement> heroList = driver1.findElements(By.xpath("//div[@id='hero-lightSlider']/div"));
			
			for(int x = 1; x <= heroList.size(); x++){
				int a = 0;
				String pic_name = driver1.findElement(By.xpath("(//div[@id='hero-lightSlider']/div)["+x+"]/div[1]/img")).getAttribute("alt").toString();
				List<WebElement> imgList1 = driver1.findElements(By.xpath("(//div[@id='hero-lightSlider']/div)["+x+"]/div[1]/img"));
				for(int y =1; y <= imgList1.size(); y++){
					boolean b1 = !driver1.findElement(By.xpath("((//div[@id='hero-lightSlider']/div)["+x+"]/div[1]/img)["+y+"]")).getAttribute("src").isEmpty();
					if(b1 == false){
						a = 1;
					}
					System.out.println("1--------:" + b1 + "-----:" + a);
				}
				List<WebElement> imgList2 = driver1.findElements(By.xpath("((//div[@id='hero-lightSlider']/div)["+x+"]/div[2]/img)"));
				int c = 0;
				for(int z =1; z <= imgList2.size(); z++){
					boolean b2  = !driver1.findElement(By.xpath("((//div[@id='hero-lightSlider']/div)["+x+"]/div[2]/img)["+z+"]")).getAttribute("src").isEmpty();
					System.out.println("2--------:" + b2);
					if(b2 == false){
						c =1;
						break;
					}
				}
				
				boolean b = (a+c)==0;
				System.out.println("b is :" + b);
				
				String summary = "Hero image should display and function properly";
				String testpoint = "Each hero image should display";
				ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23179", b, "NA-23179" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + pic_name +  " can not display", summary, testpoint, ErrorsCount);

			}
			
			if(ErrorsCount == HeroImage){
				String summary = "Hero image should display and function properly";
				String testpoint = "Each hero image should display";
				Common.AddTestCasePassContent("NA-23179" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}
				
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			String summary = "Hero image should display and function properly";
			String testpoint = "Each hero image should display";
			Common.detailFailReason(e, "NA-23179", "NA-23179" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);

		}
		
		
		//Ensure each link on each hero image navigates properly
		try{
			driver1.get(Url);
			int hero_Image_Navigate = ErrorsCount;
			//(//*[@id='hero-lightSlider']//div[contains(@class,'active')]//a[@class='premium-links'])[1]
			
			List<WebElement> heroList = driver1.findElements(By.xpath("//div[@id='hero-lightSlider']/div"));
			
			for(int x = 1; x <= heroList.size(); x++){
				driver1.findElement(By.xpath("//*[@id='rotating-hero-banner']//a[@class='lSNext']")).click();
				String textLink = driver1.findElement(By.xpath("//*[@id='rotating-hero-banner']//a[@class='lSNext']")).getText().toString();
				//Explore the IdeaPad 320
				driver1.findElement(By.xpath("//*[@id='rotating-hero-banner']//a[@class='lSNext']")).click();
				
				
				
				
				
				
				
			}
			
			if(ErrorsCount == hero_Image_Navigate){
				String summary = "Hero image should display and function properly";
				String testpoint = "Ensure each link on each hero image navigates properly";
				Common.AddTestCasePassContent("NA-23179" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			String summary = "Hero image should display and function properly";
			String testpoint = "Ensure each link on each hero image navigates properly";
			Common.detailFailReason(e, "NA-23179", "NA-23179" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);
		}
		
		//Ensure the user can navigate between hero images using the side arrows or the bars below the hero images.
		try{
			int switch_heroImage = ErrorsCount;
			
			List<WebElement> heroList = driver1.findElements(By.xpath("//div[@id='hero-lightSlider']/div"));
			
			for(int x = 1; x <= heroList.size(); x++){
				driver1.findElement(By.xpath("//*[@id='rotating-hero-banner']//a[@class='lSNext']")).click();
				String textLink = driver1.findElement(By.xpath("//*[@id='rotating-hero-banner']//a[@class='lSNext']")).getText().toString();
				//Explore the IdeaPad 320
				driver1.findElement(By.xpath("//*[@id='rotating-hero-banner']//a[@class='lSNext']")).click();
				
				Thread.sleep(5000);
				
				String textLink_1 = driver1.findElement(By.xpath("//*[@id='rotating-hero-banner']//a[@class='lSNext']")).getText().toString();
				boolean b = !textLink.equals(textLink_1);
				
				String summary = "Hero image should display and function properly";
				String testpoint = "Ensure each link on each hero image navigates properly";
				ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23179", b, "NA-23179" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "the user can not navigate between hero images using the side arrows", summary, testpoint, ErrorsCount);

			}
			
			if(switch_heroImage == ErrorsCount){
				String summary = "Hero image should display and function properly";
				String testpoint = "Ensure each link on each hero image navigates properly";
				Common.AddTestCasePassContent("NA-23179" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			String summary = "Hero image should display and function properly";
			String testpoint = "Ensure each link on each hero image navigates properly";
			Common.detailFailReason(e, "NA-23179", "NA-23179" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);	
		}
		
		/*
		 * 
		 * Validate country selection dropdown list functions properly and navigates to the proper site.
		 * 
		 * */
		
		// - Ensure that each link for each country navigates to the proper site
		
		try{
			int properSite = ErrorsCount;
			driver1.get(Url);
			
			((JavascriptExecutor) driver1).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);
			List<WebElement> list = driver1.findElements(By.xpath("//*[@id='countrySelector']/option"));
			for(int x = 1; x <list.size(); x++){
				driver1.findElement(By.xpath("//*[@id='countrySelector']")).click();
				String optionName = driver1.findElement(By.xpath("(//*[@id='countrySelector']/option)["+x+"]")).getAttribute("value").toString();
				String country = driver1.findElement(By.xpath("(//*[@id='countrySelector']/option)["+x+"]")).getText().toString();
				driver1.findElement(By.xpath("(//*[@id='countrySelector']/option)["+x+"]")).click();
				Thread.sleep(5000);
				String countryUrl = driver1.getCurrentUrl().toString();
				
				boolean b = countryUrl.contains(optionName);
				String summary = "Validate country selection dropdown list functions properly and navigates to the proper site.";
				String testpoint = "Ensure that each link for each country navigates to the proper site";
				
				ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23213", b, "NA-23213" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + country + " can not navigate to proper site", summary, testpoint, ErrorsCount);
				
				driver1.get(Url);
				((JavascriptExecutor) driver1).executeScript("window.scrollTo(0, document.body.scrollHeight)");
				Thread.sleep(5000);
				
			}
			
			if(properSite == ErrorsCount){
				String summary = "Validate country selection dropdown list functions properly and navigates to the proper site.";
				String testpoint = "Ensure that each link for each country navigates to the proper site";
				Common.AddTestCasePassContent("NA-23213" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			
			}
			
		}catch(Exception e){
			ErrorsCount++;
			String summary = "Validate country selection dropdown list functions properly and navigates to the proper site.";
			String testpoint = "Ensure that each link for each country navigates to the proper site";
			Common.detailFailReason(e, "NA-23213", "NA-23213" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);	

		}
		
		
		//Ensure that the dropdown appears and disappers when the user clicks to do so
		
		try{
			int appear_disappear = ErrorsCount;
			driver1.get(Url);
			
			((JavascriptExecutor) driver1).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);
			driver1.findElement(By.xpath("//*[@id='countrySelector']")).click();
			boolean b1 = driver1.findElement(By.xpath(".//*[@id='countrySelector']/option")).isDisplayed();
			driver1.findElement(By.xpath("//*[@id='countrySelector']")).click();
			boolean b2 = !driver1.findElement(By.xpath(".//*[@id='countrySelector']/option")).isDisplayed();
			
			boolean b = b1 && b2;
			
			String summary = "Validate country selection dropdown list functions properly and navigates to the proper site.";
			String testpoint = "Ensure that the dropdown appears and disappers when the user clicks to do so";
			
			ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23213", b, "NA-23213" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "the country dropdown can not expand or hide after click it ", summary, testpoint, ErrorsCount);
			
			if(appear_disappear == ErrorsCount){
				Common.AddTestCasePassContent("NA-23213" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			String summary = "Validate country selection dropdown list functions properly and navigates to the proper site.";
			String testpoint = "Ensure that the dropdown appears and disappers when the user clicks to do so";
			
			Common.detailFailReason(e, "NA-23213", "NA-23213" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);		
		}
		
		// Ensure that each flag appears when on the corresponding site
		
		try{
			int flag_Errors = ErrorsCount;
			driver1.get(Url);
			
			((JavascriptExecutor) driver1).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);
			
			List<WebElement> list = driver1.findElements(By.xpath("//*[@id='countrySelector']/option"));
			for(int x = 1; x <list.size(); x++){
				driver1.findElement(By.xpath("//*[@id='countrySelector']")).click();
				String optionName = driver1.findElement(By.xpath("(//*[@id='countrySelector']/option)["+x+"]")).getAttribute("value").toString();
				String country = driver1.findElement(By.xpath("(//*[@id='countrySelector']/option)["+x+"]")).getText().toString();
				String cc = driver1.findElement(By.xpath("(//*[@id='countrySelector']/option)["+x+"]")).getAttribute("cc").toString();
			
				driver1.findElement(By.xpath("(//*[@id='countrySelector']/option)["+x+"]")).click();
				Thread.sleep(5000);
				
				String flag_class = driver1.findElement(By.xpath("//*[@id='countrySelector']/../div")).getAttribute("class").toString();
				
				boolean b = flag_class.contains(cc);
				
				String summary = "Validate country selection dropdown list functions properly and navigates to the proper site.";
				String testpoint = "Ensure that each flag appears when on the corresponding site";
				
				ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23213", b, "NA-23213" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "the country dropdown can not expand or hide after click it ", summary, testpoint, ErrorsCount);
				
				if(flag_Errors == ErrorsCount){
					Common.AddTestCasePassContent("NA-23213" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
				}
	
				driver1.get(Url);
				((JavascriptExecutor) driver1).executeScript("window.scrollTo(0, document.body.scrollHeight)");
				Thread.sleep(5000);
				
			}
	
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			String summary = "Validate country selection dropdown list functions properly and navigates to the proper site.";
			String testpoint = "Ensure that each flag appears when on the corresponding site";
			
			Common.detailFailReason(e, "NA-23213", "NA-23213" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);			
		}
	
		
		//Ensure the user can scroll through options and select desired country
		try{
			int scrollFunc = ErrorsCount;
			driver1.get(Url);
			
			((JavascriptExecutor) driver1).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);
			
			List<WebElement> list = driver1.findElements(By.xpath("//*[@id='countrySelector']/option"));
			driver1.findElement(By.xpath("//*[@id='countrySelector']")).click();
			
			String optionName = driver1.findElement(By.xpath("(//*[@id='countrySelector']/option)["+list.size()+"]")).getAttribute("value").toString();
			WebElement target = driver1.findElement(By.xpath("(//*[@id='countrySelector']/option)["+list.size()+"]"));
			Actions action = new Actions(driver1);
			action.moveToElement(target).perform();
			action.click(target).perform();
			
			String currentUrl = driver1.getCurrentUrl().toString();
			
			boolean b = currentUrl.contains(optionName);
			
			String summary = "Validate country selection dropdown list functions properly and navigates to the proper site.";
			String testpoint = "Ensure the user can scroll through options and select desired country";
			
			ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23213", b, "NA-23213" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "user can not scroll through options and select desired country ", summary, testpoint, ErrorsCount);
			
			if(scrollFunc == ErrorsCount){
				Common.AddTestCasePassContent("NA-23213" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}

		}catch(Exception e){
			e.printStackTrace();
			
			ErrorsCount++;
			String summary = "Validate country selection dropdown list functions properly and navigates to the proper site.";
			String testpoint = "Ensure the user can scroll through options and select desired country";
			
			Common.detailFailReason(e, "NA-23213", "NA-23213" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);			
			
		}
		
		
		/*
		 * 
		 * Feedback link on rightside of page should open a feedback popup
		 * 
		 * */
		
		//  Feedback link on rightside of page should open a feedback popup
		
		try{
			int Feedback = ErrorsCount;
			driver1.get(Url);
			String mainHandle = driver1.getWindowHandle().toString();
			
			driver1.findElement(By.xpath("//*[@id='oo_tab']")).click();
			
			Set<String> set = driver1.getWindowHandles();
			System.out.println("set size is :" + set.size());

			for(String str : set){
				if(str.equals(mainHandle)){
					continue;
				}
				driver1.switchTo().window(str);
			}
			
			boolean b = driver1.findElement(By.xpath("//div[@class='container']//input[@type='submit']")).isDisplayed();
			
			driver1.close();
			driver1.switchTo().defaultContent();
			
			String summary = "Validate country selection dropdown list functions properly and navigates to the proper site.";
			String testpoint = "Feedback link on rightside of page should open a feedback popup";
			
			ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23213", b, "NA-23213" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "Feedback link on rightside of page can not open a feedback popup ", summary, testpoint, ErrorsCount);
			
			if(Feedback == ErrorsCount){
				Common.AddTestCasePassContent("NA-23213" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}

			
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			String summary = "Validate country selection dropdown list functions properly and navigates to the proper site.";
			String testpoint = "Feedback link on rightside of page should open a feedback popup";
			
			Common.detailFailReason(e, "NA-23213", "NA-23213" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);			
		}
		
		
		
		// The user cannot submit their feedback unless they give their overall rating at the top
		
		try{
			int overrall = ErrorsCount;
			
			driver1.get(Url);
			String mainHandle = driver1.getWindowHandle().toString();
			
			driver1.findElement(By.xpath("//*[@id='oo_tab']")).click();
			
			Set<String> set = driver1.getWindowHandles();
			System.out.println("set size is :" + set.size());

			for(String str : set){
				if(str.equals(mainHandle)){
					continue;
				}
				driver1.switchTo().window(str);
			}
			
			driver1.findElement(By.xpath("//div[@class='container']//input[@type='submit']")).click();
			
			boolean b = Common.isElementExist(driver1,By.xpath("//section[contains(@class,'ratings')]/div/section[@class='required error']"));
			
			driver1.close();
			driver1.switchTo().defaultContent();
			
			
			String summary = "Validate country selection dropdown list functions properly and navigates to the proper site.";
			String testpoint = "The user cannot submit their feedback unless they give their overall rating at the top";
			
			ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23213", b, "NA-23213" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "The user can submit their feedback unless they give their overall rating at the top ", summary, testpoint, ErrorsCount);
			
			if(overrall == ErrorsCount){
				Common.AddTestCasePassContent("NA-23213" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}
			
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			
			ErrorsCount++;
			String summary = "Validate country selection dropdown list functions properly and navigates to the proper site.";
			String testpoint = "The user cannot submit their feedback unless they give their overall rating at the top";
			
			Common.detailFailReason(e, "NA-23213", "NA-23213" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);			

		}
		
		// All other fields can be left blank if the user desires
		
		try{
			int left_blank = ErrorsCount;
			
			driver1.get(Url);
			String mainHandle = driver1.getWindowHandle().toString();
			
			driver1.findElement(By.xpath("//*[@id='oo_tab']")).click();
			
			Set<String> set = driver1.getWindowHandles();
			System.out.println("set size is :" + set.size());

			for(String str : set){
				if(str.equals(mainHandle)){
					continue;
				}
				driver1.switchTo().window(str);
			}
			
			List<WebElement> list = driver1.findElements(By.xpath("//div[@class='radiogroup']/div/input"));
			
			int point = list.size()/2;
			
			driver1.findElement(By.xpath("(//div[@class='radiogroup']/div/input)["+point+"]")).click();
			driver1.findElement(By.xpath("//div[@class='container']//input[@type='submit']")).click();
			
			boolean b = !Common.isElementExist(driver1, By.xpath("//div[@class='container']//input[@type='submit']"));
			
			driver1.close();
			driver1.switchTo().defaultContent();
			
			String summary = "Validate country selection dropdown list functions properly and navigates to the proper site.";
			String testpoint = " All other fields can be left blank if the user desires";
			
			ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23213", b, "NA-23213" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "other fields can not be left blank ", summary, testpoint, ErrorsCount);
			
			if(left_blank == ErrorsCount){
				Common.AddTestCasePassContent("NA-23213" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}

		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			
			String summary = "Validate country selection dropdown list functions properly and navigates to the proper site.";
			String testpoint = " All other fields can be left blank if the user desires";
			Common.detailFailReason(e, "NA-23213", "NA-23213" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);				
		}
		
		
		/*
		 * - Click on the 'Print this Page' link at bottom of page. 
		 * Print popup should appear and prompt the user on print settings.
		 * 
		 * 
		 * */
		
		//Click on the 'Print this Page' link at bottom of page. 
		//Print popup should appear and prompt the user on print settings.
		
		try{
			int printPage = ErrorsCount;
			driver1.get(Url);
			((JavascriptExecutor) driver1).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);
			String attr = "window.print();";
			String onlick = driver1.findElement(By.xpath("//div[@class='footer-bottomBar-nav']/a[@href='#']")).getAttribute("onclick").toString();
			
			boolean b = attr.equals(onlick);
			
			String summary = "Click on the 'Print this Page' link at bottom of page. Print popup should appear and prompt the user on print settings.";
			String testpoint = " Print popup should appear and prompt the user on print settings";
			
			ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23238", b, "NA-23238" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "print pop up can not appear", summary, testpoint, ErrorsCount);
			
			if(printPage == ErrorsCount){
				Common.AddTestCasePassContent("NA-23238" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}

			
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			String summary = "Click on the 'Print this Page' link at bottom of page. Print popup should appear and prompt the user on print settings.";
			String testpoint = " Print popup should appear and prompt the user on print settings";
			
			Common.detailFailReason(e, "NA-23238", "NA-23238" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);				
	
		}	
		
		
		/*
		 * 
		 *	- Login/ logout works with cloud accounts
			- able to access saved cart, quote, order history, wishlist etc
			- Edit profile info
			- Able to create new accounts
			- Forget password/ user name for existing accounts
			- Forget password email should work.. Url in the email should be valid
		 * 
		 * 
		 * 
		 * */
		
		//-- User can update their first and last name, their title, 
		//and whether they would like to receive free email offers from Lenovo
		
		try{
			int update_ErrorsCount = ErrorsCount;
			
			driver1.get(testData.B2C.getloginPageUrl());
			
			if (!driver1.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
			if (!driver1.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
			
			
			
			
			b2cPage.myAccount_link.click();
			driver1.findElement(By.xpath("(//li[not(contains(@class,'hidden'))]/div/a[contains(@href,'my-account') and contains(@class,'linkLevel_2')])[1]")).click();
			
			driver1.findElement(By.xpath("//div[@class='signInModule-content']//a[contains(@href,'profile')]")).click();
			driver1.findElement(By.xpath("//a[@href='update-profile']")).click();
			
			boolean firstName = driver1.findElement(By.xpath("//*[@id='profile.firstName']")).isEnabled();
			boolean lastName = driver1.findElement(By.xpath("//*[@id='profile.lastName']")).isEnabled();
			boolean option = driver1.findElement(By.xpath("//*[@id='profile.optin']")).isEnabled();
			
			boolean b = firstName && lastName && option;
			
			
			String summary = "User can edit their details informaion";
			String testpoint = " User can update their first and last name, their title, and whether they would like to receive free email offers from Lenovo";
			
			ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23265", b, "NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "user can not edit their first name and last name and the whether they would like to receive free email offers from Lenovo", summary, testpoint, ErrorsCount);
			
			if(update_ErrorsCount == ErrorsCount){
				Common.AddTestCasePassContent("NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}	
			
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			String summary = "User can edit their details informaion";
			String testpoint = " User can update their first and last name, their title, and whether they would like to receive free email offers from Lenovo";
			
			Common.detailFailReason(e, "NA-23265", "NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);				
	
		}
		
		
		//User can access address book, payment details, order history,
		//saved quotes, and save cart history from profile page
		try{
			int address = ErrorsCount;
			
			driver1.get(testData.B2C.getloginPageUrl());
			
			if (!driver1.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
			if (!driver1.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
			
			driver1.get(Url);
			b2cPage.myAccount_link.click();
			driver1.findElement(By.xpath("(//li[not(contains(@class,'hidden'))]/div/a[contains(@href,'my-account') and contains(@class,'linkLevel_2')])[1]")).click();
			driver1.findElement(By.xpath("//div[@class='signInModule-content']//a[contains(@href,'profile')]")).click();
			
			driver1.findElement(By.xpath(".//*[@id='accountNav']/ul/li/a[contains(@href,'address-book')]")).click();
			
			 boolean b = driver1.findElement(By.xpath("//*[@id='addressBookPage']")).isDisplayed();
			
			String summary = "User can update address book";
			String testpoint = "User can update address book";
			
			ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23265", b, "NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "can not access address book", summary, testpoint, ErrorsCount);
			
			if(address == ErrorsCount){
				Common.AddTestCasePassContent("NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}
	
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			String summary = "User can update address book";
			String testpoint = "User can update address book";
			Common.detailFailReason(e, "NA-23265", "NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);				
		}
		
		// payment details
		
		try{
			int payment_details = ErrorsCount;
			
			driver1.get(testData.B2C.getloginPageUrl());
			
			if (!driver1.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
			if (!driver1.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
			
			driver1.get(Url);
			b2cPage.myAccount_link.click();
			driver1.findElement(By.xpath("(//li[not(contains(@class,'hidden'))]/div/a[contains(@href,'my-account') and contains(@class,'linkLevel_2')])[1]")).click();
			driver1.findElement(By.xpath("//div[@class='signInModule-content']//a[contains(@href,'profile')]")).click();
			
			
			driver1.findElement(By.xpath(".//*[@id='accountNav']/ul/li/a[contains(@href,'payment-details')]")).click();
			
			boolean b = driver1.findElement(By.xpath("//div[@class='paymentItem']")).isDisplayed();
			
			String summary = "User can update payment details";
			String testpoint = "User can update payment details";
			
			ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23265", b, "NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "User can not update payment details", summary, testpoint, ErrorsCount);
			
			if(payment_details == ErrorsCount){
				Common.AddTestCasePassContent("NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			String summary = "User can update payment details";
			String testpoint = "User can update payment details";
			Common.detailFailReason(e, "NA-23265", "NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);					
		}
		
		//order history
		try{
			int order_history = ErrorsCount;
			
			driver1.get(testData.B2C.getloginPageUrl());
			
			if (!driver1.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
			if (!driver1.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
			
			driver1.get(Url);
			b2cPage.myAccount_link.click();
			driver1.findElement(By.xpath("(//li[not(contains(@class,'hidden'))]/div/a[contains(@href,'my-account') and contains(@class,'linkLevel_2')])[1]")).click();
			driver1.findElement(By.xpath("//div[@class='signInModule-content']//a[contains(@href,'profile')]")).click();
			
			
			driver1.findElement(By.xpath("//*[@id='accountNav']/ul/li/a[contains(@href,'orders')]")).click();
			
			boolean b = driver1.findElement(By.xpath("//h1[@class='bar_3-heading']")).isDisplayed();
			String summary = "User can access order history";
			String testpoint = "User can access order history";
			
			ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23265", b, "NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "User can not access order history", summary, testpoint, ErrorsCount);
			
			if(order_history == ErrorsCount){
				Common.AddTestCasePassContent("NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}
	
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			
			String summary = "User can access order history";
			String testpoint = "User can access order history";
			Common.detailFailReason(e, "NA-23265", "NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);					
		}
		
		//saved quotes
		try{
			int saved_quotes = ErrorsCount;
			
			driver1.get(testData.B2C.getloginPageUrl());
			
			if (!driver1.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
			if (!driver1.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
			
			driver1.get(Url);
			b2cPage.myAccount_link.click();
			driver1.findElement(By.xpath("(//li[not(contains(@class,'hidden'))]/div/a[contains(@href,'my-account') and contains(@class,'linkLevel_2')])[1]")).click();
			driver1.findElement(By.xpath("//div[@class='signInModule-content']//a[contains(@href,'profile')]")).click();
			
			driver1.findElement(By.xpath("//*[@id='accountNav']/ul/li/a[contains(@href,'quotes')]")).click();
			
			boolean b = driver1.findElement(By.xpath("//h1[@class='bar_3-heading']")).isDisplayed();
			
			String summary = "User can access quote history";
			String testpoint = "User can access quote history";
			
			ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23265", b, "NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "User can not access quote history", summary, testpoint, ErrorsCount);
			
			if(saved_quotes == ErrorsCount){
				Common.AddTestCasePassContent("NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			
			String summary = "User can access quote history";
			String testpoint = "User can access quote history";
			
			Common.detailFailReason(e, "NA-23265", "NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);					
		}
		
		// save cart history
		try{
			
			int cart_history = ErrorsCount;
			
			driver1.get(testData.B2C.getloginPageUrl());
			
			if (!driver1.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
			if (!driver1.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
			
			driver1.get(Url);
			b2cPage.myAccount_link.click();
			driver1.findElement(By.xpath("(//li[not(contains(@class,'hidden'))]/div/a[contains(@href,'my-account') and contains(@class,'linkLevel_2')])[1]")).click();
			driver1.findElement(By.xpath("//div[@class='signInModule-content']//a[contains(@href,'profile')]")).click();
			
			driver1.findElement(By.xpath(".//*[@id='accountNav']/ul/li/a[contains(@href,'save-carts')]")).click();
			
			boolean b = driver1.findElement(By.xpath("//h1[@class='bar_3-heading']")).isDisplayed();
			
			String summary = "User can access quote history";
			String testpoint = "User can access quote history";
			
			ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23265", b, "NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "User can not access save cart history", summary, testpoint, ErrorsCount);
			
			if(cart_history == ErrorsCount){
				Common.AddTestCasePassContent("NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			String summary = "User can access quote history";
			String testpoint = "User can access quote history";
			
			Common.detailFailReason(e, "NA-23265", "NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);					
			
		}
		
		
		//User must check the terms and conditions agreement box to continue
		
		try{
			int terms = ErrorsCount;
			
			driver1.get(testData.B2C.getloginPageUrl());
			b2cPage.createAccount.click();
			
			String timestamp = Common.getDateTimeString();
			
			String Email = "test"+timestamp+"@lenovo.com";
			String firstname = "test";
			String lastname = "test";
			
			String password = "1q2w3e4r";
			
			b2cPage.RegistrateAccount_EmailIdTextBox.clear();
			b2cPage.RegistrateAccount_EmailIdTextBox.sendKeys(Email);
			
			b2cPage.RegistrateAccount_ConfirmEmailTextBox.clear();
			b2cPage.RegistrateAccount_ConfirmEmailTextBox.sendKeys(Email);
			
			b2cPage.RegistrateAccount_FirstNameTextBox.clear();
			b2cPage.RegistrateAccount_FirstNameTextBox.sendKeys(firstname);
			
			b2cPage.RegistrateAccount_LastNameTextBox.clear();
			b2cPage.RegistrateAccount_LastNameTextBox.sendKeys(lastname);
			
			b2cPage.RegistrateAccount_PasswordTextBox.clear();
			b2cPage.RegistrateAccount_PasswordTextBox.sendKeys(password);
			
			b2cPage.RegistrateAccount_ConfirmPasswordTextBox.clear();
			b2cPage.RegistrateAccount_ConfirmPasswordTextBox.sendKeys(password);
			
			b2cPage.RegistrateAccount_CreateAccountButton.click();
			
			boolean b = driver1.findElement(By.xpath("//*[@id='acceptTerms.errors']")).isDisplayed();
			
			b2cPage.RegistrateAccount_EmailIdTextBox.clear();
			b2cPage.RegistrateAccount_EmailIdTextBox.sendKeys(Email);
			
			b2cPage.RegistrateAccount_ConfirmEmailTextBox.clear();
			b2cPage.RegistrateAccount_ConfirmEmailTextBox.sendKeys(Email);
			
			b2cPage.RegistrateAccount_FirstNameTextBox.clear();
			b2cPage.RegistrateAccount_FirstNameTextBox.sendKeys(firstname);
			
			b2cPage.RegistrateAccount_LastNameTextBox.clear();
			b2cPage.RegistrateAccount_LastNameTextBox.sendKeys(lastname);
			
			b2cPage.RegistrateAccount_PasswordTextBox.clear();
			b2cPage.RegistrateAccount_PasswordTextBox.sendKeys(password);
			
			b2cPage.RegistrateAccount_ConfirmPasswordTextBox.clear();
			b2cPage.RegistrateAccount_ConfirmPasswordTextBox.sendKeys(password);
			
			b2cPage.RegistrateAccount_AcceptTermsCheckBox.click();
			
			b2cPage.RegistrateAccount_CreateAccountButton.click();
			
			boolean c = Common.isElementExist(driver1, By.xpath("//button[contains(@class,'submitButton')]"));
			
			boolean d = b && c;
			
			String summary = "User must check the terms and conditions agreement box to continue";
			String testpoint = "User must check the terms and conditions agreement box to continue";
			
			ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23265", b, "NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "User can continue without checking the create account terms", summary, testpoint, ErrorsCount);
			
			if(terms == ErrorsCount){
				Common.AddTestCasePassContent("NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
			ErrorsCount++;
			String summary = "User must check the terms and conditions agreement box to continue";
			String testpoint = "User must check the terms and conditions agreement box to continue";
			
			Common.detailFailReason(e, "NA-23265", "NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);					
			
		}
		
		//Forget password
		//Basic format of page should be similar to those before
		try{
			int forget_password = ErrorsCount;
			driver1.get(testData.B2C.getloginPageUrl());
			driver1.findElement(By.xpath("//a[contains(@href,'/pw/request')]")).click();
			
			
			List<WebElement> navigationList = driver1.findElements(By.xpath("//ul[@class='menu prd_Menu']/li/a/span"));
			
		
			String navigation_name = driver1.findElement(By.xpath("//ul[@class='menu prd_Menu']/li[contains(@class,'product')]/a/span")).getText().toString();
			driver1.findElement(By.xpath("//ul[@class='menu prd_Menu']/li[contains(@class,'product')]/a/span")).click();
			System.out.println("navigation_name is :" + navigation_name);
			List<WebElement> subList = driver1.findElements(By.xpath("//a[@class='products_submenu']"));
			for(int y = 1; y<=subList.size(); y++){
				
				boolean label_displayed = driver1.findElement(By.xpath("(//a[@class='products_submenu'])["+y+"]/div")).isDisplayed();
				System.out.println("label_displayed:"+ label_displayed);
				String sub_category = driver1.findElement(By.xpath("(//a[@class='products_submenu'])["+y+"]/div")).getText().toString();
				boolean picExists = !driver1.findElement(By.xpath("(//a[@class='products_submenu'])["+y+"]")).getAttribute("style").isEmpty();
				System.out.println("picExists:"+ picExists);
				
				System.out.println("judge:"+ (label_displayed && picExists));
				
				String summary = "Forget password";
				String testpoint = "Basic format of page should be similar to those before";
				ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23265", label_displayed && picExists, "NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "Basic format of page  is not similar to those before", summary, testpoint, ErrorsCount);
		
			}

			if(ErrorsCount == forget_password){
				String summary = "Forget password";
				String testpoint = "Basic format of page should be similar to those before";
				Common.AddTestCasePassContent("NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			String summary = "Forget password";
			String testpoint = "Basic format of page should be similar to those before";
			
			Common.detailFailReason(e, "NA-23265", "NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);						
		}
		
		
		// User enters email for their account
		
		try{
			int user_email = ErrorsCount;
			
			driver1.get(testData.B2C.getloginPageUrl());
			driver1.findElement(By.xpath("//a[contains(@href,'/pw/request')]")).click();
			
			
			String Email = testData.B2C.getLoginID();
			
			driver1.findElement(By.xpath("//*[@id='forgottenPwd.email']")).clear();
			
			driver1.findElement(By.xpath("//*[@id='forgottenPwd.email']")).sendKeys(Email);
			
			boolean b = driver1.findElement(By.xpath("//*[@id='forgottenPwd.email']")).isEnabled();
			
			String summary = "Forget password";
			String testpoint = "User enters email for their account";
			
			ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23265", b, "NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "user can not enter emails for their account", summary, testpoint, ErrorsCount);
			
			if(user_email == ErrorsCount){
				Common.AddTestCasePassContent("NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}	
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			
			String summary = "Forget password";
			String testpoint = "User enters email for their account";
			
			Common.detailFailReason(e, "NA-23265", "NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);						
		}
		
		//If invalid email, proper message is displayed in red
	
		try{
			int invlid_emails = ErrorsCount;
			
			driver1.get(testData.B2C.getloginPageUrl());
			driver1.findElement(By.xpath("//a[contains(@href,'/pw/request')]")).click();
			
			String timestamp = Common.getDateTimeString();
			String Email = "test"+timestamp+"@lenovo.com";
			
			driver1.findElement(By.xpath("//*[@id='forgottenPwd.email']")).clear();
			driver1.findElement(By.xpath("//*[@id='forgottenPwd.email']")).sendKeys(Email);
			
			driver1.findElement(By.xpath(".//*[@id='forgottenPwdForm']//button[@type='submit']")).click();
			
			String summary = "Forget password";
			String testpoint = "If invalid email, proper message is displayed in red";
			
			boolean b = driver1.findElement(By.xpath("//*[@id='email.errors']")).isDisplayed();
			
			ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23265", b, "NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "if invalid email, proper message can not show in red", summary, testpoint, ErrorsCount);
			
			if(invlid_emails == ErrorsCount){
				Common.AddTestCasePassContent("NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}	

			
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			String summary = "Forget password";
			String testpoint = "If invalid email, proper message is displayed in red";
			
			Common.detailFailReason(e, "NA-23265", "NA-23265" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);						
	
		}
		
		/*
		 * - Stay Connected should have a text box where the user can input their email. 
		   -Clicking 'Sign Up' should open a popup which prompts the user to input more information.
		 * */
		
		// user can input emails
		try{
			int input_emails = ErrorsCount;
			
			driver1.get(testData.B2C.getloginPageUrl());
			driver1.findElement(By.xpath("//a[contains(@href,'/pw/request')]")).click();
			
			boolean b = driver1.findElement(By.xpath("//form[@class='newsletterInput']/input[@name='email']")).isEnabled();
			
			String summary = "Stay Connected should have a text box where the user can input their email.";
			String testpoint = "user can input emails";
			
			ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23306", b, "NA-23306" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "if invalid email, proper message can not show in red", summary, testpoint, ErrorsCount);
			
			if(input_emails == ErrorsCount){
				Common.AddTestCasePassContent("NA-23306" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}	
			
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;

			String summary = "Stay Connected should have a text box where the user can input their email.";
			String testpoint = "user can input emails";
			
			Common.detailFailReason(e, "NA-23306", "NA-23306" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);						
		}
		
		//Entered email should be displayed in the next page
		
		try{
			int email_displayed = ErrorsCount;
			
			driver1.get(testData.B2C.getloginPageUrl());
			driver1.findElement(By.xpath("//a[contains(@href,'/pw/request')]")).click();
			
			String email = "testau@yopmail.com";
			driver1.findElement(By.xpath("//form[@class='newsletterInput']/input[@name='email']")).sendKeys(email);
			
			driver1.findElement(By.xpath("//*[@id='footerSignUp']")).click();
			
			String value = driver1.findElement(By.xpath("//*[@id='Email']")).getAttribute("value").toString();
			
			boolean b = email.equals(value);
			
			
			String summary = "Stay Connected should have a text box where the user can input their email.";
			String testpoint = "Entered email should be displayed in the next page";
			
			ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23306", b, "NA-23306" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "enter email can not display on the next page", summary, testpoint, ErrorsCount);
			
			if(email_displayed == ErrorsCount){
				Common.AddTestCasePassContent("NA-23306" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}	
			
			
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			String summary = "Stay Connected should have a text box where the user can input their email.";
			String testpoint = "Entered email should be displayed in the next page";
			
			Common.detailFailReason(e, "NA-23306", "NA-23306" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);						
			
		}
		
		//Clicking 'Sign Up' should direct to a new page which prompts the user to input more information
		
		//User enters their first and last name
		
		try{
			int lastName = ErrorsCount;
			
			driver1.get(testData.B2C.getloginPageUrl());
			driver1.findElement(By.xpath("//a[contains(@href,'/pw/request')]")).click();
			
			String email = "testau@yopmail.com";
			driver1.findElement(By.xpath("//form[@class='newsletterInput']/input[@name='email']")).sendKeys(email);
			
			driver1.findElement(By.xpath("//*[@id='footerSignUp']")).click();
			
			boolean  b = driver1.findElement(By.xpath("//*[@id='Name_First']")).isEnabled();
			
			String summary = "Clicking Sign Up should direct to a new page which prompts the user to input more information";
			String testpoint = "User enters their first and last name";
			
			ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23306", b, "NA-23306" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "user can not enter their first name and last name", summary, testpoint, ErrorsCount);
			
			if(lastName == ErrorsCount){
				Common.AddTestCasePassContent("NA-23306" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			String summary = "Clicking Sign Up should direct to a new page which prompts the user to input more information";
			String testpoint = "User enters their first and last name";
			
			Common.detailFailReason(e, "NA-23306", "NA-23306" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);						

		}
		
		//Email address entered should be checked if it is valid
		try{
			int valid_Email = ErrorsCount;
			
			driver1.get(testData.B2C.getloginPageUrl());
			driver1.findElement(By.xpath("//a[contains(@href,'/pw/request')]")).click();
			
			String email = "testau@yopmail.com";
			driver1.findElement(By.xpath("//form[@class='newsletterInput']/input[@name='email']")).sendKeys(email);
			
			driver1.findElement(By.xpath("//*[@id='footerSignUp']")).click();
			
			driver1.findElement(By.xpath("//*[@id='Name_First']")).sendKeys("Jim Green");
			
			String Email = testData.B2C.getLoginID();
			driver1.findElement(By.xpath("//*[@id='Email']")).clear();
			driver1.findElement(By.xpath("//*[@id='Email']")).sendKeys(Email);
			driver1.findElement(By.xpath("//input[@type='submit']")).click();
			
			MailPage ec = new MailPage(driver1);

			String emailUrl = ec.homePageUrl;
			
			EMailCommon.createEmail(driver1, ec, Email);
			// can not receive the email and can not write the scripts 
			
			
			
			
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			
			
			
		}
		
		
		/*
		 * TrustE Privacy thumbnail image should link to the Truste page. 
		 * 
		 * */
		//Verify that the image takes the user to the TrustE page
		
		try{
			int thumbnail = ErrorsCount;
			driver1.get(testData.B2C.getHomePageUrl());
			
			((JavascriptExecutor) driver1).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);
			
			String mainHandle = driver1.getWindowHandle().toString();
			
			driver1.findElement(By.xpath("//div[@class='footer-trusteValidation']/a")).click();
			
			Set<String> set = driver1.getWindowHandles();
			
			for(String str : set){
				if(str.equals(mainHandle)){
					continue;
				}
				driver1.switchTo().window(str);
			}
			
			String another_url = driver1.getCurrentUrl().toString();
			String truste = "privacy.truste.com";
			driver1.close();
			driver1.switchTo().defaultContent();
		
			boolean b = another_url.contains(truste);
			
			String summary = "TrustE Privacy thumbnail image should link to the Truste page.";
			String testpoint = "Verify that the image takes the user to the TrustE page";
			
			ErrorsCount = ErrorsCount + Common.AssertTrue("NA-23309", b, "NA-23309" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "the image can not take the user to the TrustE page", summary, testpoint, ErrorsCount);
			
			if(thumbnail == ErrorsCount){
				Common.AddTestCasePassContent("NA-23309" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo + "#" + "PASS", summary, testpoint);
			}

		}catch(Exception e){
			e.printStackTrace();
			ErrorsCount++;
			
			String summary = "TrustE Privacy thumbnail image should link to the Truste page.";
			String testpoint = "Verify that the image takes the user to the TrustE page";
			Common.detailFailReason(e, "NA-23309", "NA-23309" + "#" + Country + "#" + "Home Page" + "#" + Common.getDate() + "#" + machineInfo, summary, testpoint, ErrorsCount);	
	
		}
		

		
	}
	
	@AfterClass
	public void tearDown(){
		
		driver1.close();
		
	}
	

}
