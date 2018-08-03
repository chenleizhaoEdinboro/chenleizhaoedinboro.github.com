package TestScript.B2C;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class NA28206Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	String productNo;
	String totalPrice;
	String loginUser;
	String defaultProductNo;
	String cartID;
	
	public NA28206Test(String store,String defaultProductNo) {
		this.Store = store;
		this.testName = "NA-28206";
		this.defaultProductNo = defaultProductNo;
	}

	@Test(alwaysRun= true)
	public void NA28206(ITestContext ctx) {
		try {
			WebElement ele;
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			loginUser = testData.B2C.getLoginID();
			
			Dailylog.logInfoDB(4,"Login website, add product", Store,testName);
			driver.get(testData.B2C.getHomePageUrl());
			System.out.println(driver.getCurrentUrl());
			B2CCommon.handleTeleGateKeeper(b2cPage, testData);
			driver.get(testData.B2C.getloginPageUrl());
			//Handle advertisement
			if(Common.checkElementDisplays(driver, By.xpath("//div[@class='mfp-iframe-scaler']/button[@title='Close (Esc)']"), 3)){
				driver.findElement(By.xpath("//div[@class='mfp-iframe-scaler']/button[@title='Close (Esc)']")).click();
			}
			B2CCommon.login(b2cPage, loginUser, "1q2w3e4r");
			int i=0;
			while(driver.getCurrentUrl().contains("j_spring_security")&&(i++<3)) {
				driver.get(testData.B2C.getloginPageUrl());
				B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
			}
			Common.sleep(3000);
			driver.get(testData.B2C.getHomePageUrl()+"/cart");
			B2CCommon.emptyCart(driver, b2cPage);
			
			//B2CCommon.addPartNumberToCart(b2cPage,"80VR00DALS");
			try {
				addPartNumberToCart(b2cPage,testData.B2C.getDefaultMTMPN());
			}catch(Exception e) {
				B2CCommon.addPartNumberToCart(b2cPage,defaultProductNo);
			}
			//Step 6:
			Dailylog.logInfoDB(6,"Checkout and type shipping information then continue.", Store,testName);
			b2cPage.Cart_CheckoutButton.click();
			Common.sleep(1000);
			if(driver.getCurrentUrl().contains("login")){
				B2CCommon.login(b2cPage, loginUser, "1q2w3e4r");
			}
			if(Common.checkElementDisplays(driver, b2cPage.ASM_EditAddress, 3)){
				b2cPage.ASM_EditAddress.click();
			}
			Thread.sleep(1000);
			B2CCommon.fillShippingInfo(b2cPage, "Batman", "Begins", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), "Quebec", testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), loginUser, testData.B2C.getConsumerTaxNumber());
			
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Common.sleep(10000);;
			//Common.scrollToElement(driver, b2cPage.Shipping_ContinueButton);
			if (Common.checkElementExists(driver, b2cPage.Shipping_AddressMatchOKButton, 10))
				b2cPage.Shipping_AddressMatchOKButton.click();
			if (Common.checkElementDisplays(driver, b2cPage.ValidateInfo_SkipButton, 10))
				b2cPage.ValidateInfo_SkipButton.click();
			/*if (Common.checkElementDisplays(driver, b2cPage.Shipping_validateAddressItem, 3))
				b2cPage.Shipping_validateAddressItem.click();*/
			Common.waitElementClickable(driver, b2cPage.Payment_ContinueButton, 15);
			/*Thread.sleep(3000);
			b2cPage.Shipping_ContinueButton.click();*/


			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			if (Common.checkElementExists(driver, b2cPage.Payment_UseShippingAddress, 10)
					&& (b2cPage.Payment_UseShippingAddress.getAttribute("value").equals("true"))) {
				b2cPage.Payment_UseShippingAddress.click();
			}
			fillPaymentAddressInfo(b2cPage, "Batman", "Begins", testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(),
					testData.B2C.getConsumerTaxNumber());
			
			Common.scrollToElement(driver, b2cPage.Payment_ContinueButton);
			b2cPage.Payment_ContinueButton.click();

			B2CCommon.handleVisaVerify(b2cPage);
			driver.findElement(By.xpath("//div[@class='show-cartId']")).click();
			cartID = driver.findElement(By.xpath("//span[@class='cartId-info']/span")).getText();
			Dailylog.logInfoDB(5,"cartID "+cartID, Store,testName);
			
//			driver.get(testData.HMC.getHomePageUrl());
//			HMCCommon.Login(hmcPage, testData);
//			Thread.sleep(8000);
//			hmcPage.marketing.click();
//			hmcPage.marketing_orderStatistics.click();
//			hmcPage.marketing_orderStatistics_carts.click();
//			
//			hmcPage.marketing_os_carts_orderNumTxtBox.clear();
//			hmcPage.marketing_os_carts_orderNumTxtBox.sendKeys(cartID);
//			hmcPage.marketing_os_carts_search.click();
//			Common.doubleClick(driver, driver.findElement(By.xpath("//*[text()='" + cartID+ "']")));
//			hmcPage.marketing_os_carts_administration.click();
//			
//			String xmlContent = driver.findElement(By.id("Content/TextAreaEditor[in Content/Attribute[Cart.sabrixRequestXmlContent]]_textarea")).getText();
//			System.out.println("Check xml content");
//			System.out.println(xmlContent);
//			
//			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//			dbf.setValidating(false);
//			DocumentBuilder db = dbf.newDocumentBuilder();
//			
//			xmlContent = new String(xmlContent.getBytes(), "utf-8");
//			InputStream is = new ByteArrayInputStream(xmlContent.getBytes("utf-8"));
//			Document document = db.parse(is);   
//
//			XPathFactory factory = XPathFactory.newInstance();
//			XPath xpath = factory.newXPath();
//			Node xmlNode;
//			NodeList customerList = (NodeList) xpath.evaluate("//customer", document, XPathConstants.NODESET);
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	
	public void addPartNumberToCart(B2CPage b2cPage, String partNum) {
		b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
		b2cPage.Cart_AddButton.click();
		Common.waitElementClickable(b2cPage.PageDriver,
				b2cPage.PageDriver.findElement(By.xpath("//*[text()='" + partNum + "']")), 5);
	}
	
	public void fillPaymentAddressInfo(B2CPage b2cPage, String firstName, String lastName, String addressline1,
			String city, String state, String postCode, String phone,String consumerTaxNumber) {
		if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.Payment_FirstNameTextBox, 3)) {
			if (b2cPage.Payment_FirstNameTextBox.isEnabled()) {
				b2cPage.Payment_FirstNameTextBox.clear();
				b2cPage.Payment_FirstNameTextBox.sendKeys(firstName);
			}
			if (b2cPage.Payment_LastNameTextBox.isEnabled()) {
				b2cPage.Payment_LastNameTextBox.clear();
				b2cPage.Payment_LastNameTextBox.sendKeys(lastName);
			}
			if (b2cPage.Payment_AddressLine1TextBox.isEnabled()) {
				b2cPage.Payment_AddressLine1TextBox.clear();
				b2cPage.Payment_AddressLine1TextBox.sendKeys(addressline1);
			}
			if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.Payment_CityTextBox, 1)) {
				if (b2cPage.Payment_CityTextBox.isEnabled()) {
					b2cPage.Payment_CityTextBox.clear();
					b2cPage.Payment_CityTextBox.sendKeys(city);
				}
			} else if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.Payment_City2TextBox, 1)) {
				if (b2cPage.Payment_City2TextBox.isEnabled()) {
					b2cPage.Payment_City2TextBox.clear();
					b2cPage.Payment_City2TextBox.sendKeys(city);
				}
			}
			if (b2cPage.Payment_StateDropdown.isEnabled()) {
				Select dropdown = new Select(b2cPage.Payment_StateDropdown);
				dropdown.selectByVisibleText(state);
			}
			if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.Payment_PostCodeTextBox, 1)) {
				if (b2cPage.Payment_PostCodeTextBox.isEnabled()) {
					b2cPage.Payment_PostCodeTextBox.clear();
					b2cPage.Payment_PostCodeTextBox.sendKeys(postCode);
				}
			}
			if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.Payment_ContactNumTextBox, 1)) {
				if (b2cPage.Payment_ContactNumTextBox.isEnabled()) {
					b2cPage.Payment_ContactNumTextBox.clear();
					b2cPage.Payment_ContactNumTextBox.sendKeys(phone);
				}
			}
			if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.Payment_consumerTaxNumber, 1)) {
				if (b2cPage.Payment_consumerTaxNumber.isEnabled()) {
					b2cPage.Payment_consumerTaxNumber.clear();
					b2cPage.Payment_consumerTaxNumber.sendKeys(consumerTaxNumber);
				}
			}
			
		}
	}
	

}
	

