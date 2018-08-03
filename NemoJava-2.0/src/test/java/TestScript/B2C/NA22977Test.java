package TestScript.B2C;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class NA22977Test extends SuperTestClass {
	public HMCPage hmcPage;
	public B2CPage b2cPage;
	public MailPage mailPage;
	
	public String homePageUrl;
	public String loginUrl;
	public String cartUrl;
	
	public String hmcLoginUrl;
	public String hmcHomePageUrl;
	

	public NA22977Test(String store){
		this.Store = store;
		this.testName = "NA-22977";
	}
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2c"})
	public void NA22977(ITestContext ctx){
		try{
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			mailPage = new MailPage(driver);
			
			homePageUrl = testData.B2C.getTeleSalesUrl();
			loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			
			hmcLoginUrl = testData.HMC.getHomePageUrl();
			hmcHomePageUrl = testData.HMC.getHomePageUrl();
			
	     //1.  Login website with credential of a Tele Rep
			driver.get(homePageUrl);
		    B2CCommon.handleTeleGateKeeper(b2cPage, testData);
		    driver.get(loginUrl);
		    B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
		    Common.javascriptClick(driver, b2cPage.MyAccount_myAccount);
		    
		    
		 //2. Click *Start Assisted Service Session* like to start ASM
		    b2cPage.myAccountPage_startAssistedServiceSession.click();
			Thread.sleep(5000);
			B2CCommon.closeHomePagePopUP(driver);
			
		//3. Click My Account navigation node on the header.
			
		    Common.javascriptClick(driver, b2cPage.MyAccount_myAccount);
			
			//4. Click *Create Account* Btn
			b2cPage.Login_CreateAnAccountButton.click();
			  //validate whethere there is password inputbox (Account Creation page shown * No password related filed asked on this page.)
			if(CheckWhetherPasswordInbox(driver)){
				Assert.fail("Account Creation page shown.* there password related filed asked on this page.");
			}
			//5. Fill out customer info like ID(email), NAME... and then submit the form.
		    String EmailAddress = EMailCommon.getRandomEmailAddress();  
		    System.out.println("EmailAddress:"+EmailAddress);
		    //email
		    driver.findElement(By.xpath(".//*[@id='register.email']")).clear();
		    driver.findElement(By.xpath(".//*[@id='register.email']")).sendKeys(EmailAddress);
		    //re-enter eamil
		    driver.findElement(By.xpath(".//*[@id='register.chkEmail']")).clear();
		    driver.findElement(By.xpath(".//*[@id='register.chkEmail']")).sendKeys(EmailAddress);
		    //first name
		    driver.findElement(By.xpath(".//*[@id='register.firstName']")).clear();
		    driver.findElement(By.xpath(".//*[@id='register.firstName']")).sendKeys("firstname");
		    //last name
		    driver.findElement(By.xpath(".//*[@id='register.lastName']")).clear();
		    driver.findElement(By.xpath(".//*[@id='register.lastName']")).sendKeys("lastname");
            if(Common.checkElementDisplays(driver, By.xpath(".//*[@id='register.acceptterms']"), 5)){
            	driver.findElement(By.xpath(".//*[@id='register.acceptterms']")).click();
            }
            if(Common.checkElementDisplays(driver, By.xpath(".//*[@id='nemoRegisterForm']/button"), 5)){
            	driver.findElement(By.xpath(".//*[@id='nemoRegisterForm']/button")).click();
            }
            if(Common.isElementExist(driver, By.xpath(".//*[@id='customerFilter']"), 5)){
            	driver.findElement(By.xpath(".//*[@id='customerFilter']")).clear();
            	driver.findElement(By.xpath(".//*[@id='customerFilter']")).sendKeys(EmailAddress);
            	Common.sleep(5000);
            	if(Common.isElementExist(driver, By.xpath(".//div[@id='asmAutoCompleteCustomer']/ul[1]/li[1]/a/span[2]"), 10)){
            		//System.out.println("search result:"+driver.findElement(By.xpath(".//div[@id='asmAutoCompleteCustomer']/ul[1]/li[1]/a/span[2]")).getText());
            		if(!(driver.findElement(By.xpath(".//div[@id='asmAutoCompleteCustomer']/ul[1]/li[1]/a/span[2]")).getText().equals(EmailAddress))){
            			Assert.fail("Input account to customer ID on ASM, It didn't show.");	
            		}
            	}else
            	{
            		Assert.fail("Input account to customer ID on ASM, It didn't show.");
            	}
            }
            b2cPage.ASM_signout.click();
            Dailylog.logInfoDB(1, "Search costomer on HMC", Store, testName);
            driver.get(hmcHomePageUrl);
            Common.sleep(5000);
            HMCCommon.Login(hmcPage, testData);
            hmcPage.home_userTab.click();
            hmcPage.userTab_customerTab.click();
            if(Common.checkElementExists(driver, hmcPage.customer_customerIDTextBox, 5)){
            	hmcPage.customer_customerIDTextBox.clear();
            	hmcPage.customer_customerIDTextBox.sendKeys(EmailAddress);
            	hmcPage.customer_customerSearchButtonn.click();
            	if(Common.isElementExist(driver, By.xpath(".//*[@id='Content/StringDisplay["+EmailAddress+"]_span']"), 5)){
            		String result = driver.findElement(By.xpath(".//*[@id='Content/StringDisplay["+EmailAddress+"]_span']")).getText();
            		//System.out.println("result:"+result);
            		if(!result.equals(EmailAddress)){
            		Assert.fail(" no resust;LID and Hybris not be able to find this customer master data.");	
            		}
            	}else{
            		Assert.fail(" no resust;LID and Hybris not be able to find this customer master data！！！");
            	}
            }
           //6. 【Customer use this ID to login website by himself】Open B2C login  url
            
            driver.get(testData.B2C.getHomePageUrl());
            B2CCommon.handleGateKeeper(b2cPage, testData);
		    driver.get(testData.B2C.getloginPageUrl());
            
           //7-1. Click *Forget Password* to walk through forget password Input Email address->Click submit->Click Reset password link in Emal-> update password
		    b2cPage.LoginPage_forgotPassword.click();
			Assert.assertTrue(driver.getCurrentUrl().contains("pw/request"),"User should be on forgot password page.");
			Dailylog.logInfoDB(2, "Forgot Password Link is clicked.", Store, testName);
			
			b2cPage.ForgotPassword_emailIDTxtBox.clear();
			b2cPage.ForgotPassword_emailIDTxtBox.sendKeys(EmailAddress );
			b2cPage.ForgotPassword_submitButton.click();
			Dailylog.logInfoDB(3, "Submit button is clicked after entering email ID.", Store, testName);
			
			//7-2 Check whether user received the email and click Reset Password Link  ===========//
			EMailCommon.createEmail(driver, mailPage, EmailAddress.replace("@sharklasers.com", ""));			
			Common.sleep(2000);
			String  Currenthandle= driver.getWindowHandle();
			Check_Content(0);			
			for (String handle : driver.getWindowHandles()) {
				driver.switchTo().window(handle);
			}
			Common.sleep(2000);
			String resetPageURL = driver.getCurrentUrl();
			Assert.assertTrue(resetPageURL.contains("ResetPass"),"Password reset is successful.");
			//7-3 Reset the password and login with new password ===========//
			b2cPage.ResetPassword_newPassword.clear();
			b2cPage.ResetPassword_newPassword.sendKeys(testData.B2C.getLoginPassword());
			b2cPage.ResetPassword_confirmNewPassword.clear();
			b2cPage.ResetPassword_confirmNewPassword.sendKeys(testData.B2C.getLoginPassword());
			b2cPage.ForgotPassword_submitButton.click();
			Dailylog.logInfoDB(6, "Submit button is clicked with new password.", Store, testName);
			Common.sleep(4000);
			String passwordResetSuccMsg = b2cPage.ResetPassword_PasswordResetSuccMsg.getText();
			Dailylog.logInfoDB(6, passwordResetSuccMsg, Store, testName);
			Assert.assertTrue(b2cPage.ResetPassword_PasswordResetSuccMsg.isDisplayed(),"Password reset success msg should be displayed");
			b2cPage.ResetPassword_ContinueButton.click();
			Common.sleep(3000);
			B2CCommon.login(b2cPage, EmailAddress, testData.B2C.getLoginPassword());	
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Common.sleep(3000);
			String urlAfterLoginWithNewPass = driver.getCurrentUrl();
			Assert.assertTrue(urlAfterLoginWithNewPass.contains("my-account"));
			driver.manage().deleteAllCookies();
			driver.close();
			driver.switchTo().window(Currenthandle);

            
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}	
	}
	
	
	private void Check_Content(int i){
		Dailylog.logInfoDB(5,"Checking mail content...time="+i,Store,testName);
		if (Common.isElementExist(driver,By.xpath("(//td[contains(.,'Forgot your password')])")) == true || Common.isElementExist(driver,By.xpath("(//td[contains(.,'パスワードを忘れた場合')])")) == true) {
			Dailylog.logInfoDB(4, "Forgot Password Email is present.", Store, testName);
			if(Store.equals("JP")){
				driver.findElement(By.xpath("(//td[contains(.,'パスワードを忘れた場合')])")).click();
			}else{
				driver.findElement(By.xpath("(//td[contains(.,'Forgot your password')])")).click();
			}
			Common.sleep(2000);	
			//=========== Step:5 Click Password Reset Link ===========//
			if(Store.equals("JP")){
				driver.findElement(By.xpath("//tr/td/a[contains(.,'新しいパスワードを設定するには、ここをクリックしてください')]")).click();
				
			}else{
				driver.findElement(By.xpath("//tr/td/a[contains(.,'click here')]")).click();
			}
			Dailylog.logInfoDB(5, "Reset Password Link is clicked from the email.", Store, testName);
			Common.sleep(2000);	
			mailPage.Mail_backToInbox.click();			
		} else if(i < 10){
			Dailylog.logInfoDB(5,"Mail is not present...check later",Store,testName);
			Check_Content(++i);
		}
	}
	public boolean CheckWhetherPasswordInbox(WebDriver driver){
		boolean flag = false;
		ArrayList<WebElement> RowsInput	= (ArrayList<WebElement>) driver.findElements(By.xpath("//form/div/div/input"));
//		for(WebElement row : RowsInput){
//			System.out.println("<input> name value:"+ row.getAttribute("name"));
//		}
		for(int i = 0 ;i< RowsInput.size();i++){
			//System.out.println("<input> name value:"+ RowsInput.get(i).getAttribute("name"));
			if(RowsInput.get(i).getAttribute("name").contains("password")||RowsInput.get(i).getAttribute("name").contains("pw")){
				flag = true;
				break;
			}
		}
		return flag;
	}
	
}