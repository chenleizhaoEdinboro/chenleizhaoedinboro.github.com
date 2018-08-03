package TestScript.B2B;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA21286Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	private String cardName = "Card_21286";
	private String ContentBox_Data = "Congratulations! Your cart value has exceeded {5}, you are going to enjoy a {8} months credit card financing offer with an interest rate {9}. Monthly payment is as low as {10}! Please see {11} for more information.";
	public String titleName;
	public String CountryCurrency;
	public String CurrencySign;
	public String Minimum_Price;
	public String Maximum_Price;
	public String InterestRate = "500";

	public NA21286Test(String store ,String countryCurrency ,String currencySign) {
		this.Store = store;
		this.testName = "NA-21286";
		this.CountryCurrency = countryCurrency;
		this.CurrencySign = currencySign;
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "payment", "p2", "b2b"})
	public void NA21286(ITestContext ctx) {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			//+=+=+=+=+=+=Get the Lower limit and upper Limit from products price+=+=+=+=+=+=//
			driver.get(testData.B2B.getHomePageUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
			
			B2BCommon.addProduct(driver, b2bPage, testData.B2B.getDefaultMTMPN1());
			
			Dailylog.logInfoDB(6,"Added product no. : "+testData.B2B.getDefaultMTMPN1(),Store,testName);
			
			String Txt = b2bPage.cartPage_TotalPrice.getText();
			Dailylog.logInfoDB(6,"Price of item on Web page : " + Txt,Store,testName);
			
			// removing currency sign from Price
			String[] Price_modulous = Txt.split("\\"+CurrencySign);		
			Dailylog.logInfoDB(6,"after removing"+CurrencySign+"  :" + Price_modulous[1],Store,testName);
			
			String refinedPrice = Price_modulous[1].replaceAll(",","");
			Dailylog.logInfoDB(6,"Refined Price :"+refinedPrice,Store,testName);
			
			double Price_string_to_double = Double.parseDouble(refinedPrice);
			double MinPrice_string_to_double = Price_string_to_double+1;
			double MaxPrice_string_to_double = Price_string_to_double*3;
			Minimum_Price = Double.toString(MinPrice_string_to_double);
			Maximum_Price = Double.toString(MaxPrice_string_to_double);
			Dailylog.logInfoDB(0,"Minimum_Price : " + Minimum_Price,Store,testName);
			Dailylog.logInfoDB(0,"Maximum_Price : " + Maximum_Price,Store,testName);
			// +=+=+=+=+=Step~1 : Go to HMC --> Nemo --> Payment --> Payment
			// Type Customize+=+=+=+=+=+=//
			// --> Payment Message, right click create new payment message

			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);

			hmcPage.Home_Nemo.click();
			hmcPage.Home_payment.click();
			Common.javascriptClick(driver, hmcPage.Home_paymentCustomize);
			Dailylog.logInfoDB(1,"Clicked on Payment Customization",Store,testName);
			
			Common.rightClick(driver, hmcPage.NemoPayment_NemoPaymentMessage);
			Common.waitElementClickable(driver,
					hmcPage.NemoPayment_CreatePaymentMessage, 20);
			hmcPage.NemoPayment_CreatePaymentMessage.click();
			Dailylog.logInfoDB(1,"Clicked on create payment message",Store,testName);
			// +=+=+=+=Step~2 : Adding Name,Code,Payment_Type,configuration &
			// channel+=+=+=+=+=+=//
			// Under Description tab, enter :-
			// (1)Name : Card 1
			hmcPage.PaymentMessage_DescInputName.clear();
			hmcPage.PaymentMessage_DescInputName.sendKeys(cardName);
			// (2) Code : card_1
			hmcPage.PaymentMessage_DescInputCode.clear();
			hmcPage.PaymentMessage_DescInputCode.sendKeys(cardName);
			// (3) Choose payment type: Card Payment
			hmcPage.PaymentMessage_DescpaymentType.click();
			hmcPage.PaymentMessage_DescCardPaymentOption.click();
			Dailylog.logInfoDB(2,"Clicked on card Payment option",Store,testName);
			// (4)Choose configuration level (Global -> payment level, specific
			// -> unit level) : SPECIFIC
			hmcPage.PaymentMessage_DescConfigLevel.click();
			hmcPage.PaymentMessage_DescSpecificConfig.click();
			// (5) Choose Channel (applies to B2C or B2B or ALL) : ALL
			hmcPage.PaymentMessage_DescChannel.click();
			hmcPage.PaymentMessage_DescAllChannel.click();
			Dailylog.logInfoDB(2,"Selected channel as ALL",Store,testName);
			// (6) Set active to Yes
			hmcPage.PaymentMessage_DescActiveYes.click();
			// +=+=+=+=+=+=+=Step~3: Adding Threshold country currency and
			// Amount+=+=+=+=+=+=+=+=//
			// Under Message tab :-
			hmcPage.PaymentMessage_Messagetab.click();
			// Add Maximum Threshold
			ThresholdTotal(hmcPage.PaymentMessage_MesgThresholdMaxTable, 1,
					Maximum_Price);
			// Add Minimum Threshold
			ThresholdTotal(hmcPage.PaymentMessage_MesgThresholdMinTable, 2,
					Minimum_Price);
			// Priority
			hmcPage.PaymentMessage_MesgPriority.clear();
			hmcPage.PaymentMessage_MesgPriority.sendKeys("100");
			// Tier
			hmcPage.PaymentMessage_MesgTier.clear();
			hmcPage.PaymentMessage_MesgTier.sendKeys("9");
			// Interest Rate
			hmcPage.PaymentMessage_MesgInterestRate.clear();
			hmcPage.PaymentMessage_MesgInterestRate.sendKeys(InterestRate);
			// Terms&Condition
			hmcPage.PaymentMessage_MesgTermsAndCond.clear();
			hmcPage.PaymentMessage_MesgTermsAndCond.sendKeys("http://cn.bing.com/");
			// ContentBox
			hmcPage.PaymentMessage_MesgContentBox.clear();
			hmcPage.PaymentMessage_MesgContentBox.sendKeys(ContentBox_Data);
			Common.sleep(2000);
			// +=+=+=+=+=+=+=Step~4 : Add B2C unit+=+=+=+=+=+=+=+=+=//
			// Under B2C Unit Tab :-
			hmcPage.PaymentMessage_B2BUnitTab.click();
			Dailylog.logInfoDB(4,"B2B unit tab is clicked",Store,testName);
			
			Common.rightClick(driver, hmcPage.PaymentMessage_B2CUnitTableHead);
			hmcPage.PaymentMessage_AddB2BUnit.click();
			Dailylog.logInfoDB(4,"clicked Add B2B unit",Store,testName);
			Common.sleep(4000);
			switchToWindow(1);
			Dailylog.logInfoDB(4,"Switched to window 1",Store,testName);
			// input unit id
			hmcPage.PaymentMessage_IdCodeInputBox_B2B.clear();
			hmcPage.PaymentMessage_IdCodeInputBox_B2B.sendKeys(testData.B2B.getB2BUnit());
			hmcPage.PaymentMessage_Search.click();
			Dailylog.logInfoDB(4,"Clicked on Search button",Store,testName);
			driver.findElement(
					By.xpath("(//div[contains(@id,'" + testData.B2B.getB2BUnit()
							+ "')]/div)[1]")).click();
			hmcPage.PaymentMessage_Use.click();
			switchToWindow(0);
			Dailylog.logInfoDB(4,"Switched back to main window",Store,testName);
			Common.sleep(4000);
			hmcPage.HMC_Save.click();
			Common.sleep(5000);

			// Set Disable payment message roll back to No
			HMCCommon.searchB2BUnit(hmcPage, testData);
			hmcPage.B2BUnit_siteAttribute.click();
			hmcPage.SiteAttribute_DisablePaymentMsgFallback_No.click();
			hmcPage.HMC_Save.click();
			Common.sleep(5000);
			
			// +=+=+=+=+=+=+=Step~5 : Go to B2C -->Add an item+=+=+=+=+=+=+=//
			driver.get(testData.B2B.getHomePageUrl() + "/cart");
			Common.sleep(2500);
			titleName = driver.getTitle();
			Dailylog.logInfoDB(5,titleName,Store,testName);
			ComparePriceAndAddToCart();
			// +=+=+=+=+=+=+=+=+Step~7 : Add item(Minimum_Price<item_price<Maximum_Price) to
			// cart+=+=+=+=+=+=+=+=+=+=+=+=//
			Common.sleep(4000);
			b2bPage.cartPage_Quantity.clear();
			b2bPage.cartPage_Quantity.sendKeys("2");
			b2bPage.cartPage_QuantityUpdate.click();
			Dailylog.logInfoDB(8,"New Quantity has been updated",Store,testName);
			Common.sleep(3000);
			ComparePriceAndAddToCart();
			// +=+=+=+=+=+=+=+=+Step~8 : Add item(item_price > Maximum_Price) to
			// cart+=+=+=+=+=+=+=+=+=+=+=+=//
			b2bPage.cartPage_Quantity.clear();
			b2bPage.cartPage_Quantity.sendKeys("5");
			b2bPage.cartPage_QuantityUpdate.click();
			Dailylog.logInfoDB(8,"New Quantity has been updated",Store,testName);
			Common.sleep(3000);
			ComparePriceAndAddToCart();
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}


	// +=+=+=+=+=+=+Step~9 : Roll back by deleting step 1-4 payment
	// message+=+=+=+=+=+=+=//
	@AfterTest
	public void rollback() throws InterruptedException, MalformedURLException {
		Dailylog.logInfoDB(9,"rollback",Store,testName); // roll back
		SetupBrowser();
		try {
		driver.get(testData.HMC.getHomePageUrl());
		hmcPage = new HMCPage(driver);
		HMCCommon.Login(hmcPage, testData);
		Dailylog.logInfoDB(9,"Logged in into HMC",Store,testName);
		hmcPage.Home_Nemo.click();
		hmcPage.Home_payment.click();
		hmcPage.Home_paymentCustomize.click();
		hmcPage.NemoPayment_NemoPaymentMessage.click();
		Dailylog.logInfoDB(9,"clicked on Payment Message",Store,testName);
		Common.sleep(3000);
		hmcPage.PaymentMessage_PaymentTypeDropDown.click();
		hmcPage.PaymentMessage_DescCardPaymentOption.click();
		hmcPage.PaymentMessage_Search.click();
		Common.sleep(2000);
		List<WebElement> CountOfCard = driver.findElements(By
				.xpath("//td[@class='sorted']/div/div[contains(@id,'"
						+ cardName + "')]"));
		Dailylog.logInfoDB(9,"Total no of Card present : " + CountOfCard.size(),Store,testName);
		for (int i = 1; i <= CountOfCard.size(); i++) {
			RemoveCardName();
		}
		}catch(Throwable e) {
			
		}finally
		{
			driver.quit();
		}
	}
	//-----------------used methods----------------------------//

	// +=+=+=+=+=+=+=+=+=Switching Window+=+=+=+=+=+=+=+=+=+=//
	private void switchToWindow(int windowNo) {
		Common.sleep(2000);
		ArrayList<String> windows = new ArrayList<String>(
				driver.getWindowHandles());
		driver.switchTo().window(windows.get(windowNo));
	}

	// +=+=+=+=+=+=+=+=Adding Threshold currency and
	// amount+=+=+=+=+=+=+=+=+=+=//

	public void ThresholdTotal(WebElement TableAddress, int n, String Amount)
	{
		// Right click on Threshold table head
		Common.rightClick(driver, TableAddress);
		Common.sleep(2000);
		Common.mouseHover(driver, hmcPage.PaymentMessage_MesgCreateNew);
		hmcPage.PaymentMessage_MesgPromotionPriceRow.click();
		Dailylog.logInfoDB(3,"Clicked on Promotoion Price Row",Store,testName);
		Common.sleep(2000);
		// selecting country currency
		driver.findElement(
				By.xpath("(//select[contains(@id,'CreateItemListEntry')])[" + n
						+ "]")).click();
		driver.findElement(
				By.xpath("(//select[contains(@id,'CreateItemListEntry')])[" + n
						+ "]/option[contains(.,'"
						+ CountryCurrency + "')]")).click();

		Dailylog.logInfoDB(9,"Selected currency of Country",Store,testName);
		// Inputting Amount
		driver.findElement(
				By.xpath("(//input[contains(@id,'CreateItemListEntry')])[" + n
						+ "]")).clear();
		driver.findElement(
				By.xpath("(//input[contains(@id,'CreateItemListEntry')])[" + n
						+ "]")).sendKeys(Amount);
		Dailylog.logInfoDB(9,"Provided Max/Min amount for Threshold",Store,testName);
		Common.sleep(2000);
	}

	// +=+=+=+=+=+=+=+=Remove Card Name from List+=+=+=+=+=+=+=+=+=//
	private void RemoveCardName() {
		WebElement ListCardName = driver.findElement(By
				.xpath("(//td[@class='sorted']/div/div[contains(@id,'"
						+ cardName + "')])[last()]"));
		ListCardName.click();
		Common.mouseHover(driver, ListCardName);
		Common.rightClick(driver, ListCardName);
		hmcPage.PaymentMessage_RemoveCardName.click();
		// accept alert
		Alert alert = driver.switchTo().alert();
		alert.accept();
		Dailylog.logInfoDB(9,"Created card was removed successfully",Store,testName);
	}

	// +=+=+=+=+=+=+=+=Comparing Price and Adding it to cart+=+=+=+=+=+=+=+=//
	public void ComparePriceAndAddToCart()
			throws InterruptedException {
		String Txt = b2bPage.cartPage_TotalPrice.getText();
		Dailylog.logInfoDB(6,"Price of item on Web page : " + Txt,Store,testName);
		// removing currency sign from Price
		String[] Price_modulous = Txt.split("\\"+CurrencySign);		
		Dailylog.logInfoDB(6,"after removing"+CurrencySign+"  :" + Price_modulous[1],Store,testName);
		String refinedPrice = Price_modulous[1].replaceAll(",","");
		Dailylog.logInfoDB(6,"Refined Price :"+refinedPrice,Store,testName);
		double refined_Price = Double.parseDouble(refinedPrice);
		Dailylog.logInfoDB(6,"Final Price in double :"+refined_Price,Store,testName);
		if (refined_Price < Double.parseDouble(Minimum_Price)) {
			Dailylog.logInfoDB(6,"Price is less than "+Minimum_Price+".",Store,testName);			
			Assert.assertFalse(Common.isElementExist(driver,By.xpath("//div/ul/li[contains(.,'Congratulations! Your cart value has exceeded')]")));			
		} else if (refined_Price > Double.parseDouble(Minimum_Price) && refined_Price < Double.parseDouble(Maximum_Price)) {
			Dailylog.logInfoDB(6,"Price is greater than "+Minimum_Price+". and Price is less than "+Maximum_Price+".",Store,testName);
			String Mesg_Promotion = driver.findElement(By.xpath("//div/ul/li[contains(.,'Congratulations! Your cart value has exceeded')]")).getText();
			Dailylog.logInfoDB(6,"Promotional Message is : "+Mesg_Promotion,Store,testName);			
			Assert.assertTrue(Common.isElementExist(driver,By.xpath("//div/ul/li[contains(.,'Congratulations! Your cart value has exceeded')]")));
		} else {
			Dailylog.logInfoDB(6,"Price is greater than "+Maximum_Price+".",Store,testName);
			Assert.assertFalse(Common.isElementExist(driver,By.xpath("//div/ul/li[contains(.,'Congratulations! Your cart value has exceeded')]")));			
		}
	}


}
