package TestScript.B2B;

import java.util.ArrayList;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
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
import junit.framework.Assert;



public class NA18960Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public B2BPunchoutPage punchoutPage;
	String oxmlUrl;
	String defaultUnit = "1213577815";
	String defaultDMU = "1213348423";
	String AccessLevel = "1213348423";
	String B2BCustomer = "PUNCHOUT-RE-18960";
	String B2BCustomerGroups = "punchOutCustomer";
	int logResult = 0;
	String serviceNumber="5PS0F15928";
	String CardId="";
	
	public NA18960Test(String store){
		this.Store = store;
		this.testName = "NA-18960";
	}
	
	@Test(alwaysRun = true, groups = {"contentgroup", "b2bpunchout", "p2", "b2b"})
	public void NA18960(ITestContext ctx){
		try{
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			punchoutPage = new B2BPunchoutPage(driver);
			
			oxmlUrl= testData.B2B.getHomePageUrl().split("le/")[0]+"nemopunchouttool/oxml";

			String UserName = "PUNCHOUT-RE-18960";
			String Password = "aaa";

			
			//PunchOut Credential:Code Domain Identity SharedSecret
			String Code = "PUNCHOUT-RE-18960";
			String Domain = "NetworkID";
			String Identity = "PUNCHOUT-RE-18960";
			String SharedSecret = "aaa";
			
			//OCI and Oracle : Code UserName Password
			
			//Profile: ProfileName UserName
			String ProfileName = "PUNCHOUT-RE-18960";
			
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(1,"Login HMC successfully.", Store,testName);
			
			checkB2BCustomer(B2BCustomer,B2BCustomerGroups,"B2BCustomer");
			Dailylog.logInfoDB(2,"Create B2BCustome, and check if exist the custome, edit it successfully.", Store,testName);
			
			checkPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret,UserName,Password);
			Dailylog.logInfoDB(2,"Create Punchout Credential, and check if exist, edit it successfully.", Store,testName);
			
			checkPunchoutProfile(ProfileName,B2BCustomer);
			Dailylog.logInfoDB(2,"Create Punchout Profile, and check if exist, remove it then create again successfully.", Store,testName);
			
			
			SimulationToolOracle(3,UserName,Password,defaultUnit);
			Dailylog.logInfoDB(3,"Simulation Tool Oracle", Store,testName);
			
			saveOCICart(serviceNumber);
			Dailylog.logInfoDB(4,"Go to cart page, add one product to cart, click Checkout", Store,testName);
			driver.switchTo().defaultContent();
		
			CardId=driver.findElement(By.xpath(".//*[@id='orderList']/xmp[1]")).getText().split("cart=\"")[1].split("\"")[0];
			
			driver.get(testData.HMC.getHomePageUrl());
			checkoutTraceLog(CardId,UserName);
			Dailylog.logInfoDB(5,"find the latest one then check the Cart ID is same", Store,testName);
			
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public void checkoutTraceLog(String CardId,String UserName) throws InterruptedException{
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Nemo.click();
		hmcPage.Home_Nemo_Punchout.click();
		hmcPage.Home_Punchout_Tracelog.click();


		driver.findElement(By.xpath("//select[contains(@id,'[PunchOutTraceLog]_select')]//option[contains(text(),'Protocol')]")).click();

		Thread.sleep(3000);
		driver.findElement(By.xpath("//select[contains(@id,'[PunchOutTraceLog.protocol][1]]_select')]//option[contains(text(),'Oracle')]")).click();
		Dailylog.logInfoDB(5, "CardId:"+CardId, Store, testName);
		driver.findElement(By.xpath(".//*[@id='Content/StringEditor[in Content/GenericCondition[PunchOutTraceLog.cartId]]_input']")).sendKeys(CardId);
		hmcPage.Home_Punchout_Tracelog_Search.click();
	
		//TraceLog result one
		if(Common.checkElementExists(driver, driver.findElement(By.xpath(".//*[@id='Content/StringDisplay[]_span']")), 10)){
			Common.doubleClick(driver, driver.findElement(By.xpath(".//*[@id='Content/StringDisplay[]_span']")));
		}

		Assert.assertTrue(driver.findElement(By.xpath("//textarea[contains(@id,'[PunchOutTraceLog.cartXml]]_textarea')]")).getText().contains(CardId));
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
	public void  checkB2BCustomer(String B2BCustomer, String group, String customerType){
		if(!Common.checkElementExists(driver, hmcPage.Home_B2BCustomer, 5)){
			hmcPage.Home_B2BCommerceLink.click();
		}
		hmcPage.Home_B2BCustomer.click();
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
		hmcPage.B2BCustomer_ActiveAccountDropdownValue.click();
		hmcPage.baseStore_save.click();
		System.out.println("Edit B2BCustomer successfully!");
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
	
	public void editPunchoutCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Domain,String Identity, String SharedSecret,String UserName,String Password){
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
	
	public void editPunchoutCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code) throws InterruptedException{
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
		Thread.sleep(3000);
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
	public void SimulationToolOracle(int step,String Name,String Password,String defaultUnit){
		driver.manage().deleteAllCookies();
		driver.get(oxmlUrl);
		B2BCommon.punchoutLogin(driver,punchoutPage);
		System.out.println("Go to oxmlUrl, and checkout punchout successfully!");
		String loginRequestText = punchoutPage.Oracle_LoginRequestTextBox.getText().replaceFirst("<username />", "<username>"+Name+"</username>");
		loginRequestText = loginRequestText.replaceFirst("<password>welcome</password>", "<password>"+Password+"</password>");
		punchoutPage.Oracle_LoginRequestTextBox.clear();
		punchoutPage.Oracle_LoginRequestTextBox.sendKeys(loginRequestText);
		punchoutPage.Oracle_PunchoutButton.click();
		Common.sleep(5000);
		driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
		if(!Common.checkElementExists(driver, punchoutPage.Lenovo_Link, 30)){
			Dailylog.logInfoDB(step,"Punchout fail, don't open B2B website, and punchout again!", Store,testName);
			driver.switchTo().defaultContent();
			punchoutPage.Ariba_PunchoutButton.click();
			logResult++;
			Common.sleep(1000);
			driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
		} else if(!punchoutPage.Lenovo_Link.getAttribute("href").contains("isPunchout=true")){
			System.out.println("The href is: "+punchoutPage.Lenovo_Link.getAttribute("href"));
			Dailylog.logInfoDB(step,"Punchout fail, the B2B website is error, and punchout again!", Store,testName);
			driver.switchTo().defaultContent();
			punchoutPage.Ariba_PunchoutButton.click();
			logResult++;
			Common.sleep(1000);
			driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
		}
		
		System.out.println("The href is: "+punchoutPage.Lenovo_Link.getAttribute("href"));
		assert punchoutPage.Lenovo_Link.getAttribute("href").contains(defaultUnit);
		assert punchoutPage.Lenovo_Link.getAttribute("href").contains("isPunchout=true");
		System.out.println("Punchout successfully!");
		Common.sleep(5000);
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
	public void saveOCICart(String partNumber) throws InterruptedException{
		punchoutPage.B2Bsite_service.click();
		Common.sleep(8000);
		driver.findElement(By.xpath(".//*[@id='addToCartForm"+partNumber+"']/button")).click();
		driver.findElement(By.xpath(".//*[@id='addtoCartModal"+partNumber+"']/aside/div[3]/button")).click();
		
		Thread.sleep(5000);
		driver.findElement(By.xpath((".//*[@id='addtoCartModal"+partNumber+"']/aside/div[3]/a[2]"))).click();
		
		Common.isElementExist(driver, By.id("validateDateformatForCheckout"), 20);
		driver.findElement(By.id("validateDateformatForCheckout")).click();
		
		switchToWindow(0);
		Thread.sleep(5000);
	}
}
	
			
