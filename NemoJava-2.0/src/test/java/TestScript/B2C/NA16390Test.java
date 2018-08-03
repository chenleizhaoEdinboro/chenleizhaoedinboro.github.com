package TestScript.B2C;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class NA16390Test extends SuperTestClass {

	B2CPage b2cPage = null;

	Actions actions = null;
	String orderNum = null;
	private String customer;

	private String addressline;
	private String county;
	private String zipcode;
	private String statelocator;
	private boolean flag;
	private String tempBrowser;

	public NA16390Test(String store, String StateXpath) {
		this.Store = store;
		this.testName = "NA-16390";
		this.statelocator = StateXpath;
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "process", "p1", "b2c", "compatibility"})
	public void NA16390(ITestContext ctx) {
		try {
			By ThankYouTitle = By
					.xpath(".//*[contains(text(),'Thank you') or contains(text(),'ご注文ありがとうございました。') or contains(text(),'感謝您')]");
			// Verify if accessory is displayed
			By ASM_PW = By
					.xpath(".//*[@id='asmLoginForm']/fieldset/div[2]/input");
			// Check if visa verification window is displayed
			By Visa_PW = By.xpath("//input[@name='external.field.password']");
			// check if quote details are displayed
			By Approve_reject = By
					.xpath("//button[contains(text(),'Approve/Reject') or contains(text(),'承認/却下') or contains(text(),'核准/拒絕') or contains(text(),'승인/거부') or contains(text(),'Aprovar/Rejeitar')]");
			// Check if convert to order exists
			By Convert_to_order = By
					.xpath("//button[contains(text(),'Convert To Order') or contains(text(),'注文に切り替える') or contains(text(),'轉換至訂單') or contains(text(),'주문으로 변환') or contains(text(),'Continuar com o Pedido')]");
			// check if menu-product is clickable
			By Add_to_Cart = By
					.xpath("//button[contains(text(),'Add to cart') or contains(text(),'Agregar al carrito')]");
			By First_Accessory = By
					.xpath(".//*[@id='productGrid-target']//div[2]/div/div[2]/h2/a");
			By Popup_Add = By
					.xpath("//div[1]/div[2]/div[2]/div[1]/div/div[1]/button[1]");
			By SerialNumber = By
					.xpath("//input[@id='gatekeeper.serialNumber.id']");
			By LenovoID = By.xpath("//input[@id='gatekeeper.login.email.id']");
			By GateKeeperNew = By
					.xpath("//input[@id='gatekeeper.login.email.id']");
			By Promotion = By.xpath(".//*[@title='Close (Esc)']");
			By StartASM = By
					.xpath("//a[contains(text(),'Start Assisted Service Session') or contains(text(),'サポート付きのサービスセッションを開始') or contains(text(),'장바구니 이력 보기 및 저장') or contains(text(),'開始協助服務工作階段') or contains(text(),'Iniciar sesión de servicio asistido') or contains(text(),'Início de Sessão de Serviço Assistido')]");
			By addressOptions = By.xpath("//input[@name='address']");
			By suggestedAddressOption = By
					.xpath(".//*[@id='checkout_validateFrom_ok']");
			By addressLine3 = By.xpath("//input[@id='line3']");
			By invoiceIndicator = By.xpath("//input[@id='carrierCodeTW']");
			By AccessoryProduct = By
					.xpath(".//*[@id='mainContent']//ol/li[3]//div[4]/a");
			By AddtoBasket = By.xpath("(.//*[@id='addToCartButtonTop'])[1]");
			By AddtoCart = By.xpath("//button[@id='addToCart']");
			By LocatorDatePicker = By
					.xpath("//div[@id='ui-datepicker-div']//tr[last()]/td/a");
			By DR_AddtoCart = By.xpath("(.//*[@id='addToCartButtonTop'])[1]");
			By ShallWeConnect = By.id("nm_closebtn");

			Dailylog.logInfo("Test run for " + Store);

			this.prepareTest();
			b2cPage = new B2CPage(driver);

			actions = new Actions(driver);
			float Price;
			String QuoteNo;

			StartAssistingMode(driver, b2cPage);

			closePromotion(driver, b2cPage);

			// search for customer and start session
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath(".//*[@id='customerFilter']")));
			b2cPage.ASM_SearchCustomer.sendKeys("jdamore@lenovo.com");
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.cssSelector("[id^='ui-id-']>a")));
			b2cPage.ASM_CustomerResult.click();
			b2cPage.ASM_StartSession.click();
			Thread.sleep(5000);
			Dailylog.logInfo(Store
					+ "Search customer successfully and start session");

			/*
			 * try { new WebDriverWait(driver, 500).until(ExpectedConditions
			 * .elementToBeClickable(b2cPage.Navigation_ProductsLink));
			 * b2cPage.Navigation_ProductsLink.click(); Thread.sleep(3000);
			 * b2cPage.AccesoryCategory.click(); } catch
			 * (ElementNotVisibleException e) { B2CCommon .NavigateToUrl(
			 * driver, Browser, testData.B2C.getHomePageUrl() +
			 * "/accessories-and-upgrade/accessories/c/ACCESSORY?menu-id=Accessories_Upgrades"
			 * );
			 * 
			 * } catch (TimeoutException e) { B2CCommon .NavigateToUrl( driver,
			 * Browser, testData.B2C.getHomePageUrl() +
			 * "/accessories-and-upgrade/accessories/c/ACCESSORY?menu-id=Accessories_Upgrades"
			 * );
			 * 
			 * }
			 */

			NavigationBar.goToSplitterPageUnderProducts(b2cPage,
					SplitterPage.Accessories);
			Thread.sleep(5000);
			if (Common.isElementExist(driver, ShallWeConnect)) {

				Actions actions = new Actions(driver);

				actions.moveToElement(driver.findElement(By.id("nm_closebtn")))
						.click().perform();

			}
			if (Common.isElementExist(driver, First_Accessory)) {
				new WebDriverWait(driver, 500)
						.until(ExpectedConditions.presenceOfElementLocated(By
								.xpath("//h1[contains(text(),'Accessories') or contains(text(),'周辺機器') or contains(text(),'액세서리와 업그레이드') or contains(text(),'Accesorios')]")));
				b2cPage.FirstAccessoryType.click();
				Thread.sleep(5000);
			} else {
				b2cPage.browseAllCategories.click();
				try{
					Common.javascriptClick(driver, b2cPage.keyBoardandMice);
					// b2cPage.keyBoardandMice.click();
				}catch(Exception e){
					Common.javascriptClick(driver, driver.findElement(By.xpath("//a[contains(text(),'Mice')]")));
				}
				
			}
			Thread.sleep(5000);
			if (Common.isElementExist(driver, AccessoryProduct)) {
				if (Common.isElementExist(driver, AddtoBasket)) {
					Common.javascriptClick(driver, b2cPage.DR_AddtoBasket);
					// b2cPage.DR_AddtoBasket.click();

				} else {
					b2cPage.FirstAccessoryProduct.click();
				}

			} else if (Common.isElementExist(driver, AddtoBasket)) {
				b2cPage.DR_AddtoBasket.click();

			} else {
				b2cPage.FirstAccessoryProduct_KR.click();
			}
			Thread.sleep(5000);
			if (Common.isElementExist(driver, AddtoCart)) {
				b2cPage.Add2Cart.click();
				Thread.sleep(4000);

				// If the website has add to cart poptup,
				if (!Common.isElementExist(driver, Popup_Add)) {
					if (Common.isElementExist(driver, Add_to_Cart)) {
						b2cPage.PopUpAdd2Cart.click();
						Thread.sleep(10000);
						b2cPage.Go2Cart.click();
					}
				} else {
					b2cPage.NAPopUpAdd2Cart.click();
					Thread.sleep(10000);
					b2cPage.NAGo2Cart.click();
				}
			} else {
				Common.NavigateToUrl(
						driver,
						Browser,
						testData.B2C.getHomePageUrl().replace("pre-c-hybris",
								"test-tele-hybris")
								+ "/cart");

			}

			// Override
			Thread.sleep(4000);
			if (Common.isElementExist(b2cPage.PageDriver,
					By.xpath("//button[contains(@class,'Submit_Button')]"))) {
				Price = GetPriceValue(b2cPage.NewCart_SalesPrice.getText()
						.toString().replaceAll("HK", "").replaceAll("SG", "")
						.replace("£", "").replace("€", "").replaceAll("￥", "")
						.replaceAll("NT", "").replaceAll("₩", ""));
				Price = Price - 1;
				b2cPage.OverrideValue.sendKeys((int) (Price) + "");
				b2cPage.OverrideDropdown.click();
				b2cPage.OverrideCheckbox.sendKeys("xxxxx");
				b2cPage.NewCart_UpdatePrice.click();
				System.out.println("Override performs successfully");
				Thread.sleep(5000);
				b2cPage.Override_RequestQuote.click();
				Thread.sleep(6000);
				b2cPage.Override_RepID.clear();
				Thread.sleep(4000);
				b2cPage.Override_RepID.sendKeys("2900718028");
				Thread.sleep(6000);
				b2cPage.Override_SubmitQuote.click();

			} else {
				Price = GetPriceValue(b2cPage.SalesPrice.getText().toString()
						.replaceAll("HK", "").replaceAll("SG", "")
						.replace("£", "").replace("€", "").replaceAll("￥", "")
						.replaceAll("NT", "").replaceAll("₩", ""));
				Price = Price - 1;
				b2cPage.OverrideValue.sendKeys((int) (Price) + "");
				b2cPage.OverrideDropdown.click();
				b2cPage.OverrideCheckbox.sendKeys("xxxxx");
				b2cPage.OverrideUpdate.click();
				System.out.println("Override performs successfully");
				Thread.sleep(5000);
				b2cPage.Override_RequestQuote.click();
				Thread.sleep(6000);
				b2cPage.Override_RepID.clear();
				Thread.sleep(4000);
				b2cPage.Override_RepID.sendKeys("2900718028");
				Thread.sleep(6000);
				b2cPage.Override_SubmitQuote.click();
			}

			Thread.sleep(6000);
			QuoteNo = b2cPage.Override_QuoteNumber.getText().toString();
			Dailylog.logInfo(Store + " quote number is " + QuoteNo);
			b2cPage.Tele_Endsession.click();
			Thread.sleep(5000);
			if (Common.isAlertPresent(driver)) {
				Alert alert = driver.switchTo().alert();
				alert.accept();
			}
			// b2cPage.SignoutASM.click();
			Thread.sleep(5000);
			/*
			 * driver.quit(); System.setProperty("webdriver.chrome.driver",
			 * "src/test/resources/chromedriver.exe"); driver = new
			 * ChromeDriver(); b2cPage = new B2CPage(driver); tempBrowser =
			 * this.Browser; this.Browser = "chrome"; // actions = new
			 * Actions(driver); StartAssistingMode(driver, b2cPage);
			 * 
			 * closePromotion(driver, b2cPage);
			 */
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath(".//*[@id='customerFilter']")));
			b2cPage.TransactionID.sendKeys(QuoteNo);
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By.cssSelector("[id^='Q']>a")));
			b2cPage.ASM_QuoteResult.click();
			Thread.sleep(3000);
			b2cPage.ASM_StartSession.click();
			Thread.sleep(6000);

			if (Common
					.isElementExist(
							driver,
							By.xpath("//button[contains(text(),'Approve/Reject') or contains(text(),'承認/却下') or contains(text(),'核准/拒絕')]"))) {
				new WebDriverWait(driver, 500)
						.until(ExpectedConditions.presenceOfElementLocated(By
								.xpath("//button[contains(text(),'Approve/Reject') or contains(text(),'承認/却下') or contains(text(),'核准/拒絕')]")));
				b2cPage.ASM_ApproveOrReject.click();
				Thread.sleep(5000);
				b2cPage.ASM_ApproveComment.sendKeys("xxxx");
				Thread.sleep(2000);
				b2cPage.ASM_PopupReject.click();
				Thread.sleep(3000);
				new WebDriverWait(driver, 500).until(ExpectedConditions
						.presenceOfElementLocated(By
								.xpath("//button[@id='quoteStatusChange']")));
				b2cPage.ASM_ApproveButton.click();
				Thread.sleep(5000);
				b2cPage.ASM_ApproveComment.sendKeys("approve");
				Thread.sleep(2000);
				b2cPage.ASM_PopupApprove.click();
				Thread.sleep(5000);
				b2cPage.Tele_Endsession.click();
				Thread.sleep(5000);
				if (Common.isAlertPresent(driver)) {
					Alert alert = driver.switchTo().alert();
					alert.accept();
				}
				/*
				 * b2cPage.SignoutASM.click(); Thread.sleep(10000);
				 * driver.quit(); System.setProperty("webdriver.chrome.driver",
				 * "src/test/resources/chromedriver.exe"); driver = new
				 * ChromeDriver(); b2cPage = new B2CPage(driver); actions = new
				 * Actions(driver); StartAssistingMode(driver, b2cPage);
				 * closePromotion(driver, b2cPage);
				 */
				new WebDriverWait(driver, 500).until(ExpectedConditions
						.presenceOfElementLocated(By
								.xpath(".//*[@id='customerFilter']")));
				b2cPage.TransactionID.sendKeys(QuoteNo);
				new WebDriverWait(driver, 500)
						.until(ExpectedConditions.presenceOfElementLocated(By
								.cssSelector("[id^='Q']>a")));
				b2cPage.ASM_QuoteResult.click();
				Thread.sleep(3000);
				b2cPage.ASM_StartSession.click();

			}
			Thread.sleep(5000);
			if (!Common.isElementExist(driver, Convert_to_order)) {
				driver.navigate().refresh();
				Thread.sleep(5000);
				b2cPage.TransactionID.sendKeys(QuoteNo);
				new WebDriverWait(driver, 500)
						.until(ExpectedConditions.presenceOfElementLocated(By
								.cssSelector("[id^='Q']>a")));
				b2cPage.ASM_QuoteResult.click();
				Thread.sleep(3000);
				b2cPage.ASM_StartSession.click();
				Thread.sleep(5000);
			}
			// covert and buy

			b2cPage.ASM_ConvertToOrder.click();
			Thread.sleep(8000);
			if (!Store.equals("GB") && !Store.equals("IE")) {
                B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
                Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
                Common.sleep(5000);
				B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
				
				if (Common.isElementExist(driver,
						By.xpath("//button[@class='billing_section_continue_button']"))) {
					driver.findElement(
							By.xpath("//button[@class='billing_section_continue_button']"))
							.click();
				}else{
					 Common.javascriptClick(driver,	b2cPage.Payment_ContinueButton);
				}
				Thread.sleep(6000);
				Common.javascriptClick(driver,
						b2cPage.OrderSummary_AcceptTermsCheckBox);
				// b2cPage.OrderSummary_AcceptTermsCheckBox.click();
				B2CCommon.clickPlaceOrder(b2cPage);
				Thread.sleep(8000);
				// order confirmation

				assert Common.isElementExist(driver, ThankYouTitle);
				Dailylog.logInfoDB(1, "orderNum:" + QuoteNo, Store, testName);

			} else {
				// DigitalRiverPayment(driver,b2cPage);
				// assert b2cPage.DR_OrderNumber.isDisplayed();
				Dailylog.logInfo(Store
						+ "DR countries doesn't support quote to order");

			}
			this.Browser = tempBrowser;
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");

		if (Common.isElementExist(driver, Promotion)) {

			Actions actions = new Actions(driver);

			actions.moveToElement(page.PromotionBanner).click().perform();

		}
	}

	public float GetPriceValue(String Price) {
		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("€", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.trim();
		float priceValue;
		priceValue = Float.parseFloat(Price);
		return priceValue;
	}

	public void StartAssistingMode(WebDriver driver, B2CPage b2cPage)
			throws InterruptedException {
		By StartASM = By
				.xpath("//a[contains(text(),'Start Assisted Service Session') or contains(text(),'サポート付きのサービスセッションを開始') or contains(text(),'장바구니 이력 보기 및 저장') or contains(text(),'開始協助服務工作階段') or contains(text(),'Iniciar sesión de servicio asistido') or contains(text(),'Início de Sessão de Serviço Assistido')]");

		By ASM_PW = By.xpath(".//*[@id='asmLoginForm']/fieldset/div[2]/input");
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");
		Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl()
				.replace("pre-c-hybris", "test-tele-hybris") + "/login");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		B2CCommon.handleGateKeeper(b2cPage, testData);
		Thread.sleep(2000);
		closePromotion(driver, b2cPage);
		if (Common.isElementExist(driver,
				By.xpath(".//*[@id='smb-login-button']"))) {
			b2cPage.SMB_LoginButton.click();
		}
		B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(),
				testData.B2C.getTelesalesPassword());
		HandleJSpring(driver);
		closePromotion(driver, b2cPage);
		B2CCommon.handleGateKeeper(b2cPage, testData);
		if (Common.isElementExist(driver, StartASM)) {
			b2cPage.StartASM.click();
		} else {
			Common.NavigateToUrl(driver, Browser, driver.getCurrentUrl()
					+ "/my-account");
			System.out.println(driver.getCurrentUrl());
			Thread.sleep(5000);
			b2cPage.StartASM.click();
		}
		Thread.sleep(3000);
		if (Common.isElementExist(driver, Promotion)) {
			b2cPage.PromotionBanner.click();
		}
		if (Common.isElementExist(driver, ASM_PW)) {
			new WebDriverWait(driver, 500)
					.until(ExpectedConditions.presenceOfElementLocated(By
							.xpath(".//*[@id='asmLoginForm']/fieldset/div[2]/input")));
			b2cPage.ASM_password.sendKeys(testData.B2C.getTelesalesPassword());
			b2cPage.ASM_signin.click();
		}
	}

	public void DigitalRiverPayment(WebDriver driver, B2CPage b2cPage) {
		By SkipRegistration = By.xpath(".//*[@id='vr_skipregistration']");
		Actions actions = new Actions(driver);
		b2cPage.DR_Email.clear();
		b2cPage.DR_Email.sendKeys("Lixe1@Lenovo.com");
		b2cPage.DR_FirstName.clear();
		b2cPage.DR_FirstName.sendKeys("xxxxxxxxxxxxxxxx");
		b2cPage.DR_LastName.clear();
		b2cPage.DR_LastName.sendKeys("xxxxxxxxxxxxxxxx");
		b2cPage.DR_AddressLine1.clear();
		b2cPage.DR_AddressLine1.sendKeys(testData.B2C.getDefaultAddressLine1());
		b2cPage.DR_City.clear();
		b2cPage.DR_City.sendKeys(testData.B2C.getDefaultAddressCity());
		b2cPage.DR_PostCode.clear();
		b2cPage.DR_PostCode.sendKeys(testData.B2C.getDefaultAddressPostCode());
		WebElement state = driver.findElement(By.xpath(statelocator));
		state.click();
		b2cPage.DR_PhoneNumber.clear();
		b2cPage.DR_PhoneNumber.sendKeys("12412431");
		actions.moveToElement(b2cPage.DR_CreditCard).click().perform();
		b2cPage.DR_CreditCardNumber.clear();
		b2cPage.DR_CreditCardNumber.sendKeys("4111111111111111");
		b2cPage.DR_CardExpirationMonth.click();
		b2cPage.DR_CardExpirationYear.click();
		;
		b2cPage.DR_SecurityCode.sendKeys("132");
		b2cPage.DR_Continue.click();
		if (Common.isElementExist(driver, SkipRegistration)) {
			b2cPage.DR_SkipRegistration.click();
		}
		b2cPage.DR_AgreetoTerms.click();
		b2cPage.DR_BuyNow.click();

	}

	public void HandleJSpring(WebDriver driver) {

		if (driver.getCurrentUrl().contains("j_spring_security_check")) {

			driver.get(driver.getCurrentUrl().replace(
					"j_spring_security_check", "login"));
			closePromotion(driver, b2cPage);
			if (Common.isElementExist(driver,
					By.xpath(".//*[@id='smb-login-button']"))) {
				b2cPage.SMB_LoginButton.click();
			}
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(),
					testData.B2C.getTelesalesPassword());
			B2CCommon.handleGateKeeper(b2cPage, testData);
		}
	}
}
