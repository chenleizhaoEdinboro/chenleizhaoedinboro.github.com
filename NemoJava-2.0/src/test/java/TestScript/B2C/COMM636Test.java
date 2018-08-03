package TestScript.B2C;

import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.FEPage;
import Pages.HMCPage;
import Pages.PartSalesPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class COMM636Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	String SubSeries;
	FEPage fePage;
	PartSalesPage PSPage;
	private String test_cb_1 = "test-cb-1";
	private String test_cb_2 = "test-cb-2";
	private String leading_item_1 = "20LDCTO1WWENUS0";
	private String sub_item_a = "81CU000TUS";
	private int m_sub_item_a;
	private String leading_item_2 = "81CU000RUS";
	private int n_leading_item_2;
	private String sub_item_b = "81CU000SUS";
	private int p_sub_item_b;
	private String sub_item_c = "4XD0K25029";;
	private int q_sub_item_c;
	private Actions act;
	private List<WebElement> editAmount;
	private double cart_saving_price;
	private String cronJob = "nemoReservedRemoveCronJob" ;


	public COMM636Test(String store){
		this.Store = store;
		this.testName = "COMM-636";
		
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void COMM636(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			PSPage = new PartSalesPage(driver);
			fePage = new FEPage(driver);
			act = new Actions(driver);
			
			//test-cb-1(20LDCTO1WWENUS0/20LFCTO1WWENUS0,81CU000TUS)CTO-CTO,MTM(1:1:1)
			//test-cb-2(81CU000RUS:1/81CU000SUS:2,4XD0K25029:3)MTM-MTM,ACCESSORY(1:2:3)
			//step~1,2
//			Dailylog.logInfoDB(2, "Check the inventory of item:", Store, testName);
//			driver.get(testData.HMC.getHomePageUrl());
//			HMCCommon.Login(hmcPage, testData);
//			m_sub_item_a = Integer.parseInt(getReservedStockLevel(sub_item_a));
//			n_leading_item_2 = Integer.parseInt(getReservedStockLevel(leading_item_2));
//			p_sub_item_b = Integer.parseInt(getReservedStockLevel(sub_item_b));
////			q_sub_item_c = Integer.parseInt(getReservedStockLevel(sub_item_c));
//			System.out.println("amount_sub_item_a:" + m_sub_item_a);
//			System.out.println("amount_leading_item_2:" + n_leading_item_2);
//			System.out.println("amount_sub_item_b:" + p_sub_item_b);
////			System.out.println("amount_sub_item_c:" + q_sub_item_c);
//			
//			//seup the value of release time (step~15)
//			//300 means 5 mins
//			setReleaseTime("300");
//			JavascriptExecutor js = (JavascriptExecutor)driver;
//			js.executeScript("window.open()");
//			Common.switchToWindow(driver, 1);
		
			//step~3,4,5
			Dailylog.logInfoDB(3, "Add a leading item" + leading_item_1 + "[CTO] to cart.", Store, testName);
			driver.get(testData.B2C.getloginPageUrl());
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
//			B2CCommon.clearTheCart(driver, b2cPage, testData);
			addCTOToCart(leading_item_1);
			
			Dailylog.logInfoDB(4, "Add a CB" + test_cb_1 + " to cart.", Store, testName);
			//add another CB to cart
			addCBToCart(test_cb_1);

			Dailylog.logInfoDB(5, "Add another bundle" + test_cb_2 + "to cart.", Store, testName);
			addCBToCart(test_cb_2);
			
			//step~7
			Dailylog.logInfoDB(7, "Add sub item" + sub_item_b + "[MTM] to cart.", Store, testName);
			B2CCommon.addPartNumberToCart(b2cPage, sub_item_b);
			
			//step~9
			Dailylog.logInfoDB(9, "Add sub item" + sub_item_c + "[MTM] to cart.", Store, testName);
			B2CCommon.addPartNumberToCart(b2cPage, sub_item_c);
			
			//step~6,8,10
			Common.switchToWindow(driver, 0);
			int m_sub_item_a1 = Integer.parseInt(getReservedStockLevel(sub_item_a));
			int n_leading_item_21 = Integer.parseInt(getReservedStockLevel(leading_item_2));
			int p_sub_item_b1 = Integer.parseInt(getReservedStockLevel(sub_item_b));
			int q_sub_item_c1 = Integer.parseInt(getReservedStockLevel(sub_item_c));
			System.out.println("amount_sub_item_a:" + m_sub_item_a1);
			System.out.println("amount_leading_item_2:" + n_leading_item_21);
			System.out.println("amount_sub_item_b:" + p_sub_item_b1);
			System.out.println("amount_sub_item_c:" + q_sub_item_c1);
			Assert.assertEquals("sub_item_a reserved amount is incorrect", m_sub_item_a + 1, m_sub_item_a1);
			Assert.assertEquals("sub_item_a reserved amount is incorrect", n_leading_item_2 + 1, n_leading_item_21);
			Assert.assertEquals("sub_item_a reserved amount is incorrect", p_sub_item_b + 2, p_sub_item_b1);
			Assert.assertEquals("sub_item_a reserved amount is incorrect", q_sub_item_c + 3, q_sub_item_c1);
			
			//step~17,18
			Dailylog.logInfoDB(17, "After 5 mins(the release time just setup), run cronjob.", Store, testName);
			Common.sleep(600000);
			hmcPage.System_Menu.click();
			hmcPage.System_CronJobs.click();
			hmcPage.CronJobs_OptionContainsforJobs.click();
			hmcPage.CronJob_jobName.sendKeys(cronJob);
			hmcPage.CronJob_searchButton.click();
			By JobXpath = By.xpath("//div[text()='" + cronJob + "']");
			if (!Common.isElementExist(driver, JobXpath)) {
				driver.findElement(By.xpath("//img[contains(@id,'next_page')]"))
						.click();
			}
			WebElement JobName = driver.findElement(JobXpath);
			Common.doubleClick(driver, JobName);
			hmcPage.CronJobs_RunAS.click();
			
			Common.switchToWindow(driver, 1);
			
				
			//add leading item to cart
			Dailylog.logInfoDB(6, "Add a sub item (item-a) to cart.", Store, testName);
			B2CCommon.addPartNumberToCart(b2cPage, leading_item_2);
			
			//step~7
			Dailylog.logInfoDB(7, "Check the display in Cart page", Store, testName);
			//1>will verify in step 5
			//2>Sub-component quantity can't be edited.
			By sub_quantity = By.xpath("//*[@class='child_entries']/div[@class='cart-item-addedItem-quantity']");
			System.out.println("step5.2");
			Assert.assertTrue("Sub-component quantity can be edited", Common.checkElementDisplays(driver, sub_quantity, 5));
			//3>"Remove button", "Save for later" button is shown
			System.out.println("step5.3");
			Assert.assertTrue(Common.checkElementDisplays(driver, fePage.removeLink, 5));
			WebElement saveForLater = driver.findElement(By.linkText("Save for Later"));
			System.out.println("step5.3.2");
			Assert.assertTrue(Common.checkElementDisplays(driver, saveForLater, 5));
			//4>
			//5>The separate sub item(item-a) can update quantity.
			System.out.println("step5.5");
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//input[@id='QuantityProduct_3']"), 3));
			//6>
			
			//step~8
			Dailylog.logInfoDB(8, "Click 'Edit' button of CB test-cb-2", Store, testName);
			List<WebElement> edit_test_cb_2 = driver.findElements(By.xpath("//li[@class='qa_Edit_link']//a"));
			edit_test_cb_2.get(1).click();

			//step~9
			Dailylog.logInfoDB(9, "Click 'NO' button", Store, testName);
			Common.javascriptClick(driver, b2cPage.cart_edit_no);
			
			//step~10
			Dailylog.logInfoDB(10, "Click 'YES' button", Store, testName);
			double cartSubTotalPricePre = GetPriceValue(b2cPage.ConvenienceBundle_SubTotal.getText());
			
			int preEditAmount = edit_test_cb_2.size();
			System.out.println("edit number is :" + preEditAmount);
			edit_test_cb_2.get(1).click();
			Common.javascriptClick(driver, b2cPage.cart_edit_yes);
			
			//step~11
			Dailylog.logInfoDB(11, "doesn't changed configuration add to cart again", Store, testName);
			if(Common.checkElementDisplays(driver, By.xpath("//*[@id='addToCartButtonTop']"), 5)){
				Common.scrollToElement(driver, b2cPage.Cart_saveCartBtn);
				Common.javascriptClick(driver, b2cPage.Cart_saveCartBtn);
			}else if(Common.checkElementDisplays(driver, By.xpath(".//*[@id='addToCartButton4Bundle']"), 5)){
				Common.javascriptClick(driver, b2cPage.ConvenienceBundle_Add);
				b2cPage.cto_AddToCartButton.click();
			}
			
			//step~12
			Dailylog.logInfoDB(12, "Check the display in Cart page.", Store, testName);
			double cartSubTotalPrice = GetPriceValue(b2cPage.ConvenienceBundle_SubTotal.getText());
			Assert.assertEquals(cartSubTotalPricePre, cartSubTotalPrice);
			verifyEditAmount(preEditAmount);
			
			//step~13
			Dailylog.logInfoDB(13, "Click 'Edit' button of CB test-cb-2", Store, testName);
			Common.javascriptClick(driver, editAmount.get(0));
			
			//step~14
			Dailylog.logInfoDB(14, "Click 'YES' button", Store, testName);
			Common.javascriptClick(driver, b2cPage.cart_edit_yes);
			
			//step~15,16
			Dailylog.logInfoDB(15, "changed configuration of CB ", Store, testName);
			Common.javascriptClick(driver, b2cPage.Product_AddToCartBtn);
			Common.javascriptClick(driver, b2cPage.PB_AccessoriesTab);
			String accessoryMsg = "sorry";
			String accessoryID = "";
			accessoryID = selectOptionPB(accessoryMsg, "Accessories");
			System.out.println("accessory ID is:" + accessoryID);
			Common.javascriptClick(driver, b2cPage.PDP_AddToCartButton1);
			verifyEditAmount(preEditAmount+2);
			WebElement changedPrice =  driver.findElement(By.xpath("//div[@data-p-code='" + accessoryID + "']/dd"));
			double cartSubTotalPriceAdd = GetPriceValue(b2cPage.ConvenienceBundle_SubTotal.getText());
			System.out.println(cartSubTotalPrice + GetPriceValue(changedPrice.getText()) + cart_saving_price);
			System.out.println(cartSubTotalPriceAdd);
			Assert.assertEquals(cartSubTotalPrice + GetPriceValue(changedPrice.getText()) + cart_saving_price, cartSubTotalPriceAdd);
			
			//step~17
			Dailylog.logInfoDB(17, "Click Checkout button", Store, testName);
			driver.findElement(By.xpath("//*[@id='lenovo-checkout-sold-out']")).click();
			Common.sleep(3000);
			double summary_price = GetPriceValue(fePage.billingCycleOnPaymentPage.getText());
			Assert.assertEquals(cartSubTotalPriceAdd, summary_price, cartSubTotalPriceAdd);
			
			//step~18
			Dailylog.logInfoDB(18, "proceed to thankyou page", Store, testName);
			//fill shipping
			Common.sleep(2000);
			if(Common.checkElementDisplays(driver, b2cPage.Checkout_StartCheckoutButton, 5)){
				b2cPage.Checkout_StartCheckoutButton.click();
			}
			Common.sleep(2000);
			if(b2cPage.Shipping_FirstNameTextBox.getText().isEmpty()){
				B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			}
		
			act.sendKeys(Keys.PAGE_UP).perform();
			b2cPage.Shipping_ContinueButton.click();
			//fill payment info
			B2CCommon.handleAddressVerify(driver,b2cPage);
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			Common.sleep(10000);
			b2cPage.Payment_ContinueButton.click();
			Common.sleep(2000);
			act.sendKeys(Keys.PAGE_DOWN).perform();
			if (Common
					.isElementExist(
							driver,
							By.xpath("//label[@class='redesign-term-check redesign-unchecked-icon']"))) {
				driver.findElement(
						By.xpath("//label[@class='redesign-term-check redesign-unchecked-icon']"))
						.click();
			} else {
				Common.javascriptClick(driver,
						b2cPage.OrderSummary_AcceptTermsCheckBox);
			}
			b2cPage.OrderSummary_PlaceOrderButton.click();
			Common.sleep(2000);
			double thankyou_price = GetPriceValue(fePage.billingCycleOnPaymentPage.getText());
			System.out.println(thankyou_price);
			System.out.println(summary_price);
			Assert.assertEquals("price is not correct", thankyou_price, summary_price);
			String orderNumber = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			
			//step~19
			Dailylog.logInfoDB(9, "check the display order detail in Account.order number is:" + orderNumber, Store, testName);
			String myAccountUrl = testData.B2C.getHomePageUrl() + "/my-account";
			
			driver.get(myAccountUrl);
			
			b2cPage.MyAccount_ViewOrderHistoryLink.click();
			
			//click view order status link
			
			driver.findElement(
					By.xpath(".//*[@id='accountOrderHistory']//a[contains(.,'"
							+ orderNumber + "')]")).click();
			
			double orderThankyou_SubtotalPrice = GetPriceValue(b2cPage.OrderThankyou_SubtotalPrice.getText());
			Assert.assertEquals("price is not correct on orderhistory", orderThankyou_SubtotalPrice, cartSubTotalPriceAdd);
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
		
	}
	
//	@AfterTest(alwaysRun = true, enabled = true)
//	public void rollback(ITestContext ctx) throws InterruptedException, MalformedURLException {
//		driver.get(testData.HMC.getHomePageUrl());
//		HMCCommon.Login(hmcPage, testData);
//		hmcPage.Home_baseCommerce.click();
//		hmcPage.Home_baseStore.click();
//		hmcPage.baseStore_id.clear();
//		hmcPage.baseStore_id.sendKeys(testData.B2C.getStore());
//		hmcPage.baseStore_search.click();
//		Common.doubleClick(driver, hmcPage.BaseStore_SearchResult);
//		Common.sleep(3000);
//		hmcPage.baseStore_administration.click();
//		WebElement releaseTime = driver.findElement(By.xpath("//input[@id,'BaseStore.releaseTime']"));
//		releaseTime.clear();
//		releaseTime.sendKeys("3600");
//		hmcPage.HMC_Save.click();
//	}
	
	
	public void addCTOToCart(String CTONo){
		String b2cProductUrlCustomize = testData.B2C.getHomePageUrl() + "/p/" + CTONo;
		driver.get(b2cProductUrlCustomize);
		Common.sleep(3000);
		closePromotion(driver, b2cPage);
		b2cPage.Product_viewModel.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.PDP_AddToCartOrCustomize);
		Common.sleep(3000);
		if (Common.checkElementDisplays(driver, b2cPage.B2C_PDP_AddToCart, 5)) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.B2C_PDP_AddToCart);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_AddToCartBtn);

		} else {
			b2cPage.Product_AddToCartBtn.click();
			while (Common.checkElementDisplays(driver, b2cPage.Product_Continue, 5)) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_Continue);
			}
		}
	}
	
	public void setReleaseTime(String Time){
		hmcPage.Home_baseStore.click();
		hmcPage.baseStore_id.clear();
		hmcPage.baseStore_id.sendKeys(testData.B2C.getStore());
		hmcPage.baseStore_search.click();
		Common.doubleClick(driver, hmcPage.BaseStore_SearchResult);
		Common.sleep(3000);
		hmcPage.baseStore_administration.click();
		WebElement releaseTime = driver.findElement(By.xpath("//input[contains(@id,'BaseStore.releaseTime')]"));
		releaseTime.clear();
		releaseTime.sendKeys(Time);
		hmcPage.HMC_Save.click();
	}
	
	public void verifyEditAmount(int preEditAmount){
		driver.navigate().refresh();
		editAmount = driver.findElements(By.xpath("//li[@class='qa_Edit_link']//a"));
		Assert.assertEquals(preEditAmount ,editAmount.size());
	}
	
	private String selectOptionPB(String message, String type) throws InterruptedException {
		String testItem = "";
		String testItemID = "";

		testItem = Common.convertWebElementToString(b2cPage.PB_accessoryItem);
		if (Common.checkElementDisplays(driver, By.xpath(testItem), 10)) {
			testItemID = b2cPage.PB_addAccessoryItemID.getAttribute("value");
			Common.scrollToElement(driver, b2cPage.PB_accessoryItem);
			Common.javascriptClick(driver, b2cPage.PB_accessoryItem);
		} 
		return testItemID;
	}
	
	public double addCBToCart(String test_cb_1){
		driver.get(testData.B2C.getHomePageUrl() + "/p/" + test_cb_1);
		Common.sleep(2000);
		closePromotion(driver, b2cPage);
		WebElement ConvenienceBundle_PBPrice = driver
				.findElement(By.xpath("(//dd[@class='saleprice pricingSummary-priceList-value priceDataHighlight'][2])"));
		double your_price = GetPriceValue(ConvenienceBundle_PBPrice.getText());
		System.out.println("page_price:" + your_price);
		double from_price = GetPriceValue(b2cPage.ConvenienceBundle_PBPrice.getText());
		double cart_saving_price = from_price - your_price;

		try {
			Common.scrollToElement(driver, b2cPage.ConvenienceBundle_Add);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Common.javascriptClick(driver, b2cPage.ConvenienceBundle_Add);
		if(Common.checkElementDisplays(driver, By.xpath("//button[contains(@class, 'add-to-cart')]"), 5)){
			b2cPage.cto_AddToCartButton.click();
		}
		return cart_saving_price;
	}
	
	public static double GetPriceValue(String Price) {

		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.replaceAll("\\￥", "");
		Price = Price.replaceAll("HK", "");
		Price = Price.replaceAll("₹", "");
		Price = Price.trim();
		double priceValue;
		priceValue = Double.parseDouble(Price);
		return priceValue;
	}
	
	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");

		if (Common.isElementExist(driver, Promotion)) {

			Actions actions = new Actions(driver);

			actions.moveToElement(page.PromotionBanner).click().perform();

		}
	}
	
	public String getReservedStockLevel(String partNumber){
		hmcPage.Home_baseCommerce.click();
		if(!Common.checkElementDisplays(driver, By.xpath("//a[contains(@id,'StockLevel')]"), 3)){
			hmcPage.Home_baseCommerce.click();
		}
		hmcPage.BaseCommerce_StockLevel.click();
		By StockLevel_PartNoTextBox = By.xpath("//input[@id='Content/StringEditor[in Content/GenericCondition[StockLevel.productCode]]_input']");
		if(!Common.checkElementDisplays(driver, StockLevel_PartNoTextBox, 5)){
			driver.findElement(By.xpath("//a[contains(@id,'[organizersearch][StockLevel]_pin')]")).click();
			Common.sleep(2000);
		}
		hmcPage.StockLevel_PartNoTextBox.clear();
		hmcPage.StockLevel_PartNoTextBox.sendKeys(partNumber);
		hmcPage.stockLevel_search.click();
		Common.sleep(3000);
		By isProductExist = By.xpath("//div/div[contains(text(),'" + partNumber + "')]");
		Common.doubleClick(driver, driver.findElement(isProductExist));
		Common.sleep(2000);
		WebElement reserveWareHouseAmount = driver
				.findElement(By.xpath(".//*[contains(@id,'Attribute[.reserved]]_input') or contains(@id,'Attribute[StockLevel.reserved]')]"));
		return reserveWareHouseAmount.getAttribute("value");
		
	}
	
}
