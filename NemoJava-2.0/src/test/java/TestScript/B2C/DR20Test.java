package TestScript.B2C;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

public class DR20Test extends SuperTestClass {

	public String MTMPartNumber;
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	Actions actions;
	public String Accessories;
	public String MTMPrice;

	public DR20Test(String store, String MTMPartNumber, String Accessories) {
		this.Store = store;
		this.MTMPartNumber = MTMPartNumber;
		this.Accessories = Accessories;
		this.testName = "DR-20";
	}


	@Test(alwaysRun = true, groups = {"contentgroup", "dr", "p2", "b2c"})
	public void DR17(ITestContext ctx) {

		try {
			this.prepareTest();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			actions = new Actions(driver);

			// 1, login HMC
			driver.get(testData.HMC.getHomePageUrl());
			Thread.sleep(3000);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("window.open('"
					+ testData.B2C.getHomePageUrl() + "')");
			Set<String> winHandels = driver.getWindowHandles();
			List<String> it = new ArrayList<String>(winHandels);
			driver.switchTo().window(it.get(0));

			hmcPage = new HMCPage(driver);
			HMCCommon.Login(hmcPage, testData);

			// 2, DR Site configuration
			hmcPage.Home_B2CCommercelink.click();
			hmcPage.Home_B2CUnitLink.click();
			hmcPage.B2CUnit_IDTextBox.clear();
			hmcPage.B2CUnit_IDTextBox.sendKeys(testData.B2C.getUnit());
			hmcPage.B2CUnit_SearchButton.click();

			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();

			boolean isDigitalRiverSelected = driver
					.findElement(
							By.xpath("//*[contains(@id,'B2CUnit.isDigitalRiverCountry]]_true')]"))
					.isSelected();
			System.out.println("isDigitalRiverSelected :"
					+ isDigitalRiverSelected);
			if (!isDigitalRiverSelected) {
				driver.findElement(
						By.xpath("//*[contains(@id,'B2CUnit.isDigitalRiverCountry]]_true')]"))
						.click();
			}
			hmcPage.B2BUnit_Save.click();
			boolean isDigitalRiverSelected_verify = driver
					.findElement(
							By.xpath("//*[contains(@id,'B2CUnit.isDigitalRiverCountry]]_true')]"))
					.isSelected();
			Assert.assertTrue(isDigitalRiverSelected_verify);

			// 3, DR Site ID Configuration
			driver.findElement(
					By.xpath("//*[@id='Tree/GenericExplorerMenuTreeNode[group.basecommerce]_label']"))
					.click();
			driver.findElement(
					By.xpath("//*[@id='Tree/GenericLeafNode[BaseStore]_label']"))
					.click();
			driver.findElement(
					By.xpath("//*[@id='Content/StringEditor[in Content/GenericCondition[BaseStore.uid]]_input']"))
					.clear();
			driver.findElement(
					By.xpath("//*[@id='Content/StringEditor[in Content/GenericCondition[BaseStore.uid]]_input']"))
					.sendKeys(testData.B2C.getUnit());
			driver.findElement(
					By.xpath(".//*[@id='Content/NemoSearchConfigurable[BaseStore]_searchbutton']"))
					.click();

			WebElement corres_unit = driver.findElement(By
					.xpath("//*[@id='Content/StringDisplay["
							+ testData.B2C.getUnit() + "]_span']"));
			actions.doubleClick(corres_unit).perform();
			driver.findElement(
					By.xpath("//*[@id='Content/EditorTab[BaseStore.administration]_span']"))
					.click();
			driver.findElement(
					By.xpath("//*[contains(@id,'digitalRiverSiteID')]"))
					.clear();
			driver.findElement(
					By.xpath("//*[contains(@id,'digitalRiverSiteID')]"))
					.sendKeys("lenovoeu");
			hmcPage.B2BUnit_Save.click();
	
			// 4,login site
			// switchToOtherWindow(winhandow_hmc);
			driver.switchTo().window(it.get(1));
			driver.get(testData.B2C.getloginPageUrl());
			if (!driver.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
			if (!driver.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
			// after the login , get the homepage url immediately
			// get the cart url and empty the cart
			String cartUrl = testData.B2C.getHomePageUrl() + "/cart";
			driver.get(cartUrl);

			Thread.sleep(5000);
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			b2cPage.Cart_QuickOrderTextBox.sendKeys(MTMPartNumber);
			b2cPage.Cart_AddButton.click();
			b2cPage.Cart_QuickOrderTextBox.sendKeys(Accessories);
			b2cPage.Cart_AddButton.click();
			b2cPage.Cart_CheckoutButton.click();
			Dailylog.logInfoDB(1, "Lenovo Checkout", Store, testName);
			
			
			driver.switchTo().window(it.get(0));
			if (Common.isElementExist(driver, By.id("Main_user"))) {
				HMCCommon.Login(hmcPage, testData);
				driver.navigate().refresh();
			}
			hmcPage.Home_Nemo.click();
			hmcPage.NEMO_digitalRiver.click();
			hmcPage.NEMO_digitalRiver_tracelog.click();
			hmcPage.NEMO_digitalRiver_tracelogID.sendKeys(testData.B2C
					.getLoginID());

			hmcPage.NEMO_digitalRiver_tracelogCountry.click();
			if (Store.equals("GB")) {
				hmcPage.NEMO_digitalRiver_tracelogCountry_GB.click();
			} else if (Store.equals("FR")) {
				hmcPage.NEMO_digitalRiver_tracelogCountry_FR.click();
			} else if (Store.equals("IE")) {
				hmcPage.NEMO_digitalRiver_tracelogCountry_IE.click();
			}

			Thread.sleep(5000);
			hmcPage.NEMO_digitalRiver_search.click();
			Thread.sleep(5000);
			hmcPage.NEMO_digitalRiver_creationtime_sort.click();
			Common.doubleClick(driver,
					hmcPage.NEMO_digitalRiver_search_firstResultItem);
			
			String cartID = driver
					.findElement(
							By.xpath("//*[contains(@id,'DigitalRiverTraceLog.cartID')]"))
					.getAttribute("value").toString();
			System.out.println("cartID is :" + cartID);
			String xmlcontent = hmcPage.NEMO_digitalRiver_search_xml.getText();

			Assert.assertTrue(xmlcontent.contains(MTMPartNumber),"xml content doesn't contains " + MTMPartNumber);
			Assert.assertTrue(xmlcontent.contains(Accessories),"xml content doesn't contains " + Accessories);
			
			
			
			// 3 , PLACE ORDER
			driver.switchTo().window(it.get(1));
			
			b2cPage.DR_emailTxtBox.sendKeys(testData.B2C.getLoginID());
			b2cPage.DR_fNameTxtBox.sendKeys("TESTdr20");
			b2cPage.DR_lNameTxtBox.sendKeys("TESTdr20");
			b2cPage.DR_addLine1TxtBox.sendKeys(testData.B2C
					.getDefaultAddressLine1());
			b2cPage.DR_cityTxtBox
					.sendKeys(testData.B2C.getDefaultAddressCity());
			b2cPage.DR_countryDD.click();

			// if(Store.equals("FR")){
			// b2cPage.DR_frOption.click();
			// }else{
			// b2cPage.DR_ukOption.click();
			// }

			b2cPage.DR_phoneNumTxtBox.sendKeys(testData.B2C
					.getDefaultAddressPhone());
			b2cPage.DR_PostCode.sendKeys(testData.B2C
					.getDefaultAddressPostCode());
			b2cPage.DR_ccChkBox.click();
			b2cPage.DR_ccNumberTxtBox.sendKeys("4111111111111111");
			b2cPage.DR_monthDD.click();
			b2cPage.DR_selectedMonthOption.click();
			b2cPage.DR_yearDD.click();
			b2cPage.DR_selectedYearOption.click();
			b2cPage.DR_securityCodeTxtBox.sendKeys("123");
			b2cPage.DR_continueBtn.click();

			Thread.sleep(1000);

			b2cPage.DR_tncChkBox.click();
			b2cPage.DR_submitBtn.click();
			Thread.sleep(1000);

			String orderNum = b2cPage.DR_orderNum.getText();
			System.out.println("orderNum is :" + orderNum);

			Dailylog.logInfoDB(9, "Place order", Store, testName);
			// 9, xml check
			driver.switchTo().window(it.get(0));
			if (Common.isElementExist(driver, By.id("Main_user"))) {
				HMCCommon.Login(hmcPage, testData);
				driver.navigate().refresh();
			}
			hmcPage.Home_Nemo.click();
			hmcPage.NEMO_digitalRiver.click();
			hmcPage.NEMO_digitalRiver_tracelog.click();
			hmcPage.NEMO_digitalRiver_tracelogID.sendKeys(testData.B2C
					.getLoginID());

			hmcPage.NEMO_digitalRiver_tracelogCountry.click();
			if (Store.equals("GB")) {
				hmcPage.NEMO_digitalRiver_tracelogCountry_GB.click();
			} else if (Store.equals("FR")) {
				hmcPage.NEMO_digitalRiver_tracelogCountry_FR.click();
			} else if (Store.equals("IE")) {
				hmcPage.NEMO_digitalRiver_tracelogCountry_IE.click();
			}

			Thread.sleep(5000);
			hmcPage.NEMO_digitalRiver_search.click();
			Thread.sleep(5000);
			hmcPage.NEMO_digitalRiver_creationtime_sort.click();
			
			Common.rightClick(driver, hmcPage.NEMO_digitalRiver_search_firstResultItem);
			Thread.sleep(2000);
			hmcPage.DigitalRiverTraceLog_actions.click();
			hmcPage.DigitalRiverTraceLog_exportdrtracelogcsv.click();
			
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}


	public void switchToOtherWindow(String windowHandle) {
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			if (!window.equals(windowHandle)) {
				break;
			}
		}
	}




}
