package TestScript.B2C;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class CONTENT219Test extends SuperTestClass {
	public HMCPage hmcPage;
	private B2CPage b2cPage;
	private B2BPage b2bPage;
	String url = "https://presupport.lenovo.com/ie/en/partslookup";
	String partnumber = "00HM888";
	String ieZipCode = "E45 HW09";
	String ieCity = "Ireland";
	String username = "tangling1@lenovo.com";
	String password = "1q2w3e4r";
	String partnumber1 = "00KT110";
	String urlQuan = "http://10.122.11.57:8003/";
			 
	/**
	 * the case  only run locally
	 * @param store
	 */
	public CONTENT219Test(String store) {
		this.Store = store;
		this.testName = "CONTENT-219";
	}

	@Test(alwaysRun = true)
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
			//2.load dr
			loadDR();
			
			//change quantity
			changeQuantity("3",partnumber);
			changeQuantity("3",partnumber1);
			switchToWindow(0);
			
			changeHybris("1");
			changeHybris("2");
			
			writeBilling("E45 HW09");
			
			searchQuantity("2");
			Dailylog.logInfoDB(11, "Open the url:http://10.122.11.57:8003/ to query the inventory", Store, testName);
			
			changeQuantity("2",partnumber);
			changeHybris("1");
			changeQuantity("3",partnumber1);
			changeHybris("2");
			
			writeBilling("E45 HW09");
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}


	private void searchQuantity(String quantity) {
		((JavascriptExecutor)driver).executeScript("(window.open(''))");
		Common.switchToWindow(driver, 1);
		driver.get(urlQuan);
		b2cPage.searchQuantity.click();
		b2cPage.partid.clear();
		b2cPage.partid.sendKeys(partnumber);
		if(Store.toLowerCase().equals("ie")) {
			b2cPage.country.clear();
			b2cPage.country.sendKeys("IE");
		}else if(Store.toLowerCase().equals("fr")) {
			b2cPage.country.clear();
			b2cPage.country.sendKeys("FR");
		}else if(Store.toLowerCase().equals("gb")) {
			b2cPage.country.clear();
			b2cPage.country.sendKeys("DE");
		}
		b2cPage.searchBtn.click();
		String text = driver.switchTo().alert().getText();
		Assert.assertEquals(text, quantity);
	}

	private void changeHybris(String number) {
		b2cPage.quantity0.clear();
		b2cPage.quantity0.sendKeys(number);
		b2cPage.QuantityProduct_0.click();
	}

	private void changeQuantity(String number, String partnumber) {
		((JavascriptExecutor)driver).executeScript("(window.open(''))");
		Common.switchToWindow(driver, 1);
		driver.get(urlQuan);
		b2cPage.updateQuantity.click();
		b2cPage.partid.clear();
		b2cPage.partid.sendKeys(partnumber);
		if(Store.toLowerCase().equals("ie")) {
			b2cPage.country.clear();
			b2cPage.country.sendKeys("IE");
		}else if(Store.toLowerCase().equals("fr")) {
			b2cPage.country.clear();
			b2cPage.country.sendKeys("FR");
		}else if(Store.toLowerCase().equals("gb")) {
			b2cPage.country.clear();
			b2cPage.country.sendKeys("DE");
		}
		b2cPage.quantity.clear();
		b2cPage.quantity.sendKeys(number);
		b2cPage.updateBtn.click();
		Common.sleep(5000);
		driver.switchTo().alert().accept();
		driver.close();
		Dailylog.logInfoDB(9, "Quantity is updated successfully", Store, testName);
	}

	private void loadDR() {
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
		Dailylog.logInfoDB(1, "login dr", Store, testName);
		b2cPage.PartNumQuery.clear();
		b2cPage.PartNumQuery.sendKeys(partnumber);
		b2cPage.PartNumberSearchbutton.click();
		Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath("//span[contains(text(),'"+partnumber+"')]")), 10),"product is not exist");
		Dailylog.logInfoDB(2, "The result is displayed below.", Store, testName);
		Common.sleep(3000);
		b2cPage.IconCart.click();
		Assert.assertTrue(Common.checkElementExists(driver, b2cPage.btnBlueShopping, 10),"continue shopping is exist");
		Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Viewmycart, 10),"view mycart is exist");
		Dailylog.logInfoDB(3, "Popup show ,it contain contiue shopping button and view my cart button", Store, testName);
		Common.sleep(3000);
		b2cPage.btnBlueShopping.click();
		
		b2cPage.PartNumQuery.sendKeys(partnumber1);
		b2cPage.PartNumberSearchbutton.click();
		Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath("//span[contains(text(),'"+partnumber1+"')]")), 10),"product is not exist");
		Dailylog.logInfoDB(5, "The result is displayed below.", Store, testName);
		Common.sleep(3000);
		b2cPage.IconCart.click();
		Assert.assertTrue(Common.checkElementExists(driver, b2cPage.btnBlueShopping, 10),"continue shopping is exist");
		Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Viewmycart, 10),"view mycart is exist");
		Dailylog.logInfoDB(6, "The result is displayed below.", Store, testName);
		Common.sleep(3000);
		b2cPage.Viewmycart.click();
		Common.sleep(4000);
		b2cPage.BtnCheckout.click();
		Common.sleep(3000);
		Assert.assertTrue(Common.checkElementExists(driver, b2cPage.CartSummary, 10));
	}

	private void writeBilling(String code) {
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
		b2cPage.rtavContinueButton.click();
		Common.sleep(5000);
	}


	private void changeHMCGatekeeper(String unit) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_B2CCommercelink.click();
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
