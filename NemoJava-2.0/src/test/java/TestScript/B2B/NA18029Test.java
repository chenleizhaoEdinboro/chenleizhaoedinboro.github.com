package TestScript.B2B;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.openqa.selenium.JavascriptExecutor;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA18029Test extends SuperTestClass {
	private B2BPage b2bPage;
	private String partNum_one;
	private String partNum_two;
	private String privateCartName_One = "Private_Cart_One"
			+ Common.getDateTimeString();
	private String privateCartName_Two = "Private_Cart_Two"
			+ Common.getDateTimeString();
	private String companyCartName_One = "Company_Cart_One"
			+ Common.getDateTimeString();
	private String companyCartName_Two = "Company_Cart_Two"
			+ Common.getDateTimeString();

	public NA18029Test(String store) {
		this.Store = store;
		this.testName = "NA-18029";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2b"})
	public void NA18029(ITestContext ctx) {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			partNum_one = testData.B2B.getDefaultMTMPN1();
			partNum_two = testData.B2B.getDefaultMTMPN1();
			
			Dailylog.logInfoDB(1, "Login to B2B website", Store, testName);
			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(),
					testData.B2B.getDefaultPassword());

			
			Dailylog.logInfoDB(2, "Add a product to cart", Store, testName);
			B2BCommon.addProduct(driver, b2bPage, partNum_one);

			
			Dailylog.logInfoDB(3, "Save Cart", Store, testName);
			b2bPage.cartPage_saveCartButton.click();

			Dailylog.logInfoDB(4, "Save Cart as Private Cart One : " +privateCartName_One , Store, testName);
			b2bPage.cartPage_companySaveCartButton.click();
			b2bPage.cartPage_SaveCart_cartNameField
					.sendKeys(privateCartName_One);
			b2bPage.cartPage_SaveCart_save.click();

			
			Dailylog.logInfoDB(5, "Add another product", Store, testName);
			B2BCommon.addProduct(driver, b2bPage, partNum_two);

			
			Dailylog.logInfoDB(6, "Save Cart ", Store, testName);
			((JavascriptExecutor) driver).executeScript("scroll(0,300)");
			b2bPage.cartPage_saveCartButton.click();

			
			Dailylog.logInfoDB(7, "Save Cart as Company Cart One : " + companyCartName_One, Store, testName);
			b2bPage.cartPage_SaveCart_cartNameField
					.sendKeys(companyCartName_One);
			b2bPage.cartPage_companySaveCartButton.click();
			b2bPage.cartPage_SaveCart_save.click();
			
			Dailylog.logInfoDB(8, "Verify saved cart page", Store, testName);
			Assert.assertTrue(Common.isElementExist(driver,
					By.xpath("//tr/th[contains(.,'Cart ID')]")));
			Assert.assertTrue(Common.isElementExist(driver,
					By.xpath("//tr/th[contains(.,'Cart Name')]")));
			Assert.assertTrue(Common.isElementExist(driver,
					By.xpath("//tr/th[contains(.,'Owner Name')]")));
			Assert.assertTrue(Common.isElementExist(driver,
					By.xpath("//tr/th[contains(.,'Store ID')]")));
			Assert.assertTrue(Common.isElementExist(driver,
					By.xpath("//tr/th[contains(.,'Store Name')]")));
			Assert.assertTrue(Common.isElementExist(driver,
					By.xpath("//tr/th[contains(.,'Date Placed')]")));
			Assert.assertTrue(Common.isElementExist(driver,
					By.xpath("//tr/th[contains(.,'Total')]")));
			Assert.assertTrue(Common.isElementExist(driver,
					By.xpath("//tr/th[contains(.,'Operation')]")));
			
			
			Dailylog.logInfoDB(9, "View Saved Carts", Store, testName);
			b2bPage.myAccount_link.click();
			b2bPage.myAccountPage_viewCartHistory.click();
			driver.findElement(
					By.xpath("//td/h3[contains(.,'" + privateCartName_One
							+ "')]/../../td[@data-title='viewCart']//div"))
					.click();
			Common.sleep(3000);
			
			Assert.assertTrue(Common.isElementExist(driver,
					By.xpath(".//input[@id='quantity3']")));
			

			driver.findElement(By.xpath(".//*[@id='bodywrapinner']/a[2]"))
					.click();

			driver.findElement(
					By.xpath("//td/h3[contains(.,'" + companyCartName_One
							+ "')]/../../td[@data-title='viewCart']//div"))
					.click();
			Common.sleep(2000);
			String totalPrice = b2bPage.EmailCart_sendSuccessMsg.getText();
			String getTotalPrice[] = totalPrice.split("\\$");
			String TotalPriceAsString = getTotalPrice[getTotalPrice.length - 1];
			String price = TotalPriceAsString.replaceAll(",", "");
		
			Dailylog.logInfoDB(9, "Verify Amount", Store, testName);
			Assert.assertTrue(Common.isElementExist(driver,
					By.xpath(".//input[@id='quantity3']")));

			b2bPage.homepage_Signout.click();
			Common.sleep(3000);
			driver.manage().deleteAllCookies();

			
			Dailylog.logInfoDB(10, "Login with Other B2B Account", Store, testName);
			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuilderId(),
					testData.B2B.getDefaultPassword());
			Common.sleep(4000);

			
			Dailylog.logInfoDB(12, " View saved company Cart", Store, testName);
			b2bPage.myAccount_link.click();
			b2bPage.myAccountPage_viewCartHistory.click();
			
			Assert.assertTrue(companyCartName_One + " cart history is not shown",Common.isElementExist(
					driver,
					By.xpath("//td/h3[contains(.,'" + companyCartName_One
							+ "')]")));

			// Validating that disabled Edit and Delete links are present
	
			Assert.assertTrue(Common.isElementExist(
					driver,
					By.xpath("//td/h3[contains(.,'"
							+ companyCartName_One
							+ "')]/../../td/div/div[@class='disableLinkText'][contains(.,'Edit')]")));
	
			Assert.assertTrue(Common.isElementExist(
					driver,
					By.xpath("//td/h3[contains(.,'"
							+ companyCartName_One
							+ "')]/../../td/div/div[@class='disableLinkText'][contains(.,'Delete')]")));
		
		
			Dailylog.logInfoDB(13, "Add a product to cart", Store, testName);
			B2BCommon.addProduct(driver, b2bPage, partNum_one);

			
			Dailylog.logInfoDB(14, "Save Cart as Private Cart One : "+companyCartName_Two, Store, testName);

			b2bPage.cartPage_saveCartButton.click();
			b2bPage.cartPage_companySaveCartButton.click();
			b2bPage.cartPage_SaveCart_cartNameField
			.sendKeys(companyCartName_Two);
			b2bPage.cartPage_SaveCart_save.click();

			
			Dailylog.logInfoDB(16, "Add another product into cart", Store, testName);
			B2BCommon.addProduct(driver, b2bPage, partNum_two);

			
			Dailylog.logInfoDB(17, "Save Cart", Store, testName);
			b2bPage.cartPage_saveCartButton.click();

			
			Dailylog.logInfoDB(18, "Save Cart as Company Cart One", Store, testName);
			b2bPage.cartPage_companySaveCartButton.click();
			b2bPage.cartPage_SaveCart_cartNameField
			.sendKeys(privateCartName_Two);
			b2bPage.cartPage_SaveCart_save.click();


			Dailylog.logInfoDB(19, "Log off current account and login", Store, testName);
			b2bPage.homepage_Signout.click();
			Common.sleep(3000);
			driver.manage().deleteAllCookies();

			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(),
					testData.B2B.getDefaultPassword());
			Common.sleep(4000);

			
			Dailylog.logInfoDB(20, "View saved company Cart", Store, testName);
			b2bPage.myAccount_link.click();
			b2bPage.myAccountPage_viewCartHistory.click();
			Common.sleep(3000);

			// Check the saved carts on Cart history page //
			String companyCartOne_ID = driver.findElement(
					By.xpath("//td/h3[contains(.,'" + companyCartName_One
							+ "')]/../../td[@data-title='Cart ID']//a"))
					.getText();
			String companyCartTwo_ID = driver.findElement(
					By.xpath("//td/h3[contains(.,'" + companyCartName_Two
							+ "')]/../../td[@data-title='Cart ID']//a"))
					.getText();
			String privateCartOne_ID = driver.findElement(
					By.xpath("//td/h3[contains(.,'" + privateCartName_One
							+ "')]/../../td[@data-title='Cart ID']//a"))
					.getText();
			
			Assert.assertTrue(Common.isElementExist(
					driver,
					By.xpath("//td/h3[contains(.,'" + companyCartName_One
							+ "')]")));
			
			Assert.assertTrue(Common.isElementExist(
					driver,
					By.xpath("//td/h3[contains(.,'" + companyCartName_Two
							+ "')]")));
		
			Assert.assertTrue(Common.isElementExist(
					driver,
					By.xpath("//td/h3[contains(.,'" + privateCartName_One
							+ "')]")));
			
			Dailylog.logInfoDB(21, "Search with CartID", Store, testName);
			b2bPage.cartHistory_searchCartByDropDown.click();
			b2bPage.SavedCartPage_SearchWithCartID.click();
			b2bPage.SavedCartPage_Search_InputTxtBox.clear();
			b2bPage.SavedCartPage_Search_InputTxtBox
					.sendKeys(companyCartOne_ID);
			b2bPage.SavedCartPage_SearchButton.click();
			Common.sleep(2000);
			Assert.assertTrue(Common.isElementExist(
					driver,
					By.xpath("//td/h3[contains(.,'" + companyCartName_One
							+ "')]")));
			
			Dailylog.logInfoDB(22, "Search with Cart Name", Store, testName);
			b2bPage.cartHistory_searchCartByDropDown.click();
			b2bPage.SavedCartPage_SearchWithCartName.click();
			b2bPage.SavedCartPage_Search_InputTxtBox.clear();
			b2bPage.SavedCartPage_Search_InputTxtBox.sendKeys("Company");
			b2bPage.SavedCartPage_SearchButton.click();
			Common.sleep(2000);
		
			Assert.assertTrue(Common.isElementExist(
					driver,
					By.xpath("//td/h3[contains(.,'" + companyCartName_One
							+ "')]")));
			
			Assert.assertTrue(Common.isElementExist(
					driver,
					By.xpath("//td/h3[contains(.,'" + companyCartName_Two
							+ "')]")));
	
			Dailylog.logInfo("Step:23 Search with Store ID");
			
			String getStoreID[] = testData.B2B.getLoginUrl().split("/");
			String storeID = getStoreID[getStoreID.length - 2];
			b2bPage.cartHistory_searchCartByDropDown.click();
			b2bPage.SavedCartPage_SearchWithStoreID.click();
			b2bPage.SavedCartPage_Search_InputTxtBox.clear();
			b2bPage.SavedCartPage_Search_InputTxtBox.sendKeys(storeID);
			b2bPage.SavedCartPage_SearchButton.click();
			Common.sleep(2000);
			Assert.assertTrue(Common.isElementExist(
					driver,
					By.xpath("//td/h3[contains(.,'" + companyCartName_One
							+ "')]")));
			Assert.assertTrue(Common.isElementExist(
					driver,
					By.xpath("//td/h3[contains(.,'" + companyCartName_Two
							+ "')]")));
			Assert.assertTrue(Common.isElementExist(
					driver,
					By.xpath("//td/h3[contains(.,'" + privateCartName_One
							+ "')]")));
			Dailylog.logInfo("Step:24 Search with Store Name");
			
			String storeName = null;
			if (this.Store.equalsIgnoreCase("AU")) {
				storeName = "Adobe";
			} else if (this.Store.equalsIgnoreCase("US")) {
				storeName = "Barnes & Noble";
			} else if (this.Store.equalsIgnoreCase("JP")) {
				storeName = "日本オラクル";
			}
			b2bPage.cartHistory_searchCartByDropDown.click();
			b2bPage.SavedCartPage_SearchWithStoreName.click();
			b2bPage.SavedCartPage_Search_InputTxtBox.clear();
			b2bPage.SavedCartPage_Search_InputTxtBox.sendKeys(storeName);
			b2bPage.SavedCartPage_SearchButton.click();
			Common.sleep(2000);
			Assert.assertTrue(Common.isElementExist(
					driver,
					By.xpath("//td/h3[contains(.,'" + companyCartName_One
							+ "')]")));
			Assert.assertTrue(Common.isElementExist(
					driver,
					By.xpath("//td/h3[contains(.,'" + companyCartName_Two
							+ "')]")));
			Assert.assertTrue(Common.isElementExist(
					driver,
					By.xpath("//td/h3[contains(.,'" + privateCartName_One
							+ "')]")));

//			Dailylog.logInfoDB(25, "Search with Owner Name", Store, testName);
//			b2bPage.cartHistory_searchCartByDropDown.click();
//			b2bPage.SavedCartPage_SearchWithOwnerName.click();
//			b2bPage.SavedCartPage_Search_InputTxtBox.clear();
//
//			b2bPage.SavedCartPage_Search_InputTxtBox.sendKeys("John Snow");
//			b2bPage.SavedCartPage_SearchButton.click();
//			Common.sleep(2000);
//			Assert.assertTrue(Common.isElementExist(
//					driver,
//					By.xpath("//td/h3[contains(.,'" + companyCartName_One
//							+ "')]")));
//			Assert.assertTrue(Common.isElementExist(
//					driver,
//					By.xpath("//td/h3[contains(.,'" + privateCartName_One
//							+ "')]")));

			Dailylog.logInfoDB(26, "Search with Date Range", Store, testName);	
			b2bPage.cartHistory_searchCartByDropDown.click();
			b2bPage.SavedCartPage_SearchWithDateRange.click();
			Common.sleep(2000);
			b2bPage.SavedCartPage_StartDate.click();
			Common.sleep(2000);
			b2bPage.SavedCartPage_DateSelection_BeforeToday.click();
			Common.sleep(2000);
			b2bPage.SavedCartPage_EndDate.click();
			Common.sleep(2000);
			b2bPage.SavedCartPage_DateSelection_BeforeToday.click();
			Common.sleep(2000);
			b2bPage.SavedCartPage_SearchButton.click();
			Common.sleep(2000);
			Assert.assertFalse(Common.isElementExist(
					driver,
					By.xpath("//td/h3[contains(.,'" + companyCartName_One
							+ "')]")));
			Assert.assertFalse(Common.isElementExist(
					driver,
					By.xpath("//td/h3[contains(.,'" + companyCartName_Two
							+ "')]")));

			Dailylog.logInfoDB(27, "Search with Date Range", Store, testName);	
			b2bPage.cartHistory_searchCartByDropDown.click();
			b2bPage.SavedCartPage_SearchWithDateRange.click();
			Common.sleep(2000);
			b2bPage.SavedCartPage_StartDate.click();
			Common.sleep(2000);
			b2bPage.SavedCartPage_DateSelection_Today.click();
			Common.sleep(2000);
			b2bPage.SavedCartPage_EndDate.click();
			Common.sleep(2000);
			b2bPage.SavedCartPage_DateSelection_Today.click();
			Common.sleep(2000);
			b2bPage.SavedCartPage_SearchButton.click();
			Common.sleep(2000);
			Assert.assertTrue(Common.isElementExist(
					driver,
					By.xpath("//td/h3[contains(.,'" + companyCartName_One
							+ "')]")));
			Assert.assertTrue(Common.isElementExist(
					driver,
					By.xpath("//td/h3[contains(.,'" + companyCartName_Two
							+ "')]")));
			Assert.assertTrue(Common.isElementExist(
					driver,
					By.xpath("//td/h3[contains(.,'" + privateCartName_One
							+ "')]")));

			Dailylog.logInfoDB(28, "Search with Part Number", Store, testName);		
			Dailylog.logInfoDB(29, "Search with Total Amount (Less Than)", Store, testName);
			b2bPage.cartHistory_searchCartByDropDown.click();
			b2bPage.SavedCartPage_SearchWithTotalPriceRange.click();
			b2bPage.SavedCartPage_TotalRange_Criteria.click();
			b2bPage.SavedCartPage_TotalRange_LessThan.click();
			try{
			b2bPage.SavedCartPage_Search_InputTxtBox.clear();
			b2bPage.SavedCartPage_Search_InputTxtBox.sendKeys(price);}
			catch(Exception e){
				driver.findElement(By.xpath(".//*[@id='searchKeyWordTotal']")).clear();
				driver.findElement(By.xpath(".//*[@id='searchKeyWordTotal']")).sendKeys(price);
			}
			b2bPage.SavedCartPage_SearchButton.click();
		
			Dailylog.logInfoDB(30, "Search with Total Amount (More Than)", Store, testName);
			b2bPage.cartHistory_searchCartByDropDown.click();
			b2bPage.SavedCartPage_SearchWithTotalPriceRange.click();
			b2bPage.SavedCartPage_TotalRange_Criteria.click();
			b2bPage.SavedCartPage_TotalRange_MoreThan.click();
			try{
			b2bPage.SavedCartPage_Search_InputTxtBox.clear();
			b2bPage.SavedCartPage_Search_InputTxtBox.sendKeys(price);
			}
			catch(Exception e){
				driver.findElement(By.xpath(".//*[@id='searchKeyWordTotal']")).clear();
				driver.findElement(By.xpath(".//*[@id='searchKeyWordTotal']")).sendKeys(price);
			}
			
			b2bPage.SavedCartPage_SearchButton.click();

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}
