package TestScript.B2C;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class NA23698Test extends SuperTestClass {

	B2CPage b2cPage = null;
	Actions actions = null;
	String orderNum = null;
	JavascriptExecutor js ;

	private boolean flag;
	
	public String homePageUrl;
	public String loginUrl;
	public String cartUrl;
	
	public String hmcLoginUrl;
	public String hmcHomePageUrl;

	public NA23698Test(String store) {
		this.Store = store;
		this.testName = "NA-23698";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "telesales", "p1", "b2c"})
	public void NA23698(ITestContext ctx) {
		try {
			this.prepareTest();
			
			homePageUrl = testData.B2C.getTeleSalesUrl();
			loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			
			hmcLoginUrl = testData.HMC.getHomePageUrl();
			hmcHomePageUrl = testData.HMC.getHomePageUrl();
			
			
			DecimalFormat df = new DecimalFormat("#.00");
			
			By by_ASM_CustomerResult =By.xpath(".//*[contains(@id,'ui-id')]/a/span[contains(text(),'"+testData.B2C.getLoginID()+"')]");
			b2cPage = new B2CPage(driver);
			actions = new Actions(driver);
			js = (JavascriptExecutor) driver;
			String MTMPN = testData.B2C.getDefaultMTMPN();
			By ThankYouTitle = By
					.xpath(".//*[contains(text(),'Thank you') or contains(text(),'ご注文ありがとうございました。') or contains(text(),'感謝您')]");
			// Verify if accessory is displayed
			By ASM_PW = By
					.xpath(".//*[@id='asmLoginForm']/fieldset/div[2]/input");
			// Check if visa verification window is displayed
			By Visa_PW = By.xpath("//input[@name='external.field.password']");
			// check if quote details are displayed
			By Approve_reject = By
					.xpath("//button[contains(text(),'Approve/Reject') or contains(text(),'承認/却下') or contains(text(),'核准/拒絕') or contains(text(),'승인/거부')]");
			// Check if convert to order exists
			By Convert_to_order = By
					.xpath("//button[contains(text(),'Convert To Order') or contains(text(),'注文に切り替える') or contains(text(),'轉換至訂單') or contains(text(),'주문으로 변환')]");
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
					.xpath("//a[contains(text(),'Start Assisted Service Session') or contains(text(),'サポート付きのサービスセッションを開始') or contains(text(),'장바구니 이력 보기 및 저장') or contains(text(),'開始協助服務工作階段') or contains(text(),'Iniciar sesión de servicio asistido')]");
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
			driver.get(loginUrl);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			StartAssistingMode(driver,b2cPage);
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath(".//*[@id='customerFilter']")));
			b2cPage.ASM_SearchCustomer.sendKeys(testData.B2C.getLoginID());
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(by_ASM_CustomerResult));
			driver.findElement(by_ASM_CustomerResult).click();
			b2cPage.ASM_StartSession.click();
			Thread.sleep(5000);
			Dailylog.logInfoDB(1,Store+ "Search customer successfully and start session",Store,testName);
			
			
		    driver.get(B2CCommon.getCTOProduct(homePageUrl,Store)+"/customize?");
		    System.out.println(driver.getCurrentUrl());
		    Thread.sleep(16000);
		    int a=0;
			while(!Common.isElementExist(driver, By.xpath(".//*[@id='render-summary']//span[@id='CTO_addToCart']")) 
					&& a<3 ){
				
				driver.get(driver.getCurrentUrl()+"/customize?");
				Thread.sleep(8000);
				a++;																							
			}
		    if(Common.isElementExist(driver, By.xpath("//button[text()='Customize']")) && driver.findElement(By.xpath("//button[text()='Customize']")).isDisplayed()){
				System.out.println("here is ------");
				driver.findElement(By.xpath("(//button[@class='close' and text()='×'])[2]")).click();
				Thread.sleep(5000);
			}
		    
		    priceOverwriteCV();
		    driver.findElement(By.xpath(".//*[@id='render-summary']//span[@id='CTO_addToCart']")).click();
			Thread.sleep(5000);
			priceOverwritePB();
			
			
			driver.get(homePageUrl+"/p/"+MTMPN);
			System.out.println(driver.getCurrentUrl());
			b2cPage.PDP_ViewCurrentModelTab.click();
			Common.sleep(3000);
			b2cPage.PDP_AddToCartButton_2.click();
			Common.sleep(5000);
			priceOverwritePB();
			
			
			Common.sleep(10000);
			priceOverwriteCart();
			System.out.println("Override performs successfully");
			Thread.sleep(5000);
			b2cPage.Override_RequestQuote.click();
			Thread.sleep(6000);
			b2cPage.Override_RepID.clear();
			Thread.sleep(4000);
			b2cPage.Override_RepID.sendKeys("2900718028");
			Thread.sleep(6000);
			b2cPage.Override_SubmitQuote.click();
			Thread.sleep(6000);
			String QuoteNo = b2cPage.Override_QuoteNumber.getText().toString();
			Dailylog.logInfo(Store + " quote number is " + QuoteNo);
			
			b2cPage.SignoutASM.click();
			driver.manage().deleteAllCookies();
			
			Thread.sleep(10000);
			driver.get(loginUrl);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			
			StartAssistingMode(driver, b2cPage);

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
			if (!Common.isElementExist(driver, Approve_reject)) {
				driver.navigate().refresh();
				Thread.sleep(5000);
				b2cPage.TransactionID.sendKeys(QuoteNo);
				new WebDriverWait(driver, 500)
						.until(ExpectedConditions.presenceOfElementLocated(By
								.cssSelector("[id^='Q']>a")));
				b2cPage.ASM_QuoteResult.click();
				Thread.sleep(3000);
				b2cPage.ASM_StartSession.click();
			}
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
			Thread.sleep(3000);
			b2cPage.SignoutASM.click();
			driver.manage().deleteAllCookies();
			
			Thread.sleep(10000);
			driver.get(testData.B2C.getloginPageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			
			StartAssistingMode(driver, b2cPage);
		
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath(".//*[@id='customerFilter']")));
			b2cPage.TransactionID.sendKeys(QuoteNo);
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By.cssSelector("[id^='Q']>a")));
			b2cPage.ASM_QuoteResult.click();
			Thread.sleep(3000);
			b2cPage.ASM_StartSession.click();
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
				new WebDriverWait(driver, 500).until(ExpectedConditions
						.elementToBeClickable(b2cPage.ASM_EditAddress));
				By locator11 = By
						.xpath(".//*[@id='checkoutForm-shippingContinueButton']");
				By locator12 = By.xpath("//input[@value='ok']");
				By locator13 = By.xpath("//input[@id='copyAddressToBilling']");
				Actions actions = new Actions(driver);
				new WebDriverWait(driver, 500).until(ExpectedConditions
						.elementToBeClickable(b2cPage.ASM_EditAddress));
				if (Common.isElementExist(driver, locator13)) {
					actions.moveToElement(b2cPage.CopyAddress).click()
							.perform();
				}
				b2cPage.ASM_EditAddress.click();
				Thread.sleep(5000);
				b2cPage.Shipping_FirstNameTextBox.clear();
				b2cPage.Shipping_FirstNameTextBox.sendKeys("Shane88888888");
				b2cPage.Shipping_LastNameTextBox.clear();
				b2cPage.Shipping_LastNameTextBox.sendKeys("Li88888888");
				b2cPage.Shipping_AddressLine1TextBox.clear();
				b2cPage.Shipping_AddressLine1TextBox.sendKeys(testData.B2C
						.getDefaultAddressLine1());
				if (Common.isElementExist(driver, addressLine3)) {
					b2cPage.Shipping_AddressLine3TextBox.clear();
					b2cPage.Shipping_AddressLine3TextBox.sendKeys(testData.B2C
							.getDefaultAddressCity());
				} else {
					b2cPage.ASM_City.clear();
					b2cPage.ASM_City.sendKeys(testData.B2C
							.getDefaultAddressCity());
					b2cPage.Shipping_PostCodeTextBox.clear();
					b2cPage.Shipping_PostCodeTextBox.sendKeys(testData.B2C
							.getDefaultAddressPostCode());
				}
				
				WebElement state = driver.findElement(By.xpath(".//*[@id='state']/option[(text()='"+testData.B2C.getDefaultAddressState()+"')]"));
				state.click();
				b2cPage.Mobile.clear();
				b2cPage.Mobile.sendKeys("2022022020");
				b2cPage.Shipping_EmailTextBox.clear();
				b2cPage.Shipping_EmailTextBox.sendKeys("lixe1@lenovo.com");

				Thread.sleep(3000);
				((JavascriptExecutor) driver).executeScript("scroll(0,300)");

				actions.moveToElement(b2cPage.standardShipping).click()
						.perform();

				Thread.sleep(5000);

				actions.moveToElement(b2cPage.Shipping_ContinueButton).click()
						.perform();
				Thread.sleep(12000);
				if (Common.isElementExist(driver, locator12)) {
					if (Common.isElementExist(driver, addressOptions)) {
						b2cPage.Shipping_AddressOptionsList.click();
					}
					b2cPage.addressValidation.click();
					Thread.sleep(6000);
				}
				if (Common.isElementExist(driver, suggestedAddressOption)) {
					b2cPage.Shipping_SuggestedAddress.click();
					Thread.sleep(3000);
				}
				while (Common.isElementExist(driver, locator11)) {
					actions.moveToElement(b2cPage.Shipping_ContinueButton)
							.click().perform();
					Thread.sleep(5000);
				}
				Thread.sleep(5000);
				driver.switchTo().frame(b2cPage.Payment_CreditCardFrame);

				// if(!b2cPage.CreditCard.isSelected()){b2cPage.CreditCard.click();}
				b2cPage.Visa.click();
				b2cPage.Payment_CardNumberTextBox.sendKeys("4111111111111111");
				b2cPage.Payment_CardMonthTextBox.sendKeys("06");
				b2cPage.Payment_CardYearTextBox.sendKeys("20");
				b2cPage.Payment_SecurityCodeTextBox.sendKeys("132");
				driver.switchTo().defaultContent();

				b2cPage.Payment_CardHolderNameTextBox.sendKeys("LIXE");
				// System.out.println(countryweb+" "+Common.isElementExist(driver,
				// invoiceIndicator));
				if (Common.isElementExist(driver, invoiceIndicator)) {
					b2cPage.Payment_invoiceType.click();

				}
				if (b2cPage.purchaseNum.isDisplayed() && b2cPage.purchaseNum.isEnabled()) {
					b2cPage.purchaseNum.sendKeys("1234567654");
					SimpleDateFormat dataFormat = new SimpleDateFormat("MM/dd/YYYY");
					b2cPage.purchaseDate
							.sendKeys(dataFormat.format(new Date()).toString());
					if (Common.isElementExist(driver,
							By.xpath("//div[@id='ui-datepicker-div']//tr[last()]/td/a"))) {
						driver.findElements(LocatorDatePicker)
								.get(driver.findElements(LocatorDatePicker).size() - 1)
								.click();
					}
				}
				Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
				//b2cPage.Payment_ContinueButton.click();
				Thread.sleep(3000);
				if (Common.isElementExist(driver, Visa_PW)) {
					b2cPage.VisaPassword.sendKeys("1234");
					b2cPage.VisaSubmit.click();
				}
				if (Common.isElementExist(b2cPage.PageDriver,
						By.id("hidden3DSFrame0"))) {
					B2CCommon.handleVisaVerify(b2cPage);

				}
				Thread.sleep(6000);

				b2cPage.OrderSummary_AcceptTermsCheckBox.click();
				B2CCommon.clickPlaceOrder(b2cPage);
				Thread.sleep(8000);
				// order confirmation

				assert Common.isElementExist(driver, ThankYouTitle);
				Dailylog.logInfo(Store + " Order number is " + QuoteNo);
			}

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
		Price = Price.replaceAll("\\$", "").replaceAll("HK", "").replaceAll("SG", "").replace("£", "")
				.replace("€", "").replaceAll("￥", "").replaceAll("NT", "")
				.replaceAll("₩", "");
		Price = Price.replaceAll("CAD", "");
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
				.xpath("//a[contains(text(),'Start Assisted Service Session') or contains(text(),'サポート付きのサービスセッションを開始') or contains(text(),'장바구니 이력 보기 및 저장') or contains(text(),'開始協助服務工作階段') or contains(text(),'Iniciar sesión de servicio asistido')]");

		By ASM_PW = By.xpath(".//*[@id='asmLoginForm']/fieldset/div[2]/input");
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");
		
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
	}
	
	public void priceOverwriteCV() throws Exception{
		if(Common.isElementExist(driver, By.xpath(".//*[text()='CHANGE' or text()='変更'or text()='更改']"))){
			Dailylog.logInfoDB(1,"new UI" , Store, testName);
			priceOverNewCV();	
		}
		else {
			Dailylog.logInfoDB(1,"Old UI" , Store, testName);
			priceOverOldCV();	
		}
	}
	
	public void priceOverNewCV() throws Exception{
		
		if(Common.isElementExist(driver, By.xpath("(//div[contains(@id,'list_group_NB') and @data-showtab = 'true']/div[contains(@class,'sectionRow')]/button[contains(@class,'qa-configurator-sectionCollapseButton')]/span)[1]")) && driver.findElement(By.xpath("(//div[contains(@id,'list_group_NB') and @data-showtab = 'true']/div[contains(@class,'sectionRow')]/button[contains(@class,'qa-configurator-sectionCollapseButton')]/span)[1]")).isDisplayed()){
			driver.findElement(By.xpath("(//div[contains(@id,'list_group_NB') and @data-showtab = 'true']/div[contains(@class,'sectionRow')]/button[contains(@class,'qa-configurator-sectionCollapseButton')]/span)[1]")).click();
		}
		Thread.sleep(5000);
		b2cPage.CTOConfigurator_Change.click();
		float beforeValue =  GetPriceValue(	b2cPage.NewCTOConfigurator_OverPrice.getAttribute("value"));
		float afterValue =  beforeValue-1;
		DecimalFormat df = new DecimalFormat("#.00");
		String message = "auto test";
		b2cPage.NewCTOConfigurator_OverPrice.clear();
		b2cPage.NewCTOConfigurator_OverPrice.sendKeys(df.format(afterValue));
		b2cPage.NewCTOConfigurator_OverReason.sendKeys(message);
		b2cPage.NewCTOConfigurator_OverReasonSelect.click();
		b2cPage.NewCTOConfigurator_OverReasonSet.click();
		Common.sleep(5000);
	}
	
	public void priceOverOldCV(){
		Common.sleep(5000);
		float beforeValue =  GetPriceValue(	b2cPage.CTOConfigurator_OverPrice.getAttribute("value"));
		float afterValue =  beforeValue-1;
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println(df.format(afterValue));
		String message = "auto test";
		b2cPage.CTOConfigurator_OverPrice.clear();
		b2cPage.CTOConfigurator_OverPrice.sendKeys(df.format(afterValue));
		b2cPage.CTOConfigurator_OverReason.sendKeys(message);
		b2cPage.CTOConfigurator_OverReasonSelect.click();
		b2cPage.CTOConfigurator_OverReasonSet.click();
		Common.sleep(5000);
	}
	
	public void priceOverNewPB() throws Exception{
		//Common.sleep(10000);
		String by_Change = "//*[text()='CHANGE' or text()='変更'or text()='更改']";
		String by_list = "//div[contains(@class,'configuratorItem-accessories-wrapper')]/div";
		List<WebElement> list = driver.findElements(By.xpath("//div[contains(@class,'configuratorItem-accessories-wrapper')]/div"));
		List<WebElement> tab = driver.findElements(By.xpath("//*[@id='jsStepsItem']/li//span"));
		int tabList = list.size();
		int j;
		System.out.println("tablist is "+ tabList);
		System.out.println("first list is diaplay: "+driver.findElement(By.xpath("//div[contains(@class,'configuratorItem-accessories-wrapper')]/div")).isDisplayed());
			for(int i = 1;i < tabList ;i++){
			j= i+1;
			System.out.println("the list number is "+j);
			if(tab.size()==list.size()){
				tab.get(i).click();
			}else{
				tab.get(i-1).click();
			}
			//System.out.println("("+by_list+")["+j+"]"+by_Change);
			if(Common.isElementExist(driver,By.xpath("("+by_list+")["+j+"]"+by_Change))){
				Common.sleep(3000);
				actions.sendKeys(Keys.PAGE_UP).perform();
				Common.sleep(3000);
		
				System.out.println("xpath is :" + "("+by_list+")["+j+"]"+by_Change);
				driver.findElement(By.xpath("("+by_list+")["+j+"]"+by_Change)).click();
				Thread.sleep(6000);
				
				if(Common.isElementExist(driver, By.xpath("(("+by_list+")["+j+"]"+"//label[@class='option-price-label']/div/span[contains(text(),'+')]/..)[2]"))){		
					JavascriptExecutor js = (JavascriptExecutor)driver;
					js.executeScript("arguments[0].scrollIntoViewIfNeeded(true);", driver.findElement(By.xpath("(("+by_list+")["+j+"]"+"//label[@class='option-price-label']/div/span[contains(text(),'+')]/..)[2]")));
					Thread.sleep(3000);
					driver.findElement(By.xpath("(("+by_list+")["+j+"]"+"//label[@class='option-price-label']/div/span[contains(text(),'+')]/..)[2]")).click();
					//list.get(i).findElement(By.xpath("//label[@class='option-price-label']/div/span[contains(text(),'+')]/..")).click();
					//b2cPage.NewPB_PriceOverride.click();
					driver.findElement(By.xpath("("+by_list+")["+j+"]"+"//div[@class='option-profitOverride']/a")).click();
					priceOverNewPBSection();
					js.executeScript("arguments[0].click();", b2cPage.NewPB_ChangeClose);
				}else{
					continue;
				}
			}
			else if (Common.isElementExist(driver, By.xpath("("+by_list+")["+j+"]"+"//label[@class='option-price-label']"))){
				System.out.println("("+by_list+")["+j+"]"+"//label[@class='option-price-label']");
				Common.sleep(3000);
				actions.sendKeys(Keys.PAGE_UP).perform();
				Common.sleep(3000);
				try{
					
				System.out.println("xpath is :::::" + "("+by_list+")["+j+"]"+"//div[contains(@class,'expandableHeading')]");
				Common.scrollToElement(driver, driver.findElement(By.xpath("("+by_list+")["+j+"]"+"//div[contains(@class,'expandableHeading')]")));
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				//driver.findElement(By.xpath("("+by_list+")["+j+"]"+"//div[contains(@class,'expandableHeading')]")).click();
				Common.sleep(5000);
				if(!driver.findElement(By.xpath("("+by_list+")["+j+"]"+"//div[contains(@class,'expandableHeading')]")).getAttribute("class").contains("expandableHeading-is-expanded")){
					
					driver.findElement(By.xpath("("+by_list+")["+j+"]"+"//div[contains(@class,'expandableHeading')]")).click();
					Common.sleep(3000);
				}
				
				driver.findElement(By.xpath("("+by_list+")["+j+"]"+"//label[@class='option-price-label']")).click();
				Common.sleep(3000);
				int m = 1;
				while(!Common.isElementExist(driver, By.xpath("(("+by_list+")["+j+"]"+"//label[@class='option-price-label'])["+m+"]/../div[contains(@class,'option-profitOverride')]/a"))){
					m++;
					
					driver.findElement(By.xpath("(("+by_list+")["+j+"]"+"//label[@class='option-price-label'])["+m+"]")).click();
				}
				Common.sleep(5000);
				driver.findElement(By.xpath("("+by_list+")["+j+"]"+"//div[contains(@class,'option-profitOverride')]/a")).click();
				//b2cPage.NewPB_PriceOverride.click();
				priceOverNewPBSection2();
			} else {
				System.out.println("No option to override");
			}
			//b2cPage.Product_Continue.click();
		}
	}
	
	public void priceOverNewPBSection(){
		Common.sleep(5000);
		float beforeValue =  GetPriceValue(	b2cPage.NewPB_OverPrice.getAttribute("placeholder"));
		float afterValue =  beforeValue-1;
		String message = "auto test";
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println(b2cPage.NewPB_OverPrice.getAttribute("style"));
		b2cPage.NewPB_OverPrice.clear();
		b2cPage.NewPB_OverPrice.sendKeys(df.format(afterValue));
		b2cPage.NewPB_OverReason.sendKeys(message);
		b2cPage.NewPB_OverReasonSelect.click();
		b2cPage.NewPB_OverReasonSet.click();
		b2cPage.NewPB_OverrideClose.click();
	}
	
	public void priceOverNewPBSection2(){
		Common.sleep(5000);
		float beforeValue =  GetPriceValue(	b2cPage.NewPB_OverPrice2.getAttribute("placeholder"));
		float afterValue =  beforeValue-1;
		String message = "auto test";
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println(driver.findElement(By.xpath("//div[contains(@id,'group_')]/div[@class='priceOverride_main']/div[@style]")).getAttribute("style"));
		System.out.println(driver.findElement(By.xpath("//div[contains(@id,'group_')]/div[@class='priceOverride_main']/div[@style]")).getCssValue("display"));
		b2cPage.NewPB_OverPrice2.clear();
		b2cPage.NewPB_OverPrice2.sendKeys(df.format(afterValue));
		b2cPage.NewPB_OverReason2.sendKeys(message);
		b2cPage.NewPB_OverReasonSelect2.click();
		b2cPage.NewPB_OverReasonSet2.click();
		b2cPage.NewPB_OverrideClose2.click();
	}
	
	public void priceOverOldPB(){
		Common.sleep(10000);
		try {
			b2cPage.PDP_firstConfigraution.click();
		} catch (Exception e) {
			
			Dailylog.logInfo("dont click ");
		}
		if(Common.isElementExist(driver, By.xpath("(//div[@data-show='true']//div[@class='option-textFrame'])[2]/div[1]"))){
			/*if(b2cPage.PB_OptionSelection.getAttribute("type").equals("checkbox")){
				System.out.println("this tab is checbox");
				b2cPage.PB_OptionSelection.click();	
			}else if (b2cPage.PB_OptionSelection.getAttribute("type").equals("radio")){
				b2cPage.PB_OptionSelection.click();
				System.out.println("this tab is radio");
			}*/
			//WebElement element= driver.findElement(By.xpath("(//div[@data-show='true']//div[@class='option-textFrame'])[2]/../div[@class='option-profitOverride']"));
			b2cPage.PB_OptionSelection.click();
			Common.sleep(5000);
			System.out.println(b2cPage.PB_OverPrice.getAttribute("placeholder"));
			Common.sleep(28000);
			float beforeValue =  GetPriceValue(	b2cPage.PB_OverPrice.getAttribute("placeholder"));
			float afterValue =  beforeValue-1;
			String message = "auto test";
			DecimalFormat df = new DecimalFormat("#.00");
			b2cPage.PB_OverPrice.clear();
			b2cPage.PB_OverPrice.sendKeys(df.format(afterValue));
			b2cPage.PB_OverReason.sendKeys(message);
			b2cPage.PB_OverReasonSelect.click();
			b2cPage.PB_OverReasonSet.click();}
		else{
			System.out.println("No option to override");
			}
	}
	
    public void priceOverwritePB() throws Exception{
    	Common.sleep(10000);
    	if(Common.isElementExist(driver, By.xpath(".//*[text()='CHANGE' or text()='変更'or text()='更改']"))){
			Dailylog.logInfoDB(1,"new UI" , Store, testName);
			priceOverNewPB();
			Common.sleep(3000);
			b2cPage.Product_AddToCartBtn.click();
		}
		else {
			Dailylog.logInfoDB(1,"Old UI" , Store, testName);
			List<WebElement> list = driver.findElements(By.xpath("//*[@id='product-builder-form']//ol[@class='stepsItem']/li"));

			int tabList = list.size()-2;
			for(int i = 0;i < tabList ;i++){
				priceOverOldPB();		
				b2cPage.Product_Continue.click();
			}	
		}
		
	}
    
    public void priceOverwriteCart(){
    	List<WebElement> list =  driver.findElements(By.xpath("//form[contains(@id,'updatePriceForm')]"));
    	for (int i=0;i<list.size();i++){
    		int j= i+1;
    		float beforeValue = GetPriceValue(driver.findElement(By.xpath("(//form[contains(@id,'updatePriceForm')])["+j+"]//td[contains(text(),'Line total') or contains(text(),'合計')]/../td[3]")).getText().toString());
        	float afterValue = beforeValue - 1;
        	driver.findElement(By.xpath("(//form[contains(@id,'updatePriceForm')])["+j+"]//input[contains(@name,'price')]")).clear();
        	driver.findElement(By.xpath("(//form[contains(@id,'updatePriceForm')])["+j+"]//input[contains(@name,'price')]")).sendKeys(afterValue+ "");
        	driver.findElement(By.xpath("(//form[contains(@id,'updatePriceForm')])["+j+"]//*[contains(@id,'reasonCode')]/option[2]")).click();
        	driver.findElement(By.xpath("(//form[contains(@id,'updatePriceForm')])["+j+"]//*[@id='reasonText']")).sendKeys("xxxxx");
        	driver.findElement(By.xpath("(//form[contains(@id,'updatePriceForm')])["+j+"]/input[2]")).click();
        	
    	}
    	
	}

    
	
	public void HandleJSpring(WebDriver driver) {

		if (driver.getCurrentUrl().contains("j_spring_security_check")) {

			driver.get(driver.getCurrentUrl().replace(
					"j_spring_security_check", "login"));
			closePromotion(driver, b2cPage);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(),
					testData.B2C.getTelesalesPassword());
			B2CCommon.handleGateKeeper(b2cPage, testData);
		}
	}
	
	
}
