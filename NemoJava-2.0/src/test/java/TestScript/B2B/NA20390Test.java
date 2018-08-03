package TestScript.B2B;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import CommonFunction.B2BCommon;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2BPunchoutPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA20390Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public B2BPunchoutPage punchoutPage;
	String ociUrl;
	String defaultUnit = "1213577815";
	String defaultDMU = "1213348423";
	String AccessLevel = "1213348423";
	String viewError = "AU-ERROR";
	String passwordErr = "aaa-err";
	String message = "Punchout Server Error: PunchOut Exception\r\n" + 
			"Detailed error message:\r\n" + 
			"java.lang.Exception: No OCI credential found for given user name and password";
	String today=Common.getDateTimeString();
	String [] soldTo1 = {"1","View"+today,"test1","1213521232"};
	String [] soldTo2 = {"1","VIEW"+today,"test2","1213521232"};
	String serviceNumber="20JES1X200";
	String serviceNumber2= "20JES1X200";
	private String placeAnOrder;
	String converted = "This quote is already converted to order";
	String expired = "This quote is expired";
	String delete = "Quote is no longer available.";
	String status = "1-Final";
	String available = "was removed from your cart as it is no longer available";
	public NA20390Test(String store){
		this.Store = store;
		this.testName = "NA-20390";
	}
	
	@Test(alwaysRun = true, groups = {"contentgroup", "b2bpunchout", "p2", "b2b"})
	public void NA20390(ITestContext ctx){
		try{
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			punchoutPage = new B2BPunchoutPage(driver);
			
			ociUrl = testData.B2B.getHomePageUrl().split("le/")[0]+"nemopunchouttool/ariba";
			String B2BCustomer = "NA-9998-001";
			String B2BCustomerGroups = "punchOutCustomer";
			String Code = "NA-9998-001";
			String Domain = "NetworkID";
			String SharedSecret = "aaa";
			String UserName = "NA-9998-001";
			String Password = "aaa";
			String Identity = "NA-9998-001";
			String ProfileName = "NA-9998-001";
			
			
			Dailylog.logInfoDB(1,"Login HMC.", Store,testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
				
			Dailylog.logInfoDB(2,"Create B2BCustome, and check if exist the custome, edit it.", Store,testName);
			checkB2BCustomer(B2BCustomer,B2BCustomerGroups);
			
			Dailylog.logInfoDB(3,"4Create Punchout Credential, and check if exist, edit it.", Store,testName);
			checkPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret,UserName,Password);
			
			Dailylog.logInfoDB(5,"Create punchout profile①，and check if exist, edit it.", Store,testName);
			checkPunchoutProfile(ProfileName,B2BCustomer,0);
			
			Dailylog.logInfoDB(6,"7Simulation Tool---OCI", Store,testName);
			SimulationToolOCI(Domain, Identity,SharedSecret,0);
			
			Dailylog.logInfoDB(8,"Quote is Expired", Store,testName);
			checkSimulationTool(hmcPage.editOrder,"Expired");
			
			Dailylog.logInfoDB(9,"Quote is Converted", Store,testName);
			checkOrder(placeAnOrder,hmcPage.quoteStatusSelectConverted,9);
			checkSimulationTool(hmcPage.editOrder,"Converted");
			
			Dailylog.logInfoDB(10,"Quote is Deleted", Store,testName);
			checkOrder(placeAnOrder,hmcPage.quoteStatusSelectConverted,10);
			checkSimulationTool(hmcPage.editOrder,"Deleted");
			
			Dailylog.logInfoDB(11,"Punchout and purchase product", Store,testName);
			SimulationToolOCI(Domain, Identity,SharedSecret,0);
			
			Dailylog.logInfoDB(12,"Product is not available", Store,testName);
			checkCatalog();
			checkSimulationTool(hmcPage.editOrder,"available");
			
			Dailylog.logInfoDB(13,"Product is not available", Store,testName);
			checkSimulationTool(hmcPage.inspectBtn,"available");
			
			Dailylog.logInfoDB(14,"Product is not available", Store,testName);
			checkSimulationTool(hmcPage.editOrder,"available");
								
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	@Test(priority=1,alwaysRun = true)
	public void rollBack(ITestContext ctx){
		try{
//			SetupBrowser();
//			driver.manage().deleteAllCookies();
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_CatalogLink.click();
			hmcPage.Home_ProductsLink.click();
			hmcPage.Catalog_ArticleNumberTextBox.clear();
			hmcPage.Catalog_ArticleNumberTextBox.sendKeys(serviceNumber);
			hmcPage.Catalog_SearchButton.click();
			hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult.click();
			hmcPage.catelogAdministration.click();
			Common.scrollToElement(driver, hmcPage.status);
			hmcPage.statusInput.clear();
			hmcPage.statusInput.sendKeys("Final");
			Common.scrollToElement(driver, hmcPage.webDisplayFlag);
			hmcPage.webDisplayFlagChecked.click();
			hmcPage.PaymentLeasing_saveAndCreate.click();
			hmcPage.HMC_Logout.click();
			Common.sleep(1500);
		}
		catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}
	
	
	public void checkCatalog() throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("(window.open(''))");
		Common.switchToWindow(driver, 1);
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_CatalogLink.click();
		hmcPage.Home_ProductsLink.click();
		hmcPage.Catalog_ArticleNumberTextBox.clear();
		hmcPage.Catalog_ArticleNumberTextBox.sendKeys(serviceNumber);
		hmcPage.Catalog_SearchButton.click();
		hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult.click();
		hmcPage.catelogAdministration.click();
		Common.scrollToElement(driver, hmcPage.status);
		hmcPage.statusInput.clear();
		hmcPage.statusInput.sendKeys(status);
		Common.scrollToElement(driver, hmcPage.webDisplayFlag);
		hmcPage.webDisplayFlagSelect.click();
		hmcPage.PaymentLeasing_saveAndCreate.click();
	}

	public void checkSimulationTool(WebElement editOrder, String message) throws InterruptedException {
		Common.switchToWindow(driver, 0);
		Common.scrollToElement(driver, editOrder);
		editOrder.click();
		Common.sleep(3000);
		driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
		if(message.equals("Expired")) {
			Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div[2]")).getText().contains(expired));
			hmcPage.exitSimulation.click();
		}
		if(message.equals("Converted")) {
			Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div[2]")).getText().contains(converted));
			hmcPage.exitSimulation.click();
		}
		if(message.equals("Deleted")) {
			Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div[2]")).getText().contains(delete));
			hmcPage.exitSimulation.click();
		}
		
		if(message.equals("available")) {
			Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div[1]/p")).getText().contains(available));
			hmcPage.exitSimulation.click();
		}
		
		
	}

	public void checkOrder(String placeAnOrder, WebElement quoteStatusSelectOption, int i) throws InterruptedException {
		Common.sleep(3000);
		((JavascriptExecutor) driver).executeScript("(window.open(''))");
		Common.switchToWindow(driver, 1);
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Order.click();
		hmcPage.Home_Order_Orders.click();
		hmcPage.Home_Order_OrderID.clear();
		hmcPage.Home_Order_OrderID.sendKeys(placeAnOrder);
		hmcPage.Home_Order_OrderSearch.click();
		if(i==10) {
			Common.rightClick(driver, hmcPage.orederResult);
			hmcPage.PunchoutCredential_RemoveAribaCredential.click();
			Common.sleep(2000);
			Alert alert =  driver.switchTo().alert();
			alert.accept();
		}else {
			Common.sleep(3000);
			Common.doubleClick(driver, hmcPage.orederResult);
			hmcPage.orderAdministration.click();
			Common.scrollToElement(driver, hmcPage.quoteStatusSelect);
			quoteStatusSelectOption.click();
			hmcPage.PaymentLeasing_saveAndCreate.click();
			hmcPage.HMC_Logout.click();
		}
	}

	public void checkPunchoutProfile(String ProfileName,String B2BCustomer,int type){
		Common.javascriptClick(driver, hmcPage.Home_Punchout_CustomerProfile);
//		hmcPage.Home_Punchout_CustomerProfile.click();
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
			creatPunchoutProfile(ProfileName,B2BCustomer,type);
		} else {
			creatPunchoutProfile(ProfileName,B2BCustomer,type);
		}
	}

	
	public void SimulationToolOCI(String Domain,String Identity,String sharedSecret, int type) throws Exception{
		driver.manage().deleteAllCookies();
		driver.get(ociUrl);
		B2BCommon.punchoutLogin(driver,punchoutPage);
		System.out.println("Go to ociUrl, and checkout punchout successfully!");
		driver.findElement(By.xpath("//input[@id='userName']")).clear();
		driver.findElement(By.xpath("//input[@id='userName']")).sendKeys("LIeCommerce");
		driver.findElement(By.xpath("//input[@id='password']")).clear();
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("M0C0v0n3L!");
//		punchoutPage.OCI_UserNameTextBox.clear();
//		punchoutPage.OCI_UserNameTextBox.sendKeys(UserName);
//		punchoutPage.OCI_PasswordTextBox.clear();
//		punchoutPage.OCI_PasswordTextBox.sendKeys(Password);	
		punchoutPage.Ariba_DomainTextBox.clear();
		punchoutPage.Ariba_DomainTextBox.sendKeys(Domain);
		punchoutPage.Ariba_IdentityTextBox.clear();
		punchoutPage.Ariba_IdentityTextBox.sendKeys(Identity);
		punchoutPage.Ariba_SharedSecretTextBox.clear();
		punchoutPage.Ariba_SharedSecretTextBox.sendKeys(sharedSecret);
		Common.sleep(1000);
		punchoutPage.Ariba_PunchoutButton.click();
		Common.sleep(3000);
		driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
		Common.sleep(8000);
		saveOCICart(serviceNumber,serviceNumber2);
		Common.sleep(12000);
		b2bPage.lenovoCheckout.click();
		Common.sleep(8000);
		driver.switchTo().defaultContent();
		placeAnOrder=driver.findElement(By.xpath(".//*[@id='orderList']/xmp[1]")).getText().split("cart=\"")[1].split("\"")[0];
		Common.sleep(3000);
		checkOrder(placeAnOrder,hmcPage.quoteStatusSelectOption,6);
		hmcPage.HMC_Logout.click();
	}
	
	public void saveOCICart(String partNumber, String serviceNumber) {
		Common.sleep(3000);
		b2bPage.HomePage_CartIcon.click();
		Common.sleep(9000);
		b2bPage.cartPage_quickOrder.clear();
		b2bPage.cartPage_quickOrder.sendKeys(partNumber);
		b2bPage.cartPage_quickOrder.clear();
		b2bPage.cartPage_quickOrder.sendKeys(serviceNumber);
		b2bPage.cartPage_addButton.click();
	}
	
	
	public void  checkB2BCustomer(String B2BCustomer, String group){
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BCustomer.click();
		if(!Common.checkElementExists(driver, hmcPage.B2BCustomer_SearchIDTextBox, 60)){
			hmcPage.B2BCustomer_SearchToggle.click();
		}
		hmcPage.B2BCustomer_SearchIDTextBox.clear();
		hmcPage.B2BCustomer_SearchIDTextBox.sendKeys(B2BCustomer);
		hmcPage.B2BCustomer_SearchButton.click();
		if(Common.checkElementExists(driver, hmcPage.B2BCustomer_1stSearchedResult, 10)){
			editB2BCustomer(B2BCustomer,group);
		} else {
			creatB2BCustomer(B2BCustomer,group);
		}
	}
	
	public void creatB2BCustomer(String B2BCustomer,String group){
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
		hmcPage.B2BCustomer_EmailInput.sendKeys("test@nemo.com");
		hmcPage.B2BCustomer_PasswordTab.click();
		hmcPage.B2BCustomer_ActiveStatus.click();
		hmcPage.B2BCustomer_CreateButton.click();
		System.out.println("Create B2BCustomer successfully!");
	}
	
	public void editB2BCustomer(String B2BCustomer,String group){
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
		hmcPage.B2BCustomer_EmailEdit.sendKeys("test@nemo.com");
		hmcPage.B2BCustomer_PasswordTab.click();
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
			Common.sleep(1000);
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
				Common.sleep(2000);
				Alert alert =  driver.switchTo().alert();
				alert.accept();
				System.out.println("Remove Ariba/OCI/Oracle Punchout Credential!");
				Common.sleep(8000);
			}
		}	
		addCredentialAriba(Code,Domain,Identity,SharedSecret);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("Create Punchout Credential successfully!");
	}
	
	public void addCredentialAriba(String Code,String Domain,String Identity,String SharedSecret ) throws InterruptedException{
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

	public void createCredentialAriba(String Code,String Domain,String Identity,String SharedSecret ) throws InterruptedException{
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
		Thread.sleep(3000);
		System.out.println("Create Credential Ariba successfully!");
		driver.close();
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
	
	public void creatPunchoutProfile(String ProfileName,String B2BCustomer,int type){
		Common.sleep(1000);
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
	
		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("Create Punchout Profile successfully!");
	}
	
	public void AddCustomerForProfile(String B2BCustomer){
		Common.rightClick(driver, hmcPage.PunchoutProfile_CustomerField);
		hmcPage.PunchoutCredential_AddAribaCredential.click();
		System.out.println("Add Customer For Profile!");
		switchToWindow(1);
		hmcPage.PunchoutProfile_CustomerInput.clear();
		hmcPage.PunchoutProfile_CustomerInput.sendKeys(B2BCustomer);
		Common.sleep(1000);
		driver.findElement(By.xpath(".//*[@id='AutocompleteReferenceEditor[in GenericCondition[B2BCustomerPunchOutCredentialMapping.b2bCustomer]]_ajaxselect_"+B2BCustomer+"']")).click();
		hmcPage.Contract_searchbutton.click();
		driver.findElement(By.xpath(".//*[contains(@id,'ItemDisplay["+B2BCustomer+"]')]")).click();
		driver.findElement(By.xpath(".//span[contains(text(),'Use')]")).click();
		System.out.println("Add Customer For Profile successfully!");
		switchToWindow(0);
	}
}
	
			