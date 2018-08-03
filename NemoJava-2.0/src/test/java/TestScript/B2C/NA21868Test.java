package TestScript.B2C;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class NA21868Test extends SuperTestClass {
	public B2CPage b2cPage;
	public B2CPage b2cPage_ASM;
	public HMCPage hmcPage;
	WebDriver ASMdriver;
	WebDriver normaldriver;

	public String Name = "21868CardTW";
	public String Code = "21868CardTW";

	public NA21868Test(String Store) {
		this.Store = Store;
		this.testName = "NA-21868";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "payment", "p2", "b2c"})
	public void NA21868(ITestContext ctx) {

		try {

			this.prepareTest();
			hmcPage = new HMCPage(driver); // HMC driver
			ASMdriver = Common.openNewBrowser();
			b2cPage_ASM = new B2CPage(ASMdriver);
			normaldriver = Common.openNewBrowser();
			b2cPage = new B2CPage(normaldriver);

			// 1, Go to HMC --> Nemo --> Payment --> Payment Type Customize, right click to
			// create a new payment profile

			System.out.println("step 1 HMC");

			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);

			hmcPage.Home_Nemo.click();
			hmcPage.Home_payment.click();

			driver.findElement(By.xpath("//a[contains(@id,'nemo.payment.type.customize')]")).click();

			((JavascriptExecutor) driver).executeScript("scroll(0,1200)");

			driver.findElement(By.xpath("//a[contains(@id,'PaymentTypeProfile')]")).click();

			Select select_name1 = new Select(
					driver.findElement(By.xpath("//select[contains(@id,'[attributeselect][PaymentTypeProfile]')]")));
			select_name1.selectByValue("name");

			driver.findElement(By.xpath("//input[contains(@id,'PaymentTypeProfile.name')]")).sendKeys(Name);
			driver.findElement(By.xpath("//span[contains(@id,'searchbutton')]")).click();

			// driver.findElement(By.xpath("//img[contains(@id,'Content/OrganizerListEntry')]")).click();

			if (Common.isElementExist(driver, By.xpath("//img[contains(@id,'Content/OrganizerListEntry')]"))) {
				// if 17980card profile already exists, then remove all of them
				driver.findElement(By.xpath("//img[contains(@id,'Content/OrganizerListEntry')]")).click();
				driver.findElement(By
						.xpath("//*[contains(@id,'Content/OrganizerItemChip$2[organizer.editor.delete.title]_label')]"))
						.click();
				driver.switchTo().alert().accept();
				Thread.sleep(10000);
			}

			Actions actions = new Actions(driver);
			actions.moveToElement(driver.findElement(By.xpath("//a[contains(@id,'PaymentTypeProfile')]")));
			actions.perform();

			actions.contextClick(driver.findElement(By.xpath("//a[contains(@id,'PaymentTypeProfile')]")));

			actions.perform();

			driver.findElement(By.xpath("//td[contains(@id,'create_PaymentTypeProfile_label')][1]")).click();

			// 2, Go to B2C Properties tab
			System.out.println("step 2 HMC");

			/*
			 * "Disable For Customer", "Disable For ASM", "Enable Threshold Rule",
			 * "Threshold Minimize", "Threshold Maximum" options are provided
			 */
			boolean disable_For_Customer = driver
					.findElement(By.xpath("//table[@class='attributeChip']//div[contains(.,'Disable For Customer')]"))
					.isDisplayed();
			boolean disable_For_ASM = driver
					.findElement(By.xpath("//table[@class='attributeChip']//div[contains(.,'Disable For ASM')]"))
					.isDisplayed();
			boolean Enable_Threshold_Rule = driver
					.findElement(By.xpath("//table[@class='attributeChip']//div[contains(.,'Enable Threshold Rule')]"))
					.isDisplayed();
			boolean Threshold_Minimize = driver
					.findElement(By.xpath("//table[@class='attributeChip']//div[contains(.,'Threshold Minimize')]"))
					.isDisplayed();
			boolean Threshold_Maximum = driver
					.findElement(By.xpath("//table[@class='attributeChip']//div[contains(.,'Threshold Maximum')]"))
					.isDisplayed();

			Assert.assertTrue(disable_For_Customer && disable_For_ASM && Enable_Threshold_Rule && Threshold_Minimize
					&& Threshold_Maximum);

			// 3, Enter values
			System.out.println("step 3 HMC");
			/*
			 * Name: 17980card Code: 17980card Payment Type: Card Payment Configuration
			 * Level: SPECIFIC Channel: ALL Active: Yes
			 * 
			 * Disable For customer = n/a Disable For ASM = n/a Save changes successfully =
			 * n/a
			 */

			driver.findElement(By.xpath("//input[contains(@id,'in Content/Attribute[.name')]")).clear();
			driver.findElement(By.xpath("//input[contains(@id,'in Content/Attribute[.name')]")).sendKeys(Name);

			driver.findElement(By.xpath("//input[contains(@id,'code')]")).clear();
			driver.findElement(By.xpath("//input[contains(@id,'code')]")).sendKeys(Code);

			Select select_paymentType = new Select(
					driver.findElement(By.xpath("//select[contains(@id,'checkoutPaymentType')]")));
			select_paymentType.selectByVisibleText("Card Payment");

			Select select_configLevel = new Select(
					driver.findElement(By.xpath("//select[contains(@id,'configLevel')]")));
			select_configLevel.selectByVisibleText("SPECIFIC");

			Select select_Channel = new Select(driver.findElement(By.xpath("//select[contains(@id,'channel')]")));
			select_Channel.selectByVisibleText("B2C");

			if (!driver.findElement(By.xpath("//input[contains(@id,'active') and contains(@id,'true')]"))
					.isSelected()) {
				driver.findElement(By.xpath("//input[contains(@id,'active') and contains(@id,'true')]")).click();
			}

			if (!driver
					.findElement(By.xpath("//input[contains(@id,'disablePayment4Customer') and contains(@id,'null')]"))
					.isSelected()) {
				driver.findElement(
						By.xpath("//input[contains(@id,'disablePayment4Customer') and contains(@id,'null')]")).click();
			}

			if (!driver.findElement(By.xpath("//input[contains(@id,'disablePayment4ASM') and contains(@id,'null')]"))
					.isSelected()) {
				driver.findElement(By.xpath("//input[contains(@id,'disablePayment4ASM') and contains(@id,'null')]"))
						.click();
			}

			if (!driver
					.findElement(
							By.xpath("//input[contains(@id,'enablePaymentThresholdRule') and contains(@id,'null')]"))
					.isSelected()) {
				driver.findElement(
						By.xpath("//input[contains(@id,'enablePaymentThresholdRule') and contains(@id,'null')]"))
						.click();
			}

			// 4, Go to B2CUnits tab, add a B2C unit, then create
			System.out.println("step 4 HMC");

			driver.findElement(By.xpath("//span[contains(@id,'b2cunits')]")).click();

			String main_winHandle = driver.getWindowHandle();

			Actions actions_idenfication = new Actions(driver);
			actions_idenfication.contextClick(driver
					.findElement(By.xpath("//div[@class='gilcEntry-mandatory'][contains(.,'Identification code')]")));
			actions_idenfication.perform();

			driver.findElement(By.xpath("//td[contains(@id,'true_label')][contains(.,'Add B2C Unit')][1]")).click();
			Thread.sleep(5000);
			Set<String> set = driver.getWindowHandles();

			for (String str : set) {
				if (str.equals(main_winHandle))
					continue;
				driver.switchTo().window(str);
			}

			String UnitID = testData.B2C.getUnit();

			Select select_identificationCode = new Select(
					driver.findElement(By.xpath("//select[contains(@id,'B2CUnit.uid')]")));

			select_identificationCode.selectByValue("1");
			driver.findElement(By.xpath("//input[contains(@id,'B2CUnit.uid') and contains(@id,'input')]")).clear();
			driver.findElement(By.xpath("//input[contains(@id,'B2CUnit.uid') and contains(@id,'input')]"))
					.sendKeys(UnitID);
			driver.findElement(By.xpath("//span[contains(@id,'searchbutton')]")).click();

			driver.findElement(
					By.xpath("//div[contains(@id,'StringDisplay[" + UnitID + "]') and contains(@id,'span')]")).click();
			driver.findElement(By.xpath("//span[contains(@id,'use')]")).click();

			driver.switchTo().window(main_winHandle);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//div[contains(@id,'saveandcreate')]")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//div[contains(@id,'organizer.editor.save.title')]")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//img[contains(@id,'closesession')]")).click();

			// 5, Go to https://tun-c-hybris.lenovo.com/us/en/usweb/cart, add item to cart
			System.out.println("step 5");

			normaldriver.get(testData.B2C.getTeleSalesUrl() + "/login");
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());

			normaldriver.get(testData.B2C.getTeleSalesUrl() + "/cart");

			String MTMProdNumber = testData.B2C.getDefaultMTMPN();

			if (Common.isElementExist(normaldriver, By.xpath(".//*[@id='emptyCartItemsForm']/a"))) {
				normaldriver.findElement(By.xpath(".//*[@id='emptyCartItemsForm']/a")).click();
			}

			addPartNumberToCart(b2cPage, MTMProdNumber);
			By by_Checkout = By.xpath("//a[contains(@id,'lenovo-checkout')]");
			if(!Common.checkElementDisplays(ASMdriver, by_Checkout, 5)){
				MTMProdNumber = "81C3005MTW";
				addPartNumberToCart(b2cPage, MTMProdNumber);
			}
			// 6, Proceed to checkout payment page
			System.out.println("step 6");
			b2cPage.lenovo_checkout.click();

			B2CCommon.fillShippingInfo(b2cPage, "test", "test", testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(),
					testData.B2C.getLoginID(), testData.B2C.getConsumerTaxNumber());

			Common.javascriptClick(normaldriver, b2cPage.Shipping_ContinueButton);
			B2CCommon.handleAddressVerify(normaldriver, b2cPage);

			Thread.sleep(10000);
			Assert.assertTrue(normaldriver.getCurrentUrl().toString().endsWith("add-payment-method"));

			Assert.assertTrue(Common.isElementExist(normaldriver, By.xpath(".//*[@id='PaymentTypeSelection_CARD']")));

			// 7, Open another browser session, login as an ASM, start session as a customer
			System.out.println("step 7 ASM");

			ASMdriver.get(testData.B2C.getTeleSalesUrl() + "/login");
			B2CCommon.login(b2cPage_ASM, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());

			if (Common.checkElementDisplays(ASMdriver,
					By.xpath("//ul[@class='menu general_Menu']//a[contains(@href,'my-account')]/span"), 3))
				ASMdriver
						.findElement(By.xpath("//ul[@class='menu general_Menu']//a[contains(@href,'my-account')]/span"))
						.click();
			ASMdriver.findElement(By.xpath("//a[contains(@href,'activateASM')]")).click();

			new WebDriverWait(ASMdriver, 500)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='customerFilter']")));

			handlePopup();

			b2cPage_ASM.ASM_SearchCustomer.sendKeys(testData.B2C.getLoginID());
			new WebDriverWait(ASMdriver, 500)
					.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[id^='ui-id-']>a")));
			b2cPage_ASM.ASM_CustomerResult.click();
			b2cPage_ASM.ASM_StartSession.click();
			Thread.sleep(5000);

			// 8,Add item to cart, proceed to checkout payment page
			System.out.println("step 8 ASM");

			ASMdriver.get(testData.B2C.getTeleSalesUrl() + "/cart");

			if (Common.isElementExist(ASMdriver, By.xpath(".//*[@id='emptyCartItemsForm']/a"))) {
				ASMdriver.findElement(By.xpath(".//*[@id='emptyCartItemsForm']/a")).click();
			}

			b2cPage_ASM.Cart_QuickOrderTextBox.sendKeys(MTMProdNumber);
			b2cPage_ASM.Cart_AddButton.click();

			b2cPage_ASM.lenovo_checkout.click();

			B2CCommon.fillShippingInfo(b2cPage_ASM, "test", "test", testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(),
					testData.B2C.getLoginID(), testData.B2C.getConsumerTaxNumber());

			Common.javascriptClick(ASMdriver, b2cPage_ASM.Shipping_ContinueButton);
			B2CCommon.handleAddressVerify(ASMdriver, b2cPage_ASM);
			Assert.assertTrue(Common.isElementExist(ASMdriver, By.xpath(".//*[@id='PaymentTypeSelection_CARD']")));

			// 9, Go back to HMC, payment profile --> B2C Properties tab, save changes
			/*
			 * Disable For customer = No Disable For ASM = n/a
			 * 
			 */
			System.out.println("step 9 HMC");

			String HMCHomePage = testData.HMC.getHomePageUrl();
			for (int x = 1; x <= 5; x++) {
				Thread.sleep(4000);
				driver.get(HMCHomePage);
			}

			HMCCommon.Login(hmcPage, testData);

			hmcPage.Home_Nemo.click();
			hmcPage.Home_payment.click();

			driver.findElement(By.xpath("//a[contains(@id,'nemo.payment.type.customize')]")).click();
			driver.findElement(By.xpath(".//*[@id='Tree/GenericLeafNode[PaymentTypeProfile]_label']")).click();

			Select select_name = new Select(
					driver.findElement(By.xpath("//select[contains(@id,'[attributeselect][PaymentTypeProfile]')]")));
			select_name.selectByValue("name");

			driver.findElement(By.xpath("//input[contains(@id,'PaymentTypeProfile.name')]")).sendKeys(Name);
			driver.findElement(By.xpath("//span[contains(@id,'searchbutton')]")).click();

			driver.findElement(By.xpath("//img[contains(@id,'Content/OrganizerListEntry')]")).click();

			// set the disable for customer value into no
			if (!driver
					.findElement(By.xpath("//input[contains(@id,'disablePayment4Customer') and contains(@id,'false')]"))
					.isSelected()) {
				driver.findElement(
						By.xpath("//input[contains(@id,'disablePayment4Customer') and contains(@id,'false')]")).click();
			}

			driver.findElement(By.xpath("//div[contains(@id,'organizer.editor.save.title')]")).click();
			driver.findElement(By.xpath("//img[contains(@id,'closesession')]")).click();

			// 10,Refresh customer session payment page
			System.out.println("step 10");

			normaldriver.navigate().refresh();
			Thread.sleep(5000);
			Assert.assertTrue(Common.isElementExist(normaldriver, By.xpath(".//*[@id='PaymentTypeSelection_CARD']")));
			// 11,Refresh ASM session payment page
			System.out.println("step 11 ASM");

			ASMdriver.navigate().refresh();
			Thread.sleep(5000);
			Assert.assertTrue(Common.isElementExist(ASMdriver, By.xpath(".//*[@id='PaymentTypeSelection_CARD']")));

			// 12, Go back to HMC, payment profile --> B2C Properties tab, save changes
			System.out.println("step 12 HMC");

			/*
			 * Disable For customer = Yes Disable For ASM = n/a
			 * 
			 * 
			 */

			driver.get(HMCHomePage);
			HMCCommon.Login(hmcPage, testData);

			backToHmcAndChangePaymentProfile_step12(Name,
					"//input[contains(@id,'disablePayment4Customer') and contains(@id,'true')]");

			// 13, Refresh customer session payment page
			System.out.println("step 13");

			normaldriver.navigate().refresh();
			Thread.sleep(5000);
			Assert.assertTrue(!Common.isElementExist(normaldriver, By.xpath(".//*[@id='PaymentTypeSelection_CARD']")));

			// 14, Refresh ASM session payment page
			System.out.println("step 14 ASM");

			ASMdriver.navigate().refresh();
			Thread.sleep(5000);
			Assert.assertTrue(Common.isElementExist(ASMdriver, By.xpath(".//*[@id='PaymentTypeSelection_CARD']")));
			// 15, Go back to HMC, payment profile --> B2C Properties tab, save changes
			System.out.println("step 15 HMC");

			/*
			 * Disable For customer = Yes Disable For ASM = No
			 * 
			 */

			driver.get(HMCHomePage);
			HMCCommon.Login(hmcPage, testData);
			backToHmcAndChangePaymentProfile_step15(Name,
					"//input[contains(@id,'disablePayment4Customer') and contains(@id,'true')]",
					"//input[contains(@id,'disablePayment4ASM') and contains(@id,'false')]");

			// 16,Refresh customer session payment page
			System.out.println("step 16");

			normaldriver.navigate().refresh();
			Thread.sleep(5000);
			Assert.assertTrue(!Common.isElementExist(normaldriver, By.xpath(".//*[@id='PaymentTypeSelection_CARD']")));
			// 17, Refresh ASM session payment page
			System.out.println("step 17 ASM");

			ASMdriver.navigate().refresh();
			Thread.sleep(5000);
			Assert.assertTrue(Common.isElementExist(ASMdriver, By.xpath(".//*[@id='PaymentTypeSelection_CARD']")));
			// 18, Go back to HMC, payment profile --> B2C Properties tab, save changes
			System.out.println("step 18 HMC");

			/*
			 * Disable For customer = Yes Disable For ASM = Yes
			 */
			driver.get(HMCHomePage);
			HMCCommon.Login(hmcPage, testData);
			backToHmcAndChangePaymentProfile_step15(Name,
					"//input[contains(@id,'disablePayment4Customer') and contains(@id,'true')]",
					"//input[contains(@id,'disablePayment4ASM') and contains(@id,'true')]");

			// 19,Refresh customer session payment page
			System.out.println("step 19");

			normaldriver.navigate().refresh();
			Thread.sleep(5000);
			Assert.assertTrue(!Common.isElementExist(normaldriver, By.xpath(".//*[@id='PaymentTypeSelection_CARD']")));

			// 20,Refresh ASM session payment page
			System.out.println("step 20 ASM");

			ASMdriver.navigate().refresh();
			Thread.sleep(5000);
			Assert.assertTrue(!Common.isElementExist(ASMdriver, By.xpath(".//*[@id='PaymentTypeSelection_CARD']")));

			// 21,Go back to HMC, payment profile --> B2C Properties tab, save changes
			System.out.println("step 21 HMC");
			/*
			 * Disable For customer = No Disable For ASM = Yes
			 */
			driver.get(HMCHomePage);
			HMCCommon.Login(hmcPage, testData);
			backToHmcAndChangePaymentProfile_step15(Name,
					"//input[contains(@id,'disablePayment4Customer') and contains(@id,'false')]",
					"//input[contains(@id,'disablePayment4ASM') and contains(@id,'true')]");

			// 22, Refresh customer session payment page
			System.out.println("step 22");

			normaldriver.navigate().refresh();
			Thread.sleep(5000);
			Assert.assertTrue(Common.isElementExist(normaldriver, By.xpath(".//*[@id='PaymentTypeSelection_CARD']")));

			// 23, Refresh ASM session payment page
			System.out.println("step 23 ASM");

			ASMdriver.navigate().refresh();
			Thread.sleep(5000);
			Assert.assertTrue(!Common.isElementExist(ASMdriver, By.xpath(".//*[@id='PaymentTypeSelection_CARD']")));

			// 24, Go back to HMC, payment profile --> B2C Properties tab, save changes
			System.out.println("step 24 HMC");

			/*
			 * Disable For customer = n/a Disable For ASM = Yes
			 * 
			 */
			driver.get(HMCHomePage);
			HMCCommon.Login(hmcPage, testData);
			backToHmcAndChangePaymentProfile_step15(Name,
					"//input[contains(@id,'disablePayment4Customer') and contains(@id,'null')]",
					"//input[contains(@id,'disablePayment4ASM') and contains(@id,'true')]");

			// 25,Refresh customer session payment page
			System.out.println("step 25");

			normaldriver.navigate().refresh();
			Thread.sleep(5000);
			Assert.assertTrue(Common.isElementExist(normaldriver, By.xpath(".//*[@id='PaymentTypeSelection_CARD']")));

			// 26,Refresh ASM session payment page
			System.out.println("step 26 ASM");

			ASMdriver.navigate().refresh();
			Thread.sleep(5000);
			Assert.assertTrue(!Common.isElementExist(ASMdriver, By.xpath(".//*[@id='PaymentTypeSelection_CARD']")));

			// 27,Go back to HMC, payment profile --> B2C Properties tab, save changes
			System.out.println("step 27 HMC");

			/*
			 * Disable For customer = n/a Disable For ASM = No
			 */
			driver.get(HMCHomePage);

			HMCCommon.Login(hmcPage, testData);
			backToHmcAndChangePaymentProfile_step15(Name,
					"//input[contains(@id,'disablePayment4Customer') and contains(@id,'null')]",
					"//input[contains(@id,'disablePayment4ASM') and contains(@id,'false')]");

			// 28,Refresh customer session payment page
			System.out.println("step 28");

			normaldriver.navigate().refresh();
			Thread.sleep(5000);
			Assert.assertTrue(Common.isElementExist(normaldriver, By.xpath(".//*[@id='PaymentTypeSelection_CARD']")));

			// 29,Refresh ASM session payment page
			System.out.println("step 29 ASM");

			ASMdriver.navigate().refresh();
			Thread.sleep(5000);
			Assert.assertTrue(Common.isElementExist(ASMdriver, By.xpath(".//*[@id='PaymentTypeSelection_CARD']")));

			// 30,Go back to HMC, payment profile --> B2C Properties tab, save changes
			System.out.println("step 30 HMC");

			/*
			 * Disable For customer = No Disable For ASM = No
			 * 
			 */
			driver.get(HMCHomePage);
			HMCCommon.Login(hmcPage, testData);
			backToHmcAndChangePaymentProfile_step15(Name,
					"//input[contains(@id,'disablePayment4Customer') and contains(@id,'false')]",
					"//input[contains(@id,'disablePayment4ASM') and contains(@id,'false')]");

			// 31, Refresh customer session payment page
			System.out.println("step 31");

			normaldriver.navigate().refresh();
			Thread.sleep(5000);
			Assert.assertTrue(Common.isElementExist(normaldriver, By.xpath(".//*[@id='PaymentTypeSelection_CARD']")));

			// 32, Refresh ASM session payment page
			System.out.println("step 32 ASM");

			ASMdriver.navigate().refresh();
			Thread.sleep(5000);
			Assert.assertTrue(Common.isElementExist(ASMdriver, By.xpath(".//*[@id='PaymentTypeSelection_CARD']")));

			// 33, Go back to HMC, payment profile --> B2C Properties tab, save changes
			System.out.println("step 33 HMC");

			driver.get(HMCHomePage);
			HMCCommon.Login(hmcPage, testData);

			backToHmcAndChangePaymentProfile_step33(Name,
					"//input[contains(@id,'enablePaymentThresholdRule') and contains(@id,'true')]");

			driver.findElement(By.xpath("//img[contains(@id,'closesession')]")).click();

			// 34, Make cart value < 200, go to checkout payment page
			System.out.println("step 34");

			normaldriver.get(testData.B2C.getTeleSalesUrl() + "/login");

			System.out.println("MTMProdNumber is :" + MTMProdNumber);

			addProdIntoCart(MTMProdNumber);

			String price_1 = normaldriver.findElement(By.xpath("//div[@class='price-calculator-cart-items']/dl"))
					.getText().toString();

			long totalPrice_1 = getPrice(price_1);

			long lowerPrice = totalPrice_1 + 100;
			long higherPrice = totalPrice_1 + 200;

			driver.get(HMCHomePage);
			HMCCommon.Login(hmcPage, testData);

			backToHmcAndChangeLowerPriceAndHigherPrice(Name, lowerPrice + "", higherPrice + "");
			driver.findElement(By.xpath("//img[contains(@id,'closesession')]")).click();

			fromCartpageToPaymentPage(testData.B2C.getHomePageUrl(), MTMProdNumber);

			Assert.assertTrue(!Common.isElementExist(normaldriver, By.xpath(".//*[@id='PaymentTypeSelection_CARD']")));

			// 35,Make cart value >= 200 and < 500, go to checkout payment page
			System.out.println("step 35");

			long lowerPrice_1 = totalPrice_1 - 10;
			long higherPrice_1 = totalPrice_1 + 10;

			driver.get(HMCHomePage);
			HMCCommon.Login(hmcPage, testData);

			backToHmcAndChangeLowerPriceAndHigherPrice(Name, lowerPrice_1 + "", higherPrice_1 + "");

			driver.findElement(By.xpath("//img[contains(@id,'closesession')]")).click();

			fromCartpageToPaymentPage(testData.B2C.getHomePageUrl(), MTMProdNumber);

			Assert.assertTrue(Common.isElementExist(normaldriver, By.xpath(".//*[@id='PaymentTypeSelection_CARD']")));

			// 36, Make cart value >= 500, go to checkout payment page
			System.out.println("step 36");

			long lowerPrice_2 = totalPrice_1 - 20;
			System.out.println("totalPrice_1 is :" + totalPrice_1);
			System.out.println("lowerPrice_2 is :" + lowerPrice_2);

			long higherPrice_2 = totalPrice_1 - 10;
			System.out.println("higherPrice_2 is :" + higherPrice_2);

			driver.get(HMCHomePage);
			HMCCommon.Login(hmcPage, testData);

			backToHmcAndChangeLowerPriceAndHigherPrice(Name, lowerPrice_2 + "", higherPrice_2 + "");

			driver.findElement(By.xpath("//img[contains(@id,'closesession')]")).click();

			fromCartpageToPaymentPage(testData.B2C.getHomePageUrl(), MTMProdNumber);

			Assert.assertTrue(!Common.isElementExist(normaldriver, By.xpath(".//*[@id='PaymentTypeSelection_CARD']")));
			normaldriver.quit();
			ASMdriver.quit();
		} catch (Throwable e) {
			if (normaldriver != null) {
				try {
					normaldriver.quit();
				} catch (Exception ex) {
					handleAlert(normaldriver);
					Dailylog.logInfo("SetupBrowser has a trouble");
				}
			}
			if (ASMdriver != null) {
				try {
					ASMdriver.quit();
				} catch (Exception ex) {
					handleAlert(ASMdriver);
					Dailylog.logInfo("SetupBrowser has a trouble");
				}
			}
			handleThrowable(e, ctx);
		}
	}
	
	public static void addPartNumberToCart(B2CPage b2cPage, String partNum) {
		b2cPage.Cart_QuickOrderTextBox.clear();
		b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
		Common.sleep(1000);
		b2cPage.Cart_AddButton.click();
		
	}

	public void addProdIntoCart(String MTMProdNumber) {
		String cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
		normaldriver.get(cartUrl);

		if (Common.isElementExist(normaldriver, By.xpath(".//*[@id='emptyCartItemsForm']/a"))) {
			normaldriver.findElement(By.xpath(".//*[@id='emptyCartItemsForm']/a")).click();
		}

		B2CCommon.addPartNumberToCart(b2cPage, MTMProdNumber);
	}

	public void backToHmcAndChangeLowerPriceAndHigherPrice(String name, String lowerPrice, String higherPrice) {
		hmcPage.Home_Nemo.click();
		hmcPage.Home_payment.click();

		driver.findElement(By.xpath("//a[contains(@id,'nemo.payment.type.customize')]")).click();
		driver.findElement(By.xpath(".//*[@id='Tree/GenericLeafNode[PaymentTypeProfile]_label']")).click();

		Select select_name = new Select(
				driver.findElement(By.xpath("//select[contains(@id,'[attributeselect][PaymentTypeProfile]')]")));
		select_name.selectByValue("name");

		driver.findElement(By.xpath("//input[contains(@id,'PaymentTypeProfile.name')]")).sendKeys(name);
		driver.findElement(By.xpath("//span[contains(@id,'searchbutton')]")).click();

		driver.findElement(By.xpath("//img[contains(@id,'Content/OrganizerListEntry')]")).click();

		driver.findElement(By.xpath("//input[contains(@id,'EditableItemListEntry') and contains(@id,'input')]"))
				.clear();
		driver.findElement(By.xpath("//input[contains(@id,'EditableItemListEntry') and contains(@id,'input')]"))
				.sendKeys(lowerPrice);

		driver.findElement(
				By.xpath("(//input[contains(@id,'EditableItemListEntry') and contains(@id,'input')])[last()]")).clear();
		driver.findElement(
				By.xpath("(//input[contains(@id,'EditableItemListEntry') and contains(@id,'input')])[last()]"))
				.sendKeys(higherPrice);

		driver.findElement(By.xpath("//div[contains(@id,'organizer.editor.save.title')]")).click();
	}

	public int getPrice(String price) {

		price = price.replace("$", "").replace(".00", "").replace(",", "").replace("NT", "").trim();

		int price1 = Integer.parseInt(price);

		return price1;

	}

	public String getProdNumber(String store) {

		String prodNumber = "";

		if (store.equals("AU")) {
			prodNumber = "06P4069";
		} else if (store.equals("CA")) {
			prodNumber = "GXY0K07131";
		} else if (store.equals("HK")) {
			prodNumber = "06P4069";
		}
		return prodNumber;

	}

	public void loginASM() throws Exception {
		if (Common.checkElementDisplays(driver,
				By.xpath("//ul[@class='menu general_Menu']//a[contains(@href,'my-account')]/span"), 3))
			driver.findElement(By.xpath("//ul[@class='menu general_Menu']//a[contains(@href,'my-account')]/span"))
					.click();
		driver.findElement(By.xpath("//a[contains(@href,'activateASM')]")).click();

		new WebDriverWait(driver, 500)
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='customerFilter']")));
		handlePopup();

		b2cPage.ASM_SearchCustomer.sendKeys(testData.B2C.getLoginID());
		new WebDriverWait(driver, 500)
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[id^='ui-id-']>a")));
		b2cPage.ASM_CustomerResult.click();
		b2cPage.ASM_StartSession.click();
		Thread.sleep(5000);
	}

	public void fromCartpageToPaymentPage(String url, String MTMProdNumber) {
		String cartUrl = url + "/cart";
		normaldriver.get(cartUrl);

		if (Common.isElementExist(normaldriver, By.xpath(".//*[@id='emptyCartItemsForm']/a"))) {
			normaldriver.findElement(By.xpath(".//*[@id='emptyCartItemsForm']/a")).click();
		}

		normaldriver.findElement(By.xpath(".//*[@id='quickOrderProductId']")).clear();
		normaldriver.findElement(By.xpath(".//*[@id='quickOrderProductId']")).sendKeys(MTMProdNumber);
		b2cPage.Cart_AddButton.click();

		b2cPage.lenovo_checkout.click();
		if (Common.checkElementExists(normaldriver, b2cPage.Checkout_StartCheckoutButton, 5)) {
			b2cPage.Checkout_StartCheckoutButton.click();
		}

		B2CCommon.fillShippingInfo(b2cPage, "test", "test", testData.B2C.getDefaultAddressLine1(),
				testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
				testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(),
				testData.B2C.getLoginID(), testData.B2C.getConsumerTaxNumber());

		Common.javascriptClick(normaldriver, b2cPage.Shipping_ContinueButton);
		B2CCommon.handleAddressVerify(normaldriver, b2cPage);
	}

	public void backToHmcAndChangePaymentProfile_step33(String Name, String xpath) {

		hmcPage.Home_Nemo.click();
		hmcPage.Home_payment.click();

		driver.findElement(By.xpath("//a[contains(@id,'nemo.payment.type.customize')]")).click();
		driver.findElement(By.xpath(".//*[@id='Tree/GenericLeafNode[PaymentTypeProfile]_label']")).click();

		Select select_name = new Select(
				driver.findElement(By.xpath("//select[contains(@id,'[attributeselect][PaymentTypeProfile]')]")));
		select_name.selectByValue("name");

		driver.findElement(By.xpath("//input[contains(@id,'PaymentTypeProfile.name')]")).sendKeys(Name);
		driver.findElement(By.xpath("//span[contains(@id,'searchbutton')]")).click();

		driver.findElement(By.xpath("//img[contains(@id,'Content/OrganizerListEntry')]")).click();

		// set the disable for customer value into no
		if (!driver.findElement(By.xpath(xpath)).isSelected()) {
			driver.findElement(By.xpath(xpath)).click();
		}

		Actions action_Price = new Actions(driver);
		action_Price.moveToElement(driver.findElement(By.xpath(
				"(//table[contains(@id,'table_Content/GenericItemList') and contains(@id,'table')]/tbody/tr[2]/td)[1]")));
		action_Price.perform();
		action_Price.contextClick(driver.findElement(By.xpath(
				"(//table[contains(@id,'table_Content/GenericItemList') and contains(@id,'table')]/tbody/tr[2]/td)[1]")));
		action_Price.perform();
		action_Price.moveToElement(
				driver.findElement(By.xpath("(//td[@id='Content/GenericItemList_null_null_label'])[last()]")));
		action_Price.perform();
		driver.findElement(By.xpath("(//*[@id='Content/GenericItemList_!create_PromotionPriceRow_label'])[last()]"))
				.click();

		Select select_price = new Select(driver
				.findElement(By.xpath("//select[contains(@id,'CreateItemListEntry') and contains(@id,'select')]")));

		// if(Store.equals("AU")){
		// select_price.selectByValue("3");
		// }else if(Store.equals("CA")){
		// select_price.selectByValue("85");
		// }else if(Store.equalsIgnoreCase("HK")){
		// select_price.selectByValue("31");
		// }
		select_price.selectByVisibleText("TWD");

		driver.findElement(By.xpath("//input[contains(@id,'CreateItemListEntry') and contains(@id,'input')]")).clear();
		driver.findElement(By.xpath("//input[contains(@id,'CreateItemListEntry') and contains(@id,'input')]"))
				.sendKeys("200");

		Actions actions_Maxmium = new Actions(driver);
		actions_Maxmium.moveToElement(driver.findElement(By.xpath(
				"(//table[contains(@id,'table_Content/GenericItemList[1]') and contains(@id,'table')]/tbody/tr[2]/td)[1]")));
		actions_Maxmium.perform();
		actions_Maxmium.contextClick(driver.findElement(By.xpath(
				"(//table[contains(@id,'table_Content/GenericItemList[1]') and contains(@id,'table')]/tbody/tr[2]/td)[1]")));
		actions_Maxmium.perform();
		actions_Maxmium.moveToElement(driver.findElement(
				By.xpath("//td[contains(@id,'Content/GenericItemList') and contains(@id,'null_null_label')]")));
		actions_Maxmium.perform();

		driver.findElement(By.xpath("//td[contains(@id,'create_PromotionPriceRow_label')]")).click();

		Select select_price_1 = new Select(driver.findElement(
				By.xpath("(//select[contains(@id,'CreateItemListEntry') and contains(@id,'select')])[last()]")));

		// if(Store.equals("AU")){
		// select_price_1.selectByValue("3");
		// }else if(Store.equals("CA")){
		// select_price_1.selectByValue("85");
		// }else if(Store.equalsIgnoreCase("HK")){
		// select_price_1.selectByValue("31");
		// }

		select_price_1.selectByVisibleText("TWD");

		driver.findElement(By.xpath("(//input[contains(@id,'CreateItemListEntry') and contains(@id,'input')])[last()]"))
				.clear();
		driver.findElement(By.xpath("(//input[contains(@id,'CreateItemListEntry') and contains(@id,'input')])[last()]"))
				.sendKeys("500");

		driver.findElement(By.xpath("//div[contains(@id,'organizer.editor.save.title')]")).click();
	}

	// public String getCurrency(String store){
	//
	// String currency = "";
	//
	// switch(store){
	// case "AU" :
	// currency = "AUD";
	// break;
	// case "CA" :
	// currency = "USD";
	// break;
	// case "HK" :
	// currency = "";
	// default :
	// currency = "TWD";
	// break;
	// }
	// return currency;
	// }

	public void backToHmcAndChangePaymentProfile_step12(String Name, String xpath) throws InterruptedException {
		hmcPage.Home_Nemo.click();
		hmcPage.Home_payment.click();

		driver.findElement(By.xpath("//a[contains(@id,'nemo.payment.type.customize')]")).click();
		driver.findElement(By.xpath(".//*[@id='Tree/GenericLeafNode[PaymentTypeProfile]_label']")).click();

		Select select_name = new Select(
				driver.findElement(By.xpath("//select[contains(@id,'[attributeselect][PaymentTypeProfile]')]")));
		select_name.selectByValue("name");

		driver.findElement(By.xpath("//input[contains(@id,'PaymentTypeProfile.name')]")).sendKeys(Name);
		driver.findElement(By.xpath("//span[contains(@id,'searchbutton')]")).click();

		driver.findElement(By.xpath("//img[contains(@id,'Content/OrganizerListEntry')]")).click();

		// set the disable for customer value into no
		if (!driver.findElement(By.xpath(xpath)).isSelected()) {
			driver.findElement(By.xpath(xpath)).click();
		}

		driver.findElement(By.xpath("//div[contains(@id,'organizer.editor.save.title')]")).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath("//img[contains(@id,'closesession')]")).click();
	}

	public void backToHmcAndChangePaymentProfile_step15(String Name, String xpath_customer, String xpath_ASM) {
		hmcPage.Home_Nemo.click();
		hmcPage.Home_payment.click();

		driver.findElement(By.xpath("//a[contains(@id,'nemo.payment.type.customize')]")).click();
		driver.findElement(By.xpath(".//*[@id='Tree/GenericLeafNode[PaymentTypeProfile]_label']")).click();

		Select select_name = new Select(
				driver.findElement(By.xpath("//select[contains(@id,'[attributeselect][PaymentTypeProfile]')]")));
		select_name.selectByValue("name");

		driver.findElement(By.xpath("//input[contains(@id,'PaymentTypeProfile.name')]")).sendKeys(Name);
		driver.findElement(By.xpath("//span[contains(@id,'searchbutton')]")).click();

		driver.findElement(By.xpath("//img[contains(@id,'Content/OrganizerListEntry')]")).click();

		// set the disable for customer value into no
		if (!driver.findElement(By.xpath(xpath_customer)).isSelected()) {
			driver.findElement(By.xpath(xpath_customer)).click();
		}

		if (!driver.findElement(By.xpath(xpath_ASM)).isSelected()) {
			driver.findElement(By.xpath(xpath_ASM)).click();
		}

		driver.findElement(By.xpath("//div[contains(@id,'organizer.editor.save.title')]")).click();
		driver.findElement(By.xpath("//img[contains(@id,'closesession')]")).click();

	}

	public void logOut() {
		Common.javascriptClick(normaldriver, normaldriver.findElement(By.xpath("//a[contains(@href,'logout')]")));
	}

	public void ASMLogOut() {
		Common.javascriptClick(ASMdriver, ASMdriver.findElement(By.xpath("//button[contains(@class,'ASM_close')]")));
		System.out.println("ASM Logout");
	}

	public void handlePopup() {
		if (Common.isElementExist(driver, By.xpath("//button[@class='mfp-close']"))
				&& driver.findElement(By.xpath("//button[@class='mfp-close']")).isDisplayed()) {
			driver.findElement(By.xpath("//button[@class='mfp-close']")).click();

		}
	}
}
