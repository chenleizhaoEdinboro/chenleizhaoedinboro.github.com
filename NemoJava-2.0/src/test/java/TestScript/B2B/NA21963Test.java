package TestScript.B2B;

import java.util.ArrayList;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2BPunchoutPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;



public class NA21963Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public B2BPunchoutPage punchoutPage;
	String aribaUrl;
	String defaultUnit = "1213577815";
	String defaultDMU = "1213348423";
	String AccessLevel = "1213348423";
	String StandardB2BCustomer = "ROWAN_APPROVER";
	String StandardB2BCustomerGroups = "b2bcustomergroup";
	String StandardB2BCustomerPassword = "test123456";
	String B2BCustomer = "TEST-21963-001";
	String B2BCustomerGroups = "punchOutCustomer";
	String productNum;
	String today=Common.getDateTimeString();
	String product1= "20L8S07E00";
	String product2="20KGS40B00";
	
	public NA21963Test(String store){
		this.Store = store;
		this.testName = "NA-21963";
	}
	
	@Test(alwaysRun = true, groups = {"contentgroup", "b2bpunchout", "p2", "b2b"})
	public void NA21963(ITestContext ctx){
		try{
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			punchoutPage = new B2BPunchoutPage(driver);
			
			String StandardSiteUrl = testData.B2B.getHomePageUrl()+"/login";
			aribaUrl = testData.B2B.getHomePageUrl().split("le/")[0]+"nemopunchouttool/ariba";
			String Code = "TEST-21963-001";
			String Domain = "NetworkID";
			String SharedSecret = "aaa";
			String UserName = "TEST-21963-001";
			String Password = "aaa";
			String Identity = "TEST-21963-001";
			String ProfileName = "TEST-21963-001";
			String productNum1;
			String productNum2;
			String punchoutPrivateCartName1 = "punchout-site-private-cart-1-"+today;
			String punchoutPrivateCartName2 = "punchout-site-private-cart-2-"+today;
			String punchoutCompanyCartName1 = "punchout-site-company-cart-1-"+today;
			String punchoutCompanyCartName2 = "punchout-site-company-cart-2-"+today;
			String standardPrivateCartName = "standard-site-private-cart-1-"+today;
			String standardCompanyCartName = "standard-site-company-cart-1-"+today;

			Dailylog.logInfoDB(1,"Login HMC.", Store,testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			
			Dailylog.logInfoDB(2,"Create Standard B2BCustome, and check if exist the custome, edit it.", Store,testName);
			checkB2BCustomer(StandardB2BCustomer,StandardB2BCustomerGroups,"StandarCustomer");
			
			Dailylog.logInfoDB(3,"Create B2BCustome, and check if exist the custome, edit it.", Store,testName);
			checkB2BCustomer(B2BCustomer,B2BCustomerGroups,"B2BCustomer");
			
			Dailylog.logInfoDB(4,"Create Punchout Credential, and check if exist, edit it.", Store,testName);
			checkPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret,UserName,Password);
			
			Dailylog.logInfoDB(8,"Create Punchout Profile, and check if exist, remove it then create again.", Store,testName);
			checkPunchoutProfile(ProfileName,B2BCustomer);
		
			Dailylog.logInfoDB(9,"Set 'Enable Punchout CompanyCart' to false", Store,testName);
			EnablePunchoutCompanyCart(false);
			
			Dailylog.logInfoDB(10,"Go to <Rowan Standrard Site> on this link and save cart", Store,testName);
			driver.get(StandardSiteUrl);
			B2BCommon.Login(b2bPage, StandardB2BCustomer, StandardB2BCustomerPassword);
//			productNum1= addLapTopsToCart();
//			productNum1= testData.B2B.getDefaultMTMPN1();
//			productNum1="20JCS0F100";
			B2BCommon.addProduct(driver, b2bPage, product2);
			saveCart("private", standardPrivateCartName);
//			productNum2 = addLapTopsToCart();
//			productNum2= testData.B2B.getDefaultMTMPN2();
			productNum2 = product2;
			Actions actions = new Actions(driver);
			actions.sendKeys(Keys.PAGE_UP).perform();
			B2BCommon.addProduct(driver, b2bPage, testData.B2B.getDefaultMTMPN2());
			saveCart("company", standardCompanyCartName);
			
			Dailylog.logInfoDB(12,"Punchout to Rowan and save carts", Store,testName);
			SetInSimulationToolAriba(Domain,Identity,SharedSecret);	
//			AddCartInSimulationToolAriba(productNum1,"private",punchoutPrivateCartName1,false);
			AddCartInSimulationToolAriba(product1,"private",punchoutPrivateCartName1,false);
			Dailylog.logInfoDB(13,"Set 'Enable Punchout CompanyCart' to true", Store,testName);
			driver.manage().deleteAllCookies();
			driver.get(testData.HMC.getHomePageUrl());
			while(!Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 30)){
				driver.manage().deleteAllCookies();
				driver.get(testData.HMC.getHomePageUrl());
			}
			HMCCommon.Login(hmcPage, testData);
			EnablePunchoutCompanyCart(true);
			
			Dailylog.logInfoDB(14,"Punchout to Rowan and save carts", Store,testName);
			

			SetInSimulationToolAriba(Domain,Identity,SharedSecret);
//			AddCartInSimulationToolAriba(productNum2,"private",punchoutPrivateCartName2,true);
			AddCartInSimulationToolAriba(product1,"private",punchoutPrivateCartName2,true);
			
			SetInSimulationToolAriba(Domain,Identity,SharedSecret);	
			AddCartInSimulationToolAriba(product2,"company",punchoutCompanyCartName1,true);
			
			SetInSimulationToolAriba(Domain,Identity,SharedSecret);			
//			AddCartInSimulationToolAriba(productNum2,"company",punchoutCompanyCartName2,true);
			AddCartInSimulationToolAriba(product1,"private",punchoutPrivateCartName2,true);
			Dailylog.logInfoDB(15,"Punchout view carts", Store,testName);
			driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
			if(Common.checkElementExists(driver, punchoutPage.Ariba_showAllResult, 20)){
				punchoutPage.Ariba_showAllResult.click();
			}
			String [] cartName = new String [10];
			cartName[0] = standardCompanyCartName;
			cartName[1] = punchoutPrivateCartName1;
			cartName[2] = punchoutPrivateCartName2;
			cartName[3] = punchoutCompanyCartName1;
			cartName[4] = punchoutCompanyCartName2;
			for(int i=0;i<5;i++){
				System.out.println("The cartName["+i+"] is: "+cartName[i]);
			}
			
			for(int i=0;i<5;i++){		
				By punchoutXpath = By.xpath("//div[@id='mainContent']//td[@data-title='Cart Name']/h3[contains(.,'"+ cartName[i] +"')]");
				Common.sleep(8000);
				Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(punchoutXpath), 30), "Don't display '"+cartName[i]+"'!");
				System.out.println("Display the "+cartName[i]+" in punchout saved cart page!");		
			}
			
			Dailylog.logInfoDB(16,"Punchout use 'standard Site cart' to checkout", Store,testName);
			driver.findElement(By.xpath("//div[@id='mainContent']//td[@data-title='Cart Name']/h3[contains(.,'"+ standardCompanyCartName +"')]/../../td[1]/h3/a")).click();
			punchoutPage.Ariba_openCart.click();
			punchoutPage.Ariba_CheckOutButton.click();
			System.out.println("Click checkout button in punchout page!");
			driver.switchTo().defaultContent();
			Assert.assertTrue(Common.checkElementExists(driver, punchoutPage.Ariba_orderList, 30),"Checjout failed!");
			System.out.println("Checkout successfully in pubchout page!");
			
			Dailylog.logInfoDB(17,"Standard Site use 'Punchout cart' to checkout", Store,testName);
			driver.manage().deleteAllCookies();
			driver.get(StandardSiteUrl);
			B2BCommon.Login(b2bPage, StandardB2BCustomer, StandardB2BCustomerPassword);
			b2bPage.homepage_MyAccount.click();
			b2bPage.myAccountPage_viewCartHistory.click();
			if(Common.checkElementExists(driver, b2bPage.savedCartPage_showAllResult, 10)){
				b2bPage.savedCartPage_showAllResult.click();
			}
			driver.findElement(By.xpath("//div[@id='mainContent']//td[2]/h3[contains(.,'"+ punchoutCompanyCartName1 +"')]/../../td[1]/h3/a")).click();
			b2bPage.cartDetailsPage_openCart.click();
			b2bPage.cartPage_LenovoCheckout.click();
			System.out.println("Click checkout button in standard site!");
			By b2bPageXpath = By.xpath("//h1[contains(.,'Shipping Information')]");
			Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(b2bPageXpath), 10), 
					"Don't display '"+punchoutCompanyCartName1+"' in standard site saved cart page!");
			System.out.println("Display the '"+punchoutCompanyCartName1+"' in standard Site saved cart page!");
		
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public void AddCartInSimulationToolAriba(String productNum,String cartType, String cartName, Boolean AddCartInSimulationToolAriba) throws InterruptedException{
//new		driver.switchTo().frame("displayFrame");
//		Actions actions = new Actions(driver);
//		actions.sendKeys(Keys.PAGE_UP).perform();
		Thread.sleep(6000);
		punchoutPage.Ariba_CartButton.click();
		Common.sleep(8000);
		punchoutPage.Ariba_QuickOrderProduct.clear();
		punchoutPage.Ariba_QuickOrderProduct.sendKeys(productNum);
		punchoutPage.Ariba_AddButton.click();
		Common.sleep(8000);
		punchoutPage.Ariba_SaveCart.click();
		System.out.println("Click save cart!");
		if(!AddCartInSimulationToolAriba){
			Assert.assertFalse(Common.checkElementExists(driver, punchoutPage.Ariba_companySaveCartn, 5), 
					"Still exist company cart when set 'Enable Punchout CompanyCart' to false!");
			System.out.println("Can't save company cart!");
		}
		if(cartType=="private"){
			punchoutPage.Ariba_privateSaveCartn.click();
			System.out.println("Choose private cart!");
		} else {
			punchoutPage.Ariba_companySaveCartn.click();
			System.out.println("Choose company cart!");
		}
		punchoutPage.Ariba_realsavecartname.clear();
		punchoutPage.Ariba_realsavecartname.sendKeys(cartName);
		punchoutPage.Ariba_saveButton.click();
		Common.sleep(8000);
		Assert.assertTrue(Common.checkElementExists(driver, punchoutPage.Ariba_savedCartPage, 6),
				"Save cart failed! ");
		System.out.println("Save cart successfully!");
		driver.switchTo().defaultContent();
	}
	
	public void SetInSimulationToolAriba(String Domain,String Identity,String SharedSecret) throws InterruptedException{
		driver.manage().deleteAllCookies();
		driver.get(aribaUrl);
		B2BCommon.punchoutLogin(driver,punchoutPage);
		System.out.println("Go to aribaUrl, and checkout punchout successfully!");
//		driver.findElement(By.xpath("//input[@id='userName']")).clear();
//		driver.findElement(By.xpath("//input[@id='userName']")).sendKeys("LIeCommerce");
//		driver.findElement(By.xpath("//input[@id='password']")).clear();
//		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("M0C0v0n3L!");
		punchoutPage.Ariba_DomainTextBox.clear();
		punchoutPage.Ariba_DomainTextBox.sendKeys(Domain);
		punchoutPage.Ariba_IdentityTextBox.clear();
		punchoutPage.Ariba_IdentityTextBox.sendKeys(Identity);
		punchoutPage.Ariba_SharedSecretTextBox.clear();
		punchoutPage.Ariba_SharedSecretTextBox.sendKeys(SharedSecret);
		punchoutPage.Ariba_PunchoutButton.click();
		Thread.sleep(3000);
		driver.switchTo().frame(driver.findElement(By.id("displayFrame")));	
		Common.sleep(8000);
		if(!Common.checkElementExists(driver, punchoutPage.Lenovo_Link, 5)){
			Dailylog.logInfoDB(12,"Punchout fail, don't open B2B website, and punchout again!", Store,testName);
			driver.switchTo().defaultContent();
			punchoutPage.Ariba_PunchoutButton.click();
			Thread.sleep(1000);
			driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
		} else if(!punchoutPage.Lenovo_Link.getAttribute("href").contains("isPunchout=true")){
			System.out.println("The href is: "+punchoutPage.Lenovo_Link.getAttribute("href"));
			Dailylog.logInfoDB(12,"Punchout fail, the B2B website is error, and punchout again!", Store,testName);
			driver.switchTo().defaultContent();
			punchoutPage.Ariba_PunchoutButton.click();
			Thread.sleep(1000);
			driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
		}
		System.out.println("The href is: "+punchoutPage.Lenovo_Link.getAttribute("href"));
		assert punchoutPage.Lenovo_Link.getAttribute("href").contains(defaultUnit);
		assert punchoutPage.Lenovo_Link.getAttribute("href").contains("isPunchout=true");
		System.out.println("Punchout successfully!");
		//driver.switchTo().defaultContent();
		System.out.println("5");
	}
	
	public void saveCart(String cartType, String cartName){
		b2bPage.cartPage_saveCartButton.click();
		if(cartType == "private"){
			b2bPage.cartPage_privateSaveCartn.click();
		} else {
			b2bPage.cartPage_companySaveCartButton.click();
		}
		b2bPage.cartPage_realsavecartname.clear();
		b2bPage.cartPage_realsavecartname.sendKeys(cartName);
		b2bPage.cartPage_SaveCart_save.click();
		Assert.assertTrue(Common.checkElementExists(driver, b2bPage.cartPage_savedCartPage, 6),
				"Save cart failed! ");
		System.out.println("Save cart successfully!");
	}
	
//	public String addLapTopsToCart() throws InterruptedException {
//		String productNum = null;
//		b2bPage.HomePage_productsLink.click();
//		b2bPage.HomePage_Laptop.click();
//		String plpURL = driver.getCurrentUrl();
//		boolean isProductAdded = false;
//		// add contract product to cart
//		if (Common.isElementExist(driver, By.xpath("//*[contains(@title,'Add to Cart')]"))) {
//			for (int i = 0; i < b2bPage.PLPPage_addToCart.size(); i++) {
//			  //for (int i = b2bPage.PLPPage_addToCart.size()-1; i >= 0; i--) {
//				b2bPage.PLPPage_addToCart.get(i).click();
//				Common.waitElementClickable(driver, b2bPage.Product_contractAddToCartOnPopup, 60);
//				b2bPage.Product_contractAddToCartOnPopup.click();
//				Common.waitElementClickable(driver, b2bPage.ProductPage_AlertGoToCart, 60);
//				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2bPage.ProductPage_AlertGoToCart);
//				Thread.sleep(1000);
//				if (Common.isElementExist(driver, By.xpath("//div[@class='cart-item']"))) {
//					isProductAdded = true;
//					System.out.println("Contract product added successfully.");
//					productNum = b2bPage.checkoutPage_partNo.getText();
//					break;
//				} else {
//					driver.get(plpURL);
//				}
//			}
//		}
//		// contract product add failure, add agreement product
//		if (!isProductAdded) {
//			int viewDetailsNo = b2bPage.PLPPage_viewDetails.size();
//			for (int i = 0; i < viewDetailsNo; i++) {
//			//for (int i = viewDetailsNo-2; i >= 0; i--) {
//				b2bPage.PLPPage_viewDetails.get(i).click();
//				if (isAlertPresent()) {
//					System.out.println(driver.switchTo().alert().getText() + " Try next product!");
//					driver.switchTo().alert().accept();
//					driver.get(plpURL);
//				} else if (driver.getTitle().contains("Not Found")) {
//					System.out.println("Product Not Found, Try next product!");
//					driver.get(plpURL);
//				}else {
//					String pdpURL = driver.getCurrentUrl();
//					String partNum = pdpURL.substring(pdpURL.lastIndexOf('/') + 1, pdpURL.length());
//					System.out.println("Product Number: " + partNum);
//					Thread.sleep(1000);
//					if (Common.isElementExist(driver,
//							By.xpath("//*[contains(@id,'Alert')]//*[contains(@id,'_add_to_cart')]"))) {
//						Thread.sleep(500);
//						if (b2bPage.PDPPage_agreementAddToCartOnPopup.isDisplayed()){
//							((JavascriptExecutor) driver).executeScript("arguments[0].click();",
//									b2bPage.PDPPage_agreementAddToCartOnPopup);
//							productNum = partNum;
//							break;													
//						}	else if(b2bPage.productBuilder_addToCartButton.isDisplayed()){
//							b2bPage.productBuilder_addToCartButton.click();
//						}
//		
//						 else if(Common.isElementExist(driver, By.xpath("//div/button[contains(@class,'add-to-cart')][contains(@disabled,'disabled')]"))){
//							 driver.get(plpURL);
//						} else {
//							b2bPage.Agreement_agreementsAddToCart.click();
//							b2bPage.HomePage_CartIcon.click();
//							Thread.sleep(1000);
//							if (Common.isElementExist(driver, By.xpath("//div[@class='cart-item']"))) {
//								System.out.println("Agreement product added successfully.");
//								productNum = partNum;
//								break;
//							} else {
//								driver.get(plpURL);
//							}
//						}
//					} else if(Common.isElementExist(driver, By.xpath("//div/button[contains(@class,'add-to-cart')][contains(@disabled,'disabled')]"))){
//						 driver.get(plpURL);
//					} else {
//						b2bPage.Agreement_agreementsAddToCart.click();
//						b2bPage.HomePage_CartIcon.click();
//						Thread.sleep(1000);
//						if (Common.isElementExist(driver, By.xpath("//div[@class='cart-item']"))) {
//							System.out.println("Agreement product added successfully.");
//							productNum = partNum;
//							break;
//						} else {
//							driver.get(plpURL);
//						}
//					}	
//					
//				}
//			}
//		}
//		return productNum;
//	}
	
	public boolean isAlertPresent() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 15);// seconds
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}
	
	public void EnablePunchoutCompanyCart(Boolean enablePunchoutCompanyCart){
		if(!Common.checkElementExists(driver, hmcPage.Home_B2BUnitLink, 20)){
			hmcPage.Home_B2BCommerceLink.click();
		}
		hmcPage.Home_B2BUnitLink.click();
		hmcPage.B2BUnit_SearchIDTextBox.clear();
		hmcPage.B2BUnit_SearchIDTextBox.sendKeys(defaultUnit);
		hmcPage.B2BUnit_SearchButton.click();
		hmcPage.B2BUnit_ResultItem.click();
		hmcPage.B2BUnit_siteAttribute.click();
		if(enablePunchoutCompanyCart==false){
			hmcPage.B2BUnit_PunchoutCompanyCarFalse.click();
		} else if(enablePunchoutCompanyCart==true){
			hmcPage.B2BUnit_PunchoutCompanyCarTrue.click();
		}
		hmcPage.B2BUnit_Save.click();
		System.out.println("Set Punchout Company Cart Enable to "+enablePunchoutCompanyCart);
	}
	
	public void  checkB2BCustomer(String B2BCustomer, String group, String customerType){
		if(!Common.checkElementExists(driver, hmcPage.Home_B2BCustomer, 5)){
			hmcPage.Home_B2BCommerceLink.click();
		}
		Common.javascriptClick(driver, hmcPage.Home_B2BCustomer);
//		hmcPage.Home_B2BCustomer.click();
		if(!Common.checkElementExists(driver, hmcPage.B2BCustomer_SearchIDTextBox, 5)){
			hmcPage.B2BCustomer_SearchToggle.click();
		}
		hmcPage.B2BCustomer_SearchIDTextBox.clear();
		hmcPage.B2BCustomer_SearchIDTextBox.sendKeys(B2BCustomer);
		hmcPage.B2BCustomer_SearchButton.click();
		if(Common.checkElementExists(driver, hmcPage.B2BCustomer_1stSearchedResult, 10)){
			editB2BCustomer(B2BCustomer,group,customerType);
		} else {
			creatB2BCustomer(B2BCustomer,group,customerType);
		}
	}
	
	public void creatB2BCustomer(String B2BCustomer,String group, String customerType){
		if(!Common.checkElementExists(driver, hmcPage.Home_B2BCustomer, 10)){
			hmcPage.Home_B2BCommerceLink.click();
		}
		Common.rightClick(driver, hmcPage.Home_B2BCustomer);
		hmcPage.Home_CreateB2BCustomer.click();
		System.out.println("Create B2BCustomer: "+B2BCustomer);
		hmcPage.B2BCustomer_IDInput.clear();
		hmcPage.B2BCustomer_IDInput.sendKeys(B2BCustomer);
		hmcPage.B2BCustomer_NameInput.clear();
		hmcPage.B2BCustomer_NameInput.sendKeys(B2BCustomer);
		if(!Common.isElementExist(driver, 
				By.xpath(".//*[contains(text(),'Groups:')]/../..//tbody/tr/td[3]//div[text()='"+group+"']"))){
			hmcPage.B2BCustomer_GroupsInput.sendKeys(group);
			Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+group+"')][text()='"+group+"']")), 15);
			driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+group+"')][text()='"+group+"']")).click();
		}	
		hmcPage.B2BCustomer_AccessLevelTab.click();
		hmcPage.B2BCustomer_AccessLevelInput.clear();
		hmcPage.B2BCustomer_AccessLevelInput.sendKeys(AccessLevel);
		Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+AccessLevel+"')][text()='"+AccessLevel+"']")), 15);
		driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+AccessLevel+"')][text()='"+AccessLevel+"']")).click();
		hmcPage.B2BCustomer_DefaultUnitInput.clear();
		hmcPage.B2BCustomer_DefaultUnitInput.sendKeys(defaultUnit);
		Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+defaultUnit+"')][text()='"+defaultUnit+"']")), 15);
		driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+defaultUnit+"')][text()='"+defaultUnit+"']")).click();
		hmcPage.B2BCustomer_DefaultDMUInput.clear();
		hmcPage.B2BCustomer_DefaultDMUInput.sendKeys(defaultDMU);
		Common.waitElementClickable(driver, driver.findElement(By.xpath("(.//*[contains(@id,'_ajaxselect_"+defaultDMU+"')][text()='"+defaultDMU+"'])[2]")), 15);
		driver.findElement(By.xpath("(.//*[contains(@id,'_ajaxselect_"+defaultDMU+"')][text()='"+defaultDMU+"'])[2]")).click();
		hmcPage.B2BCustomer_AddressesTab.click();
		hmcPage.B2BCustomer_EmailInput.clear();
		hmcPage.B2BCustomer_EmailInput.sendKeys("sample@lenovo.com");
		hmcPage.B2BCustomer_PasswordTab.click();
		if(customerType == "StandardCustomer"){
			hmcPage.B2BCustomer_NewPassword.clear();
			hmcPage.B2BCustomer_NewPassword.sendKeys(StandardB2BCustomerPassword);
			hmcPage.B2BCustomer_PasswordRepeat.clear();
			hmcPage.B2BCustomer_PasswordRepeat.sendKeys(StandardB2BCustomerPassword);
		}
		hmcPage.B2BCustomer_ActiveStatus.click();
		hmcPage.B2BCustomer_CreateButton.click();
		System.out.println("Create B2BCustomer successfully!");
	}
	
	public void editB2BCustomer(String B2BCustomer,String group, String customerType){
		Common.doubleClick(driver, hmcPage.B2BCustomer_1stSearchedResult);
		System.out.println("Edit B2BCustomer: "+B2BCustomer);
		hmcPage.B2BCustomer_IDEdit.clear();
		hmcPage.B2BCustomer_IDEdit.sendKeys(B2BCustomer);
		hmcPage.B2BCustomer_NameEdit.clear();
		hmcPage.B2BCustomer_NameEdit.sendKeys(B2BCustomer);
		if(!Common.isElementExist(driver, 
				By.xpath(".//*[contains(text(),'Groups:')]/../..//tbody/tr/td[3]//div[text()='"+group+"']"))){
			hmcPage.B2BCustomer_GroupsInput.sendKeys(group);
			Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+group+"')][text()='"+group+"']")), 15);
			driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+group+"')][text()='"+group+"']")).click();
		}	
		hmcPage.B2BCustomer_AccessLevelTab.click();
		hmcPage.B2BCustomer_AccessLevelEdit.clear();
		hmcPage.B2BCustomer_AccessLevelEdit.sendKeys(AccessLevel);
		Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+AccessLevel+"')][text()='"+AccessLevel+"']")), 15);
		driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+AccessLevel+"')][text()='"+AccessLevel+"']")).click();
		hmcPage.B2BCustomer_DefaultUnitEdit.clear();
		hmcPage.B2BCustomer_DefaultUnitEdit.sendKeys(defaultUnit);
		Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+defaultUnit+"')][text()='"+defaultUnit+"']")), 15);
		driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+defaultUnit+"')][text()='"+defaultUnit+"']")).click();
		hmcPage.B2BCustomer_DefaultDMUEdit.clear();
		hmcPage.B2BCustomer_DefaultDMUEdit.sendKeys(defaultDMU);
		Common.waitElementClickable(driver, driver.findElement(By.xpath("(.//*[contains(@id,'_ajaxselect_"+defaultDMU+"')][text()='"+defaultDMU+"'])[2]")), 15);
		driver.findElement(By.xpath("(.//*[contains(@id,'_ajaxselect_"+defaultDMU+"')][text()='"+defaultDMU+"'])[2]")).click();
		hmcPage.B2BCustomer_AddressesTab.click();
		hmcPage.B2BCustomer_EmailEdit.clear();
		hmcPage.B2BCustomer_EmailEdit.sendKeys("sample@lenovo.com");
		hmcPage.B2BCustomer_PasswordTab.click();
		if(customerType == "StandardCustomer"){
			hmcPage.B2BCustomer_NewPassword.clear();
			hmcPage.B2BCustomer_NewPassword.sendKeys(StandardB2BCustomerPassword);
			hmcPage.B2BCustomer_PasswordRepeat.clear();
			hmcPage.B2BCustomer_PasswordRepeat.sendKeys(StandardB2BCustomerPassword);
		}
		hmcPage.B2BCustomer_ActiveAccountDropdownValue.click();
		hmcPage.baseStore_save.click();
		System.out.println("Edit B2BCustomer successfully!");
	}
	
	public void  checkPunchoutCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Domain,String Identity, String SharedSecret,String UserName,String Password) throws InterruptedException{
		if(!Common.checkElementExists(driver, hmcPage.Home_Punchout_Credential, 10)){
			if(!Common.checkElementExists(driver, hmcPage.Home_Nemo_Punchout, 10)){
				hmcPage.Home_Nemo.click();
				hmcPage.Home_Nemo_Punchout.click();
			} else {
				hmcPage.Home_Nemo_Punchout.click();
			}
		}
		hmcPage.Home_Punchout_Credential.click();
		hmcPage.PunchoutCredential_CustomerSearch.clear();
		hmcPage.PunchoutCredential_CustomerSearch.sendKeys(B2BCustomer);
		String xpath = ".//*[@id='Content/AutocompleteReferenceEditor[in Content/GenericCondition[B2BCustomerPunchOutCredentialMapping.b2bCustomer]]_ajaxselect_"+B2BCustomer+"']";
		if(Common.isElementExist(driver, By.xpath(xpath))){
			driver.findElement(By.xpath(xpath)).click();
			Thread.sleep(1000);
			hmcPage.PunchoutCredential_SearchButton.click();
			if(Common.checkElementExists(driver, hmcPage.PunchoutCredential_1stSearchedResult, 10)){
				Common.doubleClick(driver, hmcPage.PunchoutCredential_1stSearchedResult);
				editPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret,UserName,Password);
			} else {
				creatPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret,UserName,Password);
			}
		} else {
			creatPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret,UserName,Password);
		}
	}
	
	public void creatPunchoutCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Domain,String Identity, String SharedSecret,String UserName,String Password) throws InterruptedException{
		Common.rightClick(driver, hmcPage.Home_Punchout_Credential);
		hmcPage.Home_CreatePunchoutCredential.click();
		System.out.println("Create Punchout Credential!");
		hmcPage.PunchoutCredential_CustomerInput.clear();
		hmcPage.PunchoutCredential_CustomerInput.sendKeys(B2BCustomer);
		driver.findElement(By.xpath(".//*[@id='Content/AutocompleteReferenceEditor[in Content/Attribute[.b2bCustomer]]_ajaxselect_"+B2BCustomer+"']")).click();
		createCredentialAriba(Code,Domain,Identity,SharedSecret);
		createCredentialOCI(Code,UserName, Password);
		createCredentialOracle(Code,UserName, Password);
		hmcPage.PunchoutCredential_CreateButton.click();
		System.out.println("Create Punchout Credential successfully!");
	}
	
	public void editPunchoutCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Domain,String Identity, String SharedSecret,String UserName,String Password) throws InterruptedException{
		System.out.println("Edit Punchout Credential!");
		//driver.findElement(By.xpath(".//*[@id='Content/AutocompleteReferenceEditor[in Content/Attribute[.b2bCustomer]]_ajaxselect_"+B2BCustomer+"']")).click();
		for(int i=0;i<3;i++){
			if(Common.isElementExist(driver, By.xpath("//div[contains(text(),'PunchOut Credentials')]/../../td[last()]//table//table//table//input"))){
				Common.rightClick(driver, driver.findElement(By.xpath("//div[contains(text(),'PunchOut Credentials')]/../../td[last()]//table//table//table//input")));
				hmcPage.PunchoutCredential_RemoveAribaCredential.click();
				Thread.sleep(2000);
				Alert alert =  driver.switchTo().alert();
				alert.accept();
				System.out.println("Remove Ariba/OCI/Oracle Punchout Credential!");
				Thread.sleep(8000);
			}
		}	
		addCredentialAriba(Code,Domain,Identity,SharedSecret);
		addCredentialOCI(Code,UserName, Password);
		addCredentialOracle(Code,UserName, Password);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("Create Punchout Credential successfully!");
	}
	
	public void creatPunchoutCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Domain,String Identity, String SharedSecret){
		Common.rightClick(driver, hmcPage.Home_Punchout_Credential);
		hmcPage.Home_CreatePunchoutCredential.click();
		System.out.println("Create Punchout Credential!");
		hmcPage.PunchoutCredential_CustomerInput.clear();
		hmcPage.PunchoutCredential_CustomerInput.sendKeys(B2BCustomer);
		driver.findElement(By.xpath(".//*[@id='Content/AutocompleteReferenceEditor[in Content/Attribute[.b2bCustomer]]_ajaxselect_"+B2BCustomer+"']")).click();
		createCredentialAriba(Code,Domain,Identity,SharedSecret);
		hmcPage.PunchoutCredential_CreateButton.click();
		System.out.println("Create Punchout Credential successfully!");
	}
	
	public void editPunchoutCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Domain,String Identity, String SharedSecret) throws InterruptedException{
		System.out.println("Edit Punchout Credential!");
		//driver.findElement(By.xpath(".//*[@id='Content/AutocompleteReferenceEditor[in Content/Attribute[.b2bCustomer]]_ajaxselect_"+B2BCustomer+"']")).click();
		for(int i=0;i<3;i++){
			if(Common.isElementExist(driver, By.xpath("//div[contains(text(),'PunchOut Credentials')]/../../td[last()]//table//table//table//input"))){
				Common.rightClick(driver, driver.findElement(By.xpath("//div[contains(text(),'PunchOut Credentials')]/../../td[last()]//table//table//table//input")));
				hmcPage.PunchoutCredential_RemoveAribaCredential.click();
				Thread.sleep(2000);
				Alert alert =  driver.switchTo().alert();
				alert.accept();
				System.out.println("Remove Ariba/OCI/Oracle Punchout Credential!");
				Thread.sleep(8000);
			}
		}	
		addCredentialAriba(Code,Domain,Identity,SharedSecret);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("Create Punchout Credential successfully!");
	}
	
	public void createCredentialAriba(String Code,String Domain,String Identity,String SharedSecret ){
		Common.rightClick(driver, hmcPage.PunchoutCredential_AribaCredential);
		hmcPage.PunchoutCredential_CreateAribaCredential.click();
		System.out.println("Create Credential Ariba!");
		switchToWindow(1);
		hmcPage.PunchoutAribaCredential_CodeInput.clear();
		hmcPage.PunchoutAribaCredential_CodeInput.sendKeys(Code);
		hmcPage.PunchoutAribaCredential_DomainInput.clear();
		hmcPage.PunchoutAribaCredential_DomainInput.sendKeys(Domain);
		hmcPage.PunchoutAribaCredential_IdentityInput.clear();
		hmcPage.PunchoutAribaCredential_IdentityInput.sendKeys(Identity);
		hmcPage.PunchoutAribaCredential_SharedSecretInput.clear();
		hmcPage.PunchoutAribaCredential_SharedSecretInput.sendKeys(SharedSecret);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("Create Credential Ariba successfully!");
		driver.close();
		switchToWindow(0);
	}
	
	public void createCredentialOCI(String Code,String UserName,String Password){
		Common.rightClick(driver, hmcPage.PunchoutCredential_OCICredential);
		hmcPage.PunchoutCredential_CreatOCICredential.click();
		System.out.println("Create Credential OCI!");
		switchToWindow(1);
		hmcPage.PunchoutOCIOracleCredential_CodeInput.clear();
		hmcPage.PunchoutOCIOracleCredential_CodeInput.sendKeys(Code);
		hmcPage.PunchoutOCICredential_UserNameInput.clear();
		hmcPage.PunchoutOCICredential_UserNameInput.sendKeys(UserName);
		hmcPage.PunchoutOCIOracleCredential_PasswordInput.clear();
		hmcPage.PunchoutOCIOracleCredential_PasswordInput.sendKeys(Password);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("Create Credential OCI successfully!");
		driver.close();
		switchToWindow(0);
	}
	
	public void addCredentialOCI(String Code,String UserName,String Password){
		Common.rightClick(driver, hmcPage.PunchoutCredential_OCICredential);
		hmcPage.PunchoutCredential_AddOCICredential.click();
		System.out.println("Add Credential OCI!");
		switchToWindow(1);
		hmcPage.PunchoutOCICredential_CodeSearchInput.clear();
		hmcPage.PunchoutOCICredential_CodeSearchInput.sendKeys(Code);
		hmcPage.Contract_searchbutton.click();
		if(!Common.isElementExist(driver, By.xpath(".//div[contains(@id,'StringDisplay["+Code+"]')]"))){
			driver.findElement(By.xpath(".//span[contains(text(),'Cancel')]")).click();
			switchToWindow(0);
			createCredentialOCI(Code,UserName,Password);
		}else{
			driver.findElement(By.xpath(".//div[contains(@id,'StringDisplay["+Code+"]')]")).click();
			driver.findElement(By.xpath(".//span[contains(text(),'Use')]")).click();
			System.out.println("Add Credential OCI successfully!");
			switchToWindow(0);
		}
	}
	
	public void createCredentialOracle(String Code,String Name,String Password) throws InterruptedException{
		Common.rightClick(driver, hmcPage.PunchoutCredential_OracleCredential);
		hmcPage.PunchoutCredential_CreatOracleCredential.click();
		System.out.println("Create Credential Oracle!");
		Thread.sleep(1000);
		switchToWindow(1);
		hmcPage.PunchoutOCIOracleCredential_CodeInput.clear();
		hmcPage.PunchoutOCIOracleCredential_CodeInput.sendKeys(Code);
		hmcPage.PunchoutOracleCredential_NameInput.clear();
		hmcPage.PunchoutOracleCredential_NameInput.sendKeys(Name);
		hmcPage.PunchoutOCIOracleCredential_PasswordInput.clear();
		hmcPage.PunchoutOCIOracleCredential_PasswordInput.sendKeys(Password);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("Create Credential Oracle successfully!");
		driver.close();
		switchToWindow(0);
	}
	
	public void addCredentialOracle(String Code,String Name,String Password){
		Common.rightClick(driver, hmcPage.PunchoutCredential_OracleCredential);
		hmcPage.PunchoutCredential_AddOracleCredential.click();
		System.out.println("Add Credential Oracle!");
		switchToWindow(1);
		hmcPage.PunchoutOracleCredential_CodeSearchInput.clear();
		hmcPage.PunchoutOracleCredential_CodeSearchInput.sendKeys(Code);
		hmcPage.Contract_searchbutton.click();
		if(!Common.isElementExist(driver, By.xpath(".//div[contains(@id,'StringDisplay["+Code+"]')]"))){
			driver.findElement(By.xpath(".//span[contains(text(),'Cancel')]")).click();
			switchToWindow(0);
			createCredentialOCI(Code,Name,Password);
		}else{
			driver.findElement(By.xpath(".//div[contains(@id,'StringDisplay["+Code+"]')]")).click();
			driver.findElement(By.xpath(".//span[contains(text(),'Use')]")).click();
			System.out.println("Add Credential Oracle successfully!");
			switchToWindow(0);
		}
	}
	
	public void addCredentialAriba(String Code,String Domain,String Identity,String SharedSecret ){
		Common.rightClick(driver, hmcPage.PunchoutCredential_AribaCredential);
		hmcPage.PunchoutCredential_AddAribaCredential.click();
		System.out.println("Add Credential Ariba!");
		switchToWindow(1);
		hmcPage.PunchoutAribaCredential_CodeSearchInput.clear();
		hmcPage.PunchoutAribaCredential_CodeSearchInput.sendKeys(Code);
		hmcPage.Contract_searchbutton.click();
		if(!Common.isElementExist(driver, By.xpath(".//*[@id='StringDisplay["+Code+"]_span']"))){
			driver.findElement(By.xpath(".//span[contains(text(),'Cancel')]")).click();
			switchToWindow(0);
			createCredentialAriba(Code,Domain,Identity,SharedSecret);
		}else{
			driver.findElement(By.xpath(".//*[@id='StringDisplay["+Code+"]_span']")).click();
			driver.findElement(By.xpath(".//span[contains(text(),'Use')]")).click();
			System.out.println("Add Credential Ariba successfully!");
			switchToWindow(0);
		}
	}
	
	public void checkPunchoutProfile(String ProfileName,String B2BCustomer) throws InterruptedException{
		hmcPage.Home_Punchout_CustomerProfile.click();
		Select selScope =new Select(hmcPage.PunchoutProfile_SelectProfileNameScope);
		selScope.selectByVisibleText("is equal");
		hmcPage.PunchoutProfile_NameSearch.clear();
		hmcPage.PunchoutProfile_NameSearch.sendKeys(ProfileName);
		hmcPage.Contract_searchbutton.click();
		if(Common.checkElementExists(driver, hmcPage.PunchoutProfile_1stSearchedResult, 10)){
			Common.rightClick(driver, hmcPage.PunchoutProfile_1stSearchedResult);
			hmcPage.PunchoutProfile_removeResult.click();
			driver.switchTo().alert().accept();
			System.out.println("Remove Punchout Profile!");
			creatPunchoutProfile(ProfileName,B2BCustomer);
		} else {
			System.out.println("Don't need to remove Punchout Profile!");
			creatPunchoutProfile(ProfileName,B2BCustomer);
		}
	} 
	
	public void creatPunchoutProfile(String ProfileName,String B2BCustomer) throws InterruptedException{
		Thread.sleep(1000);
		Common.rightClick(driver, hmcPage.Home_Punchout_CustomerProfile);
		hmcPage.Home_CreatePunchoutProfile.click();
		System.out.println("Create Punchout Profile!");
		hmcPage.PunchoutProfile_NameInput.clear();
		hmcPage.PunchoutProfile_NameInput.sendKeys(ProfileName);
		AddCustomerForProfile(B2BCustomer);
		if(hmcPage.PunchoutProfile_WhetherActive.getAttribute("value").equals("false")){
			hmcPage.PunchoutProfile_Active.click();
		}
		if(hmcPage.PunchoutProfile_WhetherActiveCxml.getAttribute("value").equals("false")){
			hmcPage.PunchoutProfile_ActiveOxml.click();
		}
		hmcPage.PunchoutProfile_OCITab.click();
		if(hmcPage.PunchoutProfile_WhetherActiveOCI.getAttribute("value").equals("false")){
			hmcPage.PunchoutProfile_ActiveOCI.click();
		}
		hmcPage.PunchoutProfile_OracleTab.click();
		if(hmcPage.PunchoutProfile_WhetherActiveOracle.getAttribute("value").equals("false")){
			hmcPage.PunchoutProfile_ActiveOracle.click();
		}
		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("Create Punchout Profile successfully!");
	}
	
	public void AddCustomerForProfile(String B2BCustomer) throws InterruptedException{
		Common.rightClick(driver, hmcPage.PunchoutProfile_CustomerField);
		hmcPage.PunchoutCredential_AddAribaCredential.click();
		System.out.println("Add Customer For Profile!");
		switchToWindow(1);
		hmcPage.PunchoutProfile_CustomerInput.clear();
		hmcPage.PunchoutProfile_CustomerInput.sendKeys(B2BCustomer);
		Thread.sleep(1000);
		driver.findElement(By.xpath(".//*[@id='AutocompleteReferenceEditor[in GenericCondition[B2BCustomerPunchOutCredentialMapping.b2bCustomer]]_ajaxselect_"+B2BCustomer+"']")).click();
		hmcPage.Contract_searchbutton.click();
		driver.findElement(By.xpath(".//*[contains(@id,'ItemDisplay["+B2BCustomer+"]')]")).click();
		driver.findElement(By.xpath(".//span[contains(text(),'Use')]")).click();
		System.out.println("Add Customer For Profile successfully!");
		switchToWindow(0);
	}
	
	public void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());		
			driver.switchTo().window(windows.get(windowNo));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
	
			