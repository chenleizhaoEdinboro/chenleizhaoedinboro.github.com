package TestScript.B2C;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class CONTENT193Test extends SuperTestClass {
	public HMCPage hmcPage;
	private B2CPage b2cPage;
	private B2BPage b2bPage;
	String url = "https://presupport.lenovo.com/ie/en/partslookup";
	String partnumber = "00KT110";
	String ieZipCode = "E45 HW09";
	String ieCity = "Ireland";
	String username = "tangling1@lenovo.com";
	String password = "1q2w3e4r";
	
	public CONTENT193Test(String store) {
		this.Store = store;
		this.testName = "CONTENT-193";
	}

	@Test(alwaysRun = true, groups = {"contentgroup", "dr", "p2", "b2c"})
	public void CONTENT193(ITestContext ctx) {
		try {
			this.prepareTest();
			
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			b2bPage = new B2BPage(driver);
			
			//1.load hmc
			if(Store.toLowerCase().equals("ie")) {
				changeHMCGatekeeper("iepart");
			}else if(Store.toLowerCase().equals("fr")) {
				changeHMCGatekeeper("frpart");
			}else if(Store.toLowerCase().equals("gb")) {
				changeHMCGatekeeper("gbpart");
			}
			Dailylog.logInfoDB(1, "login hmc", Store, testName);
			//2.load dr
			loadDR("");
			
			//3.login
			if(Store.toLowerCase().equals("ie")) {
				changeHMCGatekeeper("iepart");
			}else if(Store.toLowerCase().equals("fr")) {
				changeHMCGatekeeper("frpart");
			}else if(Store.toLowerCase().equals("gb")){
				changeHMCGatekeeper("gbpart");
			}
			
			//4.load dr
			loadDR(username);
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}


	private void loadDR(String username) throws InterruptedException {
		driver.get(url);
		Common.sleep(2000);
		if(Store.toLowerCase().equals("ie")) {
			b2cPage.SelectedCountry.click();
			b2cPage.Ireland.click();
			b2cPage.SelectedLanguage.click();
			b2cPage.English.click();
		}else if(Store.toLowerCase().equals("fr")) {
			b2cPage.SelectedCountry.click();
			b2cPage.France.click();
			b2cPage.SelectedLanguage.click();
			b2cPage.Français.click();
		}else if(Store.toLowerCase().equals("gb")) {
			b2cPage.SelectedCountry.click();
			b2cPage.Germany.click();
			b2cPage.SelectedLanguage.click();
			b2cPage.Deutsch.click();
		}
		b2cPage.Caret.click();
		b2cPage.APartsInfo.click();
		b2cPage.PartNumQuery.clear();
		b2cPage.PartNumQuery.sendKeys(partnumber);
		Common.sleep(3000);
		Common.javascriptClick(driver, b2cPage.PartNumberSearchbutton);
		Common.sleep(3000);
		Dailylog.logInfoDB(2, "The result is displayed below.", Store, testName);
		Common.javascriptClick(driver, b2cPage.IconCart);
		Dailylog.logInfoDB(3, "Popup show ", Store, testName);
		Common.javascriptClick(driver, b2cPage.Viewmycart);
		Dailylog.logInfoDB(4, "Go to the shoplogin page", Store, testName);
		if(username.equals("tangling1@lenovo.com")) {
			b2cPage.shoplogin_email.clear();
			b2cPage.shoplogin_email.sendKeys(username);
			b2cPage.shoplogin_pwd.clear();
			b2cPage.shoplogin_pwd.sendKeys(password);
		}
		b2cPage.BtnCheckout.click();
		Common.sleep(3000);
		Assert.assertTrue(Common.checkElementExists(driver, b2cPage.CartSummary, 10));
		Dailylog.logInfoDB(5, "Go to the hybris cart page successfully", Store, testName);
		String attribute = b2cPage.Digitalriver.getAttribute("action");
		Assert.assertTrue(attribute.contains("lenovosp"));
		Dailylog.logInfoDB(6, "Action contain lenovosp(new SiteID)\\n", Store, testName);
		
		
		b2cPage.Drlenovocheckout.click();
		Common.sleep(3000);
		Assert.assertTrue(driver.getCurrentUrl().contains("cte"));
		b2cPage.DrcartLink.click();
		Assert.assertTrue(Common.checkElementExists(driver, b2cPage.CartSummary, 10));
		b2cPage.Drlenovocheckout.click();
		Common.sleep(3000);
		Assert.assertTrue(driver.getCurrentUrl().contains("cte"));
		Dailylog.logInfoDB(7, "Go to the DR site successfully,url contain cte", Store, testName);
		writeBilling(ieZipCode);
	}

	private void writeBilling(String code) throws InterruptedException {
		b2cPage.EmailAddress.sendKeys("lisong2@lenovo.com");
		b2cPage.FirstName.sendKeys("automation");
		b2cPage.LastName.sendKeys("automation");
		b2cPage.AddressLine1.sendKeys("377 Frankton Road");
		b2cPage.BillingCity.sendKeys("New Zealand");
		b2cPage.ZipCode.sendKeys(code);
		b2cPage.PhoneNumber.sendKeys("123456789");
		b2cPage.drcreditCardRadioSelect.click();
		b2cPage.DRCardNumber.sendKeys("5105105105105100");
		b2cPage.Month.click();
		b2cPage.Year.click();
		b2cPage.cardSecurityCode.sendKeys("1234");
		b2cPage.DRcheckoutButton.click();
		Common.sleep(3000);
		if(Common.checkElementExists(driver, b2cPage.rtavContinueButton, 10)) {
			b2cPage.rtavContinueButton.click();
		}
		Common.sleep(3000);
		if(Common.checkElementExists(driver, driver.findElement(By.xpath("//*[@id=\"tosAccepted\"]")), 10)) {
			driver.findElement(By.xpath("//*[@id=\"tosAccepted\"]")).click();
			driver.findElement(By.xpath("//*[@id='submitBottom']")).click();
		}
		String text = b2cPage.drorderNumber.getText();
		String[] split = text.split(":");
		String order = split[split.length-1];
		Common.sleep(5000);
		b2cPage.continueShopping.click();
		Common.sleep(8000);
		checkOrder(order);
	}

	private void checkOrder(String order) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Nemo.click();
		hmcPage.NEMO_digitalRiver.click();
		hmcPage.NEMO_digitalRiver_tracelog.click();
		driver.findElement(By.xpath("//input[@id='Content/StringEditor[in Content/GenericCondition[DigitalRiverTraceLog.drOrderNo]]_input']")).clear();
		driver.findElement(By.xpath("//input[@id='Content/StringEditor[in Content/GenericCondition[DigitalRiverTraceLog.drOrderNo]]_input']")).sendKeys(order);
		hmcPage.B2BUnit_SearchButton.click();
		//table[@id='Content/McSearchListConfigurable[Order]_innertable']/tbody/tr[contains(@id,'Content/OrganizerListEntry')]
		Common.doubleClick(driver, driver.findElement(By.xpath("(//table[contains(@id,'Content/McSearchListConfigurable[DigitalRiverTraceLog]_innertable')]/tbody/tr)[3]")));
		String text = driver.findElement(By.xpath("//input[@id='Content/StringEditor[in Content/Attribute[DigitalRiverTraceLog.drOrderNo]]_input']")).getText();
		Assert.assertTrue(text.contains(order),"order is not exist");
		String attribute = driver.findElement(By.xpath("//*[@id=\"Content/BooleanEditor[in Content/Attribute[DigitalRiverTraceLog.placeOrderStatus]]_true\"]")).getAttribute("value");
		
		Assert.assertEquals(driver.findElement(By.xpath("//table[@class='attributeChip']//td/select/option[@value=108]")),ieCity);
		Assert.assertEquals(attribute, "Yes");
		
		Assert.assertTrue(driver.findElement(By.xpath("//textarea[@id='Content/NemoPunchoutTraceLogTextAreaEditor[in Content/Attribute[DigitalRiverTraceLog.cartXml]]_textarea']")).getText().contains("lenovosp"),"lenovosp is not exist");
	}

	private void changeHMCGatekeeper(String unit) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_B2CCommercelink.click();
		hmcPage.Home_B2CUnitLink.click();
		hmcPage.B2CUnit_IDTextBox.clear();
		hmcPage.B2CUnit_IDTextBox.sendKeys(unit);
		hmcPage.B2CUnit_SearchButton.click();
		Common.sleep(3000);
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		hmcPage.B2CUnit_SiteAttributeTab.click();
		Common.scrollToElement(driver, driver.findElement(By.xpath("//div[contains(text(),'GateKeeper Attributes')]")));
		driver.findElement(By.xpath("//span[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.dynamicRegistrationGatekeeperToggle]]_spanfalse']")).click();
		driver.findElement(By.xpath("//span[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.dynamicSerialNumberGatekeeperToggle]]_spanfalse']")).click();
		driver.findElement(By.xpath("//span[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.dynamicPasscodeGatekeeperToggle]]_spanfalse']")).click();
		hmcPage.Common_SaveButton.click();
		Common.sleep(3000);
		hmcPage.hmcHome_hmcSignOut.click();
	}

	public void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());		
			driver.switchTo().window(windows.get(windowNo));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
