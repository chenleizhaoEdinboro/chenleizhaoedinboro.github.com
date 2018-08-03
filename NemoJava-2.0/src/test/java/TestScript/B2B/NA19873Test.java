package TestScript.B2B;
import java.util.ArrayList;
import java.util.Calendar;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;
public class NA19873Test extends SuperTestClass{
	private B2BPage b2bPage;
	private HMCPage hmcPage;	
	public String homePageUrl;
	public String B2B_Country;
	private String streetName = "101 East Erie Street";
	private String postal = "60611";
	private String cityName = "New York";
	private String changedCountry = "US";
	private String changedCountry1 = "United States";
	private String B2B_Country1 = "Australia";
	private String region = "US-NY";
	private String selectUSAddress = "//li[@class='ui-menu-item'][contains(text(),'" + cityName + "')]";
	private String CartPage_EmptyCartButton = ".//*[@id='emptyCartItemsForm']/a";
	public NA19873Test(String Store, String country){
		this.Store = Store;
		this.B2B_Country = country;
		this.testName = "NA-19873";
	}
	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "e2e", "b2b"})
	public void NA19873(ITestContext ctx) throws Exception {
		try{
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
		
			//Step 1 : go to hmc-->base store, search usle, under properties tab, and add "us" "au" as delivery countries.
			loginHmcPage();
			addDeliveryCountries();
			Common.sleep(2000);
			createAddress();
			hmcPage.Home_EndSessionLink.click();
			Dailylog.logInfoDB(1, "Add us&au as delivery countries in HMC", Store, testName);
			
			//Step 2 : logon B2B store.
			String b2bLoginURL = testData.B2B.getLoginUrl();
			driver.get(b2bLoginURL);
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
			HandleJSpring(driver);
			Dailylog.logInfoDB(2, "logon B2B store successfully", Store, testName);
			
			//Step 3: add product into cart, and go to cart.
			Common.sleep(3000);
			b2bPage.HomePage_CartIcon.click();
			Common.sleep(1500);
			if(Common.isElementExist(driver, By.xpath(CartPage_EmptyCartButton))){
				driver.findElement(By.xpath(CartPage_EmptyCartButton)).click();
			}
			String productPN = testData.B2B.getDefaultMTMPN1();
			B2BCommon.addProduct(driver, b2bPage, productPN );
			Common.sleep(2000);
			String cartURL = driver.getCurrentUrl();
			Assert.assertTrue(cartURL.contains("cart"));
			Dailylog.logInfoDB(3, "Shopping cart page is displayed. ", Store, testName);
			Dailylog.logInfoDB(3, "Added a product into Cart, productPN: " + productPN, Store, testName);
			
			//Step 4: click on checkout
			b2bPage.cartPage_LenovoCheckout.click();
			Common.sleep(3000);
			String shipmentURL = driver.getCurrentUrl();
			Assert.assertTrue(shipmentURL.contains("add-delivery-address"));
			Dailylog.logInfoDB(4, "Shipment page is displayed.The URL is:  " + shipmentURL , Store, testName);
			Common.sleep(3000);
			Assert.assertEquals(b2bPage.ShippingPage_Country.getAttribute("value"), B2B_Country1);
			Dailylog.logInfoDB(4, "The default country is displayed.  ", Store, testName);
			
			//Step 5: select another address in the dropdownlist.
			changeAnotheraddAddress();
			Dailylog.logInfoDB(5, "Select country in the dropdownlist.", Store, testName);
			Common.sleep(2000);
			String shippingPage_regionIso = ".//*[@id='state']/option[text()='" + cityName + "']";
			WebElement shippingPage_Suburb = driver.findElement(By.xpath(shippingPage_regionIso));
			String actualCity = shippingPage_Suburb.getText();
			Assert.assertEquals(cityName, actualCity);
			Assert.assertEquals(b2bPage.ShippingPage_Country.getAttribute("value"), changedCountry1);
			Dailylog.logInfoDB(5, "The selected shipping: " + cityName + " pre-populated into the address fields.", Store, testName);

			//Step :6 click on Continue.
			b2bPage.ShippingEmail.clear();
			b2bPage.ShippingEmail.sendKeys("usbuyer@sharklasers.com");
			b2bPage.shippingPage_ContinueToPayment.click();
			String paymentURL = driver.getCurrentUrl();
			Assert.assertTrue(paymentURL.contains("add-payment-method"));
			Dailylog.logInfoDB(6, "Payment page is displayed: " + paymentURL, Store, testName);
			Assert.assertEquals(B2B_Country, b2bPage.payMentPage_Country.getAttribute("value"));
			Dailylog.logInfoDB(6, "The country name for default billing address is displayed on billing address: " + B2B_Country, Store, testName);
			//Step :7 change to another billing address with different country
			changeAnotheraddAddress();
			Dailylog.logInfoDB(7, "Select country in the dropdownlist.", Store, testName);
			Common.sleep(2000);
			String actural_ChangedCountry = b2bPage.payMentPage_CountryAnother.getAttribute("value");
			Assert.assertEquals(actural_ChangedCountry, changedCountry);
			Dailylog.logInfoDB(7, "The country name for payment page address is changed: " + actural_ChangedCountry, Store, testName);
			
			//Step :8 click on Continue
			b2bPage.paymentPage_purchaseNum.sendKeys("1234567890");
			Common.sleep(2000);
			
			b2bPage.paymentPage_purchaseDate.click();
			Calendar CD = Calendar.getInstance();
			String DD = CD.get(Calendar.DATE) + "";
			driver.findElement(
					By.xpath("//*[@id='ui-datepicker-div']//a[text()='"
							+ DD + "']")).click();
			b2bPage.paymentPage_ContinueToPlaceOrder.click();
			Common.sleep(2000);
			String summaryURL = driver.getCurrentUrl();
			Assert.assertTrue(summaryURL.contains("summary"));
			Dailylog.logInfoDB(8, "Summary page is displayed." + summaryURL, Store, testName);
			Assert.assertEquals(cityName, b2bPage.summaryPage_city.getText());
			Dailylog.logInfoDB(8, "The selected shipping/billing address is displayed in summary page", Store, testName);
			
			//Step:9 Fill summary page details =================//
			String orderNum = fillInfo_SummaryPage();
			String orderConfirmationURL = driver.getCurrentUrl();
			Assert.assertTrue(orderConfirmationURL.contains("orderConfirmation"));
			Dailylog.logInfoDB(9, "Thank you page displayed.Order Num:" + orderNum, Store, testName);
			String thankYouPageCity = driver.findElement(By.xpath("//div[@class='item_container']/ul[@class='pad_none']/li[4]")).getText();
			System.out.println(thankYouPageCity);
			Assert.assertTrue(thankYouPageCity.contains(cityName));
			Dailylog.logInfoDB(9, "The selected shipping/billing address is displayed:" + thankYouPageCity, Store, testName);
			
			//Step: 10 check order xml in HMC
			loginHmcPage();
			Common.sleep(2000);
			HMCOrderCheck(hmcPage, orderNum);
			Common.doubleClick(driver, hmcPage.BaseStore_SearchResult);
			Common.sleep(2000);
			hmcPage.Order_ordersAdministration.click();
			Common.sleep(2000);
			String Z3_XML = getCountryFromXML("Z3");
			String Z4_XML = getCountryFromXML("Z4");
			String Z7_XML = getCountryFromXML("Z7");
			Assert.assertEquals(Z3_XML, Z4_XML);
			Assert.assertEquals(Z4_XML, Z7_XML);
			Assert.assertEquals(Z7_XML, changedCountry);
			Dailylog.logInfoDB(10, "The country " + Z3_XML + " is generated in Z3/Z4/Z7.", Store, testName);
		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}
	
	public void createAddress(){
		HMCCommon.searchB2BUnit(hmcPage, testData);
		Common.sleep(4500);
        hmcPage.B2BCustomer_AddressesTab.click();
        String addressField="Content/StringEditor[in Content/CreateItemListEntry[n/a]]";
        if( !Common.checkElementDisplays(driver, By.xpath("//img[contains(@id,'"+streetName+"')]"), 5)){
      	  Common.rightClick(driver, driver.findElement(By.xpath("//div[contains(text(),'Postal Code')]")));
            hmcPage.Address_CreateAddress.click();
            Common.sleep(3000);
            driver.findElement(By.id(addressField+"_input")).sendKeys(postal);
            driver.findElement(By.id(addressField+"[1]_input")).sendKeys(cityName);
            driver.findElement(By.id(addressField+"[2]_input")).sendKeys(streetName);
            hmcPage.Address_SaveAddress.click();
            Common.sleep(3000);
            driver.findElement(By.xpath("//img[contains(@id,'"+streetName+"')]")).click();
            switchToWindow(1);
            hmcPage.Address_setBillingAddress.click();
            driver.findElement(By.id("AdvanceBooleanEditor[in Attribute[Address.shippingAddress]]_true")).click();;
            hmcPage.Address_visibleforIndirect.click();
            hmcPage.Address_CompanyName.clear();
            hmcPage.Address_CompanyName.sendKeys("testBilling");
            Select sel = new Select(driver.findElement(By.xpath("//*[contains(@id,'[Address.country]]_select')]")));
            sel.selectByVisibleText(changedCountry1);
            hmcPage.Address_regionSetting.click();
            switchToWindow(2);
            hmcPage.Address_regionISO.sendKeys(region);
            hmcPage.Address_RegionSearch.click();
            Common.doubleClick(driver, driver.findElement(By.xpath(".//*[@id='StringDisplay[New York]_span']")));
            switchToWindow(1);
            driver.findElement(By.id("ImageToolbarAction[organizer.editor.save.title]_label")).click();  
            switchToWindow(0);
            hmcPage.Address_SaveAddress.click();
            Common.sleep(3000);
        }
	}
        
    public void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(
					driver.getWindowHandles());
			driver.switchTo().window(windows.get(windowNo));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void HMCOrderCheck(HMCPage page, String OrderNumber)
			throws Exception {
		Thread.sleep(5000);
		page.Home_Order.click();
		page.Home_Order_Orders.click();
		page.Home_Order_OrderID.clear();
		page.Home_Order_OrderID.sendKeys(OrderNumber);
		page.Home_Order_OrderSearch.click();
		Thread.sleep(5000);
		if (!page.Home_Order_OrderStatus.getText().contains("Completed")) {
			Thread.sleep(5000);
			page.Home_Order_OrderSearch.click();
		}
	}
	
	public void HandleJSpring(WebDriver driver) {
		if (driver.getCurrentUrl().contains("j_spring_security_check")) {
			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
		}
	}
	
	private String getCountryFromXML(String z){
		String Z_XML = hmcPage.Orders_OrderXML.getText().substring(hmcPage.Orders_OrderXML.getText().indexOf(z));
		return Z_XML.split("<country_code_iso>")[1].substring(0, 2);
	}
	
	private String fillInfo_SummaryPage() throws InterruptedException{
		if(Common.checkElementDisplays(driver, b2bPage.placeOrderPage_ResellerID, 5)){
			Common.scrollToElement(driver, b2bPage.placeOrderPage_ResellerID);
			b2bPage.placeOrderPage_ResellerID.sendKeys("reseller@yopmail.com");
		}
		
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.PAGE_UP).perform();
		if(Common.checkElementExists(driver, b2bPage.placeOrderPage_Terms, 20)){
			actions.sendKeys(Keys.PAGE_UP).perform();
			Common.scrollToElement(driver, b2bPage.placeOrderPage_Terms);
			b2bPage.placeOrderPage_Terms.click();		
		}		
		b2bPage.placeOrderPage_sendForApproval.click();
		String orderNum=b2bPage.placeOrderPage_OrderNumber.getText();
        System.out.println("Order Number is: " + orderNum);
        return orderNum;
		
	}
	private void loginHmcPage(){
		driver.get(testData.HMC.getHomePageUrl());
		hmcPage = new HMCPage(driver);
		HMCCommon.Login(hmcPage, testData);
		driver.navigate().refresh();
	}
	
	private void addDeliveryCountries(){
		hmcPage.HMCBaseCommerce_baseCommerce.click();
		hmcPage.HMCBaseCommerce_baseStore.click();
		hmcPage.baseStore_id.clear();
		hmcPage.baseStore_id.sendKeys("aule");
		hmcPage.baseStore_search.click();
		Common.doubleClick(driver, hmcPage.BaseStore_SearchResult);
		hmcPage.BaseStore_PropertiesTab.click();
		Common.sleep(3000);
		String[] array1=new String[]{"AU","US"};
		for(String item : array1 ){
			By baseStore_deliveryCountry = By.xpath("//*[@id='Content/StringDisplay[" + item + "]_span']");
			Common.sleep(2000);
			if(!Common.checkElementDisplays(driver, baseStore_deliveryCountry, 5)){
				Common.rightClick(driver, hmcPage.baseStore_ISOCode);
				Common.sleep(2000);
				hmcPage.baseStore_addCountry.click();
				Common.sleep(1000);
				Common.switchToWindow(driver, 1);
				Common.sleep(2000);
				hmcPage.baseStore_ISOCodeValue.sendKeys(item);
				hmcPage.baseStore_ISOCodeSearch.click();
				Common.sleep(3000);
				Common.doubleClick(driver, hmcPage.baseStore_ISOCodeSearchResult);
				Common.switchToWindow(driver, 0);
				Common.sleep(2000);
				hmcPage.B2BUnit_Save.click();
			}
		}	
	}
	
	private void changeAnotheraddAddress(){
		Common.mouseHover(driver, b2bPage.shipping_AddressDropdownButton);
		b2bPage.shipping_AddressDropdownButton.click();
		Common.sleep(2000);
		driver.findElement(By.xpath(selectUSAddress)).click();
	}
	
}