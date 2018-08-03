package TestScript.B2C;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;



public class NA25336Test extends SuperTestClass {
	
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public MailPage mailPage;
	
	public String homePageUrl;
	public String loginUrl;
	public String cartUrl;
	
	public String hmcLoginUrl;
	public String hmcHomePageUrl;
	public String Url;

	public NA25336Test(String store,String Url) {
		this.Store = store;
		this.Url = Url;
		this.testName = "NA-25336";
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "email", "p2", "b2c"})
	public void NA25336(ITestContext ctx) {
		try {
			this.prepareTest();
			
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			mailPage = new MailPage(driver);
			
			homePageUrl = testData.B2C.getTeleSalesUrl();
			loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			
			hmcLoginUrl = testData.HMC.getHomePageUrl();
			hmcHomePageUrl = testData.HMC.getHomePageUrl();
			
			//1, Go to HMC -> B2C Commerce -> B2C Unit -> Search USWEB for example -> go to "Site Atttributes" -> 
			//edit "Request Amount" and "WebForm toEmails"
			
			Dailylog.logInfoDB(1, "Go to HMC -> B2C Commerce -> B2C Unit -> Search USWEB for example -> go to Site Atttributes -> edit Request Amount and WebForm toEmails", Store, testName);
			
			driver.get(hmcLoginUrl);
			HMCCommon.Login(hmcPage, testData);
			
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			
			hmcPage.B2CUnit_SiteAttributeTab.click();
			hmcPage.siteAttribute_RequestAmount.clear();
			hmcPage.siteAttribute_RequestAmount.sendKeys("1000");
			
			hmcPage.siteAttribute_WebformToEmails.clear();
			hmcPage.siteAttribute_WebformToEmails.sendKeys(testData.B2C.getLoginID());
			
			driver.findElement(By.xpath("//div[contains(@id,'organizer.editor.save.title')]")).click();
			
			hmcPage.Home_closeSession.click();
			
			//2, Go to Lenovo Financial Service Webform.
			Dailylog.logInfoDB(2, "Go to Lenovo Financial Service Webform.", Store, testName);
			
			driver.get(Url);
			
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//span[@class='ast'][contains(.,'*')]"), 5), "* does not displays");
			
			//3, Click Submit with empty webform, check if the field validator works as expected or not 
			Dailylog.logInfoDB(3, "Click Submit with empty webform, check if the field validator works as expected or not ", Store, testName);
			
			driver.findElement(By.xpath("//a[contains(.,'Submit')]")).click();
			
			Assert.assertTrue(driver.findElement(By.xpath("(//p[@class='emptyError'])[1]")).isDisplayed(),"After click submit button , the empty filed does not show error message");
			
			
			//4, Edit "Email Address" with wrong format 
			Dailylog.logInfoDB(4, "Edit Email Address with wrong format ", Store, testName);
			
			driver.findElement(By.xpath("//input[@id='emailAddress']")).clear();
			driver.findElement(By.xpath("//input[@id='emailAddress']")).sendKeys("testus");
			
			inputBusinessInfo();
			
			Thread.sleep(4000);
			
			Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@class,'Email')]//p[@class='formatError']")).isDisplayed(), "After input the wrong Email format address , the error message does not show");
			
			//5,Edit "Requested Amount" with amount lower than the one setup in the first step 
			Dailylog.logInfoDB(5, "Edit Requested Amount with amount lower than the one setup in the first step ", Store, testName);
			
			
			driver.findElement(By.xpath(".//*[@id='emailAddress']")).clear();
			driver.findElement(By.xpath(".//*[@id='emailAddress']")).sendKeys("testus@yopmail.com");
			
			driver.findElement(By.xpath(".//*[@id='emailConfirm']")).clear();
			driver.findElement(By.xpath(".//*[@id='emailConfirm']")).sendKeys("testus@yopmail.com");
			
			driver.findElement(By.xpath(".//*[@id='phoneNumberContact']")).clear();
			driver.findElement(By.xpath(".//*[@id='phoneNumberContact']")).sendKeys("1234567890");
			
			driver.findElement(By.xpath(".//*[@id='requestAmount']")).clear();
			driver.findElement(By.xpath(".//*[@id='requestAmount']")).sendKeys("900");
			
			driver.findElement(By.xpath("//a[contains(.,'Submit')]")).click();
			
			Thread.sleep(6000);
			
			System.out.println("isDisplayed :" + driver.findElement(By.xpath("//p[@class='requestAmountThresholdError']")).isDisplayed());
			
			Assert.assertTrue(driver.findElement(By.xpath("//p[@class='requestAmountThresholdError']")).isDisplayed(), "input the requested amount lower than the value set in the HMC , After click submit button , the error message does not displays");
			
			//6, Enter into all the info to Webform, click submit
			Dailylog.logInfoDB(6, "Enter into all the info to Webform, click submit", Store, testName);
			
			driver.findElement(By.xpath(".//*[@id='requestAmount']")).clear();
			driver.findElement(By.xpath(".//*[@id='requestAmount']")).sendKeys("1500");
			
			driver.findElement(By.xpath("//a[contains(.,'Submit')]")).click();
			
			Thread.sleep(10000);
			
			Assert.assertTrue(driver.getCurrentUrl().contains("ThankYouPage") && driver.findElement(By.xpath("//div[@id='main-thankyou']")).getText().contains("Thank you"), "After input all the fields with right message , after click submit button, the thank you page does not appears ");
			
			//7, Check the testing email address setup in the first step in HMC and check the email
			Dailylog.logInfoDB(7, "Check the testing email address setup in the first step in HMC and check the email", Store, testName);
			
			EMailCommon.goToMailHomepage(driver);
			
			EMailCommon.createEmail(driver, mailPage, "testus");
			
			Assert.assertTrue(driver.findElement(By.xpath("//tbody[@id='email_list']/tr/td[3]")).getText().contains("Lenovo Financial Services"), "The email is not received!!!");
			
			EMailCommon.checkEmailContentinAllEmail(driver, mailPage, "Lenovo Financial Services", "//h3[@class='email_subject']");
			
			List<WebElement> list = driver.findElements(By.xpath("//div[@class='email_body']/div[not(contains(.,'Message'))]"));
			
			ArrayList<String> aList = new ArrayList<String>();
			
			for(int x = 1; x <= list.size(); x++){
				String str = driver.findElement(By.xpath("(//div[@class='email_body']/div[not(contains(.,'Message'))])["+x+"]")).getText().toString().trim();
				aList.add(str);
				System.out.println(x +" is :" + str);
			}
			
			for(String str : aList){
				if(str.split(":")[0].equals("businessName")){
					Assert.assertTrue(str.split(":")[1].equals("test"));
				}else if (str.split(":")[0].equals("state")) {
					Assert.assertTrue(str.split(":")[1].equals("US-IL"));
				}else if (str.split(":")[0].equals("city")) {
					Assert.assertTrue(str.split(":")[1].equals(testData.B2C.getDefaultAddressCity()));
				}else if (str.split(":")[0].equals("streetAddress")) {
					Assert.assertTrue(str.split(":")[1].equals("test"));
				}else if (str.split(":")[0].equals("zipCode")) {
					Assert.assertTrue(str.split(":")[1].equals(testData.B2C.getDefaultAddressPostCode()));
				}else if (str.split(":")[0].equals("phoneNumber")) {
					Assert.assertTrue(str.split(":")[1].equals(testData.B2C.getDefaultAddressPhone()));
				}else if (str.split(":")[0].equals("requestAmount")) {
					Assert.assertTrue(str.split(":")[1].equals("1500.0"));
				}else if (str.split(":")[0].equals("firstName")) {
					Assert.assertTrue(str.split(":")[1].equals("test"));
				}else if (str.split(":")[0].equals("lastName")) {
					Assert.assertTrue(str.split(":")[1].equals("test"));
				}else if (str.split(":")[0].equals("emailAddress")) {
					Assert.assertTrue(str.split(":")[1].equals("testus@yopmail.com"));
				}else if (str.split(":")[0].equals("emailConfirm")) {
					Assert.assertTrue(str.split(":")[1].equals("testus@yopmail.com"));
				}else if (str.split(":")[0].equals("phoneNumberContact")) {
					Assert.assertTrue(str.split(":")[1].equals("1234567890"));
				}
			}
	
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public void inputBusinessInfo(){
		
		driver.findElement(By.xpath(".//*[@id='businessName']")).clear();
		driver.findElement(By.xpath(".//*[@id='businessName']")).sendKeys("test");
		
		driver.findElement(By.xpath(".//*[@id='streetAddress']")).clear();
		driver.findElement(By.xpath(".//*[@id='streetAddress']")).sendKeys("test");
		
		driver.findElement(By.xpath(".//*[@id='city']")).clear();
		driver.findElement(By.xpath(".//*[@id='city']")).sendKeys(testData.B2C.getDefaultAddressCity());
		
		Select select_state = new Select(driver.findElement(By.xpath("//*[@id='State']")));
		select_state.selectByVisibleText(testData.B2C.getDefaultAddressState());
		
		driver.findElement(By.xpath(".//*[@id='zipCode']")).clear();
		driver.findElement(By.xpath(".//*[@id='zipCode']")).sendKeys(testData.B2C.getDefaultAddressPostCode());
		
		driver.findElement(By.xpath("//*[@id='phoneNumber']")).clear();
		driver.findElement(By.xpath("//*[@id='phoneNumber']")).sendKeys(testData.B2C.getDefaultAddressPhone());
		
		driver.findElement(By.xpath(".//*[@id='requestAmount']")).clear();
		driver.findElement(By.xpath(".//*[@id='requestAmount']")).sendKeys("1500");
		
		driver.findElement(By.xpath(".//*[@id='firstName']")).clear();
		driver.findElement(By.xpath(".//*[@id='firstName']")).sendKeys("test");
		
		driver.findElement(By.xpath(".//*[@id='lastName']")).clear();
		driver.findElement(By.xpath(".//*[@id='lastName']")).sendKeys("test");
		
		driver.findElement(By.xpath(".//*[@id='emailAddress']")).clear();
		driver.findElement(By.xpath(".//*[@id='emailAddress']")).sendKeys("testus");
		
		driver.findElement(By.xpath("//a[contains(.,'Submit')]")).click();

	}
	
	
	
}
	
	