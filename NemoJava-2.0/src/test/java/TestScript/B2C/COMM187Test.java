package TestScript.B2C;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.Payment;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HACPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class COMM187Test extends SuperTestClass {

	B2CPage b2cPage = null;
	HMCPage hmcPage=null;
	HACPage hacPage=null;
	private String testProduct;
	private String loginID;
	private String pwd;
    private String hacNode="http://10.62.6.9:9001/hac/platform/support";
	private String BRAdress = "SES-Av. das Nações";
	private String BRBuilding = "Quadra 813";
	private String BRCompletement = "Lote 51";
	private String BRRegion = "Brasília-Distrito";
	private String BRCity = "Federal";
	private String BRState = "Brasilia";
    
	public COMM187Test(String store) {
		this.Store = store;
		this.testName = "COMM-187";
	}

	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");

		if (Common.isElementExist(driver, Promotion)) {

			Actions actions = new Actions(driver);

			actions.moveToElement(page.PromotionBanner).click().perform();

		}
	}


	public void HandleJSpring(WebDriver driver, B2CPage b2cPage,String loginID, String pwd) {

		if (!driver.getCurrentUrl().contains("account")) {

			driver.get(testData.B2C.getloginPageUrl());
			closePromotion(driver, b2cPage);
			if (Common.isElementExist(driver, By.xpath(".//*[@id='smb-login-button']"))) {
				b2cPage.SMB_LoginButton.click();
			}
			B2CCommon.login(b2cPage, loginID, pwd);
			B2CCommon.handleGateKeeper(b2cPage, testData);
		}
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA21153(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage=new HMCPage(driver);
			hacPage=new HACPage(driver);
			driver.get(hacNode);
			HACLogin("lisong2","Lelisong2");
			Thread.sleep(3000);
			hacPage.HacHome_PlatformBtn.click();
			hacPage.HacPlatform_ConfigurationBtn.click();	
			Common.waitElementVisible(driver, hacPage.configKey);
			hacPage.configKey.sendKeys("nemo.nemoTransaction.postType.enable.cybersource");
			hacPage.configValue.sendKeys("true");
			hacPage.addKey.click();
			hacPage.applyAll.click();
			Thread.sleep(8000);
			Dailylog.logInfo("Complete HAC setup");
			driver.get("https://10.62.6.9:9002/br/pt/login");
			if (!driver.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
			if (!driver.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
			driver.get("https://10.62.6.9:9002/br/pt/cart");
			B2CCommon.clearTheCart(driver, b2cPage, testData);

			Common.sleep(2000);
			b2cPage.Cart_QuickOrderTextBox.clear();
			b2cPage.Cart_QuickOrderTextBox.sendKeys(testData.B2C.getDefaultMTMPN());
			Common.sleep(1000);

			Common.javascriptClick(driver, b2cPage.Cart_AddButton);
			Common.scrollAndClick(driver, b2cPage.Cart_CheckoutButton);
			Thread.sleep(2000);
			Dailylog.logInfo("click Lenovo checkout on shopping cart page");
			Common.sleep(2000);
			
			
			try {
				b2cPage.Shipping_editAddress.click();
			} catch (Exception e) {
				Dailylog.logInfo("Edit is not present");
			}
			Thread.sleep(3000);
			b2cPage.Shipping_FirstNameTextBox.clear();
			b2cPage.Shipping_FirstNameTextBox.sendKeys("test");
			b2cPage.Shipping_LastNameTextBox.clear();
			b2cPage.Shipping_LastNameTextBox.sendKeys("test");
		

			b2cPage.Shipping_AddressLine1TextBox.clear();
			b2cPage.Shipping_AddressLine1TextBox.sendKeys(BRAdress);
			b2cPage.Shipping_building.clear();
			b2cPage.Shipping_building.sendKeys(BRBuilding);
			b2cPage.Shipping_AddressLine2TextBox.clear();
			b2cPage.Shipping_AddressLine2TextBox.sendKeys(BRCompletement);
			b2cPage.Shipping_district.clear();
			b2cPage.Shipping_district.sendKeys(BRRegion);
			b2cPage.Shipping_CityTextBox.clear();
			b2cPage.Shipping_CityTextBox.sendKeys(BRCity);

			Select select_state = new Select(driver.findElement(By.xpath("//*[@id='state']")));
			select_state.selectByVisibleText(BRState);

			b2cPage.Mobile.clear();
			b2cPage.Mobile.sendKeys("12312412414");
			b2cPage.Shipping_consumerTaxNumber.clear();
			b2cPage.Shipping_consumerTaxNumber.sendKeys("84511773521");

			b2cPage.Shipping_EmailTextBox.clear();
			b2cPage.Shipping_EmailTextBox.sendKeys("lixe1@lenovo.com");
			
			b2cPage.Shipping_PostCodeTextBox.clear();
			b2cPage.Shipping_PostCodeTextBox.sendKeys("70443900");

			b2cPage.Shipping_consumerTaxNumber.clear();
			b2cPage.Shipping_consumerTaxNumber.sendKeys("84511773521");
			b2cPage.Mobile.clear();
			b2cPage.Mobile.sendKeys("12345678901");
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Thread.sleep(3000);
			
			Thread.sleep(2000);
			if (Common.isElementExist(driver, By.xpath("//*[@id='useDeliveryAddress']"))
					&& !driver.findElement(By.xpath("//*[@id='useDeliveryAddress']")).isSelected()
					&& Common.isEditable(driver.findElement(By.xpath("//*[@id='useDeliveryAddress']")))) {
				driver.findElement(By.xpath("//*[@id='useDeliveryAddress']")).clear();
			}
			
			
			Common.javascriptClick(b2cPage.PageDriver, b2cPage.Payment_Boleto);
			
			Payment.clickPaymentContinueButton(b2cPage);

			if (Common.isElementExist(driver, By.xpath("//label[contains(@class,'redesign-term-check')]"))) {
				driver.findElement(By.xpath("//label[contains(@class,'redesign-term-check')]")).click();
			} else {
				driver.findElement(By.xpath("//*[@id='Terms1']")).click();
			}

			Thread.sleep(5000);
			driver.findElement(By.xpath("//*[@id='orderSummaryReview_placeOrder']")).click();

			boolean thankyou = driver
					.findElement(By.xpath("//*[@id='configarea_scroller']//h1[contains(.,'Obrigado')]")).isDisplayed();
			Dailylog.logInfo("thankyou is displayed :" + thankyou);
			Assert.assertTrue(thankyou);
			// 9, check order in hmc
			String orderNum1 = "";
			if (Common.isElementExist(driver,
					By.xpath("div[class='checkout-confirm-orderNumbers'] tr:nth-child(2) td:nth-child(2)"))) {
				orderNum1 = b2cPage.OrderThankyou_OrderNumberLabel.getText();
			} else {
				orderNum1 = driver
						.findElement(By
								.xpath("(//*[contains(text(),'Número de pedido:') or contains(text(),'Número do Pedido')]/../*)[2]"))
						.getText();
			}

			Dailylog.logInfo("orderNum is :" + orderNum1);
			Common.NavigateToUrl(driver, Browser, "https://10.62.6.9:9002/hmc/hybris");
			Thread.sleep(3000);

			if (Common.isElementExist(driver, By.id("Main_user"))) {
				HMCCommon.Login(hmcPage, testData);
			}
            hmcPage.Home_Nemo.click();
            hmcPage.Nemo_transactionlog.click();
            Thread.sleep(5000);
            driver.findElement(By.xpath("//select[contains(@id,'Content/AbstractGenericConditionalSearchToolbarChip$3[attributeselect][NemoTransactionLog]_select')]")).click();
            hmcPage.SearchCriteria_OrderID.click();
			hmcPage.Transactionlog_OrderID.sendKeys(orderNum1);
			hmcPage.Transactionlog_search.click();
			Common.waitElementVisible(driver, hmcPage.firstItemInTransactionLog);
			Common.doubleClick(driver, hmcPage.firstItemInTransactionLog);
			Common.waitElementVisible(driver, hmcPage.configSummary);
			hmcPage.configSummary.getText().contains("ns2:state>DF</ns2:state");
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public boolean isElementExsit(WebDriver driver, By locator) {
		boolean flag = false;
		try {
			WebElement element = driver.findElement(locator);
			flag = null != element;
		} catch (NoSuchElementException e) {
			// System.out.println( "this category has only one page");
		}
		return flag;
	}

	public static String getStringDateShort(int gap) {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, gap);
		date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String dateString = formatter.format(date);
		return dateString;
	}
	private void HACLogin(String username,String pw){
		Common.sendFieldValue(hacPage.HacLogin_UserName, username);
		Common.sendFieldValue(hacPage.HacLogin_UserPassword, pw);
		hacPage.HacLogin_LoginButton.click();
	}
}
