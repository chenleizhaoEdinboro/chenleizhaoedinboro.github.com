package TestScript.B2B;

import java.util.ArrayList;
import java.util.List;

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

public class NA10264Test extends SuperTestClass {
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
	public NA10264Test(String store){
		this.Store = store;
		this.testName = "NA-10264";
	}
	
	@Test(alwaysRun = true, groups = {"contentgroup", "b2bpunchout", "p2", "b2b"})
	public void NA10264(ITestContext ctx){
		try{
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			punchoutPage = new B2BPunchoutPage(driver);
			
			ociUrl = testData.B2B.getHomePageUrl().split("le/")[0]+"nemopunchouttool/oci";
			String B2BCustomer = "NA-8943-001";
			String B2BCustomerGroups = "punchOutCustomer";
			String Code = "NA-8943-001";
			String Domain = "NetworkID";
			String SharedSecret = "aaa";
			String UserName = "NA-8943-001";
			String Password = "aaa";
			String Identity = "NA-8943-001";
			String ProfileName = "na-8943-001#AU#SoldTo_07";
			String ProfileName2 = "na-8943-001#AU#SoldTo_07Copy";
			
			
			Dailylog.logInfoDB(1,"Login HMC.", Store,testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
				
			Dailylog.logInfoDB(2,"Create B2BCustome, and check if exist the custome, edit it.", Store,testName);
			checkB2BCustomer(B2BCustomer,B2BCustomerGroups);
			
			Dailylog.logInfoDB(3,"4Create Punchout Credential, and check if exist, edit it.", Store,testName);
			checkPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret,UserName,Password);
			
			Dailylog.logInfoDB(5,"Create punchout profile①，and check if exist, edit it.", Store,testName);
			checkPunchoutProfile(ProfileName,B2BCustomer,0);
			
			Dailylog.logInfoDB(6,"7,8Simulation Tool---OCI", Store,testName);
			SimulationToolOCI(UserName, Password,0);
			
			Dailylog.logInfoDB(9,"10Set Go to determination sold to---OCI", Store,testName);
			SimulationToolOCI(UserName, Password,1);
			
			Dailylog.logInfoDB(11,"12Set Go to determination sold to---OCI error", Store,testName);
			SimulationToolOCI(UserName, Password,2);
			
			Dailylog.logInfoDB(1,"Login HMC.", Store,testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_Nemo.click();
			hmcPage.Home_Nemo_Punchout.click();
			Dailylog.logInfoDB(13,"Create punchout profile② default soldto", Store,testName);
			checkPunchoutProfile(ProfileName2,B2BCustomer,1);
			
			Dailylog.logInfoDB(14,"15Simulation Tool---OCI", Store,testName);
			SimulationToolOCI(UserName, Password,3);
			
			Dailylog.logInfoDB(16,"17Simulation Tool---OCI", Store,testName);
			SimulationToolOCI(UserName, Password,4);
			
								
		}catch (Throwable e) {
			handleThrowable(e, ctx);
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

	
	public void SimulationToolOCI(String UserName,String Password,int type){
		driver.manage().deleteAllCookies();
		driver.get(ociUrl);
		B2BCommon.punchoutLogin(driver,punchoutPage);
		System.out.println("Go to ociUrl, and checkout punchout successfully!");
		if(type == 0) {
			punchoutPage.OCI_UserNameTextBox.clear();
			punchoutPage.OCI_UserNameTextBox.sendKeys(UserName);
			punchoutPage.OCI_PasswordTextBox.clear();
			punchoutPage.OCI_PasswordTextBox.sendKeys(Password);	
		}
		if(type == 1) {
			punchoutPage.OCI_UserNameTextBox.clear();
			punchoutPage.OCI_UserNameTextBox.sendKeys(UserName);
			punchoutPage.OCI_PasswordTextBox.clear();
			punchoutPage.OCI_PasswordTextBox.sendKeys(Password);	
			punchoutPage.OCI_InboundKeyTextBox.clear();
			punchoutPage.OCI_InboundKeyTextBox.sendKeys(soldTo1[1]);
			punchoutPage.OCI_InboundValueTextBox.clear();
			punchoutPage.OCI_InboundValueTextBox.sendKeys(soldTo1[2]);
		}
		if(type == 2) {
			punchoutPage.OCI_UserNameTextBox.clear();
			punchoutPage.OCI_UserNameTextBox.sendKeys(UserName);
			punchoutPage.OCI_PasswordTextBox.clear();
			punchoutPage.OCI_PasswordTextBox.sendKeys(Password);	
			punchoutPage.OCI_InboundKeyTextBox.clear();
			punchoutPage.OCI_InboundKeyTextBox.sendKeys(soldTo1[1]);
			punchoutPage.OCI_InboundValueTextBox.clear();
			punchoutPage.OCI_InboundValueTextBox.sendKeys(viewError);
		}
		if(type == 3) {
			punchoutPage.OCI_UserNameTextBox.clear();
			punchoutPage.OCI_UserNameTextBox.sendKeys(UserName);
			punchoutPage.OCI_PasswordTextBox.clear();
			punchoutPage.OCI_PasswordTextBox.sendKeys(Password);	
			punchoutPage.OCI_InboundKeyTextBox.clear();
			punchoutPage.OCI_InboundKeyTextBox.sendKeys(soldTo2[1]);
			punchoutPage.OCI_InboundValueTextBox.clear();
			punchoutPage.OCI_InboundValueTextBox.sendKeys(soldTo2[2]);
		}
		
		if(type == 4) {
			punchoutPage.OCI_UserNameTextBox.clear();
			punchoutPage.OCI_UserNameTextBox.sendKeys(UserName);
			punchoutPage.OCI_PasswordTextBox.clear();
			punchoutPage.OCI_PasswordTextBox.sendKeys(passwordErr);
			punchoutPage.OCI_InboundKeyTextBox.clear();
			punchoutPage.OCI_InboundKeyTextBox.sendKeys(soldTo2[1]);
			punchoutPage.OCI_InboundValueTextBox.clear();
			punchoutPage.OCI_InboundValueTextBox.sendKeys(soldTo2[2]);
		}
		Common.sleep(1000);
		punchoutPage.OCI_PunchoutButton.click();
		Common.sleep(1000);
		switchToWindow(1);
		Common.sleep(8000);
		if(type==0 || type == 2) {
			assert punchoutPage.Lenovo_Link.getAttribute("href").contains(defaultUnit);
		}
		if(type == 1 || type == 3) {
			assert punchoutPage.Lenovo_Link.getAttribute("href").contains(soldTo1[3]);
		}
		if(type == 4) {
			Assert.assertTrue(driver.findElement(By.xpath("//body")).getText().contains("Punchout Server Error: PunchOut Exception"));
			Assert.assertTrue(driver.findElement(By.xpath("//body")).getText().contains("Detailed error message"));
			Assert.assertTrue(driver.findElement(By.xpath("//body")).getText().contains("java.lang.Exception: No OCI credential found for given user name and password"));
		}
		System.out.println("Punchout successfully!");
		driver.close();
		switchToWindow(0);
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
	
	public void creatPunchoutCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Domain,String Identity, String SharedSecret,String UserName,String Password){
		Common.rightClick(driver, hmcPage.Home_Punchout_Credential);
		hmcPage.Home_CreatePunchoutCredential.click();
		System.out.println("Create Punchout Credential!");
		hmcPage.PunchoutCredential_CustomerInput.clear();
		hmcPage.PunchoutCredential_CustomerInput.sendKeys(B2BCustomer);
		driver.findElement(By.xpath(".//*[@id='Content/AutocompleteReferenceEditor[in Content/Attribute[.b2bCustomer]]_ajaxselect_"+B2BCustomer+"']")).click();
	
		createCredentialOCI(Code,UserName, Password);
		hmcPage.PunchoutCredential_CreateButton.click();
		System.out.println("Create Punchout Credential successfully!");
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
		addCredentialOCI(Code,UserName, Password);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("Create Punchout Credential successfully!");
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
		
		hmcPage.PunchoutProfile_OCITab.click();
		if(hmcPage.PunchoutProfile_WhetherActiveOCI.getAttribute("value").equals("false")){
			hmcPage.PunchoutProfile_ActiveOCI.click();
		}
		Common.rightClick(driver, driver.findElement(By.xpath(".//*[contains(text(),'Inbound Rules:')]/../../td[last()]//table//table/tbody//tr[1]//th[3]")));
		driver.findElement(By.xpath(".//*[text()='Create Sold To Determination']")).click(); 
		Common.sleep(3000);
		if(type == 0) {
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]"
					+ "//table//table/tbody/tr[last()]//table//input)[1]")).sendKeys(soldTo1[0]);
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]"
					+ "//table//table/tbody/tr[last()]//table//input)[2]")).sendKeys(soldTo1[1]);
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]"
					+ "//table//table/tbody/tr[last()]//table//input)[3]")).sendKeys(soldTo1[2]);
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]"
					+ "//table//table/tbody/tr[last()]//table//input)[4]")).sendKeys(soldTo1[3]);
			Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+soldTo1[3]+"')][text()='"+soldTo1[3]+"']")), 15);
			driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+soldTo1[3]+"')][text()='"+soldTo1[3]+"']")).click();
		}
		
		if(type == 1) {
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]"
					+ "//table//table/tbody/tr[last()]//table//input)[1]")).sendKeys(soldTo2[0]);
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]"
					+ "//table//table/tbody/tr[last()]//table//input)[2]")).sendKeys(soldTo2[1]);
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]"
					+ "//table//table/tbody/tr[last()]//table//input)[3]")).sendKeys(soldTo2[2]);
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]"
					+ "//table//table/tbody/tr[last()]//table//input)[4]")).sendKeys(soldTo2[3]);
			Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+soldTo2[3]+"')][text()='"+soldTo2[3]+"']")), 15);
			driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+soldTo2[3]+"')][text()='"+soldTo2[3]+"']")).click();
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
	
			