package TestScript.B2C;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2CPage;
import Pages.HMCPage;
import TestData.PropsUtils;
import TestData.PreC.B2B.US.Data;
import TestScript.SuperTestClass;

public class NA21949Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public B2BPage b2bPage;
	
	
	public String homePageUrl;
	public String loginUrl;
	public String cartUrl;
	
	public String hmcLoginUrl;
	public String hmcHomePageUrl;
	
	
	public NA21949Test(String store) {
		this.Store = store;
		this.testName = "NA-21949";
	}

	@Test(alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2c"})
	public void NA21949(ITestContext ctx) {

		try {
			this.prepareTest();
			
			// change the default downLoad path
			String pathName = "D:\\NA21949\\";
			changeChromeDefDownFolder(pathName);

			
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			b2bPage = new B2BPage(driver);
			
			homePageUrl = testData.B2C.getTeleSalesUrl();
			loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			
			hmcLoginUrl = testData.HMC.getHomePageUrl();
			hmcHomePageUrl = testData.HMC.getHomePageUrl();
			
			// Before start this test case , we need to create an order numerber
			
			driver.get(homePageUrl);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			driver.get(loginUrl);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
			
			Thread.sleep(10000);
			
			
			
			// go to cart and empty cart 
			driver.get(cartUrl);
			B2CCommon.emptyCart(driver, b2cPage);
			
			//add product into cart using quick order 
			b2cPage.Payment_QuickAddBox.clear();
			b2cPage.Payment_QuickAddBox.sendKeys(testData.B2C.getDefaultMTMPN());
			System.out.println(testData.B2C.getDefaultMTMPN());
			
			
			b2cPage.Cart_AddButton.click();
			
			//check out and place an order
			b2cPage.lenovo_checkout.click();
			
			//fill in shipping address 
			if(Common.isElementExist(driver, By.xpath(".//*[@id='addressForm']/fieldset/legend/a")) && driver.findElement(By.xpath(".//*[@id='addressForm']/fieldset/legend/a")).isDisplayed()){
				driver.findElement(By.xpath(".//*[@id='addressForm']/fieldset/legend/a")).click();
			}
			
			//(B2CPage b2cPage, String firstName,
//			String lastName, String addressline1, String city, String state,
//			String postCode, String phone, String mail,
//			String... consumerTaxNumber)
			B2CCommon.fillShippingInfo(b2cPage, "Alberto", "Costantini", "620 Broadway ", "Everett", "Massachusetts",
					"02149", "8572779690", testData.B2C.getLoginID());
//			b2cPage.Shipping_ContinueButton.click();
			
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", b2cPage.Shipping_ContinueButton);
			
			if (Common.checkElementExists(driver, b2cPage.Shipping_AddressMatchOKButton, 10)){
				b2cPage.Shipping_AddressMatchOKButton.click();
			}
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			
			
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", b2cPage.Payment_ContinueButton);
		
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			System.out.println("Clicked place order!");
			
			// get the cart id
			String cardId = b2cPage.OrderThankyou_OrderNumberLabel.getText();
			System.out.println("cardId is :" + cardId);
			
			// go to the homepage and sign out the website 
			driver.get(homePageUrl);
			
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("//ul[contains(@class,'general_Menu')]//a[contains(@href,'logout')]")));
			
			// login with telesales 
			driver.get(homePageUrl);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			driver.get(loginUrl);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			Thread.sleep(10000);
			
			b2cPage.myAccountPage_startAssistedServiceSession.click();
			Thread.sleep(5000);
			B2CCommon.closeHomePagePopUP(driver);
			
			
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath(".//*[@id='customerFilter']")));
			
			// approve the cartid 
			b2cPage.asm_DPLReportButton.click();
			Thread.sleep(6000);
			Select select_orderType= new Select(driver.findElement(By.xpath("//select[@name='orderType']")));
			select_orderType.selectByValue("Cart");
			
			b2cPage.DPLReport_ID.clear();
			b2cPage.DPLReport_ID.sendKeys(cardId);
			b2cPage.DPLReport_SearchButton.click();
			
			Thread.sleep(5000);
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//td[text()='PENDING']")),
					"Check OCR status should be pending");
			
			b2cPage.Tele_DPLReport_Approve.click();
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//td[text()='APPROVED']")),
					"OCR status should be Approved");
			
			b2cPage.Tele_DPLReport_Close.click();
			
			Thread.sleep(6000);
			
			// start cart id transaction
			b2bPage.homePage_TransactionIDBox.click();
			b2bPage.homePage_TransactionIDBox.sendKeys(cardId);
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//div[@id='asmAutoCompleteTransactionId']//a/span[1]")));
			//b2bPage.MyAccount_QuoterResult.click();
			driver.findElement(By.xpath("//div[@id='asmAutoCompleteTransactionId']//a/span[1]")).click();
			
			Thread.sleep(3000);
			
			b2bPage.MyAccountPage_StartSessionButton.click();
			Thread.sleep(5000);
			b2cPage.Cart_openCart.click();
			Thread.sleep(10000);
			b2cPage.Cart_CheckoutButton.click();
			
			
			Thread.sleep(5000);
			if(Common.isElementExist(driver, By.xpath(".//*[@id='addressForm']/fieldset/legend/a")) && driver.findElement(By.xpath(".//*[@id='addressForm']/fieldset/legend/a")).isDisplayed()){
				driver.findElement(By.xpath(".//*[@id='addressForm']/fieldset/legend/a")).click();
			}
		
			B2CCommon.fillShippingInfo(b2cPage, "Alberto", "Costantini", "620 Broadway ", "Everett", "Massachusetts",
					"02149", "8572779690", testData.B2C.getLoginID());
			
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", b2cPage.Shipping_ContinueButton);
			
			if (Common.checkElementExists(driver, b2cPage.Shipping_AddressMatchOKButton, 10)){
				b2cPage.Shipping_AddressMatchOKButton.click();
			}
				
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			B2CCommon.fillPaymentAddressInfo(b2cPage, "Alberto", "Costantini", "620 Broadway ", "Everett",
					"Massachusetts", "02149", "8572779690");
			executor.executeScript("arguments[0].click();", b2cPage.Payment_ContinueButton);

			Thread.sleep(5000);
			
			
			executor.executeScript("arguments[0].click();", b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			System.out.println("Clicked place order!");
			String orderNum = b2cPage.OrderThankyou_OrderNumberLabelNew.getText();
			
			
			
			System.out.println("orderNum is :" + orderNum);
			
			
			// 4, Click "DPL Report" 
			//Expect : DPL Report search window pop up
			Dailylog.logInfoDB(4, "Click DPL Report", Store, testName);
			
			driver.findElement(By.xpath("//*[@id='asmDplReportButton']")).click();
			Thread.sleep(10000);
			
			Assert.assertTrue(b2cPage.asm_DPLReportPopUp.isDisplayed(),"After click DPL Report button , the pop up does not exist");
			
			
			//5, Search by Order ID and B2C Store  If need creat Order,please refer to NA-21948
			// Expect :correct result should be search out
			Dailylog.logInfoDB(5, "Search by Order ID and B2C Store  If need creat Order,please refer to NA-21948", Store, testName);
			
			Select select_orderType1= new Select(driver.findElement(By.xpath("//select[@name='orderType']")));
			select_orderType1.selectByValue("Order");
			
			b2cPage.DPLReport_ID.clear();
			b2cPage.DPLReport_ID.sendKeys(orderNum);
			b2cPage.DPLReport_SearchButton.click();
			Thread.sleep(6000);
			
			List<WebElement> resultList = driver.findElements(By.xpath("//tbody[@id='dplReportTableBody']/tr[contains(@id,'_row_')]"));
			System.out.println("result list size is :" + resultList.size());
			Assert.assertTrue(resultList.size() == 1,"After click search by Order ID and B2C Store , correct result can be searched out");
			System.out.println("----------------------------");
			b2cPage.Tele_DPLReport_Close.click();
			Thread.sleep(5000);
			System.out.println("++++++++++++++++++++++++++++");
			//6, Search by Order ID and OCR status
			//Expect : correct result should be search out
			Dailylog.logInfoDB(6, "Search by Order ID and OCR status", Store, testName);
			
			driver.findElement(By.xpath("//*[@id='asmDplReportButton']")).click();
			Thread.sleep(10000);
			
			Select select_orderType2= new Select(driver.findElement(By.xpath("//select[@name='orderType']")));
			select_orderType2.selectByValue("Order");
			
			Select select_ocrStatus = new Select(driver.findElement(By.xpath("//select[@name='ocrStatus']")));
			select_ocrStatus.selectByValue("APPROVE");
			
			b2cPage.DPLReport_ID.clear();
			b2cPage.DPLReport_ID.sendKeys(orderNum);
			b2cPage.DPLReport_SearchButton.click();
			Thread.sleep(6000);
			
			List<WebElement> resultList1 = driver.findElements(By.xpath("//tbody[@id='dplReportTableBody']/tr[contains(@id,'_row_')]"));
			
			Assert.assertTrue(resultList1.size() == 1,"After click search by Order ID and OCR status , correct result can be searched out");
			
			
			//7, Search by Cart ID and B2B Store ,If need create cart ID,please refer to NA-21944
			//Expect : correct result should be search out
			
			// logout b2c telesales account 
			Dailylog.logInfoDB(7, "Search by Cart ID and B2B Store ,If need create cart ID,please refer to NA-21944", Store, testName);
			
			b2cPage.Tele_DPLReport_Close.click();
			Thread.sleep(3000);
			
			driver.findElement(By.xpath(".//*[@id='_asm']//button[contains(@class,'ASM_close')]")).click();
			System.out.println("B2C testing over");
			
			Thread.sleep(6000);
			
			
			//login in B2B ordinary customer 
			
			Data b2bData = (Data) Class.forName("TestData.PreC.B2B.US.Data").newInstance();

			String loginUrl = b2bData.getLoginUrl();
			System.out.println("loginUrl is :" + loginUrl);
			
			driver.get(loginUrl);
			Thread.sleep(4000);
			
			B2BCommon.Login(b2bPage, b2bData.getBuyerId(), b2bData.getDefaultPassword());
			
			String homepageUrl = driver.getCurrentUrl().toString();
			String cartUrl = homepageUrl + "cart";
			
			b2bPage.HomePage_productsLink.click();
			driver.findElement(By.xpath("(//a[@class='products_submenu'])[1]"))
					.click();
			b2bPage.productPage_agreementsAndContract.click();
			
			if (Common.isElementExist(driver,
					By.xpath("//form/label[contains(.,'Agreement')]"))) {
				b2bPage.productPage_radioAgreementButton.click();
			} else {
				b2bPage.productPage_raidoContractButton.click();
			}
			
			
		
			if (Common
					.isElementExist(
							driver,
							By.xpath("(//div[@class='agreementContract'])[contains(.,'Agreement')]"))) {
				List<WebElement> agreementList = driver
						.findElements(By
								.xpath("(//div[@class='agreementContract'])[contains(.,'Agreement')]"));
				if (agreementList.size() >= 1) {
					driver.findElement(
							By.xpath("(//*[@id='resultList']/div/div[4]/a)[1]"))
							.click();
					Thread.sleep(5000);

					if (Common.isElementExist(driver,
							By.xpath("//button[text()='Customize']"))
							&& driver.findElement(
									By.xpath("//button[text()='Customize']"))
									.isDisplayed()) {
						System.out.println("here is ------");
						driver.findElement(
								By.xpath("(//button[@class='close' and text()='×'])[2]"))
								.click();
						Thread.sleep(5000);
					}

					((JavascriptExecutor) driver)
							.executeScript("scroll(0,1200)");
					Thread.sleep(4000);
				
					b2bPage.Agreement_agreementsAddToCart.click();
					Thread.sleep(5000);

					while (Common
							.isElementExist(
									driver,
									By.cssSelector("div>button.pricingSummary-button.button-called-out.button-full"))) {
						b2bPage.Agreement_addToCartAccessoryBtn.click();
					}
				}
			} else {
				
				driver.findElement(
						By.xpath("(//*[contains(@id,'addToCartForm')]/button)[1]"))
						.click();
				(new WebDriverWait(driver, 20))
						.until(ExpectedConditions
								.elementToBeClickable(b2bPage.productPage_AlertAddToCart));

				b2bPage.productPage_AlertAddToCart.click();
				(new WebDriverWait(driver, 20))
						.until(ExpectedConditions.invisibilityOfElementLocated(By
								.xpath("//h2[contains(text(),'Adding to Cart')]")));
				driver.get(cartUrl);
			}

			
			b2bPage.lenovoCheckout.click();
			
			//(WebDriver driver,B2BPage b2bPage,String Store,String FirstName,String LastName,String Company,String Address,String City,String State,String PostCode,String PhoneNum){
			B2BCommon.fillB2BShippingInfo(driver, b2bPage, "us",
					"Alberto", "Costantini", "Georgia",
					"620 Broadway", "Everett",
					"Massachusetts", "02149", "8572779690");
			
			Thread.sleep(3000);
			b2bPage.shippingPage_ContinueToPayment.click();
			
			if(Common.isElementExist(driver, By.xpath(".//*[@id='checkout_validateFrom_ok']")) && driver.findElement(By.xpath(".//*[@id='checkout_validateFrom_ok']")).isDisplayed()){
				driver.findElement(By.xpath(".//*[@id='checkout_validateFrom_ok']")).click();
			}
			
			new WebDriverWait(driver, 500)
					.until(ExpectedConditions
							.elementToBeClickable(b2bPage.paymentPage_ContinueToPlaceOrder));
			Payment(driver, b2bPage, "Visa");
			
			if (Common
					.isElementExist(driver, By.xpath("//*[@id='resellerID']"))
					&& driver.findElement(By.xpath("//*[@id='resellerID']"))
							.isEnabled() && driver.findElement(By.xpath("//*[@id='resellerID']")).isDisplayed()) {
				b2bPage.placeOrderPage_ResellerID.clear();
				b2bPage.placeOrderPage_ResellerID.sendKeys("2900718028");
			}
			b2bPage.placeOrderPage_Terms.click();
			b2bPage.placeOrderPage_PlaceOrder.click();
			
			String B2B_CartID = b2cPage.OrderThankyou_OrderNumberLabel.getText().toString().trim();
			System.out.println("cart id is :" + B2B_CartID);
			
			b2bPage.homepage_Signout.click();
			
			//login with telesales account and search by cartid and b2bstore 
			driver.get(b2bData.getLoginUrl());
			B2BCommon.Login(b2bPage, b2bData.getTelesalesId(), b2bData.getDefaultPassword());
			
			b2bPage.homepage_MyAccount.click();
			b2bPage.MyAccountPage_StartAssSerSession.click();

//			b2bPage.MyAccountPage_ASMPassword.clear();
//			b2bPage.MyAccountPage_ASMPassword.sendKeys(b2bData.getDefaultPassword());
//			b2bPage.MyAccountPage_ASMLogIn.click();
			
			Thread.sleep(5000);
			
			driver.findElement(By.xpath("//*[@id='asmDplReportButton']")).click();
			Thread.sleep(10000);
			
			Select select_orderType3 = new Select(driver.findElement(By.xpath("//select[@name='orderType']")));
			select_orderType3.selectByValue("Cart");
			
			Select select_OrderType = new Select(driver.findElement(By.xpath("(//select[@name='storeType'])[last()]")));
			select_OrderType.selectByValue("b2b");
			
			
			b2cPage.DPLReport_ID.clear();
			b2cPage.DPLReport_ID.sendKeys(B2B_CartID);
			b2cPage.DPLReport_SearchButton.click();
			
			Thread.sleep(6000);
			
			List<WebElement> list_b2b = driver.findElements(By.xpath("//tbody[@id='dplReportTableBody']/tr[contains(@id,'_row_')]"));
			
			Assert.assertTrue(list_b2b.size() == 1);
			
			//8, Click "Export"
			/*Verification point :   results are export as .CSV successfully
			Check exported file info is matched with reslut:
				Cart ID or Order ID 
				totalPrice
				store
				ocrStatus
				shippingAddress
				billingAddress
				creator*/
			Dailylog.logInfoDB(8, "Click Export", Store, testName);
			
	
			File file = new File(pathName);
			
			if(!file.exists() && !file.isDirectory()){
				file.mkdirs();
			}
			
			if(file.exists()){
				File[] files = file.listFiles();
				
				for(File file1 : files){
					file1.delete();
				}
			}
			
			driver.findElement(By.xpath("//button[text()='Export']")).click();
			Thread.sleep(6000);
			
			String fileName = "D:\\NA21949\\dplReport.csv";
			
			BufferedReader bfr = new BufferedReader(new FileReader(fileName));
			
			String line = bfr.readLine();
			String line1 = bfr.readLine();
			
			String[] line1_Collection = line1.split(",");
			
			String cartid = line1_Collection[1].replace("\"", "").replace("\"", "").replace("Q", "").trim();
			System.out.println("cartid  is :" + cartid);
		
			Assert.assertTrue(B2B_CartID.equals(cartid),"checking the exported csv file , found that the cart id is not corresponding with the searched cart id");

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}
	
	
	public void Payment(WebDriver driver, B2BPage b2bPage, String paymentMethod)
			throws Exception {
		if (paymentMethod.equals("Visa")) {
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// By locator4 =
			// By.xpath("//input[@name='external.field.password']");
			// By LocatorDatePicker = By
			// .xpath("//div[@id='ui-datepicker-div']//tr[last()]/td/a");
			Actions actions = new Actions(driver);
			actions.moveToElement(b2bPage.paymentPage_creditCardRadio).click()
					.perform();
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			driver.switchTo().frame(driver.findElement(By.id("creditcardiframe0")));
			
			Select select_cardType = new Select(driver.findElement(By.xpath("//*[@id='c-ct']")));
			select_cardType.selectByValue("vi");
			
			b2bPage.paymentPage_CardNumber.clear();
			b2bPage.paymentPage_CardNumber.sendKeys("4111111111111111");
			b2bPage.paymentPage_ExpiryMonth.clear();
			b2bPage.paymentPage_ExpiryMonth.sendKeys("06");
			b2bPage.paymentPage_ExpiryYear.clear();
			b2bPage.paymentPage_ExpiryYear.sendKeys("20");
			b2bPage.paymentPage_SecurityCode.clear();
			b2bPage.paymentPage_SecurityCode.sendKeys("132");
			
			driver.switchTo().defaultContent();
			b2bPage.paymentPage_NameonCard.clear();
			b2bPage.paymentPage_NameonCard.sendKeys("LIXE");

			// if (common.checkElementExists(driver,
			// driver.findElement(locator4))) {
			// b2bPage.paymentPage_VisaPassword.sendKeys("1234");
			// b2bPage.paymentPage_VisaSubmit.click();
			// }
			try {
				Thread.sleep(6000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			boolean b = Common
					.isElementExist(
							driver,
							By.xpath("//*[@id='checkoutForm-fieldset-purchaseorder']/fieldset"))
					&& driver
							.findElement(
									By.xpath("//*[@id='checkoutForm-fieldset-purchaseorder']/fieldset"))
							.isDisplayed();
			System.out.println("b is :" + b);

			if (Common
					.isElementExist(
							driver,
							By.xpath("//*[@id='checkoutForm-fieldset-purchaseorder']/fieldset"))
					&& driver
							.findElement(
									By.xpath("//*[@id='checkoutForm-fieldset-purchaseorder']/fieldset"))
							.isDisplayed()) {
				b2bPage.paymentPage_purchaseNum.sendKeys("123456");				
				b2bPage.paymentPage_purchaseDate.click();

				Calendar CD = Calendar.getInstance();
				String DD = CD.get(Calendar.DATE) + "";

				driver.findElement(
						By.xpath("//*[@id='ui-datepicker-div']//a[text()='"
								+ DD + "']")).click();
	
			}
			System.out.println("payment paied");

			b2bPage.paymentPage_FirstName.clear();
			b2bPage.paymentPage_FirstName.sendKeys("testtesttest");
			b2bPage.paymentPage_LastName.clear();
			b2bPage.paymentPage_LastName.sendKeys("testtesttest");

			/*
			 * if(Store.toLowerCase().equals("us")){
			 * fillB2BShippingInfo("test","test"
			 * ,"Georgia","1535 Broadway","New York"
			 * ,"New York","10036-4077","2129450100"); }else
			 * if(Store.toLowerCase().equals("au")){
			 * fillB2BShippingInfo("test","test"
			 * ,"adobe_global","62 Streeton Dr",
			 * "RIVETT","Australian Capital Territory","2611","2123981900"); }
			 */
			if (b2bPage.paymentPage_addressLine1.isEnabled()) {
				b2bPage.paymentPage_addressLine1.clear();
				if (Store.toLowerCase().equals("us")) {
					b2bPage.paymentPage_addressLine1.sendKeys("1535 Broadway");
				} else if (Store.toLowerCase().equals("au")) {
					b2bPage.paymentPage_addressLine1.sendKeys("1535 Broadway");
				}

			}
			if (b2bPage.paymentPage_cityOrSuburb.isEnabled()) {
				b2bPage.paymentPage_cityOrSuburb.clear();
				if (Store.toLowerCase().equals("us")) {
					b2bPage.paymentPage_cityOrSuburb.sendKeys("New York");
				} else if (Store.toLowerCase().equals("au")) {
					b2bPage.paymentPage_cityOrSuburb.sendKeys("RIVETT");
				}
			}
			if (b2bPage.paymentPage_addressState.isEnabled()) {
				b2bPage.paymentPage_addressState.click();
				if (Store.toLowerCase().equals("us")) {
					driver.findElement(
							By.xpath(".//*[@id='address.region']/option[contains(.,'New York')]"))
							.click();
				} else if (Store.toLowerCase().equals("au")) {
					driver.findElement(
							By.xpath(".//*[@id='address.region']/option[contains(.,'Australian Capital Territory')]"))
							.click();
				}
			}
			if (b2bPage.paymentPage_addressPostcode.isEnabled()) {
				b2bPage.paymentPage_addressPostcode.clear();
				if (Store.toLowerCase().equals("us")) {
					b2bPage.paymentPage_addressPostcode.sendKeys("10036-4077");
				} else if (Store.toLowerCase().equals("au")) {
					b2bPage.paymentPage_addressPostcode.sendKeys("2611");
				}
			}
			b2bPage.paymentPage_Phone.clear();
			b2bPage.paymentPage_Phone.sendKeys("1234567890");
			try {
				Thread.sleep(6000);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		b2bPage.paymentPage_ContinueToPlaceOrder.click();
		(new WebDriverWait(driver, 500)).until(ExpectedConditions
				.visibilityOf(b2bPage.placeOrderPage_PlaceOrder));
	}
	
	public void changeChromeDefDownFolder(String downloadFilepath) throws IOException {
		driver.close();
		
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(cap);
		
		driver.manage().timeouts().pageLoadTimeout(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}
	
	
}
