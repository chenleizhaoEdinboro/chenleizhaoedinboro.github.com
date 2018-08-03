package TestScript.B2C;



import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestData.TestData;
import TestScript.SuperTestClass;

public class NA21842Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public String statelocator;
	public String productNumber;
	
	
	public String homePageUrl;
	public String loginUrl;
	public String cartUrl;
	
	public String hmcLoginUrl;
	public String hmcHomePageUrl;

	public NA21842Test(String store,String StateXpath, String productNumber) {
		this.Store = store;
		this.statelocator = StateXpath;
		this.productNumber = productNumber;
		this.testName = "NA-21842";
	}

	@Test(alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2c"})
	public void NA21842(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			
			homePageUrl = testData.B2C.getTeleSalesUrl();
			loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			
			hmcLoginUrl = testData.HMC.getHomePageUrl();
			hmcHomePageUrl = testData.HMC.getHomePageUrl();
			
			
			//1, Login HMC use telesales account
			Dailylog.logInfoDB(1, "Login HMC use telesales account", Store, testName);
			
			driver.get(hmcHomePageUrl);
			HMCCommon.Login(hmcPage, testData);
			
			//2, Navigate to Price Settings -> Pricing cockpit -> Login again
			Dailylog.logInfoDB(2, "Navigate to Price Settings -> Pricing cockpit -> Login again", Store, testName);
			
			hmcPage.Home_PriceSettings.click();
			hmcPage.Home_PricingCockpit.click();
			//driver.switchTo().frame(hmcPage.PricingCockpit_iframe);
			driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/europe1novarto/index.jsp?lang=')]")));
	
			//3, Click on B2C Price Rules
			Dailylog.logInfoDB(3, "Click on B2C Price Rules", Store, testName);
			
			hmcPage.PricingCockpit_B2CPriceRules.click();
			
			Thread.sleep(6000);
			
			String titileName = "NA21842 Price Rule" + Store;
			
			driver.findElement(By.xpath("//div[@data-name='group']/div[contains(@id,'s2id')]/a/span[contains(@id,'select2-chosen')]")).click();
			Thread.sleep(4000);
			driver.findElement(By.xpath("//div[@id='select2-drop']/div[@class='select2-search']/input")).clear();
			driver.findElement(By.xpath("//div[@id='select2-drop']/div[@class='select2-search']/input")).sendKeys(titileName);
			Thread.sleep(10000);
			
			if(Common.isElementExist(driver, By.xpath("//div[@class='select2-result-label']/span"))){
				driver.findElement(By.xpath("//div[@class='select2-result-label']/span")).click();
				
				driver.findElement(By.xpath("//button[@type='submit'][text()='Filter']")).click();
				Thread.sleep(6000);
				
				
				driver.findElement(By.xpath("//a[contains(@class,'deleteGroup ')][contains(.,'Delete')]")).click();
				Thread.sleep(3000);
				
				driver.findElement(By.xpath("//input[contains(@class,'confirmation form-control')]")).clear();
				driver.findElement(By.xpath("//input[contains(@class,'confirmation form-control')]")).sendKeys("DELETE");
				
				driver.findElement(By.xpath("//button[contains(@class,'confirm-button')]")).click();
			}
			
			//4, Create new rule group 
			Dailylog.logInfoDB(4, "Create new rule group", Store, testName);
			
			//hmcPage.B2CPriceRules_CreateNewGroup1.click();
			driver.navigate().refresh();
			
			driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/europe1novarto/index.jsp?lang=')]")));

			//3, Click on B2C Price Rules
			Dailylog.logInfoDB(3, "Click on B2C Price Rules", Store, testName);
			
			hmcPage.PricingCockpit_B2CPriceRules.click();
			
			Thread.sleep(4000);

			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click();", hmcPage.B2CPriceRules_CreateNewGroup1);
			
			Thread.sleep(6000);
			
			driver.findElement(By.xpath("//div[@class='groupType']/div/a/span[@class='select2-chosen']")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//div[@id='select2-drop']/div/input")).clear();
			driver.findElement(By.xpath("//div[@id='select2-drop']/div/input")).sendKeys("Delegation");
			Thread.sleep(3000);
			driver.findElement(By.xpath("//ul[@class='select2-results']/li/div/span")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//button[contains(@class,'continue')]")).click();
	
			hmcPage.B2CPriceRules_PriceRuleName.clear();
			
			
			hmcPage.B2CPriceRules_PriceRuleName.sendKeys(titileName);
			
			//set the country
			hmcPage.createNewGroup_country_select.click();
			Thread.sleep(4000);
			
			hmcPage.createNewGroup_country_select_input.clear();
			hmcPage.createNewGroup_country_select_input.sendKeys(getCountry(Store));
			Thread.sleep(3000);
			
			driver.findElement(By.xpath("//div[@id='select2-drop']/ul[@class='select2-results']/li/div")).click();
			
			//set the base product
			driver.findElement(By.xpath("//div[@id='s2id_baseProduct']/a/span[@class='select2-chosen']")).click();
			Thread.sleep(4000);
			hmcPage.createNewGroup_country_select_input.clear();
			hmcPage.createNewGroup_country_select_input.sendKeys(productNumber);
			Thread.sleep(4000);
			
			driver.findElement(By.xpath("//div[@id='select2-drop']/ul[@class='select2-results']/li/div")).click();
			
			//set the catalog version
			driver.findElement(By.xpath("//div[@id='s2id_catalogId']/a/span[@class='select2-chosen']")).click();
			Thread.sleep(4000);
			hmcPage.createNewGroup_country_select_input.clear();
			hmcPage.createNewGroup_country_select_input.sendKeys("- online");
			Thread.sleep(4000);
			driver.findElement(By.xpath("//div[@id='select2-drop']/ul[@class='select2-results']/li/div")).click();
			
			// set the dynamic 
			driver.findElement(By.xpath("//li[@id='dynamic_div']/a/span")).click();
			driver.findElement(By.xpath("//input[@id='negativeSignRadio']/../label[@for='negativeSignRadio']")).click();
			driver.findElement(By.xpath("//input[@id='radioAbsolute']/../label[@for='radioAbsolute']")).click();
			
			driver.findElement(By.xpath("//div[@class='dynamic-value']/input")).clear();
			driver.findElement(By.xpath("//div[@class='dynamic-value']/input")).sendKeys("10");
			
			//set the delegation role 
			driver.findElement(By.xpath("//div[@id='s2id_delegationRole']/a/span[@class='select2-chosen']")).click();
			Thread.sleep(4000);
			((JavascriptExecutor) driver)
			.executeScript("scroll(0,600)");
			hmcPage.createNewGroup_country_select_input.clear();
			hmcPage.createNewGroup_country_select_input.sendKeys("Manager");
			
			Thread.sleep(4000);
			driver.findElement(By.xpath("//div[@id='select2-drop']/ul[@class='select2-results']/li/div")).click();
			
			// set the unit
			driver.findElement(By.xpath("//div[@id='Unit']//a[@class='head']")).click();
			Thread.sleep(4000);
			
			driver.findElement(By.xpath("//div[@id='Unit']//div[contains(@class,'select2-search')]/input[contains(@class,'searchBar')]")).sendKeys(testData.B2C.getUnit());
			Thread.sleep(4000);
			driver.findElement(By.xpath("(//*[@id='Unit']//li[contains(@class,'level-')]/div[@class='head']/a[@class='branch'])[1]")).click();
			
			
			
			driver.findElement(By.xpath("//button[contains(@class,'addRuleToGroup')]")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//button[contains(@class,'createGroup')]")).click();
			
			driver.switchTo().defaultContent();
			
			hmcPage.Home_EndSessionLink.click();

			//5, Go to B2C website and login with telesales account
			Dailylog.logInfoDB(5, "Go to B2C website and login with telesales account", Store, testName);
			
			driver.get(loginUrl);
			
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			
			//6,Click 'Start Assisted Service Session' in 'My Account',
			//input exist account (users in the same country) under the 'customer ID' label,
			//click 'Start session'
			Dailylog.logInfoDB(6, "click 'Start session'", Store, testName);
			
			b2cPage.myAccountPage_startAssistedServiceSession.click();
			Thread.sleep(7000);
			
			//B2CCommon.loginASMAndStartSession(driver, b2cPage,"customer", testData.B2C.getLoginID());
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath(".//*[@id='customerFilter']")));
			b2cPage.ASM_SearchCustomer.sendKeys("jdamore@lenovo.com");
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.cssSelector("[id^='ui-id-']>a")));
			b2cPage.ASM_CustomerResult.click();
			b2cPage.ASM_StartSession.click();
			Thread.sleep(5000);
			
			
			
			//7, Add one product to cart and go to the cart 
			Dailylog.logInfoDB(7, "Add one product to cart and go to the cart , ", Store, testName);
			
			driver.get(cartUrl);
			b2cPage.Cart_QuickOrderTextBox.sendKeys(productNumber);
			b2cPage.Cart_AddButton.click();
			
			Assert.assertTrue(driver.findElement(By.xpath("//td[contains(.,'Price Range')]")).isDisplayed());
			
			String range = driver.findElement(By.xpath("//td[contains(.,'Price Range')]/../td/dl")).getText().toString();
			
			String[] strs = range.split("~");
			
			String maxLimit = strs[1].trim().replaceAll("HK", "").replaceAll("SG", "").replace("£", "")
					.replace("€", "").replaceAll("￥", "").replaceAll("NT", "")
					.replaceAll("₩", "").replace("$", "");
			
			System.out.println("maxLimit is :" + maxLimit);
			
			if(maxLimit.contains(".")){
				maxLimit = maxLimit.split("\\.")[0];
			}
			
			int maxNumber = Integer.parseInt(maxLimit);
			
			Assert.assertTrue(maxNumber == 10);
			
			
			//8, Edit the price in Price override, select the type, and enter the reason, then click update
			// Ensure the modified price in the range of $10
			Dailylog.logInfoDB(7, "Edit the price in Price override, select the type, and enter the reason, then click update , Ensure the modified price in the range of $10", Store, testName);
			
			String priceValue = driver.findElement(By.xpath("//input[@class='cartDetails-overriddenPrice']")).getAttribute("placeholder").toString();
			if(priceValue.contains(".")){
				priceValue = priceValue.split("\\.")[0];
			}
			
			int priceNumber = Integer.parseInt(priceValue);
			
			int priceNumber_change = priceNumber - maxNumber/2;
			
			driver.findElement(By.xpath("//input[@class='cartDetails-overriddenPrice']")).clear();
			driver.findElement(By.xpath("//input[@class='cartDetails-overriddenPrice']")).sendKeys(priceNumber_change+"");
			
			Select select = new Select(driver.findElement(By.xpath("//select[@name='reasonNum']")));
			
			select.selectByValue("1");
			b2cPage.OverrideCheckbox.sendKeys("xxxxx");
			b2cPage.OverrideUpdate.click();
			System.out.println("Override performs successfully");
			Thread.sleep(5000);
			
			//9, Click lenovo checkout
			Dailylog.logInfoDB(9, "Click lenovo checkout", Store, testName);
			
			b2cPage.lenovo_checkout.click();
			By addressLine3 = By.xpath("//input[@id='line3']");
			By addressOptions = By.xpath("//input[@name='address']");
			By LocatorDatePicker = By
					.xpath("//div[@id='ui-datepicker-div']//tr[last()]/td/a");
			By invoiceIndicator = By.xpath("//input[@id='carrierCodeTW']");
			By suggestedAddressOption = By
					.xpath(".//*[@id='checkout_validateFrom_ok']");
			By Visa_PW = By.xpath("//input[@name='external.field.password']");
			By ThankYouTitle = By
					.xpath(".//*[contains(text(),'Thank you') or contains(text(),'ご注文ありがとうございました。') or contains(text(),'感謝您')]");
		
			
			By locator11 = By
					.xpath(".//*[@id='checkoutForm-shippingContinueButton']");
			By locator12 = By.xpath("//input[@value='ok']");
			By locator13 = By.xpath("//input[@id='copyAddressToBilling']");

			Actions actions = new Actions(driver);
			
			Thread.sleep(5000);
			if (Common.isElementExist(driver, locator13)) {
				actions.moveToElement(b2cPage.CopyAddress).click()
						.perform();
			}
			if (b2cPage.ASM_EditAddress.isDisplayed()) {
			b2cPage.ASM_EditAddress.click();}
			Thread.sleep(5000);
			b2cPage.Shipping_FirstNameTextBox.clear();
			b2cPage.Shipping_FirstNameTextBox.sendKeys("Shane88888888");
			b2cPage.Shipping_LastNameTextBox.clear();
			b2cPage.Shipping_LastNameTextBox.sendKeys("Li88888888");
			b2cPage.Shipping_AddressLine1TextBox.clear();
			b2cPage.Shipping_AddressLine1TextBox.sendKeys(testData.B2C
					.getDefaultAddressLine1());
			if (Common.isElementExist(driver, addressLine3)) {
				b2cPage.Shipping_AddressLine3TextBox.clear();
				b2cPage.Shipping_AddressLine3TextBox.sendKeys(testData.B2C
						.getDefaultAddressCity());
			} else {
				b2cPage.ASM_City.clear();
				b2cPage.ASM_City.sendKeys(testData.B2C
						.getDefaultAddressCity());
				b2cPage.Shipping_PostCodeTextBox.clear();
				b2cPage.Shipping_PostCodeTextBox.sendKeys(testData.B2C
						.getDefaultAddressPostCode());
			}
			WebElement state = driver.findElement(By.xpath(statelocator));
			state.click();
			b2cPage.Mobile.clear();
			b2cPage.Mobile.sendKeys("2022022020");
			b2cPage.Shipping_EmailTextBox.clear();
			b2cPage.Shipping_EmailTextBox.sendKeys("lixe1@lenovo.com");

			Thread.sleep(3000);
			((JavascriptExecutor) driver).executeScript("scroll(0,300)");

			actions.moveToElement(b2cPage.standardShipping).click()
					.perform();

			Thread.sleep(5000);

			actions.moveToElement(b2cPage.Shipping_ContinueButton).click()
					.perform();
			Thread.sleep(12000);
			if (Common.isElementExist(driver, locator12)) {
				if (Common.isElementExist(driver, addressOptions)) {
					b2cPage.Shipping_AddressOptionsList.click();
				}
				b2cPage.addressValidation.click();
				Thread.sleep(6000);
			}
			if (Common.isElementExist(driver, suggestedAddressOption)) {
				b2cPage.Shipping_SuggestedAddress.click();
				Thread.sleep(3000);
			}
			while (Common.isElementExist(driver, locator11)) {
				actions.moveToElement(b2cPage.Shipping_ContinueButton)
						.click().perform();
				Thread.sleep(5000);
			}
			Thread.sleep(5000);
			driver.switchTo().frame(b2cPage.Payment_CreditCardFrame);

			// if(!b2cPage.CreditCard.isSelected()){b2cPage.CreditCard.click();}
			b2cPage.Visa.click();
			b2cPage.Payment_CardNumberTextBox.sendKeys("4111111111111111");
			b2cPage.Payment_CardMonthTextBox.sendKeys("06");
			b2cPage.Payment_CardYearTextBox.sendKeys("20");
			b2cPage.Payment_SecurityCodeTextBox.sendKeys("132");
			driver.switchTo().defaultContent();

			b2cPage.Payment_CardHolderNameTextBox.sendKeys("LIXE");
			// System.out.println(countryweb+" "+Common.isElementExist(driver,
			// invoiceIndicator));
			if (Common.isElementExist(driver, invoiceIndicator)) {
				b2cPage.Payment_invoiceType.click();

			}
			if (b2cPage.purchaseNum.isDisplayed()
					&& b2cPage.purchaseNum.isEnabled()) {
				b2cPage.purchaseNum.sendKeys("1234567654");
				SimpleDateFormat dataFormat = new SimpleDateFormat(
						"MM/dd/YYYY");
				b2cPage.purchaseDate.sendKeys(dataFormat.format(new Date())
						.toString());
				if (Common
						.isElementExist(
								driver,
								By.xpath("//div[@id='ui-datepicker-div']//tr[last()]/td/a"))) {
					driver.findElements(LocatorDatePicker)
							.get(driver.findElements(LocatorDatePicker)
									.size() - 1).click();
				}
			}
			b2cPage.Payment_ContinueButton.click();
			Thread.sleep(3000);
			if (Common.isElementExist(driver, Visa_PW)) {
				b2cPage.VisaPassword.sendKeys("1234");
				b2cPage.VisaSubmit.click();
			}
			if (Common.isElementExist(b2cPage.PageDriver,
					By.id("hidden3DSFrame0"))) {
				B2CCommon.handleVisaVerify(b2cPage);

			}
			Thread.sleep(6000);

			//10, Complete the place order 
			Dailylog.logInfoDB(10, "Complete the place order", Store, testName);
			
			b2cPage.OrderSummary_AcceptTermsCheckBox.click();
			B2CCommon.clickPlaceOrder(b2cPage);
			Thread.sleep(8000);
			// order confirmation
			
			

			assert Common.isElementExist(driver, ThankYouTitle);
			String orderNumber = b2cPage.orderNumber.getText().toString();
			Dailylog.logInfo(Store + " Order number is " + orderNumber);
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

public String getCountry(String store){
		
		String country = "";
		
		if(store.equals("AU")){
			country = "Australia";
		}else if(store.equals("US")){
			country = "United States";
		}else if(store.equals("JP")){
			country = "Japan";
		}else if(store.equals("CA_AFFINITY")){
			country = "Canada";
		}

		return country;
		
	}	

}
	