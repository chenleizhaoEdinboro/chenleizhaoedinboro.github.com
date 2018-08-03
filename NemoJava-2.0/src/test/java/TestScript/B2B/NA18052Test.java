package TestScript.B2B;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
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

public class NA18052Test extends SuperTestClass {
	private B2BPage b2bPage;
	private HMCPage hmcPage;
	String ProductNo;
	String Rep_Value = "2900718028";
	String QuoteStatus;
	String QuotePartNumber;
	String QuoteId;
	String QuoteQuantity;
	String QuoteTotalPrice;
	String Unit_One;
	String Unit_Two;

	public NA18052Test(String store) {
		this.Store = store;
		this.testName = "NA-18052";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2b"})
	public void NA18052(ITestContext ctx) {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			//Step~1 : Go to HMC and enable request quote
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(1, "Logged in into HMC", Store, testName);
			Common.sleep(5000);
			HMCCommon.searchB2BUnit(hmcPage, testData);
			Common.sleep(4500);
			hmcPage.baseStore_administration.click();
			hmcPage.B2BAdministration_isQuoteAvailable.click();
			hmcPage.B2BUnit_Save.click();			
			Common.sleep(2500);
			hmcPage.B2BUnit_siteAttribute.click();
			hmcPage.B2BUnit_isQuoteAvailable.click();
			hmcPage.B2BUnit_Save.click();
			hmcPage.HMC_Logout.click();
			//step~2
			driver.get(testData.B2B.getLoginUrl());
			Unit_One = testData.B2B.getB2BUnit();
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
			//step~3
			AddToCartB2B(b2bPage.Laptop_SelectedLaptop1,b2bPage.Laptop_ViewDetail1,3);
			Common.sleep(2000);
			String FirstCartPartNo = b2bPage.CartPage_OnlyPartnum.getText();
			Dailylog.logInfoDB(3, "part no of first product on cart page : "+FirstCartPartNo, Store, testName);
			//step~4
			RequestQuote();
			//step~5
			String FirstQuoteStatus = QuoteStatus;
			Dailylog.logInfoDB(5, "First Quote Status : "+FirstQuoteStatus, Store, testName);
			Assert.assertTrue(FirstQuoteStatus.contains("SAVED"));

			String FirstQuotePartNumber = QuotePartNumber;
			Dailylog.logInfoDB(5, "First Quote PartNumber : "+FirstQuotePartNumber, Store, testName);
			Assert.assertTrue(FirstQuotePartNumber.equals(FirstCartPartNo));

			String FirstQuoteId = QuoteId;
			Dailylog.logInfoDB(5, "First Quote Id : "+FirstQuoteId, Store, testName);

			String FirstQuoteQuantity = QuoteQuantity;
			Dailylog.logInfoDB(5, "First Quote Quantity : "+FirstQuoteQuantity, Store, testName);

			String FirstQuoteTotalPrice = QuoteTotalPrice;
			Dailylog.logInfoDB(5, "First Quote TotalPrice : "+FirstQuoteTotalPrice, Store, testName);
			//step~6
			AddToCartB2B(b2bPage.Laptop_SelectedLaptop2,b2bPage.Laptop_ViewDetail2,6);
			Common.sleep(2000);
			String SecondCartPartNo = b2bPage.CartPage_OnlyPartnum.getText();
			Dailylog.logInfoDB(3, "part no of Second product on cart page : "+SecondCartPartNo, Store, testName);
			//step~7
			b2bPage.lenovoCheckout.click();
			Common.sleep(4000);
			Assert.assertTrue(driver.getTitle().contains("Shipping"));
			//step~8
			B2BCommon.fillB2BShippingInfo(driver, b2bPage, Store, testData.B2B.getFirstName(), testData.B2B.getLastName(), testData.B2B.getCompany(), testData.B2B.getAddressLine1(), testData.B2B.getAddressCity(), testData.B2B.getAddressState(), testData.B2B.getPostCode(), testData.B2B.getPhoneNum());
			
			Common.javascriptClick(driver, b2bPage.shippingPage_ContinueToPayment);
			//b2bPage.shippingPage_ContinueToPayment.click();
			if(Common.checkElementExists(driver, b2bPage.shippingPage_validateFromOk, 5)){
				b2bPage.shippingPage_validateFromOk.click();
			}
			Common.sleep(4000);			
			Assert.assertTrue(driver.getTitle().contains("Payment"));
			//step~9 : select payment method
			Actions actions = new Actions(driver);
			By Card = By.xpath("//input[@id='PaymentTypeSelection_CARD']");
			By DirectDeposite = By.xpath(".//*[@id='PaymentTypeSelection_BANKDEPOSIT']");			
			By Purchase_Order = By.xpath(".//*[@id='PaymentTypeSelection_PURCHASEORDER']");
			if (Common.isElementExist(driver, DirectDeposite)) {
				actions.moveToElement(b2bPage.DirectDeposit).click().perform();
				Dailylog.logInfoDB(8, "payment method Direct deposite selected", Store, testName);			
			}else if (Common.isElementExist(driver, Card)) {
				actions.moveToElement(b2bPage.paymentPage_creditCardRadio).click().perform();		        
				driver.switchTo().frame(b2bPage.creditCardFrame);
				b2bPage.paymentPage_Visa.click();
				b2bPage.paymentPage_CardNumber.sendKeys("4111111111111111");
				b2bPage.paymentPage_ExpiryMonth.sendKeys("06");
				b2bPage.paymentPage_ExpiryYear.sendKeys("20");
				b2bPage.paymentPage_SecurityCode.sendKeys("132");
				driver.switchTo().defaultContent();
				b2bPage.paymentPage_NameonCard.sendKeys("LIXE");
			} else if (Common.isElementExist(driver, Purchase_Order)){
				actions.moveToElement(b2bPage.paymentPage_PurchaseOrder).click().perform();
				b2bPage.paymentPage_purchaseNum.sendKeys("1234567890");
				b2bPage.paymentPage_purchaseDate.click();
				b2bPage.paymentPage_purchaseDate.sendKeys(Keys.ENTER);
			}

			b2bPage.paymentPage_FirstName.clear();			
			b2bPage.paymentPage_FirstName.sendKeys(testData.B2B.getFirstName());
			b2bPage.paymentPage_LastName.clear();
			b2bPage.paymentPage_LastName.sendKeys(testData.B2B.getLastName());
			Common.sleep(2000);
			if (b2bPage.paymentPage_addressLine1.getAttribute("editable").contains("true")) {
				b2bPage.paymentPage_addressLine1.clear();
				b2bPage.paymentPage_addressLine1.sendKeys(testData.B2B.getAddressLine1());
				System.out.println("Input address!");
			}

			if (b2bPage.paymentPage_cityOrSuburb.getAttribute("editable").contains("true")) {
				b2bPage.paymentPage_cityOrSuburb.clear();
				b2bPage.paymentPage_cityOrSuburb.sendKeys(testData.B2B.getAddressCity());
			}
			if (b2bPage.paymentPage_addressState.getAttribute("editable").contains("true")) {
				Select stateDropdown = new Select(b2bPage.paymentPage_addressState);
				stateDropdown.selectByVisibleText(testData.B2B.getAddressState());	
			}

			if (b2bPage.paymentPage_addressPostcode.getAttribute("editable").contains("true")) {
				b2bPage.paymentPage_addressPostcode.clear();
				b2bPage.paymentPage_addressPostcode.sendKeys(testData.B2B.getPostCode());
			}


			if (b2bPage.paymentPage_Phone.getAttribute("editable").contains("true")) {
				b2bPage.paymentPage_Phone.clear();
				b2bPage.paymentPage_Phone.sendKeys(testData.B2B.getPhoneNum());
			}

			RequestQuote();
			//storing value from Quote page
			String SecondQuoteStatus = QuoteStatus;
			Dailylog.logInfoDB(9, "Second Quote Status : "+SecondQuoteStatus, Store, testName);
			Assert.assertTrue(SecondQuoteStatus.contains("SAVED"));

			String SecondQuotePartNumber = QuotePartNumber;
			Dailylog.logInfoDB(9, "Second Quote PartNumber : "+SecondQuotePartNumber, Store, testName);
			Assert.assertTrue(SecondQuotePartNumber.equals(SecondCartPartNo));

			String SecondQuoteId = QuoteId;
			Dailylog.logInfoDB(9, "Second Quote Id : "+SecondQuoteId, Store, testName);

			String SecondQuoteQuantity = QuoteQuantity;
			Dailylog.logInfoDB(9, "Second Quote Quantity : "+SecondQuoteQuantity, Store, testName);

			String SecondQuoteTotalPrice = QuoteTotalPrice;
			Dailylog.logInfoDB(9, "Second Quote TotalPrice : "+SecondQuoteTotalPrice, Store, testName);
			//step~10 : click on change soldTo
			b2bPage.B2BHomePage_WelcomeUnitName.click();
			b2bPage.B2BHomePage_CountryName.click();
			if(Common.isElementExist(driver, By.xpath("(//div/a[contains(@onclick,'ChangeStore')][@class='clickable'])[1]"))){
				Common.sleep(3000);
				b2bPage.B2BHomePage_SubUnitName.click();
				Dailylog.logInfoDB(10, "Clicked on Sub-unit.", Store, testName);
			}
			// accept alert
			Alert alert = driver.switchTo().alert();
			alert.accept();
			Assert.assertFalse(driver.getCurrentUrl().contains(testData.B2B.getB2BUnit()));
			Unit_Two = driver.getCurrentUrl().split("/")[7];
			//step~11
			AddToCartB2B(b2bPage.Laptop_SelectedLaptop1,b2bPage.Laptop_ViewDetail1,11);
			Common.sleep(2000);
			String ThirdCartPartNo = b2bPage.CartPage_OnlyPartnum.getText();
			Dailylog.logInfoDB(12, "part no of Third product on cart page : "+ThirdCartPartNo, Store, testName);
			//step~12
			RequestQuote();
			//step~13
			String ThirdQuoteStatus = QuoteStatus;
			Dailylog.logInfoDB(13, "Third Quote Status : "+ThirdQuoteStatus, Store, testName);
			Assert.assertTrue(ThirdQuoteStatus.contains("SAVED"));

			String ThirdQuotePartNumber = QuotePartNumber;
			Dailylog.logInfoDB(13, "Third Quote PartNumber : "+ThirdQuotePartNumber, Store, testName);
			Assert.assertTrue(ThirdQuotePartNumber.equals(ThirdCartPartNo));

			String ThirdQuoteId = QuoteId;
			Dailylog.logInfoDB(13, "Third Quote Id : "+ThirdQuoteId, Store, testName);

			String ThirdQuoteQuantity = QuoteQuantity;
			Dailylog.logInfoDB(13, "Third Quote Quantity : "+ThirdQuoteQuantity, Store, testName);

			String ThirdQuoteTotalPrice = QuoteTotalPrice;
			Dailylog.logInfoDB(13, "Third Quote TotalPrice : "+ThirdQuoteTotalPrice, Store, testName);
			//step~14 : Go to my account page and check history
			Common.sleep(4000);
			b2bPage.homepage_MyAccount.click();
			b2bPage.myAccountPage_ViewQuotehistory.click();
			//checking presence of All (3) QuoteID
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//a[contains(.,'"+FirstQuoteId+"')]")));
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//a[contains(.,'"+SecondQuoteId+"')]")));
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//a[contains(.,'"+ThirdQuoteId+"')]")));
			//verifying search criteria	
			Dailylog.logInfoDB(14, "Verifying Search Criteria", Store, testName);
			Validate_SearchCriteriaText("Quote ID");
			Validate_SearchCriteriaText("Store ID");
			Validate_SearchCriteriaText("Store Name");
			Validate_SearchCriteriaText("Part Number");
			Validate_SearchCriteriaText("Total Amount");
			Validate_SearchCriteriaText("Quote Name ");
			Validate_SearchCriteriaText("Quote Description ");
			//verify Column Name	
			Dailylog.logInfoDB(14, "Verifying Column Name", Store, testName);
			Validate_ColumnNameText("Quote ID");
			Validate_ColumnNameText("Quote Name");
			Validate_ColumnNameText("Quote Status");
			Validate_ColumnNameText("Store ID");
			Validate_ColumnNameText("Store Name");
			Validate_ColumnNameText("Part Number");
			Validate_ColumnNameText("Quantity");
			Validate_ColumnNameText("Total Price");
			Validate_ColumnNameText("Date Placed");
			Validate_ColumnNameText("Actions");
			//step~15
			driver.findElement(By.xpath("//a[contains(.,'View')][contains(@href,'"+FirstQuoteId+"')]")).click();
			Dailylog.logInfoDB(15, "Clicked on View Link of : "+FirstQuoteId, Store, testName);
			Assert.assertTrue(driver.getTitle().contains("Quote Details"));
			Assert.assertTrue(driver.getCurrentUrl().contains(Unit_One));
			//step~16 : click on convert to order
			Common.javascriptClick(driver, b2bPage.cartPage_ConvertToOrderBtn);
			//b2bPage.cartPage_ConvertToOrderBtn.click();
			Dailylog.logInfoDB(16, "Clicked On Convert To Order", Store, testName);
			Assert.assertTrue(driver.getTitle().contains("Shipping Information"));
			String PartNumberOnShipping = driver.findElement(By.xpath("(//p[@class='checkout-shoppingCart-previewSubtitle'])[1]")).getText();
			PartNumberOnShipping = PartNumberOnShipping.split(":  ")[1];
			Assert.assertEquals(FirstCartPartNo, PartNumberOnShipping);
			//step~17
			b2bPage.homepage_MyAccount.click();
			b2bPage.myAccountPage_ViewQuotehistory.click();
			driver.findElement(By.xpath("//a[contains(.,'View')][contains(@href,'"+SecondQuoteId+"')]")).click();
			Dailylog.logInfoDB(17, "Clicked on View Link of : "+SecondQuoteId, Store, testName);
			Assert.assertTrue(driver.getTitle().contains("Quote Details"));
			//step~18
			Common.javascriptClick(driver, b2bPage.cartPage_ConvertToOrderBtn);
			//b2bPage.cartPage_ConvertToOrderBtn.click();
			Common.sleep(4000);
			Dailylog.logInfoDB(18, "Clicked On Convert To Order", Store, testName);
			Common.sleep(4000);
			//Shipping Page
			
			
			//second quote shouldn't go to shipping page
			
			/*Assert.assertTrue(driver.getTitle().contains("Shipping Information"));
			Dailylog.logInfoDB(18, "On Shipping Information page", Store, testName);
			String PartNumberOnShippingPage = driver.findElement(By.xpath("(//p[@class='checkout-shoppingCart-previewSubtitle'])[1]")).getText();
			PartNumberOnShippingPage = PartNumberOnShippingPage.split(":  ")[1];
			System.out.println("PartNumberOnShipping :"+PartNumberOnShippingPage);
			System.out.println("SecondCartPartNo :"+SecondCartPartNo);
			Assert.assertEquals(SecondCartPartNo, PartNumberOnShippingPage);
			String QuantityOnShippingPage = driver.findElement(By.xpath("//div[@class='checkout-shoppingCart-previewInfo']/p[3]")).getText();
			QuantityOnShippingPage = QuantityOnShippingPage.split(": ")[1];
			System.out.println("QuantityOnPayment :"+QuantityOnShippingPage);
			System.out.println("SecondQuoteQuantity :"+SecondQuoteQuantity);
			Assert.assertEquals(SecondQuoteQuantity, QuantityOnShippingPage);			
			String PriceOnShippingPage = driver.findElement(By.xpath("//dd[contains(@class,'orderSummary-pricingTotal')]")).getText();
			PriceOnShippingPage = PriceOnShippingPage.replace(" ", "");		
			System.out.println("PriceOnPayment :"+PriceOnShippingPage);
			System.out.println("SecondQuoteTotalPrice :"+SecondQuoteTotalPrice);
			Assert.assertEquals(SecondQuoteTotalPrice, PriceOnShippingPage);
			B2BCommon.fillB2BShippingInfo(driver, b2bPage, Store, testData.B2B.getFirstName(), testData.B2B.getLastName(), testData.B2B.getCompany(), testData.B2B.getAddressLine1(), testData.B2B.getAddressCity(), testData.B2B.getAddressState(), testData.B2B.getPostCode(), testData.B2B.getPhoneNum());
			b2bPage.shippingPage_ContinueToPayment.click();
			if(Common.checkElementExists(driver, b2bPage.shippingPage_validateFromOk, 5)){
				b2bPage.shippingPage_validateFromOk.click();
			}
			Common.sleep(4000);	*/
			//Payment Page
			Assert.assertTrue(driver.getTitle().contains("Payment"));
			Dailylog.logInfoDB(18, "On Payment Information page", Store, testName);
			String PartNumberOnPayment = driver.findElement(By.xpath("(//p[@class='checkout-shoppingCart-previewSubtitle'])[1]")).getText();
			PartNumberOnPayment = PartNumberOnPayment.split(":  ")[1];
			System.out.println("PartNumberOnPayment :"+PartNumberOnPayment);
			System.out.println("SecondCartPartNo :"+SecondCartPartNo);
			Assert.assertEquals(SecondCartPartNo, PartNumberOnPayment);			
			String QuantityOnPayment = driver.findElement(By.xpath("//div[@class='checkout-shoppingCart-previewInfo']/p[3]")).getText();
			QuantityOnPayment = QuantityOnPayment.split(": ")[1];
			System.out.println("QuantityOnPayment :"+QuantityOnPayment);
			System.out.println("SecondQuoteQuantity :"+SecondQuoteQuantity);
			Assert.assertEquals(SecondQuoteQuantity, QuantityOnPayment);			
			String PriceOnPayment = driver.findElement(By.xpath("//dd[contains(@class,'orderSummary-pricingTotal')]")).getText();
			PriceOnPayment = PriceOnPayment.replace(" ", "");		
			System.out.println("PriceOnPayment :"+PriceOnPayment);
			System.out.println("SecondQuoteTotalPrice :"+SecondQuoteTotalPrice);
			Assert.assertEquals(SecondQuoteTotalPrice, PriceOnPayment);
			//step~19  
			b2bPage.homepage_MyAccount.click();
			b2bPage.myAccountPage_ViewQuotehistory.click();
			b2bPage.QuoteHistoryPage_SearchCriteria.click();
			b2bPage.QuoteHistoryPage_QuoteIDInSearch.click();
			Dailylog.logInfoDB(19, "Clicked On Quote ID in search criteria", Store, testName);
			Common.sendFieldValue(b2bPage.QuoteHistoryPage_SearchValue, FirstQuoteId);
			b2bPage.QuoteHistoryPage_SearchButton.click();
			String Result19 = driver.findElement(By.xpath("//td[@quote='QuoteID']/a")).getText();
			Assert.assertEquals(Result19, FirstQuoteId);
			int Total = driver.findElements(By.xpath("//td[@quote='QuoteID']/a")).size();
			Assert.assertEquals(Total, 1);
			//step~20
			b2bPage.QuoteHistoryPage_SearchCriteria.click();
			b2bPage.QuoteHistoryPage_StoreIDInSearch.click();
			Dailylog.logInfoDB(20, "Clicked On Store ID in search criteria", Store, testName);
			Common.sendFieldValue(b2bPage.QuoteHistoryPage_SearchValue, Unit_One);
			b2bPage.QuoteHistoryPage_SearchButton.click();
			Dailylog.logInfoDB(20, "Clicked On Search button", Store, testName);
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//a[contains(.,'"+FirstQuoteId+"')]")));
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//a[contains(.,'"+SecondQuoteId+"')]")));
			Assert.assertFalse(Common.isElementExist(driver, By.xpath("//a[contains(.,'"+ThirdQuoteId+"')]")));
			//step~21
			b2bPage.QuoteHistoryPage_SearchCriteria.click();
			b2bPage.QuoteHistoryPage_PartNoInSearch.click();
			Dailylog.logInfoDB(21, "Clicked On Part Number in search criteria", Store, testName);
			Common.sendFieldValue(b2bPage.QuoteHistoryPage_SearchValue, FirstCartPartNo);
			b2bPage.QuoteHistoryPage_SearchButton.click();
			assert  driver.findElement(By.xpath("//td[@quote='QuoteID']/a[contains(text(),'"+FirstQuoteId+"')]")).isDisplayed();
			System.out.println(FirstQuoteId);
			
			//step~22
			b2bPage.QuoteHistoryPage_SearchCriteria.click();
			b2bPage.QuoteHistoryPage_StoreNameInSearchCriteria.click();
			Dailylog.logInfoDB(22, "Clicked On Store Name in search criteria", Store, testName);
			Common.sendFieldValue(b2bPage.QuoteHistoryPage_SearchValue, "le");			
			b2bPage.QuoteHistoryPage_SearchButton.click();
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//a[contains(.,'"+FirstQuoteId+"')]")),"First Quote is not present (Step :22)");
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//a[contains(.,'"+SecondQuoteId+"')]")),"Second Quote is not present (Step :22)");
			//Assert.assertTrue(Common.isElementExist(driver, By.xpath("//a[contains(.,'"+ThirdQuoteId+"')]")),"Third Quote is not present (Step :22)");
			//step~23
			b2bPage.QuoteHistoryPage_SearchCriteria.click();
			b2bPage.QuoteHistoryPage_TotalAmntInSearch.click();
			b2bPage.QuoteHistoryPage_AmountOperator.click();
			b2bPage.QuoteHistoryPage_AmntOpeIsLess.click();	
			Dailylog.logInfoDB(23, "Selected Amount is less in search criteria", Store, testName);
			Common.sendFieldValue(b2bPage.QuoteHistoryPage_SearchValue, "10");
			b2bPage.QuoteHistoryPage_SearchButton.click();
			Assert.assertFalse(Common.isElementExist(driver, By.xpath("//a[contains(.,'"+FirstQuoteId+"')]")),"First Quote is present");
			Assert.assertFalse(Common.isElementExist(driver, By.xpath("//a[contains(.,'"+SecondQuoteId+"')]")),"Second Quote is present");
			Assert.assertFalse(Common.isElementExist(driver, By.xpath("//a[contains(.,'"+ThirdQuoteId+"')]")),"Third Quote is present");
			//step~24
			b2bPage.QuoteHistoryPage_SearchCriteria.click();
			b2bPage.QuoteHistoryPage_AmountOperator.click();
			b2bPage.QuoteHistoryPage_AmntOpeIsEqual.click();	
			Dailylog.logInfoDB(24, "Selected Amount is equal in search criteria", Store, testName);
			String EnteredAmnt = SecondQuoteTotalPrice.split("\\$")[1];
			EnteredAmnt = EnteredAmnt.replace(",", "");
			Dailylog.logInfoDB(24, "Entered Amount : "+EnteredAmnt, Store, testName);
			Common.sendFieldValue(b2bPage.QuoteHistoryPage_SearchValue, EnteredAmnt);
			b2bPage.QuoteHistoryPage_SearchButton.click();
			Assert.assertTrue((Common.isElementExist(driver, By.xpath("//a[contains(.,'"+SecondQuoteId+"')]"))),"Second Quote is not present (Step :24)");
			//step~25
			b2bPage.QuoteHistoryPage_SearchCriteria.click();
			b2bPage.QuoteHistoryPage_AmountOperator.click();
			b2bPage.QuoteHistoryPage_AmntOpeIsGreater.click();	
			Dailylog.logInfoDB(25, "Selected Amount is greater in search criteria", Store, testName);
			Common.sendFieldValue(b2bPage.QuoteHistoryPage_SearchValue, "10");
			b2bPage.QuoteHistoryPage_SearchButton.click();
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//a[contains(.,'"+FirstQuoteId+"')]")),"First Quote is not present (Step :25)");
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//a[contains(.,'"+SecondQuoteId+"')]")),"Second Quote is not present (Step :25)");
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//a[contains(.,'"+ThirdQuoteId+"')]")),"Third Quote is not present (Step :25)");


		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}
	//validate options of search criteria and Column list
	private void Validate_SearchCriteriaText(String text){
		Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='searchCriteria']/option[text()='"+text+"']")).isDisplayed(),text+" is not present in search criteria");
	}
	//validate Column Name Text	
	private void Validate_ColumnNameText(String text){
		Assert.assertTrue(driver.findElement(By.xpath("//table[@class='data-table']//th[text()='"+text+"']")).isDisplayed(),text+" is not present in search criteria");
	}

	//=*=*=*=*=*=*=*Add A product to cart for B2B*=*=*=*=*=*=*=//
	private void AddToCartB2B(WebElement LaptopXpath,WebElement DetailXpath,int StepNo){
		Dailylog.logInfoDB(StepNo,"Adding a product to cart...",Store,testName);
		b2bPage.HomePage_CartIcon.click();
		Common.sleep(2500);
//		
//		b2bPage.HomePage_productsLink.click();
//		b2bPage.HomePage_Laptop.click();
//		Dailylog.logInfoDB(StepNo, "Clicked on Laptop link", Store, testName);
//
//		ProductNo = LaptopXpath.getText();
//		
//		Dailylog.logInfoDB(StepNo, "part no of selected laptop is :"+ProductNo, Store, testName);
//		if (Common.checkElementDisplays(driver, By.xpath(".//*[@id='addToCartForm"+ProductNo+"']/button"), 3)) {
//			driver.findElement(By.xpath(".//*[@id='addToCartForm"+ProductNo+"']/button")).click();
//			Common.sleep(3000);
//			driver.findElement(By.xpath("//div[@class='addtoCartCTA']/button[contains(@onclick,'"+ProductNo+"')]")).click();
//			Common.sleep(5000);
//			driver.findElement(By.xpath("//div[@class='addtoCartCTA']/button[contains(@onclick,'"+ProductNo+"')]/../a[2]")).click();
//			Common.sleep(2500);
//		}else {
//			DetailXpath.click();
//			Dailylog.logInfoDB(StepNo, "Clicked on View Detail", Store, testName);
//			Common.sleep(5000);
//			
//			if (Common.isElementExist(driver, By.xpath(".//div[@id='bundleAlert']//div[@class='modal-content']"))) {
//				if (driver.findElement(By.xpath(".//*[@id='b_marning_add_to_cart']")).isDisplayed()) {
//				b2bPage.ModalPopUp_AddToCart.click();
//				Dailylog.logInfoDB(StepNo, "Clicked on Add To Cart on Modal Pop up", Store, testName);
//				}
//			}
//			Common.sleep(3500);
//			if (Common.isElementExist(driver, By.xpath("//div/button[contains(@class,'add-to-cart')]"))) {				
//				b2bPage.PDP_AddToCart.click();
//				Dailylog.logInfoDB(StepNo, "Clicked on Add to Cart on PDP", Store, testName);
//			}
//			Common.sleep(8000);
//			//previously failed here 
//			if (Common.isElementExist(driver, By.xpath("//span[contains(.,'Add to cart')]"))) {
//				driver.findElement(By.xpath("//span[contains(.,'Add to cart')]")).click();
//				Dailylog.logInfoDB(StepNo, "Clicked on Add to Cart...", Store, testName);
//			}
//		
//			Common.sleep(5000);
//		}
		
		b2bPage.cartPage_quickOrder.clear();

		b2bPage.cartPage_quickOrder.sendKeys(testData.B2B.getDefaultMTMPN1());
		b2bPage.cartPage_addButton.click();
		
		Assert.assertTrue(driver.getTitle().contains("Cart"));
		Assert.assertTrue(b2bPage.cartPage_Quantity.isDisplayed(),"Item was not added to cart successfully");
	}
	//+=+=+=+=++=+=+=+=+=+=Request Quote Method+=+=+=+=+=+=+=+=+=+=//
	private void RequestQuote(){				
		Assert.assertTrue(b2bPage.cartPage_RequestQuoteBtn.isDisplayed(),"Request Quote button is not present.");
		b2bPage.cartPage_RequestQuoteBtn.click();
		Dailylog.logInfoDB(4, "Clicked on Request Quote button", Store, testName);
		Common.sleep(3000);
		Common.sendFieldValue(b2bPage.cartPage_RepIDBox, Rep_Value);
		b2bPage.cartPage_SubmitQuote.click();
		Common.sleep(2000);
		QuoteStatus = b2bPage.QuotePage_QuoteStatus.getText();					
		QuotePartNumber = b2bPage.QuotePage_PartNo.getText();					
		QuoteId = b2bPage.QuotePage_QuoteNo.getText();					
		QuoteQuantity = b2bPage.QuotePage_Quantity.getText();					
		QuoteTotalPrice = b2bPage.QuotePage_LinePrice.getText();					
		Dailylog.logInfoDB(4, "Captured information from Quote page", Store, testName);				
	}
	
}
