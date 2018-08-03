package TestScript.B2C;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class NA17528Test extends SuperTestClass{
	public B2CPage b2cPage;
	public String email = Common.getDateTimeString() + "1@sharklasers.com";
	public String differentStore;


	public NA17528Test(String store,String differentStore) {
		this.Store = store;
		this.testName = "NA-17528";
		this.differentStore = differentStore;
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"shopgroup", "email", "p2", "b2c"})
	public void NA17528(ITestContext ctx) throws Exception {
		try{
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			String b2cURL = testData.B2C.getHomePageUrl();
			String FirstName = ".//*[@id='Name_First']";
			String emailIDTxtBox = ".//*[@id='Email']";
			String emailIcon = "//ul[contains(@class,'common_Menu') or contains(@class,'general_Menu')]/li[contains(@class,'email_menu')]/a[contains(.,'') or contains(@href,'email')]";
			String countrySelection = ".//div/select[@id='Country_ISO2']/option[@value=";
			String InvalidEmailID = "abcdef";
			String Email_FirstName = "John";
			String Email_LastName = "Snow";

			//=========== Step:1 Accessing B2C URL ===========//
			driver.get(b2cURL);
			Common.sleep(5000);
			B2CCommon.handleGateKeeper(b2cPage, testData);

			//=========== Step:2 Clicking Email Icon ===========//
			if(Common.isElementExist(driver,By.xpath(emailIcon))){
				driver.findElement(By.xpath(emailIcon)).click();
				Dailylog.logInfoDB(2, "Email Icon is clicked.", Store, testName);
				String urlEmailpage = driver.getCurrentUrl();
				driver.switchTo().frame(0);
				if(Store == "HK" || Store == "NZ"){
					//=========== Step:3 Verifying with Invalid Email ===========//
					b2cPage.SubscriptionPage_inputEmail.sendKeys(InvalidEmailID);
					b2cPage.SubscriptionPage_goButton.click();
					Dailylog.logInfoDB(3, "Go button is clicked after entering invalid Email ID", Store, testName);
					driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					String urlAfterInvalidEmailInput = driver.getCurrentUrl();
					//Verifying that URL should not change after entering invalid email 
					assertEquals(urlAfterInvalidEmailInput, urlEmailpage);

					//=========== Step:4 Verifying with Valid Email ===========//
					b2cPage.SubscriptionPage_inputEmail.clear();
					b2cPage.SubscriptionPage_inputEmail.sendKeys(email);
					b2cPage.SubscriptionPage_goButton.click();
					Dailylog.logInfoDB(4, "Go Button is clicked after entering valid Email ID", Store, testName);
					driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					String urlAfterValidEmailInput = driver.getCurrentUrl();
					assert urlAfterValidEmailInput.contains(urlEmailpage);

					//=========== Step:5 Select Country and Check Subscription Message ===========//
					b2cPage.SubscriptionPage_countrySelectDropdown.click();
					driver.findElement(By.xpath(countrySelection + "'" + differentStore + "']")).click();
					b2cPage.SubscriptionPage_inputFName.sendKeys(Email_FirstName);
					b2cPage.SubscriptionPage_inputLName.sendKeys(Email_LastName);
					b2cPage.SubscriptionPage_subscribeButton.click();
					Dailylog.logInfoDB(5, "Subscribe button is clicked", Store, testName);
					driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
					String urlAfterSubscription = driver.getCurrentUrl();
					assert urlAfterSubscription.contains(urlEmailpage);
					String subscribeMsg = b2cPage.SubscriptionPage_subscribeSuccessMsg.getText();
					assert subscribeMsg.contains(("Thanks for creating your Lenovo communications profile"));

					//=========== Step:6 Updating profile ===========//
					/*					b2cPage.SubscriptionPage_inputFName.sendKeys(Email_FirstName + Common.getDateTimeString() );
					b2cPage.SubscriptionPage_inputLName.sendKeys(Email_LastName + Common.getDateTimeString());
					b2cPage.SubscriptionPage_updateProfileButton.click();
					driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
					String updateSuccessMsg = b2cPage.SubscriptionPage_updateSuccessMsg.getText();
					assert updateSuccessMsg.contains("Thanks! Your profile is updated. Follow Us!");
					String  Currenthandle= driver.getWindowHandle();

					//=========== Step:7 Click Start Shopping ===========//
				b2cPage.SubscriptionPage_startShopping.click();
					for (String handle : driver.getWindowHandles()) {
						driver.switchTo().window(handle);
					}
					String newTabURL = driver.getCurrentUrl();
					assert newTabURL.contains(differentStore);
					driver.close();
					driver.switchTo().window(Currenthandle);*/

				}
				//Code to handle new UI
				else{

					if(Common.isElementExist(driver,By.xpath(FirstName))){
						driver.findElement(By.xpath(FirstName)).sendKeys(Email_FirstName);
						driver.findElement(By.xpath(FirstName)).sendKeys(" ");
						driver.findElement(By.xpath(FirstName)).sendKeys(Common.getDateTimeString());
					}
					if(Common.isElementExist(driver,By.xpath(emailIDTxtBox))){
						driver.findElement(By.xpath(emailIDTxtBox)).sendKeys(email);
					}
					try{
						b2cPage.SubscriptionPage_goButton.click();
						Dailylog.logInfoDB(5, "Subscribe button is clicked(New UI)", Store, testName);
						Common.sleep(12000);
						String subscribeMsg = driver.findElement(By.xpath(".//*[@id='div-thankyou']/div/div[1]/div[2]/p")).getText();
						assert subscribeMsg.length()>0;
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			else{
				System.out.println("Email Icon is not present...!!!");
			}
		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}
}
