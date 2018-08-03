package TestScript.B2C;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA20136Test extends SuperTestClass{
	private HMCPage hmcPage;
	private B2CPage b2cPage;
	private Actions act;

	public NA20136Test(String store) {
		this.Store = store;
		this.testName = "NA-20136";
	}
	@Test(priority=0,alwaysRun = true, groups = {"commercegroup", "payment", "p1", "b2c", "compatibility"})
	public void NA20136(ITestContext ctx) throws Exception {
		try{
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			act = new Actions(driver);
			
			//step:1
		  Dailylog.logInfoDB(1, "Go to Nemo --> Installment, "
			 		+ "right click and create installments for a card type", Store, testName);
		  deleteCreateCardType(hmcPage,driver,"MONTHSBR"); //first: Go to Nemo-->Deletes the associated data 
            //Step:2
           Dailylog.logInfoDB(2, "Enter number of months, sequence number and comments "
            		+ "such as 'Amex 3 months', 'Visa 6 months', etc.", Store, testName);
           int[] number = {3,6,9,12};
           createCardType(hmcPage,driver,number);
			//Step:3
           Dailylog.logInfoDB(3, "Go to Nemo --> Credit Card Installment Setting,"
            		+ " right click and create credit card installment setting", Store, testName);
           deleteCreateCreditCardSettings(hmcPage, driver, "MONTHSBR"); //Deletes the associated data
           act.sendKeys(Keys.PAGE_DOWN).perform();
           Common.sleep(3000);
           Common.scrollToElement(driver, hmcPage.Nemo_CreditCardInstallmentSetting);
           Common.rightClick(driver, hmcPage.Nemo_CreditCardInstallmentSetting);
           hmcPage.Nemo_CreditCardInstallmentSetting_Create.click();
           //Step:4
           Dailylog.logInfoDB(4, "Choose Credit Card type, disable display of monthly installment price on payment page or not,"
           		+ " right click and add installment options set up previously", Store, testName);
           
           String[] CardTypeCreate = {"MONTHSBRVISA","MONTHSBRAMEX"};
           createCreditCardSettings(hmcPage,driver,CardTypeCreate);
           //Step:5
           Dailylog.logInfoDB(5, "Go to B2C unit --> "
           		+ "Site Attribute,Add credit card/Amex payment type 'paymentbyinstallment' "
           		+ "controls whether enable installment function on this site or not", Store, testName);
		   HMCCommon.searchB2CUnit(hmcPage, testData);
		   hmcPage.B2CUnit_FirstSearchResultItem.click();
		   hmcPage.B2CUnit_SiteAttributeTab.click();
		   hmcPage.B2CUnit_SiteAttributeTab_PaymentByInstallmentYes.click();
		   //step:6
		   Dailylog.logInfoDB(6, "Under Site Attribute, search 'creditcardinstallment',"
		   		+ " add previously set-up credit card installment settings", Store, testName);
		   deleteB2CUnitSA_CreditCardInstallment(hmcPage, driver, "MONTHSBR"); //Delete data
		   addB2CUnitSA_CreditCardInstallment(hmcPage, driver, "MONTHSBR"); //add data
		   
		   //step:7
		   Dailylog.logInfoDB(7, "Go to Checkout --> Payment page", Store, testName);
		   Common.NavigateToUrl(driver, Browser, testData.B2C.getloginPageUrl());
		   B2CCommon.handleGateKeeper(b2cPage, testData);
		   B2CCommon.login(b2cPage, "lisong2@lenovo.com", testData.B2C.getLoginPassword());
		   Dailylog.logInfo("productNO:"+testData.B2C.getDefaultMTMPN());
		   //testData.B2C.getDefaultMTMPN()=null
		   Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl()+"/cart");
		   B2CCommon.clearTheCart(driver, b2cPage, testData);
		   B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());
		   b2cPage.Cart_CheckoutButton.click();
		   
		   fillShippingInfoBR(b2cPage, driver, "AutoFirstName", "AutoLastName", "Line1",
					"building", "Line2", "district",
					"townCity", "Acre", "99999999",
					"222", "84511773521", "lisong2@lenovo.com");
		   if(Common.checkElementDisplays(driver, driver.findElement(By.id("checkoutForm-shippingContinueButton")), 10))
		     driver.findElement(By.id("checkoutForm-shippingContinueButton")).click();
		   //Step:8
		   Dailylog.logInfoDB(8, "Choose credit card payment", Store, testName);
		   Common.sleep(5000);
		   Common.javascriptClick(driver, b2cPage.Payment_CreditCardRadioButton);
		   b2cPage.PageDriver.switchTo().frame(
					b2cPage.PageDriver.findElement(By.id("creditcardiframe0")));
		   //Step:9
		   Dailylog.logInfoDB(9, "choose Card Type :Visa;Don't click continue;Installment:6 months", Store, testName);
		   paymentInfoAsData(b2cPage, "Visa", 
		    		"4222222222222", "12", "20",
		    		"222", "test", "PaymentTypeSelection_6");
		 //Step:11
		   Dailylog.logInfoDB(11, "change to Card Type :Amextype card info as data,not click continue;Installment:3 months", Store, testName);
		   b2cPage.PageDriver.switchTo().frame(
					b2cPage.PageDriver.findElement(By.id("creditcardiframe0")));
		   paymentInfoAsData(b2cPage, "American Express", 
		    		"378282246310005", "12", "20",
		    		"005", "test", "PaymentTypeSelection_3");
		 //Step:12
		   Dailylog.logInfoDB(11, "change to Card Type :Amextype card info as data, click continue;Installment:3 months", Store, testName);
		   b2cPage.PageDriver.switchTo().frame(
					b2cPage.PageDriver.findElement(By.id("creditcardiframe0")));
		   paymentInfoAsData(b2cPage, "American Express", 
		    		"378282246310005", "12", "20",
		    		"005", "test", "PaymentTypeSelection_12"); 
		 //Step:10
		   Dailylog.logInfoDB(10, "choose Card Type :Visa;Don't click continue;Installment:9 months", Store, testName);
		   b2cPage.PageDriver.switchTo().frame(
					b2cPage.PageDriver.findElement(By.id("creditcardiframe0")));
		   paymentInfoAsData(b2cPage, "Visa", 
		    		"4111111111111111", "12", "20",
		    		"222", "test", "PaymentTypeSelection_9");
		   if(Common.checkElementExists(driver, driver.findElement(By.id("add-payment-method-continue")), 10))
			     driver.findElement(By.id("add-payment-method-continue")).click();
		   //Step:13
		   Dailylog.logInfoDB(13, "On summary page, check Terms & Conditions, place order", Store, testName);
		   Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
		   String orderNumber = "";
		   if(Common.checkElementExists(driver, driver.findElement(By.xpath("//div[@class='checkout-confirm-orderNumbers']/table/tbody/tr[2]/td[2]")), 10)){
			   orderNumber = driver.findElement(By.xpath("//div[@class='checkout-confirm-orderNumbers']/table/tbody/tr[2]/td[2]")).getText();
		   }
		   Dailylog.logInfoDB(1, "Order Number is: " + orderNumber, this.Store, this.testName);
		   
		   //step:14  
		   Dailylog.logInfoDB(14, "Check order xml in hmc:Order --> Orders, open this order then switch to Administration tab ", Store, testName);
		   Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
		   if(!Common.isElementExist(driver, By.id("Toolbar/ImageToolbarAction[closesession]_img")))
			   HMCCommon.Login(hmcPage, testData);
		   Common.sleep(3000);
		   Common.javascriptClick(driver, hmcPage.Home_Order);
		   Common.sleep(3000);
		   Common.javascriptClick(driver,  hmcPage.Home_Order_Orders);
		   hmcPage.Home_Order_OrderID.clear();
		   hmcPage.Home_Order_OrderID.sendKeys(orderNumber);
		   Common.javascriptClick(driver, hmcPage.Home_Order_OrderSearch);
		   Common.sleep(3000);
		   if(Common.isElementExist(driver, By.xpath("//img[contains(@id,'"+orderNumber+"')]")))
			   driver.findElement(By.xpath("//img[contains(@id,'"+orderNumber+"')]")).click();
		   hmcPage.Home_Order_OrderAdmin.click();
		   //Step:15 <payment_terms>C012</payment_terms>
		   Dailylog.logInfoDB(15, "In order XML(xmlContentShow ), there will be "
		   		+ "<payment_terms>C0XX</payment_terms> to indicate installment terms. "
		   		+ "For example: C006 -- 6 months, C009 -- 9 months", Store, testName);
		   Common.sleep(6000);
		   StringBuffer strbuf = new StringBuffer();
		   if(Common.isElementExist(driver, By.id("Content/TextAreaEditor[in Content/Attribute[Order.xmlContentShow]]_textarea"), 60))
		       strbuf = strbuf.append(driver.findElement(By.id("Content/TextAreaEditor[in Content/Attribute[Order.xmlContentShow]]_textarea")).getText());
		   Dailylog.logInfo("Step:15-->"+strbuf);
		   if(strbuf.indexOf("<payment_terms>C009</payment_terms>") == -1){
			   Dailylog.logInfo("In order XML(xmlContentShow ), not show '<payment_terms>C0XX</payment_terms>'");
			   Assert.fail("In order XML(xmlContentShow ), not show '<payment_terms>C0XX</payment_terms>'");
		   }
		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}
	public void createCardType(HMCPage hmcPage,WebDriver driver,int[] number){
		 Actions act = new Actions(driver);
		 act.sendKeys(Keys.PAGE_DOWN).perform();
		 Common.sleep(3000);
		 try {
			Common.scrollToElement(driver, hmcPage.Nemo_Installments);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 Common.rightClick(driver, hmcPage.Nemo_Installments); //right click;then create card type
		 hmcPage.NemoInstallments_CreateInstallments.click();
		 for(int i = 0; i< number.length; i++){
	    	 if(number[i] == 3 || number[i] == 12){
	    		 hmcPage.NemoInstallments_AttributeCode.clear();
	    		 hmcPage.NemoInstallments_AttributeCode.sendKeys("MONTHSBRAMEX"+number[i]);
	    		 hmcPage.NemoInstallments_AttributeNumber.clear();
	    		 hmcPage.NemoInstallments_AttributeNumber.sendKeys(Integer.toString(number[i]));
	    		 hmcPage.NemoInstallments_AttributeSerialNumber.clear();
	    		 hmcPage.NemoInstallments_AttributeSerialNumber.sendKeys(Integer.toString(i));
	    		 hmcPage.NemoInstallments_AttributeInstallComments.clear();
	    		 hmcPage.NemoInstallments_AttributeInstallComments.sendKeys("Amex "+number[i]+" months");
	    		 hmcPage.Nemo_InstallmentsCreate.click();
	    		 hmcPage.B2BUnit_Save.click();
	    		 act.sendKeys(Keys.PAGE_DOWN).perform();
	    		 Common.sleep(3000);
	    		 Common.rightClick(driver, hmcPage.Nemo_Installments);
	    		 Common.javascriptClick(driver, hmcPage.NemoInstallments_CreateInstallments);
	    	 }else if(number[i] == 6 || number[i] == 9){
	    		 hmcPage.NemoInstallments_AttributeCode.clear();
	    		 hmcPage.NemoInstallments_AttributeCode.sendKeys("MONTHSBRVISA"+number[i]);
	    		 hmcPage.NemoInstallments_AttributeNumber.clear();
	    		 hmcPage.NemoInstallments_AttributeNumber.sendKeys(Integer.toString(number[i]));
	    		 hmcPage.NemoInstallments_AttributeSerialNumber.clear();
	    		 hmcPage.NemoInstallments_AttributeSerialNumber.sendKeys(Integer.toString(i));
	    		 hmcPage.NemoInstallments_AttributeInstallComments.clear();
	    		 hmcPage.NemoInstallments_AttributeInstallComments.sendKeys("Visa "+number[i]+" months"); 
	    		 hmcPage.Nemo_InstallmentsCreate.click();
	    		 hmcPage.B2BUnit_Save.click();
	    		 act.sendKeys(Keys.PAGE_DOWN).perform();
	    		 Common.sleep(3000);
	    		 Common.rightClick(driver, hmcPage.Nemo_Installments); 
	    		 Common.javascriptClick(driver, hmcPage.NemoInstallments_CreateInstallments);
	    	 }
		 }
	}
    public void deleteCreateCardType(HMCPage hmcPage, WebDriver driver, String code){
    	Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
    	Common.sleep(3000);
    	HMCCommon.Login(hmcPage, testData);
    	hmcPage.Home_Nemo.click();
    	hmcPage.Nemo_Installments.click();
    	hmcPage.NemoInstallments_Code.clear();
    	hmcPage.NemoInstallments_Code.sendKeys(code);
    	hmcPage.NemoInstallments_SearchButton.click();
    	if(Common.isElementExist(driver, By.xpath("//tbody/tr/td[contains(text(),'The search was finished. No results were found.')]"))){
    		Dailylog.logInfo("The search was finished. No results were found.");
    	}else{
    		if(Common.isElementExist(driver, By.id("Content/McSearchListConfigurable[Installments]_code_sort")))
        		Common.rightClick(driver, driver.findElement(By.id("Content/McSearchListConfigurable[Installments]_code_sort")));
        	driver.findElement(By.id("Content/McSearchListConfigurable[Installments]_!select_visible_true_label")).click();
        	if(Common.checkElementExists(driver, hmcPage.NemoINstallments_DeleteAllResult, 10))
        	hmcPage.NemoINstallments_DeleteAllResult.click();
        	driver.switchTo().alert().accept();	
    	}
    }
    public void createCreditCardSettings(HMCPage hmcPage,WebDriver driver,String[] CardType){
    	for(int i = 0; i < CardType.length; i++){
    		if(CardType[i].equals("MONTHSBRVISA")){
    			hmcPage.NemoInstallments_AttributeCode.clear();
    			hmcPage.NemoInstallments_AttributeCode.sendKeys(CardType[i]);
    			Select CardTypeSelect = new Select(hmcPage.Nemo_CreditCardInstallmentSetting_CardType);
    			CardTypeSelect.selectByVisibleText("Visa");
    			hmcPage.Nemo_CreditCardInstallmentSetting_DisableShowPriceYes.click();
    		    Common.rightClick(driver, hmcPage.Nemo_CreditCardInstallmentSetting_Code);
    		    hmcPage.Nemo_CreditCardInstallmentSetting_AddInstallments.click();
    		    Common.switchToWindow(driver, 1);
    		    hmcPage.CreditCardInstallmentSetting_InstallmentsCode.clear();
    		    hmcPage.CreditCardInstallmentSetting_InstallmentsCode.sendKeys(CardType[i]);
    		    hmcPage.Installments_SearchButton.click();
    		    if(Common.checkElementExists(driver, driver.findElement(By.xpath("//div/a[contains(text(),'code')]")), 10)){
    		    	Common.rightClick(driver, driver.findElement(By.xpath("//div/a[contains(text(),'code')]")));
    		    	driver.findElement(By.xpath("//td[contains(text(),'Select all')]")).click();
    		    }
    		    hmcPage.Installments_Use.click();
    		    Common.switchToWindow(driver, 0);
    		    hmcPage.Nemo_InstallmentsCreate.click();
    		    act.sendKeys(Keys.PAGE_DOWN).perform();
    		    Common.sleep(3000);
    		    Common.rightClick(driver, hmcPage.Nemo_CreditCardInstallmentSetting);
    	        hmcPage.Nemo_CreditCardInstallmentSetting_Create.click();
    		}else{
    			hmcPage.NemoInstallments_AttributeCode.clear();
    			hmcPage.NemoInstallments_AttributeCode.sendKeys(CardType[i]);
    			Select CardTypeSelect = new Select(hmcPage.Nemo_CreditCardInstallmentSetting_CardType);
    			CardTypeSelect.selectByVisibleText("American Express");
    			hmcPage.Nemo_CreditCardInstallmentSetting_DisableShowPriceNo.click();
    		    Common.rightClick(driver, hmcPage.Nemo_CreditCardInstallmentSetting_Code);
    		    hmcPage.Nemo_CreditCardInstallmentSetting_AddInstallments.click();
    		    Common.switchToWindow(driver, 1);
    		    hmcPage.CreditCardInstallmentSetting_InstallmentsCode.clear();
    		    hmcPage.CreditCardInstallmentSetting_InstallmentsCode.sendKeys(CardType[i]);
    		    hmcPage.Installments_SearchButton.click();
    		    if(Common.checkElementExists(driver, driver.findElement(By.xpath("//div/a[contains(text(),'code')]")), 10)){
    		    	Common.rightClick(driver, driver.findElement(By.xpath("//div/a[contains(text(),'code')]")));
    		    	driver.findElement(By.xpath("//td[contains(text(),'Select all')]")).click();
    		    }
    		    hmcPage.Installments_Use.click();
    		    Common.switchToWindow(driver, 0);
    		    hmcPage.Nemo_InstallmentsCreate.click();
    		    //Common.rightClick(driver, hmcPage.Nemo_CreditCardInstallmentSetting);
    	        //hmcPage.Nemo_CreditCardInstallmentSetting_Create.click();
    		}
    	}
    }
    public void deleteCreateCreditCardSettings(HMCPage hmcPage,WebDriver driver,String serchUnit){
    	hmcPage.Nemo_CreditCardInstallmentSetting.click();
    	driver.findElement(By.id("Content/StringEditor[in Content/GenericCondition[CreditCardInstallmentSetting.code]]_input")).clear();
    	driver.findElement(By.id("Content/StringEditor[in Content/GenericCondition[CreditCardInstallmentSetting.code]]_input")).sendKeys(serchUnit);
    	driver.findElement(By.id("Content/NemoSearchConfigurable[CreditCardInstallmentSetting]_searchbutton")).click();
    	if(Common.isElementExist(driver, By.xpath("//tbody/tr/td[contains(text(),'The search was finished. No results were found.')]"))){
    		Dailylog.logInfo("The search was finished. No results were found.");
    	}else{
    		if(Common.isElementExist(driver, By.id("Content/McSearchListConfigurable[CreditCardInstallmentSetting]_code_sort")))
        		Common.rightClick(driver, driver.findElement(By.id("Content/McSearchListConfigurable[CreditCardInstallmentSetting]_code_sort")));
        	driver.findElement(By.id("Content/McSearchListConfigurable[CreditCardInstallmentSetting]_!select_visible_true_label")).click();
        	if(Common.checkElementExists(driver, driver.findElement(By.id("Content/McSearchListConfigurable[CreditCardInstallmentSetting][delete]_img")), 10))
        	driver.findElement(By.id("Content/McSearchListConfigurable[CreditCardInstallmentSetting][delete]_img")).click();
        	driver.switchTo().alert().accept();
    	}
    }
    public void deleteB2CUnitSA_CreditCardInstallment(HMCPage hmcPage, WebDriver driver, String strDelete){
    	ArrayList<WebElement> listCodeWebElement = (ArrayList<WebElement>) driver.findElements(By.xpath("//table[@title='creditCardinstallments']/tbody/tr/td/div/table/tbody/tr/td/div/table/tbody/tr/td[3]/div/div"));
    	Dailylog.logInfo("listCodeWebElement arr size:"+listCodeWebElement.size());
    	String [] deleteData=new String[4];
    	for(int j = 0; j< listCodeWebElement.size(); j++){
    		if(listCodeWebElement.get(j).getText().contains(strDelete)){
    			deleteData[j] = listCodeWebElement.get(j).getText();
    		}
    		Dailylog.logInfo("listCodeContext:"+listCodeWebElement.get(j).getText());
    	}
    	for(int i = 0; i< deleteData.length; i++){
    		if(deleteData[i] != null && !(deleteData.equals(""))){
    			WebElement CodeName = driver.findElement(By.xpath("//table[@title='creditCardinstallments']/tbody/tr/td/div/table/tbody/tr/td/div/table/tbody/tr/td[3]/div/div[contains(@id,'"+deleteData[i]+"')]"));
    			Common.rightClick(driver, CodeName);
    			driver.findElement(By.xpath("//div/table/tbody/tr/td[contains(text(),'Remove')]")).click();
    			driver.switchTo().alert().accept();
    			Common.sleep(6000);
    		}
    	}
    }
    public void addB2CUnitSA_CreditCardInstallment(HMCPage hmcPage, WebDriver driver, String strADD){
	   WebElement Code = driver.findElement(By.xpath("//thead/tr/th[3]/div[contains(text(),'Code')]"));
	   try {
		Common.scrollToElement(driver, Code);
	   } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	   }
	   Common.rightClick(driver, Code);
	   driver.findElement(By.xpath("//td[contains(text(),'Add Credit Card Installment Setting')]")).click();
	   Common.switchToWindow(driver, 1);
	   driver.findElement(By.xpath("//input[contains(@id,'CreditCardInstallmentSetting.code')]")).clear();
	   driver.findElement(By.xpath("//input[contains(@id,'CreditCardInstallmentSetting.code')]")).sendKeys(strADD);
	   driver.findElement(By.xpath("//span[contains(text(),'Search')]")).click();
	   Common.sleep(3000);
	   WebElement NextPageCode = driver.findElement(By.xpath("//a[contains(text(),'Code')]"));
	   Common.rightClick(driver, NextPageCode);
	   driver.findElement(By.xpath("//td[contains(text(),'Select all')]")).click();
	   driver.findElement(By.xpath("//span[contains(text(),'Use')]")).click();
	   Common.switchToWindow(driver, 0);
	   //hmcPage.Common_SaveButton.click();
	   driver.findElement(By.xpath("//div/u[contains(text(),'S')]")).click();
   }
    public  void fillShippingInfoBR(B2CPage b2cPage, WebDriver driver, String firstName,
			String lastName, String addressline1, String building, String Line2,
			String district, String townCity, String state,String postCode, String phone, String consumerTaxNumber,
			String mail) {
		if (Common.checkElementDisplays(b2cPage.PageDriver,
				b2cPage.Shipping_FirstNameTextBox, 5)) {
			b2cPage.Shipping_FirstNameTextBox.clear();
			b2cPage.Shipping_FirstNameTextBox.sendKeys(firstName);
			
			b2cPage.Shipping_LastNameTextBox.clear();
			b2cPage.Shipping_LastNameTextBox.sendKeys(lastName);
			
			b2cPage.Shipping_AddressLine1TextBox.clear();
			b2cPage.Shipping_AddressLine1TextBox.sendKeys(addressline1);
			
			driver.findElement(By.xpath(".//*[@id='building']")).clear();
			driver.findElement(By.xpath(".//*[@id='building']")).sendKeys(building);
			
			driver.findElement(By.xpath(".//*[@id='line2']")).clear();
			driver.findElement(By.xpath(".//*[@id='line2']")).sendKeys(Line2);
			
			driver.findElement(By.xpath(".//*[@id='district']")).clear();
			driver.findElement(By.xpath(".//*[@id='district']")).sendKeys(district);
			
			b2cPage.Shipping_CityTextBox.clear();
			b2cPage.Shipping_CityTextBox.sendKeys(townCity);
			
			Select stateDropdown = new Select(b2cPage.Shipping_StateDropdown);
			stateDropdown.selectByVisibleText(state);
			
			b2cPage.Shipping_PostCodeTextBox.clear();
		    b2cPage.Shipping_PostCodeTextBox.sendKeys(postCode);
		    
		    b2cPage.Shipping_ContactNumTextBox.clear();
			b2cPage.Shipping_ContactNumTextBox.sendKeys(phone);
		    
			b2cPage.Shipping_consumerTaxNumber.clear();
			b2cPage.Shipping_consumerTaxNumber.sendKeys(consumerTaxNumber);
			
			b2cPage.Shipping_EmailTextBox.clear();
			b2cPage.Shipping_EmailTextBox.sendKeys(mail);
		}
	}
    public void paymentInfoAsData(B2CPage b2cPage, String cardType, 
    		String cardNubber, String cardMonth, String cardYear,
    		String cardCode, String cardName, String xpathid)
    {
		Select dropdown = new Select(b2cPage.Payment_CardTypeDropdown);
		dropdown.selectByVisibleText(cardType);
		b2cPage.Payment_CardNumberTextBox.clear();
		b2cPage.Payment_CardNumberTextBox.sendKeys(cardNubber);
		b2cPage.Payment_CardMonthTextBox.clear();
		b2cPage.Payment_CardMonthTextBox.sendKeys(cardMonth);
		b2cPage.Payment_CardYearTextBox.clear();
		b2cPage.Payment_CardYearTextBox.sendKeys(cardYear);
		b2cPage.Payment_SecurityCodeTextBox.clear();
		b2cPage.Payment_SecurityCodeTextBox.sendKeys(cardCode);
		b2cPage.PageDriver.switchTo().defaultContent();
    	b2cPage.Payment_CardNameTextBox.clear();
    	b2cPage.Payment_CardNameTextBox.sendKeys(cardName);
    	driver.findElement(By.id(xpathid)).click();
    }
   
}
