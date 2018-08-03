package TestScript.B2B;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestData.TestData;
import TestScript.SuperTestClass;

import java.io.ByteArrayInputStream;
import java.io.File;  
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;  
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.xpath.*;
  
import org.w3c.dom.Document;  
import org.w3c.dom.Node;  
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class NA26432Test extends SuperTestClass {

	String ToMail = "";
	String CCMail = "";
	String BCCMail = "";

	public NA26432Test(String Store) {
		this.Store = Store;
		this.testName = "NA-26432";
	}


	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"shopgroup", "Process","p2", "b2b"})
	public void NA26432(ITestContext ctx) {
		try {
			this.prepareTest();
			HMCPage hmcPage = new HMCPage(driver);
			B2BPage b2bPage = new B2BPage(driver);

			// HMC Configuration
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2BUnit(hmcPage, testData);
			hmcPage.B2BUnit_siteAttribute.click();
			setupHMCConfig();
			hmcPage.SaveButton.click();
			Thread.sleep(10000);
			hmcPage.HMC_Logout.click();

			// Add products to cart
			driver.get(testData.B2B.getHomePageUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
			Thread.sleep(2000);

			driver.get(driver.getCurrentUrl() + "cart");
			b2bPage.cartPage_quickOrder.sendKeys(testData.B2B.getDefaultMTMPN1());
			b2bPage.cartPage_addButton.click();
			Thread.sleep(3000);

			String orderNum = placeOrderFromCartCheckout(driver, b2bPage, testData);
			Dailylog.logInfoDB(1, "Order Number: " + orderNum, this.Store, this.testName);

			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Thread.sleep(8000);
			hmcPage.Home_Order.click();
			hmcPage.Home_Order_Orders.click();
			hmcPage.Home_Order_OrderID.clear();
			hmcPage.Home_Order_OrderID.sendKeys(orderNum);
			hmcPage.Home_Order_OrderSearch.click();
			System.out.println("Order status in HMC: " + hmcPage.Home_Order_OrderStatus.getText());
			Assert.assertEquals(hmcPage.Home_Order_OrderStatus.getText(), "Completed",
					"Order Status in HMC: " + orderNum);
			Dailylog.logInfoDB(12, "Order status is correct", Store, testName);
			Common.doubleClick(driver, driver.findElement(By.xpath("//*[text()='" + orderNum+ "']")));
			hmcPage.Order_ordersAdministration.click();
			String contactPersonID = driver.findElement(By.id("Content/StringEditor[in Content/Attribute[Order.contactPersonID]]_input")).getAttribute("value");
			String xmlContent = driver.findElement(By.id("Content/TextAreaEditor[in Content/Attribute[Order.xmlContentShow]]_textarea")).getText();
			System.out.println("Check xml content");
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			xmlContent = new String(xmlContent.getBytes(), "utf-8");
			InputStream is = new ByteArrayInputStream(xmlContent.getBytes("utf-8"));
			Document document = db.parse(is);   

			XPathFactory factory = XPathFactory.newInstance();
			XPath xpath = factory.newXPath();
			Node xmlNode;
			NodeList customerList = (NodeList) xpath.evaluate("//customer", document, XPathConstants.NODESET);
			for (int i =0; i<customerList.getLength(); i++){
				Node partnerFunction = (Node) xpath.evaluate("./partner_function", customerList.item(i), XPathConstants.NODE);
				switch (partnerFunction.getTextContent()){
				case "Z3":
					break;
				case "Z4":
					xmlNode = (Node) xpath.evaluate(".//first_name", customerList.item(i), XPathConstants.NODE);
					Assert.assertEquals(xmlNode.getTextContent(), "FirstNameShipping", "first name not match");
					xmlNode = (Node) xpath.evaluate(".//last_name", customerList.item(i), XPathConstants.NODE);
					Assert.assertEquals(xmlNode.getTextContent(), "LastNameShipping", "last name not match");
					xmlNode = (Node) xpath.evaluate(".//telephone_number_1", customerList.item(i), XPathConstants.NODE);
					Assert.assertEquals(xmlNode.getTextContent(), testData.B2B.getPhoneNum(), "phone number not match");
					xmlNode = (Node) xpath.evaluate(".//email_address", customerList.item(i), XPathConstants.NODE);
					Assert.assertEquals(xmlNode.getTextContent(), "test"+Store.toLowerCase()+"@sharklasers.com", "email address not match");
					break;
				case "Z7":
					xmlNode = (Node) xpath.evaluate(".//first_name", customerList.item(i), XPathConstants.NODE);
					Assert.assertEquals(xmlNode.getTextContent(), "FirstNameBilling", "first name not match");
					xmlNode = (Node) xpath.evaluate(".//last_name", customerList.item(i), XPathConstants.NODE);
					Assert.assertEquals(xmlNode.getTextContent(), "LastNameBilling", "last name not match");
					xmlNode = (Node) xpath.evaluate(".//telephone_number_1", customerList.item(i), XPathConstants.NODE);
					Assert.assertEquals(xmlNode.getTextContent(), testData.B2B.getPhoneNum(), "phone number not match");
					break;
				}
				
				System.out.println(i + ":" + partnerFunction.getTextContent());
				
			}
			
			NodeList orderTextList = (NodeList) xpath.evaluate("//order_text", document, XPathConstants.NODESET);
			for (int i =0; i<orderTextList.getLength(); i++){
				Node orderText = (Node) xpath.evaluate("./text_id", orderTextList.item(i), XPathConstants.NODE);
				switch (orderText.getTextContent()){
				case "YBCC":
					xmlNode = (Node)xpath.evaluate(".//text", orderTextList.item(i), XPathConstants.NODE);
					Assert.assertEquals(xmlNode.getTextContent(), "TestCC@Email.com", "bcc email not match");
					break;
				case "YBEX":
					xmlNode = (Node)xpath.evaluate(".//text", orderTextList.item(i), XPathConstants.NODE);
					Assert.assertEquals(xmlNode.getTextContent(), "testCommentArea", "comment not match");
					break;
				case "Y016":
					xmlNode = (Node)xpath.evaluate(".//text", orderTextList.item(i), XPathConstants.NODE);
					Assert.assertEquals(xmlNode.getTextContent(), "testShippingLabel", "shipping label not match");
					break;
				case "YI01":
					xmlNode = (Node)xpath.evaluate(".//text", orderTextList.item(i), XPathConstants.NODE);
					Assert.assertEquals(xmlNode.getTextContent(), Store, "country not match");
					break;
				case "YB11":
					xmlNode = (Node)xpath.evaluate(".//text", orderTextList.item(i), XPathConstants.NODE);
					Assert.assertEquals(xmlNode.getTextContent(), "test"+Store.toLowerCase()+"@sharklasers.com", "shipping email not match");
					break;

				}
				
				System.out.println(i + ":" + orderText.getTextContent());
				
			}
			xmlNode = (Node)xpath.evaluate(".//public_customer_number", document, XPathConstants.NODE);
			Assert.assertEquals(xmlNode.getTextContent(), contactPersonID, "contact persion ID not match");
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void setupHMCConfig() {
		// Add purchaseOrderType
		WebElement table = driver.findElement(
				By.id("table_Content/NemoB2BPaymentType2PayerId[in Content/Attribute[B2BUnit.paymentTypeAndPayerId]]_table"));
		if (!table.getText().contains("PURCHASEORDER")) {
			Common.rightClick(driver, table.findElement(By.xpath(".//tr[1]")));
			driver.findElement(By.id(
					"Content/NemoB2BPaymentType2PayerId[in Content/Attribute[B2BUnit.paymentTypeAndPayerId]]_!add_true_label"))
					.click();
			Common.switchToWindow(driver, 1);
			Common.javascriptClick(driver, driver.findElement(By.id("StringDisplay[PURCHASEORDER]_span")));
			driver.findElement(By.id("ModalGenericItemSearchList[CheckoutPaymentType]_use")).click();
			Common.switchToWindow(driver, 0);
		}

		
	}
	public String placeOrderFromCartCheckout(WebDriver driver, B2BPage b2bPage, TestData testData) throws InterruptedException
	{
		Common.javascriptClick(driver, b2bPage.cartPage_LenovoCheckout);
		B2BCommon.fillB2BShippingInfo(driver, b2bPage, testData.Store, "FirstNameShipping", "LastNameShipping",
				testData.B2B.getCompany(), testData.B2B.getAddressLine1(), testData.B2B.getAddressCity(),
				testData.B2B.getAddressState(), testData.B2B.getPostCode(), testData.B2B.getPhoneNum());
		Common.javascriptClick(driver, b2bPage.shippingPage_ContinueToPayment);

		Common.sleep(5000);
		if (Common.checkElementExists(driver, b2bPage.shippingPage_validateFromOk, 10)) {
			b2bPage.shippingPage_validateFromOk.click();
		}
		b2bPage.paymentPage_PurchaseOrder.click();
		Common.sleep(2000);
		b2bPage.paymentPage_purchaseNum.sendKeys("1234567890");
		b2bPage.paymentPage_purchaseDate.sendKeys(Keys.ENTER);

		B2BCommon.fillDefaultB2bBillingAddress(driver, b2bPage, testData);
		
		System.out.println("Go to Order page!");

		Thread.sleep(5000);
		if (Common.checkElementDisplays(driver, b2bPage.placeOrderPage_ResellerID, 5)) {
			Common.scrollToElement(driver, b2bPage.placeOrderPage_ResellerID);
			b2bPage.placeOrderPage_ResellerID.sendKeys("reseller@yopmail.com");
		}
		b2bPage.ccEmailAddress.sendKeys("TestCC@Email.com");
		b2bPage.commentArea.sendKeys("testCommentArea");
		b2bPage.shippingLabel.sendKeys("testShippingLabel");
		Common.javascriptClick(driver, b2bPage.placeOrderPage_Terms);
		Common.javascriptClick(driver, b2bPage.placeOrderPage_PlaceOrder);
		return b2bPage.placeOrderPage_OrderNumber.getText();
	}
}

