package TestScript.B2B;


import java.util.ArrayList;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
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

public class NA21817Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public B2BPunchoutPage punchoutPage;
	String aribaUrl;
	String ociUrl;
	String oxmlUrl;
	String time[];
	String defaultUnit;
	String defaultDMU;
	String AccessLevel;
	int logResult = 0;

	public NA21817Test(String store, String defaultUnit, String defaultDMU, String AccessLevel) {
		this.Store = store;
		this.defaultUnit = defaultUnit;
		this.defaultDMU = defaultDMU;
		this.AccessLevel = AccessLevel;
		this.testName = "NA-21817";
	}

	@Test(alwaysRun = true, groups = {"contentgroup", "b2bpunchout", "p2", "b2b"})
	public void NA21817(ITestContext ctx) {
		
		try {
			aribaUrl = testData.B2B.getHomePageUrl().split("le/")[0]+"nemopunchouttool/ariba";
			ociUrl= testData.B2B.getHomePageUrl().split("le/")[0]+"nemopunchouttool/oci";
			oxmlUrl = testData.B2B.getHomePageUrl().split("le/")[0]+"nemopunchouttool/oxml";
			
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			punchoutPage = new B2BPunchoutPage(driver);
			String B2BCustomer = "TEST-21817-001";
			String Code = "TEST-21817-001";
			String Domain = "NetworkID";
			String SharedSecret = "aaa";
			String UserName = "TEST-21817-001";
			String Password = "aaa";
			String Identity = "TEST-21817-001";
			String ProfileName = "TEST-21817-001";
			
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(1,"Login HMC successfully.", Store,testName);
			
			checkB2BCustomer(B2BCustomer);
			Dailylog.logInfoDB(2,"Create B2BCustome, and check if exist the custome, edit it successfully.", Store,testName);
			
			checkPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret,UserName,Password);
			Dailylog.logInfoDB(3,"Create Punchout Credential, and check if exist, edit it successfully.", Store,testName);
			
			checkPunchoutProfile(ProfileName,B2BCustomer);
			time = getGreaterTimeOfTracelog(UserName);
			Dailylog.logInfoDB(7,"Create Punchout Profile, and check if exist, remove it then create again successfully.", Store,testName);
			
			SimulationToolAriba(8,Domain,Identity,SharedSecret,defaultUnit);
			Dailylog.logInfoDB(8,"Simulation Tool Ariba successfully.", Store,testName);
			
			SimulationToolOCI(UserName,Password,defaultUnit);
			Dailylog.logInfoDB(9,"Simulation Tool OCI successfully.", Store,testName);
			
			SimulationToolOracle(10,UserName,Password,defaultUnit);
			Dailylog.logInfoDB(10,"Simulation Tool Oracle successfully.", Store,testName);	
			
			checkTracelog(UserName, "application/xml");
			time = getGreaterTimeOfTracelog(UserName);
			Dailylog.logInfoDB(11,"Check Tracelog successfully.", Store,testName);
			
			editContentTypeOfPunchoutProfile(ProfileName,"application/xml");
			Dailylog.logInfoDB(12,"Modify Punchout Profile as 'application/xml' successfully.", Store,testName);
			
			SimulationToolAriba(13,Domain,Identity,SharedSecret,defaultUnit);
			SimulationToolOCI(UserName,Password,defaultUnit);
			SimulationToolOracle(13,UserName,Password,defaultUnit);
			checkTracelog(UserName, "application/xml");
			time = getGreaterTimeOfTracelog(UserName);
			Dailylog.logInfoDB(13,"Simulation Tool Ariba/OCI/Oracle, and check tracelog successfully.", Store,testName);
			
			editContentTypeOfPunchoutProfile(ProfileName,"text/xml");
			Dailylog.logInfoDB(14,"Modify Punchout Profile as 'text/xml' successfully.", Store,testName);
			
			SimulationToolAriba(15,Domain,Identity,SharedSecret,defaultUnit);
			SimulationToolOCI(UserName,Password,defaultUnit);
			SimulationToolOracle(15,UserName,Password,defaultUnit);
			checkTracelog(UserName, "text/xml");
			Dailylog.logInfoDB(15,"Simulation Tool Ariba/OCI/Oracle, and check tracelog successfully.", Store,testName);
	
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}
	
	
	public void  checkB2BCustomer(String B2BCustomer){
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BCustomer.click();
		hmcPage.B2BCustomer_SearchIDTextBox.clear();
		hmcPage.B2BCustomer_SearchIDTextBox.sendKeys(B2BCustomer);
		hmcPage.B2BCustomer_SearchButton.click();
		if(Common.checkElementExists(driver, hmcPage.B2BCustomer_1stSearchedResult, 10)){
			editB2BCustomer(B2BCustomer,defaultUnit,defaultDMU,AccessLevel);
		} else {
			creatB2BCustomer(B2BCustomer,defaultUnit,defaultDMU,AccessLevel);
		}
	}
	
	public void  checkPunchoutCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Domain,String Identity, String SharedSecret,String UserName,String Password){
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
	
	public void checkPunchoutProfile(String ProfileName,String B2BCustomer){
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
	
	public void creatB2BCustomer(String B2BCustomer,String defaultUnit,String defaultDMU,String AccessLevel){
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
				By.xpath(".//*[contains(text(),'Groups:')]/../..//tbody/tr/td[3]//div[text()='punchOutCustomer']"))){
			hmcPage.B2BCustomer_GroupsInput.sendKeys("punchOutCustomer");
			Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_punchOutCustomer')][text()='punchOutCustomer']")), 15);
			driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_punchOutCustomer')][text()='punchOutCustomer']")).click();
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
	
	public void editB2BCustomer(String B2BCustomer,String defaultUnit,String defaultDMU,String AccessLevel){
		Common.doubleClick(driver, hmcPage.B2BCustomer_1stSearchedResult);
		System.out.println("Edit B2BCustomer: "+B2BCustomer);
		hmcPage.B2BCustomer_IDEdit.clear();
		hmcPage.B2BCustomer_IDEdit.sendKeys(B2BCustomer);
		hmcPage.B2BCustomer_NameEdit.clear();
		hmcPage.B2BCustomer_NameEdit.sendKeys(B2BCustomer);
		if(!Common.isElementExist(driver, 
				By.xpath(".//*[contains(text(),'Groups:')]/../..//tbody/tr/td[3]//div[text()='punchOutCustomer']"))){
			hmcPage.B2BCustomer_GroupsInput.sendKeys("punchOutCustomer");
			Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_punchOutCustomer')][text()='punchOutCustomer']")), 15);
			driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_punchOutCustomer')][text()='punchOutCustomer']")).click();
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
	
	public void creatPunchoutCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Domain,String Identity, String SharedSecret,String UserName,String Password){
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
		if(Common.checkElementExists(driver, b2bPage.PunchoutCredentials_OkButton, 10)){
			b2bPage.PunchoutCredentials_OkButton.click();
			driver.close();
			switchToWindow(0);
			addCredentialAriba(Code,Domain,Identity,SharedSecret);
		} else {
			driver.close();
			switchToWindow(0);
		}
		System.out.println("Create Credential Ariba successfully!");
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
		if(Common.checkElementExists(driver, b2bPage.PunchoutCredentials_OkButton, 10)){
			 b2bPage.PunchoutCredentials_OkButton.click();
			 driver.close();
			 switchToWindow(0);
			 addCredentialOCI(Code,UserName,Password);
		} else {
			driver.close();
			switchToWindow(0);
		}
		System.out.println("Create Credential OCI successfully!");
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
	
	public void createCredentialOracle(String Code,String Name,String Password){
		Common.rightClick(driver, hmcPage.PunchoutCredential_OracleCredential);
		hmcPage.PunchoutCredential_CreatOracleCredential.click();
		System.out.println("Create Credential Oracle!");
		Common.sleep(1000);
		switchToWindow(1);
		hmcPage.PunchoutOCIOracleCredential_CodeInput.clear();
		hmcPage.PunchoutOCIOracleCredential_CodeInput.sendKeys(Code);
		hmcPage.PunchoutOracleCredential_NameInput.clear();
		hmcPage.PunchoutOracleCredential_NameInput.sendKeys(Name);
		hmcPage.PunchoutOCIOracleCredential_PasswordInput.clear();
		hmcPage.PunchoutOCIOracleCredential_PasswordInput.sendKeys(Password);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		if(Common.checkElementExists(driver, b2bPage.PunchoutCredentials_OkButton, 10)){
			 b2bPage.PunchoutCredentials_OkButton.click();
			 driver.close();
			 switchToWindow(0);
			 addCredentialOracle(Code,Name,Password);
		} else {
			driver.close();
			switchToWindow(0);
		}
		System.out.println("Create Credential Oracle successfully!");
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

	public void creatPunchoutProfile(String ProfileName,String B2BCustomer){
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
		Select selectCxmlType =new Select(hmcPage.PunchoutProfile_ContentTypeSelectCxml);
		selectCxmlType.selectByValue("-1");
		hmcPage.PunchoutProfile_OCITab.click();
		if(hmcPage.PunchoutProfile_WhetherActiveOCI.getAttribute("value").equals("false")){
			hmcPage.PunchoutProfile_ActiveOCI.click();
		}
		Select  selectOCIType =new Select(hmcPage.PunchoutProfile_ContentTypeSelectOCI);
		selectOCIType.selectByValue("-1");
		hmcPage.PunchoutProfile_OracleTab.click();
		if(hmcPage.PunchoutProfile_WhetherActiveOracle.getAttribute("value").equals("false")){
			hmcPage.PunchoutProfile_ActiveOracle.click();
		}
		Select  selectOracleType =new Select(hmcPage.PunchoutProfile_ContentTypeSelectOracle);
		selectOracleType.selectByValue("-1");
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
	
	public void removePunchoutProfile(String ProfileName){
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
		} else {
			System.out.println("Don't need to remove Punchout Profile!");
		}
	} 
	
	public String[] getGreaterTimeOfTracelog(String Name){
		String time[] = {"",""};
		if(!Common.checkElementExists(driver, hmcPage.Home_Punchout_Tracelog_UserID, 10)){
			hmcPage.Home_Punchout_Tracelog.click();
		}
		hmcPage.Home_Punchout_Tracelog_UserID.clear();
		hmcPage.Home_Punchout_Tracelog_UserID.sendKeys(Name);
		hmcPage.Home_Punchout_Tracelog_Search.click();
		System.out.println("Click search  button, then search tracelog!");
		hmcPage.NEMO_digitalRiver_creationtime_sort.click();
		if(Common.checkElementExists(driver, hmcPage.Home_Punchout_Tracelog_DateTime, 10)){
			String lastResultTime = hmcPage.Home_Punchout_Tracelog_DateTime.getText();
			System.out.println("The lastResultTime is: "+lastResultTime);
			String sTime= lastResultTime.substring(0, 10);
			System.out.println("The sTime is: "+sTime);
			String sMonth = lastResultTime.substring(0,2);
			System.out.println("The sMonth is: "+sMonth);
			String sDate= lastResultTime.substring(3, 5);
			System.out.println("The sDate is: "+sDate);
			String sHour= lastResultTime.substring(lastResultTime.length()-11, lastResultTime.length()-9);
			System.out.println("The sHour is: "+sHour);
			String sMin = lastResultTime.substring(lastResultTime.length()-8, lastResultTime.length()-6);
			System.out.println("The sMin is: "+sMin);
			String sSecond = lastResultTime.substring(lastResultTime.length()-5, lastResultTime.length()-3);
			System.out.println("The sSecond is: "+sSecond);
			String timeSlot = lastResultTime.substring(lastResultTime.length()-2, lastResultTime.length());
			System.out.println("The timeSlot is: "+timeSlot);
			if(sDate.substring(0, 1).equals(" ")){
				sDate = sDate.substring(1, 2);
			}
			if(sHour.substring(0, 1).equals(" ")){
				sHour = sHour.substring(1, 2);
			}
			if(sMin.substring(0, 1).equals(" ")){
				sMin = sMin.substring(1, 2);
			}
			if(sSecond.substring(0, 1).equals(" ")){
				sSecond = sSecond.substring(1, 2);
			}
			//int iMonth = Integer.parseInt(sMonth);
			int iDate = Integer.parseInt(sDate);
			int iHour = Integer.parseInt(sHour);
			int iMin = Integer.parseInt(sMin);
			int iSecond = Integer.parseInt(sSecond)+1;
			
			if(iSecond>=60){
				iMin = iMin+1;
				iSecond = iSecond-60;
				if(iMin>=60){
					iHour = iHour+1;
					iMin = iMin-60;
					if(iHour>=13&&timeSlot=="PM"){
						iDate = iDate+1;
						iHour = iHour-12;
						timeSlot = "AM";
					} else if(iHour>=13&&timeSlot=="AM"){
						iHour = iHour-12;
						timeSlot = "PM";
					}
				}
			}
			
			String detailTime = String.valueOf(iHour)+":"+ String.valueOf(iMin)+":"+String.valueOf(iSecond)+" "+timeSlot;
			//time[0] = sMonth;
			time[0] = String.valueOf(iDate);
			time[1] = detailTime;
			System.out.println("The sMonth is: "+sMonth);
			System.out.println("The sDate is: "+String.valueOf(iDate));
			System.out.println("The detailTime is: "+detailTime);
		} else {
			time[0] = "null";
			time[1] = "null";
			//time[2] = "null";
		}
		
		return time;
	}
	
	public void SimulationToolAriba(int step, String Domain,String Identity,String SharedSecret,String defaultUnit){
		driver.manage().deleteAllCookies();
		driver.get(aribaUrl);
		B2BCommon.punchoutLogin(driver,punchoutPage);
		System.out.println("Go to aribaUrl, and checkout punchout successfully!");
		driver.findElement(By.xpath("//input[@id='userName']")).clear();
		driver.findElement(By.xpath("//input[@id='userName']")).sendKeys("LIeCommerce");
		driver.findElement(By.xpath("//input[@id='password']")).clear();
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("M0C0v0n3L!");
		punchoutPage.Ariba_DomainTextBox.clear();
		punchoutPage.Ariba_DomainTextBox.sendKeys(Domain);
		punchoutPage.Ariba_IdentityTextBox.clear();
		punchoutPage.Ariba_IdentityTextBox.sendKeys(Identity);
		punchoutPage.Ariba_SharedSecretTextBox.clear();
		punchoutPage.Ariba_SharedSecretTextBox.sendKeys(SharedSecret);
		punchoutPage.Ariba_PunchoutButton.click();
		Common.sleep(1000);
		driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
		Common.sleep(8000);
		if(!Common.checkElementExists(driver, punchoutPage.Lenovo_Link, 30)){
			System.out.println("Punchout fail, don't open B2B website, and punchout again!");
			Dailylog.logInfoDB(step,"Punchout fail, don't open B2B website, and punchout again!", Store,testName);
			driver.switchTo().defaultContent();
			punchoutPage.Ariba_PunchoutButton.click();
			logResult++;
			Common.sleep(1000);
			driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
		} else if(!punchoutPage.Lenovo_Link.getAttribute("href").contains("isPunchout=true")){
			System.out.println("The href is: "+punchoutPage.Lenovo_Link.getAttribute("href"));
			System.out.println("Punchout fail, the B2B website is error, and punchout again!");
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
		driver.switchTo().defaultContent();
	}
	
	public void SimulationToolOCI(String UserName,String Password,String defaultUnit){
		driver.manage().deleteAllCookies();
		driver.get(ociUrl);
		B2BCommon.punchoutLogin(driver,punchoutPage);
		System.out.println("Go to ociUrl, and checkout punchout successfully!");
		punchoutPage.OCI_UserNameTextBox.clear();
		punchoutPage.OCI_UserNameTextBox.sendKeys(UserName);
		punchoutPage.OCI_PasswordTextBox.clear();
		punchoutPage.OCI_PasswordTextBox.sendKeys(Password);
		punchoutPage.OCI_PunchoutButton.click();
		Common.sleep(1000);
		switchToWindow(1);
		System.out.println("The href is: "+punchoutPage.Lenovo_Link.getAttribute("href"));
		assert punchoutPage.Lenovo_Link.getAttribute("href").contains(defaultUnit);
		assert punchoutPage.Lenovo_Link.getAttribute("href").contains("isPunchout=true");
		System.out.println("Punchout successfully!");
		driver.close();
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
		Common.sleep(1000);
		driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
		Common.sleep(8000);
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
		driver.switchTo().defaultContent();
	}

	public void checkTracelog(String Name, String contentType){
		driver.manage().deleteAllCookies();
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Nemo.click();
		hmcPage.Home_Nemo_Punchout.click();
		hmcPage.Home_Punchout_Tracelog.click();
		hmcPage.Home_Punchout_Tracelog_UserID.sendKeys(Name);
		Select  satrtDate =new Select(hmcPage.Home_Punchout_Tracelog_StartDateSelect);
		satrtDate.selectByVisibleText("is greater or equal");
		if(!time[0].equals("null")){
			hmcPage.Home_Punchout_Tracelog_StartDateImg.click();
			switchToWindow(1);
//			String month = driver.findElement(By.xpath("//table[@class='listtable']/tbody/tr[last()]//font")).getText().substring(0,3);
//			int iMonth = 0;
//			if(month.equals("Jan")){
//				iMonth = 1;
//			} else if(month.equals("Feb")){
//				iMonth = 2;
//			} else if(month.equals("Mar")){
//				iMonth = 3;
//			} else if(month.equals("Apr")){
//				iMonth = 4;
//			} else if(month.equals("May")){
//				iMonth = 5;
//			} else if(month.equals("Jun")){
//				iMonth = 6;
//			} else if(month.equals("Jul")){
//				iMonth = 7;
//			} else if(month.equals("Aug")){
//				iMonth = 8;
//			} else if(month.equals("Sep")){
//				iMonth = 9;
//			} else if(month.equals("Oct")){
//				iMonth = 10;
//			} else if(month.equals("Nov")){
//				iMonth = 11;
//			} else if(month.equals("Dec")){
//				iMonth = 12;
//			} 
//			int index;
//			if(Integer.parseInt(time[0])==iMonth){
//				index =1;
//			} else if(Integer.parseInt(time[0])>iMonth){
//				index =2;
//			} else if(Integer.parseInt(time[0])<iMonth){
//				index =2;
//			}
			String index;
			if(Integer.parseInt(time[0])>=15){
				index="last()";
			} else {
				index="1";
			}
			driver.findElement(By.xpath("(//table[@class='listtable']/tbody//font[text()='"+time[0]+"'])["+index+"]")).click();
			switchToWindow(0);
			hmcPage.Home_Punchout_Tracelog_StartDateTime.clear();
			hmcPage.Home_Punchout_Tracelog_StartDateTime.sendKeys(time[1]);
		}
		
		hmcPage.Home_Punchout_Tracelog_Search.click();
		System.out.println("Click search  button, then search tracelog!");
		int resultNum = hmcPage.Home_Punchout_Tracelog_ResultNum.size();
		System.out.println("The resulrNum is: "+resultNum );
		Assert.assertEquals(resultNum, 5+logResult);
		String firstContentType = driver.findElement(By.xpath("//*[@id='outerTD']//table[@id='Content/McSearchListConfigurable[PunchOutTraceLog]_innertable']/tbody/tr["+(resultNum-2)+"]/td[last()]/div/div")).getText();
		System.out.println("The first contentType is: "+firstContentType );
		Assert.assertEquals(firstContentType, contentType, "The first contentType is not "+ contentType);
		
		String secondContentType = driver.findElement(By.xpath("//*[@id='outerTD']//table[@id='Content/McSearchListConfigurable[PunchOutTraceLog]_innertable']/tbody/tr["+(resultNum-1)+"]/td[last()]/div/div")).getText();
		System.out.println("The second contentType is: "+secondContentType );
		Assert.assertEquals(secondContentType, contentType, "The second contentType is not "+ contentType);
		
		String thirdContentType = driver.findElement(By.xpath("//*[@id='outerTD']//table[@id='Content/McSearchListConfigurable[PunchOutTraceLog]_innertable']/tbody/tr["+resultNum+"]/td[last()]/div/div")).getText();
		System.out.println("The third contentType is: "+thirdContentType );
		Assert.assertEquals(thirdContentType, contentType, "The third contentType is not "+ contentType);
		logResult = 0;
	}

	public void editContentTypeOfPunchoutProfile(String ProfileName,String contentType){
		hmcPage.Home_Punchout_CustomerProfile.click();
		hmcPage.PunchoutProfile_ProfileName.clear();
		hmcPage.PunchoutProfile_ProfileName.sendKeys(ProfileName);
		hmcPage.PunchoutProfile_ProfileSearch.click();
		System.out.println("Click search  button, then edit the profile!");
		Common.doubleClick(driver, hmcPage.PunchoutProfile_1stSearchedResult);
		Select  selectCxmlType =new Select(hmcPage.PunchoutProfile_ContentTypeSelectCxmlEdit);
		selectCxmlType.selectByVisibleText(contentType);
		hmcPage.PunchoutProfile_OCITab.click();
		Select  selectOCIType =new Select(hmcPage.PunchoutProfile_ContentTypeSelectOCIEdit);
		selectOCIType.selectByVisibleText(contentType);
		hmcPage.PunchoutProfile_OracleTab.click();
		Select  selectOracleType =new Select(hmcPage.PunchoutProfile_ContentTypeSelectOracleEdit);
		selectOracleType.selectByVisibleText(contentType);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("Save the edit profile successfully!");
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
	
	/*
	public void removeB2BCustomer(String B2BCustomer){
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BCustomer.click();
		hmcPage.B2BCustomer_SearchIDTextBox.clear();
		hmcPage.B2BCustomer_SearchIDTextBox.sendKeys(B2BCustomer);
		hmcPage.B2BCustomer_SearchButton.click();
		if(Common.checkElementExists(driver, hmcPage.B2BCustomer_1stSearchedResult, 10)){
			Common.rightClick(driver, hmcPage.B2BCustomer_1stSearchedResult);
			hmcPage.B2BCustomer_removeResult.click();
			driver.switchTo().alert().accept();
			System.out.println("Remove B2BCustomer!");
		} else {
			System.out.println("Don't need to remove B2BCustomer!");
		}
	}
	
	public void removePunchoutCredential(String B2BCustomer){
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
				List <WebElement> removeElement= driver.findElements(By.xpath("//input[contains(@id,'Content/StringEditor[in Content/EditableItemListEntry')][@value='"+B2BCustomer+"']"));
				int removeSize =removeElement.size();
				if(removeSize>0){
					for(int i=0; i<removeSize; i++){
						Common.rightClick(driver,removeElement.get(0));
						hmcPage.PunchoutCredential_removeCredential.click();
						driver.switchTo().alert().accept();
						System.out.println("Remove the Credential Ariba/OCI/Oracle.");
					}
				} 
				hmcPage.PunchoutCredential_searchToggleLabel.click();
				hmcPage.PunchoutCredential_CustomerSearch.clear();
				hmcPage.PunchoutCredential_CustomerSearch.sendKeys(B2BCustomer);
				driver.findElement(By.xpath(xpath)).click();
				Common.sleep(1000);
				hmcPage.PunchoutCredential_SearchButton.click();
				Common.rightClick(driver, hmcPage.PunchoutCredential_1stSearchedResult);
				hmcPage.PunchoutCredential_removeResult.click();
				driver.switchTo().alert().accept();
				System.out.println("Remove Punchout Credential!");
			} else {
				System.out.println("Don't need to remove Punchout Credential!");
			}
		} else {	
			System.out.println("Don't need to remove Punchout Credential!");
		}
	}
	
	public void editPunchoutProfile(String ProfileName,String B2BCustomer){
		Common.sleep(1000);
		System.out.println("Edit Punchout Profile!");
		AddCustomerForProfile(B2BCustomer);
		if(hmcPage.PunchoutProfile_WhetherActive.getAttribute("value").equals("false")){
			hmcPage.PunchoutProfile_Active.click();
		}
		if(hmcPage.PunchoutProfile_WhetherActiveCxml.getAttribute("value").equals("false")){
			hmcPage.PunchoutProfile_ActiveOxml.click();
		}
		hmcPage.PunchoutProfile_ContentTypeSelectCxml.click();
		hmcPage.PunchoutProfile_BlankContentTypeCxml.click();
		hmcPage.PunchoutProfile_OCITab.click();
		if(hmcPage.PunchoutProfile_WhetherActiveOCI.getAttribute("value").equals("false")){
			hmcPage.PunchoutProfile_ActiveOCI.click();
		}
		hmcPage.PunchoutProfile_ContentTypeSelectOCI.click();
		hmcPage.PunchoutProfile_BlankContentTypeOCI.click();
		hmcPage..click();
		if(hmcPage.PunchoutProfile_WhetherActiveOracle.getAttribute("value").equals("false")){
			hmcPage.PunchoutProfile_ActiveOracle.click();
		}
		hmcPage.PunchoutProfile_ContentTypeSelectOracle.click();
		hmcPage.PunchoutProfile_BlankContentTypeOracle.click();
		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("Edit Punchout Profile successfully!");
	}
	*/
	
}
