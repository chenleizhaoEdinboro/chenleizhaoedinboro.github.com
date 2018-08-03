package TestScript.B2C;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import Pages.PartSalesPage;
import TestScript.SuperTestClass;

public class NA26023Test extends SuperTestClass {

	public String laptopPageURL;
	private String currentURL;
	private String seriesName;
	private String partNo;
    private String B2CPartSalesURL;
	public NA26023Test(String store, String partsalesNumber,String myAccountURL) {
		this.Store = store;
		this.partNo = partsalesNumber;
		this.B2CPartSalesURL=myAccountURL;
		this.testName = "NA-26023";
		
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA26023(ITestContext ctx) {
		try {
			this.prepareTest();
			B2CPage b2cPage = new B2CPage(driver);
			PartSalesPage PSPage = new PartSalesPage(driver);
			driver.get(testData.B2C.getPartSalesUrl());
			PSPage.partSales_SelectCountry.click();
			Common.waitElementClickable(driver, driver.findElement(By.xpath("(//a[@data-code='"+this.Store.toLowerCase()+"'])[1]")), 15);
			driver.findElement(By.xpath("(//a[@data-code='"+this.Store.toLowerCase()+"'])[1]")).click();
			Common.waitElementClickable(driver,PSPage.partSales_AccessoryMenu, 20);
		    PSPage.partSales_AccessoryMenu.click();
		    PSPage.partSales_DropDownLookUp.click();
		    Common.waitElementClickable(driver, PSPage.partNumber, 20);
		    PSPage.partNumber.clear();
			PSPage.partNumber.sendKeys(partNo);
			PSPage.partLookUp.click();
	
			PSPage.addToCart.click();
			PSPage.viewMyCart.click();
			PSPage.partSalesCheckOut.click();
		    PSPage.partSales_GuestCheckOut.click();
		    
		    B2CCommon.fillShippingInfo(b2cPage, "asd", "asd", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), "lixe1@lenovo.com", testData.B2C.getConsumerTaxNumber());
			b2cPage.Shipping_ContinueButton.click();
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			B2CCommon.fillPaymentAddressInfo(b2cPage, "asd", "asd", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
			Assert.assertFalse(Common.isElementExist(driver, By.xpath(".//*[@id='quote_button']")));
            b2cPage.Payment_ContinueButton.click();
            Assert.assertTrue(Common.isElementExist(driver, By.id("Terms1")));
            b2cPage.partSales_EditCart.click();
            Thread.sleep(5000);
            Assert.assertTrue(driver.getCurrentUrl()=="https://presupport.lenovo.com/"+this.Store.toLowerCase()+"/en/partslookup");
            
            
		    
		    

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
		
	}

	public void partsalesLogin(String userName, String password,
			PartSalesPage PSPage) {
		PSPage.LenovoID.clear();
		PSPage.LenovoID.sendKeys(userName);
		PSPage.password.clear();
		PSPage.password.sendKeys(password);
		PSPage.signinButton.click();

	}
	public float GetPriceValue(String Price) {
		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("€", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.trim();
		float priceValue;
		priceValue = Float.parseFloat(Price);
		return priceValue;
	}

}