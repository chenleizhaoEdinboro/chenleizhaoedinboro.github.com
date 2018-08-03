package TestScript.B2B;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA21905Test extends SuperTestClass{
	private static B2BPage b2bPage;
	private static HMCPage hmcPage;

	public NA21905Test(String store) {
		this.Store = store;
		this.testName = "NA-21905";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2b"})
	public void NA21905(ITestContext ctx) {
		try{
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);

			//Step 1~2:
			Dailylog.logInfoDB(1, "Go to HMC --> Nemo --> Payment --> Payment Type Customize --> Payment Profile", Store, testName);
			//setDirectDepositStatus(hmcPage.paymentProfileGlobalTab_activeRadioButtonYes, true);
			
			setDirectDepositStatus();

			//Step 3:
			Dailylog.logInfoDB(3, "Go to HMC --> B2B Commerce --> B2B UNIT --> Site Attributes --> Payment Type --> Add Direct Deposit Payment", Store, testName);
			HMCCommon.searchB2BUnit(hmcPage, testData);
			hmcPage.B2BUnit_siteAttribute.click();
			if(Common.isElementExist(driver, By.xpath(".//td[contains(@id,'[B2BUnit.paymentTypeAndPayerId]]_BANKDEPOSIT')]"))){
				hmcPage.b2bUnitSiteAttribute_bankDepositPaymentType.click();
			}else {
				Common.rightClick(driver, hmcPage.paymentProfileGlobalTab_paymentTypeAndPayerIDTop);
				Thread.sleep(1000);
				hmcPage.b2bUnitSiteAttribute_addPaymentTypePayerID.click();
				Common.switchToWindow(driver, 1);
				Thread.sleep(1000);
				hmcPage.checkoutPaymentType_identifierTxtBox.clear();
				hmcPage.checkoutPaymentType_identifierTxtBox.sendKeys("BANKDEPOSIT");
				hmcPage.B2BUnit_SearchButton.click();
				Thread.sleep(5000);
				hmcPage.checkoutPaymentType_searchedResult.click();
				hmcPage.checkoutPaymentType_useButton.click();
				Thread.sleep(1000);
				Common.switchToWindow(driver, 0);
				hmcPage.B2BUnit_Save.click();
			}

			//Step 4:
			Dailylog.logInfoDB(4, "Login B2B website as a B2B buyer, add product to cart then proceed to checkout flow. Complete shipping step, then click Continue button to go to payment page.", Store, testName);
			loginB2B(testData.B2B.getBuyerId());
			fillShippingAndCheckDirectDeposit(true, "Direct deposit method is not displayed for normal user even after turning on the toggle.");
			String orderNumber = placeOrder();
			
			Assert.assertTrue(orderNumber != null);

			//Step 5:
			Dailylog.logInfoDB(5, "Logout buyer user then login as telesales user", Store, testName);
			Thread.sleep(1000);
			loginB2B(testData.B2B.getTelesalesId());

			//Step 6:
			Dailylog.logInfoDB(5, "Find My Account link from the navigation bar,and then click it. then click Start Assisted Service Session link.", Store, testName);
			driver.get(testData.B2B.getHomePageUrl() + "/my-account");
			Thread.sleep(1000);
			b2bPage.homepage_MyAccount.click();
			b2bPage.MyAccountPage_StartAssSerSession.click();
			
			B2BCommon.loginASM(driver, b2bPage, testData);

			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath(".//*[@id='customerFilter']")));
			b2bPage.MyAccountPage_CustomerIDBox.click();
			driver.findElement(By.xpath("//*[@id='customerFilter']")).sendKeys(
					testData.B2B.getBuilderId());
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.cssSelector("[id^='ui-id-']>a")));
			Actions action = new Actions(driver);
			
			b2bPage.MyAccount_CustomerResult.click();

			b2bPage.MyAccountPage_StartSessionButton.click();

			Thread.sleep(1000);
			fillShippingAndCheckDirectDeposit(false, "Direct deposit method is displayed for TELESALES user even after turning on the toggle.");
		
			// step 10, Roll Back :
			//	Follow step 1-2 and set 
			//	Visibility For ASM:Yes
			//	Active : No
			Dailylog.logInfoDB(10, "Roll Back", Store, testName);
			
		}catch (Throwable e){
			handleThrowable(e, ctx);
		}
	}
	
	@Test(priority = 1, enabled = true, alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2b"})
	public void rollback() throws InterruptedException{
		rollBack();
	}
	
	public String placeOrder(){
		b2bPage.paymentPage_PurchaseOrder.click();
		Common.sleep(2000);
		b2bPage.paymentPage_purchaseNum.sendKeys("1234567890");
		b2bPage.paymentPage_purchaseDate.sendKeys(Keys.ENTER);
		
		B2BCommon.fillDefaultB2bBillingAddress(driver, b2bPage, testData);
		
		System.out.println("Go to Order page!");
		if(Common.checkElementExists(driver, b2bPage.placeOrderPage_ResellerID, 60)){
			b2bPage.placeOrderPage_ResellerID.sendKeys("reseller@yopmail.com");
		}		
		Common.javascriptClick(driver, b2bPage.placeOrderPage_Terms);
		Common.javascriptClick(driver, b2bPage.OrderSummary_PlaceOrderButton);
		String orderNum=b2bPage.placeOrderPage_OrderNumber.getText();
		
		return orderNum;
	}

	
	public void rollBack() throws InterruptedException{
		
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		
		hmcPage.Home_Nemo.click();
		hmcPage.nemoPage_payment.click();
		hmcPage.Home_paymentCustomize.click();
		hmcPage.Home_paymentProfile.click();
		Select paymentTypeDD = new Select(hmcPage.paymentProfile_paymentType);
		paymentTypeDD.selectByVisibleText("Direct Deposit");
		hmcPage.paymentProfile_paymentSearch.click();
		Thread.sleep(1000);
		hmcPage.paymentType_directDepositSearchedResult.click();
		
		hmcPage.paymentProfile_global.click();
		
		driver.findElement(By.xpath("//span[contains(@id,'PaymentTypeProfile.active') and contains(@id,'false')]")).click();
		Thread.sleep(5000);
		
		driver.findElement(By.xpath("//input[contains(@id,'PaymentTypeProfile.visibilityForASM') and contains(@id,'true')]")).click();
		Thread.sleep(5000);
		
		
		
		boolean b = driver.findElement(By.xpath("//input[contains(@id,'PaymentTypeProfile.active') and contains(@id,'false')]")).isSelected() && driver.findElement(By.xpath("//input[contains(@id,'PaymentTypeProfile.visibilityForASM') and contains(@id,'true')]")).isSelected();
		
		boolean b1 = driver.findElement(By.xpath("//input[contains(@id,'PaymentTypeProfile.active') and contains(@id,'true')]")).isSelected();
		boolean b2 = driver.findElement(By.xpath("//input[contains(@id,'PaymentTypeProfile.visibilityForASM') and contains(@id,'true')]")).isSelected();
		
		
		System.out.println("b is :" + b);
		System.out.println("b1 is :" + b1);
		System.out.println("b2 is :" + b2);
		
		hmcPage.Catalog_save.click();
	
		
		
		Assert.assertTrue(driver.findElement(By.xpath("//input[contains(@id,'PaymentTypeProfile.active') and contains(@id,'false')]")).isSelected() && driver.findElement(By.xpath("//input[contains(@id,'PaymentTypeProfile.visibilityForASM') and contains(@id,'true')]")).isSelected());

	}
	
	public void loginB2B(String emailID) throws InterruptedException{
		driver.manage().deleteAllCookies();
		driver.get(testData.B2B.getLoginUrl());
		B2BCommon.Login(b2bPage, emailID, testData.B2B.getDefaultPassword());
		Thread.sleep(2000);
	}

	public void quickAdd() throws InterruptedException{
		driver.get(testData.B2B.getHomePageUrl() + "/cart");
		Thread.sleep(2000);
		b2bPage.cartPage_quickOrder.clear();
		b2bPage.cartPage_quickOrder.sendKeys(testData.B2B.getDefaultMTMPN1());
		b2bPage.cartPage_addButton.click();
	}

	public void checkAndRemoveUnit(WebElement presentUnitLocator, String presentUnitLocatorAsString, String errorMessage) throws InterruptedException{
		if(Common.isElementExist(driver, By.xpath(presentUnitLocatorAsString))){
			Common.rightClick(driver, presentUnitLocator);
			Thread.sleep(1000);
			hmcPage.B2CLeasing_remove.click();
			Thread.sleep(1000);
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
			Thread.sleep(1000);
			checkAndRemoveUnit(presentUnitLocator, presentUnitLocatorAsString, errorMessage);
		}else {
			Dailylog.logInfoDB(1, errorMessage, Store, testName);
		}
	}

	//Function to remove B2C units, B2B units and countries, if any under the payment options.
	public void removeUnitAndCountry(WebElement masterTabLocator, WebElement presentUnitLocator, String presentUnitLocatorAsString, String errorMessage) throws InterruptedException{
		masterTabLocator.click();
		Thread.sleep(1000);
		checkAndRemoveUnit(presentUnitLocator, presentUnitLocatorAsString, errorMessage);
	}
	
	public void setDirectDepositStatus() throws InterruptedException{
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Nemo.click();
		hmcPage.nemoPage_payment.click();
		hmcPage.Home_paymentCustomize.click();
		hmcPage.Home_paymentProfile.click();
		Select paymentTypeDD = new Select(hmcPage.paymentProfile_paymentType);
		paymentTypeDD.selectByVisibleText("Direct Deposit");
		hmcPage.paymentProfile_paymentSearch.click();
		Thread.sleep(1000);
		
		if(Common.isElementExist(driver, By.xpath("//img[contains(@id,'Direct Deposit')]"))){
			hmcPage.paymentType_directDepositSearchedResult.click();
			
			hmcPage.paymentProfile_configLevel.click();
			driver.findElement(By.xpath("//select[contains(@id,'configLevel')]/option[contains(text(),'GLOBAL')]")).click();
			hmcPage.paymentProfile_channel.click();
			hmcPage.paymentProfile_channelALL.click();
			hmcPage.paymentProfile_activeTrue.click();
			
			removeUnitAndCountry(hmcPage.paymentProfile_B2CUnits, hmcPage.paymentProfile_availableB2CUnits, ".//table[@title='b2CUnits']//td[contains(@style,'border-right')]//div[contains(@id,'span')]", "No B2C unit available.");
			removeUnitAndCountry(hmcPage.paymentTypeProfile_b2bunits, hmcPage.paymentProfile_availableB2BUnits, ".//table[@title='b2BUnits']//td[contains(@style,'border-right')]//div[contains(@id,'span')]", "No B2B unit available.");
			removeUnitAndCountry(hmcPage.paymentProfile_countries, hmcPage.paymentProfile_availableCountries, ".//table[@title='countries']//td[contains(@style,'border-right')]//div[contains(@id,'span')]", "No country available.");
			Thread.sleep(1000);
			
			hmcPage.paymentProfile_global.click();
			
			hmcPage.paymentProfileGlobalTab_visibilityForASMNo.click();
			hmcPage.paymentProfileGlobalTab_disableMixedOrderYes.click();
			hmcPage.paymentProfileGlobalTab_disablePcgOrderNo.click();
			hmcPage.paymentProfileGlobalTab_disableDcgOrderYes.click();
			hmcPage.Catalog_save.click();
		}else{
			Common.scrollToElement(driver, hmcPage.Home_paymentProfile);

			Actions action = new Actions(driver);
			action.contextClick(hmcPage.Home_paymentProfile);
			action.perform();
			hmcPage.createPaymentProfile.click();
			
			driver.findElement(By.xpath("//input[contains(@id,'name')]")).clear();
			driver.findElement(By.xpath("//input[contains(@id,'name')]")).sendKeys("Direct Deposit");
			
			Select paymentType = new Select(hmcPage.paymentProfile_paymentType);
			paymentTypeDD.selectByVisibleText("Direct Deposit");
			
			hmcPage.paymentProfile_configLevel.click();
			driver.findElement(By.xpath("//select[contains(@id,'configLevel')]/option[contains(text(),'GLOBAL')]")).click();
			hmcPage.paymentProfile_channel.click();
			hmcPage.paymentProfile_channelALL.click();
			hmcPage.paymentProfile_activeTrue.click();
			
			removeUnitAndCountry(hmcPage.paymentProfile_B2CUnits, hmcPage.paymentProfile_availableB2CUnits, ".//table[@title='b2CUnits']//td[contains(@style,'border-right')]//div[contains(@id,'span')]", "No B2C unit available.");
			removeUnitAndCountry(hmcPage.paymentTypeProfile_b2bunits, hmcPage.paymentProfile_availableB2BUnits, ".//table[@title='b2BUnits']//td[contains(@style,'border-right')]//div[contains(@id,'span')]", "No B2B unit available.");
			removeUnitAndCountry(hmcPage.paymentProfile_countries, hmcPage.paymentProfile_availableCountries, ".//table[@title='countries']//td[contains(@style,'border-right')]//div[contains(@id,'span')]", "No country available.");
			Thread.sleep(1000);
			
			hmcPage.paymentProfile_global.click();
			
			hmcPage.paymentProfileGlobalTab_visibilityForASMNo.click();
			hmcPage.paymentProfileGlobalTab_disableMixedOrderYes.click();
			hmcPage.paymentProfileGlobalTab_disablePcgOrderNo.click();
			hmcPage.paymentProfileGlobalTab_disableDcgOrderYes.click();
			
			driver.findElement(By.xpath("//input[contains(@id,'code')]")).sendKeys("Direct Deposit");
			
			driver.findElement(By.xpath("//div[contains(@id,'saveandcreate')]")).click();
			Thread.sleep(3000);
			hmcPage.Catalog_save.click();
		}
		
		
	}

	public void setDirectDepositStatus(WebElement directDepositStatusLocator, boolean isActive) throws InterruptedException{
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Nemo.click();
		hmcPage.nemoPage_payment.click();
		hmcPage.Home_paymentCustomize.click();
		hmcPage.Home_paymentProfile.click();
		Select paymentTypeDD = new Select(hmcPage.paymentProfile_paymentType);
		paymentTypeDD.selectByVisibleText("Direct Deposit");
		hmcPage.paymentProfile_paymentSearch.click();
		Thread.sleep(1000);
		hmcPage.paymentType_directDepositSearchedResult.click();
		if(isActive){
			removeUnitAndCountry(hmcPage.paymentProfile_B2CUnits, hmcPage.paymentProfile_availableB2CUnits, ".//table[@title='b2CUnits']//td[contains(@style,'border-right')]//div[contains(@id,'span')]", "No B2C unit available.");
			removeUnitAndCountry(hmcPage.paymentTypeProfile_b2bunits, hmcPage.paymentProfile_availableB2BUnits, ".//table[@title='b2BUnits']//td[contains(@style,'border-right')]//div[contains(@id,'span')]", "No B2B unit available.");
			removeUnitAndCountry(hmcPage.paymentProfile_countries, hmcPage.paymentProfile_availableCountries, ".//table[@title='countries']//td[contains(@style,'border-right')]//div[contains(@id,'span')]", "No country available.");
			Thread.sleep(1000);

			//Step 2:
			Dailylog.logInfoDB(2, "Search Direct Deposit Payment then Edit this Payment Profile", Store, testName);
			hmcPage.paymentProfile_global.click();
			Select selectConfigurationLevel = new Select(hmcPage.paymentProfileGlobalTab_configurationLevelDD);
			selectConfigurationLevel.selectByVisibleText("GLOBAL");
			Select selectChannel = new Select(hmcPage.paymentProfileGlobalTab_channelDD);
			selectChannel.selectByVisibleText("ALL");
		} else {
			hmcPage.paymentProfile_global.click();
		}
		Thread.sleep(1000);
		directDepositStatusLocator.click();
		hmcPage.Catalog_save.click();
	}

	public void fillShippingAndCheckDirectDeposit(boolean isDirectDepositPresent, String failureMessage) throws InterruptedException{
		quickAdd();
		b2bPage.cartPage_LenovoCheckout.click();
		B2BCommon.fillB2BShippingInfo(driver, b2bPage, Store, testData.B2B.getFirstName(), testData.B2B.getLastName(), testData.B2B.getCompany(), testData.B2B.getAddressLine1(), testData.B2B.getAddressCity(), testData.B2B.getAddressState(), testData.B2B.getPostCode(), testData.B2B.getPhoneNum());
		b2bPage.shippingPage_ContinueToPayment.click();
		Thread.sleep(6000);
		
		if(Common.isElementExist(driver, By.xpath("//input[@id='checkout_validateFrom_ok']")) && driver.findElement(By.xpath("//input[@id='checkout_validateFrom_ok']")).isDisplayed()){
			driver.findElement(By.xpath("//input[@id='checkout_validateFrom_ok']")).click();
		}
		
		Assert.assertEquals(Common.isElementExist(driver, By.xpath(Common.convertWebElementToString(b2bPage.DirectDeposit))), isDirectDepositPresent);
	}
}
